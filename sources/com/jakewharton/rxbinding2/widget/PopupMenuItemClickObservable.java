package com.jakewharton.rxbinding2.widget;

import android.view.MenuItem;
import android.widget.PopupMenu;
import com.jakewharton.rxbinding2.internal.Preconditions;
import p028io.reactivex.Observable;
import p028io.reactivex.Observer;
import p028io.reactivex.android.MainThreadDisposable;

final class PopupMenuItemClickObservable extends Observable<MenuItem> {
    private final PopupMenu view;

    PopupMenuItemClickObservable(PopupMenu popupMenu) {
        this.view = popupMenu;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super MenuItem> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            this.view.setOnMenuItemClickListener(listener);
            observer.onSubscribe(listener);
        }
    }

    static final class Listener extends MainThreadDisposable implements PopupMenu.OnMenuItemClickListener {
        private final Observer<? super MenuItem> observer;
        private final PopupMenu view;

        Listener(PopupMenu popupMenu, Observer<? super MenuItem> observer2) {
            this.view = popupMenu;
            this.observer = observer2;
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            if (isDisposed()) {
                return false;
            }
            this.observer.onNext(menuItem);
            return true;
        }

        /* access modifiers changed from: protected */
        public void onDispose() {
            this.view.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) null);
        }
    }
}
