package com.jakewharton.rxbinding2.widget;

import android.widget.RatingBar;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import p028io.reactivex.functions.Consumer;

public final class RxRatingBar {
    public static InitialValueObservable<Float> ratingChanges(RatingBar ratingBar) {
        Preconditions.checkNotNull(ratingBar, "view == null");
        return new RatingBarRatingChangeObservable(ratingBar);
    }

    public static InitialValueObservable<RatingBarChangeEvent> ratingChangeEvents(RatingBar ratingBar) {
        Preconditions.checkNotNull(ratingBar, "view == null");
        return new RatingBarRatingChangeEventObservable(ratingBar);
    }

    @Deprecated
    public static Consumer<? super Float> rating(RatingBar ratingBar) {
        Preconditions.checkNotNull(ratingBar, "view == null");
        ratingBar.getClass();
        return new Consumer(ratingBar) {
            public final /* synthetic */ RatingBar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setRating(((Float) obj).floatValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super Boolean> isIndicator(RatingBar ratingBar) {
        Preconditions.checkNotNull(ratingBar, "view == null");
        ratingBar.getClass();
        return new Consumer(ratingBar) {
            public final /* synthetic */ RatingBar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setIsIndicator(((Boolean) obj).booleanValue());
            }
        };
    }

    private RxRatingBar() {
        throw new AssertionError("No instances.");
    }
}
