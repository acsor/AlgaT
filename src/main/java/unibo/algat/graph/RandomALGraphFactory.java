package unibo.algat.graph;

/**
 * <p>A {@link RandomGraphFactory} subclass, producing random {@link ALGraph}
 * instances.</p>
 */
public class RandomALGraphFactory<T> extends RandomGraphFactory<T> {
	public RandomALGraphFactory (int vertices, int edges) {
		super(vertices, edges);
	}

	protected Graph<T> getInstance () {
        return new ALGraph<>();
	}
}
