package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.core.widget.NestedScrollView;

/* renamed from: com.kl.healthmonitor.views.MyNestedScrollView */
public class MyNestedScrollView extends NestedScrollView {
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyNestedScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
