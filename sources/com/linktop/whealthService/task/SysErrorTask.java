package com.linktop.whealthService.task;

import com.linktop.utils.BleDevLog;
import com.linktop.whealthService.util.IBleDev;

public final class SysErrorTask extends ModuleTask {

    /* renamed from: a */
    private final IBleDev f1169a;

    public SysErrorTask(IBleDev iBleDev) {
        this.f1169a = iBleDev;
    }

    public void dealData(byte[] bArr) {
        if (bArr.length >= 2 && bArr[0] == 0 && bArr[1] == 1) {
            BleDevLog.m142c("SysErrorTask", "connect interval update failed");
            this.f1169a.updateBleConnectIntervalFailed();
        }
    }
}
