package lt.smart.home.home;

import android.view.View;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;
import lt.smart.home.R;
import lt.smart.home.constans.enums.CurrencyType;
import lt.smart.home.ui.CurrencyItemView;

public class CurrentBalanceItem extends AbstractFlexibleItem <CurrentBalanceItem.ViewHolder>{

    private final Map.Entry<CurrencyType, BigDecimal> item;

    public CurrentBalanceItem(Map.Entry<CurrencyType, BigDecimal> item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_current_balance;
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads) {
        holder.currencyItemView.set(item.getKey(), item.getValue());
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new ViewHolder(view, adapter);
    }

    static class ViewHolder extends FlexibleViewHolder {

        @BindView(R.id.item)
        CurrencyItemView currencyItemView;

        public ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }
    }
}
