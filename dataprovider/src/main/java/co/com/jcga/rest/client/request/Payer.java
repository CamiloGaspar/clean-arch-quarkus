package co.com.jcga.rest.client.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Payer {

	private final String merchantPayerId;
	private final String fullName;
	private final String emailAddress;
	private final String contactPhone;
	private final String dniNumber;
	private final Address billingAddress;
}
