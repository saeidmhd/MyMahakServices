package ir.mahaksoft.mymahakservices.api;

import ir.mahaksoft.mymahakservices.api.model.request.RequestBody;
import ir.mahaksoft.mymahakservices.api.model.request.RequestEnvelope;
import ir.mahaksoft.mymahakservices.api.model.response.ResponseEnvelope;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by admin1 on 4/9/17.
 */

public interface RemoteApi {
    String Base_Url = "http://login.mahaksoft.com/";

    @Headers({
            "Content-Type: text/xml",
            "Accept-Charset: utf-8"
    })
    @POST("loginservices.asmx")
    Call<ResponseEnvelope> requestStateInfo(@Body RequestBody body);

    /*class Factory {
        private static RemoteApi service;

        public static RemoteApi getInstances(){
            if(service == null){

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Base_Url)
                        .build();

                service = retrofit.create(RemoteApi.class);
                return service;
            }else {

                return service;

            }




        }



    }*/
}
