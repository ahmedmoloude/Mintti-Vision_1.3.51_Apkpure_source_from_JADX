package androidx.test.internal.runner.tracker;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import androidx.test.internal.util.Checks;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class AnalyticsBasedUsageTracker implements UsageTracker {
    private static final String API_LEVEL_PARAM = "&cd2=";
    private static final String APP_NAME_PARAM = "an=";
    private static final String APP_VERSION_PARAM = "&av=";
    private static final String CLIENT_ID_PARAM = "&cid=";
    private static final String MODEL_NAME_PARAM = "&cd3=";
    private static final String SCREEN_NAME_PARAM = "&cd=";
    private static final String SCREEN_RESOLUTION_PARAM = "&sr=";
    private static final String TAG = "InfraTrack";
    private static final String TRACKER_ID_PARAM = "&tid=";
    private static final String UTF_8 = "UTF-8";
    private final URL analyticsURI;
    private final String apiLevel;
    private final String model;
    private final String screenResolution;
    private final String targetPackage;
    private final String trackingId;
    private final Map<String, String> usageTypeToVersion;
    private final String userId;

    private AnalyticsBasedUsageTracker(Builder builder) {
        this.usageTypeToVersion = new HashMap();
        this.trackingId = (String) Checks.checkNotNull(builder.trackingId);
        this.targetPackage = (String) Checks.checkNotNull(builder.targetPackage);
        this.analyticsURI = (URL) Checks.checkNotNull(builder.analyticsURI);
        this.apiLevel = (String) Checks.checkNotNull(builder.apiLevel);
        this.model = (String) Checks.checkNotNull(builder.model);
        this.screenResolution = (String) Checks.checkNotNull(builder.screenResolution);
        this.userId = (String) Checks.checkNotNull(builder.userId);
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public URL analyticsURI;
        private Uri analyticsUri = new Uri.Builder().scheme("https").authority("www.google-analytics.com").path("collect").build();
        /* access modifiers changed from: private */
        public String apiLevel = String.valueOf(Build.VERSION.SDK_INT);
        private boolean hashed;
        /* access modifiers changed from: private */
        public String model = Build.MODEL;
        /* access modifiers changed from: private */
        public String screenResolution;
        private final Context targetContext;
        /* access modifiers changed from: private */
        public String targetPackage;
        /* access modifiers changed from: private */
        public String trackingId = "UA-36650409-3";
        /* access modifiers changed from: private */
        public String userId;

        public Builder(Context context) {
            Objects.requireNonNull(context, "Context null!?");
            this.targetContext = context;
        }

        public Builder withTrackingId(String str) {
            this.trackingId = str;
            return this;
        }

        public Builder withAnalyticsUri(Uri uri) {
            Checks.checkNotNull(uri);
            this.analyticsUri = uri;
            return this;
        }

        public Builder withApiLevel(String str) {
            this.apiLevel = str;
            return this;
        }

        public Builder withScreenResolution(String str) {
            this.screenResolution = str;
            return this;
        }

        public Builder withUserId(String str) {
            this.userId = str;
            return this;
        }

        public Builder withModel(String str) {
            this.model = str;
            return this;
        }

        public Builder withTargetPackage(String str) {
            this.hashed = false;
            this.targetPackage = str;
            return this;
        }

        public UsageTracker buildIfPossible() {
            if (!hasInternetPermission()) {
                Log.d(AnalyticsBasedUsageTracker.TAG, "Tracking disabled due to lack of internet permissions");
                return null;
            }
            if (this.targetPackage == null) {
                withTargetPackage(this.targetContext.getPackageName());
            }
            if (this.targetPackage.contains("com.google.analytics")) {
                Log.d(AnalyticsBasedUsageTracker.TAG, "Refusing to use analytics while testing analytics.");
                return null;
            }
            try {
                if (!this.targetPackage.startsWith("com.google.") && !this.targetPackage.startsWith("com.android.")) {
                    if (!this.targetPackage.startsWith("android.support.")) {
                        if (!this.hashed) {
                            MessageDigest instance = MessageDigest.getInstance("SHA-256");
                            instance.reset();
                            instance.update(this.targetPackage.getBytes("UTF-8"));
                            String valueOf = String.valueOf(new BigInteger(instance.digest()).toString(16));
                            this.targetPackage = valueOf.length() != 0 ? "sha256-".concat(valueOf) : new String("sha256-");
                        }
                        this.hashed = true;
                    }
                }
                try {
                    this.analyticsURI = new URL(this.analyticsUri.toString());
                    if (this.screenResolution == null) {
                        Display defaultDisplay = ((WindowManager) this.targetContext.getSystemService("window")).getDefaultDisplay();
                        if (defaultDisplay == null) {
                            this.screenResolution = "0x0";
                        } else {
                            this.screenResolution = defaultDisplay.getWidth() + "x" + defaultDisplay.getHeight();
                        }
                    }
                    if (this.userId == null) {
                        this.userId = UUID.randomUUID().toString();
                    }
                    return new AnalyticsBasedUsageTracker(this);
                } catch (MalformedURLException e) {
                    String valueOf2 = String.valueOf(this.analyticsUri.toString());
                    Log.w(AnalyticsBasedUsageTracker.TAG, valueOf2.length() != 0 ? "Tracking disabled bad url: ".concat(valueOf2) : new String("Tracking disabled bad url: "), e);
                    return null;
                }
            } catch (NoSuchAlgorithmException e2) {
                Log.d(AnalyticsBasedUsageTracker.TAG, "Cannot hash package name.", e2);
                return null;
            } catch (UnsupportedEncodingException e3) {
                Log.d(AnalyticsBasedUsageTracker.TAG, "Impossible - no utf-8 encoding?", e3);
                return null;
            }
        }

        private boolean hasInternetPermission() {
            return this.targetContext.checkCallingOrSelfPermission("android.permission.INTERNET") == 0;
        }
    }

    public void trackUsage(String str, String str2) {
        synchronized (this.usageTypeToVersion) {
            this.usageTypeToVersion.put(str, str2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r2 = APP_NAME_PARAM + java.net.URLEncoder.encode(r11.targetPackage, "UTF-8") + TRACKER_ID_PARAM + java.net.URLEncoder.encode(r11.trackingId, "UTF-8") + "&v=1" + "&z=" + android.os.SystemClock.uptimeMillis() + CLIENT_ID_PARAM + java.net.URLEncoder.encode(r11.userId, "UTF-8") + SCREEN_RESOLUTION_PARAM + java.net.URLEncoder.encode(r11.screenResolution, "UTF-8") + API_LEVEL_PARAM + java.net.URLEncoder.encode(r11.apiLevel, "UTF-8") + MODEL_NAME_PARAM + java.net.URLEncoder.encode(r11.model, "UTF-8") + "&t=appview" + "&sc=start";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x00a0, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x00a1, code lost:
        android.util.Log.w(TAG, "Impossible error happened. analytics disabled.", r2);
        r2 = null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x01a2  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b1 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendUsages() {
        /*
            r11 = this;
            java.util.Map<java.lang.String, java.lang.String> r0 = r11.usageTypeToVersion
            monitor-enter(r0)
            java.util.Map<java.lang.String, java.lang.String> r1 = r11.usageTypeToVersion     // Catch:{ all -> 0x01a7 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x01a7 }
            if (r1 == 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x01a7 }
            return
        L_0x000d:
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x01a7 }
            java.util.Map<java.lang.String, java.lang.String> r2 = r11.usageTypeToVersion     // Catch:{ all -> 0x01a7 }
            r1.<init>(r2)     // Catch:{ all -> 0x01a7 }
            java.util.Map<java.lang.String, java.lang.String> r2 = r11.usageTypeToVersion     // Catch:{ all -> 0x01a7 }
            r2.clear()     // Catch:{ all -> 0x01a7 }
            monitor-exit(r0)     // Catch:{ all -> 0x01a7 }
            r0 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00a0 }
            r2.<init>()     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = "an="
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = r11.targetPackage     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r4 = "UTF-8"
            java.lang.String r3 = java.net.URLEncoder.encode(r3, r4)     // Catch:{ IOException -> 0x00a0 }
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = "&tid="
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = r11.trackingId     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r4 = "UTF-8"
            java.lang.String r3 = java.net.URLEncoder.encode(r3, r4)     // Catch:{ IOException -> 0x00a0 }
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = "&v=1"
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = "&z="
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            long r3 = android.os.SystemClock.uptimeMillis()     // Catch:{ IOException -> 0x00a0 }
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = "&cid="
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = r11.userId     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r4 = "UTF-8"
            java.lang.String r3 = java.net.URLEncoder.encode(r3, r4)     // Catch:{ IOException -> 0x00a0 }
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = "&sr="
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = r11.screenResolution     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r4 = "UTF-8"
            java.lang.String r3 = java.net.URLEncoder.encode(r3, r4)     // Catch:{ IOException -> 0x00a0 }
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = "&cd2="
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = r11.apiLevel     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r4 = "UTF-8"
            java.lang.String r3 = java.net.URLEncoder.encode(r3, r4)     // Catch:{ IOException -> 0x00a0 }
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = "&cd3="
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = r11.model     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r4 = "UTF-8"
            java.lang.String r3 = java.net.URLEncoder.encode(r3, r4)     // Catch:{ IOException -> 0x00a0 }
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = "&t=appview"
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r3 = "&sc=start"
            r2.append(r3)     // Catch:{ IOException -> 0x00a0 }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x00a0 }
            goto L_0x00a9
        L_0x00a0:
            r2 = move-exception
            java.lang.String r3 = "InfraTrack"
            java.lang.String r4 = "Impossible error happened. analytics disabled."
            android.util.Log.w(r3, r4, r2)
            r2 = r0
        L_0x00a9:
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x00b1:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x01a6
            java.lang.Object r3 = r1.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.net.URL r4 = r11.analyticsURI     // Catch:{ IOException -> 0x016d, all -> 0x0169 }
            java.net.URLConnection r4 = r4.openConnection()     // Catch:{ IOException -> 0x016d, all -> 0x0169 }
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4     // Catch:{ IOException -> 0x016d, all -> 0x0169 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0167 }
            r5.<init>()     // Catch:{ IOException -> 0x0167 }
            r5.append(r2)     // Catch:{ IOException -> 0x0167 }
            java.lang.String r6 = "&cd="
            r5.append(r6)     // Catch:{ IOException -> 0x0167 }
            java.lang.Object r6 = r3.getKey()     // Catch:{ IOException -> 0x0167 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ IOException -> 0x0167 }
            java.lang.String r7 = "UTF-8"
            java.lang.String r6 = java.net.URLEncoder.encode(r6, r7)     // Catch:{ IOException -> 0x0167 }
            r5.append(r6)     // Catch:{ IOException -> 0x0167 }
            java.lang.String r6 = "&av="
            r5.append(r6)     // Catch:{ IOException -> 0x0167 }
            java.lang.Object r6 = r3.getValue()     // Catch:{ IOException -> 0x0167 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ IOException -> 0x0167 }
            java.lang.String r7 = "UTF-8"
            java.lang.String r6 = java.net.URLEncoder.encode(r6, r7)     // Catch:{ IOException -> 0x0167 }
            r5.append(r6)     // Catch:{ IOException -> 0x0167 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0167 }
            byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x0167 }
            r6 = 3000(0xbb8, float:4.204E-42)
            r4.setConnectTimeout(r6)     // Catch:{ IOException -> 0x0167 }
            r6 = 5000(0x1388, float:7.006E-42)
            r4.setReadTimeout(r6)     // Catch:{ IOException -> 0x0167 }
            r6 = 1
            r4.setDoOutput(r6)     // Catch:{ IOException -> 0x0167 }
            int r6 = r5.length     // Catch:{ IOException -> 0x0167 }
            r4.setFixedLengthStreamingMode(r6)     // Catch:{ IOException -> 0x0167 }
            java.io.OutputStream r6 = r4.getOutputStream()     // Catch:{ IOException -> 0x0167 }
            r6.write(r5)     // Catch:{ IOException -> 0x0167 }
            int r5 = r4.getResponseCode()     // Catch:{ IOException -> 0x0167 }
            int r5 = r5 / 100
            r6 = 2
            if (r5 == r6) goto L_0x0164
            java.lang.String r5 = "InfraTrack"
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ IOException -> 0x0167 }
            int r7 = r4.getResponseCode()     // Catch:{ IOException -> 0x0167 }
            java.lang.String r8 = r4.getResponseMessage()     // Catch:{ IOException -> 0x0167 }
            java.lang.String r9 = java.lang.String.valueOf(r6)     // Catch:{ IOException -> 0x0167 }
            int r9 = r9.length()     // Catch:{ IOException -> 0x0167 }
            int r9 = r9 + 45
            java.lang.String r10 = java.lang.String.valueOf(r8)     // Catch:{ IOException -> 0x0167 }
            int r10 = r10.length()     // Catch:{ IOException -> 0x0167 }
            int r9 = r9 + r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0167 }
            r10.<init>(r9)     // Catch:{ IOException -> 0x0167 }
            java.lang.String r9 = "Analytics post: "
            r10.append(r9)     // Catch:{ IOException -> 0x0167 }
            r10.append(r6)     // Catch:{ IOException -> 0x0167 }
            java.lang.String r6 = " failed. code: "
            r10.append(r6)     // Catch:{ IOException -> 0x0167 }
            r10.append(r7)     // Catch:{ IOException -> 0x0167 }
            java.lang.String r6 = " - "
            r10.append(r6)     // Catch:{ IOException -> 0x0167 }
            r10.append(r8)     // Catch:{ IOException -> 0x0167 }
            java.lang.String r6 = r10.toString()     // Catch:{ IOException -> 0x0167 }
            android.util.Log.w(r5, r6)     // Catch:{ IOException -> 0x0167 }
        L_0x0164:
            if (r4 == 0) goto L_0x00b1
            goto L_0x019a
        L_0x0167:
            r5 = move-exception
            goto L_0x016f
        L_0x0169:
            r1 = move-exception
            r4 = r0
            r0 = r1
            goto L_0x01a0
        L_0x016d:
            r5 = move-exception
            r4 = r0
        L_0x016f:
            java.lang.String r6 = "InfraTrack"
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x019f }
            java.lang.String r7 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x019f }
            int r7 = r7.length()     // Catch:{ all -> 0x019f }
            int r7 = r7 + 25
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x019f }
            r8.<init>(r7)     // Catch:{ all -> 0x019f }
            java.lang.String r7 = "Analytics post: "
            r8.append(r7)     // Catch:{ all -> 0x019f }
            r8.append(r3)     // Catch:{ all -> 0x019f }
            java.lang.String r3 = " failed. "
            r8.append(r3)     // Catch:{ all -> 0x019f }
            java.lang.String r3 = r8.toString()     // Catch:{ all -> 0x019f }
            android.util.Log.w(r6, r3, r5)     // Catch:{ all -> 0x019f }
            if (r4 == 0) goto L_0x00b1
        L_0x019a:
            r4.disconnect()
            goto L_0x00b1
        L_0x019f:
            r0 = move-exception
        L_0x01a0:
            if (r4 == 0) goto L_0x01a5
            r4.disconnect()
        L_0x01a5:
            throw r0
        L_0x01a6:
            return
        L_0x01a7:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x01a7 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.internal.runner.tracker.AnalyticsBasedUsageTracker.sendUsages():void");
    }
}
