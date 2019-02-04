package com.imamportal.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.imamportal.R;
import com.imamportal.model.PhotoModel;
import com.imamportal.model.SantirbaniInfo;

import java.util.ArrayList;
import java.util.List;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.MyViewHolder> {

    Bitmap bmp = null;
    List<PhotoModel> listPhoto = new ArrayList<>();
    Context context;


    public ProfileListAdapter(List<PhotoModel> santirBaniList, Context context) {
        this.listPhoto = santirBaniList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView tvPicTitle;
        public MyViewHolder(View view) {
            super(view);
            imgPhoto=(ImageView) view.findViewById(R.id.imgPhoto);
            tvPicTitle=(TextView) view.findViewById(R.id.tvPicTitle);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_profile, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        PhotoModel data = listPhoto.get(position);

        holder.tvPicTitle.setText(data.getTitle());
        //Glide.with(context).load("http://192.168.0.119/imamportal/public/photo_gallery/"+data.getImage()).into( holder.imgPhoto).getRequest();


        Glide.with(context)
                .asBitmap()
                .load("http://192.168.0.119/imamportal/public/photo_gallery/"+data.getImage())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.imgPhoto.setImageBitmap(resource);
                        bmp = resource;
                    }
                });

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilogFullPic(bmp);
            }
        });


    }

    private void dilogFullPic(Bitmap bmp) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoge_bigpic);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();

        ImageView imgPhoto = (ImageView)dialog.findViewById(R.id.imgPhoto);
        imgPhoto.setImageBitmap(bmp);


    }


    @Override
    public int getItemCount()
    {
        return listPhoto.size();
    }
}

