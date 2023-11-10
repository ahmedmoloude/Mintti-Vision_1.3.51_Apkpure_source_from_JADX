package com.gyf.immersionbar.components;

public interface ImmersionOwner {
    boolean immersionBarEnabled();

    void initImmersionBar();

    void onInvisible();

    void onLazyAfterView();

    void onLazyBeforeView();

    void onVisible();
}
