package androidx.test.internal.runner;

import androidx.test.internal.runner.junit3.AndroidJUnit3Builder;
import androidx.test.internal.runner.junit3.AndroidSuiteBuilder;
import androidx.test.internal.runner.junit4.AndroidAnnotatedBuilder;
import androidx.test.internal.runner.junit4.AndroidJUnit4Builder;
import androidx.test.internal.util.AndroidRunnerParams;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.internal.builders.AnnotatedBuilder;
import org.junit.internal.builders.IgnoredBuilder;
import org.junit.internal.builders.JUnit3Builder;
import org.junit.internal.builders.JUnit4Builder;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

class AndroidRunnerBuilder extends AllDefaultPossibilitiesBuilder {
    private final AndroidAnnotatedBuilder androidAnnotatedBuilder;
    private final AndroidJUnit3Builder androidJUnit3Builder;
    private final AndroidJUnit4Builder androidJUnit4Builder;
    private final AndroidSuiteBuilder androidSuiteBuilder;
    private final List<RunnerBuilder> customRunnerBuilders;
    private final IgnoredBuilder ignoredBuilder;

    public AndroidRunnerBuilder(AndroidRunnerParams androidRunnerParams) {
        this((RunnerBuilder) null, androidRunnerParams, false, Collections.emptyList());
    }

    AndroidRunnerBuilder(AndroidRunnerParams androidRunnerParams, boolean z, List<Class<? extends RunnerBuilder>> list) {
        this((RunnerBuilder) null, androidRunnerParams, z, list);
    }

    AndroidRunnerBuilder(RunnerBuilder runnerBuilder, AndroidRunnerParams androidRunnerParams, boolean z, List<Class<? extends RunnerBuilder>> list) {
        super(true);
        this.androidJUnit3Builder = new AndroidJUnit3Builder(androidRunnerParams, z);
        this.androidJUnit4Builder = new AndroidJUnit4Builder(androidRunnerParams, z);
        this.androidSuiteBuilder = new AndroidSuiteBuilder(androidRunnerParams);
        this.androidAnnotatedBuilder = new AndroidAnnotatedBuilder(runnerBuilder == null ? this : runnerBuilder, androidRunnerParams);
        this.ignoredBuilder = new IgnoredBuilder();
        this.customRunnerBuilders = instantiateRunnerBuilders(list);
    }

    private List<RunnerBuilder> instantiateRunnerBuilders(List<Class<? extends RunnerBuilder>> list) {
        ArrayList arrayList = new ArrayList();
        for (Class next : list) {
            try {
                arrayList.add((RunnerBuilder) next.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
            } catch (InstantiationException e) {
                String valueOf = String.valueOf(next);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 113);
                sb.append("Could not create instance of ");
                sb.append(valueOf);
                sb.append(", make sure that it is a public concrete class with a public no-argument constructor");
                throw new IllegalStateException(sb.toString(), e);
            } catch (IllegalAccessException e2) {
                String valueOf2 = String.valueOf(next);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 113);
                sb2.append("Could not create instance of ");
                sb2.append(valueOf2);
                sb2.append(", make sure that it is a public concrete class with a public no-argument constructor");
                throw new IllegalStateException(sb2.toString(), e2);
            } catch (NoSuchMethodException e3) {
                String valueOf3 = String.valueOf(next);
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 113);
                sb3.append("Could not create instance of ");
                sb3.append(valueOf3);
                sb3.append(", make sure that it is a public concrete class with a public no-argument constructor");
                throw new IllegalStateException(sb3.toString(), e3);
            } catch (InvocationTargetException e4) {
                String valueOf4 = String.valueOf(next);
                StringBuilder sb4 = new StringBuilder(String.valueOf(valueOf4).length() + 74);
                sb4.append("Could not create instance of ");
                sb4.append(valueOf4);
                sb4.append(", the constructor must not throw an exception");
                throw new IllegalStateException(sb4.toString(), e4);
            }
        }
        return arrayList;
    }

    public Runner runnerForClass(Class<?> cls) throws Throwable {
        for (RunnerBuilder safeRunnerForClass : this.customRunnerBuilders) {
            Runner safeRunnerForClass2 = safeRunnerForClass.safeRunnerForClass(cls);
            if (safeRunnerForClass2 != null) {
                return safeRunnerForClass2;
            }
        }
        return super.runnerForClass(cls);
    }

    /* access modifiers changed from: protected */
    public JUnit4Builder junit4Builder() {
        return this.androidJUnit4Builder;
    }

    /* access modifiers changed from: protected */
    public JUnit3Builder junit3Builder() {
        return this.androidJUnit3Builder;
    }

    /* access modifiers changed from: protected */
    public AnnotatedBuilder annotatedBuilder() {
        return this.androidAnnotatedBuilder;
    }

    /* access modifiers changed from: protected */
    public IgnoredBuilder ignoredBuilder() {
        return this.ignoredBuilder;
    }

    /* access modifiers changed from: protected */
    public RunnerBuilder suiteMethodBuilder() {
        return this.androidSuiteBuilder;
    }
}
