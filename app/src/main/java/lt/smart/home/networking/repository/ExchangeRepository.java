package lt.smart.home.networking.repository;

import java.math.BigDecimal;

import javax.inject.Inject;

import io.reactivex.Observable;
import lt.smart.home.constans.enums.CurrencyType;
import lt.smart.home.model.response.ExchangeResponse;
import lt.smart.home.networking.ApiService;
import lt.smart.home.networking.SchedulerProvider;

public class ExchangeRepository extends BaseRepository {

    @Inject
    public ExchangeRepository(ApiService api, SchedulerProvider schedulerProvider) {
        super(api, schedulerProvider);
    }

    public Observable<ExchangeResponse> exchange(BigDecimal fromAmount, CurrencyType fromCurrency, CurrencyType toCurrency) {
        return api.exchange(fromAmount.toPlainString(), fromCurrency.name(), toCurrency.name())
                .compose(schedulerProvider.applySchedulers());
    }
}
