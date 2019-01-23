package com.imamportal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.imamportal.Adapter.AdapterQuize;
import com.imamportal.Adapter.AdapterSeraContent;

import java.util.Timer;
import java.util.TimerTask;

public class SeraContentActivity extends Activity {


    Context context;
    private ImageView imgBack;

    private ViewPager quizeViewpager;
    int[] imageRSC = {R.drawable.imam, R.drawable.imam, R.drawable.imam};
    String[] names = {"sadi", "ALom", "Saroar"};
    private Timer swipeTimer;
    boolean isTimerRunning;
    Runnable Update;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seracontent);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initUi();

    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        slideshow();
    }


    private void slideshow() {

        handler = new Handler();
        createSwipeTimer();
        isTimerRunning = true;

        quizeViewpager = (ViewPager) findViewById(R.id.quizeViewpager);

        quizeViewpager.setAdapter(new AdapterSeraContent(context, imageRSC, names));

        quizeViewpager.setCurrentItem(0);

        quizeViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isTimerRunning = false;
                    swipeTimer.cancel();
                } else {
                    if (!isTimerRunning) {
                        // createSwipeTimer();
                        isTimerRunning = true;
                    }
                }
            }
        });


        Update = new Runnable() {
            @Override
            public void run() {

                if (imageRSC != null && imageRSC.length > 0) {
                    int currentImg = quizeViewpager.getCurrentItem();
                    currentImg++;
                    if (currentImg == imageRSC.length) {
                        currentImg = 0;
                    }
                    quizeViewpager.setCurrentItem(currentImg, true);
                }

                /*
                 * if (currentPage == AllMenuImgInfo.getAllMenuImgInfo().size())
                 * { currentPage = 0; }
                 * MainViewPager.setCurrentItem(currentPage++, true);
                 */
            }
        };
    }

    private void createSwipeTimer() {
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }
}
