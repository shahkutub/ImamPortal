package com.imamportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imamportal.model.AllLocationResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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


        ImageView imgGovt = (ImageView)findViewById(R.id.imgGovt);
        ImageView imgA2i = (ImageView)findViewById(R.id.imgA2i);
        ImageView imgCabinet = (ImageView)findViewById(R.id.imgCabinet);
        ImageView imgUndp = (ImageView)findViewById(R.id.imgUndp);
        ImageView imgUsaid = (ImageView)findViewById(R.id.imgUsaid);

        imgGovt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://bangladesh.gov.bd/index.php")));
            }
        });
        imgA2i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://a2i.gov.bd/")));
            }
        });
        imgCabinet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://cabinet.gov.bd/")));
            }
        });
        imgUndp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bd.undp.org/")));
            }
        });
        imgUsaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.usaid.gov/bd")));
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
        Call<AllLocationResponse> userCall = api.get_all_location_data();
        userCall.enqueue(new Callback<AllLocationResponse>() {
            @Override
            public void onResponse(Call<AllLocationResponse> call, Response<AllLocationResponse> response) {
                pd.dismiss();

                AllLocationResponse allData = response.body();

                if(allData!=null){

                    AppConstant.allData = allData;
                    Log.e("allData",""+allData.getResult().size());

                }

            }

            @Override
            public void onFailure(Call<AllLocationResponse> call, Throwable t) {


                pd.dismiss();
            }
        });


    }

}
