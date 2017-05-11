package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs;

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
 * Created by Nikita on 09.05.2017.
 */

public class GraphModuleContract {

    public GraphModuleContract() {
        throw new RuntimeException("no instance please!");
    }

    public interface IGraphView extends IBaseView {

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

    public static abstract class AbstractGraphView extends BaseFragment<AbstractGraphPresenter, MainModuleContract.AbstractMainRouter>
            implements IGraphView {


    }

    public static abstract class AbstractGraphPresenter extends BasePresenter<IGraphView, MainModuleContract.AbstractMainRouter> {

        public AbstractGraphPresenter(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {
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
