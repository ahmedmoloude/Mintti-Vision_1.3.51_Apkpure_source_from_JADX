package p035me.yokeyword.fragmentation.exception;

import android.util.Log;

/* renamed from: me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning */
public class AfterSaveStateTransactionWarning extends RuntimeException {
    public AfterSaveStateTransactionWarning(String str) {
        super("Warning: Perform this " + str + " action after onSaveInstanceState!");
        Log.w("Fragmentation", getMessage());
    }
}
