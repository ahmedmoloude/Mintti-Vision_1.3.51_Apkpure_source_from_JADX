package androidx.test.core.app;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.test.internal.platform.app.ActivityInvoker;
import androidx.test.internal.platform.app.ActivityInvoker$$CC;
import androidx.test.internal.platform.app.ActivityLifecycleTimeout;
import androidx.test.internal.util.Checks;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class InstrumentationActivityInvoker implements ActivityInvoker {
    private static final String BOOTSTRAP_ACTIVITY_RESULT_CODE_KEY = "androidx.test.core.app.InstrumentationActivityInvoker.BOOTSTRAP_ACTIVITY_RESULT_CODE_KEY";
    private static final String BOOTSTRAP_ACTIVITY_RESULT_DATA_KEY = "androidx.test.core.app.InstrumentationActivityInvoker.BOOTSTRAP_ACTIVITY_RESULT_DATA_KEY";
    private static final String BOOTSTRAP_ACTIVITY_RESULT_RECEIVED = "androidx.test.core.app.InstrumentationActivityInvoker.BOOTSTRAP_ACTIVITY_RESULT_RECEIVED";
    private static final String CANCEL_ACTIVITY_RESULT_WAITER = "androidx.test.core.app.InstrumentationActivityInvoker.CANCEL_ACTIVITY_RESULT_WAITER";
    private static final String EMPTY_ACTIVITY_RESUMED = "androidx.test.core.app.InstrumentationActivityInvoker.EMPTY_ACTIVITY_RESUMED";
    private static final String EMPTY_FLOATING_ACTIVITY_RESUMED = "androidx.test.core.app.InstrumentationActivityInvoker.EMPTY_FLOATING_ACTIVITY_RESUMED";
    private static final String FINISH_BOOTSTRAP_ACTIVITY = "androidx.test.core.app.InstrumentationActivityInvoker.FINISH_BOOTSTRAP_ACTIVITY";
    private static final String FINISH_EMPTY_ACTIVITIES = "androidx.test.core.app.InstrumentationActivityInvoker.FINISH_EMPTY_ACTIVITIES";
    private static final int FLAG_MUTABLE = 33554432;
    private static final String TARGET_ACTIVITY_INTENT_KEY = "androidx.test.core.app.InstrumentationActivityInvoker.START_TARGET_ACTIVITY_INTENT_KEY";
    private static final String TARGET_ACTIVITY_OPTIONS_BUNDLE_KEY = "androidx.test.core.app.InstrumentationActivityInvoker.TARGET_ACTIVITY_OPTIONS_BUNDLE_KEY";
    private ActivityResultWaiter activityResultWaiter;

    public Intent getIntentForActivity(Class cls) {
        return ActivityInvoker$$CC.getIntentForActivity$$dflt$$(this, cls);
    }

    InstrumentationActivityInvoker() {
    }

    public static class BootstrapActivity extends Activity {
        private static final String IS_TARGET_ACTIVITY_STARTED_KEY = "IS_TARGET_ACTIVITY_STARTED_KEY";
        private static final String TAG = "androidx.test.core.app.InstrumentationActivityInvoker$BootstrapActivity";
        private boolean isTargetActivityStarted;
        private final BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                BootstrapActivity.this.finishActivity(0);
                BootstrapActivity.this.finish();
            }
        };

        /* access modifiers changed from: protected */
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            registerReceiver(this.receiver, new IntentFilter(InstrumentationActivityInvoker.FINISH_BOOTSTRAP_ACTIVITY));
            this.isTargetActivityStarted = bundle != null && bundle.getBoolean(IS_TARGET_ACTIVITY_STARTED_KEY, false);
            overridePendingTransition(0, 0);
        }

        public void finish() {
            super.finish();
            overridePendingTransition(0, 0);
        }

        /* access modifiers changed from: protected */
        public void onResume() {
            super.onResume();
            if (!this.isTargetActivityStarted) {
                this.isTargetActivityStarted = true;
                PendingIntent pendingIntent = (PendingIntent) Checks.checkNotNull((PendingIntent) getIntent().getParcelableExtra(InstrumentationActivityInvoker.TARGET_ACTIVITY_INTENT_KEY));
                Bundle bundleExtra = getIntent().getBundleExtra(InstrumentationActivityInvoker.TARGET_ACTIVITY_OPTIONS_BUNDLE_KEY);
                if (bundleExtra != null) {
                    try {
                        if (Build.VERSION.SDK_INT >= 16) {
                            startIntentSenderForResult(pendingIntent.getIntentSender(), 0, (Intent) null, 268435456, 0, 0, bundleExtra);
                            return;
                        }
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Failed to start target activity.", e);
                        throw new RuntimeException(e);
                    }
                }
                startIntentSenderForResult(pendingIntent.getIntentSender(), 0, (Intent) null, 268435456, 0, 0);
            }
        }

        /* access modifiers changed from: protected */
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean(IS_TARGET_ACTIVITY_STARTED_KEY, this.isTargetActivityStarted);
        }

        /* access modifiers changed from: protected */
        public void onDestroy() {
            super.onDestroy();
            unregisterReceiver(this.receiver);
        }

        /* access modifiers changed from: protected */
        public void onActivityResult(int i, int i2, Intent intent) {
            if (i == 0) {
                Intent intent2 = new Intent(InstrumentationActivityInvoker.BOOTSTRAP_ACTIVITY_RESULT_RECEIVED);
                intent2.putExtra(InstrumentationActivityInvoker.BOOTSTRAP_ACTIVITY_RESULT_CODE_KEY, i2);
                if (intent != null) {
                    intent2.putExtra(InstrumentationActivityInvoker.BOOTSTRAP_ACTIVITY_RESULT_DATA_KEY, intent);
                }
                sendBroadcast(intent2);
                finish();
            }
        }
    }

    private static class ActivityResultWaiter {
        private static final String TAG = "androidx.test.core.app.InstrumentationActivityInvoker$ActivityResultWaiter";
        /* access modifiers changed from: private */
        public Instrumentation.ActivityResult activityResult;
        /* access modifiers changed from: private */
        public final CountDownLatch latch = new CountDownLatch(1);

        public ActivityResultWaiter(Context context) {
            C06261 r0 = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    context.unregisterReceiver(this);
                    if (InstrumentationActivityInvoker.BOOTSTRAP_ACTIVITY_RESULT_RECEIVED.equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra(InstrumentationActivityInvoker.BOOTSTRAP_ACTIVITY_RESULT_CODE_KEY, 0);
                        Intent intent2 = (Intent) intent.getParcelableExtra(InstrumentationActivityInvoker.BOOTSTRAP_ACTIVITY_RESULT_DATA_KEY);
                        if (intent2 != null) {
                            intent2 = new Intent(intent2);
                        }
                        Instrumentation.ActivityResult unused = ActivityResultWaiter.this.activityResult = new Instrumentation.ActivityResult(intExtra, intent2);
                        ActivityResultWaiter.this.latch.countDown();
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter(InstrumentationActivityInvoker.BOOTSTRAP_ACTIVITY_RESULT_RECEIVED);
            intentFilter.addAction(InstrumentationActivityInvoker.CANCEL_ACTIVITY_RESULT_WAITER);
            context.registerReceiver(r0, intentFilter);
        }

        public Instrumentation.ActivityResult getActivityResult() {
            try {
                this.latch.await(ActivityLifecycleTimeout.getMillis(), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Log.i(TAG, "Waiting activity result was interrupted", e);
            }
            Checks.checkNotNull(this.activityResult, "onActivityResult never be called after %d milliseconds", Long.valueOf(ActivityLifecycleTimeout.getMillis()));
            return this.activityResult;
        }
    }

    public static class EmptyActivity extends Activity {
        private final BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                EmptyActivity.this.finish();
            }
        };

        /* access modifiers changed from: protected */
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            registerReceiver(this.receiver, new IntentFilter(InstrumentationActivityInvoker.FINISH_EMPTY_ACTIVITIES));
            overridePendingTransition(0, 0);
        }

        public void finish() {
            super.finish();
            overridePendingTransition(0, 0);
        }

        /* access modifiers changed from: protected */
        public void onResume() {
            super.onResume();
            sendBroadcast(new Intent(InstrumentationActivityInvoker.EMPTY_ACTIVITY_RESUMED));
        }

        /* access modifiers changed from: protected */
        public void onDestroy() {
            super.onDestroy();
            unregisterReceiver(this.receiver);
        }
    }

    public static class EmptyFloatingActivity extends Activity {
        private final BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                EmptyFloatingActivity.this.finish();
            }
        };

        /* access modifiers changed from: protected */
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            registerReceiver(this.receiver, new IntentFilter(InstrumentationActivityInvoker.FINISH_EMPTY_ACTIVITIES));
            overridePendingTransition(0, 0);
        }

        public void finish() {
            super.finish();
            overridePendingTransition(0, 0);
        }

        /* access modifiers changed from: protected */
        public void onResume() {
            super.onResume();
            sendBroadcast(new Intent(InstrumentationActivityInvoker.EMPTY_FLOATING_ACTIVITY_RESUMED));
        }

        /* access modifiers changed from: protected */
        public void onDestroy() {
            super.onDestroy();
            unregisterReceiver(this.receiver);
        }
    }

    public void startActivity(Intent intent, Bundle bundle) {
        if (intent.resolveActivityInfo(ApplicationProvider.getApplicationContext().getPackageManager(), 0) != null) {
            ApplicationProvider.getApplicationContext().sendBroadcast(new Intent(FINISH_BOOTSTRAP_ACTIVITY));
            ApplicationProvider.getApplicationContext().sendBroadcast(new Intent(FINISH_EMPTY_ACTIVITIES));
            this.activityResultWaiter = new ActivityResultWaiter(ApplicationProvider.getApplicationContext());
            Intent putExtra = getIntentForActivity(BootstrapActivity.class).setFlags(268468224).putExtra(TARGET_ACTIVITY_INTENT_KEY, PendingIntent.getActivity(ApplicationProvider.getApplicationContext(), 0, intent, 167772160)).putExtra(TARGET_ACTIVITY_OPTIONS_BUNDLE_KEY, bundle);
            if (Build.VERSION.SDK_INT < 16) {
                ApplicationProvider.getApplicationContext().startActivity(putExtra);
            } else {
                ApplicationProvider.getApplicationContext().startActivity(putExtra, bundle);
            }
        } else {
            String valueOf = String.valueOf(intent);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32);
            sb.append("Unable to resolve activity for: ");
            sb.append(valueOf);
            throw new RuntimeException(sb.toString());
        }
    }

    public void startActivity(Intent intent) {
        startActivity(intent, (Bundle) null);
    }

    public Instrumentation.ActivityResult getActivityResult() {
        return ((ActivityResultWaiter) Checks.checkNotNull(this.activityResultWaiter, "You must start Activity first")).getActivityResult();
    }

    public void resumeActivity(Activity activity) {
        checkActivityStageIsIn(activity, Stage.RESUMED, Stage.PAUSED, Stage.STOPPED);
        ApplicationProvider.getApplicationContext().sendBroadcast(new Intent(FINISH_EMPTY_ACTIVITIES));
    }

    public void pauseActivity(Activity activity) {
        checkActivityStageIsIn(activity, Stage.RESUMED, Stage.PAUSED);
        startFloatingEmptyActivitySync();
    }

    private void startFloatingEmptyActivitySync() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        C06241 r1 = new BroadcastReceiver(this) {
            public void onReceive(Context context, Intent intent) {
                countDownLatch.countDown();
            }
        };
        ApplicationProvider.getApplicationContext().registerReceiver(r1, new IntentFilter(EMPTY_FLOATING_ACTIVITY_RESUMED));
        ApplicationProvider.getApplicationContext().startActivity(getIntentForActivity(EmptyFloatingActivity.class).setFlags(268435456));
        try {
            countDownLatch.await(ActivityLifecycleTimeout.getMillis(), TimeUnit.MILLISECONDS);
            ApplicationProvider.getApplicationContext().unregisterReceiver(r1);
        } catch (InterruptedException e) {
            throw new AssertionError("Failed to pause activity", e);
        } catch (Throwable th) {
            ApplicationProvider.getApplicationContext().unregisterReceiver(r1);
            throw th;
        }
    }

    public void stopActivity(Activity activity) {
        checkActivityStageIsIn(activity, Stage.RESUMED, Stage.PAUSED, Stage.STOPPED);
        startEmptyActivitySync();
    }

    private void startEmptyActivitySync() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        C06252 r1 = new BroadcastReceiver(this) {
            public void onReceive(Context context, Intent intent) {
                countDownLatch.countDown();
            }
        };
        ApplicationProvider.getApplicationContext().registerReceiver(r1, new IntentFilter(EMPTY_ACTIVITY_RESUMED));
        ApplicationProvider.getApplicationContext().startActivity(getIntentForActivity(EmptyActivity.class).setFlags(268435456));
        try {
            countDownLatch.await(ActivityLifecycleTimeout.getMillis(), TimeUnit.MILLISECONDS);
            ApplicationProvider.getApplicationContext().unregisterReceiver(r1);
        } catch (InterruptedException e) {
            throw new AssertionError("Failed to stop activity", e);
        } catch (Throwable th) {
            ApplicationProvider.getApplicationContext().unregisterReceiver(r1);
            throw th;
        }
    }

    public void recreateActivity(Activity activity) {
        checkActivityStageIsIn(activity, Stage.RESUMED, Stage.PAUSED, Stage.STOPPED);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        activity.getClass();
        instrumentation.runOnMainSync(InstrumentationActivityInvoker$$Lambda$0.get$Lambda(activity));
    }

    public void finishActivity(Activity activity) {
        startEmptyActivitySync();
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        activity.getClass();
        instrumentation.runOnMainSync(InstrumentationActivityInvoker$$Lambda$1.get$Lambda(activity));
        ApplicationProvider.getApplicationContext().sendBroadcast(new Intent(FINISH_BOOTSTRAP_ACTIVITY));
        startEmptyActivitySync();
        Instrumentation instrumentation2 = InstrumentationRegistry.getInstrumentation();
        activity.getClass();
        instrumentation2.runOnMainSync(InstrumentationActivityInvoker$$Lambda$2.get$Lambda(activity));
        ApplicationProvider.getApplicationContext().sendBroadcast(new Intent(FINISH_EMPTY_ACTIVITIES));
        ApplicationProvider.getApplicationContext().sendBroadcast(new Intent(CANCEL_ACTIVITY_RESULT_WAITER));
    }

    private static void checkActivityStageIsIn(Activity activity, Stage... stageArr) {
        checkActivityStageIsIn(activity, (Set<Stage>) new HashSet(Arrays.asList(stageArr)));
    }

    private static void checkActivityStageIsIn(Activity activity, Set<Stage> set) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new InstrumentationActivityInvoker$$Lambda$3(activity, set));
    }

    static final /* synthetic */ void lambda$checkActivityStageIsIn$0$InstrumentationActivityInvoker(Activity activity, Set set) {
        Stage lifecycleStageOf = ActivityLifecycleMonitorRegistry.getInstance().getLifecycleStageOf(activity);
        Checks.checkState(set.contains(lifecycleStageOf), "Activity's stage must be %s but was %s", set, lifecycleStageOf);
    }
}
