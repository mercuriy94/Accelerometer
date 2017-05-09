package katya.mercuriy94.com.katyamacc.data.repository;

import java.util.List;

import io.reactivex.Observable;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;

/**
 * Created by Nikita on 08.05.2017.
 */

public interface IDataStore {

    Observable<Boolean> saveAccResults(List<AccelerometerSensor> sensors);

}
