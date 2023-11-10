package com.kongzue.dialogx.interfaces;

import android.content.Context;

public abstract class DialogXStyle {
    public static final int BUTTON_CANCEL = 2;
    public static final int BUTTON_OK = 1;
    public static final int BUTTON_OTHER = 3;
    public static final int SPACE = 4;
    public static final int SPLIT = 5;
    public static final int styleVer = 5;

    public int enterAnimResId() {
        return 0;
    }

    public int exitAnimResId() {
        return 0;
    }

    public int layout(boolean z) {
        return 0;
    }

    public BlurBackgroundSetting messageDialogBlurSettings() {
        return null;
    }

    public BottomDialogRes overrideBottomDialogRes() {
        return null;
    }

    public HorizontalButtonRes overrideHorizontalButtonRes() {
        return null;
    }

    public VerticalButtonRes overrideVerticalButtonRes() {
        return null;
    }

    public WaitTipRes overrideWaitTipRes() {
        return null;
    }

    public PopMenuSettings popMenuSettings() {
        return null;
    }

    public PopNotificationSettings popNotificationSettings() {
        return null;
    }

    public PopTipSettings popTipSettings() {
        return null;
    }

    public int splitColorRes(boolean z) {
        return 436207616;
    }

    public int splitWidthPx() {
        return 1;
    }

    public int[] verticalButtonOrder() {
        return new int[]{3, 5, 2, 1};
    }

    public int[] horizontalButtonOrder() {
        return new int[]{3, 2, 1};
    }

    public abstract class BlurBackgroundSetting {
        public boolean blurBackground() {
            return false;
        }

        public int blurBackgroundRoundRadiusPx() {
            return 0;
        }

        public int blurForwardColorRes(boolean z) {
            return 0;
        }

        public BlurBackgroundSetting() {
        }
    }

    public abstract class HorizontalButtonRes {
        public int overrideHorizontalCancelButtonBackgroundRes(int i, boolean z) {
            return 0;
        }

        public int overrideHorizontalOkButtonBackgroundRes(int i, boolean z) {
            return 0;
        }

        public int overrideHorizontalOtherButtonBackgroundRes(int i, boolean z) {
            return 0;
        }

        public HorizontalButtonRes() {
        }
    }

    public abstract class VerticalButtonRes {
        public int overrideVerticalCancelButtonBackgroundRes(int i, boolean z) {
            return 0;
        }

        public int overrideVerticalOkButtonBackgroundRes(int i, boolean z) {
            return 0;
        }

        public int overrideVerticalOtherButtonBackgroundRes(int i, boolean z) {
            return 0;
        }

        public VerticalButtonRes() {
        }
    }

    public abstract class WaitTipRes {
        public boolean blurBackground() {
            return false;
        }

        public int overrideBackgroundColorRes(boolean z) {
            return 0;
        }

        public int overrideRadiusPx() {
            return 0;
        }

        public int overrideTextColorRes(boolean z) {
            return 0;
        }

        public int overrideWaitLayout(boolean z) {
            return 0;
        }

        public ProgressViewInterface overrideWaitView(Context context, boolean z) {
            return null;
        }

        public WaitTipRes() {
        }
    }

    public abstract class BottomDialogRes {
        public float overrideBottomDialogMaxHeight() {
            return 0.0f;
        }

        public int overrideDialogLayout(boolean z) {
            return 0;
        }

        public int overrideMenuDividerDrawableRes(boolean z) {
            return 0;
        }

        public int overrideMenuDividerHeight(boolean z) {
            return 0;
        }

        public int overrideMenuItemLayout(boolean z, int i, int i2, boolean z2) {
            return 0;
        }

        public int overrideMenuTextColor(boolean z) {
            return 0;
        }

        public int overrideMultiSelectionImage(boolean z, boolean z2) {
            return 0;
        }

        public int overrideSelectionImage(boolean z, boolean z2) {
            return 0;
        }

        public int overrideSelectionMenuBackgroundColor(boolean z) {
            return 0;
        }

        public boolean selectionImageTint(boolean z) {
            return false;
        }

        public boolean touchSlide() {
            return false;
        }

        public BottomDialogRes() {
        }
    }

    public static abstract class PopTipSettings {

        public enum ALIGN {
            CENTER,
            TOP,
            BOTTOM,
            TOP_INSIDE,
            BOTTOM_INSIDE
        }

        public int defaultIconError() {
            return 0;
        }

        public int defaultIconSuccess() {
            return 0;
        }

        public int defaultIconWarning() {
            return 0;
        }

        public int enterAnimResId(boolean z) {
            return 0;
        }

        public int exitAnimResId(boolean z) {
            return 0;
        }

        public int layout(boolean z) {
            return 0;
        }

        public boolean tintIcon() {
            return true;
        }

        public ALIGN align() {
            return ALIGN.CENTER;
        }
    }

    public abstract class PopMenuSettings {
        public int backgroundMaskColorRes() {
            return 0;
        }

        public BlurBackgroundSetting blurBackgroundSettings() {
            return null;
        }

        public int layout(boolean z) {
            return 0;
        }

        public int overrideMenuDividerDrawableRes(boolean z) {
            return 0;
        }

        public int overrideMenuDividerHeight(boolean z) {
            return 0;
        }

        public int overrideMenuItemBackgroundRes(boolean z, int i, int i2, boolean z2) {
            return 0;
        }

        public int overrideMenuItemLayoutRes(boolean z) {
            return 0;
        }

        public int overrideMenuTextColor(boolean z) {
            return 0;
        }

        public int overrideSelectionMenuBackgroundColor(boolean z) {
            return 0;
        }

        public int paddingVertical() {
            return 0;
        }

        public boolean selectionImageTint(boolean z) {
            return false;
        }

        public PopMenuSettings() {
        }
    }

    public static abstract class PopNotificationSettings {

        public enum ALIGN {
            CENTER,
            TOP,
            BOTTOM,
            TOP_INSIDE,
            BOTTOM_INSIDE
        }

        public BlurBackgroundSetting blurBackgroundSettings() {
            return null;
        }

        public int defaultIconError() {
            return 0;
        }

        public int defaultIconSuccess() {
            return 0;
        }

        public int defaultIconWarning() {
            return 0;
        }

        public int enterAnimResId(boolean z) {
            return 0;
        }

        public int exitAnimResId(boolean z) {
            return 0;
        }

        public int layout(boolean z) {
            return 0;
        }

        public boolean tintIcon() {
            return true;
        }

        public ALIGN align() {
            return ALIGN.CENTER;
        }
    }
}
