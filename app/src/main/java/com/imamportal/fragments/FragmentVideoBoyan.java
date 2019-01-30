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

import com.imamportal.Adapter.AudioAdapter;
import com.imamportal.Adapter.VideoAdapter;
import com.imamportal.R;
import com.imamportal.model.AudioModel;
import com.imamportal.model.VideoModel;
import com.imamportal.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;


public class FragmentVideoBoyan extends Fragment {

    private RecyclerView recyclSantirBani;
    private TextView tvTotalBani,tvName;
    Context context;


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

        List<VideoModel> dataListList = new ArrayList<>();

        for (int i = 0; i < AppConstant.listVideo.size(); i++) {

            if(AppConstant.listVideo.get(i).getCategory_id()!=null){
                if(AppConstant.listVideo.get(i).getCategory_id().equalsIgnoreCase("15")){
                    dataListList.add(AppConstant.listVideo.get(i));
                }
            }

        }

        int size = dataListList.size();
        tvTotalBani.setText("সর্বমোট "+AppConstant.activitiname+size+" টি");

        VideoAdapter questionAnsAdapter = new VideoAdapter(dataListList,context);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recyclSantirBani.setLayoutManager(horizontalLayoutManager);
        recyclSantirBani.setAdapter(questionAnsAdapter);

    }

}
