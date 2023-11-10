package androidx.test.espresso.core.internal.deps.guava.cache;

public interface RemovalListener<K, V> {
    void onRemoval(RemovalNotification<K, V> removalNotification);
}
