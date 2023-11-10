package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEqualIgnoringWhiteSpace extends TypeSafeMatcher<String> {
    private final String string;

    public IsEqualIgnoringWhiteSpace(String str) {
        if (str != null) {
            this.string = str;
            return;
        }
        throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
    }

    public boolean matchesSafely(String str) {
        return stripSpace(this.string).equalsIgnoreCase(stripSpace(str));
    }

    public void describeMismatchSafely(String str, Description description) {
        description.appendText("was  ").appendText(stripSpace(str));
    }

    public void describeTo(Description description) {
        description.appendText("equalToIgnoringWhiteSpace(").appendValue(this.string).appendText(")");
    }

    public String stripSpace(String str) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isWhitespace(charAt)) {
                if (!z) {
                    sb.append(' ');
                }
                z = true;
            } else {
                sb.append(charAt);
                z = false;
            }
        }
        return sb.toString().trim();
    }

    @Factory
    public static Matcher<String> equalToIgnoringWhiteSpace(String str) {
        return new IsEqualIgnoringWhiteSpace(str);
    }
}
