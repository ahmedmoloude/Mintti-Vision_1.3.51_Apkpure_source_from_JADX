package p028io.reactivex.internal.util;

import java.util.concurrent.atomic.AtomicReference;

/* renamed from: io.reactivex.internal.util.AtomicThrowable */
public final class AtomicThrowable extends AtomicReference<Throwable> {
    private static final long serialVersionUID = 3949248817947090603L;

    public boolean addThrowable(Throwable th) {
        return ExceptionHelper.addThrowable(this, th);
    }

    public Throwable terminate() {
        return ExceptionHelper.terminate(this);
    }

    public boolean isTerminated() {
        return get() == ExceptionHelper.TERMINATED;
    }
}
