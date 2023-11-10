package androidx.test.internal.runner.filters;

import java.util.regex.Pattern;
import org.junit.runner.Description;

public final class TestsRegExFilter extends ParentFilter {
    private Pattern pattern = null;

    public String describe() {
        return "tests filter";
    }

    public void setPattern(String str) {
        this.pattern = Pattern.compile(str);
    }

    /* access modifiers changed from: protected */
    public boolean evaluateTest(Description description) {
        if (this.pattern == null) {
            return true;
        }
        return this.pattern.matcher(String.format("%s#%s", new Object[]{description.getClassName(), description.getMethodName()})).find();
    }
}
