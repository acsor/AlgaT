package unibo.algat.graph;

/**
 * <p>A {@link RandomGraphFactory} subclass, producing random {@link ALGraph}
 * instances.</p>
 */
public class RandomALGraphFactory<T> extends RandomGraphFactory<T> {
	public RandomALGraphFactory (int nodes, int edges) {
		super(nodes, edges);
	}

	protected Graph<T> getInstance () {
        return new ALGraph<>();
	}
}
