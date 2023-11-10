package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCloseTo extends TypeSafeMatcher<Double> {
    private final double delta;
    private final double value;

    public IsCloseTo(double d, double d2) {
        this.delta = d2;
        this.value = d;
    }

    public boolean matchesSafely(Double d) {
        return actualDelta(d) <= 0.0d;
    }

    public void describeMismatchSafely(Double d, Description description) {
        description.appendValue(d).appendText(" differed by ").appendValue(Double.valueOf(actualDelta(d)));
    }

    public void describeTo(Description description) {
        description.appendText("a numeric value within ").appendValue(Double.valueOf(this.delta)).appendText(" of ").appendValue(Double.valueOf(this.value));
    }

    private double actualDelta(Double d) {
        return Math.abs(d.doubleValue() - this.value) - this.delta;
    }

    @Factory
    public static Matcher<Double> closeTo(double d, double d2) {
        return new IsCloseTo(d, d2);
    }
}
