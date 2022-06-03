package com.viettel.senddb;

public interface SendDBFileCallback {
    void onPreSend(String notify, boolean shouldBlockUI);

    void onProgress(long uploadedBytes, long totalBytes);

    void onSendSuccessfully(Object... args);

    void onSendFailed(Exception exception);
}
