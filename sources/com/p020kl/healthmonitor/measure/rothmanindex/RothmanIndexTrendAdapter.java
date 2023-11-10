package com.p020kl.healthmonitor.measure.rothmanindex;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.bean.rothmanindex.TrendBean;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.healthmonitor.C1624R;
import java.util.List;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.RothmanIndexTrendAdapter */
public class RothmanIndexTrendAdapter extends BaseQuickAdapter<TrendBean.WeightedScoresBean, BaseViewHolder> {
    private int selectedIndex = 0;

    public RothmanIndexTrendAdapter(List<TrendBean.WeightedScoresBean> list) {
        super(C1624R.layout.item_rothman_index_trends, list);
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder baseViewHolder, TrendBean.WeightedScoresBean weightedScoresBean) {
        baseViewHolder.setText((int) C1624R.C1628id.tv_trend_time, (CharSequence) DateUtils.getFormatDate(weightedScoresBean.getTimestamp(), Constants.TIME_FORMAT_TIMEFULL));
        baseViewHolder.setText((int) C1624R.C1628id.tv_trend_point, (CharSequence) String.valueOf(weightedScoresBean.getPoint()));
        if (baseViewHolder.getAdapterPosition() == this.selectedIndex) {
            baseViewHolder.setTextColor(C1624R.C1628id.tv_trend_time, BaseApplication.getInstance().getResources().getColor(C1624R.C1626color.colorPrimary));
        } else {
            baseViewHolder.setTextColor(C1624R.C1628id.tv_trend_time, BaseApplication.getInstance().getResources().getColor(C1624R.C1626color.gray_999));
        }
    }

    public void setSelectedIndex(int i) {
        this.selectedIndex = i;
        notifyDataSetChanged();
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }
}
