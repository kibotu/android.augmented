package net.kibotu.android.albert;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import net.kibotu.android.albert.stl.STLFileObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 12.02.13
 * Time: 17:35
 * To change this template use File | Settings | File Templates.
 */
public class ViWiTraMain extends AndroidApplication {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static List<STLFileObject> stlFileObjects;

    /**
     * Called when the activity is first created.
     * This is where you should do all of your normal static set up: create views, bind data to lists, etc.
     * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one
     * Always followed by onStart().
     * Not killable.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useWakelock = true;
        cfg.useGL20 = true;

        initialize(new ViWiTraMainViewRenderer(this), cfg);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.productlistdialogview);
        dialog.setTitle("product list");

        dialog.show();

        ListView listView = (ListView) dialog.findViewById(R.id.productpartslistview);

        ArrayAdapter<STLFileObject> adapter = new ArrayAdapter<STLFileObject>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, stlFileObjects);

        listView.setAdapter(adapter);

    }

    /**
     * Called when the activity is becoming visible to the user.
     * Followed by onResume() if the activity comes to the foreground, or onStop() if it becomes hidden.
     * Not killable.
     */
    @Override
    protected void onStart() {
        Log.d(TAG, this.getClass().getSimpleName() + ": onStart (could be followed by the lifecyclemethod onResume() or onStop())");
        super.onStart();
    }

    /**
     * Called after your activity has been stopped, prior to it being started again.
     * Always followed by onStart()
     * Not killable.
     */
    @Override
    protected void onRestart() {
        Log.d(TAG, this.getClass().getSimpleName() + ": onRestart (followed by the lifecyclemethod onStart())");
        super.onRestart();
    }

    /**
     * Called when the activity will start interacting with the user.
     * At this point your activity is at the top of the activity stack, with user input going to it.
     * Always followed by onPause().
     * Not killable.
     */
    @Override
    protected void onResume() {
        Log.d(TAG, this.getClass().getSimpleName() + ": onResume (followed by the lifecyclemethod onPause())");
        super.onResume();
    }

    /**
     * Called when the system is about to start resuming a previous activity.
     * This is typically used to commit unsaved changes to persistent data, stop animations and other things that may be consuming CPU, etc.
     * Implementations of this method must be very quick because the next activity will not be resumed until this method returns.
     * Followed by either onResume() if the activity returns back to the front, or onStop() if it becomes invisible to the user.
     * Killable.
     */
    @Override
    protected void onPause() {
        Log.d(TAG, this.getClass().getSimpleName() + ": onPause (could be followed by the lifecyclemethod onResume() or onStop())");
        super.onPause();
    }

    /**
     * Called when the activity is no longer visible to the user, because another activity has been resumed and is covering this one.
     * This may happen either because a new activity is being started, an existing one is being brought in front of this one, or this one is being destroyed.
     * Followed by either onRestart() if this activity is coming back to interact with the user, or onDestroy() if this activity is going away.
     * Killable.
     */
    @Override
    protected void onStop() {
        Log.d(TAG, this.getClass().getSimpleName() + ": onStop (could be followed by the lifecyclemethod onRestart() or onDestroy())");
        super.onStop();
    }

    /**
     * The final call you receive before your activity is destroyed.
     * This can happen either because the activity is finishing (someone called finish() on it, or because the system is temporarily destroying this instance of the activity to save space.
     * You can distinguish between these two scenarios with the isFinishing() method.
     * Killable.
     */
    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            Log.d(TAG, this.getClass().getSimpleName() + ": onDestroy (coused by finish())");
        } else {
            Log.d(TAG, this.getClass().getSimpleName() + ": onDestroy (coused by destroying)");
        }
        super.onDestroy();
    }

}
