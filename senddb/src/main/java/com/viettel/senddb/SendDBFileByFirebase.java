package com.viettel.senddb;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class SendDBFileByFirebase implements SendDBFile {

    @Override
    public void send(Context context, Uri fileUri, DBMetadata dbMetadata, SendDBFileCallback callback) {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStorageRef.child("data/" + dbMetadata.toFilePath());
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("STAFF_ID", dbMetadata.getUserId())
                .setCustomMetadata("SHOP_ID", dbMetadata.getShopId())
                .setCustomMetadata("ROLE_ID", dbMetadata.getRoleId()).build();
        if (dbMetadata.getAppName() == null || dbMetadata.getAppName().isEmpty()) {
            if (callback != null) {
                callback.onSendFailed(new Exception("app name can not null or empty"));
            }
            return;
        }
        if (callback != null && context != null) {
            String notifyString = context.getString(R.string.PRE_SEND_DB_BY_FIREBASE);
            final boolean BLOCK_CAUSE_BY_NOT_OPEN_ANY_ACTIVITY = true;
            callback.onPreSend(notifyString, BLOCK_CAUSE_BY_NOT_OPEN_ANY_ACTIVITY);
        }
        StorageTask<UploadTask.TaskSnapshot> uploadTask = riversRef.putFile(fileUri, metadata)
                .addOnSuccessListener(taskSnapshot -> {
                    Log.i("UPLOAD", "Upload complete ");
                    if (callback != null) {
                        callback.onSendSuccessfully(taskSnapshot);
                    }
                })
                .addOnProgressListener(snapshot -> {
                    Log.i("UPLOAD", "Uploaded: " + snapshot.getBytesTransferred() + "/" + snapshot.getTotalByteCount());
                    if (callback != null) {
                        callback.onProgress(snapshot.getBytesTransferred(), snapshot.getTotalByteCount());
                    }
                })
                .addOnFailureListener((e) -> {
                    if (callback != null) {
                        callback.onSendFailed(e);
                    }
                })
                .addOnCanceledListener(() -> {
                    if (callback != null) {
                        callback.onSendFailed(new Exception("cancel upload"));
                    }
                });
    }

    @Override
    public void cancel() {

    }
}
