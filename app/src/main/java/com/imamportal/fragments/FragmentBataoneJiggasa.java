package com.imamportal.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.Adapter.AudioAdapter;
import com.imamportal.R;
import com.imamportal.model.AudioModel;
import com.imamportal.model.SignUpResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentBataoneJiggasa extends Fragment {


    Context context;
    private EditText etNmae,etMobile,etEmail,etSubject,etAsk;

    private AppCompatButton btnSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_apnar_giggasa, container, false);

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

            etNmae = (EditText) getView().findViewById(R.id.etNmae);
            etMobile = (EditText) getView().findViewById(R.id.etMobile);
            etEmail = (EditText) getView().findViewById(R.id.etEmail);
            etSubject = (EditText) getView().findViewById(R.id.etSubject);
            etAsk = (EditText) getView().findViewById(R.id.etAsk);
            btnSave = (AppCompatButton) getView().findViewById(R.id.btnSave);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = etNmae.getText().toString(),mobile = etMobile.getText().toString(),
                            email = etEmail.getText().toString(),subject = etSubject.getText().toString(),
                            ask = etAsk.getText().toString();

                    if(TextUtils.isEmpty(name)){
                        Toast.makeText(context, "আপনার নাম লিখুন", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(mobile)){
                        Toast.makeText(context, "আপনার মোবাইল নম্বর  লিখুন", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(email)){
                        Toast.makeText(context, "আপনার ইমেইল  লিখুন", Toast.LENGTH_SHORT).show();
                    }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        Toast.makeText(context, "আপনার ইমেইল ভুল", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(subject)){
                        Toast.makeText(context, "বিষয় লিখুন", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(ask)){
                        Toast.makeText(context, "প্রশ্ন লিখুন", Toast.LENGTH_SHORT).show();
                    }else {
                        JSONObject jsondata = new JSONObject();
                        try {
                            jsondata.put("name",name);
                            jsondata.put("email",email);
                            jsondata.put("mobile_number",mobile);
                            jsondata.put("subject",subject);
                            jsondata.put("question",ask);
                            jsondata.put("answer","");
                            jsondata.put("type","0");
                            jsondata.put("status","0");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("jsondata",""+jsondata);
                        jiggasaPost(jsondata.toString());
                    }


                }
            });
    }


    private void jiggasaPost(String data) {

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
        Call<SignUpResponse> userCall = api.jiggasa(data);
        userCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                pd.dismiss();

                SignUpResponse responsData = response.body();

                if(responsData!=null){
                    if(responsData.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(context, ""+responsData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                if(responsData!=null){
                    if(responsData.getStatus().equalsIgnoreCase("error")){
                        if(responsData.getData().getEmail()!=null){
                            for (int i = 0; i <responsData.getData().getEmail().size() ; i++) {
                                Toast.makeText(context, ""+responsData.getData().getEmail().get(i), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

                pd.dismiss();
            }
        });


    }

}
