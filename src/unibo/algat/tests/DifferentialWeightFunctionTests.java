package unibo.algat.tests;

import org.junit.jupiter.api.Test;
import unibo.algat.graph.DifferentialWeightFunction;
import unibo.algat.graph.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferentialWeightFunctionTests extends WeightFunctionTest {
	@Test
	void testWeight () {
		final DifferentialWeightFunction<Integer> w =
			new DifferentialWeightFunction<>();

		for (Node<Integer> u: mGraph.vertices()) {
            for (Node<Integer> v: mGraph.adjacents(u)) {
                assertEquals(
                	v.getData() - u.getData(), w.weight(mGraph, u, v)
				);
			}
		}
	}
}
