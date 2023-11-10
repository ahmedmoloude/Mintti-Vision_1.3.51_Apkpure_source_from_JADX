package androidx.test.espresso.core.internal.deps.guava.cache;

public interface Weigher<K, V> {
    int weigh(K k, V v);
}
