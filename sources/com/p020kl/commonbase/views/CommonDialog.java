package com.p020kl.commonbase.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import java.util.ArrayList;

/* renamed from: com.kl.commonbase.views.CommonDialog */
public class CommonDialog extends Dialog {
    private boolean cancelTouchout;
    private DialogInterface.OnDismissListener dismissListener;
    private int mAnimation;
    private Context mContext;
    private int mHeight;
    private int mPlaceX;
    private int mPlaceY;
    private View mView;
    private int mWidth;

    private CommonDialog(Builder builder) {
        super(builder.context);
        this.mContext = builder.context;
        this.mAnimation = builder.animation;
        this.mWidth = builder.width;
        this.mHeight = builder.height;
        this.mView = builder.view;
        this.mPlaceX = builder.placeX;
        this.mPlaceY = builder.placeY;
        this.cancelTouchout = builder.cancelTouchOut;
        this.dismissListener = builder.dismissListener;
    }

    private CommonDialog(Builder builder, int i) {
        super(builder.context, i);
        this.mContext = builder.context;
        this.mAnimation = builder.animation;
        this.mWidth = builder.width;
        this.mHeight = builder.height;
        this.mView = builder.view;
        this.mPlaceX = builder.placeX;
        this.mPlaceY = builder.placeY;
        this.cancelTouchout = builder.cancelTouchOut;
        this.dismissListener = builder.dismissListener;
    }

    public CommonDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(this.mView);
        setCanceledOnTouchOutside(this.cancelTouchout);
        Window window = getWindow();
        int i = this.mAnimation;
        if (i != -1) {
            window.setWindowAnimations(i);
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        attributes.height = this.mHeight;
        attributes.width = this.mWidth;
        attributes.x = this.mPlaceX;
        attributes.y = this.mPlaceY;
        window.setAttributes(attributes);
        window.setBackgroundDrawableResource(17170445);
    }

    /* renamed from: com.kl.commonbase.views.CommonDialog$Builder */
    public static final class Builder {
        /* access modifiers changed from: private */
        public int animation = -1;
        /* access modifiers changed from: private */
        public boolean cancelTouchOut;
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public DialogInterface.OnDismissListener dismissListener;
        /* access modifiers changed from: private */
        public int height;
        private View.OnClickListener onClickListener;
        private ArrayList<View> onClickViews;
        /* access modifiers changed from: private */
        public int placeX;
        /* access modifiers changed from: private */
        public int placeY;
        private int themeResId = -1;
        /* access modifiers changed from: private */
        public View view;
        /* access modifiers changed from: private */
        public int width;

        public Builder(Context context2) {
            ArrayList<View> arrayList = new ArrayList<>();
            this.onClickViews = arrayList;
            this.context = context2;
            arrayList.clear();
        }

        public Builder setView(int i) {
            this.view = LayoutInflater.from(this.context).inflate(i, (ViewGroup) null);
            return this;
        }

        public Builder setTheme(int i) {
            this.themeResId = i;
            return this;
        }

        public Builder setAnimation(int i) {
            this.animation = i;
            return this;
        }

        public Builder setWidth(int i) {
            this.width = i;
            return this;
        }

        public Builder setHeight(int i) {
            this.height = i;
            return this;
        }

        public Builder setPlaceX(int i) {
            this.placeX = i;
            return this;
        }

        public Builder setPlaceY(int i) {
            this.placeY = i;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean z) {
            this.cancelTouchOut = z;
            return this;
        }

        public Builder addViewOnClickListener(int i, View.OnClickListener onClickListener2) {
            this.view.findViewById(i).setOnClickListener(onClickListener2);
            return this;
        }

        public Builder addDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.dismissListener = onDismissListener;
            return this;
        }

        public CommonDialog build() {
            if (this.themeResId != -1) {
                return new CommonDialog(this, this.themeResId);
            }
            return new CommonDialog(this);
        }
    }
}
