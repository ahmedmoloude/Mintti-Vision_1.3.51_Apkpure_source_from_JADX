package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.ScaleView */
public class ScaleView extends FrameLayout {
    private int margeLeftOne;
    private int margeLeftTwo;
    private String scaleOne;
    private String scaleTwo;

    public ScaleView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ScaleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScaleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(C1624R.layout.digital_scale, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1544R.styleable.ScaleView);
        this.margeLeftOne = obtainStyledAttributes.getInt(0, 0);
        this.margeLeftTwo = obtainStyledAttributes.getInt(1, 0);
        this.scaleOne = obtainStyledAttributes.getString(2);
        this.scaleTwo = obtainStyledAttributes.getString(3);
        obtainStyledAttributes.recycle();
        TextView textView = (TextView) inflate.findViewById(C1624R.C1628id.tv_scale_left);
        TextView textView2 = (TextView) inflate.findViewById(C1624R.C1628id.tv_scale_right);
        textView.setX((float) SizeUtils.dp2px((float) this.margeLeftOne));
        textView2.setX((float) SizeUtils.dp2px((float) (this.margeLeftTwo + this.margeLeftOne)));
        textView.setText(this.scaleOne);
        textView2.setText(this.scaleTwo);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }
}
