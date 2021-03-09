package co.com.jcga.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardDataDto {

	@JsonProperty("credit_card_number")
	private String creditCardNumber;
	@JsonProperty("expire_date")
	private String expireDate;
	private String cvv;
}
