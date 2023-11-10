package androidx.test.internal.runner.listener;

import android.util.Log;
import androidx.test.internal.runner.TestSize;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

public class SuiteAssignmentPrinter extends InstrumentationRunListener {
    long endTime;
    long startTime;
    boolean timingValid;

    public void testStarted(Description description) throws Exception {
        this.timingValid = true;
        this.startTime = getCurrentTimeMillis();
    }

    public void testFinished(Description description) throws Exception {
        long currentTimeMillis = getCurrentTimeMillis();
        this.endTime = currentTimeMillis;
        if (this.timingValid) {
            long j = this.startTime;
            if (j >= 0) {
                long j2 = currentTimeMillis - j;
                TestSize testSizeForRunTime = TestSize.getTestSizeForRunTime((float) j2);
                TestSize fromDescription = TestSize.fromDescription(description);
                if (!testSizeForRunTime.equals(fromDescription)) {
                    sendString(String.format("\n%s#%s: current size: %s. suggested: %s runTime: %d ms\n", new Object[]{description.getClassName(), description.getMethodName(), fromDescription, testSizeForRunTime.getSizeQualifierName(), Long.valueOf(j2)}));
                } else {
                    sendString(".");
                    Log.d("SuiteAssignmentPrinter", String.format("%s#%s assigned correctly as %s. runTime: %d ms\n", new Object[]{description.getClassName(), description.getMethodName(), testSizeForRunTime.getSizeQualifierName(), Long.valueOf(j2)}));
                }
                this.startTime = -1;
            }
        }
        sendString("F");
        Log.d("SuiteAssignmentPrinter", String.format("%s#%s: skipping suite assignment due to test failure\n", new Object[]{description.getClassName(), description.getMethodName()}));
        this.startTime = -1;
    }

    public void testFailure(Failure failure) throws Exception {
        this.timingValid = false;
    }

    public void testAssumptionFailure(Failure failure) {
        this.timingValid = false;
    }

    public void testIgnored(Description description) throws Exception {
        this.timingValid = false;
    }

    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
