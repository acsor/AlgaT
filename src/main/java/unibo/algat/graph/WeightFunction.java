package unibo.algat.graph;

/**
 * A contract interface assigning a weight to each of a graph edge.
 *
 * @param <T> Generic type of graph
 */
public abstract class WeightFunction<T> {
	protected Graph<T> mGraph;

	public WeightFunction(Graph<T> graph) {
		mGraph = graph;
	}

	/**
	 * @return {@link Graph} objects to which weights are assigned.
	 */
	public final Graph<T> getGraph() {
		return mGraph;
	}

	/**
	 * @return The weight associated to the {@code (a, b)} edge.
	 * @throws java.util.NoSuchElementException if the {@code (a, b)} edge
	 * could not be located within the graph.
	 */
	public abstract EdgeWeight<T> weight(Node<T> a, Node<T> b);
}
