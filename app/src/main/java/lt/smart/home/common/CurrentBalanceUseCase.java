package lt.smart.home.common;

import android.content.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import lt.smart.home.R;
import lt.smart.home.common.exceptions.SameCurrencyTypeException;
import lt.smart.home.common.exceptions.NotEnoughMoneyException;
import lt.smart.home.constans.enums.CurrencyType;
import lt.smart.home.constans.enums.PayFeeType;
import lt.smart.home.home.CurrentBalanceItem;
import lt.smart.home.networking.repository.ExchangeRepository;
import timber.log.Timber;

public class CurrentBalanceUseCase {

    private final ExchangeRepository exchangeRepository;
    private final DataHolder dataHolder;
    private final Context context;

    @Inject
    public CurrentBalanceUseCase(ExchangeRepository exchangeRepository, DataHolder dataHolder, Context context) {
        this.exchangeRepository = exchangeRepository;
        this.dataHolder = dataHolder;
        this.context = context;
    }

    public Observable<List<CurrentBalanceItem>> loadCurrentBalance() {
        List<CurrentBalanceItem> results = new ArrayList<>();
        for (Map.Entry<CurrencyType, BigDecimal> key : dataHolder.getCurrentBalance().entrySet()) {
            results.add(new CurrentBalanceItem(key));
        }
        return Observable.just(results);
    }

    public Observable<List<CurrencyType>> loadCurrentFromCurrency() {
        return Observable.just(new ArrayList<>(dataHolder.getCurrentBalance().keySet()));
    }

    public Observable<List<CurrencyType>> loadToCurrency(CurrencyType fromCurrencyType) {
        List<CurrencyType> results = new ArrayList<>();
        for (CurrencyType currencyType : CurrencyType.values()) {
            if (currencyType != fromCurrencyType) results.add(currencyType);
        }
        return Observable.just(results);
    }

    public Observable<String> exchange(String fromAmountInput, CurrencyType fromCurrencyType, CurrencyType toCurrencyType) {
        BigDecimal fromAmount = parse(fromAmountInput);
        BigDecimal currentBalance = dataHolder.getCurrentBalance().get(fromCurrencyType);
        PayFeeType payFeeType = PayFeeType.get(fromAmount, dataHolder.getTransactionCount());
        BigDecimal payFee = payFeeType.getPayFeeAmount(fromAmount);
        BigDecimal discharge = fromAmount.add(payFee);

        if (fromCurrencyType == toCurrencyType) {
            return Observable.error(new SameCurrencyTypeException());
        }

        if (currentBalance.subtract(discharge).compareTo(BigDecimal.ZERO) < 0) {
            return Observable.error(new NotEnoughMoneyException());
        }
       
        return exchangeRepository.exchange(fromAmount, fromCurrencyType, toCurrencyType)
                .flatMap(exchangeResponse -> {
                    dataHolder.onIncomes(exchangeResponse.getCurrency(), exchangeResponse.getAmount());
                    dataHolder.onOutcomes(fromCurrencyType, discharge);
                    dataHolder.addPayFee(fromCurrencyType, payFee);
                    dataHolder.increaseTransactionCount();
                    return Observable.just(context.getString(R.string.transaction_successful,
                            fromAmount.setScale(2, RoundingMode.CEILING).toPlainString(),
                            fromCurrencyType.name(),
                            exchangeResponse.getAmount().setScale(2, RoundingMode.CEILING).toPlainString(),
                            exchangeResponse.getCurrency().name(),
                            payFee.setScale(2, RoundingMode.CEILING).toPlainString(),
                            fromCurrencyType.name(),
                            payFeeType.getDescriptionId(context)));
                });
    }

    public BigDecimal parse(String inputValue) {
        try {
            return new BigDecimal(inputValue);
        } catch (NumberFormatException e) {
            Timber.w(e);
            return null;
        }
    }
    public Observable<List<CurrentBalanceItem>> loadPaymentFee() {
        List<CurrentBalanceItem> results = new ArrayList<>();
        for (Map.Entry<CurrencyType, BigDecimal> key : dataHolder.getTotalFee().entrySet()) {
            results.add(new CurrentBalanceItem(key));
        }
        return Observable.just(results);
    }
}
