package androidx.test.ext.junit.runners;

import com.p020kl.commonbase.constants.Constants;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

public final class AndroidJUnit4 extends Runner implements Filterable, Sortable {
    private static final String TAG = "AndroidJUnit4";
    private final Runner delegate;

    public AndroidJUnit4(Class<?> cls) throws InitializationError {
        this.delegate = loadRunner(cls);
    }

    private static String getRunnerClassName() {
        String property = System.getProperty("android.junit.runner", (String) null);
        if (property != null) {
            return property;
        }
        if (System.getProperty("java.runtime.name").toLowerCase().contains(Constants.f840OS) || !hasClass("org.robolectric.RobolectricTestRunner")) {
            return "androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner";
        }
        return "org.robolectric.RobolectricTestRunner";
    }

    private static boolean hasClass(String str) {
        try {
            return Class.forName(str) != null;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private static Runner loadRunner(Class<?> cls) throws InitializationError {
        return loadRunner(cls, getRunnerClassName());
    }

    private static Runner loadRunner(Class<?> cls, String str) throws InitializationError {
        Class<?> cls2;
        Constructor<?> constructor = null;
        try {
            cls2 = Class.forName(str);
        } catch (ClassNotFoundException e) {
            throwInitializationError(String.format("Delegate runner %s for AndroidJUnit4 could not be found.\n", new Object[]{str}), e);
            cls2 = null;
        }
        try {
            constructor = cls2.getConstructor(new Class[]{Class.class});
        } catch (NoSuchMethodException e2) {
            throwInitializationError(String.format("Delegate runner %s for AndroidJUnit4 requires a public constructor that takes a Class<?>.\n", new Object[]{str}), e2);
        }
        try {
            return (Runner) constructor.newInstance(new Object[]{cls});
        } catch (IllegalAccessException e3) {
            throwInitializationError(String.format("Illegal constructor access for test runner %s\n", new Object[]{str}), e3);
            throw new IllegalStateException("Should never reach here");
        } catch (InstantiationException e4) {
            throwInitializationError(String.format("Failed to instantiate test runner %s\n", new Object[]{str}), e4);
            throw new IllegalStateException("Should never reach here");
        } catch (InvocationTargetException e5) {
            throwInitializationError(String.format("Failed to instantiate test runner %s\n%s\n", new Object[]{cls2, getInitializationErrorDetails(e5, cls)}), e5);
            throw new IllegalStateException("Should never reach here");
        }
    }

    private static void throwInitializationError(String str, Throwable th) throws InitializationError {
        throw new InitializationError((Throwable) new RuntimeException(str, th));
    }

    private static String getInitializationErrorDetails(Throwable th, Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        Throwable cause = th.getCause();
        if (cause == null) {
            return "";
        }
        if (cause.getClass() == InitializationError.class) {
            List<Throwable> causes = ((InitializationError) cause).getCauses();
            sb.append(String.format("Test class %s is malformed. (%s problems):\n", new Object[]{cls, Integer.valueOf(causes.size())}));
            for (Throwable append : causes) {
                sb.append(append);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public Description getDescription() {
        return this.delegate.getDescription();
    }

    public void run(RunNotifier runNotifier) {
        this.delegate.run(runNotifier);
    }

    public void filter(Filter filter) throws NoTestsRemainException {
        ((Filterable) this.delegate).filter(filter);
    }

    public void sort(Sorter sorter) {
        ((Sortable) this.delegate).sort(sorter);
    }
}
