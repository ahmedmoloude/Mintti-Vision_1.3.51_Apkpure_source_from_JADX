package androidx.test.espresso.core.internal.deps.dagger.internal;

import java.util.Objects;

public final class Preconditions {
    public static <T> T checkNotNull(T t) {
        Objects.requireNonNull(t);
        return t;
    }

    public static <T> T checkNotNullFromProvides(T t) {
        Objects.requireNonNull(t, "Cannot return null from a non-@Nullable @Provides method");
        return t;
    }
}
