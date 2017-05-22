package ir.mahaksoft.mymahakservices;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.List;

import ir.mahaksoft.mymahakservices.Receiver.ConnectivityReceiver;
import ir.mahaksoft.mymahakservices.data.model.PackInfo;
import ir.mahaksoft.mymahakservices.data.model.UserInfo;
import ir.mahaksoft.mymahakservices.data.model.UserInfoModel;

public class MainActivity extends BasicActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private Tracker mTracker;


    public static List<UserInfo> mUserInfo ;
    public static List<PackInfo> mPackageInfo ;



    private static TextView mTextView,pkg_textView;
    Snackbar mSnackbar;
    private int ConnectedConter;

    private String strResponse = LoginActivity.strResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the shared Tracker instance.
        BaseApplication application = (BaseApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("MainActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());



        mTextView = (TextView) findViewById(R.id.textView);
        pkg_textView = (TextView) findViewById(R.id.pkg_textView);
        ConnectedConter = 0;

        String result;
        UserInfoModel userInfoModel;

        Gson gson = new Gson();

        try {

            userInfoModel = gson.fromJson(strResponse,UserInfoModel.class);
            result = userInfoModel.getResult();


            if (result.equals("True")){

                mPackageInfo = userInfoModel.getPackInfo();
                mUserInfo = userInfoModel.getUserInfo();


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                Log.e("dasdasddasdasd",mUserInfo.get(0).getUserId());
                String user = mUserInfo.get(0).getUserId();
                preferences.edit().putString(BasicActivity.__Key_UserId,mUserInfo.get(0).getUserId()).commit();


                mTextView.setText(mUserInfo.get(0).getFirstName() + " " + mUserInfo.get(0).getLastName() +
                        mUserInfo.get(0).getEmail() + " "+
                        mUserInfo.get(0).getMobile());
                pkg_textView.setText(mPackageInfo.get(0).getAppName()+ " " + mPackageInfo.get(0).getVersion());
                ConnectedConter = 1;


            }
            else{

                Toast.makeText(MainActivity.this, "کاربری با این مشخصات ثبت نشده است", Toast.LENGTH_SHORT).show();
            }



        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
        }


        // Manually checking internet connection
        checkConnection();


    }


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }



    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message="";
        if (isConnected && ConnectedConter == 0) {
            message = " در حال به روز رسانی اطلاعات ......";
            mSnackbar = Snackbar
                    .make(findViewById(R.id.activity_main), message, Snackbar.LENGTH_SHORT);
            //ExecuteLoginTask();
            mSnackbar.show();

        } else if(isConnected && ConnectedConter == 1) {
            message = "اطلاعات به روز است ! ";
            mSnackbar = Snackbar
                    .make(findViewById(R.id.activity_main), message, Snackbar.LENGTH_SHORT);
            mSnackbar.show();

        }else if(!isConnected){
            message = "دستگاه خود را به اینترنت متصل کنید";
            mSnackbar = Snackbar
                    .make(findViewById(R.id.activity_main), message, Snackbar.LENGTH_INDEFINITE);
            mSnackbar.show();

        }



    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        BaseApplication.getInstance().setConnectivityListener(this);
    }


}
