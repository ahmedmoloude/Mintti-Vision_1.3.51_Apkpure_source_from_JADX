package com.p020kl.healthmonitor.wave;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.p020kl.healthmonitor.views.WaveView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.wave.DrawWave */
public abstract class DrawWave<T> {
    protected float allDataSize;
    protected final List<T> dataList = new ArrayList();
    protected View view;

    public abstract void drawWave(Canvas canvas);

    public int getWidthMeasureSpec() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public abstract float getX(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract float getY(T t);

    public abstract void initWave(float f, float f2);

    public void addData(T t) {
        this.dataList.add(t);
        if (((float) this.dataList.size()) > this.allDataSize) {
            this.dataList.remove(0);
        }
        View view2 = this.view;
        if (view2 != null) {
            view2.postInvalidate();
        }
    }

    public void addDataList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            this.dataList.addAll(list);
            View view2 = this.view;
            if (view2 != null) {
                view2.requestLayout();
            }
        }
    }

    public int getDataSize() {
        return this.dataList.size();
    }

    public void clear() {
        this.dataList.clear();
        View view2 = this.view;
        if (view2 != null) {
            view2.postInvalidate();
        }
    }

    /* access modifiers changed from: protected */
    public Paint newPaint(int i, float f) {
        Paint paint = new Paint();
        paint.setColor(i);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(f);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    public void setView(WaveView waveView) {
        this.view = waveView;
    }
}
