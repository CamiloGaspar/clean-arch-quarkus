package co.com.jcga.entrypoint.route;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.com.jcga.entrypoint.dto.PaymentDto;
import co.com.jcga.entrypoint.dto.PaymentParamsDto;
import co.com.jcga.entrypoint.presenter.PaymentsPresenter;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentsRoute {

	private final PaymentsPresenter paymentsPresenter;

	@Inject
	public PaymentsRoute(final PaymentsPresenter paymentsPresenter) {

		this.paymentsPresenter = paymentsPresenter;
	}

	@POST
	public PaymentDto payment(PaymentParamsDto paymentParamsDto) {

		return paymentsPresenter.executePayment(paymentParamsDto);
	}
}
