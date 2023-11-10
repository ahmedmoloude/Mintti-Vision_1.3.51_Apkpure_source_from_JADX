package org.hamcrest.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HasPropertyWithValue<T> extends TypeSafeDiagnosingMatcher<T> {
    private static final Condition.Step<PropertyDescriptor, Method> WITH_READ_METHOD = withReadMethod();
    private final String propertyName;
    private final Matcher<Object> valueMatcher;

    private static Matcher<Object> nastyGenericsWorkaround(Matcher<?> matcher) {
        return matcher;
    }

    public HasPropertyWithValue(String str, Matcher<?> matcher) {
        this.propertyName = str;
        this.valueMatcher = nastyGenericsWorkaround(matcher);
    }

    public boolean matchesSafely(T t, Description description) {
        Condition<U> and = propertyOn(t, description).and(WITH_READ_METHOD).and(withPropertyValue(t));
        Matcher<Object> matcher = this.valueMatcher;
        return and.matching(matcher, "property '" + this.propertyName + "' ");
    }

    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(this.propertyName).appendText(", ").appendDescriptionOf(this.valueMatcher).appendText(")");
    }

    private Condition<PropertyDescriptor> propertyOn(T t, Description description) {
        PropertyDescriptor propertyDescriptor = PropertyUtil.getPropertyDescriptor(this.propertyName, t);
        if (propertyDescriptor != null) {
            return Condition.matched(propertyDescriptor, description);
        }
        description.appendText("No property \"" + this.propertyName + "\"");
        return Condition.notMatched();
    }

    private Condition.Step<Method, Object> withPropertyValue(final T t) {
        return new Condition.Step<Method, Object>() {
            public Condition<Object> apply(Method method, Description description) {
                try {
                    return Condition.matched(method.invoke(t, PropertyUtil.NO_ARGUMENTS), description);
                } catch (Exception e) {
                    description.appendText(e.getMessage());
                    return Condition.notMatched();
                }
            }
        };
    }

    private static Condition.Step<PropertyDescriptor, Method> withReadMethod() {
        return new Condition.Step<PropertyDescriptor, Method>() {
            public Condition<Method> apply(PropertyDescriptor propertyDescriptor, Description description) {
                Method readMethod = propertyDescriptor.getReadMethod();
                if (readMethod != null) {
                    return Condition.matched(readMethod, description);
                }
                description.appendText("property \"" + propertyDescriptor.getName() + "\" is not readable");
                return Condition.notMatched();
            }
        };
    }

    @Factory
    public static <T> Matcher<T> hasProperty(String str, Matcher<?> matcher) {
        return new HasPropertyWithValue(str, matcher);
    }
}
