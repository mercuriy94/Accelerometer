package katya.mercuriy94.com.katyamacc.domain.common;

import android.support.annotation.NonNull;

import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import katya.mercuriy94.com.katyamacc.data.repository.IDataStore;

/**
 * Created by Nikita on 18.04.2017.
 */

public abstract class BaseInteractor<Result, Param> {

    @NonNull
    protected IDataStore mDataStore;
    @NonNull
    protected Scheduler mSubscribeScheduler;
    @NonNull
    protected Scheduler mObserverScheduler;
    private CompositeDisposable mCompositeDisposable;

    private Observable<Result> mResultObservable;

    private BaseInteractor() {
    }

    public BaseInteractor(@NonNull IDataStore dataStore,
                          @NonNull Scheduler subscriberScheduler,
                          @NonNull Scheduler observerScheduler) {
        mDataStore = dataStore;
        mSubscribeScheduler = subscriberScheduler;
        mObserverScheduler = observerScheduler;
        mCompositeDisposable = new CompositeDisposable();
    }

    public Disposable subscribe(DisposableObserver<Result> disposableObserver, Param param) {
        Preconditions.checkNotNull(disposableObserver);
        if (!isRun()) mResultObservable = buildObservable(param).compose(applySchedulers());
        Disposable disposable = mResultObservable.subscribeWith(disposableObserver);
        addDisposable(disposable);
        return disposable;
    }


    @Deprecated
    public void execute(DisposableObserver<Result> disposableObserver, Param param) {
        Preconditions.checkNotNull(disposableObserver);
        final Observable<Result> observable = buildObservable(param)
                .compose(applySchedulers());
        addDisposable(observable.subscribeWith(disposableObserver));
    }

    protected abstract Observable<Result> buildObservable(Param param);

    public void dispose() {
        if (!mCompositeDisposable.isDisposed()) mCompositeDisposable.dispose();
    }

    public boolean dispose(Disposable disposable) {
        return isRun() && mCompositeDisposable.remove(disposable);
    }

    private CompositeDisposable getCompositeDisposable() {
        if (isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        return mCompositeDisposable;
    }

    public boolean isRun() {
        return !isDisposed() && mCompositeDisposable.size() > 0;
    }

    private boolean isDisposed() {
        return mCompositeDisposable == null || mCompositeDisposable.isDisposed();
    }


    protected void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        getCompositeDisposable().add(disposable);
    }

    protected <T> ObservableTransformer<T, T> applySchedulers() {
        return upstream -> upstream.subscribeOn(mSubscribeScheduler)
                .observeOn(mObserverScheduler);
    }

}
