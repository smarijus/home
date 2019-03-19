package lt.smart.home.base;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import lt.smart.home.R;
import lt.smart.home.common.exceptions.NoNetworkException;
import lt.smart.home.common.exceptions.NotEnoughMoneyException;
import lt.smart.home.common.exceptions.SameCurrencyTypeException;

@Singleton
public class ErrorHandler {

    private final Context context;

    @Inject
    public ErrorHandler(Context context) {
        this.context = context;
    }

    public String getErrorMessage(Throwable throwable) {
        if (throwable instanceof NotEnoughMoneyException) {
            return context.getString(R.string.error_not_enough_money);
        } else if (throwable instanceof SameCurrencyTypeException) {
            return context.getString(R.string.error_same_currency_type);
        } else if (throwable instanceof NoNetworkException) {
            return context.getString(R.string.error_no_network);
        }
        return context.getString(R.string.error_message_generic);
    }
}
