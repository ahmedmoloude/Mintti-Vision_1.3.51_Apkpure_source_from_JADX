package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.UserItemView */
public class UserItemView extends RelativeLayout {
    private int imgId;
    private String itemText;
    private String itemUnit;
    /* access modifiers changed from: private */
    public TextView textViewUnit;

    public UserItemView(Context context) {
        this(context, (AttributeSet) null);
    }

    public UserItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public UserItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(C1624R.layout.my_item_view, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.UserItemView);
        this.itemText = obtainStyledAttributes.getString(0);
        this.imgId = obtainStyledAttributes.getResourceId(2, 0);
        this.itemUnit = obtainStyledAttributes.getString(1);
        obtainStyledAttributes.recycle();
        ImageView imageView = (ImageView) inflate.findViewById(C1624R.C1628id.iv_myitem_imgleft);
        TextView textView = (TextView) inflate.findViewById(C1624R.C1628id.tv_myitem_text);
        this.textViewUnit = (TextView) inflate.findViewById(C1624R.C1628id.tv_myitem_unit);
        if (this.imgId == 0) {
            imageView.setVisibility(8);
        }
        imageView.setImageResource(this.imgId);
        textView.setText(this.itemText);
        this.textViewUnit.setText(this.itemUnit);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public void setItemUnit(final String str) {
        post(new Runnable() {
            public void run() {
                UserItemView.this.textViewUnit.setText(str);
            }
        });
    }
}
