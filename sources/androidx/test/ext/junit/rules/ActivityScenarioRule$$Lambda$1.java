package androidx.test.ext.junit.rules;

import android.os.Bundle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.util.Checks;

/* compiled from: ActivityScenarioRule */
final /* synthetic */ class ActivityScenarioRule$$Lambda$1 implements ActivityScenarioRule.Supplier {
    private final Class arg$1;
    private final Bundle arg$2;

    ActivityScenarioRule$$Lambda$1(Class cls, Bundle bundle) {
        this.arg$1 = cls;
        this.arg$2 = bundle;
    }

    public Object get() {
        return ActivityScenario.launch((Class) Checks.checkNotNull(this.arg$1), this.arg$2);
    }
}
