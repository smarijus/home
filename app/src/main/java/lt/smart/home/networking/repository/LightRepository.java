package lt.smart.home.networking.repository;

import io.reactivex.Completable;
import lt.smart.home.networking.ApiService;
import lt.smart.home.networking.SchedulerProvider;
import lt.smart.home.networking.TurnLightRequest;

public class LightRepository extends BaseRepository {

    public LightRepository(ApiService api, SchedulerProvider schedulerProvider) {
        super(api, schedulerProvider);
    }

    public Completable turnLight(boolean on){
        return api.turnLight(new TurnLightRequest(on))
                .compose(schedulerProvider.applySchedulersForCompletable());
    }
}
