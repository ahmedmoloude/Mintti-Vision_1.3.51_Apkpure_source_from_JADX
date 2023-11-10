package androidx.test.internal.runner;

import android.app.Instrumentation;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.test.filters.RequiresDevice;
import androidx.test.filters.SdkSuppress;
import androidx.test.filters.Suppress;
import androidx.test.internal.runner.ClassPathScanner;
import androidx.test.internal.runner.RunnerArgs;
import androidx.test.internal.runner.filters.ParentFilter;
import androidx.test.internal.runner.filters.TestsRegExFilter;
import androidx.test.internal.util.AndroidRunnerParams;
import androidx.test.internal.util.Checks;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.junit.runner.Description;
import org.junit.runner.Request;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class TestRequestBuilder {
    static final String AMBIGUOUS_ARGUMENTS_MSG = "Ambiguous arguments: cannot provide both test package and test class(es) to run";
    private static final String[] DEFAULT_EXCLUDED_PACKAGES = {"junit", "org.junit", "org.hamcrest", "org.mockito", "androidx.test.internal.runner.junit3", "org.jacoco", "net.bytebuddy"};
    static final String MISSING_ARGUMENTS_MSG = "Must provide either classes to run, or paths to scan";
    private static final String TAG = "TestRequestBuilder";
    private final Bundle argsBundle;
    private ClassLoader classLoader;
    private ClassAndMethodFilter classMethodFilter;
    private List<Class<? extends RunnerBuilder>> customRunnerBuilderClasses;
    private final DeviceBuild deviceBuild;
    private Set<String> excludedClasses;
    private Set<String> excludedPackages;
    private Filter filter;
    private boolean ignoreSuiteMethods;
    private Set<String> includedClasses;
    private Set<String> includedPackages;
    private final Instrumentation instr;
    private final List<String> pathsToScan;
    private long perTestTimeout;
    private boolean skipExecution;
    private final TestsRegExFilter testsRegExFilter;

    interface DeviceBuild {
        String getCodeName();

        String getHardware();

        int getSdkVersionInt();
    }

    private static class DeviceBuildImpl implements DeviceBuild {
        private DeviceBuildImpl() {
        }

        public int getSdkVersionInt() {
            return Build.VERSION.SDK_INT;
        }

        public String getHardware() {
            return Build.HARDWARE;
        }

        public String getCodeName() {
            return Build.VERSION.CODENAME;
        }
    }

    private static class AnnotationInclusionFilter extends ParentFilter {
        private final Class<? extends Annotation> annotationClass;

        AnnotationInclusionFilter(Class<? extends Annotation> cls) {
            this.annotationClass = cls;
        }

        /* access modifiers changed from: protected */
        public boolean evaluateTest(Description description) {
            Class<?> testClass = description.getTestClass();
            return description.getAnnotation(this.annotationClass) != null || (testClass != null && testClass.isAnnotationPresent(this.annotationClass));
        }

        /* access modifiers changed from: protected */
        public Class<? extends Annotation> getAnnotationClass() {
            return this.annotationClass;
        }

        public String describe() {
            return String.format("annotation %s", new Object[]{this.annotationClass.getName()});
        }
    }

    private static class SizeFilter extends ParentFilter {
        private final TestSize testSize;

        public String describe() {
            return "";
        }

        SizeFilter(TestSize testSize2) {
            this.testSize = testSize2;
        }

        /* access modifiers changed from: protected */
        public boolean evaluateTest(Description description) {
            if (this.testSize.testMethodIsAnnotatedWithTestSize(description)) {
                return true;
            }
            if (!this.testSize.testClassIsAnnotatedWithTestSize(description)) {
                return false;
            }
            for (Annotation annotationType : description.getAnnotations()) {
                if (TestSize.isAnyTestSize(annotationType.annotationType())) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class AnnotationExclusionFilter extends ParentFilter {
        private final Class<? extends Annotation> annotationClass;

        AnnotationExclusionFilter(Class<? extends Annotation> cls) {
            this.annotationClass = cls;
        }

        /* access modifiers changed from: protected */
        public boolean evaluateTest(Description description) {
            Class<?> testClass = description.getTestClass();
            return (testClass == null || !testClass.isAnnotationPresent(this.annotationClass)) && description.getAnnotation(this.annotationClass) == null;
        }

        public String describe() {
            return String.format("not annotation %s", new Object[]{this.annotationClass.getName()});
        }
    }

    private static class ExtendedSuite extends Suite {
        static Suite createSuite(List<Runner> list) {
            try {
                return new ExtendedSuite(list);
            } catch (InitializationError unused) {
                String name = Suite.class.getName();
                StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 107);
                sb.append("Internal Error: ");
                sb.append(name);
                sb.append("(Class<?>, List<Runner>) should never throw an InitializationError when passed a null Class");
                throw new RuntimeException(sb.toString());
            }
        }

        ExtendedSuite(List<Runner> list) throws InitializationError {
            super((Class<?>) null, list);
        }
    }

    private class SdkSuppressFilter extends ParentFilter {
        private SdkSuppressFilter() {
        }

        /* access modifiers changed from: protected */
        public boolean evaluateTest(Description description) {
            SdkSuppress annotationForTest = getAnnotationForTest(description);
            if (annotationForTest == null) {
                return true;
            }
            if ((TestRequestBuilder.this.getDeviceSdkInt() < annotationForTest.minSdkVersion() || TestRequestBuilder.this.getDeviceSdkInt() > annotationForTest.maxSdkVersion()) && !TestRequestBuilder.this.getDeviceCodeName().equals(annotationForTest.codeName())) {
                return false;
            }
            return true;
        }

        private SdkSuppress getAnnotationForTest(Description description) {
            SdkSuppress sdkSuppress = (SdkSuppress) description.getAnnotation(SdkSuppress.class);
            if (sdkSuppress != null) {
                return sdkSuppress;
            }
            Class<?> testClass = description.getTestClass();
            if (testClass != null) {
                return (SdkSuppress) testClass.getAnnotation(SdkSuppress.class);
            }
            return null;
        }

        public String describe() {
            return String.format("skip tests annotated with SdkSuppress if necessary", new Object[0]);
        }
    }

    class RequiresDeviceFilter extends AnnotationExclusionFilter {
        static final String EMULATOR_HARDWARE_GCE = "gce_x86";
        static final String EMULATOR_HARDWARE_GOLDFISH = "goldfish";
        static final String EMULATOR_HARDWARE_RANCHU = "ranchu";
        private final Set<String> emulatorHardwareNames = new HashSet(Arrays.asList(new String[]{EMULATOR_HARDWARE_GOLDFISH, EMULATOR_HARDWARE_RANCHU, EMULATOR_HARDWARE_GCE}));

        RequiresDeviceFilter() {
            super(RequiresDevice.class);
        }

        /* access modifiers changed from: protected */
        public boolean evaluateTest(Description description) {
            if (!super.evaluateTest(description)) {
                return !this.emulatorHardwareNames.contains(TestRequestBuilder.this.getDeviceHardware());
            }
            return true;
        }

        public String describe() {
            return String.format("skip tests annotated with RequiresDevice if necessary", new Object[0]);
        }
    }

    private static class ShardingFilter extends Filter {
        private final int numShards;
        private final int shardIndex;

        ShardingFilter(int i, int i2) {
            this.numShards = i;
            this.shardIndex = i2;
        }

        public boolean shouldRun(Description description) {
            if (!description.isTest() || Math.abs(description.hashCode()) % this.numShards == this.shardIndex) {
                return true;
            }
            return false;
        }

        public String describe() {
            return String.format("Shard %s of %s shards", new Object[]{Integer.valueOf(this.shardIndex), Integer.valueOf(this.numShards)});
        }
    }

    private static class LenientFilterRequest extends Request {
        private final Filter filter;
        private final Request request;

        public LenientFilterRequest(Request request2, Filter filter2) {
            this.request = request2;
            this.filter = filter2;
        }

        public Runner getRunner() {
            try {
                Runner runner = this.request.getRunner();
                this.filter.apply(runner);
                return runner;
            } catch (NoTestsRemainException unused) {
                return new BlankRunner();
            }
        }
    }

    private static class BlankRunner extends Runner {
        public void run(RunNotifier runNotifier) {
        }

        private BlankRunner() {
        }

        public Description getDescription() {
            return Description.createSuiteDescription("no tests found", new Annotation[0]);
        }
    }

    private static class ClassAndMethodFilter extends ParentFilter {
        private Map<String, MethodFilter> methodFilters;

        public String describe() {
            return "Class and method filter";
        }

        private ClassAndMethodFilter() {
            this.methodFilters = new HashMap();
        }

        public boolean evaluateTest(Description description) {
            MethodFilter methodFilter;
            if (!this.methodFilters.isEmpty() && (methodFilter = this.methodFilters.get(description.getClassName())) != null) {
                return methodFilter.shouldRun(description);
            }
            return true;
        }

        public void addMethod(String str, String str2) {
            MethodFilter methodFilter = this.methodFilters.get(str);
            if (methodFilter == null) {
                methodFilter = new MethodFilter(str);
                this.methodFilters.put(str, methodFilter);
            }
            methodFilter.addInclusionMethod(str2);
        }

        public void removeMethod(String str, String str2) {
            MethodFilter methodFilter = this.methodFilters.get(str);
            if (methodFilter == null) {
                methodFilter = new MethodFilter(str);
                this.methodFilters.put(str, methodFilter);
            }
            methodFilter.addExclusionMethod(str2);
        }
    }

    private static class MethodFilter extends ParentFilter {
        private final String className;
        private Set<String> excludedMethods = new HashSet();
        private Set<String> includedMethods = new HashSet();

        public MethodFilter(String str) {
            this.className = str;
        }

        public String describe() {
            String str = this.className;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 24);
            sb.append("Method filter for ");
            sb.append(str);
            sb.append(" class");
            return sb.toString();
        }

        public boolean evaluateTest(Description description) {
            String methodName = description.getMethodName();
            if (methodName == null) {
                return false;
            }
            String stripParameterizedSuffix = stripParameterizedSuffix(methodName);
            if (this.excludedMethods.contains(methodName) || this.excludedMethods.contains(stripParameterizedSuffix)) {
                return false;
            }
            if (this.includedMethods.isEmpty() || this.includedMethods.contains(methodName) || this.includedMethods.contains(stripParameterizedSuffix) || methodName.equals("initializationError")) {
                return true;
            }
            return false;
        }

        private String stripParameterizedSuffix(String str) {
            return Pattern.compile(".+(\\[[0-9]+\\])$").matcher(str).matches() ? str.substring(0, str.lastIndexOf(91)) : str;
        }

        public void addInclusionMethod(String str) {
            this.includedMethods.add(str);
        }

        public void addExclusionMethod(String str) {
            this.excludedMethods.add(str);
        }
    }

    public TestRequestBuilder(Instrumentation instrumentation, Bundle bundle) {
        this(new DeviceBuildImpl(), instrumentation, bundle);
    }

    TestRequestBuilder(DeviceBuild deviceBuild2, Instrumentation instrumentation, Bundle bundle) {
        this.pathsToScan = new ArrayList();
        this.includedPackages = new HashSet();
        this.excludedPackages = new HashSet();
        this.includedClasses = new HashSet();
        this.excludedClasses = new HashSet();
        this.classMethodFilter = new ClassAndMethodFilter();
        TestsRegExFilter testsRegExFilter2 = new TestsRegExFilter();
        this.testsRegExFilter = testsRegExFilter2;
        this.filter = new AnnotationExclusionFilter(Suppress.class).intersect(new SdkSuppressFilter()).intersect(new RequiresDeviceFilter()).intersect(this.classMethodFilter).intersect(testsRegExFilter2);
        this.customRunnerBuilderClasses = new ArrayList();
        this.skipExecution = false;
        this.perTestTimeout = 0;
        this.ignoreSuiteMethods = false;
        this.deviceBuild = (DeviceBuild) Checks.checkNotNull(deviceBuild2);
        this.instr = (Instrumentation) Checks.checkNotNull(instrumentation);
        this.argsBundle = (Bundle) Checks.checkNotNull(bundle);
        maybeAddLegacySuppressFilter();
    }

    private void maybeAddLegacySuppressFilter() {
        try {
            this.filter = this.filter.intersect(new AnnotationExclusionFilter(Class.forName("android.test.suitebuilder.annotation.Suppress")));
        } catch (ClassNotFoundException unused) {
        }
    }

    public TestRequestBuilder addPathsToScan(Iterable<String> iterable) {
        for (String addPathToScan : iterable) {
            addPathToScan(addPathToScan);
        }
        return this;
    }

    public TestRequestBuilder addPathToScan(String str) {
        this.pathsToScan.add(str);
        return this;
    }

    public TestRequestBuilder setClassLoader(ClassLoader classLoader2) {
        this.classLoader = classLoader2;
        return this;
    }

    public TestRequestBuilder ignoreSuiteMethods(boolean z) {
        this.ignoreSuiteMethods = z;
        return this;
    }

    public TestRequestBuilder addTestClass(String str) {
        this.includedClasses.add(str);
        return this;
    }

    public TestRequestBuilder removeTestClass(String str) {
        this.excludedClasses.add(str);
        return this;
    }

    public TestRequestBuilder addTestMethod(String str, String str2) {
        this.includedClasses.add(str);
        this.classMethodFilter.addMethod(str, str2);
        return this;
    }

    public TestRequestBuilder removeTestMethod(String str, String str2) {
        this.classMethodFilter.removeMethod(str, str2);
        return this;
    }

    public TestRequestBuilder addTestPackage(String str) {
        this.includedPackages.add(str);
        return this;
    }

    public TestRequestBuilder removeTestPackage(String str) {
        this.excludedPackages.add(str);
        return this;
    }

    public TestRequestBuilder setTestsRegExFilter(String str) {
        this.testsRegExFilter.setPattern(str);
        return this;
    }

    public TestRequestBuilder addTestSizeFilter(TestSize testSize) {
        if (!TestSize.NONE.equals(testSize)) {
            addFilter(new SizeFilter(testSize));
        } else {
            Log.e(TAG, String.format("Unrecognized test size '%s'", new Object[]{testSize.getSizeQualifierName()}));
        }
        return this;
    }

    public TestRequestBuilder addAnnotationInclusionFilter(String str) {
        Class<? extends Annotation> loadAnnotationClass = loadAnnotationClass(str);
        if (loadAnnotationClass != null) {
            addFilter(new AnnotationInclusionFilter(loadAnnotationClass));
        }
        return this;
    }

    public TestRequestBuilder addAnnotationExclusionFilter(String str) {
        Class<? extends Annotation> loadAnnotationClass = loadAnnotationClass(str);
        if (loadAnnotationClass != null) {
            addFilter(new AnnotationExclusionFilter(loadAnnotationClass));
        }
        return this;
    }

    public TestRequestBuilder addShardingFilter(int i, int i2) {
        return addFilter(new ShardingFilter(i, i2));
    }

    public TestRequestBuilder addFilter(Filter filter2) {
        this.filter = this.filter.intersect(filter2);
        return this;
    }

    public TestRequestBuilder addCustomRunnerBuilderClass(Class<? extends RunnerBuilder> cls) {
        this.customRunnerBuilderClasses.add(cls);
        return this;
    }

    public TestRequestBuilder setSkipExecution(boolean z) {
        this.skipExecution = z;
        return this;
    }

    public TestRequestBuilder setPerTestTimeout(long j) {
        this.perTestTimeout = j;
        return this;
    }

    public TestRequestBuilder addFromRunnerArgs(RunnerArgs runnerArgs) {
        for (RunnerArgs.TestArg next : runnerArgs.tests) {
            if (next.methodName == null) {
                addTestClass(next.testClassName);
            } else {
                addTestMethod(next.testClassName, next.methodName);
            }
        }
        for (RunnerArgs.TestArg next2 : runnerArgs.notTests) {
            if (next2.methodName == null) {
                removeTestClass(next2.testClassName);
            } else {
                removeTestMethod(next2.testClassName, next2.methodName);
            }
        }
        for (String addTestPackage : runnerArgs.testPackages) {
            addTestPackage(addTestPackage);
        }
        for (String removeTestPackage : runnerArgs.notTestPackages) {
            removeTestPackage(removeTestPackage);
        }
        if (runnerArgs.testSize != null) {
            addTestSizeFilter(TestSize.fromString(runnerArgs.testSize));
        }
        for (String addAnnotationInclusionFilter : runnerArgs.annotations) {
            addAnnotationInclusionFilter(addAnnotationInclusionFilter);
        }
        for (String addAnnotationExclusionFilter : runnerArgs.notAnnotations) {
            addAnnotationExclusionFilter(addAnnotationExclusionFilter);
        }
        for (Filter addFilter : runnerArgs.filters) {
            addFilter(addFilter);
        }
        if (runnerArgs.testTimeout > 0) {
            setPerTestTimeout(runnerArgs.testTimeout);
        }
        if (runnerArgs.numShards > 0 && runnerArgs.shardIndex >= 0 && runnerArgs.shardIndex < runnerArgs.numShards) {
            addShardingFilter(runnerArgs.numShards, runnerArgs.shardIndex);
        }
        if (runnerArgs.logOnly) {
            setSkipExecution(true);
        }
        if (runnerArgs.classLoader != null) {
            setClassLoader(runnerArgs.classLoader);
        }
        for (Class<? extends RunnerBuilder> addCustomRunnerBuilderClass : runnerArgs.runnerBuilderClasses) {
            addCustomRunnerBuilderClass(addCustomRunnerBuilderClass);
        }
        if (runnerArgs.testsRegEx != null) {
            setTestsRegExFilter(runnerArgs.testsRegEx);
        }
        return this;
    }

    public Request build() {
        Collection collection;
        this.includedPackages.removeAll(this.excludedPackages);
        this.includedClasses.removeAll(this.excludedClasses);
        validate(this.includedClasses);
        boolean isEmpty = this.includedClasses.isEmpty();
        TestLoader testLoader = TestLoader.testLoader(this.classLoader, getRunnerBuilder(new AndroidRunnerParams(this.instr, this.argsBundle, this.perTestTimeout, this.ignoreSuiteMethods || isEmpty), isEmpty), isEmpty);
        if (isEmpty) {
            collection = getClassNamesFromClassPath();
        } else {
            collection = this.includedClasses;
        }
        return new LenientFilterRequest(Request.runner(ExtendedSuite.createSuite(testLoader.getRunnersFor(collection, isEmpty))), this.filter);
    }

    private void validate(Set<String> set) {
        if (set.isEmpty() && this.pathsToScan.isEmpty()) {
            throw new IllegalArgumentException(MISSING_ARGUMENTS_MSG);
        }
    }

    private RunnerBuilder getRunnerBuilder(AndroidRunnerParams androidRunnerParams, boolean z) {
        if (this.skipExecution) {
            return new AndroidLogOnlyBuilder(androidRunnerParams, z, this.customRunnerBuilderClasses);
        }
        return new AndroidRunnerBuilder(androidRunnerParams, z, this.customRunnerBuilderClasses);
    }

    private Collection<String> getClassNamesFromClassPath() {
        if (!this.pathsToScan.isEmpty()) {
            Log.i(TAG, String.format("Scanning classpath to find tests in paths %s", new Object[]{this.pathsToScan}));
            ClassPathScanner createClassPathScanner = createClassPathScanner(this.pathsToScan);
            ClassPathScanner.ChainedClassNameFilter chainedClassNameFilter = new ClassPathScanner.ChainedClassNameFilter();
            chainedClassNameFilter.add(new ClassPathScanner.ExternalClassNameFilter());
            for (String str : DEFAULT_EXCLUDED_PACKAGES) {
                if (!this.includedPackages.contains(str)) {
                    this.excludedPackages.add(str);
                }
            }
            if (!this.includedPackages.isEmpty()) {
                chainedClassNameFilter.add(new ClassPathScanner.InclusivePackageNamesFilter(this.includedPackages));
            }
            for (String excludePackageNameFilter : this.excludedPackages) {
                chainedClassNameFilter.add(new ClassPathScanner.ExcludePackageNameFilter(excludePackageNameFilter));
            }
            chainedClassNameFilter.add(new ClassPathScanner.ExcludeClassNamesFilter(this.excludedClasses));
            try {
                return createClassPathScanner.getClassPathEntries(chainedClassNameFilter);
            } catch (IOException e) {
                Log.e(TAG, "Failed to scan classes", e);
                return Collections.emptyList();
            }
        } else {
            throw new IllegalStateException("neither test class to execute or class paths were provided");
        }
    }

    /* access modifiers changed from: package-private */
    public ClassPathScanner createClassPathScanner(List<String> list) {
        return new ClassPathScanner((Collection<String>) list);
    }

    private Class<? extends Annotation> loadAnnotationClass(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            Log.e(TAG, String.format("Could not find annotation class: %s", new Object[]{str}));
            return null;
        } catch (ClassCastException unused2) {
            Log.e(TAG, String.format("Class %s is not an annotation", new Object[]{str}));
            return null;
        }
    }

    /* access modifiers changed from: private */
    public int getDeviceSdkInt() {
        return this.deviceBuild.getSdkVersionInt();
    }

    /* access modifiers changed from: private */
    public String getDeviceHardware() {
        return this.deviceBuild.getHardware();
    }

    /* access modifiers changed from: private */
    public String getDeviceCodeName() {
        return this.deviceBuild.getCodeName();
    }
}
