package androidx.test.espresso.base;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingPolicy;
import androidx.test.espresso.InjectEventSecurityException;
import androidx.test.espresso.base.IdlingResourceRegistry;
import androidx.test.espresso.base.Interrogator;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.base.Throwables;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.inject.Provider;

final class UiControllerImpl implements InterruptableUiController, Handler.Callback, IdlingUiController {
    private static final Callable<Void> NO_OP = new Callable<Void>() {
        public Void call() {
            return null;
        }
    };
    /* access modifiers changed from: private */
    public static final String TAG = "UiControllerImpl";
    private IdleNotifier<Runnable> asyncIdle;
    private IdleNotifier<Runnable> compatIdle;
    private final BitSet conditionSet;
    /* access modifiers changed from: private */
    public Handler controllerHandler;
    private Provider<IdleNotifier<IdlingResourceRegistry.IdleNotificationCallback>> dynamicIdleProvider;
    /* access modifiers changed from: private */
    public final EventInjector eventInjector;
    /* access modifiers changed from: private */
    public int generation = 0;
    private final IdlingResourceRegistry idlingResourceRegistry;
    /* access modifiers changed from: private */
    public MainThreadInterrogation interrogation;
    private final ExecutorService keyEventExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("Espresso Key Event #%d").build());
    private final Looper mainLooper;

    /* renamed from: androidx.test.espresso.base.UiControllerImpl$7 */
    static /* synthetic */ class C07067 {

        /* renamed from: $SwitchMap$androidx$test$espresso$base$UiControllerImpl$IdleCondition */
        static final /* synthetic */ int[] f198x22726293;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                androidx.test.espresso.base.UiControllerImpl$IdleCondition[] r0 = androidx.test.espresso.base.UiControllerImpl.IdleCondition.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f198x22726293 = r0
                androidx.test.espresso.base.UiControllerImpl$IdleCondition r1 = androidx.test.espresso.base.UiControllerImpl.IdleCondition.ASYNC_TASKS_HAVE_IDLED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f198x22726293     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.test.espresso.base.UiControllerImpl$IdleCondition r1 = androidx.test.espresso.base.UiControllerImpl.IdleCondition.COMPAT_TASKS_HAVE_IDLED     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f198x22726293     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.test.espresso.base.UiControllerImpl$IdleCondition r1 = androidx.test.espresso.base.UiControllerImpl.IdleCondition.DYNAMIC_TASKS_HAVE_IDLED     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.base.UiControllerImpl.C07067.<clinit>():void");
        }
    }

    enum IdleCondition {
        DELAY_HAS_PAST,
        ASYNC_TASKS_HAVE_IDLED,
        COMPAT_TASKS_HAVE_IDLED,
        KEY_INJECT_HAS_COMPLETED,
        MOTION_INJECTION_HAS_COMPLETED,
        DYNAMIC_TASKS_HAVE_IDLED;

        public static BitSet createConditionSet() {
            return new BitSet(values().length);
        }

        public static boolean handleMessage(Message message, BitSet bitSet, int i) {
            IdleCondition[] values = values();
            if (message.what < 0 || message.what >= values.length) {
                return false;
            }
            IdleCondition idleCondition = values[message.what];
            if (message.arg1 == i) {
                idleCondition.signal(bitSet);
                return true;
            }
            String access$000 = UiControllerImpl.TAG;
            String valueOf = String.valueOf(idleCondition);
            int i2 = message.arg1;
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 90);
            sb.append("ignoring signal of: ");
            sb.append(valueOf);
            sb.append(" from previous generation: ");
            sb.append(i2);
            sb.append(" current generation: ");
            sb.append(i);
            Log.w(access$000, sb.toString());
            return true;
        }

        public Message createSignal(Handler handler, int i) {
            return Message.obtain(handler, ordinal(), i, 0, (Object) null);
        }

        public boolean isSignaled(BitSet bitSet) {
            return bitSet.get(ordinal());
        }

        public void reset(BitSet bitSet) {
            bitSet.set(ordinal(), false);
        }

        /* access modifiers changed from: protected */
        public void signal(BitSet bitSet) {
            bitSet.set(ordinal());
        }
    }

    private enum InterrogationStatus {
        TIMED_OUT,
        COMPLETED,
        INTERRUPTED
    }

    private static final class MainThreadInterrogation implements Interrogator.InterrogationHandler<InterrogationStatus> {
        private final BitSet conditionSet;
        private final EnumSet<IdleCondition> conditions;
        /* access modifiers changed from: private */
        public int execCount = 0;
        private final long giveUpAtMs;
        private String lastMessage;
        private InterrogationStatus status = InterrogationStatus.COMPLETED;

        MainThreadInterrogation(EnumSet<IdleCondition> enumSet, BitSet bitSet, long j) {
            this.conditions = enumSet;
            this.conditionSet = bitSet;
            this.giveUpAtMs = j;
        }

        private boolean conditionsMet() {
            boolean z = true;
            if (InterrogationStatus.INTERRUPTED == this.status) {
                return true;
            }
            int i = this.execCount;
            boolean z2 = i > 0 && i % 100 == 0;
            Iterator it = this.conditions.iterator();
            while (it.hasNext()) {
                IdleCondition idleCondition = (IdleCondition) it.next();
                if (!idleCondition.isSignaled(this.conditionSet)) {
                    if (!z2) {
                        return false;
                    }
                    String access$000 = UiControllerImpl.TAG;
                    String name = idleCondition.name();
                    int i2 = this.execCount;
                    StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 41);
                    sb.append("Waiting for: ");
                    sb.append(name);
                    sb.append(" for ");
                    sb.append(i2);
                    sb.append(" iterations.");
                    Log.w(access$000, sb.toString());
                    z = false;
                }
            }
            return z;
        }

        private boolean continueOrTimeout() {
            if (InterrogationStatus.INTERRUPTED == this.status) {
                return false;
            }
            if (SystemClock.uptimeMillis() < this.giveUpAtMs) {
                return true;
            }
            this.status = InterrogationStatus.TIMED_OUT;
            return false;
        }

        public boolean barrierUp() {
            return continueOrTimeout();
        }

        public boolean beforeTaskDispatch() {
            this.execCount++;
            return continueOrTimeout();
        }

        public InterrogationStatus get() {
            return this.status;
        }

        public String getMessage() {
            return this.lastMessage;
        }

        /* access modifiers changed from: package-private */
        public void interruptInterrogation() {
            this.status = InterrogationStatus.INTERRUPTED;
        }

        public boolean queueEmpty() {
            return !conditionsMet();
        }

        public void quitting() {
        }

        public void setMessage(Message message) {
            try {
                this.lastMessage = message.toString();
            } catch (NullPointerException e) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32);
                sb.append("NPE calling message toString(): ");
                sb.append(valueOf);
                this.lastMessage = sb.toString();
            }
        }

        public boolean taskDueLong() {
            return !conditionsMet();
        }

        public boolean taskDueSoon() {
            return continueOrTimeout();
        }
    }

    private class SignalingTask<T> extends FutureTask<T> {
        private final IdleCondition condition;
        private final int myGeneration;

        public SignalingTask(Callable<T> callable, IdleCondition idleCondition, int i) {
            super(callable);
            this.condition = (IdleCondition) Preconditions.checkNotNull(idleCondition);
            this.myGeneration = i;
        }

        /* access modifiers changed from: protected */
        public void done() {
            UiControllerImpl.this.controllerHandler.sendMessage(this.condition.createSignal(UiControllerImpl.this.controllerHandler, this.myGeneration));
        }
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [javax.inject.Provider<androidx.test.espresso.base.IdleNotifier<androidx.test.espresso.base.IdlingResourceRegistry$IdleNotificationCallback>>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    UiControllerImpl(androidx.test.espresso.base.EventInjector r3, @androidx.test.espresso.base.SdkAsyncTask androidx.test.espresso.base.IdleNotifier<java.lang.Runnable> r4, @androidx.test.espresso.base.CompatAsyncTask androidx.test.espresso.base.IdleNotifier<java.lang.Runnable> r5, javax.inject.Provider<androidx.test.espresso.base.IdleNotifier<androidx.test.espresso.base.IdlingResourceRegistry.IdleNotificationCallback>> r6, android.os.Looper r7, androidx.test.espresso.base.IdlingResourceRegistry r8) {
        /*
            r2 = this;
            r2.<init>()
            androidx.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder r0 = new androidx.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder
            r0.<init>()
            java.lang.String r1 = "Espresso Key Event #%d"
            androidx.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder r0 = r0.setNameFormat(r1)
            java.util.concurrent.ThreadFactory r0 = r0.build()
            java.util.concurrent.ExecutorService r0 = java.util.concurrent.Executors.newSingleThreadExecutor(r0)
            r2.keyEventExecutor = r0
            r0 = 0
            r2.generation = r0
            java.lang.Object r3 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r3)
            androidx.test.espresso.base.EventInjector r3 = (androidx.test.espresso.base.EventInjector) r3
            r2.eventInjector = r3
            java.lang.Object r3 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r4)
            androidx.test.espresso.base.IdleNotifier r3 = (androidx.test.espresso.base.IdleNotifier) r3
            r2.asyncIdle = r3
            java.lang.Object r3 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r5)
            androidx.test.espresso.base.IdleNotifier r3 = (androidx.test.espresso.base.IdleNotifier) r3
            r2.compatIdle = r3
            java.util.BitSet r3 = androidx.test.espresso.base.UiControllerImpl.IdleCondition.createConditionSet()
            r2.conditionSet = r3
            java.lang.Object r3 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r6)
            javax.inject.Provider r3 = (javax.inject.Provider) r3
            r2.dynamicIdleProvider = r3
            java.lang.Object r3 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r7)
            android.os.Looper r3 = (android.os.Looper) r3
            r2.mainLooper = r3
            java.lang.Object r3 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r8)
            androidx.test.espresso.base.IdlingResourceRegistry r3 = (androidx.test.espresso.base.IdlingResourceRegistry) r3
            r2.idlingResourceRegistry = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.base.UiControllerImpl.<init>(androidx.test.espresso.base.EventInjector, androidx.test.espresso.base.IdleNotifier, androidx.test.espresso.base.IdleNotifier, javax.inject.Provider, android.os.Looper, androidx.test.espresso.base.IdlingResourceRegistry):void");
    }

    public static KeyCharacterMap getKeyCharacterMap() {
        if (Build.VERSION.SDK_INT < 11) {
            return KeyCharacterMap.load(0);
        }
        return KeyCharacterMap.load(-1);
    }

    private void initialize() {
        if (this.controllerHandler == null) {
            this.controllerHandler = new Handler(this);
        }
    }

    private void loopUntil(IdleCondition idleCondition, IdleNotifier<IdlingResourceRegistry.IdleNotificationCallback> idleNotifier) {
        loopUntil((EnumSet<IdleCondition>) EnumSet.of(idleCondition), idleNotifier);
    }

    public IdlingResourceRegistry getIdlingResourceRegistry() {
        return this.idlingResourceRegistry;
    }

    public boolean handleMessage(Message message) {
        if (IdleCondition.handleMessage(message, this.conditionSet, this.generation)) {
            return true;
        }
        String str = TAG;
        String valueOf = String.valueOf(message);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
        sb.append("Unknown message type: ");
        sb.append(valueOf);
        Log.i(str, sb.toString());
        return false;
    }

    public boolean injectKeyEvent(final KeyEvent keyEvent) throws InjectEventSecurityException {
        Preconditions.checkNotNull(keyEvent);
        Preconditions.checkState(Looper.myLooper() == this.mainLooper, "Expecting to be on main thread!");
        initialize();
        loopMainThreadUntilIdle();
        SignalingTask signalingTask = new SignalingTask(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Boolean.valueOf(UiControllerImpl.this.eventInjector.injectKeyEvent(keyEvent));
            }
        }, IdleCondition.KEY_INJECT_HAS_COMPLETED, this.generation);
        this.keyEventExecutor.submit(signalingTask);
        loopUntil(IdleCondition.KEY_INJECT_HAS_COMPLETED, this.dynamicIdleProvider.get());
        try {
            Preconditions.checkState(signalingTask.isDone(), "Key injection was signaled - but it wasnt done.");
            return ((Boolean) signalingTask.get()).booleanValue();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof InjectEventSecurityException) {
                throw ((InjectEventSecurityException) e.getCause());
            }
            throw new RuntimeException(e.getCause());
        } catch (InterruptedException e2) {
            throw new RuntimeException("impossible.", e2);
        }
    }

    public boolean injectMotionEvent(final MotionEvent motionEvent) throws InjectEventSecurityException {
        Preconditions.checkNotNull(motionEvent);
        Preconditions.checkState(Looper.myLooper() == this.mainLooper, "Expecting to be on main thread!");
        initialize();
        SignalingTask signalingTask = new SignalingTask(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Boolean.valueOf(UiControllerImpl.this.eventInjector.injectMotionEvent(motionEvent));
            }
        }, IdleCondition.MOTION_INJECTION_HAS_COMPLETED, this.generation);
        this.keyEventExecutor.submit(signalingTask);
        loopUntil(IdleCondition.MOTION_INJECTION_HAS_COMPLETED, this.dynamicIdleProvider.get());
        try {
            Preconditions.checkState(signalingTask.isDone(), "Motion event injection was signaled - but it wasnt done.");
            boolean booleanValue = ((Boolean) signalingTask.get()).booleanValue();
            loopMainThreadUntilIdle();
            return booleanValue;
        } catch (ExecutionException e) {
            if (!(e.getCause() instanceof InjectEventSecurityException)) {
                Throwables.throwIfUnchecked(e.getCause() != null ? e.getCause() : e);
                Throwable cause = e.getCause();
                Throwable th = e;
                if (cause != null) {
                    th = e.getCause();
                }
                throw new RuntimeException(th);
            }
            throw ((InjectEventSecurityException) e.getCause());
        } catch (InterruptedException e2) {
            throw new RuntimeException(e2);
        } catch (Throwable th2) {
            loopMainThreadUntilIdle();
            throw th2;
        }
    }

    /* JADX WARNING: type inference failed for: r7v0, types: [java.lang.Iterable<android.view.MotionEvent>, java.lang.Object, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean injectMotionEventSequence(java.lang.Iterable<android.view.MotionEvent> r7) throws androidx.test.espresso.InjectEventSecurityException {
        /*
            r6 = this;
            androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r7)
            boolean r0 = androidx.test.espresso.core.internal.deps.guava.collect.Iterables.isEmpty(r7)
            r1 = 1
            r0 = r0 ^ r1
            java.lang.String r2 = "Expecting non-empty events to inject"
            androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkState(r0, r2)
            android.os.Looper r0 = android.os.Looper.myLooper()
            android.os.Looper r2 = r6.mainLooper
            if (r0 != r2) goto L_0x0017
            goto L_0x0018
        L_0x0017:
            r1 = 0
        L_0x0018:
            java.lang.String r0 = "Expecting to be on main thread!"
            androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkState(r1, r0)
            r6.initialize()
            java.util.Iterator r0 = r7.iterator()
            r1 = 0
            java.lang.Object r7 = androidx.test.espresso.core.internal.deps.guava.collect.Iterables.getFirst(r7, r1)
            android.view.MotionEvent r7 = (android.view.MotionEvent) r7
            long r1 = r7.getEventTime()
            long r3 = android.os.SystemClock.uptimeMillis()
            androidx.test.espresso.base.UiControllerImpl$SignalingTask r7 = new androidx.test.espresso.base.UiControllerImpl$SignalingTask
            androidx.test.espresso.base.UiControllerImpl$4 r5 = new androidx.test.espresso.base.UiControllerImpl$4
            long r3 = r3 - r1
            r5.<init>(r0, r3)
            androidx.test.espresso.base.UiControllerImpl$IdleCondition r0 = androidx.test.espresso.base.UiControllerImpl.IdleCondition.MOTION_INJECTION_HAS_COMPLETED
            int r1 = r6.generation
            r7.<init>(r5, r0, r1)
            java.util.concurrent.ExecutorService r0 = r6.keyEventExecutor
            r0.submit(r7)
            androidx.test.espresso.base.UiControllerImpl$IdleCondition r0 = androidx.test.espresso.base.UiControllerImpl.IdleCondition.MOTION_INJECTION_HAS_COMPLETED
            javax.inject.Provider<androidx.test.espresso.base.IdleNotifier<androidx.test.espresso.base.IdlingResourceRegistry$IdleNotificationCallback>> r1 = r6.dynamicIdleProvider
            java.lang.Object r1 = r1.get()
            androidx.test.espresso.base.IdleNotifier r1 = (androidx.test.espresso.base.IdleNotifier) r1
            r6.loopUntil((androidx.test.espresso.base.UiControllerImpl.IdleCondition) r0, (androidx.test.espresso.base.IdleNotifier<androidx.test.espresso.base.IdlingResourceRegistry.IdleNotificationCallback>) r1)
            boolean r0 = r7.isDone()     // Catch:{ ExecutionException -> 0x0074, InterruptedException -> 0x006d }
            java.lang.String r1 = "MotionEvents injection was signaled - but it wasnt done."
            androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkState(r0, r1)     // Catch:{ ExecutionException -> 0x0074, InterruptedException -> 0x006d }
            java.lang.Object r7 = r7.get()     // Catch:{ ExecutionException -> 0x0074, InterruptedException -> 0x006d }
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ ExecutionException -> 0x0074, InterruptedException -> 0x006d }
            boolean r7 = r7.booleanValue()     // Catch:{ ExecutionException -> 0x0074, InterruptedException -> 0x006d }
            r6.loopMainThreadUntilIdle()
            return r7
        L_0x006b:
            r7 = move-exception
            goto L_0x00a3
        L_0x006d:
            r7 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch:{ all -> 0x006b }
            r0.<init>(r7)     // Catch:{ all -> 0x006b }
            throw r0     // Catch:{ all -> 0x006b }
        L_0x0074:
            r7 = move-exception
            java.lang.Throwable r0 = r7.getCause()     // Catch:{ all -> 0x006b }
            boolean r0 = r0 instanceof androidx.test.espresso.InjectEventSecurityException     // Catch:{ all -> 0x006b }
            if (r0 != 0) goto L_0x009c
            java.lang.Throwable r0 = r7.getCause()     // Catch:{ all -> 0x006b }
            if (r0 == 0) goto L_0x0088
            java.lang.Throwable r0 = r7.getCause()     // Catch:{ all -> 0x006b }
            goto L_0x0089
        L_0x0088:
            r0 = r7
        L_0x0089:
            androidx.test.espresso.core.internal.deps.guava.base.Throwables.throwIfUnchecked(r0)     // Catch:{ all -> 0x006b }
            java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch:{ all -> 0x006b }
            java.lang.Throwable r1 = r7.getCause()     // Catch:{ all -> 0x006b }
            if (r1 == 0) goto L_0x0098
            java.lang.Throwable r7 = r7.getCause()     // Catch:{ all -> 0x006b }
        L_0x0098:
            r0.<init>(r7)     // Catch:{ all -> 0x006b }
            throw r0     // Catch:{ all -> 0x006b }
        L_0x009c:
            java.lang.Throwable r7 = r7.getCause()     // Catch:{ all -> 0x006b }
            androidx.test.espresso.InjectEventSecurityException r7 = (androidx.test.espresso.InjectEventSecurityException) r7     // Catch:{ all -> 0x006b }
            throw r7     // Catch:{ all -> 0x006b }
        L_0x00a3:
            r6.loopMainThreadUntilIdle()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.base.UiControllerImpl.injectMotionEventSequence(java.lang.Iterable):boolean");
    }

    public boolean injectString(String str) throws InjectEventSecurityException {
        Preconditions.checkNotNull(str);
        Preconditions.checkState(Looper.myLooper() == this.mainLooper, "Expecting to be on main thread!");
        initialize();
        if (str.isEmpty()) {
            Log.w(TAG, "Supplied string is empty resulting in no-op (nothing is typed).");
            return true;
        }
        KeyEvent[] events = getKeyCharacterMap().getEvents(str.toCharArray());
        if (events != null) {
            Log.d(TAG, String.format(Locale.ROOT, "Injecting string: \"%s\"", new Object[]{str}));
            int length = events.length;
            int i = 0;
            boolean z = false;
            while (true) {
                if (i >= length) {
                    break;
                }
                KeyEvent keyEvent = events[i];
                Preconditions.checkNotNull(keyEvent, String.format(Locale.ROOT, "Failed to get event for character (%c) with key code (%s)", new Object[]{Integer.valueOf(keyEvent.getKeyCode()), Integer.valueOf(keyEvent.getUnicodeChar())}));
                KeyEvent keyEvent2 = keyEvent;
                z = false;
                int i2 = 0;
                while (!z && i2 < 4) {
                    keyEvent2 = KeyEvent.changeTimeRepeat(keyEvent2, SystemClock.uptimeMillis(), 0);
                    z = injectKeyEvent(keyEvent2);
                    i2++;
                }
                if (!z) {
                    Log.e(TAG, String.format(Locale.ROOT, "Failed to inject event for character (%c) with key code (%s)", new Object[]{Integer.valueOf(keyEvent2.getUnicodeChar()), Integer.valueOf(keyEvent2.getKeyCode())}));
                    break;
                }
                i++;
            }
            return z;
        }
        throw new RuntimeException(String.format(Locale.ROOT, "Failed to get key events for string %s (i.e. current IME does not understand how to translate the string into key events). As a workaround, you can use replaceText action to set the text directly in the EditText field.", new Object[]{str}));
    }

    public void interruptEspressoTasks() {
        initialize();
        this.controllerHandler.post(new Runnable() {
            public void run() {
                if (UiControllerImpl.this.interrogation != null) {
                    UiControllerImpl.this.interrogation.interruptInterrogation();
                    UiControllerImpl.this.controllerHandler.removeCallbacksAndMessages(Integer.valueOf(UiControllerImpl.this.generation));
                }
            }
        });
    }

    public void loopMainThreadForAtLeast(long j) {
        initialize();
        boolean z = false;
        Preconditions.checkState(Looper.myLooper() == this.mainLooper, "Expecting to be on main thread!");
        Preconditions.checkState(!IdleCondition.DELAY_HAS_PAST.isSignaled(this.conditionSet), "recursion detected!");
        if (j > 0) {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.controllerHandler.postAtTime(new SignalingTask(NO_OP, IdleCondition.DELAY_HAS_PAST, this.generation), Integer.valueOf(this.generation), SystemClock.uptimeMillis() + j);
        loopUntil(IdleCondition.DELAY_HAS_PAST, this.dynamicIdleProvider.get());
        loopMainThreadUntilIdle();
    }

    /* JADX INFO: finally extract failed */
    public void loopMainThreadUntilIdle() {
        initialize();
        Preconditions.checkState(Looper.myLooper() == this.mainLooper, "Expecting to be on main thread!");
        IdleNotifier<IdlingResourceRegistry.IdleNotificationCallback> idleNotifier = this.dynamicIdleProvider.get();
        while (true) {
            EnumSet<E> noneOf = EnumSet.noneOf(IdleCondition.class);
            if (!this.asyncIdle.isIdleNow()) {
                this.asyncIdle.registerNotificationCallback(new SignalingTask(NO_OP, IdleCondition.ASYNC_TASKS_HAVE_IDLED, this.generation));
                noneOf.add(IdleCondition.ASYNC_TASKS_HAVE_IDLED);
            }
            if (!this.compatIdle.isIdleNow()) {
                this.compatIdle.registerNotificationCallback(new SignalingTask(NO_OP, IdleCondition.COMPAT_TASKS_HAVE_IDLED, this.generation));
                noneOf.add(IdleCondition.COMPAT_TASKS_HAVE_IDLED);
            }
            if (!idleNotifier.isIdleNow()) {
                final IdlingPolicy dynamicIdlingResourceWarningPolicy = IdlingPolicies.getDynamicIdlingResourceWarningPolicy();
                final IdlingPolicy dynamicIdlingResourceErrorPolicy = IdlingPolicies.getDynamicIdlingResourceErrorPolicy();
                final SignalingTask signalingTask = new SignalingTask(NO_OP, IdleCondition.DYNAMIC_TASKS_HAVE_IDLED, this.generation);
                idleNotifier.registerNotificationCallback(new IdlingResourceRegistry.IdleNotificationCallback() {
                    public void allResourcesIdle() {
                        UiControllerImpl.this.controllerHandler.post(signalingTask);
                    }

                    public void resourcesHaveTimedOut(List<String> list) {
                        dynamicIdlingResourceErrorPolicy.handleTimeout(list, "IdlingResources have timed out!");
                        UiControllerImpl.this.controllerHandler.post(signalingTask);
                    }

                    public void resourcesStillBusyWarning(List<String> list) {
                        dynamicIdlingResourceWarningPolicy.handleTimeout(list, "IdlingResources are still busy!");
                    }
                });
                noneOf.add(IdleCondition.DYNAMIC_TASKS_HAVE_IDLED);
            }
            try {
                idleNotifier = loopUntil((EnumSet<IdleCondition>) noneOf, idleNotifier);
                this.asyncIdle.cancelCallback();
                this.compatIdle.cancelCallback();
                idleNotifier.cancelCallback();
                if (this.asyncIdle.isIdleNow() && this.compatIdle.isIdleNow() && idleNotifier.isIdleNow()) {
                    return;
                }
            } catch (Throwable th) {
                this.asyncIdle.cancelCallback();
                this.compatIdle.cancelCallback();
                idleNotifier.cancelCallback();
                throw th;
            }
        }
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    private androidx.test.espresso.base.IdleNotifier<androidx.test.espresso.base.IdlingResourceRegistry.IdleNotificationCallback> loopUntil(java.util.EnumSet<androidx.test.espresso.base.UiControllerImpl.IdleCondition> r11, androidx.test.espresso.base.IdleNotifier<androidx.test.espresso.base.IdlingResourceRegistry.IdleNotificationCallback> r12) {
        /*
            r10 = this;
            java.lang.String r0 = "Espresso interrogation of the main thread is interrupted"
            androidx.test.espresso.IdlingPolicy r1 = androidx.test.espresso.IdlingPolicies.getMasterIdlingPolicy()
            androidx.test.espresso.IdlingPolicy r2 = androidx.test.espresso.IdlingPolicies.getDynamicIdlingResourceErrorPolicy()
            r3 = 0
            r4 = 1
            long r5 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x016e }
            java.util.concurrent.TimeUnit r7 = r1.getIdleTimeoutUnit()     // Catch:{ all -> 0x016e }
            long r8 = r1.getIdleTimeout()     // Catch:{ all -> 0x016e }
            long r7 = r7.toMillis(r8)     // Catch:{ all -> 0x016e }
            long r5 = r5 + r7
            androidx.test.espresso.base.UiControllerImpl$MainThreadInterrogation r7 = new androidx.test.espresso.base.UiControllerImpl$MainThreadInterrogation     // Catch:{ all -> 0x016e }
            java.util.BitSet r8 = r10.conditionSet     // Catch:{ all -> 0x016e }
            r7.<init>(r11, r8, r5)     // Catch:{ all -> 0x016e }
            r10.interrogation = r7     // Catch:{ all -> 0x016e }
            java.lang.Object r5 = androidx.test.espresso.base.Interrogator.loopAndInterrogate(r7)     // Catch:{ all -> 0x016e }
            androidx.test.espresso.base.UiControllerImpl$InterrogationStatus r5 = (androidx.test.espresso.base.UiControllerImpl.InterrogationStatus) r5     // Catch:{ all -> 0x016e }
            androidx.test.espresso.base.UiControllerImpl$InterrogationStatus r6 = androidx.test.espresso.base.UiControllerImpl.InterrogationStatus.COMPLETED     // Catch:{ all -> 0x016e }
            if (r6 != r5) goto L_0x004e
            int r0 = r10.generation
            int r0 = r0 + r4
            r10.generation = r0
            java.util.Iterator r11 = r11.iterator()
        L_0x0039:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto L_0x004b
            java.lang.Object r0 = r11.next()
            androidx.test.espresso.base.UiControllerImpl$IdleCondition r0 = (androidx.test.espresso.base.UiControllerImpl.IdleCondition) r0
            java.util.BitSet r1 = r10.conditionSet
            r0.reset(r1)
            goto L_0x0039
        L_0x004b:
            r10.interrogation = r3
            return r12
        L_0x004e:
            androidx.test.espresso.base.UiControllerImpl$InterrogationStatus r6 = androidx.test.espresso.base.UiControllerImpl.InterrogationStatus.INTERRUPTED     // Catch:{ all -> 0x016e }
            if (r6 == r5) goto L_0x0163
            java.util.ArrayList r0 = androidx.test.espresso.core.internal.deps.guava.collect.Lists.newArrayList()     // Catch:{ all -> 0x016e }
            java.util.Iterator r5 = r11.iterator()     // Catch:{ all -> 0x016e }
        L_0x005a:
            boolean r6 = r5.hasNext()     // Catch:{ all -> 0x016e }
            r7 = 2
            r8 = 3
            if (r6 == 0) goto L_0x00e8
            java.lang.Object r6 = r5.next()     // Catch:{ all -> 0x016e }
            androidx.test.espresso.base.UiControllerImpl$IdleCondition r6 = (androidx.test.espresso.base.UiControllerImpl.IdleCondition) r6     // Catch:{ all -> 0x016e }
            java.util.BitSet r9 = r10.conditionSet     // Catch:{ all -> 0x016e }
            boolean r9 = r6.isSignaled(r9)     // Catch:{ all -> 0x016e }
            if (r9 != 0) goto L_0x005a
            java.lang.String r9 = r6.name()     // Catch:{ all -> 0x016e }
            r0.add(r9)     // Catch:{ all -> 0x016e }
            int[] r9 = androidx.test.espresso.base.UiControllerImpl.C07067.f198x22726293     // Catch:{ all -> 0x016e }
            int r6 = r6.ordinal()     // Catch:{ all -> 0x016e }
            r6 = r9[r6]     // Catch:{ all -> 0x016e }
            if (r6 == r4) goto L_0x00c8
            if (r6 == r7) goto L_0x00a9
            if (r6 == r8) goto L_0x0086
            goto L_0x005a
        L_0x0086:
            boolean r6 = r2.getDisableOnTimeout()     // Catch:{ all -> 0x016e }
            if (r6 != 0) goto L_0x0098
            boolean r6 = r1.getTimeoutIfDebuggerAttached()     // Catch:{ all -> 0x016e }
            if (r6 != 0) goto L_0x005a
            boolean r6 = android.os.Debug.isDebuggerConnected()     // Catch:{ all -> 0x016e }
            if (r6 == 0) goto L_0x005a
        L_0x0098:
            r12.cancelCallback()     // Catch:{ all -> 0x016e }
            androidx.test.espresso.base.NoopIdleNotificationCallbackIdleNotifierProvider r12 = new androidx.test.espresso.base.NoopIdleNotificationCallbackIdleNotifierProvider     // Catch:{ all -> 0x016e }
            r12.<init>()     // Catch:{ all -> 0x016e }
            r10.dynamicIdleProvider = r12     // Catch:{ all -> 0x016e }
            java.lang.Object r12 = r12.get()     // Catch:{ all -> 0x016e }
            androidx.test.espresso.base.IdleNotifier r12 = (androidx.test.espresso.base.IdleNotifier) r12     // Catch:{ all -> 0x016e }
            goto L_0x005a
        L_0x00a9:
            boolean r6 = r1.getDisableOnTimeout()     // Catch:{ all -> 0x016e }
            if (r6 != 0) goto L_0x00bb
            boolean r6 = r1.getTimeoutIfDebuggerAttached()     // Catch:{ all -> 0x016e }
            if (r6 != 0) goto L_0x005a
            boolean r6 = android.os.Debug.isDebuggerConnected()     // Catch:{ all -> 0x016e }
            if (r6 == 0) goto L_0x005a
        L_0x00bb:
            androidx.test.espresso.base.IdleNotifier<java.lang.Runnable> r6 = r10.compatIdle     // Catch:{ all -> 0x016e }
            r6.cancelCallback()     // Catch:{ all -> 0x016e }
            androidx.test.espresso.base.NoopRunnableIdleNotifier r6 = new androidx.test.espresso.base.NoopRunnableIdleNotifier     // Catch:{ all -> 0x016e }
            r6.<init>()     // Catch:{ all -> 0x016e }
            r10.compatIdle = r6     // Catch:{ all -> 0x016e }
            goto L_0x005a
        L_0x00c8:
            boolean r6 = r1.getDisableOnTimeout()     // Catch:{ all -> 0x016e }
            if (r6 != 0) goto L_0x00da
            boolean r6 = r1.getTimeoutIfDebuggerAttached()     // Catch:{ all -> 0x016e }
            if (r6 != 0) goto L_0x005a
            boolean r6 = android.os.Debug.isDebuggerConnected()     // Catch:{ all -> 0x016e }
            if (r6 == 0) goto L_0x005a
        L_0x00da:
            androidx.test.espresso.base.IdleNotifier<java.lang.Runnable> r6 = r10.asyncIdle     // Catch:{ all -> 0x016e }
            r6.cancelCallback()     // Catch:{ all -> 0x016e }
            androidx.test.espresso.base.NoopRunnableIdleNotifier r6 = new androidx.test.espresso.base.NoopRunnableIdleNotifier     // Catch:{ all -> 0x016e }
            r6.<init>()     // Catch:{ all -> 0x016e }
            r10.asyncIdle = r6     // Catch:{ all -> 0x016e }
            goto L_0x005a
        L_0x00e8:
            boolean r2 = r0.isEmpty()     // Catch:{ all -> 0x016e }
            if (r2 == 0) goto L_0x0117
            androidx.test.espresso.base.UiControllerImpl$MainThreadInterrogation r2 = r10.interrogation     // Catch:{ all -> 0x016e }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x016e }
            java.lang.String r5 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x016e }
            int r5 = r5.length()     // Catch:{ all -> 0x016e }
            int r5 = r5 + 37
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x016e }
            r6.<init>(r5)     // Catch:{ all -> 0x016e }
            java.lang.String r5 = "MAIN_LOOPER_HAS_IDLED(last message: "
            r6.append(r5)     // Catch:{ all -> 0x016e }
            r6.append(r2)     // Catch:{ all -> 0x016e }
            java.lang.String r2 = ")"
            r6.append(r2)     // Catch:{ all -> 0x016e }
            java.lang.String r2 = r6.toString()     // Catch:{ all -> 0x016e }
            r0.add(r2)     // Catch:{ all -> 0x016e }
        L_0x0117:
            java.util.Locale r2 = java.util.Locale.ROOT     // Catch:{ all -> 0x016e }
            java.lang.String r5 = "Looped for %s iterations over %s %s."
            java.lang.Object[] r6 = new java.lang.Object[r8]     // Catch:{ all -> 0x016e }
            r8 = 0
            androidx.test.espresso.base.UiControllerImpl$MainThreadInterrogation r9 = r10.interrogation     // Catch:{ all -> 0x016e }
            int r9 = r9.execCount     // Catch:{ all -> 0x016e }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x016e }
            r6[r8] = r9     // Catch:{ all -> 0x016e }
            long r8 = r1.getIdleTimeout()     // Catch:{ all -> 0x016e }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x016e }
            r6[r4] = r8     // Catch:{ all -> 0x016e }
            java.util.concurrent.TimeUnit r8 = r1.getIdleTimeoutUnit()     // Catch:{ all -> 0x016e }
            java.lang.String r8 = r8.name()     // Catch:{ all -> 0x016e }
            r6[r7] = r8     // Catch:{ all -> 0x016e }
            java.lang.String r2 = java.lang.String.format(r2, r5, r6)     // Catch:{ all -> 0x016e }
            r1.handleTimeout(r0, r2)     // Catch:{ all -> 0x016e }
            int r0 = r10.generation
            int r0 = r0 + r4
            r10.generation = r0
            java.util.Iterator r11 = r11.iterator()
        L_0x014e:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto L_0x0160
            java.lang.Object r0 = r11.next()
            androidx.test.espresso.base.UiControllerImpl$IdleCondition r0 = (androidx.test.espresso.base.UiControllerImpl.IdleCondition) r0
            java.util.BitSet r1 = r10.conditionSet
            r0.reset(r1)
            goto L_0x014e
        L_0x0160:
            r10.interrogation = r3
            return r12
        L_0x0163:
            java.lang.String r12 = TAG     // Catch:{ all -> 0x016e }
            android.util.Log.w(r12, r0)     // Catch:{ all -> 0x016e }
            java.lang.RuntimeException r12 = new java.lang.RuntimeException     // Catch:{ all -> 0x016e }
            r12.<init>(r0)     // Catch:{ all -> 0x016e }
            throw r12     // Catch:{ all -> 0x016e }
        L_0x016e:
            r12 = move-exception
            int r0 = r10.generation
            int r0 = r0 + r4
            r10.generation = r0
            java.util.Iterator r11 = r11.iterator()
        L_0x0178:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto L_0x018a
            java.lang.Object r0 = r11.next()
            androidx.test.espresso.base.UiControllerImpl$IdleCondition r0 = (androidx.test.espresso.base.UiControllerImpl.IdleCondition) r0
            java.util.BitSet r1 = r10.conditionSet
            r0.reset(r1)
            goto L_0x0178
        L_0x018a:
            r10.interrogation = r3
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.base.UiControllerImpl.loopUntil(java.util.EnumSet, androidx.test.espresso.base.IdleNotifier):androidx.test.espresso.base.IdleNotifier");
    }
}
