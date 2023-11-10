package androidx.test.core.app;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;

public final class ApplicationProvider {
    private ApplicationProvider() {
    }

    public static <T extends Context> T getApplicationContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }
}
