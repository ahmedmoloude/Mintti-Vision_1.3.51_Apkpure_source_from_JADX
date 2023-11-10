package androidx.test.internal.events.client;

import android.os.ConditionVariable;
import android.util.Log;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.FailureInfo;
import androidx.test.services.events.ParcelableConverter;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.TestEventException;
import androidx.test.services.events.run.TestAssumptionFailureEvent;
import androidx.test.services.events.run.TestFailureEvent;
import androidx.test.services.events.run.TestFinishedEvent;
import androidx.test.services.events.run.TestIgnoredEvent;
import androidx.test.services.events.run.TestRunFinishedEvent;
import androidx.test.services.events.run.TestRunStartedEvent;
import androidx.test.services.events.run.TestStartedEvent;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public final class OrchestratedInstrumentationListener extends RunListener {
    private static final String TAG = "OrchestrationListener";
    private Description description = Description.EMPTY;
    private final AtomicBoolean isTestFailed = new AtomicBoolean(false);
    private final TestRunEventService notificationService;
    private final ConditionVariable testFinishedCondition = new ConditionVariable();

    public OrchestratedInstrumentationListener(TestRunEventService testRunEventService) {
        Checks.checkNotNull(testRunEventService, "notificationService cannot be null");
        this.notificationService = testRunEventService;
    }

    public void testRunStarted(Description description2) {
        try {
            this.notificationService.send(new TestRunStartedEvent(ParcelableConverter.getTestCaseFromDescription(description2)));
        } catch (TestEventException e) {
            Log.e(TAG, "Unable to send TestRunStartedEvent to Orchestrator", e);
        }
    }

    public void testRunFinished(Result result) {
        List<FailureInfo> emptyList = Collections.emptyList();
        try {
            emptyList = ParcelableConverter.getFailuresFromList(result.getFailures());
        } catch (TestEventException e) {
            Log.w(TAG, "Failure event doesn't contain a test case", e);
        }
        try {
            this.notificationService.send(new TestRunFinishedEvent(result.getRunCount(), result.getIgnoreCount(), result.getRunTime(), emptyList));
        } catch (TestEventException e2) {
            Log.e(TAG, "Unable to send TestRunFinishedEvent to Orchestrator", e2);
        }
    }

    public void testStarted(Description description2) {
        this.description = description2;
        if (!JUnitValidator.validateDescription(description2)) {
            String className = description2.getClassName();
            String methodName = description2.getMethodName();
            StringBuilder sb = new StringBuilder(String.valueOf(className).length() + 51 + String.valueOf(methodName).length());
            sb.append("testStarted: JUnit reported ");
            sb.append(className);
            sb.append("#");
            sb.append(methodName);
            sb.append("; discarding as bogus.");
            Log.w(TAG, sb.toString());
            return;
        }
        try {
            this.notificationService.send(new TestStartedEvent(ParcelableConverter.getTestCaseFromDescription(description2)));
        } catch (TestEventException e) {
            Log.e(TAG, "Unable to send TestStartedEvent to Orchestrator", e);
        }
    }

    public void testFinished(Description description2) {
        if (!JUnitValidator.validateDescription(description2)) {
            String className = description2.getClassName();
            String methodName = description2.getMethodName();
            StringBuilder sb = new StringBuilder(String.valueOf(className).length() + 52 + String.valueOf(methodName).length());
            sb.append("testFinished: JUnit reported ");
            sb.append(className);
            sb.append("#");
            sb.append(methodName);
            sb.append("; discarding as bogus.");
            Log.w(TAG, sb.toString());
            return;
        }
        try {
            this.notificationService.send(new TestFinishedEvent(ParcelableConverter.getTestCaseFromDescription(description2)));
        } catch (TestEventException e) {
            Log.e(TAG, "Unable to send TestFinishedEvent to Orchestrator", e);
        }
    }

    public void testFailure(Failure failure) {
        TestFailureEvent testFailureEvent;
        if (this.isTestFailed.compareAndSet(false, true)) {
            Description description2 = failure.getDescription();
            if (!JUnitValidator.validateDescription(description2)) {
                String className = description2.getClassName();
                String methodName = description2.getMethodName();
                StringBuilder sb = new StringBuilder(String.valueOf(className).length() + 51 + String.valueOf(methodName).length());
                sb.append("testFailure: JUnit reported ");
                sb.append(className);
                sb.append("#");
                sb.append(methodName);
                sb.append("; discarding as bogus.");
                Log.w(TAG, sb.toString());
                return;
            }
            try {
                testFailureEvent = new TestFailureEvent(ParcelableConverter.getTestCaseFromDescription(failure.getDescription()), ParcelableConverter.getFailure(failure));
            } catch (TestEventException e) {
                String valueOf = String.valueOf(failure);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 45);
                sb2.append("Unable to determine test case from failure [");
                sb2.append(valueOf);
                sb2.append("]");
                Log.d(TAG, sb2.toString(), e);
                testFailureEvent = getTestFailureEventFromCachedDescription(failure);
                if (testFailureEvent == null) {
                    return;
                }
            }
            try {
                this.notificationService.send(testFailureEvent);
            } catch (TestEventException e2) {
                throw new IllegalStateException("Unable to send TestFailureEvent, terminating", e2);
            }
        }
    }

    private TestFailureEvent getTestFailureEventFromCachedDescription(Failure failure) {
        Checks.checkNotNull(failure, "failure cannot be null");
        try {
            TestCaseInfo testCaseFromDescription = ParcelableConverter.getTestCaseFromDescription(this.description);
            return new TestFailureEvent(testCaseFromDescription, new FailureInfo(failure.getMessage(), failure.getTestHeader(), failure.getTrace(), testCaseFromDescription));
        } catch (TestEventException e) {
            String valueOf = String.valueOf(this.description);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
            sb.append("Unable to determine test case from description [");
            sb.append(valueOf);
            sb.append("]");
            Log.e(TAG, sb.toString(), e);
            return null;
        }
    }

    public void testAssumptionFailure(Failure failure) {
        try {
            this.notificationService.send(new TestAssumptionFailureEvent(ParcelableConverter.getTestCaseFromDescription(failure.getDescription()), ParcelableConverter.getFailure(failure)));
        } catch (TestEventException e) {
            Log.e(TAG, "Unable to send TestAssumptionFailureEvent to Orchestrator", e);
        }
    }

    public void testIgnored(Description description2) {
        try {
            TestCaseInfo testCaseFromDescription = ParcelableConverter.getTestCaseFromDescription(description2);
            String displayName = description2.getDisplayName();
            String className = description2.getClassName();
            String methodName = description2.getMethodName();
            String classAndMethodName = testCaseFromDescription.getClassAndMethodName();
            StringBuilder sb = new StringBuilder(String.valueOf(displayName).length() + 24 + String.valueOf(className).length() + String.valueOf(methodName).length() + String.valueOf(classAndMethodName).length());
            sb.append("TestIgnoredEvent(");
            sb.append(displayName);
            sb.append("): ");
            sb.append(className);
            sb.append("#");
            sb.append(methodName);
            sb.append(" = ");
            sb.append(classAndMethodName);
            Log.i(TAG, sb.toString());
            this.notificationService.send(new TestIgnoredEvent(testCaseFromDescription));
        } catch (TestEventException e) {
            Log.e(TAG, "Unable to send TestIgnoredEvent to Orchestrator", e);
        }
    }

    public void reportProcessCrash(Throwable th, long j) {
        waitUntilTestFinished(j);
        if (!this.isTestFailed.get()) {
            Log.i(TAG, "No test failure has been reported. Report the process crash.");
            reportProcessCrash(th);
        }
    }

    private void reportProcessCrash(Throwable th) {
        testFailure(new Failure(this.description, th));
        testFinished(this.description);
    }

    private void waitUntilTestFinished(long j) {
        if (!this.testFinishedCondition.block(j)) {
            Log.w(TAG, "Timeout waiting for the test to finish");
        }
    }
}
