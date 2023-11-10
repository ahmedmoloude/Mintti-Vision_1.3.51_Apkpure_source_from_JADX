package org.hamcrest.collection;

import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsIn<T> extends BaseMatcher<T> {
    private final Collection<T> collection;

    public IsIn(Collection<T> collection2) {
        this.collection = collection2;
    }

    public IsIn(T[] tArr) {
        this.collection = Arrays.asList(tArr);
    }

    public boolean matches(Object obj) {
        return this.collection.contains(obj);
    }

    public void describeTo(Description description) {
        description.appendText("one of ");
        description.appendValueList("{", ", ", "}", this.collection);
    }

    @Factory
    public static <T> Matcher<T> isIn(Collection<T> collection2) {
        return new IsIn(collection2);
    }

    @Factory
    public static <T> Matcher<T> isIn(T[] tArr) {
        return new IsIn(tArr);
    }

    @Factory
    public static <T> Matcher<T> isOneOf(T... tArr) {
        return isIn(tArr);
    }
}
