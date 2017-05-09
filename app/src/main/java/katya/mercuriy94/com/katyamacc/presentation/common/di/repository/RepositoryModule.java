package katya.mercuriy94.com.katyamacc.presentation.common.di.repository;

import dagger.Binds;
import dagger.Module;
import katya.mercuriy94.com.katyamacc.data.repository.file.FileRepository;
import katya.mercuriy94.com.katyamacc.data.repository.file.IFileRepositry;

/**
 * Created by Nikita on 08.05.2017.
 */

@Module(includes = LocalRepositoryModule.class)
public abstract class RepositoryModule {


    @Binds
    abstract IFileRepositry bindFileRepository(FileRepository fileRepository);


}
