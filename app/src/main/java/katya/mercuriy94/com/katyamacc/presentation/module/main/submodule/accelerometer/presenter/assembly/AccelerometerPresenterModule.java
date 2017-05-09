package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.assembly;

import dagger.Module;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.PresenterModule;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.AccelerometerPresenter;

/**
 * Created by Nikita on 08.05.2017.
 */

@Module
public class AccelerometerPresenterModule extends PresenterModule<AccelerometerPresenter> {


    public AccelerometerPresenterModule(AccelerometerPresenter accelerometerPresenter) {
        super(accelerometerPresenter);
    }
}
