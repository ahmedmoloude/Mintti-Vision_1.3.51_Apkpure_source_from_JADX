package com.androidkun.xtablayout;

import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

class ViewUtilsLollipop {
    ViewUtilsLollipop() {
    }

    static void setBoundsViewOutlineProvider(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setOutlineProvider(ViewOutlineProvider.BOUNDS);
        }
    }
}
