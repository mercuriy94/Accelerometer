package katya.mercuriy94.com.katyamacc.presentation.module.main.submodule.accelerometer.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import katya.mercuriy94.com.katyamacc.R;
import katya.mercuriy94.com.katyamacc.data.entity.AccelerometerSensor;


public class RecyclerAccelerometrAdapter extends RecyclerView.Adapter<RecyclerAccelerometrAdapter.ViewHolder> {

    private List<AccelerometerSensor> mAccelerometerSensorList;

    public RecyclerAccelerometrAdapter() {
        this(null);
    }

    public RecyclerAccelerometrAdapter(List<AccelerometerSensor> accelerometerSensorList) {
        if (accelerometerSensorList == null) accelerometerSensorList = new ArrayList<>();
        mAccelerometerSensorList = accelerometerSensorList;
    }

    public List<AccelerometerSensor> getList() {
        return mAccelerometerSensorList;
    }

    public void inserItemAndNotify(AccelerometerSensor accelerometerSensor) {
        if (mAccelerometerSensorList == null) mAccelerometerSensorList = new ArrayList<>();
        mAccelerometerSensorList.add(accelerometerSensor);
        notifyItemInserted(mAccelerometerSensorList.indexOf(accelerometerSensor));
    }

    public void inserItemsAndNotify(List<AccelerometerSensor> sensors) {
        int quantityNewMessages = sensors.size();
        mAccelerometerSensorList.addAll(sensors);
        if (mAccelerometerSensorList.size() == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(sensors.size() - quantityNewMessages, quantityNewMessages);
        }
    }

    public void cleanningList() {
        if (mAccelerometerSensorList == null) mAccelerometerSensorList = new ArrayList<>();
        mAccelerometerSensorList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.act_main_sensor_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccelerometerSensor accelerometerSensor = mAccelerometerSensorList.get(position);
        holder.mTvX.setText(String.valueOf(accelerometerSensor.getX()));
        holder.mTvY.setText(String.valueOf(accelerometerSensor.getY()));
        holder.mTvZ.setText(String.valueOf(accelerometerSensor.getZ()));
    }

    @Override
    public int getItemCount() {
        return mAccelerometerSensorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.act_main_item_tv_x)
        TextView mTvX;
        @BindView(R.id.act_main_item_tv_y)
        TextView mTvY;
        @BindView(R.id.act_main_item_tv_z)
        TextView mTvZ;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
