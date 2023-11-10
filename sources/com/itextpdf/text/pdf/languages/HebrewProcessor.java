package com.itextpdf.text.pdf.languages;

import com.itextpdf.text.pdf.BidiLine;

public class HebrewProcessor implements LanguageProcessor {
    protected int runDirection = 3;

    public boolean isRTL() {
        return true;
    }

    public HebrewProcessor() {
    }

    public HebrewProcessor(int i) {
        this.runDirection = i;
    }

    public String process(String str) {
        return BidiLine.processLTR(str, this.runDirection, 0);
    }
}
