package com.imamportal.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imamportal.Adapter.AllCommonPostAdapter;
import com.imamportal.Adapter.AlquranAldadithPostAdapter;
import com.imamportal.JobCirculerActivity;
import com.imamportal.R;
import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.model.JobPortalModel;
import com.imamportal.model.QuestionAnswerModel;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentBtaionQuestionAnswer extends Fragment {

    private RecyclerView recyclSantirBani;
    private TextView tvTotalBani,tvName;
    Context context;
    private List<QuestionAnswerModel> listQuestionAns= new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_quran, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initUi();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initUi() {

        tvTotalBani = (TextView) getView().findViewById(R.id.tvTotalBani);
        recyclSantirBani = (RecyclerView) getView().findViewById(R.id.recyclSantirBani);

        getQuestionAns();
    }

    private void getQuestionAns() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<QuestionAnswerModel>> userCall = api.querylist();
        userCall.enqueue(new Callback<List<QuestionAnswerModel>>() {
            @Override
            public void onResponse(Call<List<QuestionAnswerModel>> call, Response<List<QuestionAnswerModel>> response) {
                pd.dismiss();

                listQuestionAns = response.body();
                if(listQuestionAns.size()>0){
                    int size = listQuestionAns.size();
                    tvTotalBani.setText("সর্বমোট "+AppConstant.activitiname+size+" টি");
                    QuestionAnsAdapter questionAnsAdapter = new QuestionAnsAdapter(listQuestionAns,context);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                            LinearLayoutManager.VERTICAL, false);
                    recyclSantirBani.setLayoutManager(horizontalLayoutManager);
                    recyclSantirBani.setAdapter(questionAnsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<QuestionAnswerModel>> call, Throwable t) {
                pd.dismiss();
            }
        });


    }


    private class QuestionAnsAdapter extends RecyclerView.Adapter<QuestionAnsAdapter.MyViewHolder> {


        List<QuestionAnswerModel> santirBaniList = new ArrayList<>();
        Context context;


        public QuestionAnsAdapter(List<QuestionAnswerModel> santirBaniList, Context context) {
            this.santirBaniList = santirBaniList;
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
        public QuestionAnsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_common_post, parent, false);

            return new QuestionAnsAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final QuestionAnsAdapter.MyViewHolder holder, final int position) {

            final QuestionAnswerModel data = santirBaniList.get(position);
//            holder.tvPublishDate.setText(data.getCreated_at());
//            if(data.getUser_detail()!=null){
//                holder.tvPublisher.setText(data.getUser_detail().getName());
//            }
//            holder.tvTitle.setText(data.getJob_title());
//            holder.tvShortDescription.setText(data.getJob_description());
//            //holder.tvViewCount.setText(data.getView_count());
//
//            if(!TextUtils.isEmpty(data.getFile())){
//                holder.imgFile.setVisibility(View.VISIBLE);
//            }
//
//            holder.imgFile.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("http://192.168.0.119/imamportal/public/job_file/"+data.getFile())));
//                }
//            });

        }


        @Override
        public int getItemCount()
        {
            return santirBaniList.size();
        }
    }


}
