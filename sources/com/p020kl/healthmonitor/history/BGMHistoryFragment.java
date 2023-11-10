package com.p020kl.healthmonitor.history;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import butterknife.OnClick;
import com.haibin.calendarview.Calendar;
import com.p020kl.commonbase.bean.BGEntity;
import com.p020kl.commonbase.bean.CustomDate;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.constants.DataType;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.BGTableManager;
import com.p020kl.commonbase.event.BgUnitChanged;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.navigation.HistoryFragment;
import com.p020kl.healthmonitor.navigation.NavigationFragment;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* renamed from: com.kl.healthmonitor.history.BGMHistoryFragment */
public class BGMHistoryFragment extends BaseHistoryFragment<BGEntity> {
    private HistoryFragment.OnBGMonthChangeListener listener = new HistoryFragment.OnBGMonthChangeListener() {
        public void onBGMonthChange() {
            ((NavigationFragment) BGMHistoryFragment.this.getParentFragment().getParentFragment()).start(BGHistoryListFragment.newInstance(BGMHistoryFragment.this.yearTime2, BGMHistoryFragment.this.monthTime2));
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

    public static BGMHistoryFragment newInstance() {
        Bundle bundle = new Bundle();
        BGMHistoryFragment bGMHistoryFragment = new BGMHistoryFragment();
        bGMHistoryFragment.setArguments(bundle);
        return bGMHistoryFragment;
    }

    /* access modifiers changed from: protected */
    public void setUnit() {
        if (SpManager.getBgUnit() == 0) {
            this.tvUnitValue.setText(Constants.BG_UNIT);
        } else {
            this.tvUnitValue.setText("mg/dl");
        }
    }

    /* access modifiers changed from: protected */
    public void addChartData() {
        setDataCount();
        this.xValue.clear();
        this.yDBValue.clear();
        this.yState.clear();
        for (int i = 0; i < this.btDatas.size(); i++) {
            this.state = ((BGEntity) this.btDatas.get(i)).getMeasureTime();
            if (this.state == 1) {
                this.stateType = StringUtils.getString(C1624R.string.pbs);
            } else {
                this.stateType = StringUtils.getString(C1624R.string.fbg);
            }
            List list = this.xValue;
            list.add(DateUtils.getFormatDate(((BGEntity) this.btDatas.get(i)).getCreateTime(), Constants.TIME_FORMAT_XITEM) + "");
            this.yDBValue.add(Double.valueOf(((BGEntity) this.btDatas.get(i)).getBloodGlucose()));
            this.yState.add(this.stateType);
        }
        this.cvChart.setBGValue(this.xValue, this.yDBValue, this.yState, this.postion, Constants.f837BG, false);
        disProgressDialog();
    }

    public void loadNativeDayData(Calendar calendar) {
        this.dayDatas.clear();
        this.dayDatas = BGTableManager.queryByDay(SpManager.getMemberId(), calendar.getYear(), calendar.getMonth(), calendar.getDay());
        collectionListReverse(this.dayDatas);
    }

    public void loadNativeWeekData(Calendar calendar) {
        this.weekDatas.clear();
        this.weekDatas = BGTableManager.queryByHistoryWeek(SpManager.getMemberId(), DateUtils.convertStringToDate(calendar.getTimeInMillis()));
        collectionListReverse(this.weekDatas);
    }

    /* access modifiers changed from: protected */
    public void loadNativeMonthData(Calendar calendar) {
        this.monthDatas.clear();
        this.monthDatas = BGTableManager.queryByMonth(SpManager.getMemberId(), calendar.getYear(), calendar.getMonth());
        collectionListReverse(this.monthDatas);
    }

    /* access modifiers changed from: protected */
    public void loadNativeMonthData2() {
        this.monthDatas2.clear();
        this.monthDatas2 = BGTableManager.queryByMonth(SpManager.getMemberId(), this.yearTime2, this.monthTime2);
        collectionListReverse(this.monthDatas2);
    }

    public void loadNetMonthData() {
        Log.d("bgloadnetdata", "loadNetMonthData()" + this.yearTime2 + "-" + this.monthTime2);
        RestClient.queryByDate(new CustomDate(this.yearTime2, this.monthTime2), this.page, 10, DataType.BG).subscribe(getObserver(this.netMonthDatas));
    }

    /* access modifiers changed from: protected */
    public void insertEntity(List<BGEntity> list, int i) {
        BGTableManager.insertEntity(list.get(i));
    }

    /* access modifiers changed from: protected */
    public void upDateEntity(String str, BGEntity bGEntity) {
        bGEntity.setDocId(str);
        BGTableManager.updateEntity(bGEntity);
    }

    /* access modifiers changed from: protected */
    public void registerMonthChangeListener() {
        HistoryFragment.setOnBGMonthChangeListener(this.listener);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBgUnitChanged(BgUnitChanged bgUnitChanged) {
        setUnit();
        addChartData();
    }

    public void onEventBus(Event event) {
        if (event != null && event.getData() != null) {
            super.onEventBus(event);
            if (event.getData() instanceof BGEntity) {
                setCalendarTime(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH), DateUtils.getDate(DateUtils.DateType.DAY), new Calendar());
                scrollToCurrent();
            } else if (event.getData().equals(Constants.SWITCH_MEMBER)) {
                onMonthChange(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH));
                setCalendarTime(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH), DateUtils.getDate(DateUtils.DateType.DAY), new Calendar());
                scrollToCurrent();
            } else if (event.getData().equals(Constants.BG_TYPE)) {
                scrollToCurrent();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setCalendarTime(BGEntity bGEntity, Calendar calendar) {
        setCalendarTime(bGEntity.getYear(), bGEntity.getMonth(), bGEntity.getDay(), calendar);
    }
}
