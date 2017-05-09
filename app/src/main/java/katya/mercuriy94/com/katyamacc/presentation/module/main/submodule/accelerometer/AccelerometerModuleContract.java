package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.HasPresenterSubcomponentBuilders;
import katya.mercuriy94.com.katyamacc.presentation.common.presenter.BasePresenter;
import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseFragment;
import katya.mercuriy94.com.katyamacc.presentation.common.view.IBaseView;
import katya.mercuriy94.com.katyamacc.presentation.module.main.MainModuleContract;

/**
 * Created by Nikita on 08.05.2017.
 */

public abstract class AccelerometerModuleContract {

    private AccelerometerModuleContract() {
        throw new RuntimeException("no instance please");
    }


    public interface IAccelerometerView extends IBaseView {

        @StateStrategyType(value = SkipStrategy.class)
        void showResults(List<AccelerometerSensor> accelerometerSensors, boolean scrollToLastPosition);

        @StateStrategyType(value = SkipStrategy.class)
        void addNewResult(AccelerometerSensor accelerometerSensor);

        @StateStrategyType(value = SkipStrategy.class)
        void start(boolean animEnable);

        @StateStrategyType(value = SkipStrategy.class)
        void pause(boolean animEnable);

        @StateStrategyType(value = SkipStrategy.class)
        void clear(boolean animEnable);

    }

    public abstract static class AbstractAccelerometerView extends BaseFragment<AbstractAccelerometerPresenter,
            MainModuleContract.AbstractMainRouter> implements IAccelerometerView {
    }

    public abstract static class AbstractAccelerometerPresenter extends BasePresenter<IAccelerometerView, MainModuleContract.AbstractMainRouter> {

        public AbstractAccelerometerPresenter(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {
            super(presenterSubcomponentBuilders);
        }

        public abstract void notFoundAccelerometerSensor();

        public abstract void onClickBtnPlayPause();

        public abstract void onClickBtnRemoveResults();

        public abstract void onClickBtnSave();

        public void setRxSensorPublisher(PublishSubject<AccelerometerSensor> publisher) {
            publisher.subscribe(this::onChangeAccelerometerSensor);
        }


        protected abstract void onChangeAccelerometerSensor(AccelerometerSensor accelerometerSensor);

    }
}
