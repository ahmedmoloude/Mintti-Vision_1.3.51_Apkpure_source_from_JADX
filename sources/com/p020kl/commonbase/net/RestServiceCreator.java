package com.p020kl.commonbase.net;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.net.converter.ResponseConverterFactory;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.interceptor.DownloadProgressInterceptor;
import com.p020kl.commonbase.net.interceptor.ProgressResponseListener;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* renamed from: com.kl.commonbase.net.RestServiceCreator */
public class RestServiceCreator {
    public static final String BASE_URL = "http://vision.mintti.cn/vision/";
    private static final int TIME_OUT = 30;
    public static final String TOKEN_UPDATE_URL = "http://vision.mintti.cn/vision/user/updateUserToken";
    private static OkHttpClient downloadHttpClient;
    private static OkHttpClient okHttpClient;
    private static OkHttpClient tokenClient;

    private static void initHttpClient() {
        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().sslSocketFactory(getSSLSocketFactory(), new CustomTrustManager()).hostnameVerifier(getHostnameVerifier()).connectTimeout(30, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).callTimeout(30, TimeUnit.SECONDS).addInterceptor($$Lambda$RestServiceCreator$9z0QVAMLAQlzM7KMvHIZ4CspHG0.INSTANCE).build();
        }
    }

    private static synchronized void initTokenHttpClient() {
        synchronized (RestServiceCreator.class) {
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            if (tokenClient == null || BaseApplication.isTokenExpire) {
                final String token = BaseApplication.getToken();
                BaseApplication.isTokenExpire = false;
                tokenClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).callTimeout(30, TimeUnit.SECONDS).addInterceptor(new Interceptor() {
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request.Builder newBuilder = chain.request().newBuilder();
                        newBuilder.addHeader("token", token);
                        newBuilder.addHeader("Accept-Language", Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry());
                        Response proceed = chain.proceed(newBuilder.build());
                        MediaType contentType = proceed.body().contentType();
                        String string = proceed.body().string();
                        if (RestServiceCreator.isTokenExpired(string)) {
                            System.out.println("request new token");
                            String access$000 = RestServiceCreator.getNewToken();
                            if (!TextUtils.isEmpty(access$000)) {
                                return chain.proceed(chain.request().newBuilder().addHeader("token", access$000).build());
                            }
                        }
                        return proceed.newBuilder().body(ResponseBody.create(contentType, string)).build();
                    }
                }).build();
            }
        }
    }

    private static void initDownloadHttpClient(ProgressResponseListener progressResponseListener) {
        if (downloadHttpClient == null) {
            downloadHttpClient = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS).callTimeout(120, TimeUnit.SECONDS).addInterceptor(new DownloadProgressInterceptor(progressResponseListener)).build();
        }
    }

    public static synchronized boolean isTokenExpired(String str) throws IOException {
        synchronized (RestServiceCreator.class) {
            if (((ResponseResult) new Gson().fromJson(str, new TypeToken<ResponseResult<Object>>() {
            }.getType())).getStatus() != 202) {
                return false;
            }
            BaseApplication.isTokenExpire = true;
            return true;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.String} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String getNewToken() throws java.io.IOException {
        /*
            java.lang.Class<com.kl.commonbase.net.RestServiceCreator> r0 = com.p020kl.commonbase.net.RestServiceCreator.class
            monitor-enter(r0)
            java.lang.String r1 = com.p020kl.commonbase.base.BaseApplication.getToken()     // Catch:{ all -> 0x005f }
            java.lang.String r2 = ""
            com.kl.commonbase.net.RestApiService r3 = getRestApiService()     // Catch:{ all -> 0x005f }
            retrofit2.Call r1 = r3.updateToken(r1)     // Catch:{ all -> 0x005f }
            retrofit2.Response r1 = r1.execute()     // Catch:{ all -> 0x005f }
            java.lang.Object r1 = r1.body()     // Catch:{ all -> 0x005f }
            com.kl.commonbase.net.entity.ResponseResult r1 = (com.p020kl.commonbase.net.entity.ResponseResult) r1     // Catch:{ all -> 0x005f }
            java.io.PrintStream r3 = java.lang.System.out     // Catch:{ all -> 0x005f }
            java.lang.String r4 = r1.toString()     // Catch:{ all -> 0x005f }
            r3.println(r4)     // Catch:{ all -> 0x005f }
            int r3 = r1.getStatus()     // Catch:{ all -> 0x005f }
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 != r4) goto L_0x004d
            java.lang.Object r1 = r1.getData()     // Catch:{ all -> 0x005f }
            r2 = r1
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x005f }
            com.p020kl.commonbase.base.BaseApplication.updateToken(r2)     // Catch:{ all -> 0x005f }
            java.io.PrintStream r1 = java.lang.System.out     // Catch:{ all -> 0x005f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x005f }
            r3.<init>()     // Catch:{ all -> 0x005f }
            java.lang.String r4 = "new token = "
            r3.append(r4)     // Catch:{ all -> 0x005f }
            r3.append(r2)     // Catch:{ all -> 0x005f }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x005f }
            r1.println(r3)     // Catch:{ all -> 0x005f }
            goto L_0x005d
        L_0x004d:
            int r1 = r1.getStatus()     // Catch:{ all -> 0x005f }
            r3 = 244(0xf4, float:3.42E-43)
            if (r1 != r3) goto L_0x005d
            com.kl.commonbase.event.TokenNotExistEvent r1 = new com.kl.commonbase.event.TokenNotExistEvent     // Catch:{ all -> 0x005f }
            r1.<init>()     // Catch:{ all -> 0x005f }
            com.p020kl.commonbase.utils.EventBusUtil.sendEvent(r1)     // Catch:{ all -> 0x005f }
        L_0x005d:
            monitor-exit(r0)
            return r2
        L_0x005f:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.commonbase.net.RestServiceCreator.getNewToken():java.lang.String");
    }

    public static RestApiService getRestApiService() {
        initHttpClient();
        return (RestApiService) new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(ResponseConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(RestApiService.class);
    }

    public static RestApiService getTokenApiService() {
        initTokenHttpClient();
        return (RestApiService) new Retrofit.Builder().baseUrl(BASE_URL).client(tokenClient).addConverterFactory(ResponseConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(RestApiService.class);
    }

    public static RestApiService getDownloadApiService(ProgressResponseListener progressResponseListener) {
        initDownloadHttpClient(progressResponseListener);
        return (RestApiService) new Retrofit.Builder().baseUrl(BASE_URL).client(downloadHttpClient).addConverterFactory(ResponseConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(RestApiService.class);
    }

    public static RestApiService getSessionsApiService() {
        initHttpClient();
        return (RestApiService) new Retrofit.Builder().baseUrl(Constants.URL_SESSIONS).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(RestApiService.class);
    }

    public static RestApiService getRothmanService() {
        initHttpClient();
        return (RestApiService) new Retrofit.Builder().baseUrl(Constants.URL_ROTHMAN_INDEX).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(RestApiService.class);
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init((KeyManager[]) null, new TrustManager[]{new CustomTrustManager()}, new SecureRandom());
            return instance.getSocketFactory();
        } catch (Exception unused) {
            return null;
        }
    }

    public static HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            public boolean verify(String str, SSLSession sSLSession) {
                return true;
            }
        };
    }
}
