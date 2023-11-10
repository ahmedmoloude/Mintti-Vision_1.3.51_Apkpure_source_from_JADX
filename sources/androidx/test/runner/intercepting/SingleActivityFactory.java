package androidx.test.runner.intercepting;

import android.app.Activity;
import android.content.Intent;
import androidx.test.internal.util.Checks;

public abstract class SingleActivityFactory<T extends Activity> implements InterceptingActivityFactory {
    private final Class<T> activityClassToIntercept;

    /* access modifiers changed from: protected */
    public abstract T create(Intent intent);

    public SingleActivityFactory(Class<T> cls) {
        Checks.checkNotNull(cls);
        this.activityClassToIntercept = (Class) Checks.checkNotNull(cls);
    }

    public final boolean shouldIntercept(ClassLoader classLoader, String str, Intent intent) {
        return this.activityClassToIntercept.getName().equals(str);
    }

    public final Activity create(ClassLoader classLoader, String str, Intent intent) {
        if (shouldIntercept(classLoader, str, intent)) {
            return create(intent);
        }
        throw new UnsupportedOperationException(String.format("Can't create instance of %s", new Object[]{str}));
    }

    public final Class<T> getActivityClassToIntercept() {
        return this.activityClassToIntercept;
    }
}
