package com.kongzue.dialogx.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.dialogs.BottomMenu;
import com.kongzue.dialogx.interfaces.BaseDialog;
import java.util.List;

public class BottomMenuArrayAdapter extends BaseAdapter {
    private BottomMenu bottomMenu;
    public Context context;
    TextInfo defaultMenuTextInfo;
    public List<CharSequence> objects;

    public long getItemId(int i) {
        return (long) i;
    }

    public BottomMenuArrayAdapter(BottomMenu bottomMenu2, Context context2, List<CharSequence> list) {
        this.objects = list;
        this.context = context2;
        this.bottomMenu = bottomMenu2;
    }

    class ViewHolder {
        ImageView imgDialogxMenuIcon;
        ImageView imgDialogxMenuSelection;
        Space spaceDialogxRightPadding;
        TextView txtDialogxMenuText;

        ViewHolder() {
        }
    }

    public int getCount() {
        return this.objects.size();
    }

    public CharSequence getItem(int i) {
        return this.objects.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View view2;
        boolean z = true;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater from = LayoutInflater.from(this.context);
            int i2 = C1903R.layout.item_dialogx_material_bottom_menu_normal_text;
            if (this.bottomMenu.getStyle().overrideBottomDialogRes() != null) {
                i2 = this.bottomMenu.getStyle().overrideBottomDialogRes().overrideMenuItemLayout(this.bottomMenu.isLightTheme(), i, getCount(), false);
                if (i2 == 0) {
                    i2 = C1903R.layout.item_dialogx_material_bottom_menu_normal_text;
                } else if ((!BaseDialog.isNull(this.bottomMenu.getTitle()) || !BaseDialog.isNull(this.bottomMenu.getMessage()) || this.bottomMenu.getCustomView() != null) && i == 0) {
                    i2 = this.bottomMenu.getStyle().overrideBottomDialogRes().overrideMenuItemLayout(this.bottomMenu.isLightTheme(), i, getCount(), true);
                }
            }
            view2 = from.inflate(i2, (ViewGroup) null);
            viewHolder.imgDialogxMenuIcon = (ImageView) view2.findViewById(C1903R.C1907id.img_dialogx_menu_icon);
            viewHolder.imgDialogxMenuSelection = (ImageView) view2.findViewById(C1903R.C1907id.img_dialogx_menu_selection);
            viewHolder.txtDialogxMenuText = (TextView) view2.findViewById(C1903R.C1907id.txt_dialogx_menu_text);
            viewHolder.spaceDialogxRightPadding = (Space) view2.findViewById(C1903R.C1907id.space_dialogx_right_padding);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (this.bottomMenu.getSelectMode() == BottomMenu.SELECT_MODE.SINGLE) {
            if (viewHolder.imgDialogxMenuSelection != null) {
                if (this.bottomMenu.getSelection() == i) {
                    viewHolder.imgDialogxMenuSelection.setVisibility(0);
                    int overrideSelectionImage = this.bottomMenu.getStyle().overrideBottomDialogRes().overrideSelectionImage(this.bottomMenu.isLightTheme(), true);
                    if (overrideSelectionImage != 0) {
                        viewHolder.imgDialogxMenuSelection.setImageResource(overrideSelectionImage);
                    }
                } else {
                    int overrideSelectionImage2 = this.bottomMenu.getStyle().overrideBottomDialogRes().overrideSelectionImage(this.bottomMenu.isLightTheme(), false);
                    if (overrideSelectionImage2 != 0) {
                        viewHolder.imgDialogxMenuSelection.setVisibility(0);
                        viewHolder.imgDialogxMenuSelection.setImageResource(overrideSelectionImage2);
                    } else {
                        viewHolder.imgDialogxMenuSelection.setVisibility(4);
                    }
                }
            }
        } else if (this.bottomMenu.getSelectMode() != BottomMenu.SELECT_MODE.MULTIPLE) {
            viewHolder.imgDialogxMenuSelection.setVisibility(8);
        } else if (viewHolder.imgDialogxMenuSelection != null) {
            if (this.bottomMenu.getSelectionList().contains(Integer.valueOf(i))) {
                viewHolder.imgDialogxMenuSelection.setVisibility(0);
                int overrideMultiSelectionImage = this.bottomMenu.getStyle().overrideBottomDialogRes().overrideMultiSelectionImage(this.bottomMenu.isLightTheme(), true);
                if (overrideMultiSelectionImage != 0) {
                    viewHolder.imgDialogxMenuSelection.setImageResource(overrideMultiSelectionImage);
                }
            } else {
                int overrideMultiSelectionImage2 = this.bottomMenu.getStyle().overrideBottomDialogRes().overrideMultiSelectionImage(this.bottomMenu.isLightTheme(), false);
                if (overrideMultiSelectionImage2 != 0) {
                    viewHolder.imgDialogxMenuSelection.setVisibility(0);
                    viewHolder.imgDialogxMenuSelection.setImageResource(overrideMultiSelectionImage2);
                } else {
                    viewHolder.imgDialogxMenuSelection.setVisibility(4);
                }
            }
        }
        int overrideSelectionMenuBackgroundColor = this.bottomMenu.getStyle().overrideBottomDialogRes() != null ? this.bottomMenu.getStyle().overrideBottomDialogRes().overrideSelectionMenuBackgroundColor(this.bottomMenu.isLightTheme()) : 0;
        if (this.bottomMenu.getSelection() == i && overrideSelectionMenuBackgroundColor != 0 && Build.VERSION.SDK_INT >= 21) {
            view2.setBackgroundTintList(ColorStateList.valueOf(this.context.getResources().getColor(overrideSelectionMenuBackgroundColor)));
        }
        CharSequence charSequence = this.objects.get(i);
        int i3 = this.bottomMenu.isLightTheme() ? C1903R.C1905color.black90 : C1903R.C1905color.white90;
        if (!(this.bottomMenu.getStyle().overrideBottomDialogRes() == null || this.bottomMenu.getStyle().overrideBottomDialogRes().overrideMenuTextColor(this.bottomMenu.isLightTheme()) == 0)) {
            i3 = this.bottomMenu.getStyle().overrideBottomDialogRes().overrideMenuTextColor(this.bottomMenu.isLightTheme());
        }
        if (charSequence != null) {
            if (this.defaultMenuTextInfo == null) {
                TextInfo textInfo = new TextInfo();
                if (viewHolder.txtDialogxMenuText.getEllipsize() != TextUtils.TruncateAt.END) {
                    z = false;
                }
                this.defaultMenuTextInfo = textInfo.setShowEllipsis(z).setFontColor(viewHolder.txtDialogxMenuText.getTextColors().getDefaultColor()).setBold(viewHolder.txtDialogxMenuText.getPaint().isFakeBoldText()).setFontSize(px2dip(viewHolder.txtDialogxMenuText.getTextSize())).setGravity(viewHolder.txtDialogxMenuText.getGravity()).setMaxLines(viewHolder.txtDialogxMenuText.getMaxLines());
            }
            viewHolder.txtDialogxMenuText.setText(charSequence);
            viewHolder.txtDialogxMenuText.setTextColor(this.context.getResources().getColor(i3));
            if (this.bottomMenu.getMenuItemTextInfoInterceptor() != null) {
                TextInfo menuItemTextInfo = this.bottomMenu.getMenuItemTextInfoInterceptor().menuItemTextInfo(this.bottomMenu, i, charSequence.toString());
                if (menuItemTextInfo != null) {
                    BaseDialog.useTextInfo(viewHolder.txtDialogxMenuText, menuItemTextInfo);
                } else if (this.bottomMenu.getMenuTextInfo() != null) {
                    BaseDialog.useTextInfo(viewHolder.txtDialogxMenuText, this.bottomMenu.getMenuTextInfo());
                } else {
                    BaseDialog.useTextInfo(viewHolder.txtDialogxMenuText, this.defaultMenuTextInfo);
                }
            } else if (this.bottomMenu.getMenuTextInfo() != null) {
                BaseDialog.useTextInfo(viewHolder.txtDialogxMenuText, this.bottomMenu.getMenuTextInfo());
            }
            if (Build.VERSION.SDK_INT >= 21 && viewHolder.imgDialogxMenuSelection != null) {
                if (this.bottomMenu.getStyle().overrideBottomDialogRes() == null || !this.bottomMenu.getStyle().overrideBottomDialogRes().selectionImageTint(this.bottomMenu.isLightTheme())) {
                    viewHolder.imgDialogxMenuSelection.setImageTintList((ColorStateList) null);
                } else {
                    viewHolder.imgDialogxMenuSelection.setImageTintList(ColorStateList.valueOf(this.context.getResources().getColor(i3)));
                }
            }
            if (this.bottomMenu.getOnIconChangeCallBack() != null) {
                int icon = this.bottomMenu.getOnIconChangeCallBack().getIcon(this.bottomMenu, i, charSequence.toString());
                boolean isAutoTintIconInLightOrDarkMode = this.bottomMenu.getOnIconChangeCallBack().isAutoTintIconInLightOrDarkMode();
                if (icon != 0) {
                    viewHolder.imgDialogxMenuIcon.setVisibility(0);
                    viewHolder.imgDialogxMenuIcon.setImageResource(icon);
                    if (viewHolder.spaceDialogxRightPadding != null) {
                        viewHolder.spaceDialogxRightPadding.setVisibility(0);
                    }
                    if (Build.VERSION.SDK_INT >= 21 && isAutoTintIconInLightOrDarkMode) {
                        viewHolder.imgDialogxMenuIcon.setImageTintList(ColorStateList.valueOf(this.context.getResources().getColor(i3)));
                    }
                } else {
                    viewHolder.imgDialogxMenuIcon.setVisibility(8);
                    if (viewHolder.spaceDialogxRightPadding != null) {
                        viewHolder.spaceDialogxRightPadding.setVisibility(8);
                    }
                }
            } else {
                viewHolder.imgDialogxMenuIcon.setVisibility(8);
                if (viewHolder.spaceDialogxRightPadding != null) {
                    viewHolder.spaceDialogxRightPadding.setVisibility(8);
                }
            }
        }
        return view2;
    }

    private int px2dip(float f) {
        return (int) ((f / this.context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
