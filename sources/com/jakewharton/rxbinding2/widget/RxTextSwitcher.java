package com.jakewharton.rxbinding2.widget;

import android.widget.TextSwitcher;
import com.jakewharton.rxbinding2.internal.Preconditions;
import p028io.reactivex.functions.Consumer;

public final class RxTextSwitcher {
    @Deprecated
    public static Consumer<? super CharSequence> text(TextSwitcher textSwitcher) {
        Preconditions.checkNotNull(textSwitcher, "view == null");
        textSwitcher.getClass();
        return new Consumer(textSwitcher) {
            public final /* synthetic */ TextSwitcher f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setText((CharSequence) obj);
            }
        };
    }

    @Deprecated
    public static Consumer<? super CharSequence> currentText(TextSwitcher textSwitcher) {
        Preconditions.checkNotNull(textSwitcher, "view == null");
        textSwitcher.getClass();
        return new Consumer(textSwitcher) {
            public final /* synthetic */ TextSwitcher f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(Object obj) {
                this.f$0.setCurrentText((CharSequence) obj);
            }
        };
    }

    private RxTextSwitcher() {
        throw new AssertionError("No instances.");
    }
}
