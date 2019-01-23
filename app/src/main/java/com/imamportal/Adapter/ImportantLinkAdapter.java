package com.imamportal.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.R;
import com.imamportal.model.QuestionAnsInfo;

import java.util.ArrayList;
import java.util.List;

public class ImportantLinkAdapter extends RecyclerView.Adapter<ImportantLinkAdapter.MyViewHolder> {


    List<QuestionAnsInfo> questionAnsInfoList = new ArrayList<>();
    Context context;


    public ImportantLinkAdapter(List<QuestionAnsInfo> questionAnsInfoList, Context context) {
        this.questionAnsInfoList = questionAnsInfoList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;
        LinearLayout linMainView;
        public MyViewHolder(View view) {
            super(view);
            tvQuestion=(TextView) view.findViewById(R.id.tvQuestion);
            linMainView=(LinearLayout) view.findViewById(R.id.linMainView);
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
        holder.linMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position==0){
                    openWebPage("https://mora.gov.bd/");
                }

                if (position==1){
                    openWebPage("http://www.islamicfoundation.gov.bd/");
                }

                if (position==2){
                    openWebPage("http://www.hajj.gov.bd/bn/");
                }

                if (position==3){
                    openWebPage("http://www.bmeb.gov.bd/");
                }

                if (position==4){
                    openWebPage("https://makkahlive.net/makkahlive.aspx");
                }

            }

        });

    }


    @Override
    public int getItemCount()
    {
        return questionAnsInfoList.size();
    }

    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            context.startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}

