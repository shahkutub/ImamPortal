package com.imamportal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imamportal.R;
import com.imamportal.model.CommentModel;
import com.imamportal.model.KitabInfo;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {


    List<CommentModel> commentModelList = new ArrayList<>();
    Context context;


    public CommentAdapter(List<CommentModel> commentModelList, Context context) {
        this.commentModelList = commentModelList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNme,tvComment;
        public MyViewHolder(View view) {
            super(view);
            tvNme=(TextView) view.findViewById(R.id.tvNme);
            tvComment=(TextView) view.findViewById(R.id.tvComment);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_comment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

       CommentModel data = commentModelList.get(position);
       //holder.tvNme.setText("");
       holder.tvComment.setText(data.getComment());

    }


    @Override
    public int getItemCount()
    {
        return commentModelList.size();
    }
}

