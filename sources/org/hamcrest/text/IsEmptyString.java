package org.hamcrest.text;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsNull;

public final class IsEmptyString extends BaseMatcher<String> {
    private static final IsEmptyString INSTANCE;
    private static final Matcher<String> NULL_OR_EMPTY_INSTANCE;

    static {
        IsEmptyString isEmptyString = new IsEmptyString();
        INSTANCE = isEmptyString;
        NULL_OR_EMPTY_INSTANCE = AnyOf.anyOf((Matcher<? super T>[]) new Matcher[]{IsNull.nullValue(), isEmptyString});
    }

    public boolean matches(Object obj) {
        return obj != null && (obj instanceof String) && ((String) obj).equals("");
    }

    public void describeTo(Description description) {
        description.appendText("an empty string");
    }

    @Factory
    public static Matcher<String> isEmptyString() {
        return INSTANCE;
    }

    @Factory
    public static Matcher<String> isEmptyOrNullString() {
        return NULL_OR_EMPTY_INSTANCE;
    }
}
