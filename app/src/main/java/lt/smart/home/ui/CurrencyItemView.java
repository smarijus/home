package lt.smart.home.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import lt.smart.home.R;
import lt.smart.home.constans.enums.CurrencyType;

public class CurrencyItemView extends LinearLayout {

    @BindView(R.id.currency)
    TextView currencyView;

    @BindView(R.id.amount)
    TextView amountView;

    public CurrencyItemView(Context context) {
        this(context, null);
    }

    public CurrencyItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CurrencyItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.widget_currency_item, this);
        ButterKnife.bind(this, this);
    }

    public void set(CurrencyType currencyType, BigDecimal amount) {
        currencyView.setText(currencyType.name());
        amountView.setText(amount.setScale(2, RoundingMode.CEILING).toPlainString());
    }
}
