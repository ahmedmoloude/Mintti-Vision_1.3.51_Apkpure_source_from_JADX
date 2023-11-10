package com.kongzue.dialogx.util.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import com.kongzue.dialogx.interfaces.ScrollController;

public class BottomDialogScrollView extends ScrollView implements ScrollController {
    boolean lockScroll;

    public BottomDialogScrollView(Context context) {
        super(context);
    }

    public BottomDialogScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BottomDialogScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public BottomDialogScrollView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public boolean isLockScroll() {
        return this.lockScroll;
    }

    public void lockScroll(boolean z) {
        this.lockScroll = z;
    }

    public int getScrollDistance() {
        return getScrollY();
    }

    public boolean isCanScroll() {
        View childAt = getChildAt(0);
        if (childAt == null || getHeight() >= childAt.getHeight()) {
            return false;
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.lockScroll) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }
}
