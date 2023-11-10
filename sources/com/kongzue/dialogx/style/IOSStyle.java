package com.kongzue.dialogx.style;

import android.content.Context;
import android.content.res.Resources;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.interfaces.DialogXStyle;
import com.kongzue.dialogx.interfaces.ProgressViewInterface;
import com.kongzue.dialogx.iostheme.C2083R;
import com.kongzue.dialogx.style.views.ProgressView;

public class IOSStyle extends DialogXStyle {
    public int exitAnimResId() {
        return 0;
    }

    public int splitWidthPx() {
        return 1;
    }

    public static IOSStyle style() {
        return new IOSStyle();
    }

    public int layout(boolean z) {
        return z ? C2083R.layout.layout_dialogx_ios : C2083R.layout.layout_dialogx_ios_dark;
    }

    public int enterAnimResId() {
        return C2083R.C2084anim.anim_dialogx_ios_enter;
    }

    public int[] verticalButtonOrder() {
        return new int[]{1, 5, 3, 5, 2};
    }

    public int[] horizontalButtonOrder() {
        return new int[]{2, 5, 3, 5, 1};
    }

    public int splitColorRes(boolean z) {
        return z ? C2083R.C2085color.dialogxIOSSplitLight : C2083R.C2085color.dialogxIOSSplitDark;
    }

    public DialogXStyle.BlurBackgroundSetting messageDialogBlurSettings() {
        return new DialogXStyle.BlurBackgroundSetting() {
            public boolean blurBackground() {
                return true;
            }

            public int blurForwardColorRes(boolean z) {
                return z ? C2083R.C2085color.dialogxIOSBkgLight : C2083R.C2085color.dialogxIOSBkgDark;
            }

            public int blurBackgroundRoundRadiusPx() {
                return IOSStyle.this.dip2px(15.0f);
            }
        };
    }

    /* access modifiers changed from: private */
    public int dip2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public DialogXStyle.HorizontalButtonRes overrideHorizontalButtonRes() {
        return new DialogXStyle.HorizontalButtonRes() {
            public int overrideHorizontalOkButtonBackgroundRes(int i, boolean z) {
                return i == 1 ? z ? C2083R.C2086drawable.button_dialogx_ios_bottom_light : C2083R.C2086drawable.button_dialogx_ios_bottom_night : z ? C2083R.C2086drawable.button_dialogx_ios_right_light : C2083R.C2086drawable.button_dialogx_ios_right_night;
            }

            public int overrideHorizontalCancelButtonBackgroundRes(int i, boolean z) {
                return z ? C2083R.C2086drawable.button_dialogx_ios_left_light : C2083R.C2086drawable.button_dialogx_ios_left_night;
            }

            public int overrideHorizontalOtherButtonBackgroundRes(int i, boolean z) {
                return z ? C2083R.C2086drawable.button_dialogx_ios_center_light : C2083R.C2086drawable.button_dialogx_ios_center_night;
            }
        };
    }

    public DialogXStyle.VerticalButtonRes overrideVerticalButtonRes() {
        return new DialogXStyle.VerticalButtonRes() {
            public int overrideVerticalOkButtonBackgroundRes(int i, boolean z) {
                return z ? C2083R.C2086drawable.button_dialogx_ios_center_light : C2083R.C2086drawable.button_dialogx_ios_center_night;
            }

            public int overrideVerticalCancelButtonBackgroundRes(int i, boolean z) {
                return z ? C2083R.C2086drawable.button_dialogx_ios_bottom_light : C2083R.C2086drawable.button_dialogx_ios_bottom_night;
            }

            public int overrideVerticalOtherButtonBackgroundRes(int i, boolean z) {
                return z ? C2083R.C2086drawable.button_dialogx_ios_center_light : C2083R.C2086drawable.button_dialogx_ios_center_night;
            }
        };
    }

    public DialogXStyle.WaitTipRes overrideWaitTipRes() {
        return new DialogXStyle.WaitTipRes() {
            public boolean blurBackground() {
                return true;
            }

            public int overrideRadiusPx() {
                return -1;
            }

            public int overrideTextColorRes(boolean z) {
                return 0;
            }

            public int overrideWaitLayout(boolean z) {
                return 0;
            }

            public int overrideBackgroundColorRes(boolean z) {
                return z ? C2083R.C2085color.dialogxIOSWaitBkgDark : C2083R.C2085color.dialogxIOSWaitBkgLight;
            }

            public ProgressViewInterface overrideWaitView(Context context, boolean z) {
                return new ProgressView(context).setLightMode(z);
            }
        };
    }

    public DialogXStyle.BottomDialogRes overrideBottomDialogRes() {
        return new DialogXStyle.BottomDialogRes() {
            public float overrideBottomDialogMaxHeight() {
                return 0.0f;
            }

            public int overrideMenuDividerHeight(boolean z) {
                return 1;
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
                return true;
            }

            public boolean touchSlide() {
                return false;
            }

            public int overrideDialogLayout(boolean z) {
                return z ? C2083R.layout.layout_dialogx_bottom_ios : C2083R.layout.layout_dialogx_bottom_ios_dark;
            }

            public int overrideMenuDividerDrawableRes(boolean z) {
                return z ? C2083R.C2086drawable.rect_dialogx_ios_menu_split_divider : C2083R.C2086drawable.rect_dialogx_ios_menu_split_divider_night;
            }

            public int overrideMenuTextColor(boolean z) {
                return z ? C2083R.C2085color.dialogxIOSBlue : C2083R.C2085color.dialogxIOSBlueDark;
            }

            public int overrideMenuItemLayout(boolean z, int i, int i2, boolean z2) {
                if (z) {
                    if (i == 0) {
                        return z2 ? C2083R.layout.item_dialogx_ios_bottom_menu_center_light : C2083R.layout.item_dialogx_ios_bottom_menu_top_light;
                    }
                    if (i == i2 - 1) {
                        return C2083R.layout.item_dialogx_ios_bottom_menu_bottom_light;
                    }
                    return C2083R.layout.item_dialogx_ios_bottom_menu_center_light;
                } else if (i == 0) {
                    return z2 ? C2083R.layout.item_dialogx_ios_bottom_menu_center_dark : C2083R.layout.item_dialogx_ios_bottom_menu_top_dark;
                } else {
                    if (i == i2 - 1) {
                        return C2083R.layout.item_dialogx_ios_bottom_menu_bottom_dark;
                    }
                    return C2083R.layout.item_dialogx_ios_bottom_menu_center_dark;
                }
            }
        };
    }

    public DialogXStyle.PopTipSettings popTipSettings() {
        return new DialogXStyle.PopTipSettings() {
            public boolean tintIcon() {
                return false;
            }

            public int layout(boolean z) {
                return z ? C2083R.layout.layout_dialogx_poptip_ios : C2083R.layout.layout_dialogx_poptip_ios_dark;
            }

            public DialogXStyle.PopTipSettings.ALIGN align() {
                return DialogXStyle.PopTipSettings.ALIGN.TOP;
            }

            public int enterAnimResId(boolean z) {
                return C2083R.C2084anim.anim_dialogx_ios_top_enter;
            }

            public int exitAnimResId(boolean z) {
                return C2083R.C2084anim.anim_dialogx_ios_top_exit;
            }
        };
    }

    public DialogXStyle.PopNotificationSettings popNotificationSettings() {
        return new DialogXStyle.PopNotificationSettings() {
            public boolean tintIcon() {
                return false;
            }

            public int layout(boolean z) {
                return z ? C2083R.layout.layout_dialogx_popnotification_ios : C2083R.layout.layout_dialogx_popnotification_ios_dark;
            }

            public DialogXStyle.PopNotificationSettings.ALIGN align() {
                return DialogXStyle.PopNotificationSettings.ALIGN.TOP;
            }

            public int enterAnimResId(boolean z) {
                return C1903R.C1904anim.anim_dialogx_notification_enter;
            }

            public int exitAnimResId(boolean z) {
                return C1903R.C1904anim.anim_dialogx_notification_exit;
            }

            public DialogXStyle.BlurBackgroundSetting blurBackgroundSettings() {
                return new DialogXStyle.BlurBackgroundSetting() {
                    public boolean blurBackground() {
                        return true;
                    }

                    {
                        IOSStyle iOSStyle = IOSStyle.this;
                    }

                    public int blurForwardColorRes(boolean z) {
                        return z ? C2083R.C2085color.dialogxIOSNotificationBkgLight : C2083R.C2085color.dialogxIOSNotificationBkgDark;
                    }

                    public int blurBackgroundRoundRadiusPx() {
                        return IOSStyle.this.dip2px(18.0f);
                    }
                };
            }
        };
    }

    public DialogXStyle.PopMenuSettings popMenuSettings() {
        return new DialogXStyle.PopMenuSettings() {
            public int overrideMenuDividerHeight(boolean z) {
                return 1;
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

            public int layout(boolean z) {
                return z ? C2083R.layout.layout_dialogx_popmenu_ios : C2083R.layout.layout_dialogx_popmenu_ios_dark;
            }

            public DialogXStyle.BlurBackgroundSetting blurBackgroundSettings() {
                return new DialogXStyle.BlurBackgroundSetting() {
                    public boolean blurBackground() {
                        return true;
                    }

                    {
                        IOSStyle iOSStyle = IOSStyle.this;
                    }

                    public int blurForwardColorRes(boolean z) {
                        return z ? C2083R.C2085color.dialogxIOSBkgLight : C2083R.C2085color.dialogxIOSBkgDark;
                    }

                    public int blurBackgroundRoundRadiusPx() {
                        return IOSStyle.this.dip2px(15.0f);
                    }
                };
            }

            public int backgroundMaskColorRes() {
                return C2083R.C2085color.black20;
            }

            public int overrideMenuDividerDrawableRes(boolean z) {
                return z ? C2083R.C2086drawable.rect_dialogx_ios_menu_split_divider : C2083R.C2086drawable.rect_dialogx_ios_menu_split_divider_night;
            }

            public int overrideMenuItemLayoutRes(boolean z) {
                return z ? C2083R.layout.item_dialogx_ios_popmenu_light : C2083R.layout.item_dialogx_ios_popmenu_dark;
            }

            public int overrideMenuItemBackgroundRes(boolean z, int i, int i2, boolean z2) {
                if (z) {
                    if (i == 0) {
                        return C2083R.C2086drawable.button_dialogx_ios_top_light;
                    }
                    if (i == i2 - 1) {
                        return C2083R.C2086drawable.button_dialogx_ios_bottom_light;
                    }
                    return C2083R.C2086drawable.button_dialogx_ios_center_light;
                } else if (i == 0) {
                    return C2083R.C2086drawable.button_dialogx_ios_top_night;
                } else {
                    if (i == i2 - 1) {
                        return C2083R.C2086drawable.button_dialogx_ios_bottom_night;
                    }
                    return C2083R.C2086drawable.button_dialogx_ios_center_night;
                }
            }
        };
    }
}
