package com.imamportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imamportal.Adapter.AllCommonPostAdapter;
import com.imamportal.Adapter.ProfileListAdapter;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.JobPortalModel;
import com.imamportal.model.SantirbaniInfo;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DokkotarGolpoActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private RecyclerView recyclProfielist;
    private TextView tvTotalBani,tvName;
    List<AllBlogpostModel> listDokkotagolpo = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dokkotargolpo);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initUi();
        getJobPost();
    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        tvName = (TextView) findViewById(R.id.tvName);

        //tvName.setText(AppConstant.activitiname);

        tvTotalBani = (TextView) findViewById(R.id.tvTotalBani);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclProfielist = (RecyclerView) findViewById(R.id.recyclProfielist);

//        List<SantirbaniInfo> questionAnsInfoList = new ArrayList<>();
//        SantirbaniInfo questionAnsInfo = new SantirbaniInfo();
//        for (int i = 0; i < 10; i++) {
//            questionAnsInfoList.add(i, questionAnsInfo);
//        }

//        int size = listDokkotagolpo.size();
//        tvTotalBani.setText("সর্বমোট "+AppConstant.activitiname+size+" টি");
//
//        AllCommonPostAdapter questionAnsAdapter = new AllCommonPostAdapter(listDokkotagolpo,context);
//        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.VERTICAL, false);
//        recyclProfielist.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclProfielist.setAdapter(questionAnsAdapter);

    }


    private void getJobPost() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<AllBlogpostModel>> userCall = api.skill();
        userCall.enqueue(new Callback<List<AllBlogpostModel>>() {
            @Override
            public void onResponse(Call<List<AllBlogpostModel>> call, Response<List<AllBlogpostModel>> response) {
                pd.dismiss();

                listDokkotagolpo = response.body();
                if(listDokkotagolpo.size()>0){
                    int size = listDokkotagolpo.size();
                    tvTotalBani.setText("সর্বমোট "+AppConstant.activitiname+size+" টি");
                     AllCommonPostAdapter questionAnsAdapter = new AllCommonPostAdapter(listDokkotagolpo,context);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                            LinearLayoutManager.VERTICAL, false);
                    recyclProfielist.setLayoutManager(horizontalLayoutManager);
                    recyclProfielist.setAdapter(questionAnsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<AllBlogpostModel>> call, Throwable t) {
                pd.dismiss();
            }
        });


    }

}
