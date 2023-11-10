package androidx.test.services.storage;

public class TestStorageException extends RuntimeException {
    public TestStorageException(String str) {
        super(str);
    }

    public TestStorageException(String str, Throwable th) {
        super(str, th);
    }
}
