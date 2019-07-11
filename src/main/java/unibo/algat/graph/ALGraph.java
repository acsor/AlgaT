package unibo.algat.graph;

import java.util.*;

/**
 * An adjacency list implementation of a generic Graph.
 */
public class ALGraph<T> implements Graph<T> {
    private Map<Node<T>, Set<Node<T>>> mEntries;

    public ALGraph() {
        // TODO Refine the choice of the backing data structure for the graph
        mEntries = new Hashtable<>();
    }

    @Override
    public void insertNode(Node<T> node) {
        if (node != null)
            mEntries.putIfAbsent(node, new HashSet<>());
        else
            throw new NullPointerException("node argument was null");
    }

    @Override
    public void deleteNode(Node<T> node) {
        if (node != null)
            mEntries.remove(node);
        else
            throw new NullPointerException("node argument was null");
    }

    @Override
    public boolean containsNode(Node<T> needle) {
        if (needle != null)
            return mEntries.containsKey(needle);
        else
            throw new NullPointerException("needle argument was null");
    }

    @Override
    public Set<Node<T>> nodes() {
         return mEntries.keySet();
    }

    @Override
    public Set<Node<T>> adjacents(Node<T> node) {
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
    public void insertEdge(Node<T> a, Node<T> b) {
        if (a != null && b != null) {
            if (mEntries.containsKey(a) && mEntries.containsKey(b))
                mEntries.get(a).add(b);
            else
                throw new NoSuchElementException("Either a or b was absent");
        } else {
            throw new NullPointerException("Either a or b was null");
        }
    }

    @Override
    public void deleteEdge(Node<T> a, Node<T> b) {
        if (a != null && b != null) {
            if (mEntries.containsKey(a) && mEntries.containsKey(b))
                mEntries.get(a).remove(b);
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
