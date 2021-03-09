package co.com.jcga.rest.client.request;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Order {

	private final String accountId;
	private final String referenceCode;
	private final String description;
	private final String language;
	private final String signature;
	private final String notifyUrl;
	private final Map<String, TxValues> additionalValues;
	private final Buyer buyer;
	private final Address shippingAddress;
}
