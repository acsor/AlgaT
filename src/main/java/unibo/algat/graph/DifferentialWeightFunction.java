package unibo.algat.graph;

import java.util.NoSuchElementException;

/**
 * An implementation of {@link unibo.algat.graph.WeightFunction} assigning
 * edge weights equal to the {@code b - a} difference between two
 * <b>numeric</b> values of a given {@code (a, b)} edge.<br><br>
 *
 * Due to restrictions of the Java generics system, this class {@code weight()}
 * method needs to return {@link Double}, the broadest numeric value available.
 *
 * @param <T> Type of the graph nodes to assign weights to.
 */
// TODO Might as well define a ArithmeticWeightFunction
public class DifferentialWeightFunction<T extends Number>
	extends WeightFunction<T> {
	public DifferentialWeightFunction(Graph<T> graph) {
        super(graph);
	}

	@Override
	public DifferentialEdgeWeight<T> weightBinding(Node<T> a, Node<T> b) {
		if (mGraph.containsEdge(a, b))
            return new DifferentialEdgeWeight<>(a, b);
		else
			throw new NoSuchElementException(
				"The (a, b) edge is not in the graph"
			);
	}

	public static class DifferentialEdgeWeight<T extends Number>
		extends EdgeWeight<T> {

		public DifferentialEdgeWeight(Node<T> a, Node<T> b) {
			super(a, b);
			bind(a.dataProperty(), b.dataProperty());
		}

		@Override
		protected double computeValue() {
			return mB.getData().doubleValue() - mA.getData().doubleValue();
		}
	}
}
