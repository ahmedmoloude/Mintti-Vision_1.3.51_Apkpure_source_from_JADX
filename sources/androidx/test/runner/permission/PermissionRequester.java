package androidx.test.runner.permission;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.test.internal.platform.content.PermissionGranter;
import androidx.test.internal.util.Checks;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.permission.RequestPermissionCallable;
import androidx.test.runner.permission.UiAutomationShellCommand;
import java.util.HashSet;
import java.util.Iterator;
import junit.framework.Assert;

public class PermissionRequester implements PermissionGranter {
    private static final String TAG = "PermissionRequester";
    private int androidRuntimeVersion;
    final HashSet<RequestPermissionCallable> requestedPermissions;
    private final Context targetContext;

    public PermissionRequester() {
        this(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    PermissionRequester(Context context) {
        this.androidRuntimeVersion = Build.VERSION.SDK_INT;
        this.requestedPermissions = new HashSet<>();
        this.targetContext = (Context) Checks.checkNotNull(context, "targetContext cannot be null!");
    }

    public void addPermissions(String... strArr) {
        Checks.checkNotNull(strArr, "permissions cannot be null!");
        if (deviceSupportsRuntimePermissions()) {
            for (String str : strArr) {
                Assert.assertFalse("Permission String is empty or null!", TextUtils.isEmpty(str));
                Checks.checkState(this.requestedPermissions.add(new GrantPermissionCallable(new UiAutomationShellCommand(this.targetContext.getPackageName(), str, UiAutomationShellCommand.PmCommand.GRANT_PERMISSION), this.targetContext, str)));
            }
        }
    }

    public void requestPermissions() {
        if (deviceSupportsRuntimePermissions()) {
            Iterator<RequestPermissionCallable> it = this.requestedPermissions.iterator();
            while (it.hasNext()) {
                try {
                    if (RequestPermissionCallable.Result.FAILURE == it.next().call()) {
                        Assert.fail("Failed to grant permissions, see logcat for details");
                        return;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "An Exception was thrown while granting permission", e);
                    Assert.fail("Failed to grant permissions, see logcat for details");
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setAndroidRuntimeVersion(int i) {
        this.androidRuntimeVersion = i;
    }

    private boolean deviceSupportsRuntimePermissions() {
        boolean z = getAndroidRuntimeVersion() >= 23;
        if (!z) {
            Log.w(TAG, "Permissions can only be granted on devices running Android M (API 23) orhigher. This rule is ignored.");
        }
        return z;
    }

    private int getAndroidRuntimeVersion() {
        return this.androidRuntimeVersion;
    }
}
