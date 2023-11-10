package com.p020kl.commonbase.net;

import okhttp3.Interceptor;
import okhttp3.Response;

/* renamed from: com.kl.commonbase.net.-$$Lambda$RestServiceCreator$9z0QVAMLAQlzM7KMvHIZ4CspHG0  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$RestServiceCreator$9z0QVAMLAQlzM7KMvHIZ4CspHG0 implements Interceptor {
    public static final /* synthetic */ $$Lambda$RestServiceCreator$9z0QVAMLAQlzM7KMvHIZ4CspHG0 INSTANCE = new $$Lambda$RestServiceCreator$9z0QVAMLAQlzM7KMvHIZ4CspHG0();

    private /* synthetic */ $$Lambda$RestServiceCreator$9z0QVAMLAQlzM7KMvHIZ4CspHG0() {
    }

    public final Response intercept(Interceptor.Chain chain) {
        return chain.request().newBuilder();
    }
}
