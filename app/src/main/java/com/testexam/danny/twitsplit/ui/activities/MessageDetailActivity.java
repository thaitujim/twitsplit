package com.testexam.danny.twitsplit.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.testexam.danny.twitsplit.R;
import com.testexam.danny.twitsplit.services.TweetUploadService;
import com.testexam.danny.twitsplit.ui.adapter.MessageAdapter;
import com.testexam.danny.twitsplit.ui.dialog.MessageDialog;
import com.testexam.danny.twitsplit.ui.listener.RecyclerTouchListener;
import com.testexam.danny.twitsplit.ui.listener.TweetListener;
import com.testexam.danny.twitsplit.ui.receivers.TweetFailureReceiver;
import com.testexam.danny.twitsplit.ui.receivers.TweetSuccessReceiver;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class MessageDetailActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;
    private String[] result;
    private static final String TAG = MessageDetailActivity.class.getName();
    private ProgressDialog progressDialog;
    private static TweetSuccessReceiver tweetSuccessReceiver;
    private static TweetFailureReceiver tweetFailureReceiver;
    private int currentTweet = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Bundle bundle = getIntent().getExtras();
        final String[] result = (String[]) bundle.getSerializable(MainActivity.MESSAGE_LIST);
        this.result = result;

        mAdapter = new MessageAdapter(result);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String message = result[position];
                twitMessage(message, position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        tweetSuccessReceiver = new TweetSuccessReceiver();
        tweetFailureReceiver = new TweetFailureReceiver();
        TweetListener tweetListener = new TweetListener() {

            @Override
            public void onFailure(String message) {
                progressDialog.dismiss();
                MessageDialog.showMessageError(MessageDetailActivity.this,message);
            }

            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                updateList();
            }
        };
        tweetSuccessReceiver.registerCallback(tweetListener);
        tweetFailureReceiver.registerCallback(tweetListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(tweetSuccessReceiver,new IntentFilter(TweetUploadService.UPLOAD_SUCCESS));
        registerReceiver(tweetFailureReceiver,new IntentFilter(TweetUploadService.UPLOAD_FAILURE));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(tweetSuccessReceiver);
        unregisterReceiver(tweetFailureReceiver);
    }

    //Function
    public void twitMessage(String message, int position){
        Log.d(TAG,"twitMessage -> " + message);
        progressDialog = MessageDialog.showProgressDialog(this, progressDialog);
        progressDialog.show();
        currentTweet = position;

        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();

        final TwitterAuthToken token = session.getAuthToken();
        if (token == null) {
            throw new IllegalArgumentException("TwitterSession token must not be null");
        }

        final Intent intent = new Intent(getApplicationContext(), TweetUploadService.class);
        intent.putExtra(TweetUploadService.EXTRA_USER_TOKEN, session.getAuthToken());
        intent.putExtra(TweetUploadService.EXTRA_TWEET_TEXT, message);
        this.startService(intent);

    }

    public void updateList(){
        
    }
}
