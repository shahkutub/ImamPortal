package com.imamportal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.imamportal.Adapter.QuestionAnsAdapter;
import com.imamportal.model.QuestionAnsInfo;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerActivity extends Activity {

    Context context;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiestion_ans);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initUi();

    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        questionAnswer();
    }

    private void questionAnswer() {
        RecyclerView recycleViewQuestion = (RecyclerView) findViewById(R.id.recycleViewQuestion);
        List<QuestionAnsInfo> questionAnsInfoList = new ArrayList<>();
        QuestionAnsInfo questionAnsInfo = new QuestionAnsInfo( "Something");
        for (int i = 0; i < 10; i++) {
            questionAnsInfoList.add(i, questionAnsInfo);
        }

        QuestionAnsAdapter questionAnsAdapter = new QuestionAnsAdapter(questionAnsInfoList,context);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recycleViewQuestion.setLayoutManager(horizontalLayoutManager);
        recycleViewQuestion.setAdapter(questionAnsAdapter);

    }

}
