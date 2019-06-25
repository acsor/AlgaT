package unibo.algat.graph;

/**
 * <p>A {@link GraphFactory} subclass, generating {@code Graph} instances
 * randomly with a given number of nodes and edges.</p>
 */
public class RandomGraphFactory<T> implements GraphFactory {
	private int mNodes, mEdges;

	/**
	 * @param nodes Number of total nodes the generated graph will contain
	 * @param edges Number of total edges the generated graph will contain
	 */
	public RandomGraphFactory(int nodes, int edges) {
		mNodes = nodes;
		mEdges = edges;
	}

	@Override
	public ALGraph ALGraphFactory() {
        throw new UnsupportedOperationException("Not implemented");
	}
}
