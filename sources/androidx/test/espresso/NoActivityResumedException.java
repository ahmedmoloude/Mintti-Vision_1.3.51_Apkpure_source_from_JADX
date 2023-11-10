package androidx.test.espresso;

public final class NoActivityResumedException extends RuntimeException implements EspressoException {
    public NoActivityResumedException(String str) {
        super(str);
    }

    public NoActivityResumedException(String str, Throwable th) {
        super(str, th);
    }
}
