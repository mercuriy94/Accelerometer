package katya.mercuriy94.com.katyamacc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.act_main_btn_start)
    Button mBtnStart;
    @BindView(R.id.act_main_btn_pause)
    Button mBtnPause;
    @BindView(R.id.act_main_btn_clear)
    Button mBtnClear;
    @BindView(R.id.act_main_recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerAccelerometrAdapter mRecyclerAccAdapter;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        initFields();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }


    private void initFields() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linManager = new LinearLayoutManager(this);
        linManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linManager);
        mRecyclerAccAdapter = new RecyclerAccelerometrAdapter();
        mRecyclerView.setAdapter(mRecyclerAccAdapter);
    }


    /**
     * Регистрация слушателя на изменения показаний датчика.
     *
     * @param sensorManager объекь для управления сенсорами
     * @throws NullPointerException с случае если sensorManaget = {@code null}
     */
    private void registerListenerSensorAccelerometr(SensorManager sensorManager) throws NullPointerException {
        if (sensorManager == null) {
            throw new NullPointerException(getLocalClassName() + "sensorManager = null");
        }

        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorAccelerometer == null) showDialogNotFoundAccelerometer();
        else {
            sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    private void unregisterListenerSensorAccelerometr(SensorManager sensorManager) {
        if (sensorManager == null) {
            throw new NullPointerException(getLocalClassName() + "sensorManager = null");
        }
        sensorManager.unregisterListener(this);
    }


    @OnClick(R.id.act_main_btn_start)
    public void onClickStart() {
        Log.d(TAG, "onClickStart");
        registerListenerSensorAccelerometr(mSensorManager);
    }


    @OnClick(R.id.act_main_btn_pause)
    public void onClickPause() {
        Log.d(TAG, "onClickPause");
        unregisterListenerSensorAccelerometr(mSensorManager);
    }

    @OnClick(R.id.act_main_btn_clear)
    public void onClickClear() {
        Log.d(TAG, "onClickClear");
        mRecyclerAccAdapter.cleanningList();
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterListenerSensorAccelerometr(mSensorManager);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: {
                recordReadingsAccelerometr(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * Метод для записывания показаний в список
     */
    private void recordReadingsAccelerometr(float x, float y, float z) {
        AccelerometerSensor accelerometerSensor = new AccelerometerSensor(x, y, z);
        mRecyclerAccAdapter.inserItemAndNotify(accelerometerSensor);
        mRecyclerView.smoothScrollToPosition(mRecyclerAccAdapter.getItemCount() - 1);
        loggingRecordReadingsAccelerometr(x, y, z);

    }


    private void loggingRecordReadingsAccelerometr(float x, float y, float z) {
        Log.d(TAG, String.format("Показания: x = %f, y = %f, z = %f", x, y, z));
    }


    private void showDialogNotFoundAccelerometer() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.dialog_message_acc_not_dound_and_close_app))
                .setPositiveButton(getString(R.string.dialog_btn_ok), mDialogListenerNotFoundAccelerometr)
                .setNegativeButton(getString(R.string.dialog_btn_no), mDialogListenerNotFoundAccelerometr)
                .create().show();
    }


    private DialogInterface.OnClickListener mDialogListenerNotFoundAccelerometr
            = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case DialogInterface.BUTTON_POSITIVE: {
                    closeApp();
                }
            }
        }
    };


    private void closeApp() {
        finish();
    }

}
