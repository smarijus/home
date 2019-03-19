package lt.smart.home.networking;

import java.util.concurrent.Executors;

import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface SchedulerProvider {
    Scheduler appScheduler = Schedulers.from(Executors.newSingleThreadExecutor());

    SchedulerProvider DEFAULT = new SchedulerProvider() {
        @Override
        public <T>ObservableTransformer<T, T> applySchedulers() {
            return observable -> observable.subscribeOn(appScheduler)
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public CompletableTransformer applySchedulersForCompletable() {
            return observable -> observable.subscribeOn(appScheduler)
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    SchedulerProvider MOCK = new SchedulerProvider() {
        @Override
        public <T>ObservableTransformer<T, T> applySchedulers() {
            return observable -> observable;
        }

        @Override
        public CompletableTransformer applySchedulersForCompletable() {
            return observable -> observable;
        }
    };

    <T> ObservableTransformer<T, T> applySchedulers();

    CompletableTransformer applySchedulersForCompletable();
}
