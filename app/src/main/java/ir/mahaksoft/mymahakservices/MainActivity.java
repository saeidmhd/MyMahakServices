package ir.mahaksoft.mymahakservices;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.List;
import ir.mahaksoft.mymahakservices.data.model.UserInfo;
import ir.mahaksoft.mymahakservices.data.model.UserInfoModel;

public class MainActivity extends AppCompatActivity {

    private Tracker mTracker;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";


    public static List<UserInfo> mUserInfo ;
    String jsonString;


    private static TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the shared Tracker instance.
        BaseApplication application = (BaseApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("MainActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        jsonString = "[{\"Username\":\"ajdari.j@chmail.com\",\"Password\":\"252579\"}]";

        mTextView = (TextView) findViewById(R.id.textView);

        if(checkPlayServices()){
            if(CheckNetworkAvailability()&&isOnline()){
                ExecuteLoginTask();
            }
            else{
               // Toast.makeText(this, "no internet", Toast.LENGTH_SHORT).show();

                showDialog(MainActivity.this);
               /* AlertDialog.Builder builder =
                        new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("عدم اتصال به اینترنت");
                builder.setMessage("اتصال شما به اینترنت برقرار نیست.برای استفاده از سرویس مرکز خدمات محک به اینترنت متصل شوید.");
                builder.setPositiveButton("بلی", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();*/

            }
        }
        else{
           // Toast.makeText(this, "no play services", Toast.LENGTH_SHORT).show();
            final String appPackageName = "com.google.android.gms"; // getPackageName() from Context or Activity object
            try {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="
                        + appPackageName)));
            }
        }




    }

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_internet_dialog);

      /*  TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/

        dialog.show();

    }

    public void ExecuteLoginTask(){
        new MRequestAsync().execute(jsonString,jsonString);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public boolean CheckNetworkAvailability(){

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }


    public void btnClick(View view) {

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        Intent mIntent = new Intent(MainActivity.this,DetailActivity.class);
        startActivity(mIntent);

    }

    //.AsyncTask<Params, Progress, Result>

    public class MRequestAsync extends AsyncTask<String, Integer, String> {

        private  String App_Sign = "";
        private  String js = "";
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
            mProgressDialog.setMessage("در حال دریافت اطلاعات");
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

            if (s!=null){
                userInfoModel = gson.fromJson(s,UserInfoModel.class);
                mUserInfo = userInfoModel.getUserInfo();
                mUserInfo.get(0).getFirstName();

                mTextView.setText(mUserInfo.get(0).getFirstName());

                mProgressDialog.dismiss();
            }
            else{

                mTextView.setText("no data");
                mProgressDialog.dismiss();

            }



            Log.e("ResultError",s);
            super.onPostExecute(s);

        }
    }





}
