package katya.mercuriy94.com.katyamacc.presentation.common.di.repository;

import dagger.Binds;
import dagger.Module;
import katya.mercuriy94.com.katyamacc.data.repository.file.local.FileLocalRepository;
import katya.mercuriy94.com.katyamacc.data.repository.file.local.IFileLocalRepository;

/**
 * Created by Nikita on 08.05.2017.
 */
@Module
public abstract class LocalRepositoryModule {

    @Binds
    public abstract IFileLocalRepository bindFileRepository(FileLocalRepository fileRepository);

}
