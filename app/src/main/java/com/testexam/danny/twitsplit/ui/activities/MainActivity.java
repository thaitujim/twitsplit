package com.testexam.danny.twitsplit.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.testexam.danny.twitsplit.R;
import com.testexam.danny.twitsplit.exception.MessageException;
import com.testexam.danny.twitsplit.ui.dialog.MessageDialog;
import com.testexam.danny.twitsplit.utility.MessageUtil;

public class MainActivity extends AppCompatActivity {

    private Button buttonPost;
    private EditText editTextInput;
    private static final String TAG = MainActivity.class.getName();
    public static final String MESSAGE_LIST = "message_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPost = findViewById(R.id.btn_post);
        editTextInput = (EditText)findViewById(R.id.et_input);

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = editTextInput.getText().toString();
                processMessage(message);

            }
        });
    }

    private void processMessage(String message){
        try {
            String[] result = MessageUtil.splitMessage(message);
            Bundle bundle = new Bundle();
            bundle.putSerializable(MESSAGE_LIST, result);

            Intent intent = new Intent(this, MessageDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        }catch (MessageException e){
            Log.d(TAG, e.toString());
            MessageDialog.showMessageError(MainActivity.this,e.getMessage());
        }
    }
}
