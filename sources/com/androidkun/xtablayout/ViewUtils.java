package com.androidkun.xtablayout;

import android.os.Build;
import android.view.View;
import com.androidkun.xtablayout.ValueAnimatorCompat;

class ViewUtils {
    static final ValueAnimatorCompat.Creator DEFAULT_ANIMATOR_CREATOR = new ValueAnimatorCompat.Creator() {
        public ValueAnimatorCompat createAnimator() {
            return new ValueAnimatorCompat(Build.VERSION.SDK_INT >= 12 ? new ValueAnimatorCompatImplHoneycombMr1() : new ValueAnimatorCompatImplEclairMr1());
        }
    };
    private static final ViewUtilsImpl IMPL;

    private interface ViewUtilsImpl {
        void setBoundsViewOutlineProvider(View view);
    }

    ViewUtils() {
    }

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new ViewUtilsImplLollipop();
        } else {
            IMPL = new ViewUtilsImplBase();
        }
    }

    private static class ViewUtilsImplBase implements ViewUtilsImpl {
        public void setBoundsViewOutlineProvider(View view) {
        }

        private ViewUtilsImplBase() {
        }
    }

    private static class ViewUtilsImplLollipop implements ViewUtilsImpl {
        private ViewUtilsImplLollipop() {
        }

        public void setBoundsViewOutlineProvider(View view) {
            ViewUtilsLollipop.setBoundsViewOutlineProvider(view);
        }
    }

    static void setBoundsViewOutlineProvider(View view) {
        IMPL.setBoundsViewOutlineProvider(view);
    }

    static ValueAnimatorCompat createAnimator() {
        return DEFAULT_ANIMATOR_CREATOR.createAnimator();
    }
}
