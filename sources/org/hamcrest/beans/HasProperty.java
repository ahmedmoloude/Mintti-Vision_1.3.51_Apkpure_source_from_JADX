package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class HasProperty<T> extends TypeSafeMatcher<T> {
    private final String propertyName;

    public HasProperty(String str) {
        this.propertyName = str;
    }

    public boolean matchesSafely(T t) {
        try {
            return PropertyUtil.getPropertyDescriptor(this.propertyName, t) != null;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public void describeMismatchSafely(T t, Description description) {
        description.appendText("no ").appendValue(this.propertyName).appendText(" in ").appendValue(t);
    }

    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(this.propertyName).appendText(")");
    }

    @Factory
    public static <T> Matcher<T> hasProperty(String str) {
        return new HasProperty(str);
    }
}
