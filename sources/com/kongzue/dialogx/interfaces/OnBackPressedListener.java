package com.kongzue.dialogx.interfaces;

import com.kongzue.dialogx.interfaces.BaseDialog;

public interface OnBackPressedListener<D extends BaseDialog> {
    boolean onBackPressed(D d);
}
