package com.tencent.bugly.crashreport;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.CrashModule;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.crashreport.crash.p025h5.H5JavaScriptInterface;
import com.tencent.bugly.proguard.C2231aa;
import com.tencent.bugly.proguard.C2248ac;
import com.tencent.bugly.proguard.C2263ak;
import com.tencent.bugly.proguard.C2265al;
import com.tencent.bugly.proguard.C2268an;
import com.tencent.bugly.proguard.C2273ap;
import com.tencent.bugly.proguard.C2274aq;
import com.tencent.bugly.proguard.C2293at;
import com.tencent.bugly.proguard.C2298au;
import com.tencent.bugly.proguard.C2314bc;
import com.tencent.bugly.proguard.C2348o;
import com.tencent.bugly.proguard.C2349p;
import com.tencent.bugly.proguard.C2357s;
import com.tencent.bugly.proguard.C2365w;
import com.tencent.bugly.proguard.C2367x;
import com.tencent.bugly.proguard.C2368y;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: BUGLY */
public class CrashReport {

    /* renamed from: a */
    private static Context f1288a;

    /* compiled from: BUGLY */
    public static class CrashHandleCallback extends BuglyStrategy.C2217a {
    }

    /* renamed from: com.tencent.bugly.crashreport.CrashReport$a */
    /* compiled from: BUGLY */
    public interface C2220a {
        /* renamed from: a */
        String mo27887a();

        /* renamed from: a */
        void mo27888a(H5JavaScriptInterface h5JavaScriptInterface, String str);

        /* renamed from: a */
        void mo27889a(String str);

        /* renamed from: b */
        void mo27890b();

        /* renamed from: c */
        CharSequence mo27891c();
    }

    public static void enableBugly(boolean z) {
        C2349p.f1910a = z;
    }

    public static void initCrashReport(Context context) {
        if (context != null) {
            f1288a = context;
            C2349p.m969a((C2348o) CrashModule.getInstance());
            C2349p.m966a(context);
        }
    }

    public static void initCrashReport(Context context, UserStrategy userStrategy) {
        if (context != null) {
            f1288a = context;
            C2349p.m969a((C2348o) CrashModule.getInstance());
            C2349p.m967a(context, userStrategy);
        }
    }

    public static void initCrashReport(Context context, String str, boolean z) {
        initCrashReport(context, str, z, (UserStrategy) null);
    }

    public static void initCrashReport(Context context, String str, boolean z, UserStrategy userStrategy) {
        if (context != null) {
            f1288a = context;
            C2349p.m969a((C2348o) CrashModule.getInstance());
            C2349p.m968a(context, str, z, userStrategy);
        }
    }

    public static Context getContext() {
        return f1288a;
    }

    public static String getBuglyVersion(Context context) {
        if (context != null) {
            return C2231aa.m457a(context).f1476h;
        }
        C2265al.m610d("Please call with context.", new Object[0]);
        return "unknown";
    }

    public static void testJavaCrash() {
        int i;
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not test Java crash because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C2231aa b = C2231aa.m459b();
            if (!(b == null || (i = b.f1492x) == 24096)) {
                b.f1492x = 24096;
                C2265al.m604a("server scene tag %d changed to tag %d", Integer.valueOf(i), Integer.valueOf(b.f1492x));
            }
            throw new RuntimeException("This Crash create for Test! You can go to Bugly see more detail!");
        }
    }

    public static void testNativeCrash() {
        testNativeCrash(true, true, false);
    }

    public static void testNativeCrash(boolean z, boolean z2, boolean z3) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not test native crash because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C2265al.m604a("start to create a native crash for test!", new Object[0]);
            C2293at.m738a().mo28041a(z, z2, z3);
        }
    }

    public static void testANRCrash() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not test ANR crash because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C2265al.m604a("start to create a anr crash for test!", new Object[0]);
            C2293at.m738a().mo28048h();
        }
    }

    public static void postException(Thread thread, int i, String str, String str2, String str3, Map<String, String> map) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not post crash caught because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C2298au.m759a(thread, i, str, str2, str3, map);
        }
    }

    public static void postException(int i, String str, String str2, String str3, Map<String, String> map) {
        postException(Thread.currentThread(), i, str, str2, str3, map);
    }

    public static void postCatchedException(Throwable th) {
        postCatchedException(th, Thread.currentThread());
    }

    public static void postCatchedException(Throwable th, Thread thread) {
        postCatchedException(th, thread, false);
    }

    public static void postCatchedException(Throwable th, Thread thread, boolean z) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not post crash caught because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else if (th == null) {
            C2265al.m610d("throwable is null, just return", new Object[0]);
        } else {
            if (thread == null) {
                thread = Thread.currentThread();
            }
            C2293at a = C2293at.m738a();
            a.f1655v.mo28017a(new Runnable(thread, th, z) {

                /* renamed from: a */
                final /* synthetic */ boolean f1662a = false;

                /* renamed from: b */
                final /* synthetic */ Thread f1663b;

                /* renamed from: c */
                final /* synthetic */ Throwable f1664c;

                /* renamed from: d */
                final /* synthetic */ String f1665d;

                /* renamed from: e */
                final /* synthetic */ byte[] f1666e;

                /* renamed from: f */
                final /* synthetic */ boolean f1667f;

                /* renamed from: g */
                final /* synthetic */ boolean f1668g;

                {
                    this.f1663b = r2;
                    this.f1664c = r3;
                    this.f1665d = null;
                    this.f1666e = null;
                    this.f1667f = true;
                    this.f1668g = r4;
                }

                public final void run() {
                    try {
                        C2265al.m609c("post a throwable %b", Boolean.valueOf(this.f1662a));
                        C2293at.this.f1652s.mo28060a(this.f1663b, this.f1664c, false, this.f1665d, this.f1666e, this.f1667f);
                        if (this.f1668g) {
                            C2265al.m604a("clear user datas", new Object[0]);
                            C2231aa.m457a(C2293at.this.f1650c).mo27986u();
                        }
                    } catch (Throwable th) {
                        if (!C2265al.m608b(th)) {
                            th.printStackTrace();
                        }
                        C2265al.m611e("java catch error: %s", this.f1664c.toString());
                    }
                }
            });
        }
    }

    public static void setAllThreadStackEnable(Context context, boolean z, boolean z2) {
        C2231aa a = C2231aa.m457a(context);
        a.f1436Q = z;
        a.f1437R = z2;
    }

    public static void closeNativeReport() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not close native report because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C2293at.m738a().mo28044d();
        }
    }

    public static void startCrashReport() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not start crash report because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.w(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C2293at.m738a().mo28042b();
        }
    }

    public static void closeCrashReport() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not close crash report because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.w(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            C2293at.m738a().mo28043c();
        }
    }

    public static void closeBugly() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not close bugly because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.w(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else if (f1288a != null) {
            C2274aq a = C2274aq.m679a();
            if (a != null) {
                a.mo28029b(f1288a);
            }
            closeCrashReport();
            C2357s.m995a(f1288a);
            C2263ak a2 = C2263ak.m595a();
            if (a2 != null) {
                a2.mo28019b();
            }
        }
    }

    public static void setUserSceneTag(Context context, int i) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set tag caught because bugly is disable.");
        } else if (context == null) {
            Log.e(C2265al.f1567b, "setTag args context should not be null");
        } else {
            if (i <= 0) {
                C2265al.m610d("setTag args tagId should > 0", new Object[0]);
            }
            C2231aa a = C2231aa.m457a(context);
            synchronized (a.f1440U) {
                int i2 = a.f1491w;
                if (i2 != i) {
                    a.f1491w = i;
                    C2265al.m604a("user scene tag %d changed to tag %d", Integer.valueOf(i2), Integer.valueOf(a.f1491w));
                }
            }
            C2265al.m607b("[param] set user scene tag: %d", Integer.valueOf(i));
        }
    }

    public static int getUserSceneTagId(Context context) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not get user scene tag because bugly is disable.");
            return -1;
        } else if (context != null) {
            return C2231aa.m457a(context).mo27991z();
        } else {
            Log.e(C2265al.f1567b, "getUserSceneTagId args context should not be null");
            return -1;
        }
    }

    public static String getUserData(Context context, String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not get user data because bugly is disable.");
            return "unknown";
        } else if (context == null) {
            Log.e(C2265al.f1567b, "getUserDataValue args context should not be null");
            return "unknown";
        } else if (C2273ap.m657a(str)) {
            return null;
        } else {
            return C2231aa.m457a(context).mo27973g(str);
        }
    }

    public static void putUserData(Context context, String str, String str2) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not put user data because bugly is disable.");
        } else if (context == null) {
            Log.w(C2265al.f1567b, "putUserData args context should not be null");
        } else if (str == null) {
            new StringBuilder().append(str);
            C2265al.m610d("putUserData args key should not be null or empty", new Object[0]);
        } else if (str2 == null) {
            new StringBuilder().append(str2);
            C2265al.m610d("putUserData args value should not be null", new Object[0]);
        } else {
            if (str2.length() > 200) {
                C2265al.m610d("user data value length over limit %d, it will be cutted!", 200);
                str2 = str2.substring(0, 200);
            }
            C2231aa a = C2231aa.m457a(context);
            if (a.mo27988w().contains(str)) {
                NativeCrashHandler instance = NativeCrashHandler.getInstance();
                if (instance != null) {
                    instance.putKeyValueToNative(str, str2);
                }
                C2231aa.m457a(context).mo27960a(str, str2);
                C2265al.m609c("replace KV %s %s", str, str2);
            } else if (a.mo27987v() >= 50) {
                C2265al.m610d("user data size is over limit %d, it will be cutted!", 50);
            } else {
                if (str.length() > 50) {
                    C2265al.m610d("user data key length over limit %d , will drop this new key %s", 50, str);
                    str = str.substring(0, 50);
                }
                NativeCrashHandler instance2 = NativeCrashHandler.getInstance();
                if (instance2 != null) {
                    instance2.putKeyValueToNative(str, str2);
                }
                C2231aa.m457a(context).mo27960a(str, str2);
                C2265al.m607b("[param] set user data: %s - %s", str, str2);
            }
        }
    }

    public static String removeUserData(Context context, String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not remove user data because bugly is disable.");
            return "unknown";
        } else if (context == null) {
            Log.e(C2265al.f1567b, "removeUserData args context should not be null");
            return "unknown";
        } else if (C2273ap.m657a(str)) {
            return null;
        } else {
            C2265al.m607b("[param] remove user data: %s", str);
            return C2231aa.m457a(context).mo27971f(str);
        }
    }

    public static Set<String> getAllUserDataKeys(Context context) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not get all keys of user data because bugly is disable.");
            return new HashSet();
        } else if (context != null) {
            return C2231aa.m457a(context).mo27988w();
        } else {
            Log.e(C2265al.f1567b, "getAllUserDataKeys args context should not be null");
            return new HashSet();
        }
    }

    public static int getUserDatasSize(Context context) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not get size of user data because bugly is disable.");
            return -1;
        } else if (context != null) {
            return C2231aa.m457a(context).mo27987v();
        } else {
            Log.e(C2265al.f1567b, "getUserDatasSize args context should not be null");
            return -1;
        }
    }

    public static String getAppID() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not get App ID because bugly is disable.");
            return "unknown";
        } else if (CrashModule.getInstance().hasInitialized()) {
            return C2231aa.m457a(f1288a).mo27968e();
        } else {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return "unknown";
        }
    }

    public static void setUserId(String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set user ID because bugly is disable.");
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
        } else {
            setUserId(f1288a, str);
        }
    }

    public static void setUserId(Context context, String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set user ID because bugly is disable.");
        } else if (context == null) {
            Log.e(C2265al.f1567b, "Context should not be null when bugly has not been initialed!");
        } else if (TextUtils.isEmpty(str)) {
            C2265al.m610d("userId should not be null", new Object[0]);
        } else {
            if (str.length() > 100) {
                String substring = str.substring(0, 100);
                C2265al.m610d("userId %s length is over limit %d substring to %s", str, 100, substring);
                str = substring;
            }
            if (!str.equals(C2231aa.m457a(context).mo27970f())) {
                C2231aa a = C2231aa.m457a(context);
                synchronized (a.f1441V) {
                    a.f1480l = String.valueOf(str == null ? "10000" : str);
                }
                C2265al.m607b("[user] set userId : %s", str);
                NativeCrashHandler instance = NativeCrashHandler.getInstance();
                if (instance != null) {
                    instance.setNativeUserId(str);
                }
                if (CrashModule.getInstance().hasInitialized()) {
                    C2357s.m993a();
                }
            }
        }
    }

    public static String getUserId() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not get user ID because bugly is disable.");
            return "unknown";
        } else if (CrashModule.getInstance().hasInitialized()) {
            return C2231aa.m457a(f1288a).mo27970f();
        } else {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return "unknown";
        }
    }

    public static void setDeviceId(Context context, String str) {
        if (context != null && !TextUtils.isEmpty(str)) {
            C2231aa.m457a(context).mo27959a(str);
        }
    }

    public static void setDeviceModel(Context context, String str) {
        if (context != null && !TextUtils.isEmpty(str)) {
            C2231aa.m457a(context).mo27962b(str);
        }
    }

    public static String getDeviceID(Context context) {
        return C2231aa.m457a(context).mo27972g();
    }

    public static String getAppVer() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not get app version because bugly is disable.");
            return "unknown";
        } else if (CrashModule.getInstance().hasInitialized()) {
            return C2231aa.m457a(f1288a).f1483o;
        } else {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return "unknown";
        }
    }

    public static String getAppChannel() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not get App channel because bugly is disable.");
            return "unknown";
        } else if (CrashModule.getInstance().hasInitialized()) {
            return C2231aa.m457a(f1288a).f1487s;
        } else {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return "unknown";
        }
    }

    public static void setContext(Context context) {
        f1288a = context;
    }

    public static boolean isLastSessionCrash() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "The info 'isLastSessionCrash' is not accurate because bugly is disable.");
            return false;
        } else if (!CrashModule.getInstance().hasInitialized()) {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return false;
        } else {
            C2293at a = C2293at.m738a();
            Boolean bool = a.f1659z;
            if (bool != null) {
                return bool.booleanValue();
            }
            String str = C2231aa.m459b().f1472d;
            List<C2368y> a2 = C2365w.m1033a().mo28167a(1);
            ArrayList arrayList = new ArrayList();
            if (a2 == null || a2.size() <= 0) {
                a.f1659z = Boolean.FALSE;
                return false;
            }
            for (C2368y next : a2) {
                if (str.equals(next.f1990c)) {
                    a.f1659z = Boolean.TRUE;
                    arrayList.add(next);
                }
            }
            if (arrayList.size() > 0) {
                C2365w.m1033a().mo28169a((List<C2368y>) arrayList);
            }
            return true;
        }
    }

    public static void setSdkExtraData(Context context, String str, String str2) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not put SDK extra data because bugly is disable.");
        } else if (context != null && !C2273ap.m657a(str) && !C2273ap.m657a(str2)) {
            C2231aa a = C2231aa.m457a(context);
            if (str != null && str2 != null) {
                synchronized (a.f1439T) {
                    a.f1430K.put(str, str2);
                }
            }
        }
    }

    public static Map<String, String> getSdkExtraData() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not get SDK extra data because bugly is disable.");
            return new HashMap();
        } else if (CrashModule.getInstance().hasInitialized()) {
            return C2231aa.m457a(f1288a).f1430K;
        } else {
            Log.e(C2265al.f1567b, "CrashReport has not been initialed! pls to call method 'initCrashReport' first!");
            return null;
        }
    }

    public static Map<String, String> getSdkExtraData(Context context) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not get SDK extra data because bugly is disable.");
            return new HashMap();
        } else if (context != null) {
            return C2231aa.m457a(context).f1430K;
        } else {
            C2265al.m610d("Context should not be null.", new Object[0]);
            return null;
        }
    }

    private static void putSdkData(Context context, String str, String str2) {
        if (context != null && !C2273ap.m657a(str) && !C2273ap.m657a(str2)) {
            String replace = str.replace("[a-zA-Z[0-9]]+", "");
            if (replace.length() > 100) {
                Log.w(C2265al.f1567b, String.format("putSdkData key length over limit %d, will be cutted.", new Object[]{50}));
                replace = replace.substring(0, 50);
            }
            if (str2.length() > 500) {
                Log.w(C2265al.f1567b, String.format("putSdkData value length over limit %d, will be cutted!", new Object[]{200}));
                str2 = str2.substring(0, 200);
            }
            C2231aa.m457a(context).mo27963b(replace, str2);
            C2265al.m607b(String.format("[param] putSdkData data: %s - %s", new Object[]{replace, str2}), new Object[0]);
        }
    }

    @Deprecated
    public static void setIsAppForeground(Context context, boolean z) {
        C2265al.m604a("App fore and back status are no longer supported", new Object[0]);
    }

    public static void setIsDevelopmentDevice(Context context, boolean z) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set 'isDevelopmentDevice' because bugly is disable.");
        } else if (context == null) {
            C2265al.m610d("Context should not be null.", new Object[0]);
        } else {
            if (z) {
                C2265al.m609c("This is a development device.", new Object[0]);
            } else {
                C2265al.m609c("This is not a development device.", new Object[0]);
            }
            C2231aa.m457a(context).f1428I = z;
        }
    }

    public static void setSessionIntervalMills(long j) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set 'SessionIntervalMills' because bugly is disable.");
        } else {
            C2357s.m994a(j);
        }
    }

    public static void setDumpFilePath(Context context, String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set App version because bugly is disable.");
        } else if (context == null) {
            Log.w(C2265al.f1567b, "setTombPath args context should not be null");
        } else if (str == null) {
            Log.w(C2265al.f1567b, "tombstone path is null, will not set");
        } else {
            Log.i(C2265al.f1567b, "user set tombstone path: ".concat(String.valueOf(str)));
            NativeCrashHandler.setDumpFilePath(str);
        }
    }

    public static void setAppVersion(Context context, String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set App version because bugly is disable.");
        } else if (context == null) {
            Log.w(C2265al.f1567b, "setAppVersion args context should not be null");
        } else if (str == null) {
            Log.w(C2265al.f1567b, "App version is null, will not set");
        } else {
            C2231aa.m457a(context).f1483o = str;
            NativeCrashHandler instance = NativeCrashHandler.getInstance();
            if (instance != null) {
                instance.setNativeAppVersion(str);
            }
        }
    }

    public static void setAppChannel(Context context, String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set App channel because Bugly is disable.");
        } else if (context == null) {
            Log.w(C2265al.f1567b, "setAppChannel args context should not be null");
        } else if (str == null) {
            Log.w(C2265al.f1567b, "App channel is null, will not set");
        } else {
            C2231aa.m457a(context).f1487s = str;
            NativeCrashHandler instance = NativeCrashHandler.getInstance();
            if (instance != null) {
                instance.setNativeAppChannel(str);
            }
        }
    }

    public static void setAppPackage(Context context, String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set App package because bugly is disable.");
        } else if (context == null) {
            Log.w(C2265al.f1567b, "setAppPackage args context should not be null");
        } else if (str == null) {
            Log.w(C2265al.f1567b, "App package is null, will not set");
        } else {
            C2231aa.m457a(context).f1471c = str;
            NativeCrashHandler instance = NativeCrashHandler.getInstance();
            if (instance != null) {
                instance.setNativeAppPackage(str);
            }
        }
    }

    public static void setCrashFilter(String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set App package because bugly is disable.");
            return;
        }
        Log.i(C2265al.f1567b, "Set crash stack filter: ".concat(String.valueOf(str)));
        C2293at.f1646p = str;
    }

    public static void setCrashRegularFilter(String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set App package because bugly is disable.");
            return;
        }
        Log.i(C2265al.f1567b, "Set crash stack filter: ".concat(String.valueOf(str)));
        C2293at.f1647q = str;
    }

    public static void setHandleNativeCrashInJava(boolean z) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set App package because bugly is disable.");
            return;
        }
        Log.i(C2265al.f1567b, "Should handle native crash in Java profile after handled in native profile: ".concat(String.valueOf(z)));
        NativeCrashHandler.setShouldHandleInJava(z);
    }

    public static void setBuglyDbName(String str) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set DB name because bugly is disable.");
            return;
        }
        Log.i(C2265al.f1567b, "Set Bugly DB name: ".concat(String.valueOf(str)));
        C2367x.f1984a = str;
    }

    public static void enableObtainId(Context context, boolean z) {
        setCollectPrivacyInfo(context, z);
    }

    public static void setCollectPrivacyInfo(Context context, boolean z) {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not set collect privacy info enable because bugly is disable.");
        } else if (context == null) {
            Log.w(C2265al.f1567b, "setCollectPrivacyInfo args context should not be null");
        } else {
            Log.i(C2265al.f1567b, "setCollectPrivacyInfo: ".concat(String.valueOf(z)));
            C2231aa.m457a(context).f1482n = z;
        }
    }

    public static void setServerUrl(String str) {
        if (C2273ap.m657a(str) || !C2273ap.m674c(str)) {
            Log.i(C2265al.f1567b, "URL is invalid.");
            return;
        }
        C2248ac.m535a(str);
        StrategyBean.f1313a = str;
        StrategyBean.f1314b = str;
    }

    public static void uploadUserInfo() {
        if (!C2349p.f1910a) {
            Log.w(C2265al.f1567b, "Can not upload user info because bugly is disable.");
        } else if (C2357s.f1931b == null) {
            Log.w(C2265al.f1567b, "Can not upload user info because bugly is not init.");
        } else {
            C2357s.f1931b.mo28145b();
        }
    }

    public static boolean setJavascriptMonitor(WebView webView, boolean z) {
        return setJavascriptMonitor(webView, z, false);
    }

    public static boolean setJavascriptMonitor(final WebView webView, boolean z, boolean z2) {
        if (webView == null) {
            Log.w(C2265al.f1567b, "WebView is null.");
            return false;
        }
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setAllowFileAccess(false);
        return setJavascriptMonitor((C2220a) new C2220a() {
            /* renamed from: a */
            public final String mo27887a() {
                return webView.getUrl();
            }

            /* renamed from: b */
            public final void mo27890b() {
                WebSettings settings = webView.getSettings();
                if (!settings.getJavaScriptEnabled()) {
                    settings.setJavaScriptEnabled(true);
                }
            }

            /* renamed from: a */
            public final void mo27889a(String str) {
                webView.loadUrl(str);
            }

            /* renamed from: a */
            public final void mo27888a(H5JavaScriptInterface h5JavaScriptInterface, String str) {
                webView.addJavascriptInterface(h5JavaScriptInterface, str);
            }

            /* renamed from: c */
            public final CharSequence mo27891c() {
                return webView.getContentDescription();
            }
        }, z, z2);
    }

    public static boolean setJavascriptMonitor(C2220a aVar, boolean z) {
        return setJavascriptMonitor(aVar, z, false);
    }

    public static boolean setJavascriptMonitor(C2220a aVar, boolean z, boolean z2) {
        if (aVar == null) {
            Log.w(C2265al.f1567b, "WebViewInterface is null.");
            return false;
        } else if (!CrashModule.getInstance().hasInitialized()) {
            C2265al.m611e("CrashReport has not been initialed! please to call method 'initCrashReport' first!", new Object[0]);
            return false;
        } else {
            C2265al.m604a("Set Javascript exception monitor of webview.", new Object[0]);
            if (!C2349p.f1910a) {
                Log.w(C2265al.f1567b, "Can not set JavaScript monitor because bugly is disable.");
                return false;
            }
            C2265al.m609c("URL of webview is %s", aVar.mo27887a());
            if (z2 || Build.VERSION.SDK_INT >= 19) {
                C2265al.m604a("Enable the javascript needed by webview monitor.", new Object[0]);
                aVar.mo27890b();
                H5JavaScriptInterface instance = H5JavaScriptInterface.getInstance(aVar);
                if (instance != null) {
                    C2265al.m604a("Add a secure javascript interface to the webview.", new Object[0]);
                    aVar.mo27888a(instance, "exceptionUploader");
                }
                if (z) {
                    C2265al.m604a("Inject bugly.js(v%s) to the webview.", C2314bc.m797b());
                    String a = C2314bc.m796a();
                    if (a == null) {
                        C2265al.m611e("Failed to inject Bugly.js.", C2314bc.m797b());
                        return false;
                    }
                    aVar.mo27889a("javascript:".concat(String.valueOf(a)));
                }
                return true;
            }
            C2265al.m611e("This interface is only available for Android 4.4 or later.", new Object[0]);
            return false;
        }
    }

    /* compiled from: BUGLY */
    public static class UserStrategy extends BuglyStrategy {

        /* renamed from: c */
        CrashHandleCallback f1290c;

        public UserStrategy(Context context) {
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

        public synchronized CrashHandleCallback getCrashHandleCallback() {
            return this.f1290c;
        }

        public synchronized void setCrashHandleCallback(CrashHandleCallback crashHandleCallback) {
            this.f1290c = crashHandleCallback;
        }
    }

    public static void setHttpProxy(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            C2268an.f1571a = null;
        } else {
            C2268an.f1571a = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(str, i));
        }
    }

    public static void setHttpProxy(InetAddress inetAddress, int i) {
        if (inetAddress == null) {
            C2268an.f1571a = null;
        } else {
            C2268an.f1571a = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(inetAddress, i));
        }
    }

    public static Proxy getHttpProxy() {
        return C2268an.f1571a;
    }
}
