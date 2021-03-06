package co.com.jcga;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import co.com.jcga.provider.PaymentsProvider;
import co.com.jcga.usecase.PaymentsUseCase;
import co.com.jcga.usecase.impl.PaymentsUseCaseImpl;

@Dependent
public class BeanInitializer {

	@Produces
	public PaymentsUseCase buildPaymentsUseCase(final PaymentsProvider paymentsProvider){
		return new PaymentsUseCaseImpl(paymentsProvider);
	}
}
