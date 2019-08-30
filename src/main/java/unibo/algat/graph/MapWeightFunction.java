package unibo.algat.graph;

import unibo.algat.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <p>A memory-based weight function, assigning weights to a graph based on
 * stored data.</p>
 * @param <T> Type of the vertices composing the graph.
 */
public class MapWeightFunction<T> extends WeightFunction<T> {
	private Map<Pair<Vertex<T>, Vertex<T>>, MemoryEdgeWeight<T>> mWeights;
	private double mDefault;

	/**
	 * Constructs a {@code MapWeightFunction} with default value {@code null}.
	 */
	public MapWeightFunction(Graph<T> graph) {
        this(graph, 0);
	}

	public MapWeightFunction(Graph<T> graph, double defaultValue) {
		super(graph);
		mWeights = new HashMap<>();
		mDefault = defaultValue;
	}

	@Override
	public EdgeWeight<T> weightBinding(Vertex<T> a, Vertex<T> b) {
		final Pair<Vertex<T>, Vertex<T>> key = new Pair<>(a, b);

		if (mGraph.containsEdge(a, b)) {
			MemoryEdgeWeight<T> weight = mWeights.get(key);

			if (weight == null) {
				weight = new MemoryEdgeWeight<>(a, b, mDefault);
				mWeights.put(new Pair<>(a, b), weight);
			}

			return weight;
		} else {
			throw new NoSuchElementException(
				"The (a, b) edge is not in the graph"
			);
		}
	}

	/**
	 * <p>Assigns a weight to the {@code (a, b)} edge. If {@code weight} is
	 * {@code null}, then any previously associated value is unset from the
	 * {@code (a, b)} edge.</p>
	 * @return The {@link EdgeWeight} that was assigned to the {@code (a, b)}
	 * edge.
	 * @throws java.lang.NullPointerException if either {@code a} or {@code b
	 * } was null.
	 */
	public MemoryEdgeWeight<T> assign (Vertex<T> a, Vertex<T> b, double weight) {
		if (a != null && b != null) {
			MemoryEdgeWeight<T> oldWeight = mWeights.get(new Pair<>(a, b));

			if (oldWeight == null) {
				oldWeight = new MemoryEdgeWeight<>(a, b, weight);
				mWeights.put(new Pair<>(a, b), oldWeight);
			} else {
				oldWeight.setWeight(weight);
			}

			return oldWeight;
		} else {
			throw new NullPointerException("Either a or b was null");
		}
	}

	/**
	 * <p>Removes a previously associated weight to the {@code (a, b)} edge,
	 * if any. If none is found, no action is taken.
	 * @throws java.lang.NullPointerException if either {@code a} or {@code b
	 * } was null.
	 */
	public void unassign (Vertex<T> a, Vertex<T> b) {
		if (a != null && b != null) {
			mWeights.remove(new Pair<>(a, b));
		} else {
			throw new NullPointerException("Either a or b was null");
		}
	}
}
