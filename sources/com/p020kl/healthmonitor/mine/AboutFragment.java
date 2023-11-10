package com.p020kl.healthmonitor.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.VersionInfo;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.interceptor.ProgressResponseListener;
import com.p020kl.commonbase.utils.AppUtils;
import com.p020kl.commonbase.utils.FileUtils;
import com.p020kl.commonbase.utils.LoggerUtil;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.views.UpgradeDialog;
import com.p020kl.commonbase.views.UpgradeProgressDialog;
import com.p020kl.healthmonitor.C1624R;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.ResponseBody;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.mine.AboutFragment */
public class AboutFragment extends BaseFragmentWhiteToolbar {
    private static final String BUNDLE_CUR_PROGRESS = "bundle_cur_progress";
    private static final String BUNDLE_MAX_PROGRESS = "bundle_max_progress";
    private static final int HANDLER_DOWNLOAD_OVER = 23;
    private static final int HANDLER_DOWNLOAD_PROGRESS = 24;
    private Disposable checkVersionDisposable;
    @BindView(3135)
    ConstraintLayout clCheckUpdate;
    private String curSoftVersionName;
    /* access modifiers changed from: private */
    public boolean isHasUpdate = false;
    /* access modifiers changed from: private */
    public File mApkFile;
    /* access modifiers changed from: private */
    public Disposable mDownloadDisposable;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 23) {
                if (AboutFragment.this.mUpgradeProgressDialog != null) {
                    AboutFragment.this.mUpgradeProgressDialog.cancel();
                    LoggerUtil.m112d("download_success");
                }
                AppUtils.installApk(AboutFragment.this.getActivity(), AboutFragment.this.mApkFile);
            } else if (i == 24) {
                Bundle data = message.getData();
                long j = data.getLong(AboutFragment.BUNDLE_CUR_PROGRESS);
                AboutFragment.this.mUpgradeProgressDialog.setMaxProgress((int) data.getLong(AboutFragment.BUNDLE_MAX_PROGRESS));
                AboutFragment.this.mUpgradeProgressDialog.setCurrentProgress((int) j);
                AboutFragment.this.mUpgradeProgressDialog.setTitle(AboutFragment.this.getString(C1624R.string.downloading_firmware));
            }
        }
    };
    UpgradeProgressDialog mUpgradeProgressDialog;
    /* access modifiers changed from: private */
    public VersionInfo mVersionInfo;
    /* access modifiers changed from: private */
    public int oldVersionCode = 0;
    private String oldVersionName = "0.0";
    /* access modifiers changed from: private */
    public List<String> stringLogList = new ArrayList();
    @BindView(3808)
    TextView tvSoftVersion;
    @BindView(3821)
    TextView tvUpdateInfo;
    private UpgradeDialog upgradeDialog;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public static AboutFragment newInstance() {
        Bundle bundle = new Bundle();
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.setArguments(bundle);
        return aboutFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_about);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.about);
    }

    public void onBindView(Bundle bundle, View view) {
        String versionName = AppUtils.getVersionName(requireContext());
        this.curSoftVersionName = versionName;
        this.tvSoftVersion.setText(versionName);
    }

    @OnClick({3136})
    public void onPrivacyPolicyClicked() {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(AppUtils.isZh(requireContext()) ? Constants.PRIVACY_POLICY_ZH_URL : Constants.PRIVACY_POLICY_EN_URL)));
    }

    @OnClick({3139})
    public void onUserAgreementClicked() {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(AppUtils.isZh(requireContext()) ? Constants.USER_AGREEMENT_ZH_URL : Constants.USER_AGREEMENT_EN_URL)));
    }

    /* access modifiers changed from: private */
    public void confirmUpdate() {
        if (this.mVersionInfo != null) {
            if (this.upgradeDialog == null) {
                UpgradeDialog upgradeDialog2 = new UpgradeDialog(getActivity(), this.stringLogList);
                this.upgradeDialog = upgradeDialog2;
                upgradeDialog2.setOnLaterListener(new UpgradeDialog.onLaterListener() {
                    public void onLaterClicked(UpgradeDialog upgradeDialog) {
                        upgradeDialog.dismiss();
                    }
                });
                this.upgradeDialog.setOnUpdateListener(new UpgradeDialog.onUpdateListener() {
                    public void onUpdateClicked(UpgradeDialog upgradeDialog) {
                        AboutFragment aboutFragment = AboutFragment.this;
                        aboutFragment.downloadApk(aboutFragment.mVersionInfo);
                        upgradeDialog.dismiss();
                    }
                });
            }
            this.upgradeDialog.show();
            LoggerUtil.m112d("upgradeDialog.show();");
            this.upgradeDialog.setTvTitle(getString(C1624R.string.update_software_content));
            String versionName = this.mVersionInfo.getVersionName();
            LoggerUtil.m112d("versionName = " + versionName);
            this.upgradeDialog.setVersion(versionName);
        }
    }

    private boolean checkLocalApk(VersionInfo versionInfo) {
        File newSoftFile = FileUtils.getNewSoftFile(getContext(), versionInfo.getVersionName());
        this.mApkFile = newSoftFile;
        return newSoftFile.exists() && versionInfo.getFileMd5().equals(FileUtils.getFileMd5(this.mApkFile));
    }

    /* access modifiers changed from: private */
    public void downloadApk(final VersionInfo versionInfo) {
        if (checkLocalApk(versionInfo)) {
            this.mHandler.sendEmptyMessage(23);
            return;
        }
        showDownloadProgressDialog();
        RestClient.download(versionInfo.getDownloadUrl(), new ProgressResponseListener() {
            public void onResponseProgress(long j, long j2, boolean z) {
                Message message = new Message();
                message.what = 24;
                Bundle bundle = new Bundle();
                bundle.putLong(AboutFragment.BUNDLE_CUR_PROGRESS, j);
                bundle.putLong(AboutFragment.BUNDLE_MAX_PROGRESS, j2);
                message.setData(bundle);
                AboutFragment.this.mHandler.sendMessage(message);
            }
        }).subscribeOn(Schedulers.m1081io()).observeOn(Schedulers.m1081io()).subscribe(new Observer<ResponseBody>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = AboutFragment.this.mDownloadDisposable = disposable;
            }

            /* JADX WARNING: Removed duplicated region for block: B:37:0x00a3 A[SYNTHETIC, Splitter:B:37:0x00a3] */
            /* JADX WARNING: Removed duplicated region for block: B:42:0x00ab A[Catch:{ IOException -> 0x00a7 }] */
            /* JADX WARNING: Removed duplicated region for block: B:46:0x00b6 A[SYNTHETIC, Splitter:B:46:0x00b6] */
            /* JADX WARNING: Removed duplicated region for block: B:51:0x00be A[Catch:{ IOException -> 0x00ba }] */
            /* JADX WARNING: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onNext(okhttp3.ResponseBody r6) {
                /*
                    r5 = this;
                    r0 = 0
                    java.io.InputStream r6 = r6.byteStream()     // Catch:{ Exception -> 0x009b, all -> 0x0097 }
                    com.kl.healthmonitor.mine.AboutFragment r1 = com.p020kl.healthmonitor.mine.AboutFragment.this     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    android.content.Context r2 = r1.getContext()     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    com.kl.commonbase.bean.VersionInfo r3 = r3     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    java.lang.String r3 = r3.getVersionName()     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    java.io.File r2 = com.p020kl.commonbase.utils.FileUtils.getNewSoftFile(r2, r3)     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    java.io.File unused = r1.mApkFile = r2     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    com.kl.healthmonitor.mine.AboutFragment r1 = com.p020kl.healthmonitor.mine.AboutFragment.this     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    java.io.File r1 = r1.mApkFile     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    boolean r1 = r1.exists()     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    if (r1 != 0) goto L_0x002d
                    com.kl.healthmonitor.mine.AboutFragment r1 = com.p020kl.healthmonitor.mine.AboutFragment.this     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    java.io.File r1 = r1.mApkFile     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    r1.createNewFile()     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                L_0x002d:
                    java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    com.kl.healthmonitor.mine.AboutFragment r2 = com.p020kl.healthmonitor.mine.AboutFragment.this     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    java.io.File r2 = r2.mApkFile     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    r1.<init>(r2)     // Catch:{ Exception -> 0x0091, all -> 0x008b }
                    r0 = 4096(0x1000, float:5.74E-42)
                    byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                L_0x003c:
                    int r2 = r6.read(r0)     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    r3 = -1
                    if (r2 == r3) goto L_0x0048
                    r3 = 0
                    r1.write(r0, r3, r2)     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    goto L_0x003c
                L_0x0048:
                    com.kl.commonbase.bean.VersionInfo r0 = r3     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    java.lang.String r0 = r0.getFileMd5()     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    com.kl.healthmonitor.mine.AboutFragment r2 = com.p020kl.healthmonitor.mine.AboutFragment.this     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    java.io.File r2 = r2.mApkFile     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    java.lang.String r2 = com.p020kl.commonbase.utils.FileUtils.getFileMd5(r2)     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    boolean r0 = r0.equals(r2)     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    if (r0 == 0) goto L_0x006a
                    com.kl.healthmonitor.mine.AboutFragment r0 = com.p020kl.healthmonitor.mine.AboutFragment.this     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    android.os.Handler r0 = r0.mHandler     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    r2 = 23
                    r0.sendEmptyMessage(r2)     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    goto L_0x0078
                L_0x006a:
                    com.kl.healthmonitor.mine.AboutFragment r0 = com.p020kl.healthmonitor.mine.AboutFragment.this     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    androidx.fragment.app.FragmentActivity r0 = r0.getActivity()     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    com.kl.healthmonitor.mine.AboutFragment$4$1 r2 = new com.kl.healthmonitor.mine.AboutFragment$4$1     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    r2.<init>()     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                    r0.runOnUiThread(r2)     // Catch:{ Exception -> 0x0086, all -> 0x0081 }
                L_0x0078:
                    if (r6 == 0) goto L_0x007d
                    r6.close()     // Catch:{ IOException -> 0x00a7 }
                L_0x007d:
                    r1.close()     // Catch:{ IOException -> 0x00a7 }
                    goto L_0x00b2
                L_0x0081:
                    r0 = move-exception
                    r4 = r0
                    r0 = r6
                    r6 = r4
                    goto L_0x00b4
                L_0x0086:
                    r0 = move-exception
                    r4 = r0
                    r0 = r6
                    r6 = r4
                    goto L_0x009e
                L_0x008b:
                    r1 = move-exception
                    r4 = r0
                    r0 = r6
                    r6 = r1
                    r1 = r4
                    goto L_0x00b4
                L_0x0091:
                    r1 = move-exception
                    r4 = r0
                    r0 = r6
                    r6 = r1
                    r1 = r4
                    goto L_0x009e
                L_0x0097:
                    r1 = move-exception
                    r6 = r1
                    r1 = r0
                    goto L_0x00b4
                L_0x009b:
                    r1 = move-exception
                    r6 = r1
                    r1 = r0
                L_0x009e:
                    r6.printStackTrace()     // Catch:{ all -> 0x00b3 }
                    if (r0 == 0) goto L_0x00a9
                    r0.close()     // Catch:{ IOException -> 0x00a7 }
                    goto L_0x00a9
                L_0x00a7:
                    r6 = move-exception
                    goto L_0x00af
                L_0x00a9:
                    if (r1 == 0) goto L_0x00b2
                    r1.close()     // Catch:{ IOException -> 0x00a7 }
                    goto L_0x00b2
                L_0x00af:
                    r6.printStackTrace()
                L_0x00b2:
                    return
                L_0x00b3:
                    r6 = move-exception
                L_0x00b4:
                    if (r0 == 0) goto L_0x00bc
                    r0.close()     // Catch:{ IOException -> 0x00ba }
                    goto L_0x00bc
                L_0x00ba:
                    r0 = move-exception
                    goto L_0x00c2
                L_0x00bc:
                    if (r1 == 0) goto L_0x00c5
                    r1.close()     // Catch:{ IOException -> 0x00ba }
                    goto L_0x00c5
                L_0x00c2:
                    r0.printStackTrace()
                L_0x00c5:
                    throw r6
                */
                throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.mine.AboutFragment.C17794.onNext(okhttp3.ResponseBody):void");
            }

            public void onError(Throwable th) {
                if (AboutFragment.this.mUpgradeProgressDialog != null) {
                    AboutFragment.this.mUpgradeProgressDialog.cancel();
                }
                AboutFragment.this.showHint(th.getMessage());
                if (AboutFragment.this.mApkFile != null && AboutFragment.this.mApkFile.exists()) {
                    AboutFragment.this.mApkFile.delete();
                }
            }
        });
    }

    private void requestNewVersion() {
        this.checkVersionDisposable = RestClient.getSoftwareVersion("release").subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<VersionInfo>>() {
            public void accept(ResponseResult<VersionInfo> responseResult) throws Exception {
                if (responseResult.getStatus() == 200) {
                    VersionInfo unused = AboutFragment.this.mVersionInfo = responseResult.getData();
                    int versionCode = AboutFragment.this.mVersionInfo.getVersionCode();
                    LoggerUtil.m112d("lastVersion = " + versionCode);
                    if (versionCode > AboutFragment.this.oldVersionCode) {
                        boolean unused2 = AboutFragment.this.isHasUpdate = true;
                        List unused3 = AboutFragment.this.stringLogList = Arrays.asList(AboutFragment.this.mVersionInfo.getUpdateLog().split(","));
                        AboutFragment.this.tvUpdateInfo.setText(C1624R.string.find_new_version);
                        AboutFragment.this.confirmUpdate();
                    }
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
            }
        });
    }

    private void showDownloadProgressDialog() {
        UpgradeProgressDialog build = new UpgradeProgressDialog.Builder(getActivity()).setWidth(SizeUtils.dp2px(300.0f)).setHeight(-2).setTitle(StringUtils.getString(C1624R.string.updating)).isShowFileSize(true).setOnCancelListener(new UpgradeProgressDialog.OnCancelListener() {
            public void onCancelClicked(UpgradeProgressDialog upgradeProgressDialog) {
                if (AboutFragment.this.mDownloadDisposable != null && !AboutFragment.this.mDownloadDisposable.isDisposed()) {
                    AboutFragment.this.mDownloadDisposable.dispose();
                }
                if (AboutFragment.this.mApkFile != null && AboutFragment.this.mApkFile.exists()) {
                    AboutFragment.this.mApkFile.delete();
                }
                upgradeProgressDialog.dismiss();
            }
        }).build();
        this.mUpgradeProgressDialog = build;
        build.show();
    }

    private void checkLocalApkVersion() {
        this.oldVersionCode = AppUtils.getVersionCode(requireContext());
        this.oldVersionName = AppUtils.getVersionName(requireContext());
        LoggerUtil.m112d("versionCode = " + this.oldVersionCode + " versionName : " + this.oldVersionName);
        if (StringUtils.isEmpty(this.oldVersionName)) {
            this.oldVersionName = "unknown";
        }
        this.tvSoftVersion.setText(this.oldVersionName);
    }

    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.mDownloadDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mDownloadDisposable.dispose();
        }
        Disposable disposable2 = this.checkVersionDisposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.checkVersionDisposable.dispose();
        }
    }
}
