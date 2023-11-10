package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class IsIterableContainingInOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
    private final List<Matcher<? super E>> matchers;

    public IsIterableContainingInOrder(List<Matcher<? super E>> list) {
        this.matchers = list;
    }

    /* access modifiers changed from: protected */
    public boolean matchesSafely(Iterable<? extends E> iterable, Description description) {
        MatchSeries matchSeries = new MatchSeries(this.matchers, description);
        for (Object matches : iterable) {
            if (!matchSeries.matches(matches)) {
                return false;
            }
        }
        return matchSeries.isFinished();
    }

    public void describeTo(Description description) {
        description.appendText("iterable containing ").appendList("[", ", ", "]", this.matchers);
    }

    private static class MatchSeries<F> {
        public final List<Matcher<? super F>> matchers;
        private final Description mismatchDescription;
        public int nextMatchIx = 0;

        public MatchSeries(List<Matcher<? super F>> list, Description description) {
            this.mismatchDescription = description;
            if (!list.isEmpty()) {
                this.matchers = list;
                return;
            }
            throw new IllegalArgumentException("Should specify at least one expected element");
        }

        public boolean matches(F f) {
            return isNotSurplus(f) && isMatched(f);
        }

        public boolean isFinished() {
            if (this.nextMatchIx >= this.matchers.size()) {
                return true;
            }
            this.mismatchDescription.appendText("No item matched: ").appendDescriptionOf(this.matchers.get(this.nextMatchIx));
            return false;
        }

        private boolean isMatched(F f) {
            Matcher matcher = this.matchers.get(this.nextMatchIx);
            if (!matcher.matches(f)) {
                describeMismatch(matcher, f);
                return false;
            }
            this.nextMatchIx++;
            return true;
        }

        private boolean isNotSurplus(F f) {
            if (this.matchers.size() > this.nextMatchIx) {
                return true;
            }
            this.mismatchDescription.appendText("Not matched: ").appendValue(f);
            return false;
        }

        private void describeMismatch(Matcher<? super F> matcher, F f) {
            Description description = this.mismatchDescription;
            description.appendText("item " + this.nextMatchIx + ": ");
            matcher.describeMismatch(f, this.mismatchDescription);
        }
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(E... eArr) {
        ArrayList arrayList = new ArrayList();
        for (E equalTo : eArr) {
            arrayList.add(IsEqual.equalTo(equalTo));
        }
        return contains(arrayList);
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> matcher) {
        return contains(new ArrayList(Arrays.asList(new Matcher[]{matcher})));
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... matcherArr) {
        return contains(Arrays.asList(matcherArr));
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> list) {
        return new IsIterableContainingInOrder(list);
    }
}
