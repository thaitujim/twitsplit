/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.testexam.danny.twitsplit.ui.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.testexam.danny.twitsplit.services.TweetUploadService;
import com.testexam.danny.twitsplit.ui.listener.TweetListener;

import static com.testexam.danny.twitsplit.services.TweetUploadService.EXTRA_FAIL_CAUSE;

public class TweetFailureReceiver extends BroadcastReceiver {

    private TweetListener tweetListener;
    private static final String TAG = TweetFailureReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            final Intent retryIntent
                    = intentExtras.getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
            final String failcause
                    = intentExtras.getString(TweetUploadService.EXTRA_FAIL_CAUSE);
            Log.e(TAG, retryIntent.toString());
            if(tweetListener != null){
                tweetListener.onFailure(failcause);
            }
        }
    }

    public void registerCallback(TweetListener tweetListener) {
        this.tweetListener = tweetListener;
    }
}

