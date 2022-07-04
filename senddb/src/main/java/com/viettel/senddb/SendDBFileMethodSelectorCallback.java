package com.viettel.senddb;

public interface SendDBFileMethodSelectorCallback {
    void onChoose(SendDBFile sendDBFile);

    void onError(String notify, Exception exception);
}
