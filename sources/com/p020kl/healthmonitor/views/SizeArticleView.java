package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.SizeArticleView */
public class SizeArticleView extends FrameLayout {
    private int backgroundLeft;
    private int backgroundMid;
    private int backgroundRight;
    private int sizeLeft;
    private int sizeMiddle;
    private int sizeRight;

    public SizeArticleView(Context context) {
        this(context, (AttributeSet) null);
    }

    public SizeArticleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SizeArticleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(C1624R.layout.article_size, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.SizeArticleView);
        this.sizeLeft = obtainStyledAttributes.getInt(3, 0);
        this.sizeMiddle = obtainStyledAttributes.getInt(4, 0);
        this.sizeRight = obtainStyledAttributes.getInt(5, 0);
        this.backgroundLeft = obtainStyledAttributes.getResourceId(0, 0);
        this.backgroundMid = obtainStyledAttributes.getResourceId(1, 0);
        this.backgroundRight = obtainStyledAttributes.getResourceId(2, 0);
        obtainStyledAttributes.recycle();
        View findViewById = inflate.findViewById(C1624R.C1628id.v_size_left);
        View findViewById2 = inflate.findViewById(C1624R.C1628id.v_size_middle);
        View findViewById3 = inflate.findViewById(C1624R.C1628id.v_size_right);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById.getLayoutParams();
        layoutParams.width = SizeUtils.dp2px((float) this.sizeLeft);
        findViewById.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) findViewById2.getLayoutParams();
        layoutParams2.width = SizeUtils.dp2px((float) this.sizeMiddle);
        findViewById2.setLayoutParams(layoutParams2);
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) findViewById3.getLayoutParams();
        layoutParams3.width = SizeUtils.dp2px((float) this.sizeRight);
        findViewById3.setLayoutParams(layoutParams3);
        findViewById.setBackgroundResource(this.backgroundLeft);
        findViewById2.setBackgroundResource(this.backgroundMid);
        findViewById3.setBackgroundResource(this.backgroundRight);
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
