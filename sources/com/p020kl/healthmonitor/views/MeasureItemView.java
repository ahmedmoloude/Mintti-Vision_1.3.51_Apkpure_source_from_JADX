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

/* renamed from: com.kl.healthmonitor.views.MeasureItemView */
public class MeasureItemView extends FrameLayout {
    private String measureType;
    private String measureUnit;
    private String measureValue;
    /* access modifiers changed from: private */
    public TextView tvMeasureValue;
    private String unit;

    public MeasureItemView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MeasureItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MeasureItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(C1624R.layout.measure_item, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1544R.styleable.MeasureItemView);
        this.measureType = obtainStyledAttributes.getString(0);
        this.measureValue = obtainStyledAttributes.getString(2);
        this.measureUnit = obtainStyledAttributes.getString(1);
        float dimension = obtainStyledAttributes.getDimension(4, (float) SizeUtils.sp2px(context, 35.0f));
        this.unit = obtainStyledAttributes.getString(3);
        obtainStyledAttributes.recycle();
        TextView textView = (TextView) inflate.findViewById(C1624R.C1628id.tv_measure_value);
        this.tvMeasureValue = textView;
        textView.setTextSize((float) SizeUtils.px2sp(dimension));
        ((TextView) inflate.findViewById(C1624R.C1628id.tv_measure_project)).setText(this.measureType);
        this.tvMeasureValue.setText(this.measureValue);
        ((TextView) inflate.findViewById(C1624R.C1628id.tv_measure_unit)).setText(this.measureUnit);
        ((TextView) inflate.findViewById(C1624R.C1628id.tv_unit)).setText(this.unit);
    }

    public void setMeasureValue(final String str) {
        post(new Runnable() {
            public void run() {
                if (MeasureItemView.this.tvMeasureValue != null) {
                    MeasureItemView.this.tvMeasureValue.setText(str);
                }
            }
        });
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
