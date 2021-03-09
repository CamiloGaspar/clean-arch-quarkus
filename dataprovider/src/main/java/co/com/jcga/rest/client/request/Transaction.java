package co.com.jcga.rest.client.request;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Transaction {

	private final Order order;
	private final Payer payer;
	private final CreditCard creditCard;
	private final Map<String, Object> extraParameters;
	private final String type;
	private final String paymentMethod;
	private final String paymentCountry;
	private final String deviceSessionId;
	private final String ipAddress;
	private final String cookie;
	private final String userAgent;
}
