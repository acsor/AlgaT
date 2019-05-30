package unibo.algat.graph;

import java.util.*;

/**
 * An adjacency list implementation of a generic Graph.
 */
public class ListGraph<T> implements Graph<T> {
    @Override
    public void insertNode(Node<T> node) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteNode(Node<T> node) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Set<Node<T>> vertices() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public SortedSet<Node<T>> vertices(Comparator<Node<T>> order) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Set<Node<T>> adjacents(Node<T> node) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public SortedSet<Node<T>> adjacents(
            Node<T> node, Comparator<Node<T>> order
    ) {
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
}
