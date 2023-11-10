package com.kongzue.dialogx.dialogs;

import android.app.Activity;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.kongzue.dialogx.interfaces.DialogXAnimInterface;
import com.kongzue.dialogx.interfaces.OnBackPressedListener;
import com.kongzue.dialogx.interfaces.OnBackgroundMaskClickListener;

public class TipDialog extends WaitDialog {
    public static final int NO_AUTO_DISMISS = -1;

    protected TipDialog() {
    }

    public static WaitDialog show(int i) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(i, WaitDialog.TYPE.WARNING);
        showWithInstance(noInstance);
        return m119me();
    }

    public static WaitDialog show(Activity activity, int i) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(i, WaitDialog.TYPE.WARNING);
        showWithInstance(noInstance, activity);
        return instanceNotNull;
    }

    public static WaitDialog show(CharSequence charSequence) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(charSequence, WaitDialog.TYPE.WARNING);
        showWithInstance(noInstance);
        return m119me();
    }

    public static WaitDialog show(Activity activity, CharSequence charSequence) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(charSequence, WaitDialog.TYPE.WARNING);
        if (noInstance) {
            showWithInstance(noInstance, activity);
        }
        return instanceNotNull;
    }

    public static WaitDialog show(int i, WaitDialog.TYPE type) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(i, type);
        showWithInstance(noInstance);
        return m119me();
    }

    public static WaitDialog show(Activity activity, int i, WaitDialog.TYPE type) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(i, type);
        if (noInstance) {
            showWithInstance(noInstance, activity);
        }
        return instanceNotNull;
    }

    public static WaitDialog show(CharSequence charSequence, WaitDialog.TYPE type) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(charSequence, type);
        showWithInstance(noInstance);
        return m119me();
    }

    public static WaitDialog show(Activity activity, CharSequence charSequence, WaitDialog.TYPE type) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(charSequence, type);
        if (noInstance) {
            showWithInstance(noInstance, activity);
        }
        return instanceNotNull;
    }

    public static WaitDialog show(int i, WaitDialog.TYPE type, long j) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(i, type);
        m119me().setTipShowDuration(j);
        showWithInstance(noInstance);
        return m119me();
    }

    public static WaitDialog show(Activity activity, int i, WaitDialog.TYPE type, long j) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(i, type);
        instanceNotNull.setTipShowDuration(j);
        if (noInstance) {
            showWithInstance(noInstance, activity);
        }
        return instanceNotNull;
    }

    public static WaitDialog show(CharSequence charSequence, WaitDialog.TYPE type, long j) {
        boolean noInstance = noInstance();
        if (noInstance) {
            instanceBuild();
        }
        m119me().setTip(charSequence, type);
        m119me().setTipShowDuration(j);
        showWithInstance(noInstance);
        return m119me();
    }

    public static WaitDialog show(Activity activity, CharSequence charSequence, WaitDialog.TYPE type, long j) {
        boolean noInstance = noInstance(activity);
        if (noInstance) {
            instanceBuild();
        }
        WaitDialog instanceNotNull = getInstanceNotNull(activity);
        instanceNotNull.setTip(charSequence, type);
        instanceNotNull.setTipShowDuration(j);
        if (noInstance) {
            showWithInstance(noInstance, activity);
        }
        return instanceNotNull;
    }

    public String dialogKey() {
        return getClass().getSimpleName() + "(" + Integer.toHexString(hashCode()) + ")";
    }

    protected static void showWithInstance(boolean z) {
        if (z) {
            m119me().show();
            return;
        }
        m119me().refreshUI();
        m119me().showTip(m119me().readyTipType);
    }

    protected static void showWithInstance(boolean z, Activity activity) {
        if (z) {
            m119me().show(activity);
            return;
        }
        m119me().refreshUI();
        m119me().showTip(m119me().readyTipType);
    }

    public TipDialog setMaxWidth(int i) {
        this.maxWidth = i;
        refreshUI();
        return this;
    }

    public TipDialog setDialogImplMode(DialogX.IMPL_MODE impl_mode) {
        this.dialogImplMode = impl_mode;
        return this;
    }

    public boolean isBkgInterceptTouch() {
        return this.bkgInterceptTouch;
    }

    public TipDialog setBkgInterceptTouch(boolean z) {
        this.bkgInterceptTouch = z;
        return this;
    }

    public OnBackgroundMaskClickListener<WaitDialog> getOnBackgroundMaskClickListener() {
        return this.onBackgroundMaskClickListener;
    }

    public TipDialog setOnBackgroundMaskClickListener(OnBackgroundMaskClickListener<WaitDialog> onBackgroundMaskClickListener) {
        this.onBackgroundMaskClickListener = onBackgroundMaskClickListener;
        return this;
    }

    public TipDialog setRadius(float f) {
        this.backgroundRadius = f;
        refreshUI();
        return this;
    }

    public float getRadius() {
        return this.backgroundRadius;
    }

    public TipDialog setDialogXAnimImpl(DialogXAnimInterface<WaitDialog> dialogXAnimInterface) {
        this.dialogXAnimImpl = dialogXAnimInterface;
        return this;
    }

    public TipDialog setOnBackPressedListener(OnBackPressedListener<WaitDialog> onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
        refreshUI();
        return this;
    }
}
