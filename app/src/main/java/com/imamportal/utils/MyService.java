package com.imamportal.utils;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.imamportal.model.NotificationResponse;
import com.imamportal.model.QuizeQuistionResponse;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nanosoft-Android on 6/3/2018.
 */

public class MyService extends Service {


    Context context;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        context =this;
    }

    @Override
    public void onStart(Intent intent, int startId) {

        if(PersistentUser.isLogged(context)){
            getALlnotification();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Servics Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }



    private void getALlnotification() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<NotificationResponse> userCall = api.getNotification("Bearer "+PersistData.getStringData(context,
                AppConstant.loginToken));
        userCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {


                NotificationResponse notificationResponse = new NotificationResponse();
                notificationResponse = response.body();

                if(notificationResponse!=null){

                    AppConstant.notificationResponse = notificationResponse;
                    Log.e("notificationResponse",""+notificationResponse.getComment_notification_count());



                }


            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

            }
        });


    }

}