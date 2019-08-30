package unibo.algat.graph;

import java.util.SortedSet;

/**
 * A Graph interface, designed to provide a baseline contract for concrete
 * implementations.
 */
public interface Graph<T> {
    /**
     * @param vertex Vertex<T> instance to insert into the current graph. If
     *             already present, the graph is unaffected.
	 * @return {@code true} if vertex was actually inserted into the graph,
     * {@code false} otherwise.
     * @throws java.lang.NullPointerException if {@code vertex} happens to be
     * {@code null}.
     */
    boolean insertVertex(Vertex<T> vertex);
    /**
     * Deletes a vertex from the graph (if present) and all the edges that used
     * to be associated to it.
     *
     * @param vertex Vertex<T> instance to delete from the graph. If not present
     * the graph is unaffected and thus {@code delete()} is a no-op.
     * @return {@code true} if vertex was actually deleted from the graph,
     * {@code false} otherwise.
     * @throws java.lang.NullPointerException if {@code vertex} happens to be
     * {@code null}.
     */
    boolean deleteVertex(Vertex<T> vertex);
    /**
     * @return {@code true} if {@code needle} is a vertex of this graph,
     * {@code false} otherwise.
     * @throws java.lang.NullPointerException if {@code needle} happens to
     * be {@code null}.
     */
    boolean containsVertex(Vertex<T> needle);

    /**
     * @return The set of vertices contained in the graph, empty if none are
     * present. This set will be ordered according to the natural ordering of
     * {@link Vertex}, i.e. its {@code id} field.
     */
    SortedSet<Vertex<T>> vertices();
    /**
     * @param vertex Vertex value of which a list of adjacent vertices is
     * queried for.
     * @return A set of vertices v such that, if u is the current vertex, then
     * the {@code (u, v)} edge belongs to the graph. Alternatively, all
     * vertices reachable from the given one.
     * @throws java.util.NoSuchElementException if {@code vertex} is not found
     * within the graph.
     * @throws java.lang.NullPointerException if {@code vertex} is {@code
     * null}.
     */
    SortedSet<Vertex<T>> adjacents(Vertex<T> vertex);

    /**
     * Inserts an {@code (a, b)} edge into the graph. Produces no change if
     * {@code (a, b)} was already present.
     * @return {@code true} if the {@code (a, b)} edge was actually inserted
     * into the graph, {@code false} otherwise.
     * @throws java.util.NoSuchElementException if either {@code a} or {@code b}
     * aren't within the graph.
     * @throws java.lang.NullPointerException if either {@code a} or
     * {@code b} was {@code null}.
     */
    boolean insertEdge(Vertex<T> a, Vertex<T> b);
    /**
     * Delete the {@code (a, b)} edge from the graph, if present. If not this is
     * a no-op.
     * @return {@code true} if the {@code (a, b)} edge was actually deleted
     * from the graph, {@code false} otherwise.
     * @throws java.util.NoSuchElementException if either {@code a} or {@code b}
     * aren't within the graph.
     * @throws java.lang.NullPointerException if either {@code a} or
     * {@code b} was {@code null}.
     */
    boolean deleteEdge(Vertex<T> a, Vertex<T> b);
    /**
     * @return {@code true} if {@code (a, b)} is an edge of this graph,
     * {@code false} otherwise.
     * @throws java.util.NoSuchElementException if either {@code a} or {@code b}
     * aren't within the graph.
     * @throws java.lang.NullPointerException if either {@code a} or
     * {@code b} was {@code null}.
     */
    boolean containsEdge(Vertex<T> a, Vertex<T> b);

    /**
     * Clears out all vertices and edges contained in this graph.
     */
    void clear();
}
