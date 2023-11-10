package com.p020kl.commonbase.net.interceptor;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/* renamed from: com.kl.commonbase.net.interceptor.DownloadProgressInterceptor */
public class DownloadProgressInterceptor implements Interceptor {
    private ProgressResponseListener progressListener;

    public DownloadProgressInterceptor(ProgressResponseListener progressResponseListener) {
        this.progressListener = progressResponseListener;
    }

    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response proceed = chain.proceed(chain.request());
        return proceed.newBuilder().body(new DownloadProgressResponseBody(proceed.body(), this.progressListener)).build();
    }
}
