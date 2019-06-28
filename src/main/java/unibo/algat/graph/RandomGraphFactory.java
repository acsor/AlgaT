package unibo.algat.graph;

/**
 * <p>A {@link GraphFactory} subclass, generating {@code Graph} instances
 * randomly with a specified number of nodes and edges.</p>
 */
public abstract class RandomGraphFactory<T> implements GraphFactory {
	protected int mNodes, mEdges;

	/**
	 * @param nodes Number of total nodes the generated graph will contain
	 * @param edges Number of total edges the generated graph will contain
	 */
	public RandomGraphFactory(int nodes, int edges) {
		mNodes = nodes;
		mEdges = edges;
	}

	@Override
	public Graph create() {
		final Graph instance = getInstance();

		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * @return A concrete, empty {@link Graph} instance to initialize.
	 */
	protected abstract Graph getInstance ();
}
