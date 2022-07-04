package com.viettel.senddb;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SendDBCache {
    private static final String KEY_NUMBER_OF_TIMES_SEND_DB = "number";

    private static SendDBFileMethod[] sendDBMethods = null;


    public static void increaseNumberOfTimesSendDB(Context context, String methodCode) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("send_db", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String key = KEY_NUMBER_OF_TIMES_SEND_DB + methodCode + now();
            int numberOfTimeSendDB = sharedPreferences.getInt(key, 0);
            editor.putInt(key, numberOfTimeSendDB + 1);
            editor.apply();
        }
    }

    public static int getNumberOfTimesSendDB(Context context, String methodCode) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("send_db", Context.MODE_PRIVATE);
            return sharedPreferences.getInt(KEY_NUMBER_OF_TIMES_SEND_DB + methodCode + now(), 1);
        }
        return 10000;
    }

    private static String now(){
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        return format.format(now);
    }

    /*
	Neu khong khai bao hoac status = 0 thi mac dinh gui bang Gmail
	 */
    public static void setSendDBMethods(String rawMethods, int status) {
        sendDBMethods = null;
        if (status == 1) {
            if (rawMethods == null || rawMethods.isEmpty()) {
                sendDBMethods = new SendDBFileMethod[]{};
            } else {
                String[] raws = rawMethods.replaceAll(" ", "").split(",");
                sendDBMethods = new SendDBFileMethod[raws.length];
                for (int i = 0; i < raws.length; i++) {
                    sendDBMethods[i] = new SendDBFileMethod(raws[i]);
                }
            }
        }
    }

    public static SendDBFileMethod[] getSendDBMethods() {
        if (sendDBMethods == null) {
            sendDBMethods = new SendDBFileMethod[]{
                    new SendDBFileMethod(SendDBFileMethod.GMAIL + ":" + 1000)};
        }
        return sendDBMethods;
    }
}
