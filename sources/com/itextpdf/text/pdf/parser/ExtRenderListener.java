package com.itextpdf.text.pdf.parser;

public interface ExtRenderListener extends RenderListener {
    void clipPath(int i);

    void modifyPath(PathConstructionRenderInfo pathConstructionRenderInfo);

    Path renderPath(PathPaintingRenderInfo pathPaintingRenderInfo);
}
