package androidx.test.internal.runner.junit3;

import java.util.Enumeration;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.Ignore;

@Ignore
class DelegatingTestSuite extends TestSuite {
    private TestSuite wrappedSuite;

    public DelegatingTestSuite(TestSuite testSuite) {
        this.wrappedSuite = testSuite;
    }

    public TestSuite getDelegateSuite() {
        return this.wrappedSuite;
    }

    public void setDelegateSuite(TestSuite testSuite) {
        this.wrappedSuite = testSuite;
    }

    public void addTest(Test test) {
        this.wrappedSuite.addTest(test);
    }

    public int countTestCases() {
        return this.wrappedSuite.countTestCases();
    }

    public String getName() {
        return this.wrappedSuite.getName();
    }

    public void runTest(Test test, TestResult testResult) {
        this.wrappedSuite.runTest(test, testResult);
    }

    public void setName(String str) {
        this.wrappedSuite.setName(str);
    }

    public Test testAt(int i) {
        return this.wrappedSuite.testAt(i);
    }

    public int testCount() {
        return this.wrappedSuite.testCount();
    }

    public Enumeration<Test> tests() {
        return this.wrappedSuite.tests();
    }

    public String toString() {
        return this.wrappedSuite.toString();
    }

    public void run(TestResult testResult) {
        this.wrappedSuite.run(testResult);
    }
}
