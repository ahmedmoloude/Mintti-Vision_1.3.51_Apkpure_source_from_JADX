package androidx.test.ext.junit.rules;

import android.content.Intent;
import android.os.Bundle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.util.Checks;

/* compiled from: ActivityScenarioRule */
final /* synthetic */ class ActivityScenarioRule$$Lambda$3 implements ActivityScenarioRule.Supplier {
    private final Intent arg$1;
    private final Bundle arg$2;

    ActivityScenarioRule$$Lambda$3(Intent intent, Bundle bundle) {
        this.arg$1 = intent;
        this.arg$2 = bundle;
    }

    public Object get() {
        return ActivityScenario.launch((Intent) Checks.checkNotNull(this.arg$1), this.arg$2);
    }
}
