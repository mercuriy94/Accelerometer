package katya.mercuriy94.com.katyamacc.presentation.module.main.router;

import android.support.annotation.NonNull;

import katya.mercuriy94.com.katyamacc.presentation.common.router.ActivityRouterAdapter;
import katya.mercuriy94.com.katyamacc.presentation.module.main.MainModuleContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.view.AccelerometerFragmentView;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.notfoundaccdialog.view.NotFoundAccDialogView;

/**
 * Created by Nikita on 08.05.2017.
 */

public class MainRouter extends MainModuleContract.AbstractMainRouter {
    public MainRouter(@NonNull ActivityRouterAdapter routeConductor) {
        super(routeConductor);
    }

    @Override
    public void navigateToAccelerometerScreen() {
        if (getRouteAdapter() != null) {
            navigateToFragment(AccelerometerFragmentView.newInstance(),
                    getRouteAdapter().getContainerForFragmentsId(),
                    AccelerometerFragmentView.TAG);
        }
    }

    @Override
    public void showNotFoundAccDialog() {
        if (getRouteAdapter() != null) showDialogFragment(new NotFoundAccDialogView());

    }
}
