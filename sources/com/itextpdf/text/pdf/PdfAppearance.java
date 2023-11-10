package com.itextpdf.text.pdf;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.Rectangle;
import java.util.HashMap;

public class PdfAppearance extends PdfTemplate {
    public static final HashMap<String, PdfName> stdFieldFontNames;

    static {
        HashMap<String, PdfName> hashMap = new HashMap<>();
        stdFieldFontNames = hashMap;
        hashMap.put("Courier-BoldOblique", new PdfName("CoBO"));
        hashMap.put("Courier-Bold", new PdfName("CoBo"));
        hashMap.put("Courier-Oblique", new PdfName("CoOb"));
        hashMap.put("Courier", new PdfName("Cour"));
        hashMap.put("Helvetica-BoldOblique", new PdfName("HeBO"));
        hashMap.put("Helvetica-Bold", new PdfName("HeBo"));
        hashMap.put("Helvetica-Oblique", new PdfName("HeOb"));
        hashMap.put("Helvetica", PdfName.HELV);
        hashMap.put("Symbol", new PdfName("Symb"));
        hashMap.put("Times-BoldItalic", new PdfName("TiBI"));
        hashMap.put("Times-Bold", new PdfName("TiBo"));
        hashMap.put("Times-Italic", new PdfName("TiIt"));
        hashMap.put("Times-Roman", new PdfName("TiRo"));
        hashMap.put("ZapfDingbats", PdfName.ZADB);
        hashMap.put(AsianFontMapper.KoreanFont_SMyeongJo, new PdfName("HySm"));
        hashMap.put(AsianFontMapper.KoreanFont_GoThic, new PdfName("HyGo"));
        hashMap.put(AsianFontMapper.JapaneseFont_Go, new PdfName("KaGo"));
        hashMap.put(AsianFontMapper.JapaneseFont_Min, new PdfName("KaMi"));
        hashMap.put(AsianFontMapper.ChineseTraditionalFont_MHei, new PdfName("MHei"));
        hashMap.put(AsianFontMapper.ChineseTraditionalFont_MSung, new PdfName("MSun"));
        hashMap.put(AsianFontMapper.ChineseSimplifiedFont, new PdfName("STSo"));
        hashMap.put("MSungStd-Light", new PdfName("MSun"));
        hashMap.put("STSongStd-Light", new PdfName("STSo"));
        hashMap.put("HYSMyeongJoStd-Medium", new PdfName("HySm"));
        hashMap.put("KozMinPro-Regular", new PdfName("KaMi"));
    }

    PdfAppearance() {
        this.separator = 32;
    }

    PdfAppearance(PdfIndirectReference pdfIndirectReference) {
        this.thisReference = pdfIndirectReference;
    }

    PdfAppearance(PdfWriter pdfWriter) {
        super(pdfWriter);
        this.separator = 32;
    }

    public static PdfAppearance createAppearance(PdfWriter pdfWriter, float f, float f2) {
        return createAppearance(pdfWriter, f, f2, (PdfName) null);
    }

    static PdfAppearance createAppearance(PdfWriter pdfWriter, float f, float f2, PdfName pdfName) {
        PdfAppearance pdfAppearance = new PdfAppearance(pdfWriter);
        pdfAppearance.setWidth(f);
        pdfAppearance.setHeight(f2);
        pdfWriter.addDirectTemplateSimple(pdfAppearance, pdfName);
        return pdfAppearance;
    }

    public void setFontAndSize(BaseFont baseFont, float f) {
        checkWriter();
        this.state.size = f;
        if (baseFont.getFontType() == 4) {
            this.state.fontDetails = new FontDetails((PdfName) null, ((DocumentFont) baseFont).getIndirectReference(), baseFont);
        } else {
            this.state.fontDetails = this.writer.addSimple(baseFont);
        }
        PdfName pdfName = stdFieldFontNames.get(baseFont.getPostscriptFontName());
        if (pdfName == null) {
            if (!baseFont.isSubset() || baseFont.getFontType() != 3) {
                pdfName = new PdfName(baseFont.getPostscriptFontName());
                this.state.fontDetails.setSubset(false);
            } else {
                pdfName = this.state.fontDetails.getFontName();
            }
        }
        getPageResources().addFont(pdfName, this.state.fontDetails.getIndirectReference());
        this.content.append(pdfName.getBytes()).append(' ').append(f).append(" Tf").append_i(this.separator);
    }

    public PdfContentByte getDuplicate() {
        PdfAppearance pdfAppearance = new PdfAppearance();
        pdfAppearance.writer = this.writer;
        pdfAppearance.pdf = this.pdf;
        pdfAppearance.thisReference = this.thisReference;
        pdfAppearance.pageResources = this.pageResources;
        pdfAppearance.bBox = new Rectangle(this.bBox);
        pdfAppearance.group = this.group;
        pdfAppearance.layer = this.layer;
        if (this.matrix != null) {
            pdfAppearance.matrix = new PdfArray(this.matrix);
        }
        pdfAppearance.separator = this.separator;
        return pdfAppearance;
    }
}
