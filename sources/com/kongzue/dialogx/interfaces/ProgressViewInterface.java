package com.kongzue.dialogx.interfaces;

public interface ProgressViewInterface {
    void error();

    void loading();

    void noLoading();

    void progress(float f);

    ProgressViewInterface setColor(int i);

    void success();

    void warning();

    ProgressViewInterface whenShowTick(Runnable runnable);
}
