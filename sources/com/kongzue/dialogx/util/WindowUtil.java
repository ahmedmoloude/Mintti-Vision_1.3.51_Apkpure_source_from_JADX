package com.kongzue.dialogx.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.NoTouchInterface;

public class WindowUtil {
    public static void show(final Activity activity, final View view, final boolean z) {
        try {
            if (activity.getWindow().getDecorView().isAttachedToWindow()) {
                showNow(activity, view, z);
            } else {
                activity.getWindow().getDecorView().post(new Runnable() {
                    public void run() {
                        WindowUtil.showNow(activity, view, z);
                    }
                });
            }
        } catch (Exception unused) {
            if (activity != null && !activity.isDestroyed()) {
                showNow(activity, view, z);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void showNow(final Activity activity, View view, boolean z) {
        if (!DialogX.globalHoverWindow || Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(activity)) {
            FrameLayout frameLayout = new FrameLayout(activity);
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            frameLayout.addView(view, new FrameLayout.LayoutParams(-1, -1));
            WindowManager windowManager = (WindowManager) activity.getSystemService("window");
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.gravity = 16;
            layoutParams.format = -2;
            if (!DialogX.globalHoverWindow) {
                layoutParams.type = 1003;
            } else if (Build.VERSION.SDK_INT >= 26) {
                layoutParams.type = 2038;
            } else {
                layoutParams.type = 2002;
            }
            layoutParams.flags = 201327872;
            if (!z) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        int size = BaseDialog.getRunningDialogList().size() - 1;
                        while (size >= 0) {
                            BaseDialog baseDialog = BaseDialog.getRunningDialogList().get(size);
                            if ((baseDialog instanceof NoTouchInterface) || baseDialog.getOwnActivity() != activity) {
                                size--;
                            } else if (baseDialog.getDialogView() == null) {
                                return false;
                            } else {
                                return baseDialog.getOwnActivity().dispatchTouchEvent(motionEvent);
                            }
                        }
                        return activity.dispatchTouchEvent(motionEvent);
                    }
                });
            }
            if (Build.VERSION.SDK_INT >= 28) {
                layoutParams.layoutInDisplayCutoutMode = 1;
            }
            windowManager.addView(frameLayout, layoutParams);
            return;
        }
        Toast.makeText(activity, "使用 DialogX.globalHoverWindow 必须开启悬浮窗权限", 1).show();
        Intent intent = new Intent();
        intent.setAction("android.settings.action.MANAGE_OVERLAY_PERMISSION");
        activity.startActivity(intent);
    }

    public static void dismiss(View view) {
        BaseDialog baseDialog = (BaseDialog) view.getTag();
        if (baseDialog != null && baseDialog.getOwnActivity() != null) {
            ((WindowManager) baseDialog.getOwnActivity().getSystemService("window")).removeViewImmediate((View) view.getParent());
        }
    }
}
