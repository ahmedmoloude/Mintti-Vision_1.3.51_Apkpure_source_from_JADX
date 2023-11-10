package com.itextpdf.text.pdf.parser;

public class MarkedContentRenderFilter extends RenderFilter {
    private int mcid;

    public MarkedContentRenderFilter(int i) {
        this.mcid = i;
    }

    public boolean allowText(TextRenderInfo textRenderInfo) {
        return textRenderInfo.hasMcid(this.mcid);
    }
}
