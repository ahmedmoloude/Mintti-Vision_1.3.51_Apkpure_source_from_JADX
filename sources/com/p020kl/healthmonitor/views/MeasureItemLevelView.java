package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.MeasureItemLevelView */
public class MeasureItemLevelView extends LinearLayout {
    private String measureType;
    private String measureUnit;
    private String measureValue;
    /* access modifiers changed from: private */
    public TextView tvMeasureValue;

    public MeasureItemLevelView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MeasureItemLevelView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MeasureItemLevelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(C1624R.layout.ecg_measure_item, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.MeasureItemLevelView);
        this.measureType = obtainStyledAttributes.getString(0);
        this.measureValue = obtainStyledAttributes.getString(2);
        this.measureUnit = obtainStyledAttributes.getString(1);
        obtainStyledAttributes.recycle();
        this.tvMeasureValue = (TextView) inflate.findViewById(C1624R.C1628id.tv_ecg_measure_value);
        ((TextView) inflate.findViewById(C1624R.C1628id.tv_ecg_measure_type)).setText(this.measureType);
        this.tvMeasureValue.setText(this.measureValue);
        ((TextView) inflate.findViewById(C1624R.C1628id.tv_ecg_measure_unit)).setText(this.measureUnit);
    }

    public void setResultValue(final String str) {
        post(new Runnable() {
            public void run() {
                MeasureItemLevelView.this.tvMeasureValue.setText(str);
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
