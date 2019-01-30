package com.imamportal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.Adapter.AllCommonPostAdapter;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.model.SantirbaniInfo;
import com.imamportal.utils.AlertMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JobCirculerActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private RecyclerView recyclSantirBani;
    private TextView tvTotalBani,tvName,tvJobSearch,tvJobcirForm,tvFileName;
    private LinearLayout linJobCirList,linJobCirForm;
    private EditText etJobNmae,etJobDesignation,etPlace,etSalary,etJobDescription;
    private Button btnSave,btnChoseFile;
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
                    AlertMessage.showMessage(context,"Alert","");
                    etJobNmae.requestFocus();
                }else if(TextUtils.isEmpty(jobDesignation)){
                    AlertMessage.showMessage(context,"Alert","");
                    etJobDesignation.requestFocus();
                }else if(TextUtils.isEmpty(jobplace)){
                    AlertMessage.showMessage(context,"Alert","");
                    etPlace.requestFocus();
                }else if(TextUtils.isEmpty(salary)){
                    AlertMessage.showMessage(context,"Alert","");
                    etSalary.requestFocus();
                }else if(TextUtils.isEmpty(jobDescript)){
                    AlertMessage.showMessage(context,"Alert","");
                    etJobDescription.requestFocus();
                }else {

                }


            }
        });

        tvJobSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        List<AllBlogpostModel> questionAnsInfoList = new ArrayList<>();
        AllBlogpostModel questionAnsInfo = new AllBlogpostModel();
        for (int i = 0; i < 10; i++) {
            questionAnsInfoList.add(i, questionAnsInfo);
        }

        int size = questionAnsInfoList.size();
        tvTotalBani.setText("সর্বমোট "+"চাকুরীর বিজ্ঞপ্তি "+size+" টি");

        AllCommonPostAdapter questionAnsAdapter = new AllCommonPostAdapter(questionAnsInfoList,context);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recyclSantirBani.setLayoutManager(horizontalLayoutManager);
        recyclSantirBani.setAdapter(questionAnsAdapter);

        btnChoseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedFileURI = data.getData();
                File file = new File(selectedFileURI.getPath().toString());
                Log.e("", "File : " + file.getName());
                String uploadedFileName = file.getName().toString();
                Log.e("", "File : " + uploadedFileName);
                tvFileName.setText(uploadedFileName);
//                StringTokenizer tokens = new StringTokenizer(uploadedFileName, ":");
//
//                String first = tokens.nextToken();
//                String file_1 = tokens.nextToken().trim();
//                tvFileName.setText(file_1);
            }
        }

    }
}
