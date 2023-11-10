package p035me.yokeyword.fragmentation.queue;

import android.os.Handler;
import android.os.Looper;
import java.util.LinkedList;
import java.util.Queue;
import p035me.yokeyword.fragmentation.ISupportFragment;
import p035me.yokeyword.fragmentation.SupportHelper;

/* renamed from: me.yokeyword.fragmentation.queue.ActionQueue */
public class ActionQueue {
    private Handler mMainHandler;
    /* access modifiers changed from: private */
    public Queue<Action> mQueue = new LinkedList();

    public ActionQueue(Handler handler) {
        this.mMainHandler = handler;
    }

    public void enqueue(final Action action) {
        if (!isThrottleBACK(action)) {
            if (action.action == 4 && this.mQueue.isEmpty() && Thread.currentThread() == Looper.getMainLooper().getThread()) {
                action.run();
            } else {
                this.mMainHandler.post(new Runnable() {
                    public void run() {
                        ActionQueue.this.enqueueAction(action);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void enqueueAction(Action action) {
        this.mQueue.add(action);
        if (this.mQueue.size() == 1) {
            handleAction();
        }
    }

    /* access modifiers changed from: private */
    public void handleAction() {
        if (!this.mQueue.isEmpty()) {
            Action peek = this.mQueue.peek();
            peek.run();
            executeNextAction(peek);
        }
    }

    private void executeNextAction(Action action) {
        long j;
        if (action.action == 1) {
            ISupportFragment backStackTopFragment = SupportHelper.getBackStackTopFragment(action.fragmentManager);
            if (backStackTopFragment == null) {
                j = 300;
            } else {
                j = backStackTopFragment.getSupportDelegate().getExitAnimDuration();
            }
            action.duration = j;
        }
        this.mMainHandler.postDelayed(new Runnable() {
            public void run() {
                ActionQueue.this.mQueue.poll();
                ActionQueue.this.handleAction();
            }
        }, action.duration);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0005, code lost:
        r2 = r1.mQueue.peek();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isThrottleBACK(p035me.yokeyword.fragmentation.queue.Action r2) {
        /*
            r1 = this;
            int r2 = r2.action
            r0 = 3
            if (r2 != r0) goto L_0x0015
            java.util.Queue<me.yokeyword.fragmentation.queue.Action> r2 = r1.mQueue
            java.lang.Object r2 = r2.peek()
            me.yokeyword.fragmentation.queue.Action r2 = (p035me.yokeyword.fragmentation.queue.Action) r2
            if (r2 == 0) goto L_0x0015
            int r2 = r2.action
            r0 = 1
            if (r2 != r0) goto L_0x0015
            return r0
        L_0x0015:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p035me.yokeyword.fragmentation.queue.ActionQueue.isThrottleBACK(me.yokeyword.fragmentation.queue.Action):boolean");
    }
}
