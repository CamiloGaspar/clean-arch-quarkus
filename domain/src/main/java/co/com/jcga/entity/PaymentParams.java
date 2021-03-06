package co.com.jcga.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentParams {

	private UserData userDataDto;
	private CreditCardData creditCardDataDto;
	private TransactionType transactionType;
	private Double value;
}
