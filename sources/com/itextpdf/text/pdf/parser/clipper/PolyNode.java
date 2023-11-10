package com.itextpdf.text.pdf.parser.clipper;

import com.itextpdf.text.pdf.parser.clipper.Clipper;
import com.itextpdf.text.pdf.parser.clipper.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PolyNode {
    protected final List<PolyNode> childs = new ArrayList();
    private Clipper.EndType endType;
    private int index;
    private boolean isOpen;
    private Clipper.JoinType joinType;
    private PolyNode parent;
    private final Path polygon = new Path();

    enum NodeType {
        ANY,
        OPEN,
        CLOSED
    }

    public void addChild(PolyNode polyNode) {
        int size = this.childs.size();
        this.childs.add(polyNode);
        polyNode.parent = this;
        polyNode.index = size;
    }

    public int getChildCount() {
        return this.childs.size();
    }

    public List<PolyNode> getChilds() {
        return Collections.unmodifiableList(this.childs);
    }

    public List<Point.LongPoint> getContour() {
        return this.polygon;
    }

    public Clipper.EndType getEndType() {
        return this.endType;
    }

    public Clipper.JoinType getJoinType() {
        return this.joinType;
    }

    public PolyNode getNext() {
        if (!this.childs.isEmpty()) {
            return this.childs.get(0);
        }
        return getNextSiblingUp();
    }

    private PolyNode getNextSiblingUp() {
        PolyNode polyNode = this.parent;
        if (polyNode == null) {
            return null;
        }
        if (this.index == polyNode.childs.size() - 1) {
            return this.parent.getNextSiblingUp();
        }
        return this.parent.childs.get(this.index + 1);
    }

    public PolyNode getParent() {
        return this.parent;
    }

    public Path getPolygon() {
        return this.polygon;
    }

    public boolean isHole() {
        return isHoleNode();
    }

    private boolean isHoleNode() {
        boolean z = true;
        for (PolyNode polyNode = this.parent; polyNode != null; polyNode = polyNode.parent) {
            z = !z;
        }
        return z;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setEndType(Clipper.EndType endType2) {
        this.endType = endType2;
    }

    public void setJoinType(Clipper.JoinType joinType2) {
        this.joinType = joinType2;
    }

    public void setOpen(boolean z) {
        this.isOpen = z;
    }

    public void setParent(PolyNode polyNode) {
        this.parent = polyNode;
    }
}
