package unibo.algat.graph;

import java.util.Set;

/**
 * A Graph interface, designed to provide a baseline contract for concrete
 * implementations.
 */
public interface Graph<T> {
    /**
     * @param node Node<T> instance to insert into the current graph. If
     *             already present, the graph is unaffected.
     * @throws java.lang.NullPointerException if {@code node} happens to be
     * {@code null}.
     */
    void insertNode(Node<T> node);
    /**
     * @param node Node<T> instance to delete from the graph. If not present the
     *             graph is unaffected and thus {@code delete()} is a no-op.
     * @throws java.lang.NullPointerException if {@code node} happens to be
     * {@code null}.
     */
    void deleteNode(Node<T> node);
    /**
     * @return {@code true} if {@code needle} is a node of this graph,
     * {@code false} otherwise.
     * @throws java.lang.NullPointerException if {@code needle} happens to
     * be {@code null}.
     */
    boolean containsNode(Node<T> needle);

    /**
     * @return The set of nodes contained in the graph, empty if none are
     * present.
     */
    Set<Node<T>> nodes ();
    /**
     * @param node Node value of which a list of adjacent nodes is queried for.
     * @return A set of nodes v such that, if u is the current node, then the
     * {@code (u, v)} edge belongs to the graph. Alternatively, all nodes
     * reachable from the given one.
     * @throws java.util.NoSuchElementException if {@code node} is not found
     * within the graph.
     * @throws java.lang.NullPointerException if {@code node} is {@code
     * null}.
     */
    Set<Node<T>> adjacents(Node<T> node);

    /**
     * Inserts an {@code (a, b)} edge into the graph. Produces no change if
     * {@code (a, b)} was already present.
     * @throws java.util.NoSuchElementException if either {@code a} or {@code b}
     * aren't within the graph.
     * @throws java.lang.NullPointerException if either {@code a} or
     * {@code b} was {@code null}.
     */
    void insertEdge(Node<T> a, Node<T> b);
    /**
     * Delete the {@code (a, b)} edge from the graph, if present. If not this is
     * a no-op.
     * @throws java.util.NoSuchElementException if either {@code a} or {@code b}
     * aren't within the graph.
     * @throws java.lang.NullPointerException if either {@code a} or
     * {@code b} was {@code null}.
     */
    void deleteEdge(Node<T> a, Node<T> b);
    /**
     * @return {@code true} if {@code (a, b)} is an edge of this graph,
     * {@code false} otherwise.
     * @throws java.util.NoSuchElementException if either {@code a} or {@code b}
     * aren't within the graph.
     * @throws java.lang.NullPointerException if either {@code a} or
     * {@code b} was {@code null}.
     */
    boolean containsEdge(Node<T> a, Node<T> b);
}
