package com.tencent.bugly.proguard;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.bugly.BuglyStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.p */
/* compiled from: BUGLY */
public final class C2349p {

    /* renamed from: a */
    public static boolean f1910a = true;

    /* renamed from: b */
    public static List<C2348o> f1911b = new ArrayList();

    /* renamed from: c */
    public static boolean f1912c;

    /* renamed from: d */
    private static C2365w f1913d;

    /* renamed from: e */
    private static boolean f1914e;

    /* renamed from: a */
    private static boolean m970a(C2231aa aaVar) {
        List<String> list = aaVar.f1490v;
        aaVar.getClass();
        return list != null && list.contains("bugly");
    }

    /* renamed from: a */
    public static synchronized void m966a(Context context) {
        synchronized (C2349p.class) {
            m967a(context, (BuglyStrategy) null);
        }
    }

    /* renamed from: a */
    public static synchronized void m967a(Context context, BuglyStrategy buglyStrategy) {
        synchronized (C2349p.class) {
            if (f1914e) {
                C2265al.m610d("[init] initial Multi-times, ignore this.", new Object[0]);
            } else if (context == null) {
                Log.w(C2265al.f1567b, "[init] context of init() is null, check it.");
            } else {
                C2231aa a = C2231aa.m457a(context);
                if (m970a(a)) {
                    f1910a = false;
                    return;
                }
                String e = a.mo27968e();
                if (e == null) {
                    Log.e(C2265al.f1567b, "[init] meta data of BUGLY_APPID in AndroidManifest.xml should be set.");
                } else {
                    m968a(context, e, a.f1423D, buglyStrategy);
                }
            }
        }
    }

    /* renamed from: a */
    public static synchronized void m968a(Context context, String str, boolean z, BuglyStrategy buglyStrategy) {
        byte[] bArr;
        synchronized (C2349p.class) {
            if (f1914e) {
                C2265al.m610d("[init] initial Multi-times, ignore this.", new Object[0]);
            } else if (context == null) {
                Log.w(C2265al.f1567b, "[init] context is null, check it.");
            } else if (str == null) {
                Log.e(C2265al.f1567b, "init arg 'crashReportAppID' should not be null!");
            } else {
                f1914e = true;
                if (z) {
                    f1912c = true;
                    C2265al.f1568c = true;
                    C2265al.m610d("Bugly debug模式开启，请在发布时把isDebug关闭。 -- Running in debug model for 'isDebug' is enabled. Please disable it when you release.", new Object[0]);
                    C2265al.m611e("--------------------------------------------------------------------------------------------", new Object[0]);
                    C2265al.m610d("Bugly debug模式将有以下行为特性 -- The following list shows the behaviour of debug model: ", new Object[0]);
                    C2265al.m610d("[1] 输出详细的Bugly SDK的Log -- More detailed log of Bugly SDK will be output to logcat;", new Object[0]);
                    C2265al.m610d("[2] 每一条Crash都会被立即上报 -- Every crash caught by Bugly will be uploaded immediately.", new Object[0]);
                    C2265al.m610d("[3] 自定义日志将会在Logcat中输出 -- Custom log will be output to logcat.", new Object[0]);
                    C2265al.m611e("--------------------------------------------------------------------------------------------", new Object[0]);
                    C2265al.m607b("[init] Open debug mode of Bugly.", new Object[0]);
                }
                C2265al.m604a(" crash report start initializing...", new Object[0]);
                C2265al.m607b("[init] Bugly start initializing...", new Object[0]);
                C2265al.m604a("[init] Bugly complete version: v%s", "4.1.9");
                Context a = C2273ap.m635a(context);
                C2231aa a2 = C2231aa.m457a(a);
                a2.mo27980o();
                C2269ao.m621a(a);
                f1913d = C2365w.m1034a(a, f1911b);
                C2259ai.m570a(a);
                C2248ac.m534a(a, f1911b);
                C2361u a3 = C2361u.m1014a(a);
                if (m970a(a2)) {
                    f1910a = false;
                    return;
                }
                a2.f1486r = str;
                a2.mo27963b("APP_ID", str);
                C2265al.m604a("[param] Set APP ID:%s", str);
                if (buglyStrategy != null) {
                    String appVersion = buglyStrategy.getAppVersion();
                    if (!TextUtils.isEmpty(appVersion)) {
                        if (appVersion.length() > 100) {
                            String substring = appVersion.substring(0, 100);
                            C2265al.m610d("appVersion %s length is over limit %d substring to %s", appVersion, 100, substring);
                            appVersion = substring;
                        }
                        a2.f1483o = appVersion;
                        C2265al.m604a("[param] Set App version: %s", buglyStrategy.getAppVersion());
                    }
                    try {
                        if (buglyStrategy.isReplaceOldChannel()) {
                            String appChannel = buglyStrategy.getAppChannel();
                            if (!TextUtils.isEmpty(appChannel)) {
                                if (appChannel.length() > 100) {
                                    String substring2 = appChannel.substring(0, 100);
                                    C2265al.m610d("appChannel %s length is over limit %d substring to %s", appChannel, 100, substring2);
                                    appChannel = substring2;
                                }
                                f1913d.mo28170a(556, "app_channel", appChannel.getBytes(), false);
                                a2.f1487s = appChannel;
                            }
                        } else {
                            Map<String, byte[]> a4 = f1913d.mo28168a(556, (C2364v) null);
                            if (!(a4 == null || (bArr = a4.get("app_channel")) == null)) {
                                a2.f1487s = new String(bArr);
                            }
                        }
                        C2265al.m604a("[param] Set App channel: %s", a2.f1487s);
                    } catch (Exception e) {
                        if (f1912c) {
                            e.printStackTrace();
                        }
                    }
                    String appPackageName = buglyStrategy.getAppPackageName();
                    if (!TextUtils.isEmpty(appPackageName)) {
                        if (appPackageName.length() > 100) {
                            String substring3 = appPackageName.substring(0, 100);
                            C2265al.m610d("appPackageName %s length is over limit %d substring to %s", appPackageName, 100, substring3);
                            appPackageName = substring3;
                        }
                        a2.f1471c = appPackageName;
                        C2265al.m604a("[param] Set App package: %s", buglyStrategy.getAppPackageName());
                    }
                    String deviceID = buglyStrategy.getDeviceID();
                    if (deviceID != null) {
                        if (deviceID.length() > 100) {
                            String substring4 = deviceID.substring(0, 100);
                            C2265al.m610d("deviceId %s length is over limit %d substring to %s", deviceID, 100, substring4);
                            deviceID = substring4;
                        }
                        a2.mo27959a(deviceID);
                        C2265al.m604a("[param] Set device ID: %s", deviceID);
                    }
                    String deviceModel = buglyStrategy.getDeviceModel();
                    if (deviceModel != null) {
                        a2.mo27962b(deviceModel);
                        C2265al.m604a("[param] Set device model: %s", deviceModel);
                    }
                    a2.f1474f = buglyStrategy.isUploadProcess();
                    C2269ao.f1573b = buglyStrategy.isBuglyLogUpload();
                }
                for (int i = 0; i < f1911b.size(); i++) {
                    try {
                        if (a3.mo28160b(f1911b.get(i).f1909id)) {
                            f1911b.get(i).init(a, z, buglyStrategy);
                        }
                    } catch (Throwable th) {
                        if (!C2265al.m605a(th)) {
                            th.printStackTrace();
                        }
                    }
                }
                C2357s.m996a(a, buglyStrategy);
                long appReportDelay = buglyStrategy != null ? buglyStrategy.getAppReportDelay() : 0;
                C2248ac a5 = C2248ac.m533a();
                a5.f1502c.mo28018a(new Thread() {
                    public final void run(
/*
Method generation error in method: com.tencent.bugly.proguard.ac.1.run():void, dex: classes2.dex
                    jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.tencent.bugly.proguard.ac.1.run():void, class status: UNLOADED
                    	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                    	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                    	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                    	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                    	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:156)
                    	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:175)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:152)
                    	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:175)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:152)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                    	at jadx.core.codegen.RegionGen.makeSynchronizedRegion(RegionGen.java:260)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:70)
                    	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                    	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                    	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                    	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                    	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                    	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                    	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                    	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                    	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                    	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                    	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                    
*/
                }, appReportDelay);
                C2265al.m607b("[init] Bugly initialization finished.", new Object[0]);
            }
        }
    }

    /* renamed from: a */
    public static synchronized void m969a(C2348o oVar) {
        synchronized (C2349p.class) {
            if (!f1911b.contains(oVar)) {
                f1911b.add(oVar);
            }
        }
    }
}
