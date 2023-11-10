package androidx.test.internal.runner.junit3;

import java.util.Enumeration;
import junit.framework.AssertionFailedError;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestFailure;
import junit.framework.TestListener;
import junit.framework.TestResult;

class DelegatingTestResult extends TestResult {
    private TestResult wrappedResult;

    DelegatingTestResult(TestResult testResult) {
        this.wrappedResult = testResult;
    }

    public void addError(Test test, Throwable th) {
        this.wrappedResult.addError(test, th);
    }

    public void addFailure(Test test, AssertionFailedError assertionFailedError) {
        this.wrappedResult.addFailure(test, assertionFailedError);
    }

    public void addListener(TestListener testListener) {
        this.wrappedResult.addListener(testListener);
    }

    public void removeListener(TestListener testListener) {
        this.wrappedResult.removeListener(testListener);
    }

    public void endTest(Test test) {
        this.wrappedResult.endTest(test);
    }

    public int errorCount() {
        return this.wrappedResult.errorCount();
    }

    public Enumeration<TestFailure> errors() {
        return this.wrappedResult.errors();
    }

    public int failureCount() {
        return this.wrappedResult.failureCount();
    }

    public Enumeration<TestFailure> failures() {
        return this.wrappedResult.failures();
    }

    public int runCount() {
        return this.wrappedResult.runCount();
    }

    public void runProtected(Test test, Protectable protectable) {
        this.wrappedResult.runProtected(test, protectable);
    }

    public boolean shouldStop() {
        return this.wrappedResult.shouldStop();
    }

    public void startTest(Test test) {
        this.wrappedResult.startTest(test);
    }

    public void stop() {
        this.wrappedResult.stop();
    }

    public boolean wasSuccessful() {
        return this.wrappedResult.wasSuccessful();
    }
}
