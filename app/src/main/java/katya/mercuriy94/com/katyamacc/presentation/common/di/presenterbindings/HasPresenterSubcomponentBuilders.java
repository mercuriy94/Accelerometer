package katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings;


import katya.mercuriy94.com.katyamacc.presentation.common.presenter.BasePresenter;

/**
 * Created by Nikita on 05.05.2017.
 */

public interface HasPresenterSubcomponentBuilders {

    PresenterComponentBuilder getPresenterComponentBuilder(Class<? extends BasePresenter> presenterClass);

}
