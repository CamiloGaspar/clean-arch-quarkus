package co.com.jcga.entrypoint.converter;

import co.com.jcga.entity.Payment;
import co.com.jcga.entity.PaymentParams;
import co.com.jcga.entrypoint.dto.PaymentDto;
import co.com.jcga.entrypoint.dto.PaymentParamsDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi",
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		injectionStrategy = InjectionStrategy.FIELD)
public interface PaymentsDtoMapper {

	@Mapping(source = "transactionTypeDto", target = "transactionType")
	PaymentParams toEntity(PaymentParamsDto paymentParamsDto);

	PaymentDto toDto(Payment payment);
}
