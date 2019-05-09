package com.imamportal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.Adapter.ChatAppMsgAdapter;
import com.imamportal.model.ChatAppMsgDTO;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.PersistData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatAppActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack,imgPicChatUser;
    private TextView tvChatUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        context= this;

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Get RecyclerView object.
        final RecyclerView msgRecyclerView = (RecyclerView)findViewById(R.id.chat_recycler_view);

        // Set RecyclerView layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);

        // Create the initial data list.
        final List<ChatAppMsgDTO> msgDtoList = new ArrayList<ChatAppMsgDTO>();
        ChatAppMsgDTO msgDto = new ChatAppMsgDTO(PersistData.getStringData(context, AppConstant.loginUserid),"","hello",new Date().toString());
        msgDtoList.add(msgDto);

        ChatAppMsgDTO msgDt1 = new ChatAppMsgDTO("2","","hello",new Date().toString());
        msgDtoList.add(msgDt1);


        ChatAppMsgDTO msgDt2 = new ChatAppMsgDTO(PersistData.getStringData(context, AppConstant.loginUserid),"","hello",new Date().toString());
        msgDtoList.add(msgDt2);

        ChatAppMsgDTO msgDt3 = new ChatAppMsgDTO("2","","hello",new Date().toString());
        msgDtoList.add(msgDt3);

        // Create the data adapter with above data list.
        final ChatAppMsgAdapter chatAppMsgAdapter = new ChatAppMsgAdapter(msgDtoList,context);

        // Set data adapter to RecyclerView.
        msgRecyclerView.setAdapter(chatAppMsgAdapter);

        final EditText msgInputText = (EditText)findViewById(R.id.chat_input_msg);

        ImageView msgSendButton = (ImageView) findViewById(R.id.chat_send_msg);

        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgContent = msgInputText.getText().toString();
                if(!TextUtils.isEmpty(msgContent))
                {
                    // Add a new sent message to the list.
                    ChatAppMsgDTO msgDto = new ChatAppMsgDTO("2","",msgContent,currentDate());
                    msgDtoList.add(msgDto);

                    int newMsgPosition = msgDtoList.size() - 1;

                    // Notify recycler view insert one new data.
                    chatAppMsgAdapter.notifyItemInserted(newMsgPosition);

                    // Scroll RecyclerView to the last message.
                    msgRecyclerView.scrollToPosition(newMsgPosition);

                    // Empty the input edit text box.
                    msgInputText.setText("");
                }else {
                    Toast.makeText(context, "বার্তা লিখুন", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String currentDate() {
        DateFormat dateFormatter = new SimpleDateFormat("dd-M-yyyy hh:mm");

        dateFormatter.setLenient(false);

        Date today = new Date();

        String s = dateFormatter.format(today);

        return s;
    }


}
