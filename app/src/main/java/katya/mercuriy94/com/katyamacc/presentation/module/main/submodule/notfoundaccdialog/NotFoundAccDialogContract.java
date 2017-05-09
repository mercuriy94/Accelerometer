package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.notfoundaccdialog;

import android.support.annotation.NonNull;

import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.HasPresenterSubcomponentBuilders;
import katya.mercuriy94.com.katyamacc.presentation.common.presenter.BasePresenter;
import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseDialogFragment;
import katya.mercuriy94.com.katyamacc.presentation.common.view.IBaseView;
import katya.mercuriy94.com.katyamacc.presentation.module.main.MainModuleContract;

/**
 * Created by Nikita on 09.05.2017.
 */

public abstract class NotFoundAccDialogContract {

    private NotFoundAccDialogContract() {
        throw new RuntimeException("no instance please!");
    }

    public interface INotFoundAccDialogView extends IBaseView {
    }

    public static abstract class AbstractNotFoundAccDialogView extends BaseDialogFragment<AbstractNotFoundAccDialogPresenter, MainModuleContract.AbstractMainRouter>
            implements INotFoundAccDialogView {

    }

    public static abstract class AbstractNotFoundAccDialogPresenter extends BasePresenter<INotFoundAccDialogView, MainModuleContract.AbstractMainRouter> {
        public AbstractNotFoundAccDialogPresenter(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {
            super(presenterSubcomponentBuilders);
        }

        public abstract void onClickBtnCloseApp();


    }

}
