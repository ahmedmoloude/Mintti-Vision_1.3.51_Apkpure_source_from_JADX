package androidx.test.ext.junit.rules;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.util.Checks;

/* compiled from: ActivityScenarioRule */
final /* synthetic */ class ActivityScenarioRule$$Lambda$0 implements ActivityScenarioRule.Supplier {
    private final Class arg$1;

    ActivityScenarioRule$$Lambda$0(Class cls) {
        this.arg$1 = cls;
    }

    public Object get() {
        return ActivityScenario.launch((Class) Checks.checkNotNull(this.arg$1));
    }
}
