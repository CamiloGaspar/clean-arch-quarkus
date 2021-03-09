package co.com.jcga.converter;

import co.com.jcga.database.model.UserModel;
import co.com.jcga.rest.client.request.Buyer;
import co.com.jcga.rest.client.request.Payer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi",
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		injectionStrategy = InjectionStrategy.FIELD)
public interface ProviderConverter {

	@Mapping(source = "merchantId", target = "merchantBuyerId")
	@Mapping(source = "address", target = "shippingAddress")
	Buyer toBuyerRequest(UserModel userModel);

	@Mapping(source = "merchantId", target = "merchantPayerId")
	@Mapping(source = "address", target = "billingAddress")
	Payer toPayerRequest(UserModel userModel);

}
