package androidx.test.internal.runner.junit4;

import android.util.Log;
import androidx.test.internal.util.AndroidRunnerParams;
import java.lang.reflect.Method;
import org.junit.Test;
import org.junit.internal.builders.JUnit4Builder;
import org.junit.runner.Runner;

public class AndroidJUnit4Builder extends JUnit4Builder {
    private static final String TAG = "AndroidJUnit4Builder";
    private final AndroidRunnerParams androidRunnerParams;
    private final boolean scanningPath;

    public AndroidJUnit4Builder(AndroidRunnerParams androidRunnerParams2, boolean z) {
        this.androidRunnerParams = androidRunnerParams2;
        this.scanningPath = z;
    }

    @Deprecated
    public AndroidJUnit4Builder(AndroidRunnerParams androidRunnerParams2) {
        this(androidRunnerParams2, false);
    }

    public Runner runnerForClass(Class<?> cls) throws Throwable {
        try {
            if (!this.scanningPath || hasTestMethods(cls)) {
                return new AndroidJUnit4ClassRunner(cls, this.androidRunnerParams);
            }
            return null;
        } catch (Throwable th) {
            Log.e(TAG, "Error constructing runner", th);
            throw th;
        }
    }

    private static boolean hasTestMethods(Class<?> cls) {
        try {
            for (Method isAnnotationPresent : cls.getMethods()) {
                if (isAnnotationPresent.isAnnotationPresent(Test.class)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            Log.w(TAG, String.format("%s in hasTestMethods for %s", new Object[]{th.toString(), cls.getName()}));
            return false;
        }
    }
}
