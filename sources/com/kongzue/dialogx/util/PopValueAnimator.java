package com.kongzue.dialogx.util;

import android.animation.ValueAnimator;

public class PopValueAnimator extends ValueAnimator {
    float endValue;
    float startValue;

    public static PopValueAnimator ofFloat(float... fArr) {
        PopValueAnimator popValueAnimator = new PopValueAnimator();
        popValueAnimator.setFloatValues(fArr);
        return popValueAnimator;
    }

    public void setFloatValues(float... fArr) {
        if (fArr.length > 1) {
            this.startValue = fArr[0];
            this.endValue = fArr[fArr.length - 1];
        }
        super.setFloatValues(fArr);
    }

    public float getStartValue() {
        return this.startValue;
    }

    public float getEndValue() {
        return this.endValue;
    }
}
