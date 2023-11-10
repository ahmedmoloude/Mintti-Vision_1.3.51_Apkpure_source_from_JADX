package androidx.test.internal.runner.junit4.statement;

import android.os.Looper;
import android.util.Log;
import androidx.test.platform.app.InstrumentationRegistry;
import java.lang.annotation.Annotation;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class UiThreadStatement extends Statement {
    private static final String TAG = "UiThreadStatement";
    /* access modifiers changed from: private */
    public final Statement base;
    private final boolean runOnUiThread;

    public UiThreadStatement(Statement statement, boolean z) {
        this.base = statement;
        this.runOnUiThread = z;
    }

    public boolean isRunOnUiThread() {
        return this.runOnUiThread;
    }

    public void evaluate() throws Throwable {
        if (this.runOnUiThread) {
            final AtomicReference atomicReference = new AtomicReference();
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        UiThreadStatement.this.base.evaluate();
                    } catch (Throwable th) {
                        atomicReference.set(th);
                    }
                }
            });
            Throwable th = (Throwable) atomicReference.get();
            if (th != null) {
                throw th;
            }
            return;
        }
        this.base.evaluate();
    }

    public static boolean shouldRunOnUiThread(FrameworkMethod frameworkMethod) {
        Class<? extends Annotation> loadUiThreadClass = loadUiThreadClass("android.test.UiThreadTest");
        if (hasAnnotation(frameworkMethod, loadUiThreadClass)) {
            return true;
        }
        Class<? extends Annotation> loadUiThreadClass2 = loadUiThreadClass("androidx.test.annotation.UiThreadTest");
        if (hasAnnotation(frameworkMethod, loadUiThreadClass) || hasAnnotation(frameworkMethod, loadUiThreadClass2)) {
            return true;
        }
        return false;
    }

    private static boolean hasAnnotation(FrameworkMethod frameworkMethod, Class<? extends Annotation> cls) {
        return cls != null && (frameworkMethod.getAnnotation(cls) != null || classHasAnnotation(frameworkMethod, cls));
    }

    private static boolean classHasAnnotation(FrameworkMethod frameworkMethod, Class<? extends Annotation> cls) {
        Class declaringClass = frameworkMethod.getDeclaringClass();
        for (Class isAnnotationPresent : declaringClass.getInterfaces()) {
            if (isAnnotationPresent.isAnnotationPresent(cls)) {
                return true;
            }
        }
        while (declaringClass != null) {
            if (declaringClass.isAnnotationPresent(cls)) {
                return true;
            }
            declaringClass = declaringClass.getSuperclass();
        }
        return false;
    }

    private static Class<? extends Annotation> loadUiThreadClass(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static void runOnUiThread(Runnable runnable) throws Throwable {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w(TAG, "Already on the UI thread, this method should not be called from the main application thread");
            runnable.run();
            return;
        }
        FutureTask futureTask = new FutureTask(runnable, (Object) null);
        InstrumentationRegistry.getInstrumentation().runOnMainSync(futureTask);
        try {
            futureTask.get();
        } catch (ExecutionException e) {
            throw e.getCause();
        }
    }
}
