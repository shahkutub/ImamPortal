package com.imamportal.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imamportal.ChatAppActivity;
import com.imamportal.R;

import com.imamportal.model.NotificationInfo;
import com.imamportal.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;


public class FragmentMsg extends Fragment {
    Context context;
    private RecyclerView recyclSantirBani;
    private TextView tvTotalBani,tvName;
    List<NotificationInfo> infoList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_photo, container, false);

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

        recyclSantirBani = (RecyclerView)getView().findViewById(R.id.recyclSantirBani);

        if(AppConstant.notificationResponse!=null){
            infoList = AppConstant.notificationResponse.getMessage_contents();
                    MsgAdapter msgAdapter = new MsgAdapter(infoList,context);
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false);
            recyclSantirBani.setLayoutManager(horizontalLayoutManager);
            recyclSantirBani.setAdapter(msgAdapter);
        }

    }

    public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MyViewHolder> {


        List<NotificationInfo> dataList = new ArrayList<>();
        Context context;


        public MsgAdapter(List<NotificationInfo> santirBaniList, Context context) {
            this.dataList = santirBaniList;
            this.context = context;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvShortDescription;
            private LinearLayout linFullRow;
            public MyViewHolder(View view) {
                super(view);
                tvShortDescription=(TextView) view.findViewById(R.id.tvShortDescription);
                linFullRow=(LinearLayout) view.findViewById(R.id.linFullRow);

            }
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_notification, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final NotificationInfo data = dataList.get(position);
           // holder.tvShortDescription.setText(data.getMessage());

            if(data.getUser_name()!=null){
                holder.tvShortDescription.setText(data.getUser_name()+" আপনাকে "+"''"+data.getMessage()+"''"+" বলেছেন");
            }

            holder.linFullRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.otheruserId = data.getUser_id();
                    startActivity(new Intent(context, ChatAppActivity.class));
                    AppConstant.chatType = "singlechat";
                    AppConstant.otheruserName = data.getUser_name();

                }
            });
        }


        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }


}
