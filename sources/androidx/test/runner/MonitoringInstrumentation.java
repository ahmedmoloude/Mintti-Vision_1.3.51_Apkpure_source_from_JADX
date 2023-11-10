package androidx.test.runner;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.UserHandle;
import android.util.Log;
import androidx.test.internal.platform.app.ActivityLifecycleTimeout;
import androidx.test.internal.runner.InstrumentationConnection;
import androidx.test.internal.runner.hidden.ExposedInstrumentationApi;
import androidx.test.internal.runner.intent.IntentMonitorImpl;
import androidx.test.internal.runner.intercepting.DefaultInterceptingActivityFactory;
import androidx.test.internal.runner.lifecycle.ActivityLifecycleMonitorImpl;
import androidx.test.internal.runner.lifecycle.ApplicationLifecycleMonitorImpl;
import androidx.test.internal.util.Checks;
import androidx.test.internal.util.ProcSummary;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.intent.IntentMonitorRegistry;
import androidx.test.runner.intent.IntentStubberRegistry;
import androidx.test.runner.intercepting.InterceptingActivityFactory;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.ApplicationLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.ApplicationStage;
import androidx.test.runner.lifecycle.Stage;
import com.itextpdf.text.pdf.PdfBoolean;
import java.lang.Thread;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MonitoringInstrumentation extends ExposedInstrumentationApi {
    private static final long MILLIS_TO_POLL_FOR_ACTIVITY_STOP;
    private static final long MILLIS_TO_WAIT_FOR_ACTIVITY_TO_STOP;
    private static final String TAG = "MonitoringInstr";
    private AtomicBoolean anActivityHasBeenLaunched = new AtomicBoolean(false);
    private ApplicationLifecycleMonitorImpl applicationMonitor = new ApplicationLifecycleMonitorImpl();
    private ExecutorService executorService;
    private volatile boolean finished = false;
    private Handler handlerForMainLooper;
    private MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {
        public boolean queueIdle() {
            MonitoringInstrumentation.this.lastIdleTime.set(System.currentTimeMillis());
            return true;
        }
    };
    private IntentMonitorImpl intentMonitor = new IntentMonitorImpl();
    private volatile InterceptingActivityFactory interceptingActivityFactory;
    private ThreadLocal<Boolean> isDexmakerClassLoaderInitialized = new ThreadLocal<>();
    /* access modifiers changed from: private */
    public AtomicBoolean isJsBridgeLoaded = new AtomicBoolean(false);
    private volatile Boolean isOriginalInstr = null;
    private String jsBridgeClassName;
    /* access modifiers changed from: private */
    public AtomicLong lastIdleTime = new AtomicLong(0);
    /* access modifiers changed from: private */
    public ActivityLifecycleMonitorImpl lifecycleMonitor = new ActivityLifecycleMonitorImpl();
    /* access modifiers changed from: private */
    public Thread.UncaughtExceptionHandler standardHandler;
    private AtomicInteger startedActivityCounter = new AtomicInteger(0);

    static {
        long millis = TimeUnit.SECONDS.toMillis(2);
        MILLIS_TO_WAIT_FOR_ACTIVITY_TO_STOP = millis;
        MILLIS_TO_POLL_FOR_ACTIVITY_STOP = millis / 40;
    }

    public void onCreate(Bundle bundle) {
        Log.i(TAG, "Instrumentation started!");
        logUncaughtExceptions();
        installMultidex();
        InstrumentationRegistry.registerInstance(this, bundle);
        androidx.test.InstrumentationRegistry.registerInstance(this, bundle);
        ActivityLifecycleMonitorRegistry.registerInstance(this.lifecycleMonitor);
        ApplicationLifecycleMonitorRegistry.registerInstance(this.applicationMonitor);
        IntentMonitorRegistry.registerInstance(this.intentMonitor);
        this.handlerForMainLooper = new Handler(Looper.getMainLooper());
        this.executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new SynchronousQueue(), new ThreadFactory(this) {
            public Thread newThread(Runnable runnable) {
                Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
                newThread.setName(MonitoringInstrumentation.class.getSimpleName());
                return newThread;
            }
        });
        Looper.myQueue().addIdleHandler(this.idleHandler);
        super.onCreate(bundle);
        specifyDexMakerCacheProperty();
        setupDexmakerClassloader();
        useDefaultInterceptingActivityFactory();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003f, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0045, code lost:
        throw new java.lang.RuntimeException("multidex is available at runtime, but calling it failed.", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0046, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004c, code lost:
        throw new java.lang.RuntimeException("multidex is available at runtime, but calling it failed.", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004e, code lost:
        android.util.Log.i(TAG, "No multidex.", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0052, code lost:
        android.util.Log.i(TAG, "No multidex.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f A[ExcHandler: IllegalAccessException (r1v2 'e' java.lang.IllegalAccessException A[CUSTOM_DECLARE]), Splitter:B:2:0x000c] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0046 A[ExcHandler: InvocationTargetException (r1v1 'e' java.lang.reflect.InvocationTargetException A[CUSTOM_DECLARE]), Splitter:B:2:0x000c] */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[ExcHandler: ClassNotFoundException (unused java.lang.ClassNotFoundException), SYNTHETIC, Splitter:B:2:0x000c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void installMultidex() {
        /*
            r10 = this;
            java.lang.String r0 = "multidex is available at runtime, but calling it failed."
            java.lang.String r1 = "No multidex."
            java.lang.String r2 = "MonitoringInstr"
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 21
            if (r3 >= r4) goto L_0x0055
            java.lang.Class r3 = getMultiDexClass()     // Catch:{ ClassNotFoundException -> 0x0052, NoSuchMethodException -> 0x004d, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            java.lang.String r4 = "installInstrumentation"
            r5 = 2
            java.lang.Class[] r6 = new java.lang.Class[r5]     // Catch:{ NoSuchMethodException -> 0x0036, ClassNotFoundException -> 0x0052, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            java.lang.Class<android.content.Context> r7 = android.content.Context.class
            r8 = 0
            r6[r8] = r7     // Catch:{ NoSuchMethodException -> 0x0036, ClassNotFoundException -> 0x0052, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            java.lang.Class<android.content.Context> r7 = android.content.Context.class
            r9 = 1
            r6[r9] = r7     // Catch:{ NoSuchMethodException -> 0x0036, ClassNotFoundException -> 0x0052, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            java.lang.reflect.Method r4 = r3.getDeclaredMethod(r4, r6)     // Catch:{ NoSuchMethodException -> 0x0036, ClassNotFoundException -> 0x0052, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            r6 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ NoSuchMethodException -> 0x0036, ClassNotFoundException -> 0x0052, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            android.content.Context r7 = r10.getContext()     // Catch:{ NoSuchMethodException -> 0x0036, ClassNotFoundException -> 0x0052, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            r5[r8] = r7     // Catch:{ NoSuchMethodException -> 0x0036, ClassNotFoundException -> 0x0052, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            android.content.Context r7 = r10.getTargetContext()     // Catch:{ NoSuchMethodException -> 0x0036, ClassNotFoundException -> 0x0052, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            r5[r9] = r7     // Catch:{ NoSuchMethodException -> 0x0036, ClassNotFoundException -> 0x0052, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            r4.invoke(r6, r5)     // Catch:{ NoSuchMethodException -> 0x0036, ClassNotFoundException -> 0x0052, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            goto L_0x0055
        L_0x0036:
            java.lang.String r4 = "Could not find MultiDex.installInstrumentation. Calling MultiDex.install instead. Is an old version of the multidex library being used? If test app is using multidex, classes might not be found"
            android.util.Log.w(r2, r4)     // Catch:{ ClassNotFoundException -> 0x0052, NoSuchMethodException -> 0x004d, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            r10.installOldMultiDex(r3)     // Catch:{ ClassNotFoundException -> 0x0052, NoSuchMethodException -> 0x004d, InvocationTargetException -> 0x0046, IllegalAccessException -> 0x003f }
            goto L_0x0055
        L_0x003f:
            r1 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            r2.<init>(r0, r1)
            throw r2
        L_0x0046:
            r1 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            r2.<init>(r0, r1)
            throw r2
        L_0x004d:
            r0 = move-exception
            android.util.Log.i(r2, r1, r0)
            goto L_0x0055
        L_0x0052:
            android.util.Log.i(r2, r1)
        L_0x0055:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.runner.MonitoringInstrumentation.installMultidex():void");
    }

    private static Class<?> getMultiDexClass() throws ClassNotFoundException {
        try {
            return Class.forName("androidx.multidex.MultiDex");
        } catch (ClassNotFoundException unused) {
            return Class.forName("androidx.multidex.MultiDex");
        }
    }

    /* access modifiers changed from: protected */
    public void installOldMultiDex(Class<?> cls) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        cls.getDeclaredMethod("install", new Class[]{Context.class}).invoke((Object) null, new Object[]{getTargetContext()});
    }

    /* access modifiers changed from: protected */
    public void specifyDexMakerCacheProperty() {
        System.getProperties().put("dexmaker.dexcache", getTargetContext().getDir("dxmaker_cache", 0).getAbsolutePath());
    }

    /* access modifiers changed from: protected */
    public final void setJsBridgeClassName(String str) {
        Objects.requireNonNull(str, "JsBridge class name cannot be null!");
        if (!this.isJsBridgeLoaded.get()) {
            this.jsBridgeClassName = str;
            return;
        }
        throw new IllegalStateException("JsBridge is already loaded!");
    }

    private void setupDexmakerClassloader() {
        if (!Boolean.TRUE.equals(this.isDexmakerClassLoaderInitialized.get())) {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            ClassLoader classLoader = getTargetContext().getClassLoader();
            if (contextClassLoader != classLoader) {
                Log.i(TAG, String.format("Setting context classloader to '%s', Original: '%s'", new Object[]{classLoader, contextClassLoader}));
                Thread.currentThread().setContextClassLoader(classLoader);
            }
            this.isDexmakerClassLoaderInitialized.set(Boolean.TRUE);
        }
    }

    private void logUncaughtExceptions() {
        this.standardHandler = Thread.currentThread().getUncaughtExceptionHandler();
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable th) {
                MonitoringInstrumentation.this.onException(thread, th);
                if (MonitoringInstrumentation.this.standardHandler != null) {
                    Log.w(MonitoringInstrumentation.TAG, String.format("Invoking uncaught exception handler %s (a %s)", new Object[]{MonitoringInstrumentation.this.standardHandler, MonitoringInstrumentation.this.standardHandler.getClass()}));
                    MonitoringInstrumentation.this.standardHandler.uncaughtException(thread, th);
                } else {
                    String valueOf = String.valueOf(thread.getName());
                    Log.w(MonitoringInstrumentation.TAG, valueOf.length() != 0 ? "Invoking uncaught exception handler for thread: ".concat(valueOf) : new String("Invoking uncaught exception handler for thread: "));
                    thread.getThreadGroup().uncaughtException(thread, th);
                }
                if (!"robolectric".equals(Build.FINGERPRINT) && Looper.getMainLooper().getThread().equals(thread)) {
                    Log.e(MonitoringInstrumentation.TAG, "The main thread has died and the handlers didn't care, exiting");
                    System.exit(-10);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void restoreUncaughtExceptionHandler() {
        Thread.currentThread().setUncaughtExceptionHandler(this.standardHandler);
    }

    public void onStart() {
        super.onStart();
        String str = this.jsBridgeClassName;
        if (str != null) {
            tryLoadingJsBridge(str);
        }
        waitForIdleSync();
        setupDexmakerClassloader();
        InstrumentationConnection.getInstance().init(this, new ActivityFinisher());
    }

    public void finish(int i, Bundle bundle) {
        if (this.finished) {
            Log.w(TAG, "finish called 2x!");
            return;
        }
        this.finished = true;
        if (shouldWaitForActivitiesToComplete()) {
            this.handlerForMainLooper.post(new ActivityFinisher());
            long currentTimeMillis = System.currentTimeMillis();
            waitForActivitiesToComplete();
            Log.i(TAG, String.format("waitForActivitiesToComplete() took: %sms", new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)}));
        }
        ActivityLifecycleMonitorRegistry.registerInstance((ActivityLifecycleMonitor) null);
        restoreUncaughtExceptionHandler();
        super.finish(i, bundle);
    }

    /* access modifiers changed from: protected */
    public boolean shouldWaitForActivitiesToComplete() {
        return Boolean.parseBoolean(InstrumentationRegistry.getArguments().getString("waitForActivitiesToComplete", PdfBoolean.TRUE));
    }

    /* access modifiers changed from: protected */
    public void waitForActivitiesToComplete() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            long currentTimeMillis = System.currentTimeMillis() + MILLIS_TO_WAIT_FOR_ACTIVITY_TO_STOP;
            int i = this.startedActivityCounter.get();
            while (i > 0 && System.currentTimeMillis() < currentTimeMillis) {
                try {
                    StringBuilder sb = new StringBuilder(37);
                    sb.append("Unstopped activity count: ");
                    sb.append(i);
                    Log.i(TAG, sb.toString());
                    Thread.sleep(MILLIS_TO_POLL_FOR_ACTIVITY_STOP);
                    i = this.startedActivityCounter.get();
                } catch (InterruptedException e) {
                    Log.i(TAG, "Abandoning activity wait due to interruption.", e);
                }
            }
            if (i > 0) {
                dumpThreadStateToOutputs("ThreadState-unstopped.txt");
                Log.w(TAG, String.format("Still %s activities active after waiting %s ms.", new Object[]{Integer.valueOf(i), Long.valueOf(MILLIS_TO_WAIT_FOR_ACTIVITY_TO_STOP)}));
                return;
            }
            return;
        }
        throw new IllegalStateException("Cannot be called from main thread!");
    }

    public void onDestroy() {
        Log.i(TAG, "Instrumentation Finished!");
        Looper.myQueue().removeIdleHandler(this.idleHandler);
        InstrumentationConnection.getInstance().terminate();
        super.onDestroy();
    }

    public void callApplicationOnCreate(Application application) {
        this.applicationMonitor.signalLifecycleChange(application, ApplicationStage.PRE_ON_CREATE);
        super.callApplicationOnCreate(application);
        this.applicationMonitor.signalLifecycleChange(application, ApplicationStage.CREATED);
    }

    public void runOnMainSync(Runnable runnable) {
        FutureTask futureTask = new FutureTask(runnable, (Object) null);
        super.runOnMainSync(futureTask);
        try {
            futureTask.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException(cause);
            }
        }
    }

    public Activity startActivitySync(final Intent intent) {
        Checks.checkNotMainThread();
        long j = this.lastIdleTime.get();
        if (this.anActivityHasBeenLaunched.compareAndSet(false, true)) {
            intent.addFlags(PagedChannelRandomAccessSource.DEFAULT_TOTAL_BUFSIZE);
        }
        Future submit = this.executorService.submit(new Callable<Activity>() {
            public Activity call() {
                return MonitoringInstrumentation.super.startActivitySync(intent);
            }
        });
        try {
            return (Activity) submit.get(ActivityLifecycleTimeout.getMillis(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException unused) {
            dumpThreadStateToOutputs("ThreadState-startActivityTimeout.txt");
            submit.cancel(true);
            throw new RuntimeException(String.format("Could not launch intent %s within %s milliseconds. Perhaps the main thread has not gone idle within a reasonable amount of time? There could be an animation or something constantly repainting the screen. Or the activity is doing network calls on creation? See the threaddump logs. For your reference the last time the event queue was idle before your activity launch request was %s and now the last time the queue went idle was: %s. If these numbers are the same your activity might be hogging the event queue.", new Object[]{intent, Long.valueOf(ActivityLifecycleTimeout.getMillis()), Long.valueOf(j), Long.valueOf(this.lastIdleTime.get())}));
        } catch (ExecutionException e) {
            throw new RuntimeException("Could not launch activity", e.getCause());
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("interrupted", e2);
        }
    }

    public Instrumentation.ActivityResult execStartActivity(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent intent, int i) {
        this.intentMonitor.signalIntent(intent);
        Instrumentation.ActivityResult stubResultFor = stubResultFor(intent);
        if (stubResultFor == null) {
            return super.execStartActivity(context, iBinder, iBinder2, activity, intent, i);
        }
        Log.i(TAG, String.format("Stubbing intent %s", new Object[]{intent}));
        return stubResultFor;
    }

    public Instrumentation.ActivityResult execStartActivity(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent intent, int i, Bundle bundle) {
        this.intentMonitor.signalIntent(intent);
        Instrumentation.ActivityResult stubResultFor = stubResultFor(intent);
        if (stubResultFor == null) {
            return super.execStartActivity(context, iBinder, iBinder2, activity, intent, i, bundle);
        }
        Log.i(TAG, String.format("Stubbing intent %s", new Object[]{intent}));
        return stubResultFor;
    }

    public Instrumentation.ActivityResult execStartActivity(Context context, IBinder iBinder, IBinder iBinder2, String str, Intent intent, int i, Bundle bundle) {
        this.intentMonitor.signalIntent(intent);
        Instrumentation.ActivityResult stubResultFor = stubResultFor(intent);
        if (stubResultFor == null) {
            return super.execStartActivity(context, iBinder, iBinder2, str, intent, i, bundle);
        }
        Log.i(TAG, String.format("Stubbing intent %s", new Object[]{intent}));
        return stubResultFor;
    }

    public Instrumentation.ActivityResult execStartActivity(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent intent, int i, Bundle bundle, UserHandle userHandle) {
        return super.execStartActivity(context, iBinder, iBinder2, activity, intent, i, bundle, userHandle);
    }

    public void execStartActivities(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent[] intentArr, Bundle bundle) {
        Log.d(TAG, "execStartActivities(context, ibinder, ibinder, activity, intent[], bundle)");
        for (Intent execStartActivity : intentArr) {
            execStartActivity(context, iBinder, iBinder2, activity, execStartActivity, -1, bundle);
        }
    }

    public Instrumentation.ActivityResult execStartActivity(Context context, IBinder iBinder, IBinder iBinder2, Fragment fragment, Intent intent, int i, Bundle bundle) {
        Log.d(TAG, "execStartActivity(context, IBinder, IBinder, Fragment, Intent, int, Bundle)");
        this.intentMonitor.signalIntent(intent);
        Instrumentation.ActivityResult stubResultFor = stubResultFor(intent);
        if (stubResultFor == null) {
            return super.execStartActivity(context, iBinder, iBinder2, fragment, intent, i, bundle);
        }
        Log.i(TAG, String.format("Stubbing intent %s", new Object[]{intent}));
        return stubResultFor;
    }

    private static class StubResultCallable implements Callable<Instrumentation.ActivityResult> {
        private final Intent intent;

        StubResultCallable(Intent intent2) {
            this.intent = intent2;
        }

        public Instrumentation.ActivityResult call() {
            return IntentStubberRegistry.getInstance().getActivityResultForIntent(this.intent);
        }
    }

    private Instrumentation.ActivityResult stubResultFor(Intent intent) {
        if (!IntentStubberRegistry.isLoaded()) {
            return null;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return IntentStubberRegistry.getInstance().getActivityResultForIntent(intent);
        }
        FutureTask futureTask = new FutureTask(new StubResultCallable(intent));
        runOnMainSync(futureTask);
        try {
            return (Instrumentation.ActivityResult) futureTask.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(String.format("Could not retrieve stub result for intent %s", new Object[]{intent}), e);
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e2);
        }
    }

    public boolean onException(Object obj, Throwable th) {
        Log.e(TAG, String.format("Exception encountered by: %s. Dumping thread state to outputs and pining for the fjords.", new Object[]{obj}), th);
        dumpThreadStateToOutputs("ThreadState-onException.txt");
        Log.e(TAG, "Dying now...");
        return super.onException(obj, th);
    }

    /* access modifiers changed from: protected */
    public void dumpThreadStateToOutputs(String str) {
        Log.e("THREAD_STATE", getThreadState());
    }

    /* access modifiers changed from: protected */
    public String getThreadState() {
        Set<Map.Entry<Thread, StackTraceElement[]>> entrySet = Thread.getAllStackTraces().entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry next : entrySet) {
            StringBuilder sb2 = new StringBuilder("  ");
            sb2.append(next.getKey());
            sb2.append("\n");
            for (StackTraceElement stackTraceElement : (StackTraceElement[]) next.getValue()) {
                sb2.append("    ");
                sb2.append(stackTraceElement.toString());
                sb2.append("\n");
            }
            sb2.append("\n");
            sb.append(sb2.toString());
        }
        return sb.toString();
    }

    public void callActivityOnDestroy(Activity activity) {
        super.callActivityOnDestroy(activity);
        this.lifecycleMonitor.signalLifecycleChange(Stage.DESTROYED, activity);
    }

    public void callActivityOnRestart(Activity activity) {
        super.callActivityOnRestart(activity);
        this.lifecycleMonitor.signalLifecycleChange(Stage.RESTARTED, activity);
    }

    public void callActivityOnCreate(Activity activity, Bundle bundle) {
        this.lifecycleMonitor.signalLifecycleChange(Stage.PRE_ON_CREATE, activity);
        super.callActivityOnCreate(activity, bundle);
        this.lifecycleMonitor.signalLifecycleChange(Stage.CREATED, activity);
    }

    public void callActivityOnStart(Activity activity) {
        this.startedActivityCounter.incrementAndGet();
        try {
            super.callActivityOnStart(activity);
            this.lifecycleMonitor.signalLifecycleChange(Stage.STARTED, activity);
        } catch (RuntimeException e) {
            this.startedActivityCounter.decrementAndGet();
            throw e;
        }
    }

    public void callActivityOnStop(Activity activity) {
        try {
            super.callActivityOnStop(activity);
            this.lifecycleMonitor.signalLifecycleChange(Stage.STOPPED, activity);
        } finally {
            this.startedActivityCounter.decrementAndGet();
        }
    }

    public void callActivityOnResume(Activity activity) {
        super.callActivityOnResume(activity);
        this.lifecycleMonitor.signalLifecycleChange(Stage.RESUMED, activity);
    }

    public void callActivityOnPause(Activity activity) {
        super.callActivityOnPause(activity);
        this.lifecycleMonitor.signalLifecycleChange(Stage.PAUSED, activity);
    }

    public Activity newActivity(Class<?> cls, Context context, IBinder iBinder, Application application, Intent intent, ActivityInfo activityInfo, CharSequence charSequence, Activity activity, String str, Object obj) throws InstantiationException, IllegalAccessException {
        String name = cls.getPackage().getName();
        String packageName = context.getPackageName();
        ComponentName component = intent.getComponent();
        if (!packageName.equals(component.getPackageName()) && name.equals(component.getPackageName())) {
            intent.setComponent(new ComponentName(packageName, component.getClassName()));
        }
        return super.newActivity(cls, context, iBinder, application, intent, activityInfo, charSequence, activity, str, obj);
    }

    public Activity newActivity(ClassLoader classLoader, String str, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (this.interceptingActivityFactory.shouldIntercept(classLoader, str, intent)) {
            return this.interceptingActivityFactory.create(classLoader, str, intent);
        }
        return super.newActivity(classLoader, str, intent);
    }

    public void interceptActivityUsing(InterceptingActivityFactory interceptingActivityFactory2) {
        Checks.checkNotNull(interceptingActivityFactory2);
        this.interceptingActivityFactory = interceptingActivityFactory2;
    }

    public void useDefaultInterceptingActivityFactory() {
        this.interceptingActivityFactory = new DefaultInterceptingActivityFactory();
    }

    private void tryLoadingJsBridge(final String str) {
        Objects.requireNonNull(str, "JsBridge class name cannot be null!");
        runOnMainSync(new Runnable() {
            public void run() {
                try {
                    Class.forName(str).getDeclaredMethod("installBridge", new Class[0]).invoke((Object) null, new Object[0]);
                    MonitoringInstrumentation.this.isJsBridgeLoaded.set(true);
                } catch (ClassNotFoundException | NoSuchMethodException unused) {
                    Log.i(MonitoringInstrumentation.TAG, "No JSBridge.");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("JSbridge is available at runtime, but calling it failed.", e);
                }
            }
        });
    }

    public class ActivityFinisher implements Runnable {
        public ActivityFinisher() {
        }

        public void run() {
            ArrayList<Activity> arrayList = new ArrayList<>();
            Iterator it = EnumSet.range(Stage.CREATED, Stage.STOPPED).iterator();
            while (it.hasNext()) {
                arrayList.addAll(MonitoringInstrumentation.this.lifecycleMonitor.getActivitiesInStage((Stage) it.next()));
            }
            if (arrayList.size() > 0) {
                int size = arrayList.size();
                StringBuilder sb = new StringBuilder(60);
                sb.append("Activities that are still in CREATED to STOPPED: ");
                sb.append(size);
                Log.i(MonitoringInstrumentation.TAG, sb.toString());
            }
            for (Activity activity : arrayList) {
                if (!activity.isFinishing()) {
                    try {
                        String valueOf = String.valueOf(activity);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 20);
                        sb2.append("Finishing activity: ");
                        sb2.append(valueOf);
                        Log.i(MonitoringInstrumentation.TAG, sb2.toString());
                        activity.finish();
                    } catch (RuntimeException e) {
                        Log.e(MonitoringInstrumentation.TAG, "Failed to finish activity.", e);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public boolean isPrimaryInstrProcess(String str) {
        return isPrimaryInstrProcess();
    }

    /* access modifiers changed from: protected */
    public final boolean isPrimaryInstrProcess() {
        return isOriginalInstrumentationProcess();
    }

    private boolean isHostingProcess(String str, ProcSummary procSummary) {
        int length = str.length();
        int length2 = procSummary.cmdline.length();
        if (length == length2) {
            return str.equals(procSummary.cmdline);
        }
        if (length < length2 || !str.startsWith(procSummary.cmdline) || !str.endsWith(procSummary.name)) {
            return false;
        }
        String valueOf = String.valueOf(procSummary);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 165 + String.valueOf(str).length());
        sb.append("Use smaller processNames in AndroidManifest.xml. Long names are truncated. This process's cmdline is a prefix of the processName and suffix of comm - assuming: ");
        sb.append(valueOf);
        sb.append(" is: ");
        sb.append(str);
        Log.w(TAG, sb.toString());
        return true;
    }

    private boolean isOriginalInstrumentationProcess() {
        Boolean bool = this.isOriginalInstr;
        if (bool == null) {
            bool = Boolean.valueOf(isOriginalUncached());
            this.isOriginalInstr = bool;
        }
        return bool.booleanValue();
    }

    private List<String> getTargetProcessValues() {
        if (Build.VERSION.SDK_INT < 26) {
            return Collections.emptyList();
        }
        try {
            String str = getContext().getPackageManager().getInstrumentationInfo(getComponentName(), 0).targetProcesses;
            if (str == null) {
                str = "";
            }
            String trim = str.trim();
            if (trim.length() == 0) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (String trim2 : trim.split(",", -1)) {
                String trim3 = trim2.trim();
                if (trim3.length() > 0) {
                    arrayList.add(trim3);
                }
            }
            return arrayList;
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf = String.valueOf(getComponentName());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 25);
            sb.append("Cannot locate ourselves: ");
            sb.append(valueOf);
            Log.wtf(TAG, sb.toString(), e);
            String valueOf2 = String.valueOf(getComponentName());
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 25);
            sb2.append("Cannot locate ourselves: ");
            sb2.append(valueOf2);
            throw new IllegalStateException(sb2.toString(), e);
        }
    }

    private boolean isOriginalUncached() {
        if (Build.VERSION.SDK_INT < 26) {
            return true;
        }
        List<String> targetProcessValues = getTargetProcessValues();
        if (targetProcessValues.isEmpty()) {
            return true;
        }
        boolean equals = "*".equals(targetProcessValues.get(0));
        if (targetProcessValues.size() == 1 && !equals) {
            return true;
        }
        try {
            ProcSummary summarize = ProcSummary.summarize("self");
            if (!equals) {
                return isHostingProcess(targetProcessValues.get(0), summarize);
            }
            String str = getTargetContext().getApplicationInfo().processName;
            if (str == null) {
                str = getTargetContext().getPackageName();
            }
            return isHostingProcess(str, summarize);
        } catch (ProcSummary.SummaryException e) {
            Log.w(TAG, "Could not list apps for this user, running in sandbox? Assuming primary", e);
            return false;
        }
    }
}
