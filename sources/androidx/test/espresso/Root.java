package androidx.test.espresso;

import android.view.View;
import android.view.WindowManager;
import androidx.test.espresso.core.internal.deps.guava.base.MoreObjects;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.util.EspressoOptional;
import androidx.test.espresso.util.HumanReadables;

public final class Root {
    private final View decorView;
    private final EspressoOptional<WindowManager.LayoutParams> windowLayoutParams;

    public static class Builder {
        /* access modifiers changed from: private */
        public View decorView;
        /* access modifiers changed from: private */
        public WindowManager.LayoutParams windowLayoutParams;

        public Root build() {
            return new Root(this);
        }

        public Builder withDecorView(View view) {
            this.decorView = view;
            return this;
        }

        public Builder withWindowLayoutParams(WindowManager.LayoutParams layoutParams) {
            this.windowLayoutParams = layoutParams;
            return this;
        }
    }

    private Root(Builder builder) {
        this.decorView = (View) Preconditions.checkNotNull(builder.decorView);
        this.windowLayoutParams = EspressoOptional.fromNullable(builder.windowLayoutParams);
    }

    public View getDecorView() {
        return this.decorView;
    }

    public EspressoOptional<WindowManager.LayoutParams> getWindowLayoutParams() {
        return this.windowLayoutParams;
    }

    public boolean isReady() {
        if (this.decorView.isLayoutRequested()) {
            return false;
        }
        int i = this.windowLayoutParams.get().flags;
        if (this.decorView.hasWindowFocus() || (i & 8) == 8) {
            return true;
        }
        return false;
    }

    public String toString() {
        MoreObjects.ToStringHelper add = MoreObjects.toStringHelper(this).add("application-window-token", (Object) this.decorView.getApplicationWindowToken()).add("window-token", (Object) this.decorView.getWindowToken()).add("has-window-focus", this.decorView.hasWindowFocus());
        if (this.windowLayoutParams.isPresent()) {
            add.add("layout-params-type", this.windowLayoutParams.get().type).add("layout-params-string", (Object) this.windowLayoutParams.get());
        }
        add.add("decor-view-string", (Object) HumanReadables.describe(this.decorView));
        return add.toString();
    }
}
