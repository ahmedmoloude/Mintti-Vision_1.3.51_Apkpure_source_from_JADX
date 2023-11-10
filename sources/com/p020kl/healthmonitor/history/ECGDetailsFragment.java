package com.p020kl.healthmonitor.history;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.interceptor.ProgressResponseListener;
import com.p020kl.commonbase.utils.ArrayUtils;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.views.ecg.EcgWaveView;
import com.p020kl.commonbase.views.ecg.PdfEcgWaveView;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.MeasureItemLevelView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import p028io.reactivex.Observable;
import p028io.reactivex.ObservableEmitter;
import p028io.reactivex.ObservableOnSubscribe;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.history.ECGDetailsFragment */
public class ECGDetailsFragment extends BasePdfFragment {
    private static final String BUNDLE_ECG_ENTITY = "ecg_entity";
    private long createTime;
    private long duration;
    /* access modifiers changed from: private */
    public ECGEntity ecgEntity;
    /* access modifiers changed from: private */
    public File ecgFile;
    /* access modifiers changed from: private */
    public List<Float> ecgWaveList = new ArrayList();
    @BindView(3216)
    EcgWaveView ecgWaveView;
    /* access modifiers changed from: private */
    public Disposable fileDisposable;
    private float gain;
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                ECGDetailsFragment.this.disProgressDialog();
                Toast.makeText(BaseApplication.getInstance(), StringUtils.getString(C1624R.string.generate_success), 1).show();
                Intent intent = new Intent(ECGDetailsFragment.this.getActivity(), PdfCheckActivity.class);
                intent.putExtra(DublinCoreProperties.TYPE, ECGDetailsFragment.this.getTitle());
                ECGDetailsFragment.this.startActivity(intent);
            }
        }
    };
    @BindView(3451)
    MeasureItemLevelView mlvAvgHr;
    @BindView(3448)
    MeasureItemLevelView mlvBr;
    @BindView(3444)
    MeasureItemLevelView mlvCreateTime;
    @BindView(3445)
    MeasureItemLevelView mlvDuration;
    @BindView(3450)
    MeasureItemLevelView mlvEcgGain;
    @BindView(3453)
    MeasureItemLevelView mlvEcgPaperSpeed;
    @BindView(3452)
    MeasureItemLevelView mlvHrv;
    @BindView(3456)
    MeasureItemLevelView mlvRRMax;
    @BindView(3457)
    MeasureItemLevelView mlvRRMin;
    int page;
    private int paperSpeed;
    private int sampleRate;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isShowRightImg() {
        return true;
    }

    /* access modifiers changed from: protected */
    public int setRightImage() {
        return C1624R.mipmap.share;
    }

    public static ECGDetailsFragment newInstance(ECGEntity eCGEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_ECG_ENTITY, eCGEntity);
        ECGDetailsFragment eCGDetailsFragment = new ECGDetailsFragment();
        eCGDetailsFragment.setArguments(bundle);
        return eCGDetailsFragment;
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.ecg);
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_ecg_details);
    }

    public void onBindView(Bundle bundle, View view) {
        ECGEntity eCGEntity = (ECGEntity) getArguments().getSerializable(BUNDLE_ECG_ENTITY);
        this.ecgEntity = eCGEntity;
        if (eCGEntity != null) {
            this.duration = eCGEntity.getDuration();
            this.createTime = this.ecgEntity.getCreateTime();
            Log.d("时长", this.duration + "");
            this.mlvCreateTime.setResultValue(DateUtils.getFormatDate(this.createTime, Constants.TIME_FORMAT_TIMEFULL));
            this.mlvDuration.setResultValue(String.valueOf(this.duration));
            if (this.ecgEntity.getRriMax() > 2000 || this.ecgEntity.getRriMin() < 300 || this.ecgEntity.getRriMax() < this.ecgEntity.getRriMin()) {
                this.mlvRRMax.setResultValue("0");
                this.mlvRRMin.setResultValue("0");
            } else {
                this.mlvRRMax.setResultValue(String.valueOf(this.ecgEntity.getRriMax()));
                this.mlvRRMin.setResultValue(String.valueOf(this.ecgEntity.getRriMin()));
            }
            this.mlvAvgHr.setResultValue(String.valueOf(this.ecgEntity.getAvgHr()));
            this.mlvHrv.setResultValue(String.valueOf(this.ecgEntity.getHrv()));
            this.mlvBr.setResultValue(String.valueOf(this.ecgEntity.getBr()));
            int sampleRate2 = this.ecgEntity.getSampleRate();
            this.sampleRate = sampleRate2;
            if (sampleRate2 == 200) {
                this.ecgWaveView.setSampleRate(200);
            } else {
                this.sampleRate = 512;
                this.ecgWaveView.setSampleRate(512);
            }
        }
        this.paperSpeed = SpManager.getPaperSpeed();
        float gain2 = SpManager.getGain();
        this.gain = gain2;
        this.ecgWaveView.setGain(gain2);
        this.ecgWaveView.setPagerSpeed(this.paperSpeed + 1);
        if (this.paperSpeed == 0) {
            this.mlvEcgPaperSpeed.setResultValue(String.valueOf(25));
        } else {
            this.mlvEcgPaperSpeed.setResultValue(String.valueOf(50));
        }
        this.mlvEcgGain.setResultValue(new DecimalFormat("0").format((double) (this.gain * 10.0f)));
    }

    public void onLazyInitView(Bundle bundle) {
        super.onLazyInitView(bundle);
        ECGEntity eCGEntity = this.ecgEntity;
        if (eCGEntity == null) {
            return;
        }
        if (!TextUtils.isEmpty(eCGEntity.getFilePath())) {
            File file = new File(this.ecgEntity.getFilePath());
            this.ecgFile = file;
            loadEcgData(file);
            return;
        }
        downloadEcgFile();
    }

    /* access modifiers changed from: private */
    public void loadEcgData(final File file) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
                if (file.exists()) {
                    try {
                        byte[] bArr = new byte[2048];
                        FileInputStream fileInputStream = new FileInputStream(file);
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read != -1) {
                                for (int i = 0; i < read; i += 4) {
                                    ECGDetailsFragment.this.ecgWaveList.add(Float.valueOf((float) ArrayUtils.bytes2int(bArr, i)));
                                }
                            } else {
                                fileInputStream.close();
                                observableEmitter.onComplete();
                                return;
                            }
                        }
                    } catch (Exception e) {
                        observableEmitter.onError(e);
                        e.printStackTrace();
                    }
                } else {
                    observableEmitter.onError(new Throwable("file not exists"));
                }
            }
        }).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
            public void onError(Throwable th) {
            }

            public void onNext(Object obj) {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = ECGDetailsFragment.this.fileDisposable = disposable;
            }

            public void onComplete() {
                ECGDetailsFragment.this.ecgWaveView.preparePoints(ECGDetailsFragment.this.ecgWaveList);
                ECGDetailsFragment.this.disProgressDialog();
            }
        });
    }

    private void downloadEcgFile() {
        if (this.ecgEntity != null) {
            showProgressDialog(StringUtils.getString(C1624R.string.ecg_downloading), false);
            RestClient.download(this.ecgEntity.getFileUrl(), (ProgressResponseListener) null).subscribeOn(Schedulers.m1081io()).observeOn(Schedulers.m1081io()).subscribe(new Observer<ResponseBody>() {
                public void onSubscribe(Disposable disposable) {
                }

                /* JADX WARNING: Removed duplicated region for block: B:31:0x0099 A[SYNTHETIC, Splitter:B:31:0x0099] */
                /* JADX WARNING: Removed duplicated region for block: B:36:0x00a1 A[Catch:{ IOException -> 0x009d }] */
                /* JADX WARNING: Removed duplicated region for block: B:40:0x00ac A[SYNTHETIC, Splitter:B:40:0x00ac] */
                /* JADX WARNING: Removed duplicated region for block: B:45:0x00b4 A[Catch:{ IOException -> 0x00b0 }] */
                /* JADX WARNING: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onNext(okhttp3.ResponseBody r6) {
                    /*
                        r5 = this;
                        r0 = 0
                        java.io.InputStream r6 = r6.byteStream()     // Catch:{ Exception -> 0x008b, all -> 0x0086 }
                        com.kl.healthmonitor.history.ECGDetailsFragment r1 = com.p020kl.healthmonitor.history.ECGDetailsFragment.this     // Catch:{ Exception -> 0x0081, all -> 0x007c }
                        com.kl.commonbase.bean.ECGEntity r2 = r1.ecgEntity     // Catch:{ Exception -> 0x0081, all -> 0x007c }
                        long r2 = r2.getCreateTime()     // Catch:{ Exception -> 0x0081, all -> 0x007c }
                        java.io.File r2 = com.p020kl.commonbase.utils.FileUtils.getEcgFile(r2)     // Catch:{ Exception -> 0x0081, all -> 0x007c }
                        java.io.File unused = r1.ecgFile = r2     // Catch:{ Exception -> 0x0081, all -> 0x007c }
                        java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0081, all -> 0x007c }
                        com.kl.healthmonitor.history.ECGDetailsFragment r2 = com.p020kl.healthmonitor.history.ECGDetailsFragment.this     // Catch:{ Exception -> 0x0081, all -> 0x007c }
                        java.io.File r2 = r2.ecgFile     // Catch:{ Exception -> 0x0081, all -> 0x007c }
                        r1.<init>(r2)     // Catch:{ Exception -> 0x0081, all -> 0x007c }
                        r0 = 4096(0x1000, float:5.74E-42)
                        byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x007a }
                    L_0x0025:
                        int r2 = r6.read(r0)     // Catch:{ Exception -> 0x007a }
                        r3 = -1
                        if (r2 == r3) goto L_0x0031
                        r3 = 0
                        r1.write(r0, r3, r2)     // Catch:{ Exception -> 0x007a }
                        goto L_0x0025
                    L_0x0031:
                        com.kl.healthmonitor.history.ECGDetailsFragment r0 = com.p020kl.healthmonitor.history.ECGDetailsFragment.this     // Catch:{ Exception -> 0x007a }
                        java.io.File r0 = r0.ecgFile     // Catch:{ Exception -> 0x007a }
                        java.lang.String r0 = com.p020kl.commonbase.utils.FileUtils.getFileMd5(r0)     // Catch:{ Exception -> 0x007a }
                        com.kl.healthmonitor.history.ECGDetailsFragment r2 = com.p020kl.healthmonitor.history.ECGDetailsFragment.this     // Catch:{ Exception -> 0x007a }
                        com.kl.commonbase.bean.ECGEntity r2 = r2.ecgEntity     // Catch:{ Exception -> 0x007a }
                        java.lang.String r2 = r2.getFileMd5()     // Catch:{ Exception -> 0x007a }
                        boolean r0 = r0.equals(r2)     // Catch:{ Exception -> 0x007a }
                        if (r0 == 0) goto L_0x0068
                        com.kl.healthmonitor.history.ECGDetailsFragment r0 = com.p020kl.healthmonitor.history.ECGDetailsFragment.this     // Catch:{ Exception -> 0x007a }
                        com.kl.commonbase.bean.ECGEntity r0 = r0.ecgEntity     // Catch:{ Exception -> 0x007a }
                        com.kl.healthmonitor.history.ECGDetailsFragment r2 = com.p020kl.healthmonitor.history.ECGDetailsFragment.this     // Catch:{ Exception -> 0x007a }
                        java.io.File r2 = r2.ecgFile     // Catch:{ Exception -> 0x007a }
                        java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ Exception -> 0x007a }
                        r0.setFilePath(r2)     // Catch:{ Exception -> 0x007a }
                        com.kl.healthmonitor.history.ECGDetailsFragment r0 = com.p020kl.healthmonitor.history.ECGDetailsFragment.this     // Catch:{ Exception -> 0x007a }
                        com.kl.commonbase.bean.ECGEntity r0 = r0.ecgEntity     // Catch:{ Exception -> 0x007a }
                        com.p020kl.commonbase.data.p021db.manager.ECGTableManager.updateEcgEntity(r0)     // Catch:{ Exception -> 0x007a }
                        goto L_0x0071
                    L_0x0068:
                        com.kl.healthmonitor.history.ECGDetailsFragment r0 = com.p020kl.healthmonitor.history.ECGDetailsFragment.this     // Catch:{ Exception -> 0x007a }
                        java.io.File r0 = r0.ecgFile     // Catch:{ Exception -> 0x007a }
                        r0.delete()     // Catch:{ Exception -> 0x007a }
                    L_0x0071:
                        if (r6 == 0) goto L_0x0076
                        r6.close()     // Catch:{ IOException -> 0x009d }
                    L_0x0076:
                        r1.close()     // Catch:{ IOException -> 0x009d }
                        goto L_0x00a8
                    L_0x007a:
                        r0 = move-exception
                        goto L_0x008f
                    L_0x007c:
                        r1 = move-exception
                        r4 = r1
                        r1 = r0
                        r0 = r4
                        goto L_0x00aa
                    L_0x0081:
                        r1 = move-exception
                        r4 = r1
                        r1 = r0
                        r0 = r4
                        goto L_0x008f
                    L_0x0086:
                        r6 = move-exception
                        r1 = r0
                        r0 = r6
                        r6 = r1
                        goto L_0x00aa
                    L_0x008b:
                        r6 = move-exception
                        r1 = r0
                        r0 = r6
                        r6 = r1
                    L_0x008f:
                        com.kl.healthmonitor.history.ECGDetailsFragment r2 = com.p020kl.healthmonitor.history.ECGDetailsFragment.this     // Catch:{ all -> 0x00a9 }
                        r2.disProgressDialog()     // Catch:{ all -> 0x00a9 }
                        r0.printStackTrace()     // Catch:{ all -> 0x00a9 }
                        if (r6 == 0) goto L_0x009f
                        r6.close()     // Catch:{ IOException -> 0x009d }
                        goto L_0x009f
                    L_0x009d:
                        r6 = move-exception
                        goto L_0x00a5
                    L_0x009f:
                        if (r1 == 0) goto L_0x00a8
                        r1.close()     // Catch:{ IOException -> 0x009d }
                        goto L_0x00a8
                    L_0x00a5:
                        r6.printStackTrace()
                    L_0x00a8:
                        return
                    L_0x00a9:
                        r0 = move-exception
                    L_0x00aa:
                        if (r6 == 0) goto L_0x00b2
                        r6.close()     // Catch:{ IOException -> 0x00b0 }
                        goto L_0x00b2
                    L_0x00b0:
                        r6 = move-exception
                        goto L_0x00b8
                    L_0x00b2:
                        if (r1 == 0) goto L_0x00bb
                        r1.close()     // Catch:{ IOException -> 0x00b0 }
                        goto L_0x00bb
                    L_0x00b8:
                        r6.printStackTrace()
                    L_0x00bb:
                        throw r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.history.ECGDetailsFragment.C16914.onNext(okhttp3.ResponseBody):void");
                }

                public void onError(Throwable th) {
                    ECGDetailsFragment.this.disProgressDialog();
                }

                public void onComplete() {
                    ECGDetailsFragment eCGDetailsFragment = ECGDetailsFragment.this;
                    eCGDetailsFragment.loadEcgData(eCGDetailsFragment.ecgFile);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onRightClicked() {
        showNoteDialog();
    }

    private void createPdfImg() {
        View inflate = LayoutInflater.from(getContext()).inflate(C1624R.layout.ecg_pdf_view, (ViewGroup) null);
        PdfEcgWaveView.DATA_PRE_SECOND = this.sampleRate;
        getAndSavePdfImg(inflate, (PdfEcgWaveView) inflate.findViewById(C1624R.C1628id.ecg_wave_view2), 1);
    }

    private void getAndSavePdfImg(View view, PdfEcgWaveView pdfEcgWaveView, int i) {
        pdfEcgWaveView.setGain(this.gain);
        pdfEcgWaveView.setPagerSpeed(this.paperSpeed + 1);
        Log.d("列表大小", this.ecgWaveList.size() + "");
        int i2 = this.sampleRate * 8;
        this.page = (int) Math.ceil(((double) this.ecgWaveList.size()) / ((double) i2));
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < this.page; i3++) {
            arrayList.clear();
            int i4 = i3 * i2;
            for (int i5 = i4; i5 < i2 + i4; i5++) {
                if (i5 < this.ecgWaveList.size()) {
                    arrayList.add(this.ecgWaveList.get(i5));
                }
            }
            pdfEcgWaveView.preparePoints(arrayList);
            layoutView(view, pdfEcgWaveView, arrayList);
            getAndSaveBitmap(view, i3);
        }
        Log.d("ecgsize", this.ecgWaveList.size() + "");
    }

    /* access modifiers changed from: protected */
    public void layoutView(View view, PdfEcgWaveView pdfEcgWaveView, List<Float> list) {
        int floatValue = (int) (((float) (this.sampleRate * 8)) * this.ecgWaveView.getDataSpacing().floatValue());
        Log.d("viewSize", list.size() + "");
        Log.e("bitmap", "a=" + floatValue + "-dataSpacing=" + this.ecgWaveView.getDataSpacing() + "-sampleRate=" + this.sampleRate);
        view.layout(0, 0, floatValue, SizeUtils.dp2px(180.0f));
        view.measure(View.MeasureSpec.makeMeasureSpec(floatValue, 1073741824), View.MeasureSpec.makeMeasureSpec(SizeUtils.dp2px(180.0f), 1073741824));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    /* access modifiers changed from: protected */
    public void createPdf() {
        createPdfImg();
        createAndSharePdf();
    }

    /* access modifiers changed from: protected */
    public void createOtherPart(Document document) throws DocumentException, IOException {
        addMeasureResult(document);
        addMeasureImg(document);
    }

    private void addMeasureImg(Document document) throws DocumentException, IOException {
        this.fontChinese.setSize(12.0f);
        this.fontChinese.setStyle(0);
        this.fontChinese.setColor(BaseColor.BLACK);
        int i = this.paperSpeed == 0 ? 25 : 50;
        String[] strArr = {DateUtils.getFormatDate(this.createTime, Constants.TIME_FORMAT_BIRTHDAY) + "", StringUtils.getString(C1624R.string.paper_speed_) + "" + i + "mm/s", StringUtils.getString(C1624R.string.gain) + "" + new DecimalFormat("0").format((double) (this.gain * 10.0f)) + "mm/mv"};
        for (int i2 = 0; i2 < this.page; i2++) {
            PdfPTable pdfPTable = new PdfPTable(3);
            Log.d("ddd", "长度：" + 3 + "第一个值：" + strArr[0]);
            pdfPTable.getDefaultCell().setBorder(0);
            for (int i3 = 0; i3 < 3; i3++) {
                PdfPCell pdfPCell = new PdfPCell((Phrase) new Paragraph(strArr[i3], this.fontChinese));
                pdfPCell.setBorder(0);
                pdfPCell.setHorizontalAlignment(0);
                pdfPCell.setFixedHeight(20.0f);
                pdfPCell.setVerticalAlignment(5);
                pdfPTable.addCell(pdfPCell);
            }
            document.add(pdfPTable);
            Image instance = Image.getInstance(Constants.PDF_IMG_ROTE_PATH + "/img" + i2 + ".png");
            instance.scaleAbsolute(PageSize.f457A4.getWidth() - 130.0f, 70.0f);
            instance.setAlignment(1);
            document.add(instance);
        }
        document.add(Chunk.NEWLINE);
    }

    private void addMeasureResult(Document document) throws DocumentException {
        document.add(Chunk.NEWLINE);
        this.fontChinese.setSize(14.0f);
        this.fontChinese.setStyle(0);
        this.fontChinese.setColor(BaseColor.BLACK);
        PdfPTable pdfPTable = new PdfPTable(3);
        String[] strArr = {DateUtils.getFormatDate(this.createTime, Constants.TIME_FORMAT_TIMEFULL) + "", StringUtils.getString(C1624R.string.time_consuming) + "" + this.duration + HtmlTags.f490S, StringUtils.getString(C1624R.string.rri_max) + "" + this.ecgEntity.getRriMax() + Constants.MS_UNIT, StringUtils.getString(C1624R.string.rri_min) + "" + this.ecgEntity.getRriMin() + Constants.MS_UNIT, StringUtils.getString(C1624R.string.average_heart_rate) + "" + this.ecgEntity.getAvgHr() + Constants.BPM_UNIT, StringUtils.getString(C1624R.string.hrv) + "" + this.ecgEntity.getHrv() + Constants.MS_UNIT, StringUtils.getString(C1624R.string.respiratory_rate) + "" + this.ecgEntity.getBr() + Constants.BPM_UNIT, ""};
        StringBuilder sb = new StringBuilder();
        sb.append("长度：");
        sb.append(8);
        sb.append("第一个值：");
        sb.append(strArr[0]);
        Log.d("ddd", sb.toString());
        pdfPTable.getDefaultCell().setBorder(0);
        for (int i = 0; i < 8; i++) {
            PdfPCell pdfPCell = new PdfPCell((Phrase) new Paragraph(strArr[i], this.fontChinese));
            pdfPCell.setBorder(0);
            pdfPCell.setHorizontalAlignment(0);
            pdfPCell.setFixedHeight(25.0f);
            if (i == 0 || i == 2 || i == 4 || i == 6) {
                pdfPCell.setColspan(2);
            }
            pdfPCell.setVerticalAlignment(5);
            pdfPTable.addCell(pdfPCell);
        }
        document.add(pdfPTable);
        document.add(Chunk.NEWLINE);
    }

    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.fileDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.fileDisposable.dispose();
        }
    }
}
