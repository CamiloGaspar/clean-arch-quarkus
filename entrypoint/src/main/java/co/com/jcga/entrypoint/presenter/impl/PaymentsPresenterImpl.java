package co.com.jcga.entrypoint.presenter.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import co.com.jcga.entrypoint.converter.PaymentsDtoMapper;
import co.com.jcga.entrypoint.dto.PaymentDto;
import co.com.jcga.entrypoint.dto.PaymentParamsDto;
import co.com.jcga.entrypoint.presenter.PaymentsPresenter;
import co.com.jcga.usecase.PaymentsUseCase;

@ApplicationScoped
public class PaymentsPresenterImpl implements PaymentsPresenter {

	private final PaymentsUseCase paymentsUseCase;

	private final PaymentsDtoMapper paymentsDtoMapper;

	@Inject
	public PaymentsPresenterImpl(final PaymentsUseCase paymentsUseCase,
								 final PaymentsDtoMapper paymentsDtoMapper) {

		this.paymentsUseCase = paymentsUseCase;
		this.paymentsDtoMapper = paymentsDtoMapper;
	}

	@Override
	public PaymentDto executePayment(final PaymentParamsDto paymentParamsDto) {

		return paymentsDtoMapper
				.toDto(paymentsUseCase.makePayment(paymentsDtoMapper.toEntity(paymentParamsDto)));

	}
}
