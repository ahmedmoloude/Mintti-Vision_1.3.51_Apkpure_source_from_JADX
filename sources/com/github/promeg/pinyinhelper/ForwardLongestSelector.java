package com.github.promeg.pinyinhelper;

import com.github.promeg.pinyinhelper.Engine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import org.ahocorasick.trie.Emit;

final class ForwardLongestSelector implements SegmentationSelector {
    static final Engine.EmitComparator HIT_COMPARATOR = new Engine.EmitComparator();

    ForwardLongestSelector() {
    }

    public List<Emit> select(Collection<Emit> collection) {
        if (collection == null) {
            return null;
        }
        ArrayList<Emit> arrayList = new ArrayList<>(collection);
        Collections.sort(arrayList, HIT_COMPARATOR);
        int i = -1;
        TreeSet treeSet = new TreeSet();
        for (Emit emit : arrayList) {
            if (emit.getStart() <= i || emit.getEnd() <= i) {
                treeSet.add(emit);
            } else {
                i = emit.getEnd();
            }
        }
        arrayList.removeAll(treeSet);
        return arrayList;
    }
}
