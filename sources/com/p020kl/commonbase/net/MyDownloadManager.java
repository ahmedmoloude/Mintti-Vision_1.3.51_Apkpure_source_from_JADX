package com.p020kl.commonbase.net;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.p020kl.commonbase.callback.DownloadManagerListener;
import java.io.File;

/* renamed from: com.kl.commonbase.net.MyDownloadManager */
public class MyDownloadManager {
    private Context context;
    /* access modifiers changed from: private */
    public long downloadId;
    /* access modifiers changed from: private */
    public DownloadManager downloadManager;
    /* access modifiers changed from: private */
    public DownloadManagerListener listener;
    private String name;
    /* access modifiers changed from: private */
    public String path;
    /* access modifiers changed from: private */
    public BroadcastReceiver receiver;
    private String url;

    public MyDownloadManager(Context context2, String str) {
        this(context2, str, getFileNameByUrl(str));
    }

    public MyDownloadManager(Context context2, String str, String str2) {
        this(context2, str, str2, "");
    }

    public MyDownloadManager(Context context2, String str, String str2, String str3) {
        this.receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(new long[]{MyDownloadManager.this.downloadId});
                Cursor query2 = MyDownloadManager.this.downloadManager.query(query);
                if (query2.moveToFirst()) {
                    int i = query2.getInt(query2.getColumnIndex(NotificationCompat.CATEGORY_STATUS));
                    if (i == 8) {
                        if (MyDownloadManager.this.listener != null) {
                            MyDownloadManager.this.listener.onSuccess(MyDownloadManager.this.path);
                        }
                        query2.close();
                        context.unregisterReceiver(MyDownloadManager.this.receiver);
                    } else if (i == 16) {
                        if (MyDownloadManager.this.listener != null) {
                            MyDownloadManager.this.listener.onFailed();
                        }
                        query2.close();
                        context.unregisterReceiver(MyDownloadManager.this.receiver);
                    }
                }
            }
        };
        this.context = context2;
        this.url = str;
        this.name = str2;
        this.path = str3;
    }

    public MyDownloadManager setListener(DownloadManagerListener downloadManagerListener) {
        this.listener = downloadManagerListener;
        return this;
    }

    public void download() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(this.url));
        request.setNotificationVisibility(1);
        request.setAllowedNetworkTypes(3);
        request.setTitle(this.name);
        request.setDescription("downloading......");
        request.setVisibleInDownloadsUi(true);
        if (TextUtils.isEmpty(this.path)) {
            File file = new File(this.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), this.name);
            request.setDestinationUri(Uri.fromFile(file));
            this.path = file.getAbsolutePath();
        } else {
            request.setDestinationUri(Uri.fromFile(new File(this.path)));
        }
        if (this.downloadManager == null) {
            this.downloadManager = (DownloadManager) this.context.getSystemService("download");
        }
        if (this.downloadManager != null) {
            DownloadManagerListener downloadManagerListener = this.listener;
            if (downloadManagerListener != null) {
                downloadManagerListener.onPrepare();
            }
            this.downloadId = this.downloadManager.enqueue(request);
        }
        this.context.registerReceiver(this.receiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
    }

    private static final String getFileNameByUrl(String str) {
        String substring = str.substring(str.lastIndexOf("/") + 1);
        return substring.substring(0, substring.indexOf("?") == -1 ? substring.length() : substring.indexOf("?"));
    }
}
