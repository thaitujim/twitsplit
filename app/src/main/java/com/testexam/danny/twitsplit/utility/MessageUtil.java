package com.testexam.danny.twitsplit.utility;

import android.util.Log;

import com.testexam.danny.twitsplit.exception.MessageException;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MessageUtil {

    public static final int MAXIMUM_STRING_ACCEPT = 50;

    public static final String TAG = MessageUtil.class.getName();

    public static String[] splitMessage(String input) throws MessageException {

        if (input == null) {
            throw new MessageException("input must not empty");
        }

        if (input.length() == 0) {
            throw new MessageException("input must not empty");
        }

        if (input.length() < MAXIMUM_STRING_ACCEPT) {
            return new String[]{input};
        }

        String measure = input;
        int longestPrefix = 0;
        int longestString = 0;
        int totalPart = 0;
        int indicatorLength = 1;
        longestPrefix = ("" + totalPart + "/" + totalPart + " ").length();
        // 0/9 abc def
        Log.d(TAG, "recalculate longestPrefix -> " + ("" + totalPart + "/" + totalPart + " "));
        int maximumStringRemain = MAXIMUM_STRING_ACCEPT - longestPrefix;

        ArrayList<String> result = new ArrayList<>();

        while (measure.length() > 0) {
            //find the first word
            int first = 0;

            for (int i = first; i < measure.length(); i++) {
                if (measure.charAt(i) != ' ') {
                    first = i;
                    break;
                }
                if (i == measure.length() - 1) {
                    //can not find any word, nothing to process
                    first = -1;
                    break;
                }
            }

            if (first == -1){
                //can not find start point
                break;
            }

            int last = first + maximumStringRemain;
            String word = "";

            if (last < measure.length()) {
                for (int i = last; i >= 0; i--) {
                    if (measure.charAt(i) != ' ') {
                        last--;

                        if (last == first) {
                            //can not find any word, maybe string is a long character
                            throw new MessageException(measure);
                        }
                    } else {
                        if(i > 1 && measure.charAt(i - 1) == ' '){
                            continue;
                        }else{
                            last = i;
                            word = measure.substring(first, last);
                            measure = measure.substring(last);
                            break;
                        }
                    }
                }
            } else {
                word = measure.substring(first);
                measure = "";
            }
            Log.d(TAG, "maximumStringRemain -> " + maximumStringRemain);
            Log.d(TAG, "word -> " + word);
            Log.d(TAG, "word length -> " + word.length());

            if (word.length() > longestString) {
                longestString = word.length();
            }
            totalPart = totalPart + 1;

            result.add(word);

            if (StringUtil.getLengthInt(totalPart) > indicatorLength) {
                //recalculate longestPrefix
                indicatorLength = StringUtil.getLengthInt(totalPart);
                longestPrefix = ("" + totalPart + "/" + totalPart + " ").length();

                Log.d(TAG, "recalculate longestPrefix -> " + ("" + totalPart + "/" + totalPart + " "));
                if (longestPrefix + longestString > MAXIMUM_STRING_ACCEPT) {
                    //reset all
                    Log.d(TAG, "longestPrefix + longestString > MAXIMUM_STRING_ACCEPT");
                    Log.d(TAG, "reset all");
                    totalPart = 0;
                    longestString = 0;
                    maximumStringRemain = MAXIMUM_STRING_ACCEPT - longestPrefix;
                    measure = input;
                    result.clear();
                }
            }

            Log.d(TAG, "totalPart -> " + totalPart);
            Log.d(TAG, "longestString -> " + longestString);
        }

        String[] stockArr = addPartIndicator(result, totalPart);

        return stockArr;
    }

    public static final String[] addPartIndicator(ArrayList<String> result, int totalPart) {

        if (result.size() == 0) {
            return new String[]{};
        }

        String[] stockArr = new String[result.size()];

        if (result.size() == 1) {
            stockArr[0] = result.get(0);
        } else {
            for (int i = 0; i < result.size(); i++) {
                stockArr[i] = (i + 1) + "/" + totalPart + " " + result.get(i);
            }
        }

        return stockArr;
    }
}
