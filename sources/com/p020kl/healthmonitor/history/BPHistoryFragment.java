package com.p020kl.healthmonitor.history;

import android.os.Bundle;
import android.view.View;
import butterknife.OnClick;
import com.haibin.calendarview.Calendar;
import com.p020kl.commonbase.bean.BPEntity;
import com.p020kl.commonbase.bean.CustomDate;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.constants.DataType;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.BPTableManager;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.healthmonitor.navigation.HistoryFragment;
import com.p020kl.healthmonitor.navigation.NavigationFragment;
import java.util.List;

/* renamed from: com.kl.healthmonitor.history.BPHistoryFragment */
public class BPHistoryFragment extends BaseHistoryFragment<BPEntity> {
    private HistoryFragment.OnBPMonthChangeListener listener = new HistoryFragment.OnBPMonthChangeListener() {
        public void onBPMonthChange() {
            ((NavigationFragment) BPHistoryFragment.this.getParentFragment().getParentFragment()).start(BPHistoryListFragment.newInstance(BPHistoryFragment.this.yearTime2, BPHistoryFragment.this.monthTime2));
        }
    };

    public /* bridge */ /* synthetic */ void onBindView(Bundle bundle, View view) {
        super.onBindView(bundle, view);
    }

    public /* bridge */ /* synthetic */ void onCalendarOutOfRange(Calendar calendar) {
        super.onCalendarOutOfRange(calendar);
    }

    public /* bridge */ /* synthetic */ void onCalendarSelect(Calendar calendar, boolean z) {
        super.onCalendarSelect(calendar, z);
    }

    @OnClick({3764, 3768, 3765, 3355, 3353, 3754})
    public /* bridge */ /* synthetic */ void onClick(View view) {
        super.onClick(view);
    }

    public /* bridge */ /* synthetic */ void onDestroy() {
        super.onDestroy();
    }

    public /* bridge */ /* synthetic */ void onDestroyView() {
        super.onDestroyView();
    }

    public /* bridge */ /* synthetic */ void onMonthChange(int i, int i2) {
        super.onMonthChange(i, i2);
    }

    public /* bridge */ /* synthetic */ void onSupportInvisible() {
        super.onSupportInvisible();
    }

    public /* bridge */ /* synthetic */ void onSupportVisible() {
        super.onSupportVisible();
    }

    public /* bridge */ /* synthetic */ void onViewChange(boolean z) {
        super.onViewChange(z);
    }

    public /* bridge */ /* synthetic */ void onYearChange(int i) {
        super.onYearChange(i);
    }

    public /* bridge */ /* synthetic */ void setExpand(boolean z) {
        super.setExpand(z);
    }

    public /* bridge */ /* synthetic */ Object setLayout() {
        return super.setLayout();
    }

    public static BPHistoryFragment newInstance() {
        Bundle bundle = new Bundle();
        BPHistoryFragment bPHistoryFragment = new BPHistoryFragment();
        bPHistoryFragment.setArguments(bundle);
        return bPHistoryFragment;
    }

    /* access modifiers changed from: protected */
    public void addChartData() {
        setDataCount();
        this.xValue.clear();
        this.yLowValue.clear();
        this.yHighValue.clear();
        for (int i = 0; i < this.btDatas.size(); i++) {
            this.xValue.add(DateUtils.getFormatDate(((BPEntity) this.btDatas.get(i)).getCreateTime(), Constants.TIME_FORMAT_XITEM));
            this.yLowValue.add(Integer.valueOf(((BPEntity) this.btDatas.get(i)).getSystolicPressure()));
            this.yHighValue.add(Integer.valueOf(((BPEntity) this.btDatas.get(i)).getDiastolicPressure()));
        }
        this.cvChart.setBPMValue(this.xValue, this.yLowValue, this.yHighValue, this.postion, Constants.BPM_UNIT);
        disProgressDialog();
    }

    /* access modifiers changed from: protected */
    public void setUnit() {
        this.tvUnitValue.setText(Constants.BP_UNIT);
    }

    /* access modifiers changed from: protected */
    public void loadNativeDayData(Calendar calendar) {
        this.dayDatas.clear();
        this.dayDatas = BPTableManager.queryByDay(SpManager.getMemberId(), calendar.getYear(), calendar.getMonth(), calendar.getDay());
        collectionListReverse(this.dayDatas);
    }

    /* access modifiers changed from: protected */
    public void loadNativeWeekData(Calendar calendar) {
        this.weekDatas.clear();
        this.weekDatas = BPTableManager.queryByHistoryWeek(SpManager.getMemberId(), DateUtils.convertStringToDate(calendar.getTimeInMillis()));
        collectionListReverse(this.weekDatas);
    }

    /* access modifiers changed from: protected */
    public void loadNativeMonthData(Calendar calendar) {
        this.monthDatas.clear();
        this.monthDatas = BPTableManager.queryByMonth(SpManager.getMemberId(), calendar.getYear(), calendar.getMonth());
        collectionListReverse(this.monthDatas);
    }

    /* access modifiers changed from: protected */
    public void loadNativeMonthData2() {
        this.monthDatas2.clear();
        this.monthDatas2 = BPTableManager.queryByMonth(SpManager.getMemberId(), this.yearTime2, this.monthTime2);
        collectionListReverse(this.monthDatas2);
    }

    /* access modifiers changed from: protected */
    public void loadNetMonthData() {
        RestClient.queryByDate(new CustomDate(this.yearTime2, this.monthTime2), this.page, 10, DataType.BP).subscribe(getObserver(this.netMonthDatas));
    }

    /* access modifiers changed from: protected */
    public void insertEntity(List<BPEntity> list, int i) {
        BPTableManager.insertEntity(list.get(i));
    }

    /* access modifiers changed from: protected */
    public void upDateEntity(String str, BPEntity bPEntity) {
        bPEntity.setDocId(str);
        BPTableManager.updateEntity(bPEntity);
    }

    /* access modifiers changed from: protected */
    public void registerMonthChangeListener() {
        HistoryFragment.setOnBPMonthChangeListener(this.listener);
    }

    /* access modifiers changed from: protected */
    public void setCalendarTime(BPEntity bPEntity, Calendar calendar) {
        setCalendarTime(bPEntity.getYear(), bPEntity.getMonth(), bPEntity.getDay(), calendar);
    }

    public void onEventBus(Event event) {
        if (event != null && event.getData() != null) {
            super.onEventBus(event);
            if (event.getData() instanceof BPEntity) {
                setCalendarTime(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH), DateUtils.getDate(DateUtils.DateType.DAY), new Calendar());
                scrollToCurrent();
            } else if (event.getData().equals(Constants.SWITCH_MEMBER)) {
                onMonthChange(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH));
                setCalendarTime(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH), DateUtils.getDate(DateUtils.DateType.DAY), new Calendar());
                scrollToCurrent();
            } else if (event.getData().equals(Constants.BP_TYPE)) {
                scrollToCurrent();
            }
        }
    }
}
