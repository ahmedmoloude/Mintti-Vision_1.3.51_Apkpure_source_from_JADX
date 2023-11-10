package androidx.test.internal.util;

import android.util.Log;
import androidx.test.internal.util.ProcSummary;

public final class LogUtil {
    private static volatile String myProcName;

    interface Supplier<T> {
        T get();
    }

    static final /* synthetic */ String lambda$logDebug$0$LogUtil(String str) {
        return str;
    }

    public static void logDebug(String str, String str2, Object... objArr) {
        logDebug(str, (Supplier<String>) new LogUtil$$Lambda$0(str2), objArr);
    }

    private static void logDebug(String str, Supplier<String> supplier, Object... objArr) {
        if (isLoggable(str, 3)) {
            Log.d(str, String.format(supplier.get(), objArr));
        }
    }

    static final /* synthetic */ String lambda$logDebugWithProcess$1$LogUtil(String str) {
        String procName = procName();
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 4 + String.valueOf(procName).length());
        sb.append(str);
        sb.append(" in ");
        sb.append(procName);
        return sb.toString();
    }

    public static void logDebugWithProcess(String str, String str2, Object... objArr) {
        logDebug(str, (Supplier<String>) new LogUtil$$Lambda$1(str2), objArr);
    }

    private static final String procName() {
        String str;
        String str2 = myProcName;
        if (str2 != null) {
            return str2;
        }
        try {
            str = ProcSummary.summarize("self").cmdline;
        } catch (ProcSummary.SummaryException unused) {
            str = "unknown";
        }
        return (str.length() <= 64 || !str.contains("-classpath")) ? str : "robolectric";
    }

    private static boolean isLoggable(String str, int i) {
        if (str.length() > 23) {
            str = str.substring(0, 22);
        }
        return Log.isLoggable(str, i);
    }
}
