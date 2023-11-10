package androidx.test.runner;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import androidx.test.internal.events.client.TestEventClient;
import androidx.test.internal.events.client.TestEventClientArgs;
import androidx.test.internal.events.client.TestEventClientConnectListener;
import androidx.test.internal.runner.ClassPathScanner;
import androidx.test.internal.runner.RunnerArgs;
import androidx.test.internal.runner.TestExecutor;
import androidx.test.internal.runner.TestRequestBuilder;
import androidx.test.internal.runner.listener.ActivityFinisherRunListener;
import androidx.test.internal.runner.listener.CoverageListener;
import androidx.test.internal.runner.listener.DelayInjector;
import androidx.test.internal.runner.listener.InstrumentationResultPrinter;
import androidx.test.internal.runner.listener.LogRunListener;
import androidx.test.internal.runner.listener.SuiteAssignmentPrinter;
import androidx.test.internal.runner.storage.RunnerFileIO;
import androidx.test.internal.runner.storage.RunnerIO;
import androidx.test.internal.runner.storage.RunnerTestStorageIO;
import androidx.test.internal.runner.tracker.AnalyticsBasedUsageTracker;
import androidx.test.internal.util.ReflectionUtil;
import androidx.test.runner.MonitoringInstrumentation;
import androidx.test.runner.lifecycle.ApplicationLifecycleCallback;
import androidx.test.runner.lifecycle.ApplicationLifecycleMonitorRegistry;
import androidx.test.runner.screenshot.Screenshot;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;
import org.junit.runner.Request;
import org.junit.runner.notification.RunListener;

public class AndroidJUnitRunner extends MonitoringInstrumentation implements TestEventClientConnectListener {
    private static final String LOG_TAG = "AndroidJUnitRunner";
    private static final long MILLIS_TO_WAIT_FOR_TEST_FINISH = TimeUnit.SECONDS.toMillis(20);
    private Bundle arguments;
    private InstrumentationResultPrinter instrumentationResultPrinter = new InstrumentationResultPrinter();
    private RunnerArgs runnerArgs;
    private RunnerIO runnerIO = new RunnerFileIO();
    private TestEventClient testEventClient = TestEventClient.NO_OP_CLIENT;
    private UsageTrackerFacilitator usageTrackerFacilitator;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.arguments = bundle;
        parseRunnerArgs(bundle);
        if (waitForDebugger(this.runnerArgs)) {
            Log.i("AndroidJUnitRunner", "Waiting for debugger to connect...");
            Debug.waitForDebugger();
            Log.i("AndroidJUnitRunner", "Debugger connected.");
        }
        if (isPrimaryInstrProcess(this.runnerArgs.targetProcess)) {
            this.usageTrackerFacilitator = new UsageTrackerFacilitator(this.runnerArgs);
        } else {
            this.usageTrackerFacilitator = new UsageTrackerFacilitator(false);
        }
        for (ApplicationLifecycleCallback addLifecycleCallback : this.runnerArgs.appListeners) {
            ApplicationLifecycleMonitorRegistry.getInstance().addLifecycleCallback(addLifecycleCallback);
        }
        addScreenCaptureProcessors(this.runnerArgs);
        if (isOrchestratorServiceProvided()) {
            Log.v("AndroidJUnitRunner", "Waiting to connect to the Orchestrator service...");
        } else {
            start();
        }
    }

    private boolean isOrchestratorServiceProvided() {
        TestEventClient connect = TestEventClient.connect(getContext(), this, TestEventClientArgs.builder().setConnectionFactory(AndroidJUnitRunner$$Lambda$0.$instance).setOrchestratorService(this.runnerArgs.orchestratorService).setPrimaryInstProcess(isPrimaryInstrProcess(this.runnerArgs.targetProcess)).setTestDiscoveryRequested(this.runnerArgs.listTestsForOrchestrator).setTestRunEventsRequested(!this.runnerArgs.listTestsForOrchestrator).setTestDiscoveryService(this.runnerArgs.testDiscoveryService).setTestRunEventService(this.runnerArgs.testRunEventsService).setTestPlatformMigration(this.runnerArgs.testPlatformMigration).build());
        this.testEventClient = connect;
        if (connect.isTestDiscoveryEnabled() || this.testEventClient.isTestRunEventsEnabled()) {
            return true;
        }
        return false;
    }

    private boolean waitForDebugger(RunnerArgs runnerArgs2) {
        return runnerArgs2.debug && !runnerArgs2.listTestsForOrchestrator;
    }

    @Deprecated
    public void onOrchestratorConnect() {
        onTestEventClientConnect();
    }

    public void onTestEventClientConnect() {
        start();
    }

    private void parseRunnerArgs(Bundle bundle) {
        this.runnerArgs = new RunnerArgs.Builder().fromManifest(this).fromBundle(this, bundle).build();
    }

    private Bundle getArguments() {
        return this.arguments;
    }

    /* access modifiers changed from: package-private */
    public InstrumentationResultPrinter getInstrumentationResultPrinter() {
        return this.instrumentationResultPrinter;
    }

    public void onStart() {
        setJsBridgeClassName("androidx.test.espresso.web.bridge.JavaScriptBridge");
        super.onStart();
        Request buildRequest = buildRequest(this.runnerArgs, getArguments());
        if (this.testEventClient.isTestDiscoveryEnabled()) {
            this.testEventClient.addTests(buildRequest.getRunner().getDescription());
            finish(-1, new Bundle());
            return;
        }
        if (this.runnerArgs.remoteMethod != null) {
            ReflectionUtil.reflectivelyInvokeRemoteMethod(this.runnerArgs.remoteMethod.testClassName, this.runnerArgs.remoteMethod.methodName);
        }
        if (!isPrimaryInstrProcess(this.runnerArgs.targetProcess)) {
            Log.i("AndroidJUnitRunner", "Runner is idle...");
            return;
        }
        if (this.runnerArgs.useTestStorageService) {
            this.runnerIO = new RunnerTestStorageIO();
        }
        Bundle bundle = new Bundle();
        try {
            TestExecutor.Builder builder = new TestExecutor.Builder(this);
            addListeners(this.runnerArgs, builder);
            bundle = builder.build().execute(buildRequest);
        } catch (RuntimeException e) {
            Log.e("AndroidJUnitRunner", "Fatal exception when running tests", e);
            String valueOf = String.valueOf(Log.getStackTraceString(e));
            bundle.putString("stream", valueOf.length() != 0 ? "Fatal exception when running tests\n".concat(valueOf) : new String("Fatal exception when running tests\n"));
        }
        finish(-1, bundle);
    }

    public void finish(int i, Bundle bundle) {
        try {
            this.usageTrackerFacilitator.trackUsage("AndroidJUnitRunner", "1.4.0");
            this.usageTrackerFacilitator.sendUsages();
        } catch (RuntimeException e) {
            Log.w("AndroidJUnitRunner", "Failed to send analytics.", e);
        }
        super.finish(i, bundle);
    }

    /* access modifiers changed from: package-private */
    public final void addListeners(RunnerArgs runnerArgs2, TestExecutor.Builder builder) {
        if (runnerArgs2.newRunListenerMode) {
            addListenersNewOrder(runnerArgs2, builder);
        } else {
            addListenersLegacyOrder(runnerArgs2, builder);
        }
    }

    private void addListenersLegacyOrder(RunnerArgs runnerArgs2, TestExecutor.Builder builder) {
        if (runnerArgs2.logOnly) {
            builder.addRunListener(getInstrumentationResultPrinter());
        } else if (runnerArgs2.suiteAssignment) {
            builder.addRunListener(new SuiteAssignmentPrinter());
        } else {
            builder.addRunListener(new LogRunListener());
            if (this.testEventClient.isTestRunEventsEnabled()) {
                builder.addRunListener(this.testEventClient.getRunListener());
            } else {
                builder.addRunListener(getInstrumentationResultPrinter());
            }
            if (shouldWaitForActivitiesToComplete()) {
                builder.addRunListener(new ActivityFinisherRunListener(this, new MonitoringInstrumentation.ActivityFinisher(), new Runnable() {
                    public void run() {
                        AndroidJUnitRunner.this.waitForActivitiesToComplete();
                    }
                }));
            }
            addDelayListener(runnerArgs2, builder);
            addCoverageListener(runnerArgs2, builder);
        }
        addListenersFromClasspath(builder);
        addListenersFromArg(runnerArgs2, builder);
    }

    private void addListenersNewOrder(RunnerArgs runnerArgs2, TestExecutor.Builder builder) {
        addListenersFromClasspath(builder);
        addListenersFromArg(runnerArgs2, builder);
        if (runnerArgs2.logOnly) {
            builder.addRunListener(getInstrumentationResultPrinter());
        } else if (runnerArgs2.suiteAssignment) {
            builder.addRunListener(new SuiteAssignmentPrinter());
        } else {
            builder.addRunListener(new LogRunListener());
            addDelayListener(runnerArgs2, builder);
            addCoverageListener(runnerArgs2, builder);
            if (this.testEventClient.isTestRunEventsEnabled()) {
                builder.addRunListener(this.testEventClient.getRunListener());
            } else {
                builder.addRunListener(getInstrumentationResultPrinter());
            }
            if (shouldWaitForActivitiesToComplete()) {
                builder.addRunListener(new ActivityFinisherRunListener(this, new MonitoringInstrumentation.ActivityFinisher(), new Runnable() {
                    public void run() {
                        AndroidJUnitRunner.this.waitForActivitiesToComplete();
                    }
                }));
            }
        }
    }

    private void addScreenCaptureProcessors(RunnerArgs runnerArgs2) {
        Screenshot.addScreenCaptureProcessors(new HashSet(runnerArgs2.screenCaptureProcessors));
    }

    private void addCoverageListener(RunnerArgs runnerArgs2, TestExecutor.Builder builder) {
        if (runnerArgs2.codeCoverage) {
            builder.addRunListener(new CoverageListener(runnerArgs2.codeCoveragePath, this.runnerIO));
        }
    }

    private void addDelayListener(RunnerArgs runnerArgs2, TestExecutor.Builder builder) {
        if (runnerArgs2.delayInMillis > 0) {
            builder.addRunListener(new DelayInjector(runnerArgs2.delayInMillis));
        } else if (runnerArgs2.logOnly && Build.VERSION.SDK_INT < 16) {
            builder.addRunListener(new DelayInjector(15));
        }
    }

    private static void addListenersFromClasspath(TestExecutor.Builder builder) {
        Iterator<S> it = ServiceLoader.load(RunListener.class).iterator();
        while (it.hasNext()) {
            builder.addRunListener((RunListener) it.next());
        }
    }

    private void addListenersFromArg(RunnerArgs runnerArgs2, TestExecutor.Builder builder) {
        for (RunListener addRunListener : runnerArgs2.listeners) {
            builder.addRunListener(addRunListener);
        }
    }

    public boolean onException(Object obj, Throwable th) {
        Log.e("AndroidJUnitRunner", "An unhandled exception was thrown by the app.");
        InstrumentationResultPrinter instrumentationResultPrinter2 = getInstrumentationResultPrinter();
        if (instrumentationResultPrinter2 != null) {
            instrumentationResultPrinter2.reportProcessCrash(th);
        }
        if (this.testEventClient.isTestRunEventsEnabled()) {
            this.testEventClient.reportProcessCrash(th, MILLIS_TO_WAIT_FOR_TEST_FINISH);
        }
        Log.i("AndroidJUnitRunner", "Bringing down the entire Instrumentation process.");
        return super.onException(obj, th);
    }

    /* access modifiers changed from: package-private */
    public Request buildRequest(RunnerArgs runnerArgs2, Bundle bundle) {
        TestRequestBuilder createTestRequestBuilder = createTestRequestBuilder(this, bundle);
        createTestRequestBuilder.addPathsToScan(runnerArgs2.classpathToScan);
        if (runnerArgs2.classpathToScan.isEmpty()) {
            createTestRequestBuilder.addPathsToScan(ClassPathScanner.getDefaultClasspaths(this));
        }
        createTestRequestBuilder.addFromRunnerArgs(runnerArgs2);
        registerUserTracker();
        return createTestRequestBuilder.build();
    }

    private void registerUserTracker() {
        Context targetContext = getTargetContext();
        if (targetContext != null) {
            this.usageTrackerFacilitator.registerUsageTracker(new AnalyticsBasedUsageTracker.Builder(targetContext).buildIfPossible());
        }
    }

    /* access modifiers changed from: package-private */
    public TestRequestBuilder createTestRequestBuilder(Instrumentation instrumentation, Bundle bundle) {
        return new TestRequestBuilder(instrumentation, bundle);
    }
}
