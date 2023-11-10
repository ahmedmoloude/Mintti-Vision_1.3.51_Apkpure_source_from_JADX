package androidx.test.espresso.core.internal.deps.dagger.internal;

import javax.inject.Provider;

public final class DoubleCheck<T> implements Provider<T> {
    static final /* synthetic */ boolean $assertionsDisabled = true;
    private static final Object UNINITIALIZED = new Object();
    private volatile Object instance = UNINITIALIZED;
    private volatile Provider<T> provider;

    private DoubleCheck(Provider<T> provider2) {
        if ($assertionsDisabled || provider2 != null) {
            this.provider = provider2;
            return;
        }
        throw new AssertionError();
    }

    public static <P extends Provider<T>, T> Provider<T> provider(P p) {
        Preconditions.checkNotNull(p);
        if (p instanceof DoubleCheck) {
            return p;
        }
        return new DoubleCheck(p);
    }

    public static Object reentrantCheck(Object obj, Object obj2) {
        if (!(obj != UNINITIALIZED && !(obj instanceof MemoizedSentinel)) || obj == obj2) {
            return obj2;
        }
        String valueOf = String.valueOf(obj);
        String valueOf2 = String.valueOf(obj2);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 118 + String.valueOf(valueOf2).length());
        sb.append("Scoped provider was invoked recursively returning different results: ");
        sb.append(valueOf);
        sb.append(" & ");
        sb.append(valueOf2);
        sb.append(". This is likely due to a circular dependency.");
        throw new IllegalStateException(sb.toString());
    }

    public T get() {
        T t = this.instance;
        T t2 = UNINITIALIZED;
        if (t == t2) {
            synchronized (this) {
                t = this.instance;
                if (t == t2) {
                    t = this.provider.get();
                    this.instance = reentrantCheck(this.instance, t);
                    this.provider = null;
                }
            }
        }
        return t;
    }
}
