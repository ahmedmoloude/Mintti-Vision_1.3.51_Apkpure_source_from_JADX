package androidx.test.espresso.remote;

import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.cache.Cache;
import androidx.test.espresso.core.internal.deps.guava.cache.CacheBuilder;
import androidx.test.internal.util.LogUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

final class MethodInvocation {
    private static final String TAG = "MethodInvocation";
    private static final Cache<MethodKey, Method> methodCache = CacheBuilder.newBuilder().maximumSize(256).build();
    private final Class<?> clazz;
    private final Object instance;
    private final String methodName;
    private final Class<?>[] parameterTypes;

    public MethodInvocation(Class<?> cls, Object obj, String str, Class<?>... clsArr) {
        this.clazz = (Class) Preconditions.checkNotNull(cls, "clazz cannot be null!");
        this.instance = obj;
        Preconditions.checkArgument(str != null && !str.isEmpty(), "methodName cannot be null or empty");
        this.methodName = str;
        this.parameterTypes = clsArr;
    }

    private static Method getDeclaredMethod(MethodKey methodKey) throws NoSuchMethodException {
        return getMethodInternal(methodKey, true);
    }

    private static Method getMethod(MethodKey methodKey) throws NoSuchMethodException {
        return getMethodInternal(methodKey, false);
    }

    private static Method getMethodInternal(MethodKey methodKey, boolean z) throws NoSuchMethodException {
        Method method;
        Cache<MethodKey, Method> cache = methodCache;
        Method ifPresent = cache.getIfPresent(methodKey);
        if (ifPresent == null) {
            LogUtil.logDebug(TAG, "Cache miss for method: %s#%s(%s). Loading into cache.", methodKey.type.getSimpleName(), methodKey.methodName, Arrays.toString(methodKey.parameterTypes));
            if (z) {
                method = methodKey.type.getDeclaredMethod(methodKey.methodName, methodKey.parameterTypes);
            } else {
                method = methodKey.type.getMethod(methodKey.methodName, methodKey.parameterTypes);
            }
            Method method2 = method;
            cache.put(methodKey, method2);
            return method2;
        }
        LogUtil.logDebug(TAG, "Cache hit for method: %s#%s(%s).", methodKey.type.getSimpleName(), methodKey.methodName, Arrays.toString(methodKey.parameterTypes));
        return ifPresent;
    }

    public static void invalidateCache() {
        methodCache.invalidateAll();
    }

    public Object invokeDeclaredMethod(Object... objArr) {
        try {
            return invokeMethodExplosively(getDeclaredMethod(new MethodKey(this.clazz, this.methodName, this.parameterTypes)), objArr);
        } catch (NoSuchMethodException e) {
            throw new RemoteProtocolException(String.format(Locale.ROOT, "No method: %s(%s) found for clazz: %s Available methods: %s", new Object[]{this.methodName, Arrays.asList(this.parameterTypes), this.clazz.getName(), Arrays.asList(this.clazz.getDeclaredMethods())}), e);
        }
    }

    public Object invokeMethod(Object... objArr) {
        try {
            return invokeMethodExplosively(getMethod(new MethodKey(this.clazz, this.methodName, this.parameterTypes)), objArr);
        } catch (NoSuchMethodException e) {
            throw new RemoteProtocolException(String.format(Locale.ROOT, "No method: %s found for clazz: %s. Available methods: %s", new Object[]{this.methodName, this.clazz.getName(), Arrays.asList(this.clazz.getMethods())}), e);
        }
    }

    private static final class MethodKey {
        /* access modifiers changed from: private */
        public final String methodName;
        /* access modifiers changed from: private */
        public final Class<?>[] parameterTypes;
        /* access modifiers changed from: private */
        public final Class<?> type;

        public MethodKey(Class<?> cls, String str, Class<?>[] clsArr) {
            this.type = cls;
            this.methodName = str;
            this.parameterTypes = clsArr;
        }

        public int hashCode() {
            return (((this.type.hashCode() * 31) + this.methodName.hashCode()) * 31) + Arrays.hashCode(this.parameterTypes);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            MethodKey methodKey = (MethodKey) obj;
            if (this.type.equals(methodKey.type) && this.methodName.equals(methodKey.methodName)) {
                return Arrays.equals(this.parameterTypes, methodKey.parameterTypes);
            }
            return false;
        }
    }

    private Object invokeMethodExplosively(Method method, Object... objArr) {
        try {
            method.setAccessible(true);
            Object invoke = method.invoke(this.instance, objArr);
            LogUtil.logDebug(TAG, "%s.invokeMethodExplosively(%s,%s)", this.clazz.getSimpleName(), this.methodName, Arrays.toString(objArr));
            return invoke;
        } catch (InvocationTargetException e) {
            throw new RemoteProtocolException(String.format(Locale.ROOT, "Cannot invoke method %s with args [%s] on builder %s", new Object[]{method, Arrays.toString(objArr), this.clazz.getName()}), e);
        } catch (IllegalAccessException e2) {
            throw new RemoteProtocolException(String.format(Locale.ROOT, "Cannot create instance of %s", new Object[]{this.clazz.getName()}), e2);
        } catch (SecurityException e3) {
            throw new RemoteProtocolException(String.format(Locale.ROOT, "Method not accessible: %s", new Object[]{method.getName()}), e3);
        } catch (Throwable th) {
            LogUtil.logDebug(TAG, "%s.invokeMethodExplosively(%s,%s)", this.clazz.getSimpleName(), this.methodName, Arrays.toString(objArr));
            throw th;
        }
    }
}
