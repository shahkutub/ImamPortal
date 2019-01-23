package com.imamportal.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imamportal.R;
import com.imamportal.model.KitabInfo;

import java.util.ArrayList;
import java.util.List;

public class IsFundamentalsAdapter extends RecyclerView.Adapter<IsFundamentalsAdapter.MyViewHolder> {


    List<KitabInfo> horizontalList = new ArrayList<>();
    Context context;
    private RecyclerView mRecyclerList = null;
    private int previousPosition=0;
    private boolean flagFirstItemSelected = false;

    public IsFundamentalsAdapter(List<KitabInfo> horizontalList, Context context,RecyclerView mRecyclerList) {
        this.horizontalList = horizontalList;
        this.context = context;
        this.mRecyclerList = mRecyclerList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtview;
        LinearLayout linMianView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.imgKitab);
            txtview=(TextView) itemView.findViewById(R.id.tvNameKitab);
            linMianView=(LinearLayout) itemView.findViewById(R.id.linMianView);

            linMianView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout linearLayout;
                    View view;

                    if (getAdapterPosition() == previousPosition){
                        view = mRecyclerList.findViewHolderForAdapterPosition(previousPosition).itemView;
                        linearLayout = (LinearLayout) view.findViewById(R.id.linMianView);
                        linearLayout.setBackgroundColor(Color.parseColor("#8CC63E"));
                        previousPosition = getAdapterPosition();

                    }else {
                        view = mRecyclerList.findViewHolderForAdapterPosition(previousPosition).itemView;
                        linearLayout = (LinearLayout) view.findViewById(R.id.linMianView);
                        linearLayout.setBackgroundColor(Color.parseColor("#E4DFD9"));

                        view = mRecyclerList.findViewHolderForAdapterPosition(getAdapterPosition()).itemView;
                        linearLayout = (LinearLayout) view.findViewById(R.id.linMianView);
                        linearLayout.setBackgroundColor(Color.parseColor("#8CC63E"));
                        previousPosition = getAdapterPosition();
                    }
                }
            });

        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_isfounamentals, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //holder.imageView.setImageResource(horizontalList.get(position).imageId);
        holder.txtview.setText(horizontalList.get(position).txt);
        RecyclerView.ViewHolder viewholder = mRecyclerList.findViewHolderForAdapterPosition(position);




    }


    @Override
    public int getItemCount()
    {
        return horizontalList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}

