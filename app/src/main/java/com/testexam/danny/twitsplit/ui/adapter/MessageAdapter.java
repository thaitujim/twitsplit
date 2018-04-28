package com.testexam.danny.twitsplit.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testexam.danny.twitsplit.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private String[] messageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMessage;

        public MyViewHolder(View view) {
            super(view);
            tvMessage = (TextView) view.findViewById(R.id.tv_message);
        }
    }

    public MessageAdapter(String[] messageList) {
        this.messageList = messageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String message = messageList[position];
        holder.tvMessage.setText(message);
    }

    @Override
    public int getItemCount() {
        return messageList.length;
    }
}
