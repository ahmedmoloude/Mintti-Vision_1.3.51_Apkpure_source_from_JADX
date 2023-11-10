package com.p020kl.commonbase.views.ecg;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.p020kl.commonbase.C1544R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.commonbase.views.ecg.PdfEcgWaveView */
public class PdfEcgWaveView extends View {
    public static int DATA_PRE_SECOND = 512;
    private int allDataSize;
    private final List<Float> dataList = new ArrayList();
    private float dataSpacing;
    private float gain = 1.0f;
    private float mViewHalfHeight;
    private float mViewWidth;
    private Paint mWavePaint;
    private int mWavePaintColor = -1;
    private float mWaveStrokeWidth = 1.0f;
    private int pagerSpeed = 1;
    private boolean scrollable = false;
    private float totalLattices;

    /* renamed from: xS */
    private float f854xS;

    private float calcRealMv(float f) {
        return (float) ((((((double) f) * 12.247d) / 9.5d) / 8.0d) / 1000.0d);
    }

    public PdfEcgWaveView(Context context) {
        super(context);
        initPaint();
    }

    public PdfEcgWaveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initTypedArray(context, attributeSet);
        initPaint();
    }

    public PdfEcgWaveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initTypedArray(context, attributeSet);
        initPaint();
    }

    public PdfEcgWaveView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initTypedArray(context, attributeSet);
        initPaint();
    }

    private void initTypedArray(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1544R.styleable.EcgWaveView);
        this.mWavePaintColor = obtainStyledAttributes.getColor(C1544R.styleable.EcgWaveView_waveColor, -1);
        this.mWaveStrokeWidth = obtainStyledAttributes.getDimension(C1544R.styleable.EcgWaveView_waveStrokeWidth, 1.0f);
        this.scrollable = obtainStyledAttributes.getBoolean(C1544R.styleable.EcgWaveView_scrollable, false);
        obtainStyledAttributes.recycle();
    }

    private void initPaint() {
        Paint paint = new Paint();
        this.mWavePaint = paint;
        paint.setColor(this.mWavePaintColor);
        this.mWavePaint.setAntiAlias(true);
        this.mWavePaint.setStrokeWidth(2.0f);
        this.mWavePaint.setStyle(Paint.Style.FILL);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        this.mViewWidth = (float) getWidth();
        this.mViewHalfHeight = ((float) getHeight()) / 2.0f;
        Log.d("列表长度", "dataSpacing" + this.dataSpacing);
        super.onSizeChanged(i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.scrollable) {
            this.f854xS = EcgBackgroundView.f852xS;
            float f = EcgBackgroundView.totalLattices;
            this.totalLattices = f;
            float f2 = ((float) DATA_PRE_SECOND) / (((float) this.pagerSpeed) * 25.0f);
            this.allDataSize = (int) (f * f2);
            this.dataSpacing = this.f854xS / f2;
            int size = (int) (((float) (this.dataList.size() + 2)) * this.dataSpacing);
            Log.d("列表长度", size + "dataSpacing" + this.dataSpacing);
            setMeasuredDimension(size, i2);
            return;
        }
        super.onMeasure(i, i2);
    }

    public Float getDataSpacing() {
        return Float.valueOf(this.dataSpacing);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int size = this.dataList.size();
        if (size >= 2) {
            int i = 0;
            while (i < size - 1) {
                float pointX = getPointX(i);
                float pointY = getPointY(i);
                i++;
                canvas.drawLine(pointX, pointY, getPointX(i), getPointY(i), this.mWavePaint);
            }
        }
        super.onDraw(canvas);
    }

    private float getPointX(int i) {
        if (this.scrollable) {
            return this.dataSpacing * ((float) i);
        }
        return this.mViewWidth - (this.dataSpacing * ((float) ((this.dataList.size() - 1) - i)));
    }

    private float getPointY(int i) {
        try {
            if (DATA_PRE_SECOND == 512) {
                return (float) (((double) this.mViewHalfHeight) + (((((((double) this.dataList.get(i).floatValue()) * 18.3d) / 128.0d) * ((double) this.f854xS)) / 100.0d) * ((double) this.gain)));
            }
            float calcRealMv = calcRealMv(this.dataList.get(i).floatValue());
            return this.mViewHalfHeight + (calcRealMv * this.f854xS * this.gain * 10.0f);
        } catch (IndexOutOfBoundsException unused) {
            return getPointX(i - 1);
        }
    }

    public synchronized void preparePoint(float f) {
        this.dataList.add(Float.valueOf(f));
        if (this.dataList.size() > this.allDataSize) {
            this.dataList.remove(0);
        }
        postInvalidate();
    }

    public void preparePoints(List<Float> list) {
        this.dataList.clear();
        this.dataList.addAll(list);
        requestLayout();
    }

    public void clear() {
        this.dataList.clear();
        postInvalidate();
    }

    public void setGain(float f) {
        this.gain = f;
        if (!this.dataList.isEmpty()) {
            postInvalidate();
        }
    }

    public void setPagerSpeed(int i) {
        this.pagerSpeed = i;
        float f = ((float) DATA_PRE_SECOND) / (((float) i) * 25.0f);
        this.allDataSize = (int) (this.totalLattices * f);
        this.dataSpacing = this.f854xS / f;
        if (!this.dataList.isEmpty()) {
            postInvalidate();
        }
    }
}
