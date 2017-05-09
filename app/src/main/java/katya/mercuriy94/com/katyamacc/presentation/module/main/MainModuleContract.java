package katya.mercuriy94.com.katyamacc.presentation.module.main;

import android.support.annotation.NonNull;

import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.HasPresenterSubcomponentBuilders;
import katya.mercuriy94.com.katyamacc.presentation.common.presenter.BasePresenter;
import katya.mercuriy94.com.katyamacc.presentation.common.router.ActivityRouterAdapter;
import katya.mercuriy94.com.katyamacc.presentation.common.router.BaseActivityRouter;
import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseActivity;
import katya.mercuriy94.com.katyamacc.presentation.common.view.IBaseView;

/**
 * Created by Nikita on 08.05.2017.
 */

public class MainModuleContract {

    private MainModuleContract() {
        throw new RuntimeException("no instance please!");
    }

    public interface IMainView extends IBaseView {

    }


    public static abstract class AbstractMainView extends BaseActivity<AbstractMainPresenter, AbstractMainRouter>
            implements IMainView {

    }

    public static abstract class AbstractMainPresenter extends BasePresenter<IMainView, AbstractMainRouter> {

        public AbstractMainPresenter(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {
            super(presenterSubcomponentBuilders);
        }
    }


    public static abstract class AbstractMainRouter extends BaseActivityRouter {

        public AbstractMainRouter(@NonNull ActivityRouterAdapter routeConductor) {
            super(routeConductor);
        }

        public abstract void navigateToAccelerometerScreen();

        public abstract void showNotFoundAccDialog();
    }

}
