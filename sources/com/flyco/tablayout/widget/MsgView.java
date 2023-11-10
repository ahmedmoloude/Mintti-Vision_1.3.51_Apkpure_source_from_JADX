package com.flyco.tablayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.flyco.tablayout.C1060R;

public class MsgView extends TextView {
    private int backgroundColor;
    private Context context;
    private int cornerRadius;
    private GradientDrawable gd_background;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;
    private int strokeColor;
    private int strokeWidth;

    public MsgView(Context context2) {
        this(context2, (AttributeSet) null);
    }

    public MsgView(Context context2, AttributeSet attributeSet) {
        this(context2, attributeSet, 0);
    }

    public MsgView(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        this.gd_background = new GradientDrawable();
        this.context = context2;
        obtainAttributes(context2, attributeSet);
    }

    private void obtainAttributes(Context context2, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, C1060R.styleable.MsgView);
        this.backgroundColor = obtainStyledAttributes.getColor(C1060R.styleable.MsgView_mv_backgroundColor, 0);
        this.cornerRadius = obtainStyledAttributes.getDimensionPixelSize(C1060R.styleable.MsgView_mv_cornerRadius, 0);
        this.strokeWidth = obtainStyledAttributes.getDimensionPixelSize(C1060R.styleable.MsgView_mv_strokeWidth, 0);
        this.strokeColor = obtainStyledAttributes.getColor(C1060R.styleable.MsgView_mv_strokeColor, 0);
        this.isRadiusHalfHeight = obtainStyledAttributes.getBoolean(C1060R.styleable.MsgView_mv_isRadiusHalfHeight, false);
        this.isWidthHeightEqual = obtainStyledAttributes.getBoolean(C1060R.styleable.MsgView_mv_isWidthHeightEqual, false);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (!isWidthHeightEqual() || getWidth() <= 0 || getHeight() <= 0) {
            super.onMeasure(i, i2);
            return;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(getWidth(), getHeight()), 1073741824);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (isRadiusHalfHeight()) {
            setCornerRadius(getHeight() / 2);
        } else {
            setBgSelector();
        }
    }

    public void setBackgroundColor(int i) {
        this.backgroundColor = i;
        setBgSelector();
    }

    public void setCornerRadius(int i) {
        this.cornerRadius = dp2px((float) i);
        setBgSelector();
    }

    public void setStrokeWidth(int i) {
        this.strokeWidth = dp2px((float) i);
        setBgSelector();
    }

    public void setStrokeColor(int i) {
        this.strokeColor = i;
        setBgSelector();
    }

    public void setIsRadiusHalfHeight(boolean z) {
        this.isRadiusHalfHeight = z;
        setBgSelector();
    }

    public void setIsWidthHeightEqual(boolean z) {
        this.isWidthHeightEqual = z;
        setBgSelector();
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public int getCornerRadius() {
        return this.cornerRadius;
    }

    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    public int getStrokeColor() {
        return this.strokeColor;
    }

    public boolean isRadiusHalfHeight() {
        return this.isRadiusHalfHeight;
    }

    public boolean isWidthHeightEqual() {
        return this.isWidthHeightEqual;
    }

    /* access modifiers changed from: protected */
    public int dp2px(float f) {
        return (int) ((f * this.context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* access modifiers changed from: protected */
    public int sp2px(float f) {
        return (int) ((f * this.context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    private void setDrawable(GradientDrawable gradientDrawable, int i, int i2) {
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadius((float) this.cornerRadius);
        gradientDrawable.setStroke(this.strokeWidth, i2);
    }

    public void setBgSelector() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        setDrawable(this.gd_background, this.backgroundColor, this.strokeColor);
        stateListDrawable.addState(new int[]{-16842919}, this.gd_background);
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(stateListDrawable);
        } else {
            setBackgroundDrawable(stateListDrawable);
        }
    }
}
