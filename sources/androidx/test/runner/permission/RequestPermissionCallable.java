package androidx.test.runner.permission;

import android.content.Context;
import android.text.TextUtils;
import androidx.test.internal.util.Checks;
import java.util.Objects;
import java.util.concurrent.Callable;

public abstract class RequestPermissionCallable implements Callable<Result> {
    private final String permission;
    private final ShellCommand shellCommand;
    private final Context targetContext;
    private final String targetPackage;

    public enum Result {
        SUCCESS,
        FAILURE
    }

    public RequestPermissionCallable(ShellCommand shellCommand2, Context context, String str) {
        this.shellCommand = (ShellCommand) Checks.checkNotNull(shellCommand2, "shellCommand cannot be null!");
        Context context2 = (Context) Checks.checkNotNull(context, "targetContext cannot be null!");
        this.targetContext = context2;
        String packageName = context2.getPackageName();
        Checks.checkState(!TextUtils.isEmpty(packageName), "targetPackage cannot be empty or null!");
        this.targetPackage = packageName;
        this.permission = str;
    }

    /* access modifiers changed from: protected */
    public String getPermission() {
        return this.permission;
    }

    /* access modifiers changed from: protected */
    public boolean isPermissionGranted() {
        return this.targetContext.checkCallingOrSelfPermission(this.permission) == 0;
    }

    /* access modifiers changed from: protected */
    public ShellCommand getShellCommand() {
        return this.shellCommand;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RequestPermissionCallable requestPermissionCallable = (RequestPermissionCallable) obj;
        if (!Objects.equals(this.targetPackage, requestPermissionCallable.targetPackage) || !Objects.equals(this.permission, requestPermissionCallable.permission)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.targetPackage, this.permission});
    }
}
