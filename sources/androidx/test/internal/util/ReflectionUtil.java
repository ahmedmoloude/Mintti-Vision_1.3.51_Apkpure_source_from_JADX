package androidx.test.internal.util;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {
    private static final String TAG = "ReflectionUtil";

    public static void reflectivelyInvokeRemoteMethod(String str, String str2) {
        Checks.checkNotNull(str);
        Checks.checkNotNull(str2);
        String valueOf = String.valueOf(str2);
        Log.i(TAG, valueOf.length() != 0 ? "Attempting to reflectively call: ".concat(valueOf) : new String("Attempting to reflectively call: "));
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke((Object) null, new Object[0]);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e(TAG, "Reflective call failed: ", e);
        }
    }
}
