package com.viettel.senddb;

import android.app.AlertDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


public class SendDBFileMethodSelector {

    private final Context context;
    List<SendDBFileMethod> methodList = null;

    public SendDBFileMethodSelector(Context context) {
        this.context = context;
    }

    private String getString(int resId) {
        if (context != null) {
            return context.getString(resId);
        } else {
            return null;
        }
    }

    public SendDBFileMethodSelector setAvailableMethods(String[] sendDBMethodCodes) {
        if(sendDBMethodCodes != null) {
            methodList = new ArrayList<>();
            for (String methodCode : sendDBMethodCodes) {
                switch (methodCode) {
                    case SendDBFileMethod.GMAIL:
                        String sendDBByGmail = getString(R.string.SEND_DB_BY_GMAIL);
                        methodList.add(new SendDBFileMethod(methodCode, sendDBByGmail, R.drawable.ic_gmail));
                        break;
                    case SendDBFileMethod.FIREBASE:
                        String sendDBByFirebase = getString(R.string.SEND_DB_BY_FIREBASE);
                        methodList.add(new SendDBFileMethod(methodCode, sendDBByFirebase, R.drawable.ic_storage));
                        break;
                }
            }
        }
        return this;
    }

    public void show(SendDBFileMethodSelectorCallback callback) {
        if (methodList == null || methodList.isEmpty()) {
            if (callback != null) {
                callback.onError(new Exception("Not have any method, please check AP_PARAM table with AP_PARAM_CODE = 'SEND_DB_METHOD' or something else," +
                        " available methods: " + SendDBFileMethod.FIREBASE + ", " + SendDBFileMethod.GMAIL));
            }
            return;
        }
        if (methodList.size() == 1) {
            if (callback != null) {
                callback.onChoose(methodList.get(0).code);
            }
            return;
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        SendDBFileMethodAdapter adapter = new SendDBFileMethodAdapter(context, methodList);
        alertDialog.setTitle(getString(R.string.CHOOSE_SEND_DB_METHOD));
        alertDialog.setSingleChoiceItems(adapter, 0, (dialog, which) -> {
            if (callback != null) {
                SendDBFileMethod chosen = methodList.get(which);
                callback.onChoose(chosen.code);
            }
            dialog.dismiss();
        });
        alertDialog.show();
    }
}
