package com.imamportal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.model.MoulicBisoy;
import com.imamportal.model.QuizeQuestion;
import com.imamportal.utils.AlertMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizeActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private TextView tvStart,tvScnd,tvShowMin;
    private TextView tvQuiz1,tvQuiz2,tvQuiz3,tvQuiz4,tvQuiz5;
    private TextView tvAns1,tvAns2,tvAns3,tvAns4,tvAns5;
    private LinearLayout linRules,linResSumary,linMainVew,linQues1,linQues2,linQues3,linQues4,linQues5,linTime;

    TabLayout tabQuize;
    private RadioGroup radioGroupAns1,radioGroupAns2,radioGroupAns3,radioGroupAns4,radioGroupAns5;
    private RadioButton ans1option1,ans1option2,ans1option3,ans1option4,ans2option1,ans2option2,ans2option3,ans2option4,
    ans3option1,ans3option2,ans3option3,ans3option4,ans4option1,ans4option2,ans4option3,ans4option4,ans5option1,ans5option2,
            ans5option3,ans5option4;
    int count1=59;
    int count2 = 59;
    private Button btnSubmit;
    int submitPos;

    private String ans1,ans2,ans3,ans4,ans5;
    CountDownTimer cdt = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initUi();

    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        tvStart = (TextView) findViewById(R.id.tvStart);

        tvScnd = (TextView) findViewById(R.id.tvScnd);
        tvShowMin = (TextView) findViewById(R.id.tvShowMin);
        linRules = (LinearLayout) findViewById(R.id.linRules);
        linResSumary = (LinearLayout) findViewById(R.id.linResSumary);
        linTime = (LinearLayout) findViewById(R.id.linTime);
        linTime.setVisibility(View.VISIBLE);



        linQues1 = (LinearLayout) findViewById(R.id.linQues1);
        linQues2 = (LinearLayout) findViewById(R.id.linQues2);
        linQues3 = (LinearLayout) findViewById(R.id.linQues3);
        linQues4 = (LinearLayout) findViewById(R.id.linQues4);
        linQues5 = (LinearLayout) findViewById(R.id.linQues5);

        tvQuiz1 = (TextView) findViewById(R.id.tvQuiz1);
        tvQuiz2 = (TextView) findViewById(R.id.tvQuiz2);
        tvQuiz3 = (TextView) findViewById(R.id.tvQuiz3);
        tvQuiz4 = (TextView) findViewById(R.id.tvQuiz4);
        tvQuiz5 = (TextView) findViewById(R.id.tvQuiz5);

        tvAns1 = (TextView)  findViewById(R.id.tvAns1);
        tvAns2 = (TextView)  findViewById(R.id.tvAns2);
        tvAns3 = (TextView)  findViewById(R.id.tvAns3);
        tvAns4 = (TextView)  findViewById(R.id.tvAns4);
        tvAns5 = (TextView)  findViewById(R.id.tvAns5);

        radioGroupAns1 = (RadioGroup) findViewById(R.id.radioGroupAns1);
        radioGroupAns2 = (RadioGroup) findViewById(R.id.radioGroupAns2);
        radioGroupAns3 = (RadioGroup) findViewById(R.id.radioGroupAns3);
        radioGroupAns4 = (RadioGroup) findViewById(R.id.radioGroupAns4);
        radioGroupAns5 = (RadioGroup) findViewById(R.id.radioGroupAns5);



        ans1option1 = (RadioButton) findViewById(R.id.ans1option1);
        ans1option2 = (RadioButton) findViewById(R.id.ans1option2);
        ans1option3 = (RadioButton) findViewById(R.id.ans1option3);
        ans1option4 = (RadioButton) findViewById(R.id.ans1option4);

        ans2option1 = (RadioButton) findViewById(R.id.ans2option1);
        ans2option2 = (RadioButton) findViewById(R.id.ans2option2);
        ans2option3 = (RadioButton) findViewById(R.id.ans2option3);
        ans2option4 = (RadioButton) findViewById(R.id.ans2option4);


        ans3option1 = (RadioButton) findViewById(R.id.ans3option1);
        ans3option2 = (RadioButton) findViewById(R.id.ans3option2);
        ans3option3 = (RadioButton) findViewById(R.id.ans3option3);
        ans3option4 = (RadioButton) findViewById(R.id.ans3option4);

        ans4option1 = (RadioButton) findViewById(R.id.ans4option1);
        ans4option2 = (RadioButton) findViewById(R.id.ans4option2);
        ans4option3 = (RadioButton) findViewById(R.id.ans4option3);
        ans4option4 = (RadioButton) findViewById(R.id.ans4option4);

        ans5option1 = (RadioButton) findViewById(R.id.ans5option1);
        ans5option2 = (RadioButton) findViewById(R.id.ans5option2);
        ans5option3 = (RadioButton) findViewById(R.id.ans5option3);
        ans5option4 = (RadioButton) findViewById(R.id.ans5option4);


        radioGroupAns1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int select1 = radioGroupAns1.getCheckedRadioButtonId();
                RadioButton rd1 = (RadioButton) radioGroupAns1.findViewById(select1);;
                ans1 = rd1.getText().toString();
            }
        });


        radioGroupAns2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int select2 = radioGroupAns2.getCheckedRadioButtonId();
                RadioButton rd2 = (RadioButton) radioGroupAns2.findViewById(select2);;
                ans2 = rd2.getText().toString();
            }
        });


        radioGroupAns3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int select3 = radioGroupAns3.getCheckedRadioButtonId();
                RadioButton rd3 = (RadioButton) radioGroupAns3.findViewById(select3);;
                ans3 = rd3.getText().toString();
            }
        });


        radioGroupAns4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int select4 = radioGroupAns4.getCheckedRadioButtonId();
                RadioButton rd4 = (RadioButton) radioGroupAns4.findViewById(select4);;
                ans4 = rd4.getText().toString();
            }
        });


        radioGroupAns5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int select5 = radioGroupAns5.getCheckedRadioButtonId();
                RadioButton rd5 = (RadioButton) radioGroupAns5.findViewById(select5);;
                ans5 = rd5.getText().toString();
            }
        });




        tabQuize = (TabLayout) findViewById(R.id.tabQuize);
        tabQuize.setVisibility(View.VISIBLE);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitAnswer();

            }
        });



        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdt.cancel();
                finish();
            }
        });

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linRules.setVisibility(View.GONE);
            }
        });



        final List<QuizeQuestion> quizeQuestionList = new ArrayList<>();

        QuizeQuestion mo = new QuizeQuestion();
        mo.setQuestionName("Question 1");
        mo.setOption1("one");
        mo.setOption2("two");
        mo.setOption3("three");
        mo.setOption4("four");
        mo.setOption5("five");
        quizeQuestionList.add(mo);

        QuizeQuestion mo1 = new QuizeQuestion();
        mo1.setQuestionName("Question 2");
        mo1.setOption1("one sd");
        mo1.setOption2("two");
        mo1.setOption3("three");
        mo1.setOption4("four");
        mo1.setOption5("five");
        quizeQuestionList.add(mo1);

        QuizeQuestion m2 = new QuizeQuestion();
        m2.setQuestionName("Question 3");
        m2.setOption1("one");
        m2.setOption2("two huh");
        m2.setOption3("three");
        m2.setOption4("four");
        m2.setOption5("five");
        quizeQuestionList.add(m2);

        QuizeQuestion m3 = new QuizeQuestion();
        m3.setQuestionName("Question 4");
        m3.setOption1("one");
        m3.setOption2("two");
        m3.setOption3("three fdgg");
        m3.setOption4("four");
        m3.setOption5("five");
        quizeQuestionList.add(m3);

        QuizeQuestion m4 = new QuizeQuestion();
        m4.setQuestionName("Question 5");
        m4.setOption1("one");
        m4.setOption2("two");
        m4.setOption3("three");
        m4.setOption4("four dffh");
        m4.setOption5("five");
        quizeQuestionList.add(m4);


        for (int i = 0; i < quizeQuestionList.size(); i++) {
            tabQuize.addTab(tabQuize.newTab().setText(quizeQuestionList.get(i).getQuestionName()));
        }



        tvQuiz1.setText(quizeQuestionList.get(0).getQuestionName());
        tvQuiz2.setText(quizeQuestionList.get(1).getQuestionName());
        tvQuiz3.setText(quizeQuestionList.get(2).getQuestionName());
        tvQuiz4.setText(quizeQuestionList.get(3).getQuestionName());
        tvQuiz5.setText(quizeQuestionList.get(4).getQuestionName());


        ans1option1.setText(quizeQuestionList.get(0).getOption1());
        ans1option2.setText(quizeQuestionList.get(0).getOption2());
        ans1option3.setText(quizeQuestionList.get(0).getOption3());
        ans1option4.setText(quizeQuestionList.get(0).getOption4());

        ans2option1.setText(quizeQuestionList.get(1).getOption1());
        ans2option2.setText(quizeQuestionList.get(1).getOption2());
        ans2option3.setText(quizeQuestionList.get(1).getOption3());
        ans2option4.setText(quizeQuestionList.get(1).getOption4());

        ans3option1.setText(quizeQuestionList.get(2).getOption1());
        ans3option2.setText(quizeQuestionList.get(2).getOption2());
        ans3option3.setText(quizeQuestionList.get(2).getOption3());
        ans3option4.setText(quizeQuestionList.get(2).getOption4());

        ans4option1.setText(quizeQuestionList.get(3).getOption1());
        ans4option2.setText(quizeQuestionList.get(3).getOption2());
        ans4option3.setText(quizeQuestionList.get(3).getOption3());
        ans4option4.setText(quizeQuestionList.get(3).getOption4());

        ans5option1.setText(quizeQuestionList.get(4).getOption1());
        ans5option2.setText(quizeQuestionList.get(4).getOption2());
        ans5option3.setText(quizeQuestionList.get(4).getOption3());
        ans5option4.setText(quizeQuestionList.get(4).getOption4());

        tabQuize.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {




                int pos= tab.getPosition();
                if(pos==4){
                    btnSubmit.setVisibility(View.VISIBLE);
                }else {
                    btnSubmit.setVisibility(View.GONE);
                }
                qesVisivility(pos);


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        timeCount();
    }

    private void qesVisivility(int pos) {

        if(pos==0){

            linQues1.setVisibility(View.VISIBLE);
            linQues2.setVisibility(View. GONE);
            linQues3.setVisibility(View. GONE);
            linQues4.setVisibility(View. GONE);
            linQues5.setVisibility(View. GONE);
        }else if(pos==1){
            linQues1.setVisibility(View.GONE);
            linQues2.setVisibility(View. VISIBLE);
            linQues3.setVisibility(View. GONE);
            linQues4.setVisibility(View. GONE);
            linQues5.setVisibility(View. GONE);
        }else if(pos==2){
            linQues1.setVisibility(View.GONE);
            linQues2.setVisibility(View. GONE);
            linQues3.setVisibility(View. VISIBLE);
            linQues4.setVisibility(View. GONE);
            linQues5.setVisibility(View. GONE);
        }else if(pos==3){
            linQues1.setVisibility(View.GONE);
            linQues2.setVisibility(View. GONE);
            linQues3.setVisibility(View. GONE);
            linQues4.setVisibility(View. VISIBLE);
            linQues5.setVisibility(View. GONE);
        }else if(pos==4){
            linQues1.setVisibility(View.GONE);
            linQues2.setVisibility(View. GONE);
            linQues3.setVisibility(View. GONE);
            linQues4.setVisibility(View. GONE);
            linQues5.setVisibility(View. VISIBLE);
        }
    }

    private void timeCount() {

        cdt = new CountDownTimer(120000, 1000){
            public void onTick(long millisUntilFinished){

                if(count1>0){
                    tvShowMin.setText("01m:");
                    tvScnd.setText(count1+"s");
                    count1--;
                }else if(count1==0){
                    tvShowMin.setText("00m:");
                    tvScnd.setText(count2+"s");
                    count2--;
                }


                if(count2 == 1){
                    tvShowMin.setText("00m:");
                    tvScnd.setText(00+"s");
                    showMsg(QuizeActivity.this, R.mipmap.ic_launcher_round,"Alert!","কুইজের সময় শেষ হওয়ায় আপনার কুজটি দাখিল করা হচ্ছে।");
                }

            }
            public  void onFinish(){
                if(((Activity) context).isFinishing())
                {
                    return;
                }
            }
        }.start();

    }

    public void showMsg(final Context c, int icon,
                                   final String title, final String message) {
        final AlertDialog.Builder aBuilder = new AlertDialog.Builder(c);
        aBuilder.setTitle(title);
        aBuilder.setIcon(R.mipmap.ic_launcher);
        aBuilder.setMessage(message);

        aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {

                submitAnswer();
                dialog.dismiss();
            }

        });

        aBuilder.show();
    }

    private void submitAnswer() {

        if(ans1!=null){
            tvAns1.setText("আপনার উত্তর: "+ans1);
        }else {
            tvAns1.setText("আপনার উত্তর: "+"No");
        }

        if(ans2!=null){
            tvAns2.setText("আপনার উত্তর: "+ans2);
        }else {
            tvAns2.setText("আপনার উত্তর: "+"No");
        }

        if(ans3!=null){
            tvAns3.setText("আপনার উত্তর: "+ans3);
        }else {
            tvAns3.setText("আপনার উত্তর: "+"No");
        }

        if(ans4!=null){
            tvAns4.setText("আপনার উত্তর: "+ans4);
        }else {
            tvAns4.setText("আপনার উত্তর: "+"No");
        }

        if(ans5!=null){
            tvAns5.setText("আপনার উত্তর: "+ans5);
        }else {
            tvAns5.setText("আপনার উত্তর: "+"No");
        }


        tabQuize.setVisibility(View.GONE);
        linTime.setVisibility(View.GONE);

        tvAns1.setVisibility(View.VISIBLE);
        tvAns2.setVisibility(View.VISIBLE);
        tvAns3.setVisibility(View.VISIBLE);
        tvAns4.setVisibility(View.VISIBLE);
        tvAns5.setVisibility(View.VISIBLE);

        linResSumary.setVisibility(View.VISIBLE);
        linQues1.setVisibility(View.VISIBLE);
        linQues2.setVisibility(View.VISIBLE);
        linQues3.setVisibility(View.VISIBLE);
        linQues4.setVisibility(View.VISIBLE);
        linQues5.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
    }


}
