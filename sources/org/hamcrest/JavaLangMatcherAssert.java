package org.hamcrest;

public class JavaLangMatcherAssert {
    private JavaLangMatcherAssert() {
    }

    public static <T> boolean that(T t, Matcher<? super T> matcher) {
        return matcher.matches(t);
    }
}
