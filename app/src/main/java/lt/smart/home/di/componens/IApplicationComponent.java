package lt.smart.home.di.componens;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import lt.smart.home.ApplicationApp;
import lt.smart.home.base.ErrorHandler;
import lt.smart.home.common.AppSharedPreferences;
import lt.smart.home.common.CurrentBalanceUseCase;
import lt.smart.home.common.DataHolder;
import lt.smart.home.di.module.ApplicationModule;
import lt.smart.home.di.module.RepositoryModule;
import lt.smart.home.home.HomeController;
import lt.smart.home.networking.ApiService;
import lt.smart.home.networking.SchedulerProvider;
import lt.smart.home.networking.repository.ExchangeRepository;
import lt.smart.home.networking.repository.LightRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class})
public interface IApplicationComponent {

    void inject(ApplicationApp applicationApp);

    void inject(HomeController homeController);

    Context getContext();

    ErrorHandler getErrorHandler();

    CurrentBalanceUseCase getCurrentBalanceUseCase();

    DataHolder getDataHolder();

    Retrofit getRetrofit();

    SchedulerProvider getSchedulerProvider();

    Gson getGson();

    GsonConverterFactory getGsonConverterFactory();

    AppSharedPreferences getAppSharedPreferences();

    ApiService getApiService();

    ExchangeRepository getExchangeRepository();

    LightRepository getLightRepository();
}
