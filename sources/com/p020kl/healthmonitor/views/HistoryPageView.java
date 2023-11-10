package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.HistoryPageView */
public class HistoryPageView extends ConstraintLayout {
    private CalendarLayout calendarLayout;
    private CalendarView calendarView;
    private String chartViewType;
    /* access modifiers changed from: private */
    public ImageView ivExpand;
    private ImageView ivNextImg;
    private ImageView ivPreImg;
    private TextView tvData;
    /* access modifiers changed from: private */
    public TextView tvExpand;

    public HistoryPageView(Context context) {
        this(context, (AttributeSet) null);
    }

    public HistoryPageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HistoryPageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(C1624R.layout.view_history_page, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.HistoryPageView);
        this.chartViewType = obtainStyledAttributes.getString(0);
        obtainStyledAttributes.recycle();
        ((ChartView) inflate.findViewById(C1624R.C1628id.chartview)).setLingType(this.chartViewType);
        this.calendarLayout = (CalendarLayout) inflate.findViewById(C1624R.C1628id.calendarLayout);
        this.ivExpand = (ImageView) inflate.findViewById(C1624R.C1628id.iv_expand);
        TextView textView = (TextView) inflate.findViewById(C1624R.C1628id.tv_expand);
        this.tvExpand = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (HistoryPageView.this.tvExpand.getText().toString().equals(StringUtils.getString(C1624R.string.expand_calendar))) {
                    HistoryPageView.this.tvExpand.setText(C1624R.string.collapse_calendar);
                    HistoryPageView.this.ivExpand.setImageResource(C1624R.C1627drawable.arrow_up);
                    HistoryPageView.this.setExpand(true);
                    return;
                }
                HistoryPageView.this.tvExpand.setText(C1624R.string.expand_calendar);
                HistoryPageView.this.ivExpand.setImageResource(C1624R.C1627drawable.arrow_down);
                HistoryPageView.this.setExpand(false);
            }
        });
    }

    public void setExpand(boolean z) {
        if (z) {
            this.calendarLayout.expand();
        } else {
            this.calendarLayout.shrink();
        }
    }
}
