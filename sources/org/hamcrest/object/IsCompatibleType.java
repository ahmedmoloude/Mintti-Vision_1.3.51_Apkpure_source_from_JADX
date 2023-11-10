package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCompatibleType<T> extends TypeSafeMatcher<Class<?>> {
    private final Class<T> type;

    public IsCompatibleType(Class<T> cls) {
        this.type = cls;
    }

    public boolean matchesSafely(Class<?> cls) {
        return this.type.isAssignableFrom(cls);
    }

    public void describeMismatchSafely(Class<?> cls, Description description) {
        description.appendValue(cls.getName());
    }

    public void describeTo(Description description) {
        description.appendText("type < ").appendText(this.type.getName());
    }

    @Factory
    public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> cls) {
        return new IsCompatibleType(cls);
    }
}
