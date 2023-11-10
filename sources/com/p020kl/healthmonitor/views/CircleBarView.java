package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.CircleBarView */
public class CircleBarView extends View {
    private float barWidth;
    private Paint bgPaint;
    private CircleBarAnim circleBarAnim;
    /* access modifiers changed from: private */
    public float curProgress;
    private int defaultSize;
    private RectF mRectF;
    /* access modifiers changed from: private */
    public float maxProgress;
    private Paint progressPaint;
    /* access modifiers changed from: private */
    public float progressSweepAngle;
    private float startAngle;
    /* access modifiers changed from: private */
    public float sweepAngle;

    public CircleBarView(Context context) {
        this(context, (AttributeSet) null);
    }

    public CircleBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.circleBarAnim = new CircleBarAnim();
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.CircleBarView);
        int color = obtainStyledAttributes.getColor(0, getResources().getColor(C1624R.C1626color.circle_bar));
        int color2 = obtainStyledAttributes.getColor(2, getResources().getColor(C1624R.C1626color.circle_bg));
        this.startAngle = obtainStyledAttributes.getDimension(3, -90.0f);
        this.sweepAngle = obtainStyledAttributes.getDimension(4, 360.0f);
        this.barWidth = obtainStyledAttributes.getDimension(1, 15.0f);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.progressPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.progressPaint.setColor(color);
        this.progressPaint.setAntiAlias(true);
        this.progressPaint.setStrokeWidth(this.barWidth);
        this.progressPaint.setStrokeCap(Paint.Cap.ROUND);
        Paint paint2 = new Paint();
        this.bgPaint = paint2;
        paint2.setAntiAlias(true);
        this.bgPaint.setStrokeWidth(this.barWidth);
        this.bgPaint.setStyle(Paint.Style.STROKE);
        this.bgPaint.setColor(color2);
        this.mRectF = new RectF();
        this.defaultSize = SizeUtils.dp2px(100.0f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(this.mRectF, this.startAngle, this.sweepAngle, false, this.bgPaint);
        canvas.drawArc(this.mRectF, this.startAngle, this.progressSweepAngle, false, this.progressPaint);
    }

    /* renamed from: com.kl.healthmonitor.views.CircleBarView$CircleBarAnim */
    public class CircleBarAnim extends Animation {
        public CircleBarAnim() {
        }

        /* access modifiers changed from: protected */
        public void applyTransformation(float f, Transformation transformation) {
            super.applyTransformation(f, transformation);
            CircleBarView circleBarView = CircleBarView.this;
            float unused = circleBarView.progressSweepAngle = f * circleBarView.sweepAngle * (CircleBarView.this.curProgress / CircleBarView.this.maxProgress);
            CircleBarView.this.postInvalidate();
        }
    }

    public void setMaxProgress(float f) {
        this.maxProgress = f;
    }

    public void setProgressNum(long j, float f, int i) {
        this.circleBarAnim.setDuration(j);
        this.curProgress = f;
        this.progressPaint.setColor(getResources().getColor(i));
        startAnimation(this.circleBarAnim);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int min = Math.min(measureSize(this.defaultSize, i), measureSize(this.defaultSize, i2));
        setMeasuredDimension(min, min);
        float f = (float) min;
        float f2 = this.barWidth;
        if (f >= f2 * 2.0f) {
            this.mRectF.set(f2 / 2.0f, f2 / 2.0f, f - (f2 / 2.0f), f - (f2 / 2.0f));
        }
    }

    private int measureSize(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            return size;
        }
        return mode == Integer.MIN_VALUE ? Math.min(i, size) : i;
    }
}
