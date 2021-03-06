package com.imamportal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imamportal.Adapter.MsgAdapter;
import com.imamportal.R;
import com.imamportal.model.NotificationInfo;
import com.imamportal.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;


public class FragmentComment extends Fragment {
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
            infoList = AppConstant.notificationResponse.getComment_notification_contents();
                    MsgAdapter msgAdapter = new MsgAdapter(infoList,context);
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false);
            recyclSantirBani.setLayoutManager(horizontalLayoutManager);
            recyclSantirBani.setAdapter(msgAdapter);
        }

    }

}
