package com.kongzue.dialogx.dialogs;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
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
import com.kongzue.dialogx.interfaces.OnSafeInsetsChangeListener;
import com.kongzue.dialogx.interfaces.ScrollController;
import com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor;
import com.kongzue.dialogx.util.ObjectRunnable;
import com.kongzue.dialogx.util.views.ActivityScreenShotImageView;
import com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout;
import com.kongzue.dialogx.util.views.MaxRelativeLayout;

public class FullScreenDialog extends BaseDialog {
    public static BaseDialog.BOOLEAN overrideCancelable = null;
    public static int overrideEnterDuration = -1;
    public static int overrideExitDuration = -1;
    protected boolean allowInterceptTouch = true;
    protected float backgroundRadius = -1.0f;
    protected DialogImpl dialogImpl;
    protected DialogLifecycleCallback<FullScreenDialog> dialogLifecycleCallback;
    /* access modifiers changed from: private */
    public View dialogView;
    protected DialogXAnimInterface<FullScreenDialog> dialogXAnimImpl;
    protected boolean hideWithExitAnim;
    protected boolean hideZoomBackground;
    private boolean isHide;

    /* renamed from: me */
    protected FullScreenDialog f929me = this;
    protected OnBackPressedListener<FullScreenDialog> onBackPressedListener;
    protected OnBackgroundMaskClickListener<FullScreenDialog> onBackgroundMaskClickListener;
    protected OnBindView<FullScreenDialog> onBindView;
    protected BaseDialog.BOOLEAN privateCancelable;

    protected FullScreenDialog() {
    }

    public static FullScreenDialog build() {
        return new FullScreenDialog();
    }

    public static FullScreenDialog build(OnBindView<FullScreenDialog> onBindView2) {
        return new FullScreenDialog(onBindView2);
    }

    public FullScreenDialog(OnBindView<FullScreenDialog> onBindView2) {
        this.onBindView = onBindView2;
    }

    public static FullScreenDialog show(OnBindView<FullScreenDialog> onBindView2) {
        FullScreenDialog fullScreenDialog = new FullScreenDialog(onBindView2);
        fullScreenDialog.show();
        return fullScreenDialog;
    }

    public FullScreenDialog show() {
        if (!this.isHide || getDialogView() == null || !this.isShow) {
            super.beforeShow();
            if (getDialogView() == null) {
                View createView = createView(isLightTheme() ? C1903R.layout.layout_dialogx_fullscreen : C1903R.layout.layout_dialogx_fullscreen_dark);
                this.dialogView = createView;
                this.dialogImpl = new DialogImpl(createView);
                View view = this.dialogView;
                if (view != null) {
                    view.setTag(this.f929me);
                }
            }
            show(this.dialogView);
            return this;
        }
        if (!this.hideWithExitAnim || getDialogImpl() == null) {
            getDialogView().setVisibility(0);
        } else {
            getDialogView().setVisibility(0);
            getDialogImpl().getDialogXAnimImpl().doShowAnim(this.f929me, (ObjectRunnable<Float>) null);
        }
        return this;
    }

    public void show(Activity activity) {
        super.beforeShow();
        if (getDialogView() == null) {
            View createView = createView(isLightTheme() ? C1903R.layout.layout_dialogx_fullscreen : C1903R.layout.layout_dialogx_fullscreen_dark);
            this.dialogView = createView;
            this.dialogImpl = new DialogImpl(createView);
            View view = this.dialogView;
            if (view != null) {
                view.setTag(this.f929me);
            }
        }
        show(activity, this.dialogView);
    }

    public class DialogImpl implements DialogConvertViewInterface {
        public MaxRelativeLayout bkg;
        public float bkgEnterAimY = -1.0f;
        public RelativeLayout boxBkg;
        public RelativeLayout boxCustom;
        public DialogXBaseRelativeLayout boxRoot;
        /* access modifiers changed from: private */
        public long enterAnimDurationTemp = 300;
        /* access modifiers changed from: private */
        public FullScreenDialogTouchEventInterceptor fullScreenDialogTouchEventInterceptor;
        public ActivityScreenShotImageView imgZoomActivity;
        public ScrollController scrollView;

        public DialogImpl setScrollView(ScrollController scrollController) {
            this.scrollView = scrollController;
            return this;
        }

        public DialogImpl(View view) {
            if (view != null) {
                this.imgZoomActivity = (ActivityScreenShotImageView) view.findViewById(C1903R.C1907id.img_zoom_activity);
                this.boxRoot = (DialogXBaseRelativeLayout) view.findViewById(C1903R.C1907id.box_root);
                this.boxBkg = (RelativeLayout) view.findViewById(C1903R.C1907id.box_bkg);
                this.bkg = (MaxRelativeLayout) view.findViewById(C1903R.C1907id.bkg);
                this.boxCustom = (RelativeLayout) view.findViewById(C1903R.C1907id.box_custom);
                if (FullScreenDialog.this.hideZoomBackground) {
                    FullScreenDialog.this.dialogView.setBackgroundResource(C1903R.C1905color.black20);
                    this.imgZoomActivity.setVisibility(8);
                } else {
                    FullScreenDialog.this.dialogView.setBackgroundResource(C1903R.C1905color.black);
                    this.imgZoomActivity.setVisibility(0);
                }
                init();
                FullScreenDialog.this.dialogImpl = this;
                refreshView();
            }
        }

        public void init() {
            this.boxRoot.setParentDialog(FullScreenDialog.this.f929me);
            this.boxRoot.setOnLifecycleCallBack(new DialogXBaseRelativeLayout.OnLifecycleCallBack() {
                public void onShow() {
                    boolean unused = FullScreenDialog.this.isShow = true;
                    boolean unused2 = FullScreenDialog.this.preShow = false;
                    FullScreenDialog.this.lifecycle.setCurrentState(Lifecycle.State.CREATED);
                    FullScreenDialog.this.onDialogShow();
                    FullScreenDialog.this.getDialogLifecycleCallback().onShow(FullScreenDialog.this.f929me);
                }

                public void onDismiss() {
                    boolean unused = FullScreenDialog.this.isShow = false;
                    FullScreenDialog.this.getDialogLifecycleCallback().onDismiss(FullScreenDialog.this.f929me);
                    FullScreenDialogTouchEventInterceptor unused2 = DialogImpl.this.fullScreenDialogTouchEventInterceptor = null;
                    FullScreenDialog.this.dialogImpl = null;
                    FullScreenDialog.this.dialogLifecycleCallback = null;
                    FullScreenDialog.this.lifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                    System.gc();
                }
            });
            this.boxRoot.setOnBackPressedListener(new DialogXBaseRelativeLayout.PrivateBackPressedListener() {
                public boolean onBackPressed() {
                    if (FullScreenDialog.this.onBackPressedListener != null) {
                        if (!FullScreenDialog.this.onBackPressedListener.onBackPressed(FullScreenDialog.this.f929me)) {
                            return true;
                        }
                        FullScreenDialog.this.dismiss();
                        return true;
                    } else if (!FullScreenDialog.this.isCancelable()) {
                        return true;
                    } else {
                        FullScreenDialog.this.dismiss();
                        return true;
                    }
                }
            });
            this.fullScreenDialogTouchEventInterceptor = new FullScreenDialogTouchEventInterceptor(FullScreenDialog.this.f929me, FullScreenDialog.this.dialogImpl);
            this.enterAnimDurationTemp = 300;
            if (FullScreenDialog.overrideEnterDuration >= 0) {
                this.enterAnimDurationTemp = (long) FullScreenDialog.overrideEnterDuration;
            }
            if (FullScreenDialog.this.enterAnimDuration >= 0) {
                this.enterAnimDurationTemp = FullScreenDialog.this.enterAnimDuration;
            }
            this.boxRoot.setBkgAlpha(0.0f);
            this.bkg.setY((float) this.boxRoot.getHeight());
            this.boxRoot.post(new Runnable() {
                public void run() {
                    DialogImpl.this.getDialogXAnimImpl().doShowAnim(FullScreenDialog.this.f929me, (ObjectRunnable<Float>) null);
                    FullScreenDialog.this.lifecycle.setCurrentState(Lifecycle.State.RESUMED);
                }
            });
            this.boxRoot.setOnSafeInsetsChangeListener(new OnSafeInsetsChangeListener() {
                public void onChange(Rect rect) {
                    if (rect.bottom > FullScreenDialog.this.dip2px(100.0f)) {
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DialogImpl.this.bkg, "y", new float[]{DialogImpl.this.bkg.getY(), 0.0f});
                        ofFloat.setDuration(DialogImpl.this.enterAnimDurationTemp);
                        ofFloat.start();
                    }
                }
            });
            this.bkg.setOnYChanged(new MaxRelativeLayout.OnYChanged() {
                /* renamed from: y */
                public void mo26476y(float f) {
                    float f2 = 1.0f;
                    float height = 1.0f - ((((float) DialogImpl.this.boxRoot.getHeight()) - f) * 2.0E-5f);
                    if (height <= 1.0f) {
                        f2 = height;
                    }
                    if (!FullScreenDialog.this.hideZoomBackground) {
                        DialogImpl.this.imgZoomActivity.setScaleX(f2);
                        DialogImpl.this.imgZoomActivity.setScaleY(f2);
                        DialogImpl.this.imgZoomActivity.setRadius(((float) FullScreenDialog.this.dip2px(15.0f)) * ((((float) DialogImpl.this.boxRoot.getHeight()) - f) / ((float) DialogImpl.this.boxRoot.getHeight())));
                    }
                }
            });
            FullScreenDialog.this.onDialogInit();
        }

        /* access modifiers changed from: private */
        public void showEnterAnim(int i) {
            float safeHeight = this.boxRoot.getSafeHeight() - ((float) i);
            this.bkgEnterAimY = safeHeight;
            if (safeHeight < 0.0f) {
                this.bkgEnterAimY = 0.0f;
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.bkg, "y", new float[]{(float) this.boxRoot.getHeight(), this.bkgEnterAimY});
            ofFloat.setDuration(this.enterAnimDurationTemp);
            ofFloat.setInterpolator(new DecelerateInterpolator());
            ofFloat.start();
            this.bkg.setVisibility(0);
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            ofFloat2.setDuration(this.enterAnimDurationTemp);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    DialogImpl.this.boxRoot.setBkgAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            ofFloat2.start();
        }

        public void refreshView() {
            if (this.boxRoot != null && BaseDialog.getTopActivity() != null) {
                if (FullScreenDialog.this.backgroundColor != -1) {
                    FullScreenDialog fullScreenDialog = FullScreenDialog.this;
                    fullScreenDialog.tintColor(this.bkg, fullScreenDialog.backgroundColor);
                }
                this.bkg.setMaxWidth(FullScreenDialog.this.getMaxWidth());
                if (FullScreenDialog.this.isCancelable()) {
                    this.boxRoot.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            if (FullScreenDialog.this.onBackgroundMaskClickListener == null || !FullScreenDialog.this.onBackgroundMaskClickListener.onClick(FullScreenDialog.this.f929me, view)) {
                                DialogImpl.this.doDismiss(view);
                            }
                        }
                    });
                } else {
                    this.boxRoot.setOnClickListener((View.OnClickListener) null);
                }
                if (FullScreenDialog.this.backgroundRadius > -1.0f) {
                    GradientDrawable gradientDrawable = (GradientDrawable) this.bkg.getBackground();
                    if (gradientDrawable != null) {
                        gradientDrawable.setCornerRadii(new float[]{FullScreenDialog.this.backgroundRadius, FullScreenDialog.this.backgroundRadius, FullScreenDialog.this.backgroundRadius, FullScreenDialog.this.backgroundRadius, 0.0f, 0.0f, 0.0f, 0.0f});
                    }
                    if (Build.VERSION.SDK_INT >= 21) {
                        this.bkg.setOutlineProvider(new ViewOutlineProvider() {
                            public void getOutline(View view, Outline outline) {
                                outline.setRoundRect(0, 0, view.getWidth(), (int) (((float) view.getHeight()) + FullScreenDialog.this.backgroundRadius), FullScreenDialog.this.backgroundRadius);
                            }
                        });
                        this.bkg.setClipToOutline(true);
                    }
                }
                if (FullScreenDialog.this.onBindView != null) {
                    FullScreenDialog.this.onBindView.bindParent(this.boxCustom, FullScreenDialog.this.f929me);
                    if (FullScreenDialog.this.onBindView.getCustomView() instanceof ScrollController) {
                        this.scrollView = (ScrollController) FullScreenDialog.this.onBindView.getCustomView();
                    } else {
                        View findViewWithTag = FullScreenDialog.this.onBindView.getCustomView().findViewWithTag("ScrollController");
                        if (findViewWithTag instanceof ScrollController) {
                            this.scrollView = (ScrollController) findViewWithTag;
                        }
                    }
                }
                if (FullScreenDialog.this.hideZoomBackground) {
                    FullScreenDialog.this.dialogView.setBackgroundResource(C1903R.C1905color.black20);
                    this.imgZoomActivity.setVisibility(8);
                } else {
                    FullScreenDialog.this.dialogView.setBackgroundResource(C1903R.C1905color.black);
                    this.imgZoomActivity.setVisibility(0);
                }
                this.fullScreenDialogTouchEventInterceptor.refresh(FullScreenDialog.this.f929me, this);
                FullScreenDialog.this.onDialogRefreshUI();
            }
        }

        public void doDismiss(View view) {
            if (view != null) {
                view.setEnabled(false);
            }
            if (BaseDialog.getTopActivity() != null && !FullScreenDialog.this.dismissAnimFlag) {
                boolean unused = FullScreenDialog.this.dismissAnimFlag = true;
                getDialogXAnimImpl().doExitAnim(FullScreenDialog.this.f929me, new ObjectRunnable<Float>() {
                    public void run(Float f) {
                        if (DialogImpl.this.boxRoot != null) {
                            DialogImpl.this.boxRoot.setBkgAlpha(f.floatValue());
                        }
                        if (f.floatValue() == 0.0f) {
                            if (DialogImpl.this.boxRoot != null) {
                                DialogImpl.this.boxRoot.setVisibility(8);
                            }
                            FullScreenDialog.dismiss(FullScreenDialog.this.dialogView);
                        }
                    }
                });
            }
        }

        public void preDismiss() {
            if (FullScreenDialog.this.isCancelable()) {
                doDismiss(this.boxRoot);
                return;
            }
            long j = 300;
            if (FullScreenDialog.overrideExitDuration >= 0) {
                j = (long) FullScreenDialog.overrideExitDuration;
            }
            if (FullScreenDialog.this.exitAnimDuration >= 0) {
                j = FullScreenDialog.this.exitAnimDuration;
            }
            MaxRelativeLayout maxRelativeLayout = this.bkg;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(maxRelativeLayout, "y", new float[]{maxRelativeLayout.getY(), this.bkgEnterAimY});
            ofFloat.setDuration(j);
            ofFloat.start();
        }

        /* access modifiers changed from: protected */
        public DialogXAnimInterface<FullScreenDialog> getDialogXAnimImpl() {
            if (FullScreenDialog.this.dialogXAnimImpl == null) {
                FullScreenDialog.this.dialogXAnimImpl = new DialogXAnimInterface<FullScreenDialog>() {
                    public void doShowAnim(FullScreenDialog fullScreenDialog, ObjectRunnable<Float> objectRunnable) {
                        int height = DialogImpl.this.boxCustom.getHeight();
                        if (height == 0 || isMatchParentHeightCustomView()) {
                            DialogImpl dialogImpl = DialogImpl.this;
                            dialogImpl.showEnterAnim((int) dialogImpl.boxRoot.getSafeHeight());
                            return;
                        }
                        DialogImpl.this.showEnterAnim(height);
                    }

                    private boolean isMatchParentHeightCustomView() {
                        ViewGroup.LayoutParams layoutParams;
                        if (FullScreenDialog.this.onBindView == null || FullScreenDialog.this.onBindView.getCustomView() == null || (layoutParams = FullScreenDialog.this.onBindView.getCustomView().getLayoutParams()) == null || layoutParams.height != -1) {
                            return false;
                        }
                        return true;
                    }

                    public void doExitAnim(FullScreenDialog fullScreenDialog, final ObjectRunnable<Float> objectRunnable) {
                        long j = FullScreenDialog.overrideExitDuration >= 0 ? (long) FullScreenDialog.overrideExitDuration : 300;
                        if (FullScreenDialog.this.exitAnimDuration >= 0) {
                            j = FullScreenDialog.this.exitAnimDuration;
                        }
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DialogImpl.this.bkg, "y", new float[]{DialogImpl.this.bkg.getY(), (float) DialogImpl.this.boxBkg.getHeight()});
                        ofFloat.setDuration(j);
                        ofFloat.start();
                        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
                        ofFloat2.setDuration(j);
                        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                objectRunnable.run((Float) valueAnimator.getAnimatedValue());
                            }
                        });
                        ofFloat2.start();
                    }
                };
            }
            return FullScreenDialog.this.dialogXAnimImpl;
        }
    }

    public String dialogKey() {
        return getClass().getSimpleName() + "(" + Integer.toHexString(hashCode()) + ")";
    }

    public void refreshUI() {
        if (getDialogImpl() != null) {
            runOnMain(new Runnable() {
                public void run() {
                    if (FullScreenDialog.this.dialogImpl != null) {
                        FullScreenDialog.this.dialogImpl.refreshView();
                    }
                }
            });
        }
    }

    public void dismiss() {
        runOnMain(new Runnable() {
            public void run() {
                if (FullScreenDialog.this.dialogImpl != null) {
                    FullScreenDialog.this.dialogImpl.doDismiss((View) null);
                }
            }
        });
    }

    public DialogLifecycleCallback<FullScreenDialog> getDialogLifecycleCallback() {
        DialogLifecycleCallback<FullScreenDialog> dialogLifecycleCallback2 = this.dialogLifecycleCallback;
        return dialogLifecycleCallback2 == null ? new DialogLifecycleCallback<FullScreenDialog>() {
        } : dialogLifecycleCallback2;
    }

    public FullScreenDialog setDialogLifecycleCallback(DialogLifecycleCallback<FullScreenDialog> dialogLifecycleCallback2) {
        this.dialogLifecycleCallback = dialogLifecycleCallback2;
        if (this.isShow) {
            dialogLifecycleCallback2.onShow(this.f929me);
        }
        return this;
    }

    public OnBackPressedListener<FullScreenDialog> getOnBackPressedListener() {
        return this.onBackPressedListener;
    }

    public FullScreenDialog setOnBackPressedListener(OnBackPressedListener<FullScreenDialog> onBackPressedListener2) {
        this.onBackPressedListener = onBackPressedListener2;
        refreshUI();
        return this;
    }

    public FullScreenDialog setStyle(DialogXStyle dialogXStyle) {
        this.style = dialogXStyle;
        return this;
    }

    public FullScreenDialog setTheme(DialogX.THEME theme) {
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

    public FullScreenDialog setCancelable(boolean z) {
        this.privateCancelable = z ? BaseDialog.BOOLEAN.TRUE : BaseDialog.BOOLEAN.FALSE;
        refreshUI();
        return this;
    }

    public DialogImpl getDialogImpl() {
        return this.dialogImpl;
    }

    public FullScreenDialog setCustomView(OnBindView<FullScreenDialog> onBindView2) {
        this.onBindView = onBindView2;
        refreshUI();
        return this;
    }

    public View getCustomView() {
        OnBindView<FullScreenDialog> onBindView2 = this.onBindView;
        if (onBindView2 == null) {
            return null;
        }
        return onBindView2.getCustomView();
    }

    public FullScreenDialog removeCustomView() {
        this.onBindView.clean();
        refreshUI();
        return this;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public FullScreenDialog setBackgroundColor(int i) {
        this.backgroundColor = i;
        refreshUI();
        return this;
    }

    public FullScreenDialog setBackgroundColorRes(int i) {
        this.backgroundColor = getColor(i);
        refreshUI();
        return this;
    }

    public long getEnterAnimDuration() {
        return this.enterAnimDuration;
    }

    public FullScreenDialog setEnterAnimDuration(long j) {
        this.enterAnimDuration = j;
        return this;
    }

    public long getExitAnimDuration() {
        return this.exitAnimDuration;
    }

    public FullScreenDialog setExitAnimDuration(long j) {
        this.exitAnimDuration = j;
        return this;
    }

    public boolean isHideZoomBackground() {
        return this.hideZoomBackground;
    }

    public FullScreenDialog setHideZoomBackground(boolean z) {
        this.hideZoomBackground = z;
        refreshUI();
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
        this.enterAnimDuration = 0;
        View createView = createView(isLightTheme() ? C1903R.layout.layout_dialogx_fullscreen : C1903R.layout.layout_dialogx_fullscreen_dark);
        this.dialogView = createView;
        this.dialogImpl = new DialogImpl(createView);
        View view2 = this.dialogView;
        if (view2 != null) {
            view2.setTag(this.f929me);
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
            getDialogImpl().getDialogXAnimImpl().doExitAnim(this.f929me, new ObjectRunnable<Float>() {
                public void run(Float f) {
                    FullScreenDialog.this.getDialogImpl().boxRoot.setBkgAlpha(f.floatValue());
                    if (f.floatValue() == 0.0f && FullScreenDialog.this.getDialogView() != null) {
                        FullScreenDialog.this.getDialogView().setVisibility(8);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void shutdown() {
        dismiss();
    }

    public FullScreenDialog setMaxWidth(int i) {
        this.maxWidth = i;
        refreshUI();
        return this;
    }

    public FullScreenDialog setDialogImplMode(DialogX.IMPL_MODE impl_mode) {
        this.dialogImplMode = impl_mode;
        return this;
    }

    public OnBackgroundMaskClickListener<FullScreenDialog> getOnBackgroundMaskClickListener() {
        return this.onBackgroundMaskClickListener;
    }

    public FullScreenDialog setOnBackgroundMaskClickListener(OnBackgroundMaskClickListener<FullScreenDialog> onBackgroundMaskClickListener2) {
        this.onBackgroundMaskClickListener = onBackgroundMaskClickListener2;
        return this;
    }

    public FullScreenDialog setRadius(float f) {
        this.backgroundRadius = f;
        refreshUI();
        return this;
    }

    public float getRadius() {
        return this.backgroundRadius;
    }

    public boolean isAllowInterceptTouch() {
        return this.allowInterceptTouch;
    }

    public FullScreenDialog setAllowInterceptTouch(boolean z) {
        this.allowInterceptTouch = z;
        refreshUI();
        return this;
    }

    public DialogXAnimInterface<FullScreenDialog> getDialogXAnimImpl() {
        return this.dialogXAnimImpl;
    }

    public FullScreenDialog setDialogXAnimImpl(DialogXAnimInterface<FullScreenDialog> dialogXAnimInterface) {
        this.dialogXAnimImpl = dialogXAnimInterface;
        return this;
    }
}
