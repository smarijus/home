package lt.smart.home.base;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter <T> implements IPresenter<T> {

    protected final CompositeDisposable subscriptions = new CompositeDisposable();

    private T view;

    protected final ErrorHandler errorHandler;

    public BasePresenter(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public boolean hasView() {
        return view != null;
    }

    public T getView() {
        return view;
    }

    @Override
    public void setView(T view) {
        this.view = view;
        if (view == null) {
            subscriptions.clear();
        }
    }
}