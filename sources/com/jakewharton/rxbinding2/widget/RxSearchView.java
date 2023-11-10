package com.jakewharton.rxbinding2.widget;

import android.widget.SearchView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import p028io.reactivex.functions.Consumer;

public final class RxSearchView {
    public static InitialValueObservable<SearchViewQueryTextEvent> queryTextChangeEvents(SearchView searchView) {
        Preconditions.checkNotNull(searchView, "view == null");
        return new SearchViewQueryTextChangeEventsObservable(searchView);
    }

    public static InitialValueObservable<CharSequence> queryTextChanges(SearchView searchView) {
        Preconditions.checkNotNull(searchView, "view == null");
        return new SearchViewQueryTextChangesObservable(searchView);
    }

    public static Consumer<? super CharSequence> query(SearchView searchView, boolean z) {
        Preconditions.checkNotNull(searchView, "view == null");
        return new Consumer(searchView, z) {
            public final /* synthetic */ SearchView f$0;
            public final /* synthetic */ boolean f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final void accept(Object obj) {
                this.f$0.setQuery((CharSequence) obj, this.f$1);
            }
        };
    }

    private RxSearchView() {
        throw new AssertionError("No instances.");
    }
}
