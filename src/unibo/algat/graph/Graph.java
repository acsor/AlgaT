package unibo.algat.graph;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;

/**
 * A Graph interface, designed to provide a baseline contract for concrete
 * implementations.
 */
public interface Graph<T> {
    /**
     * @param node Node<T> instance to insert into the current graph. If
     *             already present, the graph is not affected. If null,
     *             undefined behavior may occur.
     */
    void insertNode(Node<T> node);
    /**
     * @param node Node<T> instance to delete from the graph. If not present,
     *            the graph is unaffected and thus delete() is a no-op.
     *             If null, undefined behavior may occur.
     */
    void deleteNode(Node<T> node);

    /**
     * @return The set of vertices contained in the graph, empty if none are
     * present. If either argument is null, undefined behavior can occur.
     */
    Set<Node<T>> vertices();
    /**
     * @param order User-defined criteria by which the vertices should be sorted
     * @return An ordered set of vertices composing the nodes of the graph,
     * ordered according to {@code order}.
     */
    SortedSet<Node<T>> vertices(Comparator<Node<T>> order);

    /**
     * @param node Node value from which the adjacent nodes should be retrieved
     * @return A set of nodes v such that, if u is the current node, then the
     * (u, v) edge belongs to the graph. Alternatively, all nodes reachable
     * from the input one.
     */
    Set<Node<T>> adjacents(Node<T> node);
    /**
     * @param node Node value from which the adjacent nodes should be retrieved
     * @param order Order by which the adjacent nodes should be returned
     * @return An ordered set of vertices adjacent to {@code node}
     */
    SortedSet<Node<T>> adjacents(Node<T> node, Comparator<Node<T>> order);

    /**
     * Inserts an edge (a, b) into the graph.
     */
    void insertEdge(Node<T> a, Node<T> b);
    /**
     * Delete the edge (a, b) from the graph, if present. If not, this is a
     * no-op. If either argument is null, undefined behavior can occur.
     */
    void deleteEdge(Node<T> a, Node<T> b);
}
