package com.androidkun.xtablayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.core.util.Pools;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.androidkun.xtablayout.ValueAnimatorCompat;
import com.androidkun.xtablayoutlibrary.C0973R;
import com.google.android.material.C1077R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XTabLayout extends HorizontalScrollView {
    private static final int ANIMATION_DURATION = 300;
    private static final int DEFAULT_GAP_TEXT_ICON = 8;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
    private static final int FIXED_WRAP_GUTTER_MIN = 16;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    private static final int INVALID_WIDTH = -1;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    private static final int MOTION_NON_ADJACENT_OFFSET = 24;
    private static final int SELECTED_TAB_ADD_WIDTH = 20;
    private static final int TAB_MIN_WIDTH_MARGIN = 56;
    private static final Pools.Pool<Tab> sTabPool = new Pools.SynchronizedPool(16);
    /* access modifiers changed from: private */
    public int dividerColor;
    /* access modifiers changed from: private */
    public int dividerGravity;
    /* access modifiers changed from: private */
    public int dividerHeight;
    /* access modifiers changed from: private */
    public int dividerWidth;
    private int mContentInsetStart;
    /* access modifiers changed from: private */
    public int mMode;
    private OnTabSelectedListener mOnTabSelectedListener;
    private List<OnTabSelectedListener> mOnTabSelectedListenerList;
    private TabLayoutOnPageChangeListener mPageChangeListener;
    private PagerAdapter mPagerAdapter;
    private DataSetObserver mPagerAdapterObserver;
    private final int mRequestedTabMaxWidth;
    private final int mRequestedTabMinWidth;
    private ValueAnimatorCompat mScrollAnimator;
    private final int mScrollableTabMinWidth;
    /* access modifiers changed from: private */
    public Tab mSelectedTab;
    /* access modifiers changed from: private */
    public int mTabGravity;
    /* access modifiers changed from: private */
    public int mTabMaxWidth;
    /* access modifiers changed from: private */
    public int mTabPaddingBottom;
    /* access modifiers changed from: private */
    public int mTabPaddingEnd;
    /* access modifiers changed from: private */
    public int mTabPaddingStart;
    /* access modifiers changed from: private */
    public int mTabPaddingTop;
    /* access modifiers changed from: private */
    public float mTabSelectedTextSize;
    private final SlidingTabStrip mTabStrip;
    /* access modifiers changed from: private */
    public int mTabTextAppearance;
    /* access modifiers changed from: private */
    public ColorStateList mTabTextColors;
    /* access modifiers changed from: private */
    public float mTabTextMultiLineSize;
    /* access modifiers changed from: private */
    public float mTabTextSize;
    private final Pools.Pool<TabView> mTabViewPool;
    private final ArrayList<Tab> mTabs;
    private ViewPager mViewPager;
    /* access modifiers changed from: private */
    public final int xTabBackgroundColor;
    private int xTabDisplayNum;
    /* access modifiers changed from: private */
    public boolean xTabDividerWidthWidthText;
    /* access modifiers changed from: private */
    public final int xTabSelectedBackgroundColor;
    /* access modifiers changed from: private */
    public boolean xTabTextAllCaps;
    /* access modifiers changed from: private */
    public boolean xTabTextBold;
    /* access modifiers changed from: private */
    public boolean xTabTextSelectedBold;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public interface OnTabSelectedListener {
        void onTabReselected(Tab tab);

        void onTabSelected(Tab tab);

        void onTabUnselected(Tab tab);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TabGravity {
    }

    public XTabLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public XTabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX INFO: finally extract failed */
    public XTabLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.xTabTextAllCaps = false;
        this.xTabDividerWidthWidthText = false;
        this.mTabs = new ArrayList<>();
        this.mTabTextSize = 0.0f;
        this.mTabSelectedTextSize = 0.0f;
        this.mTabMaxWidth = Integer.MAX_VALUE;
        this.mOnTabSelectedListenerList = new ArrayList();
        this.mTabViewPool = new Pools.SimplePool(12);
        ThemeUtils.checkAppCompatTheme(context);
        setHorizontalScrollBarEnabled(false);
        SlidingTabStrip slidingTabStrip = new SlidingTabStrip(context);
        this.mTabStrip = slidingTabStrip;
        super.addView(slidingTabStrip, 0, new FrameLayout.LayoutParams(-2, -1));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0973R.styleable.XTabLayout, i, C1077R.C1083style.Widget_Design_TabLayout);
        slidingTabStrip.setSelectedIndicatorHeight(obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabIndicatorHeight, dpToPx(2)));
        slidingTabStrip.setmSelectedIndicatorWidth(obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabIndicatorWidth, 0));
        slidingTabStrip.setSelectedIndicatorColor(obtainStyledAttributes.getColor(C0973R.styleable.XTabLayout_xTabIndicatorColor, 0));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabPadding, 0);
        this.mTabPaddingBottom = dimensionPixelSize;
        this.mTabPaddingEnd = dimensionPixelSize;
        this.mTabPaddingTop = dimensionPixelSize;
        this.mTabPaddingStart = dimensionPixelSize;
        this.mTabPaddingStart = obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabPaddingStart, this.mTabPaddingStart);
        this.mTabPaddingTop = obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabPaddingTop, this.mTabPaddingTop);
        this.mTabPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabPaddingEnd, this.mTabPaddingEnd);
        this.mTabPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabPaddingBottom, this.mTabPaddingBottom);
        this.xTabTextAllCaps = obtainStyledAttributes.getBoolean(C0973R.styleable.XTabLayout_xTabTextAllCaps, false);
        this.mTabTextAppearance = obtainStyledAttributes.getResourceId(C0973R.styleable.XTabLayout_xTabTextAppearance, C1077R.C1083style.TextAppearance_Design_Tab);
        this.mTabTextSize = (float) obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabTextSize, 0);
        this.xTabTextBold = obtainStyledAttributes.getBoolean(C0973R.styleable.XTabLayout_xTabTextBold, false);
        this.mTabSelectedTextSize = (float) obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabSelectedTextSize, 0);
        this.xTabTextSelectedBold = obtainStyledAttributes.getBoolean(C0973R.styleable.XTabLayout_xTabTextSelectedBold, false);
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(this.mTabTextAppearance, C1077R.styleable.TextAppearance);
        try {
            if (this.mTabTextSize == 0.0f) {
                this.mTabTextSize = (float) obtainStyledAttributes2.getDimensionPixelSize(C1077R.styleable.TextAppearance_android_textSize, 0);
            }
            this.mTabTextColors = obtainStyledAttributes2.getColorStateList(C1077R.styleable.TextAppearance_android_textColor);
            obtainStyledAttributes2.recycle();
            if (obtainStyledAttributes.hasValue(C0973R.styleable.XTabLayout_xTabTextColor)) {
                this.mTabTextColors = obtainStyledAttributes.getColorStateList(C0973R.styleable.XTabLayout_xTabTextColor);
            }
            if (obtainStyledAttributes.hasValue(C0973R.styleable.XTabLayout_xTabSelectedTextColor)) {
                this.mTabTextColors = createColorStateList(this.mTabTextColors.getDefaultColor(), obtainStyledAttributes.getColor(C0973R.styleable.XTabLayout_xTabSelectedTextColor, 0));
            }
            this.xTabDisplayNum = obtainStyledAttributes.getInt(C0973R.styleable.XTabLayout_xTabDisplayNum, 0);
            this.mRequestedTabMinWidth = obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabMinWidth, -1);
            this.mRequestedTabMaxWidth = obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabMaxWidth, -1);
            this.xTabBackgroundColor = obtainStyledAttributes.getColor(C0973R.styleable.XTabLayout_xTabBackgroundColor, 0);
            this.xTabSelectedBackgroundColor = obtainStyledAttributes.getColor(C0973R.styleable.XTabLayout_xTabSelectedBackgroundColor, 0);
            this.mContentInsetStart = obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabContentStart, 0);
            this.mMode = obtainStyledAttributes.getInt(C0973R.styleable.XTabLayout_xTabMode, 1);
            this.mTabGravity = obtainStyledAttributes.getInt(C0973R.styleable.XTabLayout_xTabGravity, 0);
            this.dividerWidth = obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabDividerWidth, 0);
            this.dividerHeight = obtainStyledAttributes.getDimensionPixelSize(C0973R.styleable.XTabLayout_xTabDividerHeight, 0);
            this.dividerColor = obtainStyledAttributes.getColor(C0973R.styleable.XTabLayout_xTabDividerColor, ViewCompat.MEASURED_STATE_MASK);
            this.dividerGravity = obtainStyledAttributes.getInteger(C0973R.styleable.XTabLayout_xTabDividerGravity, 1);
            this.xTabDividerWidthWidthText = obtainStyledAttributes.getBoolean(C0973R.styleable.XTabLayout_xTabDividerWidthWidthText, false);
            obtainStyledAttributes.recycle();
            Resources resources = getResources();
            this.mTabTextMultiLineSize = (float) resources.getDimensionPixelSize(C1077R.dimen.design_tab_text_size_2line);
            this.mScrollableTabMinWidth = resources.getDimensionPixelSize(C1077R.dimen.design_tab_scrollable_min_width);
            applyModeAndGravity();
            addDivider();
        } catch (Throwable th) {
            obtainStyledAttributes2.recycle();
            throw th;
        }
    }

    private void addDivider() {
        post(new Runnable() {
            public void run() {
                if (XTabLayout.this.dividerWidth > 0) {
                    LinearLayout linearLayout = (LinearLayout) XTabLayout.this.getChildAt(0);
                    linearLayout.setShowDividers(2);
                    DividerDrawable dividerDrawable = new DividerDrawable(XTabLayout.this.getContext());
                    dividerDrawable.setDividerSize(XTabLayout.this.dividerWidth, XTabLayout.this.dividerHeight);
                    dividerDrawable.setColor(XTabLayout.this.dividerColor);
                    dividerDrawable.setGravity(XTabLayout.this.dividerGravity);
                    linearLayout.setDividerDrawable(dividerDrawable);
                }
            }
        });
    }

    public void setDividerSize(int i, int i2) {
        this.dividerWidth = i;
        this.dividerHeight = i2;
        addDivider();
    }

    public void setDividerColor(int i) {
        this.dividerColor = i;
        addDivider();
    }

    public void setDividerGravity(int i) {
        this.dividerGravity = i;
        addDivider();
    }

    public void setAllCaps(boolean z) {
        this.xTabTextAllCaps = z;
    }

    public void setSelectedTabIndicatorColor(int i) {
        this.mTabStrip.setSelectedIndicatorColor(i);
    }

    public void setSelectedTabIndicatorHeight(int i) {
        this.mTabStrip.setSelectedIndicatorHeight(i);
    }

    public void setxTabDisplayNum(int i) {
        this.xTabDisplayNum = i;
    }

    public void setScrollPosition(int i, float f, boolean z) {
        setScrollPosition(i, f, z, true);
    }

    /* access modifiers changed from: private */
    public void setScrollPosition(int i, float f, boolean z, boolean z2) {
        int round = Math.round(((float) i) + f);
        if (round >= 0 && round < this.mTabStrip.getChildCount()) {
            if (z2) {
                this.mTabStrip.setIndicatorPositionFromTabPosition(i, f);
            }
            ValueAnimatorCompat valueAnimatorCompat = this.mScrollAnimator;
            if (valueAnimatorCompat != null && valueAnimatorCompat.isRunning()) {
                this.mScrollAnimator.cancel();
            }
            scrollTo(calculateScrollXForTab(i, f), 0);
            if (z) {
                setSelectedTabView(round);
            }
        }
    }

    private float getScrollPosition() {
        return this.mTabStrip.getIndicatorPosition();
    }

    public void addTab(Tab tab) {
        addTab(tab, this.mTabs.isEmpty());
    }

    public void addTab(Tab tab, int i) {
        addTab(tab, i, this.mTabs.isEmpty());
    }

    public void addTab(Tab tab, boolean z) {
        if (tab.mParent == this) {
            addTabView(tab, z);
            configureTab(tab, this.mTabs.size());
            if (z) {
                tab.select();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }

    public void addTab(Tab tab, int i, boolean z) {
        if (tab.mParent == this) {
            addTabView(tab, i, z);
            configureTab(tab, i);
            if (z) {
                tab.select();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }

    private void addTabFromItemView(TabItem tabItem) {
        Tab newTab = newTab();
        if (tabItem.mText != null) {
            newTab.setText(tabItem.mText);
        }
        if (tabItem.mIcon != null) {
            newTab.setIcon(tabItem.mIcon);
        }
        if (tabItem.mCustomLayout != 0) {
            newTab.setCustomView(tabItem.mCustomLayout);
        }
        addTab(newTab);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.mOnTabSelectedListener = onTabSelectedListener;
    }

    public void addOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.mOnTabSelectedListenerList.add(onTabSelectedListener);
    }

    public Tab newTab() {
        Tab acquire = sTabPool.acquire();
        if (acquire == null) {
            acquire = new Tab();
        }
        XTabLayout unused = acquire.mParent = this;
        TabView unused2 = acquire.mView = createTabView(acquire);
        return acquire;
    }

    public int getTabCount() {
        return this.mTabs.size();
    }

    public Tab getTabAt(int i) {
        return this.mTabs.get(i);
    }

    public int getSelectedTabPosition() {
        Tab tab = this.mSelectedTab;
        if (tab != null) {
            return tab.getPosition();
        }
        return -1;
    }

    public void removeTab(Tab tab) {
        if (tab.mParent == this) {
            removeTabAt(tab.getPosition());
            return;
        }
        throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
    }

    public void removeTabAt(int i) {
        Tab tab = this.mSelectedTab;
        int position = tab != null ? tab.getPosition() : 0;
        removeTabViewAt(i);
        Tab remove = this.mTabs.remove(i);
        if (remove != null) {
            remove.reset();
            sTabPool.release(remove);
        }
        int size = this.mTabs.size();
        for (int i2 = i; i2 < size; i2++) {
            this.mTabs.get(i2).setPosition(i2);
        }
        if (position == i) {
            selectTab(this.mTabs.isEmpty() ? null : this.mTabs.get(Math.max(0, i - 1)));
        }
    }

    public void removeAllTabs() {
        for (int childCount = this.mTabStrip.getChildCount() - 1; childCount >= 0; childCount--) {
            removeTabViewAt(childCount);
        }
        Iterator<Tab> it = this.mTabs.iterator();
        while (it.hasNext()) {
            Tab next = it.next();
            it.remove();
            next.reset();
            sTabPool.release(next);
        }
        this.mSelectedTab = null;
    }

    public void setTabMode(int i) {
        if (i != this.mMode) {
            this.mMode = i;
            applyModeAndGravity();
        }
    }

    public int getTabMode() {
        return this.mMode;
    }

    public void setTabGravity(int i) {
        if (this.mTabGravity != i) {
            this.mTabGravity = i;
            applyModeAndGravity();
        }
    }

    public int getTabGravity() {
        return this.mTabGravity;
    }

    public void setTabTextColors(ColorStateList colorStateList) {
        if (this.mTabTextColors != colorStateList) {
            this.mTabTextColors = colorStateList;
            updateAllTabs();
        }
    }

    public ColorStateList getTabTextColors() {
        return this.mTabTextColors;
    }

    public void setTabTextColors(int i, int i2) {
        setTabTextColors(createColorStateList(i, i2));
    }

    public void setupWithViewPager(ViewPager viewPager) {
        TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener;
        ViewPager viewPager2 = this.mViewPager;
        if (!(viewPager2 == null || (tabLayoutOnPageChangeListener = this.mPageChangeListener) == null)) {
            viewPager2.removeOnPageChangeListener(tabLayoutOnPageChangeListener);
        }
        if (viewPager != null) {
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                this.mViewPager = viewPager;
                if (this.mPageChangeListener == null) {
                    this.mPageChangeListener = new TabLayoutOnPageChangeListener(this);
                }
                this.mPageChangeListener.reset();
                viewPager.addOnPageChangeListener(this.mPageChangeListener);
                setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager));
                setPagerAdapter(adapter, true);
                return;
            }
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        }
        this.mViewPager = null;
        setOnTabSelectedListener((OnTabSelectedListener) null);
        setPagerAdapter((PagerAdapter) null, true);
    }

    @Deprecated
    public void setTabsFromPagerAdapter(PagerAdapter pagerAdapter) {
        setPagerAdapter(pagerAdapter, false);
    }

    public boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }

    private int getTabScrollRange() {
        return Math.max(0, ((this.mTabStrip.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight());
    }

    private void setPagerAdapter(PagerAdapter pagerAdapter, boolean z) {
        DataSetObserver dataSetObserver;
        PagerAdapter pagerAdapter2 = this.mPagerAdapter;
        if (!(pagerAdapter2 == null || (dataSetObserver = this.mPagerAdapterObserver) == null)) {
            pagerAdapter2.unregisterDataSetObserver(dataSetObserver);
        }
        this.mPagerAdapter = pagerAdapter;
        if (z && pagerAdapter != null) {
            if (this.mPagerAdapterObserver == null) {
                this.mPagerAdapterObserver = new PagerAdapterObserver();
            }
            pagerAdapter.registerDataSetObserver(this.mPagerAdapterObserver);
        }
        populateFromPagerAdapter();
    }

    /* access modifiers changed from: private */
    public void populateFromPagerAdapter() {
        int currentItem;
        removeAllTabs();
        PagerAdapter pagerAdapter = this.mPagerAdapter;
        if (pagerAdapter != null) {
            int count = pagerAdapter.getCount();
            for (int i = 0; i < count; i++) {
                addTab(newTab().setText(this.mPagerAdapter.getPageTitle(i)), false);
            }
            ViewPager viewPager = this.mViewPager;
            if (viewPager != null && count > 0 && (currentItem = viewPager.getCurrentItem()) != getSelectedTabPosition() && currentItem < getTabCount()) {
                selectTab(getTabAt(currentItem));
                return;
            }
            return;
        }
        removeAllTabs();
    }

    private void updateAllTabs() {
        int size = this.mTabs.size();
        for (int i = 0; i < size; i++) {
            this.mTabs.get(i).updateView();
        }
    }

    private TabView createTabView(Tab tab) {
        Pools.Pool<TabView> pool = this.mTabViewPool;
        TabView acquire = pool != null ? pool.acquire() : null;
        if (acquire == null) {
            acquire = new TabView(getContext());
        }
        acquire.setTab(tab);
        acquire.setFocusable(true);
        acquire.setMinimumWidth(getTabMinWidth());
        return acquire;
    }

    private void configureTab(Tab tab, int i) {
        tab.setPosition(i);
        this.mTabs.add(i, tab);
        int size = this.mTabs.size();
        while (true) {
            i++;
            if (i < size) {
                this.mTabs.get(i).setPosition(i);
            } else {
                return;
            }
        }
    }

    private void addTabView(Tab tab, boolean z) {
        final TabView access$600 = tab.mView;
        if (this.mTabSelectedTextSize != 0.0f) {
            access$600.post(new Runnable() {
                public void run() {
                    int width = access$600.getWidth();
                    String text = access$600.getText();
                    if (!TextUtils.isEmpty(text)) {
                        Paint paint = new Paint();
                        paint.setTextSize(XTabLayout.this.mTabSelectedTextSize);
                        Rect rect = new Rect();
                        paint.getTextBounds(text, 0, text.length(), rect);
                        if (width - rect.width() < XTabLayout.this.dpToPx(20)) {
                            int width2 = rect.width() + XTabLayout.this.dpToPx(20);
                            ViewGroup.LayoutParams layoutParams = access$600.getLayoutParams();
                            layoutParams.width = width2;
                            access$600.setLayoutParams(layoutParams);
                        }
                    }
                }
            });
        }
        this.mTabStrip.addView(access$600, createLayoutParamsForTabs());
        if (z) {
            access$600.setSelected(true);
        }
    }

    private void addTabView(Tab tab, int i, boolean z) {
        TabView access$600 = tab.mView;
        this.mTabStrip.addView(access$600, i, createLayoutParamsForTabs());
        if (z) {
            access$600.setSelected(true);
        }
    }

    public void addView(View view) {
        addViewInternal(view);
    }

    public void addView(View view, int i) {
        addViewInternal(view);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view);
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view);
    }

    private void addViewInternal(View view) {
        if (view instanceof TabItem) {
            addTabFromItemView((TabItem) view);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    private LinearLayout.LayoutParams createLayoutParamsForTabs() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        updateTabViewLayoutParams(layoutParams);
        return layoutParams;
    }

    private void updateTabViewLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (this.mMode == 1 && this.mTabGravity == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = 0.0f;
    }

    /* access modifiers changed from: private */
    public int dpToPx(int i) {
        return Math.round(getResources().getDisplayMetrics().density * ((float) i));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b7, code lost:
        if (r0.getMeasuredWidth() != getMeasuredWidth()) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c2, code lost:
        if (r0.getMeasuredWidth() < getMeasuredWidth()) goto L_0x00c6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r7, int r8) {
        /*
            r6 = this;
            int r0 = r6.getDefaultHeight()
            int r0 = r6.dpToPx(r0)
            int r1 = r6.getPaddingTop()
            int r0 = r0 + r1
            int r1 = r6.getPaddingBottom()
            int r0 = r0 + r1
            int r1 = android.view.View.MeasureSpec.getMode(r8)
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = 1073741824(0x40000000, float:2.0)
            if (r1 == r2) goto L_0x0024
            if (r1 == 0) goto L_0x001f
            goto L_0x0030
        L_0x001f:
            int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r3)
            goto L_0x0030
        L_0x0024:
            int r8 = android.view.View.MeasureSpec.getSize(r8)
            int r8 = java.lang.Math.min(r0, r8)
            int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r3)
        L_0x0030:
            int r0 = android.view.View.MeasureSpec.getSize(r7)
            int r1 = android.view.View.MeasureSpec.getMode(r7)
            r2 = 1
            if (r1 == 0) goto L_0x009a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "specWidth:"
            r1.append(r4)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r4 = "BBB"
            android.util.Log.w(r4, r1)
            androidx.viewpager.widget.PagerAdapter r1 = r6.mPagerAdapter
            r4 = 56
            if (r1 == 0) goto L_0x008d
            int r5 = r6.xTabDisplayNum
            if (r5 == 0) goto L_0x008d
            int r1 = r1.getCount()
            if (r1 == r2) goto L_0x0075
            int r1 = r6.xTabDisplayNum
            if (r1 != r2) goto L_0x0067
            goto L_0x0075
        L_0x0067:
            int r1 = r6.mRequestedTabMaxWidth
            if (r1 <= 0) goto L_0x006c
            goto L_0x0072
        L_0x006c:
            int r1 = r6.dpToPx(r4)
            int r1 = r0 - r1
        L_0x0072:
            r6.mTabMaxWidth = r1
            goto L_0x009a
        L_0x0075:
            android.content.Context r0 = r6.getContext()
            java.lang.String r1 = "window"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            android.view.Display r0 = r0.getDefaultDisplay()
            int r0 = r0.getWidth()
            r6.mTabMaxWidth = r0
            goto L_0x009a
        L_0x008d:
            int r1 = r6.mRequestedTabMaxWidth
            if (r1 <= 0) goto L_0x0092
            goto L_0x0098
        L_0x0092:
            int r1 = r6.dpToPx(r4)
            int r1 = r0 - r1
        L_0x0098:
            r6.mTabMaxWidth = r1
        L_0x009a:
            super.onMeasure(r7, r8)
            int r7 = r6.getChildCount()
            if (r7 != r2) goto L_0x00e7
            r7 = 0
            android.view.View r0 = r6.getChildAt(r7)
            int r1 = r6.mMode
            if (r1 == 0) goto L_0x00ba
            if (r1 == r2) goto L_0x00af
            goto L_0x00c7
        L_0x00af:
            int r1 = r0.getMeasuredWidth()
            int r4 = r6.getMeasuredWidth()
            if (r1 == r4) goto L_0x00c5
            goto L_0x00c6
        L_0x00ba:
            int r1 = r0.getMeasuredWidth()
            int r4 = r6.getMeasuredWidth()
            if (r1 >= r4) goto L_0x00c5
            goto L_0x00c6
        L_0x00c5:
            r2 = 0
        L_0x00c6:
            r7 = r2
        L_0x00c7:
            if (r7 == 0) goto L_0x00e7
            int r7 = r6.getPaddingTop()
            int r1 = r6.getPaddingBottom()
            int r7 = r7 + r1
            android.view.ViewGroup$LayoutParams r1 = r0.getLayoutParams()
            int r1 = r1.height
            int r7 = getChildMeasureSpec(r8, r7, r1)
            int r8 = r6.getMeasuredWidth()
            int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r3)
            r0.measure(r8, r7)
        L_0x00e7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.androidkun.xtablayout.XTabLayout.onMeasure(int, int):void");
    }

    private void removeTabViewAt(int i) {
        TabView tabView = (TabView) this.mTabStrip.getChildAt(i);
        this.mTabStrip.removeViewAt(i);
        if (tabView != null) {
            tabView.reset();
            this.mTabViewPool.release(tabView);
        }
        requestLayout();
    }

    private void animateToTab(int i) {
        if (i != -1) {
            if (getWindowToken() == null || !ViewCompat.isLaidOut(this) || this.mTabStrip.childrenNeedLayout()) {
                setScrollPosition(i, 0.0f, true);
                return;
            }
            int scrollX = getScrollX();
            int calculateScrollXForTab = calculateScrollXForTab(i, 0.0f);
            if (scrollX != calculateScrollXForTab) {
                if (this.mScrollAnimator == null) {
                    ValueAnimatorCompat createAnimator = ViewUtils.createAnimator();
                    this.mScrollAnimator = createAnimator;
                    createAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                    this.mScrollAnimator.setDuration(300);
                    this.mScrollAnimator.setUpdateListener(new ValueAnimatorCompat.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                            XTabLayout.this.scrollTo(valueAnimatorCompat.getAnimatedIntValue(), 0);
                        }
                    });
                }
                this.mScrollAnimator.setIntValues(scrollX, calculateScrollXForTab);
                this.mScrollAnimator.start();
            }
            this.mTabStrip.animateIndicatorToPosition(i, 300);
        }
    }

    private void setSelectedTabView(int i) {
        int childCount = this.mTabStrip.getChildCount();
        if (i < childCount && !this.mTabStrip.getChildAt(i).isSelected()) {
            int i2 = 0;
            while (i2 < childCount) {
                this.mTabStrip.getChildAt(i2).setSelected(i2 == i);
                i2++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void selectTab(Tab tab) {
        selectTab(tab, true);
    }

    /* access modifiers changed from: package-private */
    public void selectTab(Tab tab, boolean z) {
        OnTabSelectedListener onTabSelectedListener;
        OnTabSelectedListener onTabSelectedListener2;
        Tab tab2 = this.mSelectedTab;
        if (tab2 != tab) {
            if (z) {
                int position = tab != null ? tab.getPosition() : -1;
                if (position != -1) {
                    setSelectedTabView(position);
                }
                Tab tab3 = this.mSelectedTab;
                if ((tab3 == null || tab3.getPosition() == -1) && position != -1) {
                    setScrollPosition(position, 0.0f, true);
                } else {
                    animateToTab(position);
                }
            }
            Tab tab4 = this.mSelectedTab;
            if (!(tab4 == null || (onTabSelectedListener2 = this.mOnTabSelectedListener) == null)) {
                onTabSelectedListener2.onTabUnselected(tab4);
            }
            for (OnTabSelectedListener onTabUnselected : this.mOnTabSelectedListenerList) {
                onTabUnselected.onTabUnselected(this.mSelectedTab);
            }
            this.mSelectedTab = tab;
            if (!(tab == null || (onTabSelectedListener = this.mOnTabSelectedListener) == null)) {
                onTabSelectedListener.onTabSelected(tab);
            }
            for (OnTabSelectedListener onTabSelected : this.mOnTabSelectedListenerList) {
                onTabSelected.onTabSelected(this.mSelectedTab);
            }
        } else if (tab2 != null) {
            OnTabSelectedListener onTabSelectedListener3 = this.mOnTabSelectedListener;
            if (onTabSelectedListener3 != null) {
                onTabSelectedListener3.onTabReselected(tab2);
            }
            for (OnTabSelectedListener onTabReselected : this.mOnTabSelectedListenerList) {
                onTabReselected.onTabReselected(this.mSelectedTab);
            }
            animateToTab(tab.getPosition());
        }
    }

    private int calculateScrollXForTab(int i, float f) {
        int i2 = 0;
        if (this.mMode != 0) {
            return 0;
        }
        View childAt = this.mTabStrip.getChildAt(i);
        int i3 = i + 1;
        View childAt2 = i3 < this.mTabStrip.getChildCount() ? this.mTabStrip.getChildAt(i3) : null;
        int width = childAt != null ? childAt.getWidth() : 0;
        if (childAt2 != null) {
            i2 = childAt2.getWidth();
        }
        return ((childAt.getLeft() + ((int) ((((float) (width + i2)) * f) * 0.5f))) + (childAt.getWidth() / 2)) - (getWidth() / 2);
    }

    private void applyModeAndGravity() {
        ViewCompat.setPaddingRelative(this.mTabStrip, this.mMode == 0 ? Math.max(0, this.mContentInsetStart - this.mTabPaddingStart) : 0, 0, 0, 0);
        int i = this.mMode;
        if (i == 0) {
            this.mTabStrip.setGravity(GravityCompat.START);
        } else if (i == 1) {
            this.mTabStrip.setGravity(1);
        }
        updateTabViews(true);
    }

    /* access modifiers changed from: private */
    public void updateTabViews(boolean z) {
        for (int i = 0; i < this.mTabStrip.getChildCount(); i++) {
            View childAt = this.mTabStrip.getChildAt(i);
            childAt.setMinimumWidth(getTabMinWidth());
            updateTabViewLayoutParams((LinearLayout.LayoutParams) childAt.getLayoutParams());
            if (z) {
                childAt.requestLayout();
            }
        }
    }

    public static final class Tab {
        public static final int INVALID_POSITION = -1;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        /* access modifiers changed from: private */
        public XTabLayout mParent;
        private int mPosition;
        private Object mTag;
        private CharSequence mText;
        /* access modifiers changed from: private */
        public TabView mView;

        private Tab() {
            this.mPosition = -1;
        }

        public Object getTag() {
            return this.mTag;
        }

        public int getTextWidth() {
            return this.mView.getTextWidth();
        }

        public Tab setTag(Object obj) {
            this.mTag = obj;
            return this;
        }

        public View getCustomView() {
            return this.mCustomView;
        }

        public Tab setCustomView(View view) {
            this.mCustomView = view;
            updateView();
            return this;
        }

        public Tab setCustomView(int i) {
            return setCustomView(LayoutInflater.from(this.mView.getContext()).inflate(i, this.mView, false));
        }

        public Drawable getIcon() {
            return this.mIcon;
        }

        public int getPosition() {
            return this.mPosition;
        }

        /* access modifiers changed from: package-private */
        public void setPosition(int i) {
            this.mPosition = i;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public Tab setIcon(Drawable drawable) {
            this.mIcon = drawable;
            updateView();
            return this;
        }

        public Tab setIcon(int i) {
            if (this.mParent != null) {
                return setIcon(AppCompatDrawableManager.get().getDrawable(this.mParent.getContext(), i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public Tab setText(CharSequence charSequence) {
            this.mText = charSequence;
            updateView();
            return this;
        }

        public Tab setText(int i) {
            XTabLayout xTabLayout = this.mParent;
            if (xTabLayout != null) {
                return setText(xTabLayout.getResources().getText(i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public void select() {
            XTabLayout xTabLayout = this.mParent;
            if (xTabLayout != null) {
                xTabLayout.selectTab(this);
                return;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public boolean isSelected() {
            XTabLayout xTabLayout = this.mParent;
            if (xTabLayout != null) {
                return xTabLayout.getSelectedTabPosition() == this.mPosition;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public Tab setContentDescription(int i) {
            XTabLayout xTabLayout = this.mParent;
            if (xTabLayout != null) {
                return setContentDescription(xTabLayout.getResources().getText(i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public Tab setContentDescription(CharSequence charSequence) {
            this.mContentDesc = charSequence;
            updateView();
            return this;
        }

        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }

        /* access modifiers changed from: private */
        public void updateView() {
            TabView tabView = this.mView;
            if (tabView != null) {
                tabView.update();
            }
        }

        /* access modifiers changed from: private */
        public void reset() {
            this.mParent = null;
            this.mView = null;
            this.mTag = null;
            this.mIcon = null;
            this.mText = null;
            this.mContentDesc = null;
            this.mPosition = -1;
            this.mCustomView = null;
        }
    }

    class TabView extends LinearLayout implements View.OnLongClickListener {
        private ImageView mCustomIconView;
        private TextView mCustomTextView;
        private View mCustomView;
        private int mDefaultMaxLines = 2;
        private ImageView mIconView;
        private Tab mTab;
        private TextView mTextView;

        public TabView(Context context) {
            super(context);
            ViewCompat.setPaddingRelative(this, XTabLayout.this.mTabPaddingStart, XTabLayout.this.mTabPaddingTop, XTabLayout.this.mTabPaddingEnd, XTabLayout.this.mTabPaddingBottom);
            setGravity(17);
            setOrientation(1);
            setClickable(true);
        }

        public String getText() {
            return this.mTextView.getText().toString();
        }

        public int getTextWidth() {
            if (TextUtils.isEmpty(this.mTextView.getText().toString())) {
                return 0;
            }
            Rect rect = new Rect();
            String charSequence = this.mTextView.getText().toString();
            this.mTextView.getPaint().getTextBounds(charSequence, 0, charSequence.length(), rect);
            return rect.width();
        }

        public boolean performClick() {
            boolean performClick = super.performClick();
            Tab tab = this.mTab;
            if (tab == null) {
                return performClick;
            }
            tab.select();
            return true;
        }

        public void setSelected(boolean z) {
            boolean z2 = isSelected() != z;
            super.setSelected(z);
            if (!z) {
                if (XTabLayout.this.xTabBackgroundColor != 0) {
                    setBackgroundColor(XTabLayout.this.xTabBackgroundColor);
                }
                this.mTextView.setTextSize(0, XTabLayout.this.mTabTextSize);
                if (XTabLayout.this.xTabTextBold) {
                    this.mTextView.setTypeface(Typeface.defaultFromStyle(1));
                } else {
                    this.mTextView.setTypeface(Typeface.defaultFromStyle(0));
                }
            }
            if (z2 && z) {
                if (XTabLayout.this.xTabSelectedBackgroundColor != 0) {
                    setBackgroundColor(XTabLayout.this.xTabSelectedBackgroundColor);
                }
                sendAccessibilityEvent(4);
                TextView textView = this.mTextView;
                if (textView != null) {
                    textView.setSelected(z);
                    if (XTabLayout.this.mTabSelectedTextSize != 0.0f) {
                        this.mTextView.setTextSize(0, XTabLayout.this.mTabSelectedTextSize);
                        if (XTabLayout.this.xTabTextSelectedBold) {
                            this.mTextView.setTypeface(Typeface.defaultFromStyle(1));
                        } else {
                            this.mTextView.setTypeface(Typeface.defaultFromStyle(0));
                        }
                    }
                }
                ImageView imageView = this.mIconView;
                if (imageView != null) {
                    imageView.setSelected(z);
                }
            }
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(ActionBar.Tab.class.getName());
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(ActionBar.Tab.class.getName());
        }

        public void onMeasure(int i, int i2) {
            Layout layout;
            int size = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            int access$2400 = XTabLayout.this.getTabMaxWidth();
            if (access$2400 > 0 && (mode == 0 || size > access$2400)) {
                i = View.MeasureSpec.makeMeasureSpec(XTabLayout.this.mTabMaxWidth, Integer.MIN_VALUE);
            }
            super.onMeasure(i, i2);
            if (this.mTextView != null) {
                getResources();
                float access$2000 = XTabLayout.this.mTabTextSize;
                int i3 = this.mDefaultMaxLines;
                ImageView imageView = this.mIconView;
                boolean z = true;
                if (imageView == null || imageView.getVisibility() != 0) {
                    TextView textView = this.mTextView;
                    if (textView != null && textView.getLineCount() > 1) {
                        access$2000 = XTabLayout.this.mTabTextMultiLineSize;
                    }
                } else {
                    i3 = 1;
                }
                float textSize = this.mTextView.getTextSize();
                int lineCount = this.mTextView.getLineCount();
                int maxLines = TextViewCompat.getMaxLines(this.mTextView);
                int i4 = (access$2000 > textSize ? 1 : (access$2000 == textSize ? 0 : -1));
                if (i4 != 0 || (maxLines >= 0 && i3 != maxLines)) {
                    if (XTabLayout.this.mMode == 1 && i4 > 0 && lineCount == 1 && ((layout = this.mTextView.getLayout()) == null || approximateLineWidth(layout, 0, access$2000) > ((float) layout.getWidth()))) {
                        z = false;
                    }
                    if (z) {
                        if (!this.mTextView.isSelected() || XTabLayout.this.mTabSelectedTextSize == 0.0f) {
                            this.mTextView.setTextSize(0, XTabLayout.this.mTabTextSize);
                        } else {
                            this.mTextView.setTextSize(0, XTabLayout.this.mTabSelectedTextSize);
                        }
                        this.mTextView.setMaxLines(i3);
                        super.onMeasure(i, i2);
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public void setTab(Tab tab) {
            if (tab != this.mTab) {
                this.mTab = tab;
                update();
            }
        }

        /* access modifiers changed from: private */
        public void reset() {
            setTab((Tab) null);
            setSelected(false);
        }

        /* access modifiers changed from: package-private */
        public final void update() {
            Tab tab = this.mTab;
            View customView = tab != null ? tab.getCustomView() : null;
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(customView);
                    }
                    addView(customView);
                }
                this.mCustomView = customView;
                TextView textView = this.mTextView;
                if (textView != null) {
                    textView.setVisibility(8);
                }
                ImageView imageView = this.mIconView;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.mIconView.setImageDrawable((Drawable) null);
                }
                TextView textView2 = (TextView) customView.findViewById(16908308);
                this.mCustomTextView = textView2;
                if (textView2 != null) {
                    this.mDefaultMaxLines = TextViewCompat.getMaxLines(textView2);
                }
                this.mCustomIconView = (ImageView) customView.findViewById(16908294);
            } else {
                View view = this.mCustomView;
                if (view != null) {
                    removeView(view);
                    this.mCustomView = null;
                }
                this.mCustomTextView = null;
                this.mCustomIconView = null;
            }
            if (this.mCustomView == null) {
                if (this.mIconView == null) {
                    ImageView imageView2 = (ImageView) LayoutInflater.from(getContext()).inflate(C1077R.layout.design_layout_tab_icon, this, false);
                    addView(imageView2, 0);
                    this.mIconView = imageView2;
                }
                if (this.mTextView == null) {
                    TextView textView3 = (TextView) LayoutInflater.from(getContext()).inflate(C1077R.layout.design_layout_tab_text, this, false);
                    addView(textView3);
                    this.mTextView = textView3;
                    this.mDefaultMaxLines = TextViewCompat.getMaxLines(textView3);
                }
                this.mTextView.setTextAppearance(getContext(), XTabLayout.this.mTabTextAppearance);
                if (XTabLayout.this.mTabTextColors != null) {
                    this.mTextView.setTextColor(XTabLayout.this.mTabTextColors);
                }
                updateTextAndIcon(this.mTextView, this.mIconView);
                return;
            }
            TextView textView4 = this.mCustomTextView;
            if (textView4 != null || this.mCustomIconView != null) {
                updateTextAndIcon(textView4, this.mCustomIconView);
            }
        }

        private void updateTextAndIcon(TextView textView, ImageView imageView) {
            Tab tab = this.mTab;
            Drawable icon = tab != null ? tab.getIcon() : null;
            Tab tab2 = this.mTab;
            CharSequence text = tab2 != null ? tab2.getText() : null;
            Tab tab3 = this.mTab;
            CharSequence contentDescription = tab3 != null ? tab3.getContentDescription() : null;
            if (imageView != null) {
                if (icon != null) {
                    imageView.setImageDrawable(icon);
                    imageView.setVisibility(0);
                    setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                    imageView.setImageDrawable((Drawable) null);
                }
                imageView.setContentDescription(contentDescription);
            }
            boolean z = !TextUtils.isEmpty(text);
            if (textView != null) {
                if (z) {
                    textView.setAllCaps(XTabLayout.this.xTabTextAllCaps);
                    textView.setText(text);
                    textView.setVisibility(0);
                    setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText((CharSequence) null);
                }
                textView.setContentDescription(contentDescription);
            }
            if (imageView != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                int access$1300 = (!z || imageView.getVisibility() != 0) ? 0 : XTabLayout.this.dpToPx(8);
                if (access$1300 != marginLayoutParams.bottomMargin) {
                    marginLayoutParams.bottomMargin = access$1300;
                    imageView.requestLayout();
                }
            }
            if (z || TextUtils.isEmpty(contentDescription)) {
                setOnLongClickListener((View.OnLongClickListener) null);
                setLongClickable(false);
                return;
            }
            setOnLongClickListener(this);
        }

        public boolean onLongClick(View view) {
            int[] iArr = new int[2];
            getLocationOnScreen(iArr);
            Context context = getContext();
            int width = getWidth();
            int height = getHeight();
            int i = context.getResources().getDisplayMetrics().widthPixels;
            Toast makeText = Toast.makeText(context, this.mTab.getContentDescription(), 0);
            makeText.setGravity(49, (iArr[0] + (width / 2)) - (i / 2), height);
            makeText.show();
            return true;
        }

        public Tab getTab() {
            return this.mTab;
        }

        private float approximateLineWidth(Layout layout, int i, float f) {
            return layout.getLineWidth(i) * (f / layout.getPaint().getTextSize());
        }
    }

    private class SlidingTabStrip extends LinearLayout {
        private ValueAnimatorCompat mIndicatorAnimator;
        private int mIndicatorLeft = -1;
        private int mIndicatorRight = -1;
        private int mSelectedIndicatorHeight;
        private final Paint mSelectedIndicatorPaint;
        private int mSelectedIndicatorWidth;
        /* access modifiers changed from: private */
        public int mSelectedPosition = -1;
        /* access modifiers changed from: private */
        public float mSelectionOffset;

        SlidingTabStrip(Context context) {
            super(context);
            setWillNotDraw(false);
            this.mSelectedIndicatorPaint = new Paint();
        }

        /* access modifiers changed from: package-private */
        public void setSelectedIndicatorColor(int i) {
            if (this.mSelectedIndicatorPaint.getColor() != i) {
                this.mSelectedIndicatorPaint.setColor(i);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: package-private */
        public void setSelectedIndicatorHeight(int i) {
            if (this.mSelectedIndicatorHeight != i) {
                this.mSelectedIndicatorHeight = i;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: package-private */
        public void setmSelectedIndicatorWidth(int i) {
            if (this.mSelectedIndicatorWidth != i) {
                this.mSelectedIndicatorWidth = i;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        public int getmSelectedIndicatorWidth() {
            return this.mSelectedIndicatorWidth;
        }

        /* access modifiers changed from: package-private */
        public boolean childrenNeedLayout() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (getChildAt(i).getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void setIndicatorPositionFromTabPosition(int i, float f) {
            ValueAnimatorCompat valueAnimatorCompat = this.mIndicatorAnimator;
            if (valueAnimatorCompat != null && valueAnimatorCompat.isRunning()) {
                this.mIndicatorAnimator.cancel();
            }
            this.mSelectedPosition = i;
            this.mSelectionOffset = f;
            updateIndicatorPosition();
        }

        /* access modifiers changed from: package-private */
        public float getIndicatorPosition() {
            return ((float) this.mSelectedPosition) + this.mSelectionOffset;
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (View.MeasureSpec.getMode(i) == 1073741824) {
                boolean z = true;
                if (XTabLayout.this.mMode == 1 && XTabLayout.this.mTabGravity == 1) {
                    int childCount = getChildCount();
                    int i3 = 0;
                    for (int i4 = 0; i4 < childCount; i4++) {
                        View childAt = getChildAt(i4);
                        if (childAt.getVisibility() == 0) {
                            i3 = Math.max(i3, childAt.getMeasuredWidth());
                        }
                    }
                    if (i3 > 0) {
                        if (i3 * childCount <= getMeasuredWidth() - (XTabLayout.this.dpToPx(16) * 2)) {
                            boolean z2 = false;
                            for (int i5 = 0; i5 < childCount; i5++) {
                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getChildAt(i5).getLayoutParams();
                                if (layoutParams.width != i3 || layoutParams.weight != 0.0f) {
                                    layoutParams.width = i3;
                                    layoutParams.weight = 0.0f;
                                    z2 = true;
                                }
                            }
                            z = z2;
                        } else {
                            int unused = XTabLayout.this.mTabGravity = 0;
                            XTabLayout.this.updateTabViews(false);
                        }
                        if (z) {
                            super.onMeasure(i, i2);
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            ValueAnimatorCompat valueAnimatorCompat = this.mIndicatorAnimator;
            if (valueAnimatorCompat == null || !valueAnimatorCompat.isRunning()) {
                updateIndicatorPosition();
                return;
            }
            this.mIndicatorAnimator.cancel();
            animateIndicatorToPosition(this.mSelectedPosition, Math.round((1.0f - this.mIndicatorAnimator.getAnimatedFraction()) * ((float) this.mIndicatorAnimator.getDuration())));
        }

        private void updateIndicatorPosition() {
            int i;
            int i2;
            int i3;
            View childAt = getChildAt(this.mSelectedPosition);
            if (childAt == null || childAt.getWidth() <= 0) {
                i2 = -1;
                i = -1;
            } else {
                i = childAt.getLeft();
                i2 = childAt.getRight();
                int i4 = 0;
                if (this.mSelectedIndicatorWidth == 0 && !XTabLayout.this.xTabDividerWidthWidthText) {
                    this.mSelectedIndicatorWidth = 16843039;
                }
                int i5 = this.mSelectedIndicatorWidth;
                if (i5 != 0 && (i3 = this.mIndicatorRight - this.mIndicatorLeft) > i5) {
                    i4 = (i3 - i5) / 2;
                    i += i4;
                    i2 -= i4;
                }
                if (this.mSelectionOffset > 0.0f && this.mSelectedPosition < getChildCount() - 1) {
                    View childAt2 = getChildAt(this.mSelectedPosition + 1);
                    int left = childAt2.getLeft() + i4;
                    int right = childAt2.getRight() - i4;
                    float f = this.mSelectionOffset;
                    i = (int) ((((float) left) * f) + ((1.0f - f) * ((float) i)));
                    i2 = (int) ((((float) right) * f) + ((1.0f - f) * ((float) i2)));
                }
            }
            setIndicatorPosition(i, i2);
        }

        /* access modifiers changed from: private */
        public void setIndicatorPosition(int i, int i2) {
            int access$1500 = i + XTabLayout.this.mTabPaddingStart;
            int access$1700 = i2 - XTabLayout.this.mTabPaddingEnd;
            if (access$1500 != this.mIndicatorLeft || access$1700 != this.mIndicatorRight) {
                this.mIndicatorLeft = access$1500;
                this.mIndicatorRight = access$1700;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: package-private */
        public void animateIndicatorToPosition(final int i, int i2) {
            final int i3;
            final int i4;
            ValueAnimatorCompat valueAnimatorCompat = this.mIndicatorAnimator;
            if (valueAnimatorCompat != null && valueAnimatorCompat.isRunning()) {
                this.mIndicatorAnimator.cancel();
            }
            boolean z = ViewCompat.getLayoutDirection(this) == 1;
            View childAt = getChildAt(i);
            if (childAt == null) {
                updateIndicatorPosition();
                return;
            }
            final int left = childAt.getLeft();
            final int right = childAt.getRight();
            if (Math.abs(i - this.mSelectedPosition) <= 1) {
                i4 = this.mIndicatorLeft;
                i3 = this.mIndicatorRight;
            } else {
                int access$1300 = XTabLayout.this.dpToPx(24);
                i4 = (i >= this.mSelectedPosition ? !z : z) ? left - access$1300 : access$1300 + right;
                i3 = i4;
            }
            if (i4 != left || i3 != right) {
                ValueAnimatorCompat createAnimator = ViewUtils.createAnimator();
                this.mIndicatorAnimator = createAnimator;
                createAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                createAnimator.setDuration(i2);
                createAnimator.setFloatValues(0.0f, 1.0f);
                createAnimator.setUpdateListener(new ValueAnimatorCompat.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                        float animatedFraction = valueAnimatorCompat.getAnimatedFraction();
                        SlidingTabStrip.this.setIndicatorPosition(AnimationUtils.lerp(i4, left, animatedFraction), AnimationUtils.lerp(i3, right, animatedFraction));
                    }
                });
                createAnimator.setListener(new ValueAnimatorCompat.AnimatorListenerAdapter() {
                    public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
                        int unused = SlidingTabStrip.this.mSelectedPosition = i;
                        float unused2 = SlidingTabStrip.this.mSelectionOffset = 0.0f;
                    }
                });
                createAnimator.start();
            }
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            int i = this.mIndicatorLeft;
            if (i >= 0 && this.mIndicatorRight > i) {
                if (this.mSelectedIndicatorWidth == 0 || XTabLayout.this.xTabDividerWidthWidthText) {
                    int i2 = this.mIndicatorRight - this.mIndicatorLeft;
                    if (i2 > XTabLayout.this.mSelectedTab.getTextWidth()) {
                        this.mIndicatorLeft += (i2 - XTabLayout.this.mSelectedTab.getTextWidth()) / 2;
                        this.mIndicatorRight -= (i2 - XTabLayout.this.mSelectedTab.getTextWidth()) / 2;
                    }
                } else {
                    int i3 = this.mIndicatorRight;
                    int i4 = this.mIndicatorLeft;
                    int i5 = i3 - i4;
                    int i6 = this.mSelectedIndicatorWidth;
                    if (i5 > i6) {
                        this.mIndicatorLeft = i4 + ((i5 - i6) / 2);
                        this.mIndicatorRight = i3 - ((i5 - i6) / 2);
                    }
                }
                canvas.drawRect((float) this.mIndicatorLeft, (float) (getHeight() - this.mSelectedIndicatorHeight), (float) this.mIndicatorRight, (float) getHeight(), this.mSelectedIndicatorPaint);
            }
        }
    }

    private static ColorStateList createColorStateList(int i, int i2) {
        return new ColorStateList(new int[][]{SELECTED_STATE_SET, EMPTY_STATE_SET}, new int[]{i2, i});
    }

    private int getDefaultHeight() {
        int size = this.mTabs.size();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i < size) {
                Tab tab = this.mTabs.get(i);
                if (tab != null && tab.getIcon() != null && !TextUtils.isEmpty(tab.getText())) {
                    z = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        return z ? 72 : 48;
    }

    private int getTabMinWidth() {
        if (this.mPagerAdapter != null && this.xTabDisplayNum != 0) {
            WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
            if (this.mPagerAdapter.getCount() == 1 || this.xTabDisplayNum == 1) {
                return windowManager.getDefaultDisplay().getWidth();
            }
            if (this.mPagerAdapter.getCount() < this.xTabDisplayNum) {
                return windowManager.getDefaultDisplay().getWidth() / this.mPagerAdapter.getCount();
            }
            return windowManager.getDefaultDisplay().getWidth() / this.xTabDisplayNum;
        } else if (this.xTabDisplayNum != 0) {
            return ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getWidth() / this.xTabDisplayNum;
        } else {
            int i = this.mRequestedTabMinWidth;
            if (i != -1) {
                return i;
            }
            if (this.mMode == 0) {
                return this.mScrollableTabMinWidth;
            }
            return 0;
        }
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    /* access modifiers changed from: private */
    public int getTabMaxWidth() {
        return this.mTabMaxWidth;
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int mPreviousScrollState;
        private int mScrollState;
        private final WeakReference<XTabLayout> mTabLayoutRef;

        public TabLayoutOnPageChangeListener(XTabLayout xTabLayout) {
            this.mTabLayoutRef = new WeakReference<>(xTabLayout);
        }

        public void onPageScrollStateChanged(int i) {
            this.mPreviousScrollState = this.mScrollState;
            this.mScrollState = i;
        }

        public void onPageScrolled(int i, float f, int i2) {
            XTabLayout xTabLayout = (XTabLayout) this.mTabLayoutRef.get();
            if (xTabLayout != null) {
                int i3 = this.mScrollState;
                boolean z = false;
                boolean z2 = i3 != 2 || this.mPreviousScrollState == 1;
                if (!(i3 == 2 && this.mPreviousScrollState == 0)) {
                    z = true;
                }
                xTabLayout.setScrollPosition(i, f, z2, z);
            }
        }

        public void onPageSelected(int i) {
            XTabLayout xTabLayout = (XTabLayout) this.mTabLayoutRef.get();
            if (xTabLayout != null && xTabLayout.getSelectedTabPosition() != i) {
                int i2 = this.mScrollState;
                xTabLayout.selectTab(xTabLayout.getTabAt(i), i2 == 0 || (i2 == 2 && this.mPreviousScrollState == 0));
            }
        }

        /* access modifiers changed from: private */
        public void reset() {
            this.mScrollState = 0;
            this.mPreviousScrollState = 0;
        }
    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager mViewPager;

        public void onTabReselected(Tab tab) {
        }

        public void onTabUnselected(Tab tab) {
        }

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.mViewPager = viewPager;
        }

        public void onTabSelected(Tab tab) {
            this.mViewPager.setCurrentItem(tab.getPosition());
        }
    }

    private class PagerAdapterObserver extends DataSetObserver {
        private PagerAdapterObserver() {
        }

        public void onChanged() {
            XTabLayout.this.populateFromPagerAdapter();
        }

        public void onInvalidated() {
            XTabLayout.this.populateFromPagerAdapter();
        }
    }
}
