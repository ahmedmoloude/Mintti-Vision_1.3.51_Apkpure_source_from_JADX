package org.hamcrest.object;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class HasToString<T> extends FeatureMatcher<T, String> {
    public HasToString(Matcher<? super String> matcher) {
        super(matcher, "with toString()", "toString()");
    }

    /* access modifiers changed from: protected */
    public String featureValueOf(T t) {
        return String.valueOf(t);
    }

    @Factory
    public static <T> Matcher<T> hasToString(Matcher<? super String> matcher) {
        return new HasToString(matcher);
    }

    @Factory
    public static <T> Matcher<T> hasToString(String str) {
        return new HasToString(IsEqual.equalTo(str));
    }
}
