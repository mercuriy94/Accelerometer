package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.view;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;
import katya.mercuriy94.com.katyamacc.R;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;
import katya.mercuriy94.com.katyamacc.presentation.common.annotations.Layout;
import katya.mercuriy94.com.katyamacc.presentation.module.app.MyApp;
import katya.mercuriy94.com.katyamacc.presentation.module.main.MainModuleContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.GraphModuleContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.graphs.presenter.GraphPresneter;

import static android.content.Context.SENSOR_SERVICE;

@Layout(R.layout.graph_layout)
public class GraphFragmentView extends GraphModuleContract.AbstractGraphView
        implements SensorEventListener {

    public static final String TAG = "GraphFragmentView";


    @InjectPresenter
    GraphModuleContract.AbstractGraphPresenter mPresenter;

    @BindView(R.id.chart)
    LineChart mChartView;


    @BindView(R.id.frg_graph_fab_start_pause)
    FloatingActionButton mFabStartPause;

    //Флаг указывающий на состояние регистрации слушателя
    private boolean mRunningListenerAccelerometer;

    //anims
    private Animation mAnimationFabPlayPauseRunTop;
    private Animation mAnimationFabPlayPauseRunBottom;
    private Animation mFabPlayPauseAnimZoom;
    private Animation mFabPlayPauseAnimZoomOut;

    private SensorManager mSensorManager;

    private PublishSubject<AccelerometerSensor> mSensorPublishSubject = PublishSubject.create();

    private long startTime = 0;
    double mLastRandom = 2;
    Random mRand = new Random();

    private double getRandom() {
        return mLastRandom += mRand.nextDouble() * 0.5 - 0.25;
    }

    public static GraphFragmentView newInstance() {
        return new GraphFragmentView();
    }

    @ProvidePresenter
    GraphModuleContract.AbstractGraphPresenter providePresenter() {
        return new GraphPresneter(MyApp.get(getActivity()), new RxPermissions(getActivity()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChartView.setPinchZoom(true);

        XAxis xAxis = mChartView.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setPosition(XAxis.XAxisPosition.TOP);

        YAxis leftAxis = mChartView.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setDrawTopYLabelEntry(true);

        YAxis rightAxis = mChartView.getAxisRight();
        rightAxis.setEnabled(false);


        initAnims();
        setHasOptionsMenu(true);
    }


    private void initAnims() {
        //region fab play pause anims
        mFabPlayPauseAnimZoom = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom);
        mFabPlayPauseAnimZoom.setAnimationListener(mFabPlayPauseAnimationListener);
        mFabPlayPauseAnimZoomOut = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_out);
        mFabPlayPauseAnimZoomOut.setAnimationListener(mFabPlayPauseAnimationListener);
        mAnimationFabPlayPauseRunTop = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_run_top);
        mAnimationFabPlayPauseRunTop.setAnimationListener(mFabPlayPauseAnimationListener);
        mAnimationFabPlayPauseRunBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_run_bottom);
        mAnimationFabPlayPauseRunBottom.setAnimationListener(mFabPlayPauseAnimationListener);
        //endregion fab play pause anims
    }


    @Override
    public void setInitialState() {
        if (mFabStartPause.getVisibility() == View.VISIBLE || mFabStartPause.getVisibility() == View.INVISIBLE) {
            mFabStartPause.setVisibility(View.VISIBLE);
            mFabStartPause.clearAnimation();
            mFabStartPause.startAnimation(mAnimationFabPlayPauseRunTop);
        }
    }


    @Override
    public void showResults(List<AccelerometerSensor> accelerometerSensors, boolean scrollToLastPosition) {

        if (accelerometerSensors.size() == 0) return;

        if (mChartView.getLineData() == null) {
            List<ILineDataSet> lineDataSets = createDataSets();
            for (AccelerometerSensor accelerometerSensor : accelerometerSensors) {
                addAccResuls(lineDataSets, accelerometerSensor);
            }
            LineData data = new LineData(lineDataSets);
            mChartView.setData(data);
        } else {
            List<ILineDataSet> dataSets = mChartView.getLineData().getDataSets();
            for (AccelerometerSensor accelerometerSensor : accelerometerSensors) {
                addAccResuls(dataSets, accelerometerSensor);
            }
            mChartView.getLineData().notifyDataChanged();
            mChartView.notifyDataSetChanged();
        }

        mChartView.invalidate();

    }


    @Override
    public void addNewResult(AccelerometerSensor accelerometerSensor) {

        if (mChartView.getLineData() == null) {
            List<ILineDataSet> lineDataSets = createDataSets();
            addAccResuls(lineDataSets, accelerometerSensor);
            LineData data = new LineData(lineDataSets);
            mChartView.setData(data);
        } else {
            List<ILineDataSet> dataSets = mChartView.getLineData().getDataSets();
            addAccResuls(dataSets, accelerometerSensor);
            mChartView.getLineData().notifyDataChanged();
            mChartView.notifyDataSetChanged();
        }

        mChartView.invalidate();
    }

    private void addAccResuls(List<ILineDataSet> lineDataSets, AccelerometerSensor accelerometerSensor) {
        for (ILineDataSet lineDataSet : lineDataSets) {
            if (lineDataSet.getLabel().equals("X")) {
                lineDataSet.addEntry(new Entry((float) accelerometerSensor.getTimeStamp(), accelerometerSensor.getX()));
            } else if (lineDataSet.getLabel().equals("Y")) {
                lineDataSet.addEntry(new Entry((float) accelerometerSensor.getTimeStamp(), accelerometerSensor.getY()));
            } else if (lineDataSet.getLabel().equals("Z")) {
                lineDataSet.addEntry(new Entry((float) accelerometerSensor.getTimeStamp(), accelerometerSensor.getZ()));
            }
        }
    }


    private List<ILineDataSet> createDataSets() {

        LineDataSet mLineDataX = new LineDataSet(new ArrayList<>(), "X");
        mLineDataX.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        mLineDataX.setAxisDependency(YAxis.AxisDependency.LEFT);
        mLineDataX.setLineWidth(getActivity().getResources().getDimension(R.dimen.width_line));

        LineDataSet mLineDataY = new LineDataSet(new ArrayList<>(), "Y");
        mLineDataY.setAxisDependency(YAxis.AxisDependency.LEFT);
        mLineDataY.setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        mLineDataY.setLineWidth(getActivity().getResources().getDimension(R.dimen.width_line));

        LineDataSet mLineDataZ = new LineDataSet(new ArrayList<>(), "Z");
        mLineDataZ.setAxisDependency(YAxis.AxisDependency.LEFT);
        mLineDataZ.setColor(ContextCompat.getColor(getActivity(), R.color.colorGreenNormal));
        mLineDataZ.setLineWidth(getActivity().getResources().getDimension(R.dimen.width_line));

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(mLineDataX);
        dataSets.add(mLineDataY);
        dataSets.add(mLineDataZ);
        return dataSets;
    }


    @Override
    public void start(boolean animEnable) {
        registerListenerSensorAccelerometr(getSensorManager(), animEnable);
    }

    @Override
    public void pause(boolean animEnable) {
        unregisterListenerSensorAccelerometr(getSensorManager(), animEnable);
    }

    public SensorManager getSensorManager() {
        if (mSensorManager == null) {
            mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        }
        return mSensorManager;
    }


    /**
     * Регистрация слушателя на изменения показаний датчика.
     *
     * @param sensorManager объект для управления сенсором
     * @throws NullPointerException с случае если sensorManaget = {@code null}
     */
    private boolean registerListenerSensorAccelerometr(SensorManager sensorManager, boolean animEnable) throws NullPointerException {
        if (sensorManager == null) {
            throw new NullPointerException(getClass().getName() + "sensorManager = null");
        }

        boolean result = false;
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorAccelerometer == null) mPresenter.notFoundAccelerometerSensor();
        else {
            if (!mRunningListenerAccelerometer) {
                mPresenter.setRxSensorPublisher(mSensorPublishSubject);
                result = sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                if (result) startTime = new Date().getTime();
            } else result = true;

        }
        mRunningListenerAccelerometer = result;

        if (mRunningListenerAccelerometer) {
            if (animEnable) mFabStartPause.startAnimation(mFabPlayPauseAnimZoomOut);
            else mFabStartPause.setImageResource(R.drawable.ic_pause);
        }

        return result;
    }

    private void unregisterListenerSensorAccelerometr(SensorManager sensorManager, boolean animEnable) {
        if (sensorManager == null) {
            throw new NullPointerException(getClass().getName() + "sensorManager = null");
        }
        sensorManager.unregisterListener(this);
        mRunningListenerAccelerometer = false;
        if (animEnable) mFabStartPause.startAnimation(mFabPlayPauseAnimZoomOut);
        else mFabStartPause.setImageResource(R.drawable.ic_start);
    }


    @Override
    public void clear(boolean animEnable) {
        if (mChartView.getLineData() != null) {
            mChartView.setData(null);
            mChartView.invalidate();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            mFabStartPause.startAnimation(mAnimationFabPlayPauseRunBottom);
            return true;
        } else if (id == R.id.action_cleaning) {
            Log.d(TAG, "onClickClear");
            getPresenter().onClickBtnRemoveResults();
            return true;
        } else if (id == R.id.action_save) {
            Log.d(TAG, "onClickSave");
            getPresenter().onClickBtnSave();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        mFabStartPause.startAnimation(mAnimationFabPlayPauseRunBottom);
    }

    @NonNull
    @Override
    public GraphModuleContract.AbstractGraphPresenter getPresenter() {
        return mPresenter;
    }

    @NonNull
    @Override
    protected MainModuleContract.AbstractMainRouter getRouter() {
        return ((MainModuleContract.AbstractMainView) getActivity()).getPresenter().getRouter();
    }


    public boolean isRunningListenerAccelerometer() {
        return mRunningListenerAccelerometer;
    }


    //region SensorEventListener

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: {
                mSensorPublishSubject.onNext(new AccelerometerSensor(event.values[0],
                        event.values[1],
                        event.values[2],
                        0L));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing
    }

    //endregion


    //region FABPlayPauseAnimationsListeners

    private Animation.AnimationListener mFabPlayPauseAnimationListener
            = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mFabStartPause.clearAnimation();
            if (animation == mFabPlayPauseAnimZoomOut) {
                if (isRunningListenerAccelerometer()) {
                    mFabStartPause.setImageResource(R.drawable.ic_pause);
                } else {
                    mFabStartPause.setImageResource(R.drawable.ic_start);
                }
                mFabStartPause.startAnimation(mFabPlayPauseAnimZoom);
            } else if (animation == mAnimationFabPlayPauseRunBottom) {
                mFabStartPause.setVisibility(View.GONE);
                getPresenter().onBackPressed();

            } else if (animation == mAnimationFabPlayPauseRunTop) {
                mFabStartPause.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    //endregion FABPlayPauseAnimationsListeners


    @OnClick(R.id.frg_graph_fab_start_pause)
    public void onClickPlayPauseButton() {
        getPresenter().onClickBtnPlayPause();
    }

}
