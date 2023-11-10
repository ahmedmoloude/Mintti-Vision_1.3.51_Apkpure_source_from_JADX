package androidx.test.espresso.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.base.Interrogator;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

class LooperIdlingResourceInterrogationHandler implements Interrogator.InterrogationHandler<Void>, IdlingResource {
    private static final ConcurrentHashMap<String, LooperIdlingResourceInterrogationHandler> insts = new ConcurrentHashMap<>();

    /* renamed from: cb */
    private volatile IdlingResource.ResourceCallback f196cb = null;
    private volatile boolean idle = true;
    private final String name;
    /* access modifiers changed from: private */
    public volatile MessageQueue queue = null;
    private final Interrogator.QueueInterrogationHandler<Boolean> queueHasNewTasks = new Interrogator.QueueInterrogationHandler<Boolean>(this) {
        private Boolean hasTasks = Boolean.FALSE;

        public boolean barrierUp() {
            this.hasTasks = Boolean.TRUE;
            return false;
        }

        public Boolean get() {
            return this.hasTasks;
        }

        public boolean queueEmpty() {
            this.hasTasks = Boolean.FALSE;
            return false;
        }

        public boolean taskDueLong() {
            this.hasTasks = Boolean.FALSE;
            return false;
        }

        public boolean taskDueSoon() {
            this.hasTasks = Boolean.TRUE;
            return false;
        }
    };
    /* access modifiers changed from: private */
    public volatile boolean started = false;

    private LooperIdlingResourceInterrogationHandler(String str) {
        this.name = str;
    }

    static LooperIdlingResourceInterrogationHandler forLooper(Looper looper) {
        String format = String.format(Locale.ROOT, "LooperIdlingResource-%s-%s", new Object[]{Long.valueOf(looper.getThread().getId()), looper.getThread().getName()});
        LooperIdlingResourceInterrogationHandler looperIdlingResourceInterrogationHandler = new LooperIdlingResourceInterrogationHandler(format);
        LooperIdlingResourceInterrogationHandler putIfAbsent = insts.putIfAbsent(format, looperIdlingResourceInterrogationHandler);
        if (putIfAbsent != null) {
            return putIfAbsent;
        }
        new Handler(looper).post(new Runnable() {
            public void run() {
                MessageQueue unused = LooperIdlingResourceInterrogationHandler.this.queue = Looper.myQueue();
                boolean unused2 = LooperIdlingResourceInterrogationHandler.this.started = true;
                Interrogator.loopAndInterrogate(LooperIdlingResourceInterrogationHandler.this);
            }
        });
        return looperIdlingResourceInterrogationHandler;
    }

    private void transitionToIdle() {
        this.idle = true;
        if (this.f196cb != null) {
            this.f196cb.onTransitionToIdle();
        }
    }

    public boolean barrierUp() {
        this.idle = false;
        return true;
    }

    public boolean beforeTaskDispatch() {
        this.idle = false;
        return true;
    }

    public Void get() {
        return null;
    }

    public String getMessage() {
        return null;
    }

    public String getName() {
        return this.name;
    }

    public boolean isIdleNow() {
        if (this.started && this.idle) {
            return Boolean.FALSE.equals(Interrogator.peekAtQueueState(this.queue, this.queueHasNewTasks));
        }
        return false;
    }

    public boolean queueEmpty() {
        transitionToIdle();
        return true;
    }

    public void quitting() {
        transitionToIdle();
    }

    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback resourceCallback) {
        this.f196cb = resourceCallback;
    }

    public void setMessage(Message message) {
    }

    public boolean taskDueLong() {
        transitionToIdle();
        return true;
    }

    public boolean taskDueSoon() {
        this.idle = false;
        return true;
    }
}
