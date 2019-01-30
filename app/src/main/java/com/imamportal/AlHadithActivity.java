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
import android.view.View;
import android.widget.ImageView;

import com.imamportal.fragments.FragmentBisoyHadith;
import com.imamportal.fragments.FragmentHadiseQudsi;
import com.imamportal.fragments.FragmentOnnanoHadith;
import com.imamportal.fragments.FragmentQuranPath;
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
    List<AlquranAlhadits>  listAlquranAlhadit = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.al_hadith);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        getAllQuranAlldadith();
    }

    private void initUi() {

        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentUsuleHadith(), "উসুলে হাদিস");
        adapter.addFragment(new FragmentHadiseQudsi(), "হাদিসে কুদসি");
        adapter.addFragment(new FragmentBisoyHadith(), "বিষয়ভিত্তিক হাদিস");
        adapter.addFragment(new FragmentOnnanoHadith(), "অন্যান্য");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void getAllQuranAlldadith() {

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
        Call<List<AlquranAlhadits>> userCall = api.alquranAllhadits();
        userCall.enqueue(new Callback<List<AlquranAlhadits>>() {
            @Override
            public void onResponse(Call<List<AlquranAlhadits>> call, Response<List<AlquranAlhadits>> response) {
                pd.dismiss();

                listAlquranAlhadit = response.body();

                Log.e("listAlquranAlhadit",""+listAlquranAlhadit.size());

                if(listAlquranAlhadit.size()>0){
                    AppConstant.listAlqranAlhadith = listAlquranAlhadit;

                }
                initUi();
            }

            @Override
            public void onFailure(Call<List<AlquranAlhadits>> call, Throwable t) {
                pd.dismiss();
            }
        });


    }
}
