package androidx.test.internal.runner.listener;

import android.os.Bundle;
import android.util.Log;
import androidx.test.services.events.internal.StackTrimmer;
import java.io.PrintStream;
import org.junit.internal.TextListener;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class InstrumentationResultPrinter extends InstrumentationRunListener {
    public static final String REPORT_KEY_NAME_CLASS = "class";
    public static final String REPORT_KEY_NAME_TEST = "test";
    public static final String REPORT_KEY_NUM_CURRENT = "current";
    public static final String REPORT_KEY_NUM_TOTAL = "numtests";
    public static final String REPORT_KEY_STACK = "stack";
    public static final String REPORT_VALUE_ID = "AndroidJUnitRunner";
    public static final int REPORT_VALUE_RESULT_ASSUMPTION_FAILURE = -4;
    @Deprecated
    public static final int REPORT_VALUE_RESULT_ERROR = -1;
    public static final int REPORT_VALUE_RESULT_FAILURE = -2;
    public static final int REPORT_VALUE_RESULT_IGNORED = -3;
    public static final int REPORT_VALUE_RESULT_OK = 0;
    public static final int REPORT_VALUE_RESULT_START = 1;
    private static final String TAG = "InstrumentationResultPrinter";
    private Description description = Description.EMPTY;
    private final Bundle resultTemplate;
    String testClass = null;
    int testNum = 0;
    Bundle testResult;
    int testResultCode = -999;

    public InstrumentationResultPrinter() {
        Bundle bundle = new Bundle();
        this.resultTemplate = bundle;
        this.testResult = new Bundle(bundle);
    }

    public void testRunStarted(Description description2) throws Exception {
        this.resultTemplate.putString("id", REPORT_VALUE_ID);
        this.resultTemplate.putInt(REPORT_KEY_NUM_TOTAL, description2.testCount());
    }

    public void testStarted(Description description2) throws Exception {
        this.description = description2;
        String className = description2.getClassName();
        String methodName = description2.getMethodName();
        Bundle bundle = new Bundle(this.resultTemplate);
        this.testResult = bundle;
        bundle.putString("class", className);
        this.testResult.putString(REPORT_KEY_NAME_TEST, methodName);
        Bundle bundle2 = this.testResult;
        int i = this.testNum + 1;
        this.testNum = i;
        bundle2.putInt(REPORT_KEY_NUM_CURRENT, i);
        if (className == null || className.equals(this.testClass)) {
            this.testResult.putString("stream", "");
        } else {
            this.testResult.putString("stream", String.format("\n%s:", new Object[]{className}));
            this.testClass = className;
        }
        sendStatus(1, this.testResult);
        this.testResultCode = 0;
    }

    public void testFinished(Description description2) throws Exception {
        if (this.testResultCode == 0) {
            this.testResult.putString("stream", ".");
        }
        sendStatus(this.testResultCode, this.testResult);
    }

    public void testFailure(Failure failure) throws Exception {
        boolean z;
        if (this.description.equals(Description.EMPTY) && this.testNum == 0 && this.testClass == null) {
            testStarted(failure.getDescription());
            z = true;
        } else {
            z = false;
        }
        this.testResultCode = -2;
        reportFailure(failure);
        if (z) {
            testFinished(failure.getDescription());
        }
    }

    public void testAssumptionFailure(Failure failure) {
        this.testResultCode = -4;
        this.testResult.putString(REPORT_KEY_STACK, failure.getTrace());
    }

    private void reportFailure(Failure failure) {
        String trimmedStackTrace = StackTrimmer.getTrimmedStackTrace(failure);
        this.testResult.putString(REPORT_KEY_STACK, trimmedStackTrace);
        this.testResult.putString("stream", String.format("\nError in %s:\n%s", new Object[]{failure.getDescription().getDisplayName(), trimmedStackTrace}));
    }

    public void testIgnored(Description description2) throws Exception {
        testStarted(description2);
        this.testResultCode = -3;
        testFinished(description2);
    }

    public void reportProcessCrash(Throwable th) {
        try {
            this.testResultCode = -2;
            Failure failure = new Failure(this.description, th);
            this.testResult.putString(REPORT_KEY_STACK, failure.getTrace());
            this.testResult.putString("stream", String.format("\nProcess crashed while executing %s:\n%s", new Object[]{this.description.getDisplayName(), failure.getTrace()}));
            testFinished(this.description);
        } catch (Exception unused) {
            Description description2 = this.description;
            if (description2 == null) {
                Log.e(TAG, "Failed to initialize test before process crash");
                return;
            }
            String displayName = description2.getDisplayName();
            StringBuilder sb = new StringBuilder(String.valueOf(displayName).length() + 52);
            sb.append("Failed to mark test ");
            sb.append(displayName);
            sb.append(" as finished after process crash");
            Log.e(TAG, sb.toString());
        }
    }

    public void instrumentationRunFinished(PrintStream printStream, Bundle bundle, Result result) {
        new TextListener(printStream).testRunFinished(result);
    }
}
