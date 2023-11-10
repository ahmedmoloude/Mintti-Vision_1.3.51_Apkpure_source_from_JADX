package androidx.test.espresso.core.internal.deps.guava.cache;

import androidx.test.espresso.core.internal.deps.guava.base.Equivalence;
import androidx.test.espresso.core.internal.deps.guava.base.Function;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.base.Stopwatch;
import androidx.test.espresso.core.internal.deps.guava.base.Ticker;
import androidx.test.espresso.core.internal.deps.guava.cache.CacheBuilder;
import androidx.test.espresso.core.internal.deps.guava.collect.AbstractSequentialIterator;
import androidx.test.espresso.core.internal.deps.guava.collect.ImmutableSet;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterators;
import androidx.test.espresso.core.internal.deps.guava.primitives.Ints;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.Futures;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListenableFuture;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.MoreExecutors;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.SettableFuture;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.Uninterruptibles;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
    static final Queue<?> DISCARDING_QUEUE = new AbstractQueue<Object>() {
        public Iterator<Object> iterator() {
            return ImmutableSet.m75of().iterator();
        }

        public boolean offer(Object obj) {
            return true;
        }

        public Object peek() {
            return null;
        }

        public Object poll() {
            return null;
        }

        public int size() {
            return 0;
        }
    };
    static final ValueReference<Object, Object> UNSET = new ValueReference<Object, Object>() {
        public ValueReference<Object, Object> copyFor(ReferenceQueue<Object> referenceQueue, Object obj, ReferenceEntry<Object, Object> referenceEntry) {
            return this;
        }

        public Object get() {
            return null;
        }

        public ReferenceEntry<Object, Object> getEntry() {
            return null;
        }

        public int getWeight() {
            return 0;
        }

        public boolean isActive() {
            return false;
        }

        public boolean isLoading() {
            return false;
        }

        public void notifyNewValue(Object obj) {
        }
    };
    static final Logger logger = Logger.getLogger(LocalCache.class.getName());
    final int concurrencyLevel;
    final CacheLoader<? super K, V> defaultLoader;
    final EntryFactory entryFactory;
    Set<Map.Entry<K, V>> entrySet;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final AbstractCache$StatsCounter globalStatsCounter;
    final Equivalence<Object> keyEquivalence;
    Set<K> keySet;
    final Strength keyStrength;
    final long maxWeight;
    final long refreshNanos;
    final RemovalListener<K, V> removalListener;
    final Queue<RemovalNotification<K, V>> removalNotificationQueue;
    final int segmentMask;
    final int segmentShift;
    final Segment<K, V>[] segments;
    final Ticker ticker;
    final Equivalence<Object> valueEquivalence;
    final Strength valueStrength;
    Collection<V> values;
    final Weigher<K, V> weigher;

    abstract class AbstractCacheSet<T> extends AbstractSet<T> {
        AbstractCacheSet() {
        }

        public void clear() {
            LocalCache.this.clear();
        }

        public boolean isEmpty() {
            return LocalCache.this.isEmpty();
        }

        public int size() {
            return LocalCache.this.size();
        }

        public Object[] toArray() {
            return LocalCache.toArrayList(this).toArray();
        }

        public <E> E[] toArray(E[] eArr) {
            return LocalCache.toArrayList(this).toArray(eArr);
        }
    }

    static abstract class AbstractReferenceEntry<K, V> implements ReferenceEntry<K, V> {
        AbstractReferenceEntry() {
        }

        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        public int getHash() {
            throw new UnsupportedOperationException();
        }

        public K getKey() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNext() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }

        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        public void setAccessTime(long j) {
            throw new UnsupportedOperationException();
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setValueReference(ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }

        public void setWriteTime(long j) {
            throw new UnsupportedOperationException();
        }
    }

    static final class AccessQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>(this) {
            ReferenceEntry<K, V> nextAccess = this;
            ReferenceEntry<K, V> previousAccess = this;

            public long getAccessTime() {
                return Long.MAX_VALUE;
            }

            public ReferenceEntry<K, V> getNextInAccessQueue() {
                return this.nextAccess;
            }

            public ReferenceEntry<K, V> getPreviousInAccessQueue() {
                return this.previousAccess;
            }

            public void setAccessTime(long j) {
            }

            public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.nextAccess = referenceEntry;
            }

            public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.previousAccess = referenceEntry;
            }
        };

        AccessQueue() {
        }

        public void clear() {
            ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue();
            while (true) {
                ReferenceEntry<K, V> referenceEntry = this.head;
                if (nextInAccessQueue != referenceEntry) {
                    ReferenceEntry<K, V> nextInAccessQueue2 = nextInAccessQueue.getNextInAccessQueue();
                    LocalCache.nullifyAccessOrder(nextInAccessQueue);
                    nextInAccessQueue = nextInAccessQueue2;
                } else {
                    referenceEntry.setNextInAccessQueue(referenceEntry);
                    ReferenceEntry<K, V> referenceEntry2 = this.head;
                    referenceEntry2.setPreviousInAccessQueue(referenceEntry2);
                    return;
                }
            }
        }

        public boolean contains(Object obj) {
            return ((ReferenceEntry) obj).getNextInAccessQueue() != NullEntry.INSTANCE;
        }

        public boolean isEmpty() {
            return this.head.getNextInAccessQueue() == this.head;
        }

        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) {
                /* access modifiers changed from: protected */
                public ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> referenceEntry) {
                    ReferenceEntry<K, V> nextInAccessQueue = referenceEntry.getNextInAccessQueue();
                    if (nextInAccessQueue == AccessQueue.this.head) {
                        return null;
                    }
                    return nextInAccessQueue;
                }
            };
        }

        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry.getNextInAccessQueue());
            LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), referenceEntry);
            LocalCache.connectAccessOrder(referenceEntry, this.head);
            return true;
        }

        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue();
            if (nextInAccessQueue == this.head) {
                return null;
            }
            return nextInAccessQueue;
        }

        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue();
            if (nextInAccessQueue == this.head) {
                return null;
            }
            remove(nextInAccessQueue);
            return nextInAccessQueue;
        }

        public boolean remove(Object obj) {
            ReferenceEntry referenceEntry = (ReferenceEntry) obj;
            ReferenceEntry previousInAccessQueue = referenceEntry.getPreviousInAccessQueue();
            ReferenceEntry nextInAccessQueue = referenceEntry.getNextInAccessQueue();
            LocalCache.connectAccessOrder(previousInAccessQueue, nextInAccessQueue);
            LocalCache.nullifyAccessOrder(referenceEntry);
            return nextInAccessQueue != NullEntry.INSTANCE;
        }

        public int size() {
            int i = 0;
            for (ReferenceEntry<K, V> nextInAccessQueue = this.head.getNextInAccessQueue(); nextInAccessQueue != this.head; nextInAccessQueue = nextInAccessQueue.getNextInAccessQueue()) {
                i++;
            }
            return i;
        }
    }

    enum EntryFactory {
        STRONG {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, ReferenceEntry<K, V> referenceEntry) {
                return new StrongEntry(k, i, referenceEntry);
            }
        },
        STRONG_ACCESS {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, copyEntry);
                return copyEntry;
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessEntry(k, i, referenceEntry);
            }
        },
        STRONG_WRITE {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyWriteEntry(referenceEntry, copyEntry);
                return copyEntry;
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, ReferenceEntry<K, V> referenceEntry) {
                return new StrongWriteEntry(k, i, referenceEntry);
            }
        },
        STRONG_ACCESS_WRITE {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, copyEntry);
                copyWriteEntry(referenceEntry, copyEntry);
                return copyEntry;
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, ReferenceEntry<K, V> referenceEntry) {
                return new StrongAccessWriteEntry(k, i, referenceEntry);
            }
        },
        WEAK {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, ReferenceEntry<K, V> referenceEntry) {
                return new WeakEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        },
        WEAK_ACCESS {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, copyEntry);
                return copyEntry;
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        },
        WEAK_WRITE {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyWriteEntry(referenceEntry, copyEntry);
                return copyEntry;
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, ReferenceEntry<K, V> referenceEntry) {
                return new WeakWriteEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        },
        WEAK_ACCESS_WRITE {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                ReferenceEntry<K, V> copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                copyAccessEntry(referenceEntry, copyEntry);
                copyWriteEntry(referenceEntry, copyEntry);
                return copyEntry;
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, ReferenceEntry<K, V> referenceEntry) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, k, i, referenceEntry);
            }
        };
        
        static final EntryFactory[] factories = null;

        static {
            C07241 r0;
            C07252 r1;
            C07263 r3;
            C07274 r5;
            C07285 r7;
            C07296 r9;
            C07307 r11;
            C07318 r13;
            factories = new EntryFactory[]{r0, r1, r3, r5, r7, r9, r11, r13};
        }

        static EntryFactory getFactory(Strength strength, boolean z, boolean z2) {
            char c = 0;
            boolean z3 = (strength == Strength.WEAK ? (char) 4 : 0) | z;
            if (z2) {
                c = 2;
            }
            return factories[z3 | c];
        }

        /* access modifiers changed from: package-private */
        public <K, V> void copyAccessEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setAccessTime(referenceEntry.getAccessTime());
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry2);
            LocalCache.connectAccessOrder(referenceEntry2, referenceEntry.getNextInAccessQueue());
            LocalCache.nullifyAccessOrder(referenceEntry);
        }

        /* access modifiers changed from: package-private */
        public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            return newEntry(segment, referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry2);
        }

        /* access modifiers changed from: package-private */
        public <K, V> void copyWriteEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setWriteTime(referenceEntry.getWriteTime());
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry2);
            LocalCache.connectWriteOrder(referenceEntry2, referenceEntry.getNextInWriteQueue());
            LocalCache.nullifyWriteOrder(referenceEntry);
        }

        /* access modifiers changed from: package-private */
        public abstract <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, ReferenceEntry<K, V> referenceEntry);
    }

    final class EntryIterator extends LocalCache<K, V>.HashIterator<Map.Entry<K, V>> {
        EntryIterator(LocalCache localCache) {
            super();
        }

        public Map.Entry<K, V> next() {
            return nextEntry();
        }
    }

    final class EntrySet extends LocalCache<K, V>.AbstractCacheSet<Map.Entry<K, V>> {
        EntrySet() {
            super();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
            r4 = (java.util.Map.Entry) r4;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean contains(java.lang.Object r4) {
            /*
                r3 = this;
                boolean r0 = r4 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                java.util.Map$Entry r4 = (java.util.Map.Entry) r4
                java.lang.Object r0 = r4.getKey()
                if (r0 != 0) goto L_0x000f
                return r1
            L_0x000f:
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache r2 = androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.this
                java.lang.Object r0 = r2.get(r0)
                if (r0 == 0) goto L_0x0026
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache r2 = androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.this
                androidx.test.espresso.core.internal.deps.guava.base.Equivalence<java.lang.Object> r2 = r2.valueEquivalence
                java.lang.Object r4 = r4.getValue()
                boolean r4 = r2.equivalent(r4, r0)
                if (r4 == 0) goto L_0x0026
                r1 = 1
            L_0x0026:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.EntrySet.contains(java.lang.Object):boolean");
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator(LocalCache.this);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
            r4 = (java.util.Map.Entry) r4;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean remove(java.lang.Object r4) {
            /*
                r3 = this;
                boolean r0 = r4 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                java.util.Map$Entry r4 = (java.util.Map.Entry) r4
                java.lang.Object r0 = r4.getKey()
                if (r0 == 0) goto L_0x001b
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache r2 = androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.this
                java.lang.Object r4 = r4.getValue()
                boolean r4 = r2.remove(r0, r4)
                if (r4 == 0) goto L_0x001b
                r1 = 1
            L_0x001b:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.EntrySet.remove(java.lang.Object):boolean");
        }
    }

    abstract class HashIterator<T> implements Iterator<T> {
        Segment<K, V> currentSegment;
        AtomicReferenceArray<ReferenceEntry<K, V>> currentTable;
        LocalCache<K, V>.WriteThroughEntry lastReturned;
        ReferenceEntry<K, V> nextEntry;
        LocalCache<K, V>.WriteThroughEntry nextExternal;
        int nextSegmentIndex;
        int nextTableIndex = -1;

        HashIterator() {
            this.nextSegmentIndex = LocalCache.this.segments.length - 1;
            advance();
        }

        /* access modifiers changed from: package-private */
        public final void advance() {
            this.nextExternal = null;
            if (!nextInChain() && !nextInTable()) {
                while (this.nextSegmentIndex >= 0) {
                    Segment<K, V>[] segmentArr = LocalCache.this.segments;
                    int i = this.nextSegmentIndex;
                    this.nextSegmentIndex = i - 1;
                    Segment<K, V> segment = segmentArr[i];
                    this.currentSegment = segment;
                    if (segment.count != 0) {
                        AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.currentSegment.table;
                        this.currentTable = atomicReferenceArray;
                        this.nextTableIndex = atomicReferenceArray.length() - 1;
                        if (nextInTable()) {
                            return;
                        }
                    }
                }
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public boolean advanceTo(ReferenceEntry<K, V> referenceEntry) {
            try {
                long read = LocalCache.this.ticker.read();
                K key = referenceEntry.getKey();
                V liveValue = LocalCache.this.getLiveValue(referenceEntry, read);
                if (liveValue != null) {
                    this.nextExternal = new WriteThroughEntry(key, liveValue);
                    this.currentSegment.postReadCleanup();
                    return true;
                }
                this.currentSegment.postReadCleanup();
                return false;
            } catch (Throwable th) {
                this.currentSegment.postReadCleanup();
                throw th;
            }
        }

        public boolean hasNext() {
            return this.nextExternal != null;
        }

        /* access modifiers changed from: package-private */
        public LocalCache<K, V>.WriteThroughEntry nextEntry() {
            LocalCache<K, V>.WriteThroughEntry writeThroughEntry = this.nextExternal;
            if (writeThroughEntry != null) {
                this.lastReturned = writeThroughEntry;
                advance();
                return this.lastReturned;
            }
            throw new NoSuchElementException();
        }

        /* access modifiers changed from: package-private */
        public boolean nextInChain() {
            ReferenceEntry<K, V> referenceEntry = this.nextEntry;
            if (referenceEntry == null) {
                return false;
            }
            while (true) {
                this.nextEntry = referenceEntry.getNext();
                ReferenceEntry<K, V> referenceEntry2 = this.nextEntry;
                if (referenceEntry2 == null) {
                    return false;
                }
                if (advanceTo(referenceEntry2)) {
                    return true;
                }
                referenceEntry = this.nextEntry;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean nextInTable() {
            while (true) {
                int i = this.nextTableIndex;
                if (i < 0) {
                    return false;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.currentTable;
                this.nextTableIndex = i - 1;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(i);
                this.nextEntry = referenceEntry;
                if (referenceEntry != null && (advanceTo(referenceEntry) || nextInChain())) {
                    return true;
                }
            }
        }

        public void remove() {
            Preconditions.checkState(this.lastReturned != null);
            LocalCache.this.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }

    final class KeyIterator extends LocalCache<K, V>.HashIterator<K> {
        KeyIterator(LocalCache localCache) {
            super();
        }

        public K next() {
            return nextEntry().getKey();
        }
    }

    final class KeySet extends LocalCache<K, V>.AbstractCacheSet<K> {
        KeySet() {
            super();
        }

        public boolean contains(Object obj) {
            return LocalCache.this.containsKey(obj);
        }

        public Iterator<K> iterator() {
            return new KeyIterator(LocalCache.this);
        }

        public boolean remove(Object obj) {
            return LocalCache.this.remove(obj) != null;
        }
    }

    static class LoadingValueReference<K, V> implements ValueReference<K, V> {
        final SettableFuture<V> futureValue;
        volatile ValueReference<K, V> oldValue;
        final Stopwatch stopwatch;

        public LoadingValueReference() {
            this(LocalCache.unset());
        }

        private ListenableFuture<V> fullyFailedFuture(Throwable th) {
            return Futures.immediateFailedFuture(th);
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public long elapsedNanos() {
            return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
        }

        public V get() {
            return this.oldValue.get();
        }

        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public ValueReference<K, V> getOldValue() {
            return this.oldValue;
        }

        public int getWeight() {
            return this.oldValue.getWeight();
        }

        public boolean isActive() {
            return this.oldValue.isActive();
        }

        public boolean isLoading() {
            return true;
        }

        public ListenableFuture<V> loadFuture(K k, CacheLoader<? super K, V> cacheLoader) {
            try {
                this.stopwatch.start();
                V v = this.oldValue.get();
                if (v == null) {
                    V load = cacheLoader.load(k);
                    return set(load) ? this.futureValue : Futures.immediateFuture(load);
                }
                ListenableFuture<V> reload = cacheLoader.reload(k, v);
                if (reload == null) {
                    return Futures.immediateFuture(null);
                }
                return Futures.transform(reload, new Function<V, V>() {
                    public V apply(V v) {
                        LoadingValueReference.this.set(v);
                        return v;
                    }
                }, MoreExecutors.directExecutor());
            } catch (Throwable th) {
                ListenableFuture<V> fullyFailedFuture = setException(th) ? this.futureValue : fullyFailedFuture(th);
                if (th instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return fullyFailedFuture;
            }
        }

        public boolean set(V v) {
            return this.futureValue.set(v);
        }

        public boolean setException(Throwable th) {
            return this.futureValue.setException(th);
        }

        public void notifyNewValue(V v) {
            if (v != null) {
                set(v);
            } else {
                this.oldValue = LocalCache.unset();
            }
        }

        public LoadingValueReference(ValueReference<K, V> valueReference) {
            this.futureValue = SettableFuture.create();
            this.stopwatch = Stopwatch.createUnstarted();
            this.oldValue = valueReference;
        }
    }

    private enum NullEntry implements ReferenceEntry<Object, Object> {
        INSTANCE;

        public long getAccessTime() {
            return 0;
        }

        public int getHash() {
            return 0;
        }

        public Object getKey() {
            return null;
        }

        public ReferenceEntry<Object, Object> getNext() {
            return null;
        }

        public ReferenceEntry<Object, Object> getNextInAccessQueue() {
            return this;
        }

        public ReferenceEntry<Object, Object> getNextInWriteQueue() {
            return this;
        }

        public ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
            return this;
        }

        public ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
            return this;
        }

        public ValueReference<Object, Object> getValueReference() {
            return null;
        }

        public long getWriteTime() {
            return 0;
        }

        public void setAccessTime(long j) {
        }

        public void setNextInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public void setNextInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public void setPreviousInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public void setPreviousInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public void setValueReference(ValueReference<Object, Object> valueReference) {
        }

        public void setWriteTime(long j) {
        }
    }

    static class Segment<K, V> extends ReentrantLock {
        final Queue<ReferenceEntry<K, V>> accessQueue;
        volatile int count;
        final ReferenceQueue<K> keyReferenceQueue;
        final LocalCache<K, V> map;
        final long maxSegmentWeight;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();
        final Queue<ReferenceEntry<K, V>> recencyQueue;
        final AbstractCache$StatsCounter statsCounter;
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;
        int threshold;
        long totalWeight;
        final ReferenceQueue<V> valueReferenceQueue;
        final Queue<ReferenceEntry<K, V>> writeQueue;

        Segment(LocalCache<K, V> localCache, int i, long j, AbstractCache$StatsCounter abstractCache$StatsCounter) {
            Queue<ReferenceEntry<K, V>> queue;
            Queue<ReferenceEntry<K, V>> queue2;
            Queue<ReferenceEntry<K, V>> queue3;
            this.map = localCache;
            this.maxSegmentWeight = j;
            this.statsCounter = (AbstractCache$StatsCounter) Preconditions.checkNotNull(abstractCache$StatsCounter);
            initTable(newEntryArray(i));
            ReferenceQueue<V> referenceQueue = null;
            this.keyReferenceQueue = localCache.usesKeyReferences() ? new ReferenceQueue<>() : null;
            this.valueReferenceQueue = localCache.usesValueReferences() ? new ReferenceQueue<>() : referenceQueue;
            if (localCache.usesAccessQueue()) {
                queue = new ConcurrentLinkedQueue<>();
            } else {
                queue = LocalCache.discardingQueue();
            }
            this.recencyQueue = queue;
            if (localCache.usesWriteQueue()) {
                queue2 = new WriteQueue<>();
            } else {
                queue2 = LocalCache.discardingQueue();
            }
            this.writeQueue = queue2;
            if (localCache.usesAccessQueue()) {
                queue3 = new AccessQueue<>();
            } else {
                queue3 = LocalCache.discardingQueue();
            }
            this.accessQueue = queue3;
        }

        /* access modifiers changed from: package-private */
        public void cleanUp() {
            runLockedCleanup(this.map.ticker.read());
            runUnlockedCleanup();
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            RemovalCause removalCause;
            if (this.count != 0) {
                lock();
                try {
                    preWriteCleanup(this.map.ticker.read());
                    AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                    for (int i = 0; i < atomicReferenceArray.length(); i++) {
                        for (ReferenceEntry referenceEntry = atomicReferenceArray.get(i); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                            if (referenceEntry.getValueReference().isActive()) {
                                Object key = referenceEntry.getKey();
                                Object obj = referenceEntry.getValueReference().get();
                                if (key != null) {
                                    if (obj != null) {
                                        removalCause = RemovalCause.EXPLICIT;
                                        enqueueNotification(key, referenceEntry.getHash(), obj, referenceEntry.getValueReference().getWeight(), removalCause);
                                    }
                                }
                                removalCause = RemovalCause.COLLECTED;
                                enqueueNotification(key, referenceEntry.getHash(), obj, referenceEntry.getValueReference().getWeight(), removalCause);
                            }
                        }
                    }
                    for (int i2 = 0; i2 < atomicReferenceArray.length(); i2++) {
                        atomicReferenceArray.set(i2, (Object) null);
                    }
                    clearReferenceQueues();
                    this.writeQueue.clear();
                    this.accessQueue.clear();
                    this.readCount.set(0);
                    this.modCount++;
                    this.count = 0;
                } finally {
                    unlock();
                    postWriteCleanup();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void clearKeyReferenceQueue() {
            do {
            } while (this.keyReferenceQueue.poll() != null);
        }

        /* access modifiers changed from: package-private */
        public void clearReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                clearKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                clearValueReferenceQueue();
            }
        }

        /* access modifiers changed from: package-private */
        public void clearValueReferenceQueue() {
            do {
            } while (this.valueReferenceQueue.poll() != null);
        }

        /* access modifiers changed from: package-private */
        public boolean containsKey(Object obj, int i) {
            try {
                boolean z = false;
                if (this.count != 0) {
                    ReferenceEntry liveEntry = getLiveEntry(obj, i, this.map.ticker.read());
                    if (liveEntry == null) {
                        return false;
                    }
                    if (liveEntry.getValueReference().get() != null) {
                        z = true;
                    }
                    postReadCleanup();
                    return z;
                }
                postReadCleanup();
                return false;
            } finally {
                postReadCleanup();
            }
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            if (referenceEntry.getKey() == null) {
                return null;
            }
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            V v = valueReference.get();
            if (v == null && valueReference.isActive()) {
                return null;
            }
            ReferenceEntry<K, V> copyEntry = this.map.entryFactory.copyEntry(this, referenceEntry, referenceEntry2);
            copyEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, v, copyEntry));
            return copyEntry;
        }

        /* access modifiers changed from: package-private */
        public void drainRecencyQueue() {
            while (true) {
                ReferenceEntry poll = this.recencyQueue.poll();
                if (poll == null) {
                    return;
                }
                if (this.accessQueue.contains(poll)) {
                    this.accessQueue.add(poll);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void drainReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                drainKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                drainValueReferenceQueue();
            }
        }

        /* access modifiers changed from: package-private */
        public void enqueueNotification(K k, int i, V v, int i2, RemovalCause removalCause) {
            this.totalWeight -= (long) i2;
            if (removalCause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
                this.map.removalNotificationQueue.offer(RemovalNotification.create(k, v, removalCause));
            }
        }

        /* access modifiers changed from: package-private */
        public void evictEntries(ReferenceEntry<K, V> referenceEntry) {
            if (this.map.evictsBySize()) {
                drainRecencyQueue();
                if (((long) referenceEntry.getValueReference().getWeight()) > this.maxSegmentWeight && !removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.SIZE)) {
                    throw new AssertionError();
                }
                while (this.totalWeight > this.maxSegmentWeight) {
                    ReferenceEntry nextEvictable = getNextEvictable();
                    if (!removeEntry(nextEvictable, nextEvictable.getHash(), RemovalCause.SIZE)) {
                        throw new AssertionError();
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void expand() {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int length = atomicReferenceArray.length();
            if (length < 1073741824) {
                int i = this.count;
                AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray = newEntryArray(length + length);
                this.threshold = (newEntryArray.length() * 3) / 4;
                int length2 = newEntryArray.length() - 1;
                for (int i2 = 0; i2 < length; i2++) {
                    ReferenceEntry referenceEntry = atomicReferenceArray.get(i2);
                    if (referenceEntry != null) {
                        ReferenceEntry next = referenceEntry.getNext();
                        int hash = referenceEntry.getHash() & length2;
                        if (next == null) {
                            newEntryArray.set(hash, referenceEntry);
                        } else {
                            ReferenceEntry referenceEntry2 = referenceEntry;
                            while (next != null) {
                                int hash2 = next.getHash() & length2;
                                if (hash2 != hash) {
                                    referenceEntry2 = next;
                                    hash = hash2;
                                }
                                next = next.getNext();
                            }
                            newEntryArray.set(hash, referenceEntry2);
                            while (referenceEntry != referenceEntry2) {
                                int hash3 = referenceEntry.getHash() & length2;
                                ReferenceEntry copyEntry = copyEntry(referenceEntry, newEntryArray.get(hash3));
                                if (copyEntry != null) {
                                    newEntryArray.set(hash3, copyEntry);
                                } else {
                                    removeCollectedEntry(referenceEntry);
                                    i--;
                                }
                                referenceEntry = referenceEntry.getNext();
                            }
                        }
                    }
                }
                this.table = newEntryArray;
                this.count = i;
            }
        }

        /* access modifiers changed from: package-private */
        public void expireEntries(long j) {
            ReferenceEntry peek;
            ReferenceEntry peek2;
            drainRecencyQueue();
            do {
                peek = this.writeQueue.peek();
                if (peek == null || !this.map.isExpired(peek, j)) {
                    do {
                        peek2 = this.accessQueue.peek();
                        if (peek2 == null || !this.map.isExpired(peek2, j)) {
                            return;
                        }
                    } while (removeEntry(peek2, peek2.getHash(), RemovalCause.EXPIRED));
                    throw new AssertionError();
                }
            } while (removeEntry(peek, peek.getHash(), RemovalCause.EXPIRED));
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public V get(Object obj, int i) {
            try {
                if (this.count != 0) {
                    long read = this.map.ticker.read();
                    ReferenceEntry liveEntry = getLiveEntry(obj, i, read);
                    if (liveEntry == null) {
                        return null;
                    }
                    Object obj2 = liveEntry.getValueReference().get();
                    if (obj2 != null) {
                        recordRead(liveEntry, read);
                        V scheduleRefresh = scheduleRefresh(liveEntry, liveEntry.getKey(), i, obj2, read, this.map.defaultLoader);
                        postReadCleanup();
                        return scheduleRefresh;
                    }
                    tryDrainReferenceQueues();
                }
                postReadCleanup();
                return null;
            } finally {
                postReadCleanup();
            }
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> getEntry(Object obj, int i) {
            for (ReferenceEntry<K, V> first = getFirst(i); first != null; first = first.getNext()) {
                if (first.getHash() == i) {
                    K key = first.getKey();
                    if (key == null) {
                        tryDrainReferenceQueues();
                    } else if (this.map.keyEquivalence.equivalent(obj, key)) {
                        return first;
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> getFirst(int i) {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            return atomicReferenceArray.get(i & (atomicReferenceArray.length() - 1));
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> getLiveEntry(Object obj, int i, long j) {
            ReferenceEntry<K, V> entry = getEntry(obj, i);
            if (entry == null) {
                return null;
            }
            if (!this.map.isExpired(entry, j)) {
                return entry;
            }
            tryExpireEntries(j);
            return null;
        }

        /* access modifiers changed from: package-private */
        public V getLiveValue(ReferenceEntry<K, V> referenceEntry, long j) {
            if (referenceEntry.getKey() == null) {
                tryDrainReferenceQueues();
                return null;
            }
            V v = referenceEntry.getValueReference().get();
            if (v == null) {
                tryDrainReferenceQueues();
                return null;
            } else if (!this.map.isExpired(referenceEntry, j)) {
                return v;
            } else {
                tryExpireEntries(j);
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> getNextEvictable() {
            for (ReferenceEntry<K, V> referenceEntry : this.accessQueue) {
                if (referenceEntry.getValueReference().getWeight() > 0) {
                    return referenceEntry;
                }
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public void initTable(AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray) {
            this.threshold = (atomicReferenceArray.length() * 3) / 4;
            if (!this.map.customWeigher()) {
                int i = this.threshold;
                if (((long) i) == this.maxSegmentWeight) {
                    this.threshold = i + 1;
                }
            }
            this.table = atomicReferenceArray;
        }

        /* access modifiers changed from: package-private */
        public ListenableFuture<V> loadAsync(K k, int i, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> cacheLoader) {
            ListenableFuture<V> loadFuture = loadingValueReference.loadFuture(k, cacheLoader);
            final K k2 = k;
            final int i2 = i;
            final LoadingValueReference<K, V> loadingValueReference2 = loadingValueReference;
            final ListenableFuture<V> listenableFuture = loadFuture;
            loadFuture.addListener(new Runnable() {
                public void run() {
                    try {
                        Segment.this.getAndRecordStats(k2, i2, loadingValueReference2, listenableFuture);
                    } catch (Throwable th) {
                        LocalCache.logger.logp(Level.WARNING, "androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$Segment$1", "run", "Exception thrown during refresh", th);
                        loadingValueReference2.setException(th);
                    }
                }
            }, MoreExecutors.directExecutor());
            return loadFuture;
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> newEntry(K k, int i, ReferenceEntry<K, V> referenceEntry) {
            return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(k), i, referenceEntry);
        }

        /* access modifiers changed from: package-private */
        public AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(int i) {
            return new AtomicReferenceArray<>(i);
        }

        /* access modifiers changed from: package-private */
        public void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 63) == 0) {
                cleanUp();
            }
        }

        /* access modifiers changed from: package-private */
        public void postWriteCleanup() {
            runUnlockedCleanup();
        }

        /* access modifiers changed from: package-private */
        public void preWriteCleanup(long j) {
            runLockedCleanup(j);
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0047, code lost:
            r1 = r12.getValueReference();
            r10 = r1.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x004f, code lost:
            if (r10 != null) goto L_0x0090;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0051, code lost:
            r7.modCount++;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x005b, code lost:
            if (r1.isActive() == false) goto L_0x0077;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x005d, code lost:
            enqueueNotification(r15, r16, r10, r1.getWeight(), androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.COLLECTED);
            setValue(r12, r15, r17, r8);
            r0 = r7.count;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0077, code lost:
            setValue(r12, r15, r17, r8);
            r0 = r7.count + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0084, code lost:
            r7.count = r0;
            evictEntries(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0090, code lost:
            if (r18 == false) goto L_0x009c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
            recordLockedRead(r12, r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0095, code lost:
            unlock();
            postWriteCleanup();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x009b, code lost:
            return r10;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
            r7.modCount++;
            enqueueNotification(r15, r16, r10, r1.getWeight(), androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.REPLACED);
            setValue(r12, r15, r17, r8);
            evictEntries(r12);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V put(K r15, int r16, V r17, boolean r18) {
            /*
                r14 = this;
                r7 = r14
                r0 = r15
                r3 = r16
                r14.lock()
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r1 = r7.map     // Catch:{ all -> 0x00e3 }
                androidx.test.espresso.core.internal.deps.guava.base.Ticker r1 = r1.ticker     // Catch:{ all -> 0x00e3 }
                long r8 = r1.read()     // Catch:{ all -> 0x00e3 }
                r14.preWriteCleanup(r8)     // Catch:{ all -> 0x00e3 }
                int r1 = r7.count     // Catch:{ all -> 0x00e3 }
                int r1 = r1 + 1
                int r2 = r7.threshold     // Catch:{ all -> 0x00e3 }
                if (r1 <= r2) goto L_0x001d
                r14.expand()     // Catch:{ all -> 0x00e3 }
            L_0x001d:
                java.util.concurrent.atomic.AtomicReferenceArray<androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry<K, V>> r10 = r7.table     // Catch:{ all -> 0x00e3 }
                int r1 = r10.length()     // Catch:{ all -> 0x00e3 }
                int r1 = r1 + -1
                r11 = r3 & r1
                java.lang.Object r1 = r10.get(r11)     // Catch:{ all -> 0x00e3 }
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r1 = (androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry) r1     // Catch:{ all -> 0x00e3 }
                r12 = r1
            L_0x002e:
                r13 = 0
                if (r12 == 0) goto L_0x00c3
                java.lang.Object r2 = r12.getKey()     // Catch:{ all -> 0x00e3 }
                int r4 = r12.getHash()     // Catch:{ all -> 0x00e3 }
                if (r4 != r3) goto L_0x00bd
                if (r2 == 0) goto L_0x00bd
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r4 = r7.map     // Catch:{ all -> 0x00e3 }
                androidx.test.espresso.core.internal.deps.guava.base.Equivalence<java.lang.Object> r4 = r4.keyEquivalence     // Catch:{ all -> 0x00e3 }
                boolean r2 = r4.equivalent(r15, r2)     // Catch:{ all -> 0x00e3 }
                if (r2 == 0) goto L_0x00bd
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$ValueReference r1 = r12.getValueReference()     // Catch:{ all -> 0x00e3 }
                java.lang.Object r10 = r1.get()     // Catch:{ all -> 0x00e3 }
                if (r10 != 0) goto L_0x0090
                int r2 = r7.modCount     // Catch:{ all -> 0x00e3 }
                int r2 = r2 + 1
                r7.modCount = r2     // Catch:{ all -> 0x00e3 }
                boolean r2 = r1.isActive()     // Catch:{ all -> 0x00e3 }
                if (r2 == 0) goto L_0x0077
                int r5 = r1.getWeight()     // Catch:{ all -> 0x00e3 }
                androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause r6 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.COLLECTED     // Catch:{ all -> 0x00e3 }
                r1 = r14
                r2 = r15
                r3 = r16
                r4 = r10
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00e3 }
                r1 = r14
                r2 = r12
                r3 = r15
                r4 = r17
                r5 = r8
                r1.setValue(r2, r3, r4, r5)     // Catch:{ all -> 0x00e3 }
                int r0 = r7.count     // Catch:{ all -> 0x00e3 }
                goto L_0x0084
            L_0x0077:
                r1 = r14
                r2 = r12
                r3 = r15
                r4 = r17
                r5 = r8
                r1.setValue(r2, r3, r4, r5)     // Catch:{ all -> 0x00e3 }
                int r0 = r7.count     // Catch:{ all -> 0x00e3 }
                int r0 = r0 + 1
            L_0x0084:
                r7.count = r0     // Catch:{ all -> 0x00e3 }
                r14.evictEntries(r12)     // Catch:{ all -> 0x00e3 }
            L_0x0089:
                r14.unlock()
                r14.postWriteCleanup()
                return r13
            L_0x0090:
                if (r18 == 0) goto L_0x009c
                r14.recordLockedRead(r12, r8)     // Catch:{ all -> 0x00e3 }
            L_0x0095:
                r14.unlock()
                r14.postWriteCleanup()
                return r10
            L_0x009c:
                int r2 = r7.modCount     // Catch:{ all -> 0x00e3 }
                int r2 = r2 + 1
                r7.modCount = r2     // Catch:{ all -> 0x00e3 }
                int r5 = r1.getWeight()     // Catch:{ all -> 0x00e3 }
                androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause r6 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.REPLACED     // Catch:{ all -> 0x00e3 }
                r1 = r14
                r2 = r15
                r3 = r16
                r4 = r10
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00e3 }
                r1 = r14
                r2 = r12
                r3 = r15
                r4 = r17
                r5 = r8
                r1.setValue(r2, r3, r4, r5)     // Catch:{ all -> 0x00e3 }
                r14.evictEntries(r12)     // Catch:{ all -> 0x00e3 }
                goto L_0x0095
            L_0x00bd:
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r12 = r12.getNext()     // Catch:{ all -> 0x00e3 }
                goto L_0x002e
            L_0x00c3:
                int r2 = r7.modCount     // Catch:{ all -> 0x00e3 }
                int r2 = r2 + 1
                r7.modCount = r2     // Catch:{ all -> 0x00e3 }
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r12 = r14.newEntry(r15, r3, r1)     // Catch:{ all -> 0x00e3 }
                r1 = r14
                r2 = r12
                r3 = r15
                r4 = r17
                r5 = r8
                r1.setValue(r2, r3, r4, r5)     // Catch:{ all -> 0x00e3 }
                r10.set(r11, r12)     // Catch:{ all -> 0x00e3 }
                int r0 = r7.count     // Catch:{ all -> 0x00e3 }
                int r0 = r0 + 1
                r7.count = r0     // Catch:{ all -> 0x00e3 }
                r14.evictEntries(r12)     // Catch:{ all -> 0x00e3 }
                goto L_0x0089
            L_0x00e3:
                r0 = move-exception
                r14.unlock()
                r14.postWriteCleanup()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.Segment.put(java.lang.Object, int, java.lang.Object, boolean):java.lang.Object");
        }

        /* access modifiers changed from: package-private */
        public boolean reclaimKey(ReferenceEntry<K, V> referenceEntry, int i) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                ReferenceEntry<K, V> referenceEntry2 = atomicReferenceArray.get(length);
                for (ReferenceEntry<K, V> referenceEntry3 = referenceEntry2; referenceEntry3 != null; referenceEntry3 = referenceEntry3.getNext()) {
                    if (referenceEntry3 == referenceEntry) {
                        this.modCount++;
                        atomicReferenceArray.set(length, removeValueFromChain(referenceEntry2, referenceEntry3, referenceEntry3.getKey(), i, referenceEntry3.getValueReference().get(), referenceEntry3.getValueReference(), RemovalCause.COLLECTED));
                        this.count--;
                        return true;
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean reclaimValue(K k, int i, ValueReference<K, V> valueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                ReferenceEntry referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (referenceEntry2 != null) {
                    Object key = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != i || key == null || !this.map.keyEquivalence.equivalent(k, key)) {
                        referenceEntry2 = referenceEntry2.getNext();
                    } else if (referenceEntry2.getValueReference() == valueReference) {
                        this.modCount++;
                        atomicReferenceArray.set(length, removeValueFromChain(referenceEntry, referenceEntry2, key, i, valueReference.get(), valueReference, RemovalCause.COLLECTED));
                        this.count--;
                        unlock();
                        if (!isHeldByCurrentThread()) {
                            postWriteCleanup();
                        }
                        return true;
                    } else {
                        unlock();
                        if (!isHeldByCurrentThread()) {
                            postWriteCleanup();
                        }
                        return false;
                    }
                }
                unlock();
                if (!isHeldByCurrentThread()) {
                    postWriteCleanup();
                }
                return false;
            } catch (Throwable th) {
                unlock();
                if (!isHeldByCurrentThread()) {
                    postWriteCleanup();
                }
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public void recordLockedRead(ReferenceEntry<K, V> referenceEntry, long j) {
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(j);
            }
            this.accessQueue.add(referenceEntry);
        }

        /* access modifiers changed from: package-private */
        public void recordRead(ReferenceEntry<K, V> referenceEntry, long j) {
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(j);
            }
            this.recencyQueue.add(referenceEntry);
        }

        /* access modifiers changed from: package-private */
        public void recordWrite(ReferenceEntry<K, V> referenceEntry, int i, long j) {
            drainRecencyQueue();
            this.totalWeight += (long) i;
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(j);
            }
            if (this.map.recordsWrite()) {
                referenceEntry.setWriteTime(j);
            }
            this.accessQueue.add(referenceEntry);
            this.writeQueue.add(referenceEntry);
        }

        /* access modifiers changed from: package-private */
        public V remove(Object obj, int i) {
            RemovalCause removalCause;
            lock();
            try {
                preWriteCleanup(this.map.ticker.read());
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                ReferenceEntry referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (true) {
                    if (referenceEntry2 == null) {
                        break;
                    }
                    Object key = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != i || key == null || !this.map.keyEquivalence.equivalent(obj, key)) {
                        referenceEntry2 = referenceEntry2.getNext();
                    } else {
                        ValueReference valueReference = referenceEntry2.getValueReference();
                        V v = valueReference.get();
                        if (v != null) {
                            removalCause = RemovalCause.EXPLICIT;
                        } else if (valueReference.isActive()) {
                            removalCause = RemovalCause.COLLECTED;
                        }
                        RemovalCause removalCause2 = removalCause;
                        this.modCount++;
                        atomicReferenceArray.set(length, removeValueFromChain(referenceEntry, referenceEntry2, key, i, v, valueReference, removalCause2));
                        this.count--;
                        return v;
                    }
                }
                unlock();
                postWriteCleanup();
                return null;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            int i = this.count;
            ReferenceEntry<K, V> next = referenceEntry2.getNext();
            while (referenceEntry != referenceEntry2) {
                ReferenceEntry<K, V> copyEntry = copyEntry(referenceEntry, next);
                if (copyEntry == null) {
                    removeCollectedEntry(referenceEntry);
                    i--;
                } else {
                    next = copyEntry;
                }
                referenceEntry = referenceEntry.getNext();
            }
            this.count = i;
            return next;
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public boolean removeLoadingValue(K k, int i, LoadingValueReference<K, V> loadingValueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                ReferenceEntry referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (true) {
                    if (referenceEntry2 == null) {
                        break;
                    }
                    Object key = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != i || key == null || !this.map.keyEquivalence.equivalent(k, key)) {
                        referenceEntry2 = referenceEntry2.getNext();
                    } else if (referenceEntry2.getValueReference() == loadingValueReference) {
                        if (loadingValueReference.isActive()) {
                            referenceEntry2.setValueReference(loadingValueReference.getOldValue());
                        } else {
                            atomicReferenceArray.set(length, removeEntryFromChain(referenceEntry, referenceEntry2));
                        }
                        unlock();
                        postWriteCleanup();
                        return true;
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } catch (Throwable th) {
                unlock();
                postWriteCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, K k, int i, V v, ValueReference<K, V> valueReference, RemovalCause removalCause) {
            enqueueNotification(k, i, v, valueReference.getWeight(), removalCause);
            this.writeQueue.remove(referenceEntry2);
            this.accessQueue.remove(referenceEntry2);
            if (!valueReference.isLoading()) {
                return removeEntryFromChain(referenceEntry, referenceEntry2);
            }
            valueReference.notifyNewValue(null);
            return referenceEntry;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x003f, code lost:
            r15 = r12.getValueReference();
            r16 = r15.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0047, code lost:
            if (r16 != null) goto L_0x0073;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x004d, code lost:
            if (r15.isActive() == false) goto L_0x006c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x004f, code lost:
            r9.modCount++;
            r10.set(r11, removeValueFromChain(r2, r12, r4, r19, r16, r15, androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.COLLECTED));
            r9.count--;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            r9.modCount++;
            enqueueNotification(r18, r19, r16, r15.getWeight(), androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.REPLACED);
            setValue(r12, r18, r20, r7);
            evictEntries(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0098, code lost:
            unlock();
            postWriteCleanup();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x009e, code lost:
            return r16;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V replace(K r18, int r19, V r20) {
            /*
                r17 = this;
                r9 = r17
                r0 = r19
                r17.lock()
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r1 = r9.map     // Catch:{ all -> 0x00a7 }
                androidx.test.espresso.core.internal.deps.guava.base.Ticker r1 = r1.ticker     // Catch:{ all -> 0x00a7 }
                long r7 = r1.read()     // Catch:{ all -> 0x00a7 }
                r9.preWriteCleanup(r7)     // Catch:{ all -> 0x00a7 }
                java.util.concurrent.atomic.AtomicReferenceArray<androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry<K, V>> r10 = r9.table     // Catch:{ all -> 0x00a7 }
                int r1 = r10.length()     // Catch:{ all -> 0x00a7 }
                int r1 = r1 + -1
                r11 = r0 & r1
                java.lang.Object r1 = r10.get(r11)     // Catch:{ all -> 0x00a7 }
                r2 = r1
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r2 = (androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry) r2     // Catch:{ all -> 0x00a7 }
                r12 = r2
            L_0x0024:
                r13 = 0
                if (r12 == 0) goto L_0x006c
                java.lang.Object r4 = r12.getKey()     // Catch:{ all -> 0x00a7 }
                int r1 = r12.getHash()     // Catch:{ all -> 0x00a7 }
                if (r1 != r0) goto L_0x009f
                if (r4 == 0) goto L_0x009f
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r1 = r9.map     // Catch:{ all -> 0x00a7 }
                androidx.test.espresso.core.internal.deps.guava.base.Equivalence<java.lang.Object> r1 = r1.keyEquivalence     // Catch:{ all -> 0x00a7 }
                r14 = r18
                boolean r1 = r1.equivalent(r14, r4)     // Catch:{ all -> 0x00a7 }
                if (r1 == 0) goto L_0x00a1
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$ValueReference r15 = r12.getValueReference()     // Catch:{ all -> 0x00a7 }
                java.lang.Object r16 = r15.get()     // Catch:{ all -> 0x00a7 }
                if (r16 != 0) goto L_0x0073
                boolean r1 = r15.isActive()     // Catch:{ all -> 0x00a7 }
                if (r1 == 0) goto L_0x006c
                int r1 = r9.modCount     // Catch:{ all -> 0x00a7 }
                int r1 = r1 + 1
                r9.modCount = r1     // Catch:{ all -> 0x00a7 }
                androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause r8 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.COLLECTED     // Catch:{ all -> 0x00a7 }
                r1 = r17
                r3 = r12
                r5 = r19
                r6 = r16
                r7 = r15
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r0 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00a7 }
                int r1 = r9.count     // Catch:{ all -> 0x00a7 }
                int r1 = r1 + -1
                r10.set(r11, r0)     // Catch:{ all -> 0x00a7 }
                r9.count = r1     // Catch:{ all -> 0x00a7 }
            L_0x006c:
                r17.unlock()
                r17.postWriteCleanup()
                return r13
            L_0x0073:
                int r1 = r9.modCount     // Catch:{ all -> 0x00a7 }
                int r1 = r1 + 1
                r9.modCount = r1     // Catch:{ all -> 0x00a7 }
                int r5 = r15.getWeight()     // Catch:{ all -> 0x00a7 }
                androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause r6 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.REPLACED     // Catch:{ all -> 0x00a7 }
                r1 = r17
                r2 = r18
                r3 = r19
                r4 = r16
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00a7 }
                r1 = r17
                r2 = r12
                r3 = r18
                r4 = r20
                r5 = r7
                r1.setValue(r2, r3, r4, r5)     // Catch:{ all -> 0x00a7 }
                r9.evictEntries(r12)     // Catch:{ all -> 0x00a7 }
                r17.unlock()
                r17.postWriteCleanup()
                return r16
            L_0x009f:
                r14 = r18
            L_0x00a1:
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r12 = r12.getNext()     // Catch:{ all -> 0x00a7 }
                goto L_0x0024
            L_0x00a7:
                r0 = move-exception
                r17.unlock()
                r17.postWriteCleanup()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.Segment.replace(java.lang.Object, int, java.lang.Object):java.lang.Object");
        }

        /* access modifiers changed from: package-private */
        public void runLockedCleanup(long j) {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                    expireEntries(j);
                    this.readCount.set(0);
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void runUnlockedCleanup() {
            if (!isHeldByCurrentThread()) {
                this.map.processPendingNotifications();
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x001f, code lost:
            r4 = refresh(r5, r6, r10, true);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V scheduleRefresh(androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry<K, V> r4, K r5, int r6, V r7, long r8, androidx.test.espresso.core.internal.deps.guava.cache.CacheLoader<? super K, V> r10) {
            /*
                r3 = this;
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r0 = r3.map
                boolean r0 = r0.refreshes()
                if (r0 == 0) goto L_0x0027
                long r0 = r4.getWriteTime()
                long r8 = r8 - r0
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r0 = r3.map
                long r0 = r0.refreshNanos
                int r2 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
                if (r2 <= 0) goto L_0x0027
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$ValueReference r4 = r4.getValueReference()
                boolean r4 = r4.isLoading()
                if (r4 != 0) goto L_0x0027
                r4 = 1
                java.lang.Object r4 = r3.refresh(r5, r6, r10, r4)
                if (r4 == 0) goto L_0x0027
                return r4
            L_0x0027:
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.Segment.scheduleRefresh(androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry, java.lang.Object, int, java.lang.Object, long, androidx.test.espresso.core.internal.deps.guava.cache.CacheLoader):java.lang.Object");
        }

        /* access modifiers changed from: package-private */
        public void setValue(ReferenceEntry<K, V> referenceEntry, K k, V v, long j) {
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            int weigh = this.map.weigher.weigh(k, v);
            Preconditions.checkState(weigh >= 0, "Weights must be non-negative");
            referenceEntry.setValueReference(this.map.valueStrength.referenceValue(this, referenceEntry, v, weigh));
            recordWrite(referenceEntry, weigh, j);
            valueReference.notifyNewValue(v);
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x004b, code lost:
            r1 = r14.getValueReference();
            r4 = r1.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0055, code lost:
            if (r18 == r1) goto L_0x0073;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0057, code lost:
            if (r4 != null) goto L_0x005e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x005b, code lost:
            if (r1 == androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.UNSET) goto L_0x005e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x005e, code lost:
            enqueueNotification(r16, r17, r19, 0, androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.REPLACED);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x006b, code lost:
            unlock();
            postWriteCleanup();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0072, code lost:
            return false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
            r7.modCount++;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x007c, code lost:
            if (r18.isActive() == false) goto L_0x0094;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x007e, code lost:
            if (r4 != null) goto L_0x0083;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0080, code lost:
            r1 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.COLLECTED;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0083, code lost:
            r1 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.REPLACED;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0085, code lost:
            enqueueNotification(r16, r17, r4, r18.getWeight(), r1);
            r11 = r11 - 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0094, code lost:
            setValue(r14, r16, r19, r8);
            r7.count = r11;
            evictEntries(r14);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean storeLoadedValue(K r16, int r17, androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.LoadingValueReference<K, V> r18, V r19) {
            /*
                r15 = this;
                r7 = r15
                r0 = r16
                r3 = r17
                r15.lock()
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r1 = r7.map     // Catch:{ all -> 0x00cd }
                androidx.test.espresso.core.internal.deps.guava.base.Ticker r1 = r1.ticker     // Catch:{ all -> 0x00cd }
                long r8 = r1.read()     // Catch:{ all -> 0x00cd }
                r15.preWriteCleanup(r8)     // Catch:{ all -> 0x00cd }
                int r1 = r7.count     // Catch:{ all -> 0x00cd }
                r10 = 1
                int r1 = r1 + r10
                int r2 = r7.threshold     // Catch:{ all -> 0x00cd }
                if (r1 <= r2) goto L_0x0021
                r15.expand()     // Catch:{ all -> 0x00cd }
                int r1 = r7.count     // Catch:{ all -> 0x00cd }
                int r1 = r1 + r10
            L_0x0021:
                r11 = r1
                java.util.concurrent.atomic.AtomicReferenceArray<androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry<K, V>> r12 = r7.table     // Catch:{ all -> 0x00cd }
                int r1 = r12.length()     // Catch:{ all -> 0x00cd }
                int r1 = r1 + -1
                r13 = r3 & r1
                java.lang.Object r1 = r12.get(r13)     // Catch:{ all -> 0x00cd }
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r1 = (androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry) r1     // Catch:{ all -> 0x00cd }
                r14 = r1
            L_0x0033:
                if (r14 == 0) goto L_0x00b1
                java.lang.Object r2 = r14.getKey()     // Catch:{ all -> 0x00cd }
                int r4 = r14.getHash()     // Catch:{ all -> 0x00cd }
                if (r4 != r3) goto L_0x00aa
                if (r2 == 0) goto L_0x00aa
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r4 = r7.map     // Catch:{ all -> 0x00cd }
                androidx.test.espresso.core.internal.deps.guava.base.Equivalence<java.lang.Object> r4 = r4.keyEquivalence     // Catch:{ all -> 0x00cd }
                boolean r2 = r4.equivalent(r0, r2)     // Catch:{ all -> 0x00cd }
                if (r2 == 0) goto L_0x00aa
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$ValueReference r1 = r14.getValueReference()     // Catch:{ all -> 0x00cd }
                java.lang.Object r4 = r1.get()     // Catch:{ all -> 0x00cd }
                r2 = r18
                if (r2 == r1) goto L_0x0073
                if (r4 != 0) goto L_0x005e
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$ValueReference<java.lang.Object, java.lang.Object> r5 = androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.UNSET     // Catch:{ all -> 0x00cd }
                if (r1 == r5) goto L_0x005e
                goto L_0x0073
            L_0x005e:
                r5 = 0
                androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause r6 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.REPLACED     // Catch:{ all -> 0x00cd }
                r1 = r15
                r2 = r16
                r3 = r17
                r4 = r19
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00cd }
                r15.unlock()
                r15.postWriteCleanup()
                r0 = 0
                return r0
            L_0x0073:
                int r1 = r7.modCount     // Catch:{ all -> 0x00cd }
                int r1 = r1 + r10
                r7.modCount = r1     // Catch:{ all -> 0x00cd }
                boolean r1 = r18.isActive()     // Catch:{ all -> 0x00cd }
                if (r1 == 0) goto L_0x0094
                if (r4 != 0) goto L_0x0083
                androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause r1 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.COLLECTED     // Catch:{ all -> 0x00cd }
                goto L_0x0085
            L_0x0083:
                androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause r1 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.REPLACED     // Catch:{ all -> 0x00cd }
            L_0x0085:
                r6 = r1
                int r5 = r18.getWeight()     // Catch:{ all -> 0x00cd }
                r1 = r15
                r2 = r16
                r3 = r17
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00cd }
                int r11 = r11 + -1
            L_0x0094:
                r1 = r15
                r2 = r14
                r3 = r16
                r4 = r19
                r5 = r8
                r1.setValue(r2, r3, r4, r5)     // Catch:{ all -> 0x00cd }
                r7.count = r11     // Catch:{ all -> 0x00cd }
                r15.evictEntries(r14)     // Catch:{ all -> 0x00cd }
            L_0x00a3:
                r15.unlock()
                r15.postWriteCleanup()
                return r10
            L_0x00aa:
                r2 = r18
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r14 = r14.getNext()     // Catch:{ all -> 0x00cd }
                goto L_0x0033
            L_0x00b1:
                int r2 = r7.modCount     // Catch:{ all -> 0x00cd }
                int r2 = r2 + r10
                r7.modCount = r2     // Catch:{ all -> 0x00cd }
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r14 = r15.newEntry(r0, r3, r1)     // Catch:{ all -> 0x00cd }
                r1 = r15
                r2 = r14
                r3 = r16
                r4 = r19
                r5 = r8
                r1.setValue(r2, r3, r4, r5)     // Catch:{ all -> 0x00cd }
                r12.set(r13, r14)     // Catch:{ all -> 0x00cd }
                r7.count = r11     // Catch:{ all -> 0x00cd }
                r15.evictEntries(r14)     // Catch:{ all -> 0x00cd }
                goto L_0x00a3
            L_0x00cd:
                r0 = move-exception
                r15.unlock()
                r15.postWriteCleanup()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.Segment.storeLoadedValue(java.lang.Object, int, androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$LoadingValueReference, java.lang.Object):boolean");
        }

        /* access modifiers changed from: package-private */
        public void tryDrainReferenceQueues() {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void tryExpireEntries(long j) {
            if (tryLock()) {
                try {
                    expireEntries(j);
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void drainKeyReferenceQueue() {
            int i = 0;
            do {
                Reference<? extends K> poll = this.keyReferenceQueue.poll();
                if (poll != null) {
                    this.map.reclaimKey((ReferenceEntry) poll);
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        /* access modifiers changed from: package-private */
        public void drainValueReferenceQueue() {
            int i = 0;
            do {
                Reference<? extends V> poll = this.valueReferenceQueue.poll();
                if (poll != null) {
                    this.map.reclaimValue((ValueReference) poll);
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x0051  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V getAndRecordStats(K r5, int r6, androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.LoadingValueReference<K, V> r7, androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListenableFuture<V> r8) throws java.util.concurrent.ExecutionException {
            /*
                r4 = this;
                java.lang.Object r8 = androidx.test.espresso.core.internal.deps.guava.util.concurrent.Uninterruptibles.getUninterruptibly(r8)     // Catch:{ all -> 0x004d }
                if (r8 == 0) goto L_0x0023
                androidx.test.espresso.core.internal.deps.guava.cache.AbstractCache$StatsCounter r0 = r4.statsCounter     // Catch:{ all -> 0x0021 }
                long r1 = r7.elapsedNanos()     // Catch:{ all -> 0x0021 }
                r0.recordLoadSuccess(r1)     // Catch:{ all -> 0x0021 }
                r4.storeLoadedValue(r5, r6, r7, r8)     // Catch:{ all -> 0x0021 }
                if (r8 != 0) goto L_0x0020
                androidx.test.espresso.core.internal.deps.guava.cache.AbstractCache$StatsCounter r0 = r4.statsCounter
                long r1 = r7.elapsedNanos()
                r0.recordLoadException(r1)
                r4.removeLoadingValue(r5, r6, r7)
            L_0x0020:
                return r8
            L_0x0021:
                r0 = move-exception
                goto L_0x004f
            L_0x0023:
                androidx.test.espresso.core.internal.deps.guava.cache.CacheLoader$InvalidCacheLoadException r0 = new androidx.test.espresso.core.internal.deps.guava.cache.CacheLoader$InvalidCacheLoadException     // Catch:{ all -> 0x0021 }
                java.lang.String r1 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0021 }
                java.lang.String r2 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0021 }
                int r2 = r2.length()     // Catch:{ all -> 0x0021 }
                int r2 = r2 + 35
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0021 }
                r3.<init>(r2)     // Catch:{ all -> 0x0021 }
                java.lang.String r2 = "CacheLoader returned null for key "
                r3.append(r2)     // Catch:{ all -> 0x0021 }
                r3.append(r1)     // Catch:{ all -> 0x0021 }
                java.lang.String r1 = "."
                r3.append(r1)     // Catch:{ all -> 0x0021 }
                java.lang.String r1 = r3.toString()     // Catch:{ all -> 0x0021 }
                r0.<init>(r1)     // Catch:{ all -> 0x0021 }
                throw r0     // Catch:{ all -> 0x0021 }
            L_0x004d:
                r0 = move-exception
                r8 = 0
            L_0x004f:
                if (r8 != 0) goto L_0x005d
                androidx.test.espresso.core.internal.deps.guava.cache.AbstractCache$StatsCounter r8 = r4.statsCounter
                long r1 = r7.elapsedNanos()
                r8.recordLoadException(r1)
                r4.removeLoadingValue(r5, r6, r7)
            L_0x005d:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.Segment.getAndRecordStats(java.lang.Object, int, androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$LoadingValueReference, androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListenableFuture):java.lang.Object");
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public LoadingValueReference<K, V> insertLoadingValueReference(K k, int i, boolean z) {
            lock();
            try {
                long read = this.map.ticker.read();
                preWriteCleanup(read);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                ReferenceEntry referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (referenceEntry2 != null) {
                    Object key = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != i || key == null || !this.map.keyEquivalence.equivalent(k, key)) {
                        referenceEntry2 = referenceEntry2.getNext();
                    } else {
                        ValueReference valueReference = referenceEntry2.getValueReference();
                        if (!valueReference.isLoading()) {
                            if (!z || read - referenceEntry2.getWriteTime() >= this.map.refreshNanos) {
                                this.modCount++;
                                LoadingValueReference<K, V> loadingValueReference = new LoadingValueReference<>(valueReference);
                                referenceEntry2.setValueReference(loadingValueReference);
                                unlock();
                                postWriteCleanup();
                                return loadingValueReference;
                            }
                        }
                        unlock();
                        postWriteCleanup();
                        return null;
                    }
                }
                this.modCount++;
                LoadingValueReference<K, V> loadingValueReference2 = new LoadingValueReference<>();
                ReferenceEntry newEntry = newEntry(k, i, referenceEntry);
                newEntry.setValueReference(loadingValueReference2);
                atomicReferenceArray.set(length, newEntry);
                unlock();
                postWriteCleanup();
                return loadingValueReference2;
            } catch (Throwable th) {
                unlock();
                postWriteCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public V refresh(K k, int i, CacheLoader<? super K, V> cacheLoader, boolean z) {
            LoadingValueReference insertLoadingValueReference = insertLoadingValueReference(k, i, z);
            if (insertLoadingValueReference == null) {
                return null;
            }
            ListenableFuture<V> loadAsync = loadAsync(k, i, insertLoadingValueReference, cacheLoader);
            if (loadAsync.isDone()) {
                try {
                    return Uninterruptibles.getUninterruptibly(loadAsync);
                } catch (Throwable unused) {
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public void removeCollectedEntry(ReferenceEntry<K, V> referenceEntry) {
            enqueueNotification(referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry.getValueReference().get(), referenceEntry.getValueReference().getWeight(), RemovalCause.COLLECTED);
            this.writeQueue.remove(referenceEntry);
            this.accessQueue.remove(referenceEntry);
        }

        /* access modifiers changed from: package-private */
        public boolean removeEntry(ReferenceEntry<K, V> referenceEntry, int i, RemovalCause removalCause) {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int length = (atomicReferenceArray.length() - 1) & i;
            ReferenceEntry<K, V> referenceEntry2 = atomicReferenceArray.get(length);
            for (ReferenceEntry<K, V> referenceEntry3 = referenceEntry2; referenceEntry3 != null; referenceEntry3 = referenceEntry3.getNext()) {
                if (referenceEntry3 == referenceEntry) {
                    this.modCount++;
                    ReferenceEntry<K, V> removeValueFromChain = removeValueFromChain(referenceEntry2, referenceEntry3, referenceEntry3.getKey(), i, referenceEntry3.getValueReference().get(), referenceEntry3.getValueReference(), removalCause);
                    int i2 = this.count;
                    atomicReferenceArray.set(length, removeValueFromChain);
                    this.count = i2 - 1;
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean remove(Object obj, int i, Object obj2) {
            boolean z;
            RemovalCause removalCause;
            lock();
            try {
                preWriteCleanup(this.map.ticker.read());
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int length = (atomicReferenceArray.length() - 1) & i;
                ReferenceEntry referenceEntry = atomicReferenceArray.get(length);
                ReferenceEntry referenceEntry2 = referenceEntry;
                while (true) {
                    z = false;
                    if (referenceEntry2 == null) {
                        break;
                    }
                    Object key = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() != i || key == null || !this.map.keyEquivalence.equivalent(obj, key)) {
                        referenceEntry2 = referenceEntry2.getNext();
                    } else {
                        ValueReference valueReference = referenceEntry2.getValueReference();
                        Object obj3 = valueReference.get();
                        if (this.map.valueEquivalence.equivalent(obj2, obj3)) {
                            removalCause = RemovalCause.EXPLICIT;
                        } else if (obj3 == null && valueReference.isActive()) {
                            removalCause = RemovalCause.COLLECTED;
                        }
                        this.modCount++;
                        atomicReferenceArray.set(length, removeValueFromChain(referenceEntry, referenceEntry2, key, i, obj3, valueReference, removalCause));
                        this.count--;
                        if (removalCause == RemovalCause.EXPLICIT) {
                            z = true;
                        }
                    }
                }
                return z;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x003f, code lost:
            r15 = r12.getValueReference();
            r6 = r15.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0049, code lost:
            if (r6 != null) goto L_0x0073;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x004f, code lost:
            if (r15.isActive() == false) goto L_0x006c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0051, code lost:
            r9.modCount++;
            r10.set(r11, removeValueFromChain(r2, r12, r4, r19, r6, r15, androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.COLLECTED));
            r9.count--;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x007d, code lost:
            if (r9.map.valueEquivalence.equivalent(r20, r6) == false) goto L_0x00ab;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x007f, code lost:
            r9.modCount++;
            enqueueNotification(r18, r19, r6, r15.getWeight(), androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.REPLACED);
            setValue(r12, r18, r21, r7);
            evictEntries(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a4, code lost:
            unlock();
            postWriteCleanup();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x00aa, code lost:
            return true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
            recordLockedRead(r12, r7);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean replace(K r18, int r19, V r20, V r21) {
            /*
                r17 = this;
                r9 = r17
                r0 = r19
                r17.lock()
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r1 = r9.map     // Catch:{ all -> 0x00b9 }
                androidx.test.espresso.core.internal.deps.guava.base.Ticker r1 = r1.ticker     // Catch:{ all -> 0x00b9 }
                long r7 = r1.read()     // Catch:{ all -> 0x00b9 }
                r9.preWriteCleanup(r7)     // Catch:{ all -> 0x00b9 }
                java.util.concurrent.atomic.AtomicReferenceArray<androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry<K, V>> r10 = r9.table     // Catch:{ all -> 0x00b9 }
                int r1 = r10.length()     // Catch:{ all -> 0x00b9 }
                int r1 = r1 + -1
                r11 = r0 & r1
                java.lang.Object r1 = r10.get(r11)     // Catch:{ all -> 0x00b9 }
                r2 = r1
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r2 = (androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry) r2     // Catch:{ all -> 0x00b9 }
                r12 = r2
            L_0x0024:
                r13 = 0
                if (r12 == 0) goto L_0x006c
                java.lang.Object r4 = r12.getKey()     // Catch:{ all -> 0x00b9 }
                int r1 = r12.getHash()     // Catch:{ all -> 0x00b9 }
                if (r1 != r0) goto L_0x00af
                if (r4 == 0) goto L_0x00af
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r1 = r9.map     // Catch:{ all -> 0x00b9 }
                androidx.test.espresso.core.internal.deps.guava.base.Equivalence<java.lang.Object> r1 = r1.keyEquivalence     // Catch:{ all -> 0x00b9 }
                r14 = r18
                boolean r1 = r1.equivalent(r14, r4)     // Catch:{ all -> 0x00b9 }
                if (r1 == 0) goto L_0x00b1
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$ValueReference r15 = r12.getValueReference()     // Catch:{ all -> 0x00b9 }
                java.lang.Object r6 = r15.get()     // Catch:{ all -> 0x00b9 }
                r16 = 1
                if (r6 != 0) goto L_0x0073
                boolean r1 = r15.isActive()     // Catch:{ all -> 0x00b9 }
                if (r1 == 0) goto L_0x006c
                int r1 = r9.modCount     // Catch:{ all -> 0x00b9 }
                int r1 = r1 + 1
                r9.modCount = r1     // Catch:{ all -> 0x00b9 }
                androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause r8 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.COLLECTED     // Catch:{ all -> 0x00b9 }
                r1 = r17
                r3 = r12
                r5 = r19
                r7 = r15
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r0 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00b9 }
                int r1 = r9.count     // Catch:{ all -> 0x00b9 }
                int r1 = r1 + -1
                r10.set(r11, r0)     // Catch:{ all -> 0x00b9 }
                r9.count = r1     // Catch:{ all -> 0x00b9 }
            L_0x006c:
                r17.unlock()
                r17.postWriteCleanup()
                return r13
            L_0x0073:
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r1 = r9.map     // Catch:{ all -> 0x00b9 }
                androidx.test.espresso.core.internal.deps.guava.base.Equivalence<java.lang.Object> r1 = r1.valueEquivalence     // Catch:{ all -> 0x00b9 }
                r3 = r20
                boolean r1 = r1.equivalent(r3, r6)     // Catch:{ all -> 0x00b9 }
                if (r1 == 0) goto L_0x00ab
                int r1 = r9.modCount     // Catch:{ all -> 0x00b9 }
                int r1 = r1 + 1
                r9.modCount = r1     // Catch:{ all -> 0x00b9 }
                int r5 = r15.getWeight()     // Catch:{ all -> 0x00b9 }
                androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause r10 = androidx.test.espresso.core.internal.deps.guava.cache.RemovalCause.REPLACED     // Catch:{ all -> 0x00b9 }
                r1 = r17
                r2 = r18
                r3 = r19
                r4 = r6
                r6 = r10
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00b9 }
                r1 = r17
                r2 = r12
                r3 = r18
                r4 = r21
                r5 = r7
                r1.setValue(r2, r3, r4, r5)     // Catch:{ all -> 0x00b9 }
                r9.evictEntries(r12)     // Catch:{ all -> 0x00b9 }
                r17.unlock()
                r17.postWriteCleanup()
                return r16
            L_0x00ab:
                r9.recordLockedRead(r12, r7)     // Catch:{ all -> 0x00b9 }
                goto L_0x006c
            L_0x00af:
                r14 = r18
            L_0x00b1:
                r3 = r20
                androidx.test.espresso.core.internal.deps.guava.cache.ReferenceEntry r12 = r12.getNext()     // Catch:{ all -> 0x00b9 }
                goto L_0x0024
            L_0x00b9:
                r0 = move-exception
                r17.unlock()
                r17.postWriteCleanup()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.Segment.replace(java.lang.Object, int, java.lang.Object, java.lang.Object):boolean");
        }
    }

    static class SoftValueReference<K, V> extends SoftReference<V> implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        SoftValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            super(v, referenceQueue);
            this.entry = referenceEntry;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new SoftValueReference(referenceQueue, v, referenceEntry);
        }

        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        public int getWeight() {
            return 1;
        }

        public boolean isActive() {
            return true;
        }

        public boolean isLoading() {
            return false;
        }

        public void notifyNewValue(V v) {
        }
    }

    enum Strength {
        STRONG {
            /* access modifiers changed from: package-private */
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.equals();
            }

            /* access modifiers changed from: package-private */
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i) {
                if (i == 1) {
                    return new StrongValueReference(v);
                }
                return new WeightedStrongValueReference(v, i);
            }
        },
        SOFT {
            /* access modifiers changed from: package-private */
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }

            /* access modifiers changed from: package-private */
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i) {
                if (i == 1) {
                    return new SoftValueReference(segment.valueReferenceQueue, v, referenceEntry);
                }
                return new WeightedSoftValueReference(segment.valueReferenceQueue, v, referenceEntry, i);
            }
        },
        WEAK {
            /* access modifiers changed from: package-private */
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }

            /* access modifiers changed from: package-private */
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i) {
                if (i == 1) {
                    return new WeakValueReference(segment.valueReferenceQueue, v, referenceEntry);
                }
                return new WeightedWeakValueReference(segment.valueReferenceQueue, v, referenceEntry, i);
            }
        };

        /* access modifiers changed from: package-private */
        public abstract Equivalence<Object> defaultEquivalence();

        /* access modifiers changed from: package-private */
        public abstract <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i);
    }

    static final class StrongAccessEntry<K, V> extends StrongEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

        StrongAccessEntry(K k, int i, ReferenceEntry<K, V> referenceEntry) {
            super(k, i, referenceEntry);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public void setAccessTime(long j) {
            this.accessTime = j;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }
    }

    static final class StrongAccessWriteEntry<K, V> extends StrongEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        StrongAccessWriteEntry(K k, int i, ReferenceEntry<K, V> referenceEntry) {
            super(k, i, referenceEntry);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setAccessTime(long j) {
            this.accessTime = j;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        public void setWriteTime(long j) {
            this.writeTime = j;
        }
    }

    static class StrongEntry<K, V> extends AbstractReferenceEntry<K, V> {
        final int hash;
        final K key;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        StrongEntry(K k, int i, ReferenceEntry<K, V> referenceEntry) {
            this.key = k;
            this.hash = i;
            this.next = referenceEntry;
        }

        public int getHash() {
            return this.hash;
        }

        public K getKey() {
            return this.key;
        }

        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }

        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        public void setValueReference(ValueReference<K, V> valueReference2) {
            this.valueReference = valueReference2;
        }
    }

    static class StrongValueReference<K, V> implements ValueReference<K, V> {
        final V referent;

        StrongValueReference(V v) {
            this.referent = v;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public V get() {
            return this.referent;
        }

        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public int getWeight() {
            return 1;
        }

        public boolean isActive() {
            return true;
        }

        public boolean isLoading() {
            return false;
        }

        public void notifyNewValue(V v) {
        }
    }

    static final class StrongWriteEntry<K, V> extends StrongEntry<K, V> {
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        StrongWriteEntry(K k, int i, ReferenceEntry<K, V> referenceEntry) {
            super(k, i, referenceEntry);
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        public void setWriteTime(long j) {
            this.writeTime = j;
        }
    }

    final class ValueIterator extends LocalCache<K, V>.HashIterator<V> {
        ValueIterator(LocalCache localCache) {
            super();
        }

        public V next() {
            return nextEntry().getValue();
        }
    }

    interface ValueReference<K, V> {
        ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry);

        V get();

        ReferenceEntry<K, V> getEntry();

        int getWeight();

        boolean isActive();

        boolean isLoading();

        void notifyNewValue(V v);
    }

    final class Values extends AbstractCollection<V> {
        Values() {
        }

        public void clear() {
            LocalCache.this.clear();
        }

        public boolean contains(Object obj) {
            return LocalCache.this.containsValue(obj);
        }

        public boolean isEmpty() {
            return LocalCache.this.isEmpty();
        }

        public Iterator<V> iterator() {
            return new ValueIterator(LocalCache.this);
        }

        public int size() {
            return LocalCache.this.size();
        }

        public Object[] toArray() {
            return LocalCache.toArrayList(this).toArray();
        }

        public <E> E[] toArray(E[] eArr) {
            return LocalCache.toArrayList(this).toArray(eArr);
        }
    }

    static final class WeakAccessEntry<K, V> extends WeakEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

        WeakAccessEntry(ReferenceQueue<K> referenceQueue, K k, int i, ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, i, referenceEntry);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public void setAccessTime(long j) {
            this.accessTime = j;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }
    }

    static final class WeakAccessWriteEntry<K, V> extends WeakEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        WeakAccessWriteEntry(ReferenceQueue<K> referenceQueue, K k, int i, ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, i, referenceEntry);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setAccessTime(long j) {
            this.accessTime = j;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        public void setWriteTime(long j) {
            this.writeTime = j;
        }
    }

    static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {
        final int hash;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        WeakEntry(ReferenceQueue<K> referenceQueue, K k, int i, ReferenceEntry<K, V> referenceEntry) {
            super(k, referenceQueue);
            this.hash = i;
            this.next = referenceEntry;
        }

        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        public int getHash() {
            return this.hash;
        }

        public K getKey() {
            return get();
        }

        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        public void setAccessTime(long j) {
            throw new UnsupportedOperationException();
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public void setValueReference(ValueReference<K, V> valueReference2) {
            this.valueReference = valueReference2;
        }

        public void setWriteTime(long j) {
            throw new UnsupportedOperationException();
        }
    }

    static class WeakValueReference<K, V> extends WeakReference<V> implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        WeakValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            super(v, referenceQueue);
            this.entry = referenceEntry;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeakValueReference(referenceQueue, v, referenceEntry);
        }

        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        public int getWeight() {
            return 1;
        }

        public boolean isActive() {
            return true;
        }

        public boolean isLoading() {
            return false;
        }

        public void notifyNewValue(V v) {
        }
    }

    static final class WeakWriteEntry<K, V> extends WeakEntry<K, V> {
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        WeakWriteEntry(ReferenceQueue<K> referenceQueue, K k, int i, ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, i, referenceEntry);
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        public void setWriteTime(long j) {
            this.writeTime = j;
        }
    }

    static final class WeightedSoftValueReference<K, V> extends SoftValueReference<K, V> {
        final int weight;

        WeightedSoftValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry, int i) {
            super(referenceQueue, v, referenceEntry);
            this.weight = i;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedSoftValueReference(referenceQueue, v, referenceEntry, this.weight);
        }

        public int getWeight() {
            return this.weight;
        }
    }

    static final class WeightedStrongValueReference<K, V> extends StrongValueReference<K, V> {
        final int weight;

        WeightedStrongValueReference(V v, int i) {
            super(v);
            this.weight = i;
        }

        public int getWeight() {
            return this.weight;
        }
    }

    static final class WeightedWeakValueReference<K, V> extends WeakValueReference<K, V> {
        final int weight;

        WeightedWeakValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry, int i) {
            super(referenceQueue, v, referenceEntry);
            this.weight = i;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedWeakValueReference(referenceQueue, v, referenceEntry, this.weight);
        }

        public int getWeight() {
            return this.weight;
        }
    }

    static final class WriteQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>(this) {
            ReferenceEntry<K, V> nextWrite = this;
            ReferenceEntry<K, V> previousWrite = this;

            public ReferenceEntry<K, V> getNextInWriteQueue() {
                return this.nextWrite;
            }

            public ReferenceEntry<K, V> getPreviousInWriteQueue() {
                return this.previousWrite;
            }

            public long getWriteTime() {
                return Long.MAX_VALUE;
            }

            public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.nextWrite = referenceEntry;
            }

            public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.previousWrite = referenceEntry;
            }

            public void setWriteTime(long j) {
            }
        };

        WriteQueue() {
        }

        public void clear() {
            ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue();
            while (true) {
                ReferenceEntry<K, V> referenceEntry = this.head;
                if (nextInWriteQueue != referenceEntry) {
                    ReferenceEntry<K, V> nextInWriteQueue2 = nextInWriteQueue.getNextInWriteQueue();
                    LocalCache.nullifyWriteOrder(nextInWriteQueue);
                    nextInWriteQueue = nextInWriteQueue2;
                } else {
                    referenceEntry.setNextInWriteQueue(referenceEntry);
                    ReferenceEntry<K, V> referenceEntry2 = this.head;
                    referenceEntry2.setPreviousInWriteQueue(referenceEntry2);
                    return;
                }
            }
        }

        public boolean contains(Object obj) {
            return ((ReferenceEntry) obj).getNextInWriteQueue() != NullEntry.INSTANCE;
        }

        public boolean isEmpty() {
            return this.head.getNextInWriteQueue() == this.head;
        }

        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) {
                /* access modifiers changed from: protected */
                public ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> referenceEntry) {
                    ReferenceEntry<K, V> nextInWriteQueue = referenceEntry.getNextInWriteQueue();
                    if (nextInWriteQueue == WriteQueue.this.head) {
                        return null;
                    }
                    return nextInWriteQueue;
                }
            };
        }

        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry.getNextInWriteQueue());
            LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), referenceEntry);
            LocalCache.connectWriteOrder(referenceEntry, this.head);
            return true;
        }

        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue();
            if (nextInWriteQueue == this.head) {
                return null;
            }
            return nextInWriteQueue;
        }

        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue();
            if (nextInWriteQueue == this.head) {
                return null;
            }
            remove(nextInWriteQueue);
            return nextInWriteQueue;
        }

        public boolean remove(Object obj) {
            ReferenceEntry referenceEntry = (ReferenceEntry) obj;
            ReferenceEntry previousInWriteQueue = referenceEntry.getPreviousInWriteQueue();
            ReferenceEntry nextInWriteQueue = referenceEntry.getNextInWriteQueue();
            LocalCache.connectWriteOrder(previousInWriteQueue, nextInWriteQueue);
            LocalCache.nullifyWriteOrder(referenceEntry);
            return nextInWriteQueue != NullEntry.INSTANCE;
        }

        public int size() {
            int i = 0;
            for (ReferenceEntry<K, V> nextInWriteQueue = this.head.getNextInWriteQueue(); nextInWriteQueue != this.head; nextInWriteQueue = nextInWriteQueue.getNextInWriteQueue()) {
                i++;
            }
            return i;
        }
    }

    final class WriteThroughEntry implements Map.Entry<K, V> {
        final K key;
        V value;

        WriteThroughEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (!this.key.equals(entry.getKey()) || !this.value.equals(entry.getValue())) {
                return false;
            }
            return true;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public int hashCode() {
            return this.key.hashCode() ^ this.value.hashCode();
        }

        public V setValue(V v) {
            V put = LocalCache.this.put(this.key, v);
            this.value = v;
            return put;
        }

        public String toString() {
            String valueOf = String.valueOf(getKey());
            String valueOf2 = String.valueOf(getValue());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append("=");
            sb.append(valueOf2);
            return sb.toString();
        }
    }

    LocalCache(CacheBuilder<? super K, ? super V> cacheBuilder, CacheLoader<? super K, V> cacheLoader) {
        Queue<RemovalNotification<K, V>> queue;
        this.concurrencyLevel = Math.min(cacheBuilder.getConcurrencyLevel(), 65536);
        Strength keyStrength2 = cacheBuilder.getKeyStrength();
        this.keyStrength = keyStrength2;
        this.valueStrength = cacheBuilder.getValueStrength();
        this.keyEquivalence = cacheBuilder.getKeyEquivalence();
        this.valueEquivalence = cacheBuilder.getValueEquivalence();
        long maximumWeight = cacheBuilder.getMaximumWeight();
        this.maxWeight = maximumWeight;
        this.weigher = cacheBuilder.getWeigher();
        this.expireAfterAccessNanos = cacheBuilder.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = cacheBuilder.getExpireAfterWriteNanos();
        this.refreshNanos = cacheBuilder.getRefreshNanos();
        RemovalListener<K1, V1> removalListener2 = cacheBuilder.getRemovalListener();
        this.removalListener = removalListener2;
        if (removalListener2 == CacheBuilder.NullListener.INSTANCE) {
            queue = discardingQueue();
        } else {
            queue = new ConcurrentLinkedQueue<>();
        }
        this.removalNotificationQueue = queue;
        this.ticker = cacheBuilder.getTicker(recordsTime());
        this.entryFactory = EntryFactory.getFactory(keyStrength2, usesAccessEntries(), usesWriteEntries());
        this.globalStatsCounter = (AbstractCache$StatsCounter) cacheBuilder.getStatsCounterSupplier().get();
        this.defaultLoader = cacheLoader;
        int min = Math.min(cacheBuilder.getInitialCapacity(), 1073741824);
        if (evictsBySize() && !customWeigher()) {
            min = (int) Math.min((long) min, maximumWeight);
        }
        int i = 0;
        int i2 = 1;
        int i3 = 1;
        int i4 = 0;
        while (i3 < this.concurrencyLevel && (!evictsBySize() || ((long) (i3 * 20)) <= this.maxWeight)) {
            i4++;
            i3 += i3;
        }
        this.segmentShift = 32 - i4;
        this.segmentMask = i3 - 1;
        this.segments = newSegmentArray(i3);
        int i5 = min / i3;
        while (i2 < (i5 * i3 < min ? i5 + 1 : i5)) {
            i2 += i2;
        }
        if (evictsBySize()) {
            long j = this.maxWeight;
            long j2 = (long) i3;
            long j3 = (j / j2) + 1;
            long j4 = j % j2;
            while (true) {
                Segment<K, V>[] segmentArr = this.segments;
                if (i < segmentArr.length) {
                    if (((long) i) == j4) {
                        j3--;
                    }
                    segmentArr[i] = createSegment(i2, j3, (AbstractCache$StatsCounter) cacheBuilder.getStatsCounterSupplier().get());
                    i++;
                } else {
                    return;
                }
            }
        } else {
            while (true) {
                Segment<K, V>[] segmentArr2 = this.segments;
                if (i < segmentArr2.length) {
                    segmentArr2[i] = createSegment(i2, -1, (AbstractCache$StatsCounter) cacheBuilder.getStatsCounterSupplier().get());
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    static <K, V> void connectAccessOrder(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.setNextInAccessQueue(referenceEntry2);
        referenceEntry2.setPreviousInAccessQueue(referenceEntry);
    }

    static <K, V> void connectWriteOrder(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.setNextInWriteQueue(referenceEntry2);
        referenceEntry2.setPreviousInWriteQueue(referenceEntry);
    }

    static <E> Queue<E> discardingQueue() {
        return DISCARDING_QUEUE;
    }

    static <K, V> ReferenceEntry<K, V> nullEntry() {
        return NullEntry.INSTANCE;
    }

    static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> referenceEntry) {
        ReferenceEntry nullEntry = nullEntry();
        referenceEntry.setNextInAccessQueue(nullEntry);
        referenceEntry.setPreviousInAccessQueue(nullEntry);
    }

    static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> referenceEntry) {
        ReferenceEntry nullEntry = nullEntry();
        referenceEntry.setNextInWriteQueue(nullEntry);
        referenceEntry.setPreviousInWriteQueue(nullEntry);
    }

    static int rehash(int i) {
        int i2 = i + ((i << 15) ^ -12931);
        int i3 = i2 ^ (i2 >>> 10);
        int i4 = i3 + (i3 << 3);
        int i5 = i4 ^ (i4 >>> 6);
        int i6 = i5 + (i5 << 2) + (i5 << 14);
        return i6 ^ (i6 >>> 16);
    }

    /* access modifiers changed from: private */
    public static <E> ArrayList<E> toArrayList(Collection<E> collection) {
        ArrayList<E> arrayList = new ArrayList<>(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }

    static <K, V> ValueReference<K, V> unset() {
        return UNSET;
    }

    public void clear() {
        for (Segment<K, V> clear : this.segments) {
            clear.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public Segment<K, V> createSegment(int i, long j, AbstractCache$StatsCounter abstractCache$StatsCounter) {
        return new Segment(this, i, j, abstractCache$StatsCounter);
    }

    /* access modifiers changed from: package-private */
    public boolean customWeigher() {
        return this.weigher != CacheBuilder.OneWeigher.INSTANCE;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySet;
        if (set != null) {
            return set;
        }
        EntrySet entrySet2 = new EntrySet();
        this.entrySet = entrySet2;
        return entrySet2;
    }

    /* access modifiers changed from: package-private */
    public boolean evictsBySize() {
        return this.maxWeight >= 0;
    }

    /* access modifiers changed from: package-private */
    public boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos > 0;
    }

    public V getIfPresent(Object obj) {
        int hash = hash(Preconditions.checkNotNull(obj));
        V v = segmentFor(hash).get(obj, hash);
        if (v == null) {
            this.globalStatsCounter.recordMisses(1);
        } else {
            this.globalStatsCounter.recordHits(1);
        }
        return v;
    }

    /* access modifiers changed from: package-private */
    public V getLiveValue(ReferenceEntry<K, V> referenceEntry, long j) {
        V v;
        if (referenceEntry.getKey() == null || (v = referenceEntry.getValueReference().get()) == null || isExpired(referenceEntry, j)) {
            return null;
        }
        return v;
    }

    public V getOrDefault(Object obj, V v) {
        V v2 = get(obj);
        return v2 != null ? v2 : v;
    }

    /* access modifiers changed from: package-private */
    public int hash(Object obj) {
        return rehash(this.keyEquivalence.hash(obj));
    }

    /* access modifiers changed from: package-private */
    public boolean isExpired(ReferenceEntry<K, V> referenceEntry, long j) {
        Preconditions.checkNotNull(referenceEntry);
        if (expiresAfterAccess() && j - referenceEntry.getAccessTime() >= this.expireAfterAccessNanos) {
            return true;
        }
        if (!expiresAfterWrite() || j - referenceEntry.getWriteTime() < this.expireAfterWriteNanos) {
            return false;
        }
        return true;
    }

    public Set<K> keySet() {
        Set<K> set = this.keySet;
        if (set != null) {
            return set;
        }
        KeySet keySet2 = new KeySet();
        this.keySet = keySet2;
        return keySet2;
    }

    /* access modifiers changed from: package-private */
    public long longSize() {
        Segment<K, V>[] segmentArr = this.segments;
        long j = 0;
        for (Segment<K, V> segment : segmentArr) {
            j += (long) Math.max(0, segment.count);
        }
        return j;
    }

    /* access modifiers changed from: package-private */
    public final Segment<K, V>[] newSegmentArray(int i) {
        return new Segment[i];
    }

    /* access modifiers changed from: package-private */
    public void processPendingNotifications() {
        while (true) {
            RemovalNotification poll = this.removalNotificationQueue.poll();
            if (poll != null) {
                try {
                    this.removalListener.onRemoval(poll);
                } catch (Throwable th) {
                    logger.logp(Level.WARNING, "androidx.test.espresso.core.internal.deps.guava.cache.LocalCache", "processPendingNotifications", "Exception thrown by removal listener", th);
                }
            } else {
                return;
            }
        }
    }

    public V put(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int hash = hash(k);
        return segmentFor(hash).put(k, hash, v, false);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry next : map.entrySet()) {
            put(next.getKey(), next.getValue());
        }
    }

    public V putIfAbsent(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int hash = hash(k);
        return segmentFor(hash).put(k, hash, v, true);
    }

    /* access modifiers changed from: package-private */
    public void reclaimKey(ReferenceEntry<K, V> referenceEntry) {
        int hash = referenceEntry.getHash();
        segmentFor(hash).reclaimKey(referenceEntry, hash);
    }

    /* access modifiers changed from: package-private */
    public void reclaimValue(ValueReference<K, V> valueReference) {
        ReferenceEntry<K, V> entry = valueReference.getEntry();
        int hash = entry.getHash();
        segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
    }

    /* access modifiers changed from: package-private */
    public boolean recordsAccess() {
        return expiresAfterAccess();
    }

    /* access modifiers changed from: package-private */
    public boolean recordsTime() {
        return recordsWrite() || recordsAccess();
    }

    /* access modifiers changed from: package-private */
    public boolean recordsWrite() {
        return expiresAfterWrite() || refreshes();
    }

    /* access modifiers changed from: package-private */
    public boolean refreshes() {
        return this.refreshNanos > 0;
    }

    public V replace(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int hash = hash(k);
        return segmentFor(hash).replace(k, hash, v);
    }

    /* access modifiers changed from: package-private */
    public Segment<K, V> segmentFor(int i) {
        return this.segments[(i >>> this.segmentShift) & this.segmentMask];
    }

    public int size() {
        return Ints.saturatedCast(longSize());
    }

    /* access modifiers changed from: package-private */
    public boolean usesAccessEntries() {
        return usesAccessQueue() || recordsAccess();
    }

    /* access modifiers changed from: package-private */
    public boolean usesAccessQueue() {
        return expiresAfterAccess() || evictsBySize();
    }

    /* access modifiers changed from: package-private */
    public boolean usesKeyReferences() {
        return this.keyStrength != Strength.STRONG;
    }

    /* access modifiers changed from: package-private */
    public boolean usesValueReferences() {
        return this.valueStrength != Strength.STRONG;
    }

    /* access modifiers changed from: package-private */
    public boolean usesWriteEntries() {
        return usesWriteQueue() || recordsWrite();
    }

    /* access modifiers changed from: package-private */
    public boolean usesWriteQueue() {
        return expiresAfterWrite();
    }

    public Collection<V> values() {
        Collection<V> collection = this.values;
        if (collection != null) {
            return collection;
        }
        Values values2 = new Values();
        this.values = values2;
        return values2;
    }

    static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final LocalCache<K, V> localCache;

        LocalManualCache(CacheBuilder<? super K, ? super V> cacheBuilder) {
            this(new LocalCache(cacheBuilder, (CacheLoader) null));
        }

        public V getIfPresent(Object obj) {
            return this.localCache.getIfPresent(obj);
        }

        public void invalidateAll() {
            this.localCache.clear();
        }

        public void put(K k, V v) {
            this.localCache.put(k, v);
        }

        /* access modifiers changed from: package-private */
        public Object writeReplace() {
            return new ManualSerializationProxy(this.localCache);
        }

        private LocalManualCache(LocalCache<K, V> localCache2) {
            this.localCache = localCache2;
        }
    }

    public boolean isEmpty() {
        Segment<K, V>[] segmentArr = this.segments;
        long j = 0;
        for (int i = 0; i < segmentArr.length; i++) {
            if (segmentArr[i].count != 0) {
                return false;
            }
            j += (long) segmentArr[i].modCount;
        }
        if (j == 0) {
            return true;
        }
        for (int i2 = 0; i2 < segmentArr.length; i2++) {
            if (segmentArr[i2].count != 0) {
                return false;
            }
            j -= (long) segmentArr[i2].modCount;
        }
        if (j == 0) {
            return true;
        }
        return false;
    }

    public boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        int hash = hash(obj);
        return segmentFor(hash).containsKey(obj, hash);
    }

    public boolean containsValue(Object obj) {
        Object obj2 = obj;
        if (obj2 == null) {
            return false;
        }
        long read = this.ticker.read();
        Segment<K, V>[] segmentArr = this.segments;
        long j = -1;
        int i = 0;
        while (i < 3) {
            int length = segmentArr.length;
            long j2 = 0;
            int i2 = 0;
            while (i2 < length) {
                Segment<K, V> segment = segmentArr[i2];
                int i3 = segment.count;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = segment.table;
                for (int i4 = 0; i4 < atomicReferenceArray.length(); i4++) {
                    ReferenceEntry referenceEntry = atomicReferenceArray.get(i4);
                    while (referenceEntry != null) {
                        Segment<K, V>[] segmentArr2 = segmentArr;
                        V liveValue = segment.getLiveValue(referenceEntry, read);
                        long j3 = read;
                        if (liveValue != null && this.valueEquivalence.equivalent(obj2, liveValue)) {
                            return true;
                        }
                        referenceEntry = referenceEntry.getNext();
                        segmentArr = segmentArr2;
                        read = j3;
                    }
                    long j4 = read;
                    Segment<K, V>[] segmentArr3 = segmentArr;
                }
                Segment<K, V>[] segmentArr4 = segmentArr;
                j2 += (long) segment.modCount;
                i2++;
                read = read;
            }
            long j5 = read;
            Segment<K, V>[] segmentArr5 = segmentArr;
            if (j2 == j) {
                return false;
            }
            i++;
            j = j2;
            segmentArr = segmentArr5;
            read = j5;
        }
        return false;
    }

    public V get(Object obj) {
        if (obj == null) {
            return null;
        }
        int hash = hash(obj);
        return segmentFor(hash).get(obj, hash);
    }

    public V remove(Object obj) {
        if (obj == null) {
            return null;
        }
        int hash = hash(obj);
        return segmentFor(hash).remove(obj, hash);
    }

    public boolean replace(K k, V v, V v2) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v2);
        if (v == null) {
            return false;
        }
        int hash = hash(k);
        return segmentFor(hash).replace(k, hash, v, v2);
    }

    public boolean remove(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return false;
        }
        int hash = hash(obj);
        return segmentFor(hash).remove(obj, hash, obj2);
    }

    static class ManualSerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        final int concurrencyLevel;
        transient Cache<K, V> delegate;
        final long expireAfterAccessNanos;
        final long expireAfterWriteNanos;
        final Equivalence<Object> keyEquivalence;
        final Strength keyStrength;
        final CacheLoader<? super K, V> loader;
        final long maxWeight;
        final RemovalListener<? super K, ? super V> removalListener;
        final Ticker ticker;
        final Equivalence<Object> valueEquivalence;
        final Strength valueStrength;
        final Weigher<K, V> weigher;

        private ManualSerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, long j, long j2, long j3, Weigher<K, V> weigher2, int i, RemovalListener<? super K, ? super V> removalListener2, Ticker ticker2, CacheLoader<? super K, V> cacheLoader) {
            this.keyStrength = strength;
            this.valueStrength = strength2;
            this.keyEquivalence = equivalence;
            this.valueEquivalence = equivalence2;
            this.expireAfterWriteNanos = j;
            this.expireAfterAccessNanos = j2;
            this.maxWeight = j3;
            this.weigher = weigher2;
            this.concurrencyLevel = i;
            this.removalListener = removalListener2;
            this.ticker = (ticker2 == Ticker.systemTicker() || ticker2 == CacheBuilder.NULL_TICKER) ? null : ticker2;
            this.loader = cacheLoader;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.delegate = recreateCacheBuilder().build();
        }

        private Object readResolve() {
            return this.delegate;
        }

        /* access modifiers changed from: protected */
        public Cache<K, V> delegate() {
            return this.delegate;
        }

        /* access modifiers changed from: package-private */
        public CacheBuilder<K, V> recreateCacheBuilder() {
            CacheBuilder<K1, V1> removalListener2 = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
            removalListener2.strictParsing = false;
            long j = this.expireAfterWriteNanos;
            if (j > 0) {
                removalListener2.expireAfterWrite(j, TimeUnit.NANOSECONDS);
            }
            long j2 = this.expireAfterAccessNanos;
            if (j2 > 0) {
                removalListener2.expireAfterAccess(j2, TimeUnit.NANOSECONDS);
            }
            if (this.weigher != CacheBuilder.OneWeigher.INSTANCE) {
                removalListener2.weigher(this.weigher);
                long j3 = this.maxWeight;
                if (j3 != -1) {
                    removalListener2.maximumWeight(j3);
                }
            } else {
                long j4 = this.maxWeight;
                if (j4 != -1) {
                    removalListener2.maximumSize(j4);
                }
            }
            Ticker ticker2 = this.ticker;
            if (ticker2 != null) {
                removalListener2.ticker(ticker2);
            }
            return removalListener2;
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        ManualSerializationProxy(androidx.test.espresso.core.internal.deps.guava.cache.LocalCache<K, V> r17) {
            /*
                r16 = this;
                r0 = r17
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$Strength r1 = r0.keyStrength
                androidx.test.espresso.core.internal.deps.guava.cache.LocalCache$Strength r2 = r0.valueStrength
                androidx.test.espresso.core.internal.deps.guava.base.Equivalence<java.lang.Object> r3 = r0.keyEquivalence
                androidx.test.espresso.core.internal.deps.guava.base.Equivalence<java.lang.Object> r4 = r0.valueEquivalence
                long r5 = r0.expireAfterWriteNanos
                long r7 = r0.expireAfterAccessNanos
                long r9 = r0.maxWeight
                androidx.test.espresso.core.internal.deps.guava.cache.Weigher<K, V> r11 = r0.weigher
                int r12 = r0.concurrencyLevel
                androidx.test.espresso.core.internal.deps.guava.cache.RemovalListener<K, V> r13 = r0.removalListener
                androidx.test.espresso.core.internal.deps.guava.base.Ticker r14 = r0.ticker
                androidx.test.espresso.core.internal.deps.guava.cache.CacheLoader<? super K, V> r15 = r0.defaultLoader
                r0 = r16
                r0.<init>(r1, r2, r3, r4, r5, r7, r9, r11, r12, r13, r14, r15)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.core.internal.deps.guava.cache.LocalCache.ManualSerializationProxy.<init>(androidx.test.espresso.core.internal.deps.guava.cache.LocalCache):void");
        }
    }
}
