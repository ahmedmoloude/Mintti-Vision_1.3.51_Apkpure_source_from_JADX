package com.p020kl.healthmonitor.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.LongSparseArray;
import com.p020kl.commonbase.bean.BGEntity;
import com.p020kl.commonbase.bean.BPEntity;
import com.p020kl.commonbase.bean.BTEntity;
import com.p020kl.commonbase.bean.BaseMeasureEntity;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.bean.Spo2Entity;
import com.p020kl.commonbase.data.p021db.manager.BGTableManager;
import com.p020kl.commonbase.data.p021db.manager.BPTableManager;
import com.p020kl.commonbase.data.p021db.manager.BTTableManager;
import com.p020kl.commonbase.data.p021db.manager.ECGTableManager;
import com.p020kl.commonbase.data.p021db.manager.Spo2hTableManager;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UploadResult;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.LoggerUtil;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p028io.reactivex.Observer;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Action;

/* renamed from: com.kl.healthmonitor.service.SyncService */
public class SyncService extends Service {
    /* access modifiers changed from: private */
    public LongSparseArray<BaseMeasureEntity> dataMap = new LongSparseArray<>();
    /* access modifiers changed from: private */
    public LongSparseArray<ECGEntity> ecgEntityMap = new LongSparseArray<>();
    private ExecutorService executorService = null;
    private final LocalBinder mBinder = new LocalBinder();
    private Runnable uploadEcgRunnable = new Runnable() {
        public void run() {
            SyncService.this.uploadECGData();
        }
    };
    private Runnable uploadRunnable = new Runnable() {
        public void run() {
            SyncService.this.uploadMeasureData();
        }
    };

    public void onCreate() {
        super.onCreate();
        ExecutorService executorService2 = this.executorService;
        if (executorService2 == null || executorService2.isShutdown()) {
            this.executorService = Executors.newFixedThreadPool(2);
        }
        LoggerUtil.m113d("Sync", " SyncService onCreate = " + this.executorService);
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    /* renamed from: com.kl.healthmonitor.service.SyncService$LocalBinder */
    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public SyncService getService() {
            return new SyncService();
        }
    }

    public synchronized void addEcgData(List<ECGEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            ECGEntity eCGEntity = list.get(i);
            if (this.ecgEntityMap.get(eCGEntity.getCreateTime()) == null) {
                this.ecgEntityMap.put(eCGEntity.getCreateTime(), eCGEntity);
            }
        }
    }

    public synchronized void addUploadData(List<BaseMeasureEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            BaseMeasureEntity baseMeasureEntity = list.get(i);
            if (this.dataMap.get(baseMeasureEntity.getCreateTime()) == null) {
                this.dataMap.put(baseMeasureEntity.getCreateTime(), baseMeasureEntity);
            }
        }
    }

    public void startUploadEcgData() {
        LoggerUtil.m113d("Sync", "executorService = " + this.executorService);
        ExecutorService executorService2 = this.executorService;
        if (executorService2 == null || executorService2.isShutdown()) {
            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
            this.executorService = newFixedThreadPool;
            newFixedThreadPool.execute(this.uploadEcgRunnable);
            return;
        }
        this.executorService.execute(this.uploadEcgRunnable);
    }

    public void startUploadData() {
        LoggerUtil.m113d("Sync", "executorService = " + this.executorService);
        ExecutorService executorService2 = this.executorService;
        if (executorService2 == null || executorService2.isShutdown()) {
            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
            this.executorService = newFixedThreadPool;
            newFixedThreadPool.execute(this.uploadRunnable);
            return;
        }
        this.executorService.execute(this.uploadRunnable);
    }

    /* access modifiers changed from: private */
    public void uploadECGData() {
        LoggerUtil.m113d("Sync", "ecgEntityMap size = " + this.ecgEntityMap.size());
        LongSparseArray<ECGEntity> longSparseArray = this.ecgEntityMap;
        if (longSparseArray != null && longSparseArray.size() > 0) {
            LongSparseArray<ECGEntity> longSparseArray2 = this.ecgEntityMap;
            final ECGEntity eCGEntity = longSparseArray2.get(longSparseArray2.keyAt(0));
            File file = new File(eCGEntity.getFilePath());
            if (file.exists()) {
                RestClient.uploadEcgFile(eCGEntity, file).doFinally(new Action() {
                    public void run() throws Exception {
                        SyncService.this.ecgEntityMap.removeAt(0);
                        SyncService.this.startUploadEcgData();
                    }
                }).subscribe(new Observer<ResponseResult<UploadResult>>() {
                    public void onComplete() {
                    }

                    public void onSubscribe(Disposable disposable) {
                    }

                    public void onNext(ResponseResult<UploadResult> responseResult) {
                        if (responseResult.getStatus() == 200) {
                            eCGEntity.setDocId(responseResult.getData().getDocId());
                            eCGEntity.setFileUrl(responseResult.getData().getFileUrl());
                            ECGTableManager.updateEcgEntity(eCGEntity);
                            LoggerUtil.m113d("Sync", DateUtils.getISO8601Time(eCGEntity.getCreateTime()));
                        }
                    }

                    public void onError(Throwable th) {
                        LoggerUtil.m113d("Sync", th.getMessage());
                    }
                });
                return;
            }
            this.ecgEntityMap.removeAt(0);
            startUploadEcgData();
        }
    }

    public void uploadMeasureData() {
        LongSparseArray<BaseMeasureEntity> longSparseArray = this.dataMap;
        if (longSparseArray != null && longSparseArray.size() > 0) {
            LongSparseArray<BaseMeasureEntity> longSparseArray2 = this.dataMap;
            final BaseMeasureEntity baseMeasureEntity = longSparseArray2.get(longSparseArray2.keyAt(0));
            RestClient.uploadMeasureData(baseMeasureEntity).doFinally(new Action() {
                public void run() throws Exception {
                    SyncService.this.dataMap.removeAt(0);
                    SyncService.this.startUploadData();
                }
            }).subscribe(new Observer<ResponseResult<UploadResult>>() {
                public void onComplete() {
                }

                public void onSubscribe(Disposable disposable) {
                }

                public void onNext(ResponseResult<UploadResult> responseResult) {
                    if (responseResult.getStatus() == 200) {
                        String docId = responseResult.getData().getDocId();
                        BaseMeasureEntity baseMeasureEntity = baseMeasureEntity;
                        if (baseMeasureEntity instanceof BGEntity) {
                            BGEntity bGEntity = (BGEntity) baseMeasureEntity;
                            bGEntity.setDocId(docId);
                            BGTableManager.updateEntity(bGEntity);
                        } else if (baseMeasureEntity instanceof BPEntity) {
                            BPEntity bPEntity = (BPEntity) baseMeasureEntity;
                            bPEntity.setDocId(docId);
                            BPTableManager.updateEntity(bPEntity);
                        } else if (baseMeasureEntity instanceof BTEntity) {
                            BTEntity bTEntity = (BTEntity) baseMeasureEntity;
                            bTEntity.setDocId(docId);
                            BTTableManager.updateEntity(bTEntity);
                        } else if (baseMeasureEntity instanceof Spo2Entity) {
                            Spo2Entity spo2Entity = (Spo2Entity) baseMeasureEntity;
                            spo2Entity.setDocId(docId);
                            Spo2hTableManager.updateEntity(spo2Entity);
                        }
                    }
                }

                public void onError(Throwable th) {
                    LoggerUtil.m113d("Sync", th.getMessage());
                }
            });
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.ecgEntityMap.clear();
        ExecutorService executorService2 = this.executorService;
        if (executorService2 != null && !executorService2.isShutdown()) {
            this.executorService.shutdownNow();
        }
        LoggerUtil.m113d("Sync", " SyncService onDestroy");
    }
}
