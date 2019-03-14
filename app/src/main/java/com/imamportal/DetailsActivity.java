package com.imamportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.Adapter.AllCommonPostAdapter;
import com.imamportal.Adapter.CommentAdapter;
import com.imamportal.model.AudioModel;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PersistData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {

    Context context;
    ///String title, content, publisher, publishDate, viewcount,  likecount, commentcount;

    private ImageView imgLike;
    TextView tvCommentCount,tvCountView,tvLikeCount;
    private RecyclerView recyclerViewComment;
    private boolean liked = false;
    private String comment;
    LinearLayout linLike,linComment,linComClick;
    private AppCompatButton btnPost;
    private EditText etAsk;

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
        tvCountView = (TextView)findViewById(R.id.tvCountView);
        tvLikeCount = (TextView)findViewById(R.id.tvLikeCount);
        tvCommentCount = (TextView)findViewById(R.id.tvCommentCount);
        ImageView imgShare = (ImageView)findViewById(R.id.imgShare);
        imgLike = (ImageView)findViewById(R.id.imgLike);
        linLike = (LinearLayout) findViewById(R.id.linLike);
        btnPost = (AppCompatButton) findViewById(R.id.btnPost);
        etAsk = (EditText) findViewById(R.id.etAsk);
        linComment = (LinearLayout) findViewById(R.id.linComment);
        linComClick = (LinearLayout) findViewById(R.id.linComClick);

        linComClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linComment.setVisibility(View.VISIBLE);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = etAsk.getText().toString();
                if(TextUtils.isEmpty(comment)){
                    Toast.makeText(context, "কমেন্ট লিখুন", Toast.LENGTH_SHORT).show();
                }else {
                    commentPost();
                }
            }
        });

        tvLikeCount.setText(AppConstant.detaisData.getLike_post().size()+"");
        for (int i = 0; i <AppConstant.detaisData.getLike_post().size() ; i++) {
            if(PersistData.getStringData(context,AppConstant.loginUserid).equalsIgnoreCase
                    (AppConstant.detaisData.getLike_post().get(i).getUser_id())){
                liked = true;
                imgLike.setImageResource(R.drawable.ic_like_select);
            }
        }

        linLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!liked){
                    likePost();
                }
            }
        });

        recyclerViewComment = (RecyclerView) findViewById(R.id.recyclerViewComment);
        CommentAdapter questionAnsAdapter = new CommentAdapter(AppConstant.detaisData.getComment(),context);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recyclerViewComment.setLayoutManager(horizontalLayoutManager);
        recyclerViewComment.setAdapter(questionAnsAdapter);

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = AppConstant.detaisData.getTitle()+"\n"+""+AppConstant.detaisData.getDescription().toString();
                Log.e("text",""+text);
                ShareCompat.IntentBuilder.from(DetailsActivity.this)
                        .setType("text/plain")
                        .setChooserTitle("Share")
                        .setText(Html.fromHtml(text).toString())
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
        tvCommentCount.setText(AppConstant.detaisData.getComment().size()+"");

    }


    private void likePost() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<String> userCall = api.likepost(PersistData.getStringData(context,AppConstant.loginUserid));
        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                liked = true;
                imgLike.setImageResource(R.drawable.ic_like_select);
                int like = AppConstant.detaisData.getLike_post().size();
                tvLikeCount.setText(like+1+"");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }


    private void commentPost() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<String> userCall = api.commentepost(PersistData.getStringData(context,AppConstant.loginUserid),AppConstant.detaisData.getCategory_id(),comment);
        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                
                int comment = AppConstant.detaisData.getComment().size();
                tvCommentCount.setText(comment+1+"");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }


}
