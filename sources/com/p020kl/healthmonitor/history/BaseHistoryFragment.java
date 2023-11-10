package com.p020kl.healthmonitor.history;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.mintti.visionsdk.common.LogUtils;
import com.p020kl.commonbase.bean.BaseMeasureEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.QueryResult;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UploadResult;
import com.p020kl.commonbase.net.utils.NetworkUtils;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.LanguageUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.adapter.HistoryListItemAdapter;
import com.p020kl.healthmonitor.bean.HistoryItemBean;
import com.p020kl.healthmonitor.views.ChartView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p028io.reactivex.Observer;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Consumer;

/* renamed from: com.kl.healthmonitor.history.BaseHistoryFragment */
abstract class BaseHistoryFragment<T extends BaseMeasureEntity> extends BaseAllHistoryFragment<T> implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, CalendarView.OnViewChangeListener, CalendarView.OnMonthChangeListener, View.OnClickListener {
    public static final int MSG_ADD_CHART_DATA = 10;
    protected HistoryListItemAdapter adapter;
    protected List<T> btDatas = new ArrayList();
    @BindView(3108)
    CalendarLayout calendarLayout;
    @BindView(3109)
    CalendarView calendarView;
    @BindView(2131296491)
    CardView cardView;
    protected long createTime = 0;
    @BindView(3121)
    ChartView cvChart;
    protected List<HistoryItemBean> datas = new ArrayList();
    protected List<T> dayDatas = new ArrayList();
    protected int dayTime = DateUtils.getDate(DateUtils.DateType.DAY);
    protected Disposable disposable;
    protected ExecutorService executorService;
    protected Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                BaseHistoryFragment.this.calendarView.clearSchemeDate();
                BaseHistoryFragment.this.calendarView.addSchemeDate(BaseHistoryFragment.this.mSchemeMap);
                BaseHistoryFragment.this.calendarView.invalidate();
                BaseHistoryFragment.this.disProgressDialog();
            } else if (i == 10) {
                BaseHistoryFragment.this.addChartData();
            }
        }
    };
    protected String initType = "day";
    protected boolean isLoad;
    @BindView(3346)
    ImageView ivExpand;
    protected Map<String, Calendar> mSchemeMap = new ConcurrentHashMap();
    protected List<T> monthDatas = new ArrayList();
    protected List<T> monthDatas2 = new ArrayList();
    protected int monthTime = DateUtils.getDate(DateUtils.DateType.MONTH);
    protected int monthTime2 = DateUtils.getDate(DateUtils.DateType.MONTH);
    protected List<T> netMonthDatas = new ArrayList();
    @BindView(3353)
    ImageView nextMonth;
    protected String normalText = StringUtils.getString(C1624R.string.no_day_historical_records);
    protected int page = 1;
    protected int postion = -1;
    @BindView(3355)
    ImageView preMonth;
    @BindView(2131296898)
    RecyclerView rvDataList;
    protected int state = 0;
    protected String stateType = StringUtils.getString(C1624R.string.fbg);
    @BindView(3741)
    TextView tvData;
    @BindView(3754)
    TextView tvExpand;
    @BindView(3801)
    TextView tvRecordNumble;
    @BindView(3764)
    TextView tvTopDay;
    @BindView(3765)
    TextView tvTopMonth;
    @BindView(3768)
    TextView tvTopWeek;
    @BindView(3767)
    TextView tvUnitValue;
    protected List<T> weekDatas = new ArrayList();
    protected List<String> xValue = new ArrayList();
    protected List<Double> yDBValue = new ArrayList();
    protected List<Float> yFValue = new ArrayList();
    protected List<Integer> yHighValue = new ArrayList();
    protected List<Integer> yLowValue = new ArrayList();
    protected List<String> yState = new ArrayList();
    protected int yearTime = DateUtils.getDate(DateUtils.DateType.YEAR);
    protected int yearTime2 = DateUtils.getDate(DateUtils.DateType.YEAR);

    /* access modifiers changed from: protected */
    public abstract void addChartData();

    /* access modifiers changed from: protected */
    public abstract void insertEntity(List<T> list, int i);

    /* access modifiers changed from: protected */
    public abstract void loadNativeDayData(Calendar calendar);

    /* access modifiers changed from: protected */
    public abstract void loadNativeMonthData(Calendar calendar);

    /* access modifiers changed from: protected */
    public abstract void loadNativeMonthData2();

    /* access modifiers changed from: protected */
    public abstract void loadNativeWeekData(Calendar calendar);

    /* access modifiers changed from: protected */
    public abstract void loadNetMonthData();

    public void onCalendarOutOfRange(Calendar calendar) {
    }

    public void onViewChange(boolean z) {
    }

    /* access modifiers changed from: protected */
    public abstract void registerMonthChangeListener();

    /* access modifiers changed from: protected */
    public abstract void setCalendarTime(T t, Calendar calendar);

    /* access modifiers changed from: protected */
    public abstract void setUnit();

    /* access modifiers changed from: protected */
    public abstract void upDateEntity(String str, T t);

    BaseHistoryFragment() {
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.view_history_page);
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        super.initView(view);
        this.executorService = Executors.newSingleThreadExecutor();
        initTime();
        this.calendarView.setOnCalendarSelectListener(this);
        this.calendarView.setOnMonthChangeListener(this);
        this.calendarView.setOnYearChangeListener(this);
        registerMonthChangeListener();
        CalendarView calendarView2 = this.calendarView;
        calendarView2.setRange(1970, 1, 1, calendarView2.getCurYear(), this.calendarView.getCurMonth(), this.calendarView.getCurDay());
        this.calendarView.scrollToCurrent();
        this.calendarView.invalidate();
        this.preMonth.setOnClickListener(this);
        this.nextMonth.setOnClickListener(this);
        setDataText(this.yearTime, this.monthTime);
        this.nextMonth.setVisibility(4);
        setUnit();
    }

    /* access modifiers changed from: protected */
    public void initTime() {
        this.dayTime = DateUtils.getDate(DateUtils.DateType.DAY);
        this.monthTime = DateUtils.getDate(DateUtils.DateType.MONTH);
        this.yearTime = DateUtils.getDate(DateUtils.DateType.YEAR);
        this.yearTime2 = DateUtils.getDate(DateUtils.DateType.YEAR);
        this.monthTime2 = DateUtils.getDate(DateUtils.DateType.MONTH);
    }

    /* access modifiers changed from: protected */
    public void updateCurrentDate() {
        int curYear = this.calendarView.getCurYear();
        int curMonth = this.calendarView.getCurMonth();
        int curDay = this.calendarView.getCurDay();
        long currentTimeMillis = System.currentTimeMillis();
        if (DateUtils.getYear(currentTimeMillis) != curYear || DateUtils.getMonth(currentTimeMillis) != curMonth || DateUtils.getDay(currentTimeMillis) != curDay) {
            this.calendarView.updateCurrentDate();
            initTime();
            CalendarView calendarView2 = this.calendarView;
            calendarView2.setRange(1970, 1, 1, calendarView2.getCurYear(), this.calendarView.getCurMonth(), this.calendarView.getCurDay());
            this.calendarView.invalidate();
            scrollToCurrent();
            setDataText(this.yearTime, this.monthTime);
            this.nextMonth.setVisibility(4);
        }
    }

    /* access modifiers changed from: protected */
    public void showDataList() {
        this.rvDataList.setVisibility(0);
        this.cardView.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void setDataCount() {
        String str;
        if (LanguageUtil.isZh(getContext())) {
            str = StringUtils.getString(C1624R.string.you) + this.monthTime + StringUtils.getString(C1624R.string.month) + this.dayTime + StringUtils.getString(C1624R.string.day) + StringUtils.getString(C1624R.string.a_total_of) + "<font color='#44bcb1'>" + this.dayDatas.size() + "</font>" + StringUtils.getString(C1624R.string.the_data);
        } else {
            str = "A total of<font color='#44bcb1'> " + this.dayDatas.size() + " " + "</font>" + "data on " + DateFormat.format(StringUtils.getString(C1624R.string.date_for_data), this.createTime);
        }
        this.tvRecordNumble.setText(Html.fromHtml(str));
    }

    /* access modifiers changed from: protected */
    public void setDataText(int i, int i2) {
        Calendar calendar = new Calendar();
        calendar.setYear(i);
        calendar.setMonth(i2 + 1);
        this.tvData.setText(DateFormat.format(StringUtils.getString(C1624R.string.format_date), calendar.getTimeInMillis()));
    }

    /* access modifiers changed from: protected */
    public void loadData(final Calendar calendar) {
        this.executorService.execute(new Runnable() {
            public void run() {
                BaseHistoryFragment.this.loadNativeData(calendar);
            }
        });
    }

    private void initData(String str) {
        this.yLowValue.clear();
        this.yHighValue.clear();
        this.btDatas.clear();
        this.xValue.clear();
        this.yDBValue.clear();
        this.yFValue.clear();
        this.yState.clear();
        this.datas.clear();
        this.page = 1;
        if (str.equals("day")) {
            this.btDatas.addAll(this.dayDatas);
            Log.d("haha", "bt_size:" + this.btDatas.size());
        } else if (str.equals(Constants.WEEK)) {
            this.btDatas.addAll(this.weekDatas);
        } else if (str.equals("month")) {
            this.btDatas.addAll(this.monthDatas);
        }
        this.handler.sendEmptyMessage(10);
    }

    @OnClick({3764, 3768, 3765, 3355, 3353, 3754})
    public void onClick(View view) {
        switch (view.getId()) {
            case C1624R.C1628id.iv_next_month /*2131296675*/:
                this.calendarView.scrollToNext();
                return;
            case C1624R.C1628id.iv_pre_month /*2131296677*/:
                this.calendarView.scrollToPre();
                return;
            case C1624R.C1628id.tv_expand /*2131297076*/:
                if (this.tvExpand.getText().toString().equals(StringUtils.getString(C1624R.string.expand_calendar))) {
                    this.tvExpand.setText(C1624R.string.collapse_calendar);
                    this.ivExpand.setImageResource(C1624R.C1627drawable.arrow_up);
                    setExpand(true);
                    return;
                }
                this.tvExpand.setText(C1624R.string.expand_calendar);
                this.ivExpand.setImageResource(C1624R.C1627drawable.arrow_down);
                setExpand(false);
                return;
            case C1624R.C1628id.tv_history_top_day /*2131297086*/:
                this.tvTopDay.setTextColor(getContext().getResources().getColor(C1624R.C1626color.darkGreen));
                this.tvTopWeek.setTextColor(getContext().getResources().getColor(C1624R.C1626color.gray_999));
                this.tvTopMonth.setTextColor(getContext().getResources().getColor(C1624R.C1626color.gray_999));
                this.initType = "day";
                initData("day");
                return;
            case C1624R.C1628id.tv_history_top_month /*2131297087*/:
                this.tvTopDay.setTextColor(getContext().getResources().getColor(C1624R.C1626color.gray_999));
                this.tvTopWeek.setTextColor(getContext().getResources().getColor(C1624R.C1626color.gray_999));
                this.tvTopMonth.setTextColor(getContext().getResources().getColor(C1624R.C1626color.darkGreen));
                this.initType = "month";
                initData("month");
                return;
            case C1624R.C1628id.tv_history_top_week /*2131297090*/:
                this.tvTopDay.setTextColor(getContext().getResources().getColor(C1624R.C1626color.gray_999));
                this.tvTopWeek.setTextColor(getContext().getResources().getColor(C1624R.C1626color.darkGreen));
                this.tvTopMonth.setTextColor(getContext().getResources().getColor(C1624R.C1626color.gray_999));
                this.initType = Constants.WEEK;
                initData(Constants.WEEK);
                return;
            default:
                return;
        }
    }

    public void setExpand(boolean z) {
        if (z) {
            this.calendarLayout.expand();
        } else {
            this.calendarLayout.shrink();
        }
    }

    /* access modifiers changed from: private */
    public void loadNativeData(Calendar calendar) {
        loadNativeDayData(calendar);
        loadNativeWeekData(calendar);
        loadNativeMonthData(calendar);
        initData(this.initType);
    }

    /* access modifiers changed from: private */
    public void margeMonthData() {
        this.netMonthDatas.clear();
        loadNativeMonthData2();
        if (NetworkUtils.isNetworkConnected()) {
            loadNetMonthData();
        }
    }

    /* access modifiers changed from: protected */
    public Observer<ResponseResult<QueryResult>> getObserver(final List<T> list) {
        return new Observer<ResponseResult<QueryResult>>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
                BaseHistoryFragment.this.disposableList.add(disposable);
            }

            public void onNext(ResponseResult<QueryResult> responseResult) {
                if (responseResult.getStatus() == 200) {
                    BaseHistoryFragment.this.isLoad = true;
                    boolean isLastPage = responseResult.getData().isLastPage();
                    if (responseResult.getData().getPages() == 0) {
                        Log.d("netMonthDatas", "没有数据");
                    } else if (isLastPage) {
                        list.addAll(responseResult.getData().getRows());
                        BaseHistoryFragment.this.page = 1;
                        Log.d("netMonthDatas", BaseHistoryFragment.this.netMonthDatas.size() + "--" + BaseHistoryFragment.this.monthDatas2.size());
                        BaseHistoryFragment baseHistoryFragment = BaseHistoryFragment.this;
                        baseHistoryFragment.mergeListData(baseHistoryFragment.netMonthDatas, BaseHistoryFragment.this.monthDatas2);
                    } else {
                        BaseHistoryFragment.this.page++;
                        list.addAll(responseResult.getData().getRows());
                        BaseHistoryFragment.this.loadNetMonthData();
                    }
                } else {
                    Log.d("netMonthDatas", "加载网络数据失败");
                }
            }

            public void onError(Throwable th) {
                BaseHistoryFragment.this.isLoad = false;
                Log.d("netMonthDatas", "数据异常");
            }
        };
    }

    /* access modifiers changed from: private */
    public void mergeListData(List<T> list, List<T> list2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList2.add(Long.valueOf(((BaseMeasureEntity) list.get(i)).getCreateTime()));
        }
        for (int i2 = 0; i2 < list2.size(); i2++) {
            arrayList.add(Long.valueOf(((BaseMeasureEntity) list2.get(i2)).getCreateTime()));
        }
        Log.d("haha", arrayList.size() + "hahaha" + arrayList2.size());
        int i3 = 0;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            if (!arrayList2.contains(arrayList.get(i4))) {
                StringBuilder sb = new StringBuilder();
                i3++;
                sb.append(i3);
                sb.append("");
                Log.d("合并数据呀", sb.toString());
                final BaseMeasureEntity baseMeasureEntity = (BaseMeasureEntity) list2.get(i4);
                RestClient.uploadMeasureData(baseMeasureEntity).subscribe(new Consumer<ResponseResult<UploadResult>>() {
                    public void accept(ResponseResult<UploadResult> responseResult) throws Exception {
                        if (responseResult.getStatus() == 200) {
                            ToastUtil.showToast(BaseHistoryFragment.this.getContext(), StringUtils.getString(C1624R.string.upload_successful));
                            BaseHistoryFragment.this.upDateEntity(responseResult.getData().getDocId(), baseMeasureEntity);
                        }
                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable th) throws Exception {
                    }
                });
            }
        }
        for (int i5 = 0; i5 < arrayList2.size(); i5++) {
            if (!arrayList.contains(arrayList2.get(i5))) {
                arrayList.add(arrayList2.get(i5));
                list2.add(list.get(i5));
                insertEntity(list, i5);
            }
        }
        collectionListReverse(list2);
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.datas.clear();
        this.xValue.clear();
        this.yDBValue.clear();
        this.yFValue.clear();
        this.yState.clear();
        this.btDatas.clear();
        this.yLowValue.clear();
        this.yHighValue.clear();
        Disposable disposable2 = this.disposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.disposable.dispose();
        }
        Handler handler2 = this.handler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages((Object) null);
        }
        ExecutorService executorService2 = this.executorService;
        if (executorService2 != null) {
            executorService2.shutdownNow();
        }
        disProgressDialog();
    }

    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unRegister(this);
    }

    public void onCalendarSelect(Calendar calendar, boolean z) {
        LogUtils.m378d("BaseHistoryFragment", "onCalendarSelect = " + calendar.toString());
        if (calendar.getYear() != 1970) {
            if (isSupportVisible()) {
                showProgressDialog(StringUtils.getString(C1624R.string.loading_data), false);
            }
            this.dayTime = calendar.getDay();
            this.monthTime = calendar.getMonth();
            this.yearTime = calendar.getYear();
            this.createTime = calendar.getTimeInMillis();
            this.handler.removeMessages(10);
            loadData(calendar);
        }
    }

    public void onMonthChange(int i, int i2) {
        if (i != 1970) {
            this.mSchemeMap.clear();
            this.yearTime2 = i;
            this.monthTime2 = i2;
            setDataText(i, i2);
            Log.d("月份改变", "year change");
            showProgressDialog(StringUtils.getString(C1624R.string.loading_data), false);
            this.executorService.execute(new Runnable() {
                public void run() {
                    Log.d("ddd", "线程1开始执行");
                    BaseHistoryFragment.this.margeMonthData();
                    BaseHistoryFragment.this.mSchemeMap.clear();
                    for (T calendarTime : BaseHistoryFragment.this.monthDatas2) {
                        Calendar calendar = new Calendar();
                        BaseHistoryFragment.this.setCalendarTime(calendarTime, calendar);
                        BaseHistoryFragment.this.mSchemeMap.put(calendar.toString(), calendar);
                    }
                    BaseHistoryFragment.this.handler.sendMessage(BaseHistoryFragment.this.handler.obtainMessage(1));
                }
            });
            if (i == this.calendarView.getCurYear() && i2 == this.calendarView.getCurMonth()) {
                this.nextMonth.setVisibility(4);
            } else {
                this.nextMonth.setVisibility(0);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setCalendarTime(int i, int i2, int i3, Calendar calendar) {
        calendar.setYear(i);
        calendar.setMonth(i2);
        calendar.setDay(i3);
    }

    public void onYearChange(int i) {
        if (i != 1970) {
            this.yearTime = i;
            setDataText(i, this.monthTime);
        }
    }

    /* access modifiers changed from: protected */
    public void scrollToCurrent() {
        this.calendarView.clearSingleSelect();
        this.calendarView.scrollToCurrent(true);
    }

    public void onSupportVisible() {
        super.onSupportVisible();
        updateCurrentDate();
    }

    public void onSupportInvisible() {
        super.onSupportInvisible();
    }
}
