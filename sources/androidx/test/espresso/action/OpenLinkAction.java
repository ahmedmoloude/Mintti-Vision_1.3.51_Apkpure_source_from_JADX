package androidx.test.espresso.action;

import android.net.Uri;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public final class OpenLinkAction implements ViewAction {
    private final Matcher<String> linkTextMatcher;
    private final Matcher<Uri> uriMatcher;

    /* JADX WARNING: type inference failed for: r1v0, types: [org.hamcrest.Matcher<java.lang.String>, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.Object, org.hamcrest.Matcher<android.net.Uri>] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public OpenLinkAction(org.hamcrest.Matcher<java.lang.String> r1, org.hamcrest.Matcher<android.net.Uri> r2) {
        /*
            r0 = this;
            r0.<init>()
            java.lang.Object r1 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r1)
            org.hamcrest.Matcher r1 = (org.hamcrest.Matcher) r1
            r0.linkTextMatcher = r1
            java.lang.Object r1 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r1 = (org.hamcrest.Matcher) r1
            r0.uriMatcher = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.action.OpenLinkAction.<init>(org.hamcrest.Matcher, org.hamcrest.Matcher):void");
    }

    public Matcher<View> getConstraints() {
        return Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.isAssignableFrom(TextView.class), ViewMatchers.hasLinks());
    }

    public String getDescription() {
        return String.format(Locale.ROOT, "open link with text %s and uri %s", new Object[]{this.linkTextMatcher, this.uriMatcher});
    }

    public void perform(UiController uiController, View view) {
        View view2 = view;
        TextView textView = (TextView) view2;
        String charSequence = textView.getText().toString();
        URLSpan[] urls = textView.getUrls();
        Spanned spanned = (Spanned) textView.getText();
        ArrayList newArrayList = Lists.newArrayList();
        int length = urls.length;
        int i = 0;
        while (i < length) {
            URLSpan uRLSpan = urls[i];
            int spanStart = spanned.getSpanStart(uRLSpan);
            boolean z = spanStart != -1;
            String valueOf = String.valueOf(uRLSpan);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
            sb.append("Unable to get start of text associated with url: ");
            sb.append(valueOf);
            Preconditions.checkState(z, sb.toString());
            int spanEnd = spanned.getSpanEnd(uRLSpan);
            boolean z2 = spanEnd != -1;
            String valueOf2 = String.valueOf(uRLSpan);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 47);
            sb2.append("Unable to get end of text associated with url: ");
            sb2.append(valueOf2);
            Preconditions.checkState(z2, sb2.toString());
            String substring = charSequence.substring(spanStart, spanEnd);
            newArrayList.add(substring);
            if (!this.linkTextMatcher.matches(substring) || !this.uriMatcher.matches(Uri.parse(uRLSpan.getURL()))) {
                i++;
            } else {
                uRLSpan.onClick(view2);
                return;
            }
        }
        throw new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(new RuntimeException(String.format(Locale.ROOT, "Link with text '%s' and uri '%s' not found. List of links found in this view: %s\nList of uris: %s", new Object[]{this.linkTextMatcher, this.uriMatcher, newArrayList, Arrays.asList(urls)}))).build();
    }
}
