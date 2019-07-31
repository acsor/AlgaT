package unibo.algat.tests;

import unibo.algat.graph.ALGraph;
import unibo.algat.graph.Graph;

/**
 * <p>Tests {@link ALGraph}, an adjacency-list graph implementation.</p>
 */
class ALGraphTests extends GraphTests {
	@Override
	protected Graph<Void> graphFactory() {
		return new ALGraph<>();
	}
}
