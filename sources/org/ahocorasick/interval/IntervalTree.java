package org.ahocorasick.interval;

import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class IntervalTree {
    private IntervalNode rootNode = null;

    public IntervalTree(List<Intervalable> list) {
        this.rootNode = new IntervalNode(list);
    }

    public List<Intervalable> removeOverlaps(List<Intervalable> list) {
        Collections.sort(list, new IntervalableComparatorBySize());
        TreeSet<Intervalable> treeSet = new TreeSet<>();
        for (Intervalable next : list) {
            if (!treeSet.contains(next)) {
                treeSet.addAll(findOverlaps(next));
            }
        }
        for (Intervalable remove : treeSet) {
            list.remove(remove);
        }
        Collections.sort(list, new IntervalableComparatorByPosition());
        return list;
    }

    public List<Intervalable> findOverlaps(Intervalable intervalable) {
        return this.rootNode.findOverlaps(intervalable);
    }
}
