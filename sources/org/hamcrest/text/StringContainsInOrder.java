package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class StringContainsInOrder extends TypeSafeMatcher<String> {
    private final Iterable<String> substrings;

    public StringContainsInOrder(Iterable<String> iterable) {
        this.substrings = iterable;
    }

    public boolean matchesSafely(String str) {
        int i = 0;
        for (String indexOf : this.substrings) {
            i = str.indexOf(indexOf, i);
            if (i == -1) {
                return false;
            }
        }
        return true;
    }

    public void describeMismatchSafely(String str, Description description) {
        description.appendText("was \"").appendText(str).appendText("\"");
    }

    public void describeTo(Description description) {
        description.appendText("a string containing ").appendValueList("", ", ", "", this.substrings).appendText(" in order");
    }

    @Factory
    public static Matcher<String> stringContainsInOrder(Iterable<String> iterable) {
        return new StringContainsInOrder(iterable);
    }
}
