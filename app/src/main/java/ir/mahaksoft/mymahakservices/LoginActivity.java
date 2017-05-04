package ir.mahaksoft.mymahakservices;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.mahaksoft.mymahakservices.Receiver.SmsBroadcastReceiver;
import ir.mahaksoft.mymahakservices.data.model.UserInfoModel;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    static String strResponse = "";

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView forget_password;
    private static LoginActivity activity;

    public static LoginActivity instance() {
        return activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        // Set up the login form.
        forget_password = (TextView) findViewById(R.id.forget_password);
        Typeface tf1 = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile.ttf");
        forget_password.setTypeface(tf1, Typeface.NORMAL);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        Typeface tf = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile.ttf");
        mEmailView.setTypeface(tf, Typeface.NORMAL);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Typeface tf2 = Typeface.createFromAsset(getAssets(),"font/IRANSansMobile.ttf");
        mEmailSignInButton.setTypeface(tf2, Typeface.BOLD);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);




    }

    @Override
    public void onStart() {
        super.onStart();
        activity = this;
    }

    public void updatePass(final String newSms) {
        mPasswordView.setText(newSms);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } /*else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    public void Retrive_pass(View view) {



        String value = mEmailView.getText().toString();
        Intent intent = new Intent(getApplicationContext(), forget_password_act.class);
        intent.putExtra("sample_name", value);
        startActivity(intent);


    }



    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, String> {



        private final String mEmail;
        private final String mPassword;
        private final String js2;


        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            js2 = "[{\"Username\":\"" + mEmail + "\",\"Password\":\"" + mPassword + "\"}]";

        }

        private  String App_Sign = "05b14e27-f2cd-4329-8269-cbc62b182e78";
        private  String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
        private  String SOAP_ADDRESS = "http://login.mahaksoft.com/loginservices.asmx";
        private  String SOAP_ACTION = "http://tempuri.org/ValidateUser";
        private  String OPERATION_NAME = "ValidateUser";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create ProgressBar
            mProgressDialog = new ProgressDialog(LoginActivity.this);
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

            mAuthTask = null;
            //showProgress(false);


            String result;
            UserInfoModel userInfoModel;

            Gson gson = new Gson();

            try {

                userInfoModel = gson.fromJson(s,UserInfoModel.class);
                result = userInfoModel.getResult();

                if (result.equals("True")){

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                }
                else{

                    Toast.makeText(LoginActivity.this, "کاربری با این مشخصات ثبت نشده است", Toast.LENGTH_SHORT).show();
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }



            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "مشکل سرور - دوبار تلاش کنید", Toast.LENGTH_SHORT).show();
            }
            finally {
                mProgressDialog.dismiss();
            }

            Log.e("ResultError",s);


        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }
}