package com.p020kl.healthmonitor.history;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import butterknife.OnClick;
import com.haibin.calendarview.Calendar;
import com.itextpdf.text.html.HtmlTags;
import com.p020kl.commonbase.bean.BTEntity;
import com.p020kl.commonbase.bean.CustomDate;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.constants.DataType;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.BTTableManager;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.event.TemperatureUnitChanged;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.TemperatureUtils;
import com.p020kl.healthmonitor.navigation.HistoryFragment;
import com.p020kl.healthmonitor.navigation.NavigationFragment;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* renamed from: com.kl.healthmonitor.history.BTMHistoryFragment */
public class BTMHistoryFragment extends BaseHistoryFragment<BTEntity> {
    private HistoryFragment.OnBTMonthChangeListener listener = new HistoryFragment.OnBTMonthChangeListener() {
        public void onBTMonthChange() {
            ((NavigationFragment) BTMHistoryFragment.this.getParentFragment().getParentFragment()).start(BTHistoryListFragment.newInstance(BTMHistoryFragment.this.yearTime2, BTMHistoryFragment.this.monthTime2));
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

    public static BTMHistoryFragment newInstance() {
        Bundle bundle = new Bundle();
        BTMHistoryFragment bTMHistoryFragment = new BTMHistoryFragment();
        bTMHistoryFragment.setArguments(bundle);
        return bTMHistoryFragment;
    }

    /* access modifiers changed from: protected */
    public void setUnit() {
        this.tvUnitValue.setText(TemperatureUtils.tempeConversionIntUnit(SpManager.getTemperaTrueUnit()));
    }

    /* access modifiers changed from: protected */
    public void addChartData() {
        setDataCount();
        Log.d("hehe", "体温记录界面：" + this.btDatas.size());
        this.xValue.clear();
        this.yFValue.clear();
        for (int i = 0; i < this.btDatas.size(); i++) {
            double temperature = ((BTEntity) this.btDatas.get(i)).getTemperature();
            List list = this.xValue;
            list.add(DateUtils.getFormatDate(((BTEntity) this.btDatas.get(i)).getCreateTime(), Constants.TIME_FORMAT_XITEM) + "");
            this.yFValue.add(Float.valueOf((float) temperature));
        }
        Log.d("hehe", this.xValue.size() + "");
        this.cvChart.setValue(this.xValue, this.yFValue, this.postion);
        disProgressDialog();
    }

    /* access modifiers changed from: protected */
    public void insertEntity(List<BTEntity> list, int i) {
        BTTableManager.insertEntity(list.get(i));
    }

    /* access modifiers changed from: protected */
    public void upDateEntity(String str, BTEntity bTEntity) {
        bTEntity.setDocId(str);
        BTTableManager.updateEntity(bTEntity);
    }

    /* access modifiers changed from: protected */
    public void loadNativeDayData(Calendar calendar) {
        this.dayDatas.clear();
        this.dayDatas = BTTableManager.queryByDay(SpManager.getMemberId(), calendar.getYear(), calendar.getMonth(), calendar.getDay());
        collectionListReverse(this.dayDatas);
    }

    /* access modifiers changed from: protected */
    public void loadNativeWeekData(Calendar calendar) {
        this.weekDatas.clear();
        this.weekDatas = BTTableManager.queryByHistoryWeek(SpManager.getMemberId(), DateUtils.convertStringToDate(calendar.getTimeInMillis()));
        collectionListReverse(this.weekDatas);
    }

    /* access modifiers changed from: protected */
    public void loadNativeMonthData(Calendar calendar) {
        Log.d("当月本地数据", calendar.getYear() + "-" + calendar.getMonth());
        this.monthDatas.clear();
        this.monthDatas = BTTableManager.queryByMonth(SpManager.getMemberId(), calendar.getYear(), calendar.getMonth());
        collectionListReverse(this.monthDatas);
    }

    /* access modifiers changed from: protected */
    public void loadNativeMonthData2() {
        this.monthDatas2.clear();
        this.monthDatas2 = BTTableManager.queryByMonth(SpManager.getMemberId(), this.yearTime2, this.monthTime2);
        collectionListReverse(this.monthDatas2);
    }

    /* access modifiers changed from: protected */
    public void loadNetMonthData() {
        Log.d("netMonthDatas", "加载网络数据");
        RestClient.queryByDate(new CustomDate(this.yearTime2, this.monthTime2), this.page, 10, DataType.TEMPERATURE).subscribe(getObserver(this.netMonthDatas));
    }

    public void onEventBus(Event event) {
        Log.d("haha", "BT_event");
        if (event != null && event.getData() != null) {
            super.onEventBus(event);
            if (event.getData() instanceof BTEntity) {
                setCalendarTime(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH), DateUtils.getDate(DateUtils.DateType.DAY), new Calendar());
                scrollToCurrent();
            } else if (event.getData().equals(Constants.SWITCH_MEMBER)) {
                onMonthChange(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH));
                setCalendarTime(DateUtils.getDate(DateUtils.DateType.YEAR), DateUtils.getDate(DateUtils.DateType.MONTH), DateUtils.getDate(DateUtils.DateType.DAY), new Calendar());
                scrollToCurrent();
            } else if (event.getData().equals(Constants.BT_TYPE)) {
                scrollToCurrent();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTempeUnitChanged(TemperatureUnitChanged temperatureUnitChanged) {
        setUnit();
        this.adapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: protected */
    public void setCalendarTime(BTEntity bTEntity, Calendar calendar) {
        setCalendarTime(bTEntity.getYear(), bTEntity.getMonth(), bTEntity.getDay(), calendar);
    }

    /* access modifiers changed from: protected */
    public void registerMonthChangeListener() {
        HistoryFragment.setOnBTMonthChangeListener(this.listener);
    }

    public static Bitmap getViewBp(View view) {
        if (view == null) {
            return null;
        }
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(view.getHeight(), 1073741824));
            view.layout((int) view.getX(), (int) view.getY(), ((int) view.getX()) + view.getMeasuredWidth(), ((int) view.getY()) + view.getMeasuredHeight());
        } else {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        }
        Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return createBitmap;
    }

    public void saveBitmap(Bitmap bitmap) {
        Log.d("hehe", "保存图片");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(getContext().getExternalFilesDir(HtmlTags.IMG).getAbsolutePath() + "/img2.png"));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception unused) {
        }
    }
}
