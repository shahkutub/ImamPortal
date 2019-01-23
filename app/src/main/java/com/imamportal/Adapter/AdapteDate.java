package com.imamportal.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imamportal.R;


/**
 * Created by User on 10/9/2016.
 */

public class AdapteDate extends PagerAdapter {
    private LayoutInflater inflater;
    public Context con;
    Activity activity;
    //Vector<ClubImageInfo> clubImageInfos;
   // int img[] = null;
    String name[] = null;

    // constructor
    public AdapteDate(Context con, String name[]) {
        this.con = con;
        this.name = name;

    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView image;

        // AppConstant.fitnesspost= position;

        inflater = (LayoutInflater) con
                .getSystemService(con.LAYOUT_INFLATER_SERVICE);
        final View viewLayout = inflater.inflate(R.layout.viewpager_item_date,
                container, false);
        //ImageView imageviewSlider=(ImageView) viewLayout.findViewById(R.id.imgBijoyee);
        TextView tvDate=(TextView) viewLayout.findViewById(R.id.tvDate);
       // imageviewSlider.setImageResource(img[position]);
        tvDate.setText(name[position]);

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);

    }

}



