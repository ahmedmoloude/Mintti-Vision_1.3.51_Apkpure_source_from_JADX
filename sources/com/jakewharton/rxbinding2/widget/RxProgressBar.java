package com.jakewharton.rxbinding2.widget;

import android.widget.ProgressBar;
import com.jakewharton.rxbinding2.internal.Preconditions;
import p028io.reactivex.functions.Consumer;

public final class RxProgressBar {
    @Deprecated
    public static Consumer<? super Integer> incrementProgressBy(ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        progressBar.getClass();
        return new Consumer(progressBar) {
            public final /* synthetic */ ProgressBar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.incrementProgressBy(((Integer) obj).intValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super Integer> incrementSecondaryProgressBy(ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        progressBar.getClass();
        return new Consumer(progressBar) {
            public final /* synthetic */ ProgressBar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.incrementSecondaryProgressBy(((Integer) obj).intValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super Boolean> indeterminate(ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        progressBar.getClass();
        return new Consumer(progressBar) {
            public final /* synthetic */ ProgressBar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setIndeterminate(((Boolean) obj).booleanValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super Integer> max(ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        progressBar.getClass();
        return new Consumer(progressBar) {
            public final /* synthetic */ ProgressBar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setMax(((Integer) obj).intValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super Integer> progress(ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        progressBar.getClass();
        return new Consumer(progressBar) {
            public final /* synthetic */ ProgressBar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setProgress(((Integer) obj).intValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super Integer> secondaryProgress(ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        progressBar.getClass();
        return new Consumer(progressBar) {
            public final /* synthetic */ ProgressBar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setSecondaryProgress(((Integer) obj).intValue());
            }
        };
    }

    private RxProgressBar() {
        throw new AssertionError("No instances.");
    }
}
