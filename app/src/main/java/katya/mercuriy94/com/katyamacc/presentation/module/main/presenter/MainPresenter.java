package katya.mercuriy94.com.katyamacc.presentation.module.main.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import katya.mercuriy94.com.katyamacc.R;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.HasPresenterSubcomponentBuilders;
import katya.mercuriy94.com.katyamacc.presentation.model.TitleModel;
import katya.mercuriy94.com.katyamacc.presentation.module.main.MainModuleContract;

/**
 * Created by Nikita on 08.05.2017.
 */

@InjectViewState
public class MainPresenter extends MainModuleContract.AbstractMainPresenter {

    public MainPresenter(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {
        super(presenterSubcomponentBuilders);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getRouter().navigateToAccelerometerScreen();
    }

    @Override
    protected TitleModel getTitle() {
        return new TitleModel.Builder()
                .setTitleMessageRes(R.string.app_name)
                .build();
    }
}
