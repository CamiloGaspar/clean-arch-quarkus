package co.com.jcga.rest.client.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Address {

	private final String street1;
	private final String street2;
	private final String city;
	private final String state;
	private final String country;
	private final String postalCode;
	private final String phone;
}
