package androidx.test.internal.events.client;

import android.util.Log;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.ParcelableConverter;
import androidx.test.services.events.TestEventException;
import androidx.test.services.events.discovery.TestDiscoveryFinishedEvent;
import androidx.test.services.events.discovery.TestDiscoveryStartedEvent;
import androidx.test.services.events.discovery.TestFoundEvent;
import java.util.Iterator;
import org.junit.runner.Description;

public final class TestDiscovery {
    private static final String TAG = "TestDiscovery";
    private final TestDiscoveryEventService testDiscoveryEventService;

    public TestDiscovery(TestDiscoveryEventService testDiscoveryEventService2) {
        this.testDiscoveryEventService = (TestDiscoveryEventService) Checks.checkNotNull(testDiscoveryEventService2, "testDiscoveryEventService can't be null");
    }

    public void addTests(Description description) throws TestEventClientException {
        Checks.checkNotNull(description, "description cannot be null");
        this.testDiscoveryEventService.send(new TestDiscoveryStartedEvent());
        addTest(description);
        this.testDiscoveryEventService.send(new TestDiscoveryFinishedEvent());
    }

    private void addTest(Description description) {
        if (description.isEmpty()) {
            Log.d(TAG, "addTest called with an empty test description");
        } else if (!description.isTest()) {
            Iterator<Description> it = description.getChildren().iterator();
            while (it.hasNext()) {
                addTest(it.next());
            }
        } else if (!JUnitValidator.validateDescription(description)) {
            String className = description.getClassName();
            String methodName = description.getMethodName();
            StringBuilder sb = new StringBuilder(String.valueOf(className).length() + 38 + String.valueOf(methodName).length());
            sb.append("JUnit reported ");
            sb.append(className);
            sb.append("#");
            sb.append(methodName);
            sb.append("; discarding as bogus.");
            Log.w(TAG, sb.toString());
        } else {
            try {
                this.testDiscoveryEventService.send(new TestFoundEvent(ParcelableConverter.getTestCaseFromDescription(description)));
            } catch (TestEventException e) {
                Log.e(TAG, "Failed to get test description", e);
            }
        }
    }
}
