package co.com.jcga.provider.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import co.com.jcga.converter.ProviderConverter;
import co.com.jcga.database.model.UserModel;
import co.com.jcga.database.repository.UserRepository;
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
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@ApplicationScoped
@Slf4j
public class PaymentsProviderImpl implements PaymentsProvider {

	public static final int DEFAULT_TIMEOUT = 20;

	private final PaymentRestClient paymentRestClient;

	private final UserRepository userRepository;

	private final ProviderConverter providerConverter;

	@Inject
	public PaymentsProviderImpl(@ConfigProperty(name = "payment.rest.client.url") final Optional<String> paymentUrl,
								final UserRepository userRepository,
								final ProviderConverter providerConverter) {

		this.userRepository = userRepository;
		this.providerConverter = providerConverter;

		final var logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		final var httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(logging)
				  .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
				  .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		log.info(paymentUrl.toString());
		final var retrofit = new Retrofit.Builder()
				.baseUrl(paymentUrl.orElse("https://sandbox.api.payulatam.com/payments-api/"))
				.addConverterFactory(JacksonConverterFactory.create())
				.client(httpClient.build())
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
										  .message(paymentResponse.getTransactionResponse().getResponseMessage()),
				() -> payment.message("Success payment"));

	}

	private PaymentRequest buildPaymentRequest(final PaymentParams paymentParams) {

		final var user = userRepository.findById(paymentParams.getUserDataDto().getEmail());

		final var buyer = providerConverter.toBuyerRequest(user);
		final var payer = providerConverter.toPayerRequest(user);
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

		return PaymentRequest.builder()
							 .command("SUBMIT_TRANSACTION")
							 .language("es")
							 .merchant(Merchant.builder()
											   .apiKey(apiKey)
											   .apiLogin("pRRXKOl8ikMmt9u")
											   .build())
							 .transaction(Transaction.builder()
													 .order(Order.builder()
																 .accountId("512321")
																 .referenceCode(referenceCode)
																 .description("payment test")
																 .language("es")
																 .signature(signature)
																 .notifyUrl("https//www.test.com")
																 .additionalValues(additionalValues)
																 .buyer(buyer)
																 .shippingAddress(buyer.getShippingAddress())
																 .build())
													 .payer(payer)
													 .creditCard(CreditCard.builder()
																		   .name("APPROVED")
																		   .expirationDate(paymentParams.getCreditCardDataDto().getExpireDate())
																		   .number(paymentParams.getCreditCardDataDto().getCreditCardNumber())
																		   .securityCode(paymentParams.getCreditCardDataDto().getCvv())
																		   .build())
													 .extraParameters(extraParameters)
													 .type(paymentParams.getTransactionType().name())
													 .paymentMethod("VISA")
													 .paymentCountry("CO")
													 .deviceSessionId("vghs6tvkcle931686k1900o6e1")
													 .ipAddress("127.0.0.1")
													 .cookie("pt1t38347bs6jc9ruv2ecpv7o2")
													 .userAgent(
															 "Mozilla/5.0 (Windows NT 5.1; rv:18.0) Gecko/20100101 Firefox/18.0")
													 .build())
							 .test(true)
							 .build();
	}
}
