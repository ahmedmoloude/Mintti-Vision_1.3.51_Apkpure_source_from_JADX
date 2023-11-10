package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.MeasureTopItemView */
public class MeasureTopItemView extends FrameLayout {
    /* access modifiers changed from: private */
    public TextView measureResult;
    private int measureTypeColor;
    private int measureTypeSize;
    private String measureTypeText;
    private TextView measureUnit;
    private int measureUnitColor;
    private int measureUnitSize;
    private String measureUnitText;
    /* access modifiers changed from: private */
    public TextView measureValue;
    private int measureValueColor;
    private int measureValueSize;
    private String measureValueText;

    public MeasureTopItemView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MeasureTopItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MeasureTopItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(C1624R.layout.measure_item_top, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.MeasureTopItemView);
        this.measureTypeText = obtainStyledAttributes.getString(2);
        this.measureValueText = obtainStyledAttributes.getString(8);
        this.measureUnitText = obtainStyledAttributes.getString(5);
        this.measureTypeSize = obtainStyledAttributes.getInteger(1, 15);
        this.measureValueSize = obtainStyledAttributes.getInteger(7, 35);
        this.measureUnitSize = obtainStyledAttributes.getInteger(4, 35);
        this.measureTypeColor = obtainStyledAttributes.getResourceId(0, C1624R.C1626color.gray_999);
        this.measureValueColor = obtainStyledAttributes.getResourceId(6, C1624R.C1626color.gray_333);
        this.measureUnitColor = obtainStyledAttributes.getResourceId(3, C1624R.C1626color.gray_333);
        obtainStyledAttributes.recycle();
        TextView textView = (TextView) inflate.findViewById(C1624R.C1628id.tv_measure_project);
        this.measureUnit = (TextView) inflate.findViewById(C1624R.C1628id.tv_unit);
        this.measureValue = (TextView) inflate.findViewById(C1624R.C1628id.tv_measure_value_top);
        this.measureResult = (TextView) inflate.findViewById(C1624R.C1628id.tv_measure_result);
        textView.setText(this.measureTypeText);
        textView.setTextSize((float) this.measureTypeSize);
        textView.setTextColor(context.getResources().getColor(this.measureTypeColor));
        this.measureValue.setText(this.measureValueText);
        this.measureValue.setTextSize((float) this.measureValueSize);
        this.measureValue.setTextColor(context.getResources().getColor(this.measureValueColor));
        this.measureUnit.setText(this.measureUnitText);
        this.measureUnit.setTextSize((float) this.measureUnitSize);
        this.measureUnit.setTextColor(context.getResources().getColor(this.measureUnitColor));
    }

    public void setMeasureValueText(final String str) {
        post(new Runnable() {
            public void run() {
                MeasureTopItemView.this.measureValue.setText(str);
            }
        });
    }

    public void setMeasureResult(final String str) {
        post(new Runnable() {
            public void run() {
                MeasureTopItemView.this.measureResult.setText(str);
            }
        });
    }

    public void setMeasureUnit(String str) {
        this.measureUnit.setText(str);
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
