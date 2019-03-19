package lt.smart.home.networking;

import io.reactivex.Completable;
import io.reactivex.Observable;
import lt.smart.home.model.response.ExchangeResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("currency/commercial/exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest")
    Observable<ExchangeResponse> exchange(@Path("fromAmount") String fromAmount,
                                          @Path("fromCurrency") String fromCurrency,
                                          @Path("toCurrency") String toCurrency);

    @POST("https://eu-wap.tplinkcloud.com/?token=f7195d59-B6ZiJHt62rU5nXMJRY9mNoS&appName=Kasa_Android&termID=3889981d-c069-4127-aeec-82111c8f9c9f&appVer=1.4.4.607&ospf=Android+6.0.1&netType=wifi&locale=en_US HTTP/1.1")
    Completable turnLight(@Body TurnLightRequest turnLightRequest);
}
