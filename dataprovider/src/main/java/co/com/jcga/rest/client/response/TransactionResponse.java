package co.com.jcga.rest.client.response;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {

	private Long orderId;
	private UUID transactionId;
	private String state;
	private String paymentNetworkResponseCode;
	private String paymentNetworkResponseErrorMessage;
	private String trazabilityCode;
	private String authorizationCode;
	private String pendingReason;
	private String responseCode;
	private String errorCode;
	private String responseMessage;
	private String transactionDate;
	private String transactionTime;
	private String referenceQuestionnaire;
	private Date operationDate;
	private Map<String, Object> extraParameters;
	private Map<String, Object> additionalInfo;
}
