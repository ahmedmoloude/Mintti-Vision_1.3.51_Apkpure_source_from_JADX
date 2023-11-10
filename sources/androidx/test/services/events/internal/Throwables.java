package androidx.test.services.events.internal;

import androidx.test.runner.internal.deps.desugar.ThrowableExtension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class Throwables {
    private static final String[] REFLECTION_METHOD_NAME_PREFIXES = {"sun.reflect.", "java.lang.reflect.", "jdk.internal.reflect.", "org.junit.rules.RunRules.<init>(", "org.junit.rules.RunRules.applyAll(", "org.junit.runners.BlockJUnit4ClassRunner.withMethodRules(", "junit.framework.TestCase.runBare("};
    private static final String[] TEST_FRAMEWORK_METHOD_NAME_PREFIXES = {"org.junit.runner.", "org.junit.runners.", "org.junit.experimental.runners.", "org.junit.internal.", "junit."};
    private static final String[] TEST_FRAMEWORK_TEST_METHOD_NAME_PREFIXES = {"org.junit.internal.StackTracesTest"};

    private Throwables() {
    }

    public static String getTrimmedStackTrace(Throwable th) {
        List<String> trimmedStackTraceLines = getTrimmedStackTraceLines(th);
        if (trimmedStackTraceLines.isEmpty()) {
            return getFullStackTrace(th);
        }
        StringBuilder sb = new StringBuilder(th.toString());
        appendStackTraceLines(trimmedStackTraceLines, sb);
        appendStackTraceLines(getCauseStackTraceLines(th), sb);
        return sb.toString();
    }

    private static List<String> getTrimmedStackTraceLines(Throwable th) {
        List asList = Arrays.asList(th.getStackTrace());
        int size = asList.size();
        State state = State.PROCESSING_OTHER_CODE;
        for (StackTraceElement processStackTraceElement : asReversedList(asList)) {
            state = state.processStackTraceElement(processStackTraceElement);
            if (state == State.DONE) {
                ArrayList arrayList = new ArrayList(size + 2);
                arrayList.add("");
                for (StackTraceElement valueOf : asList.subList(0, size)) {
                    String valueOf2 = String.valueOf(valueOf);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 4);
                    sb.append("\tat ");
                    sb.append(valueOf2);
                    arrayList.add(sb.toString());
                }
                if (th.getCause() != null) {
                    StringBuilder sb2 = new StringBuilder(24);
                    sb2.append("\t... ");
                    sb2.append(asList.size() - arrayList.size());
                    sb2.append(" trimmed");
                    arrayList.add(sb2.toString());
                }
                return arrayList;
            }
            size--;
        }
        return Collections.emptyList();
    }

    private static List<String> getCauseStackTraceLines(Throwable th) {
        String readLine;
        if (th.getCause() != null) {
            BufferedReader bufferedReader = new BufferedReader(new StringReader(getFullStackTrace(th).substring(th.toString().length())));
            ArrayList arrayList = new ArrayList();
            do {
                try {
                    readLine = bufferedReader.readLine();
                    if (readLine != null) {
                    }
                } catch (IOException unused) {
                }
            } while (!readLine.startsWith("Caused by: "));
            arrayList.add(readLine);
            while (true) {
                String readLine2 = bufferedReader.readLine();
                if (readLine2 == null) {
                    return arrayList;
                }
                arrayList.add(readLine2);
            }
        }
        return Collections.emptyList();
    }

    private static String getFullStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        ThrowableExtension.printStackTrace(th, new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private static void appendStackTraceLines(List<String> list, StringBuilder sb) {
        for (String str : list) {
            sb.append(String.format("%s%n", new Object[]{str}));
        }
    }

    private static <T> List<T> asReversedList(final List<T> list) {
        return new AbstractList<T>() {
            public T get(int i) {
                List list = list;
                return list.get((list.size() - i) - 1);
            }

            public int size() {
                return list.size();
            }
        };
    }

    private enum State {
        PROCESSING_OTHER_CODE {
            public State processLine(String str) {
                return Throwables.isTestFrameworkMethod(str) ? PROCESSING_TEST_FRAMEWORK_CODE : this;
            }
        },
        PROCESSING_TEST_FRAMEWORK_CODE {
            public State processLine(String str) {
                if (Throwables.isReflectionMethod(str)) {
                    return PROCESSING_REFLECTION_CODE;
                }
                if (Throwables.isTestFrameworkMethod(str)) {
                    return this;
                }
                return PROCESSING_OTHER_CODE;
            }
        },
        PROCESSING_REFLECTION_CODE {
            public State processLine(String str) {
                if (Throwables.isReflectionMethod(str)) {
                    return this;
                }
                if (Throwables.isTestFrameworkMethod(str)) {
                    return PROCESSING_TEST_FRAMEWORK_CODE;
                }
                return DONE;
            }
        },
        DONE {
            public State processLine(String str) {
                return this;
            }
        };

        /* access modifiers changed from: protected */
        public abstract State processLine(String str);

        public final State processStackTraceElement(StackTraceElement stackTraceElement) {
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            StringBuilder sb = new StringBuilder(String.valueOf(className).length() + 3 + String.valueOf(methodName).length());
            sb.append(className);
            sb.append(".");
            sb.append(methodName);
            sb.append("()");
            return processLine(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public static boolean isTestFrameworkMethod(String str) {
        return isMatchingMethod(str, TEST_FRAMEWORK_METHOD_NAME_PREFIXES) && !isMatchingMethod(str, TEST_FRAMEWORK_TEST_METHOD_NAME_PREFIXES);
    }

    /* access modifiers changed from: private */
    public static boolean isReflectionMethod(String str) {
        return isMatchingMethod(str, REFLECTION_METHOD_NAME_PREFIXES);
    }

    private static boolean isMatchingMethod(String str, String[] strArr) {
        for (String startsWith : strArr) {
            if (str.startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }
}
