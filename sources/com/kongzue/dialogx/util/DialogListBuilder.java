package com.kongzue.dialogx.util;

import com.kongzue.dialogx.interfaces.BaseDialog;
import java.util.ArrayList;

public class DialogListBuilder {
    ArrayList<BaseDialog> dialogs;

    public static DialogListBuilder create(BaseDialog... baseDialogArr) {
        DialogListBuilder dialogListBuilder = new DialogListBuilder();
        for (BaseDialog add : baseDialogArr) {
            dialogListBuilder.add(add);
        }
        return dialogListBuilder;
    }

    public DialogListBuilder add(BaseDialog baseDialog) {
        if (this.dialogs == null) {
            this.dialogs = new ArrayList<>();
        }
        if (!baseDialog.isShow() && !baseDialog.isPreShow()) {
            baseDialog.setDialogListBuilder(this);
            this.dialogs.add(baseDialog);
        }
        return this;
    }

    public DialogListBuilder show() {
        ArrayList<BaseDialog> arrayList = this.dialogs;
        if (arrayList != null && !arrayList.isEmpty()) {
            this.dialogs.get(0).show();
        }
        return this;
    }

    public void showNext() {
        ArrayList<BaseDialog> arrayList = this.dialogs;
        if (arrayList != null && !arrayList.isEmpty()) {
            ArrayList<BaseDialog> arrayList2 = this.dialogs;
            arrayList2.remove(arrayList2.get(0));
            if (!this.dialogs.isEmpty()) {
                this.dialogs.get(0).show();
            }
        }
    }

    public boolean isEmpty() {
        ArrayList<BaseDialog> arrayList = this.dialogs;
        if (arrayList == null) {
            return true;
        }
        return arrayList.isEmpty();
    }
}
