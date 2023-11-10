package com.itextpdf.text;

import java.io.Serializable;

public class AccessibleElementId implements Comparable<AccessibleElementId>, Serializable {
    private static int id_counter;

    /* renamed from: id */
    private int f435id = 0;

    public AccessibleElementId() {
        int i = id_counter + 1;
        id_counter = i;
        this.f435id = i;
    }

    public String toString() {
        return Integer.toString(this.f435id);
    }

    public int hashCode() {
        return this.f435id;
    }

    public boolean equals(Object obj) {
        return (obj instanceof AccessibleElementId) && this.f435id == ((AccessibleElementId) obj).f435id;
    }

    public int compareTo(AccessibleElementId accessibleElementId) {
        int i = this.f435id;
        int i2 = accessibleElementId.f435id;
        if (i < i2) {
            return -1;
        }
        return i > i2 ? 1 : 0;
    }
}
