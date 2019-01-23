package com.imamportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.imamportal.Adapter.ChatUsetListAdapter;
import com.imamportal.model.ChatUserModel;

import java.util.ArrayList;
import java.util.List;

public class ChatUserListActivity extends AppCompatActivity {

    Context context;
    private RelativeLayout linAutoserch;
    //private SearchView searchView;
    private ImageView imgSearch,imgCancel,imgGoSearch;
    private LinearLayout linSearch,linBack;
    private RecyclerView recycleUserList;
    AutoCompleteTextView autocoEditView;
    private String searchStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_user_list);

        context=this;
        initUi();
    }

    private void initUi() {
        linAutoserch = (RelativeLayout) findViewById(R.id.linAutoserch);

        linBack = (LinearLayout) findViewById(R.id.linBack);
        linBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgSearch = (ImageView) findViewById(R.id.imgSearch);

        autocoEditView = (AutoCompleteTextView)findViewById(R.id.autocoEditView);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        imgGoSearch = (ImageView) findViewById(R.id.imgGoSearch);
        imgGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchStr = autocoEditView.getText().toString();
                if(searchStr.length()>0){
                    linAutoserch.setVisibility(View.GONE);
                    startActivity(new Intent(context,ToolbarSearchActivity.class));
                }
            }
        });


        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linAutoserch.setVisibility(View.GONE);
            }
        });

        linSearch = (LinearLayout) findViewById(R.id.linSearch);
//        String[] ProgLanguages = { "Java", "C", "C++", ".Net", "PHP", "Perl",
//                "Objective-c", "Small-Talk", "C#", "Ruby", "ASP", "ASP .NET" };





        linSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linAutoserch.setVisibility(View.VISIBLE);
                autocoEditView.requestFocus();
                startActivity(new Intent(context, ChatSearchActivity.class));
            }
        });

        autocoEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {

                if(str.length()==0){
                    imgCancel.setVisibility(View.VISIBLE);
                }else if(str.length()>0){
                    imgCancel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


//        List<String> where = new ArrayList<String>();
//        where.add("android");
//        where.add("java");
//        where.add("dfd");
//        where.add("jadfva");
//        where.add("yh");
//        where.add("java");




        ChatUserModel chatUserModel = new ChatUserModel();

        chatUserModel.setContent("Hi whats up");
        chatUserModel.setUsername("Alom ali");
        chatUserModel.setDate("21,10,2018");

        List <ChatUserModel>  chatulist = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            chatulist.add(chatUserModel);

        }

        List<String> where = new ArrayList<String>();

        for (int i = 0; i < chatulist.size() ; i++) {
            where.add(chatulist.get(i).getUsername());
        }

        String[] simpleArray = new String[where.size()];
        where.toArray(simpleArray);



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, where);

        autocoEditView.setThreshold(1);
        autocoEditView.setAdapter(arrayAdapter);


        recycleUserList = (RecyclerView)findViewById(R.id.recycleUserList);

        ChatUsetListAdapter questionAnsAdapter = new ChatUsetListAdapter(chatulist,context);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recycleUserList.setLayoutManager(horizontalLayoutManager);
        recycleUserList.setAdapter(questionAnsAdapter);

    }


}
