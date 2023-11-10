package androidx.test.espresso.core.internal.deps.guava.collect;

import java.util.NoSuchElementException;

public abstract class AbstractSequentialIterator<T> extends UnmodifiableIterator<T> {
    private T nextOrNull;

    protected AbstractSequentialIterator(T t) {
        this.nextOrNull = t;
    }

    /* access modifiers changed from: protected */
    public abstract T computeNext(T t);

    public final boolean hasNext() {
        return this.nextOrNull != null;
    }

    public final T next() {
        if (hasNext()) {
            try {
                T t = this.nextOrNull;
                this.nextOrNull = computeNext(t);
                return t;
            } catch (Throwable th) {
                this.nextOrNull = computeNext(this.nextOrNull);
                throw th;
            }
        } else {
            throw new NoSuchElementException();
        }
    }
}
