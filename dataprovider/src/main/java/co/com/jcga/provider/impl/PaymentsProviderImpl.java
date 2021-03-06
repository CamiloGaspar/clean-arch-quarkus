package co.com.jcga.provider.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;

import co.com.jcga.entity.Payment;
import co.com.jcga.entity.PaymentParams;
import co.com.jcga.provider.PaymentsProvider;
import co.com.jcga.rest.client.PaymentRestClient;
import co.com.jcga.rest.client.request.Address;
import co.com.jcga.rest.client.request.Buyer;
import co.com.jcga.rest.client.request.CreditCard;
import co.com.jcga.rest.client.request.Merchant;
import co.com.jcga.rest.client.request.Order;
import co.com.jcga.rest.client.request.Payer;
import co.com.jcga.rest.client.request.PaymentRequest;
import co.com.jcga.rest.client.request.Transaction;
import co.com.jcga.rest.client.request.TxValues;
import co.com.jcga.rest.client.response.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@ApplicationScoped
@Slf4j
public class PaymentsProviderImpl implements PaymentsProvider {

	private final PaymentRestClient paymentRestClient;

	@Inject
	public PaymentsProviderImpl(@ConfigProperty(name = "payment.rest.client.url") Optional<String> paymentUrl) {

		log.info(paymentUrl.toString());
		final var retrofit = new Retrofit.Builder()
				.baseUrl("https://sandbox.api.payulatam.com/payments-api/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		paymentRestClient = retrofit.create(PaymentRestClient.class);
	}

	@Override
	public Payment pay(final PaymentParams paymentParams) {

		return executeRequest(paymentRestClient.pay(buildPaymentRequest(paymentParams)));
	}

	private Payment executeRequest(final Call<PaymentResponse> pay) {

		final var payment = Payment.builder();
		try {
			final var response = pay.execute();
			if (response.isSuccessful()) {
				buildSuccessResponse(Optional.ofNullable(response.body()), payment);
			} else {
				buildErrorResponse(Optional.ofNullable(response.errorBody()), payment);
			}
		} catch (IOException e) {
			payment.errors(e.getMessage());
		}
		return payment.build();
	}

	private void buildErrorResponse(final Optional<ResponseBody> optionalErrorBody, final Payment.Builder payment) {

		optionalErrorBody.ifPresentOrElse(errorBody -> {
			try {
				payment.errors(errorBody.string());
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				payment.errors(e.getMessage());
			}
		}, () -> payment.errors("Failed payment"));
	}

	private void buildSuccessResponse(final Optional<PaymentResponse> optionalPaymentResponse,
									  final Payment.Builder payment) {

		optionalPaymentResponse.ifPresentOrElse(
				paymentResponse -> payment.date(paymentResponse.getTransactionResponse().getOperationDate())
										  .message("Success payment"), () -> payment.message("Success payment"));

	}

	private PaymentRequest buildPaymentRequest(final PaymentParams paymentParams) {

		final var apiKey = "4Vj8eK4rloUd272L48hsrarnUA";
		final var merchantId = "508029";
		final var referenceCode = "TEST_" + Instant.now().toString();
		final var txValue = 10000L;
		final var currency = "COP";
		final var stringSignature = apiKey + "~" + merchantId + "~" + referenceCode + "~" + txValue + "~" + currency;
		final var signature = DigestUtils.md5Hex(stringSignature);
		final var additionalValues = new HashMap<String, TxValues>();
		additionalValues.put("TX_VALUE", TxValues.builder().currency(currency).value(txValue).build());
		final var extraParameters = new HashMap<String, Object>();
		extraParameters.put("INSTALLMENTS_NUMBER", 1);

		final var address = Address.builder()
								   .city("")
								   .country("")
								   .phone("")
								   .postalCode("")
								   .state("")
								   .street1("")
								   .street2("")
								   .state("")
								   .build();

		return PaymentRequest.builder()
							 .command("SUBMIT_TRANSACTION")
							 .language("es")
							 .merchant(Merchant.builder()
											   .apiKey(apiKey)
											   .apiLogin("pRRXKOl8ikMmt9u")
											   .build())
							 .transaction(Transaction.builder()
													 .order(Order.builder()
																 .accountID("512321")
																 .referenceCode(referenceCode)
																 .description("Houston payment test")
																 .language("es")
																 .signature(signature)
																 .notifyURL("https//www.test.com")
																 .additionalValues(additionalValues)
																 .buyer(Buyer.builder()
																			 .merchantBuyerID(merchantId)
																			 .contactPhone("")
																			 .dniNumber("")
																			 .emailAddress(paymentParams.getEmail())
																			 .fullName("")
																			 .shippingAddress(address)
																			 .build())
																 .shippingAddress(address)
																 .build())
													 .payer(Payer.builder()
																 .merchantPayerID(merchantId)
																 .contactPhone("")
																 .dniNumber("")
																 .emailAddress("")
																 .fullName("")
																 .billingAddress(address)
																 .build())
													 .creditCard(CreditCard.builder()
																		   .name("APPROVED")
																		   .expirationDate("2022/12")
																		   .number("4097440000000004")
																		   .securityCode("321")
																		   .build())
													 .extraParameters(extraParameters)
													 .type("AUTHORIZATION_AND_CAPTURE")
													 .paymentMethod("VISA")
													 .paymentCountry("CO")
													 .deviceSessionID("vghs6tvkcle931686k1900o6e1")
													 .ipAddress("")
													 .cookie("")
													 .userAgent("")
													 .build())
							 .test(true)
							 .build();
	}
}
