package com.kongzue.dialogx.util.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class RoundView extends RelativeLayout {
    private Path mBoundPath;
    private float mRadius;

    public RoundView(Context context) {
        this(context, (AttributeSet) null);
    }

    public RoundView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRadius = 0.0f;
        this.mBoundPath = null;
        setWillNotDraw(false);
        this.mRadius = 50.0f;
    }

    public void setRadius(float f) {
        if (this.mRadius != f) {
            this.mRadius = f;
            postInvalidate();
        }
    }

    public float getRadius() {
        return this.mRadius;
    }

    public void draw(Canvas canvas) {
        Rect rect = new Rect();
        getLocalVisibleRect(rect);
        Path caculateRoundRectPath = caculateRoundRectPath(rect);
        this.mBoundPath = caculateRoundRectPath;
        canvas.clipPath(caculateRoundRectPath);
        super.draw(canvas);
    }

    private Path caculateRoundRectPath(Rect rect) {
        Path path = new Path();
        float radius = getRadius();
        path.addRoundRect(new RectF(((float) rect.left) + 0.0f, ((float) rect.top) + 0.0f, ((float) rect.right) - 0.0f, ((float) rect.bottom) - 0.0f), radius, radius, Path.Direction.CW);
        return path;
    }
}
