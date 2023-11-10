package com.linktop.whealthService.task;

import com.linktop.utils.BleDevLog;
import com.neurosky.AlgoSdk.NskAlgoSdk;
import com.neurosky.AlgoSdk.NskAlgoState;

/* renamed from: com.linktop.whealthService.task.-$$Lambda$EcgTask$0XY65P8qWoxJs5laINhzjblAyPM  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$EcgTask$0XY65P8qWoxJs5laINhzjblAyPM implements NskAlgoSdk.OnStateChangeListener {
    public static final /* synthetic */ $$Lambda$EcgTask$0XY65P8qWoxJs5laINhzjblAyPM INSTANCE = new $$Lambda$EcgTask$0XY65P8qWoxJs5laINhzjblAyPM();

    private /* synthetic */ $$Lambda$EcgTask$0XY65P8qWoxJs5laINhzjblAyPM() {
    }

    public final void onStateChange(int i, int i2) {
        BleDevLog.m141b(EcgTask.TAG, "state:" + new NskAlgoState(i).toString() + ", reason:" + new NskAlgoState(i2).toString());
    }
}
