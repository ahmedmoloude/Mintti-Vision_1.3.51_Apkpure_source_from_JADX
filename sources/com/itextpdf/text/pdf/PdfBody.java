package com.itextpdf.text.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.ElementListener;
import com.itextpdf.text.Rectangle;
import java.util.List;

public class PdfBody extends Rectangle implements Element {
    public List<Chunk> getChunks() {
        return null;
    }

    public boolean isContent() {
        return false;
    }

    public boolean isNestable() {
        return false;
    }

    public boolean process(ElementListener elementListener) {
        return false;
    }

    public int type() {
        return 38;
    }

    public PdfBody(Rectangle rectangle) {
        super(rectangle);
    }
}
