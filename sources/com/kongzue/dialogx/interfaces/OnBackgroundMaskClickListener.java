package com.kongzue.dialogx.interfaces;

import android.view.View;
import com.kongzue.dialogx.interfaces.BaseDialog;

public interface OnBackgroundMaskClickListener<D extends BaseDialog> {
    boolean onClick(D d, View view);
}
