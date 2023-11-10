package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEqualIgnoringCase extends TypeSafeMatcher<String> {
    private final String string;

    public IsEqualIgnoringCase(String str) {
        if (str != null) {
            this.string = str;
            return;
        }
        throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
    }

    public boolean matchesSafely(String str) {
        return this.string.equalsIgnoreCase(str);
    }

    public void describeMismatchSafely(String str, Description description) {
        description.appendText("was ").appendText(str);
    }

    public void describeTo(Description description) {
        description.appendText("equalToIgnoringCase(").appendValue(this.string).appendText(")");
    }

    @Factory
    public static Matcher<String> equalToIgnoringCase(String str) {
        return new IsEqualIgnoringCase(str);
    }
}
