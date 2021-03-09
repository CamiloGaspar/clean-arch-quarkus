package co.com.jcga.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardData {
	private String creditCardNumber;
	private String expireDate;
	private String cvv;
}
