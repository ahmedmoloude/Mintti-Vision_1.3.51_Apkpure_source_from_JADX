package com.p020kl.healthmonitor.pdf;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.p020kl.commonbase.constants.Constants;
import java.io.File;
import java.io.FileOutputStream;

/* renamed from: com.kl.healthmonitor.pdf.PdfUtil */
public class PdfUtil {
    /* access modifiers changed from: private */
    public File file;
    /* access modifiers changed from: private */
    public Font fontChinese;

    public void createAndSharePdf() {
        new Thread(new Runnable() {
            public void run() {
                PdfUtil pdfUtil = PdfUtil.this;
                File unused = pdfUtil.file = new File(Constants.PDF_ROTE_PATH + "/测试.pdf");
                Document document = new Document(PageSize.f457A4, 5.0f, 5.0f, 10.0f, 10.0f);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(PdfUtil.this.file);
                    PdfWriter.getInstance(document, fileOutputStream);
                    document.open();
                    document.setPageCount(1);
                    PdfUtil pdfUtil2 = PdfUtil.this;
                    Font unused2 = pdfUtil2.fontChinese = pdfUtil2.setChineseFont();
                    Paragraph paragraph = new Paragraph("苏州美糯爱医疗科技有限公司", PdfUtil.this.fontChinese);
                    PdfUtil.this.fontChinese.setSize(30.0f);
                    PdfUtil.this.fontChinese.setStyle(1);
                    PdfUtil.this.fontChinese.setColor(0, 0, 0);
                    paragraph.setAlignment(1);
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE);
                    Paragraph paragraph2 = new Paragraph("动态心电分析报告", PdfUtil.this.fontChinese);
                    PdfUtil.this.fontChinese.setSize(24.0f);
                    PdfUtil.this.fontChinese.setColor(0, 0, 0);
                    PdfUtil.this.fontChinese.setStyle(0);
                    paragraph2.setAlignment(1);
                    document.add(paragraph2);
                    document.add(Chunk.NEWLINE);
                    PdfUtil.this.createUserInfo(document);
                    PdfUtil.this.addReflexTable(document);
                    PdfUtil.this.addHrv(document);
                    PdfUtil.this.addConclusion(document);
                    PdfUtil.this.addCheckInfo(document);
                    PdfUtil.this.addRRInterval(document);
                    document.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("创建失败", e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void addRRInterval(Document document) throws DocumentException {
        addTitle(document, "RR间期输出");
        addRRTable(document, "卧立位心率变化");
        addRRTable(document, "深呼吸心率差");
        addRRTable(document, "Valsalva动作指数");
    }

    private void addRRTable(Document document, String str) throws DocumentException {
        Document document2 = document;
        PdfPTable pdfPTable = new PdfPTable(3);
        addTableHead(pdfPTable, str);
        String[] strArr = {"", "RR间期", "备注"};
        String[] strArr2 = {"1", "100", "100", ExifInterface.GPS_MEASUREMENT_2D, "100", "100", ExifInterface.GPS_MEASUREMENT_3D, "100", "100", "4", "100", "100"};
        for (int i = 0; i < 3; i++) {
            pdfPTable.addCell(getChatCell(new Paragraph(strArr[i], this.fontChinese), 40, true, true));
        }
        for (int i2 = 0; i2 < 12; i2++) {
            pdfPTable.addCell(getChatCell(new Paragraph(strArr2[i2], this.fontChinese), 40, false, true));
        }
        document2.add(pdfPTable);
        document2.add(Chunk.NEWLINE);
    }

    /* access modifiers changed from: private */
    public void addCheckInfo(Document document) throws DocumentException {
        addSecondTitle(document, new String[]{"检查医生:", "检查时间:"}, new int[]{65, 35});
    }

    /* access modifiers changed from: private */
    public void addConclusion(Document document) throws DocumentException {
        addSecondTitle(document, new String[]{"结论:", ""}, new int[]{15, 85});
    }

    /* access modifiers changed from: private */
    public void addHrv(Document document) throws DocumentException {
        addTitle(document, "心率变异性");
        PdfPTable pdfPTable = new PdfPTable(2);
        addTableHead(pdfPTable, "平均静息心率：bpm");
        String[] strArr = {"SDNN(ms)", " ", "SDANN(ms)", " ", "rMSSD(ms)", " ", "SDNN index(ms)", " ", "pNN50(%)", " "};
        for (int i = 0; i < 10; i++) {
            pdfPTable.addCell(getChatCell(new Paragraph(strArr[i], this.fontChinese), 40, false, false));
        }
        document.add(pdfPTable);
        document.add(Chunk.NEWLINE);
    }

    private void addTitle(Document document, String str) throws DocumentException {
        this.fontChinese.setSize(22.0f);
        this.fontChinese.setStyle(1);
        this.fontChinese.setColor(31, 122, 193);
        Paragraph paragraph = new Paragraph(str, this.fontChinese);
        paragraph.setAlignment(1);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);
    }

    private void addTableHead(PdfPTable pdfPTable, String str) {
        PdfPCell pdfPCell;
        this.fontChinese.setColor(BaseColor.BLACK);
        this.fontChinese.setSize(16.0f);
        this.fontChinese.setStyle(0);
        int numberOfColumns = pdfPTable.getNumberOfColumns();
        for (int i = 0; i < numberOfColumns; i++) {
            if (i == 0) {
                pdfPCell = new PdfPCell((Phrase) new Paragraph(str, this.fontChinese));
            } else {
                pdfPCell = new PdfPCell((Phrase) new Paragraph("", this.fontChinese));
            }
            pdfPCell.setFixedHeight(40.0f);
            pdfPCell.setVerticalAlignment(5);
            pdfPCell.setBorder(0);
            pdfPTable.addCell(pdfPCell);
        }
    }

    private void addSecondTitle(Document document, String[] strArr, int[] iArr) throws DocumentException {
        this.fontChinese.setColor(new BaseColor(31, 122, 193));
        this.fontChinese.setSize(20.0f);
        this.fontChinese.setStyle(1);
        Paragraph paragraph = new Paragraph(strArr[0], this.fontChinese);
        PdfPTable pdfPTable = new PdfPTable(2);
        if (iArr != null) {
            pdfPTable.setWidths(iArr);
        }
        PdfPCell pdfPCell = new PdfPCell((Phrase) paragraph);
        PdfPCell pdfPCell2 = new PdfPCell((Phrase) new Paragraph(strArr[1], this.fontChinese));
        pdfPCell.setBorder(0);
        pdfPCell2.setBorder(0);
        pdfPTable.addCell(pdfPCell);
        pdfPTable.addCell(pdfPCell2);
        document.add(pdfPTable);
        document.add(Chunk.NEWLINE);
    }

    /* access modifiers changed from: private */
    public void addReflexTable(Document document) throws DocumentException {
        addTitle(document, "心脏自主神经反射实验");
        this.fontChinese.setColor(BaseColor.BLACK);
        this.fontChinese.setSize(14.0f);
        this.fontChinese.setStyle(0);
        PdfPTable pdfPTable = new PdfPTable(7);
        pdfPTable.setWidths(new int[]{18, 12, 12, 21, 11, 14, 12});
        String[] strArr = {" ", "最长RR", "最短RR", "比值/心率差", "正常", "临界", "异常"};
        String[] strArr2 = {"卧立位心率变化", "第25-35个波形中最长RR", "第10-20个波形中最短RR", "最长RR/最短RR", "≥1.04", "1.01-1.03", "≤1.00"};
        String[] strArr3 = {"深呼吸心率差", "该段波形中最长RR", "该段波形中最短RR", "（60/最短RR）-（60/最长RR）", "≥15", "11-14", "≤10"};
        String[] strArr4 = {"Valsalva动作指数", "该段波形中最长RR", "该段波形中最短RR", "最长RR/最短RR", "≥1.21", "1.11-1.20", "≤1.10"};
        for (int i = 0; i < 7; i++) {
            pdfPTable.addCell(getChatCell(new Paragraph(strArr[i], this.fontChinese), 40, true, true));
        }
        for (int i2 = 0; i2 < 7; i2++) {
            pdfPTable.addCell(getChatCell(new Paragraph(strArr2[i2], this.fontChinese), 80, false, true));
        }
        for (int i3 = 0; i3 < 7; i3++) {
            pdfPTable.addCell(getChatCell(new Paragraph(strArr3[i3], this.fontChinese), 80, false, true));
        }
        for (int i4 = 0; i4 < 7; i4++) {
            pdfPTable.addCell(getChatCell(new Paragraph(strArr4[i4], this.fontChinese), 80, false, true));
        }
        document.add(pdfPTable);
        document.add(Chunk.NEWLINE);
    }

    private void addTitleImg(Document document) throws Exception {
        Image instance = Image.getInstance("图片路径");
        instance.scaleAbsolute(207.0f, 76.0f);
        instance.setAlignment(0);
        document.add(instance);
        document.add(Chunk.NEWLINE);
    }

    /* access modifiers changed from: private */
    public void createUserInfo(Document document) throws DocumentException {
        this.fontChinese.setSize(14.0f);
        this.fontChinese.setColor(BaseColor.BLACK);
        this.fontChinese.setStyle(0);
        PdfPTable pdfPTable = new PdfPTable(3);
        String[] strArr = {"姓名：名字", "性别：女", "年龄：18岁", "科室：科室", "住院号：123456", "时长：3min 50s"};
        Log.d("ddd", "长度：" + 6 + "第一个值：" + strArr[0]);
        pdfPTable.getDefaultCell().setBorder(0);
        for (int i = 0; i < 6; i++) {
            PdfPCell pdfPCell = new PdfPCell((Phrase) new Paragraph(strArr[i], this.fontChinese));
            pdfPCell.setBorder(0);
            pdfPCell.setHorizontalAlignment(0);
            pdfPCell.setFixedHeight(25.0f);
            pdfPCell.setVerticalAlignment(5);
            pdfPTable.addCell(pdfPCell);
        }
        document.add(pdfPTable);
        document.add(Chunk.NEWLINE);
    }

    private PdfPCell getChatCell(Paragraph paragraph, int i, boolean z, boolean z2) {
        PdfPCell pdfPCell = new PdfPCell((Phrase) paragraph);
        pdfPCell.setFixedHeight((float) i);
        if (z2) {
            pdfPCell.setHorizontalAlignment(1);
        } else {
            pdfPCell.setPaddingLeft(5.0f);
        }
        pdfPCell.setVerticalAlignment(5);
        pdfPCell.setBorderColor(new BaseColor(31, 122, 193, 125));
        if (z) {
            pdfPCell.setBackgroundColor(new BaseColor(31, 122, 193, 75));
        }
        return pdfPCell;
    }

    public Font setChineseFont() {
        try {
            return new Font(BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont, AsianFontMapper.ChineseSimplifiedEncoding_H, false), 12.0f, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font();
        }
    }
}
