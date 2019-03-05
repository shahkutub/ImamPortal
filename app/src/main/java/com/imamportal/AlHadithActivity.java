package com.imamportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imamportal.fragments.FragmentBisoyHadith;
import com.imamportal.fragments.FragmentDorseQuran;
import com.imamportal.fragments.FragmentHadiseQudsi;
import com.imamportal.fragments.FragmentOnnannoQuran;
import com.imamportal.fragments.FragmentOnnanoHadith;
import com.imamportal.fragments.FragmentQuranPath;
import com.imamportal.fragments.FragmentTAfsirQuran;
import com.imamportal.fragments.FragmentUsuleHadith;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlHadithActivity extends AppCompatActivity {

    Context context;
    private TabLayout tabLayout;
    public ViewPager viewPager;
    private ImageView imgBack;
    private String tabTitles[] = new String[] { "উসুলে হাদিস", "হাদিসে কুদসি", "বিষয়ভিত্তিক হাদিস","অন্যান্য"};
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public static int int_items = 4;
    private int fragmentPos=0;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.al_hadith);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        //getAllQuranAlldadith();

        initUi();
    }

    private void initUi() {
        AppConstant.activitiname = tabTitles[0];
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                AppConstant.activitiname = tabTitles[i];
                Log.e("activitiname",""+AppConstant.activitiname);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        adapter=new MyAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        setPageItem(fragmentPos);


    }
    void setPageItem(int i)
    {
        viewPager.setCurrentItem(i);
    }




    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);

        }



        @Override
        public Fragment getItem(int position)
        {
            //position = AppConstant.POSITION;

            switch (position){

                case 0 :
                    //tvTitle.setText(tabTitles[0]);

                    return new FragmentUsuleHadith();
                case 1 :
                    // tvTitle.setText(tabTitles[1]);

                    return new FragmentHadiseQudsi();

                case 2 :
                    //tvTitle.setText(tabTitles[2]);
                    return new FragmentBisoyHadith();
                case 3 :
                    //tvTitle.setText(tabTitles[3]);
                    return new FragmentOnnanoHadith();




            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            //AppConstant.pageTitle = tabTitles[position];
            return tabTitles[position];
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(context).inflate(R.layout.tab_title_layout, null);
            TextView tv = (TextView) v.findViewById(R.id.tableTitle);
            ImageView tabImg = (ImageView)v.findViewById(R.id.tabImg);


            return v;
        }
    }

//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new FragmentUsuleHadith(), "উসুলে হাদিস");
//        adapter.addFragment(new FragmentHadiseQudsi(), "হাদিসে কুদসি");
//        adapter.addFragment(new FragmentBisoyHadith(), "বিষয়ভিত্তিক হাদিস");
//        adapter.addFragment(new FragmentOnnanoHadith(), "অন্যান্য");
//        viewPager.setAdapter(adapter);
//    }


}
