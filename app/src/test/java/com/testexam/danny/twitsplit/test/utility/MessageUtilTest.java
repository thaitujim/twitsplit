package com.testexam.danny.twitsplit.test.utility;

import com.testexam.danny.twitsplit.exception.MessageException;
import com.testexam.danny.twitsplit.utility.MessageUtil;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MessageUtilTest {
    @Test
    public void messageUtil_splitMessage_ReturnTrue() throws Exception{
        String[] result = MessageUtil.splitMessage("I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself.");
        assertEquals(result[0],"1/2 I can't believe Tweeter now supports chunking");
        assertEquals(result[1],"2/2 my messages, so I don't have to do it myself.");
    }

    @Test
    public void messageUtil_splitMessage_lessThanMaximum_ReturnTrue() throws Exception{
        String[] result = MessageUtil.splitMessage("I can't believe");
        assertEquals(result[0],"I can't believe");
    }

    @Test
    public void messageUtil_splitMessage_almostIsSpace_ReturnTrue() throws Exception{
        String[] result = MessageUtil.splitMessage("                                                                                           ");
        assertTrue(result.length == 0);
    }

    @Test(expected=MessageException.class)
    public void messageUtil_splitMessage_almostIscharacter_ReturnTrue() throws Exception{
        String[] result = MessageUtil.splitMessage("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Test(expected=MessageException.class)
    public void messageUtil_splitMessage_whenspitsecondlonger_ReturnTrue() throws Exception{
        String[] result = MessageUtil.splitMessage("aaaaaaaaaaaaaaaaaaaaaaaaaaa, 0123456789.0123456789.0123456789.0123456789...!");
    }

    @Test
    public void messageUtil_splitMessage_whenspitsecondisempty_ReturnTrue() throws Exception{
        String[] result = MessageUtil.splitMessage("aaaaaaaaaaaaaaaaaaaaaaaaaab,                                                    ");
        assertEquals(result[0],"aaaaaaaaaaaaaaaaaaaaaaaaaab,");
    }

    @Test
    public void messageUtil_splitMessage_whenspitfirstisempty_ReturnTrue() throws Exception{
        String[] result = MessageUtil.splitMessage("                                          , aaaaaaaaaaaaaaaaaaaaaaaaaab");
        assertEquals(result[0],", aaaaaaaaaaaaaaaaaaaaaaaaaab");
    }

    @Test(expected=MessageException.class)
    public void messageUtil_splitMessage_empty_ReturnTrue() throws Exception{
        String[] result = MessageUtil.splitMessage("");
    }

    @Test
    public void messageUtil_splitMessage_longestInput_ReturnTrue() throws Exception{

        File testSample01 = new File(getClass().getResource("/test_sample_01.txt").getPath());

        BufferedReader br = new BufferedReader(new FileReader(testSample01));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            String[] result = MessageUtil.splitMessage(sb.toString());

            for (int i=0;i<result.length;i++){
                assertTrue(result[i].length() <= MessageUtil.MAXIMUM_STRING_ACCEPT);
            }

        } finally {
            br.close();
        }
    }
}
