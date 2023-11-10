package org.ahocorasick.trie;

import org.ahocorasick.interval.Interval;
import org.ahocorasick.interval.Intervalable;

public class Emit extends Interval implements Intervalable {
    private final String keyword;

    public Emit(int i, int i2, String str) {
        super(i, i2);
        this.keyword = str;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public String toString() {
        return super.toString() + "=" + this.keyword;
    }
}
