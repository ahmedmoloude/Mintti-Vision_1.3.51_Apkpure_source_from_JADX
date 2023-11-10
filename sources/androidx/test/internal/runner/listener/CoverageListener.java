package androidx.test.internal.runner.listener;

import android.app.Instrumentation;
import android.os.Bundle;
import androidx.test.internal.runner.coverage.InstrumentationCoverageReporter;
import androidx.test.internal.runner.storage.RunnerFileIO;
import androidx.test.internal.runner.storage.RunnerIO;
import java.io.PrintStream;
import org.junit.runner.Result;

public class CoverageListener extends InstrumentationRunListener {
    private static final String REPORT_KEY_COVERAGE_PATH = "coverageFilePath";
    private final String coverageFilePath;
    private InstrumentationCoverageReporter coverageReporter;
    private RunnerIO runnerIO;

    public CoverageListener(String str) {
        this(str, (RunnerIO) new RunnerFileIO());
    }

    public CoverageListener(String str, RunnerIO runnerIO2) {
        this.coverageFilePath = str;
        this.runnerIO = runnerIO2;
    }

    CoverageListener(String str, InstrumentationCoverageReporter instrumentationCoverageReporter) {
        this.coverageFilePath = str;
        this.coverageReporter = instrumentationCoverageReporter;
    }

    public void setInstrumentation(Instrumentation instrumentation) {
        super.setInstrumentation(instrumentation);
        this.coverageReporter = new InstrumentationCoverageReporter(getInstrumentation(), this.runnerIO);
    }

    public void instrumentationRunFinished(PrintStream printStream, Bundle bundle, Result result) {
        bundle.putString(REPORT_KEY_COVERAGE_PATH, this.coverageReporter.generateCoverageReport(this.coverageFilePath, printStream));
    }
}
