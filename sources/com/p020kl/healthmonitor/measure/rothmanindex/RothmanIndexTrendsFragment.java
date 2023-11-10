package com.p020kl.healthmonitor.measure.rothmanindex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.rothmanindex.AuthenticatedSession;
import com.p020kl.commonbase.bean.rothmanindex.TrendBean;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.JWTUtils;
import com.p020kl.commonbase.utils.LoggerUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.measure.rothmanindex.LineChartLayout;
import java.util.ArrayList;
import java.util.List;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.RothmanIndexTrendsFragment */
public class RothmanIndexTrendsFragment extends BaseFragmentWhiteToolbar implements LineChartLayout.OnAxisClickListener, BaseQuickAdapter.OnItemClickListener {
    private List<AxisData> axisDataList = new ArrayList();
    @BindView(3168)
    CardView cardView;
    /* access modifiers changed from: private */
    public String consumerToken;
    @BindView(3378)
    LineChartLayout lineChartLayout;
    /* access modifiers changed from: private */
    public TrendBean mTrendBean;
    @BindView(3584)
    RecyclerView recyclerView;
    private RothmanIndexTrendAdapter trendAdapter;
    private List<TrendBean.WeightedScoresBean> weightedScores = new ArrayList();

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public static RothmanIndexTrendsFragment newInstance() {
        Bundle bundle = new Bundle();
        RothmanIndexTrendsFragment rothmanIndexTrendsFragment = new RothmanIndexTrendsFragment();
        rothmanIndexTrendsFragment.setArguments(bundle);
        return rothmanIndexTrendsFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_trends);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.rothman_index);
    }

    public void onBindView(Bundle bundle, View view) {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RothmanIndexTrendAdapter rothmanIndexTrendAdapter = new RothmanIndexTrendAdapter(this.weightedScores);
        this.trendAdapter = rothmanIndexTrendAdapter;
        this.recyclerView.setAdapter(rothmanIndexTrendAdapter);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        this.trendAdapter.setEmptyView(C1624R.layout.empty_view_trends, this.recyclerView);
        this.trendAdapter.setOnItemClickListener(this);
        this.lineChartLayout.setMaxYValue(100.0f);
        this.lineChartLayout.setMinYValue(0.0f);
        this.lineChartLayout.setOnAxisClickListener(this);
        TrendBean rothmanIndex = SpManager.getRothmanIndex();
        this.mTrendBean = rothmanIndex;
        if (rothmanIndex != null) {
            showTrend(rothmanIndex, true);
        }
        getToken();
    }

    private void getToken() {
        if (this.mTrendBean == null) {
            showProgressDialog(StringUtils.getString(C1624R.string.loading_data), false);
        }
        RestClient.getSessionsToken(Constants.CLIENT_ID, JWTUtils.getJwt(BaseApplication.getInstance().getRothmanIndexUuid())).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Response<AuthenticatedSession>>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(Response<AuthenticatedSession> response) {
                if (response.isSuccessful()) {
                    String unused = RothmanIndexTrendsFragment.this.consumerToken = response.body().getAccess_token();
                    RothmanIndexTrendsFragment.this.getTrends();
                    return;
                }
                RothmanIndexTrendsFragment.this.disProgressDialog();
            }

            public void onError(Throwable th) {
                Log.e("rendsFragment", "onError: " + th.toString());
                RothmanIndexTrendsFragment.this.disProgressDialog();
                if (th.getMessage().equals("Canceled")) {
                    RothmanIndexTrendsFragment rothmanIndexTrendsFragment = RothmanIndexTrendsFragment.this;
                    rothmanIndexTrendsFragment.showHint(StringUtils.getString(C1624R.string.failed_load_data) + "\n" + RothmanIndexTrendsFragment.this.getString(C1624R.string.timeout));
                    return;
                }
                RothmanIndexTrendsFragment rothmanIndexTrendsFragment2 = RothmanIndexTrendsFragment.this;
                rothmanIndexTrendsFragment2.showHint(StringUtils.getString(C1624R.string.failed_load_data) + "\n" + th.getMessage());
            }
        });
    }

    /* access modifiers changed from: private */
    public void getTrends() {
        RestClient.trendsIndividual(this.consumerToken, BaseApplication.getInstance().getRothmanIndexUuid()).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Response<TrendBean>>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(Response<TrendBean> response) {
                Log.e("TrendsFragment", "onNext: " + response.toString());
                if (response.isSuccessful()) {
                    TrendBean body = response.body();
                    if (body == null) {
                        RothmanIndexTrendsFragment.this.disProgressDialog();
                        return;
                    }
                    SpManager.setRothmanIndex(body);
                    RothmanIndexTrendsFragment rothmanIndexTrendsFragment = RothmanIndexTrendsFragment.this;
                    rothmanIndexTrendsFragment.showTrend(body, rothmanIndexTrendsFragment.mTrendBean == null);
                    RothmanIndexTrendsFragment.this.disProgressDialog();
                    return;
                }
                RothmanIndexTrendsFragment.this.disProgressDialog();
            }

            public void onError(Throwable th) {
                Log.e("rendsFragment", "onError: " + th.toString());
                RothmanIndexTrendsFragment.this.disProgressDialog();
                if (th.getMessage().equals("Canceled")) {
                    RothmanIndexTrendsFragment rothmanIndexTrendsFragment = RothmanIndexTrendsFragment.this;
                    rothmanIndexTrendsFragment.showHint(StringUtils.getString(C1624R.string.failed_load_data) + "\n" + RothmanIndexTrendsFragment.this.getString(C1624R.string.timeout));
                    return;
                }
                RothmanIndexTrendsFragment rothmanIndexTrendsFragment2 = RothmanIndexTrendsFragment.this;
                rothmanIndexTrendsFragment2.showHint(StringUtils.getString(C1624R.string.failed_load_data) + "\n" + th.getMessage());
            }
        });
    }

    /* access modifiers changed from: private */
    public void showTrend(TrendBean trendBean, boolean z) {
        this.axisDataList.clear();
        this.weightedScores.clear();
        for (TrendBean.WeightedScoresBean next : trendBean.getWeightedScores()) {
            if (next.getPoint() != 0) {
                this.weightedScores.add(next);
                AxisData axisData = new AxisData();
                axisData.setValue1(next.getPoint());
                axisData.setValue2(DateUtils.getFormatDate(next.getTimestamp(), Constants.TIME_FORMAT_XITEM));
                this.axisDataList.add(axisData);
            }
        }
        this.cardView.setVisibility(0);
        LoggerUtil.m112d(Integer.valueOf(this.axisDataList.size()));
        this.trendAdapter.notifyDataSetChanged();
        this.lineChartLayout.addAxisData(this.axisDataList);
        if (z) {
            this.lineChartLayout.setSeletedIndex(this.axisDataList.size());
            this.lineChartLayout.onAxisClickListener.onAxisClick(this.axisDataList.size());
            return;
        }
        int selectedIndex = this.trendAdapter.getSelectedIndex();
        this.lineChartLayout.setSeletedIndex(selectedIndex + 1);
        this.trendAdapter.setSelectedIndex(selectedIndex);
        ToastUtil.showShortToast(getString(C1624R.string.trend_update_success));
    }

    public void onAxisClick(int i) {
        int i2 = i - 1;
        this.trendAdapter.setSelectedIndex(i2);
        this.recyclerView.scrollToPosition(i2);
    }

    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.trendAdapter.setSelectedIndex(i);
        this.lineChartLayout.setSeletedIndex(i + 1);
    }
}
