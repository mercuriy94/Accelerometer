package katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.AccelerometerPresenter;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.assembly.IAccelerometerPresenterSubcomponent;

/**
 * Created by Nikita on 05.05.2017.
 */

@Module(subcomponents = {IAccelerometerPresenterSubcomponent.class})
public abstract class PresenterBindingModule {

    @Binds
    @IntoMap
    @PresenterKey(AccelerometerPresenter.class)
    public abstract PresenterComponentBuilder bindAccelerometerPresenterSubcomponent(IAccelerometerPresenterSubcomponent.Builder impl);


}
