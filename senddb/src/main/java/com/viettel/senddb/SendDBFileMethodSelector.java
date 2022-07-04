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

    public SendDBFileMethodSelector setAvailableMethods(SendDBFileMethod[] sendDBMethods) {
        if (sendDBMethods != null) {
            methodList = new ArrayList<>();
            for (SendDBFileMethod method : sendDBMethods) {
                switch (method.code) {
                    case SendDBFileMethod.GMAIL:
                        method.text = getString(R.string.SEND_DB_BY_GMAIL);
                        method.iconId = R.drawable.ic_gmail;
                        methodList.add(method);
                        break;
                    case SendDBFileMethod.FIREBASE:
                        method.text = getString(R.string.SEND_DB_BY_FIREBASE);
                        method.iconId = R.drawable.ic_storage;
                        methodList.add(method);
                        break;
                }
            }
        }
        return this;
    }

    public void show(SendDBFileMethodSelectorCallback selectorCallback, GetSendDBFileMethodCallback getSendDBFileMethodCallback) {
        if (methodList == null || methodList.isEmpty()) {
            if (selectorCallback != null) {
                String notify = null;
                if (context != null) {
                    notify = context.getString(R.string.NOT_HAVE_ANY_SEND_DB_METHOD);
                }
                selectorCallback.onError(notify, new Exception("Not have any method, please check AP_PARAM table with AP_PARAM_CODE = 'SEND_DB_METHOD' or something else," +
                        " available methods: " + SendDBFileMethod.FIREBASE + ", " + SendDBFileMethod.GMAIL));
            }
            return;
        }
        if (methodList.size() == 1) {
            if (selectorCallback != null && getSendDBFileMethodCallback != null) {
                selectorCallback.onChoose(decideSendDBMethod(methodList.get(0).code, getSendDBFileMethodCallback));
            }
            return;
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        SendDBFileMethodAdapter adapter = new SendDBFileMethodAdapter(context, methodList);
        alertDialog.setTitle(getString(R.string.CHOOSE_SEND_DB_METHOD));
        alertDialog.setSingleChoiceItems(adapter, 0, (dialog, which) -> {
            if (selectorCallback != null) {
                SendDBFileMethod chosen = methodList.get(which);
                final int numberOfTimesSendDB = SendDBCache.getNumberOfTimesSendDB(context, SendDBFileMethod.FIREBASE);
                final boolean isOutOfSubmissions = numberOfTimesSendDB > chosen.maxNumberOfTime;
                if (isOutOfSubmissions) {
                    String notify = null;
                    if (context != null) {
                        notify = context.getString(R.string.BLOCK_SEND_DB);
                    }
                    selectorCallback.onError(notify, new Exception("SEND DB " + chosen.code + ": Out of submissions. Max: "
                            + chosen.maxNumberOfTime + ", submitted: " + numberOfTimesSendDB));
                } else {
                    if (getSendDBFileMethodCallback != null) {
                        selectorCallback.onChoose(decideSendDBMethod(chosen.code, getSendDBFileMethodCallback));
                    }
                }
            }
            dialog.dismiss();
        });
        alertDialog.show();
    }

    SendDBFile decideSendDBMethod(String methodCode, GetSendDBFileMethodCallback callback) {
        if (callback != null) {
            if (SendDBFileMethod.FIREBASE.equals(methodCode)) {
                return callback.onGetSendDBFileByFirebase();
            } else {
                return callback.onGetSendDBFileByGmail();
            }
        } else return null;
    }
}
