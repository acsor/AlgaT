package unibo.algat.graph;

import java.util.Random;

/**
 * <p>A factory of pseudo-random {@link MapWeightFunction} objects.</p>
 */
public class RandomMWFFactory<T> implements WeightFunctionFactory<T> {
	private Random mRandom;
	private double mLow, mHigh;

	public RandomMWFFactory (double low, double high) {
		if (high < low)
			throw new IllegalArgumentException(
				"high argument must be >= low argument"
			);

		mRandom = new Random(System.currentTimeMillis());
		mLow = low;
		mHigh = high;
	}

	@Override
	public WeightFunction<T> make(Graph<T> graph) {
		final MapWeightFunction<T> f = new MapWeightFunction<T>(graph);
		final double diff = mHigh - mLow;

		for (Node<T> u: graph.nodes()) {
			for (Node<T> v: graph.adjacents(u))
				f.assign(u, v, mLow + diff * mRandom.nextDouble());
		}

		return f;
	}
}
