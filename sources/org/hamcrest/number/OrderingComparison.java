package org.hamcrest.number;

import java.lang.Comparable;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class OrderingComparison<T extends Comparable<T>> extends TypeSafeMatcher<T> {
    private static final int EQUAL = 0;
    private static final int GREATER_THAN = 1;
    private static final int LESS_THAN = -1;
    private static final String[] comparisonDescriptions = {"less than", "equal to", "greater than"};
    private final T expected;
    private final int maxCompare;
    private final int minCompare;

    private OrderingComparison(T t, int i, int i2) {
        this.expected = t;
        this.minCompare = i;
        this.maxCompare = i2;
    }

    public boolean matchesSafely(T t) {
        int signum = Integer.signum(t.compareTo(this.expected));
        return this.minCompare <= signum && signum <= this.maxCompare;
    }

    public void describeMismatchSafely(T t, Description description) {
        description.appendValue(t).appendText(" was ").appendText(asText(t.compareTo(this.expected))).appendText(" ").appendValue(this.expected);
    }

    public void describeTo(Description description) {
        description.appendText("a value ").appendText(asText(this.minCompare));
        if (this.minCompare != this.maxCompare) {
            description.appendText(" or ").appendText(asText(this.maxCompare));
        }
        description.appendText(" ").appendValue(this.expected);
    }

    private static String asText(int i) {
        return comparisonDescriptions[Integer.signum(i) + 1];
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T t) {
        return new OrderingComparison(t, 0, 0);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThan(T t) {
        return new OrderingComparison(t, 1, 1);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T t) {
        return new OrderingComparison(t, 0, 1);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThan(T t) {
        return new OrderingComparison(t, -1, -1);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T t) {
        return new OrderingComparison(t, -1, 0);
    }
}
