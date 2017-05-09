package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.HasPresenterSubcomponentBuilders;
import katya.mercuriy94.com.katyamacc.presentation.model.TitleModel;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.GraphModuleContract;

/**
 * Created by Nikita on 09.05.2017.
 */

@InjectViewState
public class GraphPresneter extends GraphModuleContract.AbstractGraphPresenter {

    public GraphPresneter(@NonNull HasPresenterSubcomponentBuilders presenterSubcomponentBuilders) {
        super(presenterSubcomponentBuilders);
    }

    @Override
    protected TitleModel getTitle() {
        return null;
    }
}
