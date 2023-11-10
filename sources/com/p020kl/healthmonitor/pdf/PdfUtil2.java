package com.p020kl.healthmonitor.pdf;

import android.util.Log;
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
import com.p020kl.commonbase.constants.Constants;
import java.io.File;
import java.io.FileOutputStream;

/* renamed from: com.kl.healthmonitor.pdf.PdfUtil2 */
public class PdfUtil2 {
    /* access modifiers changed from: private */
    public File file;
    /* access modifiers changed from: private */
    public Font fontChinese;

    public void createAndSharePdf() {
        new Thread(new Runnable() {
            public void run() {
                PdfUtil2 pdfUtil2 = PdfUtil2.this;
                File unused = pdfUtil2.file = new File(Constants.PDF_ROTE_PATH + "/测试.pdf");
                Document document = new Document(PageSize.f457A4, 5.0f, 5.0f, 10.0f, 10.0f);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(PdfUtil2.this.file);
                    PdfWriter.getInstance(document, fileOutputStream);
                    document.open();
                    document.setPageCount(1);
                    PdfUtil2 pdfUtil22 = PdfUtil2.this;
                    Font unused2 = pdfUtil22.fontChinese = pdfUtil22.setChineseFont();
                    PdfUtil2.this.fontChinese.setSize(24.0f);
                    PdfUtil2.this.fontChinese.setStyle(1);
                    PdfUtil2.this.fontChinese.setColor(0, 0, 0);
                    Paragraph paragraph = new Paragraph("苏州美糯爱医疗科技有限公司", PdfUtil2.this.fontChinese);
                    paragraph.setAlignment(1);
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE);
                    Paragraph paragraph2 = new Paragraph("动态心电分析报告", PdfUtil2.this.fontChinese);
                    PdfUtil2.this.fontChinese.setSize(18.0f);
                    PdfUtil2.this.fontChinese.setColor(0, 0, 0);
                    PdfUtil2.this.fontChinese.setStyle(0);
                    paragraph2.setAlignment(1);
                    document.add(paragraph2);
                    document.add(Chunk.NEWLINE);
                    PdfUtil2.this.addReflexTable(document);
                    PdfUtil2.this.addConclusion(document);
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
    public void addReflexTable(Document document) throws DocumentException {
        this.fontChinese.setColor(BaseColor.BLACK);
        this.fontChinese.setSize(10.0f);
        this.fontChinese.setStyle(1);
        PdfPTable pdfPTable = new PdfPTable(2);
        addUserInfo(pdfPTable);
        addHrvCell(pdfPTable);
        addEvent1(pdfPTable);
        addEvent2(pdfPTable);
        document.add(pdfPTable);
        document.add(Chunk.NEWLINE);
    }

    /* access modifiers changed from: private */
    public void addConclusion(Document document) throws DocumentException {
        PdfPTable pdfPTable = new PdfPTable(1);
        this.fontChinese.setSize(10.0f);
        this.fontChinese.setStyle(1);
        this.fontChinese.setColor(0, 0, 0);
        pdfPTable.addCell(getChatCell(new Paragraph("结论", this.fontChinese), 40, true));
        addConContent(pdfPTable);
        document.add(pdfPTable);
        document.add(Chunk.NEWLINE);
    }

    private void addConContent(PdfPTable pdfPTable) {
        String[] strArr = {"窦性心律", "房性早搏 部分呈二联律 偶见成对", "短阵房性心动过速", "单个室上早（10:45Am1）", "室上速（10:51Am1）", "心率变异性（HRV）SDNN 78 ms", "心室晚电位 阴性"};
        for (int i = 0; i < 7; i++) {
            PdfPCell chatCell = getChatCell(new Paragraph(strArr[i], this.fontChinese), 25, false);
            chatCell.disableBorderSide(2);
            chatCell.disableBorderSide(1);
            pdfPTable.addCell(chatCell);
        }
        this.fontChinese.setSize(10.0f);
        PdfPCell chatCell2 = getChatCell(new Paragraph("签名：用户名     报告日期：2022-01-14", this.fontChinese), 25, false);
        chatCell2.setPaddingRight(25.0f);
        chatCell2.setHorizontalAlignment(2);
        chatCell2.disableBorderSide(1);
        pdfPTable.addCell(chatCell2);
    }

    private void addEvent1(PdfPTable pdfPTable) {
        PdfPTable pdfPTable2 = pdfPTable;
        String[] strArr = {"室性事件", "室上性事件", "室性心搏总数: 0", "室上性心搏总数: 476", "成对室早: 0", "成对室上早: 1", "二联律总阵数: 0", "二联律总阵数: 31", "室速总阵数: 0", "室上速总阵数: 1", "最快室速心搏数: 0 发生于", "最快室上速心搏数: 5 发生于 10:51:54 -1", "最快室速心率(bpm): 0", "最快室上速心率(bpm): 170", "最长持续时间: 0:0:0 发生于", "最长持续时间: 0:0:1 发生于 10:51:54 -1"};
        PdfPCell chatCell = getChatCell(new Paragraph(strArr[0], this.fontChinese), 40, true);
        chatCell.disableBorderSide(2);
        pdfPTable2.addCell(chatCell);
        PdfPCell chatCell2 = getChatCell(new Paragraph(strArr[1], this.fontChinese), 40, true);
        chatCell2.disableBorderSide(2);
        pdfPTable2.addCell(chatCell2);
        for (int i = 2; i < 16; i++) {
            PdfPCell chatCell3 = getChatCell(new Paragraph(strArr[i], this.fontChinese), 25, false);
            if (i < 14) {
                chatCell3.disableBorderSide(2);
            }
            chatCell3.disableBorderSide(1);
            pdfPTable2.addCell(chatCell3);
        }
    }

    private void addEvent2(PdfPTable pdfPTable) {
        String[] strArr = {"心动过缓事件", "房扑/房颤事件", "总阵数: 0", "房扑/房颤总阵数: 0", "最长一阵心搏数: 0", "总时长: 0小时0.00分 占时比: 0.00%", "最长一阵: 发生于", "最快(bpm): 0 发生于", "最缓心率(bpm):0", "最慢(bpm): 0 发生于", "最缓:发生于", "最长持续时间: 0:0:0 发生于"};
        PdfPCell chatCell = getChatCell(new Paragraph(strArr[0], this.fontChinese), 40, true);
        chatCell.disableBorderSide(2);
        pdfPTable.addCell(chatCell);
        PdfPCell chatCell2 = getChatCell(new Paragraph(strArr[1], this.fontChinese), 40, true);
        chatCell2.disableBorderSide(2);
        pdfPTable.addCell(chatCell2);
        for (int i = 2; i < 12; i++) {
            PdfPCell chatCell3 = getChatCell(new Paragraph(strArr[i], this.fontChinese), 25, false);
            if (i < 10) {
                chatCell3.disableBorderSide(2);
            }
            chatCell3.disableBorderSide(1);
            pdfPTable.addCell(chatCell3);
        }
    }

    private void addHrvCell(PdfPTable pdfPTable) {
        String[] strArr = {"心率", "心率变异性", "总心搏数：2377", "SDNN(ms): 78", "平均心率(bpm)：75", "SDANN(ms): 43", "最快心率(bpm): 87 发生于 11:12:25 -1", "rMSSD(ms): 55", "最慢心率(bpm): 59 发生于 10:45:35 -1", "SDNN index(ms): 66", "停搏次数(>2.0s): 0", "pNN50(%): 8", "停最长停搏(s): 0.0 发生于", ""};
        PdfPCell chatCell = getChatCell(new Paragraph(strArr[0], this.fontChinese), 40, true);
        chatCell.disableBorderSide(2);
        pdfPTable.addCell(chatCell);
        PdfPCell chatCell2 = getChatCell(new Paragraph(strArr[1], this.fontChinese), 40, true);
        chatCell2.disableBorderSide(2);
        pdfPTable.addCell(chatCell2);
        for (int i = 2; i < 14; i++) {
            PdfPCell chatCell3 = getChatCell(new Paragraph(strArr[i], this.fontChinese), 25, false);
            if (i < 12) {
                chatCell3.disableBorderSide(2);
            }
            chatCell3.disableBorderSide(1);
            pdfPTable.addCell(chatCell3);
        }
    }

    private void addUserInfo(PdfPTable pdfPTable) {
        String[] strArr = {"姓名：自动分析测试            性别：男          年龄：56岁", "检测时间：自2022-01-13 10:45:01 至 2022-01-13 11:16:27 共0小时31分"};
        PdfPCell chatCell = getChatCell(new Paragraph(strArr[0], this.fontChinese), 40, false);
        chatCell.setColspan(2);
        chatCell.disableBorderSide(2);
        PdfPCell chatCell2 = getChatCell(new Paragraph(strArr[1], this.fontChinese), 40, false);
        chatCell2.setColspan(2);
        chatCell2.disableBorderSide(1);
        pdfPTable.addCell(chatCell);
        pdfPTable.addCell(chatCell2);
    }

    private PdfPCell getChatCell(Paragraph paragraph, int i, boolean z) {
        PdfPCell pdfPCell = new PdfPCell((Phrase) paragraph);
        pdfPCell.setFixedHeight((float) i);
        if (z) {
            pdfPCell.setHorizontalAlignment(1);
        } else {
            pdfPCell.setPaddingLeft(15.0f);
        }
        pdfPCell.setVerticalAlignment(5);
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
