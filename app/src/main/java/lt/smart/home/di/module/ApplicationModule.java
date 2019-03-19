package lt.smart.home.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lt.smart.home.BuildConfig;
import lt.smart.home.common.AppSharedPreferences;
import lt.smart.home.common.DataHolder;
import lt.smart.home.networking.ApiService;
import lt.smart.home.networking.AppHttpLoggingInterceptor;
import lt.smart.home.networking.NullOnEmptyConverterFactory;
import lt.smart.home.networking.RequestInterceptor;
import lt.smart.home.networking.RxErrorHandlingCallAdapterFactory;
import lt.smart.home.networking.SchedulerProvider;
import lt.smart.home.networking.TLSSocketFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import timber.log.Timber;

@Module
public class ApplicationModule {

    private final Application app;

    public ApplicationModule(Application application) {
        this.app = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(RequestInterceptor requestInterceptor, AppHttpLoggingInterceptor appHttpLoggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .followRedirects(false)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(requestInterceptor)
                .addInterceptor(appHttpLoggingInterceptor);
        TLSSocketFactory tlsSocketFactory;
        try {
            tlsSocketFactory = new TLSSocketFactory();
            builder.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            Timber.e(e, "Failed to create Socket connection ");
        }
        return builder.build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .baseUrl(BuildConfig.SERVER_URL)
                .build();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
    }

    @Provides
    @Singleton
    public GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    public SchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.DEFAULT;
    }

    @Provides
    @Singleton
    public AppHttpLoggingInterceptor provideAppHttpLoggingInterceptor() {
        AppHttpLoggingInterceptor logging = new AppHttpLoggingInterceptor(message -> Timber.d(message));
        logging.setLevel(AppHttpLoggingInterceptor.Level.BODY_ONLY_JSON);
        return logging;
    }



    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    public AppSharedPreferences provideAppSharedPreferences() {
        return new AppSharedPreferences(app.getSharedPreferences(AppSharedPreferences.KEY_NAME, Context.MODE_PRIVATE));
    }

    @Provides
    @Singleton
    public DataHolder provideDataHolder(){
        return new DataHolder();
    }
}
