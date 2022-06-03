package com.viettel.senddb;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

public class SendDBFileByGmail implements SendDBFile {

    private final String subject;
    private final String content;
    private final String[] recipients;

    public SendDBFileByGmail(@NonNull String subject, @NonNull String content, @NonNull String[] recipients) {
        this.subject = subject;
        this.content = content;
        this.recipients = recipients;
    }

    @Override
    public void send(Context context, Uri fileUri, DBMetadata dbMetadata, SendDBFileCallback callback) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final PackageManager pm = context.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm")
                    || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null) {
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        }
        try {
            context.startActivity(emailIntent);
        } catch (Exception e) {
            Log.e("Send db by gmail", e.getMessage());
            if (callback != null) {
                callback.onSendFailed(e);
            }
        }
    }

    @Override
    public void cancel() {

    }
}
