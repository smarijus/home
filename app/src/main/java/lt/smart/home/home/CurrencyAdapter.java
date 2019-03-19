package lt.smart.home.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import lt.smart.home.R;
import lt.smart.home.constans.enums.CurrencyType;

public class CurrencyAdapter extends ArrayAdapter<CurrencyType> {

    public CurrencyAdapter(Context context, List<CurrencyType> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_simple_spinner, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textView.setText(getItem(position).name());

        return convertView;
    }

    public void setData(List<CurrencyType> newData) {
        clear();
        addAll(newData);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView textView;

        ViewHolder(View view) {
            textView = (TextView) view;
        }
    }
}