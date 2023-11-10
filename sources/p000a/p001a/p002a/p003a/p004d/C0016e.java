package p000a.p001a.p002a.p003a.p004d;

import com.mintti.visionsdk.ble.callback.IBleWriteResponse;
import com.mintti.visionsdk.common.LogUtils;

/* renamed from: a.a.a.a.d.e */
public class C0016e implements IBleWriteResponse {

    /* renamed from: a */
    public final /* synthetic */ int f84a;

    /* renamed from: b */
    public final /* synthetic */ int f85b;

    public C0016e(C0011d dVar, int i, int i2) {
        this.f84a = i;
        this.f85b = i2;
    }

    public void onWriteFailed() {
        LogUtils.m378d("HandleVisionData", "onWriteSuccess: 写入停止加压失败");
    }

    public void onWriteSuccess() {
        LogUtils.m378d("HandleVisionData", "onWriteSuccess: 写入停止成功: stopAdd = " + this.f84a + " stopDe = " + this.f85b);
    }
}
