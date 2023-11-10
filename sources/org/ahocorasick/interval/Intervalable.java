package org.ahocorasick.interval;

public interface Intervalable extends Comparable {
    int getEnd();

    int getStart();

    int size();
}
