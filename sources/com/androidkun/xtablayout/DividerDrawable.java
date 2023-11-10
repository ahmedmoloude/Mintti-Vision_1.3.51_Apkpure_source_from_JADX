package com.androidkun.xtablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.core.view.ViewCompat;

public class DividerDrawable extends Drawable {
    public static final int BOTTOM = 2;
    public static final int CENTER = 1;
    public static final int TOP = 0;
    private int color = ViewCompat.MEASURED_STATE_MASK;
    private int gravity = 1;
    private int height = 0;
    private Paint paint;
    private RectF rectF;
    private int width;

    public int getOpacity() {
        return -3;
    }

    public DividerDrawable(Context context) {
        this.width = dip2px(context, 2.0f);
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setColor(this.color);
        this.paint.setAntiAlias(true);
    }

    public void setColor(int i) {
        this.paint.setColor(i);
    }

    public void setGravity(int i) {
        if (i == 0 || i == 1 || i == 2) {
            this.gravity = i;
            return;
        }
        throw new IllegalArgumentException("Gravity must be one of 0(DividerDrawable.TOP)、1(DividerDrawable.CENTER) and 2(DividerDrawable.BOTTOM)");
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(0);
        int i = this.height;
        if (i == 0 || ((float) i) >= this.rectF.bottom) {
            canvas.drawRect(this.rectF, this.paint);
            return;
        }
        int i2 = (int) ((this.rectF.bottom - ((float) this.height)) / 2.0f);
        int i3 = this.gravity;
        if (i3 == 0) {
            canvas.drawRect(this.rectF.left, this.rectF.top, this.rectF.right, this.rectF.bottom - ((float) (i2 * 2)), this.paint);
        } else if (i3 == 1) {
            float f = (float) i2;
            canvas.drawRect(this.rectF.left, this.rectF.top + f, this.rectF.right, this.rectF.bottom - f, this.paint);
        } else if (i3 == 2) {
            canvas.drawRect(this.rectF.left, this.rectF.top + ((float) (i2 * 2)), this.rectF.right, this.rectF.bottom, this.paint);
        }
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        this.rectF = new RectF((float) i, (float) i2, (float) i3, (float) i4);
    }

    public void setDividerSize(int i, int i2) {
        this.width = i;
        this.height = i2;
        invalidateSelf();
    }

    public int getIntrinsicHeight() {
        return super.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.width;
    }

    public void setAlpha(int i) {
        this.paint.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    public int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
