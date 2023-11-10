package com.kongzue.dialogx.util.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.DialogX;

public class MaxRelativeLayout extends RelativeLayout {
    private ScrollView childScrollView;
    private View contentView;
    /* access modifiers changed from: private */
    public float endAnimValue = 0.0f;
    private boolean interceptTouch = true;
    private boolean lockWidth;
    private int maxHeight;
    private int maxWidth;
    private int minHeight;
    private int minWidth;
    int navBarHeight;
    Paint navBarPaint;
    private View.OnTouchListener onTouchListener;
    /* access modifiers changed from: private */
    public OnYChanged onYChangedListener;
    private int preWidth = -1;
    boolean reInterceptTouch;
    /* access modifiers changed from: private */
    public float startAnimValue = 0.0f;

    public interface OnYChanged {
        /* renamed from: y */
        void mo26476y(float f);
    }

    public MaxRelativeLayout(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public MaxRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public MaxRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1903R.styleable.DialogXMaxLayout);
            this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(C1903R.styleable.DialogXMaxLayout_maxLayoutWidth, 0);
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(C1903R.styleable.DialogXMaxLayout_maxLayoutHeight, 0);
            this.minWidth = obtainStyledAttributes.getDimensionPixelSize(C1903R.styleable.DialogXMaxLayout_minLayoutWidth, 0);
            this.minHeight = obtainStyledAttributes.getDimensionPixelSize(C1903R.styleable.DialogXMaxLayout_minLayoutHeight, 0);
            this.lockWidth = obtainStyledAttributes.getBoolean(C1903R.styleable.DialogXMaxLayout_lockWidth, false);
            this.interceptTouch = obtainStyledAttributes.getBoolean(C1903R.styleable.DialogXMaxLayout_interceptTouch, true);
            obtainStyledAttributes.recycle();
        }
        int i = this.minWidth;
        if (i == 0) {
            i = getMinimumWidth();
        }
        this.minWidth = i;
        int i2 = this.minHeight;
        if (i2 == 0) {
            i2 = getMinimumHeight();
        }
        this.minHeight = i2;
        if (!isInEditMode() && Build.VERSION.SDK_INT >= 19) {
            animate().setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    long access$000 = (long) (MaxRelativeLayout.this.startAnimValue + ((MaxRelativeLayout.this.endAnimValue - MaxRelativeLayout.this.startAnimValue) * ((Float) valueAnimator.getAnimatedValue()).floatValue()));
                    if (MaxRelativeLayout.this.onYChangedListener != null) {
                        MaxRelativeLayout.this.onYChangedListener.mo26476y((float) access$000);
                    }
                }
            });
        }
    }

    public MaxRelativeLayout setMaxHeight(int i) {
        this.maxHeight = i;
        return this;
    }

    public MaxRelativeLayout setMaxWidth(int i) {
        if (i > 0) {
            this.maxWidth = i;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i);
        if (this.preWidth == -1 && size2 != 0) {
            this.preWidth = size2;
        }
        if (this.lockWidth) {
            this.maxWidth = Math.min(this.maxWidth, Math.min(size2, this.preWidth));
        }
        int i3 = this.maxHeight;
        if (size > i3 && i3 != 0) {
            size = i3;
        }
        int i4 = this.maxWidth;
        if (size2 > i4 && i4 != 0) {
            size2 = i4;
        }
        View findViewWithTag = findViewWithTag("blurView");
        View view = this.contentView;
        if (view == null) {
            view = findViewWithoutTag("blurView");
        }
        if (view != null) {
            int measuredWidth = view.getMeasuredWidth() == 0 ? getMeasuredWidth() : view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight() == 0 ? getMeasuredHeight() : view.getMeasuredHeight();
            int i5 = this.minWidth;
            if (measuredWidth < i5) {
                measuredWidth = i5;
            }
            int i6 = this.minHeight;
            if (measuredHeight < i6) {
                measuredHeight = i6;
            }
            if (findViewWithTag != null) {
                if (mode == 1073741824) {
                    measuredHeight = size;
                }
                if (mode2 == 1073741824) {
                    measuredWidth = size2;
                }
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewWithTag.getLayoutParams();
                layoutParams.width = measuredWidth;
                layoutParams.height = measuredHeight;
                findViewWithTag.setLayoutParams(layoutParams);
            }
        } else if (findViewWithTag != null) {
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) findViewWithTag.getLayoutParams();
            layoutParams2.width = getMeasuredWidth();
            layoutParams2.height = getMeasuredHeight();
            findViewWithTag.setLayoutParams(layoutParams2);
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size2, mode2), View.MeasureSpec.makeMeasureSpec(size, mode));
        this.childScrollView = (ScrollView) findViewById(C1903R.C1907id.scrollView);
    }

    private View findViewWithoutTag(String str) {
        for (int i = 0; i < getChildCount(); i++) {
            if (!str.equals(getChildAt(i).getTag())) {
                return getChildAt(i);
            }
        }
        return null;
    }

    @Deprecated
    public boolean isChildScrollViewCanScroll() {
        View childAt;
        ScrollView scrollView = this.childScrollView;
        if (scrollView != null && scrollView.isEnabled() && (childAt = this.childScrollView.getChildAt(0)) != null && this.childScrollView.getHeight() < childAt.getHeight()) {
            return true;
        }
        return false;
    }

    public int dip2px(float f) {
        return (int) ((f * getResources().getDisplayMetrics().density) + 0.5f);
    }

    public boolean isLockWidth() {
        return this.lockWidth;
    }

    public MaxRelativeLayout setLockWidth(boolean z) {
        this.lockWidth = z;
        return this;
    }

    public void setNavBarHeight(int i) {
        this.navBarHeight = i;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.navBarHeight != 0 && DialogX.bottomDialogNavbarColor != 0) {
            if (this.navBarPaint == null) {
                Paint paint = new Paint();
                this.navBarPaint = paint;
                paint.setColor(DialogX.bottomDialogNavbarColor);
            }
            canvas.drawRect(0.0f, (float) (getHeight() - this.navBarHeight), (float) getWidth(), (float) getHeight(), this.navBarPaint);
        }
    }

    public void setContentView(View view) {
        this.contentView = view;
    }

    public void setY(float f) {
        super.setY(f);
    }

    public OnYChanged getOnYChanged() {
        return this.onYChangedListener;
    }

    public MaxRelativeLayout setOnYChanged(OnYChanged onYChanged) {
        this.onYChangedListener = onYChanged;
        return this;
    }

    public void setTranslationY(float f) {
        super.setTranslationY(f);
        OnYChanged onYChanged = this.onYChangedListener;
        if (onYChanged != null) {
            onYChanged.mo26476y(f);
        }
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener2) {
        this.onTouchListener = onTouchListener2;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        View.OnTouchListener onTouchListener2 = this.onTouchListener;
        if (onTouchListener2 != null) {
            this.reInterceptTouch = onTouchListener2.onTouch(this, motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.reInterceptTouch;
    }
}
