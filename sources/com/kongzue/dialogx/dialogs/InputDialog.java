package com.kongzue.dialogx.dialogs;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.DialogXAnimInterface;
import com.kongzue.dialogx.interfaces.DialogXStyle;
import com.kongzue.dialogx.interfaces.OnBackPressedListener;
import com.kongzue.dialogx.interfaces.OnBackgroundMaskClickListener;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialogx.util.InputInfo;
import com.kongzue.dialogx.util.TextInfo;

public class InputDialog extends MessageDialog {
    protected InputDialog() {
    }

    public static InputDialog build() {
        return new InputDialog();
    }

    public static InputDialog build(DialogXStyle dialogXStyle) {
        InputDialog inputDialog = new InputDialog();
        inputDialog.setStyle(dialogXStyle);
        return inputDialog;
    }

    public static InputDialog build(OnBindView<MessageDialog> onBindView) {
        return new InputDialog().setCustomView(onBindView);
    }

    public InputDialog(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        this.cancelable = DialogX.cancelable;
        this.title = charSequence;
        this.message = charSequence2;
        this.okText = charSequence3;
    }

    public InputDialog(int i, int i2, int i3) {
        this.cancelable = DialogX.cancelable;
        this.title = getString(i);
        this.message = getString(i2);
        this.okText = getString(i3);
    }

    public static InputDialog show(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        InputDialog inputDialog = new InputDialog(charSequence, charSequence2, charSequence3);
        inputDialog.show();
        return inputDialog;
    }

    public static InputDialog show(int i, int i2, int i3) {
        InputDialog inputDialog = new InputDialog(i, i2, i3);
        inputDialog.show();
        return inputDialog;
    }

    public InputDialog(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {
        this.cancelable = DialogX.cancelable;
        this.title = charSequence;
        this.message = charSequence2;
        this.okText = charSequence3;
        this.cancelText = charSequence4;
    }

    public InputDialog(int i, int i2, int i3, int i4) {
        this.cancelable = DialogX.cancelable;
        this.title = getString(i);
        this.message = getString(i2);
        this.okText = getString(i3);
        this.cancelText = getString(i4);
    }

    public static InputDialog show(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {
        InputDialog inputDialog = new InputDialog(charSequence, charSequence2, charSequence3, charSequence4);
        inputDialog.show();
        return inputDialog;
    }

    public static InputDialog show(int i, int i2, int i3, int i4) {
        InputDialog inputDialog = new InputDialog(i, i2, i3, i4);
        inputDialog.show();
        return inputDialog;
    }

    public InputDialog(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, String str) {
        this.cancelable = DialogX.cancelable;
        this.title = charSequence;
        this.message = charSequence2;
        this.okText = charSequence3;
        this.cancelText = charSequence4;
        this.inputText = str;
    }

    public static InputDialog show(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, String str) {
        InputDialog inputDialog = new InputDialog(charSequence, charSequence2, charSequence3, charSequence4, str);
        inputDialog.show();
        return inputDialog;
    }

    public InputDialog(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {
        this.cancelable = DialogX.cancelable;
        this.title = charSequence;
        this.message = charSequence2;
        this.okText = charSequence3;
        this.cancelText = charSequence4;
        this.otherText = charSequence5;
    }

    public InputDialog(int i, int i2, int i3, int i4, int i5) {
        this.cancelable = DialogX.cancelable;
        this.title = getString(i);
        this.message = getString(i2);
        this.okText = getString(i3);
        this.cancelText = getString(i4);
        this.otherText = getString(i5);
    }

    public static InputDialog show(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {
        InputDialog inputDialog = new InputDialog(charSequence, charSequence2, charSequence3, charSequence4, charSequence5);
        inputDialog.show();
        return inputDialog;
    }

    public static InputDialog show(int i, int i2, int i3, int i4, int i5) {
        InputDialog inputDialog = new InputDialog(i, i2, i3, i4, i5);
        inputDialog.show();
        return inputDialog;
    }

    public InputDialog(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5, String str) {
        this.cancelable = DialogX.cancelable;
        this.title = charSequence;
        this.message = charSequence2;
        this.okText = charSequence3;
        this.cancelText = charSequence4;
        this.otherText = charSequence5;
        this.inputText = str;
    }

    public InputDialog(int i, int i2, int i3, int i4, int i5, int i6) {
        this.cancelable = DialogX.cancelable;
        this.title = getString(i);
        this.message = getString(i2);
        this.okText = getString(i3);
        this.cancelText = getString(i4);
        this.otherText = getString(i5);
        this.inputText = getString(i6);
    }

    public static InputDialog show(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5, String str) {
        InputDialog inputDialog = new InputDialog(charSequence, charSequence2, charSequence3, charSequence4, charSequence5, str);
        inputDialog.show();
        return inputDialog;
    }

    public static InputDialog show(int i, int i2, int i3, int i4, int i5, int i6) {
        InputDialog inputDialog = new InputDialog(i, i2, i3, i4, i5, i6);
        inputDialog.show();
        return inputDialog;
    }

    public String dialogKey() {
        return getClass().getSimpleName() + "(" + Integer.toHexString(hashCode()) + ")";
    }

    public CharSequence getOkButton() {
        return this.okText;
    }

    public InputDialog setOkButton(CharSequence charSequence) {
        this.okText = charSequence;
        refreshUI();
        return this;
    }

    public InputDialog setOkButton(int i) {
        this.okText = getString(i);
        refreshUI();
        return this;
    }

    public InputDialog setOkButton(OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.okButtonClickListener = onInputDialogButtonClickListener;
        return this;
    }

    public InputDialog setOkButton(CharSequence charSequence, OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.okText = charSequence;
        this.okButtonClickListener = onInputDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public InputDialog setOkButton(int i, OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.okText = getString(i);
        this.okButtonClickListener = onInputDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public CharSequence getCancelButton() {
        return this.cancelText;
    }

    public InputDialog setCancelButton(CharSequence charSequence) {
        this.cancelText = charSequence;
        refreshUI();
        return this;
    }

    public InputDialog setCancelButton(int i) {
        this.cancelText = getString(i);
        refreshUI();
        return this;
    }

    public InputDialog setCancelButton(OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.cancelButtonClickListener = onInputDialogButtonClickListener;
        return this;
    }

    public InputDialog setCancelButton(CharSequence charSequence, OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.cancelText = charSequence;
        this.cancelButtonClickListener = onInputDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public InputDialog setCancelButton(int i, OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.cancelText = getString(i);
        this.cancelButtonClickListener = onInputDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public CharSequence getOtherButton() {
        return this.otherText;
    }

    public InputDialog setOtherButton(CharSequence charSequence) {
        this.otherText = charSequence;
        refreshUI();
        return this;
    }

    public InputDialog setOtherButton(int i) {
        this.otherText = getString(i);
        refreshUI();
        return this;
    }

    public InputDialog setOtherButton(OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.otherButtonClickListener = onInputDialogButtonClickListener;
        return this;
    }

    public InputDialog setOtherButton(CharSequence charSequence, OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.otherText = charSequence;
        this.otherButtonClickListener = onInputDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public InputDialog setOtherButton(int i, OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.otherText = getString(i);
        this.otherButtonClickListener = onInputDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public OnInputDialogButtonClickListener<InputDialog> getInputOkButtonClickListener() {
        return (OnInputDialogButtonClickListener) this.okButtonClickListener;
    }

    public InputDialog setOkButtonClickListener(OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.okButtonClickListener = onInputDialogButtonClickListener;
        return this;
    }

    public OnInputDialogButtonClickListener getInputCancelButtonClickListener() {
        return (OnInputDialogButtonClickListener) this.cancelButtonClickListener;
    }

    public InputDialog setCancelButtonClickListener(OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.cancelButtonClickListener = onInputDialogButtonClickListener;
        return this;
    }

    public OnInputDialogButtonClickListener getInputOtherButtonClickListener() {
        return (OnInputDialogButtonClickListener) this.otherButtonClickListener;
    }

    public InputDialog setOtherButtonClickListener(OnInputDialogButtonClickListener<InputDialog> onInputDialogButtonClickListener) {
        this.otherButtonClickListener = onInputDialogButtonClickListener;
        return this;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public InputDialog setTitle(CharSequence charSequence) {
        this.title = charSequence;
        refreshUI();
        return this;
    }

    public InputDialog setTitle(int i) {
        this.title = getString(i);
        refreshUI();
        return this;
    }

    public CharSequence getMessage() {
        return this.message;
    }

    public InputDialog setMessage(CharSequence charSequence) {
        this.message = charSequence;
        refreshUI();
        return this;
    }

    public InputDialog setMessage(int i) {
        this.message = getString(i);
        refreshUI();
        return this;
    }

    public String getInputText() {
        if (getDialogImpl() == null || getDialogImpl().txtInput == null) {
            return this.inputText;
        }
        return getDialogImpl().txtInput.getText().toString();
    }

    public InputDialog setInputText(String str) {
        this.inputText = str;
        refreshUI();
        return this;
    }

    public InputDialog setInputText(int i) {
        this.inputText = getString(i);
        refreshUI();
        return this;
    }

    public String getInputHintText() {
        return this.inputHintText;
    }

    public InputDialog setInputHintText(String str) {
        this.inputHintText = str;
        refreshUI();
        return this;
    }

    public InputDialog setInputHintText(int i) {
        this.inputHintText = getString(i);
        refreshUI();
        return this;
    }

    public TextInfo getTitleTextInfo() {
        return this.titleTextInfo;
    }

    public InputDialog setTitleTextInfo(TextInfo textInfo) {
        this.titleTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getMessageTextInfo() {
        return this.messageTextInfo;
    }

    public InputDialog setMessageTextInfo(TextInfo textInfo) {
        this.messageTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getOkTextInfo() {
        return this.okTextInfo;
    }

    public InputDialog setOkTextInfo(TextInfo textInfo) {
        this.okTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getCancelTextInfo() {
        return this.cancelTextInfo;
    }

    public InputDialog setCancelTextInfo(TextInfo textInfo) {
        this.cancelTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getOtherTextInfo() {
        return this.otherTextInfo;
    }

    public InputDialog setOtherTextInfo(TextInfo textInfo) {
        this.otherTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public InputInfo getInputInfo() {
        return this.inputInfo;
    }

    public InputDialog setInputInfo(InputInfo inputInfo) {
        this.inputInfo = inputInfo;
        refreshUI();
        return this;
    }

    public int getButtonOrientation() {
        return this.buttonOrientation;
    }

    public InputDialog setButtonOrientation(int i) {
        this.buttonOrientation = i;
        refreshUI();
        return this;
    }

    public boolean isCancelable() {
        if (this.privateCancelable != null) {
            if (this.privateCancelable == BaseDialog.BOOLEAN.TRUE) {
                return true;
            }
            return false;
        } else if (overrideCancelable == null) {
            return this.cancelable;
        } else {
            if (overrideCancelable == BaseDialog.BOOLEAN.TRUE) {
                return true;
            }
            return false;
        }
    }

    public InputDialog setCancelable(boolean z) {
        this.privateCancelable = z ? BaseDialog.BOOLEAN.TRUE : BaseDialog.BOOLEAN.FALSE;
        refreshUI();
        return this;
    }

    public OnBackPressedListener<MessageDialog> getOnBackPressedListener() {
        return this.onBackPressedListener;
    }

    public InputDialog setOnBackPressedListener(OnBackPressedListener<MessageDialog> onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
        return this;
    }

    public boolean isAutoShowInputKeyboard() {
        return this.autoShowInputKeyboard;
    }

    public InputDialog setAutoShowInputKeyboard(boolean z) {
        this.autoShowInputKeyboard = z;
        return this;
    }

    public InputDialog setCustomView(OnBindView<MessageDialog> onBindView) {
        this.onBindView = onBindView;
        refreshUI();
        return this;
    }

    public View getCustomView() {
        if (this.onBindView == null) {
            return null;
        }
        return this.onBindView.getCustomView();
    }

    public InputDialog removeCustomView() {
        this.onBindView.clean();
        refreshUI();
        return this;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public InputDialog setBackgroundColor(int i) {
        this.backgroundColor = i;
        refreshUI();
        return this;
    }

    public InputDialog setBackgroundColorRes(int i) {
        this.backgroundColor = getColor(i);
        refreshUI();
        return this;
    }

    public InputDialog setMaskColor(int i) {
        this.maskColor = i;
        refreshUI();
        return this;
    }

    public long getEnterAnimDuration() {
        return this.enterAnimDuration;
    }

    public InputDialog setEnterAnimDuration(long j) {
        this.enterAnimDuration = j;
        return this;
    }

    public long getExitAnimDuration() {
        return this.exitAnimDuration;
    }

    public InputDialog setExitAnimDuration(long j) {
        this.exitAnimDuration = j;
        return this;
    }

    public void restartDialog() {
        if (this.dialogView != null) {
            dismiss(this.dialogView);
            this.isShow = false;
        }
        if (getDialogImpl().boxCustom != null) {
            getDialogImpl().boxCustom.removeAllViews();
        }
        int layout = this.style.layout(isLightTheme());
        if (layout == 0) {
            layout = isLightTheme() ? C1903R.layout.layout_dialogx_material : C1903R.layout.layout_dialogx_material_dark;
        }
        String inputText = getInputText();
        this.enterAnimDuration = 0;
        this.dialogView = createView(layout);
        this.dialogImpl = new MessageDialog.DialogImpl(this.dialogView);
        if (this.dialogView != null) {
            this.dialogView.setTag(this.f931me);
        }
        show(this.dialogView);
        setInputText(inputText);
    }

    public InputDialog setAnimResId(int i, int i2) {
        this.customEnterAnimResId = i;
        this.customExitAnimResId = i2;
        return this;
    }

    public InputDialog setEnterAnimResId(int i) {
        this.customEnterAnimResId = i;
        return this;
    }

    public InputDialog setExitAnimResId(int i) {
        this.customExitAnimResId = i;
        return this;
    }

    public InputDialog setMaxWidth(int i) {
        this.maxWidth = i;
        refreshUI();
        return this;
    }

    public InputDialog setDialogImplMode(DialogX.IMPL_MODE impl_mode) {
        this.dialogImplMode = impl_mode;
        return this;
    }

    public boolean isBkgInterceptTouch() {
        return this.bkgInterceptTouch;
    }

    public InputDialog setBkgInterceptTouch(boolean z) {
        this.bkgInterceptTouch = z;
        return this;
    }

    public OnBackgroundMaskClickListener<MessageDialog> getOnBackgroundMaskClickListener() {
        return this.onBackgroundMaskClickListener;
    }

    public InputDialog setOnBackgroundMaskClickListener(OnBackgroundMaskClickListener<MessageDialog> onBackgroundMaskClickListener) {
        this.onBackgroundMaskClickListener = onBackgroundMaskClickListener;
        return this;
    }

    public InputDialog setRadius(float f) {
        this.backgroundRadius = f;
        refreshUI();
        return this;
    }

    public InputDialog setTitleIcon(Bitmap bitmap) {
        this.titleIcon = new BitmapDrawable(getResources(), bitmap);
        refreshUI();
        return this;
    }

    public InputDialog setTitleIcon(int i) {
        this.titleIcon = getResources().getDrawable(i);
        refreshUI();
        return this;
    }

    public InputDialog setTitleIcon(Drawable drawable) {
        this.titleIcon = drawable;
        refreshUI();
        return this;
    }

    public DialogXAnimInterface<MessageDialog> getDialogXAnimImpl() {
        return this.dialogXAnimImpl;
    }

    public InputDialog setDialogXAnimImpl(DialogXAnimInterface<MessageDialog> dialogXAnimInterface) {
        this.dialogXAnimImpl = dialogXAnimInterface;
        return this;
    }
}
