package androidx.test.espresso.matcher;

import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import java.util.ArrayList;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;

public abstract class BoundedDiagnosingMatcher<S, T extends S> extends BaseMatcher<S> {
    private final Matcher<Class<?>> matcher;

    public BoundedDiagnosingMatcher(Class<? extends S> cls) {
        this.matcher = Matchers.instanceOf((Class) Preconditions.checkNotNull(cls));
    }

    /* access modifiers changed from: protected */
    public abstract void describeMoreTo(Description description);

    public final void describeTo(Description description) {
        this.matcher.describeTo(description);
        StringDescription stringDescription = new StringDescription();
        describeMoreTo(stringDescription);
        String obj = stringDescription.toString();
        if (!obj.isEmpty()) {
            description.appendText(" and ").appendText(obj);
        }
    }

    public final boolean matches(Object obj) {
        return obj != null && this.matcher.matches(obj) && matchesSafely(obj, Description.NONE);
    }

    /* access modifiers changed from: protected */
    public abstract boolean matchesSafely(T t, Description description);

    public final void describeMismatch(Object obj, Description description) {
        if (obj == null) {
            description.appendText("was null");
        } else if (!this.matcher.matches(obj)) {
            this.matcher.describeMismatch(obj, description);
        } else {
            matchesSafely(obj, description);
        }
    }

    public BoundedDiagnosingMatcher(Class<? extends S> cls, Class<?> cls2, Class<?>... clsArr) {
        ArrayList arrayList = new ArrayList(clsArr.length + 2);
        arrayList.add(Matchers.instanceOf((Class) Preconditions.checkNotNull(cls)));
        Preconditions.checkNotNull(clsArr);
        arrayList.add(Matchers.instanceOf((Class) Preconditions.checkNotNull(cls2)));
        Preconditions.checkArgument(cls2.isInterface());
        for (Class<?> cls3 : clsArr) {
            arrayList.add(Matchers.instanceOf((Class) Preconditions.checkNotNull(cls3)));
            Preconditions.checkArgument(cls3.isInterface());
        }
        this.matcher = Matchers.allOf(arrayList);
    }
}
