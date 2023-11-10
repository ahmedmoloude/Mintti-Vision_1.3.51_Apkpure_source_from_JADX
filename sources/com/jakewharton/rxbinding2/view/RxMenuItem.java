package com.jakewharton.rxbinding2.view;

import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import com.jakewharton.rxbinding2.internal.Functions;
import com.jakewharton.rxbinding2.internal.Preconditions;
import p028io.reactivex.Observable;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.functions.Predicate;

public final class RxMenuItem {
    public static Observable<Object> clicks(MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new MenuItemClickOnSubscribe(menuItem, Functions.PREDICATE_ALWAYS_TRUE);
    }

    public static Observable<Object> clicks(MenuItem menuItem, Predicate<? super MenuItem> predicate) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new MenuItemClickOnSubscribe(menuItem, predicate);
    }

    public static Observable<MenuItemActionViewEvent> actionViewEvents(MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        return new MenuItemActionViewEventObservable(menuItem, Functions.PREDICATE_ALWAYS_TRUE);
    }

    public static Observable<MenuItemActionViewEvent> actionViewEvents(MenuItem menuItem, Predicate<? super MenuItemActionViewEvent> predicate) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new MenuItemActionViewEventObservable(menuItem, predicate);
    }

    @Deprecated
    public static Consumer<? super Boolean> checked(MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        menuItem.getClass();
        return new Consumer(menuItem) {
            public final /* synthetic */ MenuItem f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setChecked(((Boolean) obj).booleanValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super Boolean> enabled(MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        menuItem.getClass();
        return new Consumer(menuItem) {
            public final /* synthetic */ MenuItem f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setEnabled(((Boolean) obj).booleanValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super Drawable> icon(MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        menuItem.getClass();
        return new Consumer(menuItem) {
            public final /* synthetic */ MenuItem f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setIcon((Drawable) obj);
            }
        };
    }

    @Deprecated
    public static Consumer<? super Integer> iconRes(MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        menuItem.getClass();
        return new Consumer(menuItem) {
            public final /* synthetic */ MenuItem f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setIcon(((Integer) obj).intValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super CharSequence> title(MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        menuItem.getClass();
        return new Consumer(menuItem) {
            public final /* synthetic */ MenuItem f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setTitle((CharSequence) obj);
            }
        };
    }

    @Deprecated
    public static Consumer<? super Integer> titleRes(MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        menuItem.getClass();
        return new Consumer(menuItem) {
            public final /* synthetic */ MenuItem f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setTitle(((Integer) obj).intValue());
            }
        };
    }

    @Deprecated
    public static Consumer<? super Boolean> visible(MenuItem menuItem) {
        Preconditions.checkNotNull(menuItem, "menuItem == null");
        menuItem.getClass();
        return new Consumer(menuItem) {
            public final /* synthetic */ MenuItem f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setVisible(((Boolean) obj).booleanValue());
            }
        };
    }

    private RxMenuItem() {
        throw new AssertionError("No instances.");
    }
}
