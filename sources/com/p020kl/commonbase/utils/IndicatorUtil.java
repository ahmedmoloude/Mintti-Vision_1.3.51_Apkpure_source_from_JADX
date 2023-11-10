package com.p020kl.commonbase.utils;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.material.tabs.TabLayout;
import java.lang.reflect.Field;

/* renamed from: com.kl.commonbase.utils.IndicatorUtil */
public class IndicatorUtil {
    public static void setIndicator(TabLayout tabLayout, int i, int i2) {
        Field field;
        LinearLayout linearLayout = null;
        try {
            field = tabLayout.getClass().getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            field = null;
        }
        field.setAccessible(true);
        try {
            linearLayout = (LinearLayout) field.get(tabLayout);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        int applyDimension = (int) TypedValue.applyDimension(1, (float) i, Resources.getSystem().getDisplayMetrics());
        int applyDimension2 = (int) TypedValue.applyDimension(1, (float) i2, Resources.getSystem().getDisplayMetrics());
        for (int i3 = 0; i3 < linearLayout.getChildCount(); i3++) {
            View childAt = linearLayout.getChildAt(i3);
            childAt.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1, 1.0f);
            layoutParams.leftMargin = applyDimension;
            layoutParams.rightMargin = applyDimension2;
            childAt.setLayoutParams(layoutParams);
            childAt.invalidate();
        }
    }
}
