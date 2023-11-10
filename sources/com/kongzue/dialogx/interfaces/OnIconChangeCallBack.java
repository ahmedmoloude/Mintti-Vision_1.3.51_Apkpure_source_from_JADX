package com.kongzue.dialogx.interfaces;

import com.kongzue.dialogx.interfaces.BaseDialog;

public abstract class OnIconChangeCallBack<D extends BaseDialog> {
    private boolean autoTintIconInLightOrDarkMode;

    public abstract int getIcon(D d, int i, String str);

    public OnIconChangeCallBack() {
    }

    public OnIconChangeCallBack(boolean z) {
        this.autoTintIconInLightOrDarkMode = z;
    }

    public boolean isAutoTintIconInLightOrDarkMode() {
        return this.autoTintIconInLightOrDarkMode;
    }
}
