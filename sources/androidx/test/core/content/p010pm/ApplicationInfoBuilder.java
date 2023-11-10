package androidx.test.core.content.p010pm;

import android.content.pm.ApplicationInfo;
import androidx.test.internal.util.Checks;

/* renamed from: androidx.test.core.content.pm.ApplicationInfoBuilder */
public final class ApplicationInfoBuilder {
    private String name;
    private String packageName;

    private ApplicationInfoBuilder() {
    }

    public static ApplicationInfoBuilder newBuilder() {
        return new ApplicationInfoBuilder();
    }

    public ApplicationInfoBuilder setPackageName(String str) {
        this.packageName = str;
        return this;
    }

    public ApplicationInfoBuilder setName(String str) {
        this.name = str;
        return this;
    }

    public ApplicationInfo build() {
        Checks.checkNotNull(this.packageName, "Mandatory field 'packageName' missing.");
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.name = this.name;
        applicationInfo.packageName = this.packageName;
        return applicationInfo;
    }
}
