package lt.smart.home.networking;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import lt.smart.home.common.exceptions.NoNetworkException;
import lt.smart.home.common.exceptions.ServerException;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    public static final int INVALID_TOKEN_ERROR_CODE = 100;

    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        this.original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public CallAdapter<Call, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, convertToCallType(original.get(returnType, annotations, retrofit), CallAdapter.class));
    }

    private static CallAdapter<Call, Object> convertToCallType(CallAdapter o, Class clazz) {
        try {
            return (CallAdapter<Call, Object>) clazz.cast(o);
        } catch (ClassCastException e) {
            return null;
        }
    }

    private static class RxCallAdapterWrapper implements CallAdapter<Call, Object> {
        private final Retrofit retrofit;
        private final CallAdapter<Call, Object> wrapped;

        private RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<Call, Object> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @Override
        public Object adapt(Call<Call> call) {
            Object adaptedCall = wrapped.adapt(call);

            if (adaptedCall instanceof Completable) {
                return ((Completable) adaptedCall).onErrorResumeNext(
                        throwable -> Completable.error(asRetrofitException(throwable, call.request())));
            }

            if (adaptedCall instanceof Single) {
                return ((Single) adaptedCall).onErrorResumeNext(
                        throwable -> Single.error(asRetrofitException((Throwable) throwable, call.request())));
            }

            if (adaptedCall instanceof Observable) {
                return ((Observable) adaptedCall).onErrorResumeNext((Function<? super Throwable, ? extends ObservableSource>)
                        throwable -> Observable.error(asRetrofitException(throwable, call.request())));
            }

            throw new RuntimeException("Observable Type not supported");
        }

        private Throwable asRetrofitException(Throwable throwable, Request request) {
            if (throwable instanceof SocketTimeoutException || throwable instanceof ConnectException) {
                return new ServerException();
            } else if (throwable instanceof UnknownHostException) {
                return new NoNetworkException();
            }
            return throwable;
        }
    }

}
