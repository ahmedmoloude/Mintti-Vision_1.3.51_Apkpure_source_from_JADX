package com.kongzue.dialogx.dialogs;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.BaseOnDialogClickCallback;
import com.kongzue.dialogx.interfaces.DialogConvertViewInterface;
import com.kongzue.dialogx.interfaces.DialogLifecycleCallback;
import com.kongzue.dialogx.interfaces.DialogXAnimInterface;
import com.kongzue.dialogx.interfaces.DialogXStyle;
import com.kongzue.dialogx.interfaces.OnBackPressedListener;
import com.kongzue.dialogx.interfaces.OnBackgroundMaskClickListener;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialogx.style.MaterialStyle;
import com.kongzue.dialogx.util.InputInfo;
import com.kongzue.dialogx.util.ObjectRunnable;
import com.kongzue.dialogx.util.TextInfo;
import com.kongzue.dialogx.util.views.BlurView;
import com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout;
import com.kongzue.dialogx.util.views.MaxRelativeLayout;

public class MessageDialog extends BaseDialog {
    public static BaseDialog.BOOLEAN overrideCancelable = null;
    public static int overrideEnterAnimRes = 0;
    public static int overrideEnterDuration = -1;
    public static int overrideExitAnimRes = 0;
    public static int overrideExitDuration = -1;
    protected float backgroundRadius = -1.0f;
    protected boolean bkgInterceptTouch = true;
    protected int buttonOrientation;
    protected BaseOnDialogClickCallback cancelButtonClickListener;
    protected CharSequence cancelText;
    protected TextInfo cancelTextInfo;
    protected int customEnterAnimResId;
    protected int customExitAnimResId;
    protected DialogImpl dialogImpl;
    protected DialogLifecycleCallback<MessageDialog> dialogLifecycleCallback;
    protected View dialogView;
    protected DialogXAnimInterface<MessageDialog> dialogXAnimImpl;
    protected boolean hideWithExitAnim;
    protected String inputHintText;
    protected InputInfo inputInfo;
    protected String inputText;
    private boolean isHide;
    protected int maskColor = -1;

    /* renamed from: me */
    protected MessageDialog f931me = this;
    protected CharSequence message;
    protected TextInfo messageTextInfo;
    protected BaseOnDialogClickCallback okButtonClickListener;
    protected CharSequence okText;
    protected TextInfo okTextInfo;
    protected OnBackPressedListener<MessageDialog> onBackPressedListener;
    protected OnBackgroundMaskClickListener<MessageDialog> onBackgroundMaskClickListener;
    protected OnBindView<MessageDialog> onBindView;
    protected BaseOnDialogClickCallback otherButtonClickListener;
    protected CharSequence otherText;
    protected TextInfo otherTextInfo;
    protected BaseDialog.BOOLEAN privateCancelable;
    protected CharSequence title;
    protected Drawable titleIcon;
    protected TextInfo titleTextInfo;

    protected MessageDialog() {
    }

    public static MessageDialog build() {
        return new MessageDialog();
    }

    public static MessageDialog build(DialogXStyle dialogXStyle) {
        return new MessageDialog().setStyle(dialogXStyle);
    }

    public static MessageDialog build(OnBindView<MessageDialog> onBindView2) {
        return new MessageDialog().setCustomView(onBindView2);
    }

    public MessageDialog(CharSequence charSequence, CharSequence charSequence2) {
        this.title = charSequence;
        this.message = charSequence2;
    }

    public MessageDialog(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        this.title = charSequence;
        this.message = charSequence2;
        this.okText = charSequence3;
    }

    public MessageDialog(int i, int i2, int i3) {
        this.title = getString(i);
        this.message = getString(i2);
        this.okText = getString(i3);
    }

    public MessageDialog(int i, int i2) {
        this.title = getString(i);
        this.message = getString(i2);
    }

    public static MessageDialog show(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        MessageDialog messageDialog = new MessageDialog(charSequence, charSequence2, charSequence3);
        messageDialog.show();
        return messageDialog;
    }

    public static MessageDialog show(int i, int i2, int i3) {
        MessageDialog messageDialog = new MessageDialog(i, i2, i3);
        messageDialog.show();
        return messageDialog;
    }

    public static MessageDialog show(CharSequence charSequence, CharSequence charSequence2) {
        MessageDialog messageDialog = new MessageDialog(charSequence, charSequence2);
        messageDialog.show();
        return messageDialog;
    }

    public static MessageDialog show(int i, int i2) {
        MessageDialog messageDialog = new MessageDialog(i, i2);
        messageDialog.show();
        return messageDialog;
    }

    public MessageDialog(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {
        this.title = charSequence;
        this.message = charSequence2;
        this.okText = charSequence3;
        this.cancelText = charSequence4;
    }

    public MessageDialog(int i, int i2, int i3, int i4) {
        this.title = getString(i);
        this.message = getString(i2);
        this.okText = getString(i3);
        this.cancelText = getString(i4);
    }

    public static MessageDialog show(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {
        MessageDialog messageDialog = new MessageDialog(charSequence, charSequence2, charSequence3, charSequence4);
        messageDialog.show();
        return messageDialog;
    }

    public static MessageDialog show(int i, int i2, int i3, int i4) {
        MessageDialog messageDialog = new MessageDialog(i, i2, i3, i4);
        messageDialog.show();
        return messageDialog;
    }

    public MessageDialog(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {
        this.title = charSequence;
        this.message = charSequence2;
        this.okText = charSequence3;
        this.cancelText = charSequence4;
        this.otherText = charSequence5;
    }

    public MessageDialog(int i, int i2, int i3, int i4, int i5) {
        this.title = getString(i);
        this.message = getString(i2);
        this.okText = getString(i3);
        this.cancelText = getString(i4);
        this.otherText = getString(i5);
    }

    public static MessageDialog show(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {
        MessageDialog messageDialog = new MessageDialog(charSequence, charSequence2, charSequence3, charSequence4, charSequence5);
        messageDialog.show();
        return messageDialog;
    }

    public static MessageDialog show(int i, int i2, int i3, int i4, int i5) {
        MessageDialog messageDialog = new MessageDialog(i, i2, i3, i4, i5);
        messageDialog.show();
        return messageDialog;
    }

    public MessageDialog show() {
        if (!this.isHide || getDialogView() == null || !this.isShow) {
            super.beforeShow();
            if (getDialogView() == null) {
                int layout = this.style.layout(isLightTheme());
                if (layout == 0) {
                    layout = isLightTheme() ? C1903R.layout.layout_dialogx_material : C1903R.layout.layout_dialogx_material_dark;
                }
                View createView = createView(layout);
                this.dialogView = createView;
                this.dialogImpl = new DialogImpl(createView);
                View view = this.dialogView;
                if (view != null) {
                    view.setTag(this.f931me);
                }
            }
            show(this.dialogView);
            return this;
        }
        if (!this.hideWithExitAnim || getDialogImpl() == null) {
            getDialogView().setVisibility(0);
        } else {
            getDialogView().setVisibility(0);
            getDialogImpl().getDialogXAnimImpl().doShowAnim(this.f931me, new ObjectRunnable<Float>() {
                public void run(Float f) {
                    MessageDialog.this.getDialogImpl().boxRoot.setBkgAlpha(f.floatValue());
                }
            });
        }
        return this;
    }

    public void show(Activity activity) {
        super.beforeShow();
        if (getDialogView() == null) {
            int layout = this.style.layout(isLightTheme());
            if (layout == 0) {
                layout = isLightTheme() ? C1903R.layout.layout_dialogx_material : C1903R.layout.layout_dialogx_material_dark;
            }
            View createView = createView(layout);
            this.dialogView = createView;
            this.dialogImpl = new DialogImpl(createView);
            View view = this.dialogView;
            if (view != null) {
                view.setTag(this.f931me);
            }
        }
        show(activity, this.dialogView);
    }

    public void refreshUI() {
        if (getDialogImpl() != null) {
            runOnMain(new Runnable() {
                public void run() {
                    if (MessageDialog.this.dialogImpl != null) {
                        MessageDialog.this.dialogImpl.refreshView();
                    }
                }
            });
        }
    }

    public class DialogImpl implements DialogConvertViewInterface {
        public MaxRelativeLayout bkg;
        BlurView blurView;
        public LinearLayout boxButton;
        public RelativeLayout boxCustom;
        public DialogXBaseRelativeLayout boxRoot;
        public TextView btnSelectNegative;
        public TextView btnSelectOther;
        public TextView btnSelectPositive;
        public View spaceOtherButton;
        public View splitHorizontal;
        public TextView txtDialogTip;
        public TextView txtDialogTitle;
        public EditText txtInput;

        public DialogImpl(View view) {
            if (view != null) {
                this.boxRoot = (DialogXBaseRelativeLayout) view.findViewById(C1903R.C1907id.box_root);
                this.bkg = (MaxRelativeLayout) view.findViewById(C1903R.C1907id.bkg);
                this.txtDialogTitle = (TextView) view.findViewById(C1903R.C1907id.txt_dialog_title);
                this.txtDialogTip = (TextView) view.findViewById(C1903R.C1907id.txt_dialog_tip);
                this.boxCustom = (RelativeLayout) view.findViewById(C1903R.C1907id.box_custom);
                this.txtInput = (EditText) view.findViewById(C1903R.C1907id.txt_input);
                this.boxButton = (LinearLayout) view.findViewById(C1903R.C1907id.box_button);
                this.btnSelectOther = (TextView) view.findViewById(C1903R.C1907id.btn_selectOther);
                this.spaceOtherButton = view.findViewById(C1903R.C1907id.space_other_button);
                this.splitHorizontal = view.findViewWithTag("split");
                this.btnSelectNegative = (TextView) view.findViewById(C1903R.C1907id.btn_selectNegative);
                this.btnSelectPositive = (TextView) view.findViewById(C1903R.C1907id.btn_selectPositive);
                init();
                MessageDialog.this.dialogImpl = this;
                refreshView();
            }
        }

        public void init() {
            if (MessageDialog.this.titleTextInfo == null) {
                MessageDialog.this.titleTextInfo = DialogX.titleTextInfo;
            }
            if (MessageDialog.this.messageTextInfo == null) {
                MessageDialog.this.messageTextInfo = DialogX.messageTextInfo;
            }
            if (MessageDialog.this.okTextInfo == null) {
                MessageDialog.this.okTextInfo = DialogX.okButtonTextInfo;
            }
            if (MessageDialog.this.okTextInfo == null) {
                MessageDialog.this.okTextInfo = DialogX.buttonTextInfo;
            }
            if (MessageDialog.this.cancelTextInfo == null) {
                MessageDialog.this.cancelTextInfo = DialogX.buttonTextInfo;
            }
            if (MessageDialog.this.otherTextInfo == null) {
                MessageDialog.this.otherTextInfo = DialogX.buttonTextInfo;
            }
            if (MessageDialog.this.inputInfo == null) {
                MessageDialog.this.inputInfo = DialogX.inputInfo;
            }
            if (MessageDialog.this.backgroundColor == -1) {
                int unused = MessageDialog.this.backgroundColor = DialogX.backgroundColor;
            }
            this.txtDialogTitle.getPaint().setFakeBoldText(true);
            this.btnSelectNegative.getPaint().setFakeBoldText(true);
            this.btnSelectPositive.getPaint().setFakeBoldText(true);
            this.btnSelectOther.getPaint().setFakeBoldText(true);
            this.txtDialogTip.setMovementMethod(LinkMovementMethod.getInstance());
            this.boxRoot.setBkgAlpha(0.0f);
            this.boxRoot.setParentDialog(MessageDialog.this.f931me);
            this.boxRoot.setOnLifecycleCallBack(new DialogXBaseRelativeLayout.OnLifecycleCallBack() {
                public void onShow() {
                    boolean unused = MessageDialog.this.isShow = true;
                    boolean unused2 = MessageDialog.this.preShow = false;
                    MessageDialog.this.lifecycle.setCurrentState(Lifecycle.State.CREATED);
                    MessageDialog.this.onDialogShow();
                    MessageDialog.this.getDialogLifecycleCallback().onShow(MessageDialog.this.f931me);
                    DialogImpl.this.getDialogXAnimImpl().doShowAnim(MessageDialog.this.f931me, new ObjectRunnable<Float>() {
                        public void run(Float f) {
                            DialogImpl.this.boxRoot.setBkgAlpha(f.floatValue());
                        }
                    });
                    if (MessageDialog.this.style.messageDialogBlurSettings() != null && MessageDialog.this.style.messageDialogBlurSettings().blurBackground()) {
                        DialogImpl.this.bkg.post(new Runnable() {
                            public void run() {
                                int color = MessageDialog.this.getResources().getColor(MessageDialog.this.style.messageDialogBlurSettings().blurForwardColorRes(MessageDialog.this.isLightTheme()));
                                DialogImpl.this.blurView = new BlurView(DialogImpl.this.bkg.getContext(), (AttributeSet) null);
                                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DialogImpl.this.bkg.getWidth(), DialogImpl.this.bkg.getHeight());
                                layoutParams.addRule(13);
                                BlurView blurView = DialogImpl.this.blurView;
                                if (MessageDialog.this.backgroundColor != -1) {
                                    color = MessageDialog.this.backgroundColor;
                                }
                                blurView.setOverlayColor(color);
                                DialogImpl.this.blurView.setTag("blurView");
                                DialogImpl.this.blurView.setRadiusPx((float) MessageDialog.this.style.messageDialogBlurSettings().blurBackgroundRoundRadiusPx());
                                DialogImpl.this.bkg.addView(DialogImpl.this.blurView, 0, layoutParams);
                                MessageDialog.this.lifecycle.setCurrentState(Lifecycle.State.RESUMED);
                            }
                        });
                    }
                    if (MessageDialog.this.autoShowInputKeyboard) {
                        DialogImpl.this.txtInput.postDelayed(new Runnable() {
                            public void run() {
                                if (DialogImpl.this.txtInput != null) {
                                    DialogImpl.this.txtInput.requestFocus();
                                    DialogImpl.this.txtInput.setFocusableInTouchMode(true);
                                    MessageDialog.this.imeShow(DialogImpl.this.txtInput, true);
                                    DialogImpl.this.txtInput.setSelection(DialogImpl.this.txtInput.getText().length());
                                    if (MessageDialog.this.inputInfo != null && MessageDialog.this.inputInfo.isSelectAllText()) {
                                        DialogImpl.this.txtInput.selectAll();
                                    }
                                }
                            }
                        }, 300);
                    } else if (MessageDialog.this.inputInfo != null && MessageDialog.this.inputInfo.isSelectAllText()) {
                        DialogImpl.this.txtInput.clearFocus();
                        DialogImpl.this.txtInput.requestFocus();
                        DialogImpl.this.txtInput.selectAll();
                    }
                }

                public void onDismiss() {
                    boolean unused = MessageDialog.this.isShow = false;
                    MessageDialog.this.getDialogLifecycleCallback().onDismiss(MessageDialog.this.f931me);
                    MessageDialog.this.dialogView = null;
                    MessageDialog.this.dialogLifecycleCallback = null;
                    MessageDialog.this.lifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                    System.gc();
                }
            });
            this.boxRoot.setOnBackPressedListener(new DialogXBaseRelativeLayout.PrivateBackPressedListener() {
                public boolean onBackPressed() {
                    if (MessageDialog.this.onBackPressedListener != null) {
                        if (!MessageDialog.this.onBackPressedListener.onBackPressed(MessageDialog.this.f931me)) {
                            return true;
                        }
                        MessageDialog.this.dismiss();
                        return true;
                    } else if (!MessageDialog.this.isCancelable()) {
                        return true;
                    } else {
                        MessageDialog.this.dismiss();
                        return true;
                    }
                }
            });
            this.btnSelectPositive.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (DialogImpl.this.txtInput != null) {
                        MessageDialog.this.imeShow(DialogImpl.this.txtInput, false);
                    }
                    if (MessageDialog.this.okButtonClickListener == null) {
                        DialogImpl.this.doDismiss(view);
                    } else if (MessageDialog.this.okButtonClickListener instanceof OnInputDialogButtonClickListener) {
                        if (!((OnInputDialogButtonClickListener) MessageDialog.this.okButtonClickListener).onClick(MessageDialog.this.f931me, view, DialogImpl.this.txtInput == null ? "" : DialogImpl.this.txtInput.getText().toString())) {
                            DialogImpl.this.doDismiss(view);
                        }
                    } else if ((MessageDialog.this.okButtonClickListener instanceof OnDialogButtonClickListener) && !((OnDialogButtonClickListener) MessageDialog.this.okButtonClickListener).onClick(MessageDialog.this.f931me, view)) {
                        DialogImpl.this.doDismiss(view);
                    }
                }
            });
            this.btnSelectNegative.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (DialogImpl.this.txtInput != null) {
                        MessageDialog.this.imeShow(DialogImpl.this.txtInput, false);
                    }
                    if (MessageDialog.this.cancelButtonClickListener == null) {
                        DialogImpl.this.doDismiss(view);
                    } else if (MessageDialog.this.cancelButtonClickListener instanceof OnInputDialogButtonClickListener) {
                        if (!((OnInputDialogButtonClickListener) MessageDialog.this.cancelButtonClickListener).onClick(MessageDialog.this.f931me, view, DialogImpl.this.txtInput == null ? "" : DialogImpl.this.txtInput.getText().toString())) {
                            DialogImpl.this.doDismiss(view);
                        }
                    } else if (!((OnDialogButtonClickListener) MessageDialog.this.cancelButtonClickListener).onClick(MessageDialog.this.f931me, view)) {
                        DialogImpl.this.doDismiss(view);
                    }
                }
            });
            this.btnSelectOther.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (DialogImpl.this.txtInput != null) {
                        MessageDialog.this.imeShow(DialogImpl.this.txtInput, false);
                    }
                    if (MessageDialog.this.otherButtonClickListener == null) {
                        DialogImpl.this.doDismiss(view);
                    } else if (MessageDialog.this.otherButtonClickListener instanceof OnInputDialogButtonClickListener) {
                        if (!((OnInputDialogButtonClickListener) MessageDialog.this.otherButtonClickListener).onClick(MessageDialog.this.f931me, view, DialogImpl.this.txtInput == null ? "" : DialogImpl.this.txtInput.getText().toString())) {
                            DialogImpl.this.doDismiss(view);
                        }
                    } else if (!((OnDialogButtonClickListener) MessageDialog.this.otherButtonClickListener).onClick(MessageDialog.this.f931me, view)) {
                        DialogImpl.this.doDismiss(view);
                    }
                }
            });
            MessageDialog.this.onDialogInit();
        }

        public void refreshView() {
            MessageDialog.log("#refreshView");
            if (this.boxRoot != null && BaseDialog.getTopActivity() != null) {
                if (MessageDialog.this.backgroundColor != -1) {
                    MessageDialog messageDialog = MessageDialog.this;
                    messageDialog.tintColor(this.bkg, messageDialog.backgroundColor);
                    if (MessageDialog.this.style instanceof MaterialStyle) {
                        MessageDialog messageDialog2 = MessageDialog.this;
                        messageDialog2.tintColor(this.btnSelectOther, messageDialog2.backgroundColor);
                        MessageDialog messageDialog3 = MessageDialog.this;
                        messageDialog3.tintColor(this.btnSelectNegative, messageDialog3.backgroundColor);
                        MessageDialog messageDialog4 = MessageDialog.this;
                        messageDialog4.tintColor(this.btnSelectPositive, messageDialog4.backgroundColor);
                    }
                }
                this.bkg.setMaxWidth(MessageDialog.this.getMaxWidth());
                View findViewWithTag = this.boxRoot.findViewWithTag("dialogx_editbox");
                if (MessageDialog.this.f931me instanceof InputDialog) {
                    if (findViewWithTag != null) {
                        findViewWithTag.setVisibility(0);
                    }
                    this.txtInput.setVisibility(0);
                    this.boxRoot.bindFocusView(this.txtInput);
                } else {
                    if (findViewWithTag != null) {
                        findViewWithTag.setVisibility(8);
                    }
                    this.txtInput.setVisibility(8);
                }
                this.boxRoot.setClickable(true);
                if (MessageDialog.this.maskColor != -1) {
                    this.boxRoot.setBackgroundColor(MessageDialog.this.maskColor);
                }
                if (MessageDialog.this.backgroundRadius > -1.0f) {
                    GradientDrawable gradientDrawable = (GradientDrawable) this.bkg.getBackground();
                    if (gradientDrawable != null) {
                        gradientDrawable.setCornerRadius(MessageDialog.this.backgroundRadius);
                    }
                    if (Build.VERSION.SDK_INT >= 21) {
                        this.bkg.setOutlineProvider(new ViewOutlineProvider() {
                            public void getOutline(View view, Outline outline) {
                                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), MessageDialog.this.backgroundRadius);
                            }
                        });
                        this.bkg.setClipToOutline(true);
                    }
                }
                MessageDialog messageDialog5 = MessageDialog.this;
                messageDialog5.showText(this.txtDialogTitle, messageDialog5.title);
                MessageDialog messageDialog6 = MessageDialog.this;
                messageDialog6.showText(this.txtDialogTip, messageDialog6.message);
                MessageDialog messageDialog7 = MessageDialog.this;
                messageDialog7.showText(this.btnSelectPositive, messageDialog7.okText);
                MessageDialog messageDialog8 = MessageDialog.this;
                messageDialog8.showText(this.btnSelectNegative, messageDialog8.cancelText);
                MessageDialog messageDialog9 = MessageDialog.this;
                messageDialog9.showText(this.btnSelectOther, messageDialog9.otherText);
                this.txtInput.setText(MessageDialog.this.inputText);
                this.txtInput.setHint(MessageDialog.this.inputHintText);
                if (this.spaceOtherButton != null) {
                    if (MessageDialog.this.otherText == null) {
                        this.spaceOtherButton.setVisibility(8);
                    } else {
                        this.spaceOtherButton.setVisibility(0);
                    }
                }
                BaseDialog.useTextInfo(this.txtDialogTitle, MessageDialog.this.titleTextInfo);
                BaseDialog.useTextInfo(this.txtDialogTip, MessageDialog.this.messageTextInfo);
                BaseDialog.useTextInfo(this.btnSelectPositive, MessageDialog.this.okTextInfo);
                BaseDialog.useTextInfo(this.btnSelectNegative, MessageDialog.this.cancelTextInfo);
                BaseDialog.useTextInfo(this.btnSelectOther, MessageDialog.this.otherTextInfo);
                if (MessageDialog.this.titleIcon != null) {
                    int textSize = (int) this.txtDialogTitle.getTextSize();
                    MessageDialog.this.titleIcon.setBounds(0, 0, textSize, textSize);
                    this.txtDialogTitle.setCompoundDrawablePadding(MessageDialog.this.dip2px(10.0f));
                    this.txtDialogTitle.setCompoundDrawables(MessageDialog.this.titleIcon, (Drawable) null, (Drawable) null, (Drawable) null);
                }
                if (MessageDialog.this.inputInfo != null) {
                    if (MessageDialog.this.inputInfo.getMAX_LENGTH() != -1) {
                        this.txtInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MessageDialog.this.inputInfo.getMAX_LENGTH())});
                    }
                    int inputType = MessageDialog.this.inputInfo.getInputType() | 1;
                    if (MessageDialog.this.inputInfo.isMultipleLines()) {
                        inputType |= 131072;
                    }
                    this.txtInput.setInputType(inputType);
                    if (MessageDialog.this.inputInfo.getTextInfo() != null) {
                        BaseDialog.useTextInfo(this.txtInput, MessageDialog.this.inputInfo.getTextInfo());
                    }
                }
                int i = !BaseDialog.isNull(MessageDialog.this.okText);
                if (!BaseDialog.isNull(MessageDialog.this.cancelText)) {
                    i++;
                }
                if (!BaseDialog.isNull(MessageDialog.this.otherText)) {
                    i++;
                }
                View view = this.splitHorizontal;
                if (view != null) {
                    MessageDialog messageDialog10 = MessageDialog.this;
                    view.setBackgroundColor(messageDialog10.getColor(messageDialog10.style.splitColorRes(MessageDialog.this.isLightTheme())));
                }
                this.boxButton.setOrientation(MessageDialog.this.buttonOrientation);
                if (MessageDialog.this.buttonOrientation == 1) {
                    if (!(MessageDialog.this.style.verticalButtonOrder() == null || MessageDialog.this.style.verticalButtonOrder().length == 0)) {
                        this.boxButton.removeAllViews();
                        for (int i2 : MessageDialog.this.style.verticalButtonOrder()) {
                            if (i2 == 1) {
                                this.boxButton.addView(this.btnSelectPositive);
                                if (MessageDialog.this.style.overrideVerticalButtonRes() != null) {
                                    this.btnSelectPositive.setBackgroundResource(MessageDialog.this.style.overrideVerticalButtonRes().overrideVerticalOkButtonBackgroundRes(i, MessageDialog.this.isLightTheme()));
                                }
                            } else if (i2 == 2) {
                                this.boxButton.addView(this.btnSelectNegative);
                                if (MessageDialog.this.style.overrideVerticalButtonRes() != null) {
                                    this.btnSelectNegative.setBackgroundResource(MessageDialog.this.style.overrideVerticalButtonRes().overrideVerticalCancelButtonBackgroundRes(i, MessageDialog.this.isLightTheme()));
                                }
                            } else if (i2 == 3) {
                                this.boxButton.addView(this.btnSelectOther);
                                if (MessageDialog.this.style.overrideVerticalButtonRes() != null) {
                                    this.btnSelectOther.setBackgroundResource(MessageDialog.this.style.overrideVerticalButtonRes().overrideVerticalOtherButtonBackgroundRes(i, MessageDialog.this.isLightTheme()));
                                }
                            } else if (i2 == 4) {
                                Space space = new Space(BaseDialog.getTopActivity());
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                                layoutParams.weight = 1.0f;
                                this.boxButton.addView(space, layoutParams);
                            } else if (i2 == 5) {
                                View view2 = new View(BaseDialog.getTopActivity());
                                view2.setBackgroundColor(MessageDialog.this.getResources().getColor(MessageDialog.this.style.splitColorRes(MessageDialog.this.isLightTheme())));
                                this.boxButton.addView(view2, new LinearLayout.LayoutParams(-1, MessageDialog.this.style.splitWidthPx()));
                            }
                        }
                    }
                } else if (!(MessageDialog.this.style.horizontalButtonOrder() == null || MessageDialog.this.style.horizontalButtonOrder().length == 0)) {
                    this.boxButton.removeAllViews();
                    for (int i3 : MessageDialog.this.style.horizontalButtonOrder()) {
                        if (i3 == 1) {
                            this.boxButton.addView(this.btnSelectPositive);
                            if (MessageDialog.this.style.overrideHorizontalButtonRes() != null) {
                                this.btnSelectPositive.setBackgroundResource(MessageDialog.this.style.overrideHorizontalButtonRes().overrideHorizontalOkButtonBackgroundRes(i, MessageDialog.this.isLightTheme()));
                            }
                        } else if (i3 == 2) {
                            this.boxButton.addView(this.btnSelectNegative);
                            if (MessageDialog.this.style.overrideHorizontalButtonRes() != null) {
                                this.btnSelectNegative.setBackgroundResource(MessageDialog.this.style.overrideHorizontalButtonRes().overrideHorizontalCancelButtonBackgroundRes(i, MessageDialog.this.isLightTheme()));
                            }
                        } else if (i3 == 3) {
                            this.boxButton.addView(this.btnSelectOther);
                            if (MessageDialog.this.style.overrideHorizontalButtonRes() != null) {
                                this.btnSelectOther.setBackgroundResource(MessageDialog.this.style.overrideHorizontalButtonRes().overrideHorizontalOtherButtonBackgroundRes(i, MessageDialog.this.isLightTheme()));
                            }
                        } else if (i3 != 4) {
                            if (i3 == 5 && this.boxButton.getChildCount() >= 1) {
                                LinearLayout linearLayout = this.boxButton;
                                if (linearLayout.getChildAt(linearLayout.getChildCount() - 1).getVisibility() != 8) {
                                    View view3 = new View(BaseDialog.getTopActivity());
                                    view3.setBackgroundColor(MessageDialog.this.getResources().getColor(MessageDialog.this.style.splitColorRes(MessageDialog.this.isLightTheme())));
                                    this.boxButton.addView(view3, new LinearLayout.LayoutParams(MessageDialog.this.style.splitWidthPx(), -1));
                                }
                            }
                        } else if (this.boxButton.getChildCount() >= 1) {
                            LinearLayout linearLayout2 = this.boxButton;
                            if (linearLayout2.getChildAt(linearLayout2.getChildCount() - 1).getVisibility() != 8) {
                                Space space2 = new Space(BaseDialog.getTopActivity());
                                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
                                layoutParams2.weight = 1.0f;
                                this.boxButton.addView(space2, layoutParams2);
                            }
                        }
                    }
                }
                if (!MessageDialog.this.bkgInterceptTouch) {
                    this.boxRoot.setClickable(false);
                } else if (MessageDialog.this.isCancelable()) {
                    this.boxRoot.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            if (MessageDialog.this.onBackgroundMaskClickListener == null || !MessageDialog.this.onBackgroundMaskClickListener.onClick(MessageDialog.this.f931me, view)) {
                                DialogImpl.this.doDismiss(view);
                            }
                        }
                    });
                } else {
                    this.boxRoot.setOnClickListener((View.OnClickListener) null);
                }
                if (MessageDialog.this.onBindView == null || MessageDialog.this.onBindView.getCustomView() == null) {
                    this.boxCustom.setVisibility(8);
                } else {
                    MessageDialog.this.onBindView.bindParent(this.boxCustom, MessageDialog.this.f931me);
                    this.boxCustom.setVisibility(0);
                }
                MessageDialog.this.onDialogRefreshUI();
            }
        }

        public void doDismiss(View view) {
            if (view != null) {
                view.setEnabled(false);
            }
            if (BaseDialog.getTopActivity() != null && !MessageDialog.this.dismissAnimFlag) {
                boolean unused = MessageDialog.this.dismissAnimFlag = true;
                getDialogXAnimImpl().doExitAnim(MessageDialog.this.f931me, new ObjectRunnable<Float>() {
                    public void run(Float f) {
                        if (DialogImpl.this.boxRoot != null) {
                            DialogImpl.this.boxRoot.setBkgAlpha(f.floatValue());
                        }
                        if (f.floatValue() == 0.0f) {
                            if (DialogImpl.this.boxRoot != null) {
                                DialogImpl.this.boxRoot.setVisibility(8);
                            }
                            MessageDialog.dismiss(MessageDialog.this.dialogView);
                        }
                    }
                });
            }
        }

        /* access modifiers changed from: protected */
        public DialogXAnimInterface<MessageDialog> getDialogXAnimImpl() {
            if (MessageDialog.this.dialogXAnimImpl == null) {
                MessageDialog.this.dialogXAnimImpl = new DialogXAnimInterface<MessageDialog>() {
                    public void doShowAnim(MessageDialog messageDialog, final ObjectRunnable<Float> objectRunnable) {
                        int enterAnimResId = MessageDialog.this.style.enterAnimResId() == 0 ? C1903R.C1904anim.anim_dialogx_default_enter : MessageDialog.this.style.enterAnimResId();
                        if (MessageDialog.overrideEnterAnimRes != 0) {
                            enterAnimResId = MessageDialog.overrideEnterAnimRes;
                        }
                        if (MessageDialog.this.customEnterAnimResId != 0) {
                            enterAnimResId = MessageDialog.this.customEnterAnimResId;
                        }
                        Animation loadAnimation = AnimationUtils.loadAnimation(BaseDialog.getTopActivity(), enterAnimResId);
                        long duration = loadAnimation.getDuration();
                        if (MessageDialog.overrideEnterDuration >= 0) {
                            duration = (long) MessageDialog.overrideEnterDuration;
                        }
                        if (MessageDialog.this.enterAnimDuration >= 0) {
                            duration = MessageDialog.this.enterAnimDuration;
                        }
                        loadAnimation.setDuration(duration);
                        loadAnimation.setInterpolator(new DecelerateInterpolator());
                        DialogImpl.this.bkg.startAnimation(loadAnimation);
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                        ofFloat.setDuration(duration);
                        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                objectRunnable.run((Float) valueAnimator.getAnimatedValue());
                            }
                        });
                        ofFloat.start();
                    }

                    public void doExitAnim(MessageDialog messageDialog, final ObjectRunnable<Float> objectRunnable) {
                        int exitAnimResId = MessageDialog.this.style.exitAnimResId() == 0 ? C1903R.C1904anim.anim_dialogx_default_exit : MessageDialog.this.style.exitAnimResId();
                        if (MessageDialog.overrideExitAnimRes != 0) {
                            exitAnimResId = MessageDialog.overrideExitAnimRes;
                        }
                        if (MessageDialog.this.customExitAnimResId != 0) {
                            exitAnimResId = MessageDialog.this.customExitAnimResId;
                        }
                        Animation loadAnimation = AnimationUtils.loadAnimation(BaseDialog.getTopActivity(), exitAnimResId);
                        long duration = loadAnimation.getDuration();
                        loadAnimation.setInterpolator(new AccelerateInterpolator());
                        if (MessageDialog.overrideExitDuration >= 0) {
                            duration = (long) MessageDialog.overrideExitDuration;
                        }
                        if (MessageDialog.this.exitAnimDuration >= 0) {
                            duration = MessageDialog.this.exitAnimDuration;
                        }
                        loadAnimation.setDuration(duration);
                        DialogImpl.this.bkg.startAnimation(loadAnimation);
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
                        ofFloat.setDuration(duration);
                        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                objectRunnable.run((Float) valueAnimator.getAnimatedValue());
                            }
                        });
                        ofFloat.start();
                    }
                };
            }
            return MessageDialog.this.dialogXAnimImpl;
        }
    }

    public String dialogKey() {
        return getClass().getSimpleName() + "(" + Integer.toHexString(hashCode()) + ")";
    }

    public void dismiss() {
        runOnMain(new Runnable() {
            public void run() {
                if (MessageDialog.this.dialogImpl != null) {
                    MessageDialog.this.dialogImpl.doDismiss(MessageDialog.this.dialogImpl.bkg);
                }
            }
        });
    }

    public DialogLifecycleCallback<MessageDialog> getDialogLifecycleCallback() {
        DialogLifecycleCallback<MessageDialog> dialogLifecycleCallback2 = this.dialogLifecycleCallback;
        return dialogLifecycleCallback2 == null ? new DialogLifecycleCallback<MessageDialog>() {
        } : dialogLifecycleCallback2;
    }

    public MessageDialog setDialogLifecycleCallback(DialogLifecycleCallback<MessageDialog> dialogLifecycleCallback2) {
        this.dialogLifecycleCallback = dialogLifecycleCallback2;
        if (this.isShow) {
            dialogLifecycleCallback2.onShow(this.f931me);
        }
        return this;
    }

    public MessageDialog setStyle(DialogXStyle dialogXStyle) {
        this.style = dialogXStyle;
        return this;
    }

    public MessageDialog setTheme(DialogX.THEME theme) {
        this.theme = theme;
        return this;
    }

    public CharSequence getOkButton() {
        return this.okText;
    }

    public MessageDialog setOkButton(CharSequence charSequence) {
        this.okText = charSequence;
        refreshUI();
        return this;
    }

    public MessageDialog setOkButton(int i) {
        this.okText = getString(i);
        refreshUI();
        return this;
    }

    public MessageDialog setOkButton(OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.okButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public MessageDialog setOkButton(CharSequence charSequence, OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.okText = charSequence;
        this.okButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public MessageDialog setOkButton(int i, OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.okText = getString(i);
        this.okButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public CharSequence getCancelButton() {
        return this.cancelText;
    }

    public MessageDialog setCancelButton(CharSequence charSequence) {
        this.cancelText = charSequence;
        refreshUI();
        return this;
    }

    public MessageDialog setCancelButton(int i) {
        this.cancelText = getString(i);
        refreshUI();
        return this;
    }

    public MessageDialog setCancelButton(OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.cancelButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public MessageDialog setCancelButton(CharSequence charSequence, OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.cancelText = charSequence;
        this.cancelButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public MessageDialog setCancelButton(int i, OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.cancelText = getString(i);
        this.cancelButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public CharSequence getOtherButton() {
        return this.otherText;
    }

    public MessageDialog setOtherButton(CharSequence charSequence) {
        this.otherText = charSequence;
        refreshUI();
        return this;
    }

    public MessageDialog setOtherButton(int i) {
        this.otherText = getString(i);
        refreshUI();
        return this;
    }

    public MessageDialog setOtherButton(OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.otherButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public MessageDialog setOtherButton(CharSequence charSequence, OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.otherText = charSequence;
        this.otherButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public MessageDialog setOtherButton(int i, OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.otherText = getString(i);
        this.otherButtonClickListener = onDialogButtonClickListener;
        refreshUI();
        return this;
    }

    public OnDialogButtonClickListener<MessageDialog> getOkButtonClickListener() {
        return (OnDialogButtonClickListener) this.okButtonClickListener;
    }

    public MessageDialog setOkButtonClickListener(OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.okButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public OnDialogButtonClickListener<MessageDialog> getCancelButtonClickListener() {
        return (OnDialogButtonClickListener) this.cancelButtonClickListener;
    }

    public MessageDialog setCancelButtonClickListener(OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.cancelButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public OnDialogButtonClickListener<MessageDialog> getOtherButtonClickListener() {
        return (OnDialogButtonClickListener) this.otherButtonClickListener;
    }

    public MessageDialog setOtherButtonClickListener(OnDialogButtonClickListener<MessageDialog> onDialogButtonClickListener) {
        this.otherButtonClickListener = onDialogButtonClickListener;
        return this;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public MessageDialog setTitle(CharSequence charSequence) {
        this.title = charSequence;
        refreshUI();
        return this;
    }

    public MessageDialog setTitle(int i) {
        this.title = getString(i);
        refreshUI();
        return this;
    }

    public CharSequence getMessage() {
        return this.message;
    }

    public MessageDialog setMessage(CharSequence charSequence) {
        this.message = charSequence;
        refreshUI();
        return this;
    }

    public MessageDialog setMessage(int i) {
        this.message = getString(i);
        refreshUI();
        return this;
    }

    public TextInfo getTitleTextInfo() {
        return this.titleTextInfo;
    }

    public MessageDialog setTitleTextInfo(TextInfo textInfo) {
        this.titleTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getMessageTextInfo() {
        return this.messageTextInfo;
    }

    public MessageDialog setMessageTextInfo(TextInfo textInfo) {
        this.messageTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getOkTextInfo() {
        return this.okTextInfo;
    }

    public MessageDialog setOkTextInfo(TextInfo textInfo) {
        this.okTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getCancelTextInfo() {
        return this.cancelTextInfo;
    }

    public MessageDialog setCancelTextInfo(TextInfo textInfo) {
        this.cancelTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public TextInfo getOtherTextInfo() {
        return this.otherTextInfo;
    }

    public MessageDialog setOtherTextInfo(TextInfo textInfo) {
        this.otherTextInfo = textInfo;
        refreshUI();
        return this;
    }

    public int getButtonOrientation() {
        return this.buttonOrientation;
    }

    public MessageDialog setButtonOrientation(int i) {
        this.buttonOrientation = i;
        refreshUI();
        return this;
    }

    public boolean isCancelable() {
        BaseDialog.BOOLEAN booleanR = this.privateCancelable;
        if (booleanR == null) {
            BaseDialog.BOOLEAN booleanR2 = overrideCancelable;
            if (booleanR2 == null) {
                return this.cancelable;
            }
            if (booleanR2 == BaseDialog.BOOLEAN.TRUE) {
                return true;
            }
            return false;
        } else if (booleanR == BaseDialog.BOOLEAN.TRUE) {
            return true;
        } else {
            return false;
        }
    }

    public MessageDialog setCancelable(boolean z) {
        this.privateCancelable = z ? BaseDialog.BOOLEAN.TRUE : BaseDialog.BOOLEAN.FALSE;
        refreshUI();
        return this;
    }

    public OnBackPressedListener<MessageDialog> getOnBackPressedListener() {
        return this.onBackPressedListener;
    }

    public MessageDialog setOnBackPressedListener(OnBackPressedListener<MessageDialog> onBackPressedListener2) {
        this.onBackPressedListener = onBackPressedListener2;
        return this;
    }

    public DialogImpl getDialogImpl() {
        return this.dialogImpl;
    }

    public MessageDialog setCustomView(OnBindView<MessageDialog> onBindView2) {
        this.onBindView = onBindView2;
        refreshUI();
        return this;
    }

    public View getCustomView() {
        OnBindView<MessageDialog> onBindView2 = this.onBindView;
        if (onBindView2 == null) {
            return null;
        }
        return onBindView2.getCustomView();
    }

    public MessageDialog removeCustomView() {
        this.onBindView.clean();
        refreshUI();
        return this;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public MessageDialog setBackgroundColor(int i) {
        this.backgroundColor = i;
        refreshUI();
        return this;
    }

    public String getInputText() {
        return this.dialogImpl.txtInput != null ? this.dialogImpl.txtInput.getText().toString() : "";
    }

    public MessageDialog setBackgroundColorRes(int i) {
        this.backgroundColor = getColor(i);
        refreshUI();
        return this;
    }

    public MessageDialog setMaskColor(int i) {
        this.maskColor = i;
        refreshUI();
        return this;
    }

    public long getEnterAnimDuration() {
        return this.enterAnimDuration;
    }

    public MessageDialog setEnterAnimDuration(long j) {
        this.enterAnimDuration = j;
        return this;
    }

    public long getExitAnimDuration() {
        return this.exitAnimDuration;
    }

    public MessageDialog setExitAnimDuration(long j) {
        this.exitAnimDuration = j;
        return this;
    }

    public void restartDialog() {
        View view = this.dialogView;
        if (view != null) {
            dismiss(view);
            this.isShow = false;
        }
        if (getDialogImpl().boxCustom != null) {
            getDialogImpl().boxCustom.removeAllViews();
        }
        int layout = this.style.layout(isLightTheme());
        if (layout == 0) {
            layout = isLightTheme() ? C1903R.layout.layout_dialogx_material : C1903R.layout.layout_dialogx_material_dark;
        }
        this.enterAnimDuration = 0;
        View createView = createView(layout);
        this.dialogView = createView;
        this.dialogImpl = new DialogImpl(createView);
        View view2 = this.dialogView;
        if (view2 != null) {
            view2.setTag(this.f931me);
        }
        show(this.dialogView);
    }

    public void hide() {
        this.isHide = true;
        this.hideWithExitAnim = false;
        if (getDialogView() != null) {
            getDialogView().setVisibility(8);
        }
    }

    public void hideWithExitAnim() {
        this.hideWithExitAnim = true;
        this.isHide = true;
        if (getDialogImpl() != null) {
            getDialogImpl().getDialogXAnimImpl().doExitAnim(this.f931me, new ObjectRunnable<Float>() {
                public void run(Float f) {
                    MessageDialog.this.getDialogImpl().boxRoot.setBkgAlpha(f.floatValue());
                    if (f.floatValue() == 0.0f && MessageDialog.this.getDialogView() != null) {
                        MessageDialog.this.getDialogView().setVisibility(8);
                    }
                }
            });
        }
    }

    public MessageDialog setAnimResId(int i, int i2) {
        this.customEnterAnimResId = i;
        this.customExitAnimResId = i2;
        return this;
    }

    public MessageDialog setEnterAnimResId(int i) {
        this.customEnterAnimResId = i;
        return this;
    }

    public MessageDialog setExitAnimResId(int i) {
        this.customExitAnimResId = i;
        return this;
    }

    /* access modifiers changed from: protected */
    public void shutdown() {
        dismiss();
    }

    public MessageDialog setMaxWidth(int i) {
        this.maxWidth = i;
        refreshUI();
        return this;
    }

    public MessageDialog setDialogImplMode(DialogX.IMPL_MODE impl_mode) {
        this.dialogImplMode = impl_mode;
        return this;
    }

    public boolean isBkgInterceptTouch() {
        return this.bkgInterceptTouch;
    }

    public MessageDialog setBkgInterceptTouch(boolean z) {
        this.bkgInterceptTouch = z;
        return this;
    }

    public OnBackgroundMaskClickListener<MessageDialog> getOnBackgroundMaskClickListener() {
        return this.onBackgroundMaskClickListener;
    }

    public MessageDialog setOnBackgroundMaskClickListener(OnBackgroundMaskClickListener<MessageDialog> onBackgroundMaskClickListener2) {
        this.onBackgroundMaskClickListener = onBackgroundMaskClickListener2;
        return this;
    }

    public MessageDialog setRadius(float f) {
        this.backgroundRadius = f;
        refreshUI();
        return this;
    }

    public float getRadius() {
        return this.backgroundRadius;
    }

    public Drawable getTitleIcon() {
        return this.titleIcon;
    }

    public MessageDialog setTitleIcon(Bitmap bitmap) {
        this.titleIcon = new BitmapDrawable(getResources(), bitmap);
        refreshUI();
        return this;
    }

    public MessageDialog setTitleIcon(int i) {
        this.titleIcon = getResources().getDrawable(i);
        refreshUI();
        return this;
    }

    public MessageDialog setTitleIcon(Drawable drawable) {
        this.titleIcon = drawable;
        refreshUI();
        return this;
    }

    public DialogXAnimInterface<MessageDialog> getDialogXAnimImpl() {
        return this.dialogXAnimImpl;
    }

    public MessageDialog setDialogXAnimImpl(DialogXAnimInterface<MessageDialog> dialogXAnimInterface) {
        this.dialogXAnimImpl = dialogXAnimInterface;
        return this;
    }
}
