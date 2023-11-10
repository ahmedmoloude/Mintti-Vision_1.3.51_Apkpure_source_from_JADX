package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.utils.LoggerUtil;
import com.p020kl.commonbase.utils.TemperatureUtils;
import com.p020kl.commonbase.utils.UnitUtil;
import com.p020kl.healthmonitor.C1624R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.views.ChartView */
public class ChartView extends View {
    private float dp10;
    private float dp12;
    private float dp14;
    private float dp17;
    private float dp18;
    private float dp2;
    private float dp4;
    private float dp7;
    private int endX;
    private int endY;
    private int height;
    private int interval;
    private boolean isBgList;
    private boolean isScroll;
    private Paint linePaint;
    private int linecolor;
    private String lingType;
    private float maxXInit;
    private float minXInit;
    private int pointTextHight;
    private int position;
    private int scale;
    private float startX;
    private int width;
    private float xInit;
    private int xOri;
    private List<String> xValue;
    private Rect xValueRect;
    private TextPaint xyTextPaint;
    private int xylinewidth;
    private int xytextcolor;
    private int xytextsize;
    private List<Double> yBGValue;
    private List<Integer> yBpmValue;
    private List<Integer> yHighValue;
    private List<Integer> yLowValue;
    private int yOri;
    private List<Double> ySpo2Value;
    private List<String> yState;
    private List<Float> yValue;

    public ChartView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ChartView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChartView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.xylinewidth = (int) getResources().getDimension(C1624R.dimen.dp_1);
        this.dp2 = getResources().getDimension(C1624R.dimen.dp_2);
        this.dp4 = getResources().getDimension(C1624R.dimen.dp_4);
        this.dp7 = getResources().getDimension(C1624R.dimen.dp_7);
        this.dp10 = getResources().getDimension(C1624R.dimen.dp_10);
        this.dp12 = getResources().getDimension(C1624R.dimen.dp_12);
        this.dp14 = getResources().getDimension(C1624R.dimen.dp_14);
        this.dp17 = getResources().getDimension(C1624R.dimen.dp_17);
        this.dp18 = getResources().getDimension(C1624R.dimen.dp_18);
        this.xytextsize = (int) getContext().getResources().getDimension(C1624R.dimen.dp_12);
        this.isScroll = false;
        this.lingType = "BTM";
        this.xValue = new ArrayList();
        this.yValue = new ArrayList();
        this.yLowValue = new ArrayList();
        this.yHighValue = new ArrayList();
        this.ySpo2Value = new ArrayList();
        this.yBpmValue = new ArrayList();
        this.yBGValue = new ArrayList();
        this.yState = new ArrayList();
        this.scale = 1;
        init(context, attributeSet, i);
        initPaint();
    }

    private void initPaint() {
        TextPaint textPaint = new TextPaint();
        this.xyTextPaint = textPaint;
        textPaint.setAntiAlias(true);
        this.xyTextPaint.setTextSize((float) this.xytextsize);
        this.xyTextPaint.setStrokeCap(Paint.Cap.ROUND);
        this.xyTextPaint.setColor(this.xytextcolor);
        this.xyTextPaint.setStyle(Paint.Style.STROKE);
        Paint paint = new Paint();
        this.linePaint = paint;
        paint.setAntiAlias(true);
        this.linePaint.setStrokeWidth((float) this.xylinewidth);
        this.linePaint.setStrokeCap(Paint.Cap.ROUND);
        this.linePaint.setColor(this.linecolor);
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.xValueRect = getTextBounds("00:00 00-00", this.xyTextPaint);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.chartView, i, 0);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = obtainStyledAttributes.getIndex(i2);
            if (index == 0) {
                this.interval = (int) obtainStyledAttributes.getDimension(index, TypedValue.applyDimension(0, (float) this.interval, getResources().getDisplayMetrics()));
            } else if (index == 1) {
                this.isScroll = obtainStyledAttributes.getBoolean(index, this.isScroll);
            } else if (index == 2) {
                this.linecolor = obtainStyledAttributes.getColor(index, this.linecolor);
            } else if (index == 3) {
                this.lingType = obtainStyledAttributes.getString(index);
            } else if (index == 4) {
                this.xytextcolor = obtainStyledAttributes.getColor(index, this.xytextcolor);
            } else if (index == 5) {
                this.xytextsize = (int) obtainStyledAttributes.getDimension(index, TypedValue.applyDimension(0, (float) this.xytextsize, getResources().getDisplayMetrics()));
            }
        }
        obtainStyledAttributes.recycle();
    }

    public void setLingType(String str) {
        this.lingType = str;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            this.width = getWidth();
            this.height = getHeight();
            this.xOri = 0;
            this.yOri = (int) ((((float) this.height) - ((float) (this.xValueRect.height() * 2))) - this.dp12);
            this.xInit = (float) (this.xValueRect.width() / 2);
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Log.d("绘制", "haha");
        drawXY(canvas);
        drawBrokenLineAndPoint(canvas);
    }

    private void drawBrokenLineAndPoint(Canvas canvas) {
        if (this.xValue.size() > 0) {
            int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) this.width, (float) this.height, (Paint) null, 31);
            if (this.lingType.equals(Constants.BPM_UNIT)) {
                drawBrokenBPMLine(canvas);
                drawBrokenBPMPoint(canvas);
            } else if (this.lingType.equals("SPO2H")) {
                drawBrokenSPO2HLine(canvas);
                drawBrokenSPO2HPoint(canvas);
            } else if (this.lingType.equals(Constants.f837BG)) {
                drawBrokenBGLine(canvas);
                drawBrokenBGPoint(canvas);
            } else if (this.lingType.equals("POINT")) {
                drawPointPoint(canvas);
                drawPointLine(canvas);
            } else {
                drawBrokenLine(canvas);
                drawBrokenPoint(canvas);
            }
            this.linePaint.setStyle(Paint.Style.FILL);
            this.linePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawRect(new RectF(0.0f, 0.0f, (float) this.xOri, (float) this.height), this.linePaint);
            this.linePaint.setXfermode((Xfermode) null);
            canvas.restoreToCount(saveLayer);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0114  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenPoint(android.graphics.Canvas r7) {
        /*
            r6 = this;
            r0 = 0
        L_0x0001:
            java.util.List<java.lang.String> r1 = r6.xValue
            int r1 = r1.size()
            if (r0 >= r1) goto L_0x011f
            float r1 = r6.xInit
            int r2 = r6.interval
            int r2 = r2 * r0
            float r2 = (float) r2
            float r1 = r1 + r2
            java.util.List<java.lang.Float> r2 = r6.yValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            r3 = 1108606976(0x42140000, float:37.0)
            r4 = 1108344832(0x42100000, float:36.0)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x0053
            java.util.List<java.lang.Float> r2 = r6.yValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 > 0) goto L_0x0053
            int r2 = r6.yOri
            float r3 = (float) r2
            int r2 = r2 / 2
            float r2 = (float) r2
            java.util.List<java.lang.Float> r5 = r6.yValue
            java.lang.Object r5 = r5.get(r0)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            float r5 = r5 - r4
            float r2 = r2 * r5
            float r3 = r3 - r2
            int r2 = r6.yOri
            int r2 = r2 / 8
            float r2 = (float) r2
        L_0x0050:
            float r3 = r3 - r2
            goto L_0x00e7
        L_0x0053:
            java.util.List<java.lang.Float> r2 = r6.yValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x008b
            java.util.List<java.lang.Float> r2 = r6.yValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            r4 = 1108082688(0x420c0000, float:35.0)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x008b
            int r2 = r6.yOri
            float r3 = (float) r2
            int r2 = r2 / 8
            float r2 = (float) r2
            java.util.List<java.lang.Float> r5 = r6.yValue
            java.lang.Object r5 = r5.get(r0)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            float r5 = r5 - r4
            float r2 = r2 * r5
            goto L_0x0050
        L_0x008b:
            java.util.List<java.lang.Float> r2 = r6.yValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            r4 = 1109655552(0x42240000, float:41.0)
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x00cd
            java.util.List<java.lang.Float> r2 = r6.yValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x00cd
            int r2 = r6.yOri
            float r4 = (float) r2
            int r2 = r2 / 24
            float r2 = (float) r2
            java.util.List<java.lang.Float> r5 = r6.yValue
            java.lang.Object r5 = r5.get(r0)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            float r5 = r5 - r3
            float r2 = r2 * r5
            float r4 = r4 - r2
            int r2 = r6.yOri
            int r2 = r2 / 8
            int r2 = r2 * 5
            float r2 = (float) r2
            float r3 = r4 - r2
            goto L_0x00e7
        L_0x00cd:
            java.util.List<java.lang.Float> r2 = r6.yValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x00e4
            int r2 = r6.yOri
            int r2 = r2 / 24
            int r2 = r2 * 5
            goto L_0x00e6
        L_0x00e4:
            int r2 = r6.yOri
        L_0x00e6:
            float r3 = (float) r2
        L_0x00e7:
            float r2 = r6.dp7
            float r2 = r3 - r2
            java.util.List<java.lang.Float> r4 = r6.yValue
            java.lang.Object r4 = r4.get(r0)
            java.lang.Float r4 = (java.lang.Float) r4
            float r4 = r4.floatValue()
            r6.drawFloatTextBox(r7, r1, r2, r4)
            android.graphics.Paint r2 = r6.linePaint
            android.graphics.Paint$Style r4 = android.graphics.Paint.Style.FILL
            r2.setStyle(r4)
            android.graphics.Paint r2 = r6.linePaint
            int r4 = r6.linecolor
            r2.setColor(r4)
            int r2 = r6.position
            if (r0 != r2) goto L_0x0114
            float r2 = r6.dp4
            android.graphics.Paint r4 = r6.linePaint
            r7.drawCircle(r1, r3, r2, r4)
            goto L_0x011b
        L_0x0114:
            float r2 = r6.dp2
            android.graphics.Paint r4 = r6.linePaint
            r7.drawCircle(r1, r3, r2, r4)
        L_0x011b:
            int r0 = r0 + 1
            goto L_0x0001
        L_0x011f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.ChartView.drawBrokenPoint(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00fb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenLine(android.graphics.Canvas r11) {
        /*
            r10 = this;
            android.graphics.Paint r0 = r10.linePaint
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            r0.setStyle(r1)
            android.graphics.Paint r0 = r10.linePaint
            int r1 = r10.linecolor
            r0.setColor(r1)
            android.graphics.Path r0 = new android.graphics.Path
            r0.<init>()
            float r1 = r10.xInit
            int r2 = r10.interval
            r3 = 0
            int r2 = r2 * 0
            float r2 = (float) r2
            float r1 = r1 + r2
            java.util.List<java.lang.Float> r2 = r10.yValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            r4 = 1109655552(0x42240000, float:41.0)
            r5 = 1108082688(0x420c0000, float:35.0)
            r6 = 1108606976(0x42140000, float:37.0)
            r7 = 1108344832(0x42100000, float:36.0)
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 < 0) goto L_0x0062
            java.util.List<java.lang.Float> r2 = r10.yValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 > 0) goto L_0x0062
            int r2 = r10.yOri
            float r8 = (float) r2
            int r2 = r2 / 2
            float r2 = (float) r2
            java.util.List<java.lang.Float> r9 = r10.yValue
            java.lang.Object r3 = r9.get(r3)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            float r3 = r3 - r7
            float r2 = r2 * r3
            float r8 = r8 - r2
            int r2 = r10.yOri
            int r2 = r2 / 8
        L_0x005e:
            float r2 = (float) r2
        L_0x005f:
            float r8 = r8 - r2
            goto L_0x00ef
        L_0x0062:
            java.util.List<java.lang.Float> r2 = r10.yValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 >= 0) goto L_0x0098
            java.util.List<java.lang.Float> r2 = r10.yValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 < 0) goto L_0x0098
            int r2 = r10.yOri
            float r8 = (float) r2
            int r2 = r2 / 8
            float r2 = (float) r2
            java.util.List<java.lang.Float> r9 = r10.yValue
            java.lang.Object r3 = r9.get(r3)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            float r3 = r3 - r5
            float r2 = r2 * r3
            goto L_0x005f
        L_0x0098:
            java.util.List<java.lang.Float> r2 = r10.yValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x00d5
            java.util.List<java.lang.Float> r2 = r10.yValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x00d5
            int r2 = r10.yOri
            float r8 = (float) r2
            int r2 = r2 / 24
            float r2 = (float) r2
            java.util.List<java.lang.Float> r9 = r10.yValue
            java.lang.Object r3 = r9.get(r3)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            float r3 = r3 - r6
            float r2 = r2 * r3
            float r8 = r8 - r2
            int r2 = r10.yOri
            int r2 = r2 / 8
            int r2 = r2 * 5
            goto L_0x005e
        L_0x00d5:
            java.util.List<java.lang.Float> r2 = r10.yValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x00ec
            int r2 = r10.yOri
            int r2 = r2 / 24
            int r2 = r2 * 5
            goto L_0x00ee
        L_0x00ec:
            int r2 = r10.yOri
        L_0x00ee:
            float r8 = (float) r2
        L_0x00ef:
            r0.moveTo(r1, r8)
            r1 = 1
        L_0x00f3:
            java.util.List<java.lang.String> r2 = r10.xValue
            int r2 = r2.size()
            if (r1 >= r2) goto L_0x01d5
            float r2 = r10.xInit
            int r3 = r10.interval
            int r3 = r3 * r1
            float r3 = (float) r3
            float r2 = r2 + r3
            java.util.List<java.lang.Float> r3 = r10.yValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 < 0) goto L_0x0141
            java.util.List<java.lang.Float> r3 = r10.yValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            int r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r3 > 0) goto L_0x0141
            int r3 = r10.yOri
            float r8 = (float) r3
            int r3 = r3 / 2
            float r3 = (float) r3
            java.util.List<java.lang.Float> r9 = r10.yValue
            java.lang.Object r9 = r9.get(r1)
            java.lang.Float r9 = (java.lang.Float) r9
            float r9 = r9.floatValue()
            float r9 = r9 - r7
            float r3 = r3 * r9
            float r8 = r8 - r3
            int r3 = r10.yOri
            int r3 = r3 / 8
        L_0x013d:
            float r3 = (float) r3
        L_0x013e:
            float r8 = r8 - r3
            goto L_0x01ce
        L_0x0141:
            java.util.List<java.lang.Float> r3 = r10.yValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 >= 0) goto L_0x0177
            java.util.List<java.lang.Float> r3 = r10.yValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 < 0) goto L_0x0177
            int r3 = r10.yOri
            float r8 = (float) r3
            int r3 = r3 / 8
            float r3 = (float) r3
            java.util.List<java.lang.Float> r9 = r10.yValue
            java.lang.Object r9 = r9.get(r1)
            java.lang.Float r9 = (java.lang.Float) r9
            float r9 = r9.floatValue()
            float r9 = r9 - r5
            float r3 = r3 * r9
            goto L_0x013e
        L_0x0177:
            java.util.List<java.lang.Float> r3 = r10.yValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            int r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r3 <= 0) goto L_0x01b4
            java.util.List<java.lang.Float> r3 = r10.yValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 >= 0) goto L_0x01b4
            int r3 = r10.yOri
            float r8 = (float) r3
            int r3 = r3 / 24
            float r3 = (float) r3
            java.util.List<java.lang.Float> r9 = r10.yValue
            java.lang.Object r9 = r9.get(r1)
            java.lang.Float r9 = (java.lang.Float) r9
            float r9 = r9.floatValue()
            float r9 = r9 - r6
            float r3 = r3 * r9
            float r8 = r8 - r3
            int r3 = r10.yOri
            int r3 = r3 / 8
            int r3 = r3 * 5
            goto L_0x013d
        L_0x01b4:
            java.util.List<java.lang.Float> r3 = r10.yValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 < 0) goto L_0x01cb
            int r3 = r10.yOri
            int r3 = r3 / 24
            int r3 = r3 * 5
            goto L_0x01cd
        L_0x01cb:
            int r3 = r10.yOri
        L_0x01cd:
            float r8 = (float) r3
        L_0x01ce:
            r0.lineTo(r2, r8)
            int r1 = r1 + 1
            goto L_0x00f3
        L_0x01d5:
            android.graphics.Paint r1 = r10.linePaint
            r11.drawPath(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.ChartView.drawBrokenLine(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01f5  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0200  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenBPMPoint(android.graphics.Canvas r10) {
        /*
            r9 = this;
            r0 = 0
            r1 = 0
        L_0x0002:
            java.util.List<java.lang.String> r2 = r9.xValue
            int r2 = r2.size()
            r3 = 180(0xb4, float:2.52E-43)
            r4 = 50
            r5 = 140(0x8c, float:1.96E-43)
            r6 = 60
            if (r1 >= r2) goto L_0x010c
            float r2 = r9.xInit
            int r7 = r9.interval
            int r7 = r7 * r1
            float r7 = (float) r7
            float r2 = r2 + r7
            java.util.List<java.lang.Integer> r7 = r9.yLowValue
            java.lang.Object r7 = r7.get(r1)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 < r6) goto L_0x0052
            java.util.List<java.lang.Integer> r7 = r9.yLowValue
            java.lang.Object r7 = r7.get(r1)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 > r5) goto L_0x0052
            int r3 = r9.yOri
            int r4 = r3 / 128
            java.util.List<java.lang.Integer> r5 = r9.yLowValue
            java.lang.Object r5 = r5.get(r1)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            int r5 = r5 - r6
            int r4 = r4 * r5
            int r3 = r3 - r4
            int r4 = r9.yOri
            int r4 = r4 / 8
        L_0x004e:
            int r3 = r3 - r4
        L_0x004f:
            float r3 = (float) r3
            goto L_0x00d1
        L_0x0052:
            java.util.List<java.lang.Integer> r7 = r9.yLowValue
            java.lang.Object r7 = r7.get(r1)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 >= r6) goto L_0x0083
            java.util.List<java.lang.Integer> r6 = r9.yLowValue
            java.lang.Object r6 = r6.get(r1)
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            if (r6 <= r4) goto L_0x0083
            int r3 = r9.yOri
            int r5 = r3 / 80
            java.util.List<java.lang.Integer> r6 = r9.yLowValue
            java.lang.Object r6 = r6.get(r1)
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            int r6 = r6 - r4
            int r5 = r5 * r6
            int r3 = r3 - r5
            goto L_0x004f
        L_0x0083:
            java.util.List<java.lang.Integer> r4 = r9.yLowValue
            java.lang.Object r4 = r4.get(r1)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            if (r4 <= r5) goto L_0x00ba
            java.util.List<java.lang.Integer> r4 = r9.yLowValue
            java.lang.Object r4 = r4.get(r1)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            if (r4 >= r3) goto L_0x00ba
            int r3 = r9.yOri
            int r4 = r3 / 320
            java.util.List<java.lang.Integer> r6 = r9.yLowValue
            java.lang.Object r6 = r6.get(r1)
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            int r6 = r6 - r5
            int r4 = r4 * r6
            int r3 = r3 - r4
            int r4 = r9.yOri
            int r4 = r4 / 4
            int r4 = r4 * 3
            goto L_0x004e
        L_0x00ba:
            java.util.List<java.lang.Integer> r4 = r9.yLowValue
            java.lang.Object r4 = r4.get(r1)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            if (r4 < r3) goto L_0x00cd
            int r3 = r9.yOri
            int r3 = r3 / 8
            goto L_0x004f
        L_0x00cd:
            int r3 = r9.yOri
            goto L_0x004f
        L_0x00d1:
            float r4 = r9.dp2
            float r4 = r4 + r3
            java.util.List<java.lang.Integer> r5 = r9.yLowValue
            java.lang.Object r5 = r5.get(r1)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r9.drawFloatBPMTextBox(r10, r2, r4, r5)
            android.graphics.Paint r4 = r9.linePaint
            android.graphics.Paint$Style r5 = android.graphics.Paint.Style.FILL
            r4.setStyle(r5)
            android.graphics.Paint r4 = r9.linePaint
            int r5 = r9.linecolor
            r4.setColor(r5)
            int r4 = r9.position
            if (r1 != r4) goto L_0x0100
            float r4 = r9.dp2
            float r3 = r3 + r4
            float r4 = r9.dp4
            android.graphics.Paint r5 = r9.linePaint
            r10.drawCircle(r2, r3, r4, r5)
            goto L_0x0108
        L_0x0100:
            float r4 = r9.dp2
            float r3 = r3 + r4
            android.graphics.Paint r5 = r9.linePaint
            r10.drawCircle(r2, r3, r4, r5)
        L_0x0108:
            int r1 = r1 + 1
            goto L_0x0002
        L_0x010c:
            java.util.List<java.lang.String> r1 = r9.xValue
            int r1 = r1.size()
            if (r0 >= r1) goto L_0x020e
            float r1 = r9.xInit
            int r2 = r9.interval
            int r2 = r2 * r0
            float r2 = (float) r2
            float r1 = r1 + r2
            java.util.List<java.lang.Integer> r2 = r9.yHighValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 < r6) goto L_0x0154
            java.util.List<java.lang.Integer> r2 = r9.yHighValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 > r5) goto L_0x0154
            int r2 = r9.yOri
            int r7 = r2 / 128
            java.util.List<java.lang.Integer> r8 = r9.yHighValue
            java.lang.Object r8 = r8.get(r0)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            int r8 = r8 - r6
            int r7 = r7 * r8
            int r2 = r2 - r7
            int r7 = r9.yOri
            int r7 = r7 / 8
        L_0x0150:
            int r2 = r2 - r7
        L_0x0151:
            float r2 = (float) r2
            goto L_0x01d1
        L_0x0154:
            java.util.List<java.lang.Integer> r2 = r9.yHighValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 >= r6) goto L_0x0184
            java.util.List<java.lang.Integer> r2 = r9.yHighValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 <= r4) goto L_0x0184
            int r2 = r9.yOri
            int r7 = r2 / 80
            java.util.List<java.lang.Integer> r8 = r9.yHighValue
            java.lang.Object r8 = r8.get(r0)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            int r8 = r8 - r4
            int r7 = r7 * r8
            goto L_0x0150
        L_0x0184:
            java.util.List<java.lang.Integer> r2 = r9.yHighValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 <= r5) goto L_0x01bb
            java.util.List<java.lang.Integer> r2 = r9.yHighValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 >= r3) goto L_0x01bb
            int r2 = r9.yOri
            int r7 = r2 / 320
            java.util.List<java.lang.Integer> r8 = r9.yHighValue
            java.lang.Object r8 = r8.get(r0)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            int r8 = r8 - r5
            int r7 = r7 * r8
            int r2 = r2 - r7
            int r7 = r9.yOri
            int r7 = r7 / 4
            int r7 = r7 * 3
            goto L_0x0150
        L_0x01bb:
            java.util.List<java.lang.Integer> r2 = r9.yHighValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 < r3) goto L_0x01ce
            int r2 = r9.yOri
            int r2 = r2 / 8
            goto L_0x0151
        L_0x01ce:
            int r2 = r9.yOri
            goto L_0x0151
        L_0x01d1:
            float r7 = r9.dp4
            float r7 = r7 + r2
            java.util.List<java.lang.Integer> r8 = r9.yHighValue
            java.lang.Object r8 = r8.get(r0)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            r9.drawFloatBPMTextBox(r10, r1, r7, r8)
            android.graphics.Paint r7 = r9.linePaint
            android.graphics.Paint$Style r8 = android.graphics.Paint.Style.FILL
            r7.setStyle(r8)
            android.graphics.Paint r7 = r9.linePaint
            int r8 = r9.linecolor
            r7.setColor(r8)
            int r7 = r9.position
            if (r0 != r7) goto L_0x0200
            float r7 = r9.dp7
            float r2 = r2 + r7
            float r7 = r9.dp4
            android.graphics.Paint r8 = r9.linePaint
            r10.drawCircle(r1, r2, r7, r8)
            goto L_0x020a
        L_0x0200:
            float r7 = r9.dp7
            float r2 = r2 + r7
            float r7 = r9.dp2
            android.graphics.Paint r8 = r9.linePaint
            r10.drawCircle(r1, r2, r7, r8)
        L_0x020a:
            int r0 = r0 + 1
            goto L_0x010c
        L_0x020e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.ChartView.drawBrokenBPMPoint(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01de  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x01fa  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0286  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenBPMLine(android.graphics.Canvas r13) {
        /*
            r12 = this;
            android.graphics.Paint r0 = r12.linePaint
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            r0.setStyle(r1)
            android.graphics.Paint r0 = r12.linePaint
            int r1 = r12.linecolor
            r0.setColor(r1)
            android.graphics.Path r0 = new android.graphics.Path
            r0.<init>()
            float r1 = r12.xInit
            int r2 = r12.interval
            r3 = 0
            int r2 = r2 * 0
            float r2 = (float) r2
            float r1 = r1 + r2
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            r4 = 180(0xb4, float:2.52E-43)
            r5 = 50
            r6 = 140(0x8c, float:1.96E-43)
            r7 = 60
            if (r2 < r7) goto L_0x005c
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 > r6) goto L_0x005c
            int r2 = r12.yOri
            int r8 = r2 / 128
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r3)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r7
            int r8 = r8 * r9
            int r2 = r2 - r8
            int r8 = r12.yOri
            int r8 = r8 / 8
        L_0x0058:
            int r2 = r2 - r8
        L_0x0059:
            float r2 = (float) r2
            goto L_0x00d9
        L_0x005c:
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 >= r7) goto L_0x008c
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 <= r5) goto L_0x008c
            int r2 = r12.yOri
            int r8 = r2 / 80
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r3)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r5
            int r8 = r8 * r9
            goto L_0x0058
        L_0x008c:
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 <= r6) goto L_0x00c3
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 >= r4) goto L_0x00c3
            int r2 = r12.yOri
            int r8 = r2 / 320
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r3)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r6
            int r8 = r8 * r9
            int r2 = r2 - r8
            int r8 = r12.yOri
            int r8 = r8 / 4
            int r8 = r8 * 3
            goto L_0x0058
        L_0x00c3:
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 < r4) goto L_0x00d6
            int r2 = r12.yOri
            int r2 = r2 / 8
            goto L_0x0059
        L_0x00d6:
            int r2 = r12.yOri
            goto L_0x0059
        L_0x00d9:
            float r8 = r12.dp2
            float r2 = r2 + r8
            r0.moveTo(r1, r2)
            r1 = 1
            r2 = 1
        L_0x00e1:
            java.util.List<java.lang.String> r8 = r12.xValue
            int r8 = r8.size()
            if (r2 >= r8) goto L_0x01b0
            float r8 = r12.xInit
            int r9 = r12.interval
            int r9 = r9 * r2
            float r9 = (float) r9
            float r8 = r8 + r9
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 < r7) goto L_0x0129
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 > r6) goto L_0x0129
            int r9 = r12.yOri
            int r10 = r9 / 128
            java.util.List<java.lang.Integer> r11 = r12.yLowValue
            java.lang.Object r11 = r11.get(r2)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r7
            int r10 = r10 * r11
            int r9 = r9 - r10
            int r10 = r12.yOri
            int r10 = r10 / 8
        L_0x0125:
            int r9 = r9 - r10
        L_0x0126:
            float r9 = (float) r9
            goto L_0x01a6
        L_0x0129:
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 >= r7) goto L_0x0159
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 <= r5) goto L_0x0159
            int r9 = r12.yOri
            int r10 = r9 / 80
            java.util.List<java.lang.Integer> r11 = r12.yLowValue
            java.lang.Object r11 = r11.get(r2)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r5
            int r10 = r10 * r11
            goto L_0x0125
        L_0x0159:
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 <= r6) goto L_0x0190
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 >= r4) goto L_0x0190
            int r9 = r12.yOri
            int r10 = r9 / 320
            java.util.List<java.lang.Integer> r11 = r12.yLowValue
            java.lang.Object r11 = r11.get(r2)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r6
            int r10 = r10 * r11
            int r9 = r9 - r10
            int r10 = r12.yOri
            int r10 = r10 / 4
            int r10 = r10 * 3
            goto L_0x0125
        L_0x0190:
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 < r4) goto L_0x01a3
            int r9 = r12.yOri
            int r9 = r9 / 8
            goto L_0x0126
        L_0x01a3:
            int r9 = r12.yOri
            goto L_0x0126
        L_0x01a6:
            float r10 = r12.dp2
            float r9 = r9 + r10
            r0.lineTo(r8, r9)
            int r2 = r2 + 1
            goto L_0x00e1
        L_0x01b0:
            android.graphics.Paint r2 = r12.linePaint
            r13.drawPath(r0, r2)
            android.graphics.Path r0 = new android.graphics.Path
            r0.<init>()
            float r2 = r12.xInit
            int r8 = r12.interval
            int r8 = r8 * 0
            float r8 = (float) r8
            float r2 = r2 + r8
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 < r7) goto L_0x01fa
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 > r6) goto L_0x01fa
            int r8 = r12.yOri
            int r9 = r8 / 128
            java.util.List<java.lang.Integer> r10 = r12.yHighValue
            java.lang.Object r3 = r10.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            int r3 = r3 - r7
            int r9 = r9 * r3
            int r8 = r8 - r9
            int r3 = r12.yOri
            int r3 = r3 / 8
        L_0x01f6:
            int r8 = r8 - r3
        L_0x01f7:
            float r3 = (float) r8
            goto L_0x0278
        L_0x01fa:
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 >= r7) goto L_0x022b
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 <= r5) goto L_0x022b
            int r8 = r12.yOri
            int r9 = r8 / 80
            java.util.List<java.lang.Integer> r10 = r12.yHighValue
            java.lang.Object r3 = r10.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            int r3 = r3 - r5
            int r9 = r9 * r3
            int r8 = r8 - r9
            goto L_0x01f7
        L_0x022b:
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 <= r6) goto L_0x0262
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 >= r4) goto L_0x0262
            int r8 = r12.yOri
            int r9 = r8 / 320
            java.util.List<java.lang.Integer> r10 = r12.yHighValue
            java.lang.Object r3 = r10.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            int r3 = r3 - r6
            int r9 = r9 * r3
            int r8 = r8 - r9
            int r3 = r12.yOri
            int r3 = r3 / 4
            int r3 = r3 * 3
            goto L_0x01f6
        L_0x0262:
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r3 = r8.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 < r4) goto L_0x0275
            int r3 = r12.yOri
            int r3 = r3 / 8
            goto L_0x0277
        L_0x0275:
            int r3 = r12.yOri
        L_0x0277:
            float r3 = (float) r3
        L_0x0278:
            float r8 = r12.dp7
            float r3 = r3 + r8
            r0.moveTo(r2, r3)
        L_0x027e:
            java.util.List<java.lang.String> r2 = r12.xValue
            int r2 = r2.size()
            if (r1 >= r2) goto L_0x034d
            float r2 = r12.xInit
            int r3 = r12.interval
            int r3 = r3 * r1
            float r3 = (float) r3
            float r2 = r2 + r3
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 < r7) goto L_0x02c6
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 > r6) goto L_0x02c6
            int r3 = r12.yOri
            int r8 = r3 / 128
            java.util.List<java.lang.Integer> r9 = r12.yHighValue
            java.lang.Object r9 = r9.get(r1)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r7
            int r8 = r8 * r9
            int r3 = r3 - r8
            int r8 = r12.yOri
            int r8 = r8 / 8
        L_0x02c2:
            int r3 = r3 - r8
        L_0x02c3:
            float r3 = (float) r3
            goto L_0x0343
        L_0x02c6:
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 >= r7) goto L_0x02f6
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 <= r5) goto L_0x02f6
            int r3 = r12.yOri
            int r8 = r3 / 80
            java.util.List<java.lang.Integer> r9 = r12.yHighValue
            java.lang.Object r9 = r9.get(r1)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r5
            int r8 = r8 * r9
            goto L_0x02c2
        L_0x02f6:
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 <= r6) goto L_0x032d
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 >= r4) goto L_0x032d
            int r3 = r12.yOri
            int r8 = r3 / 320
            java.util.List<java.lang.Integer> r9 = r12.yHighValue
            java.lang.Object r9 = r9.get(r1)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r6
            int r8 = r8 * r9
            int r3 = r3 - r8
            int r8 = r12.yOri
            int r8 = r8 / 4
            int r8 = r8 * 3
            goto L_0x02c2
        L_0x032d:
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 < r4) goto L_0x0340
            int r3 = r12.yOri
            int r3 = r3 / 8
            goto L_0x02c3
        L_0x0340:
            int r3 = r12.yOri
            goto L_0x02c3
        L_0x0343:
            float r8 = r12.dp7
            float r3 = r3 + r8
            r0.lineTo(r2, r3)
            int r1 = r1 + 1
            goto L_0x027e
        L_0x034d:
            android.graphics.Paint r1 = r12.linePaint
            r13.drawPath(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.ChartView.drawBrokenBPMLine(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0101  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenSPO2HPoint(android.graphics.Canvas r12) {
        /*
            r11 = this;
            r0 = 0
            r1 = 0
        L_0x0002:
            java.util.List<java.lang.String> r2 = r11.xValue
            int r2 = r2.size()
            if (r1 >= r2) goto L_0x010c
            float r2 = r11.xInit
            int r3 = r11.interval
            int r3 = r3 * r1
            float r3 = (float) r3
            float r2 = r2 + r3
            java.util.List<java.lang.Integer> r3 = r11.yBpmValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            r4 = 100
            r5 = 60
            if (r3 < r5) goto L_0x004e
            java.util.List<java.lang.Integer> r3 = r11.yBpmValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 >= r4) goto L_0x004e
            int r3 = r11.yOri
            int r4 = r3 / 160
            java.util.List<java.lang.Integer> r6 = r11.yBpmValue
            java.lang.Object r6 = r6.get(r1)
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            int r6 = r6 - r5
            int r4 = r4 * r6
            int r3 = r3 - r4
            int r4 = r11.yOri
            int r4 = r4 / 8
        L_0x004a:
            int r3 = r3 - r4
        L_0x004b:
            float r3 = (float) r3
            goto L_0x00d4
        L_0x004e:
            java.util.List<java.lang.Integer> r3 = r11.yBpmValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 >= r5) goto L_0x0080
            java.util.List<java.lang.Integer> r3 = r11.yBpmValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            r5 = 50
            if (r3 <= r5) goto L_0x0080
            int r3 = r11.yOri
            int r4 = r3 / 80
            java.util.List<java.lang.Integer> r6 = r11.yBpmValue
            java.lang.Object r6 = r6.get(r1)
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            int r6 = r6 - r5
            int r4 = r4 * r6
            goto L_0x004a
        L_0x0080:
            java.util.List<java.lang.Integer> r3 = r11.yBpmValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            r5 = 250(0xfa, float:3.5E-43)
            if (r3 < r4) goto L_0x00ba
            java.util.List<java.lang.Integer> r3 = r11.yBpmValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 > r5) goto L_0x00ba
            int r3 = r11.yOri
            int r5 = r3 / 8
            int r5 = r5 * 3
            int r5 = r3 - r5
            int r3 = r3 / 1200
            java.util.List<java.lang.Integer> r6 = r11.yBpmValue
            java.lang.Object r6 = r6.get(r1)
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            int r6 = r6 - r4
            int r3 = r3 * r6
            int r5 = r5 - r3
            float r3 = (float) r5
            goto L_0x00d4
        L_0x00ba:
            java.util.List<java.lang.Integer> r3 = r11.yBpmValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 <= r5) goto L_0x00d0
            int r3 = r11.yOri
            int r3 = r3 / 16
            int r3 = r3 * 9
            goto L_0x004b
        L_0x00d0:
            int r3 = r11.yOri
            goto L_0x004b
        L_0x00d4:
            float r4 = r11.dp7
            float r4 = r3 - r4
            java.util.List<java.lang.Integer> r5 = r11.yBpmValue
            java.lang.Object r5 = r5.get(r1)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r11.drawFloatBPMTextBox(r12, r2, r4, r5)
            android.graphics.Paint r4 = r11.linePaint
            android.graphics.Paint$Style r5 = android.graphics.Paint.Style.FILL
            r4.setStyle(r5)
            android.graphics.Paint r4 = r11.linePaint
            int r5 = r11.linecolor
            r4.setColor(r5)
            int r4 = r11.position
            if (r1 != r4) goto L_0x0101
            float r4 = r11.dp4
            android.graphics.Paint r5 = r11.linePaint
            r12.drawCircle(r2, r3, r4, r5)
            goto L_0x0108
        L_0x0101:
            float r4 = r11.dp2
            android.graphics.Paint r5 = r11.linePaint
            r12.drawCircle(r2, r3, r4, r5)
        L_0x0108:
            int r1 = r1 + 1
            goto L_0x0002
        L_0x010c:
            java.util.List<java.lang.String> r1 = r11.xValue
            int r1 = r1.size()
            if (r0 >= r1) goto L_0x0199
            float r1 = r11.xInit
            int r2 = r11.interval
            int r2 = r2 * r0
            float r2 = (float) r2
            float r1 = r1 + r2
            java.util.List<java.lang.Double> r2 = r11.ySpo2Value
            java.lang.Object r2 = r2.get(r0)
            java.lang.Double r2 = (java.lang.Double) r2
            double r2 = r2.doubleValue()
            r4 = 4636666922610458624(0x4058c00000000000, double:99.0)
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 < 0) goto L_0x0132
            r2 = r4
        L_0x0132:
            r4 = 4636385447633747968(0x4057c00000000000, double:95.0)
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 < 0) goto L_0x014c
            int r6 = r11.yOri
            double r7 = (double) r6
            int r9 = r6 / 20
            double r9 = (double) r9
            double r2 = r2 - r4
            double r9 = r9 * r2
            double r7 = r7 - r9
            int r6 = r6 / 8
            int r6 = r6 * 5
            double r2 = (double) r6
            double r7 = r7 - r2
            goto L_0x0154
        L_0x014c:
            int r2 = r11.yOri
            int r3 = r2 / 8
            int r3 = r3 * 5
            int r2 = r2 - r3
            double r7 = (double) r2
        L_0x0154:
            float r2 = (float) r7
            float r3 = r11.dp7
            float r6 = r2 - r3
            java.util.List<java.lang.Double> r3 = r11.ySpo2Value
            java.lang.Object r3 = r3.get(r0)
            java.lang.Double r3 = (java.lang.Double) r3
            double r7 = r3.doubleValue()
            r3 = r11
            r4 = r12
            r5 = r1
            r3.drawFloatSPO2HTextBox(r4, r5, r6, r7)
            android.graphics.Paint r3 = r11.linePaint
            android.graphics.Paint$Style r4 = android.graphics.Paint.Style.FILL
            r3.setStyle(r4)
            android.graphics.Paint r3 = r11.linePaint
            android.content.res.Resources r4 = r11.getResources()
            r5 = 2131099818(0x7f0600aa, float:1.7812E38)
            int r4 = r4.getColor(r5)
            r3.setColor(r4)
            int r3 = r11.position
            if (r0 != r3) goto L_0x018e
            float r3 = r11.dp4
            android.graphics.Paint r4 = r11.linePaint
            r12.drawCircle(r1, r2, r3, r4)
            goto L_0x0195
        L_0x018e:
            float r3 = r11.dp2
            android.graphics.Paint r4 = r11.linePaint
            r12.drawCircle(r1, r2, r3, r4)
        L_0x0195:
            int r0 = r0 + 1
            goto L_0x010c
        L_0x0199:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.ChartView.drawBrokenSPO2HPoint(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x01d9  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01e3  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x01f4  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0208  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenSPO2HLine(android.graphics.Canvas r15) {
        /*
            r14 = this;
            android.graphics.Paint r0 = r14.linePaint
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            r0.setStyle(r1)
            android.graphics.Paint r0 = r14.linePaint
            int r1 = r14.linecolor
            r0.setColor(r1)
            android.graphics.Path r0 = new android.graphics.Path
            r0.<init>()
            float r1 = r14.xInit
            int r2 = r14.interval
            r3 = 0
            int r2 = r2 * 0
            float r2 = (float) r2
            float r1 = r1 + r2
            java.util.List<java.lang.Integer> r2 = r14.yBpmValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            r4 = 250(0xfa, float:3.5E-43)
            r5 = 50
            r6 = 100
            r7 = 60
            if (r2 < r7) goto L_0x005c
            java.util.List<java.lang.Integer> r2 = r14.yBpmValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 >= r6) goto L_0x005c
            int r2 = r14.yOri
            int r8 = r2 / 160
            java.util.List<java.lang.Integer> r9 = r14.yBpmValue
            java.lang.Object r9 = r9.get(r3)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r7
            int r8 = r8 * r9
            int r2 = r2 - r8
            int r8 = r14.yOri
            int r8 = r8 / 8
        L_0x0058:
            int r2 = r2 - r8
        L_0x0059:
            float r2 = (float) r2
            goto L_0x00dd
        L_0x005c:
            java.util.List<java.lang.Integer> r2 = r14.yBpmValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 >= r7) goto L_0x008c
            java.util.List<java.lang.Integer> r2 = r14.yBpmValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 <= r5) goto L_0x008c
            int r2 = r14.yOri
            int r8 = r2 / 80
            java.util.List<java.lang.Integer> r9 = r14.yBpmValue
            java.lang.Object r9 = r9.get(r3)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r5
            int r8 = r8 * r9
            goto L_0x0058
        L_0x008c:
            java.util.List<java.lang.Integer> r2 = r14.yBpmValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 < r6) goto L_0x00c4
            java.util.List<java.lang.Integer> r2 = r14.yBpmValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 > r4) goto L_0x00c4
            int r2 = r14.yOri
            int r8 = r2 / 8
            int r8 = r8 * 3
            int r8 = r2 - r8
            int r2 = r2 / 1200
            java.util.List<java.lang.Integer> r9 = r14.yBpmValue
            java.lang.Object r9 = r9.get(r3)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r6
            int r2 = r2 * r9
            int r8 = r8 - r2
            float r2 = (float) r8
            goto L_0x00dd
        L_0x00c4:
            java.util.List<java.lang.Integer> r2 = r14.yBpmValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 <= r4) goto L_0x00d9
            int r2 = r14.yOri
            int r2 = r2 / 16
            int r2 = r2 * 9
            goto L_0x0059
        L_0x00d9:
            int r2 = r14.yOri
            goto L_0x0059
        L_0x00dd:
            r0.moveTo(r1, r2)
            r1 = 1
            r2 = 1
        L_0x00e2:
            java.util.List<java.lang.String> r8 = r14.xValue
            int r8 = r8.size()
            if (r2 >= r8) goto L_0x01b2
            float r8 = r14.xInit
            int r9 = r14.interval
            int r9 = r9 * r2
            float r9 = (float) r9
            float r8 = r8 + r9
            java.util.List<java.lang.Integer> r9 = r14.yBpmValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 < r7) goto L_0x012a
            java.util.List<java.lang.Integer> r9 = r14.yBpmValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 >= r6) goto L_0x012a
            int r9 = r14.yOri
            int r10 = r9 / 160
            java.util.List<java.lang.Integer> r11 = r14.yBpmValue
            java.lang.Object r11 = r11.get(r2)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r7
            int r10 = r10 * r11
            int r9 = r9 - r10
            int r10 = r14.yOri
            int r10 = r10 / 8
        L_0x0126:
            int r9 = r9 - r10
        L_0x0127:
            float r9 = (float) r9
            goto L_0x01ab
        L_0x012a:
            java.util.List<java.lang.Integer> r9 = r14.yBpmValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 >= r7) goto L_0x015a
            java.util.List<java.lang.Integer> r9 = r14.yBpmValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 <= r5) goto L_0x015a
            int r9 = r14.yOri
            int r10 = r9 / 80
            java.util.List<java.lang.Integer> r11 = r14.yBpmValue
            java.lang.Object r11 = r11.get(r2)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r5
            int r10 = r10 * r11
            goto L_0x0126
        L_0x015a:
            java.util.List<java.lang.Integer> r9 = r14.yBpmValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 < r6) goto L_0x0192
            java.util.List<java.lang.Integer> r9 = r14.yBpmValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 > r4) goto L_0x0192
            int r9 = r14.yOri
            int r10 = r9 / 8
            int r10 = r10 * 3
            int r10 = r9 - r10
            int r9 = r9 / 1200
            java.util.List<java.lang.Integer> r11 = r14.yBpmValue
            java.lang.Object r11 = r11.get(r2)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r6
            int r9 = r9 * r11
            int r10 = r10 - r9
            float r9 = (float) r10
            goto L_0x01ab
        L_0x0192:
            java.util.List<java.lang.Integer> r9 = r14.yBpmValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 <= r4) goto L_0x01a7
            int r9 = r14.yOri
            int r9 = r9 / 16
            int r9 = r9 * 9
            goto L_0x0127
        L_0x01a7:
            int r9 = r14.yOri
            goto L_0x0127
        L_0x01ab:
            r0.lineTo(r8, r9)
            int r2 = r2 + 1
            goto L_0x00e2
        L_0x01b2:
            android.graphics.Paint r2 = r14.linePaint
            r15.drawPath(r0, r2)
            android.graphics.Path r0 = new android.graphics.Path
            r0.<init>()
            float r2 = r14.xInit
            int r4 = r14.interval
            int r4 = r4 * 0
            float r4 = (float) r4
            float r2 = r2 + r4
            java.util.List<java.lang.Double> r4 = r14.ySpo2Value
            java.lang.Object r3 = r4.get(r3)
            java.lang.Double r3 = (java.lang.Double) r3
            double r3 = r3.doubleValue()
            r5 = 4636666922610458624(0x4058c00000000000, double:99.0)
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 < 0) goto L_0x01da
            r3 = r5
        L_0x01da:
            r7 = 4636385447633747968(0x4057c00000000000, double:95.0)
            int r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r9 < 0) goto L_0x01f4
            int r9 = r14.yOri
            double r10 = (double) r9
            int r12 = r9 / 20
            double r12 = (double) r12
            double r3 = r3 - r7
            double r12 = r12 * r3
            double r10 = r10 - r12
            int r9 = r9 / 8
            int r9 = r9 * 5
            double r3 = (double) r9
            double r10 = r10 - r3
            goto L_0x01fc
        L_0x01f4:
            int r3 = r14.yOri
            int r4 = r3 / 8
            int r4 = r4 * 5
            int r3 = r3 - r4
            double r10 = (double) r3
        L_0x01fc:
            float r3 = (float) r10
            r0.moveTo(r2, r3)
        L_0x0200:
            java.util.List<java.lang.String> r2 = r14.xValue
            int r2 = r2.size()
            if (r1 >= r2) goto L_0x0245
            float r2 = r14.xInit
            int r3 = r14.interval
            int r3 = r3 * r1
            float r3 = (float) r3
            float r2 = r2 + r3
            java.util.List<java.lang.Double> r3 = r14.ySpo2Value
            java.lang.Object r3 = r3.get(r1)
            java.lang.Double r3 = (java.lang.Double) r3
            double r3 = r3.doubleValue()
            int r9 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r9 < 0) goto L_0x0221
            r3 = r5
        L_0x0221:
            int r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r9 < 0) goto L_0x0236
            int r9 = r14.yOri
            double r10 = (double) r9
            int r12 = r9 / 20
            double r12 = (double) r12
            double r3 = r3 - r7
            double r12 = r12 * r3
            double r10 = r10 - r12
            int r9 = r9 / 8
            int r9 = r9 * 5
            double r3 = (double) r9
            double r10 = r10 - r3
            goto L_0x023e
        L_0x0236:
            int r3 = r14.yOri
            int r4 = r3 / 8
            int r4 = r4 * 5
            int r3 = r3 - r4
            double r10 = (double) r3
        L_0x023e:
            float r3 = (float) r10
            r0.lineTo(r2, r3)
            int r1 = r1 + 1
            goto L_0x0200
        L_0x0245:
            android.graphics.Paint r1 = r14.linePaint
            android.content.res.Resources r2 = r14.getResources()
            r3 = 2131099818(0x7f0600aa, float:1.7812E38)
            int r2 = r2.getColor(r3)
            r1.setColor(r2)
            android.graphics.Paint r1 = r14.linePaint
            r15.drawPath(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.ChartView.drawBrokenSPO2HLine(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x013a  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0142  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenBGPoint(android.graphics.Canvas r13) {
        /*
            r12 = this;
            r0 = 0
        L_0x0001:
            java.util.List<java.lang.String> r1 = r12.xValue
            int r1 = r1.size()
            if (r0 >= r1) goto L_0x014d
            float r1 = r12.xInit
            int r2 = r12.interval
            int r2 = r2 * r0
            float r2 = (float) r2
            float r1 = r1 + r2
            java.util.List<java.lang.Double> r2 = r12.yBGValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Double r2 = (java.lang.Double) r2
            double r2 = r2.doubleValue()
            r4 = 4620468037700760371(0x401f333333333333, double:7.8)
            r6 = 4615288898129284301(0x400ccccccccccccd, double:3.6)
            int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r8 < 0) goto L_0x0061
            java.util.List<java.lang.Double> r2 = r12.yBGValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Double r2 = (java.lang.Double) r2
            double r2 = r2.doubleValue()
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 > 0) goto L_0x0061
            int r2 = r12.yOri
            double r3 = (double) r2
            double r8 = (double) r2
            r10 = 4620918397663497421(0x4020cccccccccccd, double:8.4)
            double r8 = r8 / r10
            java.util.List<java.lang.Double> r2 = r12.yBGValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Double r2 = (java.lang.Double) r2
            double r10 = r2.doubleValue()
            double r10 = r10 - r6
            double r8 = r8 * r10
            double r3 = r3 - r8
            int r2 = r12.yOri
            int r5 = r2 / 8
            double r5 = (double) r5
            double r3 = r3 - r5
            int r2 = r2 / 16
        L_0x005d:
            double r5 = (double) r2
            double r3 = r3 - r5
            goto L_0x0108
        L_0x0061:
            java.util.List<java.lang.Double> r2 = r12.yBGValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Double r2 = (java.lang.Double) r2
            double r2 = r2.doubleValue()
            int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x00a2
            java.util.List<java.lang.Double> r2 = r12.yBGValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Double r2 = (java.lang.Double) r2
            double r2 = r2.doubleValue()
            r6 = 4613937818241073152(0x4008000000000000, double:3.0)
            int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r8 < 0) goto L_0x00a2
            int r2 = r12.yOri
            double r3 = (double) r2
            double r8 = (double) r2
            r10 = 4617090337980232499(0x4013333333333333, double:4.8)
            double r8 = r8 / r10
            java.util.List<java.lang.Double> r2 = r12.yBGValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Double r2 = (java.lang.Double) r2
            double r10 = r2.doubleValue()
            double r10 = r10 - r6
            double r8 = r8 * r10
            double r3 = r3 - r8
            int r2 = r12.yOri
            int r2 = r2 / 16
            goto L_0x005d
        L_0x00a2:
            java.util.List<java.lang.Double> r2 = r12.yBGValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Double r2 = (java.lang.Double) r2
            double r2 = r2.doubleValue()
            r6 = 4621256167635550208(0x4022000000000000, double:9.0)
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 <= 0) goto L_0x00ec
            java.util.List<java.lang.Double> r2 = r12.yBGValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Double r2 = (java.lang.Double) r2
            double r2 = r2.doubleValue()
            int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x00ec
            int r2 = r12.yOri
            double r6 = (double) r2
            double r2 = (double) r2
            r8 = 4621593937607602995(0x4023333333333333, double:9.6)
            double r2 = r2 / r8
            java.util.List<java.lang.Double> r8 = r12.yBGValue
            java.lang.Object r8 = r8.get(r0)
            java.lang.Double r8 = (java.lang.Double) r8
            double r8 = r8.doubleValue()
            double r8 = r8 - r4
            double r2 = r2 * r8
            double r6 = r6 - r2
            int r2 = r12.yOri
            int r3 = r2 / 8
            int r3 = r3 * 5
            double r3 = (double) r3
            double r6 = r6 - r3
            int r2 = r2 / 16
            double r2 = (double) r2
            double r3 = r6 - r2
            goto L_0x0108
        L_0x00ec:
            java.util.List<java.lang.Double> r2 = r12.yBGValue
            java.lang.Object r2 = r2.get(r0)
            java.lang.Double r2 = (java.lang.Double) r2
            double r2 = r2.doubleValue()
            int r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r4 < 0) goto L_0x0105
            int r2 = r12.yOri
            int r3 = r2 / 4
            int r2 = r2 / 16
            int r3 = r3 + r2
            double r3 = (double) r3
            goto L_0x0108
        L_0x0105:
            int r2 = r12.yOri
            double r3 = (double) r2
        L_0x0108:
            float r2 = (float) r3
            float r3 = r12.dp7
            float r6 = r2 - r3
            java.util.List<java.lang.Double> r3 = r12.yBGValue
            java.lang.Object r3 = r3.get(r0)
            java.lang.Double r3 = (java.lang.Double) r3
            double r7 = r3.doubleValue()
            java.util.List<java.lang.String> r3 = r12.yState
            java.lang.Object r3 = r3.get(r0)
            r9 = r3
            java.lang.String r9 = (java.lang.String) r9
            r3 = r12
            r4 = r13
            r5 = r1
            r3.drawFloatBGTextBox(r4, r5, r6, r7, r9)
            android.graphics.Paint r3 = r12.linePaint
            android.graphics.Paint$Style r4 = android.graphics.Paint.Style.FILL
            r3.setStyle(r4)
            android.graphics.Paint r3 = r12.linePaint
            int r4 = r12.linecolor
            r3.setColor(r4)
            int r3 = r12.position
            if (r0 != r3) goto L_0x0142
            float r3 = r12.dp4
            android.graphics.Paint r4 = r12.linePaint
            r13.drawCircle(r1, r2, r3, r4)
            goto L_0x0149
        L_0x0142:
            float r3 = r12.dp2
            android.graphics.Paint r4 = r12.linePaint
            r13.drawCircle(r1, r2, r3, r4)
        L_0x0149:
            int r0 = r0 + 1
            goto L_0x0001
        L_0x014d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.ChartView.drawBrokenBGPoint(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0123  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenBGLine(android.graphics.Canvas r26) {
        /*
            r25 = this;
            r0 = r25
            android.graphics.Paint r1 = r0.linePaint
            android.graphics.Paint$Style r2 = android.graphics.Paint.Style.STROKE
            r1.setStyle(r2)
            android.graphics.Paint r1 = r0.linePaint
            int r2 = r0.linecolor
            r1.setColor(r2)
            android.graphics.Path r1 = new android.graphics.Path
            r1.<init>()
            float r2 = r0.xInit
            int r3 = r0.interval
            r4 = 0
            int r3 = r3 * 0
            float r3 = (float) r3
            float r2 = r2 + r3
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            r9 = 4617090337980232499(0x4013333333333333, double:4.8)
            r11 = 4620918397663497421(0x4020cccccccccccd, double:8.4)
            r13 = 4621256167635550208(0x4022000000000000, double:9.0)
            r15 = 4613937818241073152(0x4008000000000000, double:3.0)
            r17 = 4620468037700760371(0x401f333333333333, double:7.8)
            r19 = 4615288898129284301(0x400ccccccccccccd, double:3.6)
            int r3 = (r5 > r19 ? 1 : (r5 == r19 ? 0 : -1))
            if (r3 < 0) goto L_0x0078
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            int r3 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
            if (r3 > 0) goto L_0x0078
            int r3 = r0.yOri
            double r5 = (double) r3
            double r7 = (double) r3
            double r7 = r7 / r11
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r3 = r3.doubleValue()
            double r3 = r3 - r19
            double r7 = r7 * r3
            double r5 = r5 - r7
            int r3 = r0.yOri
            int r4 = r3 / 8
            double r7 = (double) r4
            double r5 = r5 - r7
            int r3 = r3 / 16
        L_0x0074:
            double r3 = (double) r3
            double r5 = r5 - r3
            goto L_0x0116
        L_0x0078:
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            int r3 = (r5 > r19 ? 1 : (r5 == r19 ? 0 : -1))
            if (r3 >= 0) goto L_0x00b2
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            int r3 = (r5 > r15 ? 1 : (r5 == r15 ? 0 : -1))
            if (r3 < 0) goto L_0x00b2
            int r3 = r0.yOri
            double r5 = (double) r3
            double r7 = (double) r3
            double r7 = r7 / r9
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r3 = r3.doubleValue()
            double r3 = r3 - r15
            double r7 = r7 * r3
            double r5 = r5 - r7
            int r3 = r0.yOri
            int r3 = r3 / 16
            goto L_0x0074
        L_0x00b2:
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            int r3 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
            if (r3 <= 0) goto L_0x00fa
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            int r3 = (r5 > r13 ? 1 : (r5 == r13 ? 0 : -1))
            if (r3 >= 0) goto L_0x00fa
            int r3 = r0.yOri
            double r5 = (double) r3
            double r7 = (double) r3
            r21 = 4621593937607602995(0x4023333333333333, double:9.6)
            double r7 = r7 / r21
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r3 = r3.doubleValue()
            double r3 = r3 - r17
            double r7 = r7 * r3
            double r5 = r5 - r7
            int r3 = r0.yOri
            int r4 = r3 / 8
            int r4 = r4 * 5
            double r7 = (double) r4
            double r5 = r5 - r7
            int r3 = r3 / 16
            goto L_0x0074
        L_0x00fa:
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r3 = r3.doubleValue()
            int r5 = (r3 > r13 ? 1 : (r3 == r13 ? 0 : -1))
            if (r5 < 0) goto L_0x0113
            int r3 = r0.yOri
            int r4 = r3 / 4
            int r3 = r3 / 16
            int r4 = r4 + r3
            double r5 = (double) r4
            goto L_0x0116
        L_0x0113:
            int r3 = r0.yOri
            double r5 = (double) r3
        L_0x0116:
            float r3 = (float) r5
            r1.moveTo(r2, r3)
            r2 = 1
        L_0x011b:
            java.util.List<java.lang.String> r3 = r0.xValue
            int r3 = r3.size()
            if (r2 >= r3) goto L_0x021f
            float r3 = r0.xInit
            int r4 = r0.interval
            int r4 = r4 * r2
            float r4 = (float) r4
            float r3 = r3 + r4
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r19 ? 1 : (r4 == r19 ? 0 : -1))
            if (r6 < 0) goto L_0x0172
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r6 > 0) goto L_0x0172
            int r4 = r0.yOri
            double r5 = (double) r4
            double r7 = (double) r4
            double r7 = r7 / r11
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r23 = r4.doubleValue()
            double r23 = r23 - r19
            double r7 = r7 * r23
            double r5 = r5 - r7
            int r4 = r0.yOri
            int r7 = r4 / 8
            double r7 = (double) r7
            double r5 = r5 - r7
            int r4 = r4 / 16
        L_0x0169:
            double r7 = (double) r4
            double r5 = r5 - r7
            r21 = 4621593937607602995(0x4023333333333333, double:9.6)
            goto L_0x0217
        L_0x0172:
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r19 ? 1 : (r4 == r19 ? 0 : -1))
            if (r6 >= 0) goto L_0x01ad
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r15 ? 1 : (r4 == r15 ? 0 : -1))
            if (r6 < 0) goto L_0x01ad
            int r4 = r0.yOri
            double r5 = (double) r4
            double r7 = (double) r4
            double r7 = r7 / r9
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r23 = r4.doubleValue()
            double r23 = r23 - r15
            double r7 = r7 * r23
            double r5 = r5 - r7
            int r4 = r0.yOri
            int r4 = r4 / 16
            goto L_0x0169
        L_0x01ad:
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r6 <= 0) goto L_0x01f6
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r6 >= 0) goto L_0x01f6
            int r4 = r0.yOri
            double r5 = (double) r4
            double r7 = (double) r4
            r21 = 4621593937607602995(0x4023333333333333, double:9.6)
            double r7 = r7 / r21
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r23 = r4.doubleValue()
            double r23 = r23 - r17
            double r7 = r7 * r23
            double r5 = r5 - r7
            int r4 = r0.yOri
            int r7 = r4 / 8
            int r7 = r7 * 5
            double r7 = (double) r7
            double r5 = r5 - r7
            int r4 = r4 / 16
            double r7 = (double) r4
            double r5 = r5 - r7
            goto L_0x0217
        L_0x01f6:
            r21 = 4621593937607602995(0x4023333333333333, double:9.6)
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r6 < 0) goto L_0x0214
            int r4 = r0.yOri
            int r5 = r4 / 4
            int r4 = r4 / 16
            int r5 = r5 + r4
            double r5 = (double) r5
            goto L_0x0217
        L_0x0214:
            int r4 = r0.yOri
            double r5 = (double) r4
        L_0x0217:
            float r4 = (float) r5
            r1.lineTo(r3, r4)
            int r2 = r2 + 1
            goto L_0x011b
        L_0x021f:
            android.graphics.Paint r2 = r0.linePaint
            r3 = r26
            r3.drawPath(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.ChartView.drawBrokenBGLine(android.graphics.Canvas):void");
    }

    private void drawFloatTextBox(Canvas canvas, float f, float f2, float f3) {
        if (SpManager.getTemperaTrueUnit() == 1) {
            f3 = (float) TemperatureUtils.tempeConversionDouble((double) f3);
        }
        this.linePaint.setColor(getResources().getColor(C1624R.C1626color.darkGreen));
        this.linePaint.setStyle(Paint.Style.FILL);
        this.linePaint.setTextSize(getResources().getDimension(C1624R.dimen.dp_14));
        Rect textBounds = getTextBounds(f3 + "", this.linePaint);
        this.pointTextHight = textBounds.height();
        canvas.drawText(f3 + "", f - ((float) (textBounds.width() / 2)), f2 - ((this.dp18 - ((float) textBounds.height())) / 2.0f), this.linePaint);
    }

    private void drawFloatBPMTextBox(Canvas canvas, float f, float f2, int i) {
        this.linePaint.setColor(getResources().getColor(C1624R.C1626color.darkGreen));
        this.linePaint.setStyle(Paint.Style.FILL);
        this.linePaint.setTextSize(this.dp14);
        Rect textBounds = getTextBounds(i + "", this.linePaint);
        this.pointTextHight = textBounds.height();
        canvas.drawText(i + "", f - ((float) (textBounds.width() / 2)), f2 - ((this.dp18 - ((float) textBounds.height())) / 2.0f), this.linePaint);
    }

    private void drawFloatSPO2HTextBox(Canvas canvas, float f, float f2, double d) {
        this.linePaint.setColor(getResources().getColor(C1624R.C1626color.lightGreen));
        this.linePaint.setStyle(Paint.Style.FILL);
        this.linePaint.setTextSize(this.dp14);
        Rect textBounds = getTextBounds(d + "%", this.linePaint);
        this.pointTextHight = textBounds.height();
        canvas.drawText(d + "%", f - ((float) (textBounds.width() / 2)), f2 - ((this.dp18 - ((float) textBounds.height())) / 2.0f), this.linePaint);
    }

    private void drawFloatBGTextBox(Canvas canvas, float f, float f2, double d, String str) {
        double bgValue = UnitUtil.getBgValue(d);
        this.linePaint.setColor(getResources().getColor(C1624R.C1626color.darkGreen));
        this.linePaint.setStyle(Paint.Style.FILL);
        this.linePaint.setTextSize(this.dp14);
        Rect textBounds = getTextBounds(bgValue + "", this.linePaint);
        this.pointTextHight = textBounds.height();
        canvas.drawText(bgValue + "", f - ((float) (textBounds.width() / 2)), f2 - ((this.dp18 - ((float) textBounds.height())) / 2.0f), this.linePaint);
        this.xyTextPaint.setTextSize(this.dp12);
        this.xyTextPaint.setColor(getResources().getColor(C1624R.C1626color.darkGreen));
        canvas.drawText(str, f - ((float) (textBounds.width() / 2)), f2 - ((this.dp17 - ((float) textBounds.height())) * 3.0f), this.xyTextPaint);
    }

    private void drawPointPoint(Canvas canvas) {
        float f;
        int i;
        for (int i2 = 0; i2 < this.xValue.size(); i2++) {
            float f2 = this.xInit + ((float) (this.interval * i2));
            if (this.yValue.get(i2).floatValue() <= 0.0f || this.yValue.get(i2).floatValue() > 100.0f) {
                if (this.yValue.get(i2).floatValue() <= 0.0f) {
                    i = this.yOri;
                } else {
                    i = this.yOri / 8;
                }
                f = (float) i;
            } else {
                int i3 = this.yOri;
                f = ((float) i3) - (((float) ((i3 * 7) / 800)) * this.yValue.get(i2).floatValue());
            }
            drawFloatTextBox(canvas, f2, f - this.dp7, this.yValue.get(i2).floatValue());
            this.linePaint.setStyle(Paint.Style.FILL);
            this.linePaint.setColor(this.linecolor);
            canvas.drawCircle(f2, f, this.dp2, this.linePaint);
        }
    }

    private void drawPointLine(Canvas canvas) {
        float f;
        float f2;
        int i;
        int i2;
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.linePaint.setColor(this.linecolor);
        Path path = new Path();
        float f3 = this.xInit + ((float) (this.interval * 0));
        if (this.yValue.get(0).floatValue() <= 0.0f || this.yValue.get(0).floatValue() > 100.0f) {
            if (this.yValue.get(0).floatValue() <= 0.0f) {
                i2 = this.yOri;
            } else {
                i2 = this.yOri / 8;
            }
            f = (float) i2;
        } else {
            int i3 = this.yOri;
            f = ((float) i3) - (((float) ((i3 * 7) / 800)) * this.yValue.get(0).floatValue());
        }
        path.moveTo(f3, f);
        for (int i4 = 1; i4 < this.xValue.size(); i4++) {
            float f4 = this.xInit + ((float) (this.interval * i4));
            if (this.yValue.get(i4).floatValue() <= 0.0f || this.yValue.get(i4).floatValue() > 100.0f) {
                if (this.yValue.get(i4).floatValue() <= 0.0f) {
                    i = this.yOri;
                } else {
                    i = this.yOri / 8;
                }
                f2 = (float) i;
            } else {
                int i5 = this.yOri;
                f2 = ((float) i5) - (((float) ((i5 * 7) / 800)) * this.yValue.get(i4).floatValue());
            }
            path.lineTo(f4, f2);
        }
        canvas.drawPath(path, this.linePaint);
    }

    private void drawXY(Canvas canvas) {
        new Path();
        for (int i = 0; i < this.xValue.size(); i++) {
            float f = this.xInit + ((float) (this.interval * i));
            if (f >= ((float) this.xOri)) {
                this.xyTextPaint.setColor(this.xytextcolor);
                this.xyTextPaint.setStyle(Paint.Style.FILL);
                String str = this.xValue.get(i);
                StaticLayout staticLayout = new StaticLayout(str, this.xyTextPaint, getTextBounds(str + " : ", this.xyTextPaint).width() / 2, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                canvas.save();
                canvas.translate(f - ((float) (this.xValueRect.width() / 4)), (float) this.yOri);
                staticLayout.draw(canvas);
                canvas.restore();
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (action == 2) {
            int i = x - this.endX;
            LoggerUtil.m112d(i + "ddddddd");
            if (Math.abs(i) + 5 < Math.abs(y - this.endY)) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        this.endX = x;
        this.endY = y;
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i = this.width;
        this.minXInit = (((float) i) - (((float) (i - this.xOri)) * 0.1f)) - ((float) (this.interval * (this.xValue.size() - 1)));
        this.maxXInit = this.xInit;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startX = motionEvent.getX();
            return true;
        } else if (action != 2) {
            return super.onTouchEvent(motionEvent);
        } else {
            if (this.interval * this.xValue.size() > this.width - this.xOri) {
                float x = motionEvent.getX() - this.startX;
                this.startX = motionEvent.getX();
                float f = this.xInit;
                float f2 = this.minXInit;
                if (f + x < f2) {
                    this.xInit = f2;
                } else if (f + x <= this.maxXInit) {
                    this.xInit = f + x;
                } else if (f >= ((float) (this.xValueRect.width() / 2))) {
                    this.xInit = (float) (this.xValueRect.width() / 2);
                } else {
                    this.xInit = this.maxXInit + x;
                }
            }
            invalidate();
            return true;
        }
    }

    public void setxValue(List<String> list) {
        this.xValue = list;
    }

    public void setyValue(List<Float> list) {
        this.yValue = list;
        invalidate();
    }

    public void setValue(List<String> list, List<Float> list2, int i) {
        this.xValue.clear();
        this.yValue.clear();
        this.xValue.addAll(list);
        this.yValue.addAll(list2);
        this.position = i;
        this.xInit = (float) (this.xValueRect.width() / 2);
        invalidate();
    }

    public void setPointValue(List<String> list, List<Float> list2) {
        this.xValue.clear();
        this.yValue.clear();
        this.xValue.addAll(list);
        this.yValue.addAll(list2);
        this.xInit = (float) (this.xValueRect.width() / 2);
        invalidate();
    }

    public void setBGValue(List<String> list, List<Double> list2, List<String> list3, int i, String str, boolean z) {
        this.xValue.clear();
        this.yBGValue.clear();
        this.yState.clear();
        this.xValue.addAll(list);
        this.yBGValue.addAll(list2);
        this.yState.addAll(list3);
        this.position = i;
        this.lingType = str;
        this.isBgList = z;
        this.xInit = (float) (this.xValueRect.width() / 2);
        invalidate();
    }

    public void setBPMValue(List<String> list, List<Integer> list2, List<Integer> list3, int i, String str) {
        this.xValue = list;
        this.yLowValue = list2;
        this.yHighValue = list3;
        this.position = i;
        this.lingType = str;
        this.xInit = (float) (this.xValueRect.width() / 2);
        invalidate();
    }

    public void setSPO2Value(List<String> list, List<Integer> list2, List<Double> list3, int i, String str) {
        this.xValue = list;
        this.yBpmValue = list2;
        this.ySpo2Value = list3;
        this.position = i;
        this.lingType = str;
        this.xInit = (float) (this.xValueRect.width() / 2);
        invalidate();
    }

    public List<String> getxValue() {
        return this.xValue;
    }

    public List<Float> getyValue() {
        return this.yValue;
    }

    private Rect getTextBounds(String str, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect;
    }
}
