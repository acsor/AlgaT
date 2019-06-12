package unibo.algat.tests;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import unibo.algat.graph.MapWeightFunction;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MapWeightFunctionTests extends WeightFunctionTest {
	/**
	 * Tests {@code MapWeightFunction.weight()} when given an {@code (a, b)}
	 * edge not present on the graph.
	 */
	@ParameterizedTest
	@MethodSource("defaultWeightFunction")
	void testNoEdge (MapWeightFunction<Integer, Double> w) {
		final Executable computeWeight = () -> w.weight(
			mGraph, mNodes.get(0), mNodes.get(2)
		);

		assertThrows(NoSuchElementException.class, computeWeight);
	}

	@ParameterizedTest
	@MethodSource("defaultWeightFunction")
	void testDefaultValue (MapWeightFunction<Integer, Double> w) {
		final Double[] defaultValues = new Double[]{null, 0.0, -10.0};

		for (Double def: defaultValues) {
			w.setDefault(def);
			assertEquals(def, w.weight(mGraph, mNodes.get(0), mNodes.get(1)));
		}
	}

	/**
	 * Tests the {@code weight()} and {@code assign()} methods of
	 * {@link MapWeightFunction}.
	 * @param w Weight function to test.
	 */
	@ParameterizedTest
	@MethodSource("defaultWeightFunction")
	void testWeightAndAssign (MapWeightFunction<Integer, Double> w) {
		final Double[] weights = {-1.0, 0.0, 1.0, null};

		for (Double weight: weights) {
			w.assign(mNodes.get(0), mNodes.get(1), weight);

			assertEquals(
				weight, w.weight(mGraph, mNodes.get(0), mNodes.get(1))
			);
		}
	}

	static Iterable<MapWeightFunction<Integer, Double>> defaultWeightFunction ()
	{
		return Arrays.asList(new MapWeightFunction<>());
	}
}
