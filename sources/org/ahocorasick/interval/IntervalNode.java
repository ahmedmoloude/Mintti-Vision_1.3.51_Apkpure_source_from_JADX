package org.ahocorasick.interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntervalNode {
    private List<Intervalable> intervals = new ArrayList();
    private IntervalNode left = null;
    private int point;
    private IntervalNode right = null;

    private enum Direction {
        LEFT,
        RIGHT
    }

    public IntervalNode(List<Intervalable> list) {
        this.point = determineMedian(list);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Intervalable next : list) {
            if (next.getEnd() < this.point) {
                arrayList.add(next);
            } else if (next.getStart() > this.point) {
                arrayList2.add(next);
            } else {
                this.intervals.add(next);
            }
        }
        if (arrayList.size() > 0) {
            this.left = new IntervalNode(arrayList);
        }
        if (arrayList2.size() > 0) {
            this.right = new IntervalNode(arrayList2);
        }
    }

    public int determineMedian(List<Intervalable> list) {
        int i = -1;
        int i2 = -1;
        for (Intervalable next : list) {
            int start = next.getStart();
            int end = next.getEnd();
            if (i == -1 || start < i) {
                i = start;
            }
            if (i2 == -1 || end > i2) {
                i2 = end;
            }
        }
        return (i + i2) / 2;
    }

    public List<Intervalable> findOverlaps(Intervalable intervalable) {
        ArrayList arrayList = new ArrayList();
        if (this.point < intervalable.getStart()) {
            addToOverlaps(intervalable, arrayList, findOverlappingRanges(this.right, intervalable));
            addToOverlaps(intervalable, arrayList, checkForOverlapsToTheRight(intervalable));
        } else if (this.point > intervalable.getEnd()) {
            addToOverlaps(intervalable, arrayList, findOverlappingRanges(this.left, intervalable));
            addToOverlaps(intervalable, arrayList, checkForOverlapsToTheLeft(intervalable));
        } else {
            addToOverlaps(intervalable, arrayList, this.intervals);
            addToOverlaps(intervalable, arrayList, findOverlappingRanges(this.left, intervalable));
            addToOverlaps(intervalable, arrayList, findOverlappingRanges(this.right, intervalable));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void addToOverlaps(Intervalable intervalable, List<Intervalable> list, List<Intervalable> list2) {
        for (Intervalable next : list2) {
            if (!next.equals(intervalable)) {
                list.add(next);
            }
        }
    }

    /* access modifiers changed from: protected */
    public List<Intervalable> checkForOverlapsToTheLeft(Intervalable intervalable) {
        return checkForOverlaps(intervalable, Direction.LEFT);
    }

    /* access modifiers changed from: protected */
    public List<Intervalable> checkForOverlapsToTheRight(Intervalable intervalable) {
        return checkForOverlaps(intervalable, Direction.RIGHT);
    }

    /* access modifiers changed from: protected */
    public List<Intervalable> checkForOverlaps(Intervalable intervalable, Direction direction) {
        ArrayList arrayList = new ArrayList();
        for (Intervalable next : this.intervals) {
            int i = C25801.$SwitchMap$org$ahocorasick$interval$IntervalNode$Direction[direction.ordinal()];
            if (i != 1) {
                if (i == 2 && next.getEnd() >= intervalable.getStart()) {
                    arrayList.add(next);
                }
            } else if (next.getStart() <= intervalable.getEnd()) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    /* renamed from: org.ahocorasick.interval.IntervalNode$1 */
    static /* synthetic */ class C25801 {
        static final /* synthetic */ int[] $SwitchMap$org$ahocorasick$interval$IntervalNode$Direction;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        static {
            /*
                org.ahocorasick.interval.IntervalNode$Direction[] r0 = org.ahocorasick.interval.IntervalNode.Direction.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$ahocorasick$interval$IntervalNode$Direction = r0
                org.ahocorasick.interval.IntervalNode$Direction r1 = org.ahocorasick.interval.IntervalNode.Direction.LEFT     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$org$ahocorasick$interval$IntervalNode$Direction     // Catch:{ NoSuchFieldError -> 0x001d }
                org.ahocorasick.interval.IntervalNode$Direction r1 = org.ahocorasick.interval.IntervalNode.Direction.RIGHT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.ahocorasick.interval.IntervalNode.C25801.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public List<Intervalable> findOverlappingRanges(IntervalNode intervalNode, Intervalable intervalable) {
        if (intervalNode != null) {
            return intervalNode.findOverlaps(intervalable);
        }
        return Collections.emptyList();
    }
}
