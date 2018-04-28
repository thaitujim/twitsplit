package com.testexam.danny.twitsplit.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.testexam.danny.twitsplit.R;
import com.testexam.danny.twitsplit.ui.adapter.MessageAdapter;

public class MessageDetailActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Bundle bundle = getIntent().getExtras();
        String[] result = (String[]) bundle.getSerializable(MainActivity.MESSAGE_LIST);


        mAdapter = new MessageAdapter(result);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);
    }
}
