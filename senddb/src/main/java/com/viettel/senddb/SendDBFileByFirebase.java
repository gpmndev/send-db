package com.viettel.senddb;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class SendDBFileByFirebase implements SendDBFile {

    StorageTask<UploadTask.TaskSnapshot> uploadTask = null;

    @Override
    public void send(Context context, Uri fileUri, DBMetadata dbMetadata, SendDBFileCallback callback) {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStorageRef.child("data/" + dbMetadata.toFilePath());
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("STAFF_ID", dbMetadata.getUserId())
                .setCustomMetadata("SHOP_ID", dbMetadata.getShopId())
                .setCustomMetadata("ROLE_ID", dbMetadata.getRoleId())
                .build();
        if (dbMetadata.getAppName() == null || dbMetadata.getAppName().isEmpty()) {
            if (callback != null) {
                callback.onSendFailed(null, new Exception("SEND DB: App name can not null or empty"));
            }
            return;
        }
        if (callback != null && context != null) {
            String notifyString = context.getString(R.string.PRE_SEND_DB_BY_FIREBASE);
            final boolean BLOCK_CAUSE_BY_NOT_OPEN_ANY_ACTIVITY = true;
            callback.onPreSend(notifyString, BLOCK_CAUSE_BY_NOT_OPEN_ANY_ACTIVITY);
        }
        uploadTask = riversRef.putFile(fileUri, metadata)
                .addOnSuccessListener(taskSnapshot -> {
                    if (context != null) {
                        SendDBCache.increaseNumberOfTimesSendDB(context, SendDBFileMethod.FIREBASE);
                    }
                    if (callback != null) {
                        String notify = null;
                        if (context != null) {
                            notify = context.getString(R.string.TEXT_DATA_SEND_SUCCESS);
                        }
                        callback.onSendSuccessful(notify, taskSnapshot);
                    }
                })
                .addOnProgressListener(snapshot -> {
                    if (callback != null) {
                        callback.onProgress(snapshot.getBytesTransferred(), snapshot.getTotalByteCount());
                    }
                })
                .addOnFailureListener((e) -> {
                    if (callback != null) {
                        callback.onSendFailed(null, e);
                    }
                })
                .addOnCanceledListener(() -> {
                    if (callback != null) {
                        String notify = null;
                        if (context != null) {
                            notify = context.getString(R.string.CANCEL_SEND_DB);
                        }
                        callback.onSendFailed(notify, new Exception("cancel upload"));
                    }
                });
    }

    @Override
    public void cancel() {
        try {
            uploadTask.cancel();
        } catch (Exception ignore) {

        }
    }
}
