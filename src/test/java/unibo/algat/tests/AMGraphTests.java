package unibo.algat.tests;

import unibo.algat.graph.AMGraph;
import unibo.algat.graph.Graph;

/**
 * <p>Tests {@link AMGraph}, an adjacency-matrix graph implementation.</p>
 */
class AMGraphTests extends GraphTests {
	@Override
	protected Graph<Void> graphFactory() {
		return new AMGraph<>(10);
	}
}
