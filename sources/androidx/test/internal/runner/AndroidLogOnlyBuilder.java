package androidx.test.internal.runner;

import androidx.test.internal.runner.junit3.JUnit38ClassRunner;
import androidx.test.internal.runner.junit3.NonExecutingTestSuite;
import androidx.test.internal.util.AndroidRunnerBuilderUtil;
import androidx.test.internal.util.AndroidRunnerParams;
import androidx.test.internal.util.Checks;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.internal.runners.ErrorReportingRunner;
import org.junit.internal.runners.SuiteMethod;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

class AndroidLogOnlyBuilder extends RunnerBuilder {
    private final AndroidRunnerBuilder builder;
    private int runnerCount = 0;
    private final AndroidRunnerParams runnerParams;
    private final boolean scanningPath;

    AndroidLogOnlyBuilder(AndroidRunnerParams androidRunnerParams, boolean z, List<Class<? extends RunnerBuilder>> list) {
        this.runnerParams = (AndroidRunnerParams) Checks.checkNotNull(androidRunnerParams, "runnerParams cannot be null!");
        this.scanningPath = z;
        this.builder = new AndroidRunnerBuilder(this, androidRunnerParams, z, list);
    }

    public Runner runnerForClass(Class<?> cls) throws Throwable {
        this.runnerCount++;
        if (AndroidRunnerBuilderUtil.isJUnit3Test(cls)) {
            if (!this.scanningPath || AndroidRunnerBuilderUtil.hasJUnit3TestMethod(cls)) {
                return new JUnit38ClassRunner((Test) new NonExecutingTestSuite(cls));
            }
            return null;
        } else if (!AndroidRunnerBuilderUtil.hasSuiteMethod(cls)) {
            int i = this.runnerCount;
            Runner runnerForClass = this.builder.runnerForClass(cls);
            if (runnerForClass == null) {
                return null;
            }
            if (!(runnerForClass instanceof ErrorReportingRunner) && this.runnerCount <= i) {
                return new NonExecutingRunner(runnerForClass);
            }
            return runnerForClass;
        } else if (this.runnerParams.isIgnoreSuiteMethods()) {
            return null;
        } else {
            Test testFromSuiteMethod = SuiteMethod.testFromSuiteMethod(cls);
            if (testFromSuiteMethod instanceof TestSuite) {
                return new JUnit38ClassRunner((Test) new NonExecutingTestSuite((TestSuite) testFromSuiteMethod));
            }
            throw new IllegalArgumentException(String.valueOf(cls.getName()).concat("#suite() did not return a TestSuite"));
        }
    }
}
