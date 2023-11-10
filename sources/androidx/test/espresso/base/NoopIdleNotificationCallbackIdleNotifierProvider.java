package androidx.test.espresso.base;

import androidx.test.espresso.base.IdlingResourceRegistry;
import javax.inject.Provider;

final class NoopIdleNotificationCallbackIdleNotifierProvider implements Provider<IdleNotifier<IdlingResourceRegistry.IdleNotificationCallback>> {

    private static class NoopIdleNotificationCallbackIdleNotifier implements IdleNotifier<IdlingResourceRegistry.IdleNotificationCallback> {
        private NoopIdleNotificationCallbackIdleNotifier() {
        }

        public void cancelCallback() {
        }

        public boolean isIdleNow() {
            return true;
        }

        public void registerNotificationCallback(IdlingResourceRegistry.IdleNotificationCallback idleNotificationCallback) {
            idleNotificationCallback.allResourcesIdle();
        }
    }

    NoopIdleNotificationCallbackIdleNotifierProvider() {
    }

    public NoopIdleNotificationCallbackIdleNotifier get() {
        return new NoopIdleNotificationCallbackIdleNotifier();
    }
}
