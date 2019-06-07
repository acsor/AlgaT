package unibo.algat.graph;

import unibo.algat.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A memory-based weight function, assigning weight to a graph based on stored
 * data.
 * @param <T> Type of the nodes composing the graph.
 * @param <W> Type of the weight value to assign to the graph edges.
 */
public class MapWeightFunction<T, W extends Number> implements
	WeightFunction<T, W> {
	private Map<Pair<Node<T>, Node<T>>, W> mWeights;
	private W mDefault;

	public MapWeightFunction() {
        this(null);
	}

	public MapWeightFunction(W defaultValue) {
		mWeights = new HashMap<>();
		mDefault = defaultValue;
	}

	@Override
	public W weight(Graph<T> g, Node<T> a, Node<T> b) {
		// TODO Test this method, and the whole class by that matter
		final Pair<Node<T>, Node<T>> key = new Pair<>(a, b);

		if (g.containsEdge(a, b))
			return (mWeights.containsKey(key)) ? mWeights.get(key): mDefault;
		else
			throw new NoSuchElementException(
				"The (a, b) edge was not in the graph"
			);
	}

	/**
	 * Assigns a weight to the {@code (a, b)} edge.
	 * @throws java.lang.NullPointerException if either {@code a} or {@code b
	 * } was null.
	 */
	public void assign(Node<T> a, Node<T> b, W weight) {
		if (a != null && b != null)
		    mWeights.put(new Pair<>(a,b), weight);
		else throw new NullPointerException("either a or b were null");
	}

	/**
	 * <p>Sets a default value to return from {@code weight()} when a given
	 * {@code (a, b)} edge is associated no weight.</p>
	 *
	 * <p>The default can also be unset by calling {@code setDefault(null)}.</p>
	 *
	 * @param value Default value to set for unspecified edges.
	 */
	public void setDefault(W value) {
        mDefault = value;
	}
}
