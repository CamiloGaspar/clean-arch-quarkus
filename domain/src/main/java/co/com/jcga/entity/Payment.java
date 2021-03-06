package co.com.jcga.entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
@Builder(builderClassName = "Builder")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

	private Date date;
	private String message;
	@Singular("errors")
	private List<String> errors;

	public boolean hasError() {

		return !errors.isEmpty();
	}

}
