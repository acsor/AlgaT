package unibo.algat.graph;

/**
 * A contract interface assigning a weight to each of a graph edge.
 *
 * @param <T> Generic type of graph
 * @param <W> Type to represent edges weight with
 */
public interface WeightFunction<T, W extends Number> {
	/**
	 * @param g The graph to which assign weights.
	 * @return The weight associated to the {@code (a, b)} edge.
	 * @throws java.util.NoSuchElementException if the {@code (a, b)} edge
	 * could not be located within the graph.
	 */
	W weight(Graph<T> g, Node<T> a, Node<T> b);
}
