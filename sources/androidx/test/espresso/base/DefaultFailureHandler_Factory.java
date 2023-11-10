package androidx.test.espresso.base;

import android.content.Context;
import javax.inject.Provider;

public final class DefaultFailureHandler_Factory implements Provider<DefaultFailureHandler> {
    private final Provider<Context> appContextProvider;

    public DefaultFailureHandler_Factory(Provider<Context> provider) {
        this.appContextProvider = provider;
    }

    public static DefaultFailureHandler_Factory create(Provider<Context> provider) {
        return new DefaultFailureHandler_Factory(provider);
    }

    public static DefaultFailureHandler newInstance(Context context) {
        return new DefaultFailureHandler(context);
    }

    public DefaultFailureHandler get() {
        return newInstance(this.appContextProvider.get());
    }
}
