package androidx.test.platform.p013ui;

import androidx.test.platform.TestFrameworkException;

/* renamed from: androidx.test.platform.ui.InjectEventSecurityException */
public class InjectEventSecurityException extends Exception implements TestFrameworkException {
    public InjectEventSecurityException(String str) {
        super(str);
    }

    public InjectEventSecurityException(Throwable th) {
        super(th);
    }

    public InjectEventSecurityException(String str, Throwable th) {
        super(str, th);
    }
}
