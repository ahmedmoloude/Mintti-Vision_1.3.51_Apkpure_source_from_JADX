package androidx.test.internal.runner.junit3;

import android.util.Log;
import androidx.test.internal.util.AndroidRunnerParams;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.internal.builders.SuiteMethodBuilder;
import org.junit.internal.runners.SuiteMethod;
import org.junit.runner.Runner;

public class AndroidSuiteBuilder extends SuiteMethodBuilder {
    private static final String LOG_TAG = "AndroidSuiteBuilder";
    private final AndroidRunnerParams androidRunnerParams;

    public AndroidSuiteBuilder(AndroidRunnerParams androidRunnerParams2) {
        this.androidRunnerParams = androidRunnerParams2;
    }

    public Runner runnerForClass(Class<?> cls) throws Throwable {
        if (this.androidRunnerParams.isIgnoreSuiteMethods()) {
            return null;
        }
        try {
            if (!hasSuiteMethod(cls)) {
                return null;
            }
            Test testFromSuiteMethod = SuiteMethod.testFromSuiteMethod(cls);
            if (testFromSuiteMethod instanceof TestSuite) {
                return new JUnit38ClassRunner((Test) new AndroidTestSuite((TestSuite) testFromSuiteMethod, this.androidRunnerParams));
            }
            throw new IllegalArgumentException(String.valueOf(cls.getName()).concat("#suite() did not return a TestSuite"));
        } catch (Throwable th) {
            Log.e(LOG_TAG, "Error constructing runner", th);
            throw th;
        }
    }
}
