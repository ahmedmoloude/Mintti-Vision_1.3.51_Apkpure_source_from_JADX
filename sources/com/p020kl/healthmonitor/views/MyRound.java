package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.MyRound */
public class MyRound extends View {
    private Paint paint;
    private RectF rectf;
    private int roundwidth;

    public MyRound(Context context) {
        this(context, (AttributeSet) null);
    }

    public MyRound(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MyRound(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.roundwidth = SizeUtils.dp2px(5.0f);
        initView();
    }

    private void initView() {
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setAntiAlias(true);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStrokeWidth((float) this.roundwidth);
        int i = this.roundwidth;
        this.rectf = new RectF((float) (i / 2), (float) (i / 2), (float) (getWidth() - (this.roundwidth / 2)), (float) (getHeight() - (this.roundwidth / 2)));
        this.paint.setColor(getResources().getColor(C1624R.C1626color.lightGray));
        canvas.drawArc(this.rectf, 90.0f, 180.0f, false, this.paint);
        this.paint.setColor(getResources().getColor(C1624R.C1626color.darkGreen));
        canvas.drawArc(this.rectf, -90.0f, 180.0f, false, this.paint);
    }
}
