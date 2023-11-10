package com.kongzue.dialogx.dialogs;

import android.animation.Animator;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.kongzue.dialogx.interfaces.NoTouchInterface;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialogx.interfaces.OnSafeInsetsChangeListener;
import com.kongzue.dialogx.util.ObjectRunnable;
import com.kongzue.dialogx.util.TextInfo;
import com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class PopTip extends BaseDialog implements NoTouchInterface {
    public static final int TIME_NO_AUTO_DISMISS_DELAY = -1;
    public static int overrideEnterAnimRes = 0;
    public static long overrideEnterDuration = -1;
    public static int overrideExitAnimRes = 0;
    public static long overrideExitDuration = -1;
    protected static List<PopTip> popTipList;
    protected DialogXStyle.PopTipSettings.ALIGN align;
    protected long autoDismissDelay;
    protected Timer autoDismissTimer;
    protected float backgroundRadius = -1.0f;
    protected int[] bodyMargin = {-1, -1, -1, -1};
    protected CharSequence buttonText;
    protected TextInfo buttonTextInfo = new TextInfo().setBold(true);
    protected DialogImpl dialogImpl;
    protected DialogLifecycleCallback<PopTip> dialogLifecycleCallback;
    private View dialogView;
    protected DialogXAnimInterface<PopTip> dialogXAnimImpl;
    protected int enterAnimResId = 0;
    protected int exitAnimResId = 0;
    protected int iconResId;
    private boolean isHide;

    /* renamed from: me */
    protected PopTip f935me = this;
    protected CharSequence message;
    protected TextInfo messageTextInfo;
    protected OnBindView<PopTip> onBindView;
    protected OnDialogButtonClickListener<PopTip> onButtonClickListener;
    protected OnDialogButtonClickListener<PopTip> onPopTipClickListener;
    protected boolean preRecycle = false;
    protected BaseDialog.BOOLEAN tintIcon;

    public boolean isCancelable() {
        return false;
    }

    protected PopTip() {
    }

    public static PopTip build() {
        return new PopTip();
    }

    public static PopTip build(DialogXStyle dialogXStyle) {
        return new PopTip().setStyle(dialogXStyle);
    }

    public static PopTip build(OnBindView<PopTip> onBindView2) {
        return new PopTip().setCustomView(onBindView2);
    }

    public PopTip(OnBindView<PopTip> onBindView2) {
        this.onBindView = onBindView2;
    }

    public PopTip(CharSequence charSequence) {
        this.message = charSequence;
    }

    public PopTip(int i) {
        this.message = getString(i);
    }

    public PopTip(int i, CharSequence charSequence) {
        this.iconResId = i;
        this.message = charSequence;
    }

    public PopTip(int i, CharSequence charSequence, CharSequence charSequence2) {
        this.iconResId = i;
        this.message = charSequence;
        this.buttonText = charSequence2;
    }

    public PopTip(int i, int i2, int i3) {
        this.iconResId = i;
        this.message = getString(i2);
        this.buttonText = getString(i3);
    }

    public PopTip(CharSequence charSequence, CharSequence charSequence2) {
        this.message = charSequence;
        this.buttonText = charSequence2;
    }

    public PopTip(int i, int i2) {
        this.message = getString(i);
        this.buttonText = getString(i2);
    }

    public PopTip(CharSequence charSequence, OnBindView<PopTip> onBindView2) {
        this.message = charSequence;
        this.onBindView = onBindView2;
    }

    public PopTip(int i, OnBindView<PopTip> onBindView2) {
        this.message = getString(i);
        this.onBindView = onBindView2;
    }

    public PopTip(int i, CharSequence charSequence, OnBindView<PopTip> onBindView2) {
        this.iconResId = i;
        this.message = charSequence;
        this.onBindView = onBindView2;
    }

    public PopTip(int i, CharSequence charSequence, CharSequence charSequence2, OnBindView<PopTip> onBindView2) {
        this.iconResId = i;
        this.message = charSequence;
        this.buttonText = charSequence2;
        this.onBindView = onBindView2;
    }

    public PopTip(int i, int i2, int i3, OnBindView<PopTip> onBindView2) {
        this.iconResId = i;
        this.message = getString(i2);
        this.buttonText = getString(i3);
        this.onBindView = onBindView2;
    }

    public PopTip(CharSequence charSequence, CharSequence charSequence2, OnBindView<PopTip> onBindView2) {
        this.message = charSequence;
        this.buttonText = charSequence2;
        this.onBindView = onBindView2;
    }

    public PopTip(int i, int i2, OnBindView<PopTip> onBindView2) {
        this.message = getString(i);
        this.buttonText = getString(i2);
        this.onBindView = onBindView2;
    }

    public static PopTip show(OnBindView<PopTip> onBindView2) {
        PopTip popTip = new PopTip(onBindView2);
        popTip.show();
        return popTip;
    }

    public static PopTip show(CharSequence charSequence) {
        PopTip popTip = new PopTip(charSequence);
        popTip.show();
        return popTip;
    }

    public static PopTip show(int i) {
        PopTip popTip = new PopTip(i);
        popTip.show();
        return popTip;
    }

    public static PopTip show(CharSequence charSequence, OnBindView<PopTip> onBindView2) {
        PopTip popTip = new PopTip(charSequence, onBindView2);
        popTip.show();
        return popTip;
    }

    public static PopTip show(int i, OnBindView<PopTip> onBindView2) {
        PopTip popTip = new PopTip(i, onBindView2);
        popTip.show();
        return popTip;
    }

    public static PopTip show(CharSequence charSequence, CharSequence charSequence2) {
        PopTip popTip = new PopTip(charSequence, charSequence2);
        popTip.show();
        return popTip;
    }

    public static PopTip show(int i, int i2) {
        PopTip popTip = new PopTip(i, i2);
        popTip.show();
        return popTip;
    }

    public static PopTip show(int i, CharSequence charSequence, OnBindView<PopTip> onBindView2) {
        PopTip popTip = new PopTip(i, charSequence, onBindView2);
        popTip.show();
        return popTip;
    }

    public static PopTip show(int i, CharSequence charSequence) {
        PopTip popTip = new PopTip(i, charSequence);
        popTip.show();
        return popTip;
    }

    public static PopTip show(int i, CharSequence charSequence, CharSequence charSequence2) {
        PopTip popTip = new PopTip(i, charSequence, charSequence2);
        popTip.show();
        return popTip;
    }

    public static PopTip show(int i, CharSequence charSequence, CharSequence charSequence2, OnBindView<PopTip> onBindView2) {
        PopTip popTip = new PopTip(i, charSequence, charSequence2, onBindView2);
        popTip.show();
        return popTip;
    }

    public static PopTip show(int i, int i2, int i3, OnBindView<PopTip> onBindView2) {
        PopTip popTip = new PopTip(i, i2, i3, onBindView2);
        popTip.show();
        return popTip;
    }

    public static PopTip show(CharSequence charSequence, CharSequence charSequence2, OnBindView<PopTip> onBindView2) {
        PopTip popTip = new PopTip(charSequence, charSequence2, onBindView2);
        popTip.show();
        return popTip;
    }

    public static PopTip show(int i, int i2, OnBindView<PopTip> onBindView2) {
        PopTip popTip = new PopTip(i, i2, onBindView2);
        popTip.show();
        return popTip;
    }

    public PopTip show() {
        if (!this.isHide || getDialogView() == null) {
            super.beforeShow();
            if (getDialogView() == null) {
                if (DialogX.onlyOnePopTip) {
                    PopTip popTip = null;
                    List<PopTip> list = popTipList;
                    if (list != null && !list.isEmpty()) {
                        List<PopTip> list2 = popTipList;
                        popTip = list2.get(list2.size() - 1);
                    }
                    if (popTip != null) {
                        popTip.dismiss();
                    }
                } else if (popTipList != null) {
                    for (int i = 0; i < popTipList.size(); i++) {
                        popTipList.get(i).moveUp();
                    }
                }
                if (popTipList == null) {
                    popTipList = new ArrayList();
                }
                popTipList.add(this);
                int i2 = isLightTheme() ? C1903R.layout.layout_dialogx_poptip_material : C1903R.layout.layout_dialogx_poptip_material_dark;
                if (this.style.popTipSettings() != null) {
                    if (this.style.popTipSettings().layout(isLightTheme()) != 0) {
                        i2 = this.style.popTipSettings().layout(isLightTheme());
                    }
                    if (this.align == null) {
                        if (this.style.popTipSettings().align() == null) {
                            this.align = DialogXStyle.PopTipSettings.ALIGN.BOTTOM;
                        } else {
                            this.align = this.style.popTipSettings().align();
                        }
                    }
                    int enterAnimResId2 = this.style.popTipSettings().enterAnimResId(isLightTheme());
                    int exitAnimResId2 = this.style.popTipSettings().exitAnimResId(isLightTheme());
                    int i3 = this.enterAnimResId;
                    if (i3 != 0 || (i3 = overrideEnterAnimRes) != 0) {
                        enterAnimResId2 = i3;
                    } else if (enterAnimResId2 == 0) {
                        enterAnimResId2 = C1903R.C1904anim.anim_dialogx_default_enter;
                    }
                    this.enterAnimResId = enterAnimResId2;
                    int i4 = this.exitAnimResId;
                    if (i4 != 0 || (i4 = overrideExitAnimRes) != 0) {
                        exitAnimResId2 = i4;
                    } else if (exitAnimResId2 == 0) {
                        exitAnimResId2 = C1903R.C1904anim.anim_dialogx_default_exit;
                    }
                    this.exitAnimResId = exitAnimResId2;
                    this.enterAnimDuration = this.enterAnimDuration == -1 ? overrideEnterDuration : this.enterAnimDuration;
                    this.exitAnimDuration = this.exitAnimDuration == -1 ? overrideExitDuration : this.exitAnimDuration;
                }
                View createView = createView(i2);
                this.dialogView = createView;
                this.dialogImpl = new DialogImpl(createView);
                View view = this.dialogView;
                if (view != null) {
                    view.setTag(this.f935me);
                }
            }
            show(this.dialogView);
            return this;
        }
        getDialogView().setVisibility(0);
        return this;
    }

    public PopTip show(Activity activity) {
        super.beforeShow();
        if (this.dialogView != null) {
            if (DialogX.onlyOnePopTip) {
                PopTip popTip = null;
                List<PopTip> list = popTipList;
                if (list != null && !list.isEmpty()) {
                    List<PopTip> list2 = popTipList;
                    popTip = list2.get(list2.size() - 1);
                }
                if (popTip != null) {
                    popTip.dismiss();
                }
            } else if (popTipList != null) {
                for (int i = 0; i < popTipList.size(); i++) {
                    popTipList.get(i).moveUp();
                }
            }
            if (popTipList == null) {
                popTipList = new ArrayList();
            }
            popTipList.add(this);
            int i2 = isLightTheme() ? C1903R.layout.layout_dialogx_poptip_material : C1903R.layout.layout_dialogx_poptip_material_dark;
            if (this.style.popTipSettings() != null) {
                if (this.style.popTipSettings().layout(isLightTheme()) != 0) {
                    i2 = this.style.popTipSettings().layout(isLightTheme());
                }
                if (this.align == null) {
                    if (this.style.popTipSettings().align() == null) {
                        this.align = DialogXStyle.PopTipSettings.ALIGN.BOTTOM;
                    } else {
                        this.align = this.style.popTipSettings().align();
                    }
                }
                int enterAnimResId2 = this.style.popTipSettings().enterAnimResId(isLightTheme());
                int exitAnimResId2 = this.style.popTipSettings().exitAnimResId(isLightTheme());
                int i3 = this.enterAnimResId;
                if (i3 != 0 || (i3 = overrideEnterAnimRes) != 0) {
                    enterAnimResId2 = i3;
                } else if (enterAnimResId2 == 0) {
                    enterAnimResId2 = C1903R.C1904anim.anim_dialogx_default_enter;
                }
                this.enterAnimResId = enterAnimResId2;
                int i4 = this.exitAnimResId;
                if (i4 != 0 || (i4 = overrideExitAnimRes) != 0) {
                    exitAnimResId2 = i4;
                } else if (exitAnimResId2 == 0) {
                    exitAnimResId2 = C1903R.C1904anim.anim_dialogx_default_exit;
                }
                this.exitAnimResId = exitAnimResId2;
                this.enterAnimDuration = this.enterAnimDuration == -1 ? overrideEnterDuration : this.enterAnimDuration;
                this.exitAnimDuration = this.exitAnimDuration == -1 ? overrideExitDuration : this.exitAnimDuration;
            }
            View createView = createView(i2);
            this.dialogView = createView;
            this.dialogImpl = new DialogImpl(createView);
            View view = this.dialogView;
            if (view != null) {
                view.setTag(this.f935me);
            }
        }
        show(activity, this.dialogView);
        return this;
    }

    public String dialogKey() {
        return getClass().getSimpleName() + "(" + Integer.toHexString(hashCode()) + ")";
    }

    public PopTip autoDismiss(long j) {
        this.autoDismissDelay = j;
        Timer timer = this.autoDismissTimer;
        if (timer != null) {
            timer.cancel();
        }
        if (j < 0) {
            return this;
        }
        Timer timer2 = new Timer();
        this.autoDismissTimer = timer2;
        timer2.schedule(new TimerTask() {
            public void run() {
                PopTip.this.dismiss();
            }
        }, j);
        return this;
    }

    public void resetAutoDismissTimer() {
        autoDismiss(this.autoDismissDelay);
    }

    public PopTip showShort() {
        autoDismiss(2000);
        if (!this.preShow && !this.isShow) {
            show();
        }
        return this;
    }

    public PopTip showLong() {
        autoDismiss(3500);
        if (!this.preShow && !this.isShow) {
            show();
        }
        return this;
    }

    public PopTip showAlways() {
        return noAutoDismiss();
    }

    public PopTip noAutoDismiss() {
        autoDismiss(-1);
        return this;
    }

    public class DialogImpl implements DialogConvertViewInterface {
        public LinearLayout boxBody;
        public RelativeLayout boxCustom;
        public DialogXBaseRelativeLayout boxRoot;
        public ImageView imgDialogxPopIcon;
        public TextView txtDialogxButton;
        public TextView txtDialogxPopText;

        public DialogImpl(View view) {
            if (view != null) {
                this.boxRoot = (DialogXBaseRelativeLayout) view.findViewById(C1903R.C1907id.box_root);
                this.boxBody = (LinearLayout) view.findViewById(C1903R.C1907id.box_body);
                this.imgDialogxPopIcon = (ImageView) view.findViewById(C1903R.C1907id.img_dialogx_pop_icon);
                this.txtDialogxPopText = (TextView) view.findViewById(C1903R.C1907id.txt_dialogx_pop_text);
                this.boxCustom = (RelativeLayout) view.findViewById(C1903R.C1907id.box_custom);
                this.txtDialogxButton = (TextView) view.findViewById(C1903R.C1907id.txt_dialogx_button);
                init();
                PopTip.this.dialogImpl = this;
                refreshView();
            }
        }

        public void init() {
            if (PopTip.this.messageTextInfo == null) {
                PopTip.this.messageTextInfo = DialogX.popTextInfo;
            }
            if (PopTip.this.buttonTextInfo == null) {
                PopTip.this.buttonTextInfo = DialogX.buttonTextInfo;
            }
            if (PopTip.this.backgroundColor == -1) {
                int unused = PopTip.this.backgroundColor = DialogX.backgroundColor;
            }
            if (PopTip.this.autoDismissTimer == null) {
                PopTip.this.showShort();
            }
            this.boxRoot.setParentDialog(PopTip.this.f935me);
            this.boxRoot.setAutoUnsafePlacePadding(false);
            this.boxRoot.setOnLifecycleCallBack(new DialogXBaseRelativeLayout.OnLifecycleCallBack() {
                public void onShow() {
                    boolean unused = PopTip.this.isShow = true;
                    boolean unused2 = PopTip.this.preShow = false;
                    PopTip.this.lifecycle.setCurrentState(Lifecycle.State.CREATED);
                    DialogImpl.this.boxRoot.setAlpha(0.0f);
                    PopTip.this.onDialogShow();
                    PopTip.this.getDialogLifecycleCallback().onShow(PopTip.this.f935me);
                }

                public void onDismiss() {
                    if (PopTip.popTipList != null) {
                        PopTip.popTipList.remove(PopTip.this);
                    }
                    boolean unused = PopTip.this.isShow = false;
                    PopTip.this.getDialogLifecycleCallback().onDismiss(PopTip.this.f935me);
                    PopTip.this.dialogImpl = null;
                    PopTip.this.lifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                    System.gc();
                }
            });
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.boxBody.getLayoutParams();
            if (PopTip.this.align == null) {
                PopTip.this.align = DialogXStyle.PopTipSettings.ALIGN.BOTTOM;
            }
            int i = C20366.f936x462451d5[PopTip.this.align.ordinal()];
            if (i == 1) {
                layoutParams.removeRule(13);
                layoutParams.addRule(10);
            } else if (i == 2) {
                layoutParams.removeRule(13);
                layoutParams.addRule(12);
                this.boxRoot.setAutoUnsafePlacePadding(true);
            } else if (i == 3) {
                layoutParams.removeRule(10);
                layoutParams.removeRule(12);
                layoutParams.addRule(13);
            }
            this.boxBody.setLayoutParams(layoutParams);
            this.boxRoot.setOnSafeInsetsChangeListener(new OnSafeInsetsChangeListener() {
                public void onChange(Rect rect) {
                    if (PopTip.this.align == DialogXStyle.PopTipSettings.ALIGN.TOP) {
                        DialogImpl.this.boxBody.setY((float) (rect.top + PopTip.this.bodyMargin[1]));
                    } else if (PopTip.this.align == DialogXStyle.PopTipSettings.ALIGN.TOP_INSIDE) {
                        DialogImpl.this.boxBody.setPadding(0, rect.top, 0, 0);
                    }
                }
            });
            this.boxRoot.setOnBackPressedListener(new DialogXBaseRelativeLayout.PrivateBackPressedListener() {
                public boolean onBackPressed() {
                    return false;
                }
            });
            this.boxRoot.post(new Runnable() {
                public void run() {
                    DialogImpl.this.getDialogXAnimImpl().doShowAnim(PopTip.this.f935me, (ObjectRunnable<Float>) null);
                    PopTip.this.lifecycle.setCurrentState(Lifecycle.State.RESUMED);
                }
            });
            this.txtDialogxButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (PopTip.this.onButtonClickListener == null) {
                        DialogImpl.this.doDismiss(view);
                    } else if (!PopTip.this.onButtonClickListener.onClick(PopTip.this.f935me, view)) {
                        DialogImpl.this.doDismiss(view);
                    }
                }
            });
            PopTip.this.onDialogInit();
        }

        public void refreshView() {
            if (this.boxRoot != null && BaseDialog.getTopActivity() != null) {
                if (PopTip.this.backgroundColor != -1) {
                    PopTip popTip = PopTip.this;
                    popTip.tintColor(this.boxBody, popTip.backgroundColor);
                }
                if (PopTip.this.onBindView == null || PopTip.this.onBindView.getCustomView() == null) {
                    this.boxCustom.setVisibility(8);
                } else {
                    PopTip.this.onBindView.bindParent(this.boxCustom, PopTip.this.f935me);
                    this.boxCustom.setVisibility(0);
                }
                PopTip popTip2 = PopTip.this;
                popTip2.showText(this.txtDialogxPopText, popTip2.message);
                PopTip popTip3 = PopTip.this;
                popTip3.showText(this.txtDialogxButton, popTip3.buttonText);
                BaseDialog.useTextInfo(this.txtDialogxPopText, PopTip.this.messageTextInfo);
                BaseDialog.useTextInfo(this.txtDialogxButton, PopTip.this.buttonTextInfo);
                if (PopTip.this.iconResId != 0) {
                    this.imgDialogxPopIcon.setVisibility(0);
                    this.imgDialogxPopIcon.setImageResource(PopTip.this.iconResId);
                    if (Build.VERSION.SDK_INT >= 21) {
                        if (PopTip.this.isTintIcon()) {
                            this.imgDialogxPopIcon.setImageTintList(this.txtDialogxPopText.getTextColors());
                        } else {
                            this.imgDialogxPopIcon.setImageTintList((ColorStateList) null);
                        }
                    }
                } else {
                    this.imgDialogxPopIcon.setVisibility(8);
                }
                if (PopTip.this.backgroundRadius > -1.0f) {
                    GradientDrawable gradientDrawable = (GradientDrawable) this.boxBody.getBackground();
                    if (gradientDrawable != null) {
                        gradientDrawable.setCornerRadius(PopTip.this.backgroundRadius);
                    }
                    if (Build.VERSION.SDK_INT >= 21) {
                        this.boxBody.setOutlineProvider(new ViewOutlineProvider() {
                            public void getOutline(View view, Outline outline) {
                                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), PopTip.this.backgroundRadius);
                            }
                        });
                        this.boxBody.setClipToOutline(true);
                    }
                }
                if (PopTip.this.onPopTipClickListener != null) {
                    this.boxBody.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            if (!PopTip.this.onPopTipClickListener.onClick(PopTip.this.f935me, view)) {
                                PopTip.this.dismiss();
                            }
                        }
                    });
                } else {
                    this.boxBody.setOnClickListener((View.OnClickListener) null);
                    this.boxBody.setClickable(false);
                }
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.boxBody.getLayoutParams();
                if (PopTip.this.bodyMargin[0] != -1) {
                    layoutParams.leftMargin = PopTip.this.bodyMargin[0];
                }
                if (PopTip.this.bodyMargin[1] != -1) {
                    layoutParams.topMargin = PopTip.this.bodyMargin[1];
                }
                if (PopTip.this.bodyMargin[2] != -1) {
                    layoutParams.rightMargin = PopTip.this.bodyMargin[2];
                }
                if (PopTip.this.bodyMargin[3] != -1) {
                    layoutParams.bottomMargin = PopTip.this.bodyMargin[3];
                }
                this.boxBody.setLayoutParams(layoutParams);
                PopTip.this.onDialogRefreshUI();
            }
        }

        public void doDismiss(View view) {
            if (view != null) {
                view.setEnabled(false);
            }
            if (!PopTip.this.dismissAnimFlag) {
                boolean unused = PopTip.this.dismissAnimFlag = true;
                this.boxRoot.post(new Runnable() {
                    public void run() {
                        DialogImpl.this.getDialogXAnimImpl().doExitAnim(PopTip.this.f935me, (ObjectRunnable<Float>) null);
                    }
                });
            }
        }

        /* access modifiers changed from: protected */
        public DialogXAnimInterface<PopTip> getDialogXAnimImpl() {
            if (PopTip.this.dialogXAnimImpl == null) {
                PopTip.this.dialogXAnimImpl = new DialogXAnimInterface<PopTip>() {
                    public void doShowAnim(PopTip popTip, ObjectRunnable<Float> objectRunnable) {
                        Animation loadAnimation = AnimationUtils.loadAnimation(BaseDialog.getTopActivity(), PopTip.this.enterAnimResId == 0 ? C1903R.C1904anim.anim_dialogx_default_enter : PopTip.this.enterAnimResId);
                        loadAnimation.setInterpolator(new DecelerateInterpolator(2.0f));
                        if (PopTip.this.enterAnimDuration != -1) {
                            loadAnimation.setDuration(PopTip.this.enterAnimDuration);
                        }
                        loadAnimation.setFillAfter(true);
                        DialogImpl.this.boxBody.startAnimation(loadAnimation);
                        DialogImpl.this.boxRoot.animate().setDuration(PopTip.this.enterAnimDuration == -1 ? loadAnimation.getDuration() : PopTip.this.enterAnimDuration).alpha(1.0f).setInterpolator(new DecelerateInterpolator()).setListener((Animator.AnimatorListener) null);
                    }

                    public void doExitAnim(PopTip popTip, ObjectRunnable<Float> objectRunnable) {
                        Animation loadAnimation = AnimationUtils.loadAnimation(BaseDialog.getTopActivity() == null ? DialogImpl.this.boxRoot.getContext() : BaseDialog.getTopActivity(), PopTip.this.exitAnimResId == 0 ? C1903R.C1904anim.anim_dialogx_default_exit : PopTip.this.exitAnimResId);
                        if (PopTip.this.exitAnimDuration != -1) {
                            loadAnimation.setDuration(PopTip.this.exitAnimDuration);
                        }
                        loadAnimation.setFillAfter(true);
                        DialogImpl.this.boxBody.startAnimation(loadAnimation);
                        DialogImpl.this.boxRoot.animate().alpha(0.0f).setInterpolator(new AccelerateInterpolator()).setDuration(PopTip.this.exitAnimDuration == -1 ? loadAnimation.getDuration() : PopTip.this.exitAnimDuration);
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            public void run() {
                                PopTip.this.waitForDismiss();
                            }
                        }, PopTip.this.exitAnimDuration == -1 ? loadAnimation.getDuration() : PopTip.this.exitAnimDuration);
                    }
                };
            }
            return PopTip.this.dialogXAnimImpl;
        }
    }

    /* renamed from: com.kongzue.dialogx.dialogs.PopTip$6 */
    static /* synthetic */ class C20366 {

        /* renamed from: $SwitchMap$com$kongzue$dialogx$interfaces$DialogXStyle$PopTipSettings$ALIGN */
        static final /* synthetic */ int[] f936x462451d5;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings$ALIGN[] r0 = com.kongzue.dialogx.interfaces.DialogXStyle.PopTipSettings.ALIGN.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f936x462451d5 = r0
                com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopTipSettings.ALIGN.TOP     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f936x462451d5     // Catch:{ NoSuchFieldError -> 0x001d }
                com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopTipSettings.ALIGN.BOTTOM     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f936x462451d5     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopTipSettings.ALIGN.CENTER     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = f936x462451d5     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopTipSettings.ALIGN.TOP_INSIDE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = f936x462451d5     // Catch:{ NoSuchFieldError -> 0x003e }
                com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopTipSettings.ALIGN.BOTTOM_INSIDE     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.dialogs.PopTip.C20366.<clinit>():void");
        }
    }

    /* access modifiers changed from: private */
    public void waitForDismiss() {
        this.preRecycle = true;
        List<PopTip> list = popTipList;
        if (list != null) {
            for (PopTip popTip : list) {
                if (!popTip.preRecycle) {
                    return;
                }
            }
            Iterator it = new CopyOnWriteArrayList(popTipList).iterator();
            while (it.hasNext()) {
                dismiss(((PopTip) it.next()).dialogView);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0055, code lost:
        if (r2 != 5) goto L_0x0081;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void moveUp() {
        /*
            r7 = this;
            com.kongzue.dialogx.dialogs.PopTip$DialogImpl r0 = r7.getDialogImpl()
            if (r0 == 0) goto L_0x00cb
            com.kongzue.dialogx.dialogs.PopTip$DialogImpl r0 = r7.getDialogImpl()
            android.widget.LinearLayout r0 = r0.boxBody
            if (r0 == 0) goto L_0x00cb
            com.kongzue.dialogx.dialogs.PopTip$DialogImpl r0 = r7.getDialogImpl()
            android.widget.LinearLayout r0 = r0.boxBody
            com.kongzue.dialogx.dialogs.PopTip$DialogImpl r1 = r7.getDialogImpl()
            if (r1 == 0) goto L_0x00cb
            if (r0 != 0) goto L_0x001e
            goto L_0x00cb
        L_0x001e:
            com.kongzue.dialogx.interfaces.DialogXStyle r1 = r7.style
            com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings r1 = r1.popTipSettings()
            if (r1 == 0) goto L_0x0032
            com.kongzue.dialogx.interfaces.DialogXStyle r1 = r7.style
            com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings r1 = r1.popTipSettings()
            com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings$ALIGN r1 = r1.align()
            r7.align = r1
        L_0x0032:
            com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings$ALIGN r1 = r7.align
            if (r1 != 0) goto L_0x003a
            com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopTipSettings.ALIGN.TOP
            r7.align = r1
        L_0x003a:
            r1 = 0
            int[] r2 = com.kongzue.dialogx.dialogs.PopTip.C20366.f936x462451d5
            com.kongzue.dialogx.interfaces.DialogXStyle$PopTipSettings$ALIGN r3 = r7.align
            int r3 = r3.ordinal()
            r2 = r2[r3]
            r3 = 1067869798(0x3fa66666, float:1.3)
            r4 = 2
            r5 = 1
            if (r2 == r5) goto L_0x0075
            if (r2 == r4) goto L_0x0068
            r6 = 3
            if (r2 == r6) goto L_0x0068
            r6 = 4
            if (r2 == r6) goto L_0x0058
            r6 = 5
            if (r2 == r6) goto L_0x0068
            goto L_0x0081
        L_0x0058:
            float r1 = r0.getY()
            int r2 = r0.getHeight()
            float r2 = (float) r2
            float r1 = r1 + r2
            int r2 = r0.getPaddingTop()
            float r2 = (float) r2
            goto L_0x0073
        L_0x0068:
            float r1 = r0.getY()
            int r2 = r0.getHeight()
            float r2 = (float) r2
            float r2 = r2 * r3
        L_0x0073:
            float r1 = r1 - r2
            goto L_0x0081
        L_0x0075:
            float r1 = r0.getY()
            int r2 = r0.getHeight()
            float r2 = (float) r2
            float r2 = r2 * r3
            float r1 = r1 + r2
        L_0x0081:
            java.lang.Object r2 = r0.getTag()
            boolean r2 = r2 instanceof android.animation.ValueAnimator
            if (r2 == 0) goto L_0x0092
            java.lang.Object r2 = r0.getTag()
            android.animation.ValueAnimator r2 = (android.animation.ValueAnimator) r2
            r2.end()
        L_0x0092:
            float[] r2 = new float[r4]
            r3 = 0
            float r4 = r0.getY()
            r2[r3] = r4
            r2[r5] = r1
            android.animation.ValueAnimator r1 = android.animation.ValueAnimator.ofFloat(r2)
            r0.setTag(r1)
            com.kongzue.dialogx.dialogs.PopTip$2 r0 = new com.kongzue.dialogx.dialogs.PopTip$2
            r0.<init>()
            r1.addUpdateListener(r0)
            long r2 = r7.enterAnimDuration
            r4 = -1
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x00b7
            r2 = 300(0x12c, double:1.48E-321)
            goto L_0x00b9
        L_0x00b7:
            long r2 = r7.enterAnimDuration
        L_0x00b9:
            android.animation.ValueAnimator r0 = r1.setDuration(r2)
            android.view.animation.DecelerateInterpolator r2 = new android.view.animation.DecelerateInterpolator
            r3 = 1073741824(0x40000000, float:2.0)
            r2.<init>(r3)
            r0.setInterpolator(r2)
            r1.start()
        L_0x00cb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.dialogs.PopTip.moveUp():void");
    }

    public void refreshUI() {
        if (getDialogImpl() != null) {
            runOnMain(new Runnable() {
                public void run() {
                    if (PopTip.this.dialogImpl != null) {
                        PopTip.this.dialogImpl.refreshView();
                    }
                }
            });
        }
    }

    public void dismiss() {
        runOnMain(new Runnable() {
            public void run() {
                if (PopTip.this.dialogImpl != null) {
                    PopTip.this.dialogImpl.doDismiss((View) null);
                }
            }
        });
    }

    public DialogLifecycleCallback<PopTip> getDialogLifecycleCallback() {
        DialogLifecycleCallback<PopTip> dialogLifecycleCallback2 = this.dialogLifecycleCallback;
        return dialogLifecycleCallback2 == null ? new DialogLifecycleCallback<PopTip>() {
        } : dialogLifecycleCallback2;
    }

    public PopTip setDialogLifecycleCallback(DialogLifecycleCallback<PopTip> dialogLifecycleCallback2) {
        this.dialogLifecycleCallback = dialogLifecycleCallback2;
        if (this.isShow) {
            dialogLifecycleCallback2.onShow(this.f935me);
        }
        return this;
    }

    public PopTip setStyle(DialogXStyle dialogXStyle) {
        this.style = dialogXStyle;
        return this;
    }

    public PopTip setTheme(DialogX.THEME theme) {
        this.theme = theme;
        return this;
    }

    public DialogImpl getDialogImpl() {
        return this.dialogImpl;
    }

    public PopTip setCustomView(OnBindView<PopTip> onBindView2) {
        this.onBindView = onBindView2;
        refreshUI();
        return this;
    }

    public View getCustomView() {
        OnBindView<PopTip> onBindView2 = this.onBindView;
        if (onBindView2 == null) {
            return null;
        }
        return onBindView2.getCustomView();
    }

    public PopTip removeCustomView() {
        this.onBindView.clean();
        refreshUI();
        return this;
    }

    public DialogXStyle.PopTipSettings.ALIGN getAlign() {
        return this.align;
    }

    @Deprecated
    public PopTip setAlign(DialogXStyle.PopTipSettings.ALIGN align2) {
        this.align = align2;
        return this;
    }

    public int getIconResId() {
        return this.iconResId;
    }

    public PopTip setIconResId(int i) {
        this.iconResId = i;
        refreshUI();
        return this;
    }

    public CharSequence getMessage() {
        return this.message;
    }

    public PopTip setMessage(CharSequence charSequence) {
        this.message = charSequence;
        refreshUI();
        return this;
    }

    public PopTip setMessage(int i) {
        this.message = getString(i);
        refreshUI();
        return this;
    }

    public CharSequence getButtonText() {
        return this.buttonText;
    }

    public PopTip setButton(CharSequence charSequence) {
        this.buttonText = charSequence;
        refreshUI();
        return this;
    }

    public PopTip setButton(int i) {
        this.buttonText = getString(i);
        refreshUI();
        return this;
    }

    public PopTip setButton(CharSequence charSequence, OnDialogButtonClickListener<PopTip> onDialogButtonClickListener) {
        this.buttonText = charSequence;
        this.onButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public PopTip setButton(int i, OnDialogButtonClickListener<PopTip> onDialogButtonClickListener) {
        this.buttonText = getString(i);
        this.onButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public PopTip setButton(OnDialogButtonClickListener<PopTip> onDialogButtonClickListener) {
        this.onButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public TextInfo getMessageTextInfo() {
        return this.messageTextInfo;
    }

    public PopTip setMessageTextInfo(TextInfo textInfo) {
        this.messageTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getButtonTextInfo() {
        return this.buttonTextInfo;
    }

    public PopTip setButtonTextInfo(TextInfo textInfo) {
        this.buttonTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public OnDialogButtonClickListener<PopTip> getOnButtonClickListener() {
        return this.onButtonClickListener;
    }

    public PopTip setOnButtonClickListener(OnDialogButtonClickListener<PopTip> onDialogButtonClickListener) {
        this.onButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    @Deprecated
    public boolean isAutoTintIconInLightOrDarkMode() {
        return isTintIcon();
    }

    @Deprecated
    public PopTip setAutoTintIconInLightOrDarkMode(boolean z) {
        setTintIcon(z);
        return this;
    }

    public OnDialogButtonClickListener<PopTip> getOnPopTipClickListener() {
        return this.onPopTipClickListener;
    }

    public PopTip setOnPopTipClickListener(OnDialogButtonClickListener<PopTip> onDialogButtonClickListener) {
        this.onPopTipClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public PopTip setBackgroundColor(int i) {
        this.backgroundColor = i;
        refreshUI();
        return this;
    }

    public PopTip setBackgroundColorRes(int i) {
        this.backgroundColor = getColor(i);
        refreshUI();
        return this;
    }

    public long getEnterAnimDuration() {
        return this.enterAnimDuration;
    }

    public PopTip setEnterAnimDuration(long j) {
        this.enterAnimDuration = j;
        return this;
    }

    public long getExitAnimDuration() {
        return this.exitAnimDuration;
    }

    public PopTip setExitAnimDuration(long j) {
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
        if (DialogX.onlyOnePopTip) {
            PopTip popTip = null;
            List<PopTip> list = popTipList;
            if (list != null && !list.isEmpty()) {
                List<PopTip> list2 = popTipList;
                popTip = list2.get(list2.size() - 1);
            }
            if (popTip != null) {
                popTip.dismiss();
            }
        } else if (popTipList != null) {
            for (int i = 0; i < popTipList.size(); i++) {
                popTipList.get(i).moveUp();
            }
        }
        if (popTipList == null) {
            popTipList = new ArrayList();
        }
        popTipList.add(this);
        int i2 = isLightTheme() ? C1903R.layout.layout_dialogx_poptip_material : C1903R.layout.layout_dialogx_poptip_material_dark;
        if (this.style.popTipSettings() != null) {
            if (this.style.popTipSettings().layout(isLightTheme()) != 0) {
                i2 = this.style.popTipSettings().layout(isLightTheme());
            }
            if (this.align == null) {
                if (this.style.popTipSettings().align() == null) {
                    this.align = DialogXStyle.PopTipSettings.ALIGN.BOTTOM;
                } else {
                    this.align = this.style.popTipSettings().align();
                }
            }
            int enterAnimResId2 = this.style.popTipSettings().enterAnimResId(isLightTheme());
            int exitAnimResId2 = this.style.popTipSettings().exitAnimResId(isLightTheme());
            int i3 = this.enterAnimResId;
            if (i3 != 0 || (i3 = overrideEnterAnimRes) != 0) {
                enterAnimResId2 = i3;
            } else if (enterAnimResId2 == 0) {
                enterAnimResId2 = C1903R.C1904anim.anim_dialogx_default_enter;
            }
            this.enterAnimResId = enterAnimResId2;
            int i4 = this.exitAnimResId;
            if (i4 != 0 || (i4 = overrideExitAnimRes) != 0) {
                exitAnimResId2 = i4;
            } else if (exitAnimResId2 == 0) {
                exitAnimResId2 = C1903R.C1904anim.anim_dialogx_default_exit;
            }
            this.exitAnimResId = exitAnimResId2;
            this.enterAnimDuration = this.enterAnimDuration == -1 ? overrideEnterDuration : this.enterAnimDuration;
            this.exitAnimDuration = this.exitAnimDuration == -1 ? overrideExitDuration : this.exitAnimDuration;
        }
        this.enterAnimDuration = 0;
        View createView = createView(i2);
        this.dialogView = createView;
        this.dialogImpl = new DialogImpl(createView);
        View view2 = this.dialogView;
        if (view2 != null) {
            view2.setTag(this.f935me);
        }
        show(this.dialogView);
    }

    public void hide() {
        this.isHide = true;
        if (getDialogView() != null) {
            getDialogView().setVisibility(8);
        }
    }

    public PopTip setAnimResId(int i, int i2) {
        this.enterAnimResId = i;
        this.exitAnimResId = i2;
        return this;
    }

    public PopTip setEnterAnimResId(int i) {
        this.enterAnimResId = i;
        return this;
    }

    public PopTip setExitAnimResId(int i) {
        this.exitAnimResId = i;
        return this;
    }

    /* access modifiers changed from: protected */
    public void shutdown() {
        dismiss();
    }

    public PopTip setDialogImplMode(DialogX.IMPL_MODE impl_mode) {
        this.dialogImplMode = impl_mode;
        return this;
    }

    public PopTip setMargin(int i, int i2, int i3, int i4) {
        int[] iArr = this.bodyMargin;
        iArr[0] = i;
        iArr[1] = i2;
        iArr[2] = i3;
        iArr[3] = i4;
        refreshUI();
        return this;
    }

    public PopTip setMarginLeft(int i) {
        this.bodyMargin[0] = i;
        refreshUI();
        return this;
    }

    public PopTip setMarginTop(int i) {
        this.bodyMargin[1] = i;
        refreshUI();
        return this;
    }

    public PopTip setMarginRight(int i) {
        this.bodyMargin[2] = i;
        refreshUI();
        return this;
    }

    public PopTip setMarginBottom(int i) {
        this.bodyMargin[3] = i;
        refreshUI();
        return this;
    }

    public int getMarginLeft() {
        return this.bodyMargin[0];
    }

    public int getMarginTop() {
        return this.bodyMargin[1];
    }

    public int getMarginRight() {
        return this.bodyMargin[2];
    }

    public int getMarginBottom() {
        return this.bodyMargin[3];
    }

    public PopTip iconSuccess() {
        setTintIcon(false);
        int i = C1903R.mipmap.ico_dialogx_success;
        if (!(getStyle().popTipSettings() == null || getStyle().popTipSettings().defaultIconSuccess() == 0)) {
            i = getStyle().popTipSettings().defaultIconSuccess();
        }
        setIconResId(i);
        return this;
    }

    public PopTip iconWarning() {
        setTintIcon(false);
        int i = C1903R.mipmap.ico_dialogx_warning;
        if (!(getStyle().popTipSettings() == null || getStyle().popTipSettings().defaultIconWarning() == 0)) {
            i = getStyle().popTipSettings().defaultIconWarning();
        }
        setIconResId(i);
        return this;
    }

    public PopTip iconError() {
        setTintIcon(false);
        int i = C1903R.mipmap.ico_dialogx_error;
        if (!(getStyle().popTipSettings() == null || getStyle().popTipSettings().defaultIconError() == 0)) {
            i = getStyle().popTipSettings().defaultIconError();
        }
        setIconResId(i);
        return this;
    }

    public boolean isTintIcon() {
        if (this.tintIcon != null || getStyle().popTipSettings() == null) {
            return this.tintIcon == BaseDialog.BOOLEAN.TRUE;
        }
        return getStyle().popTipSettings().tintIcon();
    }

    public PopTip setTintIcon(boolean z) {
        this.tintIcon = z ? BaseDialog.BOOLEAN.TRUE : BaseDialog.BOOLEAN.FALSE;
        refreshUI();
        return this;
    }

    public PopTip setRadius(float f) {
        this.backgroundRadius = f;
        refreshUI();
        return this;
    }

    public float getRadius() {
        return this.backgroundRadius;
    }

    public DialogXAnimInterface<PopTip> getDialogXAnimImpl() {
        return this.dialogXAnimImpl;
    }

    public PopTip setDialogXAnimImpl(DialogXAnimInterface<PopTip> dialogXAnimInterface) {
        this.dialogXAnimImpl = dialogXAnimInterface;
        return this;
    }
}
