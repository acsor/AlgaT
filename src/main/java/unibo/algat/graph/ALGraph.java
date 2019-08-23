package unibo.algat.graph;

import java.util.*;

/**
 * <p>An adjacency list implementation of a {@link Graph}.</p>
 */
public class ALGraph<T> implements Graph<T> {
    private SortedMap<Node<T>, SortedSet<Node<T>>> mEntries;

    public ALGraph() {
        mEntries = new TreeMap<>();
    }

    @Override
    public boolean insertNode(Node<T> node) {
        if (node != null)
            return mEntries.putIfAbsent(node, new TreeSet<>()) == null;
        else
            throw new NullPointerException("node argument was null");
    }

    @Override
    public boolean deleteNode(Node<T> node) {
        if (node != null) {
            // If the node being removed actually existed inside the graph:
            if (mEntries.remove(node) != null) {
                for (Set<Node<T>> adjList: mEntries.values())
                    adjList.remove(node);

                return true;
            }

            return false;
        } else {
            throw new NullPointerException("node argument was null");
        }
    }

    @Override
    public boolean containsNode(Node<T> needle) {
        if (needle != null)
            return mEntries.containsKey(needle);
        else
            throw new NullPointerException("needle argument was null");
    }

    @Override
    public SortedSet<Node<T>> nodes() {
         return (SortedSet<Node<T>>) mEntries.keySet();
    }

    @Override
    public SortedSet<Node<T>> adjacents(Node<T> node) {
        if (node != null) {
            if (mEntries.containsKey(node))
                return mEntries.get(node);
            else
                throw new NoSuchElementException("node argument not in graph");
        } else {
            throw new NullPointerException("node argument was null");
        }
    }

    @Override
    public boolean insertEdge(Node<T> a, Node<T> b) {
        if (a != null && b != null) {
            if (mEntries.containsKey(a) && mEntries.containsKey(b))
                return mEntries.get(a).add(b);
            else
                throw new NoSuchElementException("Either a or b was absent");
        } else {
            throw new NullPointerException("Either a or b was null");
        }
    }

    @Override
    public boolean deleteEdge(Node<T> a, Node<T> b) {
        if (a != null && b != null) {
            if (mEntries.containsKey(a) && mEntries.containsKey(b))
                return mEntries.get(a).remove(b);
            else
                throw new NoSuchElementException("Either a or b was absent");
        } else {
            throw new NullPointerException("Either a or b was null");
        }
    }

    @Override
    public boolean containsEdge(Node<T> a, Node<T> b) {
        if (a != null && b != null) {
            if (mEntries.containsKey(a) && mEntries.containsKey(b))
                return mEntries.get(a).contains(b);
            else
                throw new NoSuchElementException("Either a or b was absent");
        } else {
            throw new NullPointerException("Either a or b was null");
        }
    }

    @Override
    public void clear() {
        mEntries.clear();
    }

    @Override
    public String toString(){
        final StringBuilder s = new StringBuilder();
        int edges = 0;

        for (Set<Node<T>> adj: mEntries.values())
            edges += adj.size();

        s.append(
            String.format("%s [nodes=%d] [edges=%d]\n", getClass().getName(),
            mEntries.keySet().size(), edges)
        );

        for (Node<T> a : mEntries.keySet()) {
            s.append(a).append(" -> ").append(mEntries.get(a)).append("\n");
        }

        return s.toString();
    }
}
