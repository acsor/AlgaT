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
public class DifferentialWeightFunction<T extends Number> implements
	WeightFunction<T, Double> {

	@Override
	public Double weight(Graph<T> g, Node<T> a, Node<T> b) {
		if (g.containsEdge(a, b))
			return b.getData().doubleValue() - a.getData().doubleValue();
		else
			throw new NoSuchElementException(
				"The (a, b) edge is not in the graph"
			);
	}
}
