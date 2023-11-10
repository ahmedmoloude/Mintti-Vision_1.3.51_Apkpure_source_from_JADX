package com.linktop.whealthService.task;

import com.linktop.constant.IUserProfile;
import com.linktop.constant.Pair;
import com.linktop.whealthService.util.Communicate;
import com.linktop.whealthService.util.IBleDev;

public abstract class HcModuleTask extends ModuleTask {
    public final Communicate mCommunicate;
    public final IBleDev mIBleDev;

    public HcModuleTask(IBleDev iBleDev) {
        this.mIBleDev = iBleDev;
        this.mCommunicate = iBleDev.getCommunicate();
    }

    public IBleDev getIBleDev() {
        return this.mIBleDev;
    }

    public IUserProfile getUserProfile() {
        return this.mIBleDev.getUserProfile();
    }

    public void start(Pair... pairArr) {
        this.mIBleDev.setMeasuring(true);
    }

    public void stop() {
        this.mIBleDev.setMeasuring(false);
    }
}
