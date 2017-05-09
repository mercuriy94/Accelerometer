package katya.mercuriy94.com.katyamacc.data.repository.file.local;

import android.content.Context;
import android.os.Environment;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import katya.mercuriy94.com.katyamacc.R;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;

/**
 * Created by Nikita on 08.05.2017.
 */

public class FileLocalRepository implements IFileLocalRepository {

    public static final String TIME_FORMAT = "dd-MM-yyyy'T'HH:mm:ss";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());


    @Inject
    Context mContext;

    @Inject
    public FileLocalRepository() {
    }


    @Override
    public Observable<Boolean> saveAccResults(List<AccelerometerSensor> accelerometerSensors) {
        return Observable.fromCallable(() -> {

            StringBuilder stringBuilder = new StringBuilder();

          /*  for (AccelerometerSensor sensor : accelerometerSensors) {
                stringBuilder.append(sensor.getX())
                        .append("\t")
                        .append(sensor.getY())
                        .append("\t")
                        .append(sensor.getZ())
                        .append("\n");
            }
            return write(simpleDateFormat.format(new Date()) + ".txt", stringBuilder.toString());*/
            return writeToExcelFile(simpleDateFormat.format(new Date()) + ".csv", accelerometerSensors);
        });
    }


    private boolean writeToExcelFile(String fileName, List<AccelerometerSensor> accelerometerSensors) throws IOException {
        String dir = mContext.getString(R.string.app_name);
        File file = new File(Environment.getExternalStorageDirectory(), dir);
        if (!file.exists() && !file.mkdirs()) return false;
        file = new File(file.getAbsolutePath(), fileName);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
        CSVWriter csvWriter = new CSVWriter(writer);
        List<String[]> list = new ArrayList<>();

        for (int i = 0; i < accelerometerSensors.size(); i++) {
            AccelerometerSensor accelerometerSensor = accelerometerSensors.get(i);
            String[] row = new String[3];
            row[0] = String.valueOf(accelerometerSensor.getX());
            row[1] = String.valueOf(accelerometerSensor.getY());
            row[2] = String.valueOf(accelerometerSensor.getZ());
            list.add(row);
        }

        csvWriter.writeAll(list);
        csvWriter.close();
        return true;
    }


    private boolean write(String fileName, String text) throws FileNotFoundException {

        String dir = mContext.getString(R.string.app_name);
        File file = new File(Environment.getExternalStorageDirectory(), dir);
        if (!file.exists() && !file.mkdirs()) return false;
        file = new File(file.getAbsolutePath(), fileName);
        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
        try {
            out.print(text);
        } finally {
            out.close();
        }
        return true;
    }

}
