package com.kongzue.dialogx.interfaces;

public abstract class OnMenuItemSelectListener<D> implements OnMenuItemClickListener<D> {
    @Deprecated
    public boolean onClick(D d, CharSequence charSequence, int i) {
        return true;
    }

    public void onMultiItemSelect(D d, CharSequence[] charSequenceArr, int[] iArr) {
    }

    public void onOneItemSelect(D d, CharSequence charSequence, int i, boolean z) {
    }
}
