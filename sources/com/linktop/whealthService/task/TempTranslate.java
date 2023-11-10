package com.linktop.whealthService.task;

public final class TempTranslate {
    static {
        System.loadLibrary("temperature");
    }

    public static native double getBodyTemp(double d, double d2);
}
