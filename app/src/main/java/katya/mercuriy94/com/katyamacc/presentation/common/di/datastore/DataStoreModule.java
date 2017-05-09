package katya.mercuriy94.com.katyamacc.presentation.common.di.datastore;

import dagger.Binds;
import dagger.Module;
import katya.mercuriy94.com.katyamacc.data.repository.DataStore;
import katya.mercuriy94.com.katyamacc.data.repository.IDataStore;
import katya.mercuriy94.com.katyamacc.presentation.common.di.repository.RepositoryModule;

/**
 * Created by Nikita on 08.05.2017.
 */
@Module(includes = RepositoryModule.class)
public abstract class DataStoreModule {

    @Binds
    public abstract IDataStore bindDataStore(DataStore dataStore);
}
