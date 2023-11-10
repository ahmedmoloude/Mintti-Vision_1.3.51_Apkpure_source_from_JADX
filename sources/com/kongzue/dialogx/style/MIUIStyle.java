package com.kongzue.dialogx.style;

import android.content.Context;
import android.content.res.Resources;
import com.kongzue.dialogx.interfaces.DialogXStyle;
import com.kongzue.dialogx.interfaces.ProgressViewInterface;
import com.kongzue.dialogx.miuistyle.C2090R;

public class MIUIStyle extends DialogXStyle {
    public DialogXStyle.BlurBackgroundSetting messageDialogBlurSettings() {
        return null;
    }

    public DialogXStyle.HorizontalButtonRes overrideHorizontalButtonRes() {
        return null;
    }

    public DialogXStyle.VerticalButtonRes overrideVerticalButtonRes() {
        return null;
    }

    public DialogXStyle.PopTipSettings popTipSettings() {
        return null;
    }

    public int splitColorRes(boolean z) {
        return 0;
    }

    public int splitWidthPx() {
        return 0;
    }

    public static MIUIStyle style() {
        return new MIUIStyle();
    }

    public int layout(boolean z) {
        return z ? C2090R.layout.layout_dialogx_miui : C2090R.layout.layout_dialogx_miui_dark;
    }

    public int enterAnimResId() {
        return C2090R.C2091anim.anim_dialogx_bottom_enter;
    }

    public int exitAnimResId() {
        return C2090R.C2091anim.anim_dialogx_bottom_exit;
    }

    public int[] verticalButtonOrder() {
        return new int[]{1, 3, 2};
    }

    public int[] horizontalButtonOrder() {
        return new int[]{2, 3, 1};
    }

    public DialogXStyle.WaitTipRes overrideWaitTipRes() {
        return new DialogXStyle.WaitTipRes() {
            public boolean blurBackground() {
                return false;
            }

            public int overrideBackgroundColorRes(boolean z) {
                return 0;
            }

            public int overrideRadiusPx() {
                return -1;
            }

            public int overrideWaitLayout(boolean z) {
                return 0;
            }

            public ProgressViewInterface overrideWaitView(Context context, boolean z) {
                return null;
            }

            public int overrideTextColorRes(boolean z) {
                return z ? C2090R.C2092color.white : C2090R.C2092color.black;
            }
        };
    }

    public DialogXStyle.BottomDialogRes overrideBottomDialogRes() {
        return new DialogXStyle.BottomDialogRes() {
            public float overrideBottomDialogMaxHeight() {
                return 0.6f;
            }

            public int overrideMenuDividerDrawableRes(boolean z) {
                return 0;
            }

            public int overrideMenuDividerHeight(boolean z) {
                return 0;
            }

            public int overrideMultiSelectionImage(boolean z, boolean z2) {
                return 0;
            }

            public int overrideSelectionImage(boolean z, boolean z2) {
                return 0;
            }

            public boolean selectionImageTint(boolean z) {
                return false;
            }

            public boolean touchSlide() {
                return false;
            }

            public int overrideDialogLayout(boolean z) {
                return z ? C2090R.layout.layout_dialogx_bottom_miui : C2090R.layout.layout_dialogx_bottom_miui_dark;
            }

            public int overrideMenuTextColor(boolean z) {
                return z ? C2090R.C2092color.black : C2090R.C2092color.dialogxMIUITextDark;
            }

            public int overrideMenuItemLayout(boolean z, int i, int i2, boolean z2) {
                if (z) {
                    if (i == 0) {
                        return C2090R.layout.item_dialogx_miui_bottom_menu_top_light;
                    }
                    if (i == i2 - 1) {
                        return C2090R.layout.item_dialogx_miui_bottom_menu_bottom_light;
                    }
                    return C2090R.layout.item_dialogx_miui_bottom_menu_center_light;
                } else if (i == 0) {
                    return C2090R.layout.item_dialogx_miui_bottom_menu_top_dark;
                } else {
                    if (i == i2 - 1) {
                        return C2090R.layout.item_dialogx_miui_bottom_menu_bottom_dark;
                    }
                    return C2090R.layout.item_dialogx_miui_bottom_menu_center_dark;
                }
            }

            public int overrideSelectionMenuBackgroundColor(boolean z) {
                return z ? C2090R.C2092color.dialogxMIUIItemSelectionBkg : C2090R.C2092color.dialogxMIUIItemSelectionBkgDark;
            }
        };
    }

    public DialogXStyle.PopNotificationSettings popNotificationSettings() {
        return new DialogXStyle.PopNotificationSettings() {
            public boolean tintIcon() {
                return false;
            }

            public int layout(boolean z) {
                return z ? C2090R.layout.layout_dialogx_popnotification_miui : C2090R.layout.layout_dialogx_popnotification_miui_dark;
            }

            public DialogXStyle.PopNotificationSettings.ALIGN align() {
                return DialogXStyle.PopNotificationSettings.ALIGN.TOP;
            }

            public int enterAnimResId(boolean z) {
                return C2090R.C2091anim.anim_dialogx_notification_enter;
            }

            public int exitAnimResId(boolean z) {
                return C2090R.C2091anim.anim_dialogx_notification_exit;
            }
        };
    }

    public DialogXStyle.PopMenuSettings popMenuSettings() {
        return new DialogXStyle.PopMenuSettings() {
            public DialogXStyle.BlurBackgroundSetting blurBackgroundSettings() {
                return null;
            }

            public int overrideMenuDividerDrawableRes(boolean z) {
                return 0;
            }

            public int overrideMenuDividerHeight(boolean z) {
                return 0;
            }

            public int overrideMenuTextColor(boolean z) {
                return 0;
            }

            public int overrideSelectionMenuBackgroundColor(boolean z) {
                return 0;
            }

            public boolean selectionImageTint(boolean z) {
                return false;
            }

            public int layout(boolean z) {
                return z ? C2090R.layout.layout_dialogx_popmenu_miui : C2090R.layout.layout_dialogx_popmenu_miui_dark;
            }

            public int backgroundMaskColorRes() {
                return C2090R.C2092color.black20;
            }

            public int overrideMenuItemLayoutRes(boolean z) {
                return C2090R.layout.item_dialogx_miui_popmenu;
            }

            public int overrideMenuItemBackgroundRes(boolean z, int i, int i2, boolean z2) {
                if (z) {
                    if (i == 0) {
                        return C2090R.C2093drawable.button_dialogx_miui_top_light;
                    }
                    if (i == i2 - 1) {
                        return C2090R.C2093drawable.button_dialogx_miui_bottom_light;
                    }
                    return C2090R.C2093drawable.button_dialogx_miui_center_light;
                } else if (i == 0) {
                    return C2090R.C2093drawable.button_dialogx_miui_top_night;
                } else {
                    if (i == i2 - 1) {
                        return C2090R.C2093drawable.button_dialogx_miui_bottom_night;
                    }
                    return C2090R.C2093drawable.button_dialogx_miui_center_night;
                }
            }

            public int paddingVertical() {
                return MIUIStyle.this.dip2px(10.0f);
            }
        };
    }

    /* access modifiers changed from: private */
    public int dip2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }
}
