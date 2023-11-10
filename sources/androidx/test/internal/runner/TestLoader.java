package androidx.test.internal.runner;

import android.util.Log;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.RunnerBuilder;

class TestLoader {
    private static final String LOG_TAG = "TestLoader";
    private final ClassLoader classLoader;
    private final RunnerBuilder runnerBuilder;
    private final Map<String, Runner> runnersMap = new LinkedHashMap();

    static TestLoader testLoader(ClassLoader classLoader2, RunnerBuilder runnerBuilder2, boolean z) {
        if (z) {
            runnerBuilder2 = new ScanningRunnerBuilder(runnerBuilder2);
        }
        if (classLoader2 == null) {
            classLoader2 = TestLoader.class.getClassLoader();
        }
        return new TestLoader(classLoader2, runnerBuilder2);
    }

    private TestLoader(ClassLoader classLoader2, RunnerBuilder runnerBuilder2) {
        this.classLoader = classLoader2;
        this.runnerBuilder = runnerBuilder2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void doCreateRunner(java.lang.String r8, boolean r9) {
        /*
            r7 = this;
            java.util.Map<java.lang.String, org.junit.runner.Runner> r0 = r7.runnersMap
            boolean r0 = r0.containsKey(r8)
            if (r0 == 0) goto L_0x0009
            return
        L_0x0009:
            r0 = 0
            r1 = 1
            r2 = 0
            java.lang.ClassLoader r3 = r7.classLoader     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            java.lang.Class r3 = java.lang.Class.forName(r8, r2, r3)     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            org.junit.runners.model.RunnerBuilder r4 = r7.runnerBuilder     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            org.junit.runner.Runner r4 = r4.safeRunnerForClass(r3)     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            if (r4 != 0) goto L_0x002c
            java.lang.String r5 = "Skipping class %s: not a test"
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            java.lang.String r3 = r3.getName()     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            r6[r2] = r3     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            java.lang.String r3 = java.lang.String.format(r5, r6)     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            logDebug(r3)     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            goto L_0x0042
        L_0x002c:
            org.junit.runner.Runner r5 = androidx.test.internal.runner.junit3.AndroidJUnit3Builder.NOT_A_VALID_TEST     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            if (r4 != r5) goto L_0x0042
            java.lang.String r4 = "Skipping class %s: not a valid test"
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            java.lang.String r3 = r3.getName()     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            r5[r2] = r3     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            java.lang.String r3 = java.lang.String.format(r4, r5)     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            logDebug(r3)     // Catch:{ ClassNotFoundException -> 0x0046, LinkageError -> 0x0044 }
            goto L_0x0068
        L_0x0042:
            r0 = r4
            goto L_0x0068
        L_0x0044:
            r3 = move-exception
            goto L_0x0047
        L_0x0046:
            r3 = move-exception
        L_0x0047:
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r1[r2] = r8
            java.lang.String r4 = "Could not find class: %s"
            java.lang.String r1 = java.lang.String.format(r4, r1)
            java.lang.String r4 = "TestLoader"
            android.util.Log.e(r4, r1)
            java.lang.annotation.Annotation[] r1 = new java.lang.annotation.Annotation[r2]
            org.junit.runner.Description r1 = org.junit.runner.Description.createSuiteDescription(r8, r1)
            org.junit.runner.notification.Failure r2 = new org.junit.runner.notification.Failure
            r2.<init>(r1, r3)
            if (r9 != 0) goto L_0x0068
            androidx.test.internal.runner.TestLoader$UnloadableClassRunner r0 = new androidx.test.internal.runner.TestLoader$UnloadableClassRunner
            r0.<init>(r1, r2)
        L_0x0068:
            if (r0 == 0) goto L_0x006f
            java.util.Map<java.lang.String, org.junit.runner.Runner> r9 = r7.runnersMap
            r9.put(r8, r0)
        L_0x006f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.internal.runner.TestLoader.doCreateRunner(java.lang.String, boolean):void");
    }

    /* access modifiers changed from: package-private */
    public List<Runner> getRunnersFor(Collection<String> collection, boolean z) {
        for (String doCreateRunner : collection) {
            doCreateRunner(doCreateRunner, z);
        }
        return new ArrayList(this.runnersMap.values());
    }

    /* access modifiers changed from: private */
    public static void logDebug(String str) {
        if (Log.isLoggable(LOG_TAG, 3)) {
            Log.d(LOG_TAG, str);
        }
    }

    private static class ScanningRunnerBuilder extends RunnerBuilder {
        private final RunnerBuilder runnerBuilder;

        ScanningRunnerBuilder(RunnerBuilder runnerBuilder2) {
            this.runnerBuilder = runnerBuilder2;
        }

        public Runner runnerForClass(Class<?> cls) throws Throwable {
            if (!Modifier.isAbstract(cls.getModifiers())) {
                return this.runnerBuilder.runnerForClass(cls);
            }
            TestLoader.logDebug(String.format("Skipping abstract class %s: not a test", new Object[]{cls.getName()}));
            return null;
        }
    }

    static class UnloadableClassRunner extends Runner {
        private final Description description;
        private final Failure failure;

        UnloadableClassRunner(Description description2, Failure failure2) {
            this.description = description2;
            this.failure = failure2;
        }

        public Description getDescription() {
            return this.description;
        }

        public void run(RunNotifier runNotifier) {
            runNotifier.fireTestStarted(this.description);
            runNotifier.fireTestFailure(this.failure);
            runNotifier.fireTestFinished(this.description);
        }
    }
}
