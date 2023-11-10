package androidx.test.internal.platform.util;

import androidx.test.internal.platform.ServiceLoaderWrapper;
import java.io.Serializable;
import java.util.Map;

public class TestOutputEmitter {
    private static final TestOutputHandler debugHandler = ((TestOutputHandler) ServiceLoaderWrapper.loadSingleService(TestOutputHandler.class, TestOutputEmitter$$Lambda$0.$instance));

    static final /* synthetic */ TestOutputHandler lambda$static$0$TestOutputEmitter() {
        return new TestOutputHandler() {
            public boolean addOutputProperties(Map<String, Serializable> map) {
                return false;
            }

            public boolean captureWindowHierarchy(String str) {
                return false;
            }

            public void dumpThreadStates(String str) {
            }

            public boolean takeScreenshot(String str) {
                return false;
            }
        };
    }

    private TestOutputEmitter() {
    }

    public static void dumpThreadStates(String str) {
        debugHandler.dumpThreadStates(str);
    }

    public static boolean takeScreenshot(String str) {
        return debugHandler.takeScreenshot(str);
    }

    public static boolean captureWindowHierarchy(String str) {
        return debugHandler.captureWindowHierarchy(str);
    }

    public static boolean addOutputProperties(Map<String, Serializable> map) {
        return debugHandler.addOutputProperties(map);
    }
}
