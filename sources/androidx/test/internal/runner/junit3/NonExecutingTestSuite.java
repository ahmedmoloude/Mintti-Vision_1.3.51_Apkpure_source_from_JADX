package androidx.test.internal.runner.junit3;

import java.util.Enumeration;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.Ignore;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;

@Ignore
public class NonExecutingTestSuite extends DelegatingFilterableTestSuite {
    public /* bridge */ /* synthetic */ void addTest(Test test) {
        super.addTest(test);
    }

    public /* bridge */ /* synthetic */ int countTestCases() {
        return super.countTestCases();
    }

    public /* bridge */ /* synthetic */ void filter(Filter filter) throws NoTestsRemainException {
        super.filter(filter);
    }

    public /* bridge */ /* synthetic */ TestSuite getDelegateSuite() {
        return super.getDelegateSuite();
    }

    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    public /* bridge */ /* synthetic */ void runTest(Test test, TestResult testResult) {
        super.runTest(test, testResult);
    }

    public /* bridge */ /* synthetic */ void setDelegateSuite(TestSuite testSuite) {
        super.setDelegateSuite(testSuite);
    }

    public /* bridge */ /* synthetic */ void setName(String str) {
        super.setName(str);
    }

    public /* bridge */ /* synthetic */ Test testAt(int i) {
        return super.testAt(i);
    }

    public /* bridge */ /* synthetic */ int testCount() {
        return super.testCount();
    }

    public /* bridge */ /* synthetic */ Enumeration tests() {
        return super.tests();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public NonExecutingTestSuite(Class<?> cls) {
        this(new TestSuite(cls));
    }

    public NonExecutingTestSuite(TestSuite testSuite) {
        super(testSuite);
    }

    public void run(TestResult testResult) {
        super.run(new NonExecutingTestResult(testResult));
    }
}
