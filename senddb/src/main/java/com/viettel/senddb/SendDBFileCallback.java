package com.viettel.senddb;

public interface SendDBFileCallback {
    void onPreSend(String notify, boolean shouldBlockUI);

    void onProgress(long uploadedBytes, long totalBytes);

    void onSendSuccessful(String notify, Object... args);

    void onSendFailed(String notify, Exception exception);
}
