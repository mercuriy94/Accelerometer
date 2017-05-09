package katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings;

import dagger.MembersInjector;
import katya.mercuriy94.com.katyamacc.presentation.common.presenter.BasePresenter;

/**
 * Created by Nikita on 05.05.2017.
 */

public interface PresenterComponent<Presenter extends BasePresenter> extends MembersInjector<Presenter>{
}
