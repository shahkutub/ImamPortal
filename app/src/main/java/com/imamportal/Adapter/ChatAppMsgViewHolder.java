package com.imamportal.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imamportal.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatAppMsgViewHolder extends RecyclerView.ViewHolder {

    LinearLayout leftMsgLayout;

    LinearLayout rightMsgLayout;

    TextView text_message_name,leftMsgTextView,chat_left_text_message_time;

    TextView rightMsgTextView,chat_right_msg_text_message_time;
    CircleImageView image_message_profile;

    public ChatAppMsgViewHolder(View itemView) {
        super(itemView);

        if(itemView!=null) {
            image_message_profile = (CircleImageView)itemView.findViewById(R.id.image_message_profile);
            leftMsgLayout = (LinearLayout) itemView.findViewById(R.id.chat_left_msg_layout);
            rightMsgLayout = (LinearLayout) itemView.findViewById(R.id.chat_right_msg_layout);
            leftMsgTextView = (TextView) itemView.findViewById(R.id.chat_left_msg_text_view);
            text_message_name = (TextView) itemView.findViewById(R.id.text_message_name);
            rightMsgTextView = (TextView) itemView.findViewById(R.id.chat_right_msg_text_view);
            chat_right_msg_text_message_time = (TextView) itemView.findViewById(R.id.chat_right_msg_text_message_time);
            chat_left_text_message_time = (TextView) itemView.findViewById(R.id.chat_left_text_message_time);
        }
    }
}
