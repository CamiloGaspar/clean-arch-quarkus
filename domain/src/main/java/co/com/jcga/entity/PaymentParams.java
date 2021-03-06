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

	private String email;
	private Double value;
	private Long creditCardNumber;
	private String expireDate;
	private Integer cvv;
}