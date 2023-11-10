package androidx.test.internal.util;

import androidx.test.internal.util.LogUtil;

/* compiled from: LogUtil */
final /* synthetic */ class LogUtil$$Lambda$1 implements LogUtil.Supplier {
    private final String arg$1;

    LogUtil$$Lambda$1(String str) {
        this.arg$1 = str;
    }

    public Object get() {
        return LogUtil.lambda$logDebugWithProcess$1$LogUtil(this.arg$1);
    }
}
