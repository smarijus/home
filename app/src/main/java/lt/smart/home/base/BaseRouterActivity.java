package lt.smart.home.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bluelinelabs.conductor.ChangeHandlerFrameLayout;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;

import butterknife.BindView;
import butterknife.ButterKnife;
import lt.smart.home.R;

public abstract class BaseRouterActivity  extends AppCompatActivity {

    @BindView(R.id.controller_container)
    public ChangeHandlerFrameLayout container;

    protected Router router;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);
        ButterKnife.bind(this);
        router = Conductor.attachRouter(this, container, savedInstanceState);
        onRouterInit(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    public abstract void onRouterInit(@Nullable Bundle savedInstanceState);
}
