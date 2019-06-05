package unibo.algat.graph;

/**
 * A memory-based weight function, assigning weight to a graph based on
 * stored data.
 * @param <T>
 * @param <W>
 */
public class MapWeightFunction<T, W extends Number> implements
	WeightFunction<T, W> {

	@Override
	public W weight(Node<T> a, Node<T> b) {
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Assigns a weight to the {@code (a, b)} edge.
	 * @throws java.lang.NullPointerException if either {@code a} or {@code b
	 * } was null.
	 */
	public void assign(Node<T> a, Node<T> b, W weight) {
		throw new UnsupportedOperationException("Not implemented");
	}
}
