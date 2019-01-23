package com.imamportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imamportal.ChatAppActivity;
import com.imamportal.R;
import com.imamportal.model.ChatUserModel;
import com.imamportal.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class ChatCreatAdapter extends RecyclerView.Adapter<ChatCreatAdapter.MyViewHolder> implements Filterable {


    List<ChatUserModel> contactList = new ArrayList<>();
    List<ChatUserModel> contactListFiltered = new ArrayList<>();
    Context context;
    FrameLayout frmFloatingOk;


    public ChatCreatAdapter(List<ChatUserModel> userModelList, FrameLayout frmFloatingOk, Context context) {
        this.contactList = userModelList;
        this.contactListFiltered = userModelList;
        this.frmFloatingOk = frmFloatingOk;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPicChat;
        TextView tvNameChat,tvChatDate,tvChatMsg;
        RelativeLayout relFullItem;
        CheckBox checkbox;
        public MyViewHolder(View view) {
            super(view);
            tvNameChat=(TextView) view.findViewById(R.id.tvNameChat);
            tvChatDate=(TextView) view.findViewById(R.id.tvChatDate);
            tvChatMsg=(TextView) view.findViewById(R.id.tvChatMsg);
            imgPicChat=(ImageView) view.findViewById(R.id.imgPicChat);
            relFullItem=(RelativeLayout) view.findViewById(R.id.relFullItem);
            checkbox=(CheckBox) view.findViewById(R.id.checkbox);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_create_group, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final ChatUserModel contact = contactListFiltered.get(position);
        holder.tvChatMsg.setText(contact.getContent());
        holder.tvNameChat.setText(contact.getUsername());
        holder.tvChatDate.setText(contact.getDate());

        if(AppConstant.chatActivityName.equalsIgnoreCase("newchat")){
            holder.checkbox.setVisibility(View.GONE);
        }else if (AppConstant.chatActivityName.equalsIgnoreCase("groupchat")){
            holder.checkbox.setVisibility(View.VISIBLE);
        }

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    frmFloatingOk.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.relFullItem.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if(AppConstant.chatActivityName.equalsIgnoreCase("newchat")){
                    context.startActivity(new Intent(context,ChatAppActivity.class));
                }

            }

        });

    }


    @Override
    public int getItemCount()
    {
        return contactListFiltered.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<ChatUserModel> filteredList = new ArrayList<>();
                    for (ChatUserModel row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getUsername().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<ChatUserModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}

