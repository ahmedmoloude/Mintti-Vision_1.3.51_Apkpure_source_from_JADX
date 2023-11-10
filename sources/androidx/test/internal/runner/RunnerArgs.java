package androidx.test.internal.runner;

import android.app.Instrumentation;
import android.app.UiAutomation;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import androidx.test.runner.lifecycle.ApplicationLifecycleCallback;
import androidx.test.runner.screenshot.ScreenCaptureProcessor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.notification.RunListener;
import org.junit.runners.model.RunnerBuilder;

public class RunnerArgs {
    static final String ARGUMENT_ANNOTATION = "annotation";
    static final String ARGUMENT_APP_LISTENER = "appListener";
    static final String ARGUMENT_CLASSPATH_TO_SCAN = "classpathToScan";
    static final String ARGUMENT_CLASS_LOADER = "classLoader";
    static final String ARGUMENT_COVERAGE = "coverage";
    static final String ARGUMENT_COVERAGE_PATH = "coverageFile";
    static final String ARGUMENT_DEBUG = "debug";
    static final String ARGUMENT_DELAY_IN_MILLIS = "delay_msec";
    static final String ARGUMENT_DISABLE_ANALYTICS = "disableAnalytics";
    static final String ARGUMENT_FILTER = "filter";
    static final String ARGUMENT_LISTENER = "listener";
    static final String ARGUMENT_LIST_TESTS_FOR_ORCHESTRATOR = "listTestsForOrchestrator";
    static final String ARGUMENT_LOG_ONLY = "log";
    static final String ARGUMENT_NOT_ANNOTATION = "notAnnotation";
    static final String ARGUMENT_NOT_TEST_CLASS = "notClass";
    static final String ARGUMENT_NOT_TEST_FILE = "notTestFile";
    static final String ARGUMENT_NOT_TEST_PACKAGE = "notPackage";
    static final String ARGUMENT_NUM_SHARDS = "numShards";
    static final String ARGUMENT_ORCHESTRATOR_DISCOVERY_SERVICE = "testDiscoveryService";
    static final String ARGUMENT_ORCHESTRATOR_RUN_EVENTS_SERVICE = "testRunEventsService";
    static final String ARGUMENT_ORCHESTRATOR_SERVICE = "orchestratorService";
    static final String ARGUMENT_REMOTE_INIT_METHOD = "remoteMethod";
    static final String ARGUMENT_RUNNER_BUILDER = "runnerBuilder";
    static final String ARGUMENT_RUN_LISTENER_NEW_ORDER = "newRunListenerMode";
    static final String ARGUMENT_SCREENSHOT_PROCESSORS = "screenCaptureProcessors";
    static final String ARGUMENT_SHARD_INDEX = "shardIndex";
    static final String ARGUMENT_SHELL_EXEC_BINDER_KEY = "shellExecBinderKey";
    static final String ARGUMENT_SUITE_ASSIGNMENT = "suiteAssignment";
    static final String ARGUMENT_TARGET_PROCESS = "targetProcess";
    static final String ARGUMENT_TESTS_REGEX = "tests_regex";
    static final String ARGUMENT_TEST_CLASS = "class";
    static final String ARGUMENT_TEST_FILE = "testFile";
    static final String ARGUMENT_TEST_PACKAGE = "package";
    static final String ARGUMENT_TEST_PLATFORM_MIGRATION = "temporary_testPlatformMigration";
    static final String ARGUMENT_TEST_SIZE = "size";
    static final String ARGUMENT_TIMEOUT = "timeout_msec";
    static final String ARGUMENT_USE_TEST_STORAGE_SERVICE = "useTestStorageService";
    private static final String CLASSPATH_SEPARATOR = ":";
    private static final String CLASS_SEPARATOR = ",";
    private static final String LOG_TAG = "RunnerArgs";
    private static final char METHOD_SEPARATOR = '#';
    public final List<String> annotations;
    public final List<ApplicationLifecycleCallback> appListeners;
    public final ClassLoader classLoader;
    public final Set<String> classpathToScan;
    public final boolean codeCoverage;
    public final String codeCoveragePath;
    public final boolean debug;
    public final int delayInMillis;
    public final boolean disableAnalytics;
    public final List<Filter> filters;
    public final boolean listTestsForOrchestrator;
    public final List<RunListener> listeners;
    public final boolean logOnly;
    public final boolean newRunListenerMode;
    public final List<String> notAnnotations;
    public final List<String> notTestPackages;
    public final List<TestArg> notTests;
    public final int numShards;
    public final String orchestratorService;
    public final TestArg remoteMethod;
    public final List<Class<? extends RunnerBuilder>> runnerBuilderClasses;
    public final List<ScreenCaptureProcessor> screenCaptureProcessors;
    public final int shardIndex;
    public final String shellExecBinderKey;
    public final boolean suiteAssignment;
    public final String targetProcess;
    public final String testDiscoveryService;
    public final List<String> testPackages;
    public final boolean testPlatformMigration;
    public final String testRunEventsService;
    public final String testSize;
    public final long testTimeout;
    public final List<TestArg> tests;
    public final String testsRegEx;
    public final boolean useTestStorageService;

    public static class TestArg {
        public final String methodName;
        public final String testClassName;

        TestArg(String str, String str2) {
            this.testClassName = str;
            this.methodName = str2;
        }

        TestArg(String str) {
            this(str, (String) null);
        }

        public String toString() {
            String str = this.methodName;
            if (str == null) {
                return this.testClassName;
            }
            String str2 = this.testClassName;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 1 + String.valueOf(str).length());
            sb.append(str2);
            sb.append(RunnerArgs.METHOD_SEPARATOR);
            sb.append(str);
            return sb.toString();
        }
    }

    private static final class TestFileArgs {
        /* access modifiers changed from: private */
        public final List<String> packages;
        /* access modifiers changed from: private */
        public final List<TestArg> tests;

        private TestFileArgs() {
            this.tests = new ArrayList();
            this.packages = new ArrayList();
        }
    }

    private RunnerArgs(Builder builder) {
        this.debug = builder.debug;
        this.suiteAssignment = builder.suiteAssignment;
        this.codeCoverage = builder.codeCoverage;
        this.codeCoveragePath = builder.codeCoveragePath;
        this.delayInMillis = builder.delayInMillis;
        this.logOnly = builder.logOnly;
        this.testPackages = builder.testPackages;
        this.notTestPackages = builder.notTestPackages;
        this.testSize = builder.testSize;
        this.annotations = Collections.unmodifiableList(builder.annotations);
        this.notAnnotations = Collections.unmodifiableList(builder.notAnnotations);
        this.testTimeout = builder.testTimeout;
        this.listeners = Collections.unmodifiableList(builder.listeners);
        this.filters = Collections.unmodifiableList(builder.filters);
        this.runnerBuilderClasses = Collections.unmodifiableList(builder.runnerBuilderClasses);
        this.tests = Collections.unmodifiableList(builder.tests);
        this.notTests = Collections.unmodifiableList(builder.notTests);
        this.numShards = builder.numShards;
        this.shardIndex = builder.shardIndex;
        this.disableAnalytics = builder.disableAnalytics;
        this.appListeners = Collections.unmodifiableList(builder.appListeners);
        this.classLoader = builder.classLoader;
        this.classpathToScan = builder.classpathToScan;
        this.remoteMethod = builder.remoteMethod;
        this.orchestratorService = builder.orchestratorService;
        this.listTestsForOrchestrator = builder.listTestsForOrchestrator;
        this.testDiscoveryService = builder.testDiscoveryService;
        this.testRunEventsService = builder.testRunEventsService;
        this.useTestStorageService = builder.useTestStorageService;
        this.screenCaptureProcessors = Collections.unmodifiableList(builder.screenCaptureProcessors);
        this.targetProcess = builder.targetProcess;
        this.shellExecBinderKey = builder.shellExecBinderKey;
        this.newRunListenerMode = builder.newRunListenerMode;
        this.testsRegEx = builder.testsRegEx;
        this.testPlatformMigration = builder.testPlatformMigration;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public final List<String> annotations = new ArrayList();
        /* access modifiers changed from: private */
        public List<ApplicationLifecycleCallback> appListeners = new ArrayList();
        /* access modifiers changed from: private */
        public ClassLoader classLoader = null;
        /* access modifiers changed from: private */
        public Set<String> classpathToScan = new HashSet();
        /* access modifiers changed from: private */
        public boolean codeCoverage = false;
        /* access modifiers changed from: private */
        public String codeCoveragePath = null;
        /* access modifiers changed from: private */
        public boolean debug = false;
        /* access modifiers changed from: private */
        public int delayInMillis = -1;
        /* access modifiers changed from: private */
        public boolean disableAnalytics = false;
        /* access modifiers changed from: private */
        public List<Filter> filters = new ArrayList();
        /* access modifiers changed from: private */
        public boolean listTestsForOrchestrator = false;
        /* access modifiers changed from: private */
        public List<RunListener> listeners = new ArrayList();
        /* access modifiers changed from: private */
        public boolean logOnly = false;
        /* access modifiers changed from: private */
        public boolean newRunListenerMode = false;
        /* access modifiers changed from: private */
        public final List<String> notAnnotations = new ArrayList();
        /* access modifiers changed from: private */
        public List<String> notTestPackages = new ArrayList();
        /* access modifiers changed from: private */
        public List<TestArg> notTests = new ArrayList();
        /* access modifiers changed from: private */
        public int numShards = 0;
        /* access modifiers changed from: private */
        public String orchestratorService = null;
        /* access modifiers changed from: private */
        public TestArg remoteMethod = null;
        /* access modifiers changed from: private */
        public List<Class<? extends RunnerBuilder>> runnerBuilderClasses = new ArrayList();
        /* access modifiers changed from: private */
        public List<ScreenCaptureProcessor> screenCaptureProcessors = new ArrayList();
        /* access modifiers changed from: private */
        public int shardIndex = 0;
        public String shellExecBinderKey;
        /* access modifiers changed from: private */
        public boolean suiteAssignment = false;
        /* access modifiers changed from: private */
        public String targetProcess = null;
        /* access modifiers changed from: private */
        public String testDiscoveryService = null;
        /* access modifiers changed from: private */
        public List<String> testPackages = new ArrayList();
        /* access modifiers changed from: private */
        public boolean testPlatformMigration = false;
        /* access modifiers changed from: private */
        public String testRunEventsService = null;
        /* access modifiers changed from: private */
        public String testSize = null;
        /* access modifiers changed from: private */
        public long testTimeout = -1;
        /* access modifiers changed from: private */
        public List<TestArg> tests = new ArrayList();
        /* access modifiers changed from: private */
        public String testsRegEx = null;
        /* access modifiers changed from: private */
        public boolean useTestStorageService = false;

        public Builder fromBundle(Instrumentation instrumentation, Bundle bundle) {
            this.debug = parseBoolean(bundle.getString("debug"));
            this.delayInMillis = parseUnsignedInt(bundle.get(RunnerArgs.ARGUMENT_DELAY_IN_MILLIS), RunnerArgs.ARGUMENT_DELAY_IN_MILLIS);
            this.tests.addAll(parseTestClasses(bundle.getString("class")));
            this.notTests.addAll(parseTestClasses(bundle.getString(RunnerArgs.ARGUMENT_NOT_TEST_CLASS)));
            this.testPackages.addAll(parseTestPackages(bundle.getString(RunnerArgs.ARGUMENT_TEST_PACKAGE)));
            this.notTestPackages.addAll(parseTestPackages(bundle.getString(RunnerArgs.ARGUMENT_NOT_TEST_PACKAGE)));
            TestFileArgs parseFromFile = parseFromFile(instrumentation, bundle.getString(RunnerArgs.ARGUMENT_TEST_FILE));
            this.tests.addAll(parseFromFile.tests);
            this.testPackages.addAll(parseFromFile.packages);
            TestFileArgs parseFromFile2 = parseFromFile(instrumentation, bundle.getString(RunnerArgs.ARGUMENT_NOT_TEST_FILE));
            this.notTests.addAll(parseFromFile2.tests);
            this.notTestPackages.addAll(parseFromFile2.packages);
            this.listeners.addAll(parseLoadAndInstantiateClasses(bundle.getString(RunnerArgs.ARGUMENT_LISTENER), RunListener.class, (Bundle) null));
            this.filters.addAll(parseLoadAndInstantiateClasses(bundle.getString(RunnerArgs.ARGUMENT_FILTER), Filter.class, bundle));
            this.runnerBuilderClasses.addAll(parseAndLoadClasses(bundle.getString(RunnerArgs.ARGUMENT_RUNNER_BUILDER), RunnerBuilder.class));
            this.testSize = bundle.getString("size");
            this.annotations.addAll(parseStrings(bundle.getString(RunnerArgs.ARGUMENT_ANNOTATION)));
            this.notAnnotations.addAll(parseStrings(bundle.getString(RunnerArgs.ARGUMENT_NOT_ANNOTATION)));
            this.testTimeout = parseUnsignedLong(bundle.getString(RunnerArgs.ARGUMENT_TIMEOUT), RunnerArgs.ARGUMENT_TIMEOUT);
            this.numShards = parseUnsignedInt(bundle.get(RunnerArgs.ARGUMENT_NUM_SHARDS), RunnerArgs.ARGUMENT_NUM_SHARDS);
            this.shardIndex = parseUnsignedInt(bundle.get(RunnerArgs.ARGUMENT_SHARD_INDEX), RunnerArgs.ARGUMENT_SHARD_INDEX);
            this.logOnly = parseBoolean(bundle.getString(RunnerArgs.ARGUMENT_LOG_ONLY));
            this.disableAnalytics = parseBoolean(bundle.getString(RunnerArgs.ARGUMENT_DISABLE_ANALYTICS));
            this.appListeners.addAll(parseLoadAndInstantiateClasses(bundle.getString(RunnerArgs.ARGUMENT_APP_LISTENER), ApplicationLifecycleCallback.class, (Bundle) null));
            this.codeCoverage = parseBoolean(bundle.getString("coverage"));
            this.codeCoveragePath = bundle.getString(RunnerArgs.ARGUMENT_COVERAGE_PATH);
            this.suiteAssignment = parseBoolean(bundle.getString(RunnerArgs.ARGUMENT_SUITE_ASSIGNMENT));
            this.classLoader = (ClassLoader) parseLoadAndInstantiateClass(bundle.getString(RunnerArgs.ARGUMENT_CLASS_LOADER), ClassLoader.class);
            this.classpathToScan = parseClasspath(bundle.getString(RunnerArgs.ARGUMENT_CLASSPATH_TO_SCAN));
            if (bundle.containsKey(RunnerArgs.ARGUMENT_REMOTE_INIT_METHOD)) {
                this.remoteMethod = parseTestClass(bundle.getString(RunnerArgs.ARGUMENT_REMOTE_INIT_METHOD));
            }
            this.orchestratorService = bundle.getString(RunnerArgs.ARGUMENT_ORCHESTRATOR_SERVICE);
            this.listTestsForOrchestrator = parseBoolean(bundle.getString(RunnerArgs.ARGUMENT_LIST_TESTS_FOR_ORCHESTRATOR));
            this.testDiscoveryService = bundle.getString(RunnerArgs.ARGUMENT_ORCHESTRATOR_DISCOVERY_SERVICE);
            this.testRunEventsService = bundle.getString(RunnerArgs.ARGUMENT_ORCHESTRATOR_RUN_EVENTS_SERVICE);
            this.useTestStorageService = parseBoolean(bundle.getString(RunnerArgs.ARGUMENT_USE_TEST_STORAGE_SERVICE));
            this.targetProcess = bundle.getString(RunnerArgs.ARGUMENT_TARGET_PROCESS);
            this.screenCaptureProcessors.addAll(parseLoadAndInstantiateClasses(bundle.getString(RunnerArgs.ARGUMENT_SCREENSHOT_PROCESSORS), ScreenCaptureProcessor.class, (Bundle) null));
            this.shellExecBinderKey = bundle.getString(RunnerArgs.ARGUMENT_SHELL_EXEC_BINDER_KEY);
            this.newRunListenerMode = parseBoolean(bundle.getString(RunnerArgs.ARGUMENT_RUN_LISTENER_NEW_ORDER));
            this.testsRegEx = bundle.getString(RunnerArgs.ARGUMENT_TESTS_REGEX);
            this.testPlatformMigration = parseBoolean(bundle.getString(RunnerArgs.ARGUMENT_TEST_PLATFORM_MIGRATION));
            return this;
        }

        public Builder fromManifest(Instrumentation instrumentation) {
            try {
                Bundle bundle = instrumentation.getContext().getPackageManager().getInstrumentationInfo(instrumentation.getComponentName(), 128).metaData;
                if (bundle == null) {
                    return this;
                }
                return fromBundle(instrumentation, bundle);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.wtf(RunnerArgs.LOG_TAG, String.format("Could not find component %s", new Object[]{instrumentation.getComponentName()}));
                return this;
            }
        }

        private static List<String> parseStrings(String str) {
            if (str == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(str.split(RunnerArgs.CLASS_SEPARATOR));
        }

        private static boolean parseBoolean(String str) {
            return str != null && Boolean.parseBoolean(str);
        }

        private static int parseUnsignedInt(Object obj, String str) {
            if (obj == null) {
                return -1;
            }
            int parseInt = Integer.parseInt(obj.toString());
            if (parseInt >= 0) {
                return parseInt;
            }
            throw new NumberFormatException(String.valueOf(str).concat(" can not be negative"));
        }

        private static long parseUnsignedLong(Object obj, String str) {
            if (obj == null) {
                return -1;
            }
            long parseLong = Long.parseLong(obj.toString());
            if (parseLong >= 0) {
                return parseLong;
            }
            throw new NumberFormatException(String.valueOf(str).concat(" can not be negative"));
        }

        private static List<String> parseTestPackages(String str) {
            ArrayList arrayList = new ArrayList();
            if (str != null) {
                for (String add : str.split(RunnerArgs.CLASS_SEPARATOR)) {
                    arrayList.add(add);
                }
            }
            return arrayList;
        }

        private List<TestArg> parseTestClasses(String str) {
            ArrayList arrayList = new ArrayList();
            if (str != null) {
                for (String parseTestClass : str.split(RunnerArgs.CLASS_SEPARATOR)) {
                    arrayList.add(parseTestClass(parseTestClass));
                }
            }
            return arrayList;
        }

        private static Set<String> parseClasspath(String str) {
            if (str == null || str.isEmpty()) {
                return new HashSet();
            }
            return new HashSet(Arrays.asList(str.split(RunnerArgs.CLASSPATH_SEPARATOR, -1)));
        }

        private static TestArg parseTestClass(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            int indexOf = str.indexOf(35);
            if (indexOf <= 0) {
                return new TestArg(str);
            }
            return new TestArg(str.substring(0, indexOf), str.substring(indexOf + 1));
        }

        /* JADX WARNING: type inference failed for: r1v0, types: [androidx.test.internal.runner.RunnerArgs$1, java.io.BufferedReader] */
        private TestFileArgs parseFromFile(Instrumentation instrumentation, String str) {
            ? r1 = 0;
            TestFileArgs testFileArgs = new TestFileArgs();
            if (str == null) {
                return testFileArgs;
            }
            try {
                BufferedReader openFile = openFile(instrumentation, str);
                while (true) {
                    String readLine = openFile.readLine();
                    if (readLine == null) {
                        break;
                    } else if (isClassOrMethod(readLine)) {
                        testFileArgs.tests.add(parseTestClass(readLine));
                    } else {
                        testFileArgs.packages.addAll(parseTestPackages(readLine));
                    }
                }
                if (openFile != null) {
                    try {
                        openFile.close();
                    } catch (IOException unused) {
                    }
                }
                return testFileArgs;
            } catch (FileNotFoundException e) {
                String valueOf = String.valueOf(str);
                throw new IllegalArgumentException(valueOf.length() != 0 ? "testfile not found: ".concat(valueOf) : new String("testfile not found: "), e);
            } catch (IOException e2) {
                String valueOf2 = String.valueOf(str);
                throw new IllegalArgumentException(valueOf2.length() != 0 ? "Could not read test file ".concat(valueOf2) : new String("Could not read test file "), e2);
            } catch (Throwable th) {
                if (r1 != 0) {
                    try {
                        r1.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        }

        private BufferedReader openFile(Instrumentation instrumentation, String str) throws IOException {
            Reader reader;
            if (Build.VERSION.SDK_INT >= 26 && instrumentation.getContext().getPackageManager().isInstantApp()) {
                UiAutomation uiAutomation = instrumentation.getUiAutomation();
                String valueOf = String.valueOf(str);
                reader = new InputStreamReader(new ParcelFileDescriptor.AutoCloseInputStream(uiAutomation.executeShellCommand(valueOf.length() != 0 ? "cat ".concat(valueOf) : new String("cat "))));
            } else {
                reader = new FileReader(new File(str));
            }
            return new BufferedReader(reader);
        }

        static boolean isClassOrMethod(String str) {
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (charAt == '#' || Character.isUpperCase(charAt)) {
                    return true;
                }
            }
            return false;
        }

        private <T> List<T> parseLoadAndInstantiateClasses(String str, Class<T> cls, Bundle bundle) {
            ArrayList arrayList = new ArrayList();
            if (str != null) {
                for (String loadClassByNameInstantiateAndAdd : str.split(RunnerArgs.CLASS_SEPARATOR)) {
                    loadClassByNameInstantiateAndAdd(arrayList, loadClassByNameInstantiateAndAdd, cls, bundle);
                }
            }
            return arrayList;
        }

        private <T> T parseLoadAndInstantiateClass(String str, Class<T> cls) {
            List<T> parseLoadAndInstantiateClasses = parseLoadAndInstantiateClasses(str, cls, (Bundle) null);
            if (parseLoadAndInstantiateClasses.isEmpty()) {
                return null;
            }
            if (parseLoadAndInstantiateClasses.size() <= 1) {
                return parseLoadAndInstantiateClasses.get(0);
            }
            throw new IllegalArgumentException(String.format("Expected 1 class loader, %d given", new Object[]{Integer.valueOf(parseLoadAndInstantiateClasses.size())}));
        }

        /* JADX WARNING: Code restructure failed: missing block: B:21:0x003f, code lost:
            r8 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0040, code lost:
            r9 = java.lang.String.valueOf(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004c, code lost:
            if (r9.length() != 0) goto L_0x004e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x004e, code lost:
            r9 = "Failed to create listener: ".concat(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0053, code lost:
            r9 = new java.lang.String("Failed to create listener: ");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x005b, code lost:
            throw new java.lang.IllegalArgumentException(r9, r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x005c, code lost:
            r8 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x005d, code lost:
            r9 = java.lang.String.valueOf(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0067, code lost:
            if (r9.length() != 0) goto L_0x0069;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0069, code lost:
            r9 = "Failed to create: ".concat(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x006e, code lost:
            r9 = new java.lang.String("Failed to create: ");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x0076, code lost:
            throw new java.lang.IllegalArgumentException(r9, r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0077, code lost:
            r8 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0078, code lost:
            r9 = java.lang.String.valueOf(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0082, code lost:
            if (r9.length() != 0) goto L_0x0084;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0084, code lost:
            r9 = "Failed to create: ".concat(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0089, code lost:
            r9 = new java.lang.String("Failed to create: ");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0091, code lost:
            throw new java.lang.IllegalArgumentException(r9, r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x0092, code lost:
            r10 = r10.getName();
            r0 = new java.lang.StringBuilder((java.lang.String.valueOf(r9).length() + 17) + java.lang.String.valueOf(r10).length());
            r0.append(r9);
            r0.append(" does not extend ");
            r0.append(r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c2, code lost:
            throw new java.lang.IllegalArgumentException(r0.toString());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00e0, code lost:
            r9 = java.lang.String.valueOf(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ed, code lost:
            if (r9.length() != 0) goto L_0x00ef;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ef, code lost:
            r9 = "Could not find extra class ".concat(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x00f4, code lost:
            r9 = new java.lang.String("Could not find extra class ");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x00fc, code lost:
            throw new java.lang.IllegalArgumentException(r9);
         */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x003f A[ExcHandler: IllegalAccessException (r8v6 'e' java.lang.IllegalAccessException A[CUSTOM_DECLARE]), Splitter:B:4:0x000c] */
        /* JADX WARNING: Removed duplicated region for block: B:28:0x005c A[ExcHandler: InvocationTargetException (r8v5 'e' java.lang.reflect.InvocationTargetException A[CUSTOM_DECLARE]), Splitter:B:4:0x000c] */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x0077 A[ExcHandler: InstantiationException (r8v4 'e' java.lang.InstantiationException A[CUSTOM_DECLARE]), Splitter:B:4:0x000c] */
        /* JADX WARNING: Removed duplicated region for block: B:43:? A[ExcHandler: ClassCastException (unused java.lang.ClassCastException), SYNTHETIC, Splitter:B:4:0x000c] */
        /* JADX WARNING: Removed duplicated region for block: B:53:? A[ExcHandler: ClassNotFoundException (unused java.lang.ClassNotFoundException), SYNTHETIC, Splitter:B:4:0x000c] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private <T> void loadClassByNameInstantiateAndAdd(java.util.List<T> r8, java.lang.String r9, java.lang.Class<T> r10, android.os.Bundle r11) {
            /*
                r7 = this;
                java.lang.String r0 = "Failed to create: "
                if (r9 == 0) goto L_0x00fd
                int r1 = r9.length()
                if (r1 != 0) goto L_0x000c
                goto L_0x00fd
            L_0x000c:
                java.lang.Class r1 = java.lang.Class.forName(r9)     // Catch:{ ClassNotFoundException -> 0x00e0, NoSuchMethodException -> 0x00c3, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                r2 = 1
                r3 = 0
                java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ NoSuchMethodException -> 0x001b, ClassNotFoundException -> 0x00e0, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                java.lang.reflect.Constructor r4 = r1.getConstructor(r4)     // Catch:{ NoSuchMethodException -> 0x001b, ClassNotFoundException -> 0x00e0, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                java.lang.Object[] r11 = new java.lang.Object[r3]     // Catch:{ NoSuchMethodException -> 0x001b, ClassNotFoundException -> 0x00e0, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                goto L_0x002e
            L_0x001b:
                r4 = move-exception
                if (r11 == 0) goto L_0x003e
                java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch:{ NoSuchMethodException -> 0x0039, ClassNotFoundException -> 0x00e0, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                java.lang.Class<android.os.Bundle> r6 = android.os.Bundle.class
                r5[r3] = r6     // Catch:{ NoSuchMethodException -> 0x0039, ClassNotFoundException -> 0x00e0, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                java.lang.reflect.Constructor r1 = r1.getConstructor(r5)     // Catch:{ NoSuchMethodException -> 0x0039, ClassNotFoundException -> 0x00e0, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ NoSuchMethodException -> 0x0039, ClassNotFoundException -> 0x00e0, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                r5[r3] = r11     // Catch:{ NoSuchMethodException -> 0x0039, ClassNotFoundException -> 0x00e0, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                r4 = r1
                r11 = r5
            L_0x002e:
                r4.setAccessible(r2)     // Catch:{ ClassNotFoundException -> 0x00e0, NoSuchMethodException -> 0x00c3, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                java.lang.Object r11 = r4.newInstance(r11)     // Catch:{ ClassNotFoundException -> 0x00e0, NoSuchMethodException -> 0x00c3, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                r8.add(r11)     // Catch:{ ClassNotFoundException -> 0x00e0, NoSuchMethodException -> 0x00c3, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                return
            L_0x0039:
                r8 = move-exception
                r8.initCause(r4)     // Catch:{ ClassNotFoundException -> 0x00e0, NoSuchMethodException -> 0x00c3, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
                throw r8     // Catch:{ ClassNotFoundException -> 0x00e0, NoSuchMethodException -> 0x00c3, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
            L_0x003e:
                throw r4     // Catch:{ ClassNotFoundException -> 0x00e0, NoSuchMethodException -> 0x00c3, ClassCastException -> 0x0092, InstantiationException -> 0x0077, InvocationTargetException -> 0x005c, IllegalAccessException -> 0x003f }
            L_0x003f:
                r8 = move-exception
                java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
                java.lang.String r11 = "Failed to create listener: "
                java.lang.String r9 = java.lang.String.valueOf(r9)
                int r0 = r9.length()
                if (r0 == 0) goto L_0x0053
                java.lang.String r9 = r11.concat(r9)
                goto L_0x0058
            L_0x0053:
                java.lang.String r9 = new java.lang.String
                r9.<init>(r11)
            L_0x0058:
                r10.<init>(r9, r8)
                throw r10
            L_0x005c:
                r8 = move-exception
                java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
                java.lang.String r9 = java.lang.String.valueOf(r9)
                int r11 = r9.length()
                if (r11 == 0) goto L_0x006e
                java.lang.String r9 = r0.concat(r9)
                goto L_0x0073
            L_0x006e:
                java.lang.String r9 = new java.lang.String
                r9.<init>(r0)
            L_0x0073:
                r10.<init>(r9, r8)
                throw r10
            L_0x0077:
                r8 = move-exception
                java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
                java.lang.String r9 = java.lang.String.valueOf(r9)
                int r11 = r9.length()
                if (r11 == 0) goto L_0x0089
                java.lang.String r9 = r0.concat(r9)
                goto L_0x008e
            L_0x0089:
                java.lang.String r9 = new java.lang.String
                r9.<init>(r0)
            L_0x008e:
                r10.<init>(r9, r8)
                throw r10
            L_0x0092:
                java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
                java.lang.String r10 = r10.getName()
                java.lang.String r11 = java.lang.String.valueOf(r9)
                int r11 = r11.length()
                int r11 = r11 + 17
                java.lang.String r0 = java.lang.String.valueOf(r10)
                int r0 = r0.length()
                int r11 = r11 + r0
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>(r11)
                r0.append(r9)
                java.lang.String r9 = " does not extend "
                r0.append(r9)
                r0.append(r10)
                java.lang.String r9 = r0.toString()
                r8.<init>(r9)
                throw r8
            L_0x00c3:
                java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
                java.lang.String r10 = "Must have no argument constructor for class "
                java.lang.String r9 = java.lang.String.valueOf(r9)
                int r11 = r9.length()
                if (r11 == 0) goto L_0x00d7
                java.lang.String r9 = r10.concat(r9)
                goto L_0x00dc
            L_0x00d7:
                java.lang.String r9 = new java.lang.String
                r9.<init>(r10)
            L_0x00dc:
                r8.<init>(r9)
                throw r8
            L_0x00e0:
                java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
                java.lang.String r10 = "Could not find extra class "
                java.lang.String r9 = java.lang.String.valueOf(r9)
                int r11 = r9.length()
                if (r11 == 0) goto L_0x00f4
                java.lang.String r9 = r10.concat(r9)
                goto L_0x00f9
            L_0x00f4:
                java.lang.String r9 = new java.lang.String
                r9.<init>(r10)
            L_0x00f9:
                r8.<init>(r9)
                throw r8
            L_0x00fd:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.internal.runner.RunnerArgs.Builder.loadClassByNameInstantiateAndAdd(java.util.List, java.lang.String, java.lang.Class, android.os.Bundle):void");
        }

        private <T> List<Class<? extends T>> parseAndLoadClasses(String str, Class<T> cls) {
            ArrayList arrayList = new ArrayList();
            if (str != null) {
                for (String loadClassByNameAndAdd : str.split(RunnerArgs.CLASS_SEPARATOR)) {
                    loadClassByNameAndAdd(arrayList, loadClassByNameAndAdd, cls);
                }
            }
            return arrayList;
        }

        private <T> void loadClassByNameAndAdd(List<Class<? extends T>> list, String str, Class<T> cls) {
            if (str != null && str.length() != 0) {
                try {
                    Class<?> cls2 = Class.forName(str);
                    if (cls.isAssignableFrom(cls2)) {
                        list.add(cls2);
                        return;
                    }
                    String name = cls.getName();
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 17 + String.valueOf(name).length());
                    sb.append(str);
                    sb.append(" does not extend ");
                    sb.append(name);
                    throw new IllegalArgumentException(sb.toString());
                } catch (ClassNotFoundException unused) {
                    String valueOf = String.valueOf(str);
                    throw new IllegalArgumentException(valueOf.length() != 0 ? "Could not find extra class ".concat(valueOf) : new String("Could not find extra class "));
                } catch (ClassCastException unused2) {
                    String name2 = cls.getName();
                    StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 17 + String.valueOf(name2).length());
                    sb2.append(str);
                    sb2.append(" does not extend ");
                    sb2.append(name2);
                    throw new IllegalArgumentException(sb2.toString());
                }
            }
        }

        public RunnerArgs build() {
            return new RunnerArgs(this);
        }
    }
}
