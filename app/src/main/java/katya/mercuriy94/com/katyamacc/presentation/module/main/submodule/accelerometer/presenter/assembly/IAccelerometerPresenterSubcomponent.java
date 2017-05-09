package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.assembly;

import dagger.Subcomponent;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.PresenterComponent;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.PresenterComponentBuilder;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.AccelerometerPresenter;

/**
 * Created by Nikita on 08.05.2017.
 */
@Subcomponent(modules = AccelerometerPresenterModule.class)
public interface IAccelerometerPresenterSubcomponent extends PresenterComponent<AccelerometerPresenter> {

    @Subcomponent.Builder
    interface Builder extends PresenterComponentBuilder<AccelerometerPresenterModule, IAccelerometerPresenterSubcomponent> {

    }

}
