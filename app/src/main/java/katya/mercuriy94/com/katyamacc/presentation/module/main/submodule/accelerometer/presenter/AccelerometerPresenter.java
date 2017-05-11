package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter;

import android.Manifest;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import katya.mercuriy94.com.katyamacc.R;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;
import katya.mercuriy94.com.katyamacc.domain.acc.CalculateAccResultInteractor;
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

    private RxPermissions mRxPermissions;

    @Inject
    SaveAccResultsInteractor mSaveAccResultsInteractor;

    @Inject
    CalculateAccResultInteractor mCalculateAccResultInteractor;

    Disposable mDisposableCalcAccResult;


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
        mCalculateAccResultInteractor.dispose();
        getViewState().pause(true);
        getRouter().showNotFoundAccDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSaveAccResultsInteractor.dispose();
        mCalculateAccResultInteractor.dispose();
    }

    @Override
    public void onClickBtnPlayPause() {
        if (!mCalculateAccResultInteractor.isRun()) {
            mCalculateAccResultInteractor.dispose();
            mCalculateAccResultInteractor.clearReadyMadeResults();
            getViewState().clear(false);
            mDisposableCalcAccResult = mCalculateAccResultInteractor.subscribe(new CalculateAccResultDisposable(), null);
        } else {
            getViewState().pause(true);
            mCalculateAccResultInteractor.dispose();
        }
    }

    @Override
    public void onClickBtnGraph() {
        getRouter().navigateToGraphScreen();
    }

    @Override
    public void onClickBtnSave() {
        if (mCalculateAccResultInteractor.getReadyMadeResults().isEmpty()) {
            getViewState().showShortToast(R.string.dlg_msg_save_results_empty_data);
        } else {
            mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            mSaveAccResultsInteractor.subscribe(new SaveAccResultsDisposable(), mCalculateAccResultInteractor.getReadyMadeResults());
                        }
                    });
        }
    }

    @Override
    public void onClickBtnRemoveResults() {
        mCalculateAccResultInteractor.dispose();
        mCalculateAccResultInteractor.clearReadyMadeResults();
        getViewState().pause(true);
        getViewState().clear(true);
    }

    @Override
    public void attachView(AccelerometerModuleContract.IAccelerometerView view) {
        super.attachView(view);
        if (mCalculateAccResultInteractor.isRun()) {
            if (mDisposableCalcAccResult == null || mDisposableCalcAccResult.isDisposed()) {
                mDisposableCalcAccResult = mCalculateAccResultInteractor.subscribe(new CalculateAccResultDisposable(), null);
            }
            view.showResults(mCalculateAccResultInteractor.getReadyMadeResults(), true);
            view.start(false);
        } else {
            view.showResults(mCalculateAccResultInteractor.getReadyMadeResults(), false);
        }
    }

    @Override
    public void detachView(AccelerometerModuleContract.IAccelerometerView view) {
        super.detachView(view);
        view.clear(false);
    }


    @Override
    protected void onChangeAccelerometerSensor(AccelerometerSensor accelerometerSensor) {
        mCalculateAccResultInteractor.addResult(accelerometerSensor);
    }

    @Override
    protected TitleModel getTitle() {
        return new TitleModel.Builder()
                .setTitleMessageRes(R.string.app_name)
                .build();
    }


    private class CalculateAccResultDisposable extends DefaultObserver<AccelerometerSensor> {

        @Override
        protected void onStart() {

            getViewState().start(true);
        }

        @Override
        public void onNext(AccelerometerSensor accelerometerSensor) {
            getViewState().addNewResult(accelerometerSensor);
        }
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
