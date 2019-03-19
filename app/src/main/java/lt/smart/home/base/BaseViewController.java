package lt.smart.home.base;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import lt.smart.home.utils.KeyboardUtils;
import timber.log.Timber;

public abstract class BaseViewController extends ButterKnifeController implements IBaseView {

    @Override
    protected void onViewBound(@NonNull View view) {
    }

    @Override
    public boolean showError(Throwable throwable, String localizedMessage) {
        Timber.w(throwable);
        KeyboardUtils.hideKeyboard(getActivity());

        Toast.makeText(getActivity(), localizedMessage, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        setRetainViewMode(RetainViewMode.RETAIN_DETACH);
    }
}
