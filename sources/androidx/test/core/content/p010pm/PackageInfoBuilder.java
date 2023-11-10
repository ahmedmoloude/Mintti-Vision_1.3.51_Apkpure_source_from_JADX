package androidx.test.core.content.p010pm;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import androidx.test.internal.util.Checks;

/* renamed from: androidx.test.core.content.pm.PackageInfoBuilder */
public final class PackageInfoBuilder {
    private ApplicationInfo applicationInfo;
    private String packageName;

    private PackageInfoBuilder() {
    }

    public static PackageInfoBuilder newBuilder() {
        return new PackageInfoBuilder();
    }

    public PackageInfoBuilder setPackageName(String str) {
        this.packageName = str;
        return this;
    }

    public PackageInfoBuilder setApplicationInfo(ApplicationInfo applicationInfo2) {
        this.applicationInfo = applicationInfo2;
        return this;
    }

    public PackageInfo build() {
        Checks.checkNotNull(this.packageName, "Mandatory field 'packageName' missing.");
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.packageName = this.packageName;
        if (this.applicationInfo == null) {
            this.applicationInfo = ApplicationInfoBuilder.newBuilder().setPackageName(this.packageName).build();
        }
        packageInfo.applicationInfo = this.applicationInfo;
        Checks.checkState(packageInfo.packageName.equals(packageInfo.applicationInfo.packageName), "Field 'packageName' must match field 'applicationInfo.packageName'");
        return packageInfo;
    }
}
