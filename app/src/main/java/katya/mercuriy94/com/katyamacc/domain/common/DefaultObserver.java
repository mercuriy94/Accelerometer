package katya.mercuriy94.com.katyamacc.domain.common;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by Nikita on 23.04.2017.
 */

public abstract class DefaultObserver<T> extends DisposableObserver<T> {
    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
