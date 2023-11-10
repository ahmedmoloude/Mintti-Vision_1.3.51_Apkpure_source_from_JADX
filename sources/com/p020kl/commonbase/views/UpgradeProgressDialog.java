package com.p020kl.commonbase.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.utils.FileUtils;

/* renamed from: com.kl.commonbase.views.UpgradeProgressDialog */
public class UpgradeProgressDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private int currentProgress;
    private int height;
    private boolean isShowFileSize;
    private TextView mPercent;
    private ProgressBar mProgress;
    private TextView mTitle;
    private TextView mTotalSize;
    private int maxProgress;
    private OnCancelListener onCaneclListener;
    private String title;
    private int width;

    /* renamed from: com.kl.commonbase.views.UpgradeProgressDialog$OnCancelListener */
    public interface OnCancelListener {
        void onCancelClicked(UpgradeProgressDialog upgradeProgressDialog);
    }

    public void onClick(View view) {
        OnCancelListener onCancelListener;
        if (view.getId() == C1544R.C1548id.btn_cancel && (onCancelListener = this.onCaneclListener) != null) {
            onCancelListener.onCancelClicked(this);
        }
    }

    private UpgradeProgressDialog(Builder builder) {
        super(builder.context);
        this.isShowFileSize = false;
        this.context = builder.context;
        this.title = builder.title;
        this.maxProgress = builder.maxProgress;
        this.currentProgress = builder.currentProgress;
        this.onCaneclListener = builder.onCancelListener;
        this.width = builder.width;
        this.height = builder.height;
        this.isShowFileSize = builder.isShowFileSize;
    }

    private UpgradeProgressDialog(Builder builder, int i) {
        super(builder.context, i);
        this.isShowFileSize = false;
        this.context = builder.context;
        this.title = builder.title;
        this.maxProgress = builder.maxProgress;
        this.currentProgress = builder.currentProgress;
        this.onCaneclListener = builder.onCancelListener;
        this.width = builder.width;
        this.height = builder.height;
        this.isShowFileSize = builder.isShowFileSize;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View inflate = LayoutInflater.from(this.context).inflate(C1544R.layout.common_dialog_upgrade_progress, (ViewGroup) null);
        setContentView(inflate);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        attributes.height = this.height;
        attributes.width = this.width;
        window.setAttributes(attributes);
        window.setBackgroundDrawableResource(17170445);
        setCancelable(false);
        initView(inflate);
    }

    private void initView(View view) {
        this.mProgress = (ProgressBar) view.findViewById(C1544R.C1548id.pb_progress);
        this.mTitle = (TextView) view.findViewById(C1544R.C1548id.tv_title);
        this.mPercent = (TextView) view.findViewById(C1544R.C1548id.tv_percent);
        this.mTotalSize = (TextView) view.findViewById(C1544R.C1548id.tv_total_size);
        ((Button) view.findViewById(C1544R.C1548id.btn_cancel)).setOnClickListener(this);
        if (this.isShowFileSize) {
            this.mTotalSize.setVisibility(0);
        } else {
            this.mTotalSize.setVisibility(8);
        }
        initData();
    }

    private void initData() {
        this.mTitle.setText(this.title);
        this.mProgress.setMax(this.maxProgress);
        this.mProgress.setProgress(this.currentProgress);
        formatProgress(this.currentProgress);
    }

    public void setTitle(String str) {
        this.title = str;
        this.mTitle.setText(str);
    }

    public void setCurrentProgress(int i) {
        this.mProgress.setProgress(i);
        formatProgress(i);
    }

    public void setMaxProgress(int i) {
        this.maxProgress = i;
        this.mProgress.setMax(i);
    }

    private void formatProgress(int i) {
        int i2 = this.maxProgress;
        if (i2 != 0) {
            if (this.isShowFileSize) {
                int i3 = (i * 100) / i2;
                TextView textView = this.mPercent;
                textView.setText(i3 + "%");
                String formatFileSize = FileUtils.formatFileSize((long) this.maxProgress);
                String formatFileSize2 = FileUtils.formatFileSize((long) i);
                TextView textView2 = this.mTotalSize;
                textView2.setText(formatFileSize2 + "/" + formatFileSize);
                return;
            }
            TextView textView3 = this.mPercent;
            textView3.setText(i + "%");
        }
    }

    /* renamed from: com.kl.commonbase.views.UpgradeProgressDialog$Builder */
    public static class Builder {
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public int currentProgress = 0;
        /* access modifiers changed from: private */
        public int height;
        /* access modifiers changed from: private */
        public boolean isShowFileSize;
        /* access modifiers changed from: private */
        public int maxProgress = 0;
        /* access modifiers changed from: private */
        public OnCancelListener onCancelListener;
        private int theme = 0;
        /* access modifiers changed from: private */
        public String title;
        /* access modifiers changed from: private */
        public int width;

        public Builder(Context context2) {
            this.context = context2;
        }

        public Builder(Context context2, int i) {
            this.context = context2;
            this.theme = i;
        }

        public Builder setTitle(String str) {
            this.title = str;
            return this;
        }

        public Builder setMaxProgress(int i) {
            this.maxProgress = i;
            return this;
        }

        public Builder setCurrentProgres(int i) {
            this.currentProgress = i;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener2) {
            this.onCancelListener = onCancelListener2;
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

        public Builder isShowFileSize(boolean z) {
            this.isShowFileSize = z;
            return this;
        }

        public UpgradeProgressDialog build() {
            if (this.theme == 0) {
                return new UpgradeProgressDialog(this);
            }
            return new UpgradeProgressDialog(this, this.theme);
        }
    }
}
