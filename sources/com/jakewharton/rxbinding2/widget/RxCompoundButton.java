package com.jakewharton.rxbinding2.widget;

import android.widget.CompoundButton;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import p028io.reactivex.functions.Consumer;

public final class RxCompoundButton {
    public static InitialValueObservable<Boolean> checkedChanges(CompoundButton compoundButton) {
        Preconditions.checkNotNull(compoundButton, "view == null");
        return new CompoundButtonCheckedChangeObservable(compoundButton);
    }

    @Deprecated
    public static Consumer<? super Boolean> checked(CompoundButton compoundButton) {
        Preconditions.checkNotNull(compoundButton, "view == null");
        compoundButton.getClass();
        return new Consumer(compoundButton) {
            public final /* synthetic */ CompoundButton f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setChecked(((Boolean) obj).booleanValue());
            }
        };
    }

    public static Consumer<? super Object> toggle(CompoundButton compoundButton) {
        Preconditions.checkNotNull(compoundButton, "view == null");
        return new Consumer(compoundButton) {
            public final /* synthetic */ CompoundButton f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.toggle();
            }
        };
    }

    private RxCompoundButton() {
        throw new AssertionError("No instances.");
    }
}
