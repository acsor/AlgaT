package unibo.algat.graph;

import java.util.*;

/**
 * An adjacency list implementation of a generic Graph.
 */
public class AListGraph<T> implements Graph<T> {
    private SortedMap<Node<T>, SortedSet<Node<T>>> mEntries;
    /**
     * The criteria by which nodes in vertices() and adjacents() are ordered
     */
    private Comparator<Node<T>> mNodesOrder;

    public AListGraph() {
        mEntries = new TreeMap<>(new InsertionOrderComparator());
    }

    public AListGraph(Comparator<Node<T>> nodesOrder) {
        mEntries = new TreeMap<>(nodesOrder);
    }

    @Override
    public void insertNode(Node<T> node) {
        if (node != null)
            mEntries.putIfAbsent(node, new TreeSet<>(mNodesOrder));
        else
            throw new IllegalArgumentException();
    }

    @Override
    public void deleteNode(Node<T> node) {
        if (node != null)
            mEntries.remove(node);
        else
            throw new IllegalArgumentException();
    }

    @Override
    public boolean containsNode(Node<T> needle) {
        if (needle != null)
            return (mEntries.containsKey(needle));
        else
            throw new IllegalArgumentException();
    }

    @Override
    public SortedSet<Node<T>> vertices() {
        // TODO: keySet returns Set, required SortedSet;
        // return mEntries.keySet();
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public SortedSet<Node<T>> adjacents(Node<T> node) {
        if (node != null) {
            if (mEntries.containsKey(node))
                return mEntries.get(node);
            else
                throw new NoSuchElementException("");
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void insertEdge(Node<T> a, Node<T> b) {
        if (a != null && b != null) {
            if (mEntries.containsKey(a) && mEntries.containsKey(b))
                mEntries.get(a).add(b);
            else
                throw new NoSuchElementException("a or b were absent");
        } else {
            throw new IllegalArgumentException("a or b were null");
        }
    }

    @Override
    public void deleteEdge(Node<T> a, Node<T> b) {
        if (a != null && b != null)
            mEntries.get(a).remove(b);
        else
            throw new IllegalArgumentException();
    }

    @Override
    public boolean containsEdge(Node<T> a, Node<T> b) {
        if (a != null && b != null)
            return mEntries.get(a).contains(b);
        else
            throw new IllegalArgumentException();
    }

    @Override
    public Comparator<Node<T>> comparator() {
        return mNodesOrder;
    }

    class InsertionOrderComparator implements Comparator<Node<T>> {
        @Override
        public int compare(Node<T> a, Node<T> b) {
            throw new UnsupportedOperationException("Not implemented");
        }
    };
}
