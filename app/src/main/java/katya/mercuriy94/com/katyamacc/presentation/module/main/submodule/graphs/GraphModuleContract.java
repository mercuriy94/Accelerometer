package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs;

import android.support.annotation.NonNull;

import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.HasPresenterSubcomponentBuilders;
import katya.mercuriy94.com.katyamacc.presentation.common.presenter.BasePresenter;
import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseFragment;
import katya.mercuriy94.com.katyamacc.presentation.common.view.IBaseView;
import katya.mercuriy94.com.katyamacc.presentation.module.main.MainModuleContract;

/**
 * Created by Nikita on 09.05.2017.
 */

public class GraphModuleContract {

    public GraphModuleContract() {
        throw new RuntimeException("no instance please!");
    }

    public interface IGraphView extends IBaseView {

    }

    public static abstract class AbstractGraphView extends BaseFragment<AbstractGraphPresenter, MainModuleContract.AbstractMainRouter>
            implements IGraphView {


    }

    public static abstract class AbstractGraphPresenter extends BasePresenter<IGraphView, MainModuleContract.AbstractMainRouter> {

        public AbstractGraphPresenter(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {
            super(presenterSubcomponentBuilders);
        }
    }


}
