package com.linktop.whealthService.util;

import com.linktop.constant.IUserProfile;
import com.linktop.whealthService.task.AckTask;
import com.linktop.whealthService.task.BatteryTask;
import com.linktop.whealthService.task.BpTask;
import com.linktop.whealthService.task.BtTask;
import com.linktop.whealthService.task.DeviceTask;
import com.linktop.whealthService.task.EcgTask;
import com.linktop.whealthService.task.OxTask;
import com.linktop.whealthService.task.SysErrorTask;
import com.linktop.whealthService.task.TestPaperTask;

public interface IBleDev {
    void clearCmdToSend(byte b, byte b2);

    void cmdToSend(byte[] bArr);

    AckTask getAckTask();

    BatteryTask getBatteryTask();

    BpTask getBpTask();

    BtTask getBtTask();

    Communicate getCommunicate();

    DeviceTask getDeviceTask();

    EcgTask getEcgTask();

    OxTask getOxTask();

    SysErrorTask getSysErrorTask();

    TestPaperTask getTestPaperTask();

    IUserProfile getUserProfile();

    boolean isMeasuring();

    void onInnerThrowableCallback(Throwable th);

    void setMeasuring(boolean z);

    void setUserProfile(IUserProfile iUserProfile);

    void updateBleConnectIntervalFailed();
}
