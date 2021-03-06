package co.com.jcga.rest.client.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Merchant {
	private final String apiKey;
	private final String apiLogin;
}
