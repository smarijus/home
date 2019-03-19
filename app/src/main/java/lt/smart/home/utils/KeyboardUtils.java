package lt.smart.home.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    public static void hideKeyboard(Activity activity) {
        if (activity == null) return;
        Window window = activity.getWindow();
        if (window == null) return;
        View decorView = window.getDecorView();
        if (decorView == null) return;
        IBinder binder = decorView.getWindowToken();
        if (binder == null) return;
        getInputMethodManager(activity).hideSoftInputFromWindow(binder, 0);
    }

    private static InputMethodManager getInputMethodManager(Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }
}
