package com.tencent.bugly;

import com.tencent.bugly.proguard.C2231aa;
import java.util.Map;

/* compiled from: BUGLY */
public class BuglyStrategy {

    /* renamed from: a */
    protected int f1261a = 31;

    /* renamed from: b */
    protected boolean f1262b = false;

    /* renamed from: c */
    private String f1263c;

    /* renamed from: d */
    private String f1264d;

    /* renamed from: e */
    private String f1265e;

    /* renamed from: f */
    private long f1266f;

    /* renamed from: g */
    private String f1267g;

    /* renamed from: h */
    private String f1268h;

    /* renamed from: i */
    private String f1269i;

    /* renamed from: j */
    private boolean f1270j = true;

    /* renamed from: k */
    private boolean f1271k = true;

    /* renamed from: l */
    private boolean f1272l = true;

    /* renamed from: m */
    private boolean f1273m = false;

    /* renamed from: n */
    private boolean f1274n = true;

    /* renamed from: o */
    private Class<?> f1275o = null;

    /* renamed from: p */
    private boolean f1276p = true;

    /* renamed from: q */
    private boolean f1277q = true;

    /* renamed from: r */
    private boolean f1278r = true;

    /* renamed from: s */
    private boolean f1279s = true;

    /* renamed from: t */
    private boolean f1280t = false;

    /* renamed from: u */
    private C2217a f1281u;

    /* renamed from: v */
    private boolean f1282v = false;

    public synchronized BuglyStrategy setBuglyLogUpload(boolean z) {
        this.f1276p = z;
        return this;
    }

    public synchronized BuglyStrategy setRecordUserInfoOnceADay(boolean z) {
        this.f1280t = z;
        return this;
    }

    public synchronized BuglyStrategy setUploadProcess(boolean z) {
        this.f1278r = z;
        return this;
    }

    public synchronized boolean isUploadProcess() {
        return this.f1278r;
    }

    public synchronized boolean isBuglyLogUpload() {
        return this.f1276p;
    }

    public synchronized boolean recordUserInfoOnceADay() {
        return this.f1280t;
    }

    public boolean isReplaceOldChannel() {
        return this.f1277q;
    }

    public void setReplaceOldChannel(boolean z) {
        this.f1277q = z;
    }

    public synchronized String getAppVersion() {
        String str = this.f1263c;
        if (str != null) {
            return str;
        }
        return C2231aa.m459b().f1483o;
    }

    public synchronized BuglyStrategy setAppVersion(String str) {
        this.f1263c = str;
        return this;
    }

    public synchronized BuglyStrategy setUserInfoActivity(Class<?> cls) {
        this.f1275o = cls;
        return this;
    }

    public synchronized Class<?> getUserInfoActivity() {
        return this.f1275o;
    }

    public synchronized String getAppChannel() {
        String str = this.f1264d;
        if (str != null) {
            return str;
        }
        return C2231aa.m459b().f1487s;
    }

    public synchronized BuglyStrategy setAppChannel(String str) {
        this.f1264d = str;
        return this;
    }

    public synchronized String getAppPackageName() {
        String str = this.f1265e;
        if (str != null) {
            return str;
        }
        return C2231aa.m459b().f1471c;
    }

    public synchronized BuglyStrategy setAppPackageName(String str) {
        this.f1265e = str;
        return this;
    }

    public synchronized long getAppReportDelay() {
        return this.f1266f;
    }

    public synchronized BuglyStrategy setAppReportDelay(long j) {
        this.f1266f = j;
        return this;
    }

    public synchronized String getLibBuglySOFilePath() {
        return this.f1267g;
    }

    public synchronized BuglyStrategy setLibBuglySOFilePath(String str) {
        this.f1267g = str;
        return this;
    }

    public synchronized String getDeviceID() {
        return this.f1268h;
    }

    public synchronized BuglyStrategy setDeviceID(String str) {
        this.f1268h = str;
        return this;
    }

    public synchronized String getDeviceModel() {
        return this.f1269i;
    }

    public synchronized BuglyStrategy setDeviceModel(String str) {
        this.f1269i = str;
        return this;
    }

    public synchronized boolean isEnableNativeCrashMonitor() {
        return this.f1270j;
    }

    public synchronized BuglyStrategy setEnableNativeCrashMonitor(boolean z) {
        this.f1270j = z;
        return this;
    }

    public synchronized BuglyStrategy setEnableUserInfo(boolean z) {
        this.f1274n = z;
        return this;
    }

    public synchronized boolean isEnableUserInfo() {
        return this.f1274n;
    }

    public synchronized boolean isEnableCatchAnrTrace() {
        return this.f1272l;
    }

    public void setEnableCatchAnrTrace(boolean z) {
        this.f1272l = z;
    }

    public void setEnableRecordAnrMainStack(boolean z) {
        this.f1273m = z;
    }

    public boolean isEnableRecordAnrMainStack() {
        return this.f1273m;
    }

    public synchronized boolean isEnableANRCrashMonitor() {
        return this.f1271k;
    }

    public synchronized BuglyStrategy setEnableANRCrashMonitor(boolean z) {
        this.f1271k = z;
        return this;
    }

    public synchronized C2217a getCrashHandleCallback() {
        return this.f1281u;
    }

    public synchronized BuglyStrategy setCrashHandleCallback(C2217a aVar) {
        this.f1281u = aVar;
        return this;
    }

    public synchronized void setCallBackType(int i) {
        this.f1261a = i;
    }

    public synchronized int getCallBackType() {
        return this.f1261a;
    }

    public synchronized void setCloseErrorCallback(boolean z) {
        this.f1262b = z;
    }

    public synchronized boolean getCloseErrorCallback() {
        return this.f1262b;
    }

    public boolean isMerged() {
        return this.f1282v;
    }

    @Deprecated
    public void setMerged(boolean z) {
        this.f1282v = z;
    }

    public synchronized void setUploadSpotCrash(boolean z) {
        this.f1279s = z;
    }

    public synchronized boolean isUploadSpotCrash() {
        return this.f1279s;
    }

    /* renamed from: com.tencent.bugly.BuglyStrategy$a */
    /* compiled from: BUGLY */
    public static class C2217a {
        public static final int CRASHTYPE_ANR = 4;
        public static final int CRASHTYPE_BLOCK = 7;
        public static final int CRASHTYPE_COCOS2DX_JS = 5;
        public static final int CRASHTYPE_COCOS2DX_LUA = 6;
        public static final int CRASHTYPE_JAVA_CATCH = 1;
        public static final int CRASHTYPE_JAVA_CRASH = 0;
        public static final int CRASHTYPE_NATIVE = 2;
        public static final int CRASHTYPE_U3D = 3;
        public static final int MAX_USERDATA_KEY_LENGTH = 100;
        public static final int MAX_USERDATA_VALUE_LENGTH = 100000;

        public synchronized Map<String, String> onCrashHandleStart(int i, String str, String str2, String str3) {
            return null;
        }

        public synchronized byte[] onCrashHandleStart2GetExtraDatas(int i, String str, String str2, String str3) {
            return null;
        }
    }
}
