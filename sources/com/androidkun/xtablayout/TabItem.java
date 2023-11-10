package com.androidkun.xtablayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.C1077R;

public final class TabItem extends View {
    final int mCustomLayout;
    final Drawable mIcon;
    final CharSequence mText;

    public TabItem(Context context) {
        this(context, (AttributeSet) null);
    }

    public TabItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, C1077R.styleable.TabItem);
        this.mText = obtainStyledAttributes.getText(C1077R.styleable.TabItem_android_text);
        this.mIcon = obtainStyledAttributes.getDrawable(C1077R.styleable.TabItem_android_icon);
        this.mCustomLayout = obtainStyledAttributes.getResourceId(C1077R.styleable.TabItem_android_layout, 0);
        obtainStyledAttributes.recycle();
    }
}
