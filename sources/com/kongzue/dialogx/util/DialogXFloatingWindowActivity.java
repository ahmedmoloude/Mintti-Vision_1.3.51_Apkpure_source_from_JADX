package com.kongzue.dialogx.util;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.interfaces.BaseDialog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DialogXFloatingWindowActivity extends AppCompatActivity {
    static WeakReference<DialogXFloatingWindowActivity> dialogXFloatingWindowActivity;
    int fromActivityHashCode;
    boolean isScreenshot;
    List<String> shownDialogXList = new ArrayList();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        dialogXFloatingWindowActivity = new WeakReference<>(this);
        super.onCreate(bundle);
        setContentView(C1903R.layout.layout_dialogx_empty);
        int intExtra = getIntent().getIntExtra("fromActivityUiStatus", 0);
        if (intExtra == 0) {
            getWindow().getDecorView().setSystemUiVisibility(512);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(intExtra | 512);
        }
        setFromActivityHashCode(getIntent().getIntExtra(TypedValues.TransitionType.S_FROM, 0));
        String stringExtra = getIntent().getStringExtra("dialogXKey");
        ActivityRunnable activityRunnable = BaseDialog.getActivityRunnable(stringExtra);
        if (activityRunnable == null) {
            finish();
        } else {
            this.shownDialogXList.add(stringExtra);
            activityRunnable.run(this);
        }
        getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 3 || BaseDialog.getTopActivity() == null || (BaseDialog.getTopActivity() instanceof DialogXFloatingWindowActivity)) {
                    return false;
                }
                return BaseDialog.getTopActivity().dispatchTouchEvent(motionEvent);
            }
        });
    }

    public boolean isSameFrom(int i) {
        return i == this.fromActivityHashCode;
    }

    public void showDialogX(String str) {
        ActivityRunnable activityRunnable = BaseDialog.getActivityRunnable(str);
        if (activityRunnable != null) {
            this.shownDialogXList.add(str);
            activityRunnable.run(this);
        }
    }

    public int getFromActivityHashCode() {
        return this.fromActivityHashCode;
    }

    public DialogXFloatingWindowActivity setFromActivityHashCode(int i) {
        this.fromActivityHashCode = i;
        return this;
    }

    public static DialogXFloatingWindowActivity getDialogXFloatingWindowActivity() {
        WeakReference<DialogXFloatingWindowActivity> weakReference = dialogXFloatingWindowActivity;
        if (weakReference == null) {
            return null;
        }
        return (DialogXFloatingWindowActivity) weakReference.get();
    }

    public void finish(String str) {
        this.shownDialogXList.remove(str);
        if (this.shownDialogXList.isEmpty()) {
            WeakReference<DialogXFloatingWindowActivity> weakReference = dialogXFloatingWindowActivity;
            if (weakReference != null) {
                weakReference.clear();
            }
            dialogXFloatingWindowActivity = null;
            super.finish();
            if (Integer.valueOf(Build.VERSION.SDK_INT).intValue() > 5) {
                overridePendingTransition(0, 0);
            }
        }
    }

    public void finish() {
        WeakReference<DialogXFloatingWindowActivity> weakReference = dialogXFloatingWindowActivity;
        if (weakReference != null) {
            weakReference.clear();
        }
        dialogXFloatingWindowActivity = null;
        super.finish();
        if (Integer.valueOf(Build.VERSION.SDK_INT).intValue() > 5) {
            overridePendingTransition(0, 0);
        }
    }

    public boolean isScreenshot() {
        return this.isScreenshot;
    }

    public DialogXFloatingWindowActivity setScreenshot(boolean z) {
        this.isScreenshot = z;
        return this;
    }
}
