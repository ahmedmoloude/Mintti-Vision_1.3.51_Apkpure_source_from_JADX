package com.jakewharton.rxbinding2.widget;

import android.widget.AutoCompleteTextView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import p028io.reactivex.Observable;
import p028io.reactivex.functions.Consumer;

public final class RxAutoCompleteTextView {
    public static Observable<AdapterViewItemClickEvent> itemClickEvents(AutoCompleteTextView autoCompleteTextView) {
        Preconditions.checkNotNull(autoCompleteTextView, "view == null");
        return new AutoCompleteTextViewItemClickEventObservable(autoCompleteTextView);
    }

    @Deprecated
    public static Consumer<? super CharSequence> completionHint(AutoCompleteTextView autoCompleteTextView) {
        Preconditions.checkNotNull(autoCompleteTextView, "view == null");
        autoCompleteTextView.getClass();
        return new Consumer(autoCompleteTextView) {
            public final /* synthetic */ AutoCompleteTextView f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setCompletionHint((CharSequence) obj);
            }
        };
    }

    @Deprecated
    public static Consumer<? super Integer> threshold(AutoCompleteTextView autoCompleteTextView) {
        Preconditions.checkNotNull(autoCompleteTextView, "view == null");
        autoCompleteTextView.getClass();
        return new Consumer(autoCompleteTextView) {
            public final /* synthetic */ AutoCompleteTextView f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setThreshold(((Integer) obj).intValue());
            }
        };
    }

    private RxAutoCompleteTextView() {
        throw new AssertionError("No instances.");
    }
}
