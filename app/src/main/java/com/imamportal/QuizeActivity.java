package com.imamportal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.imamportal.model.MoulicBisoy;
import com.imamportal.model.QuizeQuestion;
import com.imamportal.model.QuizeQuistionResponse;
import com.imamportal.model.SignUpResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PersistData;
import com.imamportal.utils.PersistentUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizeActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack,imgWrong1,imgWrong2,imgWrong3,imgWrong4,imgWrong5,imgWright1,imgWright2,imgWright3,imgWright4,imgWright5;
    private TextView tvStart,tvScnd,tvShowMin,tvRightAns,tvRongAns,tvTotalNumber;
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
    QuizeQuistionResponse responsData = new QuizeQuistionResponse();
    QuizeQuistionResponse submitResponse = new QuizeQuistionResponse();

    List<String> wrightWrong = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        if(PersistData.getStringData(context,AppConstant.loginToken)!=null){
//
//        }else {
//
//        }

        //Log.e("usertoken",""+PersistData.getStringData(context,AppConstant.loginToken));
        getQuize();

        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cdt!=null){
                    cdt.cancel();
                }
                finish();
            }
        });

    }

    private void initUi(QuizeQuistionResponse responsData) {
        imgWrong1 = (ImageView)findViewById(R.id.imgWrong1);
        imgWrong2 = (ImageView)findViewById(R.id.imgWrong2);
        imgWrong3 = (ImageView)findViewById(R.id.imgWrong3);
        imgWrong4 = (ImageView)findViewById(R.id.imgWrong4);
        imgWrong5 = (ImageView)findViewById(R.id.imgWrong5);

        imgWright1 = (ImageView)findViewById(R.id.imgWright1);
        imgWright2 = (ImageView)findViewById(R.id.imgWright2);
        imgWright3 = (ImageView)findViewById(R.id.imgWright3);
        imgWright4 = (ImageView)findViewById(R.id.imgWright4);
        imgWright5 = (ImageView)findViewById(R.id.imgWright5);

        tvStart = (TextView) findViewById(R.id.tvStart);

        tvScnd = (TextView) findViewById(R.id.tvScnd);
        tvShowMin = (TextView) findViewById(R.id.tvShowMin);
        tvRightAns = (TextView) findViewById(R.id.tvRightAns);
        tvRongAns = (TextView) findViewById(R.id.tvRongAns);
        tvTotalNumber = (TextView) findViewById(R.id.tvTotalNumber);

        linRules = (LinearLayout) findViewById(R.id.linRules);
        linResSumary = (LinearLayout) findViewById(R.id.linResSumary);
        linTime = (LinearLayout) findViewById(R.id.linTime);
        linTime.setVisibility(View.VISIBLE);



        linQues1 = (LinearLayout) findViewById(R.id.linQues1);
        linQues1.setVisibility(View.VISIBLE);

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





        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linRules.setVisibility(View.GONE);
            }
        });



        final List<QuizeQuestion> quizeQuestionList = new ArrayList<>();

        QuizeQuestion mo = new QuizeQuestion();
        mo.setQuestionName(responsData.getQuestions().get(0).getTitle());
        mo.setOption1(responsData.getQuestions().get(0).getQuiz_question_option().get(0).getOption_title());
        mo.setOption2(responsData.getQuestions().get(0).getQuiz_question_option().get(1).getOption_title());
        mo.setOption3(responsData.getQuestions().get(0).getQuiz_question_option().get(2).getOption_title());
        mo.setOption4(responsData.getQuestions().get(0).getQuiz_question_option().get(3).getOption_title());
        quizeQuestionList.add(mo);

        QuizeQuestion mo1 = new QuizeQuestion();
        mo1.setQuestionName(responsData.getQuestions().get(1).getTitle());
        mo1.setOption1(responsData.getQuestions().get(1).getQuiz_question_option().get(0).getOption_title());
        mo1.setOption2(responsData.getQuestions().get(1).getQuiz_question_option().get(1).getOption_title());
        mo1.setOption3(responsData.getQuestions().get(1).getQuiz_question_option().get(2).getOption_title());
        mo1.setOption4(responsData.getQuestions().get(1).getQuiz_question_option().get(3).getOption_title());
        quizeQuestionList.add(mo1);

        QuizeQuestion m2 = new QuizeQuestion();
        m2.setQuestionName(responsData.getQuestions().get(2).getTitle());
        m2.setOption1(responsData.getQuestions().get(2).getQuiz_question_option().get(0).getOption_title());
        m2.setOption2(responsData.getQuestions().get(2).getQuiz_question_option().get(1).getOption_title());
        m2.setOption3(responsData.getQuestions().get(2).getQuiz_question_option().get(2).getOption_title());
        m2.setOption4(responsData.getQuestions().get(2).getQuiz_question_option().get(3).getOption_title());
        quizeQuestionList.add(m2);

        QuizeQuestion m3 = new QuizeQuestion();
        m3.setQuestionName(responsData.getQuestions().get(3).getTitle());
        m3.setOption1(responsData.getQuestions().get(3).getQuiz_question_option().get(0).getOption_title());
        m3.setOption2(responsData.getQuestions().get(3).getQuiz_question_option().get(1).getOption_title());
        m3.setOption3(responsData.getQuestions().get(3).getQuiz_question_option().get(2).getOption_title());
        m3.setOption4(responsData.getQuestions().get(3).getQuiz_question_option().get(3).getOption_title());
        quizeQuestionList.add(m3);

        QuizeQuestion m4 = new QuizeQuestion();
        m4.setQuestionName(responsData.getQuestions().get(4).getTitle());
        m4.setOption1(responsData.getQuestions().get(4).getQuiz_question_option().get(0).getOption_title());
        m4.setOption2(responsData.getQuestions().get(4).getQuiz_question_option().get(1).getOption_title());
        m4.setOption3(responsData.getQuestions().get(4).getQuiz_question_option().get(2).getOption_title());
        m4.setOption4(responsData.getQuestions().get(4).getQuiz_question_option().get(3).getOption_title());
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
                    if(context !=null){
                        if(!((Activity) context).isFinishing())
                        {
                            //show dialog
                            showMsg(context, R.mipmap.ic_launcher_round,"Alert!","কুইজের সময় শেষ হওয়ায় আপনার কুজটি দাখিল করা হচ্ছে।");
                        }
                    }
                }

            }

            @Override
            public void onFinish() {

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
        if (cdt!=null){
            cdt.cancel();
        }
        JsonArray jsonArrayQuiestion = new JsonArray();
        JSONObject jsonObjectAnswer = new JSONObject();
        if(ans1!=null){

            tvAns1.setText("আপনার উত্তর: "+ans1);
            jsonArrayQuiestion.add(responsData.getQuestions().get(0).getId());
            for (int i = 0; i <responsData.getQuestions().get(0).getQuiz_question_option().size() ; i++) {
                if(ans1.equalsIgnoreCase(responsData.getQuestions().get(0).getQuiz_question_option().get(i).getOption_title())){
                    try {
                        jsonObjectAnswer.put(responsData.getQuestions().get(0).getId(),responsData.getQuestions().get(0).getQuiz_question_option().get(i).getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(responsData.getQuestions().get(0).getQuiz_question_option().get(i).getRight_answer().equalsIgnoreCase("1")){
                       // imgWright1.setVisibility(View.VISIBLE);
                        wrightWrong.add("1");
                    }else {
                        //imgWrong1.setVisibility(View.VISIBLE);
                        wrightWrong.add("0");
                    }
                }



            }

        }else {
            tvAns1.setText("আপনার উত্তর: "+"No");
        }

        //2
        if(ans2!=null){
            tvAns2.setText("আপনার উত্তর: "+ans2);

            jsonArrayQuiestion.add(responsData.getQuestions().get(1).getId());
            for (int i = 0; i <responsData.getQuestions().get(1).getQuiz_question_option().size() ; i++) {
                if(ans2.equalsIgnoreCase(responsData.getQuestions().get(1).getQuiz_question_option().get(i).getOption_title())){
                    try {
                        jsonObjectAnswer.put(responsData.getQuestions().get(1).getId(),responsData.getQuestions().get(1).getQuiz_question_option().get(i).getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(responsData.getQuestions().get(1).getQuiz_question_option().get(i).getRight_answer().equalsIgnoreCase("1")){
                        wrightWrong.add("1");
                    }else {
                        //imgWrong1.setVisibility(View.VISIBLE);
                        wrightWrong.add("0");
                    }
                }
            }


        }else {
            tvAns2.setText("আপনার উত্তর: "+"No");
        }

        if(ans3!=null){
            tvAns3.setText("আপনার উত্তর: "+ans3);

            jsonArrayQuiestion.add(responsData.getQuestions().get(2).getId());
            for (int i = 0; i <responsData.getQuestions().get(2).getQuiz_question_option().size() ; i++) {
                if(ans2.equalsIgnoreCase(responsData.getQuestions().get(2).getQuiz_question_option().get(i).getOption_title())){
                    try {
                        jsonObjectAnswer.put(responsData.getQuestions().get(2).getId(),responsData.getQuestions().get(2).getQuiz_question_option().get(i).getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(responsData.getQuestions().get(2).getQuiz_question_option().get(i).getRight_answer().equalsIgnoreCase("1")){
                        wrightWrong.add("1");
                    }else {
                        //imgWrong1.setVisibility(View.VISIBLE);
                        wrightWrong.add("0");
                    }
                }
            }


        }else {
            tvAns3.setText("আপনার উত্তর: "+"No");
        }

        if(ans4!=null){
            tvAns4.setText("আপনার উত্তর: "+ans4);

            jsonArrayQuiestion.add(responsData.getQuestions().get(3).getId());
            for (int i = 0; i <responsData.getQuestions().get(3).getQuiz_question_option().size() ; i++) {
                if(ans2.equalsIgnoreCase(responsData.getQuestions().get(3).getQuiz_question_option().get(i).getOption_title())){
                    try {
                        jsonObjectAnswer.put(responsData.getQuestions().get(3).getId(),responsData.getQuestions().get(3).getQuiz_question_option().get(i).getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(responsData.getQuestions().get(3).getQuiz_question_option().get(i).getRight_answer().equalsIgnoreCase("1")){
                        wrightWrong.add("1");
                    }else {
                        //imgWrong1.setVisibility(View.VISIBLE);
                        wrightWrong.add("0");
                    }
                }
            }

        }else {
            tvAns4.setText("আপনার উত্তর: "+"No");
        }

        if(ans5!=null){
            tvAns5.setText("আপনার উত্তর: "+ans5);

            jsonArrayQuiestion.add(responsData.getQuestions().get(4).getId());
            for (int i = 0; i <responsData.getQuestions().get(4).getQuiz_question_option().size() ; i++) {
                if(ans2.equalsIgnoreCase(responsData.getQuestions().get(4).getQuiz_question_option().get(i).getOption_title())){
                    try {
                        jsonObjectAnswer.put(responsData.getQuestions().get(4).getId(),responsData.getQuestions().get(4).getQuiz_question_option().get(i).getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(responsData.getQuestions().get(4).getQuiz_question_option().get(i).getRight_answer().equalsIgnoreCase("1")){
                        wrightWrong.add("1");
                    }else {
                        //imgWrong1.setVisibility(View.VISIBLE);
                        wrightWrong.add("0");
                    }
                }
            }

        }else {
            tvAns5.setText("আপনার উত্তর: "+"No");
        }

        Log.e("jsonArrayQuiestion",""+jsonArrayQuiestion.toString());
        Log.e("jsonObjectAnswer",""+jsonObjectAnswer.toString());

        submitQuize(jsonArrayQuiestion.toString(),jsonObjectAnswer.toString());

    }


    private void getQuize() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<QuizeQuistionResponse> userCall = api.quiz_start("Bearer "+PersistData.getStringData(context,AppConstant.loginToken),"1");
        userCall.enqueue(new Callback<QuizeQuistionResponse>() {
            @Override
            public void onResponse(Call<QuizeQuistionResponse> call, Response<QuizeQuistionResponse> response) {
                pd.dismiss();

                responsData = response.body();

                if(responsData!=null){
                    if(responsData.getQuestions()!=null){
                        Log.e("datasize",""+responsData.getQuestions().size());
                        initUi(responsData);
                        timeCount();
                    }

                }else {
                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<QuizeQuistionResponse> call, Throwable t) {

                pd.dismiss();
            }
        });


    }

    private void submitQuize(String question,String answer) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<QuizeQuistionResponse> userCall = api.quiz_submit("Bearer "+PersistData.getStringData(context,
                AppConstant.loginToken),question,answer);
        userCall.enqueue(new Callback<QuizeQuistionResponse>() {
            @Override
            public void onResponse(Call<QuizeQuistionResponse> call, Response<QuizeQuistionResponse> response) {
                pd.dismiss();

                submitResponse = response.body();

                if(submitResponse!=null){
                    if(submitResponse.getQuestions()!=null){
                        Log.e("datasize",""+submitResponse.getQuestions().size());
                        showAnswerView();
                        tvRongAns.setText("ভুল উত্তর - "+submitResponse.getWrong_answer());
                        tvRightAns.setText("সঠিক উত্তর - "+submitResponse.getRight_answer());
                        tvTotalNumber.setText("মোট নাম্বার - "+Integer.parseInt(submitResponse.getRight_answer())*20+"");
                    }

                }else {
                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<QuizeQuistionResponse> call, Throwable t) {

                pd.dismiss();
            }
        });


    }

    private void showAnswerView() {
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

        for (int i = 0; i <wrightWrong.size() ; i++) {
            if(i==0){
                if(wrightWrong.get(0).equalsIgnoreCase("0")){
                    imgWrong1.setVisibility(View.VISIBLE);
                }else {
                    imgWright1.setVisibility(View.VISIBLE);
                }
            }

            if(i==1){
                if(wrightWrong.get(1).equalsIgnoreCase("0")){
                    imgWrong2.setVisibility(View.VISIBLE);
                }else {
                    imgWright2.setVisibility(View.VISIBLE);
                }
            }

             if(i==2){
                 if(wrightWrong.get(2).equalsIgnoreCase("0")){
                     imgWrong3.setVisibility(View.VISIBLE);
                 }else {
                     imgWright3.setVisibility(View.VISIBLE);
                 }
            }

             if(i==3){
                 if(wrightWrong.get(3).equalsIgnoreCase("0")){
                     imgWrong4.setVisibility(View.VISIBLE);
                 }else {
                     imgWright4.setVisibility(View.VISIBLE);
                 }
            }

             if(i==4){
                 if(wrightWrong.get(4).equalsIgnoreCase("0")){
                     imgWrong5.setVisibility(View.VISIBLE);
                 }else {
                     imgWright5.setVisibility(View.VISIBLE);
                 }
            }

        }

        


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                if (cdt!=null){
                    cdt.cancel();
                }
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
