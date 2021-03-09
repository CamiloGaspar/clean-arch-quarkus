package co.com.jcga.rest.client;

import co.com.jcga.rest.client.request.PaymentRequest;
import co.com.jcga.rest.client.response.PaymentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PaymentRestClient {

	@Headers("Accept: application/json")
	@POST("4.0/service.cgi")
	Call<PaymentResponse> pay(@Body PaymentRequest paymentRequest);
}
