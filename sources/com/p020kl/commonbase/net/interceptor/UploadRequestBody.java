package com.p020kl.commonbase.net.interceptor;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/* renamed from: com.kl.commonbase.net.interceptor.UploadRequestBody */
public class UploadRequestBody extends RequestBody {
    protected CountingSink countingSink;
    protected RequestBody delegate;
    protected ProgressResponseListener progressResponseListener;

    public UploadRequestBody(RequestBody requestBody, ProgressResponseListener progressResponseListener2) {
        this.delegate = requestBody;
        this.progressResponseListener = progressResponseListener2;
    }

    public MediaType contentType() {
        return this.delegate.contentType();
    }

    public long contentLength() {
        try {
            return this.delegate.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void writeTo(BufferedSink bufferedSink) throws IOException {
        CountingSink countingSink2 = new CountingSink(bufferedSink);
        this.countingSink = countingSink2;
        BufferedSink buffer = Okio.buffer((Sink) countingSink2);
        this.delegate.writeTo(buffer);
        buffer.flush();
    }

    /* renamed from: com.kl.commonbase.net.interceptor.UploadRequestBody$CountingSink */
    protected final class CountingSink extends ForwardingSink {
        long bytesWritten = 0;
        long contentLength = 0;

        public CountingSink(Sink sink) {
            super(sink);
        }

        public void write(Buffer buffer, long j) throws IOException {
            super.write(buffer, j);
            if (this.contentLength == 0) {
                this.contentLength = UploadRequestBody.this.contentLength();
            }
            this.bytesWritten += j;
            if (UploadRequestBody.this.progressResponseListener != null) {
                ProgressResponseListener progressResponseListener = UploadRequestBody.this.progressResponseListener;
                long j2 = this.bytesWritten;
                long j3 = this.contentLength;
                progressResponseListener.onResponseProgress(j2, j3, j2 == j3);
            }
        }
    }
}
