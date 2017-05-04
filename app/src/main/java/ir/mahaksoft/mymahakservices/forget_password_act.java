package ir.mahaksoft.mymahakservices;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.mahaksoft.mymahakservices.data.model.UserRetriveInfo;

public class forget_password_act extends AppCompatActivity {

    // UI references.
    TextView textMahakID,textPackNum,textMIDValue,textPackNumValue,result_text;
    Button send_pass,cancel;
    static String strResponse = "";

    private AutoCompleteTextView forget_email;

    private RetrivePassTask mRetriveTask = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forget_pass);

        send_pass = (Button) findViewById(R.id.send_pass);
        cancel = (Button) findViewById(R.id.cancel);

        textMahakID = (TextView) findViewById(R.id.textMahakID);
        textPackNum = (TextView) findViewById(R.id.textPackNum);
        textMIDValue = (TextView) findViewById(R.id.textMIDValue);
        textPackNumValue = (TextView) findViewById(R.id.textPackNumValue);
        result_text = (TextView) findViewById(R.id.result_text);
        forget_email = (AutoCompleteTextView) findViewById(R.id.forget_email);

        Typeface tf1 = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile.ttf");
        textMahakID.setTypeface(tf1, Typeface.NORMAL);
        Typeface tf2 = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile.ttf");
        textPackNum.setTypeface(tf2, Typeface.NORMAL);
        Typeface tf3 = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile.ttf");
        textMIDValue.setTypeface(tf3, Typeface.NORMAL);
        Typeface tf4 = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile(FaNum).ttf");
        textPackNumValue.setTypeface(tf4, Typeface.NORMAL);
        Typeface tf5 = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile.ttf");
        forget_email.setTypeface(tf5, Typeface.NORMAL);
        Typeface tf6 = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile.ttf");
        result_text.setTypeface(tf6, Typeface.NORMAL);
        Typeface tf7 = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile.ttf");
        send_pass.setTypeface(tf7, Typeface.NORMAL);
        Typeface tf8 = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile.ttf");
        cancel.setTypeface(tf8, Typeface.NORMAL);

        String value = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            value = bundle.getString("sample_name");
        }

        forget_email.setText(value);



    }

    public void send_pass(View view) {

        attemptLogin();

    }

    private void attemptLogin() {
        if (mRetriveTask != null) {
            return;
        }

        // Reset errors.
        forget_email.setError(null);


        // Store values at the time of the login attempt.
        String email = forget_email.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            forget_email.setError(getString(R.string.error_field_required));
            focusView = forget_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mRetriveTask = new RetrivePassTask(email);
            mRetriveTask.execute((Void) null);
        }
    }

    public void cancel(View view) {

        this.onBackPressed();
        finish();
    }

    public class RetrivePassTask extends AsyncTask<Void, Void, String> {


        private final String js2;
        private final String mEmail;

        RetrivePassTask(String email) {

            mEmail = email;
            js2 = "[{\"UserName\":\"" + mEmail + "\"}]";

        }


//        RetrivePassTask() {
//            js2 = "[{\"UserName\":\"saeid.mhd@gmail.com\"}]";
//
//        }

        private  String App_Sign = "05b14e27-f2cd-4329-8269-cbc62b182e78";
        private  String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
        private  String SOAP_ADDRESS = "http://login.mahaksoft.com/loginservices.asmx";
        private  String SOAP_ACTION = "http://tempuri.org/ForgetPassword";
        private  String OPERATION_NAME = "ForgetPassword";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create ProgressBar
            mProgressDialog = new ProgressDialog(forget_password_act.this);
            //  ProgressBar Title
            mProgressDialog.setTitle("مرکز خدمات محک");
            //  ProgressBar Message
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
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            //Create request
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

            //Property which holds input parameters
            request.addProperty("AppSign", App_Sign);
            request.addProperty("jsonString", js2);

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
        protected void onPostExecute(String s) {

            mProgressDialog.dismiss();
            Log.e("ResultError",s);

            mRetriveTask = null;
            //showProgress(false);


            String result;
            String msg;
            UserRetriveInfo userRetriveInfo;

            Gson gson = new Gson();

            try {

                userRetriveInfo = gson.fromJson(s,UserRetriveInfo.class);
                result = userRetriveInfo.getResult();
                msg = userRetriveInfo.getMsg();

                if (result.equals("True")){
                    Toast.makeText(forget_password_act.this, msg, Toast.LENGTH_SHORT).show();
                    forget_password_act.this.onBackPressed();
                    finish();


                }
                else{

                    Toast.makeText(forget_password_act.this, "کاربری با این مشخصات ثبت نشده است", Toast.LENGTH_SHORT).show();
                    /*mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();*/
                }



            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                Toast.makeText(forget_password_act.this, "مشکل سرور - دوبار تلاش کنید", Toast.LENGTH_SHORT).show();
            }
            finally {
                mProgressDialog.dismiss();
            }

            Log.e("ResultError",s);

        }

    }


}
