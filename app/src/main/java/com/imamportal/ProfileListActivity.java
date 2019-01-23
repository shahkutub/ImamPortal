package com.imamportal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imamportal.Adapter.ProfileListAdapter;
import com.imamportal.model.SantirbaniInfo;
import com.imamportal.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class ProfileListActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private RecyclerView recyclProfielist;
    private TextView tvTotalBani,tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_list);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initUi();

    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        tvName = (TextView) findViewById(R.id.tvName);

        //tvName.setText(AppConstant.activitiname);

        tvTotalBani = (TextView) findViewById(R.id.tvTotalBani);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclProfielist = (RecyclerView) findViewById(R.id.recyclProfielist);

        List<SantirbaniInfo> questionAnsInfoList = new ArrayList<>();
        SantirbaniInfo questionAnsInfo = new SantirbaniInfo();
        for (int i = 0; i < 10; i++) {
            questionAnsInfoList.add(i, questionAnsInfo);
        }

        int size = questionAnsInfoList.size();
        tvTotalBani.setText("সর্বমোট "+AppConstant.activitiname+size+" টি");

        ProfileListAdapter questionAnsAdapter = new ProfileListAdapter(questionAnsInfoList,context);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recyclProfielist.setLayoutManager(new GridLayoutManager(this, 2));
        recyclProfielist.setAdapter(questionAnsAdapter);

    }

}
