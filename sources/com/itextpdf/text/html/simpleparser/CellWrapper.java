package com.itextpdf.text.html.simpleparser;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.ElementListener;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TextElementArray;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.html.HtmlUtilities;
import com.itextpdf.text.pdf.PdfPCell;
import java.util.List;

@Deprecated
public class CellWrapper implements TextElementArray {
    private final PdfPCell cell;
    private boolean percentage;
    private float width;

    public List<Chunk> getChunks() {
        return null;
    }

    public boolean isContent() {
        return false;
    }

    public boolean isNestable() {
        return false;
    }

    public boolean process(ElementListener elementListener) {
        return false;
    }

    public int type() {
        return 0;
    }

    public CellWrapper(String str, ChainedProperties chainedProperties) {
        this.cell = createPdfPCell(str, chainedProperties);
        String property = chainedProperties.getProperty(HtmlTags.WIDTH);
        if (property != null) {
            String trim = property.trim();
            if (trim.endsWith("%")) {
                this.percentage = true;
                trim = trim.substring(0, trim.length() - 1);
            }
            this.width = Float.parseFloat(trim);
        }
    }

    public PdfPCell createPdfPCell(String str, ChainedProperties chainedProperties) {
        PdfPCell pdfPCell = new PdfPCell((Phrase) null);
        String property = chainedProperties.getProperty(HtmlTags.COLSPAN);
        if (property != null) {
            pdfPCell.setColspan(Integer.parseInt(property));
        }
        String property2 = chainedProperties.getProperty(HtmlTags.ROWSPAN);
        if (property2 != null) {
            pdfPCell.setRowspan(Integer.parseInt(property2));
        }
        if (str.equals(HtmlTags.f492TH)) {
            pdfPCell.setHorizontalAlignment(1);
        }
        String property3 = chainedProperties.getProperty(HtmlTags.ALIGN);
        if (property3 != null) {
            pdfPCell.setHorizontalAlignment(HtmlUtilities.alignmentValue(property3));
        }
        String property4 = chainedProperties.getProperty(HtmlTags.VALIGN);
        pdfPCell.setVerticalAlignment(5);
        if (property4 != null) {
            pdfPCell.setVerticalAlignment(HtmlUtilities.alignmentValue(property4));
        }
        String property5 = chainedProperties.getProperty(HtmlTags.BORDER);
        float f = 0.0f;
        if (property5 != null) {
            f = Float.parseFloat(property5);
        }
        pdfPCell.setBorderWidth(f);
        String property6 = chainedProperties.getProperty(HtmlTags.CELLPADDING);
        if (property6 != null) {
            pdfPCell.setPadding(Float.parseFloat(property6));
        }
        pdfPCell.setUseDescender(true);
        pdfPCell.setBackgroundColor(HtmlUtilities.decodeColor(chainedProperties.getProperty(HtmlTags.BGCOLOR)));
        return pdfPCell;
    }

    public PdfPCell getCell() {
        return this.cell;
    }

    public float getWidth() {
        return this.width;
    }

    public boolean isPercentage() {
        return this.percentage;
    }

    public boolean add(Element element) {
        this.cell.addElement(element);
        return true;
    }
}
