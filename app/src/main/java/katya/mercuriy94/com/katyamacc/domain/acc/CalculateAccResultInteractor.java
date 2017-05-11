package katya.mercuriy94.com.katyamacc.domain.acc;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;
import katya.mercuriy94.com.katyamacc.data.repository.IDataStore;
import katya.mercuriy94.com.katyamacc.domain.common.BaseInteractor;
import katya.mercuriy94.com.katyamacc.presentation.common.di.rxschedulers.RxSchedulerModule;

/**
 * Created by Nikita on 10.05.2017.
 */

public class CalculateAccResultInteractor extends BaseInteractor<AccelerometerSensor, Void> {

    private CopyOnWriteArrayList<AccelerometerSensor> mTempAccResults = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<AccelerometerSensor> mAccResults = new CopyOnWriteArrayList<>();


    @Inject
    public CalculateAccResultInteractor(@NonNull IDataStore dataStore,
                                        @Named(RxSchedulerModule.COMPUTATION) @NonNull Scheduler subscriberScheduler,
                                        @Named(RxSchedulerModule.MAIN) @NonNull Scheduler observerScheduler) {
        super(dataStore, subscriberScheduler, observerScheduler);
    }

    @Override
    protected Observable<AccelerometerSensor> buildObservable(Void aVoid) {
        return Observable.interval(150000, TimeUnit.MICROSECONDS)
                .map(this::calculateAccResult)
                .doOnNext(mAccResults::add)
                .share();
    }

    public void addResult(AccelerometerSensor accelerometerSensor) {
        mTempAccResults.add(accelerometerSensor);
    }

    public ArrayList<AccelerometerSensor> getReadyMadeResults() {
        return new ArrayList<>(mAccResults);
    }


    public void clearReadyMadeResults() {
        mAccResults.clear();
    }

    @Override
    public void dispose() {
        super.dispose();
        mTempAccResults = new CopyOnWriteArrayList<>();
    }

    private synchronized AccelerometerSensor calculateAccResult(long number) {

        List<AccelerometerSensor> list = new ArrayList<>(mTempAccResults);
        float x = 0f, y = 0f, z = 0f;
        mTempAccResults.clear();

        for (AccelerometerSensor accelerometerSensor : list) {
            x += accelerometerSensor.getX();
            y += accelerometerSensor.getY();
            z += accelerometerSensor.getZ();
        }

        x = x / (float) list.size();
        y = y / (float) list.size();
        z = z / (float) list.size();

        return new AccelerometerSensor(x, y, z, (number) * 150);
    }


}
