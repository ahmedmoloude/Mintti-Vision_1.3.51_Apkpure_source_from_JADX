package com.itextpdf.text;

import com.itextpdf.text.api.WriterOperation;
import java.util.ArrayList;
import java.util.List;

public abstract class WritableDirectElement implements Element, WriterOperation {
    public static final int DIRECT_ELEMENT_TYPE_HEADER = 1;
    public static final int DIRECT_ELEMENT_TYPE_UNKNOWN = 0;
    protected int directElementType = 0;

    public boolean isContent() {
        return false;
    }

    public int type() {
        return 666;
    }

    public WritableDirectElement() {
    }

    public WritableDirectElement(int i) {
        this.directElementType = i;
    }

    public boolean process(ElementListener elementListener) {
        throw new UnsupportedOperationException();
    }

    public boolean isNestable() {
        throw new UnsupportedOperationException();
    }

    public List<Chunk> getChunks() {
        return new ArrayList(0);
    }

    public int getDirectElementType() {
        return this.directElementType;
    }
}
