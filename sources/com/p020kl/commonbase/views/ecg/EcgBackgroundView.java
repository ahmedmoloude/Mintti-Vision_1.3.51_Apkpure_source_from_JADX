package com.p020kl.commonbase.views.ecg;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import com.p020kl.commonbase.C1544R;

/* renamed from: com.kl.commonbase.views.ecg.EcgBackgroundView */
public class EcgBackgroundView extends View {
    public static final int DATA_PER_SEC = 512;
    private static final float mm2Inches = 0.03937f;
    public static float totalLattices;

    /* renamed from: xS */
    public static float f852xS;
    private int mLargeGridColor;
    private Paint mPaintLargeGrid;
    private Paint mPaintSmallGrid;
    private int mSmallGirdColor;
    private float mViewHalfHeight;
    private float mViewHalfWidth;
    private float mViewHeight;
    private float mViewWidth;
    private int sizeX;
    private int sizeY;

    public EcgBackgroundView(Context context) {
        super(context);
        init();
    }

    public EcgBackgroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initTypedArray(context, attributeSet);
        init();
    }

    public EcgBackgroundView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initTypedArray(context, attributeSet);
        init();
    }

    public EcgBackgroundView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initTypedArray(context, attributeSet);
        init();
    }

    private void initTypedArray(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1544R.styleable.EcgBackgroundView);
        this.mSmallGirdColor = obtainStyledAttributes.getColor(C1544R.styleable.EcgBackgroundView_smallGridColor, -11249817);
        this.mLargeGridColor = obtainStyledAttributes.getColor(C1544R.styleable.EcgBackgroundView_largeGridColor, -15657174);
        obtainStyledAttributes.recycle();
    }

    private void init() {
        Paint paint = new Paint();
        this.mPaintLargeGrid = paint;
        paint.setAntiAlias(true);
        this.mPaintLargeGrid.setColor(this.mLargeGridColor);
        this.mPaintLargeGrid.setStrokeWidth(2.0f);
        this.mPaintLargeGrid.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        this.mPaintSmallGrid = paint2;
        paint2.setAntiAlias(true);
        this.mPaintSmallGrid.setColor(this.mSmallGirdColor);
        this.mPaintSmallGrid.setStrokeWidth(1.0f);
        this.mPaintSmallGrid.setStyle(Paint.Style.FILL);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i <= this.sizeX; i++) {
            float f = this.mViewHalfWidth;
            float f2 = (float) i;
            float f3 = f852xS;
            float f4 = f - (f2 * f3);
            float f5 = f + (f2 * f3);
            if (i % 5 == 0) {
                canvas.drawLine(f4, 0.0f, f4, this.mViewHeight, this.mPaintLargeGrid);
                if (i > 0) {
                    canvas.drawLine(f5, 0.0f, f5, this.mViewHeight, this.mPaintLargeGrid);
                }
            } else {
                canvas.drawLine(f4, 0.0f, f4, this.mViewHeight, this.mPaintSmallGrid);
                canvas.drawLine(f5, 0.0f, f5, this.mViewHeight, this.mPaintSmallGrid);
            }
        }
        for (int i2 = 0; i2 <= this.sizeY; i2++) {
            float f6 = this.mViewHalfHeight;
            float f7 = (float) i2;
            float f8 = f852xS;
            float f9 = f6 - (f7 * f8);
            float f10 = f6 + (f7 * f8);
            if (i2 % 5 == 0) {
                canvas.drawLine(0.0f, f9, this.mViewWidth, f9, this.mPaintLargeGrid);
                if (i2 > 0) {
                    canvas.drawLine(0.0f, f10, this.mViewWidth, f10, this.mPaintLargeGrid);
                }
            } else {
                canvas.drawLine(0.0f, f9, this.mViewWidth, f9, this.mPaintSmallGrid);
                canvas.drawLine(0.0f, f10, this.mViewWidth, f10, this.mPaintSmallGrid);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        initDrawLatticeParams();
        super.onLayout(z, i, i2, i3, i4);
    }

    private void initDrawLatticeParams() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float f = displayMetrics.density;
        this.mViewWidth = (float) getWidth();
        float height = (float) getHeight();
        this.mViewHeight = height;
        this.mViewHalfWidth = this.mViewWidth / 2.0f;
        this.mViewHalfHeight = height / 2.0f;
        float f2 = displayMetrics.xdpi;
        float f3 = displayMetrics.ydpi;
        float f4 = f2 - 160.0f;
        float f5 = f3 - 160.0f;
        float f6 = 1.0f;
        if (f4 > -1.0f && f4 < 1.0f && f5 > -1.0f && f5 < 1.0f) {
            double d = (double) f;
            if (d == 2.0d) {
                f6 = 1.7f;
            }
            if (d == 3.0d) {
                f6 = 2.52f;
            }
        }
        float f7 = this.mViewWidth;
        float f8 = (f7 / (f2 * f6)) / mm2Inches;
        float f9 = (this.mViewHeight / (f3 * f6)) / mm2Inches;
        float f10 = f8 * 0.5f;
        int i = (int) f10;
        this.sizeX = i;
        if (f10 - ((float) i) >= 0.5f) {
            this.sizeX = i + 1;
        }
        double d2 = (double) (f9 * 0.5f);
        int i2 = (int) d2;
        this.sizeY = i2;
        if (d2 - ((double) i2) >= 0.5d) {
            this.sizeY = i2 + 1;
        }
        float f11 = f7 / f8;
        f852xS = f11;
        totalLattices = f7 / f11;
    }
}
