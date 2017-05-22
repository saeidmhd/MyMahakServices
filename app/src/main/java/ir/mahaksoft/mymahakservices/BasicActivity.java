package ir.mahaksoft.mymahakservices;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class BasicActivity extends AppCompatActivity {

    private Tracker mTracker;


    public static String UserId = "";
    private static final String TAG = "BasicActivity";


    // Preferences Keys //
    public static SharedPreferences SP;
    public static String __Key_UserId = "UserId";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);


        //Shared Preferences
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        RefreshSharedPreferences();
    }

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
    protected void onResume() {

        super.onResume();
        getDefaultTracker().setScreenName(this.getLocalClassName());
        getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        setDimensions();
    }


    public static void RefreshSharedPreferences() {
        //Shared Preferences

        UserId = SP.getString(__Key_UserId, "");
        Log.e(TAG, SP.getString(__Key_UserId, ""));


    }

    private  void  setDimensions(){

        //custom dimension

        try{

            RefreshSharedPreferences();

            //DeviceModelNameDetail
            getDefaultTracker().send(new HitBuilders.ScreenViewBuilder()
                    .setCustomDimension(1, ""+android.os.Build.MANUFACTURER + android.os.Build.MODEL)
                    .build()
            );


            //UserId
            getDefaultTracker().send(new HitBuilders.ScreenViewBuilder()
                    .setCustomDimension(2,UserId != null ? UserId:"")
                    .build()
            );





            /*//userType
            getDefaultTracker().send(new HitBuilders.ScreenViewBuilder()
                    .setCustomDimension(3,ActivationStatus()? "premiumUser":"normalUser")
                    .build()
            );





            //applicationMode
            getDefaultTracker().send(new HitBuilders.ScreenViewBuilder()
                    .setCustomDimension(4,BaseActivity.Application_Mode+"")
                    .build()
            );*/

        }
        catch (Exception e){

        }

    }
}
