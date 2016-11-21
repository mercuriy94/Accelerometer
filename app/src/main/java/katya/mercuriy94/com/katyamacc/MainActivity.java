package katya.mercuriy94.com.katyamacc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //const
    public static final String TAG = "MainActivity";

    //views
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.act_main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.act_main_fab_start)
    FloatingActionButton mFabPlay;
    @BindView(R.id.act_main_fab_pause)
    FloatingActionButton mFabPause;

    //data
    private RecyclerAccelerometrAdapter mRecyclerAccAdapter;
    private SensorManager mSensorManager;

    //anims
    private Animation mAnimZoom;
    private Animation mAnimZoomOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        initFields();
        initAnims();
    }


    private void initFields() {
        initRecyclerView();
    }

    private void initAnims() {
        mAnimZoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
        mAnimZoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
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
     * @param sensorManager объект для управления сенсором
     * @throws NullPointerException с случае если sensorManaget = {@code null}
     */
    private boolean registerListenerSensorAccelerometr(SensorManager sensorManager) throws NullPointerException {
        if (sensorManager == null) {
            throw new NullPointerException(getLocalClassName() + "sensorManager = null");
        }

        boolean result = false;
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorAccelerometer == null) showDialogNotFoundAccelerometer();
        else {
            result = sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        }
        return result;
    }


    private void unregisterListenerSensorAccelerometr(SensorManager sensorManager) {
        if (sensorManager == null) {
            throw new NullPointerException(getLocalClassName() + "sensorManager = null");
        }
        sensorManager.unregisterListener(this);
    }


    public SensorManager getSensorManager() {
        if (mSensorManager == null) {
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        }
        return mSensorManager;
    }


    @OnClick(R.id.act_main_fab_start)
    public void onClickPlay() {
        boolean resultRgistration = registerListenerSensorAccelerometr(getSensorManager());
        if (resultRgistration) {
            mAnimZoomOut.setAnimationListener(mAnimationListenerForRegisterSensorListener);
            mFabPlay.startAnimation(mAnimZoomOut);
        }
    }

    @OnClick(R.id.act_main_fab_pause)
    public void onClickPause() {
        unregisterListenerSensorAccelerometr(getSensorManager());
        mAnimZoomOut.setAnimationListener(mAnimationListenerForUnegisterSensorListener);
        mFabPause.startAnimation(mAnimZoomOut);
    }

    private void setFabsVisibilityForPlaying() {
        mFabPlay.setVisibility(View.GONE);
        mFabPause.setVisibility(View.VISIBLE);
        mFabPlay.setClickable(false);
        mFabPause.setClickable(true);
    }

    private void setFabsVeibilityForStoping() {
        mFabPause.setVisibility(View.GONE);
        mFabPlay.setVisibility(View.VISIBLE);
        mFabPlay.setClickable(true);
        mFabPause.setClickable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cleaning) {
            Log.d(TAG, "onClickClear");
            mRecyclerAccAdapter.cleanningList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setFabsVeibilityForStoping();
        mFabPlay.startAnimation(mAnimZoom);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterListenerSensorAccelerometr(getSensorManager());
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


    private Animation.AnimationListener mAnimationListenerForRegisterSensorListener
            = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            mAnimZoomOut.setAnimationListener(null);
            mAnimZoom.setAnimationListener(null);
            mFabPause.startAnimation(mAnimZoom);
            setFabsVisibilityForPlaying();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private Animation.AnimationListener mAnimationListenerForUnegisterSensorListener
            = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mAnimZoomOut.setAnimationListener(null);
            mAnimZoom.setAnimationListener(null);
            mFabPlay.startAnimation(mAnimZoom);
            setFabsVeibilityForStoping();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };


}
