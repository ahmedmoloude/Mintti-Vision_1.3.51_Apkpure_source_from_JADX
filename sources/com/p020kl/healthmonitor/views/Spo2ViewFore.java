package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* renamed from: com.kl.healthmonitor.views.Spo2ViewFore */
public class Spo2ViewFore extends View {
    private Paint mPaint;

    public Spo2ViewFore(Context context) {
        this(context, (AttributeSet) null);
    }

    public Spo2ViewFore(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Spo2ViewFore(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(0);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
