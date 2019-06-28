package unibo.algat.graph;

/**
 * <p>A {@link RandomGraphFactory}, generating {@link ALGraph} instances only.
 * </p>
 */
public class RandomALGraphFactory extends RandomGraphFactory {
	public RandomALGraphFactory (int nodes, int edges) {
		super(nodes, edges);
	}

	protected Graph getInstance () {
		// Here you need to return a fresh instance of ALGraph, supplying
		// arguments to its constructor (if available and/or needed)
		throw new UnsupportedOperationException("Not implemented");
	}
}
