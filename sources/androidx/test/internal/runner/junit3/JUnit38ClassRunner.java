package androidx.test.internal.runner.junit3;

import java.lang.annotation.Annotation;
import junit.extensions.TestDecorator;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class JUnit38ClassRunner extends Runner implements Filterable, Sortable {
    private volatile Test fTest;

    private static final class OldTestClassAdaptingListener implements TestListener {
        private Test currentTest;
        private Description description;
        private final RunNotifier fNotifier;

        private OldTestClassAdaptingListener(RunNotifier runNotifier) {
            this.currentTest = null;
            this.description = null;
            this.fNotifier = runNotifier;
        }

        public void endTest(Test test) {
            this.fNotifier.fireTestFinished(asDescription(test));
        }

        public void startTest(Test test) {
            this.fNotifier.fireTestStarted(asDescription(test));
        }

        public void addError(Test test, Throwable th) {
            this.fNotifier.fireTestFailure(new Failure(asDescription(test), th));
        }

        private Description asDescription(Test test) {
            Description description2;
            Test test2 = this.currentTest;
            if (test2 != null && test2.equals(test) && (description2 = this.description) != null) {
                return description2;
            }
            this.currentTest = test;
            if (test instanceof Describable) {
                this.description = ((Describable) test).getDescription();
            } else if (test instanceof TestCase) {
                this.description = JUnit38ClassRunner.makeDescription(test);
            } else {
                this.description = Description.createTestDescription(getEffectiveClass(test), test.toString());
            }
            return this.description;
        }

        private Class<? extends Test> getEffectiveClass(Test test) {
            return test.getClass();
        }

        public void addFailure(Test test, AssertionFailedError assertionFailedError) {
            addError(test, assertionFailedError);
        }
    }

    public JUnit38ClassRunner(Class<?> cls) {
        this((Test) new TestSuite((Class<?>) cls.asSubclass(TestCase.class)));
    }

    public JUnit38ClassRunner(Test test) {
        setTest(test);
    }

    public void run(RunNotifier runNotifier) {
        TestResult testResult = new TestResult();
        testResult.addListener(createAdaptingListener(runNotifier));
        getTest().run(testResult);
    }

    public TestListener createAdaptingListener(RunNotifier runNotifier) {
        return new OldTestClassAdaptingListener(runNotifier);
    }

    public Description getDescription() {
        return makeDescription(getTest());
    }

    static Description makeDescription(Test test) {
        if (test instanceof TestCase) {
            TestCase testCase = (TestCase) test;
            return Description.createTestDescription(testCase.getClass(), testCase.getName(), getAnnotations(testCase));
        } else if (test instanceof TestSuite) {
            TestSuite testSuite = (TestSuite) test;
            Description createSuiteDescription = Description.createSuiteDescription(testSuite.getName() == null ? createSuiteDescription(testSuite) : testSuite.getName(), new Annotation[0]);
            int testCount = testSuite.testCount();
            for (int i = 0; i < testCount; i++) {
                createSuiteDescription.addChild(makeDescription(testSuite.testAt(i)));
            }
            return createSuiteDescription;
        } else if (test instanceof Describable) {
            return ((Describable) test).getDescription();
        } else {
            if (test instanceof TestDecorator) {
                return makeDescription(((TestDecorator) test).getTest());
            }
            return Description.createSuiteDescription(test.getClass());
        }
    }

    private static Annotation[] getAnnotations(TestCase testCase) {
        try {
            return testCase.getClass().getMethod(testCase.getName(), new Class[0]).getDeclaredAnnotations();
        } catch (NoSuchMethodException | SecurityException unused) {
            return new Annotation[0];
        }
    }

    private static String createSuiteDescription(TestSuite testSuite) {
        String str;
        int countTestCases = testSuite.countTestCases();
        if (countTestCases == 0) {
            str = "";
        } else {
            str = String.format(" [example: %s]", new Object[]{testSuite.testAt(0)});
        }
        return String.format("TestSuite with %s tests%s", new Object[]{Integer.valueOf(countTestCases), str});
    }

    public void filter(Filter filter) throws NoTestsRemainException {
        if (getTest() instanceof Filterable) {
            ((Filterable) getTest()).filter(filter);
        } else if (getTest() instanceof TestSuite) {
            TestSuite testSuite = (TestSuite) getTest();
            TestSuite testSuite2 = new TestSuite(testSuite.getName());
            int testCount = testSuite.testCount();
            for (int i = 0; i < testCount; i++) {
                Test testAt = testSuite.testAt(i);
                if (filter.shouldRun(makeDescription(testAt))) {
                    testSuite2.addTest(testAt);
                }
            }
            setTest(testSuite2);
            if (testSuite2.testCount() == 0) {
                throw new NoTestsRemainException();
            }
        }
    }

    public void sort(Sorter sorter) {
        if (getTest() instanceof Sortable) {
            ((Sortable) getTest()).sort(sorter);
        }
    }

    private void setTest(Test test) {
        this.fTest = test;
    }

    private Test getTest() {
        return this.fTest;
    }
}
