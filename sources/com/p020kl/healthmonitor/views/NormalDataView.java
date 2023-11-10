package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.NormalDataView */
public class NormalDataView extends LinearLayout {
    private String text;
    /* access modifiers changed from: private */
    public TextView tvText;

    public NormalDataView(Context context) {
        this(context, (AttributeSet) null);
    }

    public NormalDataView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NormalDataView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(C1624R.layout.normal_data, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.NormalDataView);
        this.text = obtainStyledAttributes.getString(0);
        obtainStyledAttributes.recycle();
        TextView textView = (TextView) inflate.findViewById(C1624R.C1628id.tv_normal_text);
        this.tvText = textView;
        textView.setText(this.text);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public void setValue(final String str) {
        post(new Runnable() {
            public void run() {
                NormalDataView.this.tvText.setText(str);
            }
        });
    }
}
