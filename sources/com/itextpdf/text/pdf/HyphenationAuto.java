package com.itextpdf.text.pdf;

import com.itextpdf.text.pdf.hyphenation.Hyphenation;
import com.itextpdf.text.pdf.hyphenation.Hyphenator;

public class HyphenationAuto implements HyphenationEvent {
    protected Hyphenator hyphenator;
    protected String post;

    public String getHyphenSymbol() {
        return "-";
    }

    public HyphenationAuto(String str, String str2, int i, int i2) {
        this.hyphenator = new Hyphenator(str, str2, i, i2);
    }

    public String getHyphenatedWordPre(String str, BaseFont baseFont, float f, float f2) {
        Hyphenation hyphenate;
        this.post = str;
        String hyphenSymbol = getHyphenSymbol();
        float widthPoint = baseFont.getWidthPoint(hyphenSymbol, f);
        if (widthPoint > f2 || (hyphenate = this.hyphenator.hyphenate(str)) == null) {
            return "";
        }
        int length = hyphenate.length();
        int i = 0;
        while (i < length && baseFont.getWidthPoint(hyphenate.getPreHyphenText(i), f) + widthPoint <= f2) {
            i++;
        }
        int i2 = i - 1;
        if (i2 < 0) {
            return "";
        }
        this.post = hyphenate.getPostHyphenText(i2);
        return hyphenate.getPreHyphenText(i2) + hyphenSymbol;
    }

    public String getHyphenatedWordPost() {
        return this.post;
    }
}
