package com.itextpdf.text.pdf.parser.clipper;

import com.itextpdf.text.pdf.parser.clipper.Point;
import com.itextpdf.text.pdf.parser.clipper.PolyNode;
import java.util.ArrayList;
import java.util.Iterator;

public class Paths extends ArrayList<Path> {
    private static final long serialVersionUID = 1910552127810480852L;

    public static Paths closedPathsFromPolyTree(PolyTree polyTree) {
        Paths paths = new Paths();
        paths.addPolyNode(polyTree, PolyNode.NodeType.CLOSED);
        return paths;
    }

    public static Paths makePolyTreeToPaths(PolyTree polyTree) {
        Paths paths = new Paths();
        paths.addPolyNode(polyTree, PolyNode.NodeType.ANY);
        return paths;
    }

    public static Paths openPathsFromPolyTree(PolyTree polyTree) {
        Paths paths = new Paths();
        for (PolyNode next : polyTree.getChilds()) {
            if (next.isOpen()) {
                paths.add(next.getPolygon());
            }
        }
        return paths;
    }

    public Paths() {
    }

    public Paths(int i) {
        super(i);
    }

    /* renamed from: com.itextpdf.text.pdf.parser.clipper.Paths$1 */
    static /* synthetic */ class C15331 {

        /* renamed from: $SwitchMap$com$itextpdf$text$pdf$parser$clipper$PolyNode$NodeType */
        static final /* synthetic */ int[] f758x75adf5da;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        static {
            /*
                com.itextpdf.text.pdf.parser.clipper.PolyNode$NodeType[] r0 = com.itextpdf.text.pdf.parser.clipper.PolyNode.NodeType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f758x75adf5da = r0
                com.itextpdf.text.pdf.parser.clipper.PolyNode$NodeType r1 = com.itextpdf.text.pdf.parser.clipper.PolyNode.NodeType.OPEN     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f758x75adf5da     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.pdf.parser.clipper.PolyNode$NodeType r1 = com.itextpdf.text.pdf.parser.clipper.PolyNode.NodeType.CLOSED     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.parser.clipper.Paths.C15331.<clinit>():void");
        }
    }

    public void addPolyNode(PolyNode polyNode, PolyNode.NodeType nodeType) {
        int i = C15331.f758x75adf5da[nodeType.ordinal()];
        boolean z = true;
        if (i != 1) {
            if (i == 2) {
                z = true ^ polyNode.isOpen();
            }
            if (polyNode.getPolygon().size() > 0 && z) {
                add(polyNode.getPolygon());
            }
            for (PolyNode addPolyNode : polyNode.getChilds()) {
                addPolyNode(addPolyNode, nodeType);
            }
        }
    }

    public Paths cleanPolygons() {
        return cleanPolygons(1.415d);
    }

    public Paths cleanPolygons(double d) {
        Paths paths = new Paths(size());
        for (int i = 0; i < size(); i++) {
            paths.add(((Path) get(i)).cleanPolygon(d));
        }
        return paths;
    }

    public LongRect getBounds() {
        int size = size();
        LongRect longRect = new LongRect();
        int i = 0;
        while (i < size && ((Path) get(i)).isEmpty()) {
            i++;
        }
        if (i == size) {
            return longRect;
        }
        longRect.left = ((Point.LongPoint) ((Path) get(i)).get(0)).getX();
        longRect.right = longRect.left;
        longRect.f2174top = ((Point.LongPoint) ((Path) get(i)).get(0)).getY();
        longRect.bottom = longRect.f2174top;
        while (i < size) {
            for (int i2 = 0; i2 < ((Path) get(i)).size(); i2++) {
                if (((Point.LongPoint) ((Path) get(i)).get(i2)).getX() < longRect.left) {
                    longRect.left = ((Point.LongPoint) ((Path) get(i)).get(i2)).getX();
                } else if (((Point.LongPoint) ((Path) get(i)).get(i2)).getX() > longRect.right) {
                    longRect.right = ((Point.LongPoint) ((Path) get(i)).get(i2)).getX();
                }
                if (((Point.LongPoint) ((Path) get(i)).get(i2)).getY() < longRect.f2174top) {
                    longRect.f2174top = ((Point.LongPoint) ((Path) get(i)).get(i2)).getY();
                } else if (((Point.LongPoint) ((Path) get(i)).get(i2)).getY() > longRect.bottom) {
                    longRect.bottom = ((Point.LongPoint) ((Path) get(i)).get(i2)).getY();
                }
            }
            i++;
        }
        return longRect;
    }

    public void reversePaths() {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Path) it.next()).reverse();
        }
    }
}
