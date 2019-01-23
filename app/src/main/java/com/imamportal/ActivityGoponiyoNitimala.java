package com.imamportal;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityGoponiyoNitimala extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goponionitimala);
        TextView tvtext = findViewById(R.id.tvtext);
        TextView tvName = findViewById(R.id.tvName);
        tvName.setText(getString(R.string.gopnionitimala));

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvtext.setMovementMethod(new ScrollingMovementMethod());

    }
}
