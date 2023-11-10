package androidx.test.internal.platform.app;

import android.content.ComponentName;
import android.content.Intent;
import androidx.test.platform.app.InstrumentationRegistry;

public abstract /* synthetic */ class ActivityInvoker$$CC {
    public static Intent getIntentForActivity$$dflt$$(ActivityInvoker activityInvoker, Class cls) {
        Intent makeMainActivity = Intent.makeMainActivity(new ComponentName(InstrumentationRegistry.getInstrumentation().getTargetContext(), cls));
        if (InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageManager().resolveActivity(makeMainActivity, 0) != null) {
            return makeMainActivity;
        }
        return Intent.makeMainActivity(new ComponentName(InstrumentationRegistry.getInstrumentation().getContext(), cls));
    }
}
