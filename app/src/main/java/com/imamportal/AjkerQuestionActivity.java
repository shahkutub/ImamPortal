package com.imamportal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AjkerQuestionActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack,imgTick;

    private RadioGroup radioGroupAns;
    private RadioButton radioButtonAns;
    private Button btnDakhil;
    LinearLayout linTotho,linans;
    private String answer="জিবরাঈল (আঃ)",givenAns;

    private TextView tvTotho,tvans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajkerquestion);
        context=this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initUi();
    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgTick = (ImageView)findViewById(R.id.imgTick);
        linans = (LinearLayout)findViewById(R.id.linans);
        linTotho= (LinearLayout)findViewById(R.id.linTotho);
        tvans= (TextView) findViewById(R.id.tvans);
        tvTotho= (TextView) findViewById(R.id.tvTotho);
        tvTotho.setText(answer);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        radioGroupAns = (RadioGroup) findViewById(R.id.radioGroupAns);
        btnDakhil = (Button)findViewById(R.id.btnDakhil);

        btnDakhil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get selected radio button from radioGroup
                int selectedId = radioGroupAns.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButtonAns = (RadioButton) findViewById(selectedId);



                if(radioButtonAns==null){
                    Toast.makeText(context,
                            "Select answer", Toast.LENGTH_SHORT).show();
                }else {
                    givenAns = radioButtonAns.getText().toString();
                   // if(givenAns.equalsIgnoreCase(answer)){
                        tvans.setText(givenAns);
                        linans.setVisibility(View.VISIBLE);
                        //linTotho.setVisibility(View.VISIBLE);
                        if(!givenAns.equalsIgnoreCase(answer)){
                            imgTick.setImageResource(R.drawable.ic_error);
                            linTotho.setVisibility(View.VISIBLE);
                        }else {
                            imgTick.setImageResource(R.drawable.ic_tick);
                        }

                        btnDakhil.setVisibility(View.GONE);
                    //}

//                    else {
//                        tvTotho.setText(answer);
//                        tvans.setText(givenAns);
//                        linTotho.setVisibility(View.VISIBLE);
//                        linans.setVisibility(View.VISIBLE);
//                    }
                }


            }
        });

    }

}
