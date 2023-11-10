package com.itextpdf.text;

import com.itextpdf.text.pdf.PdfChunk;

public class TabSplitCharacter implements SplitCharacter {
    public static final SplitCharacter TAB = new TabSplitCharacter();

    public boolean isSplitCharacter(int i, int i2, int i3, char[] cArr, PdfChunk[] pdfChunkArr) {
        return true;
    }
}
