package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;
import com.haibin.calendarview.CalendarLayout;

/* renamed from: com.kl.healthmonitor.views.MyCalendarLayout */
public class MyCalendarLayout extends CalendarLayout {
    private RecyclerView recyclerView;

    public MyCalendarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setRecyclerView(RecyclerView recyclerView2) {
        this.recyclerView = recyclerView2;
    }

    /* access modifiers changed from: protected */
    public boolean isScrollTop() {
        return this.recyclerView.computeVerticalScrollOffset() == 0;
    }
}
