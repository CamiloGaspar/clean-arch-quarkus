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
public class PaymentParamsDto {

	private UserDataDto userDataDto;
	private CreditCardDataDto creditCardDataDto;
	private Double value;
	@JsonProperty("transaction_type")
	private TransactionTypeDto transactionTypeDto;
}
