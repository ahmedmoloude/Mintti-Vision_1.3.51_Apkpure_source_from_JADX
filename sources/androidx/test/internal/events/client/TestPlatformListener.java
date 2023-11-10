package androidx.test.internal.events.client;

import android.util.Log;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.ErrorInfo;
import androidx.test.services.events.ParcelableConverter;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.TestEventException;
import androidx.test.services.events.TestRunInfo;
import androidx.test.services.events.TestStatus;
import androidx.test.services.events.TimeStamp;
import androidx.test.services.events.platform.TestCaseErrorEvent;
import androidx.test.services.events.platform.TestCaseFinishedEvent;
import androidx.test.services.events.platform.TestCaseStartedEvent;
import androidx.test.services.events.platform.TestPlatformEvent;
import androidx.test.services.events.platform.TestRunErrorEvent;
import androidx.test.services.events.platform.TestRunFinishedEvent;
import androidx.test.services.events.platform.TestRunStartedEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public final class TestPlatformListener extends RunListener {
    private static final String INIT_ERROR = "initializationError";
    private static final String TAG = "TestPlatformListener";
    private final AtomicReference<Description> currentTestCase = new AtomicReference<>(Description.EMPTY);
    private Set<Description> finishedTestCases;
    private Set<Description> foundTestCases;
    private TestRunInfo memoizedTestRun;
    private final TestPlatformEventService notificationService;
    private final AtomicReference<Result> ongoingResult;
    private final AtomicReference<RunListener> ongoingResultListener;
    private final AtomicBoolean processCrashed = new AtomicBoolean(false);
    private Set<Description> startedTestCases;
    private Map<Description, TestStatus.Status> testCaseToStatus;
    private Description testRunDescription = Description.EMPTY;

    public TestPlatformListener(TestPlatformEventService testPlatformEventService) {
        AtomicReference<Result> atomicReference = new AtomicReference<>(new Result());
        this.ongoingResult = atomicReference;
        this.ongoingResultListener = new AtomicReference<>(atomicReference.get().createListener());
        this.notificationService = (TestPlatformEventService) Checks.checkNotNull(testPlatformEventService, "notificationService cannot be null");
    }

    public void testRunStarted(Description description) throws Exception {
        TimeStamp timeStamp = getTimeStamp();
        resetListener();
        this.ongoingResultListener.get().testRunStarted(description);
        setRunDescription(description);
        for (Description next : JUnitDescriptionParser.getAllTestCaseDescriptions(this.testRunDescription)) {
            this.foundTestCases.add(next);
            this.testCaseToStatus.put(next, TestStatus.Status.PASSED);
        }
        try {
            this.memoizedTestRun = convertToTestRun(this.testRunDescription);
            this.notificationService.send(new TestRunStartedEvent(this.memoizedTestRun, timeStamp));
        } catch (TestEventException e) {
            Log.e(TAG, "Unable to send TestRunStartedEvent to Test Platform", e);
        }
    }

    public void testRunFinished(Result result) throws Exception {
        TimeStamp timeStamp = getTimeStamp();
        this.ongoingResultListener.get().testRunFinished(result);
        TestStatus.Status status = result.wasSuccessful() ? TestStatus.Status.PASSED : TestStatus.Status.FAILED;
        if (this.processCrashed.get()) {
            status = TestStatus.Status.FAILED;
        }
        if (this.foundTestCases.size() > this.finishedTestCases.size()) {
            if (status.equals(TestStatus.Status.PASSED)) {
                status = TestStatus.Status.ABORTED;
            }
            for (Description next : JUnitDescriptionParser.getAllTestCaseDescriptions(this.testRunDescription)) {
                if (!this.finishedTestCases.contains(next)) {
                    if (this.startedTestCases.contains(next)) {
                        this.testCaseToStatus.put(next, TestStatus.Status.ABORTED);
                    } else {
                        this.testCaseToStatus.put(next, TestStatus.Status.CANCELLED);
                    }
                    testFinishedInternal(next, timeStamp);
                }
            }
        }
        try {
            this.notificationService.send(new TestRunFinishedEvent(this.memoizedTestRun, new TestStatus(status), timeStamp));
        } catch (TestEventException e) {
            Log.e(TAG, "Unable to send TestRunFinishedEvent to Test Platform", e);
        }
    }

    public void testStarted(Description description) throws Exception {
        TimeStamp timeStamp = getTimeStamp();
        if (!isInitError(description)) {
            this.ongoingResultListener.get().testStarted(description);
            this.startedTestCases.add(description);
            this.currentTestCase.set(description);
            try {
                this.notificationService.send(new TestCaseStartedEvent(convertToTestCase(description), timeStamp));
            } catch (TestEventException e) {
                Log.e(TAG, "Unable to send TestStartedEvent to Test Platform", e);
            }
        }
    }

    public void testFinished(Description description) throws Exception {
        testFinishedInternal(description, getTimeStamp());
    }

    private void testFinishedInternal(Description description, TimeStamp timeStamp) throws Exception {
        if (!isInitError(description)) {
            this.ongoingResultListener.get().testFinished(description);
            this.finishedTestCases.add(description);
            try {
                this.notificationService.send(new TestCaseFinishedEvent(convertToTestCase(description), new TestStatus(this.testCaseToStatus.get(description)), timeStamp));
            } catch (TestEventException e) {
                Log.e(TAG, "Unable to send TestFinishedEvent to Test Platform", e);
            } catch (Throwable th) {
                this.currentTestCase.set(Description.EMPTY);
                throw th;
            }
            this.currentTestCase.set(Description.EMPTY);
        }
    }

    public void testFailure(Failure failure) throws Exception {
        TimeStamp timeStamp = getTimeStamp();
        Description description = failure.getDescription();
        this.ongoingResultListener.get().testFailure(failure);
        if (description.isTest() && !isInitError(description)) {
            this.testCaseToStatus.put(description, TestStatus.Status.FAILED);
        }
        try {
            this.notificationService.send(createErrorEvent(failure, timeStamp));
        } catch (TestEventException e) {
            throw new IllegalStateException("Unable to send error event", e);
        }
    }

    public void testAssumptionFailure(Failure failure) {
        TimeStamp timeStamp = getTimeStamp();
        this.ongoingResultListener.get().testAssumptionFailure(failure);
        if (failure.getDescription().isTest()) {
            this.testCaseToStatus.put(failure.getDescription(), TestStatus.Status.SKIPPED);
        }
        try {
            this.notificationService.send(createErrorEvent(failure, timeStamp));
        } catch (TestEventException e) {
            Log.e(TAG, "Unable to send TestAssumptionFailureEvent to Test Platform", e);
        }
    }

    public void testIgnored(Description description) throws Exception {
        TimeStamp timeStamp = getTimeStamp();
        this.ongoingResultListener.get().testIgnored(description);
        String displayName = description.getDisplayName();
        String className = description.getClassName();
        String methodName = description.getMethodName();
        StringBuilder sb = new StringBuilder(String.valueOf(displayName).length() + 21 + String.valueOf(className).length() + String.valueOf(methodName).length());
        sb.append("TestIgnoredEvent(");
        sb.append(displayName);
        sb.append("): ");
        sb.append(className);
        sb.append("#");
        sb.append(methodName);
        Log.i(TAG, sb.toString());
        this.testCaseToStatus.put(description, TestStatus.Status.IGNORED);
        testFinishedInternal(description, timeStamp);
    }

    public void reportProcessCrash(Throwable th) {
        boolean z = true;
        this.processCrashed.set(true);
        Description description = this.currentTestCase.get();
        if (description.equals(Description.EMPTY)) {
            z = false;
            description = this.testRunDescription;
        }
        try {
            testFailure(new Failure(description, th));
            if (z) {
                testFinished(description);
            }
            testRunFinished(this.ongoingResult.get());
        } catch (Exception e) {
            Log.e("An exception was encountered while reporting the process crash", e.getMessage());
        }
    }

    private void resetListener() {
        this.finishedTestCases = new HashSet();
        this.foundTestCases = new HashSet();
        this.startedTestCases = new HashSet();
        this.testCaseToStatus = new HashMap();
        this.currentTestCase.set(Description.EMPTY);
        this.testRunDescription = Description.EMPTY;
        this.memoizedTestRun = null;
        this.processCrashed.set(false);
        this.ongoingResult.set(new Result());
        this.ongoingResultListener.set(this.ongoingResult.get().createListener());
    }

    private void setRunDescription(Description description) {
        this.testRunDescription = description;
        while (this.testRunDescription.getDisplayName().equals("null") && this.testRunDescription.getChildren().size() == 1) {
            this.testRunDescription = this.testRunDescription.getChildren().get(0);
        }
    }

    private static TestCaseInfo convertToTestCase(Description description) throws TestEventException {
        return ParcelableConverter.getTestCaseFromDescription(description);
    }

    private static TestRunInfo convertToTestRun(Description description) throws TestEventException {
        ArrayList arrayList = new ArrayList();
        for (Description convertToTestCase : JUnitDescriptionParser.getAllTestCaseDescriptions(description)) {
            arrayList.add(convertToTestCase(convertToTestCase));
        }
        return new TestRunInfo(description.getDisplayName(), arrayList);
    }

    private static boolean isInitError(Description description) {
        return description.getMethodName() != null && description.getMethodName().equals(INIT_ERROR);
    }

    private TestPlatformEvent createErrorEvent(Failure failure, TimeStamp timeStamp) {
        Description description = failure.getDescription();
        if (!description.isTest() || isInitError(description)) {
            description = this.testRunDescription;
        }
        ErrorInfo errorInfo = new ErrorInfo(failure.getMessage(), failure.getException().getClass().getName(), failure.getTrace());
        if (!description.equals(this.testRunDescription)) {
            try {
                return new TestCaseErrorEvent(convertToTestCase(description), errorInfo, timeStamp);
            } catch (TestEventException e) {
                Log.e(TAG, "Unable to create TestCaseErrorEvent", e);
            }
        }
        return new TestRunErrorEvent(this.memoizedTestRun, errorInfo, timeStamp);
    }

    private TimeStamp getTimeStamp() {
        long nanoTime = System.nanoTime();
        long seconds = TimeUnit.NANOSECONDS.toSeconds(nanoTime);
        return new TimeStamp(Long.valueOf(seconds), Integer.valueOf((int) (nanoTime - TimeUnit.SECONDS.toNanos(seconds))));
    }
}
