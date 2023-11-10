package androidx.test.internal.runner;

import android.app.Instrumentation;
import android.os.Build;
import android.util.Log;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class ClassPathScanner {
    private static final String TAG = "ClassPathScanner";
    private final Set<String> classPath;

    public static class AcceptAllFilter implements ClassNameFilter {
        public boolean accept(String str) {
            return true;
        }
    }

    public interface ClassNameFilter {
        boolean accept(String str);
    }

    public static class ChainedClassNameFilter implements ClassNameFilter {
        private final List<ClassNameFilter> filters = new ArrayList();

        public void add(ClassNameFilter classNameFilter) {
            this.filters.add(classNameFilter);
        }

        public void addAll(ClassNameFilter... classNameFilterArr) {
            this.filters.addAll(Arrays.asList(classNameFilterArr));
        }

        public boolean accept(String str) {
            for (ClassNameFilter accept : this.filters) {
                if (!accept.accept(str)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static class ExternalClassNameFilter implements ClassNameFilter {
        public boolean accept(String str) {
            return !str.contains("$");
        }
    }

    public static class InclusivePackageNamesFilter implements ClassNameFilter {
        private final Collection<String> pkgNames;

        InclusivePackageNamesFilter(Collection<String> collection) {
            this.pkgNames = new ArrayList(collection.size());
            for (String next : collection) {
                if (!next.endsWith(".")) {
                    this.pkgNames.add(String.format("%s.", new Object[]{next}));
                } else {
                    this.pkgNames.add(next);
                }
            }
        }

        public boolean accept(String str) {
            for (String startsWith : this.pkgNames) {
                if (str.startsWith(startsWith)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class ExcludePackageNameFilter implements ClassNameFilter {
        private final String pkgName;

        ExcludePackageNameFilter(String str) {
            if (!str.endsWith(".")) {
                this.pkgName = String.format("%s.", new Object[]{str});
                return;
            }
            this.pkgName = str;
        }

        public boolean accept(String str) {
            return !str.startsWith(this.pkgName);
        }
    }

    static class ExcludeClassNamesFilter implements ClassNameFilter {
        private final Set<String> excludedClassNames;

        public ExcludeClassNamesFilter(Set<String> set) {
            this.excludedClassNames = set;
        }

        public boolean accept(String str) {
            return !this.excludedClassNames.contains(str);
        }
    }

    public ClassPathScanner(String... strArr) {
        this((Collection<String>) Arrays.asList(strArr));
    }

    public ClassPathScanner(Collection<String> collection) {
        HashSet hashSet = new HashSet();
        this.classPath = hashSet;
        hashSet.addAll(collection);
    }

    public static Collection<String> getDefaultClasspaths(Instrumentation instrumentation) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(instrumentation.getContext().getPackageCodePath());
        if (Build.VERSION.SDK_INT <= 20) {
            Pattern compile = Pattern.compile(".*\\.classes\\d+\\.zip");
            try {
                File file = new File(instrumentation.getTargetContext().getApplicationInfo().dataDir);
                ArrayDeque arrayDeque = new ArrayDeque();
                arrayDeque.add(file);
                while (!arrayDeque.isEmpty()) {
                    for (File file2 : ((File) arrayDeque.pop()).listFiles()) {
                        if (file2.isDirectory()) {
                            arrayDeque.add(file2);
                        } else if (file2.isFile() && compile.matcher(file2.getName()).matches()) {
                            arrayList.add(file2.getPath());
                        }
                    }
                }
            } catch (RuntimeException e) {
                Log.w(TAG, "Failed to retrieve ApplicationInfo, no additional .dex files add for app under test", e);
                return Collections.emptyList();
            }
        }
        return arrayList;
    }

    private void addEntriesFromPath(Set<String> set, String str, ClassNameFilter classNameFilter) throws IOException {
        DexFile dexFile;
        DexFile dexFile2 = null;
        try {
            dexFile = new DexFile(str);
        } catch (IOException e) {
            if (str.endsWith(".zip")) {
                dexFile = DexFile.loadDex(str, String.valueOf(str.substring(0, str.length() - 3)).concat("dex"), 0);
            } else {
                throw e;
            }
        } catch (Throwable th) {
            if (dexFile2 != null) {
                dexFile2.close();
            }
            throw th;
        }
        Enumeration<String> entries = dexFile.entries();
        while (entries.hasMoreElements()) {
            String nextElement = entries.nextElement();
            if (classNameFilter.accept(nextElement)) {
                set.add(nextElement);
            }
        }
        if (dexFile != null) {
            dexFile.close();
        }
    }

    public Set<String> getClassPathEntries(ClassNameFilter classNameFilter) throws IOException {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (String addEntriesFromPath : this.classPath) {
            addEntriesFromPath(linkedHashSet, addEntriesFromPath, classNameFilter);
        }
        return linkedHashSet;
    }
}
