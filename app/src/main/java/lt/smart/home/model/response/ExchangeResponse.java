package lt.smart.home.model.response;

import java.math.BigDecimal;

import lt.smart.home.constans.enums.CurrencyType;

public class ExchangeResponse {

    BigDecimal amount;

    CurrencyType currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }
}
