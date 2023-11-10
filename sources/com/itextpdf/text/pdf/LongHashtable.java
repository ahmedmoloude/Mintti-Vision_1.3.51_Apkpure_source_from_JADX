package com.itextpdf.text.pdf;

import com.itextpdf.text.error_messages.MessageLocalization;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LongHashtable implements Cloneable {
    private transient int count;
    private float loadFactor;
    private transient Entry[] table;
    private int threshold;

    public LongHashtable() {
        this(150, 0.75f);
    }

    public LongHashtable(int i) {
        this(i, 0.75f);
    }

    public LongHashtable(int i, float f) {
        if (i < 0) {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("illegal.capacity.1", i));
        } else if (f > 0.0f) {
            i = i == 0 ? 1 : i;
            this.loadFactor = f;
            this.table = new Entry[i];
            this.threshold = (int) (((float) i) * f);
        } else {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("illegal.load.1", String.valueOf(f)));
        }
    }

    public int size() {
        return this.count;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public boolean contains(long j) {
        Entry[] entryArr = this.table;
        int length = entryArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return false;
            }
            for (Entry entry = entryArr[i]; entry != null; entry = entry.next) {
                if (entry.value == j) {
                    return true;
                }
            }
            length = i;
        }
    }

    public boolean containsValue(long j) {
        return contains(j);
    }

    public boolean containsKey(long j) {
        Entry[] entryArr = this.table;
        int i = (int) ((j >>> 32) ^ j);
        for (Entry entry = entryArr[(Integer.MAX_VALUE & i) % entryArr.length]; entry != null; entry = entry.next) {
            if (entry.hash == i && entry.key == j) {
                return true;
            }
        }
        return false;
    }

    public long get(long j) {
        Entry[] entryArr = this.table;
        int i = (int) ((j >>> 32) ^ j);
        for (Entry entry = entryArr[(Integer.MAX_VALUE & i) % entryArr.length]; entry != null; entry = entry.next) {
            if (entry.hash == i && entry.key == j) {
                return entry.value;
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void rehash() {
        Entry[] entryArr = this.table;
        int length = entryArr.length;
        int i = (length * 2) + 1;
        Entry[] entryArr2 = new Entry[i];
        this.threshold = (int) (((float) i) * this.loadFactor);
        this.table = entryArr2;
        while (true) {
            int i2 = length - 1;
            if (length > 0) {
                Entry entry = entryArr[i2];
                while (entry != null) {
                    Entry entry2 = entry.next;
                    int i3 = (entry.hash & Integer.MAX_VALUE) % i;
                    entry.next = entryArr2[i3];
                    entryArr2[i3] = entry;
                    entry = entry2;
                }
                length = i2;
            } else {
                return;
            }
        }
    }

    public long put(long j, long j2) {
        Entry[] entryArr = this.table;
        int i = (int) ((j >>> 32) ^ j);
        int i2 = Integer.MAX_VALUE & i;
        int length = i2 % entryArr.length;
        for (Entry entry = entryArr[length]; entry != null; entry = entry.next) {
            if (entry.hash == i && entry.key == j) {
                long j3 = entry.value;
                entry.value = j2;
                return j3;
            }
        }
        if (this.count >= this.threshold) {
            rehash();
            entryArr = this.table;
            length = i2 % entryArr.length;
        }
        entryArr[length] = new Entry(i, j, j2, entryArr[length]);
        this.count++;
        return 0;
    }

    public long remove(long j) {
        Entry[] entryArr = this.table;
        int i = (int) ((j >>> 32) ^ j);
        int length = (Integer.MAX_VALUE & i) % entryArr.length;
        Entry entry = null;
        for (Entry entry2 = entryArr[length]; entry2 != null; entry2 = entry2.next) {
            if (entry2.hash == i && entry2.key == j) {
                if (entry != null) {
                    entry.next = entry2.next;
                } else {
                    entryArr[length] = entry2.next;
                }
                this.count--;
                long j2 = entry2.value;
                entry2.value = 0;
                return j2;
            }
            entry = entry2;
        }
        return 0;
    }

    public void clear() {
        Entry[] entryArr = this.table;
        int length = entryArr.length;
        while (true) {
            length--;
            if (length >= 0) {
                entryArr[length] = null;
            } else {
                this.count = 0;
                return;
            }
        }
    }

    static class Entry {
        int hash;
        long key;
        Entry next;
        long value;

        protected Entry(int i, long j, long j2, Entry entry) {
            this.hash = i;
            this.key = j;
            this.value = j2;
            this.next = entry;
        }

        public long getKey() {
            return this.key;
        }

        public long getValue() {
            return this.value;
        }

        /* access modifiers changed from: protected */
        public Object clone() {
            int i = this.hash;
            long j = this.key;
            long j2 = this.value;
            Entry entry = this.next;
            return new Entry(i, j, j2, entry != null ? (Entry) entry.clone() : null);
        }
    }

    static class LongHashtableIterator implements Iterator<Entry> {
        Entry entry;
        int index;
        Entry[] table;

        LongHashtableIterator(Entry[] entryArr) {
            this.table = entryArr;
            this.index = entryArr.length;
        }

        public boolean hasNext() {
            Entry entry2;
            if (this.entry != null) {
                return true;
            }
            do {
                int i = this.index;
                int i2 = i - 1;
                this.index = i2;
                if (i <= 0) {
                    return false;
                }
                entry2 = this.table[i2];
                this.entry = entry2;
            } while (entry2 == null);
            return true;
        }

        public Entry next() {
            Entry entry2;
            if (this.entry == null) {
                do {
                    int i = this.index;
                    int i2 = i - 1;
                    this.index = i2;
                    if (i <= 0) {
                        break;
                    }
                    entry2 = this.table[i2];
                    this.entry = entry2;
                } while (entry2 == null);
            }
            Entry entry3 = this.entry;
            if (entry3 != null) {
                this.entry = entry3.next;
                return entry3;
            }
            throw new NoSuchElementException(MessageLocalization.getComposedMessage("inthashtableiterator", new Object[0]));
        }

        public void remove() {
            throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("remove.not.supported", new Object[0]));
        }
    }

    public Iterator<Entry> getEntryIterator() {
        return new LongHashtableIterator(this.table);
    }

    public long[] toOrderedKeys() {
        long[] keys = getKeys();
        Arrays.sort(keys);
        return keys;
    }

    public long[] getKeys() {
        int i;
        long[] jArr = new long[this.count];
        int length = this.table.length;
        int i2 = 0;
        Entry entry = null;
        while (true) {
            if (entry == null) {
                while (true) {
                    i = length - 1;
                    if (length <= 0 || (entry = this.table[i]) != null) {
                        length = i;
                    } else {
                        length = i;
                    }
                }
                length = i;
            }
            if (entry == null) {
                return jArr;
            }
            Entry entry2 = entry.next;
            jArr[i2] = entry.key;
            entry = entry2;
            i2++;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0019 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x001a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getOneKey() {
        /*
            r5 = this;
            int r0 = r5.count
            r1 = 0
            if (r0 != 0) goto L_0x0007
            return r1
        L_0x0007:
            com.itextpdf.text.pdf.LongHashtable$Entry[] r0 = r5.table
            int r0 = r0.length
            r3 = 0
        L_0x000b:
            int r4 = r0 + -1
            if (r0 <= 0) goto L_0x0017
            com.itextpdf.text.pdf.LongHashtable$Entry[] r0 = r5.table
            r3 = r0[r4]
            if (r3 != 0) goto L_0x0017
            r0 = r4
            goto L_0x000b
        L_0x0017:
            if (r3 != 0) goto L_0x001a
            return r1
        L_0x001a:
            long r0 = r3.key
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.LongHashtable.getOneKey():long");
    }

    public Object clone() {
        try {
            LongHashtable longHashtable = (LongHashtable) super.clone();
            longHashtable.table = new Entry[this.table.length];
            int length = this.table.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    return longHashtable;
                }
                Entry[] entryArr = longHashtable.table;
                Entry[] entryArr2 = this.table;
                entryArr[i] = entryArr2[i] != null ? (Entry) entryArr2[i].clone() : null;
                length = i;
            }
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }
}
