package com.imamportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imamportal.R;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.model.SantirbaniInfo;
import com.imamportal.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class AlquranAldadithPostAdapter extends RecyclerView.Adapter<AlquranAldadithPostAdapter.MyViewHolder> {


    List<AlquranAlhadits> dataList = new ArrayList<>();
    Context context;


    public AlquranAldadithPostAdapter(List<AlquranAlhadits> santirBaniList, Context context) {
        this.dataList = santirBaniList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvShortDescription,tvPublisher,tvPublishDate;
        ImageView imgFile,imgPublisher;
        public MyViewHolder(View view) {
            super(view);
            tvTitle=(TextView) view.findViewById(R.id.tvTitle);
            tvShortDescription=(TextView) view.findViewById(R.id.tvShortDescription);
            tvPublisher=(TextView) view.findViewById(R.id.tvPublisher);
            tvPublishDate=(TextView) view.findViewById(R.id.tvPublishDate);
            imgFile=(ImageView) view.findViewById(R.id.imgFile);
            imgPublisher=(ImageView) view.findViewById(R.id.imgPublisher);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_common_post, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final AlquranAlhadits data = dataList.get(position);

        holder.tvPublishDate.setText(data.getCreated_at());
        holder.tvPublisher.setText(data.getUser_detail().getName());
        holder.tvTitle.setText(data.getTitle());
        if(data.getDescription()!=null){
            holder.tvShortDescription.setText(android.text.Html.fromHtml(data.getDescription()).toString());
        }


        if(data.getFile()!=null){
            holder.imgFile.setVisibility(View.VISIBLE);
        }

        holder.imgFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.119/imamportal/public/al-quran_hadith/"+data.getFile())));
            }
        });

        holder.tvShortDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.dilogDetails(context,data.getTitle(),data.getDescription(),data.getUser_detail().getName(),data.getCreated_at(),
                        "","","");
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return dataList.size();
    }
}

