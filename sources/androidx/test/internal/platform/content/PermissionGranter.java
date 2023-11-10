package androidx.test.internal.platform.content;

public interface PermissionGranter {
    void addPermissions(String... strArr);

    void requestPermissions();
}
