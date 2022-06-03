package com.viettel.senddb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class SendDBFileMethodSelector {
    public static final String SEND_BY_FIREBASE = "send_by_firebase";
    public static final String SEND_BY_GMAIL = "send_by_gmail";
    private final Context context;

    public SendDBFileMethodSelector(Context context) {
        this.context = context;
    }

    public void show(SendDBFileMethodSelectorCallback callback) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        CharSequence[] charSequence = new CharSequence[]{"Gmail", "FireBase"};
        alertDialog.setSingleChoiceItems(charSequence, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    String[] chooseArr = {SEND_BY_GMAIL, SEND_BY_FIREBASE};
                    callback.onChoose(chooseArr[which]);
                }
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
