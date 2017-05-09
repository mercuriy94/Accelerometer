package katya.mercuriy94.com.katyamacc.presentation.common.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;

import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.HasPresenterSubcomponentBuilders;
import katya.mercuriy94.com.katyamacc.presentation.common.router.ActivityRouterAdapter;
import katya.mercuriy94.com.katyamacc.presentation.common.router.BaseActivityRouter;
import katya.mercuriy94.com.katyamacc.presentation.common.view.IBaseView;
import katya.mercuriy94.com.katyamacc.presentation.model.TitleModel;
import katya.mercuriy94.com.katyamacc.presentation.utils.StringUtils;


/**
 * Created by nikita on 21.01.2017.
 */


public abstract class BasePresenter<View extends IBaseView, Router extends BaseActivityRouter>
        extends MvpPresenter<View> {

    public static final String TAG = "BasePresenter";

    private Router mRouter;


    public BasePresenter(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {
        inject(presenterSubcomponentBuilders);
    }

    public void inject(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {

    }

    public void setRouter(@NonNull Router router) {
        mRouter = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        updateTitle();
    }

    @Override
    public void attachView(View view) {
        super.attachView(view);
        view.setInitialState();

    }

    @SuppressWarnings("unchecked")
    public void setRouteConductor(@NonNull ActivityRouterAdapter routeConductor) {
        if (getRouter() != null) getRouter().setRouterAdapter(routeConductor);
    }


    public Router getRouter() {
        return mRouter;
    }

    public boolean onBackPressed() {
        getRouter().onBackPressed();
        return true;
    }

    protected abstract TitleModel getTitle();

    protected void onBindTitle(TitleModel title) {
        if (title == null || getViewState() == null) return;
        getViewState().setTitleText(title.getTitleMessageRes());
        getViewState().setVisibilityBackButton(title.isVisibleBackButton());
        if (StringUtils.isNullOrEmpty(title.getImageWeb())) getViewState().setTitleImage(null);
    }

    public void updateTitle() {
        onBindTitle(getTitle());
    }

}
