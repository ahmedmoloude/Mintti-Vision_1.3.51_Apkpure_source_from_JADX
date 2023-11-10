package com.linktop.whealthService.task;

import com.linktop.whealthService.util.IBleDev;

public final class AckTask extends ModuleTask {

    /* renamed from: a */
    private IBleDev f1044a;

    public AckTask(IBleDev iBleDev) {
        this.f1044a = iBleDev;
    }

    public void dealData(byte[] bArr) {
        IBleDev iBleDev;
        if (bArr.length >= 2 && (iBleDev = this.f1044a) != null) {
            iBleDev.clearCmdToSend(bArr[0], bArr[1]);
        }
    }
}
