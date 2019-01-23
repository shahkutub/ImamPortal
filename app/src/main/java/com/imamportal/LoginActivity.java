package com.imamportal;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Context context;
    private String name,password;
    private EditText input_name,input_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        context=this;
        initUi();
    }

    private void initUi() {

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        TextView input_name = (TextView)findViewById(R.id.input_name);
        TextView input_password = (TextView)findViewById(R.id.input_password);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
