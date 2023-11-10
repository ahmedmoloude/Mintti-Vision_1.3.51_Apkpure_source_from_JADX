package p028io.reactivex.internal.fuseable;

import p028io.reactivex.MaybeSource;

/* renamed from: io.reactivex.internal.fuseable.HasUpstreamMaybeSource */
public interface HasUpstreamMaybeSource<T> {
    MaybeSource<T> source();
}
