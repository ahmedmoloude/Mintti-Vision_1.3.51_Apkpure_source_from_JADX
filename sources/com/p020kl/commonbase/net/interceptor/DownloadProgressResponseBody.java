package com.p020kl.commonbase.net.interceptor;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/* renamed from: com.kl.commonbase.net.interceptor.DownloadProgressResponseBody */
public class DownloadProgressResponseBody extends ResponseBody {
    private BufferedSource bufferedSource;
    /* access modifiers changed from: private */
    public final ProgressResponseListener progressListener;
    /* access modifiers changed from: private */
    public final ResponseBody responseBody;

    public DownloadProgressResponseBody(ResponseBody responseBody2, ProgressResponseListener progressResponseListener) {
        this.responseBody = responseBody2;
        this.progressListener = progressResponseListener;
    }

    public MediaType contentType() {
        return this.responseBody.contentType();
    }

    public long contentLength() {
        return this.responseBody.contentLength();
    }

    public BufferedSource source() {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(source(this.responseBody.source()));
        }
        return this.bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0;

            public long read(Buffer buffer, long j) throws IOException {
                long read = super.read(buffer, j);
                int i = (read > -1 ? 1 : (read == -1 ? 0 : -1));
                this.totalBytesRead += i != 0 ? read : 0;
                if (DownloadProgressResponseBody.this.progressListener != null) {
                    DownloadProgressResponseBody.this.progressListener.onResponseProgress(this.totalBytesRead, DownloadProgressResponseBody.this.responseBody.contentLength(), i == 0);
                }
                return read;
            }
        };
    }
}
