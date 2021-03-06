package co.com.jcga.rest.client.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreditCard {

	private final String number;
	private final String securityCode;
	private final String expirationDate;
	private final String name;
}
