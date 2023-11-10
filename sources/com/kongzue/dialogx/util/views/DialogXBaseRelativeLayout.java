package com.kongzue.dialogx.util.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import androidx.core.view.ViewCompat;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.OnSafeInsetsChangeListener;
import java.lang.ref.WeakReference;

public class DialogXBaseRelativeLayout extends RelativeLayout {
    private boolean autoUnsafePlacePadding = true;
    private ViewTreeObserver.OnGlobalLayoutListener decorViewLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= 23) {
                DialogXBaseRelativeLayout dialogXBaseRelativeLayout = DialogXBaseRelativeLayout.this;
                dialogXBaseRelativeLayout.paddingView(dialogXBaseRelativeLayout.getRootWindowInsets());
            } else if (BaseDialog.getTopActivity() != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                BaseDialog.getTopActivity().getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
                Rect rect = new Rect();
                BaseDialog.getTopActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                DialogXBaseRelativeLayout.this.paddingView(rect.left, rect.top, displayMetrics.widthPixels - rect.right, displayMetrics.heightPixels - rect.bottom);
            }
        }
    };
    private boolean focusable = true;
    private boolean interceptBack = true;
    private boolean isInited = false;
    boolean isLightMode = true;
    float nowBkgAlphaValue;
    private PrivateBackPressedListener onBackPressedListener;
    private OnLifecycleCallBack onLifecycleCallBack;
    private OnSafeInsetsChangeListener onSafeInsetsChangeListener;
    private BaseDialog parentDialog;
    private WeakReference<View> requestFocusView;
    protected Rect unsafePlace = new Rect();

    public static abstract class OnLifecycleCallBack {
        public abstract void onDismiss();

        public void onShow() {
        }
    }

    public interface PrivateBackPressedListener {
        boolean onBackPressed();
    }

    public DialogXBaseRelativeLayout(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    public DialogXBaseRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public DialogXBaseRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        if (Build.VERSION.SDK_INT >= 29) {
            setForceDarkAllowed(false);
        }
        if (!this.isInited) {
            if (attributeSet != null) {
                TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C1903R.styleable.DialogXBaseRelativeLayout);
                this.focusable = obtainStyledAttributes.getBoolean(C1903R.styleable.DialogXBaseRelativeLayout_baseFocusable, true);
                this.autoUnsafePlacePadding = obtainStyledAttributes.getBoolean(C1903R.styleable.DialogXBaseRelativeLayout_autoSafeArea, true);
                this.interceptBack = obtainStyledAttributes.getBoolean(C1903R.styleable.DialogXBaseRelativeLayout_interceptBack, true);
                obtainStyledAttributes.recycle();
                this.isInited = true;
            }
            if (this.focusable) {
                setFocusable(true);
                setFocusableInTouchMode(true);
                requestFocus();
            }
            setBkgAlpha(0.0f);
            BaseDialog baseDialog = this.parentDialog;
            if (baseDialog != null && baseDialog.getDialogImplMode() != DialogX.IMPL_MODE.VIEW) {
                setFitsSystemWindows(true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        if (DialogX.useActivityLayoutTranslationNavigationBar || this.parentDialog.getDialogImplMode() != DialogX.IMPL_MODE.VIEW) {
            paddingView(rect.left, rect.top, rect.right, rect.bottom);
        }
        return super.fitSystemWindows(rect);
    }

    public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
        if (Build.VERSION.SDK_INT >= 21 && (DialogX.useActivityLayoutTranslationNavigationBar || this.parentDialog.getDialogImplMode() != DialogX.IMPL_MODE.VIEW)) {
            paddingView(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
        return super.dispatchApplyWindowInsets(windowInsets);
    }

    public void paddingView(WindowInsets windowInsets) {
        if (windowInsets == null) {
            if (BaseDialog.publicWindowInsets() != null) {
                windowInsets = BaseDialog.publicWindowInsets();
            } else {
                return;
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            paddingView(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        PrivateBackPressedListener privateBackPressedListener;
        if (!isAttachedToWindow() || keyEvent.getAction() != 1 || keyEvent.getKeyCode() != 4 || !this.interceptBack || (privateBackPressedListener = this.onBackPressedListener) == null) {
            return super.dispatchKeyEvent(keyEvent);
        }
        return privateBackPressedListener.onBackPressed();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindowToken(), 2);
        return super.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            ViewParent parent = getParent();
            if (parent instanceof View) {
                ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows((View) parent));
            }
            ViewCompat.requestApplyInsets(this);
            if (BaseDialog.getTopActivity() != null) {
                BaseDialog.getTopActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this.decorViewLayoutListener);
                this.decorViewLayoutListener.onGlobalLayout();
                OnLifecycleCallBack onLifecycleCallBack2 = this.onLifecycleCallBack;
                if (onLifecycleCallBack2 != null) {
                    onLifecycleCallBack2.onShow();
                }
                this.isLightMode = (getResources().getConfiguration().uiMode & 48) == 16;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (!(this.decorViewLayoutListener == null || BaseDialog.getTopActivity() == null)) {
            BaseDialog.getTopActivity().getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this.decorViewLayoutListener);
        }
        OnLifecycleCallBack onLifecycleCallBack2 = this.onLifecycleCallBack;
        if (onLifecycleCallBack2 != null) {
            onLifecycleCallBack2.onDismiss();
        }
        this.onSafeInsetsChangeListener = null;
        super.onDetachedFromWindow();
    }

    public boolean performClick() {
        if (!isEnabled()) {
            return false;
        }
        return super.performClick();
    }

    public boolean callOnClick() {
        if (!isEnabled()) {
            return false;
        }
        return super.callOnClick();
    }

    public DialogXBaseRelativeLayout setOnLifecycleCallBack(OnLifecycleCallBack onLifecycleCallBack2) {
        this.onLifecycleCallBack = onLifecycleCallBack2;
        return this;
    }

    public float getSafeHeight() {
        return (float) ((getMeasuredHeight() - this.unsafePlace.bottom) - this.unsafePlace.top);
    }

    public void bindFocusView(View view) {
        this.requestFocusView = new WeakReference<>(view);
    }

    public boolean requestFocus(int i, Rect rect) {
        WeakReference<View> weakReference;
        if (i != 130 || (weakReference = this.requestFocusView) == null || weakReference.get() == null) {
            return super.requestFocus(i, rect);
        }
        return ((View) this.requestFocusView.get()).requestFocus();
    }

    /* access modifiers changed from: private */
    public void paddingView(int i, int i2, int i3, int i4) {
        Rect rect = new Rect(i, i2, i3, i4);
        this.unsafePlace = rect;
        OnSafeInsetsChangeListener onSafeInsetsChangeListener2 = this.onSafeInsetsChangeListener;
        if (onSafeInsetsChangeListener2 != null) {
            onSafeInsetsChangeListener2.onChange(rect);
        }
        MaxRelativeLayout maxRelativeLayout = (MaxRelativeLayout) findViewById(C1903R.C1907id.bkg);
        if (maxRelativeLayout != null && (maxRelativeLayout.getLayoutParams() instanceof RelativeLayout.LayoutParams) && ((RelativeLayout.LayoutParams) maxRelativeLayout.getLayoutParams()).getRules()[12] == -1 && isAutoUnsafePlacePadding()) {
            maxRelativeLayout.setPadding(0, 0, 0, i4);
            maxRelativeLayout.setNavBarHeight(i4);
            setPadding(i, i2, i3, 0);
        } else if (isAutoUnsafePlacePadding()) {
            setPadding(i, i2, i3, i4);
        }
    }

    public DialogXBaseRelativeLayout setOnBackPressedListener(PrivateBackPressedListener privateBackPressedListener) {
        this.onBackPressedListener = privateBackPressedListener;
        return this;
    }

    public OnSafeInsetsChangeListener getOnSafeInsetsChangeListener() {
        return this.onSafeInsetsChangeListener;
    }

    public DialogXBaseRelativeLayout setOnSafeInsetsChangeListener(OnSafeInsetsChangeListener onSafeInsetsChangeListener2) {
        this.onSafeInsetsChangeListener = onSafeInsetsChangeListener2;
        return this;
    }

    public boolean isAutoUnsafePlacePadding() {
        return this.autoUnsafePlacePadding;
    }

    public Rect getUnsafePlace() {
        return this.unsafePlace;
    }

    public DialogXBaseRelativeLayout setAutoUnsafePlacePadding(boolean z) {
        this.autoUnsafePlacePadding = z;
        if (!z) {
            setPadding(0, 0, 0, 0);
        }
        return this;
    }

    public BaseDialog getParentDialog() {
        return this.parentDialog;
    }

    public DialogXBaseRelativeLayout setParentDialog(BaseDialog baseDialog) {
        this.parentDialog = baseDialog;
        if (baseDialog.getDialogImplMode() != DialogX.IMPL_MODE.VIEW) {
            setFitsSystemWindows(true);
            if (Build.VERSION.SDK_INT >= 23) {
                paddingView(getRootWindowInsets());
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.isLightMode != ((configuration.uiMode & 48) == 16) && DialogX.globalTheme == DialogX.THEME.AUTO) {
            getParentDialog().restartDialog();
        }
    }

    public DialogXBaseRelativeLayout setBkgAlpha(float f) {
        this.nowBkgAlphaValue = f;
        if (getBackground() != null) {
            getBackground().mutate().setAlpha((int) (f * 255.0f));
        }
        return this;
    }

    public void setBackground(Drawable drawable) {
        drawable.setAlpha((int) (this.nowBkgAlphaValue * 255.0f));
        super.setBackground(drawable);
    }

    public void setBackgroundColor(int i) {
        setBackground(new ColorDrawable(i));
    }

    public boolean isBaseFocusable() {
        return this.focusable;
    }

    public boolean isInterceptBack() {
        return this.interceptBack;
    }

    public DialogXBaseRelativeLayout setInterceptBack(boolean z) {
        this.interceptBack = z;
        return this;
    }

    public void setVisibility(int i) {
        if (i == 8 && getAlpha() == 0.0f) {
            setAlpha(0.01f);
        }
        super.setVisibility(i);
    }
}
