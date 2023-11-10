package com.itextpdf.text.pdf.parser;

import com.itextpdf.awt.geom.Rectangle2D;

public class TextMarginFinder implements RenderListener {
    private Rectangle2D.Float textRectangle = null;

    public void beginTextBlock() {
    }

    public void endTextBlock() {
    }

    public void renderImage(ImageRenderInfo imageRenderInfo) {
    }

    public void renderText(TextRenderInfo textRenderInfo) {
        Rectangle2D.Float floatR = this.textRectangle;
        if (floatR == null) {
            this.textRectangle = textRenderInfo.getDescentLine().getBoundingRectange();
        } else {
            floatR.add((Rectangle2D) textRenderInfo.getDescentLine().getBoundingRectange());
        }
        this.textRectangle.add((Rectangle2D) textRenderInfo.getAscentLine().getBoundingRectange());
    }

    public float getLlx() {
        return this.textRectangle.f410x;
    }

    public float getLly() {
        return this.textRectangle.f411y;
    }

    public float getUrx() {
        return this.textRectangle.f410x + this.textRectangle.width;
    }

    public float getUry() {
        return this.textRectangle.f411y + this.textRectangle.height;
    }

    public float getWidth() {
        return this.textRectangle.width;
    }

    public float getHeight() {
        return this.textRectangle.height;
    }
}
