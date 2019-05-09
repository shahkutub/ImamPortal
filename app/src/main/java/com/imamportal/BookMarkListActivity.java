package com.imamportal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.realm.BookmarkContent;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class BookMarkListActivity extends AppCompatActivity{
    Context context;
    private RecyclerView recycler_view_bookmark;
    List<AllBlogpostModel> listSantirbani = new ArrayList<>();
    Realm mRealm = null;
    private ImageView imgBack;
    BookMarkAdapter questionAnsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarklist);
        context= this;

        mRealm.init(context);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        mRealm = Realm.getInstance(config);
        initUi();


    }

    private void initUi() {

        recycler_view_bookmark = (RecyclerView)findViewById(R.id.recycler_view_bookmark);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recycler_view_bookmark.setLayoutManager(horizontalLayoutManager);

        setData();
    }

    private void setData() {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<BookmarkContent> results = realm.where(BookmarkContent.class).findAll();
                //Toast.makeText(context, ""+results.size(), Toast.LENGTH_SHORT).show();
                listSantirbani.clear();
                for (BookmarkContent employee : results) {

                    String content = employee.content; // The String which Need To Be Converted
                    Log.e("content",""+content);

                    AllBlogpostModel convertedObject = new Gson().fromJson(content, AllBlogpostModel.class);

                    listSantirbani.add(convertedObject);

                }
            }
        });

        questionAnsAdapter = new BookMarkAdapter(listSantirbani,context,mRealm);
        recycler_view_bookmark.setAdapter(questionAnsAdapter);

    }


    public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.MyViewHolder> {


        List<AllBlogpostModel> santirBaniList = new ArrayList<>();
        Context context;
        private String name="";
        Realm mRealm = null;

        public BookMarkAdapter(List<AllBlogpostModel> santirBaniList, Context context,Realm mRealm) {
            this.santirBaniList = santirBaniList;
            this.context = context;
            this.mRealm = mRealm;
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
//                    if(AppConstant.activitiname.equalsIgnoreCase("answer")){
//                        AppConstant.detaisData = data;
//                        context.startActivity(new Intent(context, DetailsActivity.class));
////                    AppConstant.dilogDetails(context,data.getQuestion(),data.getAnswer(),""+name,
////                            data.getCreated_at(),data.getView_count(),"","");
//                    }else {
////                    AppConstant.dilogDetails(context,data.getTitle(),data.getDescription(),""+name,
////                            data.getCreated_at(),data.getView_count(),"","");
//                        AppConstant.detaisData = data;
//                        context.startActivity(new Intent(context, DetailsActivity.class));
//                    }

                    AppConstant.detaisData = data;
                    context.startActivity(new Intent(context, DetailsActivity.class));

                }
            });

            holder.linFullView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Gson gsonObj = new Gson();
                    String jsonStr = gsonObj.toJson(data);
                    deleteRecord(jsonStr);
                    return true;
                }
            });

            if(data.getQuestion()!=null||data.getAnswer()!=null){
                holder.tvTitle.setText(data.getQuestion());
                if(data.getAnswer()!=null){
                    holder.tvShortDescription.setText(android.text.Html.fromHtml(data.getAnswer()).toString());
                }
            }else {
                holder.tvTitle.setText(data.getTitle());
                if(data.getDescription()!=null){
                    holder.tvShortDescription.setText(android.text.Html.fromHtml(data.getDescription()).toString());
                }
            }


        }


        @Override
        public int getItemCount()
        {
            return santirBaniList.size();
        }

    }

    public void deleteRecord(final String data) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                BookmarkContent employee = realm.where(BookmarkContent.class).equalTo(BookmarkContent.PROPERTY_CONTENT, data.toString()).findFirst();
                if (employee != null) {
                    employee.deleteFromRealm();
                }
            }
        });
        setData();
    }

}
