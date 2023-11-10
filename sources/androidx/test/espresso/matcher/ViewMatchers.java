package androidx.test.espresso.matcher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.remote.annotation.RemoteMsgConstructor;
import androidx.test.espresso.remote.annotation.RemoteMsgField;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import com.p020kl.commonbase.constants.Constants;
import java.util.Locale;
import java.util.regex.Pattern;
import junit.framework.AssertionFailedError;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public final class ViewMatchers {
    /* access modifiers changed from: private */
    public static final Pattern RESOURCE_ID_PATTERN = Pattern.compile("\\d+");

    /* renamed from: androidx.test.espresso.matcher.ViewMatchers$2 */
    static /* synthetic */ class C07802 {

        /* renamed from: $SwitchMap$androidx$test$espresso$matcher$ViewMatchers$WithCharSequenceMatcher$TextViewMethod */
        static final /* synthetic */ int[] f213x6a05bebb;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        static {
            /*
                androidx.test.espresso.matcher.ViewMatchers$WithCharSequenceMatcher$TextViewMethod[] r0 = androidx.test.espresso.matcher.ViewMatchers.WithCharSequenceMatcher.TextViewMethod.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f213x6a05bebb = r0
                androidx.test.espresso.matcher.ViewMatchers$WithCharSequenceMatcher$TextViewMethod r1 = androidx.test.espresso.matcher.ViewMatchers.WithCharSequenceMatcher.TextViewMethod.GET_TEXT     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f213x6a05bebb     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.test.espresso.matcher.ViewMatchers$WithCharSequenceMatcher$TextViewMethod r1 = androidx.test.espresso.matcher.ViewMatchers.WithCharSequenceMatcher.TextViewMethod.GET_HINT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.C07802.<clinit>():void");
        }
    }

    static final class HasChildCountMatcher extends BoundedDiagnosingMatcher<View, ViewGroup> {
        @RemoteMsgField(order = 0)
        private final int childCount;

        @RemoteMsgConstructor
        private HasChildCountMatcher(int i) {
            super(ViewGroup.class);
            this.childCount = i;
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("viewGroup.getChildCount() to be ").appendValue(Integer.valueOf(this.childCount));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(ViewGroup viewGroup, Description description) {
            description.appendText("viewGroup.getChildCount() was ").appendValue(Integer.valueOf(viewGroup.getChildCount()));
            return viewGroup.getChildCount() == this.childCount;
        }
    }

    static final class HasContentDescriptionMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgConstructor
        private HasContentDescriptionMatcher() {
        }

        public void describeTo(Description description) {
            description.appendText("view.getContentDescription() is not null");
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            if (view.getContentDescription() != null) {
                return true;
            }
            description.appendText("view.getContentDescription() was null");
            return false;
        }
    }

    static final class HasDescendantMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Matcher<View> descendantMatcher;
        private final Matcher<ViewGroup> isViewGroupMatcher;

        @RemoteMsgConstructor
        private HasDescendantMatcher(Matcher<View> matcher) {
            this.isViewGroupMatcher = Matchers.isA(ViewGroup.class);
            this.descendantMatcher = matcher;
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ boolean lambda$matchesSafely$0$ViewMatchers$HasDescendantMatcher(View view, View view2) {
            return view2 != view && this.descendantMatcher.matches(view2);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            if (!this.isViewGroupMatcher.matches(view)) {
                description.appendText("view ");
                this.isViewGroupMatcher.describeMismatch(view, description);
                return false;
            }
            if (Iterables.filter(TreeIterables.breadthFirstViewTraversal(view), new ViewMatchers$HasDescendantMatcher$$Lambda$0(this, view)).iterator().hasNext()) {
                return true;
            }
            description.appendText("no descendant matching ").appendDescriptionOf(this.descendantMatcher).appendText(" was found");
            return false;
        }

        public void describeTo(Description description) {
            description.appendText("(view ").appendDescriptionOf(this.isViewGroupMatcher).appendText(" and has descendant matching ").appendDescriptionOf(this.descendantMatcher).appendText(")");
        }
    }

    static final class HasErrorTextMatcher extends BoundedDiagnosingMatcher<View, EditText> {
        @RemoteMsgField(order = 0)
        private final Matcher<String> stringMatcher;

        @RemoteMsgConstructor
        private HasErrorTextMatcher(Matcher<String> matcher) {
            super(EditText.class);
            this.stringMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("editText.getError() to match ").appendDescriptionOf(this.stringMatcher);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(EditText editText, Description description) {
            description.appendText("editText.getError() was ").appendValue(editText.getError());
            return this.stringMatcher.matches(editText.getError());
        }
    }

    static final class HasFocusMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final boolean hasFocus;

        @RemoteMsgConstructor
        private HasFocusMatcher(boolean z) {
            this.hasFocus = z;
        }

        public void describeTo(Description description) {
            description.appendText("view.hasFocus() is ").appendValue(Boolean.valueOf(this.hasFocus));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            boolean hasFocus2 = view.hasFocus();
            if (hasFocus2 == this.hasFocus) {
                return true;
            }
            description.appendText("view.hasFocus() is ").appendValue(Boolean.valueOf(hasFocus2));
            return false;
        }
    }

    static final class HasImeActionMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Matcher<Integer> imeActionMatcher;

        @RemoteMsgConstructor
        private HasImeActionMatcher(Matcher<Integer> matcher) {
            this.imeActionMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            int i;
            EditorInfo editorInfo = new EditorInfo();
            if (view.onCreateInputConnection(editorInfo) == null) {
                description.appendText("view.onCreateInputConnection() was null");
                return false;
            }
            if (editorInfo.actionId != 0) {
                i = editorInfo.actionId;
            } else {
                i = editorInfo.imeOptions & 255;
            }
            if (this.imeActionMatcher.matches(Integer.valueOf(i))) {
                return true;
            }
            description.appendText("editorInfo.actionId ");
            this.imeActionMatcher.describeMismatch(Integer.valueOf(i), description);
            return false;
        }

        public void describeTo(Description description) {
            description.appendText("(view.onCreateInputConnection() is not null and editorInfo.actionId ").appendDescriptionOf(this.imeActionMatcher).appendText(")");
        }
    }

    static final class HasLinksMatcher extends BoundedDiagnosingMatcher<View, TextView> {
        @RemoteMsgConstructor
        private HasLinksMatcher() {
            super(TextView.class);
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("textView.getUrls().length > 0");
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(TextView textView, Description description) {
            description.appendText("textView.getUrls().length was ").appendValue(Integer.valueOf(textView.getUrls().length));
            return textView.getUrls().length > 0;
        }
    }

    static final class HasMinimumChildCountMatcher extends BoundedDiagnosingMatcher<View, ViewGroup> {
        @RemoteMsgField(order = 0)
        private final int minChildCount;

        @RemoteMsgConstructor
        private HasMinimumChildCountMatcher(int i) {
            super(ViewGroup.class);
            this.minChildCount = i;
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("viewGroup.getChildCount() to be at least ").appendValue(Integer.valueOf(this.minChildCount));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(ViewGroup viewGroup, Description description) {
            description.appendText("viewGroup.getChildCount() was ").appendValue(Integer.valueOf(viewGroup.getChildCount()));
            return viewGroup.getChildCount() >= this.minChildCount;
        }
    }

    static final class HasSiblingMatcher extends TypeSafeDiagnosingMatcher<View> {
        private final Matcher<ViewGroup> parentMatcher;
        @RemoteMsgField(order = 0)
        private final Matcher<View> siblingMatcher;

        @RemoteMsgConstructor
        private HasSiblingMatcher(Matcher<View> matcher) {
            this.parentMatcher = Matchers.isA(ViewGroup.class);
            this.siblingMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            ViewParent parent = view.getParent();
            if (!this.parentMatcher.matches(parent)) {
                description.appendText("view.getParent() ");
                this.parentMatcher.describeMismatch(parent, description);
                return false;
            }
            ViewGroup viewGroup = (ViewGroup) parent;
            if (viewGroup.getChildCount() == 1) {
                description.appendText("no siblings found");
                return false;
            }
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (view != childAt && this.siblingMatcher.matches(childAt)) {
                    return true;
                }
            }
            description.appendText("none of the ").appendValue(Integer.valueOf(viewGroup.getChildCount() - 1)).appendText(" siblings match");
            return false;
        }

        public void describeTo(Description description) {
            description.appendText("(view.getParent() ").appendDescriptionOf(this.parentMatcher).appendText(" and has a sibling matching ").appendDescriptionOf(this.siblingMatcher).appendText(")");
        }
    }

    static final class IsAssignableFromMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Class<?> clazz;

        @RemoteMsgConstructor
        private IsAssignableFromMatcher(Class<?> cls) {
            this.clazz = (Class) Preconditions.checkNotNull(cls);
        }

        public void describeTo(Description description) {
            description.appendText("is assignable from class ").appendValue(this.clazz);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            if (this.clazz.isAssignableFrom(view.getClass())) {
                return true;
            }
            description.appendText("view.getClass() was ").appendValue(view.getClass());
            return false;
        }
    }

    static final class IsClickableMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final boolean isClickable;

        @RemoteMsgConstructor
        private IsClickableMatcher(boolean z) {
            this.isClickable = z;
        }

        public void describeTo(Description description) {
            description.appendText("view.isClickable() is ").appendValue(Boolean.valueOf(this.isClickable));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            boolean isClickable2 = view.isClickable();
            if (isClickable2 == this.isClickable) {
                return true;
            }
            description.appendText("view.isClickable() was ").appendValue(Boolean.valueOf(isClickable2));
            return false;
        }
    }

    static final class IsDescendantOfAMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Matcher<View> ancestorMatcher;

        @RemoteMsgConstructor
        private IsDescendantOfAMatcher(Matcher<View> matcher) {
            this.ancestorMatcher = matcher;
        }

        private boolean checkAncestors(ViewParent viewParent) {
            if (!(viewParent instanceof View)) {
                return false;
            }
            if (this.ancestorMatcher.matches(viewParent)) {
                return true;
            }
            return checkAncestors(viewParent.getParent());
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            boolean checkAncestors = checkAncestors(view.getParent());
            if (!checkAncestors) {
                description.appendText("none of the ancestors match ").appendDescriptionOf(this.ancestorMatcher);
            }
            return checkAncestors;
        }

        public void describeTo(Description description) {
            description.appendText("is descendant of a view matching ").appendDescriptionOf(this.ancestorMatcher);
        }
    }

    static final class IsDisplayedMatcher extends TypeSafeDiagnosingMatcher<View> {
        private final Matcher<View> visibilityMatcher;

        @RemoteMsgConstructor
        private IsDisplayedMatcher() {
            this.visibilityMatcher = ViewMatchers.withEffectiveVisibility(Visibility.VISIBLE);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            if (!this.visibilityMatcher.matches(view)) {
                this.visibilityMatcher.describeMismatch(view, description);
                return false;
            } else if (view.getGlobalVisibleRect(new Rect())) {
                return true;
            } else {
                description.appendText("view.getGlobalVisibleRect() returned empty rectangle");
                return false;
            }
        }

        public void describeTo(Description description) {
            description.appendText("(").appendDescriptionOf(this.visibilityMatcher).appendText(" and view.getGlobalVisibleRect() to return non-empty rectangle)");
        }
    }

    static final class IsDisplayingAtLeastMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        final int areaPercentage;
        private final Matcher<View> visibilityMatchers;

        @RemoteMsgConstructor
        private IsDisplayingAtLeastMatcher(int i) {
            this.visibilityMatchers = ViewMatchers.withEffectiveVisibility(Visibility.VISIBLE);
            this.areaPercentage = i;
        }

        private Rect getScreenWithoutStatusBarActionBar(View view) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) view.getContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int identifier = view.getContext().getResources().getIdentifier("status_bar_height", "dimen", Constants.f840OS);
            int dimensionPixelSize = identifier > 0 ? view.getContext().getResources().getDimensionPixelSize(identifier) : 0;
            TypedValue typedValue = new TypedValue();
            return new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels - (dimensionPixelSize + (view.getContext().getTheme().resolveAttribute(16843499, typedValue, true) ? TypedValue.complexToDimensionPixelSize(typedValue.data, view.getContext().getResources().getDisplayMetrics()) : 0)));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            if (!this.visibilityMatchers.matches(view)) {
                this.visibilityMatchers.describeMismatch(view, description);
                return false;
            }
            Rect rect = new Rect();
            if (!view.getGlobalVisibleRect(rect)) {
                description.appendText("view was ").appendValue(0).appendText(" percent visible to the user");
                return false;
            }
            Rect screenWithoutStatusBarActionBar = getScreenWithoutStatusBarActionBar(view);
            float height = (float) (view.getHeight() > screenWithoutStatusBarActionBar.height() ? screenWithoutStatusBarActionBar.height() : view.getHeight());
            float width = (float) (view.getWidth() > screenWithoutStatusBarActionBar.width() ? screenWithoutStatusBarActionBar.width() : view.getWidth());
            if (Build.VERSION.SDK_INT >= 11) {
                height = Math.min(((float) view.getHeight()) * Math.abs(view.getScaleY()), (float) screenWithoutStatusBarActionBar.height());
                width = Math.min(((float) view.getWidth()) * Math.abs(view.getScaleX()), (float) screenWithoutStatusBarActionBar.width());
            }
            int height2 = (int) ((((double) (rect.height() * rect.width())) / ((double) (height * width))) * 100.0d);
            if (height2 >= this.areaPercentage) {
                return true;
            }
            description.appendText("view was ").appendValue(Integer.valueOf(height2)).appendText(" percent visible to the user");
            return false;
        }

        public void describeTo(Description description) {
            description.appendText("(").appendDescriptionOf(this.visibilityMatchers).appendText(" and view.getGlobalVisibleRect() covers at least ").appendValue(Integer.valueOf(this.areaPercentage)).appendText(" percent of the view's area)");
        }
    }

    static final class IsEnabledMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final boolean isEnabled;

        @RemoteMsgConstructor
        private IsEnabledMatcher(boolean z) {
            this.isEnabled = z;
        }

        public void describeTo(Description description) {
            description.appendText("view.isEnabled() is ").appendValue(Boolean.valueOf(this.isEnabled));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            boolean isEnabled2 = view.isEnabled();
            if (isEnabled2 == this.isEnabled) {
                return true;
            }
            description.appendText("view.isEnabled() was ").appendValue(Boolean.valueOf(isEnabled2));
            return false;
        }
    }

    static final class IsFocusableMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final boolean isFocusable;

        @RemoteMsgConstructor
        private IsFocusableMatcher(boolean z) {
            this.isFocusable = z;
        }

        public void describeTo(Description description) {
            description.appendText("view.isFocusable() is ").appendValue(Boolean.valueOf(this.isFocusable));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            boolean isFocusable2 = view.isFocusable();
            if (isFocusable2 == this.isFocusable) {
                return true;
            }
            description.appendText("view.isFocusable() was ").appendValue(Boolean.valueOf(isFocusable2));
            return false;
        }
    }

    static final class IsFocusedMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final boolean isFocused;

        @RemoteMsgConstructor
        private IsFocusedMatcher(boolean z) {
            this.isFocused = z;
        }

        public void describeTo(Description description) {
            description.appendText("view.isFocused() is ").appendValue(Boolean.valueOf(this.isFocused));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            boolean isFocused2 = view.isFocused();
            if (isFocused2 == this.isFocused) {
                return true;
            }
            description.appendText("view.isFocused() was ").appendValue(Boolean.valueOf(isFocused2));
            return false;
        }
    }

    static final class IsJavascriptEnabledMatcher extends BoundedDiagnosingMatcher<View, WebView> {
        @RemoteMsgConstructor
        private IsJavascriptEnabledMatcher() {
            super(WebView.class);
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("webView.getSettings().getJavaScriptEnabled() is ").appendValue(true);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(WebView webView, Description description) {
            description.appendText("webView.getSettings().getJavaScriptEnabled() was ").appendValue(Boolean.valueOf(webView.getSettings().getJavaScriptEnabled()));
            return webView.getSettings().getJavaScriptEnabled();
        }
    }

    static final class IsRootMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgConstructor
        private IsRootMatcher() {
        }

        public void describeTo(Description description) {
            description.appendText("view.getRootView() to equal view");
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            View rootView = view.getRootView();
            if (rootView == view) {
                return true;
            }
            description.appendText("view.getRootView() was ").appendValue(rootView);
            return false;
        }
    }

    static final class IsSelectedMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final boolean isSelected;

        @RemoteMsgConstructor
        private IsSelectedMatcher(boolean z) {
            this.isSelected = z;
        }

        public void describeTo(Description description) {
            description.appendText("view.isSelected() is ").appendValue(Boolean.valueOf(this.isSelected));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            boolean isSelected2 = view.isSelected();
            if (isSelected2 == this.isSelected) {
                return true;
            }
            description.appendText("view.isSelected() was ").appendValue(Boolean.valueOf(isSelected2));
            return false;
        }
    }

    static final class SupportsInputMethodsMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgConstructor
        private SupportsInputMethodsMatcher() {
        }

        public void describeTo(Description description) {
            description.appendText("view.onCreateInputConnection() is not null");
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            if (view.onCreateInputConnection(new EditorInfo()) != null) {
                return true;
            }
            description.appendText("view.onCreateInputConnection() was null");
            return false;
        }
    }

    public enum Visibility {
        VISIBLE(0),
        INVISIBLE(4),
        GONE(8);
        
        private final int value;

        private Visibility(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        public static Visibility forViewVisibility(int i) {
            if (i == 0) {
                return VISIBLE;
            }
            if (i == 4) {
                return INVISIBLE;
            }
            if (i == 8) {
                return GONE;
            }
            StringBuilder sb = new StringBuilder(38);
            sb.append("Invalid visibility value <");
            sb.append(i);
            sb.append(">");
            throw new IllegalArgumentException(sb.toString());
        }

        public static Visibility forViewVisibility(View view) {
            return forViewVisibility(view.getVisibility());
        }
    }

    static final class WithAlphaMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final float alpha;

        @RemoteMsgConstructor
        private WithAlphaMatcher(float f) {
            this.alpha = f;
        }

        public void describeTo(Description description) {
            description.appendText("view.getAlpha() is ").appendValue(Float.valueOf(this.alpha));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            float alpha2 = view.getAlpha();
            if (alpha2 == this.alpha) {
                return true;
            }
            description.appendText("view.getAlpha() was ").appendValue(Float.valueOf(alpha2));
            return false;
        }
    }

    static final class WithCharSequenceMatcher extends BoundedDiagnosingMatcher<View, TextView> {
        private String expectedText;
        @RemoteMsgField(order = 1)
        private final TextViewMethod method;
        @RemoteMsgField(order = 0)
        private final int resourceId;
        private String resourceName;

        private enum TextViewMethod {
            GET_TEXT,
            GET_HINT
        }

        @RemoteMsgConstructor
        private WithCharSequenceMatcher(int i, TextViewMethod textViewMethod) {
            super(TextView.class);
            this.resourceId = i;
            this.method = textViewMethod;
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            int i = C07802.f213x6a05bebb[this.method.ordinal()];
            if (i == 1) {
                description.appendText("view.getText()");
            } else if (i == 2) {
                description.appendText("view.getHint()");
            }
            description.appendText(" equals string from resource id: ").appendValue(Integer.valueOf(this.resourceId));
            if (this.resourceName != null) {
                description.appendText(" [").appendText(this.resourceName).appendText("]");
            }
            if (this.expectedText != null) {
                description.appendText(" value: ").appendText(this.expectedText);
            }
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(TextView textView, Description description) {
            CharSequence charSequence;
            if (this.expectedText == null) {
                try {
                    this.expectedText = textView.getResources().getString(this.resourceId);
                } catch (Resources.NotFoundException unused) {
                }
                this.resourceName = ViewMatchers.safeGetResourceEntryName(textView.getResources(), this.resourceId);
            }
            int i = C07802.f213x6a05bebb[this.method.ordinal()];
            if (i == 1) {
                charSequence = textView.getText();
                description.appendText("view.getText() was ");
            } else if (i == 2) {
                charSequence = textView.getHint();
                description.appendText("view.getHint() was ");
            } else {
                String valueOf = String.valueOf(this.method);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 28);
                sb.append("Unexpected TextView method: ");
                sb.append(valueOf);
                throw new IllegalStateException(sb.toString());
            }
            description.appendValue(charSequence);
            String str = this.expectedText;
            if (str == null || charSequence == null || !str.contentEquals(charSequence)) {
                return false;
            }
            return true;
        }
    }

    static final class WithCheckBoxStateMatcher<E extends View & Checkable> extends BoundedDiagnosingMatcher<View, E> {
        @RemoteMsgField(order = 0)
        private final Matcher<Boolean> checkStateMatcher;

        @RemoteMsgConstructor
        private WithCheckBoxStateMatcher(Matcher<Boolean> matcher) {
            super(View.class, Checkable.class, new Class[0]);
            this.checkStateMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("view.isChecked() matching: ").appendDescriptionOf(this.checkStateMatcher);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(E e, Description description) {
            boolean isChecked = ((Checkable) e).isChecked();
            description.appendText("view.isChecked() was ").appendValue(Boolean.valueOf(isChecked));
            return this.checkStateMatcher.matches(Boolean.valueOf(isChecked));
        }
    }

    static final class WithChildMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Matcher<View> childMatcher;
        private final Matcher<ViewGroup> viewGroupMatcher;

        @RemoteMsgConstructor
        private WithChildMatcher(Matcher<View> matcher) {
            this.viewGroupMatcher = Matchers.isA(ViewGroup.class);
            this.childMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            if (!this.viewGroupMatcher.matches(view)) {
                description.appendText("view ");
                this.viewGroupMatcher.describeMismatch(view, description);
                return false;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (this.childMatcher.matches(viewGroup.getChildAt(i))) {
                    return true;
                }
            }
            description.appendText("All ").appendValue(Integer.valueOf(viewGroup.getChildCount())).appendText(" children did not match");
            return false;
        }

        public void describeTo(Description description) {
            description.appendText("(view ").appendDescriptionOf(this.viewGroupMatcher).appendText(" and has child matching: ").appendDescriptionOf(this.childMatcher).appendText(")");
        }
    }

    static final class WithClassNameMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        final Matcher<String> classNameMatcher;

        @RemoteMsgConstructor
        private WithClassNameMatcher(Matcher<String> matcher) {
            this.classNameMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            String name = view.getClass().getName();
            if (this.classNameMatcher.matches(name)) {
                return true;
            }
            description.appendText("view.getClass().getName() ");
            this.classNameMatcher.describeMismatch(name, description);
            return false;
        }

        public void describeTo(Description description) {
            description.appendText("view.getClass().getName() matches: ").appendDescriptionOf(this.classNameMatcher);
        }
    }

    static final class WithContentDescriptionFromIdMatcher extends TypeSafeDiagnosingMatcher<View> {
        private String expectedText;
        @RemoteMsgField(order = 0)
        private final int resourceId;
        private String resourceName;

        @RemoteMsgConstructor
        private WithContentDescriptionFromIdMatcher(int i) {
            this.resourceName = null;
            this.expectedText = null;
            this.resourceId = i;
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            if (this.expectedText == null) {
                try {
                    this.expectedText = view.getResources().getString(this.resourceId);
                } catch (Resources.NotFoundException unused) {
                }
                this.resourceName = ViewMatchers.safeGetResourceEntryName(view.getResources(), this.resourceId);
            }
            if (this.expectedText == null) {
                description.appendText("Failed to resolve resource id ").appendValue(Integer.valueOf(this.resourceId));
                return false;
            }
            CharSequence contentDescription = view.getContentDescription();
            if (contentDescription != null && this.expectedText.contentEquals(contentDescription)) {
                return true;
            }
            description.appendText("view.getContentDescription() was ").appendValue(contentDescription);
            return false;
        }

        public void describeTo(Description description) {
            description.appendText("view.getContentDescription() to match resource id ").appendValue(Integer.valueOf(this.resourceId));
            if (this.resourceName != null) {
                description.appendText("[").appendText(this.resourceName).appendText("]");
            }
            if (this.expectedText != null) {
                description.appendText(" with value ").appendValue(this.expectedText);
            }
        }
    }

    static final class WithContentDescriptionMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Matcher<? extends CharSequence> charSequenceMatcher;

        @RemoteMsgConstructor
        private WithContentDescriptionMatcher(Matcher<? extends CharSequence> matcher) {
            this.charSequenceMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            CharSequence contentDescription = view.getContentDescription();
            if (this.charSequenceMatcher.matches(contentDescription)) {
                return true;
            }
            description.appendText("view.getContentDescription() ");
            this.charSequenceMatcher.describeMismatch(contentDescription, description);
            return false;
        }

        public void describeTo(Description description) {
            description.appendText("view.getContentDescription() ").appendDescriptionOf(this.charSequenceMatcher);
        }
    }

    static final class WithContentDescriptionTextMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Matcher<String> textMatcher;

        @RemoteMsgConstructor
        private WithContentDescriptionTextMatcher(Matcher<String> matcher) {
            this.textMatcher = matcher;
        }

        public void describeTo(Description description) {
            description.appendText("view.getContentDescription() ").appendDescriptionOf(this.textMatcher);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            String charSequence = view.getContentDescription() != null ? view.getContentDescription().toString() : null;
            if (this.textMatcher.matches(charSequence)) {
                return true;
            }
            description.appendText("view.getContentDescription() ");
            this.textMatcher.describeMismatch(charSequence, description);
            return false;
        }
    }

    static final class WithEffectiveVisibilityMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Visibility visibility;

        @RemoteMsgConstructor
        private WithEffectiveVisibilityMatcher(Visibility visibility2) {
            this.visibility = visibility2;
        }

        public void describeTo(Description description) {
            description.appendText("view has effective visibility ").appendValue(this.visibility);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            if (this.visibility.getValue() == 0) {
                if (view.getVisibility() == this.visibility.getValue()) {
                    while (view.getParent() instanceof View) {
                        view = (View) view.getParent();
                        if (view.getVisibility() != this.visibility.getValue()) {
                            description.appendText("ancestor ").appendValue(view).appendText("'s getVisibility() was ").appendValue(Visibility.forViewVisibility(view));
                            return false;
                        }
                    }
                    return true;
                }
                description.appendText("view.getVisibility() was ").appendValue(Visibility.forViewVisibility(view));
                return false;
            } else if (view.getVisibility() == this.visibility.getValue()) {
                return true;
            } else {
                while (view.getParent() instanceof View) {
                    view = (View) view.getParent();
                    if (view.getVisibility() == this.visibility.getValue()) {
                        return true;
                    }
                }
                description.appendText("neither view nor its ancestors have getVisibility() set to ").appendValue(this.visibility);
                return false;
            }
        }
    }

    static final class WithHintMatcher extends BoundedDiagnosingMatcher<View, TextView> {
        @RemoteMsgField(order = 0)
        private final Matcher<String> stringMatcher;

        @RemoteMsgConstructor
        private WithHintMatcher(Matcher<String> matcher) {
            super(TextView.class);
            this.stringMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("view.getHint() matching: ");
            this.stringMatcher.describeTo(description);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(TextView textView, Description description) {
            CharSequence hint = textView.getHint();
            description.appendText("view.getHint() was ").appendValue(hint);
            return this.stringMatcher.matches(hint);
        }
    }

    static final class WithIdMatcher extends TypeSafeDiagnosingMatcher<View> {
        private Resources resources;
        @RemoteMsgField(order = 0)
        Matcher<Integer> viewIdMatcher;

        @RemoteMsgConstructor
        private WithIdMatcher(Matcher<Integer> matcher) {
            this.viewIdMatcher = matcher;
        }

        private String getViewIdString(String str) {
            java.util.regex.Matcher matcher = ViewMatchers.RESOURCE_ID_PATTERN.matcher(str);
            StringBuffer stringBuffer = new StringBuffer(str.length());
            while (matcher.find()) {
                if (this.resources != null) {
                    String group = matcher.group();
                    String access$4200 = ViewMatchers.safeGetResourceName(this.resources, Integer.parseInt(group));
                    if (access$4200 != null) {
                        StringBuilder sb = new StringBuilder(String.valueOf(group).length() + 1 + String.valueOf(access$4200).length());
                        sb.append(group);
                        sb.append("/");
                        sb.append(access$4200);
                        matcher.appendReplacement(stringBuffer, sb.toString());
                    } else {
                        matcher.appendReplacement(stringBuffer, String.format(Locale.ROOT, "%s (resource name not found)", new Object[]{group}));
                    }
                }
            }
            matcher.appendTail(stringBuffer);
            return stringBuffer.toString();
        }

        public void describeTo(Description description) {
            description.appendText("view.getId() ").appendText(getViewIdString(this.viewIdMatcher.toString()));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            this.resources = view.getResources();
            boolean matches = this.viewIdMatcher.matches(Integer.valueOf(view.getId()));
            if (!matches && !(description instanceof Description.NullDescription)) {
                Description appendText = description.appendText("view.getId() was ");
                int id = view.getId();
                StringBuilder sb = new StringBuilder(13);
                sb.append("<");
                sb.append(id);
                sb.append(">");
                appendText.appendText(getViewIdString(sb.toString()));
            }
            return matches;
        }
    }

    static final class WithInputTypeMatcher extends BoundedDiagnosingMatcher<View, EditText> {
        @RemoteMsgField(order = 0)
        private final int inputType;

        @RemoteMsgConstructor
        private WithInputTypeMatcher(int i) {
            super(EditText.class);
            this.inputType = i;
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("editText.getInputType() is ").appendValue(Integer.valueOf(this.inputType));
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(EditText editText, Description description) {
            description.appendText("editText.getInputType() was ").appendValue(Integer.valueOf(editText.getInputType()));
            return editText.getInputType() == this.inputType;
        }
    }

    static final class WithParentIndexMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final int index;
        private final Matcher<ViewGroup> parentViewGroupMatcher;

        @RemoteMsgConstructor
        private WithParentIndexMatcher(int i) {
            this.parentViewGroupMatcher = Matchers.isA(ViewGroup.class);
            this.index = i;
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            ViewParent parent = view.getParent();
            if (!this.parentViewGroupMatcher.matches(parent)) {
                description.appendText("view.getParent() ");
                this.parentViewGroupMatcher.describeMismatch(parent, description);
                return false;
            }
            ViewGroup viewGroup = (ViewGroup) parent;
            int childCount = viewGroup.getChildCount();
            int i = this.index;
            if (childCount <= i) {
                description.appendText("parent only has ").appendValue(Integer.valueOf(viewGroup.getChildCount())).appendText(" children");
                return false;
            }
            View childAt = viewGroup.getChildAt(i);
            if (childAt == view) {
                return true;
            }
            description.appendText("child view at index ").appendValue(Integer.valueOf(this.index)).appendText(" was ").appendValue(childAt);
            return false;
        }

        public void describeTo(Description description) {
            description.appendText("(view.getParent() ").appendDescriptionOf(this.parentViewGroupMatcher).appendText(" and is at child index ").appendValue(Integer.valueOf(this.index)).appendText(")");
        }
    }

    static final class WithParentMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Matcher<View> parentMatcher;

        @RemoteMsgConstructor
        private WithParentMatcher(Matcher<View> matcher) {
            this.parentMatcher = matcher;
        }

        public void describeTo(Description description) {
            description.appendText("view.getParent() ").appendDescriptionOf(this.parentMatcher);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            ViewParent parent = view.getParent();
            if (this.parentMatcher.matches(parent)) {
                return true;
            }
            description.appendText("view.getParent() ");
            this.parentMatcher.describeMismatch(parent, description);
            return false;
        }
    }

    static final class WithResourceNameMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Matcher<String> stringMatcher;

        @RemoteMsgConstructor
        private WithResourceNameMatcher(Matcher<String> matcher) {
            this.stringMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            int id = view.getId();
            if (id == -1) {
                description.appendText("view.getId() was View.NO_ID");
                return false;
            } else if (view.getResources() == null) {
                description.appendText("view.getResources() was null, can't resolve resource name");
                return false;
            } else if (ViewMatchers.isViewIdGenerated(id)) {
                description.appendText("view.getId() was generated by a call to View.generateViewId()");
                return false;
            } else {
                String access$4400 = ViewMatchers.safeGetResourceEntryName(view.getResources(), view.getId());
                if (access$4400 == null) {
                    description.appendText("view.getId() was ").appendValue(Integer.valueOf(id)).appendText(" which fails to resolve resource name");
                    return false;
                } else if (this.stringMatcher.matches(access$4400)) {
                    return true;
                } else {
                    description.appendText("view.getId() was <").appendText(access$4400).appendText(">");
                    return false;
                }
            }
        }

        public void describeTo(Description description) {
            description.appendText("view.getId()'s resource name should match ").appendDescriptionOf(this.stringMatcher);
        }
    }

    static final class WithSpinnerTextIdMatcher extends BoundedDiagnosingMatcher<View, Spinner> {
        private String expectedText;
        @RemoteMsgField(order = 0)
        private final int resourceId;
        private String resourceName;

        @RemoteMsgConstructor
        private WithSpinnerTextIdMatcher(int i) {
            super(Spinner.class);
            this.resourceName = null;
            this.expectedText = null;
            this.resourceId = i;
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(Spinner spinner, Description description) {
            if (this.expectedText == null) {
                try {
                    this.expectedText = spinner.getResources().getString(this.resourceId);
                } catch (Resources.NotFoundException unused) {
                }
                this.resourceName = ViewMatchers.safeGetResourceEntryName(spinner.getResources(), this.resourceId);
            }
            if (this.expectedText == null) {
                description.appendText("failure to resolve resourceId ").appendValue(Integer.valueOf(this.resourceId));
                return false;
            }
            Object selectedItem = spinner.getSelectedItem();
            if (selectedItem == null) {
                description.appendText("spinner.getSelectedItem() was null");
                return false;
            }
            description.appendText("spinner.getSelectedItem().toString() was ").appendValue(selectedItem.toString());
            return this.expectedText.equals(selectedItem.toString());
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("spinner.getSelectedItem().toString() to match string from resource id: ").appendValue(Integer.valueOf(this.resourceId));
            if (this.resourceName != null) {
                description.appendText(" [").appendText(this.resourceName).appendText("]");
            }
            if (this.expectedText != null) {
                description.appendText(" value: ").appendText(this.expectedText);
            }
        }
    }

    static final class WithSpinnerTextMatcher extends BoundedDiagnosingMatcher<View, Spinner> {
        @RemoteMsgField(order = 0)
        private final Matcher<String> stringMatcher;

        @RemoteMsgConstructor
        private WithSpinnerTextMatcher(Matcher<String> matcher) {
            super(Spinner.class);
            this.stringMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(Spinner spinner, Description description) {
            Object selectedItem = spinner.getSelectedItem();
            if (selectedItem == null) {
                description.appendText("spinner.getSelectedItem() was null");
                return false;
            }
            description.appendText("spinner.getSelectedItem().toString() was ").appendValue(selectedItem.toString());
            return this.stringMatcher.matches(spinner.getSelectedItem().toString());
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("spinner.getSelectedItem().toString() to match ").appendDescriptionOf(this.stringMatcher);
        }
    }

    static final class WithTagKeyMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final int key;
        @RemoteMsgField(order = 1)
        private final Matcher<?> objectMatcher;

        @RemoteMsgConstructor
        private WithTagKeyMatcher(int i, Matcher<?> matcher) {
            this.key = i;
            this.objectMatcher = matcher;
        }

        public void describeTo(Description description) {
            int i = this.key;
            StringBuilder sb = new StringBuilder(25);
            sb.append("view.getTag(");
            sb.append(i);
            sb.append(") ");
            description.appendText(sb.toString()).appendDescriptionOf(this.objectMatcher);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            Object tag = view.getTag(this.key);
            if (this.objectMatcher.matches(tag)) {
                return true;
            }
            int i = this.key;
            StringBuilder sb = new StringBuilder(25);
            sb.append("view.getTag(");
            sb.append(i);
            sb.append(") ");
            description.appendText(sb.toString());
            this.objectMatcher.describeMismatch(tag, description);
            return false;
        }
    }

    static final class WithTagValueMatcher extends TypeSafeDiagnosingMatcher<View> {
        @RemoteMsgField(order = 0)
        private final Matcher<Object> tagValueMatcher;

        @RemoteMsgConstructor
        private WithTagValueMatcher(Matcher<Object> matcher) {
            this.tagValueMatcher = matcher;
        }

        public void describeTo(Description description) {
            description.appendText("view.getTag() ").appendDescriptionOf(this.tagValueMatcher);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(View view, Description description) {
            Object tag = view.getTag();
            if (this.tagValueMatcher.matches(tag)) {
                return true;
            }
            description.appendText("view.getTag() ");
            this.tagValueMatcher.describeMismatch(tag, description);
            return false;
        }
    }

    static final class WithTextMatcher extends BoundedDiagnosingMatcher<View, TextView> {
        @RemoteMsgField(order = 0)
        private final Matcher<String> stringMatcher;

        @RemoteMsgConstructor
        private WithTextMatcher(Matcher<String> matcher) {
            super(TextView.class);
            this.stringMatcher = matcher;
        }

        /* access modifiers changed from: protected */
        public void describeMoreTo(Description description) {
            description.appendText("view.getText() with or without transformation to match: ");
            this.stringMatcher.describeTo(description);
        }

        /* access modifiers changed from: protected */
        public boolean matchesSafely(TextView textView, Description description) {
            String charSequence = textView.getText().toString();
            if (this.stringMatcher.matches(charSequence)) {
                return true;
            }
            description.appendText("view.getText() was ").appendValue(charSequence);
            if (textView.getTransformationMethod() == null) {
                return false;
            }
            CharSequence transformation = textView.getTransformationMethod().getTransformation(charSequence, textView);
            description.appendText(" transformed text was ").appendValue(transformation);
            if (transformation != null) {
                return this.stringMatcher.matches(transformation.toString());
            }
            return false;
        }
    }

    private ViewMatchers() {
    }

    public static <T> void assertThat(T t, Matcher<T> matcher) {
        assertThat("", t, matcher);
    }

    public static Matcher<View> doesNotHaveFocus() {
        return new HasFocusMatcher(false);
    }

    private static <T> String getMismatchDescriptionString(T t, Matcher<T> matcher) {
        StringDescription stringDescription = new StringDescription();
        matcher.describeMismatch(t, stringDescription);
        String trim = stringDescription.toString().trim();
        return trim.isEmpty() ? t.toString() : trim;
    }

    public static Matcher<View> hasBackground(int i) {
        return new HasBackgroundMatcher(i);
    }

    public static Matcher<View> hasChildCount(int i) {
        return new HasChildCountMatcher(i);
    }

    public static Matcher<View> hasContentDescription() {
        return new HasContentDescriptionMatcher();
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> hasDescendant(org.hamcrest.Matcher<android.view.View> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$HasDescendantMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$HasDescendantMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.hasDescendant(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    public static Matcher<View> hasErrorText(String str) {
        return hasErrorText((Matcher<String>) Matchers.m1139is(str));
    }

    public static Matcher<View> hasFocus() {
        return new HasFocusMatcher(true);
    }

    public static Matcher<View> hasImeAction(int i) {
        return hasImeAction((Matcher<Integer>) Matchers.m1139is(Integer.valueOf(i)));
    }

    public static Matcher<View> hasLinks() {
        return new HasLinksMatcher();
    }

    public static Matcher<View> hasMinimumChildCount(int i) {
        return new HasMinimumChildCountMatcher(i);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> hasSibling(org.hamcrest.Matcher<android.view.View> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$HasSiblingMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$HasSiblingMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.hasSibling(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    public static Matcher<View> hasTextColor(final int i) {
        return new BoundedDiagnosingMatcher<View, TextView>(TextView.class) {
            private Context context;

            private String getColorHex(int i) {
                return String.format(Locale.ROOT, "#%02X%06X", new Object[]{Integer.valueOf(Color.alpha(i) & 255), Integer.valueOf(i & ViewCompat.MEASURED_SIZE_MASK)});
            }

            /* access modifiers changed from: protected */
            public void describeMoreTo(Description description) {
                int i;
                description.appendText("textView.getCurrentTextColor() is color with ");
                if (this.context == null) {
                    description.appendText("ID ").appendValue(Integer.valueOf(i));
                    return;
                }
                if (Build.VERSION.SDK_INT <= 22) {
                    i = this.context.getResources().getColor(i);
                } else {
                    i = this.context.getColor(i);
                }
                String valueOf = String.valueOf(getColorHex(i));
                description.appendText(valueOf.length() != 0 ? "value ".concat(valueOf) : new String("value "));
            }

            /* access modifiers changed from: protected */
            public boolean matchesSafely(TextView textView, Description description) {
                int i;
                this.context = textView.getContext();
                int currentTextColor = textView.getCurrentTextColor();
                if (Build.VERSION.SDK_INT <= 22) {
                    i = this.context.getResources().getColor(i);
                } else {
                    i = this.context.getColor(i);
                }
                description.appendText("textView.getCurrentTextColor() was ").appendText(getColorHex(currentTextColor));
                return currentTextColor == i;
            }
        };
    }

    public static Matcher<View> isAssignableFrom(Class<? extends View> cls) {
        return new IsAssignableFromMatcher(cls);
    }

    public static Matcher<View> isChecked() {
        return withCheckBoxState(Matchers.m1139is(true));
    }

    public static Matcher<View> isClickable() {
        return new IsClickableMatcher(true);
    }

    public static Matcher<View> isCompletelyDisplayed() {
        return isDisplayingAtLeast(100);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> isDescendantOfA(org.hamcrest.Matcher<android.view.View> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$IsDescendantOfAMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$IsDescendantOfAMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    public static Matcher<View> isDisplayed() {
        return new IsDisplayedMatcher();
    }

    public static Matcher<View> isDisplayingAtLeast(int i) {
        boolean z = true;
        Preconditions.checkArgument(i <= 100, "Cannot have over 100 percent: %s", i);
        if (i <= 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "Must have a positive, non-zero value: %s", i);
        return new IsDisplayingAtLeastMatcher(i);
    }

    public static Matcher<View> isEnabled() {
        return new IsEnabledMatcher(true);
    }

    public static Matcher<View> isFocusable() {
        return new IsFocusableMatcher(true);
    }

    public static Matcher<View> isFocused() {
        return new IsFocusedMatcher(true);
    }

    public static Matcher<View> isJavascriptEnabled() {
        return new IsJavascriptEnabledMatcher();
    }

    public static Matcher<View> isNotChecked() {
        return withCheckBoxState(Matchers.m1139is(false));
    }

    public static Matcher<View> isNotClickable() {
        return new IsClickableMatcher(false);
    }

    public static Matcher<View> isNotEnabled() {
        return new IsEnabledMatcher(false);
    }

    public static Matcher<View> isNotFocusable() {
        return new IsFocusableMatcher(false);
    }

    public static Matcher<View> isNotFocused() {
        return new IsFocusedMatcher(false);
    }

    public static Matcher<View> isNotSelected() {
        return new IsSelectedMatcher(false);
    }

    public static Matcher<View> isRoot() {
        return new IsRootMatcher();
    }

    public static Matcher<View> isSelected() {
        return new IsSelectedMatcher(true);
    }

    /* access modifiers changed from: private */
    public static boolean isViewIdGenerated(int i) {
        return (-16777216 & i) == 0 && (i & ViewCompat.MEASURED_SIZE_MASK) != 0;
    }

    /* access modifiers changed from: private */
    public static String safeGetResourceEntryName(Resources resources, int i) {
        try {
            if (isViewIdGenerated(i)) {
                return null;
            }
            return resources.getResourceEntryName(i);
        } catch (Resources.NotFoundException unused) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static String safeGetResourceName(Resources resources, int i) {
        try {
            if (isViewIdGenerated(i)) {
                return null;
            }
            return resources.getResourceName(i);
        } catch (Resources.NotFoundException unused) {
            return null;
        }
    }

    public static Matcher<View> supportsInputMethods() {
        return new SupportsInputMethodsMatcher();
    }

    public static Matcher<View> withAlpha(float f) {
        return new WithAlphaMatcher(f);
    }

    private static <E extends View & Checkable> Matcher<View> withCheckBoxState(Matcher<Boolean> matcher) {
        return new WithCheckBoxStateMatcher(matcher);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> withChild(org.hamcrest.Matcher<android.view.View> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$WithChildMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$WithChildMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.withChild(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<java.lang.String>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> withClassName(org.hamcrest.Matcher<java.lang.String> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$WithClassNameMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$WithClassNameMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.withClassName(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    public static Matcher<View> withContentDescription(int i) {
        return new WithContentDescriptionFromIdMatcher(i);
    }

    public static Matcher<View> withEffectiveVisibility(Visibility visibility) {
        return new WithEffectiveVisibilityMatcher(visibility);
    }

    public static Matcher<View> withHint(int i) {
        return new WithCharSequenceMatcher(i, WithCharSequenceMatcher.TextViewMethod.GET_HINT);
    }

    public static Matcher<View> withId(int i) {
        return withId((Matcher<Integer>) Matchers.m1139is(Integer.valueOf(i)));
    }

    public static Matcher<View> withInputType(int i) {
        return new WithInputTypeMatcher(i);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> withParent(org.hamcrest.Matcher<android.view.View> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$WithParentMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$WithParentMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.withParent(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    public static Matcher<View> withParentIndex(int i) {
        Preconditions.checkArgument(i >= 0, "Index %s must be >= 0", i);
        return new WithParentIndexMatcher(i);
    }

    public static Matcher<View> withResourceName(String str) {
        return withResourceName((Matcher<String>) Matchers.m1139is(str));
    }

    public static Matcher<View> withSpinnerText(int i) {
        return new WithSpinnerTextIdMatcher(i);
    }

    public static Matcher<View> withSubstring(String str) {
        return withText(Matchers.containsString(str));
    }

    public static Matcher<View> withTagKey(int i) {
        return withTagKey(i, Matchers.notNullValue());
    }

    public static Matcher<View> withTagValue(Matcher<Object> matcher) {
        return new WithTagValueMatcher((Matcher) Preconditions.checkNotNull(matcher));
    }

    public static Matcher<View> withText(int i) {
        return new WithCharSequenceMatcher(i, WithCharSequenceMatcher.TextViewMethod.GET_TEXT);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<java.lang.String>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> hasErrorText(org.hamcrest.Matcher<java.lang.String> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$HasErrorTextMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$HasErrorTextMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.hasErrorText(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    public static Matcher<View> hasImeAction(Matcher<Integer> matcher) {
        return new HasImeActionMatcher(matcher);
    }

    public static Matcher<View> withContentDescription(String str) {
        return new WithContentDescriptionTextMatcher(Matchers.m1139is(str));
    }

    public static Matcher<View> withHint(String str) {
        return withHint((Matcher<String>) Matchers.m1139is((String) Preconditions.checkNotNull(str)));
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<java.lang.Integer>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> withId(org.hamcrest.Matcher<java.lang.Integer> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$WithIdMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$WithIdMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.withId(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<java.lang.String>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> withResourceName(org.hamcrest.Matcher<java.lang.String> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$WithResourceNameMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$WithResourceNameMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.withResourceName(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    public static Matcher<View> withSpinnerText(String str) {
        return withSpinnerText((Matcher<String>) Matchers.m1139is(str));
    }

    public static Matcher<View> withTagKey(int i, Matcher<?> matcher) {
        return new WithTagKeyMatcher(i, (Matcher) Preconditions.checkNotNull(matcher));
    }

    public static Matcher<View> withText(String str) {
        return withText((Matcher<String>) Matchers.m1139is(str));
    }

    public static <T> void assertThat(String str, T t, Matcher<T> matcher) {
        if (!matcher.matches(t)) {
            StringDescription stringDescription = new StringDescription();
            stringDescription.appendText(str).appendText("\nExpected: ").appendDescriptionOf(matcher).appendText("\n     Got: ").appendText(getMismatchDescriptionString(t, matcher));
            if (t instanceof View) {
                stringDescription.appendText("\nView Details: ").appendText(HumanReadables.describe((View) t));
            }
            stringDescription.appendText("\n");
            throw new AssertionFailedError(stringDescription.toString());
        }
    }

    public static Matcher<View> withContentDescription(Matcher<? extends CharSequence> matcher) {
        return new WithContentDescriptionMatcher((Matcher) Preconditions.checkNotNull(matcher));
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<java.lang.String>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> withHint(org.hamcrest.Matcher<java.lang.String> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$WithHintMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$WithHintMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.withHint(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<java.lang.String>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> withSpinnerText(org.hamcrest.Matcher<java.lang.String> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$WithSpinnerTextMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$WithSpinnerTextMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.withSpinnerText(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<java.lang.String>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.hamcrest.Matcher<android.view.View> withText(org.hamcrest.Matcher<java.lang.String> r2) {
        /*
            androidx.test.espresso.matcher.ViewMatchers$WithTextMatcher r0 = new androidx.test.espresso.matcher.ViewMatchers$WithTextMatcher
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.matcher.ViewMatchers.withText(org.hamcrest.Matcher):org.hamcrest.Matcher");
    }
}
