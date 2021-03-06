package co.com.jcga.rest.client.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {

	private final String code;
	private final String error;
	private final TransactionResponse transactionResponse;
}
