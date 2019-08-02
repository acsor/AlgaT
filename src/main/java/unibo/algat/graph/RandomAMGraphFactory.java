package unibo.algat.graph;

/**
 * <p>A {@link RandomGraphFactory} subclass, producing random {@link AMGraph}
 * instances.</p>
 */
public class RandomAMGraphFactory<T> extends RandomGraphFactory<T> {
	public RandomAMGraphFactory(int nodes, int edges) {
		super(nodes, edges);
	}

	@Override
	protected Graph<T> getInstance() {
		return new AMGraph<>(mNodes);
	}
}
