package com.p020kl.healthmonitor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.TemperatureUtils;
import com.p020kl.commonbase.utils.UnitUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.bean.HistoryItemBean;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.adapter.HistoryListItemAdapter */
public class HistoryListItemAdapter extends BaseQuickAdapter<HistoryItemBean, BaseViewHolder> {
    /* access modifiers changed from: private */
    public List<HistoryItemBean> checkedData = new ArrayList();
    private Context context;
    /* access modifiers changed from: private */
    public List<HistoryItemBean> datas;
    private OnItemClickListener onItemClickListener = null;
    private int position = 0;
    private int tempeUnitType;
    private String[] values;

    /* renamed from: com.kl.healthmonitor.adapter.HistoryListItemAdapter$OnItemClickListener */
    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public HistoryListItemAdapter(Context context2, List<HistoryItemBean> list) {
        super(C1624R.layout.bthistory_list_item, list);
        this.datas = list;
        this.context = context2;
        this.tempeUnitType = SpManager.getTemperaTrueUnit();
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder baseViewHolder, HistoryItemBean historyItemBean) {
        final BaseViewHolder baseViewHolder2 = baseViewHolder;
        if (this.datas.size() != 0 && historyItemBean != null) {
            this.position = baseViewHolder.getAdapterPosition();
            Log.d("position", "" + this.position);
            final CheckBox checkBox = (CheckBox) baseViewHolder2.itemView.findViewById(C1624R.C1628id.checkedImg);
            View findViewById = baseViewHolder2.itemView.findViewById(C1624R.C1628id.data_item);
            findViewById.setOnClickListener((View.OnClickListener) null);
            checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
            checkBox.setChecked(historyItemBean.isChecked());
            String valueOf = String.valueOf(historyItemBean.getYear());
            String valueOf2 = String.valueOf(historyItemBean.getMonth());
            LinearLayout linearLayout = (LinearLayout) baseViewHolder2.getView(C1624R.C1628id.ll_data_unit);
            String listType = historyItemBean.getListType();
            if (listType.equals(Constants.BT_LIST) || listType.equals(Constants.SPO2H_LIST) || listType.equals(Constants.BP_LIST) || listType.equals(Constants.BG_LIST)) {
                if (listType.equals(Constants.BT_LIST)) {
                    baseViewHolder2.setText((int) C1624R.C1628id.tv_list_unitvalue, (CharSequence) TemperatureUtils.tempeConversionIntUnit(this.tempeUnitType));
                } else if (listType.equals(Constants.SPO2H_LIST)) {
                    baseViewHolder2.setText((int) C1624R.C1628id.tv_list_unitvalue, (CharSequence) Constants.SPO2_UNIT);
                } else if (listType.equals(Constants.BP_LIST)) {
                    baseViewHolder2.setText((int) C1624R.C1628id.tv_list_unitvalue, (CharSequence) Constants.BP_UNIT);
                } else if (listType.equals(Constants.BG_LIST)) {
                    if (SpManager.getBgUnit() == 0) {
                        baseViewHolder2.setText((int) C1624R.C1628id.tv_list_unitvalue, (CharSequence) Constants.BG_UNIT);
                    } else {
                        baseViewHolder2.setText((int) C1624R.C1628id.tv_list_unitvalue, (CharSequence) "mg/dl");
                    }
                }
                int i = this.position;
                if (i == 0) {
                    linearLayout.setVisibility(0);
                    baseViewHolder2.setText((int) C1624R.C1628id.tv_list_data, (CharSequence) historyItemBean.getListData());
                } else if (!String.valueOf(this.datas.get(i - 1).getYear()).equals(valueOf) || !String.valueOf(this.datas.get(this.position - 1).getMonth()).equals(valueOf2)) {
                    linearLayout.setVisibility(0);
                    baseViewHolder2.setText((int) C1624R.C1628id.tv_list_data, (CharSequence) historyItemBean.getListData());
                } else {
                    linearLayout.setVisibility(8);
                }
            }
            if (listType.equals(Constants.BG_LIST) || listType.equals(Constants.BG_HOME)) {
                this.values = historyItemBean.getMeasureValue().split("/");
                baseViewHolder2.setText((int) C1624R.C1628id.tv_history_item_time, (CharSequence) historyItemBean.getMeasureTime());
                baseViewHolder2.setText((int) C1624R.C1628id.tv_history_item_value, (CharSequence) UnitUtil.getBgValue(Double.parseDouble(this.values[0])) + "/");
                baseViewHolder2.setText((int) C1624R.C1628id.tv_history_item_state, (CharSequence) this.values[1]);
                baseViewHolder2.setText((int) C1624R.C1628id.tv_history_item_result, (CharSequence) historyItemBean.getMeasureResult());
            } else if (listType.equals(Constants.BT_HOME) || listType.equals(Constants.BT_LIST)) {
                baseViewHolder2.setText((int) C1624R.C1628id.tv_history_item_time, (CharSequence) historyItemBean.getMeasureTime());
                baseViewHolder2.setText((int) C1624R.C1628id.tv_history_item_value, (CharSequence) TemperatureUtils.tempeTypeConversionStrD(Double.valueOf(historyItemBean.getMeasureValue()).doubleValue()));
                baseViewHolder2.setText((int) C1624R.C1628id.tv_history_item_result, (CharSequence) historyItemBean.getMeasureResult());
            } else {
                baseViewHolder2.setText((int) C1624R.C1628id.tv_history_item_time, (CharSequence) historyItemBean.getMeasureTime());
                baseViewHolder2.setText((int) C1624R.C1628id.tv_history_item_value, (CharSequence) historyItemBean.getMeasureValue());
                baseViewHolder2.setText((int) C1624R.C1628id.tv_history_item_result, (CharSequence) historyItemBean.getMeasureResult());
            }
            String measureResult = historyItemBean.getMeasureResult();
            if (measureResult.equals(StringUtils.getString(C1624R.string.normal))) {
                baseViewHolder2.setTextColor(C1624R.C1628id.tv_history_item_result, this.context.getResources().getColor(C1624R.C1626color.darkGreen));
            } else if (measureResult.equals(StringUtils.getString(C1624R.string.abnormal)) || measureResult.equals(StringUtils.getString(C1624R.string.low_fever)) || measureResult.equals(StringUtils.getString(C1624R.string.high_fever)) || measureResult.equals(StringUtils.getString(C1624R.string.high)) || measureResult.equals(StringUtils.getString(C1624R.string.middle_fever))) {
                baseViewHolder2.setTextColor(C1624R.C1628id.tv_history_item_result, this.context.getResources().getColor(C1624R.C1626color.reded4b49));
            } else {
                baseViewHolder2.setTextColor(C1624R.C1628id.tv_history_item_result, this.context.getResources().getColor(C1624R.C1626color.yellowf8d253));
            }
            findViewById.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.d("点击事件", "haha");
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                    } else {
                        checkBox.setChecked(true);
                    }
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    Log.d("checked", "" + z);
                    ((HistoryItemBean) HistoryListItemAdapter.this.datas.get(baseViewHolder2.getAdapterPosition())).setChecked(z);
                    if (z) {
                        if (!HistoryListItemAdapter.this.checkedData.contains(HistoryListItemAdapter.this.datas.get(baseViewHolder2.getAdapterPosition()))) {
                            HistoryListItemAdapter.this.checkedData.add(HistoryListItemAdapter.this.datas.get(baseViewHolder2.getAdapterPosition()));
                        }
                    } else if (HistoryListItemAdapter.this.checkedData.contains(HistoryListItemAdapter.this.datas.get(baseViewHolder2.getAdapterPosition()))) {
                        HistoryListItemAdapter.this.checkedData.remove(HistoryListItemAdapter.this.datas.get(baseViewHolder2.getAdapterPosition()));
                    }
                }
            });
        }
    }

    public List<HistoryItemBean> getCheckedData() {
        return this.checkedData;
    }

    public void setCheckedData(List<HistoryItemBean> list) {
        for (int i = 0; i < list.size(); i++) {
            if (!this.checkedData.contains(list.get(i))) {
                this.checkedData.add(list.get(i));
            }
        }
    }

    public void removeCheckedData(List<HistoryItemBean> list) {
        this.checkedData.removeAll(list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }
}
