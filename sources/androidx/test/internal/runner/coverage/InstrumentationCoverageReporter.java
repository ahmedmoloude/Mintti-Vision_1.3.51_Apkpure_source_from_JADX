package androidx.test.internal.runner.coverage;

import android.app.Instrumentation;
import android.util.Log;
import androidx.test.internal.runner.storage.RunnerIO;
import androidx.test.internal.runner.storage.RunnerTestStorageIO;
import androidx.test.runner.internal.deps.desugar.ThrowableExtension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class InstrumentationCoverageReporter {
    private static final String DEFAULT_COVERAGE_FILE_NAME = "coverage.ec";
    private static final String EMMA_RUNTIME_CLASS = "com.vladium.emma.rt.RT";
    private static final String TAG = "InstrumentationCoverageReporter";
    private final Instrumentation instrumentation;
    private final RunnerIO runnerIO;

    public InstrumentationCoverageReporter(Instrumentation instrumentation2, RunnerIO runnerIO2) {
        this.instrumentation = instrumentation2;
        this.runnerIO = runnerIO2;
    }

    public String generateCoverageReport(String str, PrintStream printStream) {
        String str2;
        if (this.runnerIO instanceof RunnerTestStorageIO) {
            str2 = dumpCoverageToTestStorage(str, printStream);
        } else {
            str2 = dumpCoverageToFile(str, printStream);
        }
        String str3 = TAG;
        String valueOf = String.valueOf(str2);
        Log.d(str3, valueOf.length() != 0 ? "Coverage file was generated to ".concat(valueOf) : new String("Coverage file was generated to "));
        printStream.format("\nGenerated code coverage data to %s", new Object[]{str2});
        return str2;
    }

    private String dumpCoverageToFile(String str, PrintStream printStream) {
        if (str == null) {
            Log.d(TAG, "No coverage file path was specified. Dumps to the default file path.");
            String absolutePath = this.instrumentation.getTargetContext().getFilesDir().getAbsolutePath();
            String str2 = File.separator;
            StringBuilder sb = new StringBuilder(String.valueOf(absolutePath).length() + 11 + String.valueOf(str2).length());
            sb.append(absolutePath);
            sb.append(str2);
            sb.append(DEFAULT_COVERAGE_FILE_NAME);
            str = sb.toString();
        }
        if (!generateCoverageInternal(str, printStream)) {
            Log.w(TAG, "Failed to generate the coverage data file. Please refer to the instrumentation result for more info.");
        }
        return str;
    }

    private String dumpCoverageToTestStorage(String str, PrintStream printStream) {
        if (str == null) {
            Log.d(TAG, "No coverage file path was specified. Dumps to the default coverage file using test storage.");
            str = DEFAULT_COVERAGE_FILE_NAME;
        }
        String absolutePath = this.instrumentation.getTargetContext().getFilesDir().getAbsolutePath();
        String str2 = File.separator;
        StringBuilder sb = new StringBuilder(String.valueOf(absolutePath).length() + 11 + String.valueOf(str2).length());
        sb.append(absolutePath);
        sb.append(str2);
        sb.append(DEFAULT_COVERAGE_FILE_NAME);
        String sb2 = sb.toString();
        if (!generateCoverageInternal(sb2, printStream)) {
            Log.w(TAG, "Failed to generate the coverage data file. Please refer to the instrumentation result for more info.");
        }
        try {
            Log.d(TAG, "Test service is available. Moving the coverage data file to be managed by the storage service.");
            moveFileToTestStorage(sb2, str);
            return str;
        } catch (IOException e) {
            reportEmmaError(printStream, e);
            return null;
        }
    }

    private void moveFileToTestStorage(String str, String str2) throws IOException {
        WritableByteChannel newChannel;
        File file = new File(str);
        if (file.exists()) {
            String str3 = TAG;
            Log.d(str3, String.format("Moving coverage file [%s] to the internal test storage [%s].", new Object[]{str, str2}));
            OutputStream openOutputStream = this.runnerIO.openOutputStream(str2);
            try {
                FileChannel channel = new FileInputStream(str).getChannel();
                try {
                    newChannel = Channels.newChannel(openOutputStream);
                    channel.transferTo(0, channel.size(), newChannel);
                    if (newChannel != null) {
                        newChannel.close();
                    }
                    if (channel != null) {
                        channel.close();
                    }
                    if (openOutputStream != null) {
                        openOutputStream.close();
                    }
                    if (!file.delete()) {
                        Log.e(str3, String.format("Failed to delete original coverage file [%s]", new Object[]{file.getAbsolutePath()}));
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    if (channel != null) {
                        channel.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                if (openOutputStream != null) {
                    try {
                        openOutputStream.close();
                    } catch (Throwable th3) {
                        ThrowableExtension.addSuppressed(th2, th3);
                    }
                }
                throw th2;
            }
        } else {
            return;
        }
        throw th;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0018, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:?, code lost:
        r0 = java.lang.Class.forName(EMMA_RUNTIME_CLASS, true, r8.instrumentation.getContext().getClassLoader());
        android.util.Log.w(TAG, "Generating coverage for alternate test context.");
        r10.format("\nWarning: %s", new java.lang.Object[]{"Generating coverage for alternate test context."});
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x006d, code lost:
        reportEmmaError(r10, r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x0022 */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x0018 A[ExcHandler: IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException (r0v5 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:1:0x0009] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean generateCoverageInternal(java.lang.String r9, java.io.PrintStream r10) {
        /*
            r8 = this;
            java.lang.String r0 = "com.vladium.emma.rt.RT"
            java.io.File r1 = new java.io.File
            r1.<init>(r9)
            r9 = 0
            r2 = 1
            android.app.Instrumentation r3 = r8.instrumentation     // Catch:{ ClassNotFoundException -> 0x0022, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            android.content.Context r3 = r3.getTargetContext()     // Catch:{ ClassNotFoundException -> 0x0022, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.ClassLoader r3 = r3.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0022, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.Class r0 = java.lang.Class.forName(r0, r2, r3)     // Catch:{ ClassNotFoundException -> 0x0022, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            goto L_0x0040
        L_0x0018:
            r0 = move-exception
            goto L_0x006d
        L_0x001a:
            r0 = move-exception
            goto L_0x006d
        L_0x001c:
            r0 = move-exception
            goto L_0x006d
        L_0x001e:
            r0 = move-exception
            goto L_0x006d
        L_0x0020:
            r0 = move-exception
            goto L_0x006d
        L_0x0022:
            android.app.Instrumentation r3 = r8.instrumentation     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            android.content.Context r3 = r3.getContext()     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.ClassLoader r3 = r3.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.Class r0 = java.lang.Class.forName(r0, r2, r3)     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.String r3 = "Generating coverage for alternate test context."
            java.lang.String r4 = TAG     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            android.util.Log.w(r4, r3)     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.String r4 = "\nWarning: %s"
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            r5[r9] = r3     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            r10.format(r4, r5)     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
        L_0x0040:
            java.lang.String r3 = "dumpCoverageData"
            r4 = 3
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.Class r6 = r1.getClass()     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            r5[r9] = r6     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.Class r6 = java.lang.Boolean.TYPE     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            r5[r2] = r6     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.Class r6 = java.lang.Boolean.TYPE     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            r7 = 2
            r5[r7] = r6     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.reflect.Method r0 = r0.getMethod(r3, r5)     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            r4[r9] = r1     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r9)     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            r4[r2] = r1     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r9)     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            r4[r7] = r1     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            r0.invoke(r3, r4)     // Catch:{ ClassNotFoundException -> 0x0071, SecurityException -> 0x0020, NoSuchMethodException -> 0x001e, IllegalArgumentException -> 0x001c, IllegalAccessException -> 0x001a, InvocationTargetException -> 0x0018 }
            return r2
        L_0x006d:
            r8.reportEmmaError(r10, r0)
            goto L_0x0077
        L_0x0071:
            r0 = move-exception
            java.lang.String r1 = "Is Emma/JaCoCo jar on classpath?"
            r8.reportEmmaError(r10, r1, r0)
        L_0x0077:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.internal.runner.coverage.InstrumentationCoverageReporter.generateCoverageInternal(java.lang.String, java.io.PrintStream):boolean");
    }

    private void reportEmmaError(PrintStream printStream, Exception exc) {
        reportEmmaError(printStream, "", exc);
    }

    private void reportEmmaError(PrintStream printStream, String str, Exception exc) {
        String valueOf = String.valueOf(str);
        String concat = valueOf.length() != 0 ? "Failed to generate Emma/JaCoCo coverage. ".concat(valueOf) : new String("Failed to generate Emma/JaCoCo coverage. ");
        Log.e(TAG, concat, exc);
        printStream.format("\nError: %s", new Object[]{concat});
    }
}
