package androidx.test.espresso.core.internal.deps.guava.collect;

import androidx.test.espresso.core.internal.deps.guava.base.Optional;

public abstract class FluentIterable<E> implements Iterable<E> {
    private final Optional<Iterable<E>> iterableDelegate = Optional.absent();

    protected FluentIterable() {
    }

    private Iterable<E> getDelegate() {
        return this.iterableDelegate.mo11035or(this);
    }

    public String toString() {
        return Iterables.toString(getDelegate());
    }
}
