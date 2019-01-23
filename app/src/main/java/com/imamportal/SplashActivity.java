package com.imamportal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imamportal.utils.AppConstant;
import com.imamportal.utils.PersistData;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME = 1000;
    private LinearLayout linBg;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context=this;

        Log.e("token", PersistData.getStringData(context, AppConstant.fcm_token));

       // linBg = (LinearLayout)findViewById(R.id.linBg);

        ImageView imgNext = (ImageView)findViewById(R.id.imgNext);
        ImageView imgA2i = (ImageView)findViewById(R.id.imgA2i);

        imgA2i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://a2i.gov.bd/")));
            }
        });

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(100); //You can manage the blinking time with this parameter
        anim.setStartOffset(40);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        imgNext.startAnimation(anim);

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LanguageActivity.class));
                        finish();
            }
        });



//        Thread timer = new Thread() {
//            public void run() {
//                try {
//                    sleep(SPLASH_TIME);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    //if (SharedPref.getPrefForLoginStatus(SplashActivity.this)) {
//                     startActivity(new Intent(getApplicationContext(),LanguageActivity.class));
//                        finish();
////                    } else {
////                        ProjectUtils.genericIntent(SplashActivity.this, LoginActivity.class, null, true);
////                    }
//
//                }
//            }
//        };
//        timer.start();
    }
}
