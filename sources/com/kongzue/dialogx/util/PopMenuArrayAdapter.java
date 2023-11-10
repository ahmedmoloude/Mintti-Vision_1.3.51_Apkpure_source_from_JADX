package com.kongzue.dialogx.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.dialogs.PopMenu;
import com.kongzue.dialogx.interfaces.BaseDialog;
import java.util.ArrayList;
import java.util.List;

public class PopMenuArrayAdapter extends BaseAdapter {
    public Context context;
    LayoutInflater mInflater;
    public List<CharSequence> menuList;
    private PopMenu popMenu;

    private boolean isHaveProperties(int i, int i2) {
        return (i & i2) == i2;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public PopMenuArrayAdapter(PopMenu popMenu2, Context context2, List<CharSequence> list) {
        this.popMenu = popMenu2;
        this.menuList = list;
        this.context = context2;
        this.mInflater = LayoutInflater.from(context2);
    }

    public List<CharSequence> getMenuList() {
        return this.menuList;
    }

    public int getCount() {
        if (this.menuList == null) {
            this.menuList = new ArrayList();
        }
        return this.menuList.size();
    }

    public Object getItem(int i) {
        return this.menuList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        int overrideMenuItemLayoutRes;
        if (view == null) {
            ViewHolder viewHolder2 = new ViewHolder();
            int i2 = C1903R.layout.item_dialogx_material_context_menu_normal_text;
            if (!(this.popMenu.getStyle().popMenuSettings() == null || (overrideMenuItemLayoutRes = this.popMenu.getStyle().popMenuSettings().overrideMenuItemLayoutRes(this.popMenu.isLightTheme())) == 0)) {
                i2 = overrideMenuItemLayoutRes;
            }
            View inflate = this.mInflater.inflate(i2, (ViewGroup) null);
            viewHolder2.imgDialogxMenuIcon = (ImageView) inflate.findViewById(C1903R.C1907id.img_dialogx_menu_icon);
            viewHolder2.txtDialogxMenuText = (TextView) inflate.findViewById(C1903R.C1907id.txt_dialogx_menu_text);
            viewHolder2.spaceRightPadding = (Space) inflate.findViewById(C1903R.C1907id.space_dialogx_right_padding);
            inflate.setTag(viewHolder2);
            View view2 = inflate;
            viewHolder = viewHolder2;
            view = view2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        int overrideMenuItemBackgroundRes = this.popMenu.getStyle().popMenuSettings() == null ? 0 : this.popMenu.getStyle().popMenuSettings().overrideMenuItemBackgroundRes(this.popMenu.isLightTheme(), i, getCount(), false);
        if (overrideMenuItemBackgroundRes != 0) {
            view.setBackgroundResource(overrideMenuItemBackgroundRes);
        }
        viewHolder.imgDialogxMenuIcon.setVisibility(8);
        viewHolder.txtDialogxMenuText.setText(this.menuList.get(i));
        if (!(this.popMenu.getStyle().popMenuSettings() == null || this.popMenu.getStyle().popMenuSettings().paddingVertical() == 0)) {
            if (i == 0) {
                view.setPadding(0, this.popMenu.getStyle().popMenuSettings().paddingVertical(), 0, 0);
            } else if (i == getCount() - 1) {
                view.setPadding(0, 0, 0, this.popMenu.getStyle().popMenuSettings().paddingVertical());
            } else {
                view.setPadding(0, 0, 0, 0);
            }
        }
        if (this.popMenu.getMenuTextInfo() != null) {
            BaseDialog.useTextInfo(viewHolder.txtDialogxMenuText, this.popMenu.getMenuTextInfo());
        }
        int i3 = this.popMenu.isLightTheme() ? C1903R.C1905color.black90 : C1903R.C1905color.white90;
        viewHolder.txtDialogxMenuText.setTextColor(this.context.getResources().getColor(i3));
        if (this.popMenu.getOnIconChangeCallBack() != null) {
            int icon = this.popMenu.getOnIconChangeCallBack().getIcon(this.popMenu, i, this.menuList.get(i).toString());
            boolean isAutoTintIconInLightOrDarkMode = this.popMenu.getOnIconChangeCallBack().isAutoTintIconInLightOrDarkMode();
            if (icon != 0) {
                viewHolder.imgDialogxMenuIcon.setVisibility(0);
                viewHolder.imgDialogxMenuIcon.setImageResource(icon);
                if ((isHaveProperties(viewHolder.txtDialogxMenuText.getGravity(), 17) || isHaveProperties(viewHolder.txtDialogxMenuText.getGravity(), 1)) && viewHolder.spaceRightPadding != null) {
                    viewHolder.spaceRightPadding.setVisibility(0);
                }
                if (Build.VERSION.SDK_INT >= 21 && isAutoTintIconInLightOrDarkMode) {
                    viewHolder.imgDialogxMenuIcon.setImageTintList(ColorStateList.valueOf(this.context.getResources().getColor(i3)));
                }
            } else {
                viewHolder.imgDialogxMenuIcon.setVisibility(8);
                if (viewHolder.spaceRightPadding != null) {
                    viewHolder.spaceRightPadding.setVisibility(8);
                }
            }
        } else {
            viewHolder.imgDialogxMenuIcon.setVisibility(8);
            if (viewHolder.spaceRightPadding != null) {
                viewHolder.spaceRightPadding.setVisibility(8);
            }
        }
        return view;
    }

    class ViewHolder {
        ImageView imgDialogxMenuIcon;
        Space spaceRightPadding;
        TextView txtDialogxMenuText;

        ViewHolder() {
        }
    }
}
