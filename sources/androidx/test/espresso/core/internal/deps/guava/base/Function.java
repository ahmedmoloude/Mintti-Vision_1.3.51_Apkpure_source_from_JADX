package androidx.test.espresso.core.internal.deps.guava.base;

public interface Function<F, T> {
    T apply(F f);

    boolean equals(Object obj);
}
