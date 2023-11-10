package com.p020kl.healthmonitor.mine;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.DeviceType;
import com.mintti.visionsdk.ble.callback.IDeviceVersionCallback;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.VersionInfo;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.event.BleStatusEvent;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.interceptor.ProgressResponseListener;
import com.p020kl.commonbase.service.HealthMonitorService;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.FileUtils;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.views.CommonSelectDialog;
import com.p020kl.commonbase.views.UpgradeDialog;
import com.p020kl.commonbase.views.UpgradeProgressDialog;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.base.dfu.DfuService;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.ResponseBody;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.schedulers.Schedulers;
import p036no.nordicsemi.android.dfu.DfuProgressListener;
import p036no.nordicsemi.android.dfu.DfuServiceController;
import p036no.nordicsemi.android.dfu.DfuServiceInitiator;
import p036no.nordicsemi.android.dfu.DfuServiceListenerHelper;

/* renamed from: com.kl.healthmonitor.mine.MyDeviceFragment */
public class MyDeviceFragment extends BaseFragmentWhiteToolbar implements DfuProgressListener {
    private static final int HANDLER_CUR_PROGRESS = 1;
    private static final int HANDLER_DFU_ABORTED = 2;
    private static final int HANDLER_DFU_ERROR = 3;
    private static final int HANDLER_DFU_STARTED = 6;
    private static final int HANDLER_DOWNLOAD_OVER = 5;
    private static final int HANDLER_UPDATE_COMPLETE = 4;
    @BindView(3103)
    Button btnUnBind;
    @BindView(3089)
    Button btnUpgrade;
    /* access modifiers changed from: private */
    public int curFirmVersion;
    /* access modifiers changed from: private */
    public DfuServiceController dfuServiceController = null;
    private Disposable downloadDisposable = null;
    /* access modifiers changed from: private */
    public String downloadUrl;
    /* access modifiers changed from: private */
    public boolean isHasNewFirmVersion = false;
    /* access modifiers changed from: private */
    public List<String> logArrayList = new ArrayList();
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    MyDeviceFragment.this.upgradeProgressDialog.setCurrentProgress(message.arg1);
                    return;
                case 2:
                    MyDeviceFragment myDeviceFragment = MyDeviceFragment.this;
                    myDeviceFragment.showHint(myDeviceFragment.getString(C1624R.string.firmware_update_exited));
                    MyDeviceFragment.this.btnUpgrade.setEnabled(false);
                    return;
                case 3:
                    MyDeviceFragment.this.upgradeProgressDialog.cancel();
                    MyDeviceFragment myDeviceFragment2 = MyDeviceFragment.this;
                    myDeviceFragment2.showHint(myDeviceFragment2.getString(C1624R.string.firm_update_failed_restart));
                    MyDeviceFragment.this.btnUpgrade.setEnabled(false);
                    return;
                case 4:
                    if (MyDeviceFragment.this.upgradeProgressDialog != null && MyDeviceFragment.this.upgradeProgressDialog.isShowing()) {
                        SpManager.setFirmwareVersion(MyDeviceFragment.this.newVersionName);
                        MyDeviceFragment.this.tvFirmVersion.setText(MyDeviceFragment.this.newVersionName);
                        MyDeviceFragment.this.upgradeProgressDialog.dismiss();
                        MyDeviceFragment.this.btnUpgrade.setEnabled(false);
                        FileUtils.deleteZipFileDir();
                        MyDeviceFragment.this.showHint(Integer.valueOf(C1624R.string.update_firmware_success));
                        return;
                    }
                    return;
                case 5:
                    MyDeviceFragment.this.upgradeProgressDialog.setTitle(StringUtils.getString(C1624R.string.updating));
                    MyDeviceFragment.this.prepareUpgrade();
                    return;
                case 6:
                    MyDeviceFragment.this.upgradeProgressDialog.setTitle(MyDeviceFragment.this.getString(C1624R.string.firm_updating));
                    return;
                default:
                    return;
            }
        }
    };
    private Disposable netVersionDisposable = null;
    /* access modifiers changed from: private */
    public int newFirmVersion;
    /* access modifiers changed from: private */
    public File newFirmZipFile = null;
    /* access modifiers changed from: private */
    public String newVersionName = "";
    @BindView(3755)
    TextView tvFirmVersion;
    UpgradeDialog upgradeDialog;
    /* access modifiers changed from: private */
    public UpgradeProgressDialog upgradeProgressDialog;
    /* access modifiers changed from: private */
    public String versionName = null;
    /* access modifiers changed from: private */
    public String zipFileMd5;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public void onBindView(Bundle bundle, View view) {
    }

    public static MyDeviceFragment newInstance() {
        Bundle bundle = new Bundle();
        MyDeviceFragment myDeviceFragment = new MyDeviceFragment();
        myDeviceFragment.setArguments(bundle);
        return myDeviceFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_mydevice);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.my_device);
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        super.initView(view);
        if (!TextUtils.isEmpty(SpManager.getDeviceAddress())) {
            this.btnUnBind.setEnabled(true);
        } else {
            this.tvFirmVersion.setText(C1624R.string.device_unbound);
        }
        this.tvFirmVersion.setText(SpManager.getFirmwareVersion());
        getDeviceVersion();
        DfuServiceListenerHelper.registerProgressListener(requireContext(), this);
    }

    private void getDeviceVersion() {
        if (HealthMonitorService.getInstance().isConnected() && HealthMonitorService.getInstance().getDeviceType() == DeviceType.TYPE_MINTTI_VISION) {
            BleManager.getInstance().getDeviceVersionInfo(new IDeviceVersionCallback() {
                public final void onDeviceVersionInfo(String str) {
                    MyDeviceFragment.this.lambda$getDeviceVersion$7$MyDeviceFragment(str);
                }
            });
        }
    }

    public /* synthetic */ void lambda$getDeviceVersion$7$MyDeviceFragment(String str) {
        this.versionName = str;
        requireActivity().runOnUiThread(new Runnable() {
            public final void run() {
                MyDeviceFragment.this.lambda$null$6$MyDeviceFragment();
            }
        });
        getNetVersionInfo();
    }

    public /* synthetic */ void lambda$null$6$MyDeviceFragment() {
        this.tvFirmVersion.setText(this.versionName);
        this.curFirmVersion = StringUtils.parseFirmwareVersionCode(this.versionName);
        Log.e("MyDeviceFragment", "getDeviceVersion: " + this.versionName);
    }

    private void getNetVersionInfo() {
        this.netVersionDisposable = RestClient.getFirmwareVersion("release", BleManager.getInstance().getSampleRate() == 512 ? Constants.VISION_FIRMWARE_A01 : Constants.VISION_FIRMWARE_A00).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<VersionInfo>>() {
            public void accept(ResponseResult<VersionInfo> responseResult) throws Exception {
                if (responseResult.getStatus() == 200) {
                    VersionInfo data = responseResult.getData();
                    int unused = MyDeviceFragment.this.newFirmVersion = data.getVersionCode();
                    String unused2 = MyDeviceFragment.this.newVersionName = data.getVersionName();
                    String access$200 = MyDeviceFragment.this.TAG;
                    Log.e(access$200, "accept: code=" + MyDeviceFragment.this.newFirmVersion + "name=" + MyDeviceFragment.this.newVersionName);
                    String unused3 = MyDeviceFragment.this.downloadUrl = data.getDownloadUrl();
                    String unused4 = MyDeviceFragment.this.zipFileMd5 = data.getFileMd5();
                    List unused5 = MyDeviceFragment.this.logArrayList = Arrays.asList(data.getUpdateLog().split(","));
                    if (((MyDeviceFragment.this.versionName.contains("A00") && MyDeviceFragment.this.newVersionName.contains("A00")) || (MyDeviceFragment.this.versionName.contains("A01") && MyDeviceFragment.this.newVersionName.contains("A01"))) && MyDeviceFragment.this.newFirmVersion > MyDeviceFragment.this.curFirmVersion) {
                        boolean unused6 = MyDeviceFragment.this.isHasNewFirmVersion = true;
                        MyDeviceFragment.this.btnUpgrade.setEnabled(true);
                        MyDeviceFragment.this.onUpgradeClick();
                    }
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                MyDeviceFragment.this.showHint(th.getMessage());
            }
        });
    }

    @OnClick({3089})
    public void onUpgradeClick() {
        UpgradeDialog upgradeDialog2 = new UpgradeDialog(getActivity(), this.logArrayList);
        this.upgradeDialog = upgradeDialog2;
        upgradeDialog2.setOnLaterListener(new UpgradeDialog.onLaterListener() {
            public void onLaterClicked(UpgradeDialog upgradeDialog) {
                upgradeDialog.dismiss();
            }
        });
        this.upgradeDialog.setOnUpdateListener(new UpgradeDialog.onUpdateListener() {
            public void onUpdateClicked(UpgradeDialog upgradeDialog) {
                MyDeviceFragment.this.showUpgradeProgressDialog();
                MyDeviceFragment.this.prepareUpgrade();
                upgradeDialog.dismiss();
            }
        });
        this.upgradeDialog.show();
        this.upgradeDialog.setVersion(String.valueOf(this.newFirmVersion));
        this.upgradeDialog.setTvTitle(StringUtils.getString(C1624R.string.update_firm_content));
    }

    /* access modifiers changed from: private */
    public void prepareUpgrade() {
        Log.e("MyDeviceFragment", "prepareUpgrade: " + this.zipFileMd5);
        if (FileUtils.checkNewFirmZipExist(this.newVersionName, this.zipFileMd5)) {
            this.newFirmZipFile = FileUtils.getNewFirmZipFile(this.newVersionName);
            startUpdate();
            return;
        }
        Log.e("MyDeviceFragment", "prepareUpgrade: 下载");
        UpgradeProgressDialog upgradeProgressDialog2 = this.upgradeProgressDialog;
        if (upgradeProgressDialog2 != null) {
            upgradeProgressDialog2.setTitle(StringUtils.getString(C1624R.string.downloading_firmware));
        }
        downloadFirmFile();
    }

    private void startUpdate() {
        HealthMonitorService.getInstance().setDfu(true);
        EventBusUtil.sendEvent(new BleStatusEvent(106));
        Log.e("DeviceInfoFragment", "startUpdate: ");
        String deviceAddress = SpManager.getDeviceAddress();
        String deviceName = SpManager.getDeviceName();
        DfuServiceInitiator dfuServiceInitiator = new DfuServiceInitiator(deviceAddress);
        dfuServiceInitiator.setDeviceName(deviceName).setDisableNotification(true).setKeepBond(true).setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true);
        dfuServiceInitiator.setForeground(true);
        if (Build.VERSION.SDK_INT >= 26) {
            DfuServiceInitiator.createDfuNotificationChannel(requireContext());
        }
        dfuServiceInitiator.setZip(Uri.fromFile(this.newFirmZipFile));
        this.dfuServiceController = dfuServiceInitiator.start(requireContext(), DfuService.class);
    }

    /* access modifiers changed from: private */
    public void showUpgradeProgressDialog() {
        UpgradeProgressDialog build = new UpgradeProgressDialog.Builder(getActivity()).setWidth(SizeUtils.dp2px(300.0f)).setHeight(-2).setTitle(StringUtils.getString(C1624R.string.updating)).setMaxProgress(100).setCurrentProgres(0).isShowFileSize(false).setOnCancelListener(new UpgradeProgressDialog.OnCancelListener() {
            public void onCancelClicked(UpgradeProgressDialog upgradeProgressDialog) {
                if (MyDeviceFragment.this.dfuServiceController != null) {
                    MyDeviceFragment.this.dfuServiceController.abort();
                }
                upgradeProgressDialog.dismiss();
            }
        }).build();
        this.upgradeProgressDialog = build;
        build.show();
    }

    @OnClick({3103})
    public void onUnbindClicked() {
        new CommonSelectDialog.Builder(getActivity()).setTitle(StringUtils.getString(C1624R.string.device_is_unbind)).setWidth((int) getResources().getDimension(C1624R.dimen.dp_250)).setHeight((int) getResources().getDimension(C1624R.dimen.dp_100)).setOkText(StringUtils.getString(C1624R.string.picker_ok)).setCancelText(StringUtils.getString(C1624R.string.picker_cancel)).setOnClickListener(new CommonSelectDialog.OnClickListener() {
            public void onClick(CommonSelectDialog commonSelectDialog, boolean z) {
                if (z) {
                    HealthMonitorService.getInstance().setUnbind(true);
                    BleManager.getInstance().disconnect();
                    EventBusUtil.sendEvent(new BleStatusEvent(106));
                    SpManager.setFirmwareVersion("");
                    SpManager.setDeviceAddress("");
                    SpManager.setDeviceName("");
                    ToastUtil.showToast((Context) MyDeviceFragment.this.requireActivity(), (int) C1624R.string.device_unbind_success);
                    MyDeviceFragment.this.pop();
                }
                commonSelectDialog.cancel();
            }
        }).build().show();
    }

    private void downloadFirmFile() {
        Log.e("MyDeviceFragment", "downloadFirmFile: " + this.downloadUrl);
        this.downloadDisposable = RestClient.download(this.downloadUrl, new ProgressResponseListener() {
            public void onResponseProgress(long j, long j2, boolean z) {
            }
        }).subscribeOn(Schedulers.m1081io()).observeOn(Schedulers.m1081io()).subscribe(new Consumer<ResponseBody>() {
            /* JADX WARNING: Removed duplicated region for block: B:37:0x009c A[SYNTHETIC, Splitter:B:37:0x009c] */
            /* JADX WARNING: Removed duplicated region for block: B:42:0x00a4 A[Catch:{ IOException -> 0x00a0 }] */
            /* JADX WARNING: Removed duplicated region for block: B:46:0x00af A[SYNTHETIC, Splitter:B:46:0x00af] */
            /* JADX WARNING: Removed duplicated region for block: B:51:0x00b7 A[Catch:{ IOException -> 0x00b3 }] */
            /* JADX WARNING: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void accept(okhttp3.ResponseBody r6) throws java.lang.Exception {
                /*
                    r5 = this;
                    r0 = 0
                    java.io.InputStream r6 = r6.byteStream()     // Catch:{ Exception -> 0x0094, all -> 0x0090 }
                    com.kl.healthmonitor.mine.MyDeviceFragment r1 = com.p020kl.healthmonitor.mine.MyDeviceFragment.this     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    java.lang.String r2 = r1.newVersionName     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    java.io.File r2 = com.p020kl.commonbase.utils.FileUtils.getNewFirmZipFile(r2)     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    java.io.File unused = r1.newFirmZipFile = r2     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    com.kl.healthmonitor.mine.MyDeviceFragment r1 = com.p020kl.healthmonitor.mine.MyDeviceFragment.this     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    java.io.File r1 = r1.newFirmZipFile     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    boolean r1 = r1.exists()     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    if (r1 != 0) goto L_0x0027
                    com.kl.healthmonitor.mine.MyDeviceFragment r1 = com.p020kl.healthmonitor.mine.MyDeviceFragment.this     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    java.io.File r1 = r1.newFirmZipFile     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    r1.createNewFile()     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                L_0x0027:
                    java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    com.kl.healthmonitor.mine.MyDeviceFragment r2 = com.p020kl.healthmonitor.mine.MyDeviceFragment.this     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    java.io.File r2 = r2.newFirmZipFile     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    r1.<init>(r2)     // Catch:{ Exception -> 0x008a, all -> 0x0084 }
                    r0 = 4096(0x1000, float:5.74E-42)
                    byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                L_0x0036:
                    int r2 = r6.read(r0)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    r3 = -1
                    if (r2 == r3) goto L_0x0042
                    r3 = 0
                    r1.write(r0, r3, r2)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    goto L_0x0036
                L_0x0042:
                    com.kl.healthmonitor.mine.MyDeviceFragment r0 = com.p020kl.healthmonitor.mine.MyDeviceFragment.this     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    java.lang.String r0 = r0.zipFileMd5     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    com.kl.healthmonitor.mine.MyDeviceFragment r2 = com.p020kl.healthmonitor.mine.MyDeviceFragment.this     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    java.io.File r2 = r2.newFirmZipFile     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    java.lang.String r2 = com.p020kl.commonbase.utils.FileUtils.getFileMd5(r2)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    boolean r0 = r0.equals(r2)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    if (r0 == 0) goto L_0x0063
                    com.kl.healthmonitor.mine.MyDeviceFragment r0 = com.p020kl.healthmonitor.mine.MyDeviceFragment.this     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    android.os.Handler r0 = r0.mHandler     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    r2 = 5
                    r0.sendEmptyMessage(r2)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    goto L_0x0071
                L_0x0063:
                    com.kl.healthmonitor.mine.MyDeviceFragment r0 = com.p020kl.healthmonitor.mine.MyDeviceFragment.this     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    androidx.fragment.app.FragmentActivity r0 = r0.requireActivity()     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    com.kl.healthmonitor.mine.MyDeviceFragment$7$1 r2 = new com.kl.healthmonitor.mine.MyDeviceFragment$7$1     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    r2.<init>()     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                    r0.runOnUiThread(r2)     // Catch:{ Exception -> 0x007f, all -> 0x007a }
                L_0x0071:
                    if (r6 == 0) goto L_0x0076
                    r6.close()     // Catch:{ IOException -> 0x00a0 }
                L_0x0076:
                    r1.close()     // Catch:{ IOException -> 0x00a0 }
                    goto L_0x00ab
                L_0x007a:
                    r0 = move-exception
                    r4 = r0
                    r0 = r6
                    r6 = r4
                    goto L_0x00ad
                L_0x007f:
                    r0 = move-exception
                    r4 = r0
                    r0 = r6
                    r6 = r4
                    goto L_0x0097
                L_0x0084:
                    r1 = move-exception
                    r4 = r0
                    r0 = r6
                    r6 = r1
                    r1 = r4
                    goto L_0x00ad
                L_0x008a:
                    r1 = move-exception
                    r4 = r0
                    r0 = r6
                    r6 = r1
                    r1 = r4
                    goto L_0x0097
                L_0x0090:
                    r1 = move-exception
                    r6 = r1
                    r1 = r0
                    goto L_0x00ad
                L_0x0094:
                    r1 = move-exception
                    r6 = r1
                    r1 = r0
                L_0x0097:
                    r6.printStackTrace()     // Catch:{ all -> 0x00ac }
                    if (r0 == 0) goto L_0x00a2
                    r0.close()     // Catch:{ IOException -> 0x00a0 }
                    goto L_0x00a2
                L_0x00a0:
                    r6 = move-exception
                    goto L_0x00a8
                L_0x00a2:
                    if (r1 == 0) goto L_0x00ab
                    r1.close()     // Catch:{ IOException -> 0x00a0 }
                    goto L_0x00ab
                L_0x00a8:
                    r6.printStackTrace()
                L_0x00ab:
                    return
                L_0x00ac:
                    r6 = move-exception
                L_0x00ad:
                    if (r0 == 0) goto L_0x00b5
                    r0.close()     // Catch:{ IOException -> 0x00b3 }
                    goto L_0x00b5
                L_0x00b3:
                    r0 = move-exception
                    goto L_0x00bb
                L_0x00b5:
                    if (r1 == 0) goto L_0x00be
                    r1.close()     // Catch:{ IOException -> 0x00b3 }
                    goto L_0x00be
                L_0x00bb:
                    r0.printStackTrace()
                L_0x00be:
                    throw r6
                */
                throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.mine.MyDeviceFragment.C18207.accept(okhttp3.ResponseBody):void");
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                MyDeviceFragment.this.showHint(th.getMessage());
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.netVersionDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.netVersionDisposable.dispose();
        }
        Disposable disposable2 = this.downloadDisposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.downloadDisposable.dispose();
        }
        DfuServiceListenerHelper.unregisterProgressListener(requireContext(), this);
        UpgradeDialog upgradeDialog2 = this.upgradeDialog;
        if (upgradeDialog2 != null && upgradeDialog2.isShowing()) {
            this.upgradeDialog.cancel();
        }
        UpgradeProgressDialog upgradeProgressDialog2 = this.upgradeProgressDialog;
        if (upgradeProgressDialog2 != null && upgradeProgressDialog2.isShowing()) {
            this.upgradeProgressDialog.cancel();
        }
    }

    public void onDeviceConnecting(String str) {
        Log.e("MyDeviceFragment", "onDeviceConnecting: ");
    }

    public void onDeviceConnected(String str) {
        Log.e("MyDeviceFragment", "onDeviceConnected: ");
    }

    public void onDfuProcessStarting(String str) {
        Log.e("MyDeviceFragment", "onDfuProcessStarting: ");
    }

    public void onDfuProcessStarted(String str) {
        Log.e("MyDeviceFragment", "onDfuProcessStarted: ");
        this.mHandler.sendEmptyMessage(6);
    }

    public void onEnablingDfuMode(String str) {
        Log.e("MyDeviceFragment", "onEnablingDfuMode: ");
    }

    public void onProgressChanged(String str, int i, float f, float f2, int i2, int i3) {
        Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 1;
        obtainMessage.arg1 = i;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void onFirmwareValidating(String str) {
        Log.e("MyDeviceFragment", "onFirmwareValidating: ");
    }

    public void onDeviceDisconnecting(String str) {
        Log.e("MyDeviceFragment", "onDeviceDisconnecting: ");
    }

    public void onDeviceDisconnected(String str) {
        Log.e("MyDeviceFragment", "onDeviceDisconnected: ");
    }

    public void onDfuCompleted(String str) {
        Log.e("MyDeviceFragment", "onDfuCompleted: ");
        this.mHandler.sendEmptyMessage(4);
    }

    public void onDfuAborted(String str) {
        Log.e("DeviceFragment", "onDfuAborted: " + str);
        this.mHandler.sendEmptyMessage(2);
    }

    public void onError(String str, int i, int i2, String str2) {
        Log.e("DeviceFragment", "onError: " + i + " errorType " + i2 + "  message " + str2);
        this.mHandler.sendEmptyMessage(3);
    }
}
