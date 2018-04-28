package com.testexam.danny.twitsplit.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class MessageDialog {

    private static final String TAG = MessageDialog.class.getName();

    public static final AlertDialog showMessageError(Context context, String message){
        return new AlertDialog.Builder(context)
                .setTitle("Error?")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "dialog closed");
                    }
                })
                .show();
    }

    public static final ProgressDialog showProgressDialog(Context context,ProgressDialog progressDialog){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Twit twit...");
        progressDialog.setCancelable(false);
        return progressDialog;
    }
}
