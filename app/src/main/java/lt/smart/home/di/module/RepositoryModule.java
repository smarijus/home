package lt.smart.home.di.module;

import dagger.Module;
import dagger.Provides;
import lt.smart.home.networking.ApiService;
import lt.smart.home.networking.SchedulerProvider;
import lt.smart.home.networking.repository.ExchangeRepository;
import lt.smart.home.networking.repository.LightRepository;

@Module
public class RepositoryModule {

    @Provides
    public ExchangeRepository provideExchangeRepository(ApiService apiService, SchedulerProvider schedulerProvider) {
        return new ExchangeRepository(apiService, schedulerProvider);
    }
    @Provides
    public LightRepository provideLightRepository(ApiService apiService, SchedulerProvider schedulerProvider) {
        return new LightRepository(apiService, schedulerProvider);
    }
}
