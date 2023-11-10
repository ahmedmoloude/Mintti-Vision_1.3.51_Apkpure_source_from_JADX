package com.kongzue.dialogx.style.views;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.interfaces.ProgressViewInterface;
import com.kongzue.dialogx.iostheme.C2083R;

public class ProgressView extends View implements ProgressViewInterface {
    public static final int STATUS_ERROR = 3;
    public static final int STATUS_LOADING = 0;
    public static final int STATUS_PROGRESSING = 4;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_WARNING = 2;
    private int color = -1;
    /* access modifiers changed from: private */
    public float currentRotateDegrees;
    private ValueAnimator followAnimator;
    /* access modifiers changed from: private */
    public float followRotateDegrees;
    private float halfSweepA;
    private float halfSweepAMaxValue = 180.0f;
    private float halfSweepAMinValue = 80.0f;
    private TimeInterpolator interpolator;
    private boolean isInited = false;
    private boolean isLightMode;
    private int line1X = 0;
    private int line1Y = 0;
    private int line2X = 0;
    private int line2Y = 0;
    private float mCenterX;
    private float mCenterY;
    Paint mPaint = new Paint();
    private float mRadius = 100.0f;
    private boolean noShowLoading;
    protected float oldAnimAngle;
    private RectF oval;
    private Rect ovalInt;
    private ValueAnimator rotateAnimator;
    private int status = 0;
    private int successStep = 0;
    private Runnable tickShowRunnable;
    private int tickStep = 0;
    private Runnable waitProgressingRunnable;
    private int width = dip2px(2.0f);

    public ProgressView(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    public ProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public ProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    public boolean isLightMode() {
        return this.isLightMode;
    }

    public ProgressView setLightMode(boolean z) {
        this.isLightMode = z;
        return this;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00d4, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void init(android.util.AttributeSet r9) {
        /*
            r8 = this;
            java.lang.Class<com.kongzue.dialogx.style.views.ProgressView> r0 = com.kongzue.dialogx.style.views.ProgressView.class
            monitor-enter(r0)
            boolean r1 = r8.isInited     // Catch:{ all -> 0x00d5 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x00d5 }
            return
        L_0x0009:
            r1 = 1
            r8.isInited = r1     // Catch:{ all -> 0x00d5 }
            r2 = 1073741824(0x40000000, float:2.0)
            if (r9 == 0) goto L_0x0033
            android.content.Context r3 = r8.getContext()     // Catch:{ all -> 0x00d5 }
            int[] r4 = com.kongzue.dialogx.iostheme.C2083R.styleable.ProgressView     // Catch:{ all -> 0x00d5 }
            android.content.res.TypedArray r9 = r3.obtainStyledAttributes(r9, r4)     // Catch:{ all -> 0x00d5 }
            int r3 = com.kongzue.dialogx.iostheme.C2083R.styleable.ProgressView_progressStrokeWidth     // Catch:{ all -> 0x00d5 }
            int r4 = r8.dip2px(r2)     // Catch:{ all -> 0x00d5 }
            int r3 = r9.getDimensionPixelSize(r3, r4)     // Catch:{ all -> 0x00d5 }
            r8.width = r3     // Catch:{ all -> 0x00d5 }
            int r3 = com.kongzue.dialogx.iostheme.C2083R.styleable.ProgressView_progressStrokeColor     // Catch:{ all -> 0x00d5 }
            int r4 = r8.color     // Catch:{ all -> 0x00d5 }
            int r3 = r9.getDimensionPixelSize(r3, r4)     // Catch:{ all -> 0x00d5 }
            r8.color = r3     // Catch:{ all -> 0x00d5 }
            r9.recycle()     // Catch:{ all -> 0x00d5 }
        L_0x0033:
            android.graphics.Paint r9 = r8.mPaint     // Catch:{ all -> 0x00d5 }
            r9.setAntiAlias(r1)     // Catch:{ all -> 0x00d5 }
            android.graphics.Paint r9 = r8.mPaint     // Catch:{ all -> 0x00d5 }
            android.graphics.Paint$Style r3 = android.graphics.Paint.Style.STROKE     // Catch:{ all -> 0x00d5 }
            r9.setStyle(r3)     // Catch:{ all -> 0x00d5 }
            android.graphics.Paint r9 = r8.mPaint     // Catch:{ all -> 0x00d5 }
            int r3 = r8.width     // Catch:{ all -> 0x00d5 }
            float r3 = (float) r3     // Catch:{ all -> 0x00d5 }
            r9.setStrokeWidth(r3)     // Catch:{ all -> 0x00d5 }
            android.graphics.Paint r9 = r8.mPaint     // Catch:{ all -> 0x00d5 }
            android.graphics.Paint$Cap r3 = android.graphics.Paint.Cap.ROUND     // Catch:{ all -> 0x00d5 }
            r9.setStrokeCap(r3)     // Catch:{ all -> 0x00d5 }
            android.graphics.Paint r9 = r8.mPaint     // Catch:{ all -> 0x00d5 }
            int r3 = r8.color     // Catch:{ all -> 0x00d5 }
            r9.setColor(r3)     // Catch:{ all -> 0x00d5 }
            int r9 = r8.color     // Catch:{ all -> 0x00d5 }
            r3 = 0
            r4 = -1
            if (r9 == r4) goto L_0x005d
            r9 = 1
            goto L_0x005e
        L_0x005d:
            r9 = 0
        L_0x005e:
            r8.isLightMode = r9     // Catch:{ all -> 0x00d5 }
            boolean r9 = r8.isInEditMode()     // Catch:{ all -> 0x00d5 }
            if (r9 != 0) goto L_0x00d3
            float r9 = r8.halfSweepAMaxValue     // Catch:{ all -> 0x00d5 }
            float r5 = r8.halfSweepAMinValue     // Catch:{ all -> 0x00d5 }
            float r9 = r9 - r5
            float r9 = r9 / r2
            r8.halfSweepA = r9     // Catch:{ all -> 0x00d5 }
            r9 = 2
            float[] r2 = new float[r9]     // Catch:{ all -> 0x00d5 }
            r5 = 0
            r2[r3] = r5     // Catch:{ all -> 0x00d5 }
            r6 = 1090519040(0x41000000, float:8.0)
            r2[r1] = r6     // Catch:{ all -> 0x00d5 }
            android.animation.ValueAnimator r2 = android.animation.ValueAnimator.ofFloat(r2)     // Catch:{ all -> 0x00d5 }
            r8.rotateAnimator = r2     // Catch:{ all -> 0x00d5 }
            r6 = 1000(0x3e8, double:4.94E-321)
            r2.setDuration(r6)     // Catch:{ all -> 0x00d5 }
            android.animation.ValueAnimator r2 = r8.rotateAnimator     // Catch:{ all -> 0x00d5 }
            android.view.animation.LinearInterpolator r6 = new android.view.animation.LinearInterpolator     // Catch:{ all -> 0x00d5 }
            r6.<init>()     // Catch:{ all -> 0x00d5 }
            r2.setInterpolator(r6)     // Catch:{ all -> 0x00d5 }
            android.animation.ValueAnimator r2 = r8.rotateAnimator     // Catch:{ all -> 0x00d5 }
            r2.setRepeatCount(r4)     // Catch:{ all -> 0x00d5 }
            android.animation.ValueAnimator r2 = r8.rotateAnimator     // Catch:{ all -> 0x00d5 }
            com.kongzue.dialogx.style.views.ProgressView$1 r6 = new com.kongzue.dialogx.style.views.ProgressView$1     // Catch:{ all -> 0x00d5 }
            r6.<init>()     // Catch:{ all -> 0x00d5 }
            r2.addUpdateListener(r6)     // Catch:{ all -> 0x00d5 }
            float[] r9 = new float[r9]     // Catch:{ all -> 0x00d5 }
            r9[r3] = r5     // Catch:{ all -> 0x00d5 }
            r2 = 1136033792(0x43b68000, float:365.0)
            r9[r1] = r2     // Catch:{ all -> 0x00d5 }
            android.animation.ValueAnimator r9 = android.animation.ValueAnimator.ofFloat(r9)     // Catch:{ all -> 0x00d5 }
            r8.followAnimator = r9     // Catch:{ all -> 0x00d5 }
            r1 = 1500(0x5dc, double:7.41E-321)
            r9.setDuration(r1)     // Catch:{ all -> 0x00d5 }
            android.animation.ValueAnimator r9 = r8.followAnimator     // Catch:{ all -> 0x00d5 }
            android.view.animation.LinearInterpolator r1 = new android.view.animation.LinearInterpolator     // Catch:{ all -> 0x00d5 }
            r1.<init>()     // Catch:{ all -> 0x00d5 }
            r9.setInterpolator(r1)     // Catch:{ all -> 0x00d5 }
            android.animation.ValueAnimator r9 = r8.followAnimator     // Catch:{ all -> 0x00d5 }
            r9.setRepeatCount(r4)     // Catch:{ all -> 0x00d5 }
            android.animation.ValueAnimator r9 = r8.followAnimator     // Catch:{ all -> 0x00d5 }
            com.kongzue.dialogx.style.views.ProgressView$2 r1 = new com.kongzue.dialogx.style.views.ProgressView$2     // Catch:{ all -> 0x00d5 }
            r1.<init>()     // Catch:{ all -> 0x00d5 }
            r9.addUpdateListener(r1)     // Catch:{ all -> 0x00d5 }
            android.animation.ValueAnimator r9 = r8.followAnimator     // Catch:{ all -> 0x00d5 }
            r9.start()     // Catch:{ all -> 0x00d5 }
            android.animation.ValueAnimator r9 = r8.rotateAnimator     // Catch:{ all -> 0x00d5 }
            r9.start()     // Catch:{ all -> 0x00d5 }
        L_0x00d3:
            monitor-exit(r0)     // Catch:{ all -> 0x00d5 }
            return
        L_0x00d5:
            r9 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00d5 }
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.style.views.ProgressView.init(android.util.AttributeSet):void");
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        int dip2px = dip2px(30.0f);
        this.mCenterX = (((float) i) * 1.0f) / 2.0f;
        this.mCenterY = (((float) i2) * 1.0f) / 2.0f;
        this.mRadius = (float) ((dip2px / 2) - (this.width / 2));
        float f = this.mCenterX;
        float f2 = this.mRadius;
        float f3 = this.mCenterY;
        this.oval = new RectF(f - f2, f3 - f2, f + f2, f3 + f2);
        float f4 = this.mCenterX;
        float f5 = this.mRadius;
        float f6 = this.mCenterY;
        this.ovalInt = new Rect((int) (f4 - f5), (int) (f6 - f5), (int) (f4 + f5), (int) (f6 + f5));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Runnable runnable;
        if (isInEditMode()) {
            canvas.drawArc(this.oval, 0.0f, 365.0f, false, this.mPaint);
        } else if (this.noShowLoading) {
            this.successStep = 2;
            drawDoneMark(this.status, canvas);
        } else {
            Math.sin(Math.toRadians((double) this.followRotateDegrees));
            int i = this.status;
            if (i == 0) {
                Bitmap loadingBitmap = getLoadingBitmap();
                canvas.drawBitmap(getLoadingBitmap(), new Rect(0, 0, loadingBitmap.getWidth(), loadingBitmap.getHeight()), this.ovalInt, (Paint) null);
            } else if (i == 1 || i == 2 || i == 3) {
                drawDoneMark(i, canvas);
            } else if (i == 4) {
                canvas.drawArc(this.oval, -90.0f, this.currentRotateDegrees, false, this.mPaint);
                if (this.currentRotateDegrees == 365.0f && (runnable = this.waitProgressingRunnable) != null) {
                    runnable.run();
                    this.waitProgressingRunnable = null;
                }
            }
        }
    }

    private Bitmap getLoadingBitmap() {
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), this.isLightMode ? C2083R.mipmap.img_progress_ios_light : C2083R.mipmap.img_progress_ios_dark);
        Matrix matrix = new Matrix();
        matrix.setRotate((float) (((int) this.currentRotateDegrees) * 45));
        Bitmap createBitmap = Bitmap.createBitmap(decodeResource, 0, 0, decodeResource.getWidth(), decodeResource.getHeight(), matrix, false);
        int width2 = (createBitmap.getWidth() / 2) - (decodeResource.getWidth() / 2);
        int height = (createBitmap.getHeight() / 2) - (decodeResource.getHeight() / 2);
        int width3 = decodeResource.getWidth();
        int height2 = decodeResource.getHeight();
        int i = width2 < 0 ? 0 : width2;
        int i2 = height < 0 ? 0 : height;
        int width4 = i + width3 > createBitmap.getWidth() ? createBitmap.getWidth() - i : width3;
        if (i2 + height2 > createBitmap.getHeight()) {
            height2 = createBitmap.getHeight() - i2;
        }
        return Bitmap.createBitmap(createBitmap, i, i2, width4, height2, (Matrix) null, false);
    }

    private void drawDoneMark(int i, Canvas canvas) {
        TimeInterpolator interpolator2 = this.rotateAnimator.getInterpolator();
        TimeInterpolator timeInterpolator = this.interpolator;
        if (interpolator2 != timeInterpolator) {
            this.rotateAnimator.setInterpolator(timeInterpolator);
        }
        Runnable runnable = this.tickShowRunnable;
        if (runnable != null) {
            runnable.run();
            if (DialogX.useHaptic) {
                performHapticFeedback(0);
            }
            this.tickShowRunnable = null;
        }
        if (i == 1) {
            showSuccessTick(canvas);
        } else if (i == 2) {
            showWarningTick(canvas);
        } else if (i == 3) {
            showErrorTick(canvas);
        }
    }

    private void showSuccessTick(Canvas canvas) {
        int i;
        float f = this.mCenterX;
        float f2 = this.mRadius;
        int i2 = (int) (((double) f) - (((((double) f2) * 1.5d) * 1.0d) / 2.0d));
        int i3 = (int) (((double) f) - ((((double) f2) * 1.5d) / 10.0d));
        int i4 = (int) (((double) f2) * 1.5d * 0.9900000095367432d);
        int i5 = this.tickStep;
        if (i5 == 0) {
            int i6 = this.line1X;
            if (i2 + i6 < i3) {
                this.line1X = i6 + 2;
                this.line1Y += 2;
            } else {
                this.line2X = i6;
                this.line2Y = this.line1Y;
                this.tickStep = 1;
            }
        } else if (i5 == 1 && (i = this.line2X) < i4) {
            this.line2X = i + 4;
            this.line2Y -= 5;
        }
        float f3 = this.mCenterY;
        canvas.drawLine((float) i2, f3, (float) (this.line1X + i2), f3 + ((float) this.line1Y), this.mPaint);
        float f4 = (float) (this.line1X + i2);
        float f5 = this.mCenterY;
        canvas.drawLine(f4, f5 + ((float) this.line1Y), (float) (i2 + this.line2X), f5 + ((float) this.line2Y), this.mPaint);
        postInvalidateDelayed(1);
    }

    private void showWarningTick(Canvas canvas) {
        int i = (int) this.mCenterX;
        float f = this.mCenterY;
        float f2 = this.mRadius;
        int i2 = (int) (((double) f) - (((((double) f2) * 1.5d) * 1.0d) / 2.0d));
        int i3 = (int) (((double) f) + (((((double) f2) * 1.5d) * 1.0d) / 8.0d));
        int i4 = (int) (((double) f) + (((((double) f2) * 1.5d) * 3.0d) / 7.0d));
        int i5 = this.tickStep;
        if (i5 == 0) {
            int i6 = this.line1Y;
            int i7 = i3 - i2;
            if (i6 < i7) {
                this.line1Y = i6 + 4;
            } else {
                this.line1Y = i7;
                this.tickStep = 1;
            }
        } else if (i5 == 1 && this.line2Y != i4) {
            float f3 = (float) i;
            canvas.drawLine(f3, (float) i4, f3, (float) (i4 + 1), this.mPaint);
        }
        float f4 = (float) i;
        canvas.drawLine(f4, (float) i2, f4, (float) (i2 + this.line1Y), this.mPaint);
        postInvalidateDelayed(this.tickStep == 1 ? 100 : 1);
    }

    private void showErrorTick(Canvas canvas) {
        float f = this.mCenterX;
        float f2 = this.mRadius;
        int i = (int) (((double) f) - (((((double) f2) * 1.5d) * 4.0d) / 10.0d));
        int i2 = (int) (((double) f) + (((((double) f2) * 1.5d) * 4.0d) / 10.0d));
        int i3 = (int) (((double) this.mCenterY) - (((((double) f2) * 1.5d) * 4.0d) / 10.0d));
        int i4 = this.tickStep;
        if (i4 == 0) {
            int i5 = this.line1X;
            if (i2 - i5 > i) {
                this.line1X = i5 + 4;
                this.line1Y += 4;
            } else {
                this.tickStep = 1;
                canvas.drawLine((float) i2, (float) i3, (float) (i2 - i5), (float) (i3 + this.line1Y), this.mPaint);
                postInvalidateDelayed(150);
                return;
            }
        } else if (i4 == 1) {
            int i6 = this.line2X;
            if (i + i6 < i2) {
                this.line2X = i6 + 4;
                this.line2Y += 4;
            }
            canvas.drawLine((float) i, (float) i3, (float) (i + this.line2X), (float) (this.line2Y + i3), this.mPaint);
        }
        canvas.drawLine((float) i2, (float) i3, (float) (i2 - this.line1X), (float) (i3 + this.line1Y), this.mPaint);
        postInvalidateDelayed(1);
    }

    public void success() {
        this.tickStep = 0;
        this.line1X = 0;
        this.line1Y = 0;
        this.line2X = 0;
        this.line2Y = 0;
        this.interpolator = new AccelerateDecelerateInterpolator();
        this.status = 1;
        invalidate();
    }

    public void warning() {
        this.tickStep = 0;
        this.line1X = 0;
        this.line1Y = 0;
        this.line2X = 0;
        this.line2Y = 0;
        this.interpolator = new DecelerateInterpolator(2.0f);
        this.status = 2;
        invalidate();
    }

    public void error() {
        this.tickStep = 0;
        this.line1X = 0;
        this.line1Y = 0;
        this.line2X = 0;
        this.line2Y = 0;
        this.interpolator = new DecelerateInterpolator(2.0f);
        this.status = 3;
        invalidate();
    }

    public void progress(float f) {
        ValueAnimator valueAnimator = this.rotateAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.followAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
        if (this.status != 4) {
            this.currentRotateDegrees = 0.0f;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.currentRotateDegrees, f * 365.0f});
        this.rotateAnimator = ofFloat;
        ofFloat.setDuration(1000);
        this.rotateAnimator.setInterpolator(new DecelerateInterpolator(2.0f));
        this.rotateAnimator.setRepeatCount(0);
        this.rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float unused = ProgressView.this.currentRotateDegrees = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ProgressView.this.invalidate();
            }
        });
        this.rotateAnimator.start();
        this.status = 4;
    }

    public ProgressView whenShowTick(Runnable runnable) {
        this.tickShowRunnable = runnable;
        return this;
    }

    public void loading() {
        this.noShowLoading = false;
        this.oldAnimAngle = 0.0f;
        this.successStep = 0;
        this.line1X = 0;
        this.line1Y = 0;
        this.line2X = 0;
        this.line2Y = 0;
        this.status = 0;
        ValueAnimator valueAnimator = this.rotateAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.followAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
        this.isInited = false;
        init((AttributeSet) null);
    }

    public int getStatus() {
        return this.status;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        ValueAnimator valueAnimator = this.rotateAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.followAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
        super.onDetachedFromWindow();
    }

    public int getStrokeWidth() {
        return this.width;
    }

    public ProgressView setStrokeWidth(int i) {
        this.width = i;
        Paint paint = this.mPaint;
        if (paint != null) {
            paint.setStrokeWidth((float) i);
        }
        return this;
    }

    public int getColor() {
        return this.color;
    }

    public ProgressView setColor(int i) {
        this.color = i;
        Paint paint = this.mPaint;
        if (paint != null) {
            paint.setColor(i);
        }
        return this;
    }

    public void noLoading() {
        this.noShowLoading = true;
    }

    private int dip2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }
}
