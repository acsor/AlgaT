package unibo.algat.tests;

import unibo.algat.graph.ALGraph;
import unibo.algat.graph.Graph;
import unibo.algat.graph.ObservableGraph;

/**
 * <p>Tests an {@link ObservableGraph} wrapping an {@link ALGraph}
 * implementation.</p>
 */
public class ObservableALGraphTests extends GraphTests {
	@Override
	protected Graph<Void> graphFactory() {
		return new ObservableGraph<>(new ALGraph<>());
	}
}
