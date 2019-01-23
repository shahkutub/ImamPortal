package com.imamportal;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imamportal.model.MoulicBisoy;

import java.util.ArrayList;
import java.util.List;

public class SompadokioActivity extends AppCompatActivity {

    Context context;
    private TabLayout tabMoulikBisoy,tabSopmadokio;
    private RelativeLayout relIslamMoulikBisoy,relTabSopmpadokio;
    TextView tvTabDescription,tvSompadokioDes,tvDate,MarqueeText;
    private ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sompadokio);
        context =this;
        tabSompadokio();
    }


    String title,descript;
    private void tabSompadokio() {
        imgback = (ImageView)findViewById(R.id.imgback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        relTabSopmpadokio = (RelativeLayout)findViewById(R.id.relTabSopmpadokio);


        tabSopmadokio = (TabLayout) findViewById(R.id.tabSopmadokio);
        tvSompadokioDes = (TextView) findViewById(R.id.tvSompadokioDes);
        tabSopmadokio.setTabTextColors(Color.BLACK,Color.RED);

        final List<MoulicBisoy> moulicBisoyList = new ArrayList<>();

        MoulicBisoy mo = new MoulicBisoy();
        mo.setTitle(getString(R.string.recentsompadokio));
        mo.setDetails("");
        moulicBisoyList.add(mo);

        MoulicBisoy mo1 = new MoulicBisoy();
        mo1.setTitle(getString(R.string.previoussompadokio));
        mo1.setDetails("");
        moulicBisoyList.add(mo1);



//        for (int i = 0; i <7 ; i++) {
//            moulicBisoyList.add(i,mo);
//        }

        for (int i = 0; i < moulicBisoyList.size(); i++) {
            tabSopmadokio.addTab(tabSopmadokio.newTab().setText(moulicBisoyList.get(i).getTitle()));
        }

        tvSompadokioDes.setText(moulicBisoyList.get(0).getDetails());
        title = moulicBisoyList.get(0).getDetails();
        descript = moulicBisoyList.get(0).getTitle();

//        final String text = moulicBisoyList.get(0).getDetails()+"<font color='blue'>বিস্তারিত</font>";
//        tvTabDescription.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        tabSopmadokio.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tvSompadokioDes.setText(moulicBisoyList.get(tab.getPosition()).getDetails());
                //tvTabDescription.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
                descript = moulicBisoyList.get(tab.getPosition()).getDetails();
                title = moulicBisoyList.get(tab.getPosition()).getTitle();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        relTabSopmpadokio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogeMoulikBisoy(title,descript);
            }
        });

    }


    private void dialogeMoulikBisoy(String moulikTitle,String details) {
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialoge_moulik);

        ImageView imgCross = (ImageView)dialog.findViewById(R.id.imgCross);
        TextView tvBisoy = (TextView)dialog.findViewById(R.id.tvBisoy);
        TextView tvBisoyDescription = (TextView)dialog.findViewById(R.id.tvBisoyDescription);

        tvBisoy.setText(moulikTitle);
        tvBisoyDescription.setText(details);
        tvBisoyDescription.setMovementMethod(new ScrollingMovementMethod());

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
}
