package com.kongzue.dialogx.dialogs;

import android.animation.ValueAnimator;
import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import androidx.lifecycle.Lifecycle;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.DialogConvertViewInterface;
import com.kongzue.dialogx.interfaces.DialogLifecycleCallback;
import com.kongzue.dialogx.interfaces.DialogXAnimInterface;
import com.kongzue.dialogx.interfaces.DialogXStyle;
import com.kongzue.dialogx.interfaces.OnBackPressedListener;
import com.kongzue.dialogx.interfaces.OnBackgroundMaskClickListener;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack;
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener;
import com.kongzue.dialogx.util.ObjectRunnable;
import com.kongzue.dialogx.util.PopMenuArrayAdapter;
import com.kongzue.dialogx.util.TextInfo;
import com.kongzue.dialogx.util.views.BlurView;
import com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout;
import com.kongzue.dialogx.util.views.MaxRelativeLayout;
import com.kongzue.dialogx.util.views.PopMenuListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopMenu extends BaseDialog {
    public static long overrideEnterDuration = -1;
    public static long overrideExitDuration = -1;
    protected int alignGravity = -1;
    protected float backgroundRadius = -1.0f;
    protected View baseView;
    protected int[] baseViewLoc = new int[2];
    protected boolean bkgInterceptTouch = true;
    /* access modifiers changed from: private */
    public boolean closing;
    protected DialogImpl dialogImpl;
    protected DialogLifecycleCallback<PopMenu> dialogLifecycleCallback;
    protected View dialogView;
    protected DialogXAnimInterface<PopMenu> dialogXAnimImpl;
    protected int height = -1;
    protected boolean hideWithExitAnim;
    private boolean isHide;

    /* renamed from: me */
    protected PopMenu f932me = this;
    protected List<CharSequence> menuList;
    protected PopMenuArrayAdapter menuListAdapter;
    protected TextInfo menuTextInfo;
    protected boolean offScreen = false;
    protected OnBackPressedListener<PopMenu> onBackPressedListener;
    protected OnBackgroundMaskClickListener<PopMenu> onBackgroundMaskClickListener;
    protected OnBindView<PopMenu> onBindView;
    protected OnIconChangeCallBack<PopMenu> onIconChangeCallBack;
    protected OnMenuItemClickListener<PopMenu> onMenuItemClickListener;
    protected boolean overlayBaseView = true;
    protected int width = -1;

    public boolean isCancelable() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void shutdown() {
    }

    public PopMenu() {
    }

    public PopMenu(View view, List<CharSequence> list) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(list);
        this.baseView = view;
    }

    public PopMenu(View view, CharSequence[] charSequenceArr) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(Arrays.asList(charSequenceArr));
        this.baseView = view;
    }

    public PopMenu(List<CharSequence> list) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(list);
    }

    public PopMenu(CharSequence[] charSequenceArr) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(Arrays.asList(charSequenceArr));
    }

    public PopMenu(OnBindView<PopMenu> onBindView2) {
        this.onBindView = onBindView2;
    }

    public PopMenu(View view, OnBindView<PopMenu> onBindView2) {
        this.baseView = view;
        this.onBindView = onBindView2;
    }

    public PopMenu(View view, List<CharSequence> list, OnBindView<PopMenu> onBindView2) {
        this.baseView = view;
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(list);
        this.onBindView = onBindView2;
    }

    public PopMenu(View view, CharSequence[] charSequenceArr, OnBindView<PopMenu> onBindView2) {
        this.baseView = view;
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(Arrays.asList(charSequenceArr));
        this.onBindView = onBindView2;
    }

    public PopMenu(List<CharSequence> list, OnBindView<PopMenu> onBindView2) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(list);
        this.onBindView = onBindView2;
    }

    public PopMenu(CharSequence[] charSequenceArr, OnBindView<PopMenu> onBindView2) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(Arrays.asList(charSequenceArr));
        this.onBindView = onBindView2;
    }

    public static PopMenu build() {
        return new PopMenu();
    }

    public static PopMenu build(DialogXStyle dialogXStyle) {
        return new PopMenu().setStyle(dialogXStyle);
    }

    public static PopMenu show(CharSequence[] charSequenceArr) {
        PopMenu popMenu = new PopMenu(charSequenceArr);
        popMenu.show();
        return popMenu;
    }

    public static PopMenu show(List<CharSequence> list) {
        PopMenu popMenu = new PopMenu(list);
        popMenu.show();
        return popMenu;
    }

    public static PopMenu show(View view, CharSequence[] charSequenceArr) {
        PopMenu popMenu = new PopMenu(view, charSequenceArr);
        popMenu.show();
        return popMenu;
    }

    public static PopMenu show(View view, List<CharSequence> list) {
        PopMenu popMenu = new PopMenu(view, list);
        popMenu.show();
        return popMenu;
    }

    public static PopMenu show(View view, CharSequence[] charSequenceArr, OnBindView<PopMenu> onBindView2) {
        PopMenu popMenu = new PopMenu(view, charSequenceArr, onBindView2);
        popMenu.show();
        return popMenu;
    }

    public static PopMenu show(View view, List<CharSequence> list, OnBindView<PopMenu> onBindView2) {
        PopMenu popMenu = new PopMenu(view, list, onBindView2);
        popMenu.show();
        return popMenu;
    }

    public static PopMenu show(CharSequence[] charSequenceArr, OnBindView<PopMenu> onBindView2) {
        PopMenu popMenu = new PopMenu(charSequenceArr, onBindView2);
        popMenu.show();
        return popMenu;
    }

    public static PopMenu show(List<CharSequence> list, OnBindView<PopMenu> onBindView2) {
        PopMenu popMenu = new PopMenu(list, onBindView2);
        popMenu.show();
        return popMenu;
    }

    public PopMenu show() {
        if (!this.isHide || getDialogView() == null || !this.isShow) {
            super.beforeShow();
            if (getDialogView() == null) {
                int i = isLightTheme() ? C1903R.layout.layout_dialogx_popmenu_material : C1903R.layout.layout_dialogx_popmenu_material_dark;
                if (!(getStyle().popMenuSettings() == null || getStyle().popMenuSettings().layout(isLightTheme()) == 0)) {
                    i = getStyle().popMenuSettings().layout(isLightTheme());
                }
                View createView = createView(i);
                this.dialogView = createView;
                this.dialogImpl = new DialogImpl(createView);
                View view = this.dialogView;
                if (view != null) {
                    view.setTag(this.f932me);
                }
            }
            show(this.dialogView);
            View view2 = this.baseView;
            if (view2 != null) {
                view2.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        if (PopMenu.this.getDialogImpl() != null) {
                            PopMenu.this.baseViewLoc = new int[2];
                            PopMenu.this.baseView.getLocationOnScreen(PopMenu.this.baseViewLoc);
                            int width = PopMenu.this.baseView.getWidth();
                            int height = PopMenu.this.baseView.getHeight();
                            int i = PopMenu.this.baseViewLoc[0];
                            int i2 = PopMenu.this.baseViewLoc[1];
                            if (PopMenu.this.overlayBaseView) {
                                height = 0;
                            }
                            PopMenu.this.getDialogImpl().boxBody.setX((float) i);
                            PopMenu.this.getDialogImpl().boxBody.setY((float) (i2 + height));
                            if (!(width == 0 || PopMenu.this.getDialogImpl().boxBody.getWidth() == width)) {
                                PopMenu.this.getDialogImpl().boxBody.setLayoutParams(new RelativeLayout.LayoutParams(width, -2));
                            }
                            PopMenu.this.baseView.getViewTreeObserver().removeOnPreDrawListener(this);
                        }
                        return true;
                    }
                });
            }
            return this;
        }
        if (!this.hideWithExitAnim || getDialogImpl() == null) {
            getDialogView().setVisibility(0);
        } else {
            getDialogView().setVisibility(0);
            getDialogImpl().getDialogXAnimImpl().doShowAnim(this.f932me, new ObjectRunnable<Float>() {
                public void run(Float f) {
                    PopMenu.this.getDialogImpl().boxRoot.setBkgAlpha(f.floatValue());
                }
            });
        }
        return this;
    }

    public class DialogImpl implements DialogConvertViewInterface {
        /* access modifiers changed from: private */
        public BlurView blurView;
        /* access modifiers changed from: private */
        public MaxRelativeLayout boxBody;
        /* access modifiers changed from: private */
        public RelativeLayout boxCustom;
        /* access modifiers changed from: private */
        public DialogXBaseRelativeLayout boxRoot;
        /* access modifiers changed from: private */
        public PopMenuListView listMenu;

        public DialogImpl(View view) {
            this.boxRoot = (DialogXBaseRelativeLayout) view.findViewById(C1903R.C1907id.box_root);
            this.boxBody = (MaxRelativeLayout) view.findViewById(C1903R.C1907id.box_body);
            this.boxCustom = (RelativeLayout) view.findViewById(C1903R.C1907id.box_custom);
            this.listMenu = (PopMenuListView) view.findViewById(C1903R.C1907id.listMenu);
            init();
        }

        public void init() {
            int i;
            int i2;
            boolean unused = PopMenu.this.closing = false;
            if (PopMenu.this.menuListAdapter == null) {
                PopMenu.this.menuListAdapter = new PopMenuArrayAdapter(PopMenu.this.f932me, BaseDialog.getTopActivity(), PopMenu.this.menuList);
            }
            this.boxRoot.setParentDialog(PopMenu.this.f932me);
            this.boxRoot.setOnLifecycleCallBack(new DialogXBaseRelativeLayout.OnLifecycleCallBack() {
                public void onShow() {
                    boolean unused = PopMenu.this.isShow = true;
                    boolean unused2 = PopMenu.this.preShow = false;
                    PopMenu.this.lifecycle.setCurrentState(Lifecycle.State.CREATED);
                    PopMenu.this.onDialogShow();
                    PopMenu.this.getDialogLifecycleCallback().onShow(PopMenu.this.f932me);
                    PopMenu.this.refreshUI();
                }

                public void onDismiss() {
                    boolean unused = PopMenu.this.isShow = false;
                    PopMenu.this.getDialogLifecycleCallback().onDismiss(PopMenu.this.f932me);
                    PopMenu.this.menuListAdapter = null;
                    PopMenu.this.dialogImpl = null;
                    PopMenu.this.baseView = null;
                    PopMenu.this.dialogLifecycleCallback = null;
                    PopMenu.this.lifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                    System.gc();
                }
            });
            this.boxRoot.setOnBackPressedListener(new DialogXBaseRelativeLayout.PrivateBackPressedListener() {
                public boolean onBackPressed() {
                    if (PopMenu.this.onBackPressedListener != null) {
                        if (!PopMenu.this.onBackPressedListener.onBackPressed(PopMenu.this.f932me)) {
                            return true;
                        }
                        PopMenu.this.dismiss();
                        return true;
                    } else if (!PopMenu.this.isCancelable()) {
                        return true;
                    } else {
                        PopMenu.this.dismiss();
                        return true;
                    }
                }
            });
            this.listMenu.setMaxHeight((float) (BaseDialog.getRootFrameLayout() == null ? PopMenu.this.dip2px(500.0f) : BaseDialog.getRootFrameLayout().getMeasuredHeight() - PopMenu.this.dip2px(150.0f)));
            this.boxBody.setVisibility(4);
            this.boxBody.post(new Runnable() {
                public void run() {
                    DialogImpl.this.getDialogXAnimImpl().doShowAnim(PopMenu.this.f932me, new ObjectRunnable<Float>() {
                        public void run(Float f) {
                            DialogImpl.this.boxRoot.setBkgAlpha(f.floatValue());
                        }
                    });
                    PopMenu.this.lifecycle.setCurrentState(Lifecycle.State.RESUMED);
                }
            });
            if (PopMenu.this.style.popMenuSettings() != null) {
                i2 = PopMenu.this.style.popMenuSettings().overrideMenuDividerDrawableRes(PopMenu.this.isLightTheme());
                i = PopMenu.this.style.popMenuSettings().overrideMenuDividerHeight(PopMenu.this.isLightTheme());
            } else {
                i2 = 0;
                i = 0;
            }
            if (i2 == 0) {
                i2 = PopMenu.this.isLightTheme() ? C1903R.C1906drawable.rect_dialogx_material_menu_split_divider : C1903R.C1906drawable.rect_dialogx_material_menu_split_divider_night;
            }
            this.listMenu.setOverScrollMode(2);
            this.listMenu.setVerticalScrollBarEnabled(false);
            this.listMenu.setDivider(PopMenu.this.getResources().getDrawable(i2));
            this.listMenu.setDividerHeight(i);
            this.listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    if (!PopMenu.this.closing && !PopMenu.this.getOnMenuItemClickListener().onClick(PopMenu.this.f932me, PopMenu.this.menuList.get(i), i)) {
                        PopMenu.this.dismiss();
                    }
                }
            });
            PopMenu.this.onDialogInit();
        }

        public void refreshView() {
            if (this.boxRoot != null && BaseDialog.getTopActivity() != null) {
                if (this.listMenu.getAdapter() == null) {
                    this.listMenu.setAdapter(PopMenu.this.menuListAdapter);
                } else if (PopMenu.this.menuListAdapter.getMenuList() != PopMenu.this.menuList) {
                    PopMenu.this.menuListAdapter = new PopMenuArrayAdapter(PopMenu.this.f932me, BaseDialog.getTopActivity(), PopMenu.this.menuList);
                    this.listMenu.setAdapter(PopMenu.this.menuListAdapter);
                } else {
                    PopMenu.this.menuListAdapter.notifyDataSetChanged();
                }
                if (!PopMenu.this.bkgInterceptTouch) {
                    this.boxRoot.setClickable(false);
                } else if (PopMenu.this.isCancelable()) {
                    this.boxRoot.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            if (PopMenu.this.onBackgroundMaskClickListener == null || !PopMenu.this.onBackgroundMaskClickListener.onClick(PopMenu.this.f932me, view)) {
                                DialogImpl.this.doDismiss(view);
                            }
                        }
                    });
                } else {
                    this.boxRoot.setOnClickListener((View.OnClickListener) null);
                }
                if (PopMenu.this.backgroundRadius > -1.0f) {
                    GradientDrawable gradientDrawable = (GradientDrawable) this.boxBody.getBackground();
                    if (gradientDrawable != null) {
                        gradientDrawable.setCornerRadius(PopMenu.this.backgroundRadius);
                    }
                    if (Build.VERSION.SDK_INT >= 21) {
                        this.boxBody.setOutlineProvider(new ViewOutlineProvider() {
                            public void getOutline(View view, Outline outline) {
                                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), PopMenu.this.backgroundRadius);
                            }
                        });
                        this.boxBody.setClipToOutline(true);
                    }
                }
                if (PopMenu.this.onBindView == null || PopMenu.this.onBindView.getCustomView() == null) {
                    this.boxCustom.setVisibility(8);
                } else {
                    PopMenu.this.onBindView.bindParent(this.boxCustom, PopMenu.this.f932me);
                    this.boxCustom.setVisibility(0);
                }
                if (PopMenu.this.width != -1) {
                    this.boxBody.setMaxWidth(PopMenu.this.width);
                    this.boxBody.setMinimumWidth(PopMenu.this.width);
                }
                if (PopMenu.this.height != -1) {
                    this.boxBody.setMaxHeight(PopMenu.this.height);
                    this.boxBody.setMinimumHeight(PopMenu.this.height);
                }
                PopMenu.this.onDialogRefreshUI();
            }
        }

        public void doDismiss(View view) {
            if (view != null) {
                view.setEnabled(false);
            }
            if (!PopMenu.this.dismissAnimFlag) {
                boolean unused = PopMenu.this.dismissAnimFlag = true;
                this.boxRoot.post(new Runnable() {
                    public void run() {
                        DialogImpl.this.getDialogXAnimImpl().doExitAnim(PopMenu.this.f932me, new ObjectRunnable<Float>() {
                            public void run(Float f) {
                                if (DialogImpl.this.boxRoot != null && PopMenu.this.baseView == null) {
                                    DialogImpl.this.boxRoot.setBkgAlpha(f.floatValue());
                                }
                                if (f.floatValue() == 0.0f) {
                                    PopMenu.dismiss(PopMenu.this.dialogView);
                                }
                            }
                        });
                    }
                });
            }
        }

        /* access modifiers changed from: protected */
        public DialogXAnimInterface<PopMenu> getDialogXAnimImpl() {
            if (PopMenu.this.dialogXAnimImpl == null) {
                PopMenu.this.dialogXAnimImpl = new DialogXAnimInterface<PopMenu>() {
                    int selectMenuIndex = -1;

                    /* JADX WARNING: Code restructure failed: missing block: B:63:0x022f, code lost:
                        if (r13.this$1.this$0.isAlignGravity(80) != false) goto L_0x029b;
                     */
                    /* JADX WARNING: Removed duplicated region for block: B:79:0x02a3  */
                    /* JADX WARNING: Removed duplicated region for block: B:90:0x0305  */
                    /* JADX WARNING: Removed duplicated region for block: B:92:0x0311  */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void doShowAnim(com.kongzue.dialogx.dialogs.PopMenu r14, final com.kongzue.dialogx.util.ObjectRunnable<java.lang.Float> r15) {
                        /*
                            r13 = this;
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r14 = com.kongzue.dialogx.dialogs.PopMenu.this
                            long r0 = r14.enterAnimDuration
                            r2 = -1
                            int r14 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                            if (r14 == 0) goto L_0x0017
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r14 = com.kongzue.dialogx.dialogs.PopMenu.this
                            long r0 = r14.enterAnimDuration
                            goto L_0x0022
                        L_0x0017:
                            long r0 = com.kongzue.dialogx.dialogs.PopMenu.overrideEnterDuration
                            int r14 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                            if (r14 != 0) goto L_0x0020
                            r0 = 150(0x96, double:7.4E-322)
                            goto L_0x0022
                        L_0x0020:
                            long r0 = com.kongzue.dialogx.dialogs.PopMenu.overrideEnterDuration
                        L_0x0022:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r14 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r14 = r14.baseView
                            r2 = -1
                            r3 = 2
                            r4 = 0
                            if (r14 == 0) goto L_0x0415
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r14 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r14 = r14.getBodyRealHeight()
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r15 = r15.boxBody
                            android.view.ViewGroup$LayoutParams r15 = r15.getLayoutParams()
                            r5 = 1
                            r15.height = r5
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r15 = r15.overlayBaseView
                            r6 = 1073741824(0x40000000, float:2.0)
                            if (r15 == 0) goto L_0x0102
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.PopMenuListView r15 = r15.listMenu
                            boolean r15 = r15.isCanScroll()
                            if (r15 != 0) goto L_0x0102
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r15 = r15.baseView
                            boolean r15 = r15 instanceof android.widget.TextView
                            if (r15 == 0) goto L_0x009e
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r15 = r15.baseView
                            android.widget.TextView r15 = (android.widget.TextView) r15
                            java.lang.CharSequence r15 = r15.getText()
                            java.lang.String r15 = r15.toString()
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r7 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r7 = com.kongzue.dialogx.dialogs.PopMenu.this
                            java.util.List<java.lang.CharSequence> r7 = r7.menuList
                            java.util.Iterator r7 = r7.iterator()
                        L_0x007c:
                            boolean r8 = r7.hasNext()
                            if (r8 == 0) goto L_0x009e
                            java.lang.Object r8 = r7.next()
                            java.lang.CharSequence r8 = (java.lang.CharSequence) r8
                            java.lang.String r9 = r8.toString()
                            boolean r9 = android.text.TextUtils.equals(r9, r15)
                            if (r9 == 0) goto L_0x007c
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            java.util.List<java.lang.CharSequence> r15 = r15.menuList
                            int r15 = r15.indexOf(r8)
                            r13.selectMenuIndex = r15
                        L_0x009e:
                            int r15 = r13.selectMenuIndex
                            if (r15 == r2) goto L_0x0102
                            int[] r15 = new int[r3]
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r7 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.PopMenuListView r7 = r7.listMenu
                            int r8 = r13.selectMenuIndex
                            android.view.View r7 = r7.getChildAt(r8)
                            if (r7 == 0) goto L_0x0102
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r7 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.PopMenuListView r7 = r7.listMenu
                            int r8 = r13.selectMenuIndex
                            android.view.View r7 = r7.getChildAt(r8)
                            int r7 = r7.getMeasuredHeight()
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r8 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.PopMenuListView r8 = r8.listMenu
                            int r9 = r13.selectMenuIndex
                            android.view.View r8 = r8.getChildAt(r9)
                            r8.getLocationOnScreen(r15)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r8 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r8 = r8.boxBody
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r9 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r9 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int[] r9 = r9.baseViewLoc
                            r9 = r9[r5]
                            float r9 = (float) r9
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r10 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r10 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r10 = r10.baseView
                            int r10 = r10.getMeasuredHeight()
                            float r10 = (float) r10
                            float r10 = r10 / r6
                            float r9 = r9 + r10
                            r15 = r15[r5]
                            float r15 = (float) r15
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r10 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r10 = r10.boxBody
                            float r10 = r10.getY()
                            float r15 = r15 - r10
                            float r9 = r9 - r15
                            float r15 = (float) r7
                            float r15 = r15 / r6
                            float r9 = r9 - r15
                            r8.setY(r9)
                        L_0x0102:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int[] r15 = r15.baseViewLoc
                            r15 = r15[r4]
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r7 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r7 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int[] r7 = r7.baseViewLoc
                            r7 = r7[r5]
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r8 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r8 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r8 = r8.alignGravity
                            if (r8 == r2) goto L_0x031b
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r8 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r8 = com.kongzue.dialogx.dialogs.PopMenu.this
                            r9 = 16
                            boolean r8 = r8.isAlignGravity(r9)
                            if (r8 == 0) goto L_0x0143
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r8 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r8 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r8 = r8.baseView
                            int r8 = r8.getMeasuredHeight()
                            int r8 = r8 / r3
                            int r8 = r8 + r7
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r9 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.boxBody
                            int r9 = r9.getHeight()
                            int r9 = r9 / r3
                            int r8 = r8 - r9
                            int r8 = java.lang.Math.max(r4, r8)
                            goto L_0x0144
                        L_0x0143:
                            r8 = 0
                        L_0x0144:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r9 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r9 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r5 = r9.isAlignGravity(r5)
                            if (r5 == 0) goto L_0x0175
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r5 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r5 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r5 = r5.getWidth()
                            if (r5 <= 0) goto L_0x016e
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r5 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r5 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r5 = r5.baseView
                            int r5 = r5.getMeasuredWidth()
                            int r5 = r5 / r3
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r9 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r9 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r9 = r9.getWidth()
                            int r9 = r9 / r3
                            int r5 = r5 - r9
                            goto L_0x016f
                        L_0x016e:
                            r5 = 0
                        L_0x016f:
                            int r5 = r5 + r15
                            int r5 = java.lang.Math.max(r4, r5)
                            goto L_0x0176
                        L_0x0175:
                            r5 = 0
                        L_0x0176:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r9 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r9 = com.kongzue.dialogx.dialogs.PopMenu.this
                            r10 = 17
                            boolean r9 = r9.isAlignGravity(r10)
                            if (r9 == 0) goto L_0x01c4
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r5 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r5 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r5 = r5.getWidth()
                            if (r5 <= 0) goto L_0x01a2
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r5 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r5 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r5 = r5.baseView
                            int r5 = r5.getMeasuredWidth()
                            int r5 = r5 / r3
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r8 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r8 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r8 = r8.getWidth()
                            int r8 = r8 / r3
                            int r5 = r5 - r8
                            goto L_0x01a3
                        L_0x01a2:
                            r5 = 0
                        L_0x01a3:
                            int r5 = r5 + r15
                            int r5 = java.lang.Math.max(r4, r5)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r8 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r8 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r8 = r8.baseView
                            int r8 = r8.getMeasuredHeight()
                            int r8 = r8 / r3
                            int r8 = r8 + r7
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r9 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.boxBody
                            int r9 = r9.getHeight()
                            int r9 = r9 / r3
                            int r8 = r8 - r9
                            int r8 = java.lang.Math.max(r4, r8)
                        L_0x01c4:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r3 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r3 = r3.overlayBaseView
                            r9 = 80
                            r10 = 5
                            r11 = 3
                            r12 = 48
                            if (r3 == 0) goto L_0x0232
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r3 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r3 = r3.isAlignGravity(r12)
                            if (r3 == 0) goto L_0x01f3
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r3 = r3.boxBody
                            int r3 = r3.getHeight()
                            int r3 = r7 - r3
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r8 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r8 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r8 = r8.baseView
                            int r8 = r8.getHeight()
                            int r8 = r8 + r3
                        L_0x01f3:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r3 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r3 = r3.isAlignGravity(r11)
                            if (r3 == 0) goto L_0x01fe
                            r5 = r15
                        L_0x01fe:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r3 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r3 = r3.isAlignGravity(r10)
                            if (r3 == 0) goto L_0x0227
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r3 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r3 = r3.getWidth()
                            if (r3 <= 0) goto L_0x0224
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r3 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r3 = r3.baseView
                            int r3 = r3.getMeasuredWidth()
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r5 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r5 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r5 = r5.width
                            int r3 = r3 - r5
                            goto L_0x0225
                        L_0x0224:
                            r3 = 0
                        L_0x0225:
                            int r5 = r15 + r3
                        L_0x0227:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r15 = r15.isAlignGravity(r9)
                            if (r15 == 0) goto L_0x029a
                            goto L_0x029b
                        L_0x0232:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r3 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r3 = r3.isAlignGravity(r12)
                            if (r3 == 0) goto L_0x024c
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r3 = r3.boxBody
                            int r3 = r3.getHeight()
                            int r3 = r7 - r3
                            int r8 = java.lang.Math.max(r4, r3)
                        L_0x024c:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r3 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r3 = r3.isAlignGravity(r11)
                            if (r3 == 0) goto L_0x0266
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r3 = r3.boxBody
                            int r3 = r3.getWidth()
                            int r3 = r15 - r3
                            int r5 = java.lang.Math.max(r4, r3)
                        L_0x0266:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r3 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r3 = r3.isAlignGravity(r10)
                            if (r3 == 0) goto L_0x0280
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r3 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r3 = r3.baseView
                            int r3 = r3.getWidth()
                            int r15 = r15 + r3
                            int r15 = java.lang.Math.max(r4, r15)
                            r5 = r15
                        L_0x0280:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r15 = r15.isAlignGravity(r9)
                            if (r15 == 0) goto L_0x029a
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.view.View r15 = r15.baseView
                            int r15 = r15.getHeight()
                            int r7 = r7 + r15
                            int r7 = java.lang.Math.max(r4, r7)
                            goto L_0x029b
                        L_0x029a:
                            r7 = r8
                        L_0x029b:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r15 = r15.offScreen
                            if (r15 != 0) goto L_0x0303
                            if (r5 >= 0) goto L_0x02a6
                            r5 = 0
                        L_0x02a6:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r15 = r15.boxBody
                            int r15 = r15.getWidth()
                            int r15 = r15 + r5
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r3 = r3.boxRoot
                            int r3 = r3.getWidth()
                            if (r15 <= r3) goto L_0x02d3
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r15 = r15.boxRoot
                            int r15 = r15.getWidth()
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r3 = r3.boxBody
                            int r3 = r3.getWidth()
                            int r15 = r15 - r3
                            r5 = r15
                        L_0x02d3:
                            if (r7 >= 0) goto L_0x02d6
                            r7 = 0
                        L_0x02d6:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r15 = r15.boxBody
                            int r15 = r15.getHeight()
                            int r15 = r15 + r7
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r3 = r3.boxRoot
                            int r3 = r3.getHeight()
                            if (r15 <= r3) goto L_0x0303
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r15 = r15.boxRoot
                            int r15 = r15.getHeight()
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r3 = r3.boxBody
                            int r3 = r3.getHeight()
                            int r7 = r15 - r3
                        L_0x0303:
                            if (r5 == 0) goto L_0x030f
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r15 = r15.boxBody
                            float r3 = (float) r5
                            r15.setX(r3)
                        L_0x030f:
                            if (r7 == 0) goto L_0x031b
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r15 = r15.boxBody
                            float r3 = (float) r7
                            r15.setY(r3)
                        L_0x031b:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl$8$1 r15 = new com.kongzue.dialogx.dialogs.PopMenu$DialogImpl$8$1
                            r15.<init>(r14)
                            android.view.animation.DecelerateInterpolator r3 = new android.view.animation.DecelerateInterpolator
                            r3.<init>(r6)
                            r15.setInterpolator(r3)
                            r15.setDuration(r0)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r0 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r0 = r0.boxBody
                            r0.startAnimation(r15)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r15 = r15.boxBody
                            r15.setVisibility(r4)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            com.kongzue.dialogx.interfaces.DialogXStyle r15 = r15.getStyle()
                            com.kongzue.dialogx.interfaces.DialogXStyle$PopMenuSettings r15 = r15.popMenuSettings()
                            if (r15 == 0) goto L_0x04bf
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            com.kongzue.dialogx.interfaces.DialogXStyle r15 = r15.getStyle()
                            com.kongzue.dialogx.interfaces.DialogXStyle$PopMenuSettings r15 = r15.popMenuSettings()
                            com.kongzue.dialogx.interfaces.DialogXStyle$BlurBackgroundSetting r15 = r15.blurBackgroundSettings()
                            if (r15 == 0) goto L_0x04bf
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            com.kongzue.dialogx.interfaces.DialogXStyle r15 = r15.getStyle()
                            com.kongzue.dialogx.interfaces.DialogXStyle$PopMenuSettings r15 = r15.popMenuSettings()
                            com.kongzue.dialogx.interfaces.DialogXStyle$BlurBackgroundSetting r15 = r15.blurBackgroundSettings()
                            boolean r15 = r15.blurBackground()
                            if (r15 == 0) goto L_0x04bf
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            android.content.res.Resources r15 = r15.getResources()
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r0 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r0 = com.kongzue.dialogx.dialogs.PopMenu.this
                            com.kongzue.dialogx.interfaces.DialogXStyle r0 = r0.getStyle()
                            com.kongzue.dialogx.interfaces.DialogXStyle$PopMenuSettings r0 = r0.popMenuSettings()
                            com.kongzue.dialogx.interfaces.DialogXStyle$BlurBackgroundSetting r0 = r0.blurBackgroundSettings()
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r1 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r1 = com.kongzue.dialogx.dialogs.PopMenu.this
                            boolean r1 = r1.isLightTheme()
                            int r0 = r0.blurForwardColorRes(r1)
                            int r15 = r15.getColor(r0)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r0 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.BlurView r1 = new com.kongzue.dialogx.util.views.BlurView
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r3 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r3 = r3.boxBody
                            android.content.Context r3 = r3.getContext()
                            r5 = 0
                            r1.<init>(r3, r5)
                            com.kongzue.dialogx.util.views.BlurView unused = r0.blurView = r1
                            android.widget.RelativeLayout$LayoutParams r0 = new android.widget.RelativeLayout$LayoutParams
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r1 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r1 = r1.boxBody
                            int r1 = r1.getWidth()
                            r0.<init>(r1, r14)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.BlurView r14 = r14.blurView
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r1 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r1 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r1 = r1.backgroundColor
                            if (r1 != r2) goto L_0x03d0
                            goto L_0x03d8
                        L_0x03d0:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r15 = r15.backgroundColor
                        L_0x03d8:
                            r14.setOverlayColor(r15)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.BlurView r14 = r14.blurView
                            java.lang.String r15 = "blurView"
                            r14.setTag(r15)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.BlurView r14 = r14.blurView
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r15 = com.kongzue.dialogx.dialogs.PopMenu.this
                            com.kongzue.dialogx.interfaces.DialogXStyle r15 = r15.getStyle()
                            com.kongzue.dialogx.interfaces.DialogXStyle$PopMenuSettings r15 = r15.popMenuSettings()
                            com.kongzue.dialogx.interfaces.DialogXStyle$BlurBackgroundSetting r15 = r15.blurBackgroundSettings()
                            int r15 = r15.blurBackgroundRoundRadiusPx()
                            float r15 = (float) r15
                            r14.setRadiusPx(r15)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r14 = r14.boxBody
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r15 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.BlurView r15 = r15.blurView
                            r14.addView(r15, r4, r0)
                            goto L_0x04bf
                        L_0x0415:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r14 = r14.boxBody
                            android.view.ViewGroup$LayoutParams r14 = r14.getLayoutParams()
                            android.widget.RelativeLayout$LayoutParams r14 = (android.widget.RelativeLayout.LayoutParams) r14
                            r5 = 13
                            r14.addRule(r5)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r5 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r5 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r5 = r5.getWidth()
                            if (r5 != r2) goto L_0x0431
                            goto L_0x0439
                        L_0x0431:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r2 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r2 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r2 = r2.getWidth()
                        L_0x0439:
                            r14.width = r2
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r2 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r2 = com.kongzue.dialogx.dialogs.PopMenu.this
                            r5 = 1112014848(0x42480000, float:50.0)
                            int r2 = r2.dip2px(r5)
                            r14.leftMargin = r2
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r2 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r2 = com.kongzue.dialogx.dialogs.PopMenu.this
                            int r2 = r2.dip2px(r5)
                            r14.rightMargin = r2
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r2 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r2 = r2.boxBody
                            r2.setLayoutParams(r14)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r14 = r14.boxBody
                            r2 = 0
                            r14.setAlpha(r2)
                            int r14 = android.os.Build.VERSION.SDK_INT
                            r2 = 21
                            if (r14 < r2) goto L_0x047e
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r14 = r14.boxBody
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r2 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.dialogs.PopMenu r2 = com.kongzue.dialogx.dialogs.PopMenu.this
                            r5 = 1101004800(0x41a00000, float:20.0)
                            int r2 = r2.dip2px(r5)
                            float r2 = (float) r2
                            r14.setElevation(r2)
                        L_0x047e:
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r14 = r14.boxBody
                            r14.setVisibility(r4)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r14 = r14.boxBody
                            android.view.ViewPropertyAnimator r14 = r14.animate()
                            r2 = 1065353216(0x3f800000, float:1.0)
                            android.view.ViewPropertyAnimator r14 = r14.alpha(r2)
                            r14.setDuration(r0)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl r14 = com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.this
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r14 = r14.boxBody
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl$8$2 r2 = new com.kongzue.dialogx.dialogs.PopMenu$DialogImpl$8$2
                            r2.<init>()
                            r14.post(r2)
                            float[] r14 = new float[r3]
                            r14 = {0, 1065353216} // fill-array
                            android.animation.ValueAnimator r14 = android.animation.ValueAnimator.ofFloat(r14)
                            r14.setDuration(r0)
                            com.kongzue.dialogx.dialogs.PopMenu$DialogImpl$8$3 r0 = new com.kongzue.dialogx.dialogs.PopMenu$DialogImpl$8$3
                            r0.<init>(r15)
                            r14.addUpdateListener(r0)
                            r14.start()
                        L_0x04bf:
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.dialogs.PopMenu.DialogImpl.C20088.doShowAnim(com.kongzue.dialogx.dialogs.PopMenu, com.kongzue.dialogx.util.ObjectRunnable):void");
                    }

                    public void doExitAnim(PopMenu popMenu, final ObjectRunnable<Float> objectRunnable) {
                        if (PopMenu.overrideExitDuration != -1) {
                            long unused = PopMenu.this.exitAnimDuration = PopMenu.overrideExitDuration;
                        }
                        Animation loadAnimation = AnimationUtils.loadAnimation(BaseDialog.getTopActivity() == null ? DialogImpl.this.boxRoot.getContext() : BaseDialog.getTopActivity(), C1903R.C1904anim.anim_dialogx_default_exit);
                        if (PopMenu.this.exitAnimDuration != -1) {
                            loadAnimation.setDuration(PopMenu.this.exitAnimDuration);
                        }
                        DialogImpl.this.boxBody.startAnimation(loadAnimation);
                        DialogImpl.this.boxRoot.animate().alpha(0.0f).setInterpolator(new AccelerateInterpolator()).setDuration(PopMenu.this.exitAnimDuration == -1 ? loadAnimation.getDuration() : PopMenu.this.exitAnimDuration);
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
                        ofFloat.setDuration(PopMenu.this.exitAnimDuration == -1 ? loadAnimation.getDuration() : PopMenu.this.exitAnimDuration);
                        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                objectRunnable.run((Float) valueAnimator.getAnimatedValue());
                            }
                        });
                        ofFloat.start();
                    }
                };
            }
            return PopMenu.this.dialogXAnimImpl;
        }
    }

    /* access modifiers changed from: private */
    public int getBodyRealHeight() {
        if (getDialogImpl() == null) {
            return 0;
        }
        getDialogImpl().boxBody.measure(View.MeasureSpec.makeMeasureSpec(((View) getDialogImpl().boxBody.getParent()).getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(((View) getDialogImpl().boxBody.getParent()).getHeight(), Integer.MIN_VALUE));
        return getDialogImpl().boxBody.getMeasuredHeight();
    }

    public void dismiss() {
        this.closing = true;
        runOnMain(new Runnable() {
            public void run() {
                if (PopMenu.this.dialogImpl != null) {
                    PopMenu.this.dialogImpl.doDismiss((View) null);
                }
            }
        });
    }

    public void restartDialog() {
        View view = this.dialogView;
        if (view != null) {
            dismiss(view);
            this.isShow = false;
        }
        if (getDialogImpl().boxCustom != null) {
            getDialogImpl().boxCustom.removeAllViews();
        }
        show();
    }

    public View getDialogView() {
        View view = this.dialogView;
        if (view == null) {
            return null;
        }
        return view;
    }

    public List<CharSequence> getMenuList() {
        return this.menuList;
    }

    public PopMenu setMenuList(List<CharSequence> list) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(list);
        refreshUI();
        return this;
    }

    public PopMenu setMenuList(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(Arrays.asList(strArr));
        refreshUI();
        return this;
    }

    public PopMenu setMenuList(CharSequence[] charSequenceArr) {
        ArrayList arrayList = new ArrayList();
        this.menuList = arrayList;
        arrayList.addAll(Arrays.asList(charSequenceArr));
        refreshUI();
        return this;
    }

    public void refreshUI() {
        if (getDialogImpl() != null) {
            runOnMain(new Runnable() {
                public void run() {
                    if (PopMenu.this.dialogImpl != null) {
                        PopMenu.this.dialogImpl.refreshView();
                    }
                }
            });
        }
    }

    public DialogImpl getDialogImpl() {
        return this.dialogImpl;
    }

    public String dialogKey() {
        return getClass().getSimpleName() + "(" + Integer.toHexString(hashCode()) + ")";
    }

    public DialogLifecycleCallback<PopMenu> getDialogLifecycleCallback() {
        DialogLifecycleCallback<PopMenu> dialogLifecycleCallback2 = this.dialogLifecycleCallback;
        return dialogLifecycleCallback2 == null ? new DialogLifecycleCallback<PopMenu>() {
        } : dialogLifecycleCallback2;
    }

    public PopMenu setDialogLifecycleCallback(DialogLifecycleCallback<PopMenu> dialogLifecycleCallback2) {
        this.dialogLifecycleCallback = dialogLifecycleCallback2;
        if (this.isShow) {
            dialogLifecycleCallback2.onShow(this.f932me);
        }
        return this;
    }

    public boolean isOverlayBaseView() {
        return this.overlayBaseView;
    }

    public PopMenu setOverlayBaseView(boolean z) {
        this.overlayBaseView = z;
        refreshUI();
        return this;
    }

    public OnMenuItemClickListener<PopMenu> getOnMenuItemClickListener() {
        OnMenuItemClickListener<PopMenu> onMenuItemClickListener2 = this.onMenuItemClickListener;
        return onMenuItemClickListener2 == null ? new OnMenuItemClickListener<PopMenu>() {
            public boolean onClick(PopMenu popMenu, CharSequence charSequence, int i) {
                return false;
            }
        } : onMenuItemClickListener2;
    }

    public PopMenu setOnMenuItemClickListener(OnMenuItemClickListener<PopMenu> onMenuItemClickListener2) {
        this.onMenuItemClickListener = onMenuItemClickListener2;
        return this;
    }

    public OnIconChangeCallBack<PopMenu> getOnIconChangeCallBack() {
        return this.onIconChangeCallBack;
    }

    public PopMenu setOnIconChangeCallBack(OnIconChangeCallBack<PopMenu> onIconChangeCallBack2) {
        this.onIconChangeCallBack = onIconChangeCallBack2;
        return this;
    }

    public PopMenu setCustomView(OnBindView<PopMenu> onBindView2) {
        this.onBindView = onBindView2;
        refreshUI();
        return this;
    }

    public View getCustomView() {
        OnBindView<PopMenu> onBindView2 = this.onBindView;
        if (onBindView2 == null) {
            return null;
        }
        return onBindView2.getCustomView();
    }

    public PopMenu removeCustomView() {
        this.onBindView.clean();
        refreshUI();
        return this;
    }

    public int getWidth() {
        return this.width;
    }

    public PopMenu setWidth(int i) {
        this.width = i;
        refreshUI();
        return this;
    }

    public int getHeight() {
        return this.height;
    }

    public PopMenu setHeight(int i) {
        this.height = i;
        refreshUI();
        return this;
    }

    public int getAlignGravity() {
        return this.alignGravity;
    }

    public boolean isAlignGravity(int i) {
        return (this.alignGravity & i) == i;
    }

    public PopMenu setAlignGravity(int i) {
        this.alignGravity = i;
        return this;
    }

    public PopMenu setDialogImplMode(DialogX.IMPL_MODE impl_mode) {
        this.dialogImplMode = impl_mode;
        return this;
    }

    public TextInfo getMenuTextInfo() {
        TextInfo textInfo = this.menuTextInfo;
        return textInfo == null ? DialogX.menuTextInfo : textInfo;
    }

    public PopMenu setMenuTextInfo(TextInfo textInfo) {
        this.menuTextInfo = textInfo;
        return this;
    }

    public boolean isOffScreen() {
        return this.offScreen;
    }

    public PopMenu setOffScreen(boolean z) {
        this.offScreen = z;
        return this;
    }

    public boolean isBkgInterceptTouch() {
        return this.bkgInterceptTouch;
    }

    public PopMenu setBkgInterceptTouch(boolean z) {
        this.bkgInterceptTouch = z;
        return this;
    }

    public OnBackgroundMaskClickListener<PopMenu> getOnBackgroundMaskClickListener() {
        return this.onBackgroundMaskClickListener;
    }

    public PopMenu setOnBackgroundMaskClickListener(OnBackgroundMaskClickListener<PopMenu> onBackgroundMaskClickListener2) {
        this.onBackgroundMaskClickListener = onBackgroundMaskClickListener2;
        return this;
    }

    public PopMenu setStyle(DialogXStyle dialogXStyle) {
        this.style = dialogXStyle;
        return this;
    }

    public PopMenu setTheme(DialogX.THEME theme) {
        this.theme = theme;
        return this;
    }

    public PopMenu setRadius(float f) {
        this.backgroundRadius = f;
        refreshUI();
        return this;
    }

    public float getRadius() {
        return this.backgroundRadius;
    }

    public void hide() {
        this.isHide = true;
        this.hideWithExitAnim = false;
        if (getDialogView() != null) {
            getDialogView().setVisibility(8);
        }
    }

    public void hideWithExitAnim() {
        this.hideWithExitAnim = true;
        this.isHide = true;
        if (getDialogImpl() != null) {
            getDialogImpl().getDialogXAnimImpl().doExitAnim(this.f932me, new ObjectRunnable<Float>() {
                public void run(Float f) {
                    PopMenu.this.getDialogImpl().boxRoot.setBkgAlpha(f.floatValue());
                    if (f.floatValue() == 0.0f && PopMenu.this.getDialogView() != null) {
                        PopMenu.this.getDialogView().setVisibility(8);
                    }
                }
            });
        }
    }

    public DialogXAnimInterface<PopMenu> getDialogXAnimImpl() {
        return this.dialogXAnimImpl;
    }

    public PopMenu setDialogXAnimImpl(DialogXAnimInterface<PopMenu> dialogXAnimInterface) {
        this.dialogXAnimImpl = dialogXAnimInterface;
        return this;
    }

    public OnBackPressedListener<PopMenu> getOnBackPressedListener() {
        return this.onBackPressedListener;
    }

    public PopMenu setOnBackPressedListener(OnBackPressedListener<PopMenu> onBackPressedListener2) {
        this.onBackPressedListener = onBackPressedListener2;
        return this;
    }

    public View getBaseView() {
        return this.baseView;
    }

    public PopMenu setBaseView(View view) {
        this.baseView = view;
        refreshUI();
        return this;
    }
}
