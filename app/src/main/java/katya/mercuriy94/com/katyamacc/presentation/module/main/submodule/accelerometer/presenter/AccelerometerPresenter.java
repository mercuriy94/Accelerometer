package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter;

import android.Manifest;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import katya.mercuriy94.com.katyamacc.R;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;
import katya.mercuriy94.com.katyamacc.domain.acc.SaveAccResultsInteractor;
import katya.mercuriy94.com.katyamacc.domain.common.DefaultObserver;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.HasPresenterSubcomponentBuilders;
import katya.mercuriy94.com.katyamacc.presentation.model.TitleModel;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.AccelerometerModuleContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.assembly.AccelerometerPresenterModule;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.assembly.IAccelerometerPresenterSubcomponent;

/**
 * Created by Nikita on 08.05.2017.
 */
@InjectViewState
public class AccelerometerPresenter extends AccelerometerModuleContract.AbstractAccelerometerPresenter {

    public static final String TAG = "AccelerometerPresenter";

    private boolean mIsRunningListenerAccelerometer = false;
    private List<AccelerometerSensor> mAccelerometerSensors;

    private RxPermissions mRxPermissions;

    @Inject
    SaveAccResultsInteractor mSaveAccResultsInteractor;

    public AccelerometerPresenter(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders,
                                  @NonNull RxPermissions rxPermissions) {
        super(presenterSubcomponentBuilders);
        mRxPermissions = rxPermissions;
    }

    @Override
    public void inject(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {
        super.inject(presenterSubcomponentBuilders);
        ((IAccelerometerPresenterSubcomponent.Builder) presenterSubcomponentBuilders.getPresenterComponentBuilder(AccelerometerPresenter.class))
                .presenterModule(new AccelerometerPresenterModule(this))
                .build()
                .injectMembers(this);
    }

    @Override
    public void notFoundAccelerometerSensor() {
        mIsRunningListenerAccelerometer = false;
        getViewState().pause(true);
        getRouter().showNotFoundAccDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSaveAccResultsInteractor.dispose();
    }


    @Override
    public void onClickBtnPlayPause() {
        mIsRunningListenerAccelerometer = !mIsRunningListenerAccelerometer;
        if (mIsRunningListenerAccelerometer) getViewState().start(true);
        else getViewState().pause(true);
    }

    @Override
    public void onClickBtnSave() {
        if (mAccelerometerSensors.isEmpty()) {
            getViewState().showShortToast(R.string.dlg_msg_save_results_empty_data);
        } else {
            getViewState().pause(true);
            mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            mSaveAccResultsInteractor.execute(new SaveAccResultsDisposable(), mAccelerometerSensors);
                        }
                    });
        }
    }

    @Override
    public void onClickBtnRemoveResults() {
        mAccelerometerSensors.clear();
        getViewState().clear(true);
    }

    @Override
    public void attachView(AccelerometerModuleContract.IAccelerometerView view) {
        super.attachView(view);

        if (mIsRunningListenerAccelerometer) {
            view.showResults(getAccelerometerSensors(), true);
            view.start(false);
        } else {
            view.showResults(getAccelerometerSensors(), false);
        }
    }

    @Override
    public void detachView(AccelerometerModuleContract.IAccelerometerView view) {
        super.detachView(view);
        view.clear(false);
        // GraphModuleContract.pause(false);
    }


    @Override
    protected void onChangeAccelerometerSensor(AccelerometerSensor accelerometerSensor) {
        Log.d(TAG, String.format(Locale.getDefault(), "onChangeAccelerometerSensor: x = %f, y = %f, z = %f",
                accelerometerSensor.getX(), accelerometerSensor.getY(), accelerometerSensor.getZ()));
        getAccelerometerSensors().add(accelerometerSensor);
        getViewState().addNewResult(accelerometerSensor);
    }

    public List<AccelerometerSensor> getAccelerometerSensors() {
        if (mAccelerometerSensors == null) mAccelerometerSensors = new ArrayList<>();
        return mAccelerometerSensors;
    }

    @Override
    protected TitleModel getTitle() {
        return new TitleModel.Builder()
                .setTitleMessageRes(R.string.app_name)
                .build();
    }

    private class SaveAccResultsDisposable extends DefaultObserver<Boolean> {

        @Override
        protected void onStart() {
            super.onStart();
            getViewState().showPending(R.string.dlg_msg_save_results);
        }

        @Override
        public void onNext(Boolean aBoolean) {
            super.onNext(aBoolean);
            getViewState().hidePending();
            if (aBoolean) getViewState().showShortToast(R.string.dlg_msg_save_results_success);
            else getViewState().showShortToast(R.string.dlg_msg_save_results_error);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            getViewState().hidePending();
            getViewState().showShortToast(R.string.dlg_msg_save_results_error);
        }
    }


}
