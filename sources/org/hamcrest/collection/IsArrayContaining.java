package org.hamcrest.collection;

import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

public class IsArrayContaining<T> extends TypeSafeMatcher<T[]> {
    private final Matcher<? super T> elementMatcher;

    public IsArrayContaining(Matcher<? super T> matcher) {
        this.elementMatcher = matcher;
    }

    public boolean matchesSafely(T[] tArr) {
        for (T matches : tArr) {
            if (this.elementMatcher.matches(matches)) {
                return true;
            }
        }
        return false;
    }

    public void describeMismatchSafely(T[] tArr, Description description) {
        super.describeMismatch(Arrays.asList(tArr), description);
    }

    public void describeTo(Description description) {
        description.appendText("an array containing ").appendDescriptionOf(this.elementMatcher);
    }

    @Factory
    public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> matcher) {
        return new IsArrayContaining(matcher);
    }

    @Factory
    public static <T> Matcher<T[]> hasItemInArray(T t) {
        return hasItemInArray(IsEqual.equalTo(t));
    }
}
