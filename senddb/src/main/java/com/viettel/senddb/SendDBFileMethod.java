package com.viettel.senddb;

public class SendDBFileMethod {
    public static final String FIREBASE = "firebase";
    public static final String GMAIL = "gmail";
    final String code;
    final int maxNumberOfTime;
    public String text;
    public int iconId;

    SendDBFileMethod(String raw) {
        String code = null;
        int maxNumberOfTime = 0;
        try {
            String[] split = raw.split(":");
            code = split[0];
            maxNumberOfTime = Integer.parseInt(split[1]);
        } catch (Exception ignore) {

        }
        this.code = code;
        this.maxNumberOfTime = maxNumberOfTime;
        this.text = null;
        this.iconId = 0;
    }
}
