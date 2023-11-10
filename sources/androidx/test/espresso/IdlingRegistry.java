package androidx.test.espresso;

import android.os.Looper;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class IdlingRegistry {
    private static final IdlingRegistry instance = new IdlingRegistry();
    private final Set<Looper> loopers = Collections.synchronizedSet(new HashSet());
    private final Set<IdlingResource> resources = Collections.synchronizedSet(new HashSet());

    IdlingRegistry() {
    }

    public static IdlingRegistry getInstance() {
        return instance;
    }

    public boolean register(IdlingResource... idlingResourceArr) {
        Objects.requireNonNull(idlingResourceArr, "idlingResources cannot be null!");
        return this.resources.addAll(Arrays.asList(idlingResourceArr));
    }

    public boolean unregister(IdlingResource... idlingResourceArr) {
        Objects.requireNonNull(idlingResourceArr, "idlingResources cannot be null!");
        return this.resources.removeAll(Arrays.asList(idlingResourceArr));
    }

    public void registerLooperAsIdlingResource(Looper looper) {
        Objects.requireNonNull(looper, "looper cannot be null!");
        if (Looper.getMainLooper() != looper) {
            this.loopers.add(looper);
            return;
        }
        throw new IllegalArgumentException("Not intended for use with main looper!");
    }

    public boolean unregisterLooperAsIdlingResource(Looper looper) {
        Objects.requireNonNull(looper, "looper cannot be null!");
        return this.loopers.remove(looper);
    }

    public Collection<IdlingResource> getResources() {
        return new HashSet(this.resources);
    }

    public Collection<Looper> getLoopers() {
        return new HashSet(this.loopers);
    }
}
