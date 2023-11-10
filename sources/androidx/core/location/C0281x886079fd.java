package androidx.core.location;

import androidx.core.location.LocationManagerCompat;
import java.lang.ref.WeakReference;
import java.util.function.Predicate;

/* renamed from: androidx.core.location.-$$Lambda$LocationManagerCompat$LocationListenerTransport$QJAWzn5huAZOFDXXrtzdeajRRuc */
/* compiled from: lambda */
public final /* synthetic */ class C0281x886079fd implements Predicate {
    public static final /* synthetic */ C0281x886079fd INSTANCE = new C0281x886079fd();

    private /* synthetic */ C0281x886079fd() {
    }

    public final boolean test(Object obj) {
        return LocationManagerCompat.LocationListenerTransport.lambda$unregister$1((WeakReference) obj);
    }
}
