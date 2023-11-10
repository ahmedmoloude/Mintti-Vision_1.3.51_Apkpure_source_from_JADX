package com.p020kl.healthmonitor.wave;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;

/* renamed from: com.kl.healthmonitor.wave.PPGDrawWave */
public class PPGDrawWave extends DrawWave<Integer> {
    private static final int X_INTERVAL = 2;
    private static final int waveColor = -12272463;
    private static final float waveStrokeWidth = 2.0f;
    private float dataMax;
    private float dataMin;

    /* renamed from: dp */
    private float f904dp;
    private float mViewHeight;
    private float mViewWidth;
    private Paint mWavePaint = newPaint(waveColor, 2.0f);

    public void initWave(float f, float f2) {
        this.mViewWidth = f;
        this.mViewHeight = f2;
        this.allDataSize = f / 2.0f;
    }

    public void clear() {
        super.clear();
        this.dataMin = 0.0f;
        this.dataMax = 0.0f;
        this.f904dp = 0.0f;
    }

    public void drawWave(Canvas canvas) {
        Integer num;
        Object obj;
        ArrayList arrayList = new ArrayList(this.dataList);
        int size = arrayList.size();
        if (size >= 2) {
            int i = 0;
            float intValue = (float) ((Integer) arrayList.get(0)).intValue();
            this.dataMin = intValue;
            this.dataMax = intValue;
            for (int i2 = 0; i2 < size; i2++) {
                try {
                    float intValue2 = (float) ((Integer) arrayList.get(i2)).intValue();
                    if (intValue2 < this.dataMin) {
                        this.dataMin = intValue2;
                    }
                    if (intValue2 > this.dataMax) {
                        this.dataMax = intValue2;
                    }
                } catch (NullPointerException e) {
                    e.fillInStackTrace();
                }
            }
            float f = this.dataMax - this.dataMin;
            float f2 = this.mViewHeight;
            float f3 = f / (f2 - ((f2 / 10.0f) * 2.0f));
            this.f904dp = f3;
            if (f3 == 0.0f) {
                this.f904dp = 1.0f;
            }
            while (i < size - 1) {
                try {
                    num = (Integer) arrayList.get(i);
                } catch (IndexOutOfBoundsException unused) {
                    num = (Integer) arrayList.get(i - 1);
                }
                int i3 = i + 1;
                try {
                    obj = arrayList.get(i3);
                } catch (IndexOutOfBoundsException unused2) {
                    obj = arrayList.get(i);
                }
                Canvas canvas2 = canvas;
                canvas2.drawLine(getX(i, size), getY(num), getX(i3, size), getY((Integer) obj), this.mWavePaint);
                i = i3;
            }
        }
    }

    /* access modifiers changed from: protected */
    public float getX(int i, int i2) {
        try {
            return this.mViewWidth - ((float) (((i2 - 1) - i) * 2));
        } catch (NullPointerException unused) {
            return 0.0f;
        }
    }

    /* access modifiers changed from: protected */
    public float getY(Integer num) {
        try {
            float f = this.mViewHeight;
            return (f - (f / 10.0f)) - ((((float) num.intValue()) - this.dataMin) / this.f904dp);
        } catch (NullPointerException unused) {
            return 0.0f;
        }
    }
}
