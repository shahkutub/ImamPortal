package com.imamportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.imamportal.R;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.NotificationInfo;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MyViewHolder> {


    List<NotificationInfo> dataList = new ArrayList<>();
    Context context;


    public MsgAdapter(List<NotificationInfo> santirBaniList, Context context) {
        this.dataList = santirBaniList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvShortDescription;
        public MyViewHolder(View view) {
            super(view);
            tvShortDescription=(TextView) view.findViewById(R.id.tvShortDescription);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final NotificationInfo data = dataList.get(position);
        holder.tvShortDescription.setText(data.getMessage());

//        if(data.getUser_name()!=null){
//            holder.tvShortDescription.setText(data.getUser_name()+" "+data.getMessage());
//        }

    }


    @Override
    public int getItemCount()
    {
        return dataList.size();
    }
}

