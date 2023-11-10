package com.p020kl.healthmonitor.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.DisclaimerDialog */
public class DisclaimerDialog extends Dialog implements View.OnClickListener {
    private CheckBox cbDis;
    private boolean isShowCheck;
    private Button mBtnCancel;
    private ICancelClickListener mCancelClickListener;
    private Context mContext;
    private NestedScrollView mNsvView;
    private TextView tvDisclaimer;

    /* renamed from: com.kl.healthmonitor.views.DisclaimerDialog$ICancelClickListener */
    public interface ICancelClickListener {
        void onCancelClick();
    }

    public void setCancelClickListener(ICancelClickListener iCancelClickListener) {
        this.mCancelClickListener = iCancelClickListener;
    }

    public DisclaimerDialog(Context context) {
        super(context, C1624R.C1631style.CommonDialogTheme);
        this.mContext = context;
        this.isShowCheck = true;
        initView();
    }

    public DisclaimerDialog(Context context, boolean z) {
        super(context, C1624R.C1631style.CommonDialogTheme);
        this.mContext = context;
        this.isShowCheck = z;
        initView();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCanceledOnTouchOutside(false);
    }

    private void initView() {
        setContentView(LayoutInflater.from(this.mContext).inflate(C1624R.layout.dialog_disclaimer, (ViewGroup) null));
        Button button = (Button) findViewById(C1624R.C1628id.btn_cancel);
        this.mBtnCancel = button;
        button.setOnClickListener(this);
        this.mNsvView = (NestedScrollView) findViewById(C1624R.C1628id.nsv_device);
        this.tvDisclaimer = (TextView) findViewById(C1624R.C1628id.tv_disclaimer);
        this.cbDis = (CheckBox) findViewById(C1624R.C1628id.cb_disclaimer);
        this.tvDisclaimer.setText(getContext().getString(C1624R.string.disclaimer_content));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        if (!this.isShowCheck) {
            this.cbDis.setVisibility(8);
        }
    }

    public void onClick(View view) {
        if (!this.isShowCheck) {
            cancel();
        } else if (this.cbDis.isChecked()) {
            ICancelClickListener iCancelClickListener = this.mCancelClickListener;
            if (iCancelClickListener != null) {
                iCancelClickListener.onCancelClick();
            }
            SpManager.setIsReadDisclaimer(true);
            cancel();
        } else {
            ToastUtil.showLongToast(this.mContext.getString(C1624R.string.please_read_disclaimer));
        }
    }
}
