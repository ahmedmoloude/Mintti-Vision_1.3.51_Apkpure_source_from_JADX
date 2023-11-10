package com.kongzue.dialogx.util.views;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.interfaces.ProgressViewInterface;

public class ProgressView extends View implements ProgressViewInterface {
    public static long PROGRESSING_ANIMATOR_DURATION = 1000;
    public static final int STATUS_ERROR = 3;
    public static final int STATUS_LOADING = 0;
    public static final int STATUS_PROGRESSING = 4;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_WARNING = 2;
    public static long TIP_ANIMATOR_DURATION = 300;
    private int color = -1;
    /* access modifiers changed from: private */
    public float currentRotateDegrees;
    private ValueAnimator followAnimator;
    /* access modifiers changed from: private */
    public float followRotateDegrees;
    private float halfSweepA;
    private float halfSweepAMaxValue = 180.0f;
    private float halfSweepAMinValue = 80.0f;
    /* access modifiers changed from: private */
    public Interpolator interpolator;
    private boolean isInited = false;
    private int line1X = 0;
    private int line1Y = 0;
    private int line2X = 0;
    private int line2Y = 0;
    private float mCenterX;
    private float mCenterY;
    Paint mPaint = new Paint();
    private float mRadius = 100.0f;
    private boolean noShowLoading;
    private float nowLoadingProgressEndAngle;
    private float nowLoadingProgressValue;
    private RectF oval;
    private ValueAnimator rotateAnimator;
    /* access modifiers changed from: private */
    public int status = 0;
    private int successStep = 0;
    private ValueAnimator tickAnimator;
    /* access modifiers changed from: private */
    public float tickAnimatorValue;
    private Runnable tickShowRunnable;
    Runnable waitArticulationAnimationRunnable;
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

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x00c9, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void init(android.util.AttributeSet r9) {
        /*
            r8 = this;
            java.lang.Class<com.kongzue.dialogx.util.views.ProgressView> r0 = com.kongzue.dialogx.util.views.ProgressView.class
            monitor-enter(r0)
            boolean r1 = r8.isInited     // Catch:{ all -> 0x00ca }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x00ca }
            return
        L_0x0009:
            r1 = 1
            r8.isInited = r1     // Catch:{ all -> 0x00ca }
            r2 = 1073741824(0x40000000, float:2.0)
            if (r9 == 0) goto L_0x0033
            android.content.Context r3 = r8.getContext()     // Catch:{ all -> 0x00ca }
            int[] r4 = com.kongzue.dialogx.C1903R.styleable.ProgressView     // Catch:{ all -> 0x00ca }
            android.content.res.TypedArray r9 = r3.obtainStyledAttributes(r9, r4)     // Catch:{ all -> 0x00ca }
            int r3 = com.kongzue.dialogx.C1903R.styleable.ProgressView_progressStrokeWidth     // Catch:{ all -> 0x00ca }
            int r4 = r8.dip2px(r2)     // Catch:{ all -> 0x00ca }
            int r3 = r9.getDimensionPixelSize(r3, r4)     // Catch:{ all -> 0x00ca }
            r8.width = r3     // Catch:{ all -> 0x00ca }
            int r3 = com.kongzue.dialogx.C1903R.styleable.ProgressView_progressStrokeColor     // Catch:{ all -> 0x00ca }
            int r4 = r8.color     // Catch:{ all -> 0x00ca }
            int r3 = r9.getDimensionPixelSize(r3, r4)     // Catch:{ all -> 0x00ca }
            r8.color = r3     // Catch:{ all -> 0x00ca }
            r9.recycle()     // Catch:{ all -> 0x00ca }
        L_0x0033:
            android.graphics.Paint r9 = r8.mPaint     // Catch:{ all -> 0x00ca }
            r9.setAntiAlias(r1)     // Catch:{ all -> 0x00ca }
            android.graphics.Paint r9 = r8.mPaint     // Catch:{ all -> 0x00ca }
            android.graphics.Paint$Style r3 = android.graphics.Paint.Style.STROKE     // Catch:{ all -> 0x00ca }
            r9.setStyle(r3)     // Catch:{ all -> 0x00ca }
            android.graphics.Paint r9 = r8.mPaint     // Catch:{ all -> 0x00ca }
            int r3 = r8.width     // Catch:{ all -> 0x00ca }
            float r3 = (float) r3     // Catch:{ all -> 0x00ca }
            r9.setStrokeWidth(r3)     // Catch:{ all -> 0x00ca }
            android.graphics.Paint r9 = r8.mPaint     // Catch:{ all -> 0x00ca }
            android.graphics.Paint$Cap r3 = android.graphics.Paint.Cap.ROUND     // Catch:{ all -> 0x00ca }
            r9.setStrokeCap(r3)     // Catch:{ all -> 0x00ca }
            android.graphics.Paint r9 = r8.mPaint     // Catch:{ all -> 0x00ca }
            int r3 = r8.color     // Catch:{ all -> 0x00ca }
            r9.setColor(r3)     // Catch:{ all -> 0x00ca }
            boolean r9 = r8.isInEditMode()     // Catch:{ all -> 0x00ca }
            if (r9 != 0) goto L_0x00c8
            float r9 = r8.halfSweepAMaxValue     // Catch:{ all -> 0x00ca }
            float r3 = r8.halfSweepAMinValue     // Catch:{ all -> 0x00ca }
            float r9 = r9 - r3
            float r9 = r9 / r2
            r8.halfSweepA = r9     // Catch:{ all -> 0x00ca }
            r9 = 2
            float[] r2 = new float[r9]     // Catch:{ all -> 0x00ca }
            r3 = 0
            r4 = 0
            r2[r4] = r3     // Catch:{ all -> 0x00ca }
            r5 = 1136033792(0x43b68000, float:365.0)
            r2[r1] = r5     // Catch:{ all -> 0x00ca }
            android.animation.ValueAnimator r2 = android.animation.ValueAnimator.ofFloat(r2)     // Catch:{ all -> 0x00ca }
            r8.rotateAnimator = r2     // Catch:{ all -> 0x00ca }
            r6 = 1000(0x3e8, double:4.94E-321)
            r2.setDuration(r6)     // Catch:{ all -> 0x00ca }
            android.animation.ValueAnimator r2 = r8.rotateAnimator     // Catch:{ all -> 0x00ca }
            android.view.animation.LinearInterpolator r6 = new android.view.animation.LinearInterpolator     // Catch:{ all -> 0x00ca }
            r6.<init>()     // Catch:{ all -> 0x00ca }
            r2.setInterpolator(r6)     // Catch:{ all -> 0x00ca }
            android.animation.ValueAnimator r2 = r8.rotateAnimator     // Catch:{ all -> 0x00ca }
            r6 = -1
            r2.setRepeatCount(r6)     // Catch:{ all -> 0x00ca }
            android.animation.ValueAnimator r2 = r8.rotateAnimator     // Catch:{ all -> 0x00ca }
            com.kongzue.dialogx.util.views.ProgressView$1 r7 = new com.kongzue.dialogx.util.views.ProgressView$1     // Catch:{ all -> 0x00ca }
            r7.<init>()     // Catch:{ all -> 0x00ca }
            r2.addUpdateListener(r7)     // Catch:{ all -> 0x00ca }
            float[] r9 = new float[r9]     // Catch:{ all -> 0x00ca }
            r9[r4] = r3     // Catch:{ all -> 0x00ca }
            r9[r1] = r5     // Catch:{ all -> 0x00ca }
            android.animation.ValueAnimator r9 = android.animation.ValueAnimator.ofFloat(r9)     // Catch:{ all -> 0x00ca }
            r8.followAnimator = r9     // Catch:{ all -> 0x00ca }
            r1 = 1500(0x5dc, double:7.41E-321)
            r9.setDuration(r1)     // Catch:{ all -> 0x00ca }
            android.animation.ValueAnimator r9 = r8.followAnimator     // Catch:{ all -> 0x00ca }
            android.view.animation.LinearInterpolator r1 = new android.view.animation.LinearInterpolator     // Catch:{ all -> 0x00ca }
            r1.<init>()     // Catch:{ all -> 0x00ca }
            r9.setInterpolator(r1)     // Catch:{ all -> 0x00ca }
            android.animation.ValueAnimator r9 = r8.followAnimator     // Catch:{ all -> 0x00ca }
            r9.setRepeatCount(r6)     // Catch:{ all -> 0x00ca }
            android.animation.ValueAnimator r9 = r8.followAnimator     // Catch:{ all -> 0x00ca }
            com.kongzue.dialogx.util.views.ProgressView$2 r1 = new com.kongzue.dialogx.util.views.ProgressView$2     // Catch:{ all -> 0x00ca }
            r1.<init>()     // Catch:{ all -> 0x00ca }
            r9.addUpdateListener(r1)     // Catch:{ all -> 0x00ca }
            android.animation.ValueAnimator r9 = r8.followAnimator     // Catch:{ all -> 0x00ca }
            r9.start()     // Catch:{ all -> 0x00ca }
            android.animation.ValueAnimator r9 = r8.rotateAnimator     // Catch:{ all -> 0x00ca }
            r9.start()     // Catch:{ all -> 0x00ca }
        L_0x00c8:
            monitor-exit(r0)     // Catch:{ all -> 0x00ca }
            return
        L_0x00ca:
            r9 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ca }
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.util.views.ProgressView.init(android.util.AttributeSet):void");
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mCenterX = (((float) i) * 1.0f) / 2.0f;
        this.mCenterY = (((float) i2) * 1.0f) / 2.0f;
        this.mRadius = (float) ((Math.min(getWidth(), getHeight()) / 2) - (this.width / 2));
        float f = this.mCenterX;
        float f2 = this.mRadius;
        float f3 = this.mCenterY;
        this.oval = new RectF(f - f2, f3 - f2, f + f2, f3 + f2);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            canvas.drawArc(this.oval, 0.0f, 365.0f, false, this.mPaint);
        } else if (this.noShowLoading) {
            canvas.drawArc(this.oval, 0.0f, 365.0f, false, this.mPaint);
            this.successStep = 2;
            drawDoneMark(this.status, canvas);
        } else {
            int i = this.status;
            if (i == 0) {
                float sin = ((float) (((double) this.halfSweepA) * Math.sin(Math.toRadians((double) this.followRotateDegrees)))) + this.halfSweepA + (this.halfSweepAMinValue / 2.0f);
                float f = this.currentRotateDegrees;
                float f2 = f - sin;
                this.nowLoadingProgressValue = f2;
                if (f2 < 0.0f) {
                    this.nowLoadingProgressValue = f2 + 360.0f;
                }
                this.nowLoadingProgressEndAngle = sin;
                canvas.drawArc(this.oval, f, -sin, false, this.mPaint);
            } else if (i == 1 || i == 2 || i == 3) {
                int i2 = this.successStep;
                if (i2 == 0) {
                    float f3 = this.nowLoadingProgressEndAngle + 5.0f;
                    this.nowLoadingProgressEndAngle = f3;
                    canvas.drawArc(this.oval, this.nowLoadingProgressValue, f3, false, this.mPaint);
                    if (this.nowLoadingProgressEndAngle - 360.0f >= this.nowLoadingProgressValue) {
                        this.successStep = 1;
                        Runnable runnable = this.waitArticulationAnimationRunnable;
                        if (runnable != null) {
                            runnable.run();
                            this.waitArticulationAnimationRunnable = null;
                        }
                    }
                } else if (i2 == 1) {
                    canvas.drawArc(this.oval, 0.0f, 360.0f, false, this.mPaint);
                    drawDoneMark(this.status, canvas);
                }
            } else if (i == 4) {
                int i3 = this.successStep;
                if (i3 == 0) {
                    canvas.drawArc(this.oval, -90.0f, this.currentRotateDegrees, false, this.mPaint);
                    if (this.currentRotateDegrees == 365.0f) {
                        this.successStep = 1;
                        Runnable runnable2 = this.waitArticulationAnimationRunnable;
                        if (runnable2 != null) {
                            runnable2.run();
                            this.waitArticulationAnimationRunnable = null;
                        }
                    }
                } else if (i3 == 1) {
                    canvas.drawArc(this.oval, 0.0f, 360.0f, false, this.mPaint);
                    drawDoneMark(this.status, canvas);
                }
            }
        }
    }

    private void drawDoneMark(int i, Canvas canvas) {
        TimeInterpolator interpolator2 = this.rotateAnimator.getInterpolator();
        Interpolator interpolator3 = this.interpolator;
        if (interpolator2 != interpolator3) {
            this.rotateAnimator.setInterpolator(interpolator3);
        }
        Runnable runnable = this.tickShowRunnable;
        if (runnable != null) {
            runnable.run();
            this.tickShowRunnable = null;
            if (DialogX.useHaptic) {
                if (i == 1) {
                    performHapticFeedback(3);
                } else if (i == 2) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        public void run() {
                            ProgressView.this.performHapticFeedback(0);
                        }
                    }, (long) (((float) TIP_ANIMATOR_DURATION) * 0.8f));
                } else if (i == 3) {
                    performHapticFeedback(3);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        public void run() {
                            ProgressView.this.performHapticFeedback(3);
                        }
                    }, (long) (((float) TIP_ANIMATOR_DURATION) * 0.5f));
                }
            }
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
        float f = this.mRadius;
        float f2 = this.mCenterX;
        float f3 = (float) ((int) (f / 20.0f));
        int i = (int) ((f2 - (f / 10.0f)) - f3);
        int i2 = (int) (f2 - (f / 2.0f));
        int i3 = (int) (this.mCenterY + f3);
        int i4 = (int) (f2 + (f / 2.0f));
        float f4 = (float) i2;
        int i5 = (int) ((((float) (i4 - i2)) * this.tickAnimatorValue) + f4);
        Path path = new Path();
        path.moveTo(f4, (float) i3);
        if (i5 < i) {
            this.line1X = i5;
            int i6 = i3 + (i5 - i2);
            this.line1Y = i6;
            path.lineTo((float) i5, (float) i6);
        } else {
            this.line1X = i;
            int i7 = i3 + (i - i2);
            this.line1Y = i7;
            path.lineTo((float) i, (float) i7);
            this.line2X = i5;
            int i8 = this.line1Y - (i5 - this.line1X);
            this.line2Y = i8;
            path.lineTo((float) i5, (float) i8);
        }
        canvas.drawPath(path, this.mPaint);
    }

    private void showWarningTick(Canvas canvas) {
        int i = (int) this.mCenterX;
        float f = this.mCenterY;
        float f2 = this.mRadius;
        int i2 = (int) (f - ((f2 * 1.0f) / 2.0f));
        int i3 = (int) (((1.0f * f2) / 8.0f) + f);
        int i4 = (int) (f + ((f2 * 3.0f) / 7.0f));
        float f3 = this.tickAnimatorValue;
        if (f3 < 0.9f) {
            float f4 = (float) i;
            float f5 = (float) i2;
            canvas.drawLine(f4, f5, f4, f5 + (((float) (i3 - i2)) * f3), this.mPaint);
            return;
        }
        float f6 = (float) i;
        float f7 = f6;
        canvas.drawLine(f7, (float) i2, f6, (float) i3, this.mPaint);
        canvas.drawLine(f6, (float) i4, f7, (float) (i4 + 1), this.mPaint);
    }

    private void showErrorTick(Canvas canvas) {
        float f = this.mCenterY;
        float f2 = this.mRadius;
        int i = (int) (f - ((f2 * 4.0f) / 10.0f));
        int i2 = (int) (this.mCenterX + ((f2 * 4.0f) / 10.0f));
        float f3 = this.tickAnimatorValue;
        if (f3 < 0.5f) {
            float f4 = (float) i;
            float f5 = (float) (i2 - i);
            int i3 = (int) ((f3 * 2.0f * f5) + f4);
            this.line1X = i3;
            int i4 = (int) ((f3 * 2.0f * f5) + f4);
            this.line1Y = i4;
            canvas.drawLine(f4, f4, (float) i3, (float) i4, this.mPaint);
            return;
        }
        float f6 = (float) i;
        float f7 = (float) (i2 - i);
        this.line1X = (int) ((f3 * 2.0f * f7) + f6);
        this.line1Y = (int) ((f3 * 2.0f * f7) + f6);
        float f8 = (float) i2;
        canvas.drawLine(f6, f6, f8, f8, this.mPaint);
        float f9 = this.tickAnimatorValue;
        int i5 = (int) (f8 - (((f9 - 0.5f) * 2.0f) * f7));
        this.line2X = i5;
        int i6 = (int) (((f9 - 0.5f) * 2.0f * f7) + f6);
        this.line2Y = i6;
        float f10 = (float) i6;
        canvas.drawLine(f8, f6, (float) i5, f10, this.mPaint);
    }

    public void success() {
        if (this.status == 4) {
            progress(1.0f);
            this.waitArticulationAnimationRunnable = new Runnable() {
                public void run() {
                    ProgressView.this.initTipAnimator(1, new AccelerateDecelerateInterpolator());
                }
            };
            return;
        }
        initTipAnimator(1, new AccelerateDecelerateInterpolator());
    }

    public void warning() {
        if (this.status == 4) {
            progress(1.0f);
            this.waitArticulationAnimationRunnable = new Runnable() {
                public void run() {
                    ProgressView.this.initTipAnimator(2, new AccelerateInterpolator(2.0f));
                }
            };
            return;
        }
        initTipAnimator(2, new AccelerateInterpolator(2.0f));
    }

    public void error() {
        if (this.status == 4) {
            progress(1.0f);
            this.waitArticulationAnimationRunnable = new Runnable() {
                public void run() {
                    ProgressView.this.initTipAnimator(3, new DecelerateInterpolator(2.0f));
                }
            };
            return;
        }
        initTipAnimator(3, new DecelerateInterpolator(2.0f));
    }

    /* access modifiers changed from: private */
    public void initTipAnimator(int i, Interpolator interpolator2) {
        this.interpolator = interpolator2;
        this.status = i;
        if (this.successStep == 0) {
            this.waitArticulationAnimationRunnable = new Runnable() {
                public void run() {
                    ProgressView progressView = ProgressView.this;
                    progressView.initTipAnimator(progressView.status, ProgressView.this.interpolator);
                }
            };
            return;
        }
        ValueAnimator valueAnimator = this.tickAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.tickAnimator = null;
        }
        this.tickAnimatorValue = 0.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.tickAnimator = ofFloat;
        ofFloat.setDuration(TIP_ANIMATOR_DURATION);
        this.tickAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float unused = ProgressView.this.tickAnimatorValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ProgressView.this.invalidate();
            }
        });
        this.tickAnimator.start();
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
        ofFloat.setDuration(PROGRESSING_ANIMATOR_DURATION);
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
