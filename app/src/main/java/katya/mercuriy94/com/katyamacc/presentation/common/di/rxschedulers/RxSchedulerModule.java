package katya.mercuriy94.com.katyamacc.presentation.common.di.rxschedulers;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 19.04.2017.
 */

@Module
public class RxSchedulerModule {

    public static final String COMPUTATION = "computation";
    public static final String MAIN = "main";


    @Provides
    @Singleton
    @Named(COMPUTATION)
    public Scheduler provideIOScheduler() {
        return Schedulers.computation();
    }

    @Provides
    @Singleton
    @Named(MAIN)
    public Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
