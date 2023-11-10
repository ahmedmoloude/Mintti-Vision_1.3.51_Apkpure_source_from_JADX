package com.p020kl.commonbase.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.base.BaseApplication;

/* renamed from: com.kl.commonbase.utils.ToastUtil */
public class ToastUtil {
    private static final long SHOW_TOAST_INTERVAL = 1000;
    /* access modifiers changed from: private */
    public static long lastShowToastTime;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public static Toast mToast;
    private static Toast toast;

    public static void showToast(Context context, int i) {
        showToast(context, i, 0);
    }

    public static void showToast(Context context, int i, int i2) {
        if (context != null && i != 0) {
            showToast(context, context.getString(i), i2, false);
        }
    }

    public static void showToast(Context context, String str) {
        showToast(context, str, 0, false);
    }

    public static void showToastUserLayout(Context context, String str) {
        showToast(context, str, 0, true);
    }

    public static void showToast(final Context context, final String str, final int i, final boolean z) {
        if (context != null && !TextUtils.isEmpty(str)) {
            mHandler.post(new Runnable() {
                public void run() {
                    if (System.currentTimeMillis() - ToastUtil.lastShowToastTime > ToastUtil.SHOW_TOAST_INTERVAL) {
                        if (ToastUtil.mToast != null) {
                            ToastUtil.mToast.cancel();
                        }
                        if (z) {
                            View inflate = LayoutInflater.from(context).inflate(C1544R.layout.layout_toast, (ViewGroup) null);
                            ((TextView) inflate.findViewById(C1544R.C1548id.tv_title)).setText(str);
                            Toast unused = ToastUtil.mToast = new Toast(context);
                            ToastUtil.mToast.setView(inflate);
                            ToastUtil.mToast.setDuration(1);
                        } else {
                            Toast unused2 = ToastUtil.mToast = Toast.makeText(context, str, i);
                            ToastUtil.mToast.setText(str);
                        }
                        ToastUtil.mToast.show();
                        long unused3 = ToastUtil.lastShowToastTime = System.currentTimeMillis();
                    }
                }
            });
        }
    }

    public static void cancelToast() {
        Toast toast2 = mToast;
        if (toast2 != null) {
            toast2.cancel();
        }
    }

    public static void showShortToast(String str) {
        Toast toast2 = toast;
        if (toast2 == null) {
            toast = Toast.makeText(BaseApplication.getInstance(), str, 0);
        } else {
            toast2.cancel();
            Toast makeText = Toast.makeText(BaseApplication.getInstance(), str, 0);
            toast = makeText;
            makeText.setDuration(0);
        }
        toast.show();
    }

    public static void showLongToast(String str) {
        Toast toast2 = toast;
        if (toast2 == null) {
            toast = Toast.makeText(BaseApplication.getInstance(), str, 1);
        } else {
            toast2.cancel();
            Toast makeText = Toast.makeText(BaseApplication.getInstance(), str, 0);
            toast = makeText;
            makeText.setDuration(1);
        }
        toast.show();
    }
}
