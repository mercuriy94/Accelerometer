package katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings;

import dagger.MapKey;
import katya.mercuriy94.com.katyamacc.presentation.common.presenter.BasePresenter;

/**
 * Created by Nikita on 05.05.2017.
 */

@MapKey
public @interface PresenterKey {

    Class<? extends BasePresenter> value();

}
