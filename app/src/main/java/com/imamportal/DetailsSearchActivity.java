package com.imamportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.google.gson.Gson;
import com.imamportal.Adapter.CommentAdapter;
import com.imamportal.model.BlogPostSearchDetails;
import com.imamportal.model.CommentModel;
import com.imamportal.model.CommentResponse;
import com.imamportal.model.SignUpResponse;
import com.imamportal.realm.BookmarkContent;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PersistData;
import com.imamportal.utils.ZoomTextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchAnimListener;
import com.mahfa.dnswitch.DayNightSwitchListener;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsSearchActivity extends AppCompatActivity{

    Context context;
    ///String title, content, publisher, publishDate, viewcount,  likecount, commentcount;

    private ImageView imgLike,imgUpDown;
    TextView tvCommentCount,tvCountView,tvLikeCount;
    private RecyclerView recyclerViewComment;
    private boolean liked = false;
    private String comment;
    LinearLayout linLike,linComment,linLikeViw,linComClick,linUpDown;
    private AppCompatButton btnPost;
    private EditText etAsk;
    ZoomTextView tvDetails;
    ImageView imgBookmark;
    Realm mRealm = null;
    private DayNightSwitch day_night_switch;
    public static final String TAG = "MainActivity";
    public static final String KEY_DAY_NIGHT_SWITCH_STATE = "day_night_switch_state";
    private RelativeLayout relDetail;
    private BlogPostSearchDetails blogPostSearchDetails = new BlogPostSearchDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = this;
        mRealm.init(context);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        mRealm = Realm.getInstance(config);

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
        imgUpDown = (ImageView)findViewById(R.id.imgUpDown);
        linLike = (LinearLayout) findViewById(R.id.linLike);
        btnPost = (AppCompatButton) findViewById(R.id.btnPost);
        etAsk = (EditText) findViewById(R.id.etAsk);
        linComment = (LinearLayout) findViewById(R.id.linComment);
        linLikeViw = (LinearLayout) findViewById(R.id.linLikeViw);
        linComClick = (LinearLayout) findViewById(R.id.linComClick);
        linUpDown = (LinearLayout) findViewById(R.id.linUpDown);

        linUpDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linComment.getVisibility() == View.GONE){
                    imgUpDown.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    linComment.setVisibility(View.VISIBLE);
                    linLikeViw.setVisibility(View.VISIBLE);


                }else {
                    imgUpDown.setImageResource(R.drawable.ic_expand_less_black_24dp);

                    linComment.setVisibility(View.GONE);
                    linLikeViw.setVisibility(View.GONE);
                                }


            }
        });

        linComClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linComment.setVisibility(View.VISIBLE);
                linLikeViw.setVisibility(View.VISIBLE);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = etAsk.getText().toString();
                if(TextUtils.isEmpty(PersistData.getStringData(context,AppConstant.loginUserid))){
                    startActivity(new Intent(context,LoginActivity.class));
                }else if(TextUtils.isEmpty(comment)){
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

                    if(TextUtils.isEmpty(PersistData.getStringData(context,AppConstant.loginUserid))){
                        startActivity(new Intent(context,LoginActivity.class));
                    }else {
                        likePost();
                    }

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

                String text = blogPostSearchDetails.getTitle()+"\n"+""+tvDetails.getText().toString();
                Log.e("text",""+text);
                ShareCompat.IntentBuilder.from(DetailsSearchActivity.this)
                        .setType("text/plain")
                        .setChooserTitle("Share")
                        .setText(Html.fromHtml(text).toString())
                        .startChooser();
            }
        });

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);

        tvDetails = (ZoomTextView)findViewById(R.id.tvDetails);
//        tvDetails.setText(Html.fromHtml(AppConstant.detaisData.getQuestion()).toString());
        tvDetails.setMovementMethod(new ScrollingMovementMethod());


//        if(AppConstant.activitiname.equalsIgnoreCase("answer")){
//            String htmlString = "<div style=\"color:#000000\"><b>"+"প্রশ্ন: "+  ""+AppConstant.detaisData.getQuestion()+"</b></div\n" + "<p style=\"margin-bottom:50px\">"+""+AppConstant.detaisData.getAnswer()+"</p>";
//            webView.loadData(htmlString, "text/html; charset=utf-8", "UTF-8");
//
//            tvDetails.setText(Html.fromHtml(htmlString).toString());
//
//        }else {
//            String htmlString = "<div style=\"color:#000000\"><b>"+"প্রশ্ন: "+ AppConstant.detaisData.getTitle()+""+"</b></div\n" + "<p style=\"margin-bottom:50px\">"+""+AppConstant.detaisData.getDescription()+"</p>";
//            webView.loadData(htmlString, "text/html; charset=utf-8", "UTF-8");
//            tvDetails.setText(Html.fromHtml(htmlString).toString());
//        }



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

        imgBookmark = (ImageView)findViewById(R.id.imgBookmark);
        Gson gsonObj = new Gson();
        // converts object to json string
        String jsonStr = gsonObj.toJson(AppConstant.detaisData);
        BookmarkContent content = mRealm.where(BookmarkContent.class).equalTo(BookmarkContent.PROPERTY_CONTENT, jsonStr).findFirst();
        if(content!=null){
            //imgBookmark.setVisibility(View.GONE);
        }

        imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookMark();
            }
        });


        day_night_switch = (DayNightSwitch) findViewById(R.id.day_night_switch);
        day_night_switch.setDuration(450);

        relDetail = (RelativeLayout)findViewById(R.id.relDetail);

        day_night_switch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                //Log.d(TAG, "onSwitch() called with: is_night = [" + is_night + "]");
                if (is_night){
                    relDetail.setBackgroundColor(Color.parseColor("#000000"));
                    tvDetails.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    relDetail.setBackgroundColor(Color.parseColor("#ffffff"));
                    tvDetails.setTextColor(Color.parseColor("#000000"));
                }



            }
        });

        day_night_switch.setAnimListener(new DayNightSwitchAnimListener() {
            @Override
            public void onAnimStart() {
               // Log.d(TAG, "onAnimStart() called");
            }

            @Override
            public void onAnimEnd() {
               // Log.d(TAG, "onAnimEnd() called");
            }

            @Override
            public void onAnimValueChanged(float value) {
//                tvDetails.setAlpha(value);
//                tvDetails.setTextColor(Color.parseColor("#000"));
                // Log.d(TAG, "onAnimValueChanged() called with: value = [" + value + "]");
            }
        });


        getSearchDetail();
    }



    private void addBookMark() {

        try {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    try {
                        if (!tvDetails.getText().toString().trim().isEmpty()) {
                            BookmarkContent bookmarkContent = new BookmarkContent();
//                            bookmarkContent.content = ""+tvDetails.getText();
//                            bookmarkContent.title = ""+AppConstant.detaisData.getTitle();

                            Gson gsonObj = new Gson();
                            // converts object to json string
                            String jsonStr = gsonObj.toJson(blogPostSearchDetails);
                            bookmarkContent.content = ""+jsonStr;
                            //realm.copyToRealm(bookmarkContent);
                            realm.insert(bookmarkContent);
                            Toast.makeText(context, "Added to bookmark", Toast.LENGTH_SHORT).show();
                            imgBookmark.setVisibility(View.GONE);

                        }

                    } catch (RealmPrimaryKeyConstraintException e) {
                        Toast.makeText(getApplicationContext(), "Primary Key exists, Press Update instead", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } finally {
            if (mRealm != null) {
               // mRealm.close();
            }
        }

        //readBookMark();
    }


//    private void readBookMark() {
//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                RealmResults<BookmarkContent> results = realm.where(BookmarkContent.class).findAll();
//                Toast.makeText(context, ""+results.size(), Toast.LENGTH_SHORT).show();
//                tvDetails.setText("");
//                for (BookmarkContent employee : results) {
//                    tvDetails.append(BookmarkContent.PROPERTY_CONTENT);
//                }
//            }
//        });
//    }






    private void likePost() {

        Log.e("user_id",""+PersistData.getStringData(context,AppConstant.loginUserid));
        Log.e("id",""+AppConstant.detaisData.getId());
        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<String> userCall = api.likepost("Bearer"+ PersistData.getStringData(context, AppConstant.loginToken),PersistData.getStringData(context,AppConstant.loginUserid),AppConstant.searchId);
        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body()!=null){
                    liked = true;
                    imgLike.setImageResource(R.drawable.ic_like_select);
                    int like = AppConstant.detaisData.getLike_post().size();
                    tvLikeCount.setText(like+1+"");
                }

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
        Call<CommentResponse> userCall = api.commentepost("Bearer "+ PersistData.getStringData(context, AppConstant.loginToken),PersistData.getStringData(context,AppConstant.loginUserid),AppConstant.searchId,comment);
        userCall.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {

                if(response.body()!=null){
                    int comments = AppConstant.detaisData.getComment().size();
                    tvCommentCount.setText(comments+1+"");

                    CommentModel commentModel = new CommentModel();
                    commentModel.setComment(comment);

                    AppConstant.detaisData.getComment().add(commentModel);

                    recyclerViewComment = (RecyclerView) findViewById(R.id.recyclerViewComment);
                    CommentAdapter questionAnsAdapter = new CommentAdapter(AppConstant.detaisData.getComment(),context);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                            LinearLayoutManager.VERTICAL, false);
                    recyclerViewComment.setLayoutManager(horizontalLayoutManager);
                    recyclerViewComment.setAdapter(questionAnsAdapter);

                }

            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {

            }
        });


    }



//    private void getSearchDetails() {
//
//        if(!NetInfo.isOnline(context)){
//            AlertMessage.showMessage(context,"Alert!","No internet connection!");
//        }
//        final ProgressDialog pd = new ProgressDialog(context);
//        pd.setMessage("Loading data....");
//        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pd.show();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Api.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Api api = retrofit.create(Api.class);
//        Call<BlogPostSearchDetails> userCall = api.blog_post_description("api/blog_post_description/"+AppConstant.searchId);
//        userCall.enqueue(new Callback<BlogPostSearchDetails>() {
//            @Override
//            public void onResponse(Call<BlogPostSearchDetails> call, Response<BlogPostSearchDetails> response) {
//
//                pd.dismiss();
//                if(response.body()!=null){
//
//                    blogPostSearchDetails = response.body();
//                    if(blogPostSearchDetails.getDescription()!=null){
//                        String htmlString = "<div style=\"color:#000000\"><b>"+""+  ""+blogPostSearchDetails.getTitle()+"</b></div\n" + "<p style=\"margin-bottom:50px\">"+""+blogPostSearchDetails.getDescription()+"</p>";
//
//                        tvDetails.setText(Html.fromHtml(htmlString).toString());
//
//                    }else {
////                        String htmlString = "<div style=\"color:#000000\"><b>"+""+ AppConstant.detaisData.getTitle()+""+"</b></div\n" + "<p style=\"margin-bottom:50px\">"+""+AppConstant.detaisData.getDescription()+"</p>";
////                        tvDetails.setText(Html.fromHtml(htmlString).toString());
//                    }
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<BlogPostSearchDetails> call, Throwable t) {
//                pd.dismiss();
//            }
//        });
//
//
//    }

    protected void getSearchDetail() {
        String url = Api.BASE_URL+"api/blog_post_description/"+AppConstant.searchId;

        if (!NetInfo.isOnline(context)) {
            AlertMessage.showMessage(context, "Alert",
                    "Check Internet");
            return;
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setCancelable(false);
        pd.setMessage("Data uploading...");
        pd.show();

        final AsyncHttpClient client = new AsyncHttpClient();

        // String credentials = Username + ":" + Password;
        // String base64EncodedCredentials =
        // Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        // client.addHeader("Authorization", "Basic " +
        // base64EncodedCredentials);

//        final RequestParams param = new RequestParams();
//
//        try {
//
//            //String path = PersistData.getStringData(con, AppConstant.path);
//            param.put("data",data);
//            param.put("image",new File(filePath));
//
//
//        } catch (final Exception e1) {
//            e1.printStackTrace();
//        }

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] response) {
                // called when response HTTP status is "200 OK"

                pd.dismiss();

//                Toast.makeText(context, "Prescription Uploaded",
//                        Toast.LENGTH_LONG).show();

                Log.e("resposne ", ">>" + new String(response));


                String dataRes = new String(response).substring(1,new String(response).length()-1);

                Log.e("resposne ", ">>" + new String(dataRes));

                Gson g = new Gson();
                blogPostSearchDetails =  g.fromJson(new String(dataRes), BlogPostSearchDetails.class);

                if(blogPostSearchDetails!=null){

                        if(blogPostSearchDetails!=null){
                            String htmlString = "<div style=\"color:#000000\"><b>"+""+  ""+blogPostSearchDetails.getTitle()+"</b></div\n" + "<p style=\"margin-bottom:50px\">"+""+blogPostSearchDetails.getDescription()+"</p>";

                            tvDetails.setText(Html.fromHtml(htmlString).toString());

                        }else {
//                        String htmlString = "<div style=\"color:#000000\"><b>"+""+ AppConstant.detaisData.getTitle()+""+"</b></div\n" + "<p style=\"margin-bottom:50px\">"+""+AppConstant.detaisData.getDescription()+"</p>";
//                        tvDetails.setText(Html.fromHtml(htmlString).toString());
                        }


                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                // Log.e("errorResponse", new String(errorResponse));

                pd.dismiss();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried

            }
        });

    }

}
