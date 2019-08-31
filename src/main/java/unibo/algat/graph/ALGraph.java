package unibo.algat.graph;

import java.util.*;

/**
 * <p>An adjacency list implementation of a {@link Graph}.</p>
 */
public class ALGraph<T> implements Graph<T> {
    private SortedMap<Vertex<T>, SortedSet<Vertex<T>>> mEntries;

    public ALGraph() {
        mEntries = new TreeMap<>();
    }

    @Override
    public boolean insertVertex(Vertex<T> vertex) {
        if (vertex != null)
            return mEntries.putIfAbsent(vertex, new TreeSet<>()) == null;
        else
            throw new NullPointerException("vertex argument was null");
    }

    @Override
    public boolean deleteVertex(Vertex<T> vertex) {
        if (vertex != null) {
            // If the vertex being removed actually existed inside the graph:
            if (mEntries.remove(vertex) != null) {
                for (Set<Vertex<T>> adjList: mEntries.values())
                    adjList.remove(vertex);

                return true;
            }

            return false;
        } else {
            throw new NullPointerException("vertex argument was null");
        }
    }

    @Override
    public boolean containsVertex(Vertex<T> needle) {
        if (needle != null)
            return mEntries.containsKey(needle);
        else
            throw new NullPointerException("needle argument was null");
    }

    @Override
    public SortedSet<Vertex<T>> vertices() {
         return (SortedSet<Vertex<T>>) mEntries.keySet();
    }

    @Override
    public SortedSet<Vertex<T>> adjacents(Vertex<T> vertex) {
        if (vertex != null) {
            if (mEntries.containsKey(vertex))
                return mEntries.get(vertex);
            else
                throw new NoSuchElementException(
                    "vertex argument not in graph"
                );
        } else {
            throw new NullPointerException("vertex argument was null");
        }
    }

    @Override
    public boolean insertEdge(Vertex<T> a, Vertex<T> b) {
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
    public boolean deleteEdge(Vertex<T> a, Vertex<T> b) {
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
    public boolean containsEdge(Vertex<T> a, Vertex<T> b) {
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

        for (Set<Vertex<T>> adj: mEntries.values())
            edges += adj.size();

        s.append(
            String.format("%s [vertices=%d] [edges=%d]\n", getClass().getName(),
            mEntries.keySet().size(), edges)
        );

        for (Vertex<T> a : mEntries.keySet()) {
            s.append(a).append(" -> ").append(mEntries.get(a)).append("\n");
        }

        return s.toString();
    }
}
