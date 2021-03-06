package co.com.jcga.provider;

import co.com.jcga.entity.Payment;
import co.com.jcga.entity.PaymentParams;

public interface PaymentsProvider {

	Payment pay(PaymentParams paymentParams);
}
