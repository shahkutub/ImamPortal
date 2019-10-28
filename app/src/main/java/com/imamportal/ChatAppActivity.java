package com.imamportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.imamportal.Adapter.ChatAppMsgAdapter;
import com.imamportal.Adapter.ChatUsetListAdapter;
import com.imamportal.Adapter.GroupChatAppMsgAdapter;
import com.imamportal.model.ChatAppMsgDTO;
import com.imamportal.model.ChatUserModel;
import com.imamportal.model.ChatUserResponse;
import com.imamportal.model.GroupMessageResponse;
import com.imamportal.model.MessageResponse;
import com.imamportal.model.SendMsgResponse;
import com.imamportal.model.SignUpResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PersistData;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAppActivity extends AppCompatActivity{

    Context context;
    private ImageView imgBack,imgPicChatUser;
    private TextView tvChatUserName;
    private List<ChatAppMsgDTO> msgDtoList = new ArrayList<ChatAppMsgDTO>();
    private String id;
    String currentPage;
    private MessageResponse responsData;
    private GroupMessageResponse groupMsgresponsData;
    private RecyclerView msgRecyclerView;
    private ChatAppMsgAdapter chatAppMsgAdapter;
    private GroupChatAppMsgAdapter groupChatAppMsgAdapter;

    private EditText msgInputText;


    Timer timer;
    private boolean isExpired = false;
    private Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        context= this;

        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, 3000);

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgPicChatUser = (ImageView) findViewById(R.id.imgPicChatUser);



        tvChatUserName = (TextView) findViewById(R.id.tvChatUserName);
        tvChatUserName.setText(""+AppConstant.otheruserName);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Get RecyclerView object.
        msgRecyclerView = (RecyclerView)findViewById(R.id.chat_recycler_view);


        msgInputText = (EditText)findViewById(R.id.chat_input_msg);

        ImageView msgSendButton = (ImageView) findViewById(R.id.chat_send_msg);

        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msgContent = msgInputText.getText().toString();
                if(!TextUtils.isEmpty(msgContent))
                {

                    if(AppConstant.chatType.equalsIgnoreCase("singlechat")){
                        sendMsg(AppConstant.otheruserId,msgContent);
                    }else if(AppConstant.chatType.equalsIgnoreCase("groupchat")){
                        sendGroupMsg(AppConstant.otheruserId,msgContent);
                    }

                }else {
                    Toast.makeText(context, "বার্তা লিখুন", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private class RemindTask extends TimerTask {


        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {
                    //if(!isExpired){
                    if(AppConstant.chatType.equalsIgnoreCase("singlechat")){
                        message_conversations("1");
                    }else if(AppConstant.chatType.equalsIgnoreCase("groupchat")){
                        groupMessage_conversations("1");
                    }

                    //}

                }
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private String currentDate() {
        DateFormat dateFormatter = new SimpleDateFormat("dd-M-yyyy hh:mm");

        dateFormatter.setLenient(false);

        Date today = new Date();

        String s = dateFormatter.format(today);

        return s;
    }

    private void message_conversations(String page) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }
        //swiperefresh.setRefreshing(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<MessageResponse> userCall = api.user_messages("Bearer "+ PersistData.getStringData(context, AppConstant.loginToken),"api/user_messages/"+AppConstant.otheruserId+"?current_page="+page);
        userCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                //swiperefresh.setRefreshing(false);
                responsData = response.body();

                if(responsData!=null){
                    currentPage = responsData.getMessages().getCurrent_page();
                    if(responsData.getMessages().getData()!=null){

                        if(currentPage.equalsIgnoreCase("1")){
                            msgDtoList.clear();
                        }
                        for (ChatAppMsgDTO data: responsData.getMessages().getData()) {
                            msgDtoList.add(data);
                        }
                        Collections.reverse(msgDtoList);
// Create the data adapter with above data list.
                        chatAppMsgAdapter = new ChatAppMsgAdapter(msgDtoList,responsData.getFrom_user().getUsername(),responsData.getFrom_user().getUser_details().getImage(),context);
// Set RecyclerView layout manager.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        msgRecyclerView.setLayoutManager(linearLayoutManager);
                        // Set data adapter to RecyclerView.
                        msgRecyclerView.setAdapter(chatAppMsgAdapter);


                        int newMsgPosition = msgDtoList.size() - 1;

                        // Notify recycler view insert one new data.
                        chatAppMsgAdapter.notifyItemInserted(newMsgPosition);

                        // Scroll RecyclerView to the last message.
                        msgRecyclerView.scrollToPosition(newMsgPosition);
                        Glide.with(context)
                                .asBitmap()
                                .load(Api.BASE_URL+"public/upload/user/"+responsData.getFrom_user().getUser_details().getImage())
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        if(resource!=null){
                                            imgPicChatUser.setImageBitmap(resource);
                                        }
                                    }
                                });
                    }

                }else {
                    //if(!isExpired){
                        timer.cancel();
                       // isExpired = true;
                        //Toast.makeText(context, "Login token expired, Please login again ", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(context,LoginActivity.class));
//                        finish();
                    //}

                }


            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                //swiperefresh.setRefreshing(false);
                timer.cancel();
            }
        });
        timer.cancel();

    }

    private void groupMessage_conversations(String page) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }
        //swiperefresh.setRefreshing(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<GroupMessageResponse> userCall = api.group_messages("Bearer "+ PersistData.getStringData(context, AppConstant.loginToken),"api/group_messages/"+AppConstant.otheruserId+"?current_page="+page);
        userCall.enqueue(new Callback<GroupMessageResponse>() {
            @Override
            public void onResponse(Call<GroupMessageResponse> call, Response<GroupMessageResponse> response) {
                //swiperefresh.setRefreshing(false);
                groupMsgresponsData = response.body();

                if(groupMsgresponsData!=null){
                    currentPage = groupMsgresponsData.getCurrent_page();
                    if(groupMsgresponsData.getData()!=null){

                        if(currentPage.equalsIgnoreCase("1")){
                            msgDtoList.clear();
                        }
                        for (ChatAppMsgDTO data: groupMsgresponsData.getData()) {
                            msgDtoList.add(data);
                        }
                        Collections.reverse(msgDtoList);
// Create the data adapter with above data list.
                        groupChatAppMsgAdapter = new GroupChatAppMsgAdapter(msgDtoList,context);
// Set RecyclerView layout manager.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        msgRecyclerView.setLayoutManager(linearLayoutManager);
                        // Set data adapter to RecyclerView.
                        msgRecyclerView.setAdapter(groupChatAppMsgAdapter);


                        int newMsgPosition = msgDtoList.size() - 1;

                        // Notify recycler view insert one new data.
                        groupChatAppMsgAdapter.notifyItemInserted(newMsgPosition);

                        // Scroll RecyclerView to the last message.
                        msgRecyclerView.scrollToPosition(newMsgPosition);

                    }

                }else {
                    //if(!isExpired){
                    timer.cancel();
                    // isExpired = true;
                    //Toast.makeText(context, "Login token expired, Please login again ", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(context,LoginActivity.class));
//                        finish();
                    //}

                }


            }

            @Override
            public void onFailure(Call<GroupMessageResponse> call, Throwable t) {
                //swiperefresh.setRefreshing(false);
                timer.cancel();
            }
        });
        timer.cancel();

    }



    private void sendMsg(final String touser, final String msg) {

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
        Call<SendMsgResponse> userCall = api.send_message("Bearer "+ PersistData.getStringData(context, AppConstant.loginToken),touser,msg);
        userCall.enqueue(new Callback<SendMsgResponse>() {
            @Override
            public void onResponse(Call<SendMsgResponse> call, Response<SendMsgResponse> response) {
                pd.dismiss();

                SendMsgResponse responsData = response.body();

                if(responsData!=null){
                    if(responsData.getStatus().equalsIgnoreCase("success")){
                        // Add a new sent message to the list.
                        ChatAppMsgDTO msgDto = new ChatAppMsgDTO(touser,msg);
                        msgDtoList.add(msgDto);

                        int newMsgPosition = msgDtoList.size() - 1;

                        // Notify recycler view insert one new data.
                        chatAppMsgAdapter.notifyItemInserted(newMsgPosition);

                        // Scroll RecyclerView to the last message.
                        msgRecyclerView.scrollToPosition(newMsgPosition);

                        // Empty the input edit text box.
                        msgInputText.setText("");
                    }
                }



            }

            @Override
            public void onFailure(Call<SendMsgResponse> call, Throwable t) {

                pd.dismiss();
            }
        });


    }

    private void sendGroupMsg(final String touser, final String msg) {

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
        Call<SendMsgResponse> userCall = api.send_group_message("Bearer "+ PersistData.getStringData(context, AppConstant.loginToken),touser,msg);
        userCall.enqueue(new Callback<SendMsgResponse>() {
            @Override
            public void onResponse(Call<SendMsgResponse> call, Response<SendMsgResponse> response) {
                pd.dismiss();

                SendMsgResponse responsData = response.body();

                if(responsData!=null){
                    if(responsData.getStatus().equalsIgnoreCase("success")){
                        // Add a new sent message to the list.
                        ChatAppMsgDTO msgDto = new ChatAppMsgDTO(PersistData.getStringData(context,AppConstant.loginUserid),msg);
                        msgDtoList.add(msgDto);

                        int newMsgPosition = msgDtoList.size() - 1;

                        // Notify recycler view insert one new data.
                        groupChatAppMsgAdapter.notifyItemInserted(newMsgPosition);

                        // Scroll RecyclerView to the last message.
                        msgRecyclerView.scrollToPosition(newMsgPosition);

                        // Empty the input edit text box.
                        msgInputText.setText("");
                    }
                }



            }

            @Override
            public void onFailure(Call<SendMsgResponse> call, Throwable t) {

                pd.dismiss();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            //do actions like show message
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
