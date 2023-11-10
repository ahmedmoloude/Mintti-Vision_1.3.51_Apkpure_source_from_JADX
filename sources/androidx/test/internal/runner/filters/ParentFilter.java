package androidx.test.internal.runner.filters;

import java.util.Iterator;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

public abstract class ParentFilter extends Filter {
    /* access modifiers changed from: protected */
    public abstract boolean evaluateTest(Description description);

    public boolean shouldRun(Description description) {
        if (description.isTest()) {
            return evaluateTest(description);
        }
        Iterator<Description> it = description.getChildren().iterator();
        while (it.hasNext()) {
            if (shouldRun(it.next())) {
                return true;
            }
        }
        return false;
    }
}
