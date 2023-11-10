package com.p020kl.healthmonitor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.MeasureItemLevelView;
import java.util.List;

/* renamed from: com.kl.healthmonitor.adapter.HistoryEcgItemAdapter */
public class HistoryEcgItemAdapter extends BaseQuickAdapter<ECGEntity, BaseViewHolder> {
    private Context context;
    private List<ECGEntity> datas;
    /* access modifiers changed from: private */
    public OnItemClickListener itemClickListener;
    private int position = 0;
    private String type;

    /* renamed from: com.kl.healthmonitor.adapter.HistoryEcgItemAdapter$OnItemClickListener */
    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public HistoryEcgItemAdapter(Context context2, List<ECGEntity> list, String str) {
        super(C1624R.layout.ecg_history_item, list);
        this.datas = list;
        this.context = context2;
        this.type = str;
    }

    /* access modifiers changed from: protected */
    public void convert(final BaseViewHolder baseViewHolder, ECGEntity eCGEntity) {
        if (this.datas.size() != 0 && eCGEntity != null) {
            int adapterPosition = baseViewHolder.getAdapterPosition();
            this.position = adapterPosition;
            String valueOf = String.valueOf(this.datas.get(adapterPosition).getYear());
            String valueOf2 = String.valueOf(this.datas.get(this.position).getMonth());
            TextView textView = (TextView) baseViewHolder.getView(C1624R.C1628id.tv_list_data);
            View view = baseViewHolder.itemView;
            view.setOnClickListener((View.OnClickListener) null);
            if (this.type.equals(Constants.ECG_LIST)) {
                int i = this.position;
                if (i == 0) {
                    textView.setVisibility(0);
                    baseViewHolder.setText((int) C1624R.C1628id.tv_list_data, (CharSequence) DateUtils.getFormatDate(eCGEntity.getCreateTime(), Constants.TIME_FORMAT_ITEM_TOP));
                } else if (!String.valueOf(this.datas.get(i - 1).getYear()).equals(valueOf) || !String.valueOf(this.datas.get(this.position - 1).getMonth()).equals(valueOf2)) {
                    textView.setVisibility(0);
                    baseViewHolder.setText((int) C1624R.C1628id.tv_list_data, (CharSequence) DateUtils.getFormatDate(eCGEntity.getCreateTime(), Constants.TIME_FORMAT_ITEM_TOP));
                } else {
                    textView.setVisibility(8);
                }
            }
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    HistoryEcgItemAdapter.this.itemClickListener.onItemClick(baseViewHolder.getAdapterPosition());
                }
            });
            baseViewHolder.setText((int) C1624R.C1628id.tv_ecg_data, (CharSequence) DateUtils.getFormatDate(eCGEntity.getCreateTime(), Constants.TIME_FORMAT_TIMEFULL) + "");
            MeasureItemLevelView measureItemLevelView = (MeasureItemLevelView) baseViewHolder.getView(C1624R.C1628id.mlv_ecg_rrimax);
            MeasureItemLevelView measureItemLevelView2 = (MeasureItemLevelView) baseViewHolder.getView(C1624R.C1628id.mlv_ecg_rrimin);
            if (eCGEntity.getRriMax() > 2000 || eCGEntity.getRriMin() < 300 || eCGEntity.getRriMax() < eCGEntity.getRriMin()) {
                measureItemLevelView.setResultValue("0");
                measureItemLevelView2.setResultValue("0");
            } else {
                measureItemLevelView.setResultValue(eCGEntity.getRriMax() + "");
                measureItemLevelView2.setResultValue(eCGEntity.getRriMin() + "");
            }
            ((MeasureItemLevelView) baseViewHolder.getView(C1624R.C1628id.mlv_ecg_heart_rate)).setResultValue(eCGEntity.getAvgHr() + "");
            ((MeasureItemLevelView) baseViewHolder.getView(C1624R.C1628id.mlv_ecg_time)).setResultValue(eCGEntity.getDuration() + "");
            ((MeasureItemLevelView) baseViewHolder.getView(C1624R.C1628id.mlv_ecg_hrv)).setResultValue(eCGEntity.getHrv() + "");
            ((MeasureItemLevelView) baseViewHolder.getView(C1624R.C1628id.mlv_ecg_br)).setResultValue(eCGEntity.getBr() + "");
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }
}
