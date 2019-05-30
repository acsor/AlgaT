package unibo.algat.graph;

import java.util.*;

/**
 * An adjacency list implementation of a generic Graph.
 */
public class ListGraph<T> implements Graph<T> {
    private SortedMap<Node<T>, SortedSet<Node<T>>> mEntries;
    /**
     * The criteria by which nodes in vertices() and adjacents() are ordered
     */
    private Comparator<Node<T>> mNodesOrder;

    public ListGraph () {
        mEntries = new TreeMap<>(new InsertionOrderComparator());
    }

    public ListGraph (Comparator<Node<T>> nodesOrder) {
        mEntries = new TreeMap<>(nodesOrder);
    }

    @Override
    public void insertNode(Node<T> node) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteNode(Node<T> node) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public SortedSet<Node<T>> vertices() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public SortedSet<Node<T>> adjacents(Node<T> node) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void insertEdge(Node<T> a, Node<T> b) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteEdge(Node<T> a, Node<T> b) {
        throw new UnsupportedOperationException("Not implemented");
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
