package com.p020kl.healthmonitor.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.adapter.BleDeviceAdapter;

/* renamed from: com.kl.healthmonitor.views.BleDeviceDialog */
public class BleDeviceDialog extends Dialog implements View.OnClickListener {
    private BleDeviceAdapter mAdapter;
    private Button mBtnCancel;
    private ICancelClickListener mCancelClickListener;
    private Context mContext;
    private RecyclerView mRecyclerView;

    /* renamed from: com.kl.healthmonitor.views.BleDeviceDialog$ICancelClickListener */
    public interface ICancelClickListener {
        void onCancelClick();
    }

    public void setCancelClickListener(ICancelClickListener iCancelClickListener) {
        this.mCancelClickListener = iCancelClickListener;
    }

    public BleDeviceDialog(Context context, BleDeviceAdapter bleDeviceAdapter) {
        super(context, C1624R.C1631style.CommonDialogTheme);
        this.mContext = context;
        this.mAdapter = bleDeviceAdapter;
        initView();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCanceledOnTouchOutside(false);
    }

    private void initView() {
        setContentView(LayoutInflater.from(this.mContext).inflate(C1624R.layout.dialog_device_list, (ViewGroup) null));
        Button button = (Button) findViewById(C1624R.C1628id.btn_cancel);
        this.mBtnCancel = button;
        button.setOnClickListener(this);
        this.mRecyclerView = (RecyclerView) findViewById(C1624R.C1628id.rv_device);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    public void notifyChange() {
        this.mAdapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        ICancelClickListener iCancelClickListener;
        if (view.getId() == C1624R.C1628id.btn_cancel && (iCancelClickListener = this.mCancelClickListener) != null) {
            iCancelClickListener.onCancelClick();
        }
    }
}
