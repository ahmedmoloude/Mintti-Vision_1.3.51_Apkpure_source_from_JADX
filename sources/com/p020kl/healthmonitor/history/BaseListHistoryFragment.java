package com.p020kl.healthmonitor.history;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.bean.BaseMeasureEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.TemperatureUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.utils.UnitUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.adapter.HistoryListItemAdapter;
import com.p020kl.healthmonitor.adapter.MyLinearLayoutManager;
import com.p020kl.healthmonitor.bean.HistoryItemBean;
import com.p020kl.healthmonitor.views.ChartView;
import com.p020kl.healthmonitor.views.NormalDataView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import p028io.reactivex.Observable;
import p028io.reactivex.ObservableEmitter;
import p028io.reactivex.ObservableOnSubscribe;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.history.BaseListHistoryFragment */
abstract class BaseListHistoryFragment<T extends BaseMeasureEntity> extends BaseAllHistoryFragment<T> {
    protected HistoryListItemAdapter adapter;
    protected List<HistoryItemBean> checkedData;
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                BaseListHistoryFragment.this.disProgressDialog();
                Toast.makeText(BaseApplication.getInstance(), StringUtils.getString(C1624R.string.generate_success), 1).show();
                Intent intent = new Intent(BaseListHistoryFragment.this.getActivity(), PdfCheckActivity.class);
                intent.putExtra(DublinCoreProperties.TYPE, BaseListHistoryFragment.this.getTitle());
                BaseListHistoryFragment.this.startActivity(intent);
            }
        }
    };
    protected List<HistoryItemBean> historyDatas = new ArrayList();
    protected boolean isSelect;
    @BindView(3384)
    LinearLayout llBottomBar;
    protected List<T> monthDatas = new ArrayList();
    @BindView(3502)
    NormalDataView normalData;
    private int pageCount;
    @BindView(3575)
    RecyclerView rvHistoryList;
    @BindView(3851)
    View vBottom;

    /* access modifiers changed from: protected */
    public abstract void addChartDataToList();

    /* access modifiers changed from: protected */
    public abstract void getAndSavePdfImg(View view, ChartView chartView, int i);

    /* access modifiers changed from: protected */
    public abstract String getMeasureType();

    /* access modifiers changed from: protected */
    public abstract List<T> getNativeData();

    /* access modifiers changed from: protected */
    public abstract String getUnit();

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    BaseListHistoryFragment() {
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_bthistory_list);
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        super.initView(view);
        this.adapter = new HistoryListItemAdapter(getContext(), this.historyDatas);
        this.rvHistoryList.setLayoutManager(new MyLinearLayoutManager(getContext()));
        this.rvHistoryList.setAdapter(this.adapter);
        showProgressDialog(StringUtils.getString(C1624R.string.loading_data), false);
        this.adapter.setOnItemClickListener(new HistoryListItemAdapter.OnItemClickListener() {
            public void onItemClick(View view) {
                BaseListHistoryFragment.this.llBottomBar.setVisibility(0);
            }
        });
        initData();
    }

    @OnClick({3025, 3026})
    public void onClick(View view) {
        switch (view.getId()) {
            case C1624R.C1628id.all_select /*2131296347*/:
                this.checkedData = this.adapter.getCheckedData();
                if (!this.isSelect || this.historyDatas.size() <= 0) {
                    if (!this.isSelect && this.historyDatas.size() > 0) {
                        if (this.checkedData.size() >= this.historyDatas.size()) {
                            setItemSelectFalse();
                            return;
                        } else {
                            setItemSelectTrue();
                            return;
                        }
                    } else {
                        return;
                    }
                } else if (this.checkedData.size() == 0) {
                    setItemSelectTrue();
                    return;
                } else {
                    setItemSelectFalse();
                    return;
                }
            case C1624R.C1628id.all_share /*2131296348*/:
                List<HistoryItemBean> checkedData2 = this.adapter.getCheckedData();
                this.checkedData = checkedData2;
                if (checkedData2.size() <= 0) {
                    ToastUtil.showLongToast(StringUtils.getString(C1624R.string.choose_share_data));
                    return;
                }
                Collections.sort(this.checkedData, new Comparator<HistoryItemBean>() {
                    public int compare(HistoryItemBean historyItemBean, HistoryItemBean historyItemBean2) {
                        if (historyItemBean.getCreateTime() > historyItemBean2.getCreateTime()) {
                            return 1;
                        }
                        return historyItemBean.getCreateTime() < historyItemBean2.getCreateTime() ? -1 : 0;
                    }
                });
                showNoteDialog();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void createPdf() {
        sharePdf();
        createSharePdf();
    }

    public void createSharePdf() {
        createAndSharePdf();
    }

    /* access modifiers changed from: protected */
    public void createOtherPart(Document document) throws IOException, DocumentException {
        createImg(document);
        createChat(document);
    }

    private void createImg(Document document) throws IOException, DocumentException {
        for (int i = 0; i < this.pageCount; i++) {
            Image instance = Image.getInstance(Constants.PDF_IMG_ROTE_PATH + "/img" + i + ".png");
            instance.scaleAbsolute(PageSize.f457A4.getWidth() - 130.0f, 70.0f);
            instance.setAlignment(1);
            document.add(instance);
            document.add(Chunk.NEWLINE);
        }
    }

    private void createChat(Document document) throws DocumentException {
        PdfPTable pdfPTable = new PdfPTable(4);
        for (int i = -1; i < this.checkedData.size(); i++) {
            if (i == -1) {
                pdfPTable.addCell(getChatCell(new Paragraph(StringUtils.getString(C1624R.string.serial_number), this.fontChinese)));
                pdfPTable.addCell(getChatCell(new Paragraph(StringUtils.getString(C1624R.string.time), this.fontChinese)));
                pdfPTable.addCell(getChatCell(new Paragraph(getTitle() + "", this.fontChinese)));
                pdfPTable.addCell(getChatCell(new Paragraph(StringUtils.getString(C1624R.string.result), this.fontChinese)));
            } else {
                Font chineseFont = setChineseFont();
                String measureResult = this.checkedData.get(i).getMeasureResult();
                if (measureResult.equals(StringUtils.getString(C1624R.string.flat))) {
                    chineseFont.setColor(BaseColor.YELLOW);
                } else if (measureResult.equals(StringUtils.getString(C1624R.string.normal))) {
                    chineseFont.setColor(BaseColor.GREEN);
                } else {
                    chineseFont.setColor(BaseColor.RED);
                }
                pdfPTable.addCell(getChatCell(new Paragraph(i + "", this.fontChinese)));
                pdfPTable.addCell(getChatCell(new Paragraph(this.checkedData.get(i).getMeasureTime() + "", this.fontChinese)));
                if (getTitle().equals(getString(C1624R.string.body_temperature))) {
                    pdfPTable.addCell(getChatCell(new Paragraph(TemperatureUtils.tempeTypeConversionDouble(Double.parseDouble(this.checkedData.get(i).getMeasureValue())) + "" + "", this.fontChinese)));
                } else if (getTitle().equals(getString(C1624R.string.blood_glucose))) {
                    String[] split = this.checkedData.get(i).getMeasureValue().split("/");
                    pdfPTable.addCell(getChatCell(new Paragraph(UnitUtil.getBgValue(Double.parseDouble(split[0])) + "/" + split[1], this.fontChinese)));
                } else {
                    pdfPTable.addCell(getChatCell(new Paragraph(this.checkedData.get(i).getMeasureValue() + "" + "", this.fontChinese)));
                }
                pdfPTable.addCell(getChatCell(new Paragraph(this.checkedData.get(i).getMeasureResult() + "" + "", chineseFont)));
            }
        }
        document.add(pdfPTable);
    }

    private PdfPCell getChatCell(Paragraph paragraph) {
        PdfPCell pdfPCell = new PdfPCell((Phrase) paragraph);
        pdfPCell.setFixedHeight(35.0f);
        pdfPCell.setHorizontalAlignment(1);
        pdfPCell.setVerticalAlignment(5);
        return pdfPCell;
    }

    private void setItemSelectTrue() {
        for (int i = 0; i < this.historyDatas.size(); i++) {
            this.historyDatas.get(i).setChecked(true);
        }
        this.adapter.setCheckedData(this.historyDatas);
        this.adapter.notifyDataSetChanged();
        this.isSelect = true;
    }

    private void setItemSelectFalse() {
        for (int i = 0; i < this.historyDatas.size(); i++) {
            this.historyDatas.get(i).setChecked(false);
        }
        this.adapter.removeCheckedData(this.historyDatas);
        this.adapter.notifyDataSetChanged();
        this.isSelect = false;
    }

    private void sharePdf() {
        View inflate = LayoutInflater.from(getContext()).inflate(C1624R.layout.pdf_view, (ViewGroup) null);
        ((TextView) inflate.findViewById(C1624R.C1628id.tv_unit)).setText(getString(C1624R.string.unit) + getUnit());
        int ceil = (int) Math.ceil(((double) this.checkedData.size()) / 20.0d);
        this.pageCount = ceil;
        getAndSavePdfImg(inflate, (ChartView) inflate.findViewById(C1624R.C1628id.pdf_chart_view), ceil);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 1) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.no_storage_permissions));
            } else {
                sharePdf();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initData() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            public void subscribe(ObservableEmitter<Boolean> observableEmitter) throws Exception {
                BaseListHistoryFragment.this.loadNativeData();
                BaseListHistoryFragment.this.addChartData();
                observableEmitter.onNext(true);
                observableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() {
            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
                BaseListHistoryFragment.this.disposableList.add(disposable);
            }

            public void onNext(Boolean bool) {
                if (BaseListHistoryFragment.this.monthDatas.size() == 0) {
                    BaseListHistoryFragment.this.normalData.setVisibility(0);
                    BaseListHistoryFragment.this.rvHistoryList.setVisibility(8);
                    BaseListHistoryFragment.this.llBottomBar.setVisibility(8);
                    BaseListHistoryFragment.this.vBottom.setVisibility(8);
                    return;
                }
                BaseListHistoryFragment.this.normalData.setVisibility(8);
                BaseListHistoryFragment.this.rvHistoryList.setVisibility(0);
                BaseListHistoryFragment.this.llBottomBar.setVisibility(0);
                BaseListHistoryFragment.this.vBottom.setVisibility(0);
            }

            public void onComplete() {
                BaseListHistoryFragment.this.disProgressDialog();
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadNativeData() {
        this.monthDatas.clear();
        List<T> nativeData = getNativeData();
        this.monthDatas = nativeData;
        collectionListReverse(nativeData);
    }

    /* access modifiers changed from: private */
    public void addChartData() {
        this.historyDatas.clear();
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                BaseListHistoryFragment.this.adapter.notifyDataSetChanged();
            }
        });
        addChartDataToList();
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                BaseListHistoryFragment.this.adapter.notifyDataSetChanged();
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        disposable();
    }
}
