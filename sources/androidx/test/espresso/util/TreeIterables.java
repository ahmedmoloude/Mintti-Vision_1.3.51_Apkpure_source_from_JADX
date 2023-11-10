package androidx.test.espresso.util;

import android.view.View;
import android.view.ViewGroup;
import androidx.test.espresso.core.internal.deps.guava.base.Function;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.AbstractIterator;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.espresso.core.internal.deps.guava.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public final class TreeIterables {
    private static final TreeViewer<View> VIEW_TREE_VIEWER = new ViewTreeViewer();

    static class DistanceRecordingTreeViewer<T> implements TreeViewer<T> {
        private final TreeViewer<T> delegateViewer;
        private final Map<T, Integer> nodeToDistance = Maps.newHashMap();
        private final T root;

        DistanceRecordingTreeViewer(T t, TreeViewer<T> treeViewer) {
            this.root = Preconditions.checkNotNull(t);
            this.delegateViewer = (TreeViewer) Preconditions.checkNotNull(treeViewer);
        }

        public Collection<T> children(T t) {
            if (t == this.root) {
                this.nodeToDistance.put(t, 0);
            }
            int distance = getDistance(t) + 1;
            Collection<T> children = this.delegateViewer.children(t);
            for (T put : children) {
                this.nodeToDistance.put(put, Integer.valueOf(distance));
            }
            return children;
        }

        /* access modifiers changed from: package-private */
        public int getDistance(T t) {
            return ((Integer) Preconditions.checkNotNull(this.nodeToDistance.get(t), "Never seen %s before", t)).intValue();
        }
    }

    private enum TraversalStrategy {
        BREADTH_FIRST {
            /* access modifiers changed from: package-private */
            public <T> void combineNewChildren(LinkedList<T> linkedList, Collection<T> collection) {
                linkedList.addAll(collection);
            }
        },
        DEPTH_FIRST {
            /* access modifiers changed from: package-private */
            public <T> void combineNewChildren(LinkedList<T> linkedList, Collection<T> collection) {
                linkedList.addAll(0, collection);
            }
        };

        /* access modifiers changed from: package-private */
        public abstract <T> void combineNewChildren(LinkedList<T> linkedList, Collection<T> collection);

        /* access modifiers changed from: package-private */
        public <T> T next(LinkedList<T> linkedList) {
            return linkedList.removeFirst();
        }
    }

    private static class TreeTraversalIterable<T> implements Iterable<T> {
        private final T root;
        /* access modifiers changed from: private */
        public final TraversalStrategy traversalStrategy;
        /* access modifiers changed from: private */
        public final TreeViewer<T> treeViewer;

        private TreeTraversalIterable(T t, TraversalStrategy traversalStrategy2, TreeViewer<T> treeViewer2) {
            this.root = Preconditions.checkNotNull(t);
            this.traversalStrategy = (TraversalStrategy) Preconditions.checkNotNull(traversalStrategy2);
            this.treeViewer = (TreeViewer) Preconditions.checkNotNull(treeViewer2);
        }

        public Iterator<T> iterator() {
            final LinkedList newLinkedList = Lists.newLinkedList();
            newLinkedList.add(this.root);
            return new AbstractIterator<T>() {
                public T computeNext() {
                    if (newLinkedList.isEmpty()) {
                        return endOfData();
                    }
                    T checkNotNull = Preconditions.checkNotNull(TreeTraversalIterable.this.traversalStrategy.next(newLinkedList), "Null items not allowed!");
                    TreeTraversalIterable.this.traversalStrategy.combineNewChildren(newLinkedList, TreeTraversalIterable.this.treeViewer.children(checkNotNull));
                    return checkNotNull;
                }
            };
        }
    }

    interface TreeViewer<T> {
        Collection<T> children(T t);
    }

    public static class ViewAndDistance {
        private final int distanceFromRoot;
        private final View view;

        private ViewAndDistance(View view2, int i) {
            this.view = view2;
            this.distanceFromRoot = i;
        }

        public int getDistanceFromRoot() {
            return this.distanceFromRoot;
        }

        public View getView() {
            return this.view;
        }
    }

    static class ViewTreeViewer implements TreeViewer<View> {
        ViewTreeViewer() {
        }

        public Collection<View> children(View view) {
            Preconditions.checkNotNull(view);
            if (!(view instanceof ViewGroup)) {
                return Collections.emptyList();
            }
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            ArrayList newArrayList = Lists.newArrayList();
            for (int i = 0; i < childCount; i++) {
                newArrayList.add(viewGroup.getChildAt(i));
            }
            return newArrayList;
        }
    }

    private TreeIterables() {
    }

    static <T> Iterable<T> breadthFirstTraversal(T t, TreeViewer<T> treeViewer) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(treeViewer);
        return new TreeTraversalIterable(t, TraversalStrategy.BREADTH_FIRST, treeViewer);
    }

    public static Iterable<View> breadthFirstViewTraversal(View view) {
        return breadthFirstTraversal(view, VIEW_TREE_VIEWER);
    }

    static <T> Iterable<T> depthFirstTraversal(T t, TreeViewer<T> treeViewer) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(treeViewer);
        return new TreeTraversalIterable(t, TraversalStrategy.DEPTH_FIRST, treeViewer);
    }

    public static Iterable<View> depthFirstViewTraversal(View view) {
        return depthFirstTraversal(view, VIEW_TREE_VIEWER);
    }

    public static Iterable<ViewAndDistance> depthFirstViewTraversalWithDistance(View view) {
        final DistanceRecordingTreeViewer distanceRecordingTreeViewer = new DistanceRecordingTreeViewer(view, VIEW_TREE_VIEWER);
        return Iterables.transform(depthFirstTraversal(view, distanceRecordingTreeViewer), new Function<View, ViewAndDistance>() {
            public ViewAndDistance apply(View view) {
                return new ViewAndDistance(view, DistanceRecordingTreeViewer.this.getDistance(view));
            }
        });
    }
}
