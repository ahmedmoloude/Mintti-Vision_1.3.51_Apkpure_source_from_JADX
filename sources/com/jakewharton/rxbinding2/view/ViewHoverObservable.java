package com.jakewharton.rxbinding2.view;

import android.view.MotionEvent;
import android.view.View;
import com.jakewharton.rxbinding2.internal.Preconditions;
import p028io.reactivex.Observable;
import p028io.reactivex.Observer;
import p028io.reactivex.android.MainThreadDisposable;
import p028io.reactivex.functions.Predicate;

final class ViewHoverObservable extends Observable<MotionEvent> {
    private final Predicate<? super MotionEvent> handled;
    private final View view;

    ViewHoverObservable(View view2, Predicate<? super MotionEvent> predicate) {
        this.view = view2;
        this.handled = predicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super MotionEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, this.handled, observer);
            observer.onSubscribe(listener);
            this.view.setOnHoverListener(listener);
        }
    }

    static final class Listener extends MainThreadDisposable implements View.OnHoverListener {
        private final Predicate<? super MotionEvent> handled;
        private final Observer<? super MotionEvent> observer;
        private final View view;

        Listener(View view2, Predicate<? super MotionEvent> predicate, Observer<? super MotionEvent> observer2) {
            this.view = view2;
            this.handled = predicate;
            this.observer = observer2;
        }

        public boolean onHover(View view2, MotionEvent motionEvent) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.handled.test(motionEvent)) {
                    return false;
                }
                this.observer.onNext(motionEvent);
                return true;
            } catch (Exception e) {
                this.observer.onError(e);
                dispose();
                return false;
            }
        }

        /* access modifiers changed from: protected */
        public void onDispose() {
            this.view.setOnHoverListener((View.OnHoverListener) null);
        }
    }
}
