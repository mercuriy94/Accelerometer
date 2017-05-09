package katya.mercuriy94.com.katyamacc.presentation.common.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;

import java.lang.annotation.Annotation;

import butterknife.ButterKnife;
import katya.mercuriy94.com.katyamacc.presentation.common.annotations.Layout;
import katya.mercuriy94.com.katyamacc.presentation.common.presenter.BasePresenter;
import katya.mercuriy94.com.katyamacc.presentation.common.router.BaseActivityRouter;


/**
 * Created by Nikita on 07.04.2017.
 */

public abstract class BaseFragment<Presenter extends BasePresenter, Router extends BaseActivityRouter>
        extends MvpAppCompatFragment
        implements IBaseView,
        OnBackPressedListener {

    private IBaseFragmentListener mBaseFragmentListener;
    protected ProgressDialog mProgressDialog;


    //region Lifecycle

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mBaseFragmentListener = (IBaseFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IBaseFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Class cls = getClass();
        if (!cls.isAnnotationPresent(Layout.class)) return null;
        Annotation annotation = cls.getAnnotation(Layout.class);
        Layout layout = (Layout) annotation;
        return inflater.inflate(layout.value(), null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getPresenter().setRouter(getRouter());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePending();
    }

    //endregion Lifecycle

    //region IBaseView

    @Override
    public void showLongToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showShortToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongToast(@StringRes int stringRes) {
        Toast.makeText(getActivity(), stringRes, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showShortToast(@StringRes int stringRes) {
        Toast.makeText(getActivity(), stringRes, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setTitleText(@StringRes int titleText) {
        mBaseFragmentListener.changeTitle(titleText);
    }

    @Override
    public void setTitleText(String title) {
        mBaseFragmentListener.changeTitle(title);

    }

    @Override
    public void setTitleImage(String url) {

    }

    @Override
    public void setVisibilityBackButton(boolean value) {
        mBaseFragmentListener.changeVisibilityBackButton(value);
    }

    @Override
    public void showPending(String message) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showPending(@StringRes int id) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(id));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hidePending() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
    }

    //endregion IBaseView

    //region IBaseFragmentListener

    @Override
    public boolean onBackPressed() {
        return getPresenter().onBackPressed();
    }

    //endregion IBaseFragmentListener

    //region Methods

    public IBaseFragmentListener getBaseFragmentListener() {
        return mBaseFragmentListener;
    }

    public void onBackStackChanged(BaseFragment baseFragment) {
        if (baseFragment == this) getPresenter().updateTitle();
    }

    //endregion Methods

    //region Abstract Methods

    @NonNull
    public abstract Presenter getPresenter();

    @NonNull
    protected abstract Router getRouter();

    //endregion Abstract Methods
}
