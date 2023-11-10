package com.androidkun.xtablayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.animation.Interpolator;
import com.androidkun.xtablayout.ValueAnimatorCompat;

class ValueAnimatorCompatImplHoneycombMr1 extends ValueAnimatorCompat.Impl {
    final ValueAnimator mValueAnimator = new ValueAnimator();

    ValueAnimatorCompatImplHoneycombMr1() {
    }

    public void start() {
        this.mValueAnimator.start();
    }

    public boolean isRunning() {
        return this.mValueAnimator.isRunning();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mValueAnimator.setInterpolator(interpolator);
    }

    public void setUpdateListener(final ValueAnimatorCompat.Impl.AnimatorUpdateListenerProxy animatorUpdateListenerProxy) {
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatorUpdateListenerProxy.onAnimationUpdate();
            }
        });
    }

    public void setListener(final ValueAnimatorCompat.Impl.AnimatorListenerProxy animatorListenerProxy) {
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                animatorListenerProxy.onAnimationStart();
            }

            public void onAnimationEnd(Animator animator) {
                animatorListenerProxy.onAnimationEnd();
            }

            public void onAnimationCancel(Animator animator) {
                animatorListenerProxy.onAnimationCancel();
            }
        });
    }

    public void setIntValues(int i, int i2) {
        this.mValueAnimator.setIntValues(new int[]{i, i2});
    }

    public int getAnimatedIntValue() {
        return ((Integer) this.mValueAnimator.getAnimatedValue()).intValue();
    }

    public void setFloatValues(float f, float f2) {
        this.mValueAnimator.setFloatValues(new float[]{f, f2});
    }

    public float getAnimatedFloatValue() {
        return ((Float) this.mValueAnimator.getAnimatedValue()).floatValue();
    }

    public void setDuration(int i) {
        this.mValueAnimator.setDuration((long) i);
    }

    public void cancel() {
        this.mValueAnimator.cancel();
    }

    public float getAnimatedFraction() {
        return this.mValueAnimator.getAnimatedFraction();
    }

    public void end() {
        this.mValueAnimator.end();
    }

    public long getDuration() {
        return this.mValueAnimator.getDuration();
    }
}
