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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.imamportal.Adapter.KitabAdapter;
import com.imamportal.Adapter.KitabListAdapter;
import com.imamportal.Adapter.ProfileListAdapter;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.CommonPostResponse;
import com.imamportal.model.PhotoModel;
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

public class KitabActivity extends AppCompatActivity implements SwipyRefreshLayout.OnRefreshListener{

    Context context;
    private ImageView imgBack;
    private RecyclerView recyclProfielist;
    private TextView tvTotalBani,tvName;
    CommonPostResponse commonPostResponse = new CommonPostResponse();
    List<AllBlogpostModel> listSantirbani = new ArrayList<>();
    String api;

    SwipyRefreshLayout swiperefresh;
    String currentPage;
    private ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitab_list);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initUi();

    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        tvName = (TextView) findViewById(R.id.tvName);

        tvName.setText(AppConstant.activitiname);

        tvTotalBani = (TextView) findViewById(R.id.tvTotalBani);

        progressbar = (ProgressBar)findViewById(R.id.progressbar);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        swiperefresh = (SwipyRefreshLayout)findViewById(R.id.swipyrefreshlayout);
        swiperefresh.setOnRefreshListener(this);
        swiperefresh.setRefreshing(true);

        recyclProfielist = (RecyclerView) findViewById(R.id.recyclProfielist);

        if(AppConstant.activitiname.equalsIgnoreCase("হিফজুল কোরান")){
            api = "api/hifjul-quran";
            getPhotos("api/hifjul-quran");
        }

        if(AppConstant.activitiname.equalsIgnoreCase("আল-কোরানের সরল অনুবাদ")){
            api = "api/al-quran-translate";
            getPhotos("api/al-quran-translate");
        }

        if(AppConstant.activitiname.equalsIgnoreCase("উলুমুল কোরান")){
            getPhotos("api/ulumul-quran");
        }

        if(AppConstant.activitiname.equalsIgnoreCase("তাফসীরুল কোরান")){
            api = "api/tafsirul-quran";
            getPhotos("api/tafsirul-quran");
        }

        if(AppConstant.activitiname.equalsIgnoreCase("জামে তিরমিযী")){
            api = "api/jame-tirjimi";
            getPhotos("api/jame-tirjimi");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("বোখারী শরীফ")){
            api = "api/bokhari-sharif";
            getPhotos("api/bokhari-sharif");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("মুসলিম শরীফ")){
            api = "api/muslim-sharif";
            getPhotos("api/muslim-sharif");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("সুনানে আবু দাউদ")){
            api = "api/sunane-abu-dayut";
            getPhotos("api/sunane-abu-dayut");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("সুনানুন নাসায়ী")){
            api = "api/sunanun-nasay";
            getPhotos("api/sunanun-nasay");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("সুনানে ইবনে মাযা")){
            api = "api/sunane-ebne-majha";
            getPhotos("api/sunane-ebne-majha");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("অন্যান্য")){
            api = "api/otherkitab";
            getPhotos("api/otherkitab");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("ফিকাহর গ্রন্থাবলী")){
            api = "api/fikahr-books";
            getPhotos("api/fikahr-books");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("ইসলামের ইতিহাসের গ্রন্থাবলী")){
            api = "api/islamic-economic_books";
            getPhotos("api/islamic-economic_books");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("জীবনী")){
            api = "api/biography";
            getPhotos("api/biography");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("ইসলামী গল্প")){
            api = "api/islami-story";
            getPhotos("api/islami-story");
        }


        if(AppConstant.activitiname.equalsIgnoreCase("ইসলামী দর্শন")){
            api = "api/islami-philosophy";
            getPhotos("api/islami-philosophy");
        }



    }

    private void getPhotos(String url) {

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
                swiperefresh.setRefreshing(false);
                progressbar.setVisibility(View.GONE);
                commonPostResponse = response.body();
                if(commonPostResponse!=null){
                    currentPage = commonPostResponse.getCurrent_page();
                    listSantirbani = commonPostResponse.getData();
                    if(listSantirbani.size()>0){
                        KitabListAdapter questionAnsAdapter = new KitabListAdapter(listSantirbani,context);
                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                                LinearLayoutManager.VERTICAL, false);
                        recyclProfielist.setLayoutManager(new GridLayoutManager(context, 2));
                        recyclProfielist.setAdapter(questionAnsAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonPostResponse> call, Throwable t) {
                swiperefresh.setRefreshing(false);
                progressbar.setVisibility(View.GONE);
            }
        });


    }


    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if(currentPage!=null){
            int pageNo = Integer.parseInt(currentPage)+1;
            getPhotos(api+"?page="+pageNo);
        }
    }
}
