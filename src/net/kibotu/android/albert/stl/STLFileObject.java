package net.kibotu.android.albert.stl;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.badlogic.gdx.math.Vector3;

import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 11.02.13
 * Time: 07:20
 * To change this template use File | Settings | File Templates.
 */
public class STLFileObject implements Parcelable {

    public static final String TAG = STLFileObject.class.getSimpleName();

    private final String name;

    private final float[] vertices;

    private final boolean isValid;

    public STLFileObject() {
        name = null;
        this.vertices = null;
        isValid = false;
    }

    public STLFileObject(Parcel in) {
        this.name = in.readString();
        Log.d(TAG, "name: " + name);
        this.vertices = in.createFloatArray();
        Log.d(TAG, "vertices length: " + vertices.length);
        isValid = in.createBooleanArray()[0];
        Log.d(TAG, "isValid: " + isValid);
    }

    public STLFileObject(final String name, final float[] vertices) {
        if(name == null || vertices == null)  {
            isValid = false;
        } else if(vertices.length%3 != 0) {
            isValid = false;
        } else {
            isValid = true;
        }
        if(name != null && name.length() > 0)
        {
            this.name = name;
        } else {
            this.name = "undefined";
        }
        this.vertices = vertices;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getName() {
        return name;
    }

    public float[] getVertices() {
        return vertices;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloatArray(vertices);
        dest.writeBooleanArray(new boolean[]{isValid});
    }

    public static final Parcelable.Creator<STLFileObject> CREATOR = new Parcelable.Creator<STLFileObject>() {
        public STLFileObject createFromParcel(Parcel in) {
            return new STLFileObject(in);
        }

        public STLFileObject[] newArray(int size) {
            return new STLFileObject[size];
        }
    };

    @Override
    public String toString() {
        return name;
    }
}
