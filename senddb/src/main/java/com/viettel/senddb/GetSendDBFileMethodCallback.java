package com.viettel.senddb;

public interface GetSendDBFileMethodCallback {
    SendDBFile onGetSendDBFileByGmail();
    SendDBFile onGetSendDBFileByFirebase();
}
