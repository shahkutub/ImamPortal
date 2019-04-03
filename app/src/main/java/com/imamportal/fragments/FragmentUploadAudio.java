package com.imamportal.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.R;
import com.imamportal.model.Catagories;
import com.imamportal.model.UploadResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PathUtils;
import com.imamportal.utils.PersistData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentUploadAudio extends Fragment {

    Context context;
    private EditText etHeadline,etAsk;
    private Spinner spinnerCatagory;
    private TextView tvFileName;
    private LinearLayout linChooseFile;

    List<Catagories> catagoriesList = new ArrayList<>();
    private String filePath;
    private AppCompatButton btnSave,btnChoseFile;
    private String category_id;
    String data;
    String token;
    Uri fileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.upload_fragment, container, false);

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

        etHeadline = (EditText)getView().findViewById(R.id.etHeadline);
        etAsk = (EditText)getView().findViewById(R.id.etAsk);
        tvFileName = (TextView) getView().findViewById(R.id.tvFileName);
        spinnerCatagory = (Spinner)getView().findViewById(R.id.spinnerCatagory);
        linChooseFile = (LinearLayout) getView().findViewById(R.id.linChooseFile);
        btnSave = (AppCompatButton) getView().findViewById(R.id.btnSave);
        btnChoseFile = (AppCompatButton) getView().findViewById(R.id.btnChoseFile);
        btnChoseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etHeadline.getText().toString();
                String description = etAsk.getText().toString();
                if(TextUtils.isEmpty(title)){
                    AlertMessage.showMessage(context,"Alert!","");
                }else if(TextUtils.isEmpty(category_id)){
                    AlertMessage.showMessage(context,"Alert!","");
                }else if(TextUtils.isEmpty(description)){
                    AlertMessage.showMessage(context,"Alert!","");
                }else if(fileUri==null){
                    AlertMessage.showMessage(context,"Alert!","");
                }else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("title",title);
                        jsonObject.put("user_id",PersistData.getStringData(context,AppConstant.loginUserid));
                        jsonObject.put("category_id",category_id);
                        jsonObject.put("description",description);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    data = jsonObject.toString();
                    token = PersistData.getStringData(context,
                            AppConstant.loginToken);

                    Log.e("data",""+data);

                    post_store();
                }

            }
        });

        Catagories catagories = new Catagories();
        catagories.setName_bn("নির্বাচন করুন");
        catagoriesList.add(0,catagories);
        for (int i = 0; i <AppConstant.listAllCatagory.size() ; i++) {
            if(AppConstant.listAllCatagory.get(i).getName_bn().equalsIgnoreCase("কুরআন তেলাওয়াত")){
                Catagories catagory = new Catagories();
                catagory.setName_bn(AppConstant.listAllCatagory.get(i).getName_bn());
                catagory.setId(AppConstant.listAllCatagory.get(i).getId());
                catagoriesList.add(catagory);
            }

            if(AppConstant.listAllCatagory.get(i).getName_bn().equalsIgnoreCase("বয়ান")){
                Catagories catagory = new Catagories();
                catagory.setName_bn(AppConstant.listAllCatagory.get(i).getName_bn());
                catagory.setId(AppConstant.listAllCatagory.get(i).getId());
                catagoriesList.add(catagory);
            }

            if(AppConstant.listAllCatagory.get(i).getName_bn().equalsIgnoreCase("হামদ-নাত")){
                Catagories catagory = new Catagories();
                catagory.setName_bn(AppConstant.listAllCatagory.get(i).getName_bn());
                catagory.setId(AppConstant.listAllCatagory.get(i).getId());
                catagoriesList.add(catagory);
            }

            if(AppConstant.listAllCatagory.get(i).getName_bn().equalsIgnoreCase("দিকনির্দেশনা")){
                Catagories catagory = new Catagories();
                catagory.setName_bn(AppConstant.listAllCatagory.get(i).getName_bn());
                catagory.setId(AppConstant.listAllCatagory.get(i).getId());
                catagoriesList.add(catagory);
            }
        }

        CustomAdapter adapterGender = new CustomAdapter(context, catagoriesList);
        spinnerCatagory.setAdapter(adapterGender);

        spinnerCatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    category_id = catagoriesList.get(position).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void post_store() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
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


        File file = new File(PathUtils.getPath(context, fileUri));

        // Parsing any Media type file
        //RequestBody requestBody=RequestBody.create(MediaType.parse("application/pdf"), file);

         RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
//        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), AppConstant.getUserdata(context).getUser_id());
        RequestBody jsonData = RequestBody.create(MediaType.parse("text/plain"), data);


        Call<UploadResponse> userCall = api.audio_store(fileToUpload,jsonData);
        userCall.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                pd.dismiss();

                UploadResponse responsData = response.body();

                if(responsData!=null){
                    if(responsData.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(context, "Send Successful", Toast.LENGTH_SHORT).show();
                    }
                }

//                if(responsData!=null){
//                    if(responsData.getStatus().equalsIgnoreCase("error")){
//                        if(responsData.getData().getEmail()!=null){
//                            for (int i = 0; i <responsData.getData().getEmail().size() ; i++) {
//                                Toast.makeText(context, ""+responsData.getData().getEmail().get(i), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                    }
//                }

            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {

                pd.dismiss();
            }
        });


    }

    private void showFileChooser() {
        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload,1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedFileURI = data.getData();
                fileUri = data.getData();
                File file = new File(selectedFileURI.getPath().toString());
                filePath = selectedFileURI.getPath().toString();
                Log.e("filePath", "" + filePath);
                tvFileName.setText(file.getName().toString());

            }
        }

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



}
