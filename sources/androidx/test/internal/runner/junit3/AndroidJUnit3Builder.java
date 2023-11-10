package androidx.test.internal.runner.junit3;

import android.util.Log;
import androidx.test.internal.util.AndroidRunnerBuilderUtil;
import androidx.test.internal.util.AndroidRunnerParams;
import junit.framework.Test;
import org.junit.internal.builders.JUnit3Builder;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class AndroidJUnit3Builder extends JUnit3Builder {
    public static final Runner NOT_A_VALID_TEST = new Runner() {
        public void run(RunNotifier runNotifier) {
        }

        public Description getDescription() {
            return Description.EMPTY;
        }
    };
    private static final String TAG = "AndroidJUnit3Builder";
    private final AndroidRunnerParams androidRunnerParams;
    private final boolean scanningPath;

    public AndroidJUnit3Builder(AndroidRunnerParams androidRunnerParams2, boolean z) {
        this.androidRunnerParams = androidRunnerParams2;
        this.scanningPath = z;
    }

    @Deprecated
    public AndroidJUnit3Builder(AndroidRunnerParams androidRunnerParams2) {
        this(androidRunnerParams2, false);
    }

    public Runner runnerForClass(Class<?> cls) throws Throwable {
        try {
            if (!AndroidRunnerBuilderUtil.isJUnit3Test(cls)) {
                return null;
            }
            if (!this.scanningPath || AndroidRunnerBuilderUtil.hasJUnit3TestMethod(cls)) {
                return new JUnit38ClassRunner((Test) new AndroidTestSuite(cls, this.androidRunnerParams));
            }
            return NOT_A_VALID_TEST;
        } catch (Throwable th) {
            Log.e(TAG, "Error constructing runner", th);
            throw th;
        }
    }
}
