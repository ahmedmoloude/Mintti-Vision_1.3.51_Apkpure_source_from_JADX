package androidx.test.runner.permission;

import android.content.Context;
import android.util.Log;
import androidx.test.runner.permission.RequestPermissionCallable;

class GrantPermissionCallable extends RequestPermissionCallable {
    private static final String TAG = "GrantPermissionCallable";

    GrantPermissionCallable(ShellCommand shellCommand, Context context, String str) {
        super(shellCommand, context, str);
    }

    public RequestPermissionCallable.Result call() throws Exception {
        if (isPermissionGranted()) {
            String permission = getPermission();
            StringBuilder sb = new StringBuilder(String.valueOf(permission).length() + 32);
            sb.append("Permission: ");
            sb.append(permission);
            sb.append(" is already granted!");
            Log.i(TAG, sb.toString());
            return RequestPermissionCallable.Result.SUCCESS;
        }
        try {
            getShellCommand().execute();
            return RequestPermissionCallable.Result.SUCCESS;
        } finally {
            if (!isPermissionGranted()) {
                Thread.sleep(1000);
                if (!isPermissionGranted()) {
                    String permission2 = getPermission();
                    StringBuilder sb2 = new StringBuilder(String.valueOf(permission2).length() + 31);
                    sb2.append("Permission: ");
                    sb2.append(permission2);
                    sb2.append(" cannot be granted!");
                    Log.e(TAG, sb2.toString());
                    return RequestPermissionCallable.Result.FAILURE;
                }
            }
        }
    }
}
