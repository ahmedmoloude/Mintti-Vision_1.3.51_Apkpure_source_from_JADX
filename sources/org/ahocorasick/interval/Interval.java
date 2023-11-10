package org.ahocorasick.interval;

public class Interval implements Intervalable {
    private int end;
    private int start;

    public Interval(int i, int i2) {
        this.start = i;
        this.end = i2;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public int size() {
        return (this.end - this.start) + 1;
    }

    public boolean overlapsWith(Interval interval) {
        return this.start <= interval.getEnd() && this.end >= interval.getStart();
    }

    public boolean overlapsWith(int i) {
        return this.start <= i && i <= this.end;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Intervalable)) {
            return false;
        }
        Intervalable intervalable = (Intervalable) obj;
        if (this.start == intervalable.getStart() && this.end == intervalable.getEnd()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.start % 100) + (this.end % 100);
    }

    public int compareTo(Object obj) {
        if (!(obj instanceof Intervalable)) {
            return -1;
        }
        Intervalable intervalable = (Intervalable) obj;
        int start2 = this.start - intervalable.getStart();
        return start2 != 0 ? start2 : this.end - intervalable.getEnd();
    }

    public String toString() {
        return this.start + ":" + this.end;
    }
}
