package com.p020kl.healthmonitor.history;

import android.os.Bundle;
import android.view.View;
import butterknife.OnClick;
import com.p020kl.commonbase.bean.BGEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.BGTableManager;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.bean.HistoryItemBean;
import com.p020kl.healthmonitor.views.ChartView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.history.BGHistoryListFragment */
public class BGHistoryListFragment extends BaseListHistoryFragment<BGEntity> {
    private int state = 0;
    private String stateType = StringUtils.getString(C1624R.string.fbg);

    /* access modifiers changed from: protected */
    public String getMeasureType() {
        return Constants.f837BG;
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

    public static BGHistoryListFragment newInstance(int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putInt("year", i);
        bundle.putInt("month", i2);
        BGHistoryListFragment bGHistoryListFragment = new BGHistoryListFragment();
        bGHistoryListFragment.setArguments(bundle);
        return bGHistoryListFragment;
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.blood_glucose);
    }

    /* access modifiers changed from: protected */
    public void getAndSavePdfImg(View view, ChartView chartView, int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int size = this.checkedData.size();
        double[] dArr = new double[size];
        String[] strArr = new String[size];
        for (int i2 = 0; i2 < size; i2++) {
            String[] split = ((HistoryItemBean) this.checkedData.get(i2)).getMeasureValue().split("/");
            dArr[i2] = Double.parseDouble(split[0]);
            strArr[i2] = split[1];
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
                    arrayList2.add(Double.valueOf(dArr[i6]));
                    arrayList3.add(strArr[i6]);
                }
            }
            chartView.setBGValue(arrayList, arrayList2, arrayList3, -1, Constants.f837BG, true);
            layoutView(view);
            getAndSaveBitmap(view, i4);
        }
    }

    /* access modifiers changed from: protected */
    public String getUnit() {
        return SpManager.getBgUnit() == 0 ? Constants.BG_UNIT : "mg/dl";
    }

    /* access modifiers changed from: protected */
    public void addChartDataToList() {
        String str;
        StringUtils.getString(C1624R.string.normal);
        for (int i = 0; i < this.monthDatas.size(); i++) {
            double bloodGlucose = ((BGEntity) this.monthDatas.get(i)).getBloodGlucose();
            int measureTime = ((BGEntity) this.monthDatas.get(i)).getMeasureTime();
            this.state = measureTime;
            if (measureTime == 1) {
                this.stateType = StringUtils.getString(C1624R.string.pbs);
                if (bloodGlucose >= 3.9d && bloodGlucose <= 7.8d) {
                    str = StringUtils.getString(C1624R.string.normal);
                } else if (0.0d < bloodGlucose && bloodGlucose < 3.9d) {
                    str = StringUtils.getString(C1624R.string.low);
                } else if (bloodGlucose == 0.0d) {
                    str = StringUtils.getString(C1624R.string.abnormal);
                } else {
                    str = StringUtils.getString(C1624R.string.high);
                }
            } else {
                this.stateType = StringUtils.getString(C1624R.string.fbg);
                if (bloodGlucose >= 3.9d && bloodGlucose <= 6.1d) {
                    str = StringUtils.getString(C1624R.string.normal);
                } else if (0.0d < bloodGlucose && bloodGlucose < 3.9d) {
                    str = StringUtils.getString(C1624R.string.low);
                } else if (bloodGlucose == 0.0d) {
                    str = StringUtils.getString(C1624R.string.abnormal);
                } else {
                    str = StringUtils.getString(C1624R.string.high);
                }
            }
            String str2 = str;
            List list = this.historyDatas;
            String formatDate = DateUtils.getFormatDate(((BGEntity) this.monthDatas.get(i)).getCreateTime(), StringUtils.getString(C1624R.string.time_format_item));
            long createTime = ((BGEntity) this.monthDatas.get(i)).getCreateTime();
            HistoryItemBean historyItemBean = r6;
            HistoryItemBean historyItemBean2 = new HistoryItemBean(formatDate, createTime, bloodGlucose + "/" + this.stateType, str2, DateUtils.getFormatDate(((BGEntity) this.monthDatas.get(i)).getCreateTime(), StringUtils.getString(C1624R.string.time_format_item_top)), Constants.BG_LIST, ((BGEntity) this.monthDatas.get(i)).getYear(), ((BGEntity) this.monthDatas.get(i)).getMonth(), ((BGEntity) this.monthDatas.get(i)).getDay());
            list.add(historyItemBean);
        }
    }

    /* access modifiers changed from: protected */
    public List<BGEntity> getNativeData() {
        return BGTableManager.queryByMonth(SpManager.getMemberId(), getArguments().getInt("year"), getArguments().getInt("month"));
    }
}
