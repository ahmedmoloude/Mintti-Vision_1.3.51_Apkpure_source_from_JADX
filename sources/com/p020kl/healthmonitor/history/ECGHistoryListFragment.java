package com.p020kl.healthmonitor.history;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.ECGTableManager;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.adapter.HistoryEcgItemAdapter;
import com.p020kl.healthmonitor.views.NormalDataView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import p028io.reactivex.Observable;
import p028io.reactivex.ObservableEmitter;
import p028io.reactivex.ObservableOnSubscribe;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.history.ECGHistoryListFragment */
public class ECGHistoryListFragment extends BaseAllHistoryFragment<ECGEntity> implements HistoryEcgItemAdapter.OnItemClickListener {
    /* access modifiers changed from: private */
    public List<ECGEntity> allDatas = new ArrayList();
    /* access modifiers changed from: private */
    public HistoryEcgItemAdapter historyEcgItemAdapter;
    protected boolean isSelect;
    @BindView(3384)
    LinearLayout llBottomBar;
    /* access modifiers changed from: private */
    public List<ECGEntity> localDatas = new ArrayList();
    @BindView(3502)
    NormalDataView normalData;
    /* access modifiers changed from: private */
    public Disposable queryDisposable;
    @BindView(3575)
    RecyclerView rvHistory;
    @BindView(3851)
    View vBottom;

    private String getMeasureType() {
        return Constants.ECG;
    }

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public /* bridge */ /* synthetic */ void onBindView(Bundle bundle, View view) {
        super.onBindView(bundle, view);
    }

    public static ECGHistoryListFragment newInstance(int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putInt("year", i);
        bundle.putInt("month", i2);
        ECGHistoryListFragment eCGHistoryListFragment = new ECGHistoryListFragment();
        eCGHistoryListFragment.setArguments(bundle);
        return eCGHistoryListFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_bthistory_list);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.ecg);
    }

    public void onLazyInitView(Bundle bundle) {
        super.onLazyInitView(bundle);
        this.rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        HistoryEcgItemAdapter historyEcgItemAdapter2 = new HistoryEcgItemAdapter(getContext(), this.allDatas, Constants.ECG_LIST);
        this.historyEcgItemAdapter = historyEcgItemAdapter2;
        this.rvHistory.setAdapter(historyEcgItemAdapter2);
        this.historyEcgItemAdapter.setOnItemClickListener(this);
        initData();
    }

    private void shareFile(File file) {
        if (Build.VERSION.SDK_INT >= 24) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/*");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
        startActivity(Intent.createChooser(intent, "share file with"));
    }

    /* access modifiers changed from: private */
    public void showEmptyView() {
        this.normalData.setVisibility(0);
        this.rvHistory.setVisibility(8);
        this.vBottom.setVisibility(8);
        this.llBottomBar.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void showDataView() {
        this.normalData.setVisibility(8);
        this.rvHistory.setVisibility(0);
        this.vBottom.setVisibility(8);
        this.llBottomBar.setVisibility(8);
    }

    private void initData() {
        showProgressDialog(StringUtils.getString(C1624R.string.loading_data), false);
        Observable create = Observable.create(new ObservableOnSubscribe<Boolean>() {
            public void subscribe(ObservableEmitter<Boolean> observableEmitter) throws Exception {
                List<ECGEntity> queryByMonth = ECGTableManager.queryByMonth(SpManager.getMemberId(), ECGHistoryListFragment.this.getArguments().getInt("year"), ECGHistoryListFragment.this.getArguments().getInt("month"));
                Log.d("当前年月数据", queryByMonth.size() + "" + ECGHistoryListFragment.this.getArguments().getInt("year") + ":" + ECGHistoryListFragment.this.getArguments().getInt("month"));
                ECGHistoryListFragment.this.localDatas.addAll(queryByMonth);
                observableEmitter.onNext(true);
                observableEmitter.onComplete();
            }
        });
        create.subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() {
            public void onNext(Boolean bool) {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = ECGHistoryListFragment.this.queryDisposable = disposable;
            }

            public void onError(Throwable th) {
                ECGHistoryListFragment.this.disProgressDialog();
            }

            public void onComplete() {
                if (ECGHistoryListFragment.this.localDatas.isEmpty()) {
                    ECGHistoryListFragment.this.showEmptyView();
                } else {
                    ECGHistoryListFragment.this.showDataView();
                    ECGHistoryListFragment.this.allDatas.addAll(ECGHistoryListFragment.this.localDatas);
                    for (ECGEntity checked : ECGHistoryListFragment.this.allDatas) {
                        checked.setChecked(false);
                    }
                    ECGHistoryListFragment eCGHistoryListFragment = ECGHistoryListFragment.this;
                    eCGHistoryListFragment.collectionList(eCGHistoryListFragment.allDatas);
                    ECGHistoryListFragment.this.historyEcgItemAdapter.notifyDataSetChanged();
                }
                ECGHistoryListFragment.this.disProgressDialog();
            }
        });
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 1) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.no_storage_permissions));
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.queryDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.queryDisposable.dispose();
        }
    }

    public void onItemClick(int i) {
        Log.d("点击事件", i + ":");
        start(ECGDetailsFragment.newInstance(this.allDatas.get(i)));
    }
}
