package com.imamportal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.imamportal.Adapter.AllCommonPostAdapter;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.CommonPostResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllCommonPostActivity extends AppCompatActivity implements SwipyRefreshLayout.OnRefreshListener{

    Context context;
    private ImageView imgBack;
    private RecyclerView recyclSantirBani;
    private TextView tvTotalBani,tvName;
    CommonPostResponse commonPostResponse = new CommonPostResponse();
    List<AllBlogpostModel> listSantirbani = new ArrayList<>();
    SwipyRefreshLayout swiperefresh;
    String currentPage;
    private ProgressBar progressbar;
    String api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.santirbani);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initUi();

    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(AppConstant.activitiname);
        tvTotalBani = (TextView) findViewById(R.id.tvTotalBani);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressbar = (ProgressBar)findViewById(R.id.progressbar);
        recyclSantirBani = (RecyclerView) findViewById(R.id.recyclSantirBani);
        swiperefresh = (SwipyRefreshLayout)findViewById(R.id.swipyrefreshlayout);
        swiperefresh.setOnRefreshListener(this);
        swiperefresh.setRefreshing(true);
       // swiperefresh.setDirection(SwipyRefreshLayoutDirection.TOP);

        if(AppConstant.activitiname.equalsIgnoreCase("শান্তির বাণী")){
            //getblog_post("api/shantirbani");
            api="api/shantirbani";
        }


         if(AppConstant.activitiname.equalsIgnoreCase("পারিবারিক আইন")){
            //getblog_post("api/familylaw");
             api="api/familylaw";
        }

         if(AppConstant.activitiname.equalsIgnoreCase("ফৌজদারি আইন")){
            //getblog_post("api/fojdariLaw");
             api="api/fojdariLaw";
        }

         if(AppConstant.activitiname.equalsIgnoreCase("ইসলামিক অর্থনীত")){
           // getblog_post("api/islamic-economicbooks");
             api="api/islamic-economicbooks";
        }

         if(AppConstant.activitiname.equalsIgnoreCase("দাওয়াত")){
            //getblog_post("api/daowat");
             api="api/daowat";
        }

         if(AppConstant.activitiname.equalsIgnoreCase("পরিবার ও সমাজ")){
            //getblog_post("api/daowat");
             api="api/family-society";
        }

         if(AppConstant.activitiname.equalsIgnoreCase("ঈমান ও আকীদাহ")){
            //getblog_post("api/daowat");
             api="api/imam-akida";
        }

         if(AppConstant.activitiname.equalsIgnoreCase("চরিত্র")){
            //getblog_post("api/daowat");
             api="api/character";
        }

         if(AppConstant.activitiname.equalsIgnoreCase("হিফজুল কোরান")){
            //getblog_post("api/daowat");
             api="api/hifjul-quran";
        }


         if(AppConstant.activitiname.equalsIgnoreCase("আল-কোরানের সরল অনুবাদ")){
            //getblog_post("api/daowat");
             api="api/al-quran-translate";
        }


         if(AppConstant.activitiname.equalsIgnoreCase("উলুমুল কোরান")){
            //getblog_post("api/daowat");
             api="api/ulumul-quran";
        }


         if(AppConstant.activitiname.equalsIgnoreCase("তাফসীরুল কোরান")){
            //getblog_post("api/daowat");
             api="api/tafsirul-quran";
        }


        getblog_post(api);

    }


    private void getblog_post(String url) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }
        if(listSantirbani.size()==0){
            progressbar.setVisibility(View.VISIBLE);
        }
        swiperefresh.setRefreshing(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<CommonPostResponse> userCall = api.shantirbani(url);
        userCall.enqueue(new Callback<CommonPostResponse>() {
            @Override
            public void onResponse(Call<CommonPostResponse> call, Response<CommonPostResponse> response) {
                if(listSantirbani.size()==0){
                    progressbar.setVisibility(View.GONE);
                }

                swiperefresh.setRefreshing(false);
                commonPostResponse = response.body();

             //   Log.e("listAlquranAlhadit",""+listAlblog.size());

                if(commonPostResponse !=null){

                    currentPage = commonPostResponse.getCurrent_page();
                    if(commonPostResponse.getData().size()>0){
                        for (AllBlogpostModel post: commonPostResponse.getData()) {
                            listSantirbani.add(post);
                        }
                        int size = listSantirbani.size();
                        tvTotalBani.setText("সর্বমোট "+AppConstant.activitiname+size+" টি");

                        //Collections.reverse(listSantirbani);
                        AllCommonPostAdapter questionAnsAdapter = new AllCommonPostAdapter(listSantirbani,context);
                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                                LinearLayoutManager.VERTICAL, false);
                        recyclSantirBani.setLayoutManager(horizontalLayoutManager);
                        recyclSantirBani.setAdapter(questionAnsAdapter);

                        horizontalLayoutManager.scrollToPositionWithOffset(size-1, 20);
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonPostResponse> call, Throwable t) {
                if(listSantirbani.size()==0){
                    progressbar.setVisibility(View.GONE);
                }
                swiperefresh.setRefreshing(false);
            }
        });

    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if(currentPage!=null){
            int pageNo = Integer.parseInt(currentPage)+1;
            getblog_post(api+"?page="+pageNo);
        }
    }

}
