package com.kongzue.dialogx.interfaces;

import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.util.TextInfo;

public abstract class MenuItemTextInfoInterceptor<D extends BaseDialog> {
    private boolean autoTintIconInLightOrDarkMode;

    public abstract TextInfo menuItemTextInfo(D d, int i, String str);

    public MenuItemTextInfoInterceptor() {
    }

    public MenuItemTextInfoInterceptor(boolean z) {
        this.autoTintIconInLightOrDarkMode = z;
    }

    public boolean isAutoTintIconInLightOrDarkMode() {
        return this.autoTintIconInLightOrDarkMode;
    }
}
