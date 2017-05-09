package katya.mercuriy94.com.katyamacc.presentation.module.app.assembly;

import javax.inject.Singleton;

import dagger.Component;
import katya.mercuriy94.com.katyamacc.presentation.common.di.datastore.DataStoreModule;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.PresenterBindingModule;
import katya.mercuriy94.com.katyamacc.presentation.common.di.rxschedulers.RxSchedulerModule;
import katya.mercuriy94.com.katyamacc.presentation.module.app.MyApp;

/**
 * Created by Nikita on 08.05.2017.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        DataStoreModule.class,
        PresenterBindingModule.class,
        RxSchedulerModule.class})
public interface IAppComponent {

    MyApp inject(MyApp application);
}
