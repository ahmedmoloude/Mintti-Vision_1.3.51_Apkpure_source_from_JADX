package androidx.test.ext.junit.rules;

import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.util.Checks;

/* compiled from: ActivityScenarioRule */
final /* synthetic */ class ActivityScenarioRule$$Lambda$2 implements ActivityScenarioRule.Supplier {
    private final Intent arg$1;

    ActivityScenarioRule$$Lambda$2(Intent intent) {
        this.arg$1 = intent;
    }

    public Object get() {
        return ActivityScenario.launch((Intent) Checks.checkNotNull(this.arg$1));
    }
}
