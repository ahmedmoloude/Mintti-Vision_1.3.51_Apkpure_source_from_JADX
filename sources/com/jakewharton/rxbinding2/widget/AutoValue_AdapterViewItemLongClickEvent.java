package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import java.util.Objects;

final class AutoValue_AdapterViewItemLongClickEvent extends AdapterViewItemLongClickEvent {
    private final View clickedView;

    /* renamed from: id */
    private final long f782id;
    private final int position;
    private final AdapterView<?> view;

    AutoValue_AdapterViewItemLongClickEvent(AdapterView<?> adapterView, View view2, int i, long j) {
        Objects.requireNonNull(adapterView, "Null view");
        this.view = adapterView;
        Objects.requireNonNull(view2, "Null clickedView");
        this.clickedView = view2;
        this.position = i;
        this.f782id = j;
    }

    public AdapterView<?> view() {
        return this.view;
    }

    public View clickedView() {
        return this.clickedView;
    }

    public int position() {
        return this.position;
    }

    /* renamed from: id */
    public long mo24529id() {
        return this.f782id;
    }

    public String toString() {
        return "AdapterViewItemLongClickEvent{view=" + this.view + ", clickedView=" + this.clickedView + ", position=" + this.position + ", id=" + this.f782id + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AdapterViewItemLongClickEvent)) {
            return false;
        }
        AdapterViewItemLongClickEvent adapterViewItemLongClickEvent = (AdapterViewItemLongClickEvent) obj;
        if (!this.view.equals(adapterViewItemLongClickEvent.view()) || !this.clickedView.equals(adapterViewItemLongClickEvent.clickedView()) || this.position != adapterViewItemLongClickEvent.position() || this.f782id != adapterViewItemLongClickEvent.mo24529id()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long j = this.f782id;
        return ((((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.clickedView.hashCode()) * 1000003) ^ this.position) * 1000003) ^ ((int) (j ^ (j >>> 32)));
    }
}
