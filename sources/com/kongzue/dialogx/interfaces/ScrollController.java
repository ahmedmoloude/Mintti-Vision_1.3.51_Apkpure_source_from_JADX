package com.kongzue.dialogx.interfaces;

public interface ScrollController {
    int getScrollDistance();

    boolean isCanScroll();

    boolean isLockScroll();

    void lockScroll(boolean z);
}
