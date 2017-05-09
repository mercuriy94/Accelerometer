package katya.mercuriy94.com.katyamacc.data.entity;

/**
 * Created by nikita on 20.11.2016.
 */
public class AccelerometerSensor {


    private float mX;
    private float mY;
    private float mZ;

    public AccelerometerSensor(float x, float y, float z) {
        mX = x;
        mY = y;
        mZ = z;
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
}
