package unibo.algat.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import unibo.algat.graph.MapWeightFunction;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MapWeightFunctionTests extends WeightFunctionTest {
	/**
	 * Tests {@code MapWeightFunction.weight()} when given an {@code (a, b)}
	 * edge not present on the graph.
	 */
	@Test
	void testNoEdge () {
		MapWeightFunction<Integer> w = new MapWeightFunction<>(mGraph);
		final Executable computeWeight = () -> w.weightBinding(
			mNodes.get(0), mNodes.get(2)
		);

		assertThrows(NoSuchElementException.class, computeWeight);
	}

	@Test
	void testDefaultValue () {
		final double[] defaultValues = new double[]{-10.0, 0.0, 10.0};
		MapWeightFunction<Integer> w;

		for (double def: defaultValues) {
			w = new MapWeightFunction<>(mGraph, def);
			assertEquals(
				def, w.weightBinding(mNodes.get(0), mNodes.get(1)).get()
			);
		}
	}

	/**
	 * Tests the {@code weight()} and {@code assign()} methods of
	 * {@link MapWeightFunction}.
	 */
	@Test
	void testWeightAndAssign () {
		MapWeightFunction<Integer> w = new MapWeightFunction<>(mGraph);
		final double[] weights = {-1.0, 0.0, 1.0};

		for (double weight: weights) {
			w.assign(mNodes.get(0), mNodes.get(1), weight);

			assertEquals(weight, w.weight(mNodes.get(0), mNodes.get(1)));
		}
	}
}
