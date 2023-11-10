package com.kongzue.dialogx.impl;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.NoTouchInterface;
import java.lang.ref.WeakReference;

public class DialogFragmentImpl extends DialogFragment {
    WeakReference<Activity> activityWeakReference = null;
    /* access modifiers changed from: private */
    public BaseDialog baseDialog;
    private View dialogView;

    public DialogFragmentImpl(BaseDialog baseDialog2, View view) {
        this.dialogView = view;
        this.baseDialog = baseDialog2;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.dialogView;
    }

    public void onStart() {
        Window window;
        super.onStart();
        if (BaseDialog.getTopActivity() != null && (BaseDialog.getTopActivity() instanceof Activity)) {
            this.activityWeakReference = new WeakReference<>(BaseDialog.getTopActivity());
        }
        WeakReference<Activity> weakReference = this.activityWeakReference;
        if (weakReference != null && weakReference.get() != null) {
            final Activity activity = (Activity) this.activityWeakReference.get();
            if (getDialog() != null && (window = getDialog().getWindow()) != null) {
                window.clearFlags(8);
                window.setSoftInputMode(16);
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.width = -1;
                attributes.height = -1;
                attributes.dimAmount = 0.0f;
                attributes.format = -2;
                this.dialogView.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        for (BaseDialog next : BaseDialog.getRunningDialogList()) {
                            if (next.getOwnActivity() == activity && next != DialogFragmentImpl.this.baseDialog && !(next instanceof NoTouchInterface)) {
                                Log.e(">>>", "onTouch: " + next);
                                next.getDialogView().dispatchTouchEvent(motionEvent);
                                return true;
                            }
                        }
                        return activity.dispatchTouchEvent(motionEvent);
                    }
                });
                window.setAttributes(attributes);
                window.addFlags(PagedChannelRandomAccessSource.DEFAULT_TOTAL_BUFSIZE);
                window.getDecorView().setPadding(0, 0, 0, 0);
                if (Build.VERSION.SDK_INT >= 28) {
                    attributes.layoutInDisplayCutoutMode = 1;
                }
                window.setBackgroundDrawable(new ColorDrawable(0));
                if (Build.VERSION.SDK_INT >= 21) {
                    int i = 1280;
                    if (activity != null && (activity.getWindow().getDecorView().getSystemUiVisibility() & 8192) == 8192) {
                        i = 9472;
                    }
                    window.getDecorView().setSystemUiVisibility(i);
                    window.addFlags(-2013265920);
                    window.setStatusBarColor(0);
                    window.setNavigationBarColor(0);
                    return;
                }
                window.addFlags(PagedChannelRandomAccessSource.DEFAULT_TOTAL_BUFSIZE);
            }
        }
    }

    public void show(FragmentManager fragmentManager, String str) {
        if (fragmentManager == null) {
            DialogX.error("DialogX.DialogFragment 模式无法支持非 AppCompatActivity 启动。");
            return;
        }
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add((Fragment) this, str);
        beginTransaction.commitAllowingStateLoss();
    }

    public void dismiss() {
        dismissAllowingStateLoss();
    }
}
