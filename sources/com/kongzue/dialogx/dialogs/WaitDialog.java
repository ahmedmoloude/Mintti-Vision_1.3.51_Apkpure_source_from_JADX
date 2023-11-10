package com.kongzue.dialogx.dialogs;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
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
import com.kongzue.dialogx.interfaces.ProgressViewInterface;
import com.kongzue.dialogx.util.ObjectRunnable;
import com.kongzue.dialogx.util.TextInfo;
import com.kongzue.dialogx.util.views.BlurView;
import com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout;
import com.kongzue.dialogx.util.views.MaxRelativeLayout;
import com.kongzue.dialogx.util.views.ProgressView;
import java.lang.ref.WeakReference;

public class WaitDialog extends BaseDialog {

    /* renamed from: me */
    protected static WeakReference<WaitDialog> f937me = null;
    public static BaseDialog.BOOLEAN overrideCancelable = null;
    public static int overrideEnterAnimRes = 0;
    public static int overrideEnterDuration = -1;
    public static int overrideExitAnimRes = 0;
    public static int overrideExitDuration = -1;
    protected float backgroundRadius = -1.0f;
    protected boolean bkgInterceptTouch = true;
    protected int customEnterAnimResId;
    protected int customExitAnimResId;
    protected WeakReference<DialogImpl> dialogImpl;
    protected DialogLifecycleCallback<WaitDialog> dialogLifecycleCallback;
    /* access modifiers changed from: private */
    public WeakReference<View> dialogView;
    protected DialogXAnimInterface<WaitDialog> dialogXAnimImpl;
    protected int maskColor = -1;
    protected CharSequence message;
    protected TextInfo messageTextInfo;
    protected OnBackPressedListener<WaitDialog> onBackPressedListener;
    protected OnBackgroundMaskClickListener<WaitDialog> onBackgroundMaskClickListener;
    protected OnBindView<WaitDialog> onBindView;
    protected BaseDialog.BOOLEAN privateCancelable;
    protected TYPE readyTipType;
    protected int showType = -1;
    protected long tipShowDuration = 1500;
    protected float waitProgress = -1.0f;

    public enum TYPE {
        NONE,
        SUCCESS,
        WARNING,
        ERROR
    }

    protected WaitDialog() {
        this.cancelable = DialogX.cancelableTipDialog;
    }

    protected static WaitDialog instanceBuild() {
        WeakReference<WaitDialog> weakReference = new WeakReference<>(new WaitDialog());
        f937me = weakReference;
        return (WaitDialog) weakReference.get();
    }

    public static WaitDialog build() {
        return new WaitDialog();
    }

    public static WaitDialog show(CharSequence charSequence) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(charSequence, TYPE.NONE);
        showWithInstance(noInstance);
        return m119me();
    }

    public static WaitDialog show(Activity activity, CharSequence charSequence) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(charSequence, TYPE.NONE);
        if (noInstance) {
            showWithInstance(instanceNotNull, activity);
        }
        return instanceNotNull;
    }

    public static WaitDialog show(int i) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(i, TYPE.NONE);
        showWithInstance(noInstance);
        return m119me();
    }

    public static WaitDialog show(Activity activity, int i) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(i, TYPE.NONE);
        if (noInstance) {
            showWithInstance(instanceNotNull, activity);
        }
        return instanceNotNull;
    }

    public static WaitDialog show(CharSequence charSequence, float f) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(charSequence, TYPE.NONE);
        m119me().setProgress(f);
        showWithInstance(noInstance);
        return m119me();
    }

    public static WaitDialog show(Activity activity, CharSequence charSequence, float f) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(charSequence, TYPE.NONE);
        instanceNotNull.setProgress(f);
        if (noInstance) {
            showWithInstance(instanceNotNull, activity);
        }
        return instanceNotNull;
    }

    public static WaitDialog show(int i, float f) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(i, TYPE.NONE);
        m119me().setProgress(f);
        showWithInstance(noInstance);
        return m119me();
    }

    public static WaitDialog show(Activity activity, int i, float f) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(i, TYPE.NONE);
        instanceNotNull.setProgress(f);
        if (noInstance) {
            showWithInstance(instanceNotNull, activity);
        }
        return instanceNotNull;
    }

    public static WaitDialog show(Activity activity, float f) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(TYPE.NONE);
        instanceNotNull.setProgress(f);
        if (noInstance) {
            showWithInstance(instanceNotNull, activity);
        }
        return instanceNotNull;
    }

    public static WaitDialog show(float f) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(TYPE.NONE);
        m119me().setProgress(f);
        showWithInstance(noInstance);
        return m119me();
    }

    public float getProgress() {
        return this.waitProgress;
    }

    public WaitDialog setProgress(float f) {
        this.waitProgress = f;
        refreshUI();
        return this;
    }

    /* access modifiers changed from: protected */
    public View getWaitDialogView() {
        WeakReference<View> weakReference = this.dialogView;
        if (weakReference == null) {
            return null;
        }
        return (View) weakReference.get();
    }

    /* access modifiers changed from: protected */
    public void setWaitDialogView(View view) {
        this.dialogView = new WeakReference<>(view);
    }

    public WaitDialog show() {
        super.beforeShow();
        runOnMain(new Runnable() {
            public void run() {
                int i = C1903R.layout.layout_dialogx_wait;
                if (!(WaitDialog.this.style.overrideWaitTipRes() == null || WaitDialog.this.style.overrideWaitTipRes().overrideWaitLayout(WaitDialog.this.isLightTheme()) == 0)) {
                    i = WaitDialog.this.style.overrideWaitTipRes().overrideWaitLayout(WaitDialog.this.isLightTheme());
                }
                WaitDialog.this.dialogImpl = new WeakReference<>(new DialogImpl(i));
                if (WaitDialog.this.getDialogImpl() != null) {
                    WaitDialog.this.getDialogImpl().lazyCreate();
                    if (WaitDialog.this.getWaitDialogView() != null) {
                        WaitDialog.this.getWaitDialogView().setTag(WaitDialog.this);
                        WaitDialog.show(WaitDialog.this.getWaitDialogView());
                    }
                }
            }
        });
        return this;
    }

    public WaitDialog show(final Activity activity) {
        super.beforeShow();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                int i = C1903R.layout.layout_dialogx_wait;
                if (!(WaitDialog.this.style.overrideWaitTipRes() == null || WaitDialog.this.style.overrideWaitTipRes().overrideWaitLayout(WaitDialog.this.isLightTheme()) == 0)) {
                    i = WaitDialog.this.style.overrideWaitTipRes().overrideWaitLayout(WaitDialog.this.isLightTheme());
                }
                WaitDialog.this.dialogImpl = new WeakReference<>(new DialogImpl(i));
                if (WaitDialog.this.getDialogImpl() != null) {
                    WaitDialog.this.getDialogImpl().lazyCreate();
                    if (WaitDialog.this.getWaitDialogView() != null) {
                        WaitDialog.this.getWaitDialogView().setTag(WaitDialog.this);
                        WaitDialog.show(activity, WaitDialog.this.getWaitDialogView());
                    }
                }
            }
        });
        return this;
    }

    public class DialogImpl implements DialogConvertViewInterface {
        public MaxRelativeLayout bkg;
        public BlurView blurView;
        public RelativeLayout boxCustomView;
        public RelativeLayout boxProgress;
        public DialogXBaseRelativeLayout boxRoot;
        private int layoutResId;
        private float oldProgress;
        public ProgressViewInterface progressView;
        public TextView txtInfo;

        public DialogImpl(int i) {
            this.layoutResId = i;
        }

        public void lazyCreate() {
            View createView = WaitDialog.this.createView(this.layoutResId);
            if (createView != null) {
                WaitDialog.this.setWaitDialogView(createView);
                this.boxRoot = (DialogXBaseRelativeLayout) createView.findViewById(C1903R.C1907id.box_root);
                this.bkg = (MaxRelativeLayout) createView.findViewById(C1903R.C1907id.bkg);
                this.blurView = (BlurView) createView.findViewById(C1903R.C1907id.blurView);
                this.boxProgress = (RelativeLayout) createView.findViewById(C1903R.C1907id.box_progress);
                View view = (View) WaitDialog.this.style.overrideWaitTipRes().overrideWaitView(BaseDialog.getTopActivity(), WaitDialog.this.isLightTheme());
                if (view == null) {
                    view = new ProgressView(BaseDialog.getTopActivity());
                }
                this.progressView = (ProgressViewInterface) view;
                this.boxProgress.addView(view, new RelativeLayout.LayoutParams(-1, -1));
                this.boxCustomView = (RelativeLayout) createView.findViewById(C1903R.C1907id.box_customView);
                this.txtInfo = (TextView) createView.findViewById(C1903R.C1907id.txt_info);
                init();
                WaitDialog.this.setDialogImpl(this);
                refreshView();
            }
        }

        public DialogImpl(View view) {
            if (view != null) {
                this.boxRoot = (DialogXBaseRelativeLayout) view.findViewById(C1903R.C1907id.box_root);
                this.bkg = (MaxRelativeLayout) view.findViewById(C1903R.C1907id.bkg);
                this.blurView = (BlurView) view.findViewById(C1903R.C1907id.blurView);
                this.boxProgress = (RelativeLayout) view.findViewById(C1903R.C1907id.box_progress);
                View view2 = (View) WaitDialog.this.style.overrideWaitTipRes().overrideWaitView(BaseDialog.getTopActivity(), WaitDialog.this.isLightTheme());
                view2 = view2 == null ? new ProgressView(BaseDialog.getTopActivity()) : view2;
                this.progressView = (ProgressViewInterface) view2;
                this.boxProgress.addView(view2, new RelativeLayout.LayoutParams(-1, -1));
                this.boxCustomView = (RelativeLayout) view.findViewById(C1903R.C1907id.box_customView);
                this.txtInfo = (TextView) view.findViewById(C1903R.C1907id.txt_info);
                init();
                WaitDialog.this.setDialogImpl(this);
                refreshView();
            }
        }

        public void init() {
            if (WaitDialog.this.messageTextInfo == null) {
                WaitDialog.this.messageTextInfo = DialogX.tipTextInfo;
            }
            if (WaitDialog.this.backgroundColor == -1) {
                int unused = WaitDialog.this.backgroundColor = DialogX.tipBackgroundColor;
            }
            if (WaitDialog.this.style.overrideWaitTipRes() == null) {
                this.blurView.setRadiusPx((float) WaitDialog.this.dip2px(15.0f));
            } else {
                this.blurView.setRadiusPx((float) (WaitDialog.this.style.overrideWaitTipRes().overrideRadiusPx() < 0 ? WaitDialog.this.dip2px(15.0f) : WaitDialog.this.style.overrideWaitTipRes().overrideRadiusPx()));
            }
            this.boxRoot.setClickable(true);
            this.boxRoot.setParentDialog(WaitDialog.m119me());
            this.boxRoot.setOnLifecycleCallBack(new DialogXBaseRelativeLayout.OnLifecycleCallBack() {
                public void onShow() {
                    boolean unused = WaitDialog.this.isShow = true;
                    boolean unused2 = WaitDialog.this.preShow = false;
                    WaitDialog.this.lifecycle.setCurrentState(Lifecycle.State.CREATED);
                    DialogImpl.this.boxRoot.setAlpha(0.0f);
                    DialogImpl.this.bkg.post(new Runnable() {
                        public void run() {
                            if (BaseDialog.getTopActivity() != null) {
                                DialogImpl.this.getDialogXAnimImpl().doShowAnim(WaitDialog.this, new ObjectRunnable<Float>() {
                                    public void run(Float f) {
                                        DialogImpl.this.boxRoot.setBkgAlpha(f.floatValue());
                                    }
                                });
                                WaitDialog.this.onDialogShow();
                                WaitDialog.this.getDialogLifecycleCallback().onShow(WaitDialog.m119me());
                                WaitDialog.this.lifecycle.setCurrentState(Lifecycle.State.RESUMED);
                            }
                        }
                    });
                }

                public void onDismiss() {
                    boolean unused = WaitDialog.this.isShow = false;
                    WaitDialog.this.getDialogLifecycleCallback().onDismiss(WaitDialog.m119me());
                    if (WaitDialog.this.dialogImpl != null) {
                        WaitDialog.this.dialogImpl.clear();
                    }
                    WaitDialog.this.dialogImpl = null;
                    if (WaitDialog.this.dialogView != null) {
                        WaitDialog.this.dialogView.clear();
                    }
                    WeakReference unused2 = WaitDialog.this.dialogView = null;
                    WaitDialog.this.dialogLifecycleCallback = null;
                    if (WaitDialog.f937me != null) {
                        WaitDialog.f937me.clear();
                    }
                    WaitDialog.f937me = null;
                    WaitDialog.this.lifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                    System.gc();
                }
            });
            if (WaitDialog.this.readyTipType != null) {
                this.progressView.noLoading();
                ((View) this.progressView).postDelayed(new Runnable() {
                    public void run() {
                        DialogImpl dialogImpl = DialogImpl.this;
                        dialogImpl.showTip(WaitDialog.this.readyTipType);
                    }
                }, 100);
            }
            this.boxRoot.setOnBackPressedListener(new DialogXBaseRelativeLayout.PrivateBackPressedListener() {
                public boolean onBackPressed() {
                    if (WaitDialog.this.onBackPressedListener != null) {
                        if (!WaitDialog.this.onBackPressedListener.onBackPressed(WaitDialog.this)) {
                            return true;
                        }
                        WaitDialog.dismiss();
                        return true;
                    } else if (!WaitDialog.this.isCancelable()) {
                        return true;
                    } else {
                        WaitDialog.dismiss();
                        return true;
                    }
                }
            });
            WaitDialog.this.onDialogInit();
        }

        public void refreshView() {
            if (this.boxRoot != null && BaseDialog.getTopActivity() != null) {
                this.bkg.setMaxWidth(WaitDialog.this.getMaxWidth());
                if (WaitDialog.this.style.overrideWaitTipRes() != null) {
                    int overrideBackgroundColorRes = WaitDialog.this.style.overrideWaitTipRes().overrideBackgroundColorRes(WaitDialog.this.isLightTheme());
                    if (overrideBackgroundColorRes == 0) {
                        overrideBackgroundColorRes = WaitDialog.this.isLightTheme() ? C1903R.C1905color.dialogxWaitBkgDark : C1903R.C1905color.dialogxWaitBkgLight;
                    }
                    BlurView blurView2 = this.blurView;
                    if (blurView2 != null) {
                        blurView2.setOverlayColor(WaitDialog.this.backgroundColor == -1 ? WaitDialog.this.getResources().getColor(overrideBackgroundColorRes) : WaitDialog.this.backgroundColor);
                        this.blurView.setUseBlur(WaitDialog.this.style.overrideWaitTipRes().blurBackground());
                    }
                    int overrideTextColorRes = WaitDialog.this.style.overrideWaitTipRes().overrideTextColorRes(WaitDialog.this.isLightTheme());
                    if (overrideTextColorRes == 0) {
                        overrideTextColorRes = WaitDialog.this.isLightTheme() ? C1903R.C1905color.white : C1903R.C1905color.black;
                    }
                    this.txtInfo.setTextColor(WaitDialog.this.getResources().getColor(overrideTextColorRes));
                    this.progressView.setColor(WaitDialog.this.getResources().getColor(overrideTextColorRes));
                } else if (WaitDialog.this.isLightTheme()) {
                    BlurView blurView3 = this.blurView;
                    if (blurView3 != null) {
                        blurView3.setOverlayColor(WaitDialog.this.backgroundColor == -1 ? WaitDialog.this.getResources().getColor(C1903R.C1905color.dialogxWaitBkgDark) : WaitDialog.this.backgroundColor);
                    }
                    this.progressView.setColor(-1);
                    this.txtInfo.setTextColor(-1);
                } else {
                    BlurView blurView4 = this.blurView;
                    if (blurView4 != null) {
                        blurView4.setOverlayColor(WaitDialog.this.backgroundColor == -1 ? WaitDialog.this.getResources().getColor(C1903R.C1905color.dialogxWaitBkgLight) : WaitDialog.this.backgroundColor);
                    }
                    this.progressView.setColor(ViewCompat.MEASURED_STATE_MASK);
                    this.txtInfo.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                }
                if (DialogX.tipProgressColor != -1) {
                    this.progressView.setColor(DialogX.tipProgressColor);
                }
                if (WaitDialog.this.waitProgress >= 0.0f && WaitDialog.this.waitProgress <= 1.0f && this.oldProgress != WaitDialog.this.waitProgress) {
                    this.progressView.progress(WaitDialog.this.waitProgress);
                    this.oldProgress = WaitDialog.this.waitProgress;
                }
                if (WaitDialog.this.backgroundRadius > -1.0f) {
                    BlurView blurView5 = this.blurView;
                    if (blurView5 != null) {
                        blurView5.setRadiusPx(WaitDialog.this.backgroundRadius);
                    }
                    if (Build.VERSION.SDK_INT >= 21) {
                        this.bkg.setOutlineProvider(new ViewOutlineProvider() {
                            public void getOutline(View view, Outline outline) {
                                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), WaitDialog.this.backgroundRadius);
                            }
                        });
                        this.bkg.setClipToOutline(true);
                    }
                }
                WaitDialog waitDialog = WaitDialog.this;
                waitDialog.showText(this.txtInfo, waitDialog.message);
                BaseDialog.useTextInfo(this.txtInfo, WaitDialog.this.messageTextInfo);
                if (WaitDialog.this.maskColor != -1) {
                    this.boxRoot.setBackgroundColor(WaitDialog.this.maskColor);
                }
                if (WaitDialog.this.onBindView == null || WaitDialog.this.onBindView.getCustomView() == null) {
                    this.boxCustomView.setVisibility(8);
                    this.boxProgress.setVisibility(0);
                } else {
                    WaitDialog.this.onBindView.bindParent(this.boxCustomView, WaitDialog.this);
                    this.boxCustomView.setVisibility(0);
                    this.boxProgress.setVisibility(8);
                }
                if (!WaitDialog.this.bkgInterceptTouch) {
                    this.boxRoot.setClickable(false);
                } else if (WaitDialog.this.isCancelable()) {
                    this.boxRoot.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            if (WaitDialog.this.onBackgroundMaskClickListener == null || !WaitDialog.this.onBackgroundMaskClickListener.onClick(WaitDialog.this, view)) {
                                DialogImpl.this.doDismiss(view);
                            }
                        }
                    });
                } else {
                    this.boxRoot.setOnClickListener((View.OnClickListener) null);
                }
                WaitDialog.this.onDialogRefreshUI();
            }
        }

        public void doDismiss(final View view) {
            if (this.boxRoot != null && BaseDialog.getTopActivity() != null && !WaitDialog.this.dismissAnimFlag) {
                boolean unused = WaitDialog.this.dismissAnimFlag = true;
                this.boxRoot.post(new Runnable() {
                    public void run() {
                        View view = view;
                        if (view != null) {
                            view.setEnabled(false);
                        }
                        DialogImpl.this.getDialogXAnimImpl().doExitAnim(WaitDialog.this, new ObjectRunnable<Float>() {
                            public void run(Float f) {
                                if (DialogImpl.this.boxRoot != null) {
                                    DialogImpl.this.boxRoot.setBkgAlpha(f.floatValue());
                                }
                                if (f.floatValue() == 0.0f) {
                                    if (DialogImpl.this.boxRoot != null) {
                                        DialogImpl.this.boxRoot.setVisibility(8);
                                    }
                                    WaitDialog.dismiss(WaitDialog.this.getWaitDialogView());
                                }
                            }
                        });
                    }
                });
            }
        }

        /* access modifiers changed from: protected */
        public DialogXAnimInterface<WaitDialog> getDialogXAnimImpl() {
            if (WaitDialog.this.dialogXAnimImpl == null) {
                WaitDialog.this.dialogXAnimImpl = new DialogXAnimInterface<WaitDialog>() {
                    public void doShowAnim(WaitDialog waitDialog, final ObjectRunnable<Float> objectRunnable) {
                        int i = C1903R.C1904anim.anim_dialogx_default_enter;
                        if (WaitDialog.overrideEnterAnimRes != 0) {
                            i = WaitDialog.overrideEnterAnimRes;
                        }
                        if (WaitDialog.this.customEnterAnimResId != 0) {
                            i = WaitDialog.this.customEnterAnimResId;
                        }
                        Animation loadAnimation = AnimationUtils.loadAnimation(BaseDialog.getTopActivity(), i);
                        long duration = loadAnimation.getDuration();
                        loadAnimation.setInterpolator(new DecelerateInterpolator());
                        if (WaitDialog.overrideEnterDuration >= 0) {
                            duration = (long) WaitDialog.overrideEnterDuration;
                        }
                        if (WaitDialog.this.enterAnimDuration >= 0) {
                            duration = WaitDialog.this.enterAnimDuration;
                        }
                        loadAnimation.setDuration(duration);
                        DialogImpl.this.bkg.startAnimation(loadAnimation);
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                        ofFloat.setDuration(duration);
                        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                objectRunnable.run((Float) valueAnimator.getAnimatedValue());
                            }
                        });
                        ofFloat.start();
                        DialogImpl.this.boxRoot.animate().setDuration(duration).alpha(1.0f).setInterpolator(new DecelerateInterpolator()).setListener((Animator.AnimatorListener) null);
                    }

                    public void doExitAnim(WaitDialog waitDialog, final ObjectRunnable<Float> objectRunnable) {
                        Context topActivity = BaseDialog.getTopActivity();
                        if (topActivity == null) {
                            topActivity = DialogImpl.this.boxRoot.getContext();
                        }
                        if (topActivity != null) {
                            int i = C1903R.C1904anim.anim_dialogx_default_exit;
                            if (WaitDialog.overrideExitAnimRes != 0) {
                                i = WaitDialog.overrideExitAnimRes;
                            }
                            if (WaitDialog.this.customExitAnimResId != 0) {
                                i = WaitDialog.this.customExitAnimResId;
                            }
                            Animation loadAnimation = AnimationUtils.loadAnimation(topActivity, i);
                            long duration = loadAnimation.getDuration();
                            if (WaitDialog.overrideExitDuration >= 0) {
                                duration = (long) WaitDialog.overrideExitDuration;
                            }
                            if (WaitDialog.this.exitAnimDuration != -1) {
                                duration = WaitDialog.this.exitAnimDuration;
                            }
                            loadAnimation.setDuration(duration);
                            loadAnimation.setInterpolator(new AccelerateInterpolator());
                            DialogImpl.this.bkg.startAnimation(loadAnimation);
                            DialogImpl.this.boxRoot.animate().alpha(0.0f).setInterpolator(new AccelerateInterpolator()).setDuration(duration);
                            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
                            ofFloat.setDuration(duration);
                            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    objectRunnable.run((Float) valueAnimator.getAnimatedValue());
                                }
                            });
                            ofFloat.start();
                        }
                    }
                };
            }
            return WaitDialog.this.dialogXAnimImpl;
        }

        public void showTip(final TYPE type) {
            WaitDialog.runOnMain(new Runnable() {
                public void run() {
                    WaitDialog.this.showType = type.ordinal();
                    if (DialogImpl.this.progressView != null) {
                        int i = C20526.$SwitchMap$com$kongzue$dialogx$dialogs$WaitDialog$TYPE[type.ordinal()];
                        if (i != 1) {
                            if (i == 2) {
                                DialogImpl.this.progressView.success();
                            } else if (i == 3) {
                                DialogImpl.this.progressView.warning();
                            } else if (i == 4) {
                                DialogImpl.this.progressView.error();
                            }
                            if (DialogImpl.this.boxProgress == null || DialogImpl.this.boxProgress.getVisibility() != 0) {
                                WaitDialog.this.getDialogLifecycleCallback().onShow(WaitDialog.this);
                                DialogImpl.this.refreshView();
                                if (WaitDialog.this.tipShowDuration > 0) {
                                    WaitDialog.runOnMainDelay(new Runnable() {
                                        public void run() {
                                            if (WaitDialog.this.showType > -1) {
                                                DialogImpl.this.doDismiss((View) null);
                                            }
                                        }
                                    }, WaitDialog.this.tipShowDuration);
                                    return;
                                }
                                return;
                            }
                            DialogImpl.this.progressView.whenShowTick(new Runnable() {
                                public void run() {
                                    WaitDialog.this.getDialogLifecycleCallback().onShow(WaitDialog.this);
                                    DialogImpl.this.refreshView();
                                    if (WaitDialog.this.tipShowDuration > 0) {
                                        ((View) DialogImpl.this.progressView).postDelayed(new Runnable() {
                                            public void run() {
                                                if (WaitDialog.this.showType > -1) {
                                                    DialogImpl.this.doDismiss((View) null);
                                                }
                                            }
                                        }, WaitDialog.this.tipShowDuration);
                                    }
                                }
                            });
                            return;
                        }
                        DialogImpl.this.progressView.loading();
                    }
                }
            });
        }
    }

    /* renamed from: com.kongzue.dialogx.dialogs.WaitDialog$6 */
    static /* synthetic */ class C20526 {
        static final /* synthetic */ int[] $SwitchMap$com$kongzue$dialogx$dialogs$WaitDialog$TYPE;

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        static {
            /*
                com.kongzue.dialogx.dialogs.WaitDialog$TYPE[] r0 = com.kongzue.dialogx.dialogs.WaitDialog.TYPE.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$kongzue$dialogx$dialogs$WaitDialog$TYPE = r0
                com.kongzue.dialogx.dialogs.WaitDialog$TYPE r1 = com.kongzue.dialogx.dialogs.WaitDialog.TYPE.NONE     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$kongzue$dialogx$dialogs$WaitDialog$TYPE     // Catch:{ NoSuchFieldError -> 0x001d }
                com.kongzue.dialogx.dialogs.WaitDialog$TYPE r1 = com.kongzue.dialogx.dialogs.WaitDialog.TYPE.SUCCESS     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$kongzue$dialogx$dialogs$WaitDialog$TYPE     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.kongzue.dialogx.dialogs.WaitDialog$TYPE r1 = com.kongzue.dialogx.dialogs.WaitDialog.TYPE.WARNING     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$kongzue$dialogx$dialogs$WaitDialog$TYPE     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.kongzue.dialogx.dialogs.WaitDialog$TYPE r1 = com.kongzue.dialogx.dialogs.WaitDialog.TYPE.ERROR     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.dialogs.WaitDialog.C20526.<clinit>():void");
        }
    }

    /* access modifiers changed from: private */
    public void setDialogImpl(DialogImpl dialogImpl2) {
        WeakReference<DialogImpl> weakReference = this.dialogImpl;
        if (weakReference != null && weakReference.get() != dialogImpl2) {
            this.dialogImpl = new WeakReference<>(dialogImpl2);
        }
    }

    public String dialogKey() {
        return getClass().getSimpleName() + "(" + Integer.toHexString(hashCode()) + ")";
    }

    public boolean isLightTheme() {
        if (DialogX.tipTheme == null) {
            return super.isLightTheme();
        }
        return DialogX.tipTheme == DialogX.THEME.LIGHT;
    }

    public void refreshUI() {
        if (getDialogImpl() != null) {
            runOnMain(new Runnable() {
                public void run() {
                    if (WaitDialog.this.getDialogImpl() != null) {
                        WaitDialog.this.getDialogImpl().refreshView();
                    }
                }
            });
        }
    }

    public void doDismiss() {
        runOnMain(new Runnable() {
            public void run() {
                if (WaitDialog.this.getDialogImpl() != null) {
                    WaitDialog.this.getDialogImpl().doDismiss((View) null);
                }
            }
        });
    }

    public static void dismiss() {
        m119me().doDismiss();
    }

    public static void dismiss(Activity activity) {
        WaitDialog instance = getInstance(activity);
        if (instance != null) {
            instance.doDismiss();
        }
    }

    /* renamed from: me */
    protected static WaitDialog m119me() {
        for (BaseDialog next : getRunningDialogList()) {
            if ((next instanceof WaitDialog) && next.isShow() && next.getOwnActivity() == getTopActivity()) {
                return (WaitDialog) next;
            }
        }
        WeakReference<WaitDialog> weakReference = f937me;
        if (weakReference == null || weakReference.get() == null) {
            return instanceBuild();
        }
        return (WaitDialog) f937me.get();
    }

    /* access modifiers changed from: protected */
    public void showTip(CharSequence charSequence, TYPE type) {
        this.showType = type.ordinal();
        this.message = charSequence;
        this.readyTipType = type;
        show();
    }

    /* access modifiers changed from: protected */
    public void showTip(Activity activity, CharSequence charSequence, TYPE type) {
        this.showType = type.ordinal();
        this.message = charSequence;
        this.readyTipType = type;
        show(activity);
    }

    /* access modifiers changed from: protected */
    public void showTip(int i, TYPE type) {
        this.showType = type.ordinal();
        this.message = getString(i);
        this.readyTipType = type;
        show();
    }

    /* access modifiers changed from: protected */
    public void showTip(Activity activity, int i, TYPE type) {
        this.showType = type.ordinal();
        this.message = getString(i);
        this.readyTipType = type;
        show(activity);
    }

    /* access modifiers changed from: protected */
    public void showTip(TYPE type) {
        if (this.readyTipType != type) {
            this.showType = type.ordinal();
            this.readyTipType = type;
            if (getDialogImpl() != null) {
                getDialogImpl().showTip(type);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setTip(TYPE type) {
        showTip(type);
    }

    /* access modifiers changed from: protected */
    public void setTip(CharSequence charSequence, TYPE type) {
        this.message = charSequence;
        showTip(type);
        refreshUI();
    }

    /* access modifiers changed from: protected */
    public void setTip(int i, TYPE type) {
        this.message = getString(i);
        showTip(type);
        refreshUI();
    }

    /* access modifiers changed from: protected */
    public void setTipShowDuration(long j) {
        this.tipShowDuration = j;
        showTip(this.readyTipType);
    }

    public static CharSequence getMessage() {
        return m119me().message;
    }

    public static WaitDialog setMessage(CharSequence charSequence) {
        m119me().preMessage(charSequence);
        m119me().refreshUI();
        return m119me();
    }

    public static WaitDialog setMessage(int i) {
        m119me().preMessage(i);
        m119me().refreshUI();
        return m119me();
    }

    public boolean isCancelable() {
        BaseDialog.BOOLEAN booleanR = this.privateCancelable;
        if (booleanR == null) {
            BaseDialog.BOOLEAN booleanR2 = overrideCancelable;
            if (booleanR2 == null) {
                return DialogX.cancelableTipDialog;
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

    public WaitDialog setCancelable(boolean z) {
        this.privateCancelable = z ? BaseDialog.BOOLEAN.TRUE : BaseDialog.BOOLEAN.FALSE;
        refreshUI();
        return this;
    }

    /* access modifiers changed from: protected */
    public WaitDialog preMessage(CharSequence charSequence) {
        this.message = charSequence;
        return this;
    }

    /* access modifiers changed from: protected */
    public WaitDialog preMessage(int i) {
        this.message = getString(i);
        return this;
    }

    public DialogLifecycleCallback<WaitDialog> getDialogLifecycleCallback() {
        DialogLifecycleCallback<WaitDialog> dialogLifecycleCallback2 = this.dialogLifecycleCallback;
        return dialogLifecycleCallback2 == null ? new DialogLifecycleCallback<WaitDialog>() {
        } : dialogLifecycleCallback2;
    }

    public WaitDialog setDialogLifecycleCallback(DialogLifecycleCallback<WaitDialog> dialogLifecycleCallback2) {
        this.dialogLifecycleCallback = dialogLifecycleCallback2;
        if (this.isShow) {
            dialogLifecycleCallback2.onShow(m119me());
        }
        return this;
    }

    public DialogImpl getDialogImpl() {
        WeakReference<DialogImpl> weakReference = this.dialogImpl;
        if (weakReference == null) {
            return null;
        }
        return (DialogImpl) weakReference.get();
    }

    public WaitDialog setCustomView(OnBindView<WaitDialog> onBindView2) {
        this.onBindView = onBindView2;
        refreshUI();
        return this;
    }

    public View getCustomView() {
        OnBindView<WaitDialog> onBindView2 = this.onBindView;
        if (onBindView2 == null) {
            return null;
        }
        return onBindView2.getCustomView();
    }

    public WaitDialog removeCustomView() {
        this.onBindView.clean();
        refreshUI();
        return this;
    }

    public OnBackPressedListener<WaitDialog> getOnBackPressedListener() {
        return this.onBackPressedListener;
    }

    public WaitDialog setOnBackPressedListener(OnBackPressedListener<WaitDialog> onBackPressedListener2) {
        this.onBackPressedListener = onBackPressedListener2;
        refreshUI();
        return this;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public WaitDialog setBackgroundColor(int i) {
        this.backgroundColor = i;
        refreshUI();
        return this;
    }

    public WaitDialog setBackgroundColorRes(int i) {
        this.backgroundColor = getColor(i);
        refreshUI();
        return this;
    }

    public WaitDialog setMaskColor(int i) {
        this.maskColor = i;
        refreshUI();
        return this;
    }

    public WaitDialog setEnterAnimDuration(long j) {
        this.enterAnimDuration = j;
        return this;
    }

    public long getExitAnimDuration() {
        return this.exitAnimDuration;
    }

    public WaitDialog setExitAnimDuration(long j) {
        this.exitAnimDuration = j;
        return this;
    }

    public void restartDialog() {
        refreshUI();
    }

    public static WaitDialog getInstance() {
        return m119me();
    }

    public static int getType() {
        return m119me().showType;
    }

    public WaitDialog setAnimResId(int i, int i2) {
        this.customEnterAnimResId = i;
        this.customExitAnimResId = i2;
        return this;
    }

    public WaitDialog setEnterAnimResId(int i) {
        this.customEnterAnimResId = i;
        return this;
    }

    public WaitDialog setExitAnimResId(int i) {
        this.customExitAnimResId = i;
        return this;
    }

    protected static boolean noInstance() {
        if (getTopActivity() != null && (getTopActivity() instanceof Activity) && getInstance(getTopActivity()) != null) {
            return false;
        }
        WeakReference<WaitDialog> weakReference = f937me;
        if (weakReference == null || weakReference.get() == null || ((WaitDialog) f937me.get()).getOwnActivity() == null || ((WaitDialog) f937me.get()).getOwnActivity() != getTopActivity()) {
            return true;
        }
        return false;
    }

    protected static boolean noInstance(Activity activity) {
        if (getTopActivity() != null && getInstance(activity) != null) {
            return false;
        }
        WeakReference<WaitDialog> weakReference = f937me;
        if (weakReference == null || weakReference.get() == null || ((WaitDialog) f937me.get()).getOwnActivity() == null || ((WaitDialog) f937me.get()).getOwnActivity() != activity) {
            return true;
        }
        return false;
    }

    public static WaitDialog getInstanceNotNull(Activity activity) {
        for (BaseDialog next : getRunningDialogList()) {
            if ((next instanceof WaitDialog) && next.isShow() && next.getOwnActivity() == activity) {
                return (WaitDialog) next;
            }
        }
        return instanceBuild();
    }

    public static WaitDialog getInstance(Activity activity) {
        for (BaseDialog next : getRunningDialogList()) {
            if ((next instanceof WaitDialog) && next.isShow() && next.getOwnActivity() == activity) {
                return (WaitDialog) next;
            }
        }
        return null;
    }

    protected static void showWithInstance(boolean z) {
        if (z) {
            m119me().show();
        } else {
            m119me().refreshUI();
        }
    }

    protected static void showWithInstance(WaitDialog waitDialog, Activity activity) {
        if (activity == null) {
            waitDialog.show();
        } else {
            waitDialog.show(activity);
        }
    }

    /* access modifiers changed from: protected */
    public void shutdown() {
        dismiss();
    }

    public WaitDialog setMaxWidth(int i) {
        this.maxWidth = i;
        refreshUI();
        return this;
    }

    public WaitDialog setDialogImplMode(DialogX.IMPL_MODE impl_mode) {
        this.dialogImplMode = impl_mode;
        return this;
    }

    public boolean isBkgInterceptTouch() {
        return this.bkgInterceptTouch;
    }

    public WaitDialog setBkgInterceptTouch(boolean z) {
        this.bkgInterceptTouch = z;
        return this;
    }

    public OnBackgroundMaskClickListener<WaitDialog> getOnBackgroundMaskClickListener() {
        return this.onBackgroundMaskClickListener;
    }

    public WaitDialog setOnBackgroundMaskClickListener(OnBackgroundMaskClickListener<WaitDialog> onBackgroundMaskClickListener2) {
        this.onBackgroundMaskClickListener = onBackgroundMaskClickListener2;
        return this;
    }

    public WaitDialog setStyle(DialogXStyle dialogXStyle) {
        this.style = dialogXStyle;
        return this;
    }

    public WaitDialog setTheme(DialogX.THEME theme) {
        this.theme = theme;
        return this;
    }

    public TextInfo getMessageTextInfo() {
        return this.messageTextInfo;
    }

    public WaitDialog setMessageTextInfo(TextInfo textInfo) {
        this.messageTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public WaitDialog setMessageContent(CharSequence charSequence) {
        this.message = charSequence;
        refreshUI();
        return this;
    }

    public WaitDialog setMessageContent(int i) {
        this.message = getString(i);
        refreshUI();
        return this;
    }

    public CharSequence getMessageContent() {
        return this.message;
    }

    public WaitDialog setTipType(TYPE type) {
        showTip(type);
        return this;
    }

    public WaitDialog setRadius(float f) {
        this.backgroundRadius = f;
        refreshUI();
        return this;
    }

    public float getRadius() {
        return this.backgroundRadius;
    }

    public DialogXAnimInterface<WaitDialog> getDialogXAnimImpl() {
        return this.dialogXAnimImpl;
    }

    public WaitDialog setDialogXAnimImpl(DialogXAnimInterface<WaitDialog> dialogXAnimInterface) {
        this.dialogXAnimImpl = dialogXAnimInterface;
        return this;
    }
}
