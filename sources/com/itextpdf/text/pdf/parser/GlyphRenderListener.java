package com.itextpdf.text.pdf.parser;

public class GlyphRenderListener implements RenderListener {
    private final RenderListener delegate;

    public GlyphRenderListener(RenderListener renderListener) {
        this.delegate = renderListener;
    }

    public void beginTextBlock() {
        this.delegate.beginTextBlock();
    }

    public void renderText(TextRenderInfo textRenderInfo) {
        for (TextRenderInfo renderText : textRenderInfo.getCharacterRenderInfos()) {
            this.delegate.renderText(renderText);
        }
    }

    public void endTextBlock() {
        this.delegate.endTextBlock();
    }

    public void renderImage(ImageRenderInfo imageRenderInfo) {
        this.delegate.renderImage(imageRenderInfo);
    }
}
