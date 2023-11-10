package androidx.test.internal.runner;

import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import androidx.test.internal.util.Checks;
import androidx.test.internal.util.LogUtil;
import androidx.test.internal.util.ParcelableIBinder;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.MonitoringInstrumentation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class InstrumentationConnection {
    public static final String BROADCAST_FILTER = "androidx.test.runner.InstrumentationConnection.event";
    static final String BUNDLE_BR_NEW_BINDER = "new_instrumentation_binder";
    private static final String BUNDLE_KEY_CLIENTS = "instr_clients";
    private static final String BUNDLE_KEY_CLIENT_MESSENGER = "instr_client_msgr";
    private static final String BUNDLE_KEY_CLIENT_TYPE = "instr_client_type";
    private static final String BUNDLE_KEY_UUID = "instr_uuid";
    private static final InstrumentationConnection DEFAULT_INSTANCE = new InstrumentationConnection(InstrumentationRegistry.getInstrumentation().getTargetContext());
    static final int MSG_ADD_CLIENTS_IN_BUNDLE = 6;
    static final int MSG_ADD_INSTRUMENTATION = 4;
    private static final int MSG_HANDLE_INSTRUMENTATION_FROM_BROADCAST = 3;
    private static final int MSG_PERFORM_CLEANUP = 11;
    private static final int MSG_PERFORM_CLEANUP_FINISHED = 12;
    private static final int MSG_REG_CLIENT = 8;
    private static final int MSG_REMOTE_ADD_CLIENT = 0;
    static final int MSG_REMOTE_CLEANUP_REQUEST = 10;
    private static final int MSG_REMOTE_REMOVE_CLIENT = 1;
    private static final int MSG_REMOVE_CLIENTS_IN_BUNDLE = 7;
    private static final int MSG_REMOVE_INSTRUMENTATION = 5;
    private static final int MSG_TERMINATE = 2;
    private static final int MSG_UN_REG_CLIENT = 9;
    private static final String TAG = "InstrConnection";
    /* access modifiers changed from: private */
    public static MonitoringInstrumentation.ActivityFinisher activityFinisher;
    /* access modifiers changed from: private */
    public static Instrumentation instrumentation;
    IncomingHandler incomingHandler;
    final BroadcastReceiver messengerReceiver = new MessengerReceiver();
    private Context targetContext;

    InstrumentationConnection(Context context) {
        this.targetContext = (Context) Checks.checkNotNull(context, "Context can't be null");
    }

    public static InstrumentationConnection getInstance() {
        return DEFAULT_INSTANCE;
    }

    public synchronized void init(Instrumentation instrumentation2, MonitoringInstrumentation.ActivityFinisher activityFinisher2) {
        LogUtil.logDebugWithProcess(TAG, "init", new Object[0]);
        if (this.incomingHandler == null) {
            instrumentation = instrumentation2;
            activityFinisher = activityFinisher2;
            HandlerThread handlerThread = new HandlerThread("InstrumentationConnectionThread");
            handlerThread.start();
            this.incomingHandler = new IncomingHandler(handlerThread.getLooper());
            Intent intent = new Intent(BROADCAST_FILTER);
            Bundle bundle = new Bundle();
            bundle.putParcelable(BUNDLE_BR_NEW_BINDER, new ParcelableIBinder(this.incomingHandler.messengerHandler.getBinder()));
            intent.putExtra(BUNDLE_BR_NEW_BINDER, bundle);
            try {
                this.targetContext.sendBroadcast(intent);
                this.targetContext.registerReceiver(this.messengerReceiver, new IntentFilter(BROADCAST_FILTER));
            } catch (SecurityException unused) {
                Log.i(TAG, "Could not send broadcast or register receiver (isolatedProcess?)");
            }
        }
        return;
    }

    public synchronized void terminate() {
        LogUtil.logDebugWithProcess(TAG, "Terminate is called", new Object[0]);
        IncomingHandler incomingHandler2 = this.incomingHandler;
        if (incomingHandler2 != null) {
            Object unused = incomingHandler2.runSyncTask(new Callable<Void>() {
                public Void call() {
                    InstrumentationConnection.this.incomingHandler.doDie();
                    return null;
                }
            });
            this.targetContext.unregisterReceiver(this.messengerReceiver);
            this.incomingHandler = null;
        }
    }

    public synchronized void requestRemoteInstancesActivityCleanup() {
        IncomingHandler incomingHandler2;
        Checks.checkState(this.incomingHandler != null, "Instrumentation Connection in not yet initialized");
        UUID randomUUID = UUID.randomUUID();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.incomingHandler.associateLatch(randomUUID, countDownLatch);
        Message obtainMessage = this.incomingHandler.obtainMessage(10);
        obtainMessage.replyTo = this.incomingHandler.messengerHandler;
        Bundle data = obtainMessage.getData();
        data.putSerializable(BUNDLE_KEY_UUID, randomUUID);
        obtainMessage.setData(data);
        this.incomingHandler.sendMessage(obtainMessage);
        try {
            if (!countDownLatch.await(2, TimeUnit.SECONDS)) {
                String valueOf = String.valueOf(randomUUID);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 60);
                sb.append("Timed out while attempting to perform activity clean up for ");
                sb.append(valueOf);
                Log.w(TAG, sb.toString());
            }
            incomingHandler2 = this.incomingHandler;
        } catch (InterruptedException e) {
            try {
                String valueOf2 = String.valueOf(randomUUID);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 61);
                sb2.append("Interrupted while waiting for response from message with id: ");
                sb2.append(valueOf2);
                Log.e(TAG, sb2.toString(), e);
                incomingHandler2 = this.incomingHandler;
            } catch (Throwable th) {
                this.incomingHandler.disassociateLatch(randomUUID);
                throw th;
            }
        }
        incomingHandler2.disassociateLatch(randomUUID);
        return;
    }

    public synchronized void registerClient(String str, Messenger messenger) {
        Checks.checkState(this.incomingHandler != null, "Instrumentation Connection in not yet initialized");
        String valueOf = String.valueOf(str);
        Log.i(TAG, valueOf.length() != 0 ? "Register client of type: ".concat(valueOf) : new String("Register client of type: "));
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_CLIENT_TYPE, str);
        bundle.putParcelable(BUNDLE_KEY_CLIENT_MESSENGER, messenger);
        Message obtainMessage = this.incomingHandler.obtainMessage(8);
        obtainMessage.setData(bundle);
        this.incomingHandler.sendMessage(obtainMessage);
    }

    public synchronized Set<Messenger> getClientsForType(String str) {
        return this.incomingHandler.getClientsForType(str);
    }

    public synchronized void unregisterClient(String str, Messenger messenger) {
        Checks.checkState(this.incomingHandler != null, "Instrumentation Connection in not yet initialized");
        String valueOf = String.valueOf(str);
        Log.i(TAG, valueOf.length() != 0 ? "Unregister client of type: ".concat(valueOf) : new String("Unregister client of type: "));
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_CLIENT_TYPE, str);
        bundle.putParcelable(BUNDLE_KEY_CLIENT_MESSENGER, messenger);
        Message obtainMessage = this.incomingHandler.obtainMessage(9);
        obtainMessage.setData(bundle);
        this.incomingHandler.sendMessage(obtainMessage);
    }

    class MessengerReceiver extends BroadcastReceiver {
        MessengerReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "Broadcast received", new Object[0]);
            Bundle bundleExtra = intent.getBundleExtra(InstrumentationConnection.BUNDLE_BR_NEW_BINDER);
            if (bundleExtra == null) {
                Log.w(InstrumentationConnection.TAG, "Broadcast intent doesn't contain any extras, ignoring it..");
                return;
            }
            ParcelableIBinder parcelableIBinder = (ParcelableIBinder) bundleExtra.getParcelable(InstrumentationConnection.BUNDLE_BR_NEW_BINDER);
            if (parcelableIBinder != null) {
                Messenger messenger = new Messenger(parcelableIBinder.getIBinder());
                Message obtainMessage = InstrumentationConnection.this.incomingHandler.obtainMessage(3);
                obtainMessage.replyTo = messenger;
                InstrumentationConnection.this.incomingHandler.sendMessage(obtainMessage);
            }
        }
    }

    static class IncomingHandler extends Handler {
        /* access modifiers changed from: private */
        public final Map<UUID, CountDownLatch> latches = new HashMap();
        Messenger messengerHandler = new Messenger(this);
        Set<Messenger> otherInstrumentations = new HashSet();
        Map<String, Set<Messenger>> typedClients = new HashMap();

        public IncomingHandler(Looper looper) {
            super(looper);
            if (Looper.getMainLooper() == looper || Looper.myLooper() == looper) {
                throw new IllegalStateException("This handler should not be using the main thread looper nor the instrumentation thread looper.");
            }
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_REMOTE_ADD_CLIENT)", new Object[0]);
                    registerClient(message.getData().getString(InstrumentationConnection.BUNDLE_KEY_CLIENT_TYPE), (Messenger) message.getData().getParcelable(InstrumentationConnection.BUNDLE_KEY_CLIENT_MESSENGER));
                    return;
                case 1:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_REMOTE_REMOVE_CLIENT)", new Object[0]);
                    unregisterClient(message.getData().getString(InstrumentationConnection.BUNDLE_KEY_CLIENT_TYPE), message.replyTo);
                    return;
                case 2:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_TERMINATE)", new Object[0]);
                    doDie();
                    return;
                case 3:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_HANDLE_INSTRUMENTATION_FROM_BROADCAST)", new Object[0]);
                    if (this.otherInstrumentations.add(message.replyTo)) {
                        sendMessageWithReply(message.replyTo, 4, (Bundle) null);
                        return;
                    } else {
                        Log.w(InstrumentationConnection.TAG, "Broadcast with existing binder was received, ignoring it..");
                        return;
                    }
                case 4:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_ADD_INSTRUMENTATION)", new Object[0]);
                    if (this.otherInstrumentations.add(message.replyTo)) {
                        if (!this.typedClients.isEmpty()) {
                            sendMessageWithReply(message.replyTo, 6, (Bundle) null);
                        }
                        clientsRegistrationFromBundle(message.getData(), true);
                        return;
                    }
                    Log.w(InstrumentationConnection.TAG, "Message with existing binder was received, ignoring it..");
                    return;
                case 5:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_REMOVE_INSTRUMENTATION)", new Object[0]);
                    if (!this.otherInstrumentations.remove(message.replyTo)) {
                        Log.w(InstrumentationConnection.TAG, "Attempting to remove a non-existent binder!");
                        return;
                    }
                    return;
                case 6:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_ADD_CLIENTS_IN_BUNDLE)", new Object[0]);
                    clientsRegistrationFromBundle(message.getData(), true);
                    return;
                case 7:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_REMOVE_CLIENTS_IN_BUNDLE)", new Object[0]);
                    clientsRegistrationFromBundle(message.getData(), false);
                    return;
                case 8:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_REG_CLIENT)", new Object[0]);
                    registerClient(message.getData().getString(InstrumentationConnection.BUNDLE_KEY_CLIENT_TYPE), (Messenger) message.getData().getParcelable(InstrumentationConnection.BUNDLE_KEY_CLIENT_MESSENGER));
                    sendMessageToOtherInstr(0, message.getData());
                    return;
                case 9:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_UN_REG_CLIENT)", new Object[0]);
                    unregisterClient(message.getData().getString(InstrumentationConnection.BUNDLE_KEY_CLIENT_TYPE), (Messenger) message.getData().getParcelable(InstrumentationConnection.BUNDLE_KEY_CLIENT_MESSENGER));
                    sendMessageToOtherInstr(1, message.getData());
                    return;
                case 10:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_REMOTE_CLEANUP_REQUEST)", new Object[0]);
                    if (this.otherInstrumentations.isEmpty()) {
                        Message obtainMessage = obtainMessage(12);
                        obtainMessage.setData(message.getData());
                        sendMessage(obtainMessage);
                        return;
                    }
                    sendMessageToOtherInstr(11, message.getData());
                    return;
                case 11:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_PERFORM_CLEANUP)", new Object[0]);
                    InstrumentationConnection.instrumentation.runOnMainSync(InstrumentationConnection.activityFinisher);
                    sendMessageWithReply(message.replyTo, 12, message.getData());
                    return;
                case 12:
                    LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "handleMessage(MSG_PERFORM_CLEANUP_FINISHED)", new Object[0]);
                    notifyLatch((UUID) message.getData().getSerializable(InstrumentationConnection.BUNDLE_KEY_UUID));
                    return;
                default:
                    int i = message.what;
                    StringBuilder sb = new StringBuilder(42);
                    sb.append("Unknown message code received: ");
                    sb.append(i);
                    Log.w(InstrumentationConnection.TAG, sb.toString());
                    super.handleMessage(message);
                    return;
            }
        }

        private void notifyLatch(UUID uuid) {
            if (uuid == null || !this.latches.containsKey(uuid)) {
                String valueOf = String.valueOf(uuid);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 16);
                sb.append("Latch not found ");
                sb.append(valueOf);
                Log.w(InstrumentationConnection.TAG, sb.toString());
                return;
            }
            this.latches.get(uuid).countDown();
        }

        /* access modifiers changed from: private */
        public void associateLatch(final UUID uuid, final CountDownLatch countDownLatch) {
            runSyncTask(new Callable<Void>() {
                public Void call() {
                    IncomingHandler.this.latches.put(uuid, countDownLatch);
                    return null;
                }
            });
        }

        /* access modifiers changed from: private */
        public void disassociateLatch(final UUID uuid) {
            runSyncTask(new Callable<Void>() {
                public Void call() {
                    IncomingHandler.this.latches.remove(uuid);
                    return null;
                }
            });
        }

        /* access modifiers changed from: private */
        public <T> T runSyncTask(Callable<T> callable) {
            FutureTask futureTask = new FutureTask(callable);
            post(futureTask);
            try {
                return futureTask.get();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e.getCause());
            } catch (ExecutionException e2) {
                throw new IllegalStateException(e2.getCause());
            }
        }

        /* access modifiers changed from: private */
        public void doDie() {
            Log.i(InstrumentationConnection.TAG, "terminating process");
            sendMessageToOtherInstr(5, (Bundle) null);
            this.otherInstrumentations.clear();
            this.typedClients.clear();
            LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "quitting looper...", new Object[0]);
            getLooper().quit();
            LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "finishing instrumentation...", new Object[0]);
            InstrumentationConnection.instrumentation.finish(0, (Bundle) null);
            Instrumentation unused = InstrumentationConnection.instrumentation = null;
            MonitoringInstrumentation.ActivityFinisher unused2 = InstrumentationConnection.activityFinisher = null;
        }

        /* access modifiers changed from: private */
        public Set<Messenger> getClientsForType(final String str) {
            FutureTask futureTask = new FutureTask(new Callable<Set<Messenger>>() {
                public Set<Messenger> call() {
                    return IncomingHandler.this.typedClients.get(str);
                }
            });
            post(futureTask);
            try {
                return (Set) futureTask.get();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } catch (ExecutionException e2) {
                throw new IllegalStateException(e2.getCause());
            }
        }

        private void sendMessageWithReply(Messenger messenger, int i, Bundle bundle) {
            StringBuilder sb = new StringBuilder(45);
            sb.append("sendMessageWithReply type: ");
            sb.append(i);
            sb.append(" called");
            LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, sb.toString(), new Object[0]);
            Message obtainMessage = obtainMessage(i);
            obtainMessage.replyTo = this.messengerHandler;
            if (bundle != null) {
                obtainMessage.setData(bundle);
            }
            if (!this.typedClients.isEmpty()) {
                Bundle data = obtainMessage.getData();
                data.putStringArrayList(InstrumentationConnection.BUNDLE_KEY_CLIENTS, new ArrayList(this.typedClients.keySet()));
                for (Map.Entry next : this.typedClients.entrySet()) {
                    data.putParcelableArray(String.valueOf(next.getKey()), (Messenger[]) ((Set) next.getValue()).toArray(new Messenger[((Set) next.getValue()).size()]));
                }
                obtainMessage.setData(data);
            }
            try {
                messenger.send(obtainMessage);
            } catch (RemoteException e) {
                Log.w(InstrumentationConnection.TAG, "The remote process is terminated unexpectedly", e);
                instrBinderDied(messenger);
            }
        }

        private void sendMessageToOtherInstr(int i, Bundle bundle) {
            LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "sendMessageToOtherInstr() called with: what = [%s], data = [%s]", Integer.valueOf(i), bundle);
            for (Messenger sendMessageWithReply : this.otherInstrumentations) {
                sendMessageWithReply(sendMessageWithReply, i, bundle);
            }
        }

        private void clientsRegistrationFromBundle(Bundle bundle, boolean z) {
            LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "clientsRegistrationFromBundle called", new Object[0]);
            if (bundle == null) {
                Log.w(InstrumentationConnection.TAG, "The client bundle is null, ignoring...");
                return;
            }
            ArrayList<String> stringArrayList = bundle.getStringArrayList(InstrumentationConnection.BUNDLE_KEY_CLIENTS);
            if (stringArrayList == null) {
                Log.w(InstrumentationConnection.TAG, "No clients found in the given bundle");
                return;
            }
            Iterator<String> it = stringArrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                Parcelable[] parcelableArray = bundle.getParcelableArray(String.valueOf(next));
                if (parcelableArray != null) {
                    for (Parcelable parcelable : parcelableArray) {
                        if (z) {
                            registerClient(next, (Messenger) parcelable);
                        } else {
                            unregisterClient(next, (Messenger) parcelable);
                        }
                    }
                }
            }
        }

        private void registerClient(String str, Messenger messenger) {
            LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "registerClient called with type = [%s] client = [%s]", str, messenger);
            Checks.checkNotNull(str, "type cannot be null!");
            Checks.checkNotNull(messenger, "client cannot be null!");
            Set set = this.typedClients.get(str);
            if (set == null) {
                HashSet hashSet = new HashSet();
                hashSet.add(messenger);
                this.typedClients.put(str, hashSet);
                return;
            }
            set.add(messenger);
        }

        private void unregisterClient(String str, Messenger messenger) {
            LogUtil.logDebugWithProcess(InstrumentationConnection.TAG, "unregisterClient called with type = [%s] client = [%s]", str, messenger);
            Checks.checkNotNull(str, "type cannot be null!");
            Checks.checkNotNull(messenger, "client cannot be null!");
            if (!this.typedClients.containsKey(str)) {
                String valueOf = String.valueOf(str);
                Log.w(InstrumentationConnection.TAG, valueOf.length() != 0 ? "There are no registered clients for type: ".concat(valueOf) : new String("There are no registered clients for type: "));
                return;
            }
            Set set = this.typedClients.get(str);
            if (!set.contains(messenger)) {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 78);
                sb.append("Could not unregister client for type ");
                sb.append(str);
                sb.append(" because it doesn't seem to be registered");
                Log.w(InstrumentationConnection.TAG, sb.toString());
                return;
            }
            set.remove(messenger);
            if (set.isEmpty()) {
                this.typedClients.remove(str);
            }
        }

        private void instrBinderDied(Messenger messenger) {
            Message obtainMessage = obtainMessage(5);
            obtainMessage.replyTo = messenger;
            sendMessage(obtainMessage);
        }
    }
}
