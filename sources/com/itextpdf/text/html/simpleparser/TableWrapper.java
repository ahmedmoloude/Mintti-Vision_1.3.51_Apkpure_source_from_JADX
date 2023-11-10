package com.itextpdf.text.html.simpleparser;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.ElementListener;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.html.HtmlUtilities;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class TableWrapper implements Element {
    private float[] colWidths;
    private final List<List<PdfPCell>> rows = new ArrayList();
    private final Map<String, String> styles;

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

    public TableWrapper(Map<String, String> map) {
        HashMap hashMap = new HashMap();
        this.styles = hashMap;
        hashMap.putAll(map);
    }

    public void addRow(List<PdfPCell> list) {
        if (list != null) {
            Collections.reverse(list);
            this.rows.add(list);
        }
    }

    public void setColWidths(float[] fArr) {
        this.colWidths = fArr;
    }

    public PdfPTable createTable() {
        if (this.rows.isEmpty()) {
            return new PdfPTable(1);
        }
        int i = 0;
        int i2 = 0;
        for (PdfPCell colspan : this.rows.get(0)) {
            i2 += colspan.getColspan();
        }
        PdfPTable pdfPTable = new PdfPTable(i2);
        String str = this.styles.get(HtmlTags.WIDTH);
        if (str == null) {
            pdfPTable.setWidthPercentage(100.0f);
        } else if (str.endsWith("%")) {
            pdfPTable.setWidthPercentage(Float.parseFloat(str.substring(0, str.length() - 1)));
        } else {
            pdfPTable.setTotalWidth(Float.parseFloat(str));
            pdfPTable.setLockedWidth(true);
        }
        String str2 = this.styles.get(HtmlTags.ALIGN);
        if (str2 != null) {
            i = HtmlUtilities.alignmentValue(str2);
        }
        pdfPTable.setHorizontalAlignment(i);
        try {
            float[] fArr = this.colWidths;
            if (fArr != null) {
                pdfPTable.setWidths(fArr);
            }
        } catch (Exception unused) {
        }
        for (List<PdfPCell> it : this.rows) {
            for (PdfPCell addCell : it) {
                pdfPTable.addCell(addCell);
            }
        }
        return pdfPTable;
    }
}
