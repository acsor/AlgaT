package unibo.algat.tests;

import org.junit.jupiter.api.Test;
import unibo.algat.graph.DifferentialWeightFunction;
import unibo.algat.graph.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferentialWeightFunctionTests extends WeightFunctionTest {
	@Test
	void testWeight () {
		final DifferentialWeightFunction<Integer> w =
			new DifferentialWeightFunction<>(mGraph);

		for (Node<Integer> u: mGraph.nodes()) {
            for (Node<Integer> v: mGraph.adjacents(u)) {
                assertEquals(
                	v.getData() - u.getData(), w.weight(u, v).get()
				);
			}
		}
	}
}
