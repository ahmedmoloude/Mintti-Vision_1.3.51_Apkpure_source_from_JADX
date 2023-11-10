package com.p020kl.healthmonitor.adapter;

import android.util.Log;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.bean.MemberEntity;
import com.p020kl.healthmonitor.C1624R;
import java.util.List;

/* renamed from: com.kl.healthmonitor.adapter.MemberAdapter */
public class MemberAdapter extends BaseQuickAdapter<MemberEntity, BaseViewHolder> {
    private int checkPosition;
    private boolean isGreenText;
    /* access modifiers changed from: private */
    public OnMemberItemClickListener memberItemClickListener;
    private int position = -1;

    /* renamed from: com.kl.healthmonitor.adapter.MemberAdapter$OnMemberItemClickListener */
    public interface OnMemberItemClickListener {
        void memberItemClick(int i);
    }

    public MemberAdapter(int i, List<MemberEntity> list) {
        super(i, list);
    }

    /* access modifiers changed from: protected */
    public void convert(final BaseViewHolder baseViewHolder, MemberEntity memberEntity) {
        if (baseViewHolder.getAdapterPosition() == 0) {
            baseViewHolder.itemView.findViewById(C1624R.C1628id.tv_main).setVisibility(0);
        } else {
            baseViewHolder.itemView.findViewById(C1624R.C1628id.tv_main).setVisibility(4);
        }
        baseViewHolder.setText((int) C1624R.C1628id.tv_myitem_text, (CharSequence) memberEntity.getName());
        Log.d("heihei", baseViewHolder.getAdapterPosition() + "");
        if (!this.isGreenText || baseViewHolder.getAdapterPosition() != this.checkPosition) {
            baseViewHolder.setTextColor(C1624R.C1628id.tv_myitem_text, BaseApplication.getInstance().getResources().getColor(C1624R.C1626color.black));
        } else {
            baseViewHolder.setTextColor(C1624R.C1628id.tv_myitem_text, BaseApplication.getInstance().getResources().getColor(C1624R.C1626color.colorPrimary));
        }
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MemberAdapter.this.memberItemClickListener != null) {
                    MemberAdapter.this.memberItemClickListener.memberItemClick(baseViewHolder.getAdapterPosition());
                }
            }
        });
    }

    public void isGreenText(int i) {
        this.isGreenText = true;
        this.checkPosition = i;
    }

    public void isBlackText() {
        this.isGreenText = false;
    }

    public void setOnMemberItemClickListener(OnMemberItemClickListener onMemberItemClickListener) {
        this.memberItemClickListener = onMemberItemClickListener;
    }
}
