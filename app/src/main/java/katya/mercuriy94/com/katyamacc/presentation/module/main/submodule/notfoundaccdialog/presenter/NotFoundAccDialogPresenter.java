package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.notfoundaccdialog.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.HasPresenterSubcomponentBuilders;
import katya.mercuriy94.com.katyamacc.presentation.model.TitleModel;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.notfoundaccdialog.NotFoundAccDialogContract;

/**
 * Created by Nikita on 09.05.2017.
 */
@InjectViewState
public class NotFoundAccDialogPresenter extends NotFoundAccDialogContract.AbstractNotFoundAccDialogPresenter {

    public NotFoundAccDialogPresenter(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {
        super(presenterSubcomponentBuilders);
    }

    @Override
    public void onClickBtnCloseApp() {
        getRouter().close();
    }


    @Override
    protected TitleModel getTitle() {
        return null;
    }
}
