package com.viettel.senddb;

public class SendDBFileMethod {
    public static final String FIREBASE = "firebase";
    public static final String GMAIL = "gmail";
    final String code;
    final String text;
    final int iconId;

    public SendDBFileMethod(String code, String text, int iconId) {
        this.code = code;
        this.text = text;
        this.iconId = iconId;
    }
}
