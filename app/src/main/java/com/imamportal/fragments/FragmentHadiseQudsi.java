package com.imamportal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.imamportal.Adapter.AllCommonPostAdapter;
import com.imamportal.Adapter.AlquranAldadithPostAdapter;
import com.imamportal.R;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.AlquranAlhadits;
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


public class FragmentHadiseQudsi extends Fragment implements SwipyRefreshLayout.OnRefreshListener{

    Context context;
    private RecyclerView recyclSantirBani;
    private TextView tvTotalBani,tvName;
    CommonPostResponse commonPostResponse = new CommonPostResponse();
    List<AllBlogpostModel> listSantirbani = new ArrayList<>();
    SwipyRefreshLayout swiperefresh;
    String currentPage;
    public static final int DISMISS_TIMEOUT = 2000;
    String api;
    private ProgressBar progressbar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_quran, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initUi();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initUi() {
//        tvName = (TextView) getView().findViewById(R.id.tvName);
//        tvName.setText(AppConstant.activitiname);

        listSantirbani.clear();
        tvTotalBani = (TextView) getView().findViewById(R.id.tvTotalBani);
        progressbar = (ProgressBar)getView().findViewById(R.id.progressbar);


        recyclSantirBani = (RecyclerView) getView().findViewById(R.id.recyclSantirBani);
        swiperefresh = (SwipyRefreshLayout)getView().findViewById(R.id.swipyrefreshlayout);
        swiperefresh.setOnRefreshListener(this);
        swiperefresh.setRefreshing(true);
        // swiperefresh.setDirection(SwipyRefreshLayoutDirection.TOP);

        if(AppConstant.activitiname.equalsIgnoreCase("কুরআন পাঠ")){
            //getblog_post("api/shantirbani");

        }

        api="api/hadith-kudsi";
//        if(AppConstant.activitiname.equalsIgnoreCase("দরসে কুরআন")){
//            //getblog_post("api/familylaw");
//            api="api/dorse-quran";
//        }
//
//        if(AppConstant.activitiname.equalsIgnoreCase("তাফসীরুল কুরআন")){
//            //getblog_post("api/fojdariLaw");
//            api="api/tafsirul-quran";
//        }
//
//        if(AppConstant.activitiname.equalsIgnoreCase("বিষয়ভিত্তিক হাদিস")){
//            // getblog_post("api/islamic-economicbooks");
//            api="api/subjective-quran";
//        }
//
//        if(AppConstant.activitiname.equalsIgnoreCase("অন্যান্য")){
//            //getblog_post("api/daowat");
//            api="api/others-quran";
//        }
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
                        tvTotalBani.setText("সর্বমোট "+size+" টি");

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
