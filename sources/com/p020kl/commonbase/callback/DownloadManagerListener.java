package com.p020kl.commonbase.callback;

/* renamed from: com.kl.commonbase.callback.DownloadManagerListener */
public interface DownloadManagerListener {
    void onFailed();

    void onPrepare();

    void onSuccess(String str);
}
