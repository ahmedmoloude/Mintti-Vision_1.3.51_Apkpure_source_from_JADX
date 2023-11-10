package com.kongzue.dialogx.dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.dialogs.BottomDialog;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.BottomMenuListViewTouchEvent;
import com.kongzue.dialogx.interfaces.DialogLifecycleCallback;
import com.kongzue.dialogx.interfaces.DialogXAnimInterface;
import com.kongzue.dialogx.interfaces.DialogXStyle;
import com.kongzue.dialogx.interfaces.MenuItemTextInfoInterceptor;
import com.kongzue.dialogx.interfaces.OnBackPressedListener;
import com.kongzue.dialogx.interfaces.OnBackgroundMaskClickListener;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack;
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener;
import com.kongzue.dialogx.interfaces.OnMenuItemSelectListener;
import com.kongzue.dialogx.util.BottomMenuArrayAdapter;
import com.kongzue.dialogx.util.TextInfo;
import com.kongzue.dialogx.util.views.BottomDialogListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BottomMenu extends BottomDialog {
    public static final int ITEM_CLICK_DELAY = 100;
    /* access modifiers changed from: private */
    public long lastClickTime = 0;
    /* access modifiers changed from: private */
    public BottomDialogListView listView;

    /* renamed from: me */
    protected BottomMenu f927me = this;
    private MenuItemTextInfoInterceptor<BottomMenu> menuItemTextInfoInterceptor;
    /* access modifiers changed from: private */
    public List<CharSequence> menuList;
    /* access modifiers changed from: private */
    public BaseAdapter menuListAdapter;
    private OnIconChangeCallBack<BottomMenu> onIconChangeCallBack;
    protected OnMenuItemClickListener<BottomMenu> onMenuItemClickListener;
    protected SELECT_MODE selectMode = SELECT_MODE.NONE;
    protected int selectionIndex = -1;
    protected ArrayList<Integer> selectionItems;
    /* access modifiers changed from: private */
    public float touchDownY;

    public enum SELECT_MODE {
        NONE,
        SINGLE,
        MULTIPLE
    }

    public static BottomMenu build() {
        return new BottomMenu();
    }

    public static BottomMenu build(DialogXStyle dialogXStyle) {
        return new BottomMenu().setStyle(dialogXStyle);
    }

    public static BottomMenu build(OnBindView<BottomDialog> onBindView) {
        return new BottomMenu().setCustomView((OnBindView) onBindView);
    }

    protected BottomMenu() {
    }

    public static BottomMenu show(List<CharSequence> list) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.setMenuList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(List<CharSequence> list, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.setMenuList(list);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu showStringList(List<String> list) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.setMenuStringList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu showStringList(List<String> list, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.setMenuStringList(list);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(String[] strArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.setMenuList(strArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(String[] strArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.setMenuList(strArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence[] charSequenceArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence[] charSequenceArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, CharSequence charSequence2, List<CharSequence> list) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.message = charSequence2;
        bottomMenu.setMenuList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, CharSequence charSequence2, List<CharSequence> list, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.message = charSequence2;
        bottomMenu.setMenuList(list);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, List<CharSequence> list) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.setMenuList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, List<CharSequence> list, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.setMenuList(list);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu showStringList(CharSequence charSequence, CharSequence charSequence2, List<String> list) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.message = charSequence2;
        bottomMenu.setMenuStringList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu showStringList(CharSequence charSequence, CharSequence charSequence2, List<String> list, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.message = charSequence2;
        bottomMenu.setMenuStringList(list);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, CharSequence charSequence2, String[] strArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.message = charSequence2;
        bottomMenu.setMenuList(strArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, CharSequence charSequence2, String[] strArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.message = charSequence2;
        bottomMenu.setMenuList(strArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, CharSequence charSequence2, CharSequence[] charSequenceArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.message = charSequence2;
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, CharSequence charSequence2, CharSequence[] charSequenceArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.message = charSequence2;
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(String str, String str2, List<CharSequence> list) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = str;
        bottomMenu.message = str2;
        bottomMenu.setMenuList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(String str, String str2, List<CharSequence> list, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = str;
        bottomMenu.message = str2;
        bottomMenu.setMenuList(list);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu showStringList(String str, String str2, List<String> list) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = str;
        bottomMenu.message = str2;
        bottomMenu.setMenuStringList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu showStringList(String str, String str2, List<String> list, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = str;
        bottomMenu.message = str2;
        bottomMenu.setMenuStringList(list);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(String str, String str2, String[] strArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = str;
        bottomMenu.message = str2;
        bottomMenu.setMenuList(strArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(String str, String str2, String[] strArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = str;
        bottomMenu.message = str2;
        bottomMenu.setMenuList(strArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(String str, String str2, CharSequence[] charSequenceArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = str;
        bottomMenu.message = str2;
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(String str, String str2, CharSequence[] charSequenceArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = str;
        bottomMenu.message = str2;
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, int i2, List<CharSequence> list) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.message = bottomMenu.getString(i2);
        bottomMenu.setMenuList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, List<CharSequence> list) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.setMenuList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu showStringList(int i, int i2, List<String> list) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.message = bottomMenu.getString(i2);
        bottomMenu.setMenuStringList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, int i2, String[] strArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.message = bottomMenu.getString(i2);
        bottomMenu.setMenuList(strArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, int i2, CharSequence[] charSequenceArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.message = bottomMenu.getString(i2);
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, int i2, List<CharSequence> list, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.message = bottomMenu.getString(i2);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.setMenuList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, List<CharSequence> list, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.setMenuList(list);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu showStringList(int i, int i2, List<String> list, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.message = bottomMenu.getString(i2);
        bottomMenu.setMenuStringList(list);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, int i2, String[] strArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.message = bottomMenu.getString(i2);
        bottomMenu.setMenuList(strArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, int i2, CharSequence[] charSequenceArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.message = bottomMenu.getString(i2);
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, CharSequence[] charSequenceArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, String[] strArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.setMenuList(strArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, CharSequence[] charSequenceArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(CharSequence charSequence, String[] strArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = charSequence;
        bottomMenu.setMenuList(strArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, CharSequence[] charSequenceArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, String[] strArr) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.setMenuList(strArr);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, CharSequence[] charSequenceArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.setMenuList(charSequenceArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    public static BottomMenu show(int i, String[] strArr, OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        BottomMenu bottomMenu = new BottomMenu();
        bottomMenu.title = bottomMenu.getString(i);
        bottomMenu.setMenuList(strArr);
        bottomMenu.setOnMenuItemClickListener(onMenuItemClickListener2);
        bottomMenu.show();
        return bottomMenu;
    }

    /* access modifiers changed from: protected */
    public void onDialogShow() {
        int i;
        int i2;
        if (getDialogImpl() != null) {
            getDialogImpl().boxList.setVisibility(0);
            if (!isAllowInterceptTouch()) {
                getDialogImpl().bkg.setMaxHeight((int) this.bottomDialogMaxHeight);
                if (this.bottomDialogMaxHeight != 0.0f) {
                    this.dialogImpl.scrollView.lockScroll(true);
                }
            }
            if (this.style.overrideBottomDialogRes() != null) {
                i2 = this.style.overrideBottomDialogRes().overrideMenuDividerDrawableRes(isLightTheme());
                i = this.style.overrideBottomDialogRes().overrideMenuDividerHeight(isLightTheme());
            } else {
                i2 = 0;
                i = 1;
            }
            if (i2 == 0) {
                i2 = isLightTheme() ? C1903R.C1906drawable.rect_dialogx_material_menu_split_divider : C1903R.C1906drawable.rect_dialogx_material_menu_split_divider_night;
            }
            if (!isLightTheme()) {
                this.listView = new BottomDialogListView(getDialogImpl(), (Context) getTopActivity(), C1903R.C1909style.DialogXCompatThemeDark);
            } else {
                this.listView = new BottomDialogListView(getDialogImpl(), (Context) getTopActivity());
            }
            this.listView.setOverScrollMode(2);
            this.listView.setDivider(getResources().getDrawable(i2));
            this.listView.setDividerHeight(i);
            this.listView.setBottomMenuListViewTouchEvent(new BottomMenuListViewTouchEvent() {
                public void down(MotionEvent motionEvent) {
                    BottomMenu bottomMenu = BottomMenu.this;
                    float unused = bottomMenu.touchDownY = bottomMenu.getDialogImpl().bkg.getY();
                }
            });
            this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - BottomMenu.this.lastClickTime > 100) {
                        long unused = BottomMenu.this.lastClickTime = currentTimeMillis;
                        if (Math.abs(BottomMenu.this.touchDownY - BottomMenu.this.getDialogImpl().bkg.getY()) <= ((float) BottomMenu.this.dip2px(15.0f))) {
                            int i2 = C19365.$SwitchMap$com$kongzue$dialogx$dialogs$BottomMenu$SELECT_MODE[BottomMenu.this.selectMode.ordinal()];
                            if (i2 != 1) {
                                if (i2 != 2) {
                                    if (i2 == 3) {
                                        if (BottomMenu.this.onMenuItemClickListener instanceof OnMenuItemSelectListener) {
                                            OnMenuItemSelectListener onMenuItemSelectListener = (OnMenuItemSelectListener) BottomMenu.this.onMenuItemClickListener;
                                            if (!onMenuItemSelectListener.onClick(BottomMenu.this.f927me, (CharSequence) BottomMenu.this.menuList.get(i), i)) {
                                                BottomMenu.this.dismiss();
                                                return;
                                            }
                                            if (BottomMenu.this.selectionItems.contains(Integer.valueOf(i))) {
                                                BottomMenu.this.selectionItems.remove(new Integer(i));
                                            } else {
                                                BottomMenu.this.selectionItems.add(Integer.valueOf(i));
                                            }
                                            BottomMenu.this.menuListAdapter.notifyDataSetInvalidated();
                                            int[] iArr = new int[BottomMenu.this.selectionItems.size()];
                                            CharSequence[] charSequenceArr = new CharSequence[BottomMenu.this.selectionItems.size()];
                                            for (int i3 = 0; i3 < BottomMenu.this.selectionItems.size(); i3++) {
                                                iArr[i3] = BottomMenu.this.selectionItems.get(i3).intValue();
                                                charSequenceArr[i3] = (CharSequence) BottomMenu.this.menuList.get(iArr[i3]);
                                            }
                                            onMenuItemSelectListener.onMultiItemSelect(BottomMenu.this.f927me, charSequenceArr, iArr);
                                        } else if (BottomMenu.this.onMenuItemClickListener == null) {
                                            BottomMenu.this.dismiss();
                                        } else if (!BottomMenu.this.onMenuItemClickListener.onClick(BottomMenu.this.f927me, (CharSequence) BottomMenu.this.menuList.get(i), i)) {
                                            BottomMenu.this.dismiss();
                                        }
                                    }
                                } else if (BottomMenu.this.onMenuItemClickListener instanceof OnMenuItemSelectListener) {
                                    OnMenuItemSelectListener onMenuItemSelectListener2 = (OnMenuItemSelectListener) BottomMenu.this.onMenuItemClickListener;
                                    if (!onMenuItemSelectListener2.onClick(BottomMenu.this.f927me, (CharSequence) BottomMenu.this.menuList.get(i), i)) {
                                        BottomMenu.this.dismiss();
                                        return;
                                    }
                                    BottomMenu.this.selectionIndex = i;
                                    BottomMenu.this.menuListAdapter.notifyDataSetInvalidated();
                                    onMenuItemSelectListener2.onOneItemSelect(BottomMenu.this.f927me, (CharSequence) BottomMenu.this.menuList.get(i), i, true);
                                } else if (BottomMenu.this.onMenuItemClickListener == null) {
                                    BottomMenu.this.dismiss();
                                } else if (!BottomMenu.this.onMenuItemClickListener.onClick(BottomMenu.this.f927me, (CharSequence) BottomMenu.this.menuList.get(i), i)) {
                                    BottomMenu.this.dismiss();
                                }
                            } else if (BottomMenu.this.onMenuItemClickListener == null) {
                                BottomMenu.this.dismiss();
                            } else if (!BottomMenu.this.onMenuItemClickListener.onClick(BottomMenu.this.f927me, (CharSequence) BottomMenu.this.menuList.get(i), i)) {
                                BottomMenu.this.dismiss();
                            }
                        }
                    }
                }
            });
            if (!(this.style.overrideBottomDialogRes() == null || this.style.overrideBottomDialogRes().overrideMenuItemLayout(true, 0, 0, false) == 0)) {
                this.listView.setSelector(C1903R.C1905color.empty);
            }
            getDialogImpl().boxList.addView(this.listView, new RelativeLayout.LayoutParams(-1, -2));
            refreshUI();
            this.listView.post(new Runnable() {
                public void run() {
                    if (BottomMenu.this.menuListAdapter instanceof BottomMenuArrayAdapter) {
                        BottomMenuArrayAdapter bottomMenuArrayAdapter = (BottomMenuArrayAdapter) BottomMenu.this.menuListAdapter;
                        final View childAt = BottomMenu.this.listView.getChildAt(BottomMenu.this.getSelection());
                        if (childAt != null) {
                            childAt.post(new Runnable() {
                                public void run() {
                                    childAt.setPressed(true);
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    /* renamed from: com.kongzue.dialogx.dialogs.BottomMenu$5 */
    static /* synthetic */ class C19365 {
        static final /* synthetic */ int[] $SwitchMap$com$kongzue$dialogx$dialogs$BottomMenu$SELECT_MODE;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.kongzue.dialogx.dialogs.BottomMenu$SELECT_MODE[] r0 = com.kongzue.dialogx.dialogs.BottomMenu.SELECT_MODE.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$kongzue$dialogx$dialogs$BottomMenu$SELECT_MODE = r0
                com.kongzue.dialogx.dialogs.BottomMenu$SELECT_MODE r1 = com.kongzue.dialogx.dialogs.BottomMenu.SELECT_MODE.NONE     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$kongzue$dialogx$dialogs$BottomMenu$SELECT_MODE     // Catch:{ NoSuchFieldError -> 0x001d }
                com.kongzue.dialogx.dialogs.BottomMenu$SELECT_MODE r1 = com.kongzue.dialogx.dialogs.BottomMenu.SELECT_MODE.SINGLE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$kongzue$dialogx$dialogs$BottomMenu$SELECT_MODE     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.kongzue.dialogx.dialogs.BottomMenu$SELECT_MODE r1 = com.kongzue.dialogx.dialogs.BottomMenu.SELECT_MODE.MULTIPLE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.dialogs.BottomMenu.C19365.<clinit>():void");
        }
    }

    public void refreshUI() {
        if (getDialogImpl() != null) {
            if (this.listView != null) {
                if (this.menuListAdapter == null) {
                    this.menuListAdapter = new BottomMenuArrayAdapter(this.f927me, getTopActivity(), this.menuList);
                }
                if (this.listView.getAdapter() == null) {
                    this.listView.setAdapter((ListAdapter) this.menuListAdapter);
                } else {
                    ListAdapter adapter = this.listView.getAdapter();
                    BaseAdapter baseAdapter = this.menuListAdapter;
                    if (adapter != baseAdapter) {
                        this.listView.setAdapter((ListAdapter) baseAdapter);
                    } else {
                        baseAdapter.notifyDataSetChanged();
                    }
                }
            }
            super.refreshUI();
        }
    }

    public void preRefreshUI() {
        if (getDialogImpl() != null) {
            runOnMain(new Runnable() {
                public void run() {
                    BottomMenu.this.refreshUI();
                }
            });
        }
    }

    public String dialogKey() {
        return getClass().getSimpleName() + "(" + Integer.toHexString(hashCode()) + ")";
    }

    public List<CharSequence> getMenuList() {
        return this.menuList;
    }

    public BottomMenu setMenuList(List<CharSequence> list) {
        this.menuList = list;
        this.menuListAdapter = null;
        preRefreshUI();
        return this;
    }

    private boolean isSameSize(int i) {
        List<CharSequence> list = this.menuList;
        if (list == null || list.size() == 0 || this.menuList.size() == i) {
            return true;
        }
        return false;
    }

    public BottomMenu setMenuStringList(List<String> list) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(list);
        this.menuListAdapter = null;
        preRefreshUI();
        return this;
    }

    public BottomMenu setMenuList(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(Arrays.asList(strArr));
        this.menuListAdapter = null;
        preRefreshUI();
        return this;
    }

    public BottomMenu setMenuList(CharSequence[] charSequenceArr) {
        this.menuList = Arrays.asList(charSequenceArr);
        this.menuListAdapter = null;
        preRefreshUI();
        return this;
    }

    public OnIconChangeCallBack<BottomMenu> getOnIconChangeCallBack() {
        return this.onIconChangeCallBack;
    }

    public BottomMenu setOnIconChangeCallBack(OnIconChangeCallBack<BottomMenu> onIconChangeCallBack2) {
        this.onIconChangeCallBack = onIconChangeCallBack2;
        return this;
    }

    public OnBackPressedListener<BottomDialog> getOnBackPressedListener() {
        return this.onBackPressedListener;
    }

    public BottomMenu setOnBackPressedListener(OnBackPressedListener<BottomDialog> onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
        preRefreshUI();
        return this;
    }

    public BottomMenu setDialogLifecycleCallback(DialogLifecycleCallback<BottomDialog> dialogLifecycleCallback) {
        this.dialogLifecycleCallback = dialogLifecycleCallback;
        if (this.isShow) {
            dialogLifecycleCallback.onShow(this.f927me);
        }
        return this;
    }

    public BottomMenu setStyle(DialogXStyle dialogXStyle) {
        this.style = dialogXStyle;
        return this;
    }

    public BottomMenu setTheme(DialogX.THEME theme) {
        this.theme = theme;
        return this;
    }

    public boolean isCancelable() {
        if (this.privateCancelable != null) {
            if (this.privateCancelable == BaseDialog.BOOLEAN.TRUE) {
                return true;
            }
            return false;
        } else if (overrideCancelable == null) {
            return this.cancelable;
        } else {
            if (overrideCancelable == BaseDialog.BOOLEAN.TRUE) {
                return true;
            }
            return false;
        }
    }

    public BottomMenu setCancelable(boolean z) {
        this.privateCancelable = z ? BaseDialog.BOOLEAN.TRUE : BaseDialog.BOOLEAN.FALSE;
        preRefreshUI();
        return this;
    }

    public BottomDialog.DialogImpl getDialogImpl() {
        return this.dialogImpl;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public BottomMenu setTitle(CharSequence charSequence) {
        this.title = charSequence;
        preRefreshUI();
        return this;
    }

    public BottomMenu setTitle(int i) {
        this.title = getString(i);
        preRefreshUI();
        return this;
    }

    public CharSequence getMessage() {
        return this.message;
    }

    public BottomMenu setMessage(CharSequence charSequence) {
        this.message = charSequence;
        preRefreshUI();
        return this;
    }

    public BottomMenu setMessage(int i) {
        this.message = getString(i);
        preRefreshUI();
        return this;
    }

    public CharSequence getCancelButton() {
        return this.cancelText;
    }

    public BottomMenu setCancelButton(CharSequence charSequence) {
        this.cancelText = charSequence;
        preRefreshUI();
        return this;
    }

    public BottomMenu setCancelButton(int i) {
        this.cancelText = getString(i);
        preRefreshUI();
        return this;
    }

    public BottomMenu setCancelButton(OnDialogButtonClickListener onDialogButtonClickListener) {
        this.cancelButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public BottomMenu setCancelButton(CharSequence charSequence, OnDialogButtonClickListener onDialogButtonClickListener) {
        this.cancelText = charSequence;
        this.cancelButtonClickListener = onDialogButtonClickListener;
        preRefreshUI();
        return this;
    }

    public BottomMenu setCancelButton(int i, OnDialogButtonClickListener onDialogButtonClickListener) {
        this.cancelText = getString(i);
        this.cancelButtonClickListener = onDialogButtonClickListener;
        preRefreshUI();
        return this;
    }

    public BottomMenu setCustomView(OnBindView<BottomDialog> onBindView) {
        this.onBindView = onBindView;
        preRefreshUI();
        return this;
    }

    public View getCustomView() {
        if (this.onBindView == null) {
            return null;
        }
        return this.onBindView.getCustomView();
    }

    public BottomMenu removeCustomView() {
        this.onBindView.clean();
        preRefreshUI();
        return this;
    }

    public boolean isAllowInterceptTouch() {
        return super.isAllowInterceptTouch();
    }

    public BottomMenu setAllowInterceptTouch(boolean z) {
        this.allowInterceptTouch = z;
        preRefreshUI();
        return this;
    }

    public float getBottomDialogMaxHeight() {
        return this.bottomDialogMaxHeight;
    }

    public BottomMenu setBottomDialogMaxHeight(float f) {
        this.bottomDialogMaxHeight = f;
        return this;
    }

    public OnMenuItemClickListener<BottomMenu> getOnMenuItemClickListener() {
        return this.onMenuItemClickListener;
    }

    public BottomMenu setOnMenuItemClickListener(OnMenuItemClickListener<BottomMenu> onMenuItemClickListener2) {
        this.onMenuItemClickListener = onMenuItemClickListener2;
        return this;
    }

    public BaseAdapter getMenuListAdapter() {
        return this.menuListAdapter;
    }

    public BottomMenu setMenuListAdapter(BaseAdapter baseAdapter) {
        this.menuListAdapter = baseAdapter;
        return this;
    }

    public OnDialogButtonClickListener getCancelButtonClickListener() {
        return this.cancelButtonClickListener;
    }

    public BottomMenu setCancelButtonClickListener(OnDialogButtonClickListener onDialogButtonClickListener) {
        this.cancelButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public TextInfo getTitleTextInfo() {
        return this.titleTextInfo;
    }

    public BottomMenu setTitleTextInfo(TextInfo textInfo) {
        this.titleTextInfo = textInfo;
        preRefreshUI();
        return this;
    }

    public TextInfo getMessageTextInfo() {
        return this.messageTextInfo;
    }

    public BottomMenu setMessageTextInfo(TextInfo textInfo) {
        this.messageTextInfo = textInfo;
        preRefreshUI();
        return this;
    }

    public TextInfo getCancelTextInfo() {
        return this.cancelTextInfo;
    }

    public BottomMenu setCancelTextInfo(TextInfo textInfo) {
        this.cancelTextInfo = textInfo;
        preRefreshUI();
        return this;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public BottomMenu setBackgroundColor(int i) {
        this.backgroundColor = i;
        preRefreshUI();
        return this;
    }

    public int getSelection() {
        return this.selectionIndex;
    }

    public ArrayList<Integer> getSelectionList() {
        return this.selectionItems;
    }

    public BottomMenu setSelection(int i) {
        this.selectMode = SELECT_MODE.SINGLE;
        this.selectionIndex = i;
        this.selectionItems = null;
        this.menuListAdapter = null;
        preRefreshUI();
        return this;
    }

    public BottomMenu setSingleSelection() {
        this.selectMode = SELECT_MODE.SINGLE;
        this.selectionIndex = -1;
        this.selectionItems = null;
        this.menuListAdapter = null;
        preRefreshUI();
        return this;
    }

    public BottomMenu setSelection(int[] iArr) {
        this.selectMode = SELECT_MODE.MULTIPLE;
        this.selectionIndex = -1;
        this.selectionItems = new ArrayList<>();
        if (iArr != null) {
            for (int valueOf : iArr) {
                this.selectionItems.add(Integer.valueOf(valueOf));
            }
        }
        this.menuListAdapter = null;
        preRefreshUI();
        return this;
    }

    public BottomMenu setMultiSelection() {
        this.selectMode = SELECT_MODE.MULTIPLE;
        this.selectionIndex = -1;
        this.selectionItems = new ArrayList<>();
        this.menuListAdapter = null;
        preRefreshUI();
        return this;
    }

    public BottomMenu setSelection(List<Integer> list) {
        this.selectMode = SELECT_MODE.MULTIPLE;
        this.selectionIndex = -1;
        this.selectionItems = new ArrayList<>(list);
        this.menuListAdapter = null;
        preRefreshUI();
        return this;
    }

    public BottomMenu setNoSelect() {
        this.selectMode = SELECT_MODE.NONE;
        this.selectionIndex = -1;
        this.selectionItems = null;
        this.menuListAdapter = null;
        preRefreshUI();
        return this;
    }

    public BottomMenu setBackgroundColorRes(int i) {
        this.backgroundColor = getColor(i);
        preRefreshUI();
        return this;
    }

    public CharSequence getOkButton() {
        return this.okText;
    }

    public BottomMenu setOkButton(CharSequence charSequence) {
        this.okText = charSequence;
        preRefreshUI();
        return this;
    }

    public BottomMenu setOkButton(int i) {
        this.okText = getString(i);
        preRefreshUI();
        return this;
    }

    public BottomMenu setOkButton(OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.okButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public BottomMenu setOkButton(CharSequence charSequence, OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.okText = charSequence;
        this.okButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public BottomMenu setOkButton(int i, OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.okText = getString(i);
        this.okButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public CharSequence getOtherButton() {
        return this.otherText;
    }

    public BottomMenu setOtherButton(CharSequence charSequence) {
        this.otherText = charSequence;
        preRefreshUI();
        return this;
    }

    public BottomMenu setOtherButton(int i) {
        this.otherText = getString(i);
        preRefreshUI();
        return this;
    }

    public BottomMenu setOtherButton(OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.otherButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public BottomMenu setOtherButton(CharSequence charSequence, OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.otherText = charSequence;
        this.otherButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public BottomMenu setOtherButton(int i, OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.otherText = getString(i);
        this.otherButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public BottomMenu setMaskColor(int i) {
        this.maskColor = i;
        preRefreshUI();
        return this;
    }

    public long getEnterAnimDuration() {
        return this.enterAnimDuration;
    }

    public BottomMenu setEnterAnimDuration(long j) {
        this.enterAnimDuration = j;
        return this;
    }

    public long getExitAnimDuration() {
        return this.exitAnimDuration;
    }

    public BottomMenu setExitAnimDuration(long j) {
        this.exitAnimDuration = j;
        return this;
    }

    public SELECT_MODE getSelectMode() {
        return this.selectMode;
    }

    /* access modifiers changed from: protected */
    public void shutdown() {
        dismiss();
    }

    public BottomMenu setMaxWidth(int i) {
        this.maxWidth = i;
        refreshUI();
        return this;
    }

    public BottomMenu setDialogImplMode(DialogX.IMPL_MODE impl_mode) {
        this.dialogImplMode = impl_mode;
        return this;
    }

    public TextInfo getMenuTextInfo() {
        if (this.menuTextInfo == null) {
            return DialogX.menuTextInfo;
        }
        return this.menuTextInfo;
    }

    public BottomMenu setMenuTextInfo(TextInfo textInfo) {
        this.menuTextInfo = textInfo;
        return this;
    }

    public MenuItemTextInfoInterceptor<BottomMenu> getMenuItemTextInfoInterceptor() {
        return this.menuItemTextInfoInterceptor;
    }

    public BottomMenu setMenuItemTextInfoInterceptor(MenuItemTextInfoInterceptor<BottomMenu> menuItemTextInfoInterceptor2) {
        this.menuItemTextInfoInterceptor = menuItemTextInfoInterceptor2;
        return this;
    }

    public boolean isBkgInterceptTouch() {
        return this.bkgInterceptTouch;
    }

    public BottomMenu setBkgInterceptTouch(boolean z) {
        this.bkgInterceptTouch = z;
        return this;
    }

    public OnBackgroundMaskClickListener<BottomDialog> getOnBackgroundMaskClickListener() {
        return this.onBackgroundMaskClickListener;
    }

    public BottomMenu setOnBackgroundMaskClickListener(OnBackgroundMaskClickListener<BottomDialog> onBackgroundMaskClickListener) {
        this.onBackgroundMaskClickListener = onBackgroundMaskClickListener;
        return this;
    }

    public BottomMenu setRadius(float f) {
        this.backgroundRadius = f;
        refreshUI();
        return this;
    }

    public float getRadius() {
        return this.backgroundRadius;
    }

    public BottomMenu setTitleIcon(Bitmap bitmap) {
        this.titleIcon = new BitmapDrawable(getResources(), bitmap);
        refreshUI();
        return this;
    }

    public BottomMenu setTitleIcon(int i) {
        this.titleIcon = getResources().getDrawable(i);
        refreshUI();
        return this;
    }

    public BottomMenu setTitleIcon(Drawable drawable) {
        this.titleIcon = drawable;
        refreshUI();
        return this;
    }

    public DialogXAnimInterface<BottomDialog> getDialogXAnimImpl() {
        return this.dialogXAnimImpl;
    }

    public BottomMenu setDialogXAnimImpl(DialogXAnimInterface<BottomDialog> dialogXAnimInterface) {
        this.dialogXAnimImpl = dialogXAnimInterface;
        return this;
    }
}
