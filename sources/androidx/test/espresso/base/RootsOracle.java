package androidx.test.espresso.base;

import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.test.espresso.Root;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

final class RootsOracle implements ActiveRootLister {
    private static final String GET_DEFAULT_IMPL = "getDefault";
    private static final String GET_GLOBAL_INSTANCE = "getInstance";
    private static final String TAG = "RootsOracle";
    private static final String VIEWS_FIELD = "mViews";
    private static final String WINDOW_MANAGER_GLOBAL_CLAZZ = "android.view.WindowManagerGlobal";
    private static final String WINDOW_MANAGER_IMPL_CLAZZ = "android.view.WindowManagerImpl";
    private static final String WINDOW_PARAMS_FIELD = "mParams";
    private boolean initialized;
    private final Looper mainLooper;
    private Field paramsField;
    private Field viewsField;
    private Object windowManagerObj;

    RootsOracle(Looper looper) {
        this.mainLooper = looper;
    }

    private void initialize() {
        this.initialized = true;
        String str = Build.VERSION.SDK_INT > 16 ? WINDOW_MANAGER_GLOBAL_CLAZZ : WINDOW_MANAGER_IMPL_CLAZZ;
        String str2 = Build.VERSION.SDK_INT > 16 ? GET_GLOBAL_INSTANCE : GET_DEFAULT_IMPL;
        try {
            Class<?> cls = Class.forName(str);
            this.windowManagerObj = cls.getMethod(str2, new Class[0]).invoke((Object) null, new Object[0]);
            Field declaredField = cls.getDeclaredField(VIEWS_FIELD);
            this.viewsField = declaredField;
            declaredField.setAccessible(true);
            Field declaredField2 = cls.getDeclaredField(WINDOW_PARAMS_FIELD);
            this.paramsField = declaredField2;
            declaredField2.setAccessible(true);
        } catch (InvocationTargetException e) {
            Log.e(TAG, String.format(Locale.ROOT, "could not invoke: %s on %s", new Object[]{str2, str}), e.getCause());
        } catch (ClassNotFoundException e2) {
            Log.e(TAG, String.format(Locale.ROOT, "could not find class: %s", new Object[]{str}), e2);
        } catch (NoSuchFieldException e3) {
            Log.e(TAG, String.format(Locale.ROOT, "could not find field: %s or %s on %s", new Object[]{WINDOW_PARAMS_FIELD, VIEWS_FIELD, str}), e3);
        } catch (NoSuchMethodException e4) {
            Log.e(TAG, String.format(Locale.ROOT, "could not find method: %s on %s", new Object[]{str2, str}), e4);
        } catch (RuntimeException e5) {
            Log.e(TAG, String.format(Locale.ROOT, "reflective setup failed using obj: %s method: %s field: %s", new Object[]{str, str2, VIEWS_FIELD}), e5);
        } catch (IllegalAccessException e6) {
            Log.e(TAG, String.format(Locale.ROOT, "reflective setup failed using obj: %s method: %s field: %s", new Object[]{str, str2, VIEWS_FIELD}), e6);
        }
    }

    public List<Root> listActiveRoots() {
        List list;
        List list2;
        Preconditions.checkState(this.mainLooper.equals(Looper.myLooper()), "must be called on main thread.");
        if (!this.initialized) {
            initialize();
        }
        if (this.windowManagerObj == null) {
            Log.w(TAG, "No reflective access to windowmanager object.");
            return Lists.newArrayList();
        } else if (this.viewsField == null) {
            Log.w(TAG, "No reflective access to mViews");
            return Lists.newArrayList();
        } else if (this.paramsField != null) {
            try {
                if (Build.VERSION.SDK_INT < 19) {
                    list = Arrays.asList((View[]) this.viewsField.get(this.windowManagerObj));
                    list2 = Arrays.asList((WindowManager.LayoutParams[]) this.paramsField.get(this.windowManagerObj));
                } else {
                    list = (List) this.viewsField.get(this.windowManagerObj);
                    list2 = (List) this.paramsField.get(this.windowManagerObj);
                }
                ArrayList newArrayList = Lists.newArrayList();
                int size = list.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        return newArrayList;
                    }
                    newArrayList.add(new Root.Builder().withDecorView((View) list.get(size)).withWindowLayoutParams((WindowManager.LayoutParams) list2.get(size)).build());
                }
            } catch (RuntimeException e) {
                Log.w(TAG, String.format(Locale.ROOT, "Reflective access to %s or %s on %s failed.", new Object[]{this.viewsField, this.paramsField, this.windowManagerObj}), e);
                return Lists.newArrayList();
            } catch (IllegalAccessException e2) {
                Log.w(TAG, String.format(Locale.ROOT, "Reflective access to %s or %s on %s failed.", new Object[]{this.viewsField, this.paramsField, this.windowManagerObj}), e2);
                return Lists.newArrayList();
            }
        } else {
            Log.w(TAG, "No reflective access to mParams");
            return Lists.newArrayList();
        }
    }
}
