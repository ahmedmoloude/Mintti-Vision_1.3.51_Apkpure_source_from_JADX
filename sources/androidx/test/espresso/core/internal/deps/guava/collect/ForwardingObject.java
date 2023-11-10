package androidx.test.espresso.core.internal.deps.guava.collect;

public abstract class ForwardingObject {
    protected ForwardingObject() {
    }

    /* access modifiers changed from: protected */
    public abstract Object delegate();

    public String toString() {
        return delegate().toString();
    }
}
