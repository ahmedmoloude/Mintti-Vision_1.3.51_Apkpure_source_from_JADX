package androidx.test.espresso.core.internal.deps.guava.base;

import java.util.Locale;
import java.util.logging.Logger;

final class Platform {
    private static final Logger logger = Logger.getLogger(Platform.class.getName());
    private static final PatternCompiler patternCompiler = loadPatternCompiler();

    private static final class JdkPatternCompiler implements PatternCompiler {
        private JdkPatternCompiler() {
        }
    }

    private Platform() {
    }

    static String emptyToNull(String str) {
        if (stringIsNullOrEmpty(str)) {
            return null;
        }
        return str;
    }

    static String formatCompact4Digits(double d) {
        return String.format(Locale.ROOT, "%.4g", new Object[]{Double.valueOf(d)});
    }

    private static PatternCompiler loadPatternCompiler() {
        return new JdkPatternCompiler();
    }

    static boolean stringIsNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    static long systemNanoTime() {
        return System.nanoTime();
    }
}
