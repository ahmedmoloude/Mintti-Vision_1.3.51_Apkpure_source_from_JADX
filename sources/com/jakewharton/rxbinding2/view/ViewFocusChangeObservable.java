package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.InitialValueObservable;
import p028io.reactivex.Observer;
import p028io.reactivex.android.MainThreadDisposable;

final class ViewFocusChangeObservable extends InitialValueObservable<Boolean> {
    private final View view;

    ViewFocusChangeObservable(View view2) {
        this.view = view2;
    }

    /* access modifiers changed from: protected */
    public void subscribeListener(Observer<? super Boolean> observer) {
        Listener listener = new Listener(this.view, observer);
        observer.onSubscribe(listener);
        this.view.setOnFocusChangeListener(listener);
    }

    /* access modifiers changed from: protected */
    public Boolean getInitialValue() {
        return Boolean.valueOf(this.view.hasFocus());
    }

    static final class Listener extends MainThreadDisposable implements View.OnFocusChangeListener {
        private final Observer<? super Boolean> observer;
        private final View view;

        Listener(View view2, Observer<? super Boolean> observer2) {
            this.view = view2;
            this.observer = observer2;
        }

        public void onFocusChange(View view2, boolean z) {
            if (!isDisposed()) {
                this.observer.onNext(Boolean.valueOf(z));
            }
        }

        /* access modifiers changed from: protected */
        public void onDispose() {
            this.view.setOnFocusChangeListener((View.OnFocusChangeListener) null);
        }
    }
}
