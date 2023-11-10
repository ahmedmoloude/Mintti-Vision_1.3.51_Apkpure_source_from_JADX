package com.p020kl.healthmonitor.history;

import android.os.Bundle;
import android.view.View;
import butterknife.OnClick;
import com.p020kl.commonbase.bean.BPEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.BPTableManager;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.bean.HistoryItemBean;
import com.p020kl.healthmonitor.views.ChartView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.history.BPHistoryListFragment */
public class BPHistoryListFragment extends BaseListHistoryFragment<BPEntity> {
    /* access modifiers changed from: protected */
    public String getMeasureType() {
        return Constants.f838BP;
    }

    /* access modifiers changed from: protected */
    public String getUnit() {
        return Constants.BP_UNIT;
    }

    public /* bridge */ /* synthetic */ void createSharePdf() {
        super.createSharePdf();
    }

    public /* bridge */ /* synthetic */ void onBindView(Bundle bundle, View view) {
        super.onBindView(bundle, view);
    }

    @OnClick({3025, 3026})
    public /* bridge */ /* synthetic */ void onClick(View view) {
        super.onClick(view);
    }

    public /* bridge */ /* synthetic */ void onDestroy() {
        super.onDestroy();
    }

    public /* bridge */ /* synthetic */ void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    public /* bridge */ /* synthetic */ Object setLayout() {
        return super.setLayout();
    }

    public static BPHistoryListFragment newInstance(int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putInt("year", i);
        bundle.putInt("month", i2);
        BPHistoryListFragment bPHistoryListFragment = new BPHistoryListFragment();
        bPHistoryListFragment.setArguments(bundle);
        return bPHistoryListFragment;
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.blood_pressure);
    }

    /* access modifiers changed from: protected */
    public void getAndSavePdfImg(View view, ChartView chartView, int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int size = this.checkedData.size();
        int[] iArr = new int[size];
        int[] iArr2 = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            String[] split = ((HistoryItemBean) this.checkedData.get(i2)).getMeasureValue().split("/");
            iArr2[i2] = Integer.parseInt(split[0]);
            iArr[i2] = Integer.parseInt(split[1]);
        }
        int i3 = i;
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.clear();
            arrayList2.clear();
            arrayList3.clear();
            int i5 = i4 * 20;
            for (int i6 = i5; i6 < i5 + 20; i6++) {
                if (i6 < size) {
                    arrayList.add(DateUtils.getFormatDate(((HistoryItemBean) this.checkedData.get(i6)).getCreateTime(), Constants.TIME_FORMAT_XITEM) + "");
                    arrayList2.add(Integer.valueOf(iArr[i6]));
                    arrayList3.add(Integer.valueOf(iArr2[i6]));
                }
            }
            chartView.setBPMValue(arrayList, arrayList2, arrayList3, -1, Constants.BPM_UNIT);
            layoutView(view);
            getAndSaveBitmap(view, i4);
        }
    }

    /* access modifiers changed from: protected */
    public void addChartDataToList() {
        String str;
        StringUtils.getString(C1624R.string.normal);
        for (int i = 0; i < this.monthDatas.size(); i++) {
            int systolicPressure = ((BPEntity) this.monthDatas.get(i)).getSystolicPressure();
            int diastolicPressure = ((BPEntity) this.monthDatas.get(i)).getDiastolicPressure();
            if (systolicPressure > 140 || diastolicPressure > 90) {
                str = StringUtils.getString(C1624R.string.high);
            } else if (systolicPressure < 90 || diastolicPressure < 60) {
                str = StringUtils.getString(C1624R.string.low);
            } else {
                str = StringUtils.getString(C1624R.string.normal);
            }
            String str2 = str;
            List list = this.historyDatas;
            String formatDate = DateUtils.getFormatDate(((BPEntity) this.monthDatas.get(i)).getCreateTime(), StringUtils.getString(C1624R.string.time_format_item));
            long createTime = ((BPEntity) this.monthDatas.get(i)).getCreateTime();
            HistoryItemBean historyItemBean = r6;
            HistoryItemBean historyItemBean2 = new HistoryItemBean(formatDate, createTime, systolicPressure + "/" + diastolicPressure, str2, DateUtils.getFormatDate(((BPEntity) this.monthDatas.get(i)).getCreateTime(), StringUtils.getString(C1624R.string.time_format_item_top)), Constants.BP_LIST, ((BPEntity) this.monthDatas.get(i)).getYear(), ((BPEntity) this.monthDatas.get(i)).getMonth(), ((BPEntity) this.monthDatas.get(i)).getDay());
            list.add(historyItemBean);
        }
    }

    /* access modifiers changed from: protected */
    public List<BPEntity> getNativeData() {
        return BPTableManager.queryByMonth(SpManager.getMemberId(), getArguments().getInt("year"), getArguments().getInt("month"));
    }
}
