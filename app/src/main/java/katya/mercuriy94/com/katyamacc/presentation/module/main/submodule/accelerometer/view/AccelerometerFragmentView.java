package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.view;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;
import katya.mercuriy94.com.katyamacc.R;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;
import katya.mercuriy94.com.katyamacc.presentation.common.annotations.Layout;
import katya.mercuriy94.com.katyamacc.presentation.module.app.MyApp;
import katya.mercuriy94.com.katyamacc.presentation.module.main.MainModuleContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.AccelerometerModuleContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.presenter.AccelerometerPresenter;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Nikita on 08.05.2017.
 */

@Layout(R.layout.frg_acc_layout)
public class AccelerometerFragmentView extends AccelerometerModuleContract.AbstractAccelerometerView
        implements SensorEventListener {

    public static final String TAG = "AccelerometerFragmentView";
    private static final String STATE_VISIBILITY_FAB_KEY = "isVisibleFab";


    @InjectPresenter
    AccelerometerModuleContract.AbstractAccelerometerPresenter mPresenter;

    private RecyclerAccelerometrAdapter mRecyclerAccAdapter;

    @BindView(R.id.act_main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.frg_acc_fab_start_pause)
    FloatingActionButton mFabStartPause;
    @BindView(R.id.frg_acc_fab_graph)
    FloatingActionButton mFabGraph;

    //Флаг указывающий на состояние регистрации слушателя
    private boolean mRunningListenerAccelerometer;
    //Вспомогательный флаг для прокрутки списка
    private boolean mKeyScroll = true;
    private SensorManager mSensorManager;
    private PublishSubject<AccelerometerSensor> mSensorPublishSubject = PublishSubject.create();
    private LinearLayoutManager mLayoutManager;

    //anims
    private Animation mAnimationFabPlayPauseRunTop;
    private Animation mAnimationFabPlayPauseRunBottom;
    private Animation mFabPlayPauseAnimZoom;
    private Animation mFabPlayPauseAnimZoomOut;
    private Animation mAnimationFabGrapghRunRight;
    private Animation mAnimationFabGrapghRunRightClick;
    private Animation mAnimationFabGrapghRunLeft;


    @ProvidePresenter
    AccelerometerModuleContract.AbstractAccelerometerPresenter providePresenter() {
        return new AccelerometerPresenter(MyApp.get(getActivity()), new RxPermissions(getActivity()));
    }

    public static AccelerometerFragmentView newInstance() {
        return new AccelerometerFragmentView();
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mFabStartPause.setVisibility(savedInstanceState.getInt(STATE_VISIBILITY_FAB_KEY));
            mFabGraph.setVisibility(savedInstanceState.getInt(STATE_VISIBILITY_FAB_KEY));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_VISIBILITY_FAB_KEY, mFabStartPause.getVisibility());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initRecyclerView();
        initAnims();
        setRetainInstance(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cleaning) {
            Log.d(TAG, "onClickClear");
            getPresenter().onClickBtnRemoveResults();
            return true;
        } else if (id == R.id.action_save) {
            Log.d(TAG, "onClickSave");
            getPresenter().onClickBtnSave();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setInitialState() {
        if (mFabStartPause.getVisibility() == View.VISIBLE || mFabStartPause.getVisibility() == View.INVISIBLE) {
            mFabStartPause.setVisibility(View.VISIBLE);
            mFabStartPause.clearAnimation();
            mFabStartPause.startAnimation(mAnimationFabPlayPauseRunTop);
            mFabGraph.setVisibility(View.VISIBLE);
            mFabGraph.clearAnimation();
            mFabGraph.startAnimation(mAnimationFabGrapghRunLeft);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFabStartPause.getVisibility() == View.VISIBLE) {
            mFabStartPause.setVisibility(View.INVISIBLE);
            mFabGraph.setVisibility(View.INVISIBLE);
        }
    }

    private void initRecyclerView() {
        mRecyclerAccAdapter = new RecyclerAccelerometrAdapter();
        mRecyclerView.setAdapter(mRecyclerAccAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
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

        //region fab graph anims
        mAnimationFabGrapghRunLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_run_left);
        mAnimationFabGrapghRunLeft.setAnimationListener(mFabGraphAnimationListener);
        mAnimationFabGrapghRunRight = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_run_right);
        mAnimationFabGrapghRunRight.setAnimationListener(mFabGraphAnimationListener);
        mAnimationFabGrapghRunRightClick = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_run_right);
        mAnimationFabGrapghRunRightClick.setAnimationListener(mFabGraphAnimationListener);
        //endegion fab graph anims
    }

    @Override
    public void showResults(List<AccelerometerSensor> accelerometerSensors, boolean scrollToLastPosition) {
        if (accelerometerSensors.size() > 0) {
            mRecyclerAccAdapter.inserItemsAndNotify(accelerometerSensors);
            if (scrollToLastPosition) {
                mRecyclerView.scrollToPosition(mRecyclerAccAdapter.getItemCount());
                mRunningListenerAccelerometer = true;
            }else {
                mRunningListenerAccelerometer = false;
            }
        }
    }

    @Override
    public void addNewResult(AccelerometerSensor accelerometerSensor) {
        mRecyclerAccAdapter.inserItemAndNotify(accelerometerSensor);
        mRecyclerView.smoothScrollToPosition(mRecyclerAccAdapter.getItemCount());
    }


    @Override
    public void start(boolean animEnable) {
        registerListenerSensorAccelerometr(getSensorManager(), animEnable);
    }

    @Override
    public void pause(boolean animEnable) {
        unregisterListenerSensorAccelerometr(getSensorManager(), animEnable);
    }

    @Override
    public void clear(boolean animEnable) {
        mRecyclerAccAdapter.cleanningList();
        if (animEnable && mFabStartPause.getVisibility() == View.GONE) {
            mFabStartPause.setVisibility(View.VISIBLE);
            mFabStartPause.startAnimation(mAnimationFabPlayPauseRunTop);
            mFabGraph.setVisibility(View.VISIBLE);
            mFabGraph.startAnimation(mAnimationFabGrapghRunLeft);
        }
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
        if (animEnable && mFabStartPause.getVisibility() == View.VISIBLE) {
            mFabStartPause.startAnimation(mFabPlayPauseAnimZoomOut);
        } else mFabStartPause.setImageResource(R.drawable.ic_start);
    }


    public SensorManager getSensorManager() {
        if (mSensorManager == null) {
            mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        }
        return mSensorManager;
    }


    @NonNull
    @Override
    public AccelerometerModuleContract.AbstractAccelerometerPresenter getPresenter() {
        return mPresenter;
    }

    @NonNull
    @Override
    protected MainModuleContract.AbstractMainRouter getRouter() {
        return ((MainModuleContract.AbstractMainView) getActivity()).getPresenter().getRouter();
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

    //region OnScrollListener
    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            Log.d(TAG, "dy = " + dy);
            if (!mRunningListenerAccelerometer) {
                if ((dy == 0) &&
                        (!mKeyScroll) &&
                        (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) &&
                        (mLayoutManager.findLastCompletelyVisibleItemPosition() == mRecyclerAccAdapter.getItemCount() - 1) &&
                        mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    mKeyScroll = true;
                    mFabStartPause.setVisibility(View.VISIBLE);
                    mFabStartPause.startAnimation(mAnimationFabPlayPauseRunTop);
                    mFabGraph.setVisibility(View.VISIBLE);
                    mFabGraph.startAnimation(mAnimationFabGrapghRunLeft);
                } else if (dy > 0 && mKeyScroll && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mKeyScroll = false;
                    mFabStartPause.startAnimation(mAnimationFabPlayPauseRunBottom);
                    mFabGraph.startAnimation(mAnimationFabGrapghRunRight);
                } else if (dy < 0 && !mKeyScroll && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mKeyScroll = true;
                    mFabStartPause.setVisibility(View.VISIBLE);
                    mFabStartPause.startAnimation(mAnimationFabPlayPauseRunTop);
                    mFabGraph.setVisibility(View.VISIBLE);
                    mFabGraph.startAnimation(mAnimationFabGrapghRunLeft);
                }
            }

        }

   /*     @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                Log.d(TAG, "SCROLL_STATE_DRAGGING");
            } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                Log.d(TAG, "SCROLL_STATE_IDLE");
            } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                Log.d(TAG, "SCROLL_STATE_SETTLING");
            }
        }*/
    };

    //endregion OnScrollListener


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
            } else if (animation == mAnimationFabPlayPauseRunTop) {
                mFabStartPause.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    //endregion FABPlayPauseAnimationsListeners

    //region  FABGraphAnimationsListeners

    private Animation.AnimationListener mFabGraphAnimationListener
            = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mFabStartPause.clearAnimation();
            if (animation == mAnimationFabGrapghRunLeft) {
                mFabGraph.setVisibility(View.VISIBLE);
            } else if (animation == mAnimationFabGrapghRunRight) {
                mFabGraph.setVisibility(View.GONE);
            } else {
                mFabGraph.setVisibility(View.INVISIBLE);
                mFabStartPause.setVisibility(View.INVISIBLE);

                getPresenter().onClickBtnGraph();

            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    //endregion FABGraphAnimationsListeners

    //region methods


    public boolean isRunningListenerAccelerometer() {
        return mRunningListenerAccelerometer;
    }


    @OnClick(R.id.frg_acc_fab_start_pause)
    public void onClickPlayPauseButton() {
        getPresenter().onClickBtnPlayPause();
    }

    @OnClick(R.id.frg_acc_fab_graph)
    public void onClickGraphButton() {
        mFabStartPause.startAnimation(mAnimationFabPlayPauseRunBottom);
        mFabGraph.startAnimation(mAnimationFabGrapghRunRightClick);
    }

    //endregion methods
}
