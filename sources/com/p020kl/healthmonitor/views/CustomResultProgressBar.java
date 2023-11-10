package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.CustomResultProgressBar */
public class CustomResultProgressBar extends FrameLayout {
    private ConstraintLayout clNumber;
    /* access modifiers changed from: private */
    public ConstraintLayout clProgress;
    /* access modifiers changed from: private */
    public ImageView ivProgress;
    private TextView tvLeftNum;
    private TextView tvRightNum;
    private View viewLeft;
    private View viewMiddle;
    private View viewRight;

    public CustomResultProgressBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public CustomResultProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomResultProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(C1624R.layout.costom_progressbar_result, this);
        this.tvLeftNum = (TextView) inflate.findViewById(C1624R.C1628id.tv_left_number);
        this.tvRightNum = (TextView) inflate.findViewById(C1624R.C1628id.tv_right_number);
        this.viewLeft = inflate.findViewById(C1624R.C1628id.v_size_left);
        this.viewMiddle = inflate.findViewById(C1624R.C1628id.v_size_middle);
        this.viewRight = inflate.findViewById(C1624R.C1628id.v_size_right);
        this.ivProgress = (ImageView) inflate.findViewById(C1624R.C1628id.iv_progress);
        this.clNumber = (ConstraintLayout) inflate.findViewById(C1624R.C1628id.cl_number);
        this.clProgress = (ConstraintLayout) inflate.findViewById(C1624R.C1628id.cl_progress);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.CustomResultProgressBar);
        String string = obtainStyledAttributes.getString(0);
        String string2 = obtainStyledAttributes.getString(5);
        float f = obtainStyledAttributes.getFloat(2, 0.333f);
        float f2 = obtainStyledAttributes.getFloat(4, 0.333f);
        float f3 = obtainStyledAttributes.getFloat(7, 0.333f);
        int resourceId = obtainStyledAttributes.getResourceId(1, C1624R.C1627drawable.view_circke_shape_left);
        int resourceId2 = obtainStyledAttributes.getResourceId(3, C1624R.C1626color.yellowf8d253);
        int resourceId3 = obtainStyledAttributes.getResourceId(6, C1624R.C1627drawable.view_circke_shape);
        obtainStyledAttributes.recycle();
        this.tvLeftNum.setText(string);
        this.tvRightNum.setText(string2);
        this.viewLeft.setBackgroundResource(resourceId);
        this.viewMiddle.setBackgroundResource(resourceId2);
        this.viewRight.setBackgroundResource(resourceId3);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.clNumber);
        constraintSet.setHorizontalBias(C1624R.C1628id.tv_left_number, f);
        constraintSet.setHorizontalBias(C1624R.C1628id.tv_right_number, f + f2);
        constraintSet.applyTo(this.clNumber);
        constraintSet.clone(this.clProgress);
        constraintSet.setHorizontalWeight(C1624R.C1628id.v_size_left, f);
        constraintSet.setHorizontalWeight(C1624R.C1628id.v_size_middle, f2);
        constraintSet.setHorizontalWeight(C1624R.C1628id.v_size_right, f3);
        constraintSet.applyTo(this.clProgress);
    }

    public void setProgressWeight(final float f) {
        post(new Runnable() {
            public void run() {
                CustomResultProgressBar.this.ivProgress.setVisibility(0);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(CustomResultProgressBar.this.clProgress);
                constraintSet.setHorizontalBias(C1624R.C1628id.iv_progress, f);
                constraintSet.applyTo(CustomResultProgressBar.this.clProgress);
            }
        });
    }

    public void clearProgress() {
        this.ivProgress.setVisibility(4);
    }

    public void setLeftText(String str) {
        Log.d("setLeftText", "setLeftText: " + str);
        this.tvLeftNum.setText(str);
    }

    public void setRightText(String str) {
        this.tvRightNum.setText(str);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            int width = this.tvLeftNum.getWidth();
            int width2 = this.tvRightNum.getWidth();
            int width3 = this.viewLeft.getWidth();
            int width4 = this.viewRight.getWidth();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.tvLeftNum.getLayoutParams();
            layoutParams.setMarginStart(width3 - (width / 2));
            this.tvLeftNum.setLayoutParams(layoutParams);
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.tvRightNum.getLayoutParams();
            layoutParams2.setMarginEnd(width4 - (width2 / 2));
            this.tvRightNum.setLayoutParams(layoutParams2);
        }
    }
}
