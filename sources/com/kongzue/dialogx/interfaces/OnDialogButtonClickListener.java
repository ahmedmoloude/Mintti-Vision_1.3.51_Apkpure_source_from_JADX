package com.kongzue.dialogx.interfaces;

import android.view.View;
import com.kongzue.dialogx.interfaces.BaseDialog;

public interface OnDialogButtonClickListener<D extends BaseDialog> extends BaseOnDialogClickCallback {
    boolean onClick(D d, View view);
}
