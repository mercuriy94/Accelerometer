package katya.mercuriy94.com.katyamacc.presentation.common.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

import java.lang.annotation.Annotation;

import butterknife.ButterKnife;
import katya.mercuriy94.com.katyamacc.presentation.common.annotations.Layout;
import katya.mercuriy94.com.katyamacc.presentation.common.presenter.BasePresenter;
import katya.mercuriy94.com.katyamacc.presentation.common.router.ActivityRouterAdapter;
import katya.mercuriy94.com.katyamacc.presentation.common.router.BaseActivityRouter;


/**
 * Created by Nikita on 06.04.2017.
 */

public abstract class BaseActivity<Presenter extends BasePresenter, Router extends BaseActivityRouter>
        extends MvpAppCompatActivity
        implements IBaseView,
        IBaseFragmentListener {

    public static final String TAG = "BaseActivity";

    private IOnActivityLifecycleListener mLifecycleListener;

    protected ProgressDialog mProgressDialog;

    //region Lifecycle

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class cls = getClass();
        if (!cls.isAnnotationPresent(Layout.class)) return;
        Annotation annotation = cls.getAnnotation(Layout.class);
        Layout layout = (Layout) annotation;
        setContentView(layout.value());
        ButterKnife.bind(this);
        resolveRouter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLifecycleListener != null) mLifecycleListener.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLifecycleListener != null) mLifecycleListener.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hidePending();
    }

    //endregion Lifecycle

    @NonNull
    public abstract Presenter getPresenter();

    @SuppressWarnings("unchecked")
    public void resolveRouter() {
        if (getPresenter().getRouter() != null) {
            getPresenter().getRouter().setRouterAdapter(createRouterAdapter());
        } else {
            getPresenter().setRouter(createRouter(createRouterAdapter()));
        }
    }

    @NonNull
    public abstract Router createRouter(ActivityRouterAdapter activityRouteConductor);

    @NonNull
    protected abstract ActivityRouterAdapter createRouterAdapter();

    @Override
    public void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongToast(@StringRes int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showShortToast(@StringRes int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
    }

    public IOnActivityLifecycleListener getLifecycleListener() {
        return mLifecycleListener;
    }

    public void setLifecycleListener(IOnActivityLifecycleListener lifecycleListener) {
        mLifecycleListener = lifecycleListener;
    }

    @Override
    public void onBackPressed() {
        if (!getPresenter().onBackPressed()) super.onBackPressed();
    }

    @Override
    public void showPending(String message) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showPending(@StringRes int id) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(id));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hidePending() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
    }

    //region IBaseFragmentListener


    @Override
    public void changeTitle(String title) {
        setTitleText(title);
    }

    @Override
    public void changeTitleImage(@Nullable String url) {
        setTitleImage(url);
    }

    @Override
    public void changeTitle(@StringRes int title) {
        setTitleText(title);
    }


    @Override
    public void changeVisibilityBackButton(boolean value) {
        setVisibilityBackButton(value);
    }

    //endregion IBaseFragmentListener

}
