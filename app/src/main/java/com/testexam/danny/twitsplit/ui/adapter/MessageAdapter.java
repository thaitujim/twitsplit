package com.testexam.danny.twitsplit.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testexam.danny.twitsplit.R;
import com.testexam.danny.twitsplit.modal.Twit;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private ArrayList<Twit> messageList;
    private ArrayList<Boolean> tweetedList;

    public ArrayList<Boolean> getTweetedList() {
        return tweetedList;
    }

    public void setTweetedList(ArrayList<Boolean> tweetedList) {
        this.tweetedList = tweetedList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMessage;
        public ImageView ivCheck;

        public MyViewHolder(View view) {
            super(view);
            tvMessage = (TextView) view.findViewById(R.id.tv_message);
            ivCheck = (ImageView) view.findViewById(R.id.iv_check);
        }
    }

    public MessageAdapter(ArrayList<Twit> messageList) {
        this.messageList = messageList;
        this.tweetedList = new ArrayList<>();
        for (int i=0;i<messageList.size();i++){
            this.tweetedList.add(false);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Twit twit = (Twit)messageList.get(position);
        String message = twit.getMessage();
        holder.tvMessage.setText(message);

        if(tweetedList.get(position).booleanValue() == true){
            holder.ivCheck.setVisibility(View.VISIBLE);
        }else{
            holder.ivCheck.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
