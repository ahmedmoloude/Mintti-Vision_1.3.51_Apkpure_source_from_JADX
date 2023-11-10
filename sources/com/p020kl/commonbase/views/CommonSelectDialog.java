package com.p020kl.commonbase.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.utils.SizeUtils;

/* renamed from: com.kl.commonbase.views.CommonSelectDialog */
public class CommonSelectDialog extends Dialog implements View.OnClickListener {
    private String cancelText;
    private boolean cancelTouchout;
    private int mAnimation;
    private Context mContext;
    private int mHeight;
    private View mView;
    private int mWidth;
    private String okText;
    private OnClickListener onClickListener;
    private String title;

    /* renamed from: com.kl.commonbase.views.CommonSelectDialog$OnClickListener */
    public interface OnClickListener {
        void onClick(CommonSelectDialog commonSelectDialog, boolean z);
    }

    public void onClick(View view) {
        OnClickListener onClickListener2;
        int id = view.getId();
        if (id == C1544R.C1548id.btn_cancel) {
            OnClickListener onClickListener3 = this.onClickListener;
            if (onClickListener3 != null) {
                onClickListener3.onClick(this, false);
            }
        } else if (id == C1544R.C1548id.btn_ok && (onClickListener2 = this.onClickListener) != null) {
            onClickListener2.onClick(this, true);
        }
    }

    private CommonSelectDialog(Builder builder) {
        this(builder, 0);
    }

    private CommonSelectDialog(Builder builder, int i) {
        super(builder.context, i);
        this.mContext = builder.context;
        this.mAnimation = builder.animation;
        this.mWidth = builder.width;
        this.mHeight = builder.height;
        this.mView = LayoutInflater.from(this.mContext).inflate(C1544R.layout.common_dialog_select, (ViewGroup) null);
        this.cancelTouchout = builder.cancelTouchOut;
        this.title = builder.title;
        this.onClickListener = builder.onClickListener;
        this.okText = builder.okText;
        this.cancelText = builder.cancelText;
    }

    public CommonSelectDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
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
        } else {
            window.setWindowAnimations(C1544R.C1551style.Animation_Popup);
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        if (this.mHeight == 0) {
            this.mHeight = SizeUtils.dp2px(100.0f);
        }
        if (this.mWidth == 0) {
            this.mWidth = SizeUtils.dp2px(300.0f);
        }
        attributes.height = this.mHeight;
        attributes.width = this.mWidth;
        window.setAttributes(attributes);
        window.setBackgroundDrawableResource(17170445);
        initView(this.mView);
    }

    private void initView(View view) {
        Button button = (Button) view.findViewById(C1544R.C1548id.btn_cancel);
        button.setOnClickListener(this);
        Button button2 = (Button) view.findViewById(C1544R.C1548id.btn_ok);
        button2.setOnClickListener(this);
        ((TextView) view.findViewById(C1544R.C1548id.tv_title)).setText(this.title);
        if (!TextUtils.isEmpty(this.okText)) {
            button2.setText(this.okText);
        }
        if (!TextUtils.isEmpty(this.cancelText)) {
            button.setText(this.cancelText);
        }
    }

    /* renamed from: com.kl.commonbase.views.CommonSelectDialog$Builder */
    public static final class Builder {
        /* access modifiers changed from: private */
        public int animation = -1;
        /* access modifiers changed from: private */
        public String cancelText;
        /* access modifiers changed from: private */
        public boolean cancelTouchOut;
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public int height = 0;
        /* access modifiers changed from: private */
        public String okText;
        /* access modifiers changed from: private */
        public OnClickListener onClickListener;
        private int themeResId = -1;
        /* access modifiers changed from: private */
        public String title;
        /* access modifiers changed from: private */
        public int width = 0;

        public Builder(Context context2) {
            this.context = context2;
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

        public Builder setTitle(String str) {
            this.title = str;
            return this;
        }

        public Builder setOnClickListener(OnClickListener onClickListener2) {
            this.onClickListener = onClickListener2;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean z) {
            this.cancelTouchOut = z;
            return this;
        }

        public Builder setOkText(String str) {
            this.okText = str;
            return this;
        }

        public Builder setCancelText(String str) {
            this.cancelText = str;
            return this;
        }

        public CommonSelectDialog build() {
            if (this.themeResId != -1) {
                return new CommonSelectDialog(this, this.themeResId);
            }
            return new CommonSelectDialog(this);
        }
    }
}
