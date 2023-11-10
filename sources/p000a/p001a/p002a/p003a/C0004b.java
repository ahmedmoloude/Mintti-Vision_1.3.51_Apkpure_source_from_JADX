package p000a.p001a.p002a.p003a;

import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.callback.IDeviceVersionCallback;
import com.mintti.visionsdk.common.LogUtils;
import p000a.p001a.p002a.p003a.p004d.C0011d;

/* renamed from: a.a.a.a.b */
public class C0004b implements IDeviceVersionCallback {

    /* renamed from: a */
    public final /* synthetic */ BleManager.C2184k f20a;

    public C0004b(BleManager.C2184k kVar) {
        this.f20a = kVar;
    }

    public void onDeviceVersionInfo(String str) {
        C0011d dVar;
        LogUtils.m378d("ECGFragment1", str);
        boolean z = false;
        if (str.contains("A00")) {
            boolean unused = BleManager.this.isLowCost = false;
            dVar = BleManager.this.handleVisionData;
        } else {
            boolean unused2 = BleManager.this.isLowCost = true;
            dVar = BleManager.this.handleVisionData;
            z = true;
        }
        dVar.f49I = z;
        if (BleManager.this.iDeviceVersionCallback != null) {
            BleManager.this.iDeviceVersionCallback.onDeviceVersionInfo(str);
        }
    }
}
