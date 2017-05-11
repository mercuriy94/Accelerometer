package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.presenter.assembly;

import dagger.Module;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.PresenterModule;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.presenter.GraphPresneter;

/**
 * Created by Nikita on 10.05.2017.
 */

@Module
public class GraphPresenterModule extends PresenterModule<GraphPresneter> {
    public GraphPresenterModule(GraphPresneter graphPresneter) {
        super(graphPresneter);
    }
}
