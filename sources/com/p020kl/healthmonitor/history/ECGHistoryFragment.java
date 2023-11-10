package com.p020kl.healthmonitor.history;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.OnClick;
import com.haibin.calendarview.Calendar;
import com.p020kl.commonbase.bean.CustomDate;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.constants.DataType;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.ECGTableManager;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.healthmonitor.adapter.HistoryEcgItemAdapter;
import com.p020kl.healthmonitor.navigation.HistoryFragment;
import com.p020kl.healthmonitor.navigation.NavigationFragment;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.history.ECGHistoryFragment */
public class ECGHistoryFragment extends BaseHistoryFragment<ECGEntity> implements HistoryEcgItemAdapter.OnItemClickListener {
    private List<ECGEntity> ecgDatas = new ArrayList();
    private HistoryEcgItemAdapter historyEcgItemAdapter;
    private HistoryFragment.OnECGMonthChangeListener listener = new HistoryFragment.OnECGMonthChangeListener() {
        public void onECGMonthChange() {
            ((NavigationFragment) ECGHistoryFragment.this.getParentFragment().getParentFragment()).start(ECGHistoryListFragment.newInstance(ECGHistoryFragment.this.yearTime2, ECGHistoryFragment.this.monthTime2));
        }
    };

    /* access modifiers changed from: protected */
    public void loadNativeWeekData(Calendar calendar) {
    }

    /* access modifiers changed from: protected */
    public void setUnit() {
    }

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

    public static ECGHistoryFragment newInstance() {
        Bundle bundle = new Bundle();
        ECGHistoryFragment eCGHistoryFragment = new ECGHistoryFragment();
        eCGHistoryFragment.setArguments(bundle);
        return eCGHistoryFragment;
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        super.initView(view);
        showDataList();
        HistoryEcgItemAdapter historyEcgItemAdapter2 = new HistoryEcgItemAdapter(getContext(), this.ecgDatas, Constants.BG_HOME);
        this.historyEcgItemAdapter = historyEcgItemAdapter2;
        historyEcgItemAdapter2.setOnItemClickListener(this);
        this.rvDataList.setAdapter(this.historyEcgItemAdapter);
        this.rvDataList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /* access modifiers changed from: protected */
    public void addChartData() {
        if (this.isViewBind) {
            setDataCount();
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.btDatas);
            collectionList(arrayList);
            this.historyEcgItemAdapter.replaceData(arrayList);
            this.historyEcgItemAdapter.notifyDataSetChanged();
            disProgressDialog();
        }
    }

    /* access modifiers changed from: protected */
    public void loadNetMonthData() {
        RestClient.queryByDate(new CustomDate(this.yearTime2, this.monthTime2), this.page, 10, DataType.ECG).subscribe(getObserver(this.netMonthDatas));
    }

    /* access modifiers changed from: protected */
    public void loadNativeMonthData2() {
        this.monthDatas2.clear();
        this.monthDatas2 = ECGTableManager.queryByMonth(SpManager.getMemberId(), this.yearTime2, this.monthTime2);
        collectionList(this.monthDatas2);
    }

    /* access modifiers changed from: protected */
    public void loadNativeDayData(Calendar calendar) {
        this.dayDatas.clear();
        this.dayDatas = ECGTableManager.queryByDay(SpManager.getMemberId(), calendar.getYear(), calendar.getMonth(), calendar.getDay());
        collectionList(this.dayDatas);
    }

    /* access modifiers changed from: protected */
    public void loadNativeMonthData(Calendar calendar) {
        this.monthDatas.clear();
        this.monthDatas = ECGTableManager.queryByMonth(SpManager.getMemberId(), calendar.getYear(), calendar.getMonth());
        collectionList(this.monthDatas);
    }

    /* access modifiers changed from: protected */
    public void insertEntity(List<ECGEntity> list, int i) {
        ECGTableManager.insertEcgEntity(list.get(i));
    }

    /* access modifiers changed from: protected */
    public void upDateEntity(String str, ECGEntity eCGEntity) {
        eCGEntity.setDocId(str);
        ECGTableManager.updateEcgEntity(eCGEntity);
    }

    /* access modifiers changed from: protected */
    public void registerMonthChangeListener() {
        HistoryFragment.setOnECGMonthChangeListener(this.listener);
    }

    /* access modifiers changed from: protected */
    public void setCalendarTime(ECGEntity eCGEntity, Calendar calendar) {
        setCalendarTime(eCGEntity.getYear(), eCGEntity.getMonth(), eCGEntity.getDay(), calendar);
    }

    public void onItemClick(int i) {
        Log.d("点击事件", i + ":");
        NavigationFragment navigationFragment = (NavigationFragment) ((HistoryFragment) getParentFragment()).getParentFragment();
        if (navigationFragment != null) {
            navigationFragment.start(ECGDetailsFragment.newInstance((ECGEntity) this.btDatas.get(i)));
        }
    }

    public void onEventBus(Event event) {
        if (event != null && event.getData() != null) {
            super.onEventBus(event);
            if (event.getData() instanceof ECGEntity) {
                setCalendarTime(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH), DateUtils.getDate(DateUtils.DateType.DAY), new Calendar());
                scrollToCurrent();
            } else if (event.getData().equals(Constants.SWITCH_MEMBER)) {
                onMonthChange(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH));
                setCalendarTime(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH), DateUtils.getDate(DateUtils.DateType.DAY), new Calendar());
                scrollToCurrent();
            } else if (event.getData().equals(Constants.ECG_TYPE)) {
                scrollToCurrent();
            }
        }
    }
}
