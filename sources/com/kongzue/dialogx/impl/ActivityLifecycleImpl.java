package com.kongzue.dialogx.impl;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.util.DialogXFloatingWindowActivity;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {
    private static ActivityLifecycleImpl activityLifecycle;
    private static Application application;
    private onActivityResumeCallBack onActivityResumeCallBack;

    public interface onActivityResumeCallBack {
        void getActivity(Activity activity);
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public ActivityLifecycleImpl(onActivityResumeCallBack onactivityresumecallback) {
        this.onActivityResumeCallBack = onactivityresumecallback;
    }

    public static void init(Context context, onActivityResumeCallBack onactivityresumecallback) {
        if (context != null) {
            Application applicationContext = getApplicationContext(context);
            if (applicationContext == null) {
                DialogX.error("DialogX 未初始化。\n请检查是否在启动对话框前进行初始化操作，使用以下代码进行初始化：\nDialogX.init(context);\n\n另外建议您前往查看 DialogX 的文档进行使用：https://github.com/kongzue/DialogX");
                return;
            }
            application = applicationContext;
            ActivityLifecycleImpl activityLifecycleImpl = activityLifecycle;
            if (activityLifecycleImpl != null) {
                applicationContext.unregisterActivityLifecycleCallbacks(activityLifecycleImpl);
            }
            ActivityLifecycleImpl activityLifecycleImpl2 = new ActivityLifecycleImpl(onactivityresumecallback);
            activityLifecycle = activityLifecycleImpl2;
            applicationContext.registerActivityLifecycleCallbacks(activityLifecycleImpl2);
            return;
        }
        Application application2 = application;
        if (application2 != null) {
            init(application2, onactivityresumecallback);
        }
    }

    public static Application getApplicationContext(Context context) {
        if (context != null) {
            return (Application) context.getApplicationContext();
        }
        try {
            return (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke((Object) null, (Object[]) null);
        } catch (Exception unused) {
            try {
                return (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication", new Class[0]).invoke((Object) null, (Object[]) null);
            } catch (Exception unused2) {
                DialogX.error("DialogX.init: 初始化异常，请确保init方法内传入的Context是有效的。");
                return null;
            }
        }
    }

    public static Application getApplicationContext() {
        Application application2 = application;
        if (application2 != null) {
            return application2;
        }
        try {
            return (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke((Object) null, (Object[]) null);
        } catch (Exception unused) {
            try {
                return (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication", new Class[0]).invoke((Object) null, (Object[]) null);
            } catch (Exception unused2) {
                return null;
            }
        }
    }

    public static Activity getTopActivity() {
        Map map;
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke((Object) null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            if (Build.VERSION.SDK_INT < 19) {
                map = (HashMap) declaredField.get(invoke);
            } else {
                map = (ArrayMap) declaredField.get(invoke);
            }
            if (map.size() < 1) {
                return null;
            }
            for (Object next : map.values()) {
                Class<?> cls2 = next.getClass();
                Field declaredField2 = cls2.getDeclaredField("paused");
                declaredField2.setAccessible(true);
                if (!declaredField2.getBoolean(next)) {
                    Field declaredField3 = cls2.getDeclaredField("activity");
                    declaredField3.setAccessible(true);
                    return (Activity) declaredField3.get(next);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        onActivityResumeCallBack onactivityresumecallback = this.onActivityResumeCallBack;
        if (onactivityresumecallback != null && !(activity instanceof DialogXFloatingWindowActivity)) {
            onactivityresumecallback.getActivity(activity);
        }
    }

    public void onActivityStarted(Activity activity) {
        if (application == null) {
            BaseDialog.init(activity);
        }
    }

    public void onActivityResumed(Activity activity) {
        if (!activity.isDestroyed() && !activity.isFinishing() && !(activity instanceof DialogXFloatingWindowActivity)) {
            onActivityResumeCallBack onactivityresumecallback = this.onActivityResumeCallBack;
            if (onactivityresumecallback != null) {
                onactivityresumecallback.getActivity(activity);
            }
            BaseDialog.onActivityResume(activity);
        }
    }

    public void onActivityDestroyed(Activity activity) {
        if (BaseDialog.getTopActivity() == activity) {
            BaseDialog.cleanContext();
        }
    }

    public void onActivityPreDestroyed(Activity activity) {
        BaseDialog.recycleDialog(activity);
    }
}
