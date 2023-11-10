package com.p020kl.commonbase.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.utils.SizeUtils;

/* renamed from: com.kl.commonbase.views.FunctionButton */
public class FunctionButton extends CardView {
    private static final String TAG = "FunctionButton";
    private int functionIcon;
    private String functionName;
    private int height;
    private int left;

    /* renamed from: top  reason: collision with root package name */
    private int f2178top;
    private int width;

    public FunctionButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public FunctionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FunctionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initButton(context, attributeSet);
    }

    private void initButton(Context context, AttributeSet attributeSet) {
        setPreventCornerOverlap(true);
        setUseCompatPadding(true);
        setCardElevation((float) SizeUtils.dp2px(8.0f));
        setRadius((float) SizeUtils.dp2px(8.0f));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1544R.styleable.FunctionButton);
        this.functionIcon = obtainStyledAttributes.getResourceId(C1544R.styleable.FunctionButton_function_icon, 0);
        this.functionName = obtainStyledAttributes.getString(C1544R.styleable.FunctionButton_function_name);
        obtainStyledAttributes.recycle();
        View inflate = LayoutInflater.from(context).inflate(C1544R.layout.button_function_layout, this, false);
        ((ImageView) inflate.findViewById(C1544R.C1548id.iv_function)).setImageResource(this.functionIcon);
        ((TextView) inflate.findViewById(C1544R.C1548id.tv_function)).setText(this.functionName);
        addView(inflate);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
        this.f2178top = getTop();
        this.left = getLeft();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            setCardElevation((float) SizeUtils.dp2px(16.0f));
            return true;
        } else if (action != 1) {
            return super.onTouchEvent(motionEvent);
        } else {
            setCardElevation((float) SizeUtils.dp2px(8.0f));
            if (isInnerView(motionEvent.getX(), motionEvent.getY())) {
                performClick();
            }
            return true;
        }
    }

    private boolean isInnerView(float f, float f2) {
        return f > 0.0f && f < ((float) this.width) && f2 > 0.0f && f2 < ((float) this.height);
    }
}
