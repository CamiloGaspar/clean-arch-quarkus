package co.com.jcga.rest.client.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentRequest {

	private final String language;
	private final String command;
	private final Merchant merchant;
	private final Transaction transaction;
	private final Boolean test;
}
