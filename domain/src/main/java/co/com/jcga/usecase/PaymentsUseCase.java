package co.com.jcga.usecase;

import co.com.jcga.entity.Payment;
import co.com.jcga.entity.PaymentParams;

public interface PaymentsUseCase {


	Payment makePayment(PaymentParams paymentParams);

}
