package com.p020kl.healthmonitor.history;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.NoteEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.views.NoteDialog;
import com.p020kl.healthmonitor.C1624R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.kl.healthmonitor.history.BasePdfFragment */
public abstract class BasePdfFragment extends BaseFragmentWhiteToolbar {
    protected File file;
    protected Font fontChinese;
    /* access modifiers changed from: private */
    public Handler handler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                BasePdfFragment.this.disProgressDialog();
                Toast.makeText(BaseApplication.getInstance(), StringUtils.getString(C1624R.string.generate_success), 1).show();
                Intent intent = new Intent(BasePdfFragment.this.getActivity(), PdfCheckActivity.class);
                intent.putExtra(DublinCoreProperties.TYPE, BasePdfFragment.this.getTitle());
                BasePdfFragment.this.startActivity(intent);
            }
        }
    };
    protected NoteEntity mNoteEntity;
    protected NoteDialog noteDialog;
    protected ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /* access modifiers changed from: protected */
    public void createOtherPart(Document document) throws IOException, DocumentException {
    }

    /* access modifiers changed from: protected */
    public void createPdf() {
    }

    /* access modifiers changed from: protected */
    public void showNoteDialog() {
        if (this.noteDialog == null) {
            this.noteDialog = new NoteDialog(getActivity(), StringUtils.getString(C1624R.string.picker_cancel), StringUtils.getString(C1624R.string.picker_ok));
        }
        this.noteDialog.setIOnClickListener(new NoteDialog.IOnClickListener() {
            public void onLeftClicked(NoteDialog noteDialog, NoteEntity noteEntity) {
                noteDialog.dismiss();
            }

            public void onRightClicked(NoteDialog noteDialog, NoteEntity noteEntity) {
                BasePdfFragment.this.showProgressDialog(StringUtils.getString(C1624R.string.being_generated), false);
                BasePdfFragment.this.mNoteEntity = noteEntity;
                if (noteEntity.getName().trim().equals("")) {
                    BasePdfFragment.this.loadUserInfo();
                } else {
                    BasePdfFragment.this.createPdf();
                }
                noteDialog.dismiss();
            }
        });
        this.noteDialog.show();
    }

    /* access modifiers changed from: private */
    public void loadUserInfo() {
        this.mNoteEntity.setName(SpManager.getMemberName());
        createPdf();
    }

    public void createAndSharePdf() {
        this.singleThreadExecutor.execute(new Runnable() {
            public void run() {
                BasePdfFragment basePdfFragment = BasePdfFragment.this;
                basePdfFragment.file = new File(Constants.PDF_ROTE_PATH + "/" + BasePdfFragment.this.getTitle() + StringUtils.getString(C1624R.string.information_report) + ".pdf");
                Document document = new Document(PageSize.f457A4, 10.0f, 10.0f, 10.0f, 10.0f);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(BasePdfFragment.this.file);
                    PdfWriter.getInstance(document, fileOutputStream);
                    document.open();
                    document.setPageCount(1);
                    BasePdfFragment basePdfFragment2 = BasePdfFragment.this;
                    basePdfFragment2.fontChinese = basePdfFragment2.setChineseFont();
                    Paragraph paragraph = new Paragraph(BasePdfFragment.this.getTitle() + StringUtils.getString(C1624R.string.information_report), BasePdfFragment.this.fontChinese);
                    BasePdfFragment.this.fontChinese.setSize(17.0f);
                    BasePdfFragment.this.fontChinese.setStyle(1);
                    BasePdfFragment.this.fontChinese.setColor(BaseColor.BLACK);
                    paragraph.setAlignment(1);
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE);
                    BasePdfFragment.this.createUserInfo(document);
                    BasePdfFragment.this.fontChinese.setSize(14.0f);
                    BasePdfFragment.this.fontChinese.setStyle(1);
                    Paragraph paragraph2 = new Paragraph(StringUtils.getString(C1624R.string.test_outcome), BasePdfFragment.this.fontChinese);
                    paragraph2.setAlignment(1);
                    document.add(paragraph2);
                    BasePdfFragment.this.createOtherPart(document);
                    document.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    BasePdfFragment.this.handler.sendEmptyMessage(1);
                } catch (Exception e) {
                    BasePdfFragment.this.disProgressDialog();
                    ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_report));
                    e.printStackTrace();
                    Log.d("创建失败", e.getLocalizedMessage());
                }
            }
        });
    }

    public Font setChineseFont() {
        try {
            return new Font(BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont, AsianFontMapper.ChineseSimplifiedEncoding_H, false), 12.0f, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font();
        }
    }

    /* access modifiers changed from: private */
    public void createUserInfo(Document document) throws DocumentException {
        String str;
        this.fontChinese.setSize(14.0f);
        this.fontChinese.setStyle(1);
        this.fontChinese.setColor(BaseColor.BLACK);
        Paragraph paragraph = new Paragraph(StringUtils.getString(C1624R.string.patient_information), this.fontChinese);
        paragraph.setAlignment(1);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);
        this.fontChinese.setSize(12.0f);
        this.fontChinese.setStyle(0);
        PdfPTable pdfPTable = new PdfPTable(3);
        if (this.mNoteEntity.getGender() == 0) {
            str = StringUtils.getString(C1624R.string.man);
        } else {
            str = StringUtils.getString(C1624R.string.woman);
        }
        String[] strArr = {StringUtils.getString(C1624R.string.pdf_name) + "：" + this.mNoteEntity.getName(), StringUtils.getString(C1624R.string.sex) + "：" + str, StringUtils.getString(C1624R.string.age) + "：" + this.mNoteEntity.getAge(), StringUtils.getString(C1624R.string.share_date) + "：" + DateUtils.getFormatDate(DateUtils.getCreateDate(), Constants.TIME_FORMAT_BIRTHDAY), StringUtils.getString(C1624R.string.node) + "：" + this.mNoteEntity.getComment()};
        StringBuilder sb = new StringBuilder();
        sb.append("长度：");
        sb.append(5);
        sb.append("第一个值：");
        sb.append(strArr[0]);
        Log.d("ddd", sb.toString());
        pdfPTable.getDefaultCell().setBorder(0);
        for (int i = 0; i < 5; i++) {
            PdfPCell pdfPCell = new PdfPCell((Phrase) new Paragraph(strArr[i], this.fontChinese));
            pdfPCell.setBorder(0);
            pdfPCell.setHorizontalAlignment(0);
            pdfPCell.setFixedHeight(25.0f);
            pdfPCell.setVerticalAlignment(5);
            if (i == 4) {
                pdfPCell.setColspan(2);
                pdfPCell.setFixedHeight(45.0f);
            }
            pdfPTable.addCell(pdfPCell);
        }
        document.add(pdfPTable);
        document.add(Chunk.NEWLINE);
    }

    /* access modifiers changed from: protected */
    public void getAndSaveBitmap(View view, int i) {
        Log.e("bitmap", "width=" + view.getWidth() + "height=" + view.getHeight());
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        saveBitmap(createBitmap, i);
    }

    /* access modifiers changed from: protected */
    public void layoutView(View view) {
        view.layout(0, 0, SizeUtils.dp2px(1340.0f), SizeUtils.dp2px(240.0f));
        view.measure(View.MeasureSpec.makeMeasureSpec(SizeUtils.dp2px(1340.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(SizeUtils.dp2px(240.0f), 1073741824));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public void saveBitmap(final Bitmap bitmap, final int i) {
        Log.d("hehe", "保存图片");
        this.singleThreadExecutor.execute(new Runnable() {
            public void run() {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(Constants.PDF_IMG_ROTE_PATH + "/img" + i + ".png"));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    BasePdfFragment.this.disProgressDialog();
                    ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_report));
                    Log.d("hehe", e.getLocalizedMessage());
                }
            }
        });
    }
}
