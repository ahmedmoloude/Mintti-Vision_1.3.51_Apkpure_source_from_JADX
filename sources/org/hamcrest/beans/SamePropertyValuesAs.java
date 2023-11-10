package org.hamcrest.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class SamePropertyValuesAs<T> extends TypeSafeDiagnosingMatcher<T> {
    private final T expectedBean;
    private final List<PropertyMatcher> propertyMatchers;
    private final Set<String> propertyNames;

    public SamePropertyValuesAs(T t) {
        PropertyDescriptor[] propertyDescriptorsFor = PropertyUtil.propertyDescriptorsFor(t, Object.class);
        this.expectedBean = t;
        this.propertyNames = propertyNamesFrom(propertyDescriptorsFor);
        this.propertyMatchers = propertyMatchersFor(t, propertyDescriptorsFor);
    }

    public boolean matchesSafely(T t, Description description) {
        return isCompatibleType(t, description) && hasNoExtraProperties(t, description) && hasMatchingValues(t, description);
    }

    public void describeTo(Description description) {
        description.appendText("same property values as " + this.expectedBean.getClass().getSimpleName()).appendList(" [", ", ", "]", this.propertyMatchers);
    }

    private boolean isCompatibleType(T t, Description description) {
        if (this.expectedBean.getClass().isAssignableFrom(t.getClass())) {
            return true;
        }
        description.appendText("is incompatible type: " + t.getClass().getSimpleName());
        return false;
    }

    private boolean hasNoExtraProperties(T t, Description description) {
        Set<String> propertyNamesFrom = propertyNamesFrom(PropertyUtil.propertyDescriptorsFor(t, Object.class));
        propertyNamesFrom.removeAll(this.propertyNames);
        if (propertyNamesFrom.isEmpty()) {
            return true;
        }
        description.appendText("has extra properties called " + propertyNamesFrom);
        return false;
    }

    private boolean hasMatchingValues(T t, Description description) {
        for (PropertyMatcher next : this.propertyMatchers) {
            if (!next.matches(t)) {
                next.describeMismatch(t, description);
                return false;
            }
        }
        return true;
    }

    private static <T> List<PropertyMatcher> propertyMatchersFor(T t, PropertyDescriptor[] propertyDescriptorArr) {
        ArrayList arrayList = new ArrayList(propertyDescriptorArr.length);
        for (PropertyDescriptor propertyMatcher : propertyDescriptorArr) {
            arrayList.add(new PropertyMatcher(propertyMatcher, t));
        }
        return arrayList;
    }

    private static Set<String> propertyNamesFrom(PropertyDescriptor[] propertyDescriptorArr) {
        HashSet hashSet = new HashSet();
        for (PropertyDescriptor displayName : propertyDescriptorArr) {
            hashSet.add(displayName.getDisplayName());
        }
        return hashSet;
    }

    public static class PropertyMatcher extends DiagnosingMatcher<Object> {
        private final Matcher<Object> matcher;
        private final String propertyName;
        private final Method readMethod;

        public PropertyMatcher(PropertyDescriptor propertyDescriptor, Object obj) {
            this.propertyName = propertyDescriptor.getDisplayName();
            Method readMethod2 = propertyDescriptor.getReadMethod();
            this.readMethod = readMethod2;
            this.matcher = IsEqual.equalTo(SamePropertyValuesAs.readProperty(readMethod2, obj));
        }

        public boolean matches(Object obj, Description description) {
            Object access$000 = SamePropertyValuesAs.readProperty(this.readMethod, obj);
            if (this.matcher.matches(access$000)) {
                return true;
            }
            description.appendText(this.propertyName + " ");
            this.matcher.describeMismatch(access$000, description);
            return false;
        }

        public void describeTo(Description description) {
            description.appendText(this.propertyName + ": ").appendDescriptionOf(this.matcher);
        }
    }

    /* access modifiers changed from: private */
    public static Object readProperty(Method method, Object obj) {
        try {
            return method.invoke(obj, PropertyUtil.NO_ARGUMENTS);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not invoke " + method + " on " + obj, e);
        }
    }

    @Factory
    public static <T> Matcher<T> samePropertyValuesAs(T t) {
        return new SamePropertyValuesAs(t);
    }
}
