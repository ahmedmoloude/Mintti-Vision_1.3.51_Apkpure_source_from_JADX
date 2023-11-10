package com.p020kl.healthmonitor.bean;

import com.p020kl.commonbase.utils.ArrayUtils;

/* renamed from: com.kl.healthmonitor.bean.EcgBufferData */
public class EcgBufferData {
    private byte[] datas = new byte[512];
    private int index = 0;

    public void addData(int i) {
        int i2 = this.index;
        byte[] bArr = this.datas;
        if (i2 < bArr.length) {
            ArrayUtils.int2bytes(i, bArr, i2);
        }
        this.index += 4;
    }

    public byte[] getDatas() {
        return this.datas;
    }

    public int getIndex() {
        return this.index;
    }
}
