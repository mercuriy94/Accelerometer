package katya.mercuriy94.com.katyamacc.domain.acc;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;
import katya.mercuriy94.com.katyamacc.data.repository.IDataStore;
import katya.mercuriy94.com.katyamacc.domain.common.BaseInteractor;
import katya.mercuriy94.com.katyamacc.presentation.common.di.rxschedulers.RxSchedulerModule;

/**
 * Created by Nikita on 08.05.2017.
 */

public class SaveAccResultsInteractor extends BaseInteractor<Boolean, List<AccelerometerSensor>> {

    @Inject
    public SaveAccResultsInteractor(
            @NonNull IDataStore dataStore,
            @Named(RxSchedulerModule.COMPUTATION) @NonNull Scheduler subscriberScheduler,
            @Named(RxSchedulerModule.MAIN) @NonNull Scheduler observerScheduler) {
        super(dataStore, subscriberScheduler, observerScheduler);
    }

    @Override
    protected Observable<Boolean> buildObservable(List<AccelerometerSensor> accelerometerSensors) {
        return mDataStore.saveAccResults(accelerometerSensors);
    }
}
