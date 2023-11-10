package com.kongzue.dialogx.dialogs;

import android.animation.Animator;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
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
import com.kongzue.dialogx.util.views.BlurView;
import com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout;
import com.kongzue.dialogx.util.views.MaxRelativeLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class PopNotification extends BaseDialog implements NoTouchInterface {
    public static final int TIME_NO_AUTO_DISMISS_DELAY = -1;
    public static int overrideEnterAnimRes = 0;
    public static long overrideEnterDuration = -1;
    public static int overrideExitAnimRes = 0;
    public static long overrideExitDuration = -1;
    protected static List<PopNotification> popNotificationList;
    protected DialogXStyle.PopNotificationSettings.ALIGN align;
    protected long autoDismissDelay;
    protected Timer autoDismissTimer;
    protected boolean autoTintIconInLightOrDarkMode = true;
    protected float backgroundRadius = -1.0f;
    protected int[] bodyMargin = {-1, -1, -1, -1};
    protected CharSequence buttonText;
    protected TextInfo buttonTextInfo = new TextInfo().setBold(true);
    protected DialogImpl dialogImpl;
    protected DialogLifecycleCallback<PopNotification> dialogLifecycleCallback;
    private View dialogView;
    protected DialogXAnimInterface<PopNotification> dialogXAnimImpl;
    protected int enterAnimResId = 0;
    protected int exitAnimResId = 0;
    protected Bitmap iconBitmap;
    protected Drawable iconDrawable;
    protected int iconResId;
    protected int iconSize;
    private boolean isHide;

    /* renamed from: me */
    protected PopNotification f933me = this;
    protected CharSequence message;
    protected TextInfo messageTextInfo;
    protected OnBindView<PopNotification> onBindView;
    protected OnDialogButtonClickListener<PopNotification> onButtonClickListener;
    protected OnDialogButtonClickListener<PopNotification> onPopNotificationClickListener;
    protected boolean preRecycle = false;
    protected BaseDialog.BOOLEAN tintIcon;
    protected CharSequence title;
    protected TextInfo titleTextInfo;

    public boolean isCancelable() {
        return false;
    }

    protected PopNotification() {
    }

    public static PopNotification build() {
        return new PopNotification();
    }

    public static PopNotification build(DialogXStyle dialogXStyle) {
        return new PopNotification().setStyle(dialogXStyle);
    }

    public static PopNotification build(OnBindView<PopNotification> onBindView2) {
        return new PopNotification().setCustomView(onBindView2);
    }

    public PopNotification(OnBindView<PopNotification> onBindView2) {
        this.onBindView = onBindView2;
    }

    public PopNotification(CharSequence charSequence) {
        this.title = charSequence;
    }

    public PopNotification(CharSequence charSequence, CharSequence charSequence2) {
        this.title = charSequence;
        this.message = charSequence2;
    }

    public PopNotification(int i) {
        this.title = getString(i);
    }

    public PopNotification(int i, int i2) {
        this.title = getString(i);
        this.message = getString(i2);
    }

    public PopNotification(int i, CharSequence charSequence) {
        this.iconResId = i;
        this.title = charSequence;
    }

    public PopNotification(int i, CharSequence charSequence, CharSequence charSequence2) {
        this.iconResId = i;
        this.title = charSequence;
        this.message = charSequence2;
    }

    public PopNotification(int i, int i2, int i3) {
        this.iconResId = i;
        this.title = getString(i2);
        this.message = getString(i3);
    }

    public PopNotification(CharSequence charSequence, OnBindView<PopNotification> onBindView2) {
        this.title = charSequence;
        this.onBindView = onBindView2;
    }

    public PopNotification(CharSequence charSequence, CharSequence charSequence2, OnBindView<PopNotification> onBindView2) {
        this.title = charSequence;
        this.message = charSequence2;
        this.onBindView = onBindView2;
    }

    public PopNotification(int i, OnBindView<PopNotification> onBindView2) {
        this.title = getString(i);
        this.onBindView = onBindView2;
    }

    public PopNotification(int i, int i2, OnBindView<PopNotification> onBindView2) {
        this.title = getString(i);
        this.message = getString(i2);
        this.onBindView = onBindView2;
    }

    public PopNotification(int i, CharSequence charSequence, OnBindView<PopNotification> onBindView2) {
        this.iconResId = i;
        this.title = charSequence;
        this.onBindView = onBindView2;
    }

    public PopNotification(int i, CharSequence charSequence, CharSequence charSequence2, OnBindView<PopNotification> onBindView2) {
        this.iconResId = i;
        this.title = charSequence;
        this.message = charSequence2;
        this.onBindView = onBindView2;
    }

    public PopNotification(int i, int i2, int i3, OnBindView<PopNotification> onBindView2) {
        this.iconResId = i;
        this.title = getString(i2);
        this.message = getString(i3);
        this.onBindView = onBindView2;
    }

    public PopNotification(int i, int i2, int i3, int i4) {
        this.iconResId = i;
        this.title = getString(i2);
        this.message = getString(i3);
        this.buttonText = getString(i4);
    }

    public PopNotification(int i, int i2, int i3, int i4, OnBindView<PopNotification> onBindView2) {
        this.iconResId = i;
        this.title = getString(i2);
        this.message = getString(i3);
        this.buttonText = getString(i4);
        this.onBindView = onBindView2;
    }

    public PopNotification(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        this.iconResId = i;
        this.title = charSequence;
        this.message = charSequence2;
        this.buttonText = charSequence3;
    }

    public PopNotification(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, OnBindView<PopNotification> onBindView2) {
        this.iconResId = i;
        this.title = charSequence;
        this.message = charSequence2;
        this.buttonText = charSequence3;
        this.onBindView = onBindView2;
    }

    public static PopNotification show(OnBindView<PopNotification> onBindView2) {
        PopNotification popNotification = new PopNotification(onBindView2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(CharSequence charSequence) {
        PopNotification popNotification = new PopNotification(charSequence);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(CharSequence charSequence, CharSequence charSequence2) {
        PopNotification popNotification = new PopNotification(charSequence, charSequence2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i) {
        PopNotification popNotification = new PopNotification(i);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, int i2) {
        PopNotification popNotification = new PopNotification(i, i2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(CharSequence charSequence, OnBindView<PopNotification> onBindView2) {
        PopNotification popNotification = new PopNotification(charSequence, onBindView2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(CharSequence charSequence, CharSequence charSequence2, OnBindView<PopNotification> onBindView2) {
        PopNotification popNotification = new PopNotification(charSequence, charSequence2, onBindView2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, OnBindView<PopNotification> onBindView2) {
        PopNotification popNotification = new PopNotification(i, onBindView2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, int i2, OnBindView<PopNotification> onBindView2) {
        PopNotification popNotification = new PopNotification(i, i2, onBindView2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, CharSequence charSequence, OnBindView<PopNotification> onBindView2) {
        PopNotification popNotification = new PopNotification(i, charSequence, onBindView2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, CharSequence charSequence, CharSequence charSequence2, OnBindView<PopNotification> onBindView2) {
        PopNotification popNotification = new PopNotification(i, charSequence, charSequence2, onBindView2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, CharSequence charSequence) {
        PopNotification popNotification = new PopNotification(i, charSequence);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, CharSequence charSequence, CharSequence charSequence2) {
        PopNotification popNotification = new PopNotification(i, charSequence, charSequence2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, int i2, int i3) {
        PopNotification popNotification = new PopNotification(i, i2, i3);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, int i2, int i3, OnBindView<PopNotification> onBindView2) {
        PopNotification popNotification = new PopNotification(i, i2, i3, onBindView2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, int i2, int i3, int i4) {
        PopNotification popNotification = new PopNotification(i, i2, i3, i4);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, int i2, int i3, int i4, OnBindView<PopNotification> onBindView2) {
        PopNotification popNotification = new PopNotification(i, i2, i3, i4, onBindView2);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        PopNotification popNotification = new PopNotification(i, charSequence, charSequence2, charSequence3);
        popNotification.show();
        return popNotification;
    }

    public static PopNotification show(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, OnBindView<PopNotification> onBindView2) {
        PopNotification popNotification = new PopNotification(i, charSequence, charSequence2, charSequence3, onBindView2);
        popNotification.show();
        return popNotification;
    }

    public PopNotification show() {
        if (!this.isHide || getDialogView() == null) {
            super.beforeShow();
            if (getDialogView() == null) {
                if (DialogX.onlyOnePopNotification) {
                    PopNotification popNotification = null;
                    List<PopNotification> list = popNotificationList;
                    if (list != null && !list.isEmpty()) {
                        List<PopNotification> list2 = popNotificationList;
                        popNotification = list2.get(list2.size() - 1);
                    }
                    if (popNotification != null) {
                        popNotification.dismiss();
                    }
                }
                if (popNotificationList == null) {
                    popNotificationList = new ArrayList();
                }
                popNotificationList.add(this);
                int i = isLightTheme() ? C1903R.layout.layout_dialogx_popnotification_material : C1903R.layout.layout_dialogx_popnotification_material_dark;
                if (this.style.popNotificationSettings() != null) {
                    if (this.style.popNotificationSettings().layout(isLightTheme()) != 0) {
                        i = this.style.popNotificationSettings().layout(isLightTheme());
                    }
                    DialogXStyle.PopNotificationSettings.ALIGN align2 = this.style.popNotificationSettings().align();
                    this.align = align2;
                    if (align2 == null) {
                        this.align = DialogXStyle.PopNotificationSettings.ALIGN.TOP;
                    }
                    int enterAnimResId2 = this.style.popNotificationSettings().enterAnimResId(isLightTheme());
                    int exitAnimResId2 = this.style.popNotificationSettings().exitAnimResId(isLightTheme());
                    int i2 = this.enterAnimResId;
                    if (i2 != 0 || (i2 = overrideEnterAnimRes) != 0) {
                        enterAnimResId2 = i2;
                    } else if (enterAnimResId2 == 0) {
                        enterAnimResId2 = C1903R.C1904anim.anim_dialogx_notification_enter;
                    }
                    this.enterAnimResId = enterAnimResId2;
                    int i3 = this.exitAnimResId;
                    if (i3 != 0 || (i3 = overrideExitAnimRes) != 0) {
                        exitAnimResId2 = i3;
                    } else if (exitAnimResId2 == 0) {
                        exitAnimResId2 = C1903R.C1904anim.anim_dialogx_notification_exit;
                    }
                    this.exitAnimResId = exitAnimResId2;
                    this.enterAnimDuration = this.enterAnimDuration == -1 ? overrideEnterDuration : this.enterAnimDuration;
                    this.exitAnimDuration = this.exitAnimDuration == -1 ? overrideExitDuration : this.exitAnimDuration;
                }
                View createView = createView(i);
                this.dialogView = createView;
                this.dialogImpl = new DialogImpl(createView);
                View view = this.dialogView;
                if (view != null) {
                    view.setTag(this.f933me);
                }
            }
            show(this.dialogView);
            return this;
        }
        getDialogView().setVisibility(0);
        return this;
    }

    public PopNotification show(Activity activity) {
        super.beforeShow();
        if (this.dialogView != null) {
            if (DialogX.onlyOnePopNotification) {
                PopNotification popNotification = null;
                List<PopNotification> list = popNotificationList;
                if (list != null && !list.isEmpty()) {
                    List<PopNotification> list2 = popNotificationList;
                    popNotification = list2.get(list2.size() - 1);
                }
                if (popNotification != null) {
                    popNotification.dismiss();
                }
            }
            if (popNotificationList == null) {
                popNotificationList = new ArrayList();
            }
            popNotificationList.add(this);
            int i = isLightTheme() ? C1903R.layout.layout_dialogx_popnotification_material : C1903R.layout.layout_dialogx_popnotification_material_dark;
            if (this.style.popNotificationSettings() != null) {
                if (this.style.popNotificationSettings().layout(isLightTheme()) != 0) {
                    i = this.style.popNotificationSettings().layout(isLightTheme());
                }
                DialogXStyle.PopNotificationSettings.ALIGN align2 = this.style.popNotificationSettings().align();
                this.align = align2;
                if (align2 == null) {
                    this.align = DialogXStyle.PopNotificationSettings.ALIGN.TOP;
                }
                int enterAnimResId2 = this.style.popNotificationSettings().enterAnimResId(isLightTheme());
                int exitAnimResId2 = this.style.popNotificationSettings().exitAnimResId(isLightTheme());
                int i2 = this.enterAnimResId;
                if (i2 != 0 || (i2 = overrideEnterAnimRes) != 0) {
                    enterAnimResId2 = i2;
                } else if (enterAnimResId2 == 0) {
                    enterAnimResId2 = C1903R.C1904anim.anim_dialogx_notification_enter;
                }
                this.enterAnimResId = enterAnimResId2;
                int i3 = this.exitAnimResId;
                if (i3 != 0 || (i3 = overrideExitAnimRes) != 0) {
                    exitAnimResId2 = i3;
                } else if (exitAnimResId2 == 0) {
                    exitAnimResId2 = C1903R.C1904anim.anim_dialogx_notification_exit;
                }
                this.exitAnimResId = exitAnimResId2;
                this.enterAnimDuration = this.enterAnimDuration == -1 ? overrideEnterDuration : this.enterAnimDuration;
                this.exitAnimDuration = this.exitAnimDuration == -1 ? overrideExitDuration : this.exitAnimDuration;
            }
            View createView = createView(i);
            this.dialogView = createView;
            this.dialogImpl = new DialogImpl(createView);
            View view = this.dialogView;
            if (view != null) {
                view.setTag(this.f933me);
            }
        }
        show(activity, this.dialogView);
        return this;
    }

    public String dialogKey() {
        return getClass().getSimpleName() + "(" + Integer.toHexString(hashCode()) + ")";
    }

    public PopNotification autoDismiss(long j) {
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
                PopNotification.this.dismiss();
            }
        }, j);
        return this;
    }

    public void resetAutoDismissTimer() {
        autoDismiss(this.autoDismissDelay);
    }

    public PopNotification showShort() {
        autoDismiss(2000);
        if (!this.preShow && !this.isShow) {
            show();
        }
        return this;
    }

    public PopNotification showLong() {
        autoDismiss(3500);
        if (!this.preShow && !this.isShow) {
            show();
        }
        return this;
    }

    public PopNotification showAlways() {
        return noAutoDismiss();
    }

    public PopNotification noAutoDismiss() {
        autoDismiss(-1);
        return this;
    }

    public class DialogImpl implements DialogConvertViewInterface {
        public BlurView blurView;
        /* access modifiers changed from: private */
        public ViewGroup boxBody;
        /* access modifiers changed from: private */
        public RelativeLayout boxCustom;
        /* access modifiers changed from: private */
        public DialogXBaseRelativeLayout boxRoot;
        private ImageView imgDialogxPopIcon;
        private TextView txtDialogxButton;
        private TextView txtDialogxPopMessage;
        private TextView txtDialogxPopTitle;

        public DialogImpl(View view) {
            if (view != null) {
                this.boxRoot = (DialogXBaseRelativeLayout) view.findViewById(C1903R.C1907id.box_root);
                this.boxBody = (ViewGroup) view.findViewById(C1903R.C1907id.box_body);
                this.imgDialogxPopIcon = (ImageView) view.findViewById(C1903R.C1907id.img_dialogx_pop_icon);
                this.txtDialogxPopTitle = (TextView) view.findViewById(C1903R.C1907id.txt_dialogx_pop_title);
                this.txtDialogxPopMessage = (TextView) view.findViewById(C1903R.C1907id.txt_dialogx_pop_message);
                this.txtDialogxButton = (TextView) view.findViewById(C1903R.C1907id.txt_dialogx_button);
                this.boxCustom = (RelativeLayout) view.findViewById(C1903R.C1907id.box_custom);
                init();
                PopNotification.this.dialogImpl = this;
                refreshView();
            }
        }

        public void init() {
            if (PopNotification.this.titleTextInfo == null) {
                PopNotification.this.titleTextInfo = DialogX.titleTextInfo;
            }
            if (PopNotification.this.messageTextInfo == null) {
                PopNotification.this.messageTextInfo = DialogX.messageTextInfo;
            }
            if (PopNotification.this.buttonTextInfo == null) {
                PopNotification.this.buttonTextInfo = DialogX.buttonTextInfo;
            }
            if (PopNotification.this.backgroundColor == -1) {
                int unused = PopNotification.this.backgroundColor = DialogX.backgroundColor;
            }
            if (PopNotification.this.autoDismissTimer == null) {
                PopNotification.this.showShort();
            }
            this.boxRoot.setClickable(false);
            this.boxRoot.setFocusable(false);
            this.boxRoot.setParentDialog(PopNotification.this.f933me);
            this.boxRoot.setAutoUnsafePlacePadding(false);
            this.boxRoot.setOnLifecycleCallBack(new DialogXBaseRelativeLayout.OnLifecycleCallBack() {
                public void onShow() {
                    boolean unused = PopNotification.this.isShow = true;
                    boolean unused2 = PopNotification.this.preShow = false;
                    PopNotification.this.lifecycle.setCurrentState(Lifecycle.State.CREATED);
                    DialogImpl.this.boxRoot.setAlpha(0.0f);
                    PopNotification.this.onDialogShow();
                    PopNotification.this.getDialogLifecycleCallback().onShow(PopNotification.this.f933me);
                }

                public void onDismiss() {
                    if (PopNotification.popNotificationList != null) {
                        PopNotification.popNotificationList.remove(PopNotification.this);
                    }
                    boolean unused = PopNotification.this.isShow = false;
                    PopNotification.this.getDialogLifecycleCallback().onDismiss(PopNotification.this.f933me);
                    PopNotification.this.dialogImpl = null;
                    PopNotification.this.lifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                    System.gc();
                }
            });
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.boxBody.getLayoutParams();
            if (PopNotification.this.align == null) {
                PopNotification.this.align = DialogXStyle.PopNotificationSettings.ALIGN.TOP;
            }
            int i = C20186.f934x81ac079[PopNotification.this.align.ordinal()];
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
                    if (PopNotification.this.align == DialogXStyle.PopNotificationSettings.ALIGN.TOP) {
                        DialogImpl.this.boxBody.setY((float) (rect.top + PopNotification.this.bodyMargin[1]));
                    } else if (PopNotification.this.align == DialogXStyle.PopNotificationSettings.ALIGN.TOP_INSIDE) {
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
                    DialogImpl.this.getDialogXAnimImpl().doShowAnim(PopNotification.this.f933me, new ObjectRunnable<Float>() {
                        public void run(Float f) {
                        }
                    });
                    if (!DialogX.onlyOnePopNotification && PopNotification.popNotificationList != null) {
                        for (int i = 0; i < PopNotification.popNotificationList.size() - 1; i++) {
                            PopNotification.popNotificationList.get(i).moveUp(DialogImpl.this.boxBody.getHeight());
                        }
                    }
                    if (!(PopNotification.this.getStyle().popNotificationSettings() == null || PopNotification.this.getStyle().popNotificationSettings().blurBackgroundSettings() == null || !PopNotification.this.getStyle().popNotificationSettings().blurBackgroundSettings().blurBackground())) {
                        MaxRelativeLayout maxRelativeLayout = (MaxRelativeLayout) DialogImpl.this.boxRoot.findViewWithTag("blurBody");
                        int color = PopNotification.this.getResources().getColor(PopNotification.this.getStyle().popNotificationSettings().blurBackgroundSettings().blurForwardColorRes(PopNotification.this.isLightTheme()));
                        DialogImpl.this.blurView = new BlurView(maxRelativeLayout.getContext(), (AttributeSet) null);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DialogImpl.this.boxBody.getWidth(), DialogImpl.this.boxBody.getHeight());
                        BlurView blurView = DialogImpl.this.blurView;
                        if (PopNotification.this.backgroundColor != -1) {
                            color = PopNotification.this.backgroundColor;
                        }
                        blurView.setOverlayColor(color);
                        DialogImpl.this.blurView.setTag("blurView");
                        DialogImpl.this.blurView.setRadiusPx((float) PopNotification.this.getStyle().popNotificationSettings().blurBackgroundSettings().blurBackgroundRoundRadiusPx());
                        maxRelativeLayout.setContentView(DialogImpl.this.boxBody);
                        maxRelativeLayout.addView(DialogImpl.this.blurView, 0, layoutParams);
                    }
                    PopNotification.this.lifecycle.setCurrentState(Lifecycle.State.RESUMED);
                }
            });
            this.txtDialogxButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (PopNotification.this.onButtonClickListener == null) {
                        DialogImpl.this.doDismiss(view);
                    } else if (!PopNotification.this.onButtonClickListener.onClick(PopNotification.this.f933me, view)) {
                        DialogImpl.this.doDismiss(view);
                    }
                }
            });
            PopNotification.this.onDialogInit();
        }

        public void refreshView() {
            if (this.boxRoot != null && BaseDialog.getTopActivity() != null) {
                if (PopNotification.this.backgroundColor != -1) {
                    PopNotification popNotification = PopNotification.this;
                    popNotification.tintColor(this.boxBody, popNotification.backgroundColor);
                }
                if (PopNotification.this.onBindView == null || PopNotification.this.onBindView.getCustomView() == null) {
                    this.boxCustom.setVisibility(8);
                } else {
                    PopNotification.this.onBindView.bindParent(this.boxCustom, PopNotification.this.f933me);
                    this.boxCustom.setVisibility(0);
                }
                if (PopNotification.this.backgroundRadius > -1.0f) {
                    GradientDrawable gradientDrawable = (GradientDrawable) this.boxBody.getBackground();
                    if (gradientDrawable != null) {
                        gradientDrawable.setCornerRadius(PopNotification.this.backgroundRadius);
                    }
                    if (Build.VERSION.SDK_INT >= 21) {
                        this.boxBody.setOutlineProvider(new ViewOutlineProvider() {
                            public void getOutline(View view, Outline outline) {
                                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), PopNotification.this.backgroundRadius);
                            }
                        });
                        this.boxBody.setClipToOutline(true);
                    }
                }
                PopNotification popNotification2 = PopNotification.this;
                popNotification2.showText(this.txtDialogxPopTitle, popNotification2.title);
                PopNotification popNotification3 = PopNotification.this;
                popNotification3.showText(this.txtDialogxPopMessage, popNotification3.message);
                PopNotification popNotification4 = PopNotification.this;
                popNotification4.showText(this.txtDialogxButton, popNotification4.buttonText);
                BaseDialog.useTextInfo(this.txtDialogxPopTitle, PopNotification.this.titleTextInfo);
                BaseDialog.useTextInfo(this.txtDialogxPopMessage, PopNotification.this.messageTextInfo);
                BaseDialog.useTextInfo(this.txtDialogxButton, PopNotification.this.buttonTextInfo);
                if (PopNotification.this.iconBitmap != null && !PopNotification.this.iconBitmap.isRecycled()) {
                    this.imgDialogxPopIcon.setVisibility(0);
                    this.imgDialogxPopIcon.setImageBitmap(PopNotification.this.iconBitmap);
                } else if (PopNotification.this.iconDrawable != null) {
                    this.imgDialogxPopIcon.setVisibility(0);
                    this.imgDialogxPopIcon.setImageDrawable(PopNotification.this.iconDrawable);
                } else if (PopNotification.this.iconResId != 0) {
                    this.imgDialogxPopIcon.setVisibility(0);
                    this.imgDialogxPopIcon.setImageResource(PopNotification.this.iconResId);
                } else {
                    this.imgDialogxPopIcon.setVisibility(8);
                }
                if (Build.VERSION.SDK_INT < 21 || PopNotification.this.tintIcon != BaseDialog.BOOLEAN.TRUE) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        this.imgDialogxPopIcon.setImageTintList((ColorStateList) null);
                    }
                } else if (PopNotification.this.autoTintIconInLightOrDarkMode) {
                    this.imgDialogxPopIcon.setImageTintList(this.txtDialogxPopTitle.getTextColors());
                } else {
                    this.imgDialogxPopIcon.setImageTintList((ColorStateList) null);
                }
                if (PopNotification.this.iconSize > 0) {
                    ViewGroup.LayoutParams layoutParams = this.imgDialogxPopIcon.getLayoutParams();
                    layoutParams.width = PopNotification.this.iconSize;
                    layoutParams.height = PopNotification.this.iconSize;
                    this.imgDialogxPopIcon.setLayoutParams(layoutParams);
                }
                this.boxBody.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (PopNotification.this.onPopNotificationClickListener == null) {
                            PopNotification.this.dismiss();
                        } else if (!PopNotification.this.onPopNotificationClickListener.onClick(PopNotification.this.f933me, view)) {
                            PopNotification.this.dismiss();
                        }
                    }
                });
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.boxBody.getLayoutParams();
                if (PopNotification.this.bodyMargin[0] != -1) {
                    layoutParams2.leftMargin = PopNotification.this.bodyMargin[0];
                }
                if (PopNotification.this.bodyMargin[1] != -1) {
                    layoutParams2.topMargin = PopNotification.this.bodyMargin[1];
                }
                if (PopNotification.this.bodyMargin[2] != -1) {
                    layoutParams2.rightMargin = PopNotification.this.bodyMargin[2];
                }
                if (PopNotification.this.bodyMargin[3] != -1) {
                    layoutParams2.bottomMargin = PopNotification.this.bodyMargin[3];
                }
                this.boxBody.setLayoutParams(layoutParams2);
                PopNotification.this.onDialogRefreshUI();
            }
        }

        public void doDismiss(View view) {
            if (view != null) {
                view.setEnabled(false);
            }
            if (!PopNotification.this.dismissAnimFlag) {
                boolean unused = PopNotification.this.dismissAnimFlag = true;
                this.boxRoot.post(new Runnable() {
                    public void run() {
                        DialogImpl.this.getDialogXAnimImpl().doExitAnim(PopNotification.this.f933me, new ObjectRunnable<Float>() {
                            public void run(Float f) {
                                if (f.floatValue() == 0.0f) {
                                    PopNotification.this.waitForDismiss();
                                }
                            }
                        });
                    }
                });
            }
        }

        /* access modifiers changed from: protected */
        public DialogXAnimInterface<PopNotification> getDialogXAnimImpl() {
            if (PopNotification.this.dialogXAnimImpl == null) {
                PopNotification.this.dialogXAnimImpl = new DialogXAnimInterface<PopNotification>() {
                    public void doShowAnim(PopNotification popNotification, ObjectRunnable<Float> objectRunnable) {
                        Animation loadAnimation = AnimationUtils.loadAnimation(BaseDialog.getTopActivity(), PopNotification.this.enterAnimResId == 0 ? C1903R.C1904anim.anim_dialogx_notification_enter : PopNotification.this.enterAnimResId);
                        loadAnimation.setInterpolator(new DecelerateInterpolator(2.0f));
                        if (PopNotification.this.enterAnimDuration != -1) {
                            loadAnimation.setDuration(PopNotification.this.enterAnimDuration);
                        }
                        loadAnimation.setFillAfter(true);
                        DialogImpl.this.boxBody.startAnimation(loadAnimation);
                        DialogImpl.this.boxRoot.animate().setDuration(PopNotification.this.enterAnimDuration == -1 ? loadAnimation.getDuration() : PopNotification.this.enterAnimDuration).alpha(1.0f).setInterpolator(new DecelerateInterpolator()).setListener((Animator.AnimatorListener) null);
                    }

                    public void doExitAnim(PopNotification popNotification, final ObjectRunnable<Float> objectRunnable) {
                        Animation loadAnimation = AnimationUtils.loadAnimation(BaseDialog.getTopActivity() == null ? DialogImpl.this.boxRoot.getContext() : BaseDialog.getTopActivity(), PopNotification.this.exitAnimResId == 0 ? C1903R.C1904anim.anim_dialogx_notification_exit : PopNotification.this.exitAnimResId);
                        if (PopNotification.this.exitAnimDuration != -1) {
                            loadAnimation.setDuration(PopNotification.this.exitAnimDuration);
                        }
                        loadAnimation.setFillAfter(true);
                        DialogImpl.this.boxBody.startAnimation(loadAnimation);
                        DialogImpl.this.boxRoot.animate().alpha(0.0f).setInterpolator(new AccelerateInterpolator()).setDuration(PopNotification.this.exitAnimDuration == -1 ? loadAnimation.getDuration() : PopNotification.this.exitAnimDuration);
                        PopNotification.runOnMainDelay(new Runnable() {
                            public void run() {
                                objectRunnable.run(Float.valueOf(0.0f));
                            }
                        }, PopNotification.this.exitAnimDuration == -1 ? loadAnimation.getDuration() : PopNotification.this.exitAnimDuration);
                    }
                };
            }
            return PopNotification.this.dialogXAnimImpl;
        }
    }

    /* renamed from: com.kongzue.dialogx.dialogs.PopNotification$6 */
    static /* synthetic */ class C20186 {

        /* renamed from: $SwitchMap$com$kongzue$dialogx$interfaces$DialogXStyle$PopNotificationSettings$ALIGN */
        static final /* synthetic */ int[] f934x81ac079;

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
                com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings$ALIGN[] r0 = com.kongzue.dialogx.interfaces.DialogXStyle.PopNotificationSettings.ALIGN.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f934x81ac079 = r0
                com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopNotificationSettings.ALIGN.TOP     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f934x81ac079     // Catch:{ NoSuchFieldError -> 0x001d }
                com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopNotificationSettings.ALIGN.BOTTOM     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f934x81ac079     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopNotificationSettings.ALIGN.CENTER     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = f934x81ac079     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopNotificationSettings.ALIGN.TOP_INSIDE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = f934x81ac079     // Catch:{ NoSuchFieldError -> 0x003e }
                com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopNotificationSettings.ALIGN.BOTTOM_INSIDE     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.dialogs.PopNotification.C20186.<clinit>():void");
        }
    }

    /* access modifiers changed from: private */
    public void waitForDismiss() {
        this.preRecycle = true;
        List<PopNotification> list = popNotificationList;
        if (list != null) {
            for (PopNotification popNotification : list) {
                if (!popNotification.preRecycle) {
                    return;
                }
            }
            Iterator it = new CopyOnWriteArrayList(popNotificationList).iterator();
            while (it.hasNext()) {
                dismiss(((PopNotification) it.next()).dialogView);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0078, code lost:
        if (r3 != 5) goto L_0x008e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void moveUp(int r9) {
        /*
            r8 = this;
            com.kongzue.dialogx.dialogs.PopNotification$DialogImpl r0 = r8.getDialogImpl()
            if (r0 == 0) goto L_0x00c7
            com.kongzue.dialogx.dialogs.PopNotification$DialogImpl r0 = r8.getDialogImpl()
            android.view.ViewGroup r0 = r0.boxBody
            if (r0 == 0) goto L_0x00c7
            com.kongzue.dialogx.dialogs.PopNotification$DialogImpl r0 = r8.getDialogImpl()
            android.view.ViewGroup r0 = r0.boxBody
            com.kongzue.dialogx.dialogs.PopNotification$DialogImpl r1 = r8.getDialogImpl()
            if (r1 == 0) goto L_0x00c7
            if (r0 != 0) goto L_0x0022
            goto L_0x00c7
        L_0x0022:
            com.kongzue.dialogx.interfaces.DialogXStyle r1 = r8.style
            com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings r1 = r1.popNotificationSettings()
            if (r1 == 0) goto L_0x0036
            com.kongzue.dialogx.interfaces.DialogXStyle r1 = r8.style
            com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings r1 = r1.popNotificationSettings()
            com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings$ALIGN r1 = r1.align()
            r8.align = r1
        L_0x0036:
            com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings$ALIGN r1 = r8.align
            if (r1 != 0) goto L_0x003e
            com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings$ALIGN r1 = com.kongzue.dialogx.interfaces.DialogXStyle.PopNotificationSettings.ALIGN.TOP
            r8.align = r1
        L_0x003e:
            r1 = 0
            float r2 = r0.getY()
            java.lang.Object r3 = r0.getTag()
            boolean r3 = r3 instanceof com.kongzue.dialogx.util.PopValueAnimator
            if (r3 == 0) goto L_0x005e
            java.lang.Object r2 = r0.getTag()
            com.kongzue.dialogx.util.PopValueAnimator r2 = (com.kongzue.dialogx.util.PopValueAnimator) r2
            r2.end()
            java.lang.Object r2 = r0.getTag()
            com.kongzue.dialogx.util.PopValueAnimator r2 = (com.kongzue.dialogx.util.PopValueAnimator) r2
            float r2 = r2.getEndValue()
        L_0x005e:
            int[] r3 = com.kongzue.dialogx.dialogs.PopNotification.C20186.f934x81ac079
            com.kongzue.dialogx.interfaces.DialogXStyle$PopNotificationSettings$ALIGN r4 = r8.align
            int r4 = r4.ordinal()
            r3 = r3[r4]
            r4 = 1066192077(0x3f8ccccd, float:1.1)
            r5 = 2
            r6 = 1
            if (r3 == r6) goto L_0x0089
            if (r3 == r5) goto L_0x0083
            r7 = 3
            if (r3 == r7) goto L_0x0083
            r7 = 4
            if (r3 == r7) goto L_0x007b
            r7 = 5
            if (r3 == r7) goto L_0x0083
            goto L_0x008e
        L_0x007b:
            float r9 = (float) r9
            float r2 = r2 + r9
            int r9 = r0.getPaddingTop()
            float r9 = (float) r9
            goto L_0x0086
        L_0x0083:
            float r9 = (float) r9
            float r9 = r9 * r4
        L_0x0086:
            float r1 = r2 - r9
            goto L_0x008e
        L_0x0089:
            float r9 = (float) r9
            float r9 = r9 * r4
            float r1 = r2 + r9
        L_0x008e:
            float[] r9 = new float[r5]
            r2 = 0
            float r3 = r0.getY()
            r9[r2] = r3
            r9[r6] = r1
            com.kongzue.dialogx.util.PopValueAnimator r9 = com.kongzue.dialogx.util.PopValueAnimator.ofFloat(r9)
            r0.setTag(r9)
            com.kongzue.dialogx.dialogs.PopNotification$2 r0 = new com.kongzue.dialogx.dialogs.PopNotification$2
            r0.<init>()
            r9.addUpdateListener(r0)
            long r0 = r8.enterAnimDuration
            r2 = -1
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x00b3
            r0 = 300(0x12c, double:1.48E-321)
            goto L_0x00b5
        L_0x00b3:
            long r0 = r8.enterAnimDuration
        L_0x00b5:
            android.animation.ValueAnimator r0 = r9.setDuration(r0)
            android.view.animation.DecelerateInterpolator r1 = new android.view.animation.DecelerateInterpolator
            r2 = 1073741824(0x40000000, float:2.0)
            r1.<init>(r2)
            r0.setInterpolator(r1)
            r9.start()
        L_0x00c7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.dialogs.PopNotification.moveUp(int):void");
    }

    public void refreshUI() {
        if (getDialogImpl() != null) {
            runOnMain(new Runnable() {
                public void run() {
                    if (PopNotification.this.dialogImpl != null) {
                        PopNotification.this.dialogImpl.refreshView();
                    }
                }
            });
        }
    }

    public void dismiss() {
        runOnMain(new Runnable() {
            public void run() {
                if (PopNotification.this.dialogImpl != null) {
                    PopNotification.this.dialogImpl.doDismiss((View) null);
                }
            }
        });
    }

    public DialogLifecycleCallback<PopNotification> getDialogLifecycleCallback() {
        DialogLifecycleCallback<PopNotification> dialogLifecycleCallback2 = this.dialogLifecycleCallback;
        return dialogLifecycleCallback2 == null ? new DialogLifecycleCallback<PopNotification>() {
        } : dialogLifecycleCallback2;
    }

    public PopNotification setDialogLifecycleCallback(DialogLifecycleCallback<PopNotification> dialogLifecycleCallback2) {
        this.dialogLifecycleCallback = dialogLifecycleCallback2;
        if (this.isShow) {
            dialogLifecycleCallback2.onShow(this.f933me);
        }
        return this;
    }

    public PopNotification setStyle(DialogXStyle dialogXStyle) {
        this.style = dialogXStyle;
        return this;
    }

    public PopNotification setTheme(DialogX.THEME theme) {
        this.theme = theme;
        return this;
    }

    public DialogImpl getDialogImpl() {
        return this.dialogImpl;
    }

    public PopNotification setCustomView(OnBindView<PopNotification> onBindView2) {
        this.onBindView = onBindView2;
        refreshUI();
        return this;
    }

    public View getCustomView() {
        OnBindView<PopNotification> onBindView2 = this.onBindView;
        if (onBindView2 == null) {
            return null;
        }
        return onBindView2.getCustomView();
    }

    public PopNotification removeCustomView() {
        this.onBindView.clean();
        refreshUI();
        return this;
    }

    public DialogXStyle.PopNotificationSettings.ALIGN getAlign() {
        return this.align;
    }

    public PopNotification setAlign(DialogXStyle.PopNotificationSettings.ALIGN align2) {
        this.align = align2;
        return this;
    }

    public int getIconResId() {
        return this.iconResId;
    }

    public PopNotification setIconResId(int i) {
        this.iconResId = i;
        refreshUI();
        return this;
    }

    public PopNotification setIcon(Bitmap bitmap) {
        this.iconBitmap = bitmap;
        refreshUI();
        return this;
    }

    public PopNotification setIcon(Drawable drawable) {
        this.iconDrawable = drawable;
        return this;
    }

    public int getIconSize() {
        return this.iconSize;
    }

    public PopNotification setIconSize(int i) {
        this.iconSize = i;
        refreshUI();
        return this;
    }

    public CharSequence getMessage() {
        return this.message;
    }

    public PopNotification setMessage(CharSequence charSequence) {
        this.message = charSequence;
        refreshUI();
        return this;
    }

    public PopNotification setMessage(int i) {
        this.message = getString(i);
        refreshUI();
        return this;
    }

    public CharSequence getButtonText() {
        return this.buttonText;
    }

    public PopNotification setButton(CharSequence charSequence) {
        this.buttonText = charSequence;
        refreshUI();
        return this;
    }

    public PopNotification setButton(int i) {
        this.buttonText = getString(i);
        refreshUI();
        return this;
    }

    public PopNotification setButton(CharSequence charSequence, OnDialogButtonClickListener<PopNotification> onDialogButtonClickListener) {
        this.buttonText = charSequence;
        this.onButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public PopNotification setButton(int i, OnDialogButtonClickListener<PopNotification> onDialogButtonClickListener) {
        this.buttonText = getString(i);
        this.onButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public PopNotification setButton(OnDialogButtonClickListener<PopNotification> onDialogButtonClickListener) {
        this.onButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public TextInfo getMessageTextInfo() {
        return this.messageTextInfo;
    }

    public PopNotification setMessageTextInfo(TextInfo textInfo) {
        this.messageTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getButtonTextInfo() {
        return this.buttonTextInfo;
    }

    public PopNotification setButtonTextInfo(TextInfo textInfo) {
        this.buttonTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public OnDialogButtonClickListener<PopNotification> getOnButtonClickListener() {
        return this.onButtonClickListener;
    }

    public PopNotification setOnButtonClickListener(OnDialogButtonClickListener<PopNotification> onDialogButtonClickListener) {
        this.onButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public boolean isAutoTintIconInLightOrDarkMode() {
        return this.autoTintIconInLightOrDarkMode;
    }

    public PopNotification setAutoTintIconInLightOrDarkMode(boolean z) {
        this.autoTintIconInLightOrDarkMode = z;
        refreshUI();
        return this;
    }

    public OnDialogButtonClickListener<PopNotification> getOnPopNotificationClickListener() {
        return this.onPopNotificationClickListener;
    }

    public PopNotification setOnPopNotificationClickListener(OnDialogButtonClickListener<PopNotification> onDialogButtonClickListener) {
        this.onPopNotificationClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public PopNotification setBackgroundColor(int i) {
        this.backgroundColor = i;
        refreshUI();
        return this;
    }

    public PopNotification setBackgroundColorRes(int i) {
        this.backgroundColor = getColor(i);
        refreshUI();
        return this;
    }

    public long getEnterAnimDuration() {
        return this.enterAnimDuration;
    }

    public PopNotification setEnterAnimDuration(long j) {
        this.enterAnimDuration = j;
        return this;
    }

    public long getExitAnimDuration() {
        return this.exitAnimDuration;
    }

    public PopNotification setExitAnimDuration(long j) {
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
        if (DialogX.onlyOnePopNotification) {
            PopNotification popNotification = null;
            List<PopNotification> list = popNotificationList;
            if (list != null && !list.isEmpty()) {
                List<PopNotification> list2 = popNotificationList;
                popNotification = list2.get(list2.size() - 1);
            }
            if (popNotification != null) {
                popNotification.dismiss();
            }
        }
        if (popNotificationList == null) {
            popNotificationList = new ArrayList();
        }
        popNotificationList.add(this);
        int i = isLightTheme() ? C1903R.layout.layout_dialogx_popnotification_material : C1903R.layout.layout_dialogx_popnotification_material_dark;
        if (this.style.popNotificationSettings() != null) {
            if (this.style.popNotificationSettings().layout(isLightTheme()) != 0) {
                i = this.style.popNotificationSettings().layout(isLightTheme());
            }
            DialogXStyle.PopNotificationSettings.ALIGN align2 = this.style.popNotificationSettings().align();
            this.align = align2;
            if (align2 == null) {
                this.align = DialogXStyle.PopNotificationSettings.ALIGN.TOP;
            }
            int enterAnimResId2 = this.style.popNotificationSettings().enterAnimResId(isLightTheme());
            int exitAnimResId2 = this.style.popNotificationSettings().exitAnimResId(isLightTheme());
            int i2 = this.enterAnimResId;
            if (i2 != 0 || (i2 = overrideEnterAnimRes) != 0) {
                enterAnimResId2 = i2;
            } else if (enterAnimResId2 == 0) {
                enterAnimResId2 = C1903R.C1904anim.anim_dialogx_notification_enter;
            }
            this.enterAnimResId = enterAnimResId2;
            int i3 = this.exitAnimResId;
            if (i3 != 0 || (i3 = overrideExitAnimRes) != 0) {
                exitAnimResId2 = i3;
            } else if (exitAnimResId2 == 0) {
                exitAnimResId2 = C1903R.C1904anim.anim_dialogx_notification_exit;
            }
            this.exitAnimResId = exitAnimResId2;
            this.enterAnimDuration = this.enterAnimDuration == -1 ? overrideEnterDuration : this.enterAnimDuration;
            this.exitAnimDuration = this.exitAnimDuration == -1 ? overrideExitDuration : this.exitAnimDuration;
        }
        this.enterAnimDuration = 0;
        View createView = createView(i);
        this.dialogView = createView;
        this.dialogImpl = new DialogImpl(createView);
        View view2 = this.dialogView;
        if (view2 != null) {
            view2.setTag(this.f933me);
        }
        show(this.dialogView);
    }

    public void hide() {
        this.isHide = true;
        if (getDialogView() != null) {
            getDialogView().setVisibility(8);
        }
    }

    public PopNotification setAnimResId(int i, int i2) {
        this.enterAnimResId = i;
        this.exitAnimResId = i2;
        return this;
    }

    public PopNotification setEnterAnimResId(int i) {
        this.enterAnimResId = i;
        return this;
    }

    public PopNotification setExitAnimResId(int i) {
        this.exitAnimResId = i;
        return this;
    }

    /* access modifiers changed from: protected */
    public void shutdown() {
        dismiss();
    }

    public PopNotification setDialogImplMode(DialogX.IMPL_MODE impl_mode) {
        this.dialogImplMode = impl_mode;
        return this;
    }

    public PopNotification setMargin(int i, int i2, int i3, int i4) {
        int[] iArr = this.bodyMargin;
        iArr[0] = i;
        iArr[1] = i2;
        iArr[2] = i3;
        iArr[3] = i4;
        refreshUI();
        return this;
    }

    public PopNotification setMarginLeft(int i) {
        this.bodyMargin[0] = i;
        refreshUI();
        return this;
    }

    public PopNotification setMarginTop(int i) {
        this.bodyMargin[1] = i;
        refreshUI();
        return this;
    }

    public PopNotification setMarginRight(int i) {
        this.bodyMargin[2] = i;
        refreshUI();
        return this;
    }

    public PopNotification setMarginBottom(int i) {
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

    public PopNotification iconSuccess() {
        setTintIcon(false);
        int i = C1903R.mipmap.ico_dialogx_success;
        if (!(getStyle().popNotificationSettings() == null || getStyle().popNotificationSettings().defaultIconSuccess() == 0)) {
            i = getStyle().popNotificationSettings().defaultIconSuccess();
        }
        setIconSize(dip2px(26.0f));
        setIconResId(i);
        return this;
    }

    public PopNotification iconWarning() {
        setTintIcon(false);
        int i = C1903R.mipmap.ico_dialogx_warning;
        if (!(getStyle().popNotificationSettings() == null || getStyle().popNotificationSettings().defaultIconWarning() == 0)) {
            i = getStyle().popNotificationSettings().defaultIconWarning();
        }
        setIconSize(dip2px(26.0f));
        setIconResId(i);
        return this;
    }

    public PopNotification iconError() {
        setTintIcon(false);
        int i = C1903R.mipmap.ico_dialogx_error;
        if (!(getStyle().popNotificationSettings() == null || getStyle().popNotificationSettings().defaultIconError() == 0)) {
            i = getStyle().popNotificationSettings().defaultIconError();
        }
        setIconSize(dip2px(26.0f));
        setIconResId(i);
        return this;
    }

    public boolean getTintIcon() {
        return this.tintIcon == BaseDialog.BOOLEAN.TRUE;
    }

    public PopNotification setTintIcon(boolean z) {
        this.tintIcon = z ? BaseDialog.BOOLEAN.TRUE : BaseDialog.BOOLEAN.FALSE;
        refreshUI();
        return this;
    }

    public Drawable getIconDrawable() {
        return this.iconDrawable;
    }

    public Bitmap getIconBitmap() {
        return this.iconBitmap;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public PopNotification setTitle(CharSequence charSequence) {
        this.title = charSequence;
        refreshUI();
        return this;
    }

    public TextInfo getTitleTextInfo() {
        return this.titleTextInfo;
    }

    public PopNotification setTitleTextInfo(TextInfo textInfo) {
        this.titleTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public PopNotification setRadius(float f) {
        this.backgroundRadius = f;
        refreshUI();
        return this;
    }

    public float getRadius() {
        return this.backgroundRadius;
    }

    public DialogXAnimInterface<PopNotification> getDialogXAnimImpl() {
        return this.dialogXAnimImpl;
    }

    public PopNotification setDialogXAnimImpl(DialogXAnimInterface<PopNotification> dialogXAnimInterface) {
        this.dialogXAnimImpl = dialogXAnimInterface;
        return this;
    }
}
