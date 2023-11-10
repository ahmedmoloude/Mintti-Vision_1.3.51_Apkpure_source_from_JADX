package com.p020kl.vision_ecg.ble;

/* renamed from: com.kl.vision_ecg.ble.IBleOperateCallback */
public interface IBleOperateCallback {
    void bleData(short s, int i);

    void bleData(short s, short s2);

    void bleData(short s, float[] fArr);

    void bleData(short s, short[] sArr);
}
