package com.imamportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imamportal.utils.AppConstant;


public class SingleImageActivity extends AppCompatActivity  {


    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_body);

        context = this;

        LinearLayout linSantirbani = (LinearLayout)findViewById(R.id.linAlQran);
        linSantirbani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.activitiname = getString(R.string.santirbani);
                AppConstant.bolgpostName = "শান্তির বাণী";
                startActivity(new Intent(context, AllCommonPostActivity.class));
            }
        });


    }


}
