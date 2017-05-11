package katya.mercuriy94.com.katyamacc.data.entity;

/**
 * Created by nikita on 20.11.2016.
 */
public class AccelerometerSensor {


    private float mX;
    private float mY;
    private float mZ;
    private long mTimeStamp;

    public AccelerometerSensor() {
    }

    public AccelerometerSensor(float x, float y, float z, long timeStamp) {
        mX = x;
        mY = y;
        mZ = z;
        mTimeStamp = timeStamp;
    }

    public float getX() {
        return mX;
    }

    public void setX(float x) {
        mX = x;
    }

    public float getY() {
        return mY;
    }

    public void setY(float y) {
        mY = y;
    }

    public float getZ() {
        return mZ;
    }

    public void setZ(float z) {
        mZ = z;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }
}
