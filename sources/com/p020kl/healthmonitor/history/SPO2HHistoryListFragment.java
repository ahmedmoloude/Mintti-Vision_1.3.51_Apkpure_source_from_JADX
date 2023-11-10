package com.p020kl.healthmonitor.history;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import butterknife.OnClick;
import com.p020kl.commonbase.bean.Spo2Entity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.Spo2hTableManager;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.bean.HistoryItemBean;
import com.p020kl.healthmonitor.views.ChartView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.history.SPO2HHistoryListFragment */
public class SPO2HHistoryListFragment extends BaseListHistoryFragment<Spo2Entity> {
    /* access modifiers changed from: protected */
    public String getMeasureType() {
        return Constants.SPO2;
    }

    /* access modifiers changed from: protected */
    public String getUnit() {
        return Constants.SPO2_UNIT;
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

    public static SPO2HHistoryListFragment newInstance(int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putInt("year", i);
        bundle.putInt("month", i2);
        SPO2HHistoryListFragment sPO2HHistoryListFragment = new SPO2HHistoryListFragment();
        sPO2HHistoryListFragment.setArguments(bundle);
        return sPO2HHistoryListFragment;
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.blood_oxygen);
    }

    /* access modifiers changed from: protected */
    public void getAndSavePdfImg(View view, ChartView chartView, int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int size = this.checkedData.size();
        int[] iArr = new int[size];
        Double[] dArr = new Double[size];
        for (int i2 = 0; i2 < size; i2++) {
            Log.d("haha", "checkedData" + ((HistoryItemBean) this.checkedData.get(i2)).getMeasureValue());
            String[] split = ((HistoryItemBean) this.checkedData.get(i2)).getMeasureValue().split("%/");
            dArr[i2] = Double.valueOf(split[0]);
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
                    arrayList3.add(dArr[i6]);
                }
            }
            chartView.setSPO2Value(arrayList, arrayList2, arrayList3, -1, "SPO2H");
            layoutView(view);
            getAndSaveBitmap(view, i4);
        }
    }

    /* access modifiers changed from: protected */
    public void addChartDataToList() {
        String string;
        StringUtils.getString(C1624R.string.normal);
        for (int i = 0; i < this.monthDatas.size(); i++) {
            double spo2 = ((Spo2Entity) this.monthDatas.get(i)).getSpo2();
            if (spo2 >= 95.0d) {
                string = StringUtils.getString(C1624R.string.normal);
            } else if (spo2 == 0.0d) {
                string = StringUtils.getString(C1624R.string.abnormal);
            } else {
                string = StringUtils.getString(C1624R.string.low);
            }
            String str = string;
            List list = this.historyDatas;
            String formatDate = DateUtils.getFormatDate(((Spo2Entity) this.monthDatas.get(i)).getCreateTime(), StringUtils.getString(C1624R.string.time_format_item));
            long createTime = ((Spo2Entity) this.monthDatas.get(i)).getCreateTime();
            HistoryItemBean historyItemBean = r6;
            HistoryItemBean historyItemBean2 = new HistoryItemBean(formatDate, createTime, spo2 + "%/" + ((Spo2Entity) this.monthDatas.get(i)).getHeartRate(), str, DateUtils.getFormatDate(((Spo2Entity) this.monthDatas.get(i)).getCreateTime(), StringUtils.getString(C1624R.string.time_format_item_top)), Constants.SPO2H_LIST, ((Spo2Entity) this.monthDatas.get(i)).getYear(), ((Spo2Entity) this.monthDatas.get(i)).getMonth(), ((Spo2Entity) this.monthDatas.get(i)).getDay());
            list.add(historyItemBean);
        }
    }

    /* access modifiers changed from: protected */
    public List<Spo2Entity> getNativeData() {
        return Spo2hTableManager.queryByMonth(SpManager.getMemberId(), getArguments().getInt("year"), getArguments().getInt("month"));
    }
}
