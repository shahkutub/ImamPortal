package com.imamportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.imamportal.DetailsActivity;
import com.imamportal.R;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.model.SantirbaniInfo;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AllCommonPostAdapter extends RecyclerView.Adapter<AllCommonPostAdapter.MyViewHolder> {


    List<AllBlogpostModel> santirBaniList = new ArrayList<>();
    Context context;
    private String name="";


    public AllCommonPostAdapter(List<AllBlogpostModel> santirBaniList, Context context) {
        this.santirBaniList = santirBaniList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linFullView;
        TextView tvTitle,tvShortDescription,tvPublisher,tvPublishDate,tvViewCount,tvCommentCount;
        ImageView imgFile;
        public MyViewHolder(View view) {
            super(view);
            tvTitle=(TextView) view.findViewById(R.id.tvTitle);
            tvShortDescription=(TextView) view.findViewById(R.id.tvShortDescription);
            tvPublisher=(TextView) view.findViewById(R.id.tvPublisher);
            tvPublishDate=(TextView) view.findViewById(R.id.tvPublishDate);
            tvViewCount=(TextView) view.findViewById(R.id.tvViewCount);
            tvCommentCount=(TextView) view.findViewById(R.id.tvCommentCount);
            imgFile=(ImageView) view.findViewById(R.id.imgFile);
            linFullView=(LinearLayout) view.findViewById(R.id.linFullView);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_common_post, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final AllBlogpostModel data = santirBaniList.get(position);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date = null;
        String fomateDate = "";

        if(!TextUtils.isEmpty(data.getCreated_at())){
            try {
                date = df.parse(data.getCreated_at());
                fomateDate = df.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            fomateDate = "";
        }

        holder.tvPublishDate.setText("প্রকাশঃ "+fomateDate);
        if(data.getUser_detail()!=null){
            if(data.getUser_detail().getName()!=null){
                holder.tvPublisher.setText(data.getUser_detail().getName());
                name = data.getUser_detail().getName();
            }
        }

        holder.tvTitle.setText(data.getTitle());
        if(data.getDescription()!=null){
            holder.tvShortDescription.setText(android.text.Html.fromHtml(data.getDescription()).toString());
        }
        holder.tvViewCount.setText(data.getView_count());
        holder.tvCommentCount.setText(data.getComment().size()+"");

        if(data.getImage()!=null){
            holder.imgFile.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .asBitmap()
                    .load(Api.BASE_URL+"public/upload/user/"+data.getImage())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            holder.imgFile.setImageBitmap(resource);
                        }
                    });
        }

        holder.linFullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppConstant.activitiname.equalsIgnoreCase("answer")){
                   AppConstant.detaisData = data;
                   context.startActivity(new Intent(context, DetailsActivity.class));
//                    AppConstant.dilogDetails(context,data.getQuestion(),data.getAnswer(),""+name,
//                            data.getCreated_at(),data.getView_count(),"","");
                }else {
//                    AppConstant.dilogDetails(context,data.getTitle(),data.getDescription(),""+name,
//                            data.getCreated_at(),data.getView_count(),"","");

                    AppConstant.detaisData = data;
                    context.startActivity(new Intent(context, DetailsActivity.class));
                }


            }
        });

        if(AppConstant.activitiname.equalsIgnoreCase("answer")){
            holder.tvTitle.setText(data.getQuestion());
            if(data.getAnswer()!=null){
                holder.tvShortDescription.setText(android.text.Html.fromHtml(data.getAnswer()).toString());
            }
        }


    }


    @Override
    public int getItemCount()
    {
        return santirBaniList.size();
    }
}

