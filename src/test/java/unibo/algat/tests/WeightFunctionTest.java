package unibo.algat.tests;

import unibo.algat.graph.ALGraph;
import unibo.algat.graph.Graph;
import unibo.algat.graph.Vertex;
import unibo.algat.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Abstract base class providing a common graph instance to aid in testing
 * weight functions.</p>
 */
public abstract class WeightFunctionTest {
	protected final Graph<Integer> mGraph;
	protected final List<Vertex<Integer>> mVertices;
	protected final List<Pair<Vertex<Integer>, Vertex<Integer>>> mEdges;

	public WeightFunctionTest() {
		mGraph = new ALGraph<>();
		mVertices = Arrays.asList(
			new Vertex<>(0, 10), new Vertex<>(1, 20), new Vertex<>(2, 30),
			new Vertex<>(3, 40), new Vertex<>(4, 50)
		);
		mEdges = Arrays.asList(
			new Pair<>(mVertices.get(0), mVertices.get(1)),
			new Pair<>(mVertices.get(0), mVertices.get(4)),
			new Pair<>(mVertices.get(1), mVertices.get(2)),
			new Pair<>(mVertices.get(1), mVertices.get(3)),
			new Pair<>(mVertices.get(1), mVertices.get(4)),
			new Pair<>(mVertices.get(4), mVertices.get(0))
		);

		for(Vertex<Integer> n: mVertices)
			mGraph.insertVertex(n);

		for (Pair<Vertex<Integer>, Vertex<Integer>> edge: mEdges)
			mGraph.insertEdge(edge.getFirst(), edge.getSecond());
	}
}
