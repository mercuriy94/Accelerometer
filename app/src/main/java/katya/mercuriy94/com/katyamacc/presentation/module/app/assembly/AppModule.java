package katya.mercuriy94.com.katyamacc.presentation.module.app.assembly;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import katya.mercuriy94.com.katyamacc.data.repository.IDataStore;
import katya.mercuriy94.com.katyamacc.domain.acc.CalculateAccResultInteractor;
import katya.mercuriy94.com.katyamacc.presentation.common.di.rxschedulers.RxSchedulerModule;

/**
 * Created by Nikita on 08.05.2017.
 */
@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @NonNull
    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }


    @NonNull
    @Provides
    @Singleton
    CalculateAccResultInteractor provideCalculateAccResult(IDataStore dataStore,
                                                           @Named(RxSchedulerModule.COMPUTATION) Scheduler schedulerJob,
                                                           @Named(RxSchedulerModule.MAIN) Scheduler schedulerObserve) {
        return new CalculateAccResultInteractor(dataStore, schedulerJob, schedulerObserve);
    }

}
