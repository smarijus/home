package lt.smart.home.networking;


import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class RequestInterceptor implements Interceptor {

    private static final String TAG = RequestInterceptor.class.getSimpleName();

    @Inject
    public RequestInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String query = request.url().query();
        String newUrl;
        long timestamp = System.currentTimeMillis();
        if (query == null || query.isEmpty()) {
            newUrl = request.url() + "?timestamp=" + timestamp;
        } else {
            newUrl = request.url() + "&timestamp=" + timestamp;
        }

        request = request.newBuilder()
                .url(newUrl)
                .build();

        return chain.proceed(request);
    }
}
