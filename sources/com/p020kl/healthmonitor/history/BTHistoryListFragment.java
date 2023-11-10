package com.p020kl.healthmonitor.history;

import android.os.Bundle;
import android.view.View;
import butterknife.OnClick;
import com.p020kl.commonbase.bean.BTEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.BTTableManager;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.TemperatureUtils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.bean.HistoryItemBean;
import com.p020kl.healthmonitor.views.ChartView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.history.BTHistoryListFragment */
public class BTHistoryListFragment extends BaseListHistoryFragment<BTEntity> {
    /* access modifiers changed from: protected */
    public String getMeasureType() {
        return Constants.f839BT;
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

    public static BTHistoryListFragment newInstance(int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putInt("year", i);
        bundle.putInt("month", i2);
        BTHistoryListFragment bTHistoryListFragment = new BTHistoryListFragment();
        bTHistoryListFragment.setArguments(bundle);
        return bTHistoryListFragment;
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.body_temperature);
    }

    /* access modifiers changed from: protected */
    public void getAndSavePdfImg(View view, ChartView chartView, int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.clear();
            arrayList2.clear();
            int i3 = i2 * 20;
            for (int i4 = i3; i4 < i3 + 20; i4++) {
                if (i4 < this.checkedData.size()) {
                    arrayList.add(DateUtils.getFormatDate(((HistoryItemBean) this.checkedData.get(i4)).getCreateTime(), Constants.TIME_FORMAT_XITEM) + "");
                    arrayList2.add(Float.valueOf(Float.parseFloat(((HistoryItemBean) this.checkedData.get(i4)).getMeasureValue())));
                }
            }
            chartView.setValue(arrayList, arrayList2, -1);
            layoutView(view);
            getAndSaveBitmap(view, i2);
        }
    }

    /* access modifiers changed from: protected */
    public String getUnit() {
        return TemperatureUtils.tempeConversionIntUnit(SpManager.getTemperaTrueUnit());
    }

    /* access modifiers changed from: protected */
    public void addChartDataToList() {
        String string;
        StringUtils.getString(C1624R.string.normal);
        for (int i = 0; i < this.monthDatas.size(); i++) {
            double temperature = ((BTEntity) this.monthDatas.get(i)).getTemperature();
            if (temperature > 37.1d && temperature <= 38.0d) {
                string = StringUtils.getString(C1624R.string.low_fever);
            } else if (temperature > 38.0d && temperature <= 39.0d) {
                string = StringUtils.getString(C1624R.string.middle_fever);
            } else if (temperature > 39.0d) {
                string = StringUtils.getString(C1624R.string.high_fever);
            } else if (temperature >= 36.0d && temperature <= 37.1d) {
                string = StringUtils.getString(C1624R.string.normal);
            } else if (temperature > 35.0d && temperature < 36.0d) {
                string = StringUtils.getString(C1624R.string.flat);
            } else if (temperature == 0.0d) {
                string = StringUtils.getString(C1624R.string.abnormal);
            } else {
                string = StringUtils.getString(C1624R.string.too_low);
            }
            String str = string;
            List list = this.historyDatas;
            HistoryItemBean historyItemBean = r6;
            HistoryItemBean historyItemBean2 = new HistoryItemBean(DateUtils.getFormatDate(((BTEntity) this.monthDatas.get(i)).getCreateTime(), StringUtils.getString(C1624R.string.time_format_item)), ((BTEntity) this.monthDatas.get(i)).getCreateTime(), String.valueOf(temperature), str, DateUtils.getFormatDate(((BTEntity) this.monthDatas.get(i)).getCreateTime(), StringUtils.getString(C1624R.string.time_format_item_top)), Constants.BT_LIST, ((BTEntity) this.monthDatas.get(i)).getYear(), ((BTEntity) this.monthDatas.get(i)).getMonth(), ((BTEntity) this.monthDatas.get(i)).getDay());
            list.add(historyItemBean);
        }
    }

    /* access modifiers changed from: protected */
    public List<BTEntity> getNativeData() {
        return BTTableManager.queryByMonth(SpManager.getMemberId(), getArguments().getInt("year"), getArguments().getInt("month"));
    }
}
