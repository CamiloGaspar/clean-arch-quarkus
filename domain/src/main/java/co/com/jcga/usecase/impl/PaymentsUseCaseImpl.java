package co.com.jcga.usecase.impl;

import co.com.jcga.entity.Payment;
import co.com.jcga.entity.PaymentParams;
import co.com.jcga.provider.PaymentsProvider;
import co.com.jcga.usecase.PaymentsUseCase;

public class PaymentsUseCaseImpl implements PaymentsUseCase {

	private final PaymentsProvider paymentsProvider;

	public PaymentsUseCaseImpl(PaymentsProvider paymentsProvider) {

		this.paymentsProvider = paymentsProvider;
	}

	@Override
	public Payment makePayment(final PaymentParams paymentParams) {

		return paymentsProvider.pay(paymentParams);
	}
}
