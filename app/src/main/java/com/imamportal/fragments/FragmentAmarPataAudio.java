package com.imamportal.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.imamportal.R;
import com.imamportal.model.AmarpataContentResponse;
import com.imamportal.model.Catagories;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PersistData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentAmarPataAudio extends Fragment {

    Context context;
    private Spinner spinnerCatagoryAmamrPata,spinnerObosta;
    private String category_id;
    private AmarpataContentResponse amarpataContentResponse = new AmarpataContentResponse();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.amarpata_fragment, container, false);

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

        spinnerCatagoryAmamrPata = (Spinner)getView().findViewById(R.id.spinnerCatagoryAmamrPata);
        spinnerObosta = (Spinner)getView().findViewById(R.id.spinnerObosta);

        spinnerCatagoryAmamrPata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    category_id = amarpataContentResponse.getContent_categories().get(position).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getContents();
    }

    public class CustomAdapter  extends BaseAdapter implements SpinnerAdapter {

        List<Catagories> company;
        Context context;


        public CustomAdapter(Context context, List<Catagories> company) {
            this.company = company;
            this.context = context;
        }

        @Override
        public int getCount() {
            return company.size();
        }

        @Override
        public Object getItem(int position) {
            return company.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view =  View.inflate(context, R.layout.company_main, null);
            TextView textView = (TextView) view.findViewById(R.id.main);
            textView.setText(company.get(position).getName_bn());
            return textView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            View view;
            view =  View.inflate(context, R.layout.company_dropdown, null);
            final TextView textView = (TextView) view.findViewById(R.id.dropdown);
            textView.setText(company.get(position).getName_bn());

            return view;
        }
    }


    private void getContents() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + PersistData.getStringData(context,AppConstant.loginToken))
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<AmarpataContentResponse> userCall = api.mypage_Audio();
        userCall.enqueue(new Callback<AmarpataContentResponse>() {
            @Override
            public void onResponse(Call<AmarpataContentResponse> call, Response<AmarpataContentResponse> response) {
                pd.dismiss();

                amarpataContentResponse = response.body();

                if(amarpataContentResponse!=null){

                    Catagories catagories = new Catagories();
                    catagories.setName_bn("নির্বাচন করুন");
                    amarpataContentResponse.getContent_categories().add(0,catagories);

                    CustomAdapter adapterGender = new CustomAdapter(context, amarpataContentResponse.getMultimedia_categories());
                    spinnerCatagoryAmamrPata.setAdapter(adapterGender);
                }

            }

            @Override
            public void onFailure(Call<AmarpataContentResponse> call, Throwable t) {
                pd.dismiss();
            }
        });


    }
}
