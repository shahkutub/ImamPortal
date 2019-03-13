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

import com.imamportal.fragments.FragmentAmarPataContent;
import com.imamportal.fragments.FragmentUploadContent;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.model.Catagories;
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

public class UploadActivity extends AppCompatActivity {

    Context context;
    private TabLayout tabLayout;
    public ViewPager viewPager;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        //getblog_post();
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
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        getblog_post();
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(getString(R.string.narikornar));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_nari, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getString(R.string.sisukisur));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_children, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getString(R.string.onnodhara));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_onnodhara, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentUploadContent(), "কনটেন্ট");
        adapter.addFragment(new FragmentUploadContent(), getString(R.string.audio));
        adapter.addFragment(new FragmentUploadContent(), getString(R.string.video));
        adapter.addFragment(new FragmentUploadContent(), getString(R.string.photogalari));
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


    private void getblog_post() {

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
        Call<List<Catagories>> userCall = api.categorys();
        userCall.enqueue(new Callback<List<Catagories>>() {
            @Override
            public void onResponse(Call<List<Catagories>> call, Response<List<Catagories>> response) {
                pd.dismiss();

                List<Catagories> listAlblog = new ArrayList<>();
                listAlblog = response.body();

                Log.e("listAlquranAlhadit",""+listAlblog.size());

                if(listAlblog.size()>0){
                    AppConstant.listAllCatagory = listAlblog;
                }
            }

            @Override
            public void onFailure(Call<List<Catagories>> call, Throwable t) {
                pd.dismiss();
            }
        });


    }
}
