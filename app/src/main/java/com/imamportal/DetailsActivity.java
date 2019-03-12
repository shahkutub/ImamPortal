package com.imamportal;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.imamportal.utils.AppConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    Context context;
    ///String title, content, publisher, publishDate, viewcount,  likecount, commentcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = this;

        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        final TextView tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvDescription.setMovementMethod(new ScrollingMovementMethod());

        TextView tvPublisher = (TextView)findViewById(R.id.tvPublisher);
        TextView tvPublishDate = (TextView)findViewById(R.id.tvPublishDate);
        TextView tvCountView = (TextView)findViewById(R.id.tvCountView);
        TextView tvCommentCount = (TextView)findViewById(R.id.tvCommentCount);
        ImageView imgShare = (ImageView)findViewById(R.id.imgShare);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareCompat.IntentBuilder.from(DetailsActivity.this)
                        .setType("text/plain")
                        .setChooserTitle("Share")
                        .setText(Html.fromHtml(AppConstant.detaisData.getDescription()).toString())
                        .startChooser();
            }
        });

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);


        if(AppConstant.activitiname.equalsIgnoreCase("answer")){
            String htmlString = "<div style=\"color:#069\"><b>"+"প্রশ্ন: "+  ""+AppConstant.detaisData.getQuestion()+"</b></div\n" + "<p style=\"margin-bottom:50px\">"+""+AppConstant.detaisData.getAnswer()+"</p>";
            webView.loadData(htmlString, "text/html; charset=utf-8", "UTF-8");
        }else {
            String htmlString = "<div style=\"color:#069\"><b>"+"প্রশ্ন: "+ AppConstant.detaisData.getTitle()+""+"</b></div\n" + "<p style=\"margin-bottom:50px\">"+""+AppConstant.detaisData.getDescription()+"</p>";
            webView.loadData(htmlString, "text/html; charset=utf-8", "UTF-8");
        }


        String pattern = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date = null;
        String fomateDate = "";

        if(!TextUtils.isEmpty(AppConstant.detaisData.getCreated_at())){
            try {
                date = df.parse(AppConstant.detaisData.getCreated_at());
                fomateDate = df.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            fomateDate = "";
        }


        
        

//        tvTitle.setText(title);
//        tvDescription.setText(content);
        if(AppConstant.detaisData.getUser_detail()!=null){
            if(AppConstant.detaisData.getUser_detail().getName()!=null){
                tvPublisher.setText(AppConstant.detaisData.getUser_detail().getName());
            }
        }
        tvPublishDate.setText(fomateDate);
        tvCountView.setText(AppConstant.detaisData.getView_count());
//        tvCommentCount.setText(AppConstant.detaisData.getComment().size());

    }

}
