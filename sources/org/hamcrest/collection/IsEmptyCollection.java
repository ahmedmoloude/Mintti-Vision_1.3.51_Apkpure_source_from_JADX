package org.hamcrest.collection;

import java.util.Collection;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyCollection<E> extends TypeSafeMatcher<Collection<? extends E>> {
    public boolean matchesSafely(Collection<? extends E> collection) {
        return collection.isEmpty();
    }

    public void describeMismatchSafely(Collection<? extends E> collection, Description description) {
        description.appendValue(collection);
    }

    public void describeTo(Description description) {
        description.appendText("an empty collection");
    }

    @Factory
    public static <E> Matcher<Collection<? extends E>> empty() {
        return new IsEmptyCollection();
    }

    @Factory
    public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> cls) {
        return empty();
    }
}
