package com.jakewharton.rxbinding2.widget;

import android.view.MenuItem;
import android.widget.Toolbar;
import com.jakewharton.rxbinding2.internal.Preconditions;
import p028io.reactivex.Observable;
import p028io.reactivex.functions.Consumer;

public final class RxToolbar {
    public static Observable<MenuItem> itemClicks(Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        return new ToolbarItemClickObservable(toolbar);
    }

    public static Observable<Object> navigationClicks(Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        return new ToolbarNavigationClickObservable(toolbar);
    }

    @Deprecated
    public static Consumer<? super CharSequence> title(Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        toolbar.getClass();
        return new Consumer(toolbar) {
            public final /* synthetic */ Toolbar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setTitle((CharSequence) obj);
            }
        };
    }

    @Deprecated
    public static Consumer<? super Integer> titleRes(Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        toolbar.getClass();
        return new Consumer(toolbar) {
            public final /* synthetic */ Toolbar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setTitle(((Integer) obj).intValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super CharSequence> subtitle(Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        toolbar.getClass();
        return new Consumer(toolbar) {
            public final /* synthetic */ Toolbar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setSubtitle((CharSequence) obj);
            }
        };
    }

    @Deprecated
    public static Consumer<? super Integer> subtitleRes(Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        toolbar.getClass();
        return new Consumer(toolbar) {
            public final /* synthetic */ Toolbar f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setSubtitle(((Integer) obj).intValue());
            }
        };
    }

    private RxToolbar() {
        throw new AssertionError("No instances.");
    }
}
