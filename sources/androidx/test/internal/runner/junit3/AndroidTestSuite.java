package androidx.test.internal.runner.junit3;

import android.os.Looper;
import android.util.Log;
import androidx.test.internal.util.AndroidRunnerParams;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.Ignore;

@Ignore
class AndroidTestSuite extends DelegatingFilterableTestSuite {
    private static final String TAG = "AndroidTestSuite";
    private final AndroidRunnerParams androidRunnerParams;

    public AndroidTestSuite(Class<?> cls, AndroidRunnerParams androidRunnerParams2) {
        this((TestSuite) new NonLeakyTestSuite(cls), androidRunnerParams2);
    }

    public AndroidTestSuite(TestSuite testSuite, AndroidRunnerParams androidRunnerParams2) {
        super(testSuite);
        this.androidRunnerParams = androidRunnerParams2;
    }

    public void run(TestResult testResult) {
        AndroidTestResult androidTestResult = new AndroidTestResult(this.androidRunnerParams.getBundle(), this.androidRunnerParams.getInstrumentation(), testResult);
        long perTestTimeout = this.androidRunnerParams.getPerTestTimeout();
        if (perTestTimeout > 0) {
            runTestsWithTimeout(perTestTimeout, androidTestResult);
        } else {
            super.run(androidTestResult);
        }
    }

    private void runTestsWithTimeout(long j, AndroidTestResult androidTestResult) {
        int testCount = testCount();
        for (int i = 0; i < testCount; i++) {
            runTestWithTimeout(testAt(i), androidTestResult, j);
        }
    }

    private void runTestWithTimeout(final Test test, final AndroidTestResult androidTestResult, long j) {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor(new ThreadFactory(this) {
            public Thread newThread(Runnable runnable) {
                Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
                newThread.setName(AndroidTestSuite.TAG);
                return newThread;
            }
        });
        C08042 r3 = new Runnable(this) {
            public void run() {
                test.run(androidTestResult);
            }
        };
        androidTestResult.setCurrentTimeout(j);
        Future<?> submit = newSingleThreadExecutor.submit(r3);
        newSingleThreadExecutor.shutdown();
        try {
            if (!newSingleThreadExecutor.awaitTermination(j, TimeUnit.MILLISECONDS)) {
                newSingleThreadExecutor.shutdownNow();
                if (!newSingleThreadExecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                    Log.e(TAG, "Failed to to stop test execution thread, the correctness of the test runner is at risk. Abort all execution!");
                    try {
                        submit.get(0, TimeUnit.MILLISECONDS);
                    } catch (ExecutionException e) {
                        Log.e(TAG, "Exception from the execution thread", e.getCause());
                    } catch (TimeoutException e2) {
                        Log.e(TAG, "Exception from the execution thread", e2);
                    }
                    terminateAllRunnerExecution(new IllegalStateException(String.format("Test timed out after %d milliseconds but execution thread failed to terminate\nDumping instr and main threads:\n%s", new Object[]{Long.valueOf(j), getStackTraces()})));
                }
            }
        } catch (InterruptedException e3) {
            Log.e(TAG, "The correctness of the test runner is at risk. Abort all execution!");
            terminateAllRunnerExecution(new IllegalStateException(String.format("Test execution thread got interrupted:\n%s\nDumping instr and main threads:\n%s", new Object[]{e3, getStackTraces()})));
        }
    }

    private void terminateAllRunnerExecution(final RuntimeException runtimeException) {
        Thread thread = new Thread(new Runnable(this) {
            public void run() {
                throw runtimeException;
            }
        }, "Terminator");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException unused) {
        }
    }

    private String getStackTraces() {
        StringBuilder sb = new StringBuilder();
        Thread currentThread = Thread.currentThread();
        sb.append(currentThread.toString());
        sb.append(10);
        for (StackTraceElement stackTraceElement : currentThread.getStackTrace()) {
            sb.append("\tat ");
            sb.append(stackTraceElement.toString());
            sb.append(10);
        }
        sb.append(10);
        Thread thread = Looper.getMainLooper().getThread();
        sb.append(thread.toString());
        sb.append(10);
        for (StackTraceElement stackTraceElement2 : thread.getStackTrace()) {
            sb.append("\tat ");
            sb.append(stackTraceElement2.toString());
            sb.append(10);
        }
        sb.append(10);
        return sb.toString();
    }
}
