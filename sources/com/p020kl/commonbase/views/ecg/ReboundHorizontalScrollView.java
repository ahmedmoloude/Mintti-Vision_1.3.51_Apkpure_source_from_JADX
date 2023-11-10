package com.p020kl.commonbase.views.ecg;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;

/* renamed from: com.kl.commonbase.views.ecg.ReboundHorizontalScrollView */
public class ReboundHorizontalScrollView extends HorizontalScrollView {
    private static final int ANIM_TIME = 300;
    private static final float MOVE_FACTOR = 0.4f;
    private boolean canPullToLeft = false;
    private boolean canPullToRight = false;
    private View contentView;
    private boolean isMoved = false;
    private Rect originalRect = new Rect();
    private float startX;

    public ReboundHorizontalScrollView(Context context) {
        super(context);
    }

    public ReboundHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            this.contentView = getChildAt(0);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View view = this.contentView;
        if (view != null) {
            this.originalRect.set(view.getLeft(), this.contentView.getTop(), this.contentView.getRight(), this.contentView.getBottom());
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (this.contentView == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            boolean z2 = false;
            if (action != 1) {
                if (action == 2) {
                    if (this.canPullToLeft || this.canPullToRight) {
                        int x = (int) (motionEvent.getX() - this.startX);
                        boolean z3 = this.canPullToLeft;
                        if ((z3 && x > 0) || (((z = this.canPullToRight) && x < 0) || (z && z3))) {
                            z2 = true;
                        }
                        if (z2) {
                            int i = (int) (((float) x) * MOVE_FACTOR);
                            this.contentView.layout(this.originalRect.left + i, this.originalRect.top, this.originalRect.right + i, this.originalRect.bottom);
                            this.isMoved = true;
                        }
                    } else {
                        this.startX = motionEvent.getX();
                        this.canPullToLeft = isCanPullToLeft();
                        this.canPullToRight = isCanPullToRight();
                    }
                }
            } else if (this.isMoved) {
                TranslateAnimation translateAnimation = new TranslateAnimation((float) this.contentView.getLeft(), (float) this.originalRect.left, 0.0f, 0.0f);
                translateAnimation.setDuration(300);
                this.contentView.startAnimation(translateAnimation);
                this.contentView.layout(this.originalRect.left, this.originalRect.top, this.originalRect.right, this.originalRect.bottom);
                this.canPullToLeft = false;
                this.canPullToRight = false;
                this.isMoved = false;
            }
        } else {
            this.canPullToLeft = isCanPullToLeft();
            this.canPullToRight = isCanPullToRight();
            this.startX = motionEvent.getX();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private boolean isCanPullToLeft() {
        return getScrollX() == 0 || this.contentView.getWidth() < getWidth() + getScrollX();
    }

    private boolean isCanPullToRight() {
        return this.contentView.getWidth() <= getWidth() + getScrollX();
    }
}
