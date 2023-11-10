package androidx.test.espresso.matcher;

import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public abstract class BoundedMatcher<T, S extends T> extends BaseMatcher<T> {
    private final Class<?> expectedType;
    private final Class<?>[] interfaceTypes;

    public BoundedMatcher(Class<? extends S> cls) {
        this.expectedType = (Class) Preconditions.checkNotNull(cls);
        this.interfaceTypes = new Class[0];
    }

    /* access modifiers changed from: protected */
    public abstract boolean matchesSafely(S s);

    public void describeMismatch(Object obj, Description description) {
        if (obj == null) {
            description.appendText("item was null");
        } else if (!this.expectedType.isInstance(obj)) {
            description.appendText("item does not extend ").appendText(this.expectedType.getName());
        } else {
            for (Class<?> cls : this.interfaceTypes) {
                if (!cls.isInstance(obj)) {
                    description.appendText("item does not implement ").appendText(cls.getName());
                    return;
                }
            }
        }
    }

    public final boolean matches(Object obj) {
        if (obj == null || !this.expectedType.isInstance(obj)) {
            return false;
        }
        for (Class<?> isInstance : this.interfaceTypes) {
            if (!isInstance.isInstance(obj)) {
                return false;
            }
        }
        return matchesSafely(obj);
    }

    public BoundedMatcher(Class<?> cls, Class<?> cls2, Class<?>... clsArr) {
        this.expectedType = (Class) Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(clsArr);
        Class<?>[] clsArr2 = new Class[(clsArr.length + 1)];
        this.interfaceTypes = clsArr2;
        clsArr2[0] = (Class) Preconditions.checkNotNull(cls2);
        Preconditions.checkArgument(cls2.isInterface());
        int i = 1;
        for (Class<?> cls3 : clsArr) {
            this.interfaceTypes[i] = (Class) Preconditions.checkNotNull(cls3);
            Preconditions.checkArgument(cls3.isInterface());
            i++;
        }
    }
}
