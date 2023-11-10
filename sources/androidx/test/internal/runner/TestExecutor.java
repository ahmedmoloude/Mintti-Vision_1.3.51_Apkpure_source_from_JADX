package androidx.test.internal.runner;

import android.app.Instrumentation;
import android.os.Bundle;
import android.util.Log;
import androidx.test.internal.runner.listener.InstrumentationRunListener;
import androidx.test.internal.util.Checks;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public final class TestExecutor {
    private static final String LOG_TAG = "TestExecutor";
    private final Instrumentation instr;
    private final List<RunListener> listeners;

    private TestExecutor(Builder builder) {
        this.listeners = (List) Checks.checkNotNull(builder.listeners);
        this.instr = builder.instr;
    }

    public Bundle execute(Request request) {
        String format;
        Bundle bundle = new Bundle();
        Result result = new Result();
        try {
            JUnitCore jUnitCore = new JUnitCore();
            setUpListeners(jUnitCore);
            Result run = jUnitCore.run(request);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(byteArrayOutputStream);
            reportRunEnded(this.listeners, printStream, bundle, run);
            printStream.close();
            format = String.format("\n%s", new Object[]{byteArrayOutputStream.toString()});
        } catch (Throwable th) {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            PrintStream printStream2 = new PrintStream(byteArrayOutputStream2);
            reportRunEnded(this.listeners, printStream2, bundle, result);
            printStream2.close();
            bundle.putString("stream", String.format("\n%s", new Object[]{byteArrayOutputStream2.toString()}));
            throw th;
        }
        bundle.putString("stream", format);
        return bundle;
    }

    private void setUpListeners(JUnitCore jUnitCore) {
        for (RunListener next : this.listeners) {
            String valueOf = String.valueOf(next.getClass().getName());
            Log.d(LOG_TAG, valueOf.length() != 0 ? "Adding listener ".concat(valueOf) : new String("Adding listener "));
            jUnitCore.addListener(next);
            if (next instanceof InstrumentationRunListener) {
                ((InstrumentationRunListener) next).setInstrumentation(this.instr);
            }
        }
    }

    private void reportRunEnded(List<RunListener> list, PrintStream printStream, Bundle bundle, Result result) {
        for (RunListener next : list) {
            if (next instanceof InstrumentationRunListener) {
                ((InstrumentationRunListener) next).instrumentationRunFinished(printStream, bundle, result);
            }
        }
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public final Instrumentation instr;
        /* access modifiers changed from: private */
        public final List<RunListener> listeners = new ArrayList();

        public Builder(Instrumentation instrumentation) {
            this.instr = instrumentation;
        }

        public Builder addRunListener(RunListener runListener) {
            this.listeners.add(runListener);
            return this;
        }

        public TestExecutor build() {
            return new TestExecutor(this);
        }
    }
}
