package androidx.test.espresso.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingPolicy;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.ImmutableList;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public final class IdlingResourceRegistry {
    private static final int DYNAMIC_RESOURCE_HAS_IDLED = 1;
    private static final int IDLE_WARNING_REACHED = 3;
    /* access modifiers changed from: private */
    public static final IdleNotificationCallback NO_OP_CALLBACK = new IdleNotificationCallback() {
        public void allResourcesIdle() {
        }

        public void resourcesHaveTimedOut(List<String> list) {
        }

        public void resourcesStillBusyWarning(List<String> list) {
        }
    };
    private static final int POSSIBLE_RACE_CONDITION_DETECTED = 4;
    /* access modifiers changed from: private */
    public static final String TAG = "IdlingResourceRegistry";
    /* access modifiers changed from: private */
    public static final Object TIMEOUT_MESSAGE_TAG = new Object();
    private static final int TIMEOUT_OCCURRED = 2;
    private final Dispatcher dispatcher;
    /* access modifiers changed from: private */
    public final Handler handler;
    /* access modifiers changed from: private */
    public IdleNotificationCallback idleNotificationCallback = NO_OP_CALLBACK;
    /* access modifiers changed from: private */
    public final List<IdlingState> idlingStates = new ArrayList();
    private final Looper looper;

    private class Dispatcher implements Handler.Callback {
        private Dispatcher() {
        }

        /* access modifiers changed from: private */
        public void deregister() {
            IdlingResourceRegistry.this.handler.removeCallbacksAndMessages(IdlingResourceRegistry.TIMEOUT_MESSAGE_TAG);
            IdleNotificationCallback unused = IdlingResourceRegistry.this.idleNotificationCallback = IdlingResourceRegistry.NO_OP_CALLBACK;
        }

        private void handleRaceCondition(Message message) {
            for (IdlingState idlingState : (List) message.obj) {
                if (!idlingState.idle) {
                    throw new IllegalStateException(String.format(Locale.ROOT, "Resource %s isIdleNow() is returning true, but a message indicating that the resource has transitioned from busy to idle was never sent.", new Object[]{idlingState.resource.getName()}));
                }
            }
        }

        private void handleResourceIdled(Message message) {
            IdlingState idlingState = (IdlingState) message.obj;
            idlingState.idle = true;
            boolean z = true;
            boolean z2 = true;
            for (IdlingState idlingState2 : IdlingResourceRegistry.this.idlingStates) {
                z = z && idlingState2.idle;
                if (!z2 && !z) {
                    break;
                } else if (z2 && idlingState2 == idlingState) {
                    z2 = false;
                }
            }
            if (z2) {
                String access$400 = IdlingResourceRegistry.TAG;
                String valueOf = String.valueOf(idlingState.resource);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 45);
                sb.append("Ignoring message from unregistered resource: ");
                sb.append(valueOf);
                Log.i(access$400, sb.toString());
            } else if (z) {
                try {
                    IdlingResourceRegistry.this.idleNotificationCallback.allResourcesIdle();
                } finally {
                    deregister();
                }
            }
        }

        private void handleTimeout() {
            List access$700 = IdlingResourceRegistry.this.getBusyResources();
            if (access$700 == null) {
                IdlingResourceRegistry.this.handler.sendMessage(IdlingResourceRegistry.this.handler.obtainMessage(2, IdlingResourceRegistry.TIMEOUT_MESSAGE_TAG));
                return;
            }
            try {
                IdlingResourceRegistry.this.idleNotificationCallback.resourcesHaveTimedOut(access$700);
            } finally {
                deregister();
            }
        }

        private void handleTimeoutWarning() {
            List access$700 = IdlingResourceRegistry.this.getBusyResources();
            if (access$700 == null) {
                IdlingResourceRegistry.this.handler.sendMessage(IdlingResourceRegistry.this.handler.obtainMessage(3, IdlingResourceRegistry.TIMEOUT_MESSAGE_TAG));
                return;
            }
            IdlingPolicy dynamicIdlingResourceWarningPolicy = IdlingPolicies.getDynamicIdlingResourceWarningPolicy();
            IdlingResourceRegistry.this.idleNotificationCallback.resourcesStillBusyWarning(access$700);
            IdlingResourceRegistry.this.handler.sendMessageDelayed(IdlingResourceRegistry.this.handler.obtainMessage(3, IdlingResourceRegistry.TIMEOUT_MESSAGE_TAG), dynamicIdlingResourceWarningPolicy.getIdleTimeoutUnit().toMillis(dynamicIdlingResourceWarningPolicy.getIdleTimeout()));
        }

        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                handleResourceIdled(message);
            } else if (i == 2) {
                handleTimeout();
            } else if (i == 3) {
                handleTimeoutWarning();
            } else if (i != 4) {
                String access$400 = IdlingResourceRegistry.TAG;
                String valueOf = String.valueOf(message);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
                sb.append("Unknown message type: ");
                sb.append(valueOf);
                Log.w(access$400, sb.toString());
                return false;
            } else {
                handleRaceCondition(message);
            }
            return true;
        }
    }

    interface IdleNotificationCallback {
        void allResourcesIdle();

        void resourcesHaveTimedOut(List<String> list);

        void resourcesStillBusyWarning(List<String> list);
    }

    private static class IdlingState implements IdlingResource.ResourceCallback {
        final Handler handler;
        boolean idle;
        final IdlingResource resource;

        private IdlingState(IdlingResource idlingResource, Handler handler2) {
            this.resource = idlingResource;
            this.handler = handler2;
        }

        /* access modifiers changed from: private */
        public void registerSelf() {
            this.resource.registerIdleTransitionCallback(this);
            this.idle = this.resource.isIdleNow();
        }

        public void onTransitionToIdle() {
            Message obtainMessage = this.handler.obtainMessage(1);
            obtainMessage.obj = this;
            this.handler.sendMessage(obtainMessage);
        }
    }

    public IdlingResourceRegistry(Looper looper2) {
        this.looper = looper2;
        Dispatcher dispatcher2 = new Dispatcher();
        this.dispatcher = dispatcher2;
        this.handler = new Handler(looper2, dispatcher2);
    }

    /* access modifiers changed from: private */
    public List<String> getBusyResources() {
        ArrayList newArrayList = Lists.newArrayList();
        ArrayList newArrayList2 = Lists.newArrayList();
        for (IdlingState next : this.idlingStates) {
            if (!next.idle) {
                if (next.resource.isIdleNow()) {
                    newArrayList2.add(next);
                } else {
                    newArrayList.add(next.resource.getName());
                }
            }
        }
        if (newArrayList2.isEmpty()) {
            return newArrayList;
        }
        Message obtainMessage = this.handler.obtainMessage(4, TIMEOUT_MESSAGE_TAG);
        obtainMessage.obj = newArrayList2;
        this.handler.sendMessage(obtainMessage);
        return null;
    }

    private void logDuplicateRegistrationError(IdlingResource idlingResource, IdlingResource idlingResource2) {
        Log.e(TAG, String.format(Locale.ROOT, "Attempted to register resource with same names: %s. R1: %s R2: %s.\nDuplicate resource registration will be ignored.", new Object[]{idlingResource.getName(), idlingResource, idlingResource2}));
    }

    private <T> T runSynchronouslyOnMainThread(Callable<T> callable) {
        FutureTask futureTask = new FutureTask(callable);
        this.handler.post(futureTask);
        try {
            return futureTask.get();
        } catch (CancellationException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e2) {
            throw new RuntimeException(e2);
        } catch (InterruptedException e3) {
            throw new RuntimeException(e3);
        }
    }

    private void scheduleTimeoutMessages() {
        IdlingPolicy dynamicIdlingResourceWarningPolicy = IdlingPolicies.getDynamicIdlingResourceWarningPolicy();
        Handler handler2 = this.handler;
        Object obj = TIMEOUT_MESSAGE_TAG;
        this.handler.sendMessageDelayed(handler2.obtainMessage(3, obj), dynamicIdlingResourceWarningPolicy.getIdleTimeoutUnit().toMillis(dynamicIdlingResourceWarningPolicy.getIdleTimeout()));
        Message obtainMessage = this.handler.obtainMessage(2, obj);
        IdlingPolicy dynamicIdlingResourceErrorPolicy = IdlingPolicies.getDynamicIdlingResourceErrorPolicy();
        this.handler.sendMessageDelayed(obtainMessage, dynamicIdlingResourceErrorPolicy.getIdleTimeoutUnit().toMillis(dynamicIdlingResourceErrorPolicy.getIdleTimeout()));
    }

    /* access modifiers changed from: package-private */
    public boolean allResourcesAreIdle() {
        Preconditions.checkState(Looper.myLooper() == this.looper);
        for (IdlingState next : this.idlingStates) {
            if (next.idle) {
                next.idle = next.resource.isIdleNow();
            }
            if (!next.idle) {
                return false;
            }
        }
        String str = TAG;
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "All idling resources are idle.");
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public IdleNotifier<IdleNotificationCallback> asIdleNotifier() {
        return new IdleNotifier<IdleNotificationCallback>() {
            public void cancelCallback() {
                IdlingResourceRegistry.this.cancelIdleMonitor();
            }

            public boolean isIdleNow() {
                return IdlingResourceRegistry.this.allResourcesAreIdle();
            }

            public void registerNotificationCallback(IdleNotificationCallback idleNotificationCallback) {
                IdlingResourceRegistry.this.notifyWhenAllResourcesAreIdle(idleNotificationCallback);
            }
        };
    }

    /* access modifiers changed from: package-private */
    public void cancelIdleMonitor() {
        this.dispatcher.deregister();
    }

    public List<IdlingResource> getResources() {
        if (Looper.myLooper() != this.looper) {
            return (List) runSynchronouslyOnMainThread(new Callable<List<IdlingResource>>() {
                public List<IdlingResource> call() {
                    return IdlingResourceRegistry.this.getResources();
                }
            });
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        for (IdlingState idlingState : this.idlingStates) {
            builder.add(idlingState.resource);
        }
        return builder.build();
    }

    /* access modifiers changed from: package-private */
    public void notifyWhenAllResourcesAreIdle(IdleNotificationCallback idleNotificationCallback2) {
        Preconditions.checkNotNull(idleNotificationCallback2);
        boolean z = true;
        Preconditions.checkState(Looper.myLooper() == this.looper);
        if (this.idleNotificationCallback != NO_OP_CALLBACK) {
            z = false;
        }
        Preconditions.checkState(z, "Callback has already been registered.");
        if (allResourcesAreIdle()) {
            idleNotificationCallback2.allResourcesIdle();
            return;
        }
        this.idleNotificationCallback = idleNotificationCallback2;
        scheduleTimeoutMessages();
    }

    public void registerLooper(Looper looper2, boolean z) {
        Preconditions.checkNotNull(looper2);
        Preconditions.checkArgument(Looper.getMainLooper() != looper2, "Not intended for use with main looper!");
        registerResources(Lists.newArrayList((E[]) new LooperIdlingResourceInterrogationHandler[]{LooperIdlingResourceInterrogationHandler.forLooper(looper2)}));
    }

    public boolean registerResources(final List<? extends IdlingResource> list) {
        boolean z;
        if (Looper.myLooper() != this.looper) {
            return ((Boolean) runSynchronouslyOnMainThread(new Callable<Boolean>() {
                public Boolean call() {
                    return Boolean.valueOf(IdlingResourceRegistry.this.registerResources(list));
                }
            })).booleanValue();
        }
        boolean z2 = true;
        for (IdlingResource idlingResource : list) {
            Preconditions.checkNotNull(idlingResource.getName(), "IdlingResource.getName() should not be null");
            Iterator<IdlingState> it = this.idlingStates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                IdlingState next = it.next();
                if (idlingResource.getName().equals(next.resource.getName())) {
                    logDuplicateRegistrationError(idlingResource, next.resource);
                    z = true;
                    break;
                }
            }
            if (!z) {
                IdlingState idlingState = new IdlingState(idlingResource, this.handler);
                this.idlingStates.add(idlingState);
                idlingState.registerSelf();
            } else {
                z2 = false;
            }
        }
        return z2;
    }

    public void sync(final Iterable<IdlingResource> iterable, final Iterable<Looper> iterable2) {
        if (Looper.myLooper() != this.looper) {
            runSynchronouslyOnMainThread(new Callable<Void>() {
                public Void call() {
                    IdlingResourceRegistry.this.sync(iterable, iterable2);
                    return null;
                }
            });
            return;
        }
        HashMap hashMap = new HashMap();
        for (IdlingResource next : iterable) {
            if (hashMap.containsKey(next.getName())) {
                logDuplicateRegistrationError(next, (IdlingResource) hashMap.get(next.getName()));
            } else {
                hashMap.put(next.getName(), next);
            }
        }
        for (Looper forLooper : iterable2) {
            LooperIdlingResourceInterrogationHandler forLooper2 = LooperIdlingResourceInterrogationHandler.forLooper(forLooper);
            if (hashMap.containsKey(forLooper2.getName())) {
                logDuplicateRegistrationError(forLooper2, (IdlingResource) hashMap.get(forLooper2.getName()));
            } else {
                hashMap.put(forLooper2.getName(), forLooper2);
            }
        }
        ArrayList arrayList = new ArrayList();
        for (IdlingState next2 : this.idlingStates) {
            IdlingResource idlingResource = (IdlingResource) hashMap.remove(next2.resource.getName());
            if (idlingResource == null) {
                arrayList.add(next2.resource);
            } else if (next2.resource != idlingResource) {
                arrayList.add(next2.resource);
                hashMap.put(idlingResource.getName(), idlingResource);
            }
        }
        unregisterResources(arrayList);
        registerResources(Lists.newArrayList(hashMap.values()));
    }

    public boolean unregisterResources(final List<? extends IdlingResource> list) {
        boolean z;
        if (Looper.myLooper() != this.looper) {
            return ((Boolean) runSynchronouslyOnMainThread(new Callable<Boolean>() {
                public Boolean call() {
                    return Boolean.valueOf(IdlingResourceRegistry.this.unregisterResources(list));
                }
            })).booleanValue();
        }
        boolean z2 = true;
        for (IdlingResource idlingResource : list) {
            int i = 0;
            while (true) {
                if (i >= this.idlingStates.size()) {
                    z = false;
                    break;
                } else if (this.idlingStates.get(i).resource.getName().equals(idlingResource.getName())) {
                    this.idlingStates.remove(i);
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
            if (!z) {
                Log.e(TAG, String.format(Locale.ROOT, "Attempted to unregister resource that is not registered: '%s'. Resource list: %s", new Object[]{idlingResource.getName(), getResources()}));
                z2 = false;
            }
        }
        return z2;
    }
}
