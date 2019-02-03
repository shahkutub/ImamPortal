package com.imamportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imamportal.Adapter.AllCommonPostAdapter;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.AlquranAlhadits;
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

public class AllCommonPostActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private RecyclerView recyclSantirBani;
    private TextView tvTotalBani,tvName;
    List<AllBlogpostModel> listAlblog = new ArrayList<>();

    List<AllBlogpostModel> listShowData = new ArrayList<>();
//    List<AllBlogpostModel> listSamprotik = new ArrayList<>();
//    List<AllBlogpostModel> listSisukisur = new ArrayList<>();
//    List<AllBlogpostModel> listNariKornar = new ArrayList<>();
//    List<AllBlogpostModel> listParibarikAin = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_common_post);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initUi();
        getblog_post();
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

        recyclSantirBani = (RecyclerView) findViewById(R.id.recyclSantirBani);






    }


    private void getblog_post() {

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
        Call<List<AllBlogpostModel>> userCall = api.blog_post();
        userCall.enqueue(new Callback<List<AllBlogpostModel>>() {
            @Override
            public void onResponse(Call<List<AllBlogpostModel>> call, Response<List<AllBlogpostModel>> response) {
                pd.dismiss();

                listAlblog = response.body();

                Log.e("listAlquranAlhadit",""+listAlblog.size());

                if(listAlblog.size()>0){



                    //পারিবারিক আইন
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("পারিবারিক আইন")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("39")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }
                        }
                    }


                    //পারিবারিক আইন
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("ফৌজদারি আইন")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("40")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }



                    //ইসলামিক অর্থনীত
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("ইসলামিক অর্থনীত")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("32")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }


                    //পারিবারিক আইন
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("দাওয়াত")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("33")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }


                    //পারিবারিক আইন
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("পরিবার ও সমাজ")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("34")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }


                    //ঈমান ও আকীদাহ
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("ঈমান ও আকীদাহ")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("35")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }


                    //চরিত্র
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("চরিত্র")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("35")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }


                    //kitab
                    // হিফজুল কোরান

                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("হিফজুল কোরান")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("49")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }

                    // আল-কোরানের সরল অনুবাদ

                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("আল-কোরানের সরল অনুবাদ")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("50")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }



                    //উলুমুল কোরান
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("উলুমুল কোরান")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("51")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }



                    //তাফসীরুল কোরান
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("তাফসীরুল কোরান")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("52")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }


                    //জামে তিরমিযী
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("জামে তিরমিযী")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("54")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }


                    //বোখারী শরীফ
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("বোখারী শরীফ")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("55")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }


                    //মুসলিম শরীফ
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("মুসলিম শরীফ")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("56")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }



                    //সুনানে আবু দাউদ
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("সুনানে আবু দাউদ")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("57")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }



                    //সুনানুন নাসায়ী
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("সুনানুন নাসায়ী")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("58")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }



                    //সুনানে ইবনে মাযা
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("সুনানে ইবনে মাযা")){
                            if(listAlblog.get(i).getCategory_id()!=null){
                                if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("59")){
                                    listShowData.add(listAlblog.get(i));
                                }
                            }

                        }
                    }



                    //শান্তির বাণী
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("শান্তির বাণী")){
                            if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("3")){
                                listShowData.add(listAlblog.get(i));
                            }
                        }
                    }


                    //samprotik
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("সাম্প্রতিক বিষয়াদি")){
                            if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("2")){
                                listShowData.add(listAlblog.get(i));
                            }
                        }
                    }



               // শিশু কিশোর কর্নার "category_id": 2,
                    //samprotik
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("শিশু কিশোর কর্নার")){
                            if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("6")){
                                listShowData.add(listAlblog.get(i));
                            }
                        }
                    }




               //নারী কর্নার "category_id": 7,
                    for (int i = 0; i < listAlblog.size(); i++) {
                        if(AppConstant.bolgpostName.equalsIgnoreCase("নারী কর্নার")){
                            if(listAlblog.get(i).getCategory_id().equalsIgnoreCase("7")){
                                listShowData.add(listAlblog.get(i));
                            }
                        }
                    }


                    if(listShowData.size()>0){
                        int size = listShowData.size();
                        tvTotalBani.setText("সর্বমোট "+AppConstant.activitiname+size+" টি");
                        AllCommonPostAdapter questionAnsAdapter = new AllCommonPostAdapter(listShowData,context);
                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                                LinearLayoutManager.VERTICAL, false);
                        recyclSantirBani.setLayoutManager(horizontalLayoutManager);
                        recyclSantirBani.setAdapter(questionAnsAdapter);
                    }


                }

            }

            @Override
            public void onFailure(Call<List<AllBlogpostModel>> call, Throwable t) {
                pd.dismiss();
            }
        });


    }

}
