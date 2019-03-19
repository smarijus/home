package lt.smart.home.networking.repository;

import lt.smart.home.networking.ApiService;
import lt.smart.home.networking.SchedulerProvider;

public abstract class BaseRepository {

    protected final ApiService api;
    protected final SchedulerProvider schedulerProvider;

    public BaseRepository(ApiService api, SchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
    }

}