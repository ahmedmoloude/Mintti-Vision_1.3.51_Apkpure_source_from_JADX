package androidx.test.internal.runner.junit3;

import android.app.Instrumentation;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import java.util.concurrent.TimeoutException;
import junit.framework.AssertionFailedError;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

class AndroidTestResult extends DelegatingTestResult {
    private final Bundle bundle;
    private final Instrumentation instr;
    private long timeout;

    AndroidTestResult(Bundle bundle2, Instrumentation instrumentation, TestResult testResult) {
        super(testResult);
        this.bundle = bundle2;
        this.instr = instrumentation;
    }

    /* access modifiers changed from: protected */
    public void run(TestCase testCase) {
        if (testCase instanceof AndroidTestCase) {
            ((AndroidTestCase) testCase).setContext(this.instr.getTargetContext());
        }
        if (testCase instanceof InstrumentationTestCase) {
            ((InstrumentationTestCase) testCase).injectInstrumentation(this.instr);
        }
        super.run(testCase);
    }

    /* access modifiers changed from: package-private */
    public void setCurrentTimeout(long j) {
        this.timeout = j;
    }

    public void runProtected(Test test, Protectable protectable) {
        try {
            protectable.protect();
        } catch (AssertionFailedError e) {
            super.addFailure(test, e);
        } catch (ThreadDeath e2) {
            throw e2;
        } catch (InterruptedException unused) {
            super.addError(test, new TimeoutException(String.format("Test timed out after %d milliseconds", new Object[]{Long.valueOf(this.timeout)})));
        } catch (Throwable th) {
            super.addError(test, th);
        }
    }
}
