package ir.mahaksoft.mymahakservices;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
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

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private Tracker mTracker;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";


    public static List<UserInfo> mUserInfo ;
    public static List<PackInfo> mPackageInfo ;


    private static TextView mTextView,pkg_textView;
    Snackbar mSnackbar;
    private int ConnectedConter;

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


        // Manually checking internet connection
        checkConnection();


    }


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    public void ExecuteLoginTask(){
        new MRequestAsync().execute();
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
            ExecuteLoginTask();
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



    //.AsyncTask<Params, Progress, Result>

    public class MRequestAsync extends AsyncTask<String, Integer, String> {

        private  String App_Sign = "05b14e27-f2cd-4329-8269-cbc62b182e78";
        private  String js = "[{\"Username\":\"ajdari.j@chmail.com\",\"Password\":\"252579\"}]";
        private  String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
        private  String SOAP_ADDRESS = "http://login.mahaksoft.com/loginservices.asmx";
        private  String SOAP_ACTION = "http://tempuri.org/ValidateUser";
        private  String OPERATION_NAME = "ValidateUser";

        String strResponse = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create ProgressBar
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set your ProgressBar Title
            mProgressDialog.setTitle("مرکز خدمات محک");
            // Set your ProgressBar Message
            mProgressDialog.setMessage("در حال بروز رسانی اطلاعات");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // Show ProgressBar
            mProgressDialog.setCancelable(false);
            //  mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... jsonString) {

            //Create request
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

            //Property which holds input parameters
            request.addProperty("AppSign", App_Sign);
            request.addProperty("jsonString", js);

            //Create envelope
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            //Create HTTP call object
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

            try{
                httpTransport.call(SOAP_ACTION, envelope);

                //Get the response
                Object response = envelope.getResponse();
                strResponse = response.toString();
                return strResponse;


            }
            catch (Exception exception){

                return exception.toString();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Update the ProgressBar
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            //mTextView.setText(s);
            UserInfoModel userInfoModel;

            Gson gson = new Gson();

            try {

                userInfoModel = gson.fromJson(s,UserInfoModel.class);
                mPackageInfo = userInfoModel.getPackInfo();
                mUserInfo = userInfoModel.getUserInfo();

                mTextView.setText(mUserInfo.get(0).getFirstName() + " " + mUserInfo.get(0).getLastName());
                pkg_textView.setText(mPackageInfo.get(0).getProductType());
                ConnectedConter = 1;


            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
            }finally {

                mProgressDialog.dismiss();
                mSnackbar.dismiss();
            }




            Log.e("ResultError",s);
            super.onPostExecute(s);

        }
    }







}
