package com.chad.library.adapter.base.loadmore;

import com.chad.library.C1039R;

public final class SimpleLoadMoreView extends LoadMoreView {
    public int getLayoutId() {
        return C1039R.layout.brvah_quick_view_load_more;
    }

    /* access modifiers changed from: protected */
    public int getLoadingViewId() {
        return C1039R.C1041id.load_more_loading_view;
    }

    /* access modifiers changed from: protected */
    public int getLoadFailViewId() {
        return C1039R.C1041id.load_more_load_fail_view;
    }

    /* access modifiers changed from: protected */
    public int getLoadEndViewId() {
        return C1039R.C1041id.load_more_load_end_view;
    }
}
