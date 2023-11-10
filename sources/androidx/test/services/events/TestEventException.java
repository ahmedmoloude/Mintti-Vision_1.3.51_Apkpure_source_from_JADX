package androidx.test.services.events;

public class TestEventException extends Exception {
    public TestEventException(String str) {
        super(str);
    }

    public TestEventException(String str, Throwable th) {
        super(str, th);
    }
}
