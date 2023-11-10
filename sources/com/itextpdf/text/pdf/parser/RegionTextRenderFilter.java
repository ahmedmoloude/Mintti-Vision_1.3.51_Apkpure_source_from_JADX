package com.itextpdf.text.pdf.parser;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.Rectangle;

public class RegionTextRenderFilter extends RenderFilter {
    private final Rectangle2D filterRect;

    public RegionTextRenderFilter(Rectangle2D rectangle2D) {
        this.filterRect = rectangle2D;
    }

    public RegionTextRenderFilter(Rectangle rectangle) {
        this.filterRect = new com.itextpdf.awt.geom.Rectangle(rectangle);
    }

    public boolean allowText(TextRenderInfo textRenderInfo) {
        LineSegment baseline = textRenderInfo.getBaseline();
        Vector startPoint = baseline.getStartPoint();
        Vector endPoint = baseline.getEndPoint();
        return this.filterRect.intersectsLine((double) startPoint.get(0), (double) startPoint.get(1), (double) endPoint.get(0), (double) endPoint.get(1));
    }
}
