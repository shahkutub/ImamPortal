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

import com.imamportal.Adapter.AlquranAldadithPostAdapter;
import com.imamportal.R;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;


public class Fragment333QuestionAnswer extends Fragment {

    private RecyclerView recyclSantirBani;
    private TextView tvTotalBani,tvName;
    Context context;

    public Fragment333QuestionAnswer() {


    }


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

        List<AlquranAlhadits> dataListList = new ArrayList<>();

        for (int i = 0; i < AppConstant.listAlqranAlhadith.size(); i++) {

                if(AppConstant.listAlqranAlhadith.get(i).getCategory_id().equalsIgnoreCase("41")){
                    dataListList.add(AppConstant.listAlqranAlhadith.get(i));
                }

        }

        int size = dataListList.size();
        tvTotalBani.setText("সর্বমোট "+AppConstant.activitiname+size+" টি");

        AlquranAldadithPostAdapter questionAnsAdapter = new AlquranAldadithPostAdapter(dataListList,context);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recyclSantirBani.setLayoutManager(horizontalLayoutManager);
        recyclSantirBani.setAdapter(questionAnsAdapter);
    }

}
