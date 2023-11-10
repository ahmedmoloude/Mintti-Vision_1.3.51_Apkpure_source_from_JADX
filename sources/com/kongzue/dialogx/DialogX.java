package com.kongzue.dialogx;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.DialogLifecycleCallback;
import com.kongzue.dialogx.interfaces.DialogXStyle;
import com.kongzue.dialogx.style.MaterialStyle;
import com.kongzue.dialogx.util.DialogListBuilder;
import com.kongzue.dialogx.util.InputInfo;
import com.kongzue.dialogx.util.TextInfo;

public class DialogX {
    public static boolean DEBUGMODE = true;
    public static final String ERROR_INIT_TIPS = "DialogX.init: 初始化异常，context 为 null 或未初始化，详情请查看 https://github.com/kongzue/DialogX/wiki";
    public static boolean autoRunOnUIThread = true;
    public static boolean autoShowInputKeyboard = true;
    public static int backgroundColor = -1;
    public static int bottomDialogNavbarColor = 0;
    public static TextInfo buttonTextInfo;
    public static String cancelButtonText;
    public static boolean cancelable = true;
    public static boolean cancelableTipDialog = false;
    public static DialogLifecycleCallback<BaseDialog> dialogLifeCycleListener;
    public static int dialogMaxWidth;
    public static long enterAnimDuration = -1;
    public static long exitAnimDuration = -1;
    public static boolean globalHoverWindow = false;
    public static DialogXStyle globalStyle = MaterialStyle.style();
    public static THEME globalTheme = THEME.LIGHT;
    public static IMPL_MODE implIMPLMode = IMPL_MODE.VIEW;
    public static InputInfo inputInfo;
    public static TextInfo menuTextInfo;
    public static TextInfo menuTitleInfo;
    public static TextInfo messageTextInfo;
    public static TextInfo okButtonTextInfo;
    public static boolean onlyOnePopNotification = true;
    public static boolean onlyOnePopTip = false;
    public static TextInfo popTextInfo;
    public static int tipBackgroundColor = -1;
    public static int tipProgressColor = -1;
    public static TextInfo tipTextInfo;
    public static THEME tipTheme;
    public static TextInfo titleTextInfo;
    public static int touchSlideTriggerThreshold = dip2px(35.0f);
    @Deprecated
    public static boolean useActivityLayoutTranslationNavigationBar = false;
    public static boolean useHaptic = true;

    public enum IMPL_MODE {
        VIEW,
        WINDOW,
        DIALOG_FRAGMENT,
        FLOATING_ACTIVITY
    }

    public enum THEME {
        LIGHT,
        DARK,
        AUTO
    }

    public static void init(Context context) {
        if (context == null) {
            error(ERROR_INIT_TIPS);
        } else {
            BaseDialog.init(context);
        }
    }

    public static void error(Object obj) {
        if (DEBUGMODE) {
            Log.e(">>>", obj.toString());
        }
    }

    private static int dip2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static DialogListBuilder showDialogList(BaseDialog... baseDialogArr) {
        return DialogListBuilder.create(baseDialogArr).show();
    }
}
