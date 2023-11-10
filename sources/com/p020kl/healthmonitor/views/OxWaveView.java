package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import com.p020kl.healthmonitor.C1624R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.views.OxWaveView */
public class OxWaveView extends View {
    private static final int OFFSET_LEFT = 0;
    private static final int OFFSET_OX_DATA = 0;
    private static final int OXYGEN_DATA_RED_MAX = 2000000;
    private static final int OXYGEN_DATA_RED_MIN = 50000;
    private static final int X_INTERVAL = 6;
    private int CHART_H = 0;
    private int CHART_W = 0;
    private List<Integer> data_list = new ArrayList();
    private boolean drawFinish = true;
    private Paint mPaint;
    private int max_ox_data;
    private int min_ox_data;
    private List<PointF> plist = new ArrayList();

    public OxWaveView(Context context) {
        super(context);
        initPaint();
    }

    public OxWaveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initPaint();
    }

    public OxWaveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initPaint();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.CHART_H == 0) {
            this.CHART_W = getWidth();
        }
        if (this.CHART_H == 0) {
            this.CHART_H = getHeight();
        }
        drawCurve(canvas);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public void clear() {
        this.min_ox_data = 0;
        this.max_ox_data = 0;
        this.data_list.clear();
        this.plist.clear();
        invalidate();
    }

    private void initPaint() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(getResources().getColor(C1624R.C1626color.colorPrimary));
        this.mPaint.setStrokeWidth(4.0f);
        this.mPaint.setAntiAlias(true);
    }

    private void drawCurve(Canvas canvas) {
        Object obj;
        this.drawFinish = false;
        if (this.plist.size() >= 2) {
            for (int i = 0; i < this.plist.size() - 1; i++) {
                PointF pointF = this.plist.get(i);
                try {
                    obj = this.plist.get(i + 1);
                } catch (IndexOutOfBoundsException unused) {
                    obj = this.plist.get(i);
                }
                PointF pointF2 = (PointF) obj;
                canvas.drawLine(pointF.x, pointF.y, pointF2.x, pointF2.y, this.mPaint);
            }
        }
        this.drawFinish = true;
    }

    public void preparePoints(int i) {
        int i2;
        if (this.drawFinish && i > OXYGEN_DATA_RED_MIN && i < OXYGEN_DATA_RED_MAX) {
            this.data_list.add(Integer.valueOf(i));
            if (this.data_list.size() > this.CHART_W / 6) {
                this.data_list.remove(0);
                this.plist.remove(0);
            }
            this.min_ox_data = this.data_list.get(0).intValue();
            this.max_ox_data = this.data_list.get(0).intValue();
            for (int i3 = 0; i3 < this.data_list.size(); i3++) {
                Integer num = this.data_list.get(i3);
                if (num == null) {
                    i2 = 0;
                } else {
                    i2 = num.intValue();
                }
                if (i2 < this.min_ox_data) {
                    this.min_ox_data = i2;
                }
                if (i2 > this.max_ox_data) {
                    this.max_ox_data = i2;
                }
            }
            this.plist.add(new PointF((float) (this.CHART_W + 0), (float) (this.CHART_H - 0)));
            int i4 = this.CHART_H;
            float f = ((float) (this.max_ox_data - this.min_ox_data)) / ((float) ((i4 + 0) - ((i4 / 10) * 2)));
            if (f == 0.0f) {
                f = -1.0f;
            }
            for (int i5 = 0; i5 < this.data_list.size(); i5++) {
                this.plist.get(i5).x -= 6.0f;
                int i6 = this.CHART_H;
                this.plist.get(i5).y = ((float) (i6 - (i6 / 10))) - (((float) (this.data_list.get(i5).intValue() - this.min_ox_data)) / f);
            }
            postInvalidate();
        }
    }
}
