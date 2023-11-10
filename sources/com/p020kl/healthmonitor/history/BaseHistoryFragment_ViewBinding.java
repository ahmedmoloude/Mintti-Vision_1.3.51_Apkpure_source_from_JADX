package com.p020kl.healthmonitor.history;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.ChartView;

/* renamed from: com.kl.healthmonitor.history.BaseHistoryFragment_ViewBinding */
public class BaseHistoryFragment_ViewBinding implements Unbinder {
    private BaseHistoryFragment target;
    private View viewd19;
    private View viewd1b;
    private View vieweaa;
    private View vieweb4;
    private View vieweb5;
    private View vieweb8;

    public BaseHistoryFragment_ViewBinding(final BaseHistoryFragment baseHistoryFragment, View view) {
        this.target = baseHistoryFragment;
        baseHistoryFragment.cvChart = (ChartView) Utils.findRequiredViewAsType(view, C1624R.C1628id.chartview, "field 'cvChart'", ChartView.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.tv_history_top_day, "field 'tvTopDay' and method 'onClick'");
        baseHistoryFragment.tvTopDay = (TextView) Utils.castView(findRequiredView, C1624R.C1628id.tv_history_top_day, "field 'tvTopDay'", TextView.class);
        this.vieweb4 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                baseHistoryFragment.onClick(view);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.tv_history_top_week, "field 'tvTopWeek' and method 'onClick'");
        baseHistoryFragment.tvTopWeek = (TextView) Utils.castView(findRequiredView2, C1624R.C1628id.tv_history_top_week, "field 'tvTopWeek'", TextView.class);
        this.vieweb8 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                baseHistoryFragment.onClick(view);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, C1624R.C1628id.tv_history_top_month, "field 'tvTopMonth' and method 'onClick'");
        baseHistoryFragment.tvTopMonth = (TextView) Utils.castView(findRequiredView3, C1624R.C1628id.tv_history_top_month, "field 'tvTopMonth'", TextView.class);
        this.vieweb5 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                baseHistoryFragment.onClick(view);
            }
        });
        baseHistoryFragment.tvUnitValue = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_history_top_unitvalue, "field 'tvUnitValue'", TextView.class);
        baseHistoryFragment.calendarView = (CalendarView) Utils.findRequiredViewAsType(view, C1624R.C1628id.calendarView, "field 'calendarView'", CalendarView.class);
        baseHistoryFragment.calendarLayout = (CalendarLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.calendarLayout, "field 'calendarLayout'", CalendarLayout.class);
        View findRequiredView4 = Utils.findRequiredView(view, C1624R.C1628id.iv_pre_month, "field 'preMonth' and method 'onClick'");
        baseHistoryFragment.preMonth = (ImageView) Utils.castView(findRequiredView4, C1624R.C1628id.iv_pre_month, "field 'preMonth'", ImageView.class);
        this.viewd1b = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                baseHistoryFragment.onClick(view);
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, C1624R.C1628id.iv_next_month, "field 'nextMonth' and method 'onClick'");
        baseHistoryFragment.nextMonth = (ImageView) Utils.castView(findRequiredView5, C1624R.C1628id.iv_next_month, "field 'nextMonth'", ImageView.class);
        this.viewd19 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                baseHistoryFragment.onClick(view);
            }
        });
        baseHistoryFragment.tvData = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_current_date, "field 'tvData'", TextView.class);
        baseHistoryFragment.tvRecordNumble = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_record_numble, "field 'tvRecordNumble'", TextView.class);
        baseHistoryFragment.ivExpand = (ImageView) Utils.findRequiredViewAsType(view, C1624R.C1628id.iv_expand, "field 'ivExpand'", ImageView.class);
        View findRequiredView6 = Utils.findRequiredView(view, C1624R.C1628id.tv_expand, "field 'tvExpand' and method 'onClick'");
        baseHistoryFragment.tvExpand = (TextView) Utils.castView(findRequiredView6, C1624R.C1628id.tv_expand, "field 'tvExpand'", TextView.class);
        this.vieweaa = findRequiredView6;
        findRequiredView6.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                baseHistoryFragment.onClick(view);
            }
        });
        baseHistoryFragment.rvDataList = (RecyclerView) Utils.findRequiredViewAsType(view, C1624R.C1628id.rv_data_list, "field 'rvDataList'", RecyclerView.class);
        baseHistoryFragment.cardView = (CardView) Utils.findRequiredViewAsType(view, C1624R.C1628id.cv_card_broke, "field 'cardView'", CardView.class);
    }

    public void unbind() {
        BaseHistoryFragment baseHistoryFragment = this.target;
        if (baseHistoryFragment != null) {
            this.target = null;
            baseHistoryFragment.cvChart = null;
            baseHistoryFragment.tvTopDay = null;
            baseHistoryFragment.tvTopWeek = null;
            baseHistoryFragment.tvTopMonth = null;
            baseHistoryFragment.tvUnitValue = null;
            baseHistoryFragment.calendarView = null;
            baseHistoryFragment.calendarLayout = null;
            baseHistoryFragment.preMonth = null;
            baseHistoryFragment.nextMonth = null;
            baseHistoryFragment.tvData = null;
            baseHistoryFragment.tvRecordNumble = null;
            baseHistoryFragment.ivExpand = null;
            baseHistoryFragment.tvExpand = null;
            baseHistoryFragment.rvDataList = null;
            baseHistoryFragment.cardView = null;
            this.vieweb4.setOnClickListener((View.OnClickListener) null);
            this.vieweb4 = null;
            this.vieweb8.setOnClickListener((View.OnClickListener) null);
            this.vieweb8 = null;
            this.vieweb5.setOnClickListener((View.OnClickListener) null);
            this.vieweb5 = null;
            this.viewd1b.setOnClickListener((View.OnClickListener) null);
            this.viewd1b = null;
            this.viewd19.setOnClickListener((View.OnClickListener) null);
            this.viewd19 = null;
            this.vieweaa.setOnClickListener((View.OnClickListener) null);
            this.vieweaa = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
