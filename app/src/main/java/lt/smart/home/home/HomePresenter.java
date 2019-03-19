package lt.smart.home.home;

import javax.inject.Inject;

import lt.smart.home.base.BasePresenter;
import lt.smart.home.base.ErrorHandler;
import lt.smart.home.networking.repository.LightRepository;

public class HomePresenter extends BasePresenter<IHomeView> {

    private final LightRepository lightRepository;

    @Inject
    public HomePresenter(ErrorHandler errorHandler, LightRepository lightRepository) {
        super(errorHandler);
        this.lightRepository = lightRepository;
    }

    void turnLight(boolean on){
        subscriptions.add(lightRepository.turnLight(on)
                .subscribe(()->{},
                        throwable -> getView().showError(throwable, errorHandler.getErrorMessage(throwable))));
    }
}
