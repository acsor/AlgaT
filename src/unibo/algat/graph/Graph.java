package unibo.algat.graph;

import java.util.Comparator;
import java.util.SortedSet;

/**
 * A Graph interface, designed to provide a baseline contract for concrete
 * implementations.
 */
public interface Graph<T> {
    /**
     * @param node Node<T> instance to insert into the current graph. If
     *             already present, the graph is unaffected.
     * @throws java.lang.NullPointerException if {@code node} happens to be
     * {@code null}
     */
    void insertNode(Node<T> node);
    /**
     * @param node Node<T> instance to delete from the graph. If not present,
     *             the graph is unaffected and thus delete() is a no-op.
     *             If null, undefined behavior may occur.
     */
    void deleteNode(Node<T> node);

    /**
     * @return The set of vertices contained in the graph, empty if none are
     * present.
     */
    SortedSet<Node<T>> vertices();

    /**
     * @param node Node value from which the adjacent nodes should be retrieved
     * @return A set of nodes v such that, if u is the current node, then the
     * {@code (u, v)} edge belongs to the graph. Alternatively, all nodes
     * reachable from the given one.
     * @throws java.util.NoSuchElementException if {@code node} is not found
     * within the graph or is {@code null}
     */
    SortedSet<Node<T>> adjacents(Node<T> node);

    /**
     * Inserts an {@code (a, b)} edge into the graph.
     * @throws java.util.NoSuchElementException if either {@code a} or {@code b}
     * aren't within the graph
     */
    void insertEdge(Node<T> a, Node<T> b);
    /**
     * Delete the {@code (a, b)} edge from the graph, if present. If not, or if
     * the {@code a} or {@code b} nodes aren't in the graph, this is a
     * no-op.
     */
    void deleteEdge(Node<T> a, Node<T> b);

    /**
     * @return The {@code Comparator<Node<T>>} instance used to order the
     * vertices in this graph.
     */
    Comparator<Node<T>> comparator ();
}
