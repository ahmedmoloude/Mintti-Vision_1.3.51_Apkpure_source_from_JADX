package com.p020kl.commonbase.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p020kl.commonbase.C1544R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.commonbase.views.BaseBottomItemDialog */
public class BaseBottomItemDialog extends Dialog implements BaseQuickAdapter.OnItemClickListener {
    private BottomItemAdapter bottomItemAdapter;
    private Context context;
    private OnOptionClick onOptionClick;
    private RecyclerView rvSelector;
    private List<String> stringList;

    /* renamed from: com.kl.commonbase.views.BaseBottomItemDialog$OnOptionClick */
    public interface OnOptionClick {
        void onOptionClick(BaseBottomItemDialog baseBottomItemDialog, String str, int i);
    }

    public BaseBottomItemDialog(Context context2, List<String> list) {
        super(context2, C1544R.C1551style.BottomDialogStyle);
        ArrayList arrayList = new ArrayList();
        this.stringList = arrayList;
        this.context = context2;
        arrayList.addAll(list);
    }

    public void setOnOptionClick(OnOptionClick onOptionClick2) {
        this.onOptionClick = onOptionClick2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1544R.layout.dialog_bottom_selector);
        Window window = getWindow();
        window.setWindowAnimations(C1544R.C1551style.Animation_Popup);
        window.setGravity(80);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = -1;
        attributes.height = -2;
        window.setAttributes(attributes);
        initView();
    }

    private void initView() {
        this.rvSelector = (RecyclerView) findViewById(C1544R.C1548id.rv_selector);
        this.bottomItemAdapter = new BottomItemAdapter(this.stringList);
        this.rvSelector.setLayoutManager(new LinearLayoutManager(this.context));
        this.rvSelector.setAdapter(this.bottomItemAdapter);
        this.rvSelector.addItemDecoration(new DividerItemDecoration(this.context, 1));
        this.bottomItemAdapter.setOnItemClickListener(this);
    }

    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        OnOptionClick onOptionClick2 = this.onOptionClick;
        if (onOptionClick2 != null) {
            onOptionClick2.onOptionClick(this, this.stringList.get(i), i);
        }
    }

    /* renamed from: com.kl.commonbase.views.BaseBottomItemDialog$BottomItemAdapter */
    public static class BottomItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public BottomItemAdapter(List<String> list) {
            super(C1544R.layout.item_bottom_selector, list);
        }

        /* access modifiers changed from: protected */
        public void convert(BaseViewHolder baseViewHolder, String str) {
            baseViewHolder.setText(C1544R.C1548id.tv_option, (CharSequence) str);
        }
    }
}
