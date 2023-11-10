package com.p020kl.healthmonitor.measure;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.mintti.visionsdk.ble.BleDevice;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.callback.IBleScanCallback;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.event.BatteryEvent;
import com.p020kl.commonbase.event.BleStatusEvent;
import com.p020kl.commonbase.utils.AppUtils;
import com.p020kl.commonbase.utils.GpsUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.TimerUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.views.CommonSelectDialog;
import com.p020kl.commonbase.views.ConnectWayDialogFragmnet;
import com.p020kl.commonbase.views.FunctionButton;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.adapter.BleDeviceAdapter;
import com.p020kl.healthmonitor.guide.measure.MeasureGuideFragment;
import com.p020kl.healthmonitor.measure.rothmanindex.RothmanIndexFragment;
import com.p020kl.healthmonitor.navigation.NavigationFragment;
import com.p020kl.healthmonitor.views.BleDeviceDialog;
import com.p020kl.healthmonitor.views.DisclaimerDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p028io.reactivex.Observable;
import p028io.reactivex.ObservableEmitter;
import p028io.reactivex.ObservableOnSubscribe;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.measure.MeasureFragment */
public class MeasureFragment extends BaseMeasureFragment implements BleDeviceDialog.ICancelClickListener, BaseQuickAdapter.OnItemClickListener {
    /* access modifiers changed from: private */
    public int againCount = 0;
    private final IBleScanCallback bleScanCallback = new IBleScanCallback() {
        public void onScanResult(BleDevice bleDevice) {
            if (!TextUtils.isEmpty(bleDevice.getName())) {
                if (bleDevice.getName().contains("HC") || bleDevice.getName().contains("Mintti-Vision")) {
                    Log.e("MeasureFragment", "onScanResult: " + bleDevice.getName());
                    if (TextUtils.isEmpty(MeasureFragment.this.macAddress)) {
                        boolean z = false;
                        for (BleDevice bleDevice2 : MeasureFragment.this.mDeviceSortList) {
                            if (bleDevice2.getMac().equals(bleDevice.getMac())) {
                                z = true;
                                bleDevice2.setRssi(bleDevice.getRssi());
                            }
                        }
                        if (!z) {
                            MeasureFragment.this.mDeviceSortList.add(bleDevice);
                        }
                        if (!MeasureFragment.this.mDeviceSortList.isEmpty()) {
                            MeasureFragment.this.mBleDeviceDialog.show();
                            MeasureFragment.this.mBleDeviceDialog.notifyChange();
                            MeasureFragment.this.stopTimer();
                        }
                    } else if (bleDevice.getMac().equals(MeasureFragment.this.macAddress)) {
                        MeasureFragment.this.stopTimer();
                        MeasureFragment.this.stopScan();
                        Log.e("MeasureFragment", "onScanResult: " + bleDevice.toString());
                        MeasureFragment.this.mHealthMonitorService.connect(bleDevice);
                    }
                }
            }
        }

        public void onScanFailed(int i) {
            Log.e("scanFailed", "扫描失败");
        }
    };
    private ConnectWayDialogFragmnet dialogFragmnet;
    private DisclaimerDialog disclaimerDialog;
    /* access modifiers changed from: private */
    public String[] dotText = {" . ", " . . ", " . . ."};
    @BindView(3249)
    FunctionButton fbtEcg;
    @BindView(3251)
    FunctionButton fbtTemperature;
    @BindView(3273)
    Group groupBattery;
    private boolean isConnecting = false;
    private boolean isScanning = false;
    private boolean isShowBleDisconnect = false;
    private boolean isShowCheckDialog = true;
    private long itemClickTime;
    @BindView(3333)
    ImageView ivBattery;
    /* access modifiers changed from: private */
    public BleDeviceDialog mBleDeviceDialog;
    private BleDeviceAdapter mDeviceAdapter;
    /* access modifiers changed from: private */
    public List<BleDevice> mDeviceSortList = new ArrayList();
    /* access modifiers changed from: private */
    public String macAddress = "";
    private Disposable permissionDisposable;
    /* access modifiers changed from: private */
    public RxPermissions rxPermissions;
    private CommonSelectDialog selectDialog;
    /* access modifiers changed from: private */
    public Disposable timerDisposable;
    @BindView(3723)
    TextView tvBattery;
    @BindView(3736)
    TextView tvConnect;
    @BindView(3776)
    TextView tvLoadingDot;
    private ValueAnimator valueAnimator;

    /* access modifiers changed from: protected */
    public boolean isEventBusRegister() {
        return true;
    }

    static /* synthetic */ int access$608(MeasureFragment measureFragment) {
        int i = measureFragment.againCount;
        measureFragment.againCount = i + 1;
        return i;
    }

    public static MeasureFragment newInstance() {
        Bundle bundle = new Bundle();
        MeasureFragment measureFragment = new MeasureFragment();
        measureFragment.setArguments(bundle);
        return measureFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_measure);
    }

    /* access modifiers changed from: protected */
    public void showGuideFragment() {
        if (SpManager.getConnectGuideIsShow()) {
            this.guideFragment = new MeasureGuideFragment();
            this.guideFragment.show(getChildFragmentManager(), "measure_guide");
            SpManager.setConnectGuideIsShow(false);
        }
    }

    public void onBindView(Bundle bundle, View view) {
        super.onBindView(bundle, view);
        this.rxPermissions = new RxPermissions((Fragment) this);
        initBleDeviceDialog();
    }

    public void onResume() {
        super.onResume();
    }

    private void initBleDeviceDialog() {
        BleDeviceAdapter bleDeviceAdapter = new BleDeviceAdapter(C1624R.layout.item_device, this.mDeviceSortList);
        this.mDeviceAdapter = bleDeviceAdapter;
        bleDeviceAdapter.setOnItemClickListener(this);
        BleDeviceDialog bleDeviceDialog = new BleDeviceDialog(requireContext(), this.mDeviceAdapter);
        this.mBleDeviceDialog = bleDeviceDialog;
        bleDeviceDialog.setCancelClickListener(this);
    }

    @OnClick({2131296480})
    public void onCreatePdf() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
            }
        }).subscribeOn(Schedulers.m1081io()).subscribeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
            public void onComplete() {
            }

            public void onError(Throwable th) {
            }

            public void onNext(Object obj) {
            }

            public void onSubscribe(Disposable disposable) {
                Log.e("hehe", "onSubscribe");
            }
        });
    }

    @OnClick({2131297108})
    public void onMedicalClick() {
        DisclaimerDialog disclaimerDialog2 = new DisclaimerDialog(requireContext(), false);
        this.disclaimerDialog = disclaimerDialog2;
        disclaimerDialog2.show();
    }

    @OnClick({3736})
    public void onConnectClicked() {
        Log.d("hyhy", "点击链接按钮");
        if (!this.tvConnect.getText().equals(getString(C1624R.string.connected))) {
            if (Build.VERSION.SDK_INT >= 31) {
                this.rxPermissions.requestEachCombined("android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT").subscribe(new Consumer<Permission>() {
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            MeasureFragment.this.handleConnect();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            MeasureFragment.this.showRequestPermissionDialog("android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT");
                        } else {
                            ToastUtil.showShortToast(MeasureFragment.this.getString(C1624R.string.permission_denied));
                        }
                    }
                });
            } else if (Build.VERSION.SDK_INT >= 23) {
                this.rxPermissions.requestEachCombined("android.permission.ACCESS_FINE_LOCATION").subscribe(new Consumer<Permission>() {
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            MeasureFragment.this.handleConnect();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            MeasureFragment.this.showRequestPermissionDialog("android.permission.ACCESS_FINE_LOCATION");
                        } else {
                            ToastUtil.showShortToast(MeasureFragment.this.getString(C1624R.string.permission_denied));
                        }
                    }
                });
            } else {
                handleConnect();
            }
        }
    }

    /* access modifiers changed from: private */
    public void showRequestPermissionDialog(final String... strArr) {
        new MessageDialog((int) C1624R.string.selector_hint, strArr.length == 2 ? C1624R.string.need_ble_permission : C1624R.string.need_location_permission, (int) C1624R.string.allow, (int) C1624R.string.deny).setButtonOrientation(0).setOkButtonClickListener(new OnDialogButtonClickListener<MessageDialog>() {
            public boolean onClick(MessageDialog messageDialog, View view) {
                MeasureFragment measureFragment = MeasureFragment.this;
                measureFragment.requestPermission(measureFragment.rxPermissions, strArr);
                return false;
            }
        }).setCancelButtonClickListener(new OnDialogButtonClickListener<MessageDialog>() {
            public boolean onClick(MessageDialog messageDialog, View view) {
                return false;
            }
        }).show();
    }

    private void showOpenPermissionDialog() {
        new MessageDialog((int) C1624R.string.permission_setting, (int) C1624R.string.manual_grant_location_permission, (int) C1624R.string.selector_confirm, (int) C1624R.string.selector_cancel).setButtonOrientation(0).setOkButtonClickListener(new OnDialogButtonClickListener<MessageDialog>() {
            public boolean onClick(MessageDialog messageDialog, View view) {
                AppUtils.startAppDetail(MeasureFragment.this.requireContext());
                return false;
            }
        }).setCancelButtonClickListener(new OnDialogButtonClickListener<MessageDialog>() {
            public boolean onClick(MessageDialog messageDialog, View view) {
                return false;
            }
        }).show();
    }

    /* access modifiers changed from: private */
    public void requestPermission(RxPermissions rxPermissions2, String... strArr) {
        this.permissionDisposable = rxPermissions2.request(strArr).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            public void accept(Boolean bool) throws Exception {
                if (bool.booleanValue()) {
                    MeasureFragment.this.handleConnect();
                } else {
                    ToastUtil.showShortToast(MeasureFragment.this.getString(C1624R.string.permission_denied));
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void handleConnect() {
        if (this.mHealthMonitorService == null) {
            ToastUtil.showToast(requireContext(), (int) C1624R.string.service_loading);
        } else if (!requireActivity().getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            ToastUtil.showToast(requireContext(), (int) C1624R.string.ble_not_support);
        } else if (!GpsUtils.isGpsEnable(getActivity())) {
            showOpenGpsDialog();
        } else if (BleManager.getInstance().isBluetoothEnable()) {
            this.macAddress = SpManager.getDeviceAddress();
            Log.e("MeasureFragment", "accept: " + this.macAddress);
            if (TextUtils.isEmpty(this.macAddress)) {
                this.macAddress = "";
                scan();
                startBleDelayed();
                return;
            }
            connect();
        } else {
            openBluetooth();
        }
    }

    private void showOpenGpsDialog() {
        if (this.selectDialog == null) {
            this.selectDialog = new CommonSelectDialog.Builder(getContext()).setTitle(StringUtils.getString(C1624R.string.local_not_open)).setOnClickListener(new CommonSelectDialog.OnClickListener() {
                public void onClick(CommonSelectDialog commonSelectDialog, boolean z) {
                    if (z) {
                        MeasureFragment.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 15);
                    }
                    commonSelectDialog.dismiss();
                }
            }).build();
        }
        this.selectDialog.show();
    }

    private void handleBleStatus(int i) {
        switch (i) {
            case 100:
                openBluetooth();
                return;
            case 101:
                this.macAddress = SpManager.getDeviceAddress();
                Log.d("hyhy", "蓝牙打开未连接");
                if (TextUtils.isEmpty(this.macAddress)) {
                    this.macAddress = "";
                    scan();
                    startBleDelayed();
                    return;
                }
                connect();
                return;
            case 102:
                Log.d("hyhy", "正在连接设备");
                return;
            case 103:
                Log.d("hyhy", "连接上设备");
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void scan() {
        this.isShowCheckDialog = false;
        loadAnimal();
        this.mDeviceSortList.clear();
        this.isScanning = true;
        this.mHealthMonitorService.scan(true, this.bleScanCallback);
    }

    /* access modifiers changed from: private */
    public void stopScan() {
        this.isScanning = false;
        this.mHealthMonitorService.scan(false, (IBleScanCallback) null);
    }

    /* access modifiers changed from: private */
    public void connect() {
        this.isShowCheckDialog = true;
        loadAnimal();
        this.mDeviceSortList.clear();
        this.mHealthMonitorService.scan(true, this.bleScanCallback);
        this.isScanning = true;
        startBleDelayed();
    }

    private void openBluetooth() {
        startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 11);
    }

    /* access modifiers changed from: private */
    public void startBleDelayed() {
        TimerUtils.delayed(10, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            public void onError(Throwable th) {
            }

            public void onNext(Long l) {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = MeasureFragment.this.timerDisposable = disposable;
            }

            public void onComplete() {
                MeasureFragment.this.connectFailed();
            }
        });
    }

    /* access modifiers changed from: private */
    public void connectFailed() {
        stopScan();
        stopLoadAnimation(false);
        if (!this.isShowCheckDialog) {
            showHint(Integer.valueOf(C1624R.string.ble_connect_timeout));
            return;
        }
        if (this.dialogFragmnet == null) {
            this.dialogFragmnet = new ConnectWayDialogFragmnet();
        }
        this.dialogFragmnet.show(getFragmentManager(), "dialogfragment");
        this.dialogFragmnet.setOnDialogItemClickListener(new ConnectWayDialogFragmnet.OnDialogItemClickListener() {
            public void onAgain() {
                if (MeasureFragment.this.againCount > 1) {
                    MeasureFragment.this.showHint(Integer.valueOf(C1624R.string.ble_connect_timeout));
                    int unused = MeasureFragment.this.againCount = 0;
                    return;
                }
                MeasureFragment.access$608(MeasureFragment.this);
                MeasureFragment.this.connect();
                Log.d("dialog", "重试");
            }

            public void onHand() {
                String unused = MeasureFragment.this.macAddress = "";
                MeasureFragment.this.scan();
                MeasureFragment.this.startBleDelayed();
                Log.d("dialog", "手动连接");
            }
        });
    }

    /* access modifiers changed from: private */
    public void stopTimer() {
        Disposable disposable = this.timerDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.timerDisposable.dispose();
        }
    }

    private void loadAnimal() {
        this.tvConnect.setText(C1624R.string.connecting);
        this.tvLoadingDot.setVisibility(0);
        if (this.valueAnimator == null) {
            ValueAnimator duration = ValueAnimator.ofInt(new int[]{0, 3}).setDuration(1000);
            this.valueAnimator = duration;
            duration.setRepeatCount(-1);
            this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    MeasureFragment.this.tvLoadingDot.setText(MeasureFragment.this.dotText[((Integer) valueAnimator.getAnimatedValue()).intValue() % MeasureFragment.this.dotText.length]);
                }
            });
        }
        this.valueAnimator.start();
    }

    private void stopLoadAnimation(boolean z) {
        ValueAnimator valueAnimator2 = this.valueAnimator;
        if (valueAnimator2 != null && valueAnimator2.isStarted()) {
            this.valueAnimator.cancel();
        }
        TextView textView = this.tvConnect;
        if (textView != null) {
            if (z) {
                textView.setText(C1624R.string.connected);
                Log.e("MeasureFragment", "stopLoadAnimation: connected");
            } else {
                textView.setText(C1624R.string.click_connect);
                Log.e("MeasureFragment", "stopLoadAnimation: click_connect");
            }
        }
        TextView textView2 = this.tvLoadingDot;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
    }

    @OnClick({3249, 3248, 3247, 3246, 3251, 3250})
    public void onFbtClicked(View view) {
        if (!this.mHealthMonitorService.isConnected() && view.getId() != C1624R.C1628id.fbt_rothman) {
            ToastUtil.showToast(getContext(), (int) C1624R.string.connect_firist);
        } else if (this.itemClickTime == 0 || System.currentTimeMillis() - this.itemClickTime > 400) {
            this.itemClickTime = System.currentTimeMillis();
            switch (view.getId()) {
                case C1624R.C1628id.fbt_blood_glucose:
                    ((NavigationFragment) getParentFragment()).start(BGMeasureFragment.newInstance());
                    return;
                case C1624R.C1628id.fbt_blood_oxygen:
                    ((NavigationFragment) getParentFragment()).start(SPO2HMeasureFragment.newInstance());
                    return;
                case C1624R.C1628id.fbt_blood_pressure:
                    ((NavigationFragment) getParentFragment()).start(BPMeasureFragment.newInstance());
                    return;
                case C1624R.C1628id.fbt_ecg:
                    ((NavigationFragment) getParentFragment()).start(ECGMeasureFragment.newInstance());
                    return;
                case C1624R.C1628id.fbt_rothman:
                    ((NavigationFragment) getParentFragment()).start(RothmanIndexFragment.newInstance());
                    return;
                case C1624R.C1628id.fbt_temp:
                    ((NavigationFragment) getParentFragment()).start(BTMeasureFragment.newInstance());
                    return;
                default:
                    return;
            }
        } else {
            this.itemClickTime = System.currentTimeMillis();
            Log.e("huhu", "onFbtClicked: 点击过快");
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        Disposable disposable = this.permissionDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.permissionDisposable.dispose();
        }
        if (this.mHealthMonitorService != null) {
            stopLoadAnimation(this.mHealthMonitorService.isConnected());
        }
        stopTimer();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onCancelClick() {
        this.mHealthMonitorService.scan(false, (IBleScanCallback) null);
        this.isScanning = false;
        this.mBleDeviceDialog.cancel();
        stopLoadAnimation(this.mHealthMonitorService.isConnected());
    }

    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.isScanning = false;
        this.mHealthMonitorService.scan(false, (IBleScanCallback) null);
        this.mHealthMonitorService.connect(this.mDeviceSortList.get(i));
        startBleDelayed();
        this.mBleDeviceDialog.cancel();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BleStatusEvent(BleStatusEvent bleStatusEvent) {
        int intValue = ((Integer) bleStatusEvent.getData()).intValue();
        Integer valueOf = Integer.valueOf(C1624R.string.ble_disconnect);
        switch (intValue) {
            case 100:
                Log.d("measureFragment", "蓝牙关闭");
                stopTimer();
                stopLoadAnimation(false);
                this.groupBattery.setVisibility(8);
                if (this.isShowBleDisconnect) {
                    showHint(valueOf);
                }
                this.isShowBleDisconnect = false;
                return;
            case 101:
                stopTimer();
                Log.d("measureFragment", "蓝牙打开未连接");
                stopLoadAnimation(false);
                if (this.isConnecting) {
                    this.isConnecting = false;
                    showHint(Integer.valueOf(C1624R.string.ble_connect_timeout));
                } else if (this.isShowBleDisconnect) {
                    this.tvConnect.setText(C1624R.string.click_connect);
                    showHint(valueOf);
                }
                this.isShowBleDisconnect = false;
                this.groupBattery.setVisibility(8);
                return;
            case 102:
                Log.d("measureFragment", "正在连接");
                this.isConnecting = true;
                return;
            case 103:
                Log.d("measureFragment", "已连接");
                stopTimer();
                stopLoadAnimation(true);
                this.isConnecting = false;
                this.isShowBleDisconnect = true;
                if (this.mHealthMonitorService != null) {
                    SpManager.setDeviceAddress(BleManager.getInstance().getBluetoothDevice().getAddress());
                    SpManager.setDeviceName(BleManager.getInstance().getBluetoothDevice().getName());
                    return;
                }
                return;
            case 106:
                Log.d("measureFragment", "解除绑定");
                stopTimer();
                stopLoadAnimation(false);
                this.groupBattery.setVisibility(8);
                return;
            case 107:
                Log.d("MeasureFragment", "连接失败");
                stopTimer();
                connectFailed();
                return;
            default:
                return;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBatteryEvent(BatteryEvent batteryEvent) {
        this.groupBattery.setVisibility(0);
        if (batteryEvent.isCharging()) {
            this.ivBattery.setImageResource(C1624R.C1627drawable.battery_charging);
            this.tvBattery.setText(C1624R.string.charging);
            return;
        }
        int batteryValue = batteryEvent.getBatteryValue();
        this.ivBattery.setImageResource(C1624R.C1627drawable.battery_level_list);
        this.ivBattery.setImageLevel(batteryValue);
        TextView textView = this.tvBattery;
        textView.setText(batteryValue + "%");
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 11 && i2 == -1) {
            onConnectClicked();
        } else if (i == 15 && i2 == -1 && GpsUtils.isGpsEnable(getActivity())) {
            onConnectClicked();
        }
    }
}
