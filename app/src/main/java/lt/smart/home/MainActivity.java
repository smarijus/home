package lt.smart.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bluelinelabs.conductor.RouterTransaction;

import lt.smart.home.base.BaseRouterActivity;
import lt.smart.home.home.HomeController;

public class MainActivity extends BaseRouterActivity {

    @Override
    public void onRouterInit(@Nullable Bundle savedInstanceState) {
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new HomeController()));
        }
    }
}
