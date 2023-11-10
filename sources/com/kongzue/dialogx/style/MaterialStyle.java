package com.kongzue.dialogx.style;

import android.content.Context;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.interfaces.DialogXStyle;
import com.kongzue.dialogx.interfaces.ProgressViewInterface;
import com.kongzue.dialogx.util.views.ProgressView;

public class MaterialStyle extends DialogXStyle {
    public DialogXStyle.BlurBackgroundSetting messageDialogBlurSettings() {
        return null;
    }

    public int splitColorRes(boolean z) {
        return 0;
    }

    public int splitWidthPx() {
        return 1;
    }

    public static MaterialStyle style() {
        return new MaterialStyle();
    }

    public int layout(boolean z) {
        return z ? C1903R.layout.layout_dialogx_material : C1903R.layout.layout_dialogx_material_dark;
    }

    public int enterAnimResId() {
        return C1903R.C1904anim.anim_dialogx_default_enter;
    }

    public int exitAnimResId() {
        return C1903R.C1904anim.anim_dialogx_default_exit;
    }

    public int[] verticalButtonOrder() {
        return new int[]{1, 3, 2};
    }

    public int[] horizontalButtonOrder() {
        return new int[]{3, 4, 2, 1};
    }

    public DialogXStyle.HorizontalButtonRes overrideHorizontalButtonRes() {
        return new DialogXStyle.HorizontalButtonRes() {
            public int overrideHorizontalOkButtonBackgroundRes(int i, boolean z) {
                return z ? C1903R.C1906drawable.button_dialogx_material_light : C1903R.C1906drawable.button_dialogx_material_night;
            }

            public int overrideHorizontalCancelButtonBackgroundRes(int i, boolean z) {
                return z ? C1903R.C1906drawable.button_dialogx_material_light : C1903R.C1906drawable.button_dialogx_material_night;
            }

            public int overrideHorizontalOtherButtonBackgroundRes(int i, boolean z) {
                return z ? C1903R.C1906drawable.button_dialogx_material_light : C1903R.C1906drawable.button_dialogx_material_night;
            }
        };
    }

    public DialogXStyle.VerticalButtonRes overrideVerticalButtonRes() {
        return new DialogXStyle.VerticalButtonRes() {
            public int overrideVerticalOkButtonBackgroundRes(int i, boolean z) {
                return z ? C1903R.C1906drawable.button_dialogx_material_light : C1903R.C1906drawable.button_dialogx_material_night;
            }

            public int overrideVerticalCancelButtonBackgroundRes(int i, boolean z) {
                return z ? C1903R.C1906drawable.button_dialogx_material_light : C1903R.C1906drawable.button_dialogx_material_night;
            }

            public int overrideVerticalOtherButtonBackgroundRes(int i, boolean z) {
                return z ? C1903R.C1906drawable.button_dialogx_material_light : C1903R.C1906drawable.button_dialogx_material_night;
            }
        };
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
                return C1903R.layout.layout_dialogx_wait;
            }

            public int overrideTextColorRes(boolean z) {
                return z ? C1903R.C1905color.white : C1903R.C1905color.black;
            }

            public ProgressViewInterface overrideWaitView(Context context, boolean z) {
                return new ProgressView(context);
            }
        };
    }

    public DialogXStyle.BottomDialogRes overrideBottomDialogRes() {
        return new DialogXStyle.BottomDialogRes() {
            public float overrideBottomDialogMaxHeight() {
                return 0.6f;
            }

            public int overrideMenuDividerHeight(boolean z) {
                return 1;
            }

            public int overrideMenuItemLayout(boolean z, int i, int i2, boolean z2) {
                return 0;
            }

            public int overrideSelectionMenuBackgroundColor(boolean z) {
                return 0;
            }

            public boolean selectionImageTint(boolean z) {
                return false;
            }

            public boolean touchSlide() {
                return true;
            }

            public int overrideDialogLayout(boolean z) {
                return z ? C1903R.layout.layout_dialogx_bottom_material : C1903R.layout.layout_dialogx_bottom_material_dark;
            }

            public int overrideMenuDividerDrawableRes(boolean z) {
                return z ? C1903R.C1906drawable.rect_dialogx_material_menu_split_divider : C1903R.C1906drawable.rect_dialogx_material_menu_split_divider_night;
            }

            public int overrideMenuTextColor(boolean z) {
                return z ? C1903R.C1905color.black90 : C1903R.C1905color.white90;
            }

            public int overrideSelectionImage(boolean z, boolean z2) {
                return z2 ? C1903R.mipmap.img_dialogx_bottom_menu_material_item_selection : C1903R.mipmap.img_dialogx_bottom_menu_material_item_non_select;
            }

            public int overrideMultiSelectionImage(boolean z, boolean z2) {
                return z2 ? C1903R.mipmap.img_dialogx_bottom_menu_material_item_multi_selection : C1903R.mipmap.img_dialogx_bottom_menu_material_item_non_multi_select;
            }
        };
    }

    public DialogXStyle.PopTipSettings popTipSettings() {
        return new DialogXStyle.PopTipSettings() {
            public boolean tintIcon() {
                return true;
            }

            public int layout(boolean z) {
                return z ? C1903R.layout.layout_dialogx_poptip_material : C1903R.layout.layout_dialogx_poptip_material_dark;
            }

            public DialogXStyle.PopTipSettings.ALIGN align() {
                return DialogXStyle.PopTipSettings.ALIGN.BOTTOM;
            }

            public int enterAnimResId(boolean z) {
                return C1903R.C1904anim.anim_dialogx_default_enter;
            }

            public int exitAnimResId(boolean z) {
                return C1903R.C1904anim.anim_dialogx_default_exit;
            }
        };
    }

    public DialogXStyle.PopNotificationSettings popNotificationSettings() {
        return new DialogXStyle.PopNotificationSettings() {
            public boolean tintIcon() {
                return false;
            }

            public int layout(boolean z) {
                return z ? C1903R.layout.layout_dialogx_popnotification_material : C1903R.layout.layout_dialogx_popnotification_material_dark;
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
        };
    }

    public DialogXStyle.PopMenuSettings popMenuSettings() {
        return new DialogXStyle.PopMenuSettings() {
            public int backgroundMaskColorRes() {
                return 0;
            }

            public DialogXStyle.BlurBackgroundSetting blurBackgroundSettings() {
                return null;
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
                return z ? C1903R.layout.layout_dialogx_popmenu_material : C1903R.layout.layout_dialogx_popmenu_material_dark;
            }

            public int overrideMenuTextColor(boolean z) {
                return z ? C1903R.C1905color.black90 : C1903R.C1905color.white90;
            }
        };
    }
}
