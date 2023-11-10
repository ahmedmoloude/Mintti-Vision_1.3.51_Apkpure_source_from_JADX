package com.p020kl.healthmonitor.measure.rothmanindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.p020kl.commonbase.utils.LoggerUtil;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.LineChartLayout */
public class LineChartLayout extends RelativeLayout {
    int bottomHeight;
    int chartHeight;
    Paint circleAndLinePaint;
    private int curHeight;
    List<AxisData> datas;
    float interval;
    private boolean isScrollAxis;
    LineChartView lineChartView;
    Context mContext;
    private float maxYValue;
    private float minYValue;
    float normalRadius;
    OnAxisClickListener onAxisClickListener;
    HorizontalScrollView scrollView;
    /* access modifiers changed from: private */
    public int selectIndex;
    int selectTextGap;
    float selectedRadius;
    Paint textPaint;
    int xStart;
    TextPaint xyPaint;
    int yOri;
    private float yScale;

    /* renamed from: com.kl.healthmonitor.measure.rothmanindex.LineChartLayout$OnAxisClickListener */
    public interface OnAxisClickListener {
        void onAxisClick(int i);
    }

    public void setMinYValue(float f) {
        this.minYValue = f;
    }

    public void setMaxYValue(float f) {
        this.maxYValue = f;
    }

    public LineChartLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public LineChartLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LineChartLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.curHeight = (int) TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics());
        this.bottomHeight = 100;
        this.selectTextGap = dpToPx(20);
        this.xStart = dpToPx(10);
        this.interval = (float) dpToPx(40);
        this.chartHeight = 0;
        this.yOri = 0;
        this.selectIndex = 1;
        this.normalRadius = (float) dpToPx(4);
        this.selectedRadius = (float) dpToPx(7);
        this.datas = new ArrayList();
        this.isScrollAxis = false;
        this.minYValue = 0.0f;
        this.maxYValue = 100.0f;
        this.yScale = 1.0f;
        this.mContext = context;
        initPaint();
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
        this.scrollView = horizontalScrollView;
        addView(horizontalScrollView, -1, -1);
        this.scrollView.setFillViewport(true);
        this.scrollView.setOverScrollMode(2);
        this.scrollView.setHorizontalScrollBarEnabled(false);
        this.lineChartView = new LineChartView(context);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.addView(this.lineChartView, -2, -1);
        this.scrollView.removeAllViews();
        this.scrollView.addView(linearLayout, -2, -1);
        Rect textBounds = getTextBounds("00:00", this.textPaint);
        this.bottomHeight = (textBounds.height() * 2) + (dpToPx(8) * 2);
        this.xStart = (textBounds.width() / 2) + dpToPx(10);
    }

    private void initPaint() {
        TextPaint textPaint2 = new TextPaint();
        this.xyPaint = textPaint2;
        textPaint2.setStyle(Paint.Style.FILL);
        this.xyPaint.setColor(Color.parseColor("#ff999999"));
        this.xyPaint.setTextSize((float) spToPx(12));
        this.xyPaint.setAntiAlias(true);
        this.xyPaint.setStrokeCap(Paint.Cap.ROUND);
        Paint paint = new Paint();
        this.textPaint = paint;
        paint.setAntiAlias(true);
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setColor(Color.parseColor("#FF44BCB1"));
        this.textPaint.setTextSize((float) spToPx(14));
        Paint paint2 = new Paint();
        this.circleAndLinePaint = paint2;
        paint2.setAntiAlias(true);
        this.circleAndLinePaint.setStyle(Paint.Style.FILL);
        this.circleAndLinePaint.setStrokeWidth(4.0f);
        this.circleAndLinePaint.setColor(Color.parseColor("#FF44BCB1"));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int height = getHeight();
        this.chartHeight = height;
        this.yOri = height - this.bottomHeight;
        this.interval = (float) dpToPx(40);
        this.yScale = getScale();
        scrollAxis(this.selectIndex - 1);
    }

    /* renamed from: com.kl.healthmonitor.measure.rothmanindex.LineChartLayout$LineChartView */
    class LineChartView extends RelativeLayout {
        private int mHeight;
        SelectedView selectedView;

        public LineChartView(Context context) {
            super(context);
            setWillNotDraw(false);
            SelectedView selectedView2 = new SelectedView(context);
            this.selectedView = selectedView2;
            addView(selectedView2, -1, -1);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            this.mHeight = getHeight();
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 1) {
                clickAction(motionEvent);
            }
            return true;
        }

        private void clickAction(MotionEvent motionEvent) {
            if (LineChartLayout.this.datas != null && LineChartLayout.this.datas.size() > 0 && this.selectedView != null) {
                int access$000 = LineChartLayout.this.dpToPx(15);
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                for (int i = 0; i < LineChartLayout.this.datas.size(); i++) {
                    AxisData axisData = LineChartLayout.this.datas.get(i);
                    if (axisData != null) {
                        float f = ((float) LineChartLayout.this.xStart) + (LineChartLayout.this.interval * ((float) i));
                        float access$100 = LineChartLayout.this.calculateY((float) axisData.getValue1());
                        float f2 = (float) access$000;
                        if (x >= f - f2 && x <= f + f2 && y >= access$100 - f2 && y <= access$100 + f2) {
                            int unused = LineChartLayout.this.selectIndex = i + 1;
                            this.selectedView.invalidate();
                            if (LineChartLayout.this.onAxisClickListener != null) {
                                LineChartLayout.this.onAxisClickListener.onAxisClick(LineChartLayout.this.selectIndex);
                                return;
                            }
                            return;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawBrokenLineAndPoint(canvas);
            drawXCoordinate(canvas);
        }

        private void drawXCoordinate(Canvas canvas) {
            for (int i = 0; i < LineChartLayout.this.datas.size(); i++) {
                AxisData axisData = LineChartLayout.this.datas.get(i);
                if (axisData != null && !TextUtils.isEmpty(axisData.getValue2())) {
                    float f = ((float) LineChartLayout.this.xStart) + (LineChartLayout.this.interval * ((float) i));
                    String value2 = axisData.getValue2();
                    LineChartLayout lineChartLayout = LineChartLayout.this;
                    Rect access$300 = lineChartLayout.getTextBounds(value2 + ":", LineChartLayout.this.xyPaint);
                    StaticLayout staticLayout = new StaticLayout(value2, LineChartLayout.this.xyPaint, access$300.width() / 2, Layout.Alignment.ALIGN_NORMAL, 1.0f, 1.0f, false);
                    canvas.save();
                    canvas.translate(f - (((float) access$300.width()) / 4.0f), ((float) LineChartLayout.this.yOri) + (((float) LineChartLayout.this.bottomHeight) / 4.0f));
                    staticLayout.draw(canvas);
                    canvas.restore();
                }
            }
        }

        private void drawBrokenLineAndPoint(Canvas canvas) {
            if (LineChartLayout.this.datas != null && LineChartLayout.this.datas.size() > 0) {
                int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), (Paint) null, 31);
                drawBrokenLine(canvas);
                drawBrokenPoint(canvas);
                canvas.restoreToCount(saveLayer);
            }
        }

        private void drawBrokenPoint(Canvas canvas) {
            for (int i = 0; i < LineChartLayout.this.datas.size(); i++) {
                AxisData axisData = LineChartLayout.this.datas.get(i);
                if (axisData != null) {
                    float f = ((float) LineChartLayout.this.xStart) + (LineChartLayout.this.interval * ((float) i));
                    float access$100 = LineChartLayout.this.calculateY((float) axisData.getValue1());
                    canvas.drawCircle(f, access$100, LineChartLayout.this.normalRadius, LineChartLayout.this.circleAndLinePaint);
                    drawFloatTextBox(canvas, f, access$100, axisData.getValue1());
                }
            }
        }

        private void drawBrokenLine(Canvas canvas) {
            new Path();
            LineChartLayout.this.circleAndLinePaint.setStyle(Paint.Style.STROKE);
            LineChartLayout.this.circleAndLinePaint.setAlpha(128);
            float f = 0.0f;
            float f2 = 0.0f;
            for (int i = 0; i < LineChartLayout.this.datas.size(); i++) {
                AxisData axisData = LineChartLayout.this.datas.get(i);
                if (axisData != null) {
                    float f3 = ((float) LineChartLayout.this.xStart) + (LineChartLayout.this.interval * ((float) i));
                    float access$100 = LineChartLayout.this.calculateY((float) axisData.getValue1());
                    if (i > 0) {
                        canvas.drawLine(f, f2, f3, access$100, LineChartLayout.this.circleAndLinePaint);
                    }
                    f2 = access$100;
                    f = f3;
                }
            }
            LineChartLayout.this.circleAndLinePaint.setAlpha(255);
            LineChartLayout.this.circleAndLinePaint.setStyle(Paint.Style.FILL);
        }

        private void drawFloatTextBox(Canvas canvas, float f, float f2, int i) {
            String str = i + "";
            LineChartLayout lineChartLayout = LineChartLayout.this;
            Rect access$300 = lineChartLayout.getTextBounds(str, lineChartLayout.textPaint);
            canvas.drawText(str, f - (((float) access$300.width()) / 2.0f), (f2 - (((float) access$300.height()) / 2.0f)) - (LineChartLayout.this.selectedRadius / 2.0f), LineChartLayout.this.textPaint);
        }

        public void refreshSelectedView() {
            SelectedView selectedView2 = this.selectedView;
            if (selectedView2 != null) {
                selectedView2.invalidate();
            }
        }

        /* renamed from: com.kl.healthmonitor.measure.rothmanindex.LineChartLayout$LineChartView$SelectedView */
        class SelectedView extends View {
            public SelectedView(Context context) {
                super(context);
            }

            /* access modifiers changed from: protected */
            public void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                drawSelectedPoint(canvas);
            }

            private void drawSelectedPoint(Canvas canvas) {
                for (int i = 0; i < LineChartLayout.this.datas.size(); i++) {
                    AxisData axisData = LineChartLayout.this.datas.get(i);
                    if (axisData != null) {
                        float f = ((float) LineChartLayout.this.xStart) + (LineChartLayout.this.interval * ((float) i));
                        float access$100 = LineChartLayout.this.calculateY((float) axisData.getValue1());
                        if (i == LineChartLayout.this.selectIndex - 1) {
                            canvas.drawCircle(f, access$100, LineChartLayout.this.selectedRadius, LineChartLayout.this.circleAndLinePaint);
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public Rect getTextBounds(String str, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect;
    }

    /* access modifiers changed from: private */
    public int dpToPx(int i) {
        return (int) ((((float) i) * getContext().getResources().getDisplayMetrics().density) + (((float) (i >= 0 ? 1 : -1)) * 0.5f));
    }

    private int spToPx(int i) {
        return (int) ((getContext().getResources().getDisplayMetrics().scaledDensity * ((float) i)) + (((float) (i >= 0 ? 1 : -1)) * 0.5f));
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000c, code lost:
        if (r3 > r0) goto L_0x0006;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float calculateY(float r3) {
        /*
            r2 = this;
            float r0 = r2.minYValue
            int r1 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r1 >= 0) goto L_0x0008
        L_0x0006:
            r3 = r0
            goto L_0x000f
        L_0x0008:
            float r0 = r2.maxYValue
            int r1 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r1 <= 0) goto L_0x000f
            goto L_0x0006
        L_0x000f:
            int r0 = r2.yOri
            float r0 = (float) r0
            float r1 = r2.yScale
            float r3 = r3 * r1
            float r0 = r0 - r3
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.measure.rothmanindex.LineChartLayout.calculateY(float):float");
    }

    private float getScale() {
        return (((float) (this.yOri - getTextBounds("100", this.textPaint).height())) - (this.selectedRadius * 2.0f)) / (this.maxYValue - this.minYValue);
    }

    public void setOnAxisClickListener(OnAxisClickListener onAxisClickListener2) {
        this.onAxisClickListener = onAxisClickListener2;
    }

    public void addAxisData(List<AxisData> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.selectIndex = 1;
        this.datas = list;
        initContent();
        this.lineChartView.refreshSelectedView();
    }

    public void initContent() {
        if (this.mContext != null && this.scrollView != null) {
            int ceil = (int) Math.ceil((double) ((((float) this.datas.size()) * this.interval) + ((float) this.xStart) + ((float) getTextBounds("00:00", this.textPaint).width())));
            LoggerUtil.m112d("tempWidth = " + ceil);
            if (ceil < getWidth()) {
                ceil = getWidth();
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.lineChartView.getLayoutParams();
            layoutParams.width = ceil;
            this.lineChartView.setLayoutParams(layoutParams);
        }
    }

    public void setSeletedIndex(int i) {
        LineChartView lineChartView2 = this.lineChartView;
        if (lineChartView2 != null) {
            this.selectIndex = i;
            lineChartView2.refreshSelectedView();
            this.isScrollAxis = true;
            scrollAxis(this.selectIndex - 1);
        }
    }

    private void scrollAxis(int i) {
        HorizontalScrollView horizontalScrollView = this.scrollView;
        if (horizontalScrollView != null) {
            if (i < 0) {
                i = 0;
            }
            horizontalScrollView.smoothScrollTo((int) (((float) i) * this.interval), 0);
        }
    }
}
