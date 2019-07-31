package unibo.algat.tests;

import unibo.algat.graph.AMGraph;
import unibo.algat.graph.Graph;
import unibo.algat.graph.ObservableGraph;

/**
 * <p>Tests an {@link ObservableGraph} wrapping an {@link AMGraph}
 * implementation.</p>
 */
public class ObservableAMGraphTests extends GraphTests {
	@Override
	protected Graph<Void> graphFactory() {
		return new ObservableGraph<>(new AMGraph<>(10));
	}
}
