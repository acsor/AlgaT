package unibo.algat.tests;

import org.junit.jupiter.api.Test;
import unibo.algat.graph.DifferentialWeightFunction;
import unibo.algat.graph.Vertex;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferentialWeightFunctionTests extends WeightFunctionTest {
	@Test
	void testWeight () {
		final DifferentialWeightFunction<Integer> w =
			new DifferentialWeightFunction<>(mGraph);

		for (Vertex<Integer> u: mGraph.vertices()) {
            for (Vertex<Integer> v: mGraph.adjacents(u)) {
                assertEquals(
                	v.getData() - u.getData(), w.weight(u, v)
				);
			}
		}
	}
}
