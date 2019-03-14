package com.imamportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.model.SignUpResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PersistData;
import com.imamportal.utils.PersistentUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Context context;
    private String name,password;
    private EditText input_name,input_password;
    private AppCompatButton btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        context=this;
        initUi();
    }

    private void initUi() {
       // PersistentUser.setLogin(context);
        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
         input_name = (EditText) findViewById(R.id.input_name);
         input_password = (EditText) findViewById(R.id.input_password);
        btnLogin = (AppCompatButton)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = input_name.getText().toString();
                password = input_password.getText().toString();
                
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(context, "আপনার ইউসার নাম লিখুন", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(context, "আপনার ইউসার পাসওয়ার্ড লিখুন", Toast.LENGTH_SHORT).show();
                }else {
                    logIn();
                }


            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    private void logIn() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<SignUpResponse> userCall = api.login(name,password);
        userCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                pd.dismiss();

                SignUpResponse responsData = response.body();

                if(responsData!=null){
                   if(responsData.getToken()!=null){
                       PersistData.setStringData(context, AppConstant.loginToken,responsData.getToken());
                       PersistData.setStringData(context, AppConstant.loginUserid,responsData.getUser_data().getId());
                       Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show();
                       PersistentUser.setLogin(context);
                       finish();
                   }
                }else {
                    Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

                pd.dismiss();
            }
        });


    }
}
