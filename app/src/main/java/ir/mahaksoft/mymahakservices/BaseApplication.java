package ir.mahaksoft.mymahakservices;

import android.app.Application;
import android.os.SystemClock;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.concurrent.TimeUnit;

import ir.mahaksoft.mymahakservices.Receiver.ConnectivityReceiver;

/**
 * Created by Saeid.mhd@gmail.com on 4/8/17.
 */

public class BaseApplication extends Application {


    private static BaseApplication mInstance;
    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Don't do this! This is just so cold launches take some time
        //SystemClock.sleep(TimeUnit.SECONDS.toMillis(2));

        mInstance = this;
    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
