package com.viettel.senddb;

import android.content.Context;
import android.net.Uri;

public interface SendDBFile {
    void send(Context context, Uri fileUri, DBMetadata dbMetadata, SendDBFileCallback callback);

    void cancel();
}
