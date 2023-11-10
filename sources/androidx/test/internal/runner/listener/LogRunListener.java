package androidx.test.internal.runner.listener;

import android.util.Log;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class LogRunListener extends RunListener {
    private static final String TAG = "TestRunner";

    public void testRunStarted(Description description) throws Exception {
        Log.i(TAG, String.format("run started: %d tests", new Object[]{Integer.valueOf(description.testCount())}));
    }

    public void testRunFinished(Result result) throws Exception {
        Log.i(TAG, String.format("run finished: %d tests, %d failed, %d ignored", new Object[]{Integer.valueOf(result.getRunCount()), Integer.valueOf(result.getFailureCount()), Integer.valueOf(result.getIgnoreCount())}));
    }

    public void testStarted(Description description) throws Exception {
        String valueOf = String.valueOf(description.getDisplayName());
        Log.i(TAG, valueOf.length() != 0 ? "started: ".concat(valueOf) : new String("started: "));
    }

    public void testFinished(Description description) throws Exception {
        String valueOf = String.valueOf(description.getDisplayName());
        Log.i(TAG, valueOf.length() != 0 ? "finished: ".concat(valueOf) : new String("finished: "));
    }

    public void testFailure(Failure failure) throws Exception {
        String valueOf = String.valueOf(failure.getDescription().getDisplayName());
        Log.e(TAG, valueOf.length() != 0 ? "failed: ".concat(valueOf) : new String("failed: "));
        Log.e(TAG, "----- begin exception -----");
        Log.e(TAG, failure.getTrace());
        Log.e(TAG, "----- end exception -----");
    }

    public void testAssumptionFailure(Failure failure) {
        String valueOf = String.valueOf(failure.getDescription().getDisplayName());
        Log.e(TAG, valueOf.length() != 0 ? "assumption failed: ".concat(valueOf) : new String("assumption failed: "));
        Log.e(TAG, "----- begin exception -----");
        Log.e(TAG, failure.getTrace());
        Log.e(TAG, "----- end exception -----");
    }

    public void testIgnored(Description description) throws Exception {
        String valueOf = String.valueOf(description.getDisplayName());
        Log.i(TAG, valueOf.length() != 0 ? "ignored: ".concat(valueOf) : new String("ignored: "));
    }
}
