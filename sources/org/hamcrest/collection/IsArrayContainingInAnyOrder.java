package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

public class IsArrayContainingInAnyOrder<E> extends TypeSafeMatcher<E[]> {
    private final IsIterableContainingInAnyOrder<E> iterableMatcher;
    private final Collection<Matcher<? super E>> matchers;

    public IsArrayContainingInAnyOrder(Collection<Matcher<? super E>> collection) {
        this.iterableMatcher = new IsIterableContainingInAnyOrder<>(collection);
        this.matchers = collection;
    }

    public boolean matchesSafely(E[] eArr) {
        return this.iterableMatcher.matches(Arrays.asList(eArr));
    }

    public void describeMismatchSafely(E[] eArr, Description description) {
        this.iterableMatcher.describeMismatch(Arrays.asList(eArr), description);
    }

    public void describeTo(Description description) {
        description.appendList("[", ", ", "]", this.matchers).appendText(" in any order");
    }

    @Factory
    public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... matcherArr) {
        return arrayContainingInAnyOrder(Arrays.asList(matcherArr));
    }

    @Factory
    public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> collection) {
        return new IsArrayContainingInAnyOrder(collection);
    }

    @Factory
    public static <E> Matcher<E[]> arrayContainingInAnyOrder(E... eArr) {
        ArrayList arrayList = new ArrayList();
        for (E equalTo : eArr) {
            arrayList.add(IsEqual.equalTo(equalTo));
        }
        return new IsArrayContainingInAnyOrder(arrayList);
    }
}
