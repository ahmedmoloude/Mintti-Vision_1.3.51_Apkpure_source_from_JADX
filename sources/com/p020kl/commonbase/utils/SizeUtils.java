package com.p020kl.commonbase.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;
import com.p020kl.commonbase.base.BaseApplication;

/* renamed from: com.kl.commonbase.utils.SizeUtils */
public class SizeUtils {
    public static int dp2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dp(float f) {
        return (int) ((f / Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int px2sp(float f) {
        return (int) ((f / Resources.getSystem().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int getScreenWidth() {
        return getScreenSize().x;
    }

    public static int getScreenHeight() {
        return getScreenSize().y;
    }

    public static Point getScreenSize() {
        WindowManager windowManager = (WindowManager) BaseApplication.getInstance().getSystemService("window");
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            windowManager.getDefaultDisplay().getRealSize(point);
        } else {
            windowManager.getDefaultDisplay().getSize(point);
        }
        return point;
    }
}
