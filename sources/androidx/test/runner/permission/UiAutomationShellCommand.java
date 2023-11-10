package androidx.test.runner.permission;

import android.app.UiAutomation;
import android.util.Log;
import androidx.test.InstrumentationRegistry;
import androidx.test.internal.util.Checks;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class UiAutomationShellCommand extends ShellCommand {
    private static final String TAG = "UiAutomationShellCmd";
    private final PmCommand command;
    private final String permission;
    private final String targetPackage;
    private final UiAutomation uiAutomation = ((UiAutomation) Checks.checkNotNull(InstrumentationRegistry.getInstrumentation().getUiAutomation()));

    enum PmCommand {
        GRANT_PERMISSION("grant");
        
        private final String pmCommand;

        private PmCommand(String str) {
            String valueOf = String.valueOf(str);
            this.pmCommand = valueOf.length() != 0 ? "pm ".concat(valueOf) : new String("pm ");
        }

        public String get() {
            return this.pmCommand;
        }
    }

    UiAutomationShellCommand(String str, String str2, PmCommand pmCommand) {
        this.targetPackage = shellEscape(str);
        this.permission = shellEscape(str2);
        this.command = pmCommand;
    }

    public void execute() throws Exception {
        executePermissionCommand(commandForPermission());
    }

    /* access modifiers changed from: protected */
    public String commandForPermission() {
        return this.command.get() + " " + this.targetPackage + " " + this.permission;
    }

    private void executePermissionCommand(String str) throws IOException {
        String valueOf = String.valueOf(str);
        Log.i(TAG, valueOf.length() != 0 ? "Requesting permission: ".concat(valueOf) : new String("Requesting permission: "));
        try {
            awaitTermination(this.uiAutomation.executeShellCommand(str), 2, TimeUnit.SECONDS);
        } catch (TimeoutException unused) {
            String valueOf2 = String.valueOf(str);
            Log.e(TAG, valueOf2.length() != 0 ? "Timeout while executing cmd: ".concat(valueOf2) : new String("Timeout while executing cmd: "));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void awaitTermination(android.os.ParcelFileDescriptor r3, long r4, java.util.concurrent.TimeUnit r6) throws java.io.IOException, java.util.concurrent.TimeoutException {
        /*
            long r4 = r6.toMillis(r4)
            r0 = 0
            int r6 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r6 <= 0) goto L_0x000f
            long r0 = java.lang.System.currentTimeMillis()
            long r0 = r0 + r4
        L_0x000f:
            r4 = 0
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ all -> 0x0040 }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ all -> 0x0040 }
            android.os.ParcelFileDescriptor$AutoCloseInputStream r2 = new android.os.ParcelFileDescriptor$AutoCloseInputStream     // Catch:{ all -> 0x0040 }
            r2.<init>(r3)     // Catch:{ all -> 0x0040 }
            r6.<init>(r2)     // Catch:{ all -> 0x0040 }
            r5.<init>(r6)     // Catch:{ all -> 0x0040 }
        L_0x001f:
            java.lang.String r3 = r5.readLine()     // Catch:{ all -> 0x003d }
            if (r3 == 0) goto L_0x0039
            java.lang.String r4 = "UiAutomationShellCmd"
            android.util.Log.i(r4, r3)     // Catch:{ all -> 0x003d }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x003d }
            int r6 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r6 > 0) goto L_0x0033
            goto L_0x001f
        L_0x0033:
            java.util.concurrent.TimeoutException r3 = new java.util.concurrent.TimeoutException     // Catch:{ all -> 0x003d }
            r3.<init>()     // Catch:{ all -> 0x003d }
            throw r3     // Catch:{ all -> 0x003d }
        L_0x0039:
            r5.close()
            return
        L_0x003d:
            r3 = move-exception
            r4 = r5
            goto L_0x0041
        L_0x0040:
            r3 = move-exception
        L_0x0041:
            if (r4 == 0) goto L_0x0046
            r4.close()
        L_0x0046:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.runner.permission.UiAutomationShellCommand.awaitTermination(android.os.ParcelFileDescriptor, long, java.util.concurrent.TimeUnit):void");
    }
}
