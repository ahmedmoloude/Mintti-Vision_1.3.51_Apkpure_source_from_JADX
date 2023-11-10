package com.kongzue.dialogx.util.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class PopMenuListView extends ListView {
    private float maxHeight = -1.0f;

    public PopMenuListView(Context context) {
        super(context);
    }

    public PopMenuListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PopMenuListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean isCanScroll() {
        return (getFirstVisiblePosition() == 0 && getCount() == getLastVisiblePosition() + 1) ? false : true;
    }

    public float getMaxHeight() {
        return this.maxHeight;
    }

    public PopMenuListView setMaxHeight(float f) {
        this.maxHeight = f;
        return this;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i2);
        float f = this.maxHeight;
        if (f <= ((float) size) && f > -1.0f) {
            i2 = View.MeasureSpec.makeMeasureSpec(Float.valueOf(f).intValue(), Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void layoutChildren() {
        try {
            super.layoutChildren();
        } catch (IllegalStateException unused) {
            ((BaseAdapter) getAdapter()).notifyDataSetChanged();
            super.layoutChildren();
        }
    }
}
