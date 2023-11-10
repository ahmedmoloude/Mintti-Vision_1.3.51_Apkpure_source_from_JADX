package com.itextpdf.text.pdf.parser;

public class FilteredTextRenderListener extends FilteredRenderListener implements TextExtractionStrategy {
    private final TextExtractionStrategy delegate;

    public FilteredTextRenderListener(TextExtractionStrategy textExtractionStrategy, RenderFilter... renderFilterArr) {
        super(textExtractionStrategy, renderFilterArr);
        this.delegate = textExtractionStrategy;
    }

    public String getResultantText() {
        return this.delegate.getResultantText();
    }
}
