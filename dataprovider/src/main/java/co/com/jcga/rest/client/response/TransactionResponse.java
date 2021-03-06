package co.com.jcga.rest.client.response;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionResponse {
	private final Long orderID;
	private final UUID transactionID;
	private final String state;
	private final String paymentNetworkResponseCode;
	private final String paymentNetworkResponseErrorMessage;
	private final String trazabilityCode;
	private final String  authorizationCode;
	private final String pendingReason;
	private final String responseCode;
	private final String errorCode;
	private final String responseMessage;
	private final String transactionDate;
	private final String transactionTime;
	private final Date operationDate;
	private final Map<String, Object> extraParameters;
}
