package katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.AccelerometerPresenter;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.assembly.IAccelerometerPresenterSubcomponent;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.presenter.GraphPresneter;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.presenter.assembly.IGraphPresenterComponent;

/**
 * Created by Nikita on 05.05.2017.
 */

@Module(subcomponents = {IAccelerometerPresenterSubcomponent.class, IGraphPresenterComponent.class})
public abstract class PresenterBindingModule {

    @Binds
    @IntoMap
    @PresenterKey(AccelerometerPresenter.class)
    public abstract PresenterComponentBuilder bindAccelerometerPresenterSubcomponent(IAccelerometerPresenterSubcomponent.Builder impl);


    @Binds
    @IntoMap
    @PresenterKey(GraphPresneter.class)
    public abstract PresenterComponentBuilder bindGraphPresenterComponentSubcomponent(IGraphPresenterComponent.Builder impl);


}
