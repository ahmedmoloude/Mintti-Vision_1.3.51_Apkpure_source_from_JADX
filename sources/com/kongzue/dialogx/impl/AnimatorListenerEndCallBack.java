package com.kongzue.dialogx.impl;

import android.animation.Animator;

public abstract class AnimatorListenerEndCallBack implements Animator.AnimatorListener {
    public void onAnimationCancel(Animator animator) {
    }

    public abstract void onAnimationEnd(Animator animator);

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
    }
}
