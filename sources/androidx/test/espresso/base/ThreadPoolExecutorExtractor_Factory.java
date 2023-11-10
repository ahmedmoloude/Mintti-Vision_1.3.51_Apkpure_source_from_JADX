package androidx.test.espresso.base;

import android.os.Looper;
import javax.inject.Provider;

public final class ThreadPoolExecutorExtractor_Factory implements Provider<ThreadPoolExecutorExtractor> {
    private final Provider<Looper> looperProvider;

    public ThreadPoolExecutorExtractor_Factory(Provider<Looper> provider) {
        this.looperProvider = provider;
    }

    public static ThreadPoolExecutorExtractor_Factory create(Provider<Looper> provider) {
        return new ThreadPoolExecutorExtractor_Factory(provider);
    }

    public static ThreadPoolExecutorExtractor newInstance(Looper looper) {
        return new ThreadPoolExecutorExtractor(looper);
    }

    public ThreadPoolExecutorExtractor get() {
        return newInstance(this.looperProvider.get());
    }
}
