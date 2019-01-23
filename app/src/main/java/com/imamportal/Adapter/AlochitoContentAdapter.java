package com.imamportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imamportal.AlochitoContentActivity;
import com.imamportal.R;
import com.imamportal.model.QuestionAnsInfo;

import java.util.ArrayList;
import java.util.List;

public class AlochitoContentAdapter extends RecyclerView.Adapter<AlochitoContentAdapter.MyViewHolder> {


    List<QuestionAnsInfo> questionAnsInfoList = new ArrayList<>();
    Context context;


    public AlochitoContentAdapter(List<QuestionAnsInfo> questionAnsInfoList, Context context) {
        this.questionAnsInfoList = questionAnsInfoList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;
        public MyViewHolder(View view) {
            super(view);
            tvQuestion=(TextView) view.findViewById(R.id.tvQuestion);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_question_ans, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //holder.imageView.setImageResource(horizontalList.get(position).imageId);
        holder.tvQuestion.setText(questionAnsInfoList.get(position).question);



       holder.tvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                context.startActivity(new Intent(context, AlochitoContentActivity.class));
            }

        });

    }


    @Override
    public int getItemCount()
    {
        return questionAnsInfoList.size();
    }
}

