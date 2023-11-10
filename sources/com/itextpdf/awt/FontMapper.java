package com.itextpdf.awt;

import com.itextpdf.text.pdf.BaseFont;
import java.awt.Font;

public interface FontMapper {
    BaseFont awtToPdf(Font font);

    Font pdfToAwt(BaseFont baseFont, int i);
}
