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
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.utils.SizeUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.commonbase.views.UpgradeDialog */
public class UpgradeDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private ArrayList<String> mLogList;
    private onLaterListener mOnLaterListener;
    private onUpdateListener mOnUpdateListener;
    private TextView tvTitle;
    private TextView tvVersion;

    /* renamed from: com.kl.commonbase.views.UpgradeDialog$onLaterListener */
    public interface onLaterListener {
        void onLaterClicked(UpgradeDialog upgradeDialog);
    }

    /* renamed from: com.kl.commonbase.views.UpgradeDialog$onUpdateListener */
    public interface onUpdateListener {
        void onUpdateClicked(UpgradeDialog upgradeDialog);
    }

    public void setOnLaterListener(onLaterListener onlaterlistener) {
        this.mOnLaterListener = onlaterlistener;
    }

    public void setOnUpdateListener(onUpdateListener onupdatelistener) {
        this.mOnUpdateListener = onupdatelistener;
    }

    public UpgradeDialog(Context context, List<String> list) {
        super(context);
        ArrayList<String> arrayList = new ArrayList<>();
        this.mLogList = arrayList;
        this.mContext = context;
        arrayList.addAll(list);
    }

    public UpgradeDialog(Context context, int i, ArrayList<String> arrayList) {
        super(context, i);
        ArrayList<String> arrayList2 = new ArrayList<>();
        this.mLogList = arrayList2;
        this.mContext = context;
        arrayList2.addAll(arrayList);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View inflate = LayoutInflater.from(this.mContext).inflate(C1544R.layout.common_dialog_firm_upgrade, (ViewGroup) null);
        setContentView(inflate);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        attributes.width = SizeUtils.dp2px(300.0f);
        window.setAttributes(attributes);
        window.setBackgroundDrawableResource(17170445);
        initView(inflate);
    }

    private void initView(View view) {
        ((Button) view.findViewById(C1544R.C1548id.btn_later)).setOnClickListener(this);
        ((Button) view.findViewById(C1544R.C1548id.btn_update)).setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(C1544R.C1548id.rv_log);
        LogsAdapter logsAdapter = new LogsAdapter(C1544R.layout.tiem_update_content, this.mLogList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        recyclerView.setAdapter(logsAdapter);
        this.tvTitle = (TextView) view.findViewById(C1544R.C1548id.tv_new_title);
        this.tvVersion = (TextView) view.findViewById(C1544R.C1548id.tv_new_version_code);
    }

    public void onClick(View view) {
        onUpdateListener onupdatelistener;
        int id = view.getId();
        if (id == C1544R.C1548id.btn_later) {
            onLaterListener onlaterlistener = this.mOnLaterListener;
            if (onlaterlistener != null) {
                onlaterlistener.onLaterClicked(this);
            }
        } else if (id == C1544R.C1548id.btn_update && (onupdatelistener = this.mOnUpdateListener) != null) {
            onupdatelistener.onUpdateClicked(this);
        }
    }

    public void setTvTitle(String str) {
        this.tvTitle.setText(str);
    }

    public void setVersion(String str) {
        this.tvVersion.setText(str);
    }

    /* renamed from: com.kl.commonbase.views.UpgradeDialog$LogsAdapter */
    public static class LogsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public LogsAdapter(int i, List<String> list) {
            super(i, list);
        }

        /* access modifiers changed from: protected */
        public void convert(BaseViewHolder baseViewHolder, String str) {
            baseViewHolder.setText(C1544R.C1548id.tv_log_content, (CharSequence) str);
        }
    }
}
