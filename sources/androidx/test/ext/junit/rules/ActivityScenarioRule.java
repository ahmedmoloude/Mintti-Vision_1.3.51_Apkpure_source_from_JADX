package androidx.test.ext.junit.rules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.util.Checks;
import org.junit.rules.ExternalResource;

public final class ActivityScenarioRule<A extends Activity> extends ExternalResource {
    private ActivityScenario<A> scenario;
    private final Supplier<ActivityScenario<A>> scenarioSupplier;

    interface Supplier<T> {
        T get();
    }

    public ActivityScenarioRule(Class<A> cls) {
        this.scenarioSupplier = new ActivityScenarioRule$$Lambda$0(cls);
    }

    public ActivityScenarioRule(Class<A> cls, Bundle bundle) {
        this.scenarioSupplier = new ActivityScenarioRule$$Lambda$1(cls, bundle);
    }

    public ActivityScenarioRule(Intent intent) {
        this.scenarioSupplier = new ActivityScenarioRule$$Lambda$2(intent);
    }

    public ActivityScenarioRule(Intent intent, Bundle bundle) {
        this.scenarioSupplier = new ActivityScenarioRule$$Lambda$3(intent, bundle);
    }

    /* access modifiers changed from: protected */
    public void before() throws Throwable {
        this.scenario = this.scenarioSupplier.get();
    }

    /* access modifiers changed from: protected */
    public void after() {
        this.scenario.close();
    }

    public ActivityScenario<A> getScenario() {
        return (ActivityScenario) Checks.checkNotNull(this.scenario);
    }
}
