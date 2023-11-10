package androidx.test.espresso.matcher;

import android.content.res.Resources;
import android.preference.Preference;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public final class PreferenceMatchers {
    private PreferenceMatchers() {
    }

    public static Matcher<Preference> isEnabled() {
        return new TypeSafeMatcher<Preference>() {
            public void describeTo(Description description) {
                description.appendText(" is an enabled preference");
            }

            public boolean matchesSafely(Preference preference) {
                return preference.isEnabled();
            }
        };
    }

    public static Matcher<Preference> withKey(String str) {
        return withKey((Matcher<String>) Matchers.m1139is(str));
    }

    public static Matcher<Preference> withSummary(final int i) {
        return new TypeSafeMatcher<Preference>() {
            private String expectedText = null;
            private String resourceName = null;

            public void describeTo(Description description) {
                description.appendText(" with summary string from resource id: ");
                description.appendValue(Integer.valueOf(i));
                if (this.resourceName != null) {
                    description.appendText("[");
                    description.appendText(this.resourceName);
                    description.appendText("]");
                }
                if (this.expectedText != null) {
                    description.appendText(" value: ");
                    description.appendText(this.expectedText);
                }
            }

            public boolean matchesSafely(Preference preference) {
                if (this.expectedText == null) {
                    try {
                        this.expectedText = preference.getContext().getResources().getString(i);
                        this.resourceName = preference.getContext().getResources().getResourceEntryName(i);
                    } catch (Resources.NotFoundException unused) {
                    }
                }
                String str = this.expectedText;
                if (str != null) {
                    return str.equals(preference.getSummary().toString());
                }
                return false;
            }
        };
    }

    public static Matcher<Preference> withSummaryText(String str) {
        return withSummaryText((Matcher<String>) Matchers.m1139is(str));
    }

    public static Matcher<Preference> withTitle(final int i) {
        return new TypeSafeMatcher<Preference>() {
            private String expectedText = null;
            private String resourceName = null;

            public void describeTo(Description description) {
                description.appendText(" with title string from resource id: ");
                description.appendValue(Integer.valueOf(i));
                if (this.resourceName != null) {
                    description.appendText("[");
                    description.appendText(this.resourceName);
                    description.appendText("]");
                }
                if (this.expectedText != null) {
                    description.appendText(" value: ");
                    description.appendText(this.expectedText);
                }
            }

            public boolean matchesSafely(Preference preference) {
                if (this.expectedText == null) {
                    try {
                        this.expectedText = preference.getContext().getResources().getString(i);
                        this.resourceName = preference.getContext().getResources().getResourceEntryName(i);
                    } catch (Resources.NotFoundException unused) {
                    }
                }
                if (this.expectedText == null || preference.getTitle() == null) {
                    return false;
                }
                return this.expectedText.equals(preference.getTitle().toString());
            }
        };
    }

    public static Matcher<Preference> withTitleText(String str) {
        return withTitleText((Matcher<String>) Matchers.m1139is(str));
    }

    public static Matcher<Preference> withKey(final Matcher<String> matcher) {
        return new TypeSafeMatcher<Preference>() {
            public void describeTo(Description description) {
                description.appendText(" preference with key matching: ");
                Matcher.this.describeTo(description);
            }

            public boolean matchesSafely(Preference preference) {
                return Matcher.this.matches(preference.getKey());
            }
        };
    }

    public static Matcher<Preference> withSummaryText(final Matcher<String> matcher) {
        return new TypeSafeMatcher<Preference>() {
            public void describeTo(Description description) {
                description.appendText(" a preference with summary matching: ");
                Matcher.this.describeTo(description);
            }

            public boolean matchesSafely(Preference preference) {
                return Matcher.this.matches(preference.getSummary().toString());
            }
        };
    }

    public static Matcher<Preference> withTitleText(final Matcher<String> matcher) {
        return new TypeSafeMatcher<Preference>() {
            public void describeTo(Description description) {
                description.appendText(" a preference with title matching: ");
                Matcher.this.describeTo(description);
            }

            public boolean matchesSafely(Preference preference) {
                if (preference.getTitle() == null) {
                    return false;
                }
                return Matcher.this.matches(preference.getTitle().toString());
            }
        };
    }
}
