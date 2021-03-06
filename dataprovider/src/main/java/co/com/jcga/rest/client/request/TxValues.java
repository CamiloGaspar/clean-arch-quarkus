package co.com.jcga.rest.client.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TxValues {
	private final Long value;
	private final String currency;
}
