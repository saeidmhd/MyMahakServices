package ir.mahaksoft.mymahakservices.remote;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by admin1 on 4/8/17.
 */

    public class RequestAsync extends AsyncTask<String, String, String>{

    private static String App_Sign = "05b14e27-f2cd-4329-8269-cbc62b182e78";
    private static String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static String SOAP_ADDRESS = "http://login.mahaksoft.com/loginservices.asmx";
    private static String SOAP_ACTION = "http://tempuri.org/ValidateUser";
    private static String OPERATION_NAME = "ValidateUser";

    String strResponse = "";
    String jsonString;


    public RequestAsync(String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    protected String doInBackground(String... params) {

        //Create request
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

        //Property which holds input parameters
        request.addProperty("AppSign", App_Sign);
        request.addProperty("jsonString", jsonString);

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

            return "exception";
        }
    }

}




