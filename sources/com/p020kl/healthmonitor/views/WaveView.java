package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.wave.DrawWave;

/* renamed from: com.kl.healthmonitor.views.WaveView */
public class WaveView extends View {
    protected DrawWave mDrawWave;
    private boolean scrollable;

    public WaveView(Context context) {
        super(context);
        initAttrs(context, (AttributeSet) null);
    }

    public WaveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initAttrs(context, attributeSet);
    }

    public WaveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initAttrs(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        DrawWave drawWave = this.mDrawWave;
        if (drawWave != null) {
            drawWave.initWave((float) getWidth(), (float) getHeight());
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DrawWave drawWave = this.mDrawWave;
        if (drawWave != null) {
            drawWave.drawWave(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        DrawWave drawWave;
        if (!this.scrollable || (drawWave = this.mDrawWave) == null) {
            super.onMeasure(i, i2);
        } else {
            setMeasuredDimension(drawWave.getWidthMeasureSpec(), i2);
        }
    }

    public <T> void setDrawWave(DrawWave<T> drawWave) {
        this.mDrawWave = drawWave;
        drawWave.setView(this);
        requestLayout();
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.WaveView);
        this.scrollable = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
    }
}
