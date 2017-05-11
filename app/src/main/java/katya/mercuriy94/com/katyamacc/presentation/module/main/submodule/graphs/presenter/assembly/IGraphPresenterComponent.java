package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.presenter.assembly;

import dagger.Subcomponent;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.PresenterComponent;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.PresenterComponentBuilder;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.presenter.GraphPresneter;

/**
 * Created by Nikita on 10.05.2017.
 */
@Subcomponent(modules = GraphPresenterModule.class)
public interface IGraphPresenterComponent extends PresenterComponent<GraphPresneter> {

    @Subcomponent.Builder
    interface Builder extends PresenterComponentBuilder<GraphPresenterModule, IGraphPresenterComponent> {

    }
}
