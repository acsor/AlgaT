package unibo.algat.graph;

/**
 * Base interface for classes producing graph weight functions.
 *
 * @param <T> Value type of input graph.
 */
public interface WeightFunctionFactory<T> {
	WeightFunction<T> make(Graph<T> graph);
}
