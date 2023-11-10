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
import android.view.View;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.commonbase.utils.TemperatureUtils;
import com.p020kl.healthmonitor.C1624R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.views.PdfChartView */
public class PdfChartView extends View {
    private int endX;
    private int endY;
    private int height;
    private int interval;
    private boolean isScroll;
    private Paint linePaint;
    private int linecolor;
    private String lingType;
    private float maxXInit;
    private float minXInit;
    private int pointTextHight;
    private int position;
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
    private List<Integer> yHighValue;
    private List<Integer> yLowValue;
    private int yOri;
    private List<String> yState;
    private List<Float> yValue;

    public PdfChartView(Context context) {
        this(context, (AttributeSet) null);
    }

    public PdfChartView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PdfChartView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.xylinewidth = SizeUtils.dp2px(1.0f);
        this.xytextsize = SizeUtils.sp2px(getContext(), 14.0f);
        this.isScroll = false;
        this.lingType = "BTM";
        this.xValue = new ArrayList();
        this.yValue = new ArrayList();
        this.yLowValue = new ArrayList();
        this.yHighValue = new ArrayList();
        this.yBGValue = new ArrayList();
        this.yState = new ArrayList();
        init(context, attributeSet, i);
        initPaint();
        setBackgroundColor(BaseApplication.getInstance().getResources().getColor(C1624R.C1626color.grayeee));
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
    public void onMeasure(int i, int i2) {
        this.xInit = (float) (this.xValueRect.width() / 2);
        int size = this.interval * this.xValue.size();
        Log.d("hahasize", size + "");
        setMeasuredDimension(size, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            this.width = getWidth();
            this.height = getHeight();
            int dp2px = SizeUtils.dp2px(2.0f);
            int dp2px2 = SizeUtils.dp2px(10.0f);
            this.xOri = 0;
            this.yOri = (int) ((((float) (this.height - dp2px)) - ((float) (this.xValueRect.height() * 2))) - ((float) dp2px2));
            this.xInit = (float) (this.xValueRect.width() / 2);
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Log.d("绘制", "hahahuizhi");
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

    /* JADX WARNING: Removed duplicated region for block: B:26:0x011d  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0123  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenPoint(android.graphics.Canvas r10) {
        /*
            r9 = this;
            r0 = 1073741824(0x40000000, float:2.0)
            int r1 = com.p020kl.commonbase.utils.SizeUtils.dp2px(r0)
            float r1 = (float) r1
            r2 = 1082130432(0x40800000, float:4.0)
            int r2 = com.p020kl.commonbase.utils.SizeUtils.dp2px(r2)
            float r2 = (float) r2
            int r0 = com.p020kl.commonbase.utils.SizeUtils.dp2px(r0)
            float r0 = (float) r0
            r3 = 0
        L_0x0014:
            java.util.List<java.lang.String> r4 = r9.xValue
            int r4 = r4.size()
            if (r3 >= r4) goto L_0x012c
            float r4 = r9.xInit
            int r5 = r9.interval
            int r5 = r5 * r3
            float r5 = (float) r5
            float r4 = r4 + r5
            java.util.List<java.lang.Float> r5 = r9.yValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            r6 = 1108606976(0x42140000, float:37.0)
            r7 = 1108344832(0x42100000, float:36.0)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 < 0) goto L_0x0066
            java.util.List<java.lang.Float> r5 = r9.yValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto L_0x0066
            int r5 = r9.yOri
            float r6 = (float) r5
            int r5 = r5 / 2
            float r5 = (float) r5
            java.util.List<java.lang.Float> r8 = r9.yValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Float r8 = (java.lang.Float) r8
            float r8 = r8.floatValue()
            float r8 = r8 - r7
            float r5 = r5 * r8
            float r6 = r6 - r5
            int r5 = r9.yOri
            int r5 = r5 / 8
            float r5 = (float) r5
        L_0x0063:
            float r6 = r6 - r5
            goto L_0x00fa
        L_0x0066:
            java.util.List<java.lang.Float> r5 = r9.yValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x009e
            java.util.List<java.lang.Float> r5 = r9.yValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            r7 = 1108082688(0x420c0000, float:35.0)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 < 0) goto L_0x009e
            int r5 = r9.yOri
            float r6 = (float) r5
            int r5 = r5 / 8
            float r5 = (float) r5
            java.util.List<java.lang.Float> r8 = r9.yValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Float r8 = (java.lang.Float) r8
            float r8 = r8.floatValue()
            float r8 = r8 - r7
            float r5 = r5 * r8
            goto L_0x0063
        L_0x009e:
            java.util.List<java.lang.Float> r5 = r9.yValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            r7 = 1109655552(0x42240000, float:41.0)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x00e0
            java.util.List<java.lang.Float> r5 = r9.yValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x00e0
            int r5 = r9.yOri
            float r7 = (float) r5
            int r5 = r5 / 24
            float r5 = (float) r5
            java.util.List<java.lang.Float> r8 = r9.yValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Float r8 = (java.lang.Float) r8
            float r8 = r8.floatValue()
            float r8 = r8 - r6
            float r5 = r5 * r8
            float r7 = r7 - r5
            int r5 = r9.yOri
            int r5 = r5 / 8
            int r5 = r5 * 5
            float r5 = (float) r5
            float r6 = r7 - r5
            goto L_0x00fa
        L_0x00e0:
            java.util.List<java.lang.Float> r5 = r9.yValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 < 0) goto L_0x00f7
            int r5 = r9.yOri
            int r5 = r5 / 24
            int r5 = r5 * 5
            goto L_0x00f9
        L_0x00f7:
            int r5 = r9.yOri
        L_0x00f9:
            float r6 = (float) r5
        L_0x00fa:
            float r5 = r6 - r0
            java.util.List<java.lang.Float> r7 = r9.yValue
            java.lang.Object r7 = r7.get(r3)
            java.lang.Float r7 = (java.lang.Float) r7
            float r7 = r7.floatValue()
            r9.drawFloatTextBox(r10, r4, r5, r7)
            android.graphics.Paint r5 = r9.linePaint
            android.graphics.Paint$Style r7 = android.graphics.Paint.Style.FILL
            r5.setStyle(r7)
            android.graphics.Paint r5 = r9.linePaint
            int r7 = r9.linecolor
            r5.setColor(r7)
            int r5 = r9.position
            if (r3 != r5) goto L_0x0123
            android.graphics.Paint r5 = r9.linePaint
            r10.drawCircle(r4, r6, r2, r5)
            goto L_0x0128
        L_0x0123:
            android.graphics.Paint r5 = r9.linePaint
            r10.drawCircle(r4, r6, r1, r5)
        L_0x0128:
            int r3 = r3 + 1
            goto L_0x0014
        L_0x012c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.PdfChartView.drawBrokenPoint(android.graphics.Canvas):void");
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
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.PdfChartView.drawBrokenLine(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01fe  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0204  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenBPMPoint(android.graphics.Canvas r13) {
        /*
            r12 = this;
            r0 = 1073741824(0x40000000, float:2.0)
            int r1 = com.p020kl.commonbase.utils.SizeUtils.dp2px(r0)
            float r1 = (float) r1
            r2 = 1082130432(0x40800000, float:4.0)
            int r2 = com.p020kl.commonbase.utils.SizeUtils.dp2px(r2)
            float r2 = (float) r2
            int r0 = com.p020kl.commonbase.utils.SizeUtils.dp2px(r0)
            float r0 = (float) r0
            r3 = 0
            r4 = 0
        L_0x0015:
            java.util.List<java.lang.String> r5 = r12.xValue
            int r5 = r5.size()
            r6 = 180(0xb4, float:2.52E-43)
            r7 = 50
            r8 = 140(0x8c, float:1.96E-43)
            r9 = 60
            if (r4 >= r5) goto L_0x0116
            float r5 = r12.xInit
            int r10 = r12.interval
            int r10 = r10 * r4
            float r10 = (float) r10
            float r5 = r5 + r10
            java.util.List<java.lang.Integer> r10 = r12.yLowValue
            java.lang.Object r10 = r10.get(r4)
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            if (r10 < r9) goto L_0x0065
            java.util.List<java.lang.Integer> r10 = r12.yLowValue
            java.lang.Object r10 = r10.get(r4)
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            if (r10 > r8) goto L_0x0065
            int r6 = r12.yOri
            int r7 = r6 / 128
            java.util.List<java.lang.Integer> r8 = r12.yLowValue
            java.lang.Object r8 = r8.get(r4)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            int r8 = r8 - r9
            int r7 = r7 * r8
            int r6 = r6 - r7
            int r7 = r12.yOri
            int r7 = r7 / 8
        L_0x0061:
            int r6 = r6 - r7
        L_0x0062:
            float r6 = (float) r6
            goto L_0x00e4
        L_0x0065:
            java.util.List<java.lang.Integer> r10 = r12.yLowValue
            java.lang.Object r10 = r10.get(r4)
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            if (r10 >= r9) goto L_0x0096
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r4)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 <= r7) goto L_0x0096
            int r6 = r12.yOri
            int r8 = r6 / 80
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r4)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r7
            int r8 = r8 * r9
            int r6 = r6 - r8
            goto L_0x0062
        L_0x0096:
            java.util.List<java.lang.Integer> r7 = r12.yLowValue
            java.lang.Object r7 = r7.get(r4)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 <= r8) goto L_0x00cd
            java.util.List<java.lang.Integer> r7 = r12.yLowValue
            java.lang.Object r7 = r7.get(r4)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 >= r6) goto L_0x00cd
            int r6 = r12.yOri
            int r7 = r6 / 320
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r4)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r8
            int r7 = r7 * r9
            int r6 = r6 - r7
            int r7 = r12.yOri
            int r7 = r7 / 4
            int r7 = r7 * 3
            goto L_0x0061
        L_0x00cd:
            java.util.List<java.lang.Integer> r7 = r12.yLowValue
            java.lang.Object r7 = r7.get(r4)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 < r6) goto L_0x00e0
            int r6 = r12.yOri
            int r6 = r6 / 8
            goto L_0x0062
        L_0x00e0:
            int r6 = r12.yOri
            goto L_0x0062
        L_0x00e4:
            float r7 = r6 - r0
            java.util.List<java.lang.Integer> r8 = r12.yLowValue
            java.lang.Object r8 = r8.get(r4)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            r12.drawFloatBPMTextBox(r13, r5, r7, r8)
            android.graphics.Paint r7 = r12.linePaint
            android.graphics.Paint$Style r8 = android.graphics.Paint.Style.FILL
            r7.setStyle(r8)
            android.graphics.Paint r7 = r12.linePaint
            int r8 = r12.linecolor
            r7.setColor(r8)
            int r7 = r12.position
            if (r4 != r7) goto L_0x010d
            android.graphics.Paint r7 = r12.linePaint
            r13.drawCircle(r5, r6, r2, r7)
            goto L_0x0112
        L_0x010d:
            android.graphics.Paint r7 = r12.linePaint
            r13.drawCircle(r5, r6, r1, r7)
        L_0x0112:
            int r4 = r4 + 1
            goto L_0x0015
        L_0x0116:
            java.util.List<java.lang.String> r4 = r12.xValue
            int r4 = r4.size()
            if (r3 >= r4) goto L_0x020d
            float r4 = r12.xInit
            int r5 = r12.interval
            int r5 = r5 * r3
            float r5 = (float) r5
            float r4 = r4 + r5
            java.util.List<java.lang.Integer> r5 = r12.yHighValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            if (r5 < r9) goto L_0x015e
            java.util.List<java.lang.Integer> r5 = r12.yHighValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            if (r5 > r8) goto L_0x015e
            int r5 = r12.yOri
            int r10 = r5 / 128
            java.util.List<java.lang.Integer> r11 = r12.yHighValue
            java.lang.Object r11 = r11.get(r3)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r9
            int r10 = r10 * r11
            int r5 = r5 - r10
            int r10 = r12.yOri
            int r10 = r10 / 8
        L_0x015a:
            int r5 = r5 - r10
        L_0x015b:
            float r5 = (float) r5
            goto L_0x01db
        L_0x015e:
            java.util.List<java.lang.Integer> r5 = r12.yHighValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            if (r5 >= r9) goto L_0x018e
            java.util.List<java.lang.Integer> r5 = r12.yHighValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            if (r5 <= r7) goto L_0x018e
            int r5 = r12.yOri
            int r10 = r5 / 80
            java.util.List<java.lang.Integer> r11 = r12.yHighValue
            java.lang.Object r11 = r11.get(r3)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r7
            int r10 = r10 * r11
            goto L_0x015a
        L_0x018e:
            java.util.List<java.lang.Integer> r5 = r12.yHighValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            if (r5 <= r8) goto L_0x01c5
            java.util.List<java.lang.Integer> r5 = r12.yHighValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            if (r5 >= r6) goto L_0x01c5
            int r5 = r12.yOri
            int r10 = r5 / 320
            java.util.List<java.lang.Integer> r11 = r12.yHighValue
            java.lang.Object r11 = r11.get(r3)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r8
            int r10 = r10 * r11
            int r5 = r5 - r10
            int r10 = r12.yOri
            int r10 = r10 / 4
            int r10 = r10 * 3
            goto L_0x015a
        L_0x01c5:
            java.util.List<java.lang.Integer> r5 = r12.yHighValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            if (r5 < r6) goto L_0x01d8
            int r5 = r12.yOri
            int r5 = r5 / 8
            goto L_0x015b
        L_0x01d8:
            int r5 = r12.yOri
            goto L_0x015b
        L_0x01db:
            float r10 = r5 - r0
            java.util.List<java.lang.Integer> r11 = r12.yHighValue
            java.lang.Object r11 = r11.get(r3)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            r12.drawFloatBPMTextBox(r13, r4, r10, r11)
            android.graphics.Paint r10 = r12.linePaint
            android.graphics.Paint$Style r11 = android.graphics.Paint.Style.FILL
            r10.setStyle(r11)
            android.graphics.Paint r10 = r12.linePaint
            int r11 = r12.linecolor
            r10.setColor(r11)
            int r10 = r12.position
            if (r3 != r10) goto L_0x0204
            android.graphics.Paint r10 = r12.linePaint
            r13.drawCircle(r4, r5, r2, r10)
            goto L_0x0209
        L_0x0204:
            android.graphics.Paint r10 = r12.linePaint
            r13.drawCircle(r4, r5, r1, r10)
        L_0x0209:
            int r3 = r3 + 1
            goto L_0x0116
        L_0x020d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.PdfChartView.drawBrokenBPMPoint(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01d8  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x01f4  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x027d  */
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
            r0.moveTo(r1, r2)
            r1 = 1
            r2 = 1
        L_0x00de:
            java.util.List<java.lang.String> r8 = r12.xValue
            int r8 = r8.size()
            if (r2 >= r8) goto L_0x01aa
            float r8 = r12.xInit
            int r9 = r12.interval
            int r9 = r9 * r2
            float r9 = (float) r9
            float r8 = r8 + r9
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 < r7) goto L_0x0126
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 > r6) goto L_0x0126
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
        L_0x0122:
            int r9 = r9 - r10
        L_0x0123:
            float r9 = (float) r9
            goto L_0x01a3
        L_0x0126:
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 >= r7) goto L_0x0156
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 <= r5) goto L_0x0156
            int r9 = r12.yOri
            int r10 = r9 / 80
            java.util.List<java.lang.Integer> r11 = r12.yLowValue
            java.lang.Object r11 = r11.get(r2)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r5
            int r10 = r10 * r11
            goto L_0x0122
        L_0x0156:
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 <= r6) goto L_0x018d
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 >= r4) goto L_0x018d
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
            goto L_0x0122
        L_0x018d:
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 < r4) goto L_0x01a0
            int r9 = r12.yOri
            int r9 = r9 / 8
            goto L_0x0123
        L_0x01a0:
            int r9 = r12.yOri
            goto L_0x0123
        L_0x01a3:
            r0.lineTo(r8, r9)
            int r2 = r2 + 1
            goto L_0x00de
        L_0x01aa:
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
            if (r8 < r7) goto L_0x01f4
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 > r6) goto L_0x01f4
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
        L_0x01f0:
            int r8 = r8 - r3
        L_0x01f1:
            float r3 = (float) r8
            goto L_0x0272
        L_0x01f4:
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 >= r7) goto L_0x0225
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 <= r5) goto L_0x0225
            int r8 = r12.yOri
            int r9 = r8 / 80
            java.util.List<java.lang.Integer> r10 = r12.yHighValue
            java.lang.Object r3 = r10.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            int r3 = r3 - r5
            int r9 = r9 * r3
            int r8 = r8 - r9
            goto L_0x01f1
        L_0x0225:
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 <= r6) goto L_0x025c
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            if (r8 >= r4) goto L_0x025c
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
            goto L_0x01f0
        L_0x025c:
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r3 = r8.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 < r4) goto L_0x026f
            int r3 = r12.yOri
            int r3 = r3 / 8
            goto L_0x0271
        L_0x026f:
            int r3 = r12.yOri
        L_0x0271:
            float r3 = (float) r3
        L_0x0272:
            r0.moveTo(r2, r3)
        L_0x0275:
            java.util.List<java.lang.String> r2 = r12.xValue
            int r2 = r2.size()
            if (r1 >= r2) goto L_0x0341
            float r2 = r12.xInit
            int r3 = r12.interval
            int r3 = r3 * r1
            float r3 = (float) r3
            float r2 = r2 + r3
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 < r7) goto L_0x02bd
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 > r6) goto L_0x02bd
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
        L_0x02b9:
            int r3 = r3 - r8
        L_0x02ba:
            float r3 = (float) r3
            goto L_0x033a
        L_0x02bd:
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 >= r7) goto L_0x02ed
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 <= r5) goto L_0x02ed
            int r3 = r12.yOri
            int r8 = r3 / 80
            java.util.List<java.lang.Integer> r9 = r12.yHighValue
            java.lang.Object r9 = r9.get(r1)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r5
            int r8 = r8 * r9
            goto L_0x02b9
        L_0x02ed:
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 <= r6) goto L_0x0324
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 >= r4) goto L_0x0324
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
            goto L_0x02b9
        L_0x0324:
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 < r4) goto L_0x0337
            int r3 = r12.yOri
            int r3 = r3 / 8
            goto L_0x02ba
        L_0x0337:
            int r3 = r12.yOri
            goto L_0x02ba
        L_0x033a:
            r0.lineTo(r2, r3)
            int r1 = r1 + 1
            goto L_0x0275
        L_0x0341:
            android.graphics.Paint r1 = r12.linePaint
            r13.drawPath(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.PdfChartView.drawBrokenBPMLine(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0110  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenSPO2HPoint(android.graphics.Canvas r11) {
        /*
            r10 = this;
            r0 = 1073741824(0x40000000, float:2.0)
            int r1 = com.p020kl.commonbase.utils.SizeUtils.dp2px(r0)
            float r1 = (float) r1
            r2 = 1082130432(0x40800000, float:4.0)
            int r2 = com.p020kl.commonbase.utils.SizeUtils.dp2px(r2)
            float r2 = (float) r2
            int r0 = com.p020kl.commonbase.utils.SizeUtils.dp2px(r0)
            float r0 = (float) r0
            r3 = 0
            r4 = 0
        L_0x0015:
            java.util.List<java.lang.String> r5 = r10.xValue
            int r5 = r5.size()
            r6 = 100
            if (r4 >= r5) goto L_0x0119
            float r5 = r10.xInit
            int r7 = r10.interval
            int r7 = r7 * r4
            float r7 = (float) r7
            float r5 = r5 + r7
            java.util.List<java.lang.Integer> r7 = r10.yLowValue
            java.lang.Object r7 = r7.get(r4)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            r8 = 60
            if (r7 < r8) goto L_0x0061
            java.util.List<java.lang.Integer> r7 = r10.yLowValue
            java.lang.Object r7 = r7.get(r4)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 >= r6) goto L_0x0061
            int r6 = r10.yOri
            int r7 = r6 / 160
            java.util.List<java.lang.Integer> r9 = r10.yLowValue
            java.lang.Object r9 = r9.get(r4)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r8
            int r7 = r7 * r9
            int r6 = r6 - r7
            int r7 = r10.yOri
            int r7 = r7 / 8
        L_0x005d:
            int r6 = r6 - r7
        L_0x005e:
            float r6 = (float) r6
            goto L_0x00e7
        L_0x0061:
            java.util.List<java.lang.Integer> r7 = r10.yLowValue
            java.lang.Object r7 = r7.get(r4)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 >= r8) goto L_0x0093
            java.util.List<java.lang.Integer> r7 = r10.yLowValue
            java.lang.Object r7 = r7.get(r4)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            r8 = 50
            if (r7 <= r8) goto L_0x0093
            int r6 = r10.yOri
            int r7 = r6 / 80
            java.util.List<java.lang.Integer> r9 = r10.yLowValue
            java.lang.Object r9 = r9.get(r4)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r8
            int r7 = r7 * r9
            goto L_0x005d
        L_0x0093:
            java.util.List<java.lang.Integer> r7 = r10.yLowValue
            java.lang.Object r7 = r7.get(r4)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            r8 = 250(0xfa, float:3.5E-43)
            if (r7 < r6) goto L_0x00cd
            java.util.List<java.lang.Integer> r7 = r10.yLowValue
            java.lang.Object r7 = r7.get(r4)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 > r8) goto L_0x00cd
            int r7 = r10.yOri
            int r8 = r7 / 8
            int r8 = r8 * 3
            int r8 = r7 - r8
            int r7 = r7 / 1200
            java.util.List<java.lang.Integer> r9 = r10.yLowValue
            java.lang.Object r9 = r9.get(r4)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r6
            int r7 = r7 * r9
            int r8 = r8 - r7
            float r6 = (float) r8
            goto L_0x00e7
        L_0x00cd:
            java.util.List<java.lang.Integer> r6 = r10.yLowValue
            java.lang.Object r6 = r6.get(r4)
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            if (r6 <= r8) goto L_0x00e3
            int r6 = r10.yOri
            int r6 = r6 / 16
            int r6 = r6 * 9
            goto L_0x005e
        L_0x00e3:
            int r6 = r10.yOri
            goto L_0x005e
        L_0x00e7:
            float r7 = r6 - r0
            java.util.List<java.lang.Integer> r8 = r10.yLowValue
            java.lang.Object r8 = r8.get(r4)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            r10.drawFloatBPMTextBox(r11, r5, r7, r8)
            android.graphics.Paint r7 = r10.linePaint
            android.graphics.Paint$Style r8 = android.graphics.Paint.Style.FILL
            r7.setStyle(r8)
            android.graphics.Paint r7 = r10.linePaint
            int r8 = r10.linecolor
            r7.setColor(r8)
            int r7 = r10.position
            if (r4 != r7) goto L_0x0110
            android.graphics.Paint r7 = r10.linePaint
            r11.drawCircle(r5, r6, r2, r7)
            goto L_0x0115
        L_0x0110:
            android.graphics.Paint r7 = r10.linePaint
            r11.drawCircle(r5, r6, r1, r7)
        L_0x0115:
            int r4 = r4 + 1
            goto L_0x0015
        L_0x0119:
            java.util.List<java.lang.String> r4 = r10.xValue
            int r4 = r4.size()
            if (r3 >= r4) goto L_0x01a3
            float r4 = r10.xInit
            int r5 = r10.interval
            int r5 = r5 * r3
            float r5 = (float) r5
            float r4 = r4 + r5
            java.util.List<java.lang.Integer> r5 = r10.yHighValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r7 = 95
            if (r5 < r7) goto L_0x0160
            java.util.List<java.lang.Integer> r5 = r10.yHighValue
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            if (r5 > r6) goto L_0x0160
            int r5 = r10.yOri
            int r8 = r5 / 20
            java.util.List<java.lang.Integer> r9 = r10.yHighValue
            java.lang.Object r9 = r9.get(r3)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r7
            int r8 = r8 * r9
            int r5 = r5 - r8
            int r7 = r10.yOri
            int r7 = r7 / 8
            goto L_0x0164
        L_0x0160:
            int r5 = r10.yOri
            int r7 = r5 / 8
        L_0x0164:
            int r7 = r7 * 5
            int r5 = r5 - r7
            float r5 = (float) r5
            float r7 = r5 - r0
            java.util.List<java.lang.Integer> r8 = r10.yHighValue
            java.lang.Object r8 = r8.get(r3)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            r10.drawFloatSPO2HTextBox(r11, r4, r7, r8)
            android.graphics.Paint r7 = r10.linePaint
            android.graphics.Paint$Style r8 = android.graphics.Paint.Style.FILL
            r7.setStyle(r8)
            android.graphics.Paint r7 = r10.linePaint
            android.content.res.Resources r8 = r10.getResources()
            r9 = 2131099818(0x7f0600aa, float:1.7812E38)
            int r8 = r8.getColor(r9)
            r7.setColor(r8)
            int r7 = r10.position
            if (r3 != r7) goto L_0x019a
            android.graphics.Paint r7 = r10.linePaint
            r11.drawCircle(r4, r5, r2, r7)
            goto L_0x019f
        L_0x019a:
            android.graphics.Paint r7 = r10.linePaint
            r11.drawCircle(r4, r5, r1, r7)
        L_0x019f:
            int r3 = r3 + 1
            goto L_0x0119
        L_0x01a3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.PdfChartView.drawBrokenSPO2HPoint(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01e2  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01ff  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0212  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBrokenSPO2HLine(android.graphics.Canvas r13) {
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
            r4 = 250(0xfa, float:3.5E-43)
            r5 = 50
            r6 = 60
            r7 = 100
            if (r2 < r6) goto L_0x005c
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 >= r7) goto L_0x005c
            int r2 = r12.yOri
            int r8 = r2 / 160
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r3)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r6
            int r8 = r8 * r9
            int r2 = r2 - r8
            int r8 = r12.yOri
            int r8 = r8 / 8
        L_0x0058:
            int r2 = r2 - r8
        L_0x0059:
            float r2 = (float) r2
            goto L_0x00dd
        L_0x005c:
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 >= r6) goto L_0x008c
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
            if (r2 < r7) goto L_0x00c4
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 > r4) goto L_0x00c4
            int r2 = r12.yOri
            int r8 = r2 / 8
            int r8 = r8 * 3
            int r8 = r2 - r8
            int r2 = r2 / 1200
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r3)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            int r9 = r9 - r7
            int r2 = r2 * r9
            int r8 = r8 - r2
            float r2 = (float) r8
            goto L_0x00dd
        L_0x00c4:
            java.util.List<java.lang.Integer> r2 = r12.yLowValue
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 <= r4) goto L_0x00d9
            int r2 = r12.yOri
            int r2 = r2 / 16
            int r2 = r2 * 9
            goto L_0x0059
        L_0x00d9:
            int r2 = r12.yOri
            goto L_0x0059
        L_0x00dd:
            r0.moveTo(r1, r2)
            r1 = 1
            r2 = 1
        L_0x00e2:
            java.util.List<java.lang.String> r8 = r12.xValue
            int r8 = r8.size()
            if (r2 >= r8) goto L_0x01b2
            float r8 = r12.xInit
            int r9 = r12.interval
            int r9 = r9 * r2
            float r9 = (float) r9
            float r8 = r8 + r9
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 < r6) goto L_0x012a
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 >= r7) goto L_0x012a
            int r9 = r12.yOri
            int r10 = r9 / 160
            java.util.List<java.lang.Integer> r11 = r12.yLowValue
            java.lang.Object r11 = r11.get(r2)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r6
            int r10 = r10 * r11
            int r9 = r9 - r10
            int r10 = r12.yOri
            int r10 = r10 / 8
        L_0x0126:
            int r9 = r9 - r10
        L_0x0127:
            float r9 = (float) r9
            goto L_0x01ab
        L_0x012a:
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 >= r6) goto L_0x015a
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 <= r5) goto L_0x015a
            int r9 = r12.yOri
            int r10 = r9 / 80
            java.util.List<java.lang.Integer> r11 = r12.yLowValue
            java.lang.Object r11 = r11.get(r2)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r5
            int r10 = r10 * r11
            goto L_0x0126
        L_0x015a:
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 < r7) goto L_0x0192
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 > r4) goto L_0x0192
            int r9 = r12.yOri
            int r10 = r9 / 8
            int r10 = r10 * 3
            int r10 = r9 - r10
            int r9 = r9 / 1200
            java.util.List<java.lang.Integer> r11 = r12.yLowValue
            java.lang.Object r11 = r11.get(r2)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            int r11 = r11 - r7
            int r9 = r9 * r11
            int r10 = r10 - r9
            float r9 = (float) r10
            goto L_0x01ab
        L_0x0192:
            java.util.List<java.lang.Integer> r9 = r12.yLowValue
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            if (r9 <= r4) goto L_0x01a7
            int r9 = r12.yOri
            int r9 = r9 / 16
            int r9 = r9 * 9
            goto L_0x0127
        L_0x01a7:
            int r9 = r12.yOri
            goto L_0x0127
        L_0x01ab:
            r0.lineTo(r8, r9)
            int r2 = r2 + 1
            goto L_0x00e2
        L_0x01b2:
            android.graphics.Paint r2 = r12.linePaint
            r13.drawPath(r0, r2)
            android.graphics.Path r0 = new android.graphics.Path
            r0.<init>()
            float r2 = r12.xInit
            int r4 = r12.interval
            int r4 = r4 * 0
            float r4 = (float) r4
            float r2 = r2 + r4
            java.util.List<java.lang.Integer> r4 = r12.yHighValue
            java.lang.Object r4 = r4.get(r3)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            r5 = 95
            if (r4 < r5) goto L_0x01ff
            java.util.List<java.lang.Integer> r4 = r12.yHighValue
            java.lang.Object r4 = r4.get(r3)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            if (r4 > r7) goto L_0x01ff
            int r4 = r12.yOri
            int r6 = r4 / 20
            java.util.List<java.lang.Integer> r8 = r12.yHighValue
            java.lang.Object r3 = r8.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            int r3 = r3 - r5
            int r6 = r6 * r3
            int r4 = r4 - r6
            int r3 = r12.yOri
            int r3 = r3 / 8
            int r3 = r3 * 5
            int r4 = r4 - r3
            float r3 = (float) r4
            goto L_0x0207
        L_0x01ff:
            int r3 = r12.yOri
            int r4 = r3 / 8
            int r4 = r4 * 5
            int r3 = r3 - r4
            float r3 = (float) r3
        L_0x0207:
            r0.moveTo(r2, r3)
        L_0x020a:
            java.util.List<java.lang.String> r2 = r12.xValue
            int r2 = r2.size()
            if (r1 >= r2) goto L_0x025d
            float r2 = r12.xInit
            int r3 = r12.interval
            int r3 = r3 * r1
            float r3 = (float) r3
            float r2 = r2 + r3
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 < r5) goto L_0x024f
            java.util.List<java.lang.Integer> r3 = r12.yHighValue
            java.lang.Object r3 = r3.get(r1)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 > r7) goto L_0x024f
            int r3 = r12.yOri
            int r4 = r3 / 20
            java.util.List<java.lang.Integer> r6 = r12.yHighValue
            java.lang.Object r6 = r6.get(r1)
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            int r6 = r6 - r5
            int r4 = r4 * r6
            int r3 = r3 - r4
            int r4 = r12.yOri
            int r4 = r4 / 8
            goto L_0x0253
        L_0x024f:
            int r3 = r12.yOri
            int r4 = r3 / 8
        L_0x0253:
            int r4 = r4 * 5
            int r3 = r3 - r4
            float r3 = (float) r3
            r0.lineTo(r2, r3)
            int r1 = r1 + 1
            goto L_0x020a
        L_0x025d:
            android.graphics.Paint r1 = r12.linePaint
            android.content.res.Resources r2 = r12.getResources()
            r3 = 2131099818(0x7f0600aa, float:1.7812E38)
            int r2 = r2.getColor(r3)
            r1.setColor(r2)
            android.graphics.Paint r1 = r12.linePaint
            r13.drawPath(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.PdfChartView.drawBrokenSPO2HLine(android.graphics.Canvas):void");
    }

    private void drawBrokenBGPoint(Canvas canvas) {
        double d;
        int i;
        Canvas canvas2 = canvas;
        float dp2px = (float) SizeUtils.dp2px(2.0f);
        float dp2px2 = (float) SizeUtils.dp2px(4.0f);
        float dp2px3 = (float) SizeUtils.dp2px(2.0f);
        for (int i2 = 0; i2 < this.xValue.size(); i2++) {
            float f = this.xInit + ((float) (this.interval * i2));
            if (this.yBGValue.get(i2).doubleValue() >= 3.6d && this.yBGValue.get(i2).doubleValue() <= 7.8d) {
                int i3 = this.yOri;
                d = (((double) i3) - ((((double) i3) / 8.4d) * (this.yBGValue.get(i2).doubleValue() - 3.6d))) - ((double) (this.yOri / 8));
            } else if (this.yBGValue.get(i2).doubleValue() < 3.6d && this.yBGValue.get(i2).doubleValue() >= 3.0d) {
                int i4 = this.yOri;
                d = ((double) i4) - ((((double) i4) / 4.8d) * (this.yBGValue.get(i2).doubleValue() - 3.0d));
            } else if (this.yBGValue.get(i2).doubleValue() <= 7.8d || this.yBGValue.get(i2).doubleValue() >= 9.0d) {
                if (this.yBGValue.get(i2).doubleValue() >= 9.0d) {
                    i = this.yOri / 4;
                } else {
                    i = this.yOri;
                }
                d = (double) i;
            } else {
                int i5 = this.yOri;
                d = (((double) i5) - ((((double) i5) / 9.6d) * (this.yBGValue.get(i2).doubleValue() - 7.8d))) - ((double) ((this.yOri / 8) * 5));
            }
            float f2 = (float) d;
            drawFloatBGTextBox(canvas, f, f2 - dp2px3, this.yBGValue.get(i2), this.yState.get(i2));
            this.linePaint.setStyle(Paint.Style.FILL);
            this.linePaint.setColor(this.linecolor);
            if (i2 == this.position) {
                canvas2.drawCircle(f, f2, dp2px2, this.linePaint);
            } else {
                canvas2.drawCircle(f, f2, dp2px, this.linePaint);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0112  */
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
            if (r3 < 0) goto L_0x0074
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            int r3 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
            if (r3 > 0) goto L_0x0074
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
            int r3 = r3 / 8
        L_0x0070:
            double r3 = (double) r3
            double r5 = r5 - r3
            goto L_0x0105
        L_0x0074:
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            int r3 = (r5 > r19 ? 1 : (r5 == r19 ? 0 : -1))
            if (r3 >= 0) goto L_0x00aa
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            int r3 = (r5 > r15 ? 1 : (r5 == r15 ? 0 : -1))
            if (r3 < 0) goto L_0x00aa
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
            goto L_0x0105
        L_0x00aa:
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            int r3 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
            if (r3 <= 0) goto L_0x00ed
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r5 = r3.doubleValue()
            int r3 = (r5 > r13 ? 1 : (r5 == r13 ? 0 : -1))
            if (r3 >= 0) goto L_0x00ed
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
            int r3 = r3 / 8
            int r3 = r3 * 5
            goto L_0x0070
        L_0x00ed:
            java.util.List<java.lang.Double> r3 = r0.yBGValue
            java.lang.Object r3 = r3.get(r4)
            java.lang.Double r3 = (java.lang.Double) r3
            double r3 = r3.doubleValue()
            int r5 = (r3 > r13 ? 1 : (r3 == r13 ? 0 : -1))
            if (r5 < 0) goto L_0x0102
            int r3 = r0.yOri
            int r3 = r3 / 4
            goto L_0x0104
        L_0x0102:
            int r3 = r0.yOri
        L_0x0104:
            double r5 = (double) r3
        L_0x0105:
            float r3 = (float) r5
            r1.moveTo(r2, r3)
            r2 = 1
        L_0x010a:
            java.util.List<java.lang.String> r3 = r0.xValue
            int r3 = r3.size()
            if (r2 >= r3) goto L_0x01fd
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
            if (r6 < 0) goto L_0x015d
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r6 > 0) goto L_0x015d
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
            int r4 = r4 / 8
            double r7 = (double) r4
        L_0x0155:
            double r5 = r5 - r7
            r21 = 4621593937607602995(0x4023333333333333, double:9.6)
            goto L_0x01f5
        L_0x015d:
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r19 ? 1 : (r4 == r19 ? 0 : -1))
            if (r6 >= 0) goto L_0x0193
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r15 ? 1 : (r4 == r15 ? 0 : -1))
            if (r6 < 0) goto L_0x0193
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
            goto L_0x0155
        L_0x0193:
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r6 <= 0) goto L_0x01d8
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r6 >= 0) goto L_0x01d8
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
            int r4 = r4 / 8
            int r4 = r4 * 5
            double r7 = (double) r4
            double r5 = r5 - r7
            goto L_0x01f5
        L_0x01d8:
            r21 = 4621593937607602995(0x4023333333333333, double:9.6)
            java.util.List<java.lang.Double> r4 = r0.yBGValue
            java.lang.Object r4 = r4.get(r2)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            int r6 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r6 < 0) goto L_0x01f2
            int r4 = r0.yOri
            int r4 = r4 / 4
            goto L_0x01f4
        L_0x01f2:
            int r4 = r0.yOri
        L_0x01f4:
            double r5 = (double) r4
        L_0x01f5:
            float r4 = (float) r5
            r1.lineTo(r3, r4)
            int r2 = r2 + 1
            goto L_0x010a
        L_0x01fd:
            android.graphics.Paint r2 = r0.linePaint
            r3 = r26
            r3.drawPath(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.views.PdfChartView.drawBrokenBGLine(android.graphics.Canvas):void");
    }

    private void drawFloatTextBox(Canvas canvas, float f, float f2, float f3) {
        if (SpManager.getTemperaTrueUnit() == 1) {
            f3 = (float) TemperatureUtils.tempeConversionDouble((double) f3);
        }
        int dp2px = SizeUtils.dp2px(18.0f);
        this.linePaint.setColor(getResources().getColor(C1624R.C1626color.darkGreen));
        this.linePaint.setStyle(Paint.Style.FILL);
        this.linePaint.setTextSize((float) SizeUtils.sp2px(getContext(), 14.0f));
        Rect textBounds = getTextBounds(f3 + "", this.linePaint);
        this.pointTextHight = textBounds.height();
        canvas.drawText(f3 + "", f - ((float) (textBounds.width() / 2)), f2 - ((float) ((dp2px - textBounds.height()) / 2)), this.linePaint);
    }

    private void drawFloatBPMTextBox(Canvas canvas, float f, float f2, int i) {
        int dp2px = SizeUtils.dp2px(18.0f);
        this.linePaint.setColor(getResources().getColor(C1624R.C1626color.darkGreen));
        this.linePaint.setStyle(Paint.Style.FILL);
        this.linePaint.setTextSize((float) SizeUtils.sp2px(getContext(), 14.0f));
        Rect textBounds = getTextBounds(i + "", this.linePaint);
        this.pointTextHight = textBounds.height();
        canvas.drawText(i + "", f - ((float) (textBounds.width() / 2)), f2 - ((float) ((dp2px - textBounds.height()) / 2)), this.linePaint);
    }

    private void drawFloatSPO2HTextBox(Canvas canvas, float f, float f2, int i) {
        int dp2px = SizeUtils.dp2px(18.0f);
        this.linePaint.setColor(getResources().getColor(C1624R.C1626color.lightGreen));
        this.linePaint.setStyle(Paint.Style.FILL);
        this.linePaint.setTextSize((float) SizeUtils.sp2px(getContext(), 14.0f));
        Rect textBounds = getTextBounds(i + "%", this.linePaint);
        this.pointTextHight = textBounds.height();
        canvas.drawText(i + "%", f - ((float) (textBounds.width() / 2)), f2 - ((float) ((dp2px - textBounds.height()) / 2)), this.linePaint);
    }

    private void drawFloatBGTextBox(Canvas canvas, float f, float f2, Double d, String str) {
        int dp2px = SizeUtils.dp2px(18.0f);
        int dp2px2 = SizeUtils.dp2px(17.0f);
        this.linePaint.setColor(getResources().getColor(C1624R.C1626color.darkGreen));
        this.linePaint.setStyle(Paint.Style.FILL);
        this.linePaint.setTextSize((float) SizeUtils.sp2px(getContext(), 14.0f));
        Rect textBounds = getTextBounds(d + "", this.linePaint);
        this.pointTextHight = textBounds.height();
        canvas.drawText(d + "", f - ((float) (textBounds.width() / 2)), f2 - ((float) ((dp2px - textBounds.height()) / 2)), this.linePaint);
        this.xyTextPaint.setTextSize((float) SizeUtils.sp2px(getContext(), 12.0f));
        this.xyTextPaint.setColor(getResources().getColor(C1624R.C1626color.darkGreen));
        canvas.drawText(str, f - ((float) (textBounds.width() / 2)), f2 - ((float) ((dp2px2 - textBounds.height()) * 3)), this.xyTextPaint);
    }

    private void drawPointPoint(Canvas canvas) {
        float f;
        int i;
        float dp2px = (float) SizeUtils.dp2px(2.0f);
        SizeUtils.dp2px(4.0f);
        float dp2px2 = (float) SizeUtils.dp2px(2.0f);
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
            drawFloatTextBox(canvas, f2, f - dp2px2, this.yValue.get(i2).floatValue());
            this.linePaint.setStyle(Paint.Style.FILL);
            this.linePaint.setColor(this.linecolor);
            canvas.drawCircle(f2, f, dp2px, this.linePaint);
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
                Log.d("haha2", "haha" + i);
                this.xyTextPaint.setColor(this.xytextcolor);
                this.xyTextPaint.setStyle(Paint.Style.FILL);
                String str = this.xValue.get(i);
                StaticLayout staticLayout = new StaticLayout(str, this.xyTextPaint, getTextBounds(str + ":", this.xyTextPaint).width() / 2, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                canvas.save();
                canvas.translate(f - ((float) (this.xValueRect.width() / 4)), (float) this.yOri);
                staticLayout.draw(canvas);
                canvas.restore();
            }
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
        Log.d("绘制", "setValue");
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

    public void setBGValue(List<String> list, List<Double> list2, List<String> list3, int i, String str) {
        this.xValue.clear();
        this.yBGValue.clear();
        this.yState.clear();
        this.xValue.addAll(list);
        this.yBGValue.addAll(list2);
        this.yState.addAll(list3);
        this.position = i;
        this.lingType = str;
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
