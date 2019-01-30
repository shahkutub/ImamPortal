package com.imamportal.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.R;
import com.imamportal.model.SeraContentData;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.NetInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentSeraContent extends Fragment {

    Context context;
    String name;
    TextView tvName;

//    private String title;
//    private int page;
List<SeraContentData> listSeraContentData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seraContent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_seracontent,
                container, false);
        tvName = (TextView)view.findViewById(R.id.tvName);

       // return inflater.inflate(R.layout.fragment_seracontent, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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


    }


    private void seraContent() {


//        if(!NetInfo.isOnline(context)){
//            AlertMessage.showMessage(context,"Alert!","No internet connection!");
//        }

//        final ProgressDialog pd = new ProgressDialog(context);
//        pd.setMessage("Loading....");
//        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pd.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<SeraContentData>> userCall = api.seraContent();
        userCall.enqueue(new Callback<List<SeraContentData>>() {
            @Override
            public void onResponse(Call<List<SeraContentData>> call, Response<List<SeraContentData>> response) {
               // pd.dismiss();

                listSeraContentData = response.body();

                if(listSeraContentData!=null){
                    name= listSeraContentData.get(0).getUser_details().getName();
                    //Toast.makeText(context, ""+listSeraContentData.get(0).getUser_details().getName(), Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(context, ""+listSeraContentData.get(0).getUser_details().getName(), Toast.LENGTH_SHORT).show();
                tvName.setText(name);

            }

            @Override
            public void onFailure(Call<List<SeraContentData>> call, Throwable t) {
                //pd.dismiss();
            }
        });


    }

}
