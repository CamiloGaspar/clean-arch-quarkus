package co.com.jcga.entrypoint.presenter;

import co.com.jcga.entrypoint.dto.PaymentDto;
import co.com.jcga.entrypoint.dto.PaymentParamsDto;

public interface PaymentsPresenter {

	PaymentDto executePayment(PaymentParamsDto paymentParamsDto);

}
