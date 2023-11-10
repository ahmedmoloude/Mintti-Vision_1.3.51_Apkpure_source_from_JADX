package com.p020kl.healthmonitor;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.mintti.visionsdk.ble.BleManager;
import com.p020kl.commonbase.base.BaseFragment;
import com.p020kl.commonbase.base.ProxyActivity;
import com.p020kl.commonbase.bean.VersionInfo;
import com.p020kl.commonbase.callback.DownloadManagerListener;
import com.p020kl.commonbase.callback.NetChangeListener;
import com.p020kl.commonbase.constants.NetType;
import com.p020kl.commonbase.event.NetworkChangeEvent;
import com.p020kl.commonbase.net.MyDownloadManager;
import com.p020kl.commonbase.net.NetworkListener;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.service.HealthMonitorService;
import com.p020kl.commonbase.utils.AppUtils;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.FileUtils;
import com.p020kl.commonbase.utils.JsonUtils;
import com.p020kl.commonbase.utils.LoggerUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.utils.soundplay.SoundUtil;
import com.p020kl.commonbase.views.UpgradeDialog;
import com.p020kl.healthmonitor.navigation.NavigationFragment;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.MainActivity */
public class MainActivity extends ProxyActivity implements DownloadManagerListener {
    private OnHMServiceBindListener hmServiceBindListener;
    private boolean isBind = false;
    private HealthMonitorService mHealthMonitorService;
    public List<String> permissionList = new ArrayList();
    public String[] permissions = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.CAMERA"};
    /* access modifiers changed from: private */
    public Disposable updateDisposable;
    private UpgradeDialog upgradeDialog;

    /* renamed from: com.kl.healthmonitor.MainActivity$OnHMServiceBindListener */
    public interface OnHMServiceBindListener {
        void onBind(HealthMonitorService healthMonitorService);
    }

    public void onFailed() {
    }

    public void onPrepare() {
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 31) {
            this.permissions = new String[]{"android.permission.CAMERA", "android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT"};
        }
        if (Build.VERSION.SDK_INT >= 23) {
            for (String str : this.permissions) {
                if (ContextCompat.checkSelfPermission(this, str) != 0) {
                    this.permissionList.add(str);
                }
            }
            if (!this.permissionList.isEmpty()) {
                List<String> list = this.permissionList;
                ActivityCompat.requestPermissions(this, (String[]) list.toArray(new String[list.size()]), 1);
            }
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (iArr[i2] != 0) {
                    Toast.makeText(this, strArr[i2], 0).show();
                    finish();
                }
            }
        }
    }

    public BaseFragment setRootFragment() {
        return NavigationFragment.newInstance();
    }

    public HealthMonitorService getHealthMonitorService() {
        return this.mHealthMonitorService;
    }

    public void setHmServiceBindListener(OnHMServiceBindListener onHMServiceBindListener) {
        this.hmServiceBindListener = onHMServiceBindListener;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.e(this.TAG, "onCreate: ");
        NetworkListener.getInstance().init(this);
        NetworkListener.getInstance().setListener(new NetChangeListener() {
            public void onConnect(NetType netType) {
                EventBusUtil.sendEvent(new NetworkChangeEvent(netType));
            }

            public void onDisConnect() {
                EventBusUtil.sendEvent(new NetworkChangeEvent(NetType.NONE));
                ToastUtil.showShortToast(MainActivity.this.getString(C1624R.string.no_network_connection));
            }
        });
        SoundUtil.getInstance(this).initSound();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    private void checkApkUpdate() {
        final int versionCode = AppUtils.getVersionCode(this);
        RestClient.getSoftwareVersion("release").subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseResult<VersionInfo>>() {
            public void onComplete() {
            }

            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = MainActivity.this.updateDisposable = disposable;
            }

            public void onNext(ResponseResult<VersionInfo> responseResult) {
                LoggerUtil.json(JsonUtils.toJsonString(responseResult));
                if (responseResult.getStatus() == 200) {
                    VersionInfo data = responseResult.getData();
                    if (data.getVersionCode() > versionCode) {
                        MainActivity.this.showApkUpdateDialog(data);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void showApkUpdateDialog(final VersionInfo versionInfo) {
        List asList = Arrays.asList(versionInfo.getUpdateLog().split(","));
        UpgradeDialog upgradeDialog2 = this.upgradeDialog;
        if (upgradeDialog2 == null || !upgradeDialog2.isShowing()) {
            UpgradeDialog upgradeDialog3 = new UpgradeDialog(this, asList);
            this.upgradeDialog = upgradeDialog3;
            upgradeDialog3.setOnLaterListener(new UpgradeDialog.onLaterListener() {
                public void onLaterClicked(UpgradeDialog upgradeDialog) {
                    upgradeDialog.dismiss();
                }
            });
            this.upgradeDialog.setOnUpdateListener(new UpgradeDialog.onUpdateListener() {
                public void onUpdateClicked(UpgradeDialog upgradeDialog) {
                    MyDownloadManager myDownloadManager = new MyDownloadManager(MainActivity.this, versionInfo.getDownloadUrl(), versionInfo.getVersionName(), FileUtils.getNewSoftFile(MainActivity.this, versionInfo.getVersionName()).getAbsolutePath());
                    myDownloadManager.setListener(MainActivity.this);
                    myDownloadManager.download();
                    upgradeDialog.dismiss();
                }
            });
            this.upgradeDialog.show();
            this.upgradeDialog.setTvTitle(StringUtils.getString(C1624R.string.update_software_content));
            this.upgradeDialog.setVersion(versionInfo.getVersionName());
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnect();
        Disposable disposable = this.updateDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.updateDisposable.dispose();
        }
        UpgradeDialog upgradeDialog2 = this.upgradeDialog;
        if (upgradeDialog2 != null && upgradeDialog2.isShowing()) {
            this.upgradeDialog.cancel();
        }
        NetworkListener.getInstance().release();
        SoundUtil.getInstance(this).release();
    }

    public void onSuccess(String str) {
        AppUtils.installApk(this, new File(str));
    }
}
