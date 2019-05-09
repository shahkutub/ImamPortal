package com.imamportal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.imamportal.R;
import com.imamportal.model.ChatAppMsgDTO;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.PersistData;

import java.util.ArrayList;
import java.util.List;

public class ChatAppMsgAdapter extends RecyclerView.Adapter<ChatAppMsgViewHolder> {

    private List<ChatAppMsgDTO> msgDtoList = null;
    Context context;

    public ChatAppMsgAdapter(List<ChatAppMsgDTO> msgDtoList,Context context) {
        this.msgDtoList = msgDtoList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ChatAppMsgViewHolder holder, int position) {
        ChatAppMsgDTO msgDto = this.msgDtoList.get(position);
        // If the message is a received message.
        if(msgDto.getFrom_user().equalsIgnoreCase(PersistData.getStringData(context, AppConstant.loginUserid)))
        {
            // Show received message in left linearlayout.
            holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);

            if(!TextUtils.isEmpty(msgDto.getFile())){
                holder.leftMsgTextView.setText(msgDto.getFile());
            }else {
                holder.leftMsgTextView.setText(msgDto.getMessage());
            }

            holder.chat_left_text_message_time.setText(msgDto.getCreated_at());
            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
            // Otherwise each iteview's distance is too big.
            holder.rightMsgLayout.setVisibility(LinearLayout.GONE);
        }
        // If the message is a sent message.
        else
        {
            // Show sent message in right linearlayout.
            holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);

            if(!TextUtils.isEmpty(msgDto.getFile())){
                holder.rightMsgTextView.setText(msgDto.getFile());
            }else {
                holder.rightMsgTextView.setText(msgDto.getMessage());
            }

            holder.chat_right_msg_text_message_time.setText(msgDto.getCreated_at());
            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
            // Otherwise each iteview's distance is too big.
            holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public ChatAppMsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_chat_app_item, parent, false);
        return new ChatAppMsgViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if(msgDtoList==null)
        {
            msgDtoList = new ArrayList<ChatAppMsgDTO>();
        }
        return msgDtoList.size();
    }
}