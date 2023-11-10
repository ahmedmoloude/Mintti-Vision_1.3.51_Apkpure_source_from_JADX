package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.os.Process;
import android.text.TextUtils;
import java.util.List;

/* renamed from: com.tencent.bugly.proguard.az */
/* compiled from: BUGLY */
public final class C2310az {
    /* renamed from: a */
    public static ActivityManager.ProcessErrorStateInfo m793a(ActivityManager activityManager, long j) {
        if (activityManager == null) {
            C2265al.m609c("get anr state, ActivityManager is null", new Object[0]);
            return null;
        }
        C2265al.m609c("get anr state, timeout:%d", Long.valueOf(j));
        long j2 = j / 500;
        int i = 0;
        while (true) {
            ActivityManager.ProcessErrorStateInfo a = m795a(activityManager.getProcessesInErrorState());
            if (a == null) {
                C2265al.m609c("found proc state is null", new Object[0]);
            } else if (a.condition == 2) {
                C2265al.m609c("found proc state is anr! proc:%s", a.processName);
                return a;
            } else if (a.condition == 1) {
                C2265al.m609c("found proc state is crashed!", new Object[0]);
                return null;
            }
            int i2 = i + 1;
            if (((long) i) >= j2) {
                return m794a("Find process anr, but unable to get anr message.");
            }
            C2265al.m609c("try the %s times:", Integer.valueOf(i2));
            C2273ap.m665b(500);
            i = i2;
        }
    }

    /* renamed from: a */
    private static ActivityManager.ProcessErrorStateInfo m795a(List<ActivityManager.ProcessErrorStateInfo> list) {
        if (list == null || list.isEmpty()) {
            C2265al.m609c("error state info list is null", new Object[0]);
            return null;
        }
        int myPid = Process.myPid();
        for (ActivityManager.ProcessErrorStateInfo next : list) {
            if (next.pid == myPid) {
                if (TextUtils.isEmpty(next.longMsg)) {
                    return null;
                }
                C2265al.m609c("found current proc in the error state", new Object[0]);
                return next;
            }
        }
        C2265al.m609c("current proc not in the error state", new Object[0]);
        return null;
    }

    /* renamed from: a */
    private static ActivityManager.ProcessErrorStateInfo m794a(String str) {
        ActivityManager.ProcessErrorStateInfo processErrorStateInfo = new ActivityManager.ProcessErrorStateInfo();
        processErrorStateInfo.pid = Process.myPid();
        processErrorStateInfo.processName = C2369z.m1058a(Process.myPid());
        processErrorStateInfo.shortMsg = str;
        return processErrorStateInfo;
    }
}
