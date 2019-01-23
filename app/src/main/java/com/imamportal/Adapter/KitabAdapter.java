package com.imamportal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.R;
import com.imamportal.model.KitabInfo;

import java.util.ArrayList;
import java.util.List;

public class KitabAdapter extends RecyclerView.Adapter<KitabAdapter.MyViewHolder> {


    List<KitabInfo> horizontalList = new ArrayList<>();
    Context context;


    public KitabAdapter(List<KitabInfo> horizontalList, Context context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtview;
        public MyViewHolder(View view) {
            super(view);
            imageView=(ImageView) view.findViewById(R.id.imgKitab);
            txtview=(TextView) view.findViewById(R.id.tvNameKitab);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_kitab, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //holder.imageView.setImageResource(horizontalList.get(position).imageId);
        holder.txtview.setText(horizontalList.get(position).txt);

//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
////                String list = horizontalList.get(position).txt.toString();
////                Toast.makeText(context, list, Toast.LENGTH_SHORT).show();
//            }
//
//        });

    }


    @Override
    public int getItemCount()
    {
        return horizontalList.size();
    }
}

