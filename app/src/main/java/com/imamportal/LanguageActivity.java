package com.imamportal;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imamportal.model.AllDataResponse;
import com.imamportal.model.Catagories;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.LocationMgr;
import com.imamportal.utils.NetInfo;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LanguageActivity extends AppCompatActivity {
    private TextView btnBangla,btnEng,btnAr;

    private LinearLayout linBang,linEng,linAr;
    private RelativeLayout relBg;
    Context context;
    Locale myLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lng);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        btnBangla = (TextView)findViewById(R.id.btnBangla);
        btnEng = (TextView)findViewById(R.id.btnEng);
        btnAr = (TextView)findViewById(R.id.btnAr);

        linBang = (LinearLayout)findViewById(R.id.linBang);
        linEng = (LinearLayout)findViewById(R.id.linEng);
        linAr = (LinearLayout)findViewById(R.id.linAr);
        relBg = (RelativeLayout) findViewById(R.id.relBg);

        //setLocale("bn");
        relBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context,MainActivity.class));
                finish();
            }
        });




        //getAlldata();

    }


    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(context, LanguageActivity.class);
        startActivity(refresh);
    }




    private void getAlldata() {

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
        Call<AllDataResponse> userCall = api.get_all_data();
        userCall.enqueue(new Callback<AllDataResponse>() {
            @Override
            public void onResponse(Call<AllDataResponse> call, Response<AllDataResponse> response) {
                pd.dismiss();

                AllDataResponse  allData = response.body();

                if(allData!=null){

                    AppConstant.allData = allData;
                    Log.e("allData",""+allData.getResult().size());

                }

            }

            @Override
            public void onFailure(Call<AllDataResponse> call, Throwable t) {


                pd.dismiss();
            }
        });


    }

}
