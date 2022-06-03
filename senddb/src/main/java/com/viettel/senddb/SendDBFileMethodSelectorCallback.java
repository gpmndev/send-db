package com.viettel.senddb;

public interface SendDBFileMethodSelectorCallback {
    void onChoose(String method);

    void onError(Exception exception);
}
