package androidx.test.orchestrator.callback;

import android.os.Bundle;
import androidx.test.internal.events.client.TestEventClientException;
import androidx.test.orchestrator.junit.ParcelableDescription;
import androidx.test.orchestrator.junit.ParcelableFailure;
import androidx.test.orchestrator.junit.ParcelableResult;
import androidx.test.orchestrator.listeners.OrchestrationListenerManager;
import androidx.test.services.events.FailureInfo;
import androidx.test.services.events.run.TestAssumptionFailureEvent;
import androidx.test.services.events.run.TestFailureEvent;
import androidx.test.services.events.run.TestFinishedEvent;
import androidx.test.services.events.run.TestIgnoredEvent;
import androidx.test.services.events.run.TestRunEvent;
import androidx.test.services.events.run.TestRunEventWithTestCase;
import androidx.test.services.events.run.TestRunFinishedEvent;
import androidx.test.services.events.run.TestRunStartedEvent;
import androidx.test.services.events.run.TestStartedEvent;
import java.util.ArrayList;
import java.util.List;

class BundleConverter {
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_FAILURE = "failure";
    private static final String KEY_RESULT = "result";

    private BundleConverter() {
    }

    public static Bundle getBundleFromTestRunEvent(TestRunEvent testRunEvent) throws TestEventClientException {
        if (testRunEvent instanceof TestAssumptionFailureEvent) {
            return getBundleFromFailureEvent((TestAssumptionFailureEvent) testRunEvent, OrchestrationListenerManager.TestEvent.TEST_ASSUMPTION_FAILURE);
        }
        if (testRunEvent instanceof TestFailureEvent) {
            return getBundleFromFailureEvent((TestFailureEvent) testRunEvent, OrchestrationListenerManager.TestEvent.TEST_FAILURE);
        }
        if (testRunEvent instanceof TestFinishedEvent) {
            return getBundleFromTestCaseEvent((TestFinishedEvent) testRunEvent, OrchestrationListenerManager.TestEvent.TEST_FINISHED);
        }
        if (testRunEvent instanceof TestIgnoredEvent) {
            return getBundleFromTestCaseEvent((TestIgnoredEvent) testRunEvent, OrchestrationListenerManager.TestEvent.TEST_IGNORED);
        }
        if (testRunEvent instanceof TestRunFinishedEvent) {
            return getBundleFromTestRunFinishedEvent((TestRunFinishedEvent) testRunEvent);
        }
        if (testRunEvent instanceof TestRunStartedEvent) {
            return getBundleFromTestCaseEvent((TestRunStartedEvent) testRunEvent, OrchestrationListenerManager.TestEvent.TEST_RUN_STARTED);
        }
        if (testRunEvent instanceof TestStartedEvent) {
            return getBundleFromTestCaseEvent((TestStartedEvent) testRunEvent, OrchestrationListenerManager.TestEvent.TEST_STARTED);
        }
        String valueOf = String.valueOf(testRunEvent);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 35);
        sb.append("Unrecognized test run event type [");
        sb.append(valueOf);
        sb.append("]");
        throw new TestEventClientException(sb.toString());
    }

    private static Bundle getBundleFromFailureEvent(TestFailureEvent testFailureEvent, OrchestrationListenerManager.TestEvent testEvent) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_FAILURE, new ParcelableFailure(new ParcelableDescription(testFailureEvent.testCase.getClassAndMethodName()), testFailureEvent.failure.stackTrace));
        bundle.putString(OrchestrationListenerManager.KEY_TEST_EVENT, testEvent.name());
        return bundle;
    }

    private static Bundle getBundleFromTestCaseEvent(TestRunEventWithTestCase testRunEventWithTestCase, OrchestrationListenerManager.TestEvent testEvent) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("description", new ParcelableDescription(testRunEventWithTestCase.testCase.getClassAndMethodName()));
        bundle.putString(OrchestrationListenerManager.KEY_TEST_EVENT, testEvent.name());
        return bundle;
    }

    private static Bundle getBundleFromTestRunFinishedEvent(TestRunFinishedEvent testRunFinishedEvent) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_RESULT, new ParcelableResult(getParcelableFailureFromList(testRunFinishedEvent.failures)));
        bundle.putString(OrchestrationListenerManager.KEY_TEST_EVENT, OrchestrationListenerManager.TestEvent.TEST_RUN_FINISHED.name());
        return bundle;
    }

    private static List<ParcelableFailure> getParcelableFailureFromList(List<FailureInfo> list) {
        ArrayList arrayList = new ArrayList();
        for (FailureInfo next : list) {
            arrayList.add(new ParcelableFailure(new ParcelableDescription(next.testCase.getClassAndMethodName()), next.stackTrace));
        }
        return arrayList;
    }
}
