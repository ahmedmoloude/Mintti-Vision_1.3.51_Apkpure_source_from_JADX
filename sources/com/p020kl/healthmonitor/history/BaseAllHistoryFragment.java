package com.p020kl.healthmonitor.history;

import android.os.Bundle;
import android.view.View;
import com.p020kl.commonbase.bean.BaseMeasureEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import p028io.reactivex.disposables.Disposable;

/* renamed from: com.kl.healthmonitor.history.BaseAllHistoryFragment */
abstract class BaseAllHistoryFragment<T extends BaseMeasureEntity> extends BasePdfFragment {
    protected List<Disposable> disposableList = new ArrayList();

    /* access modifiers changed from: protected */
    public boolean isEventBusRegister() {
        return true;
    }

    public void onBindView(Bundle bundle, View view) {
    }

    BaseAllHistoryFragment() {
    }

    /* access modifiers changed from: protected */
    public <T extends BaseMeasureEntity> void collectionListReverse(List<T> list) {
        Collections.sort(list, new Comparator<T>() {
            public int compare(T t, T t2) {
                return (int) (t.getCreateTime() - t2.getCreateTime());
            }
        });
    }

    /* access modifiers changed from: protected */
    public <T extends BaseMeasureEntity> void collectionList(List<T> list) {
        Collections.sort(list, new Comparator<T>() {
            public int compare(T t, T t2) {
                return (int) (t2.getCreateTime() - t.getCreateTime());
            }
        });
    }

    /* access modifiers changed from: protected */
    public void disposable() {
        for (Disposable next : this.disposableList) {
            if (next != null && !next.isDisposed()) {
                next.dispose();
            }
        }
    }
}
