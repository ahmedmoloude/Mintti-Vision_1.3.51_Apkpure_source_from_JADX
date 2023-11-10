package androidx.test.internal.runner.junit3;

import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

class NonExecutingTestResult extends DelegatingTestResult {
    public void runProtected(Test test, Protectable protectable) {
    }

    NonExecutingTestResult(TestResult testResult) {
        super(testResult);
    }

    /* access modifiers changed from: protected */
    public void run(TestCase testCase) {
        startTest(testCase);
        endTest(testCase);
    }
}
