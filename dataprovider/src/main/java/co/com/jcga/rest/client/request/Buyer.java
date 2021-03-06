package co.com.jcga.rest.client.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Buyer {

	private final String merchantBuyerID;
	private final String fullName;
	private final String emailAddress;
	private final String contactPhone;
	private final String dniNumber;
	private final Address shippingAddress;
}
