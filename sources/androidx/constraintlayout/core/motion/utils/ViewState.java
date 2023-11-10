package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.MotionWidget;

public class ViewState {
    public int bottom;
    public int left;
    public int right;
    public float rotation;

    /* renamed from: top  reason: collision with root package name */
    public int f2155top;

    public void getState(MotionWidget motionWidget) {
        this.left = motionWidget.getLeft();
        this.f2155top = motionWidget.getTop();
        this.right = motionWidget.getRight();
        this.bottom = motionWidget.getBottom();
        this.rotation = (float) ((int) motionWidget.getRotationZ());
    }

    public int width() {
        return this.right - this.left;
    }

    public int height() {
        return this.bottom - this.f2155top;
    }
}
