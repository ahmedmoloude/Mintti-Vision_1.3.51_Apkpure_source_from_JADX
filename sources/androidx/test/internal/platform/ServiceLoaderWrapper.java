package androidx.test.internal.platform;

import android.os.StrictMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public final class ServiceLoaderWrapper {

    public interface Factory<T> {
        T create();
    }

    private ServiceLoaderWrapper() {
    }

    public static <T> List<T> loadService(Class<T> cls) {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        ArrayList arrayList = new ArrayList();
        Iterator<S> it = ServiceLoader.load(cls).iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        StrictMode.setThreadPolicy(allowThreadDiskReads);
        return arrayList;
    }

    public static <T> T loadSingleService(Class<T> cls, Factory<T> factory) {
        T loadSingleServiceOrNull = loadSingleServiceOrNull(cls);
        return loadSingleServiceOrNull == null ? factory.create() : loadSingleServiceOrNull;
    }

    public static <T> T loadSingleServiceOrNull(Class<T> cls) {
        List<T> loadService = loadService(cls);
        if (loadService.isEmpty()) {
            return null;
        }
        if (loadService.size() == 1) {
            return loadService.get(0);
        }
        String valueOf = String.valueOf(cls.getName());
        throw new IllegalStateException(valueOf.length() != 0 ? "Found more than one implementation for ".concat(valueOf) : new String("Found more than one implementation for "));
    }
}
