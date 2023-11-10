package com.yalantis.ucrop.util;

import android.view.MotionEvent;

public class RotationGestureDetector {
    private static final int INVALID_POINTER_INDEX = -1;

    /* renamed from: fX */
    private float f2002fX;

    /* renamed from: fY */
    private float f2003fY;
    private float mAngle;
    private boolean mIsFirstTouch;
    private OnRotationGestureListener mListener;
    private int mPointerIndex1 = -1;
    private int mPointerIndex2 = -1;

    /* renamed from: sX */
    private float f2004sX;

    /* renamed from: sY */
    private float f2005sY;

    public interface OnRotationGestureListener {
        boolean onRotation(RotationGestureDetector rotationGestureDetector);
    }

    public static class SimpleOnRotationGestureListener implements OnRotationGestureListener {
        public boolean onRotation(RotationGestureDetector rotationGestureDetector) {
            return false;
        }
    }

    public RotationGestureDetector(OnRotationGestureListener onRotationGestureListener) {
        this.mListener = onRotationGestureListener;
    }

    public float getAngle() {
        return this.mAngle;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.f2004sX = motionEvent.getX();
            this.f2005sY = motionEvent.getY();
            this.mPointerIndex1 = motionEvent2.findPointerIndex(motionEvent2.getPointerId(0));
            this.mAngle = 0.0f;
            this.mIsFirstTouch = true;
        } else if (actionMasked == 1) {
            this.mPointerIndex1 = -1;
        } else if (actionMasked != 2) {
            if (actionMasked == 5) {
                this.f2002fX = motionEvent.getX();
                this.f2003fY = motionEvent.getY();
                this.mPointerIndex2 = motionEvent2.findPointerIndex(motionEvent2.getPointerId(motionEvent.getActionIndex()));
                this.mAngle = 0.0f;
                this.mIsFirstTouch = true;
            } else if (actionMasked == 6) {
                this.mPointerIndex2 = -1;
            }
        } else if (!(this.mPointerIndex1 == -1 || this.mPointerIndex2 == -1 || motionEvent.getPointerCount() <= this.mPointerIndex2)) {
            float x = motionEvent2.getX(this.mPointerIndex1);
            float y = motionEvent2.getY(this.mPointerIndex1);
            float x2 = motionEvent2.getX(this.mPointerIndex2);
            float y2 = motionEvent2.getY(this.mPointerIndex2);
            if (this.mIsFirstTouch) {
                this.mAngle = 0.0f;
                this.mIsFirstTouch = false;
            } else {
                calculateAngleBetweenLines(this.f2002fX, this.f2003fY, this.f2004sX, this.f2005sY, x2, y2, x, y);
            }
            OnRotationGestureListener onRotationGestureListener = this.mListener;
            if (onRotationGestureListener != null) {
                onRotationGestureListener.onRotation(this);
            }
            this.f2002fX = x2;
            this.f2003fY = y2;
            this.f2004sX = x;
            this.f2005sY = y;
        }
        return true;
    }

    private float calculateAngleBetweenLines(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        return calculateAngleDelta((float) Math.toDegrees((double) ((float) Math.atan2((double) (f2 - f4), (double) (f - f3)))), (float) Math.toDegrees((double) ((float) Math.atan2((double) (f6 - f8), (double) (f5 - f7)))));
    }

    private float calculateAngleDelta(float f, float f2) {
        float f3 = (f2 % 360.0f) - (f % 360.0f);
        this.mAngle = f3;
        if (f3 < -180.0f) {
            this.mAngle = f3 + 360.0f;
        } else if (f3 > 180.0f) {
            this.mAngle = f3 - 360.0f;
        }
        return this.mAngle;
    }
}
