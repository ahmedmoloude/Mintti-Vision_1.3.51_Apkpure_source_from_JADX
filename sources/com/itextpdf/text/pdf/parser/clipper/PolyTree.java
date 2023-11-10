package com.itextpdf.text.pdf.parser.clipper;

import java.util.ArrayList;
import java.util.List;

public class PolyTree extends PolyNode {
    private final List<PolyNode> allPolys = new ArrayList();

    public void Clear() {
        this.allPolys.clear();
        this.childs.clear();
    }

    public List<PolyNode> getAllPolys() {
        return this.allPolys;
    }

    public PolyNode getFirst() {
        if (!this.childs.isEmpty()) {
            return (PolyNode) this.childs.get(0);
        }
        return null;
    }

    public int getTotalSize() {
        int size = this.allPolys.size();
        return (size <= 0 || this.childs.get(0) == this.allPolys.get(0)) ? size : size - 1;
    }
}
