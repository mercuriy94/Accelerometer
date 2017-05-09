package katya.mercuriy94.com.katyamacc.data.repository.file;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;
import katya.mercuriy94.com.katyamacc.data.repository.file.local.IFileLocalRepository;

/**
 * Created by Nikita on 08.05.2017.
 */

public class FileRepository implements IFileRepositry {

    @Inject
    IFileLocalRepository mFileLocalRepository;

    @Inject
    public FileRepository() {
    }

    @Override
    public Observable<Boolean> saveAccResults(List<AccelerometerSensor> accelerometerSensors) {
        return mFileLocalRepository.saveAccResults(accelerometerSensors);
    }
}
