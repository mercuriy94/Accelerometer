package katya.mercuriy94.com.katyamacc.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;
import katya.mercuriy94.com.katyamacc.data.repository.file.IFileRepositry;

/**
 * Created by Nikita on 08.05.2017.
 */

public class DataStore implements IDataStore {

    @Inject
    IFileRepositry mFileRepositry;

    @Inject
    public DataStore() {
    }

    @Override
    public Observable<Boolean> saveAccResults(List<AccelerometerSensor> sensors) {
        return mFileRepositry.saveAccResults(sensors);
    }
}
