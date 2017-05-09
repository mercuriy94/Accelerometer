package katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nikita on 05.05.2017.
 */
@Module
public abstract class PresenterModule<Presenter> {

    protected final Presenter mPresenter;


    protected PresenterModule(Presenter presenter) {
        mPresenter = presenter;
    }

    @Provides
    @PresenterScope
    public Presenter providePresenter() {
        return mPresenter;
    }
}
