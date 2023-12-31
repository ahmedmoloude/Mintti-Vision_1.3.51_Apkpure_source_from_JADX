package p028io.reactivex.android.schedulers;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.Objects;
import java.util.concurrent.Callable;
import p028io.reactivex.Scheduler;
import p028io.reactivex.android.plugins.RxAndroidPlugins;

/* renamed from: io.reactivex.android.schedulers.AndroidSchedulers */
public final class AndroidSchedulers {
    private static final Scheduler MAIN_THREAD = RxAndroidPlugins.initMainThreadScheduler(new Callable<Scheduler>() {
        public Scheduler call() throws Exception {
            return MainHolder.DEFAULT;
        }
    });

    /* renamed from: io.reactivex.android.schedulers.AndroidSchedulers$MainHolder */
    private static final class MainHolder {
        static final Scheduler DEFAULT = new HandlerScheduler(new Handler(Looper.getMainLooper()), false);

        private MainHolder() {
        }
    }

    public static Scheduler mainThread() {
        return RxAndroidPlugins.onMainThreadScheduler(MAIN_THREAD);
    }

    public static Scheduler from(Looper looper) {
        return from(looper, false);
    }

    public static Scheduler from(Looper looper, boolean z) {
        Objects.requireNonNull(looper, "looper == null");
        if (Build.VERSION.SDK_INT < 16) {
            z = false;
        } else if (z && Build.VERSION.SDK_INT < 22) {
            Message obtain = Message.obtain();
            try {
                obtain.setAsynchronous(true);
            } catch (NoSuchMethodError unused) {
                z = false;
            }
            obtain.recycle();
        }
        return new HandlerScheduler(new Handler(looper), z);
    }

    private AndroidSchedulers() {
        throw new AssertionError("No instances.");
    }
}
