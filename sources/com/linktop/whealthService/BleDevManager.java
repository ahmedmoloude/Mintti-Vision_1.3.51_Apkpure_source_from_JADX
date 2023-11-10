package com.linktop.whealthService;

import android.content.Context;
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
import com.linktop.whealthService.util.Communicate;
import com.linktop.whealthService.util.IBleDev;
import java.util.ArrayList;
import java.util.List;

public final class BleDevManager implements IBleDev {
    private static final String TAG = "BleDevManager";
    private CmdThread cmdSendThread;
    private boolean isMeasuring;
    private AckTask mAckTask;
    private BatteryTask mBatteryTask;
    private BpTask mBpTask;
    private BtTask mBtTask;
    private Communicate mCommunicate;
    private DeviceTask mDeviceTask;
    private EcgTask mEcgTask;
    private OnSDKThrowableCallback mOnSDKThrowableCallback;
    private OxTask mOxTask;
    private SysErrorTask mSysErrorTask;
    private TestPaperTask mTestPaperTask;
    private IUserProfile mUserProfile;

    public static abstract class CmdThread extends Thread {
        private final List<byte[]> cmdList = new ArrayList();
        private boolean isRunning;

        public abstract void dealCmd(byte[] bArr);

        public void interrupt() {
            this.cmdList.clear();
            this.isRunning = false;
            super.interrupt();
        }

        public void run() {
            while (this.isRunning) {
                synchronized (this.cmdList) {
                    if (this.cmdList.isEmpty()) {
                        try {
                            this.cmdList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        dealCmd(this.cmdList.get(0));
                        this.cmdList.remove(0);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException unused) {
                }
            }
        }

        public void setCmd(byte[] bArr) {
            synchronized (this.cmdList) {
                this.cmdList.add(bArr);
                this.cmdList.notify();
            }
        }

        public synchronized void start() {
            this.isRunning = true;
            super.start();
        }
    }

    public interface OnSDKThrowableCallback {
        void onSDKThrowable(Throwable th);
    }

    public void clearCmdToSend(byte b, byte b2) {
    }

    public void cmdToSend(byte[] bArr) {
        CmdThread cmdThread = this.cmdSendThread;
        if (cmdThread != null && bArr != null) {
            cmdThread.setCmd(bArr);
        }
    }

    public void destroy() {
        CmdThread cmdThread = this.cmdSendThread;
        if (cmdThread != null) {
            cmdThread.interrupt();
            this.cmdSendThread = null;
        }
    }

    public AckTask getAckTask() {
        return this.mAckTask;
    }

    public BatteryTask getBatteryTask() {
        return this.mBatteryTask;
    }

    public BpTask getBpTask() {
        return this.mBpTask;
    }

    public BtTask getBtTask() {
        return this.mBtTask;
    }

    public Communicate getCommunicate() {
        return this.mCommunicate;
    }

    public DeviceTask getDeviceTask() {
        return this.mDeviceTask;
    }

    public EcgTask getEcgTask() {
        return this.mEcgTask;
    }

    public OxTask getOxTask() {
        return this.mOxTask;
    }

    public SysErrorTask getSysErrorTask() {
        return this.mSysErrorTask;
    }

    public TestPaperTask getTestPaperTask() {
        return this.mTestPaperTask;
    }

    public IUserProfile getUserProfile() {
        return this.mUserProfile;
    }

    public void initHC(Context context, OnSDKThrowableCallback onSDKThrowableCallback) {
        this.mOnSDKThrowableCallback = onSDKThrowableCallback;
        this.mAckTask = new AckTask(this);
        this.mBatteryTask = new BatteryTask(this);
        this.mSysErrorTask = new SysErrorTask(this);
        this.mCommunicate = new Communicate(this);
        this.mDeviceTask = new DeviceTask(this);
        this.mEcgTask = new EcgTask(context, this);
        this.mTestPaperTask = new TestPaperTask(context, this);
        this.mBpTask = new BpTask(this);
        this.mBtTask = new BtTask(this);
        this.mOxTask = new OxTask(context, this);
    }

    public boolean isMeasuring() {
        return this.isMeasuring;
    }

    public void onInnerThrowableCallback(Throwable th) {
        OnSDKThrowableCallback onSDKThrowableCallback = this.mOnSDKThrowableCallback;
        if (onSDKThrowableCallback != null) {
            onSDKThrowableCallback.onSDKThrowable(th);
        }
    }

    public void setCmdThread(CmdThread cmdThread) {
        if (this.cmdSendThread != null) {
            destroy();
        }
        this.cmdSendThread = cmdThread;
        cmdThread.start();
    }

    public void setMeasuring(boolean z) {
        this.isMeasuring = z;
    }

    public void setUserProfile(IUserProfile iUserProfile) {
        this.mUserProfile = iUserProfile;
    }

    public void updateBleConnectIntervalFailed() {
    }
}
