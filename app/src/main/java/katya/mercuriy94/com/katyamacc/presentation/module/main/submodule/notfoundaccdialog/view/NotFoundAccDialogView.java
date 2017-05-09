package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.notfoundaccdialog.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import katya.mercuriy94.com.katyamacc.R;
import katya.mercuriy94.com.katyamacc.presentation.module.app.MyApp;
import katya.mercuriy94.com.katyamacc.presentation.module.main.MainModuleContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.notfoundaccdialog.NotFoundAccDialogContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.notfoundaccdialog.presenter.NotFoundAccDialogPresenter;

/**
 * Created by Nikita on 09.05.2017.
 */

public class NotFoundAccDialogView extends NotFoundAccDialogContract.AbstractNotFoundAccDialogView {

    @InjectPresenter
    NotFoundAccDialogContract.AbstractNotFoundAccDialogPresenter mPresenter;

    @ProvidePresenter
    NotFoundAccDialogContract.AbstractNotFoundAccDialogPresenter providePresenter() {
        return new NotFoundAccDialogPresenter(MyApp.get(getActivity()));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        return new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.dialog_message_acc_not_dound_and_close_app))
                .setPositiveButton(getString(R.string.dialog_btn_ok), (dialog, which) -> getPresenter().onClickBtnCloseApp())
                .setNegativeButton(getString(R.string.dialog_btn_no), null)
                .create();
    }


    @Override
    public void setInitialState() {

    }

    @NonNull
    @Override
    public NotFoundAccDialogContract.AbstractNotFoundAccDialogPresenter getPresenter() {
        return mPresenter;
    }

    @NonNull
    @Override
    protected MainModuleContract.AbstractMainRouter getRouter() {
        return ((MainModuleContract.AbstractMainView) getActivity()).getPresenter().getRouter();
    }
}
