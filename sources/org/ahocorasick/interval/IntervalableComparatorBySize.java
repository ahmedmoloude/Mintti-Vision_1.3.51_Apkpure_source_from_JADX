package org.ahocorasick.interval;

import java.util.Comparator;

public class IntervalableComparatorBySize implements Comparator<Intervalable> {
    public int compare(Intervalable intervalable, Intervalable intervalable2) {
        int size = intervalable2.size() - intervalable.size();
        return size == 0 ? intervalable.getStart() - intervalable2.getStart() : size;
    }
}
