package lt.smart.home.constans.enums;

import android.content.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lt.smart.home.R;
import lt.smart.home.common.DataHolder;

public enum PayFeeType {
    FREE, REGULAR;

    public static PayFeeType get(BigDecimal fromAmount, int transactionCount) {
        if (transactionCount < DataHolder.FREE_PAY_FEE_COUNT) {
            return FREE;
        }
        return REGULAR;
    }

    public BigDecimal getPayFeeAmount(BigDecimal fromAmount) {
        return getPayFeeCoeficient().multiply(fromAmount).setScale(2, RoundingMode.CEILING);
    }

    private BigDecimal getPayFeeCoeficient() {
        return this == FREE ? new BigDecimal(0) : new BigDecimal(0.007);
    }

    public String getDescriptionId(Context context) {
        if (this == FREE) {
            return context.getString(R.string.pay_fee_free).toLowerCase();
        } else {
            return context.getString(R.string.pay_fee_regular,
                    getPayFeeCoeficient()
                            .multiply(new BigDecimal(100))
                            .setScale(2, RoundingMode.HALF_UP)
                            .toPlainString());
        }

    }
}
