package com.imamportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.Adapter.AllCommonPostAdapter;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.model.CommonPostResponse;
import com.imamportal.model.JobPortalModel;
import com.imamportal.model.SantirbaniInfo;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PathUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobCirculerActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private RecyclerView recyclSantirBani;
    private TextView tvTotalBani,tvName,tvJobSearch,tvJobcirForm,tvFileName;
    private LinearLayout linJobCirList,linJobCirForm;
    private EditText etJobNmae,etJobDesignation,etPlace,etSalary,etJobDescription;
    private Button btnSave,btnChoseFile;
    private CommonPostResponse commonPostResponse = new CommonPostResponse();
    private String uploadedFileName;
    private String filePath;
    JSONObject data = new JSONObject();
    Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobcircular_list);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initUi();

    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        tvName = (TextView) findViewById(R.id.tvName);
        tvJobSearch = (TextView) findViewById(R.id.tvJobSearch);
        tvJobcirForm = (TextView) findViewById(R.id.tvJobcirForm);
        tvFileName = (TextView) findViewById(R.id.tvFileName);

        linJobCirForm = (LinearLayout)findViewById(R.id.linJobCirForm);
        linJobCirList = (LinearLayout)findViewById(R.id.linJobCirList);

        etJobNmae = (EditText)findViewById(R.id.etJobNmae);
        etJobDesignation = (EditText)findViewById(R.id.etJobDesignation);
        etPlace = (EditText)findViewById(R.id.etPlace);
        etSalary = (EditText)findViewById(R.id.etSalary);
        etJobDescription = (EditText)findViewById(R.id.etJobDescription);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnChoseFile = (Button)findViewById(R.id.btnChoseFile);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jobName = etJobNmae.getText().toString();
                String  jobDesignation = etJobDesignation.getText().toString();
                String  jobplace = etPlace.getText().toString();
                String  salary = etSalary.getText().toString();
                String  jobDescript = etJobDescription.getText().toString();

                if(TextUtils.isEmpty(jobName)){
                    AlertMessage.showMessage(context,"Alert","চাকুরীর শিরোনাম লিখুন");
                    etJobNmae.requestFocus();
                }else if(TextUtils.isEmpty(jobDesignation)){
                    AlertMessage.showMessage(context,"Alert","চাকুরীর পদবী লিখুন");
                    etJobDesignation.requestFocus();
                }else if(TextUtils.isEmpty(jobplace)){
                    AlertMessage.showMessage(context,"Alert","চাকুরীর স্থান লিখুন");
                    etPlace.requestFocus();
                }else if(TextUtils.isEmpty(salary)){
                    AlertMessage.showMessage(context,"Alert","বেতন পরিসীমা লিখুন");
                    etSalary.requestFocus();
                }else if(TextUtils.isEmpty(jobDescript)){
                    AlertMessage.showMessage(context,"Alert","চাকুরীর বিবরণী লিখুন");
                    etJobDescription.requestFocus();
                }else {

                    
                    try {
                        data.put("user_id","3");
                        data.put("job_title",jobName);
                        data.put("job_designation",jobDesignation);
                        data.put("job_location",jobplace);
                        data.put("job_salary_range",salary);
                        data.put("job_description",jobDescript);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("data",""+data);

                    uploadJobPost();
                }


            }
        });

        tvJobSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJobPost();
                tvJobSearch.setBackgroundColor(Color.parseColor("#5E397A"));
                tvJobcirForm.setBackgroundColor(Color.parseColor("#4D5155"));
                linJobCirForm.setVisibility(View.GONE);
                linJobCirList.setVisibility(View.VISIBLE);
            }
        });

        tvJobcirForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvJobSearch.setBackgroundColor(Color.parseColor("#4D5155"));
                tvJobcirForm.setBackgroundColor(Color.parseColor("#5E397A"));
                linJobCirForm.setVisibility(View.VISIBLE);
                linJobCirList.setVisibility(View.GONE);
            }
        });




        //tvName.setText(AppConstant.activitiname);

        tvTotalBani = (TextView) findViewById(R.id.tvTotalBani);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclSantirBani = (RecyclerView) findViewById(R.id.recyclSantirBani);
        btnChoseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        getJobPost();

    }

//    private void showFileChooser() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("*/*");
//       // String[] mimetypes = {"image/*", "video/*"};
//        //intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
//
//        try {
//            startActivityForResult(
//                    Intent.createChooser(intent, "Select a File to Upload"),
//                    1);
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(context, "Please install a File Manager.",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedFileURI = data.getData();
                File file = new File(selectedFileURI.getPath().toString());
                filePath = selectedFileURI.getPath().toString();
                fileUri = data.getData();
                Log.e("filePath", "" + filePath);
                uploadedFileName = file.getName().toString();
                Log.e("File", "" + uploadedFileName);
                tvFileName.setText(uploadedFileName);
//                StringTokenizer tokens = new StringTokenizer(uploadedFileName, ":");
//
//                String first = tokens.nextToken();
//                String file_1 = tokens.nextToken().trim();
//                tvFileName.setText(file_1);
            }
        }

    }


    private class JobpostAdapter extends RecyclerView.Adapter<JobpostAdapter.MyViewHolder> {


        List<AllBlogpostModel> santirBaniList = new ArrayList<>();
        Context context;


        public JobpostAdapter(List<AllBlogpostModel> santirBaniList, Context context) {
            this.santirBaniList = santirBaniList;
            this.context = context;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvTitle,tvShortDescription,tvPublisher,tvPublishDate,tvViewCount;
            ImageView imgFile;
            public MyViewHolder(View view) {
                super(view);
                tvTitle=(TextView) view.findViewById(R.id.tvTitle);
                tvShortDescription=(TextView) view.findViewById(R.id.tvShortDescription);
                tvPublisher=(TextView) view.findViewById(R.id.tvPublisher);
                tvPublishDate=(TextView) view.findViewById(R.id.tvPublishDate);
                tvViewCount=(TextView) view.findViewById(R.id.tvViewCount);
                imgFile=(ImageView) view.findViewById(R.id.imgFile);
            }
        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_joblist, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final AllBlogpostModel data = santirBaniList.get(position);
            holder.tvPublishDate.setText(data.getCreated_at());
            if(data.getUser_detail()!=null){
                holder.tvPublisher.setText(data.getUser_detail().getName());
            }
            holder.tvTitle.setText(data.getJob_title());
            holder.tvShortDescription.setText(data.getJob_description());
            //holder.tvViewCount.setText(data.getView_count());

            if(!TextUtils.isEmpty(data.getFile())){
                holder.imgFile.setVisibility(View.VISIBLE);
            }

            holder.imgFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(Api.BASE_URL+"public/job_file/"+data.getFile())));
                }
            });

        }


        @Override
        public int getItemCount()
        {
            return santirBaniList.size();
        }
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
        Call<CommonPostResponse> userCall = api.jobportals();
        userCall.enqueue(new Callback<CommonPostResponse>() {
            @Override
            public void onResponse(Call<CommonPostResponse> call, Response<CommonPostResponse> response) {
                pd.dismiss();

                commonPostResponse = response.body();
                    if(commonPostResponse.getData()!=null){
                        int size = commonPostResponse.getData().size();
                        Log.e("size",""+size);
                        tvTotalBani.setText("সর্বমোট "+AppConstant.activitiname+size+" টি");
                        JobpostAdapter questionAnsAdapter = new JobpostAdapter(commonPostResponse.getData(),context);
                        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                                LinearLayoutManager.VERTICAL, false);
                        recyclSantirBani.setLayoutManager(horizontalLayoutManager);
                        recyclSantirBani.setAdapter(questionAnsAdapter);
                    }
            }

            @Override
            public void onFailure(Call<CommonPostResponse> call, Throwable t) {
                pd.dismiss();
            }
        });


    }

    private void uploadJobPost() {

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
        File file = new File(PathUtils.getPath(context, fileUri));

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);
        RequestBody dataoj = RequestBody.create(MediaType.parse("text/plain"), data.toString());


        Call<ResponseBody> userCall = api.jobPost( multipartBody,dataoj);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pd.dismiss();

              //String  reData = response.body();
                Toast.makeText(context, "Send Successful", Toast.LENGTH_SHORT).show();
               
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
            }
        });


    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
