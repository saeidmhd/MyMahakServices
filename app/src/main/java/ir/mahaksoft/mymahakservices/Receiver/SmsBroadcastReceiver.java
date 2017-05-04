package ir.mahaksoft.mymahakservices.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import ir.mahaksoft.mymahakservices.LoginActivity;
import ir.mahaksoft.mymahakservices.data.model.Message;

/**
 * Created by Saeid.mhd-at-Gmail.com on 5/3/17.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {
    Message mMessage;
    String strMessage = "";
    String strAddress = "";


    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages = null;


        if (myBundle != null) {
            Object[] pdus = (Object[]) myBundle.get("pdus");

            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = myBundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }


                strAddress = messages[i].getOriginatingAddress();
                strMessage += messages[i].getMessageBody().toString();

            }



           // Toast.makeText(context, extractCode(), Toast.LENGTH_SHORT).show();
            Log.e("ResultError",extractCode());

            LoginActivity inst = LoginActivity.instance();
            inst.updatePass(extractCode());


        }

    }


    public String extractCode(){
        String code = "";

        if (strAddress.equals("+9830008793")){

            String myString = strMessage;
            String SearchStr = " با موفقيت به ";
            int index = myString.indexOf(SearchStr);
            code = myString.substring(index + 14 , myString.length()).replaceAll("\\D+","");

        }
        return code;
    }


}
