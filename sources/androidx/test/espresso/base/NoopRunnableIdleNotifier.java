package androidx.test.espresso.base;

class NoopRunnableIdleNotifier implements IdleNotifier<Runnable> {
    NoopRunnableIdleNotifier() {
    }

    public void cancelCallback() {
    }

    public boolean isIdleNow() {
        return true;
    }

    public void registerNotificationCallback(Runnable runnable) {
        runnable.run();
    }
}
