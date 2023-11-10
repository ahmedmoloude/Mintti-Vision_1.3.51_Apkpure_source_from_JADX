package com.kongzue.dialogx.dialogs;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialogx.interfaces.ScrollController;
import com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor;
import com.kongzue.dialogx.util.ObjectRunnable;
import com.kongzue.dialogx.util.TextInfo;
import com.kongzue.dialogx.util.views.BlurView;
import com.kongzue.dialogx.util.views.BottomDialogScrollView;
import com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout;
import com.kongzue.dialogx.util.views.MaxRelativeLayout;

public class BottomDialog extends BaseDialog {
    public static BaseDialog.BOOLEAN overrideCancelable = null;
    public static int overrideEnterDuration = -1;
    public static int overrideExitDuration = -1;
    protected boolean allowInterceptTouch = true;
    protected float backgroundRadius = -1.0f;
    protected boolean bkgInterceptTouch = true;
    protected float bottomDialogMaxHeight = 0.0f;
    protected OnDialogButtonClickListener<BottomDialog> cancelButtonClickListener;
    protected CharSequence cancelText;
    protected TextInfo cancelTextInfo = new TextInfo().setBold(true);
    protected DialogImpl dialogImpl;
    protected DialogLifecycleCallback<BottomDialog> dialogLifecycleCallback;
    /* access modifiers changed from: private */
    public View dialogView;
    protected DialogXAnimInterface<BottomDialog> dialogXAnimImpl;
    protected boolean hideWithExitAnim;
    protected boolean isHide;
    protected int maskColor = -1;

    /* renamed from: me */
    protected BottomDialog f926me = this;
    protected TextInfo menuTextInfo;
    protected CharSequence message;
    protected TextInfo messageTextInfo;
    protected OnDialogButtonClickListener<BottomDialog> okButtonClickListener;
    protected CharSequence okText;
    protected TextInfo okTextInfo = new TextInfo().setBold(true);
    protected OnBackPressedListener<BottomDialog> onBackPressedListener;
    protected OnBackgroundMaskClickListener<BottomDialog> onBackgroundMaskClickListener;
    protected OnBindView<BottomDialog> onBindView;
    protected OnDialogButtonClickListener<BottomDialog> otherButtonClickListener;
    protected CharSequence otherText;
    protected TextInfo otherTextInfo = new TextInfo().setBold(true);
    protected BaseDialog.BOOLEAN privateCancelable;
    protected CharSequence title;
    protected Drawable titleIcon;
    protected TextInfo titleTextInfo;

    protected BottomDialog() {
    }

    public String dialogKey() {
        return getClass().getSimpleName() + "(" + Integer.toHexString(hashCode()) + ")";
    }

    public static BottomDialog build() {
        return new BottomDialog();
    }

    public static BottomDialog build(DialogXStyle dialogXStyle) {
        return new BottomDialog().setStyle(dialogXStyle);
    }

    public static BottomDialog build(OnBindView<BottomDialog> onBindView2) {
        return new BottomDialog().setCustomView(onBindView2);
    }

    public BottomDialog(CharSequence charSequence, CharSequence charSequence2) {
        this.title = charSequence;
        this.message = charSequence2;
    }

    public BottomDialog(int i, int i2) {
        this.title = getString(i);
        this.message = getString(i2);
    }

    public static BottomDialog show(CharSequence charSequence, CharSequence charSequence2) {
        BottomDialog bottomDialog = new BottomDialog(charSequence, charSequence2);
        bottomDialog.show();
        return bottomDialog;
    }

    public static BottomDialog show(int i, int i2) {
        BottomDialog bottomDialog = new BottomDialog(i, i2);
        bottomDialog.show();
        return bottomDialog;
    }

    public BottomDialog(CharSequence charSequence, CharSequence charSequence2, OnBindView<BottomDialog> onBindView2) {
        this.title = charSequence;
        this.message = charSequence2;
        this.onBindView = onBindView2;
    }

    public BottomDialog(int i, int i2, OnBindView<BottomDialog> onBindView2) {
        this.title = getString(i);
        this.message = getString(i2);
        this.onBindView = onBindView2;
    }

    public static BottomDialog show(CharSequence charSequence, CharSequence charSequence2, OnBindView<BottomDialog> onBindView2) {
        BottomDialog bottomDialog = new BottomDialog(charSequence, charSequence2, onBindView2);
        bottomDialog.show();
        return bottomDialog;
    }

    public static BottomDialog show(int i, int i2, OnBindView<BottomDialog> onBindView2) {
        BottomDialog bottomDialog = new BottomDialog(i, i2, onBindView2);
        bottomDialog.show();
        return bottomDialog;
    }

    public BottomDialog(CharSequence charSequence, OnBindView<BottomDialog> onBindView2) {
        this.title = charSequence;
        this.onBindView = onBindView2;
    }

    public BottomDialog(int i, OnBindView<BottomDialog> onBindView2) {
        this.title = getString(i);
        this.onBindView = onBindView2;
    }

    public static BottomDialog show(CharSequence charSequence, OnBindView<BottomDialog> onBindView2) {
        BottomDialog bottomDialog = new BottomDialog(charSequence, onBindView2);
        bottomDialog.show();
        return bottomDialog;
    }

    public static BottomDialog show(int i, OnBindView<BottomDialog> onBindView2) {
        BottomDialog bottomDialog = new BottomDialog(i, onBindView2);
        bottomDialog.show();
        return bottomDialog;
    }

    public BottomDialog(OnBindView<BottomDialog> onBindView2) {
        this.onBindView = onBindView2;
    }

    public static BottomDialog show(OnBindView<BottomDialog> onBindView2) {
        BottomDialog bottomDialog = new BottomDialog(onBindView2);
        bottomDialog.show();
        return bottomDialog;
    }

    public BottomDialog show() {
        if (!this.isHide || getDialogView() == null || !this.isShow) {
            super.beforeShow();
            if (getDialogView() == null) {
                int i = isLightTheme() ? C1903R.layout.layout_dialogx_bottom_material : C1903R.layout.layout_dialogx_bottom_material_dark;
                if (this.style.overrideBottomDialogRes() != null) {
                    i = this.style.overrideBottomDialogRes().overrideDialogLayout(isLightTheme());
                }
                View createView = createView(i);
                this.dialogView = createView;
                this.dialogImpl = new DialogImpl(createView);
                View view = this.dialogView;
                if (view != null) {
                    view.setTag(this.f926me);
                }
            }
            show(this.dialogView);
            return this;
        }
        if (!this.hideWithExitAnim || getDialogImpl() == null) {
            getDialogView().setVisibility(0);
        } else {
            getDialogView().setVisibility(0);
            getDialogImpl().getDialogXAnimImpl().doShowAnim(this.f926me, new ObjectRunnable<Float>() {
                public void run(Float f) {
                    BottomDialog.this.getDialogImpl().boxRoot.setBkgAlpha(f.floatValue());
                }
            });
        }
        return this;
    }

    public void show(Activity activity) {
        super.beforeShow();
        if (getDialogView() == null) {
            int i = isLightTheme() ? C1903R.layout.layout_dialogx_bottom_material : C1903R.layout.layout_dialogx_bottom_material_dark;
            if (this.style.overrideBottomDialogRes() != null) {
                i = this.style.overrideBottomDialogRes().overrideDialogLayout(isLightTheme());
            }
            View createView = createView(i);
            this.dialogView = createView;
            this.dialogImpl = new DialogImpl(createView);
            View view = this.dialogView;
            if (view != null) {
                view.setTag(this.f926me);
            }
        }
        show(activity, this.dialogView);
    }

    public class DialogImpl implements DialogConvertViewInterface {
        public MaxRelativeLayout bkg;
        public float bkgEnterAimY = -1.0f;
        public BlurView blurView;
        /* access modifiers changed from: private */
        public BottomDialogTouchEventInterceptor bottomDialogTouchEventInterceptor;
        public RelativeLayout boxBkg;
        public ViewGroup boxBody;
        public ViewGroup boxCancel;
        public LinearLayout boxContent;
        public RelativeLayout boxCustom;
        public RelativeLayout boxList;
        public DialogXBaseRelativeLayout boxRoot;
        public TextView btnCancel;
        public TextView btnSelectOther;
        public TextView btnSelectPositive;
        public BlurView cancelBlurView;
        long exitAnimDurationTemp = 300;
        public View imgSplit;
        public ImageView imgTab;
        public ScrollController scrollView;
        public TextView txtDialogTip;
        public TextView txtDialogTitle;

        public DialogImpl(View view) {
            if (view != null) {
                this.boxRoot = (DialogXBaseRelativeLayout) view.findViewById(C1903R.C1907id.box_root);
                this.boxBkg = (RelativeLayout) view.findViewById(C1903R.C1907id.box_bkg);
                this.bkg = (MaxRelativeLayout) view.findViewById(C1903R.C1907id.bkg);
                this.boxBody = (ViewGroup) view.findViewWithTag("blurBody");
                this.imgTab = (ImageView) view.findViewById(C1903R.C1907id.img_tab);
                this.txtDialogTitle = (TextView) view.findViewById(C1903R.C1907id.txt_dialog_title);
                this.scrollView = (ScrollController) view.findViewById(C1903R.C1907id.scrollView);
                this.boxContent = (LinearLayout) view.findViewById(C1903R.C1907id.box_content);
                this.txtDialogTip = (TextView) view.findViewById(C1903R.C1907id.txt_dialog_tip);
                this.imgSplit = view.findViewWithTag("split");
                this.boxList = (RelativeLayout) view.findViewById(C1903R.C1907id.box_list);
                this.boxCustom = (RelativeLayout) view.findViewById(C1903R.C1907id.box_custom);
                this.blurView = (BlurView) view.findViewById(C1903R.C1907id.blurView);
                this.boxCancel = (ViewGroup) view.findViewWithTag("cancelBox");
                this.btnCancel = (TextView) view.findViewWithTag("cancel");
                this.btnSelectOther = (TextView) view.findViewById(C1903R.C1907id.btn_selectOther);
                this.btnSelectPositive = (TextView) view.findViewById(C1903R.C1907id.btn_selectPositive);
                init();
                BottomDialog.this.dialogImpl = this;
                refreshView();
            }
        }

        public void reBuild() {
            init();
            BottomDialog.this.dialogImpl = this;
            refreshView();
        }

        public void init() {
            if (BottomDialog.this.titleTextInfo == null) {
                BottomDialog.this.titleTextInfo = DialogX.titleTextInfo;
            }
            if (BottomDialog.this.messageTextInfo == null) {
                BottomDialog.this.messageTextInfo = DialogX.messageTextInfo;
            }
            if (BottomDialog.this.okTextInfo == null) {
                BottomDialog.this.okTextInfo = DialogX.okButtonTextInfo;
            }
            if (BottomDialog.this.okTextInfo == null) {
                BottomDialog.this.okTextInfo = DialogX.buttonTextInfo;
            }
            if (BottomDialog.this.cancelTextInfo == null) {
                BottomDialog.this.cancelTextInfo = DialogX.buttonTextInfo;
            }
            if (BottomDialog.this.otherTextInfo == null) {
                BottomDialog.this.otherTextInfo = DialogX.buttonTextInfo;
            }
            if (BottomDialog.this.backgroundColor == -1) {
                int unused = BottomDialog.this.backgroundColor = DialogX.backgroundColor;
            }
            if (BottomDialog.this.cancelText == null) {
                BottomDialog.this.cancelText = DialogX.cancelButtonText;
            }
            this.txtDialogTitle.getPaint().setFakeBoldText(true);
            TextView textView = this.btnCancel;
            if (textView != null) {
                textView.getPaint().setFakeBoldText(true);
            }
            TextView textView2 = this.btnSelectPositive;
            if (textView2 != null) {
                textView2.getPaint().setFakeBoldText(true);
            }
            TextView textView3 = this.btnSelectOther;
            if (textView3 != null) {
                textView3.getPaint().setFakeBoldText(true);
            }
            this.boxBkg.setY((float) BaseDialog.getRootFrameLayout().getMeasuredHeight());
            this.bkg.setMaxWidth(BottomDialog.this.getMaxWidth());
            this.boxRoot.setParentDialog(BottomDialog.this.f926me);
            this.boxRoot.setOnLifecycleCallBack(new DialogXBaseRelativeLayout.OnLifecycleCallBack() {
                public void onShow() {
                    boolean unused = BottomDialog.this.isShow = true;
                    boolean unused2 = BottomDialog.this.preShow = false;
                    BottomDialog.this.lifecycle.setCurrentState(Lifecycle.State.CREATED);
                    BottomDialog.this.getDialogLifecycleCallback().onShow(BottomDialog.this.f926me);
                    BottomDialog.this.onDialogShow();
                    DialogImpl.this.boxRoot.post(new Runnable() {
                        public void run() {
                            if (!(BottomDialog.this.style.messageDialogBlurSettings() == null || !BottomDialog.this.style.messageDialogBlurSettings().blurBackground() || DialogImpl.this.boxBody == null || DialogImpl.this.boxCancel == null)) {
                                int color = BottomDialog.this.getResources().getColor(BottomDialog.this.style.messageDialogBlurSettings().blurForwardColorRes(BottomDialog.this.isLightTheme()));
                                DialogImpl.this.blurView = new BlurView(DialogImpl.this.bkg.getContext(), (AttributeSet) null);
                                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DialogImpl.this.bkg.getWidth(), DialogImpl.this.bkg.getHeight());
                                DialogImpl.this.blurView.setOverlayColor(BottomDialog.this.backgroundColor == -1 ? color : BottomDialog.this.backgroundColor);
                                DialogImpl.this.blurView.setTag("blurView");
                                DialogImpl.this.blurView.setRadiusPx((float) BottomDialog.this.style.messageDialogBlurSettings().blurBackgroundRoundRadiusPx());
                                DialogImpl.this.boxBody.addView(DialogImpl.this.blurView, 0, layoutParams);
                                DialogImpl.this.cancelBlurView = new BlurView(DialogImpl.this.boxCancel.getContext(), (AttributeSet) null);
                                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(DialogImpl.this.boxCancel.getWidth(), DialogImpl.this.boxCancel.getHeight());
                                BlurView blurView = DialogImpl.this.cancelBlurView;
                                if (BottomDialog.this.backgroundColor != -1) {
                                    color = BottomDialog.this.backgroundColor;
                                }
                                blurView.setOverlayColor(color);
                                DialogImpl.this.cancelBlurView.setTag("blurView");
                                DialogImpl.this.cancelBlurView.setRadiusPx((float) BottomDialog.this.style.messageDialogBlurSettings().blurBackgroundRoundRadiusPx());
                                DialogImpl.this.boxCancel.addView(DialogImpl.this.cancelBlurView, 0, layoutParams2);
                            }
                            BottomDialog.this.lifecycle.setCurrentState(Lifecycle.State.RESUMED);
                        }
                    });
                    BottomDialog.this.refreshUI();
                }

                public void onDismiss() {
                    boolean unused = BottomDialog.this.isShow = false;
                    BottomDialog.this.getDialogLifecycleCallback().onDismiss(BottomDialog.this.f926me);
                    BottomDialog.this.dialogImpl = null;
                    BottomDialogTouchEventInterceptor unused2 = DialogImpl.this.bottomDialogTouchEventInterceptor = null;
                    BottomDialog.this.dialogLifecycleCallback = null;
                    BottomDialog.this.lifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                    System.gc();
                }
            });
            TextView textView4 = this.btnCancel;
            if (textView4 != null) {
                textView4.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (BottomDialog.this.cancelButtonClickListener == null) {
                            BottomDialog.this.dismiss();
                        } else if (!BottomDialog.this.cancelButtonClickListener.onClick(BottomDialog.this.f926me, view)) {
                            BottomDialog.this.dismiss();
                        }
                    }
                });
            }
            TextView textView5 = this.btnSelectOther;
            if (textView5 != null) {
                textView5.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (BottomDialog.this.otherButtonClickListener == null) {
                            BottomDialog.this.dismiss();
                        } else if (!BottomDialog.this.otherButtonClickListener.onClick(BottomDialog.this.f926me, view)) {
                            BottomDialog.this.dismiss();
                        }
                    }
                });
            }
            TextView textView6 = this.btnSelectPositive;
            if (textView6 != null) {
                textView6.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (BottomDialog.this.okButtonClickListener == null) {
                            BottomDialog.this.dismiss();
                        } else if (!BottomDialog.this.okButtonClickListener.onClick(BottomDialog.this.f926me, view)) {
                            BottomDialog.this.dismiss();
                        }
                    }
                });
            }
            if (this.imgSplit != null) {
                int overrideMenuDividerDrawableRes = BottomDialog.this.style.overrideBottomDialogRes().overrideMenuDividerDrawableRes(BottomDialog.this.isLightTheme());
                int overrideMenuDividerHeight = BottomDialog.this.style.overrideBottomDialogRes().overrideMenuDividerHeight(BottomDialog.this.isLightTheme());
                if (overrideMenuDividerDrawableRes != 0) {
                    this.imgSplit.setBackgroundResource(overrideMenuDividerDrawableRes);
                }
                if (overrideMenuDividerHeight != 0) {
                    ViewGroup.LayoutParams layoutParams = this.imgSplit.getLayoutParams();
                    layoutParams.height = overrideMenuDividerHeight;
                    this.imgSplit.setLayoutParams(layoutParams);
                }
            }
            this.boxRoot.setOnBackPressedListener(new DialogXBaseRelativeLayout.PrivateBackPressedListener() {
                public boolean onBackPressed() {
                    if (BottomDialog.this.onBackPressedListener != null) {
                        if (!BottomDialog.this.onBackPressedListener.onBackPressed(BottomDialog.this.f926me)) {
                            return true;
                        }
                        BottomDialog.this.dismiss();
                        return true;
                    } else if (!BottomDialog.this.isCancelable()) {
                        return true;
                    } else {
                        BottomDialog.this.dismiss();
                        return true;
                    }
                }
            });
            this.boxBkg.post(new Runnable() {
                public void run() {
                    DialogImpl.this.getDialogXAnimImpl().doShowAnim(BottomDialog.this, new ObjectRunnable<Float>() {
                        public void run(Float f) {
                            DialogImpl.this.boxRoot.setBkgAlpha(f.floatValue());
                            if (f.floatValue() == 1.0f) {
                                BottomDialogTouchEventInterceptor unused = DialogImpl.this.bottomDialogTouchEventInterceptor = new BottomDialogTouchEventInterceptor(BottomDialog.this.f926me, BottomDialog.this.dialogImpl);
                            }
                        }
                    });
                }
            });
            BottomDialog.this.onDialogInit();
        }

        public void refreshView() {
            if (this.boxRoot != null && BaseDialog.getTopActivity() != null) {
                if (BottomDialog.this.backgroundColor != -1) {
                    BottomDialog bottomDialog = BottomDialog.this;
                    bottomDialog.tintColor(this.bkg, bottomDialog.backgroundColor);
                    BlurView blurView2 = this.blurView;
                    if (!(blurView2 == null || this.cancelBlurView == null)) {
                        blurView2.setOverlayColor(BottomDialog.this.backgroundColor);
                        this.cancelBlurView.setOverlayColor(BottomDialog.this.backgroundColor);
                    }
                    BottomDialog bottomDialog2 = BottomDialog.this;
                    bottomDialog2.tintColor(this.btnSelectOther, bottomDialog2.backgroundColor);
                    BottomDialog bottomDialog3 = BottomDialog.this;
                    bottomDialog3.tintColor(this.btnCancel, bottomDialog3.backgroundColor);
                    BottomDialog bottomDialog4 = BottomDialog.this;
                    bottomDialog4.tintColor(this.btnSelectPositive, bottomDialog4.backgroundColor);
                }
                BottomDialog bottomDialog5 = BottomDialog.this;
                bottomDialog5.showText(this.txtDialogTitle, bottomDialog5.title);
                BottomDialog bottomDialog6 = BottomDialog.this;
                bottomDialog6.showText(this.txtDialogTip, bottomDialog6.message);
                BaseDialog.useTextInfo(this.txtDialogTitle, BottomDialog.this.titleTextInfo);
                BaseDialog.useTextInfo(this.txtDialogTip, BottomDialog.this.messageTextInfo);
                BaseDialog.useTextInfo(this.btnCancel, BottomDialog.this.cancelTextInfo);
                BaseDialog.useTextInfo(this.btnSelectOther, BottomDialog.this.otherTextInfo);
                BaseDialog.useTextInfo(this.btnSelectPositive, BottomDialog.this.okTextInfo);
                if (BottomDialog.this.titleIcon != null) {
                    int textSize = (int) this.txtDialogTitle.getTextSize();
                    BottomDialog.this.titleIcon.setBounds(0, 0, textSize, textSize);
                    this.txtDialogTitle.setCompoundDrawablePadding(BottomDialog.this.dip2px(10.0f));
                    this.txtDialogTitle.setCompoundDrawables(BottomDialog.this.titleIcon, (Drawable) null, (Drawable) null, (Drawable) null);
                }
                if (!BottomDialog.this.bkgInterceptTouch) {
                    this.boxRoot.setClickable(false);
                } else if (BottomDialog.this.isCancelable()) {
                    this.boxRoot.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            if (BottomDialog.this.onBackgroundMaskClickListener == null || !BottomDialog.this.onBackgroundMaskClickListener.onClick(BottomDialog.this.f926me, view)) {
                                DialogImpl.this.doDismiss(view);
                            }
                        }
                    });
                } else {
                    this.boxRoot.setOnClickListener((View.OnClickListener) null);
                }
                this.boxBkg.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        DialogImpl.this.boxRoot.callOnClick();
                    }
                });
                if (BottomDialog.this.backgroundRadius > -1.0f) {
                    GradientDrawable gradientDrawable = (GradientDrawable) this.bkg.getBackground();
                    if (gradientDrawable != null) {
                        gradientDrawable.setCornerRadii(new float[]{BottomDialog.this.backgroundRadius, BottomDialog.this.backgroundRadius, BottomDialog.this.backgroundRadius, BottomDialog.this.backgroundRadius, 0.0f, 0.0f, 0.0f, 0.0f});
                    }
                    if (Build.VERSION.SDK_INT >= 21) {
                        this.bkg.setOutlineProvider(new ViewOutlineProvider() {
                            public void getOutline(View view, Outline outline) {
                                outline.setRoundRect(0, 0, view.getWidth(), (int) (((float) view.getHeight()) + BottomDialog.this.backgroundRadius), BottomDialog.this.backgroundRadius);
                            }
                        });
                        this.bkg.setClipToOutline(true);
                    }
                }
                if (BottomDialog.this.maskColor != -1) {
                    this.boxRoot.setBackground(new ColorDrawable(BottomDialog.this.maskColor));
                }
                if (!(BottomDialog.this.onBindView == null || BottomDialog.this.onBindView.getCustomView() == null)) {
                    BottomDialog.this.onBindView.bindParent(this.boxCustom, BottomDialog.this.f926me);
                    if (BottomDialog.this.onBindView.getCustomView() instanceof ScrollController) {
                        ScrollController scrollController = this.scrollView;
                        if (scrollController instanceof BottomDialogScrollView) {
                            ((BottomDialogScrollView) scrollController).setVerticalScrollBarEnabled(false);
                        }
                        this.scrollView = (ScrollController) BottomDialog.this.onBindView.getCustomView();
                    } else {
                        View findViewWithTag = BottomDialog.this.onBindView.getCustomView().findViewWithTag("ScrollController");
                        if (findViewWithTag instanceof ScrollController) {
                            ScrollController scrollController2 = this.scrollView;
                            if (scrollController2 instanceof BottomDialogScrollView) {
                                ((BottomDialogScrollView) scrollController2).setVerticalScrollBarEnabled(false);
                            }
                            this.scrollView = (ScrollController) findViewWithTag;
                        }
                    }
                }
                if (!BottomDialog.this.isAllowInterceptTouch() || !BottomDialog.this.isCancelable()) {
                    ImageView imageView = this.imgTab;
                    if (imageView != null) {
                        imageView.setVisibility(8);
                    }
                } else {
                    ImageView imageView2 = this.imgTab;
                    if (imageView2 != null) {
                        imageView2.setVisibility(0);
                    }
                }
                BottomDialogTouchEventInterceptor bottomDialogTouchEventInterceptor2 = this.bottomDialogTouchEventInterceptor;
                if (bottomDialogTouchEventInterceptor2 != null) {
                    bottomDialogTouchEventInterceptor2.refresh(BottomDialog.this.f926me, this);
                }
                if (this.imgSplit != null) {
                    if (this.txtDialogTitle.getVisibility() == 0 || this.txtDialogTip.getVisibility() == 0) {
                        this.imgSplit.setVisibility(0);
                    } else {
                        this.imgSplit.setVisibility(8);
                    }
                }
                if (this.boxCancel != null) {
                    if (BaseDialog.isNull(BottomDialog.this.cancelText)) {
                        this.boxCancel.setVisibility(8);
                    } else {
                        this.boxCancel.setVisibility(0);
                    }
                }
                BottomDialog bottomDialog7 = BottomDialog.this;
                bottomDialog7.showText(this.btnSelectPositive, bottomDialog7.okText);
                BottomDialog bottomDialog8 = BottomDialog.this;
                bottomDialog8.showText(this.btnCancel, bottomDialog8.cancelText);
                BottomDialog bottomDialog9 = BottomDialog.this;
                bottomDialog9.showText(this.btnSelectOther, bottomDialog9.otherText);
                BottomDialog.this.onDialogRefreshUI();
            }
        }

        public void doDismiss(View view) {
            if (view != null) {
                view.setEnabled(false);
            }
            if (BaseDialog.getTopActivity() != null && !BottomDialog.this.dismissAnimFlag) {
                boolean unused = BottomDialog.this.dismissAnimFlag = true;
                getDialogXAnimImpl().doExitAnim(BottomDialog.this, new ObjectRunnable<Float>() {
                    public void run(Float f) {
                        if (DialogImpl.this.boxRoot != null) {
                            DialogImpl.this.boxRoot.setBkgAlpha(f.floatValue());
                        }
                        if (f.floatValue() == 0.0f) {
                            if (DialogImpl.this.boxRoot != null) {
                                DialogImpl.this.boxRoot.setVisibility(8);
                            }
                            BottomDialog.dismiss(BottomDialog.this.dialogView);
                        }
                    }
                });
                BottomDialog.runOnMainDelay(new Runnable() {
                    public void run() {
                    }
                }, this.exitAnimDurationTemp);
            }
        }

        public void preDismiss() {
            if (BottomDialog.this.isCancelable()) {
                doDismiss(this.boxRoot);
                return;
            }
            long j = 300;
            if (BottomDialog.overrideExitDuration >= 0) {
                j = (long) BottomDialog.overrideExitDuration;
            }
            if (BottomDialog.this.exitAnimDuration >= 0) {
                j = BottomDialog.this.exitAnimDuration;
            }
            RelativeLayout relativeLayout = this.boxBkg;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(relativeLayout, "y", new float[]{relativeLayout.getY(), (float) this.boxRoot.getUnsafePlace().top});
            ofFloat.setDuration(j);
            ofFloat.start();
        }

        /* access modifiers changed from: protected */
        public DialogXAnimInterface<BottomDialog> getDialogXAnimImpl() {
            if (BottomDialog.this.dialogXAnimImpl == null) {
                BottomDialog.this.dialogXAnimImpl = new DialogXAnimInterface<BottomDialog>() {
                    public void doShowAnim(BottomDialog bottomDialog, final ObjectRunnable<Float> objectRunnable) {
                        float f;
                        float f2;
                        float f3;
                        float f4;
                        float f5 = 0.0f;
                        if (bottomDialog.isAllowInterceptTouch()) {
                            if (BottomDialog.this.bottomDialogMaxHeight > 0.0f && BottomDialog.this.bottomDialogMaxHeight <= 1.0f) {
                                f3 = (float) DialogImpl.this.boxBkg.getHeight();
                                f4 = BottomDialog.this.bottomDialogMaxHeight * ((float) DialogImpl.this.boxBkg.getHeight());
                            } else if (BottomDialog.this.bottomDialogMaxHeight > 1.0f) {
                                f3 = (float) DialogImpl.this.boxBkg.getHeight();
                                f4 = BottomDialog.this.bottomDialogMaxHeight;
                            }
                            f5 = f3 - f4;
                        } else {
                            if (BottomDialog.this.bottomDialogMaxHeight <= 0.0f || BottomDialog.this.bottomDialogMaxHeight > 1.0f) {
                                if (BottomDialog.this.bottomDialogMaxHeight > 1.0f) {
                                    f = (float) DialogImpl.this.boxBkg.getHeight();
                                    f2 = BottomDialog.this.bottomDialogMaxHeight;
                                }
                                DialogImpl.this.boxBkg.setPadding(0, 0, 0, (int) f5);
                            } else {
                                f = (float) DialogImpl.this.boxBkg.getHeight();
                                f2 = BottomDialog.this.bottomDialogMaxHeight * ((float) DialogImpl.this.boxBkg.getHeight());
                            }
                            f5 = f - f2;
                            DialogImpl.this.boxBkg.setPadding(0, 0, 0, (int) f5);
                        }
                        RelativeLayout relativeLayout = DialogImpl.this.boxBkg;
                        DialogImpl dialogImpl = DialogImpl.this;
                        float f6 = ((float) dialogImpl.boxRoot.getUnsafePlace().top) + f5;
                        dialogImpl.bkgEnterAimY = f6;
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(relativeLayout, "y", new float[]{DialogImpl.this.boxBkg.getY(), f6});
                        long j = BottomDialog.overrideEnterDuration >= 0 ? (long) BottomDialog.overrideEnterDuration : 300;
                        if (BottomDialog.this.enterAnimDuration >= 0) {
                            j = BottomDialog.this.enterAnimDuration;
                        }
                        ofFloat.setDuration(j);
                        ofFloat.setAutoCancel(true);
                        ofFloat.setInterpolator(new DecelerateInterpolator(2.0f));
                        ofFloat.start();
                        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                        ofFloat2.setDuration(j);
                        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                objectRunnable.run((Float) valueAnimator.getAnimatedValue());
                            }
                        });
                        ofFloat2.start();
                    }

                    public void doExitAnim(BottomDialog bottomDialog, final ObjectRunnable<Float> objectRunnable) {
                        if (BottomDialog.overrideExitDuration >= 0) {
                            DialogImpl.this.exitAnimDurationTemp = (long) BottomDialog.overrideExitDuration;
                        }
                        if (BottomDialog.this.exitAnimDuration >= 0) {
                            DialogImpl dialogImpl = DialogImpl.this;
                            dialogImpl.exitAnimDurationTemp = BottomDialog.this.exitAnimDuration;
                        }
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DialogImpl.this.boxBkg, "y", new float[]{DialogImpl.this.boxBkg.getY(), (float) DialogImpl.this.boxBkg.getHeight()});
                        ofFloat.setDuration(DialogImpl.this.exitAnimDurationTemp);
                        ofFloat.start();
                        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
                        ofFloat2.setDuration(DialogImpl.this.exitAnimDurationTemp);
                        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                objectRunnable.run((Float) valueAnimator.getAnimatedValue());
                            }
                        });
                        ofFloat2.start();
                    }
                };
            }
            return BottomDialog.this.dialogXAnimImpl;
        }
    }

    public void refreshUI() {
        if (getDialogImpl() != null) {
            runOnMain(new Runnable() {
                public void run() {
                    if (BottomDialog.this.dialogImpl != null) {
                        BottomDialog.this.dialogImpl.refreshView();
                    }
                }
            });
        }
    }

    public void dismiss() {
        runOnMain(new Runnable() {
            public void run() {
                if (BottomDialog.this.dialogImpl != null) {
                    BottomDialog.this.dialogImpl.doDismiss((View) null);
                }
            }
        });
    }

    public DialogLifecycleCallback<BottomDialog> getDialogLifecycleCallback() {
        DialogLifecycleCallback<BottomDialog> dialogLifecycleCallback2 = this.dialogLifecycleCallback;
        return dialogLifecycleCallback2 == null ? new DialogLifecycleCallback<BottomDialog>() {
        } : dialogLifecycleCallback2;
    }

    public BottomDialog setDialogLifecycleCallback(DialogLifecycleCallback<BottomDialog> dialogLifecycleCallback2) {
        this.dialogLifecycleCallback = dialogLifecycleCallback2;
        if (this.isShow) {
            dialogLifecycleCallback2.onShow(this.f926me);
        }
        return this;
    }

    public OnBackPressedListener<BottomDialog> getOnBackPressedListener() {
        return this.onBackPressedListener;
    }

    public BottomDialog setOnBackPressedListener(OnBackPressedListener<BottomDialog> onBackPressedListener2) {
        this.onBackPressedListener = onBackPressedListener2;
        refreshUI();
        return this;
    }

    public BottomDialog setStyle(DialogXStyle dialogXStyle) {
        this.style = dialogXStyle;
        return this;
    }

    public BottomDialog setTheme(DialogX.THEME theme) {
        this.theme = theme;
        return this;
    }

    public boolean isCancelable() {
        BaseDialog.BOOLEAN booleanR = this.privateCancelable;
        if (booleanR == null) {
            BaseDialog.BOOLEAN booleanR2 = overrideCancelable;
            if (booleanR2 == null) {
                return this.cancelable;
            }
            if (booleanR2 == BaseDialog.BOOLEAN.TRUE) {
                return true;
            }
            return false;
        } else if (booleanR == BaseDialog.BOOLEAN.TRUE) {
            return true;
        } else {
            return false;
        }
    }

    public BottomDialog setCancelable(boolean z) {
        this.privateCancelable = z ? BaseDialog.BOOLEAN.TRUE : BaseDialog.BOOLEAN.FALSE;
        refreshUI();
        return this;
    }

    public DialogImpl getDialogImpl() {
        return this.dialogImpl;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public BottomDialog setTitle(CharSequence charSequence) {
        this.title = charSequence;
        refreshUI();
        return this;
    }

    public BottomDialog setTitle(int i) {
        this.title = getString(i);
        refreshUI();
        return this;
    }

    public CharSequence getMessage() {
        return this.message;
    }

    public BottomDialog setMessage(CharSequence charSequence) {
        this.message = charSequence;
        refreshUI();
        return this;
    }

    public BottomDialog setMessage(int i) {
        this.message = getString(i);
        refreshUI();
        return this;
    }

    public CharSequence getCancelButton() {
        return this.cancelText;
    }

    public BottomDialog setCancelButton(CharSequence charSequence) {
        this.cancelText = charSequence;
        refreshUI();
        return this;
    }

    public BottomDialog setCancelButton(int i) {
        this.cancelText = getString(i);
        refreshUI();
        return this;
    }

    public BottomDialog setCancelButton(OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.cancelButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public BottomDialog setCancelButton(CharSequence charSequence, OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.cancelText = charSequence;
        this.cancelButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public BottomDialog setCancelButton(int i, OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.cancelText = getString(i);
        this.cancelButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public BottomDialog setCustomView(OnBindView<BottomDialog> onBindView2) {
        this.onBindView = onBindView2;
        refreshUI();
        return this;
    }

    public View getCustomView() {
        OnBindView<BottomDialog> onBindView2 = this.onBindView;
        if (onBindView2 == null) {
            return null;
        }
        return onBindView2.getCustomView();
    }

    public BottomDialog removeCustomView() {
        this.onBindView.clean();
        refreshUI();
        return this;
    }

    public boolean isAllowInterceptTouch() {
        if (this.style.overrideBottomDialogRes() != null && this.allowInterceptTouch && this.style.overrideBottomDialogRes().touchSlide()) {
            return true;
        }
        return false;
    }

    public BottomDialog setAllowInterceptTouch(boolean z) {
        this.allowInterceptTouch = z;
        refreshUI();
        return this;
    }

    public OnDialogButtonClickListener<BottomDialog> getCancelButtonClickListener() {
        return this.cancelButtonClickListener;
    }

    public BottomDialog setCancelButtonClickListener(OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.cancelButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public TextInfo getTitleTextInfo() {
        return this.titleTextInfo;
    }

    public BottomDialog setTitleTextInfo(TextInfo textInfo) {
        this.titleTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getMessageTextInfo() {
        return this.messageTextInfo;
    }

    public BottomDialog setMessageTextInfo(TextInfo textInfo) {
        this.messageTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getCancelTextInfo() {
        return this.cancelTextInfo;
    }

    public BottomDialog setCancelTextInfo(TextInfo textInfo) {
        this.cancelTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public BottomDialog setBackgroundColor(int i) {
        this.backgroundColor = i;
        refreshUI();
        return this;
    }

    public BottomDialog setBackgroundColorRes(int i) {
        this.backgroundColor = getColor(i);
        refreshUI();
        return this;
    }

    public CharSequence getOkButton() {
        return this.okText;
    }

    public BottomDialog setOkButton(CharSequence charSequence) {
        this.okText = charSequence;
        refreshUI();
        return this;
    }

    public BottomDialog setOkButton(int i) {
        this.okText = getString(i);
        refreshUI();
        return this;
    }

    public BottomDialog setOkButton(OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.okButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public BottomDialog setOkButton(CharSequence charSequence, OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.okText = charSequence;
        this.okButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public BottomDialog setOkButton(int i, OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.okText = getString(i);
        this.okButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public CharSequence getOtherButton() {
        return this.otherText;
    }

    public BottomDialog setOtherButton(CharSequence charSequence) {
        this.otherText = charSequence;
        refreshUI();
        return this;
    }

    public BottomDialog setOtherButton(int i) {
        this.otherText = getString(i);
        refreshUI();
        return this;
    }

    public BottomDialog setOtherButton(OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.otherButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public BottomDialog setOtherButton(CharSequence charSequence, OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.otherText = charSequence;
        this.otherButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public BottomDialog setOtherButton(int i, OnDialogButtonClickListener<BottomDialog> onDialogButtonClickListener) {
        this.otherText = getString(i);
        this.otherButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public BottomDialog setMaskColor(int i) {
        this.maskColor = i;
        refreshUI();
        return this;
    }

    public long getEnterAnimDuration() {
        return this.enterAnimDuration;
    }

    public BottomDialog setEnterAnimDuration(long j) {
        this.enterAnimDuration = j;
        return this;
    }

    public long getExitAnimDuration() {
        return this.exitAnimDuration;
    }

    public BottomDialog setExitAnimDuration(long j) {
        this.exitAnimDuration = j;
        return this;
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
        if (getDialogImpl().boxList != null) {
            getDialogImpl().boxList.removeAllViews();
        }
        int i = isLightTheme() ? C1903R.layout.layout_dialogx_bottom_material : C1903R.layout.layout_dialogx_bottom_material_dark;
        if (this.style.overrideBottomDialogRes() != null) {
            i = this.style.overrideBottomDialogRes().overrideDialogLayout(isLightTheme());
        }
        this.enterAnimDuration = 0;
        View createView = createView(i);
        this.dialogView = createView;
        this.dialogImpl = new DialogImpl(createView);
        View view2 = this.dialogView;
        if (view2 != null) {
            view2.setTag(this.f926me);
        }
        show(this.dialogView);
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
            getDialogImpl().getDialogXAnimImpl().doExitAnim(this.f926me, new ObjectRunnable<Float>() {
                public void run(Float f) {
                    if (BottomDialog.this.getDialogImpl().boxRoot != null) {
                        BottomDialog.this.getDialogImpl().boxRoot.setBkgAlpha(f.floatValue());
                    }
                    if (f.floatValue() == 0.0f && BottomDialog.this.getDialogView() != null) {
                        BottomDialog.this.getDialogView().setVisibility(8);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void shutdown() {
        dismiss();
    }

    public float getBottomDialogMaxHeight() {
        return this.bottomDialogMaxHeight;
    }

    public BottomDialog setBottomDialogMaxHeight(float f) {
        this.bottomDialogMaxHeight = f;
        return this;
    }

    public BottomDialog setMaxWidth(int i) {
        this.maxWidth = i;
        refreshUI();
        return this;
    }

    public BottomDialog setDialogImplMode(DialogX.IMPL_MODE impl_mode) {
        this.dialogImplMode = impl_mode;
        return this;
    }

    public boolean isBkgInterceptTouch() {
        return this.bkgInterceptTouch;
    }

    public BottomDialog setBkgInterceptTouch(boolean z) {
        this.bkgInterceptTouch = z;
        return this;
    }

    public OnBackgroundMaskClickListener<BottomDialog> getOnBackgroundMaskClickListener() {
        return this.onBackgroundMaskClickListener;
    }

    public BottomDialog setOnBackgroundMaskClickListener(OnBackgroundMaskClickListener<BottomDialog> onBackgroundMaskClickListener2) {
        this.onBackgroundMaskClickListener = onBackgroundMaskClickListener2;
        return this;
    }

    public BottomDialog setRadius(float f) {
        this.backgroundRadius = f;
        refreshUI();
        return this;
    }

    public float getRadius() {
        return this.backgroundRadius;
    }

    public Drawable getTitleIcon() {
        return this.titleIcon;
    }

    public BottomDialog setTitleIcon(Bitmap bitmap) {
        this.titleIcon = new BitmapDrawable(getResources(), bitmap);
        refreshUI();
        return this;
    }

    public BottomDialog setTitleIcon(int i) {
        this.titleIcon = getResources().getDrawable(i);
        refreshUI();
        return this;
    }

    public BottomDialog setTitleIcon(Drawable drawable) {
        this.titleIcon = drawable;
        refreshUI();
        return this;
    }

    public DialogXAnimInterface<BottomDialog> getDialogXAnimImpl() {
        return this.dialogXAnimImpl;
    }

    public BottomDialog setDialogXAnimImpl(DialogXAnimInterface<BottomDialog> dialogXAnimInterface) {
        this.dialogXAnimImpl = dialogXAnimInterface;
        return this;
    }
}
