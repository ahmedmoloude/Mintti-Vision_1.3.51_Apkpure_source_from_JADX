package androidx.test.espresso;

import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.internal.platform.util.TestOutputEmitter;
import androidx.test.internal.runner.tracker.UsageTrackerRegistry;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public final class GraphHolder {
    private static final AtomicReference<GraphHolder> instance = new AtomicReference<>((Object) null);
    private final BaseLayerComponent component;

    private GraphHolder(BaseLayerComponent baseLayerComponent) {
        this.component = (BaseLayerComponent) Preconditions.checkNotNull(baseLayerComponent);
    }

    static BaseLayerComponent baseLayer() {
        AtomicReference<GraphHolder> atomicReference = instance;
        GraphHolder graphHolder = atomicReference.get();
        if (graphHolder != null) {
            return graphHolder.component;
        }
        GraphHolder graphHolder2 = new GraphHolder(DaggerBaseLayerComponent.create());
        if (!atomicReference.compareAndSet((Object) null, graphHolder2)) {
            return atomicReference.get().component;
        }
        UsageTrackerRegistry.getInstance().trackUsage("Espresso", UsageTrackerRegistry.AxtVersions.ESPRESSO_VERSION);
        HashMap hashMap = new HashMap();
        hashMap.put("Espresso", UsageTrackerRegistry.AxtVersions.ESPRESSO_VERSION);
        TestOutputEmitter.addOutputProperties(hashMap);
        return graphHolder2.component;
    }
}
