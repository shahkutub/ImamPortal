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
import com.imamportal.model.AudioModel;
import com.imamportal.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {


    List<AudioModel> dataList = new ArrayList<>();
    Context context;


    public AudioAdapter(List<AudioModel> santirBaniList, Context context) {
        this.dataList = santirBaniList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvShortDescription,tvPublisher,tvPublishDate,tvViewCount;
        ImageView imgFile;
        public MyViewHolder(View view) {
            super(view);
            tvTitle=(TextView) view.findViewById(R.id.tvTitle);
            tvShortDescription=(TextView) view.findViewById(R.id.tvShortDescription);
            tvPublisher=(TextView) view.findViewById(R.id.tvPublisher);
            tvPublishDate=(TextView) view.findViewById(R.id.tvPublishDate);
            tvViewCount=(TextView) view.findViewById(R.id.tvViewCount);
            imgFile=(ImageView) view.findViewById(R.id.imgFile);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_audio, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final AudioModel data = dataList.get(position);

        holder.tvPublishDate.setText(data.getCreated_at());
        holder.tvPublisher.setText(data.getUser_detail().getName());
        holder.tvTitle.setText(data.getTitle());
        if(data.getDescription()!=null){
            holder.tvShortDescription.setText(android.text.Html.fromHtml(data.getDescription()).toString());
        }
        holder.tvViewCount.setText(data.getView_count());

        if(data.getAudio()!=null){
            holder.imgFile.setVisibility(View.VISIBLE);
        }

        holder.imgFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.119/imamportal/public/al-quran_hadith/"+data.getAudio())));
            }
        });

        holder.tvShortDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.dilogDetails(context,data.getTitle(),data.getDescription(),data.getUser_detail().getName(),data.getCreated_at(),
                        data.getView_count(),"","");
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return dataList.size();
    }
}

