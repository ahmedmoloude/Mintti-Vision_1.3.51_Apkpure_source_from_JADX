package androidx.test.internal.runner.junit4;

import androidx.test.internal.runner.RunnerArgs;
import androidx.test.internal.runner.junit4.statement.RunAfters;
import androidx.test.internal.runner.junit4.statement.RunBefores;
import androidx.test.internal.runner.junit4.statement.UiThreadStatement;
import androidx.test.internal.util.AndroidRunnerParams;
import androidx.test.platform.app.InstrumentationRegistry;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class AndroidJUnit4ClassRunner extends BlockJUnit4ClassRunner {
    private final AndroidRunnerParams androidRunnerParams;

    public AndroidJUnit4ClassRunner(Class<?> cls, AndroidRunnerParams androidRunnerParams2) throws InitializationError {
        super(cls);
        this.androidRunnerParams = androidRunnerParams2;
    }

    public AndroidJUnit4ClassRunner(Class<?> cls) throws InitializationError {
        this(cls, createRunnerParams());
    }

    private static AndroidRunnerParams createRunnerParams() {
        return new AndroidRunnerParams(InstrumentationRegistry.getInstrumentation(), InstrumentationRegistry.getArguments(), new RunnerArgs.Builder().fromBundle(InstrumentationRegistry.getInstrumentation(), InstrumentationRegistry.getArguments()).build().testTimeout, false);
    }

    /* access modifiers changed from: protected */
    public Statement methodInvoker(FrameworkMethod frameworkMethod, Object obj) {
        if (UiThreadStatement.shouldRunOnUiThread(frameworkMethod)) {
            return new UiThreadStatement(super.methodInvoker(frameworkMethod, obj), true);
        }
        return super.methodInvoker(frameworkMethod, obj);
    }

    /* access modifiers changed from: protected */
    public Statement withBefores(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        List<FrameworkMethod> annotatedMethods = getTestClass().getAnnotatedMethods(Before.class);
        return annotatedMethods.isEmpty() ? statement : new RunBefores(frameworkMethod, statement, annotatedMethods, obj);
    }

    /* access modifiers changed from: protected */
    public Statement withAfters(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        List<FrameworkMethod> annotatedMethods = getTestClass().getAnnotatedMethods(After.class);
        return annotatedMethods.isEmpty() ? statement : new RunAfters(frameworkMethod, statement, annotatedMethods, obj);
    }

    /* access modifiers changed from: protected */
    public Statement withPotentialTimeout(FrameworkMethod frameworkMethod, Object obj, Statement statement) {
        long timeout = getTimeout((Test) frameworkMethod.getAnnotation(Test.class));
        if (timeout <= 0 && this.androidRunnerParams.getPerTestTimeout() > 0) {
            timeout = this.androidRunnerParams.getPerTestTimeout();
        }
        if (timeout <= 0) {
            return statement;
        }
        return new FailOnTimeout(statement, timeout);
    }

    private long getTimeout(Test test) {
        if (test == null) {
            return 0;
        }
        return test.timeout();
    }
}
