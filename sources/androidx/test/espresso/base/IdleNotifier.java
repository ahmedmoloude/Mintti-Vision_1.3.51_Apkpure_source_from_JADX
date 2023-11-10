package androidx.test.espresso.base;

interface IdleNotifier<CB> {
    void cancelCallback();

    boolean isIdleNow();

    void registerNotificationCallback(CB cb);
}
