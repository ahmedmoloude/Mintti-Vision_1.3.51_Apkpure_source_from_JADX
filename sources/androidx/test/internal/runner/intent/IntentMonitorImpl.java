package androidx.test.internal.runner.intent;

import android.content.Intent;
import android.util.Log;
import androidx.test.runner.intent.IntentCallback;
import androidx.test.runner.intent.IntentMonitor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class IntentMonitorImpl implements IntentMonitor {
    private static final String TAG = "IntentMonitorImpl";
    List<WeakReference<IntentCallback>> callbacks = Collections.synchronizedList(new ArrayList());

    public void addIntentCallback(IntentCallback intentCallback) {
        Objects.requireNonNull(intentCallback, "callback cannot be null!");
        boolean z = true;
        Iterator<WeakReference<IntentCallback>> it = this.callbacks.iterator();
        while (it.hasNext()) {
            IntentCallback intentCallback2 = (IntentCallback) it.next().get();
            if (intentCallback2 == null) {
                it.remove();
            } else if (intentCallback2 == intentCallback) {
                z = false;
            }
        }
        if (z) {
            this.callbacks.add(new WeakReference(intentCallback));
        }
    }

    public void removeIntentCallback(IntentCallback intentCallback) {
        Objects.requireNonNull(intentCallback, "callback cannot be null!");
        Iterator<WeakReference<IntentCallback>> it = this.callbacks.iterator();
        while (it.hasNext()) {
            IntentCallback intentCallback2 = (IntentCallback) it.next().get();
            if (intentCallback2 == null) {
                it.remove();
            } else if (intentCallback2 == intentCallback) {
                it.remove();
            }
        }
    }

    public void signalIntent(Intent intent) {
        Iterator<WeakReference<IntentCallback>> it = this.callbacks.iterator();
        while (it.hasNext()) {
            IntentCallback intentCallback = (IntentCallback) it.next().get();
            if (intentCallback == null) {
                it.remove();
            } else {
                try {
                    intentCallback.onIntentSent(new Intent(intent));
                } catch (RuntimeException e) {
                    Log.e(TAG, String.format("Callback threw exception! (callback: %s intent: %s)", new Object[]{intentCallback, intent}), e);
                }
            }
        }
    }
}
